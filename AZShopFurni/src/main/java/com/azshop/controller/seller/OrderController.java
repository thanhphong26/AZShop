package com.azshop.controller.seller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.OrderModel;
import com.azshop.models.PaymentModel;
import com.azshop.models.UserModel;
import com.azshop.service.IOrderService;
import com.azshop.service.IPaymentService;
import com.azshop.service.impl.OrderServiceImpl;
import com.azshop.service.impl.PaymentServiceImpl;

@WebServlet(urlPatterns = { "/sellerOrders", "/sellerUnpreOrder", "/sellerUpdateOrder", "/sellerHisOrder",
		"/sellerOrderDetail", "/sellerOrderComplete", "/sellerOrderCanceled", "/sellerConfirmedOrder" })
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = -7252150861521808974L;
	IOrderService orderService = new OrderServiceImpl();
	IPaymentService payService=new PaymentServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			List<OrderModel> listOrder = orderService.findHisOrder(user.getUserID());
			if (user.getType() == 1) {
				int userID = user.getUserID();
				String url = req.getRequestURI();
				if (url.contains("sellerOrders")) {
					int status = 0;
					sellerOrder(req, resp, status);
				} else if (url.contains("sellerUnpreOrder")) {
					int status = 1;
					sellerOrder(req, resp, status);
				} else if (url.contains("sellerHisOrder")) {
					req.setAttribute("listOrder", listOrder);
					req.getRequestDispatcher("/views/seller/order/listHisOrder.jsp").forward(req, resp);
				} else if (url.contains("sellerOrderDetail")) {
					sellerDetailOrder(req, resp);
				} else if (url.contains("sellerOrderComplete")) {
					filterOrderComplete(req, resp, userID, listOrder);
				} else if (url.contains("sellerOrderCanceled")) {
					filterOrderCanceled(req, resp, userID,listOrder);
				} else if (url.contains("sellerConfirmedOrder")) {
					req.setAttribute("listOrder", listOrder);
					req.getRequestDispatcher("/views/seller/order/listHisConfirmOrder.jsp").forward(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		int sellerID = user.getUserID();
		if (url.contains("sellerUpdateOrder")) {
			sellerUpdateOrder(req, resp, sellerID);
		}
	}

	private void sellerOrder(HttpServletRequest req, HttpServletResponse resp, int status)
			throws ServletException, IOException {
		List<OrderModel> listOrder = orderService.findOrderBySeller();
		if (status == 0) {
			listOrder = listOrder.stream().filter(OrderModel -> OrderModel.getStatus() == status )
					.collect(Collectors.toList());
		} else if (status == 1) {
			listOrder = listOrder.stream().filter(OrderModel -> OrderModel.getStatus() == status)
					.collect(Collectors.toList());
		}
		req.setAttribute("listOrder", listOrder);
		req.setAttribute("status", status);
		req.getRequestDispatcher("/views/seller/order/listOrder.jsp").forward(req, resp);
	}
	private void sellerDetailOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int orderID = Integer.parseInt(req.getParameter("orderID"));
		OrderModel order = orderService.getOrderByOrderID(orderID);
		req.setAttribute("order", order);
		req.getRequestDispatcher("/views/seller/order/detailOrder.jsp").forward(req, resp);
	}

	private void sellerUpdateOrder(HttpServletRequest req, HttpServletResponse resp, int sellerID)
			throws ServletException, IOException {
		int status = Integer.parseInt(req.getParameter("status"));
		int orderID = Integer.parseInt(req.getParameter("orderID"));
		orderService.updateStatusOrder(orderID, sellerID, status + 1);
		if (status == 0) {
			PaymentModel pay = payService.findPaymentByID(orderID);
			if(pay.getMethod()==1) {
				pay.setStatus(1);
				payService.updatePayment(pay);
			}
			resp.sendRedirect("sellerUnpreOrder");
		} else if (status == 1) {
			resp.sendRedirect("sellerConfirmedOrder");
		}
	}

	private void filterOrderComplete(HttpServletRequest req, HttpServletResponse resp, int sellerID,List<OrderModel> listOrder )
			throws ServletException, IOException {
		listOrder = listOrder.stream()
				.filter(OrderModel -> OrderModel.getStatus() == 4 && OrderModel.getPayment().getStatus() == 1)
				.collect(Collectors.toList());
		req.setAttribute("listOrder", listOrder);
		req.getRequestDispatcher("/views/seller/order/listOrderComplete.jsp").forward(req, resp);
	}

	private void filterOrderCanceled(HttpServletRequest req, HttpServletResponse resp, int sellerID,List<OrderModel> listOrder)
			throws ServletException, IOException {
		listOrder = listOrder.stream().filter(OrderModel -> OrderModel.getStatus() == 5).collect(Collectors.toList());
		req.setAttribute("listOrder", listOrder);
		req.getRequestDispatcher("/views/seller/order/listOrderCanceled.jsp").forward(req, resp);
	}
}
