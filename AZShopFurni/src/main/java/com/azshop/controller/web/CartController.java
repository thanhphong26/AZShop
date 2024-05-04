package com.azshop.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.CartModel;
import com.azshop.models.ItemModel;
import com.azshop.models.UserModel;
import com.azshop.service.ICartService;
import com.azshop.service.IItemService;
import com.azshop.service.impl.CartServiceImpl;
import com.azshop.service.impl.ItemServiceImpl;

@WebServlet({ "/carts", "/addToCart", "/deleteCart", "/deleteCarts", "/updateCart" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ICartService cartService = new CartServiceImpl();
	IItemService itemService = new ItemServiceImpl();
	RequestDispatcher rd = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String url = req.getRequestURI().toString();

		if (url.contains("carts")) {
			getAllCart(req, resp);
		} else if (url.contains("deleteCarts")) {
			deleteCarts(req, resp);
		} else if (url.contains("deleteCart")) {
			deleteCart(req, resp);
		} else if (url.contains("updateCart")) {
			updateCart(req, resp);
		}
	}

	private void updateCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int customerID = Integer.parseInt(req.getParameter("customerID"));
		int itemID = Integer.parseInt(req.getParameter("itemID"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));

		CartModel cart = new CartModel();
		cart.setCustomerID(customerID);
		cart.setItemID(itemID);

		ItemModel item = new ItemModel();
		item = itemService.findOne(itemID);
		if (item.getStock() >= quantity) {
			cart.setQuantity(quantity);
			cartService.update(cart);
		}
		resp.sendRedirect("carts");
	}

	private void deleteCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int customerID = Integer.parseInt(req.getParameter("customerID"));
		int itemID = Integer.parseInt(req.getParameter("itemID"));

		cartService.delete(customerID, itemID);
		resp.sendRedirect("carts");
	}

	private void deleteCarts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		UserModel user = (UserModel) session.getAttribute("user");
		int customerID = user.getUserID();
		cartService.deleteAllByCustomerID(customerID);
		resp.sendRedirect("carts");
	}

	private void getAllCart(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		UserModel user = (UserModel) session.getAttribute("user");
		List<CartModel> listCart = cartService.findByCustomerId(user.getUserID());

		int subTotal = 0;
		for (CartModel cart : listCart) {
			subTotal += cart.getTotalPrice();
		}

		req.setAttribute("carts", listCart);
		req.setAttribute("subTotal", subTotal);
		rd = req.getRequestDispatcher("/views/web/carts/carts.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String url = req.getRequestURI().toString();
		if (url.contains("addToCart")) {
			if(!doAction(req, resp)) {
				RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
				rDispatcher. forward(req, resp);
				return;
			}
			addToCart(req, resp);
		}
	}

	private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("user") == null) {
			resp.setContentType("application/json");
			resp.getWriter().write("{\"redirect\":\"" + req.getContextPath() + "/login\"}");
			return;
		}

		UserModel user = (UserModel) session.getAttribute("user");
		CartModel cart = new CartModel();
		CartModel oldCart = new CartModel();
		int customerID = user.getUserID();
		int itemID = Integer.parseInt(req.getParameter("selectedItemID"));
		int quantity = Integer.parseInt(req.getParameter("selectedQuantity"));
		
		cart.setCustomerID(customerID);
		cart.setItemID(itemID);
		oldCart = cartService.findOne(customerID, itemID);
		quantity += oldCart.getQuantity();
		cart.setQuantity(quantity);
		ItemModel item = new ItemModel();
		item = itemService.findOne(itemID);
		
		if (item.getStock() >= quantity) {
			if (oldCart.getQuantity() != 0) {
				cartService.update(cart);
			} else {
				cartService.insert(cart);
			}
			resp.sendRedirect("carts");
		} else {
			resp.setContentType("application/json");
			resp.getWriter().write("{\"error\":\"Số lượng không đủ!\"}");
		}
	}
	public boolean doAction(HttpServletRequest request, HttpServletResponse response) {
		String csrfCookie = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("csrf")) {
				csrfCookie = cookie.getValue();
			}
		}

		String csrfField = request.getParameter("csrfToken");

		// validate CSRF
		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			return false;
		}
		return true;
	}
}
