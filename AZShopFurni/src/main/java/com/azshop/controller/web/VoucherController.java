package com.azshop.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.UserModel;
import com.azshop.models.VoucherModel;
import com.azshop.service.IVoucherService;
import com.azshop.service.impl.VoucherServiceImpl;
import com.azshop.utils.MessageUtil;

@WebServlet(urlPatterns = { "/listVoucher", "/searchVoucher" })
@MultipartConfig
public class VoucherController extends HttpServlet {

	IVoucherService voucherService = new VoucherServiceImpl();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			String url = req.getRequestURI().toString();
			if (url.contains("listVoucher")) {
				listVoucher(req, resp);
			} else if (url.contains("searchVoucher")) {
				searchVoucher(req, resp);
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void listVoucher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		List<VoucherModel> listVoucher = voucherService.findVoucherByCustomerID(user.getUserID());
		
		req.setAttribute("listVoucher", listVoucher);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/voucher/listVoucher.jsp");
		rd.forward(req, resp);

	}

	private void searchVoucher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		List<VoucherModel> listVoucher = new ArrayList<VoucherModel>();

		if (!voucherService.containsNonDigit(keyword)) {
			int voucherID = Integer.parseInt(keyword);
			VoucherModel voucher = voucherService.findOneByCustomerID(voucherID, user.getUserID());
			VoucherModel voucherEx = voucherService.findOne(voucherID);

			if (voucher.getVoucherID() == 0 && voucherEx.getVoucherID() == voucherID) {
				MessageUtil.showMessage(req, "searchVoucherNull");
				listVoucher(req, resp);
			} else if (voucher.getVoucherID() == 0) {
				MessageUtil.showMessage(req, "searchVoucherFail");
				listVoucher(req, resp);
			} else {
				listVoucher.add(voucher);
				req.setAttribute("listVoucher", listVoucher);
				RequestDispatcher rd = req.getRequestDispatcher("/views/web/voucher/listVoucher.jsp");
				rd.forward(req, resp);
			}
		} else {
			MessageUtil.showMessage(req, "searchVoucherFail");
			listVoucher(req, resp);
		}
	}
}
