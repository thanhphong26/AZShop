package com.azshop.controller.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.DetailModel;
import com.azshop.models.OrderModel;
import com.azshop.models.UserModel;
import com.azshop.service.IDetailService;
import com.azshop.service.IOrderService;
import com.azshop.service.impl.DetailServiceImpl;
import com.azshop.service.impl.OrderServiceImpl;

@WebServlet("/submitReview")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IDetailService detailService = new DetailServiceImpl();
	IOrderService orderService = new OrderServiceImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		int itemID = Integer.parseInt(req.getParameter("itemID"));
		int orderID = Integer.parseInt(req.getParameter("orderID"));
		int rating = Integer.parseInt(req.getParameter("rating"));
		String content = req.getParameter("content");

		DetailModel detail = new DetailModel();
		detail.setItemID(itemID);
		detail.setOrderID(orderID);
		detail.setRating(rating);
		detail.setContent(content);
		detail.setEvaluationDate(new Date());
		detailService.updateDetail(detail);

		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		List<OrderModel> listOrder = orderService.listOrderByCustomerID(user.getUserID());

		req.setAttribute("listOrder", listOrder);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/order/listOrder.jsp");
		rd.forward(req, resp);
	}
}
