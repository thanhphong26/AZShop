package com.azshop.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.DetailModel;
import com.azshop.models.OrderModel;
import com.azshop.models.PaymentModel;
import com.azshop.models.UserModel;
import com.azshop.service.IDetailService;
import com.azshop.service.IOrderService;
import com.azshop.service.IPaymentService;
import com.azshop.service.impl.DetailServiceImpl;
import com.azshop.service.impl.OrderServiceImpl;
import com.azshop.service.impl.PaymentServiceImpl;

@WebServlet(urlPatterns = { "/listOrder", "/customerConfirm", "/detailOrder", "/itemRating" })
@MultipartConfig
public class OrderController extends HttpServlet {

	IOrderService orderService = new OrderServiceImpl();
	IDetailService detailService = new DetailServiceImpl();
	IPaymentService paymentService = new PaymentServiceImpl();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			String url = req.getRequestURI().toString();
			if (url.contains("listOrder")) {
				listOrder(req, resp);
			} else if (url.contains("detailOrder")) {
				detailOrder(req, resp);
			} else if (url.contains("itemRating")) {
				itemRating(req, resp);
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();
		if (url.contains("customerConfirm")) {
			String act = req.getParameter("action");
			String conf = req.getParameter("confirm");
			int orderID = Integer.parseInt(req.getParameter("orderID"));
			if ("cancelOrder".equals(act)) {
				orderService.updateStatusOrder(orderID, 5);
				listOrder(req, resp);
			} else if ("confirmOrder".equals(act)) {
				orderService.confirmOrder(orderID, 1);
				listOrder(req, resp);
			} else if ("confirmDetailOrder".equals(act)) {
				orderService.confirmOrder(orderID, 1);
				detailOrder(req, resp);
			} else if ("cancelDetailOrder".equals(act)) {
				orderService.updateStatusOrder(orderID, 5);
				detailOrder(req, resp);
			} 
		}
	}

	private void listOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		List<OrderModel> listOrder = orderService.listOrderByCustomerID(user.getUserID());

		req.setAttribute("listOrder", listOrder);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/order/listOrder.jsp");
		rd.forward(req, resp);
	}

	private void detailOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int orderID = Integer.parseInt(req.getParameter("orderID"));

		OrderModel order = orderService.getOrderByID(orderID);
		List<DetailModel> listDetail = order.getDetails();
		double totalCost = 0.0;
		for (DetailModel detail : listDetail) {
			if (detail.getItem().getPromotionPrice() == 0) {
				totalCost += detail.getItem().getOriginalPrice() * detail.getQuantity();
			} else {
				totalCost += detail.getItem().getPromotionPrice() * detail.getQuantity();
			}
		}
		PaymentModel payment = paymentService.findPaymentByID(orderID);
		req.setAttribute("payment", payment);
		req.setAttribute("rawPrice", totalCost);
		req.setAttribute("order", order);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/order/detailOrder.jsp");
		rd.forward(req, resp);
	}

	private void itemRating(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int orderID = Integer.parseInt(req.getParameter("orderID"));
		int itemID = Integer.parseInt(req.getParameter("itemID"));

		DetailModel detail = detailService.findDetailByItemID(orderID, itemID);
		OrderModel order = orderService.getOrderByOrderID(orderID);

		req.setAttribute("detail", detail);
		req.setAttribute("order", order);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/order/rating.jsp");
		rd.forward(req, resp);
	}

}
