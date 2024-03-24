package com.azshop.controller.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.CartModel;
import com.azshop.models.OrderModel;
import com.azshop.models.PaymentModel;
import com.azshop.models.UserModel;
import com.azshop.models.VoucherModel;
import com.azshop.service.ICartService;
import com.azshop.service.IOrderService;
import com.azshop.service.IPaymentService;
import com.azshop.service.IVoucherCustomerService;
import com.azshop.service.IVoucherService;
import com.azshop.service.impl.CartServiceImpl;
import com.azshop.service.impl.OrderServiceImpl;
import com.azshop.service.impl.PaymentServiceImpl;
import com.azshop.service.impl.VoucherCustomerServiceImpl;
import com.azshop.service.impl.VoucherServiceImpl;

@WebServlet(urlPatterns = { "/checkout" })
public class CheckoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String webLink = "/views/web/checkout.jsp";
	RequestDispatcher rd = null;
	ICartService cartService = new CartServiceImpl();
	IVoucherService voucherService = new VoucherServiceImpl();
	IVoucherCustomerService voucherCustomerService = new VoucherCustomerServiceImpl();
	IOrderService orderService = new OrderServiceImpl();
	IPaymentService paymentService = new PaymentServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserModel user = getCurrentUser(req, resp);
		if (user == null)
			return;
		String voucherIdString = req.getParameter("voucherId");
		List<CartModel> listCart = cartService.findByCustomerId(user.getUserID());
		List<VoucherModel> listVoucher = voucherService.findVoucherByCustomerID(user.getUserID());
		String shippingMethod = req.getParameter("shippingMethod");
		int transportFee = 200000;
		
		// if ("express".equals(shippingMethod)) int transportFee = 200000
		if ("standard".equals(shippingMethod)) {
			transportFee = 50000;
		}

		double totalCost = 0.0;
		for (CartModel cart : listCart) {
			totalCost += cart.getPromotionPrice()  * cart.getQuantity() ;
		}
		req.setAttribute("rawPrice", totalCost);
		req.setAttribute("totalCost", totalCost + transportFee);

		if (voucherIdString != null) {
			int voucherId = Integer.parseInt(voucherIdString);
			VoucherModel voucher = voucherService.findOne(voucherId);

			if (totalCost < voucher.getMinimumPrice()) {
				req.setAttribute("minimumPrice", voucher.getMinimumPrice());
			} else {
				double discount = totalCost * voucher.getDiscount() / 100;
				totalCost -= discount;
				req.setAttribute("discount", discount);
				req.setAttribute("totalCost", totalCost);
			}
		}

		req.setAttribute("listVoucher", listVoucher);
		req.setAttribute("user", user);
		req.setAttribute("listCart", listCart);

		redirectCheckoutPage(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserModel user = getCurrentUser(req, resp);
		if (user == null) {
			return;
		}
		resp.setContentType("text/htm");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String city = req.getParameter("city");
		
		String address = req.getParameter("address");
		String note = req.getParameter("note");
		int payMethod = Integer.parseInt(req.getParameter("pay-method"));
		String voucherIdString = req.getParameter("voucherId");

		int discount = (int) Double.parseDouble(req.getParameter("discount"));
		int totalCost = (int) Double.parseDouble(req.getParameter("totalCost"));

		if (city.equals("none")) {
			req.setAttribute("cityError", "Thành phố không được để trống");
			req.setAttribute("address", address);
			req.setAttribute("note", note);
			doGet(req, resp);
			return;
		}
		// Phương thức vận chuyển
		String shippingMethod = req.getParameter("shippingMethod");
		int transportFee = 200000;
		
		// if ("express".equals(shippingMethod)) int transportFee = 200000
		if ("standard".equals(shippingMethod)) {
			transportFee = 50000;
		}
		OrderModel newOrder = new OrderModel();
		newOrder.setOrderDate(new Date());
		newOrder.setAddress(address);
		newOrder.setCity(city);
		newOrder.setStatus(0);
		newOrder.setTransportFee(transportFee);
		newOrder.setDiscount(discount);
		newOrder.setTotalMoney(totalCost);
		newOrder.setNote(note);
		newOrder.setDeliveryTime(null);
		newOrder.setCustomerConfirmation(0);
		newOrder.setCustomerID(user.getUserID());

		OrderModel createdOrder = orderService.insertOrder(newOrder);

		if (voucherIdString != null) {
			int voucherID = Integer.parseInt(voucherIdString);
			voucherCustomerService.insertVoucherCustomer(voucherID, totalCost);
		}
		// *INFO: CREATE PAYMENT
		PaymentModel payment = new PaymentModel();
		payment.setMethod(payMethod);
		payment.setOrderID(createdOrder.getOrderID());

		// *INFO: SET DEFAULT STATUS = 0 (NOT CHECKOUT). UPDATE LATER
		payment.setStatus(0);
		payment.setTime(null);
		if (payMethod == 1) {
			String numCard = req.getParameter("AccountNumber");
			String cardOwner = req.getParameter("CardOwner");
			String bank = req.getParameter("Bank");
			payment.setAccountNumber(numCard);
			payment.setCardOwner(cardOwner);
			payment.setBank(bank);
			payment.setTime(new Timestamp(new Date().getTime()));
		}
		paymentService.insertPayment(payment);
		req.setAttribute("payment", payment);
		resp.sendRedirect(req.getContextPath() + "/detailOrder?orderID=" + createdOrder.getOrderID());
		return;
	}

	private void redirectCheckoutPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rd = req.getRequestDispatcher(this.webLink);
		rd.forward(req, resp);
	}

	private UserModel getCurrentUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		if (session == null || session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return null;
		}

		return (UserModel) session.getAttribute("user");
	}
}
