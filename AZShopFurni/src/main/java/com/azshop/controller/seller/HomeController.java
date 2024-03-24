package com.azshop.controller.seller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.OrderModel;
import com.azshop.models.UserModel;
import com.azshop.service.IOrderService;
import com.azshop.service.IReportService;
import com.azshop.service.impl.OrderServiceImpl;
import com.azshop.service.impl.ReportServiceImpl;

@WebServlet(urlPatterns = { "/sellerHome" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = -4373195157235388015L;
	IReportService reportService = new ReportServiceImpl();
	IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 1) {
				String url = req.getRequestURI();
				if (url.contains("sellerHome")) {
					sellerHome(req, resp, user.getUserID());
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void sellerHome(HttpServletRequest req, HttpServletResponse resp, int sellerID)
			throws ServletException, IOException {
		List<OrderModel> listOrder = new ArrayList<OrderModel>();
		List<OrderModel> listOrderUnpre = new ArrayList<OrderModel>();
		List<OrderModel> listOrder1 = orderService.findOrderBySeller();
		List<List<Object>> listTotal = reportService.reportSellerOrderByYear(sellerID);
		for (OrderModel orderModel : listOrder1) {
			if (orderModel.getStatus() == 0) {
				listOrder.add(orderModel);
			} else if (orderModel.getStatus() == 1) {
				listOrderUnpre.add(orderModel);
			}
		}
		List<OrderModel> listOrders = orderService.findHisOrder(sellerID);
		int orderCanceled = listOrders.stream().filter(OrderModel -> OrderModel.getStatus() == 5)
				.collect(Collectors.toList()).size();
		int orderUnconfirm = listOrder.size();
		int orderUnpre = listOrderUnpre.size();
		int orderComplete = listOrders.stream()
				.filter(OrderModel -> OrderModel.getStatus() == 4 && OrderModel.getPayment().getStatus() == 1)
				.collect(Collectors.toList()).size();

		req.setAttribute("listOrderByMonth", listTotal);
		req.setAttribute("orderCanceled", orderCanceled);
		req.setAttribute("orderUnconfirm", orderUnconfirm);
		req.setAttribute("orderUnpre", orderUnpre);
		req.setAttribute("orderComplete", orderComplete);
		req.getRequestDispatcher("/views/seller/home.jsp").forward(req, resp);
	}
}
