package com.azshop.controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.bean.MyItem;
import com.azshop.models.DetailModel;
import com.azshop.models.OrderModel;
import com.azshop.models.PaymentModel;
import com.azshop.models.UserModel;
import com.azshop.service.IDetailService;
import com.azshop.service.IOrderService;
import com.azshop.service.IPaymentService;
import com.azshop.service.IReportService;
import com.azshop.service.IUserService;
import com.azshop.service.impl.DetailServiceImpl;
import com.azshop.service.impl.OrderServiceImpl;
import com.azshop.service.impl.PaymentServiceImpl;
import com.azshop.service.impl.ReportServiceImpl;
import com.azshop.service.impl.UserServiceImpl;
import com.google.api.client.util.DateTime;

@WebServlet(urlPatterns = { "/adminOrder", "/adminUpdateOrder", "/aminDeleteOrder", "/adminFilterOrder" })
public class OrderController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IOrderService orderService = new OrderServiceImpl();
	IUserService userService = new UserServiceImpl();
	IPaymentService paymentService = new PaymentServiceImpl();
	IReportService reportService = new ReportServiceImpl();
	IDetailService detailService = new DetailServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 3) {
				session.getAttribute("listTotal");
				String url = req.getRequestURI().toString();
				if (url.contains("adminOrder")) {
					findAllOrder(req, resp);
				} else if (url.contains("adminUpdateOrder")) {
					updateOrder(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void updateOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int orderID = Integer.parseInt(req.getParameter("orderID"));
		List<OrderModel> listOrder1 = orderService.findAllOrder();
		OrderModel order = listOrder1.stream().filter(OrderModel -> OrderModel.getOrderID() == orderID).findFirst()
				.orElse(null);
		List<DetailModel> listdeDetailModels = detailService.listDetail(orderID);
		order.setDetails(listdeDetailModels);
		req.setAttribute("order", order);
		req.getRequestDispatcher("/views/admin/order/orderDetail.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String url = req.getRequestURI().toString();

		if (url.contains("adminFilterOrder")) {
			List<OrderModel> listOrder = orderService.findAllOrder();
			int forder = Integer.parseInt(req.getParameter("fOrderDate"));
			int fstatusOrder = Integer.parseInt(req.getParameter("fStatuOrder"));
			int fstatusPay = Integer.parseInt(req.getParameter("fStatuPayment"));
			if (forder != -1) {
				listOrder = filterByOrderDate(listOrder, fstatusPay);
			}
			if (fstatusOrder != -1) {
				listOrder = filterByOrderStatus(listOrder, fstatusOrder);
			}
			if (fstatusPay != -1) {
				listOrder = filterByPaymentStatus(listOrder, fstatusPay);
			}
			req.setAttribute("listOrder", listOrder);
			RequestDispatcher rd = req.getRequestDispatcher("/views/admin/order/order.jsp");
			rd.forward(req, resp);
		} else if (url.contains("adminUpdateOrder")) {
			int orderID = Integer.parseInt(req.getParameter("orderID"));
			int ustatusOrder = Integer.parseInt(req.getParameter("uStatuOrder"));
			int ustatusPay = Integer.parseInt(req.getParameter("uStatuPayment"));
			orderService.updateStatusOrder(orderID, ustatusOrder);
			PaymentModel pay = paymentService.findPaymentByID(orderID);
			pay.setStatus(ustatusPay);
			paymentService.updatePayment(pay);
			resp.sendRedirect("adminOrder");
		}

	}

	private List<OrderModel> filterByOrderDate(List<OrderModel> list, int daysAgo) {

		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -daysAgo);
		Date resultDate = calendar.getTime();
		return list.stream().filter(order -> order.getOrderDate().compareTo(resultDate) < 0)
				.collect(Collectors.toList());
	}

	private List<OrderModel> filterByOrderStatus(List<OrderModel> list, int orderSta) {
		return list.stream().filter(OrderModel -> OrderModel.getStatus() == orderSta).collect(Collectors.toList());
	}

	private List<OrderModel> filterByPaymentStatus(List<OrderModel> list, int paySta) {
		return list.stream().filter(OrderModel -> OrderModel.getPayment().getStatus() == paySta)
				.collect(Collectors.toList());
	}

	private void findAllOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<OrderModel> listOrder = orderService.findAllOrder();
		List<OrderModel> listOrder1 = new ArrayList<>();
		List<List<Object>> listOrder2 = new ArrayList<>();
		List<List<Object>> listOrderSta = new ArrayList<>();
		List<List<Object>> listTotal = reportService.reportTotalMoneyInMonth();
		int sumTotal = 0;
		int sumOrder = 0;
		int countPaymentCard = 0;
		int countPaymentNormal = 0;
		int totalPaymentCard = 0;
		int totalPayMentNormal = 0;
		int countNoPay = 0;
		HttpSession session = req.getSession(true);
		Date currentDate = new Date();
		int monthNow = currentDate.getMonth() + 1;
		int today = currentDate.getDate();

		for (List<Object> list : listTotal) {
			sumTotal += (long) list.get(1);
			sumOrder += (int) list.get(2);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate); // Đặt ngày trong tuần về ngày đầu tiên (Chủ Nhật) và trừ đi số ngày đã qua
										// trong tuần
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK)); // Lấy ngày đầu tiên của tuần
		Date firstDayOfWeek = calendar.getTime();
		for (OrderModel list : listOrder) {
			if (list.getOrderDate().compareTo(firstDayOfWeek) > 0)
				listOrder1.add(list);
			if (list.getOrderDate().getMonth() > currentDate.getMonth() - 6
					&& list.getOrderDate().getYear() == currentDate.getYear()) {
				List<Object> row = new ArrayList<>();
				row.add((list.getOrderDate().getMonth()) + 1);
				row.add(list.getStatus());
				listOrder2.add(row);
			}
			if (list.getOrderDate().getMonth() == currentDate.getMonth()
					&& list.getOrderDate().getYear() == currentDate.getYear()) {
				if (list.getPayment().getStatus() == 1) {
					if (list.getPayment().getMethod() == 1) {
						countPaymentCard += 1;
						totalPaymentCard += list.getTotalMoney();
					} else {
						countPaymentNormal += 1;
						totalPayMentNormal += list.getTotalMoney();
					}
				}
				if (list.getStatus() != 5 && list.getPayment().getStatus() == 0) {
					countNoPay += 1;
				}
			}
		}
		for (int i = monthNow - 6; i <= monthNow; i++) {
			List<Object> row = new ArrayList<>();
			row.add(i);
			int countHuy = 0;
			int countTC = 0;
			for (List<Object> list : listOrder2) {
				if ((int) list.get(1) == 5 && (int) list.get(0) == i) {
					countHuy += 1;
				}
				if ((int) list.get(1) == 4 && (int) list.get(0) == i) {
					countTC += 1;
				}
			}
			row.add(countTC);
			row.add(countHuy);
			listOrderSta.add(row);
		}

		int chXN = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 0).collect(Collectors.toList())
				.size();
		int daXN = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 1).collect(Collectors.toList())
				.size();
		int chDG = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 2).collect(Collectors.toList())
				.size();
		int dVC = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 3).collect(Collectors.toList())
				.size();
		int thCong = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 4).collect(Collectors.toList())
				.size();
		int daHuy = listOrder1.stream().filter(OrderModel -> OrderModel.getStatus() == 5).collect(Collectors.toList())
				.size();
		session.setAttribute("chXN", chXN);
		session.setAttribute("daXN", daXN);
		session.setAttribute("chDG", chDG);
		session.setAttribute("dVC", dVC);
		session.setAttribute("thCong", thCong);
		session.setAttribute("daHuy", daHuy);
		session.setAttribute("sumTotal", sumTotal);
		session.setAttribute("sumOrder", sumOrder);
		session.setAttribute("listPayMentInMonth", listTotal);
		session.setAttribute("listOrderSta", listOrderSta);
		session.setAttribute("countPaymentCard", countPaymentCard);
		session.setAttribute("countPaymentNormal", countPaymentNormal);
		session.setAttribute("totalPaymentCard", totalPaymentCard);
		session.setAttribute("totalPayMentNormal", totalPayMentNormal);
		session.setAttribute("countNoPay", countNoPay);
		req.setAttribute("listOrder", listOrder);
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/order/order.jsp");
		rd.forward(req, resp);
	}

}
