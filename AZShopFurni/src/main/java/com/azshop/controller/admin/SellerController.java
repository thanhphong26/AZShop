package com.azshop.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azshop.models.UserModel;
import com.azshop.service.ISellerService;
import com.azshop.service.impl.SellerServiceImpl;
import com.azshop.utils.MessageUtil;

@WebServlet(urlPatterns = { "/adminSeller", "/adminUpdateSeller", "/adminDeleteSeller", "/adminInsertSeller",
		"/adminInformationSeller" })
public class SellerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ISellerService sellerService = new SellerServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String url = req.getRequestURI().toString();
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 3) {
				if (url.contains("adminSeller")) {
					findAllSeller(req, resp);
				} else if (url.contains("adminUpdateSeller")) {
					getInforSeller(req, resp);
				} else if (url.contains("adminDeleteSeller")) {
					deleteSeller(req, resp);
					RequestDispatcher rd = req.getRequestDispatcher("adminSeller");
					rd.forward(req, resp);
				} else if (url.contains("adminInsertSeller")) {
					RequestDispatcher rd = req.getRequestDispatcher("/views/admin/seller/addSeller.jsp");
					rd.forward(req, resp);
				} else if (url.contains("adminInformationSeller")) {
					int userID = Integer.parseInt(req.getParameter("userID"));
					UserModel seller = sellerService.findOne(userID);
					req.setAttribute("user", seller);
					RequestDispatcher rd = req.getRequestDispatcher("/views/admin/seller/informationSeller.jsp");
					rd.forward(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void deleteSeller(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("userID"));
			sellerService.deleteSeller(id);
			MessageUtil.showMessage(req, "delSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "delFail");
		}

	}

	private void getInforSeller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(req.getParameter("userID"));

		UserModel model = sellerService.findOne(id);

		req.setAttribute("seller", model);
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/seller/updateSeller.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String url = req.getRequestURI().toString();

		if (url.contains("adminUpdateSeller")) {
			updateSeller(req, resp);
		} else if (url.contains("adminInsertSeller")) {
			insertSeller(req, resp);
		}
	}

	private void insertSeller(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			// thiet lap ngon ngu
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			// nhan du lieu tu form
			int id = sellerService.createSellerID();
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String address = req.getParameter("address");
			int gender = Integer.parseInt(req.getParameter("gender"));
			String phone = req.getParameter("phone");
			String avatar = req.getParameter("avatar");
			String cid = req.getParameter("cid");
			int kpi = Integer.parseInt(req.getParameter("kpi"));
			String email = req.getParameter("email");
			String dobString = req.getParameter("dob");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng của ngày tháng
			Date dob = null;
			try {
				dob = sdf.parse(dobString); // Chuyển đổi kiểu chuỗi thành kiểu Date
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// dua du lieu vao model
			UserModel newUser = new UserModel();
			newUser.setUserID(id);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setGender(gender);
			newUser.setAvatar(avatar);
			newUser.setAddress(address);
			newUser.setPhone(phone);
			newUser.setDob(dob);
			newUser.setCid(cid);
			newUser.setKpi(kpi);
			newUser.setEmail(email);
			// goi pt insert trong service
			sellerService.insertSeller(newUser);
			MessageUtil.showMessage(req, "addSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "addFail");
		}
		findAllSeller(req, resp);
	}

	private void updateSeller(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("userID"));
		try {
			// thiet lap ngon ngu

			// nhan du lieu tu form
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String address = req.getParameter("address");
			int gender = Integer.parseInt(req.getParameter("gender"));
			String phone = req.getParameter("phone");
			String avatar = req.getParameter("avatar");
			String cid = req.getParameter("cid");
			int kpi = Integer.parseInt(req.getParameter("kpi"));
			String dobString = req.getParameter("dob");
			String email = req.getParameter("email");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng của ngày tháng
			Date dob = null;
			try {
				dob = sdf.parse(dobString); // Chuyển đổi kiểu chuỗi thành kiểu Date
			} catch (ParseException e) {
				e.printStackTrace();
			}

			UserModel newUser = new UserModel();
			newUser.setUserID(id);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setGender(gender);
			newUser.setAvatar(avatar);
			newUser.setAddress(address);
			newUser.setPhone(phone);
			newUser.setDob(dob);
			newUser.setCid(cid);
			newUser.setKpi(kpi);
			newUser.setEmail(email);

			sellerService.updateSeller(newUser);
			MessageUtil.showMessage(req, "updateSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "updateFail");
		}
		resp.sendRedirect("adminInformationSeller?userID=" + id);
	}

	private void findAllSeller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserModel> listseller = sellerService.findAllSeller();
		req.setAttribute("listseller", listseller);

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/seller/listSeller.jsp");
		rd.forward(req, resp);
	}
}