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
import com.azshop.service.IShipperService;
import com.azshop.service.impl.ShipperServiceImpl;
import com.azshop.utils.MessageUtil;

@WebServlet(urlPatterns = { "/adminShipper", "/adminUpdateShipper", "/adminDeleteShipper", "/adminInsertShipper",
		"/adminInformationShipper" })
public class ShipperController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IShipperService shipperService = new ShipperServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 3) {
				if (url.contains("adminShipper")) {
					findAllShipper(req, resp);
				} else if (url.contains("adminUpdateShipper")) {
					getInforShipper(req, resp);
				} else if (url.contains("adminDeleteShipper")) {
					deleteShipper(req, resp);
					RequestDispatcher rd = req.getRequestDispatcher("adminShipper");
					rd.forward(req, resp);
				} else if (url.contains("adminInsertShipper")) {
					RequestDispatcher rd = req.getRequestDispatcher("/views/admin/shipper/addShipper.jsp");
					rd.forward(req, resp);
				} else if (url.contains("adminInformationShipper")) {
					int userID = Integer.parseInt(req.getParameter("userID"));
					UserModel shipper = shipperService.findOne(userID);
					req.setAttribute("user", shipper);
					RequestDispatcher rd = req.getRequestDispatcher("/views/admin/shipper/informationShipper.jsp");
					rd.forward(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void deleteShipper(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("userID"));
			shipperService.deleteShipper(id);
			MessageUtil.showMessage(req, "delSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "delFail");
		}
	}

	private void getInforShipper(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		int id = Integer.parseInt(req.getParameter("userID"));

		UserModel model = shipperService.findOne(id);

		req.setAttribute("shipper", model);
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/shipper/updateShipper.jsp");
		rd.forward(req, resp);

	}

	private void findAllShipper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserModel> listShipper = shipperService.findAllShipper();
		req.setAttribute("listshipper", listShipper);

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/shipper/listShipper.jsp");
		rd.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String url = req.getRequestURI().toString();

		if (url.contains("adminUpdateShipper")) {
			updateShipper(req, resp);
		} else if (url.contains("adminInsertShipper")) {
			insertShipper(req, resp);
		}
	}

	private void insertShipper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			// thiet lap ngon ngu
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");

			// nhan du lieu tu form
			int id = shipperService.createShipperID();
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String address = req.getParameter("address");
			int gender = Integer.parseInt(req.getParameter("gender"));
			String phone = req.getParameter("phone");
			String avatar = req.getParameter("avatar");
			String cid = req.getParameter("cid");
			String area = req.getParameter("area");
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
			newUser.setArea(area);
			newUser.setEmail(email);
			// goi pt insert trong service
			shipperService.insertShipper(newUser);
			MessageUtil.showMessage(req, "addSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "addFail");
		}
		findAllShipper(req, resp);
	}

	private void updateShipper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
			String area = req.getParameter("area");
			String email = req.getParameter("email");
			String dobString = req.getParameter("dob");
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
			newUser.setArea(area);
			newUser.setEmail(email);

			shipperService.updateShipper(newUser);
			MessageUtil.showMessage(req, "updateSuccess");
		} catch (Exception ex) {
			MessageUtil.showMessage(req, "updateFail");
		}
		resp.sendRedirect("adminInformationShipper?userID=" + id);

	}
}
