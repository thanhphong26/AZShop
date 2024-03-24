package com.azshop.controller.seller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;
import com.azshop.service.IAccountService;
import com.azshop.service.IUserService;
import com.azshop.service.impl.AccountServiceImpl;
import com.azshop.service.impl.UserServiceImpl;

import Orther.UploadImage;

@WebServlet(urlPatterns = { "/sellerInfor", "/sellerUpdateAvatar", "/sellerUpdateInfor", "/sellerUpdatePass" })
@MultipartConfig
public class ProfileController extends HttpServlet {
	IUserService userService = new UserServiceImpl();
	IAccountService accountService = new AccountServiceImpl();
	private static final long serialVersionUID = -2864702128082425580L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 1) {
				int userID = user.getUserID();
				String url = req.getRequestURI();
				if (url.contains("sellerInfor")) {
					getInforSeller(req, resp, user);
				} else if (url.contains("sellerUpdateInfor")) {
					updateInfor(req, resp);
				} else if (url.contains("sellerUpdatePass")) {
					updatePassword(req, resp, userID);
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
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		int userID = user.getUserID();
		String url = req.getRequestURI().toString();
		if (url.contains("sellerUpdateAvatar")) {
			updateAvatar(req, resp, userID);
			resp.sendRedirect("sellerInfor");
		} else if (url.contains("sellerUpdateInfor")) {
			createUserModel(req, resp);
			resp.sendRedirect("sellerInfor");
		} else if (url.contains("sellerUpdatePass")) {
			createAccountModel(req, resp);
		}

	}

	private void getInforSeller(HttpServletRequest req, HttpServletResponse resp, UserModel user)
			throws ServletException, IOException {
		req.setAttribute("userModel", user);
		req.getRequestDispatcher("/views/seller/profile/profile.jsp").forward(req, resp);
	}

	private void updateAvatar(HttpServletRequest req, HttpServletResponse resp, int userID)
			throws ServletException, IOException {
		Part filepart = req.getPart("image");
		Random rnd = new Random();
		String rdCode = String.valueOf(rnd.nextInt(100, 999));
		UploadImage.uploadImage("mysql-web", "web-budget", "Image/Avatar/" + userID + rdCode + ".jpg",
				filepart.getInputStream());
		String avatar = "https://storage.googleapis.com/web-budget/Image/Avatar/" + userID + rdCode + ".jpg";

		userService.updateAvatar(userID, avatar);
		UserModel user = userService.getInfoUser(userID);
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		getInforSeller(req, resp, user);
	}

	private void updatePassword(HttpServletRequest req, HttpServletResponse resp, int userID)
			throws ServletException, IOException {
		AccountModel account = userService.getInfAccount(userID);
		req.setAttribute("accountModel", account);
		RequestDispatcher rd = req.getRequestDispatcher("/views/seller/profile/updatePass.jsp");
		rd.forward(req, resp);
	}

	private void updateInfor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		int userID = userModel.getUserID();
		UserModel user = userService.getInfoUser(userID);
		req.setAttribute("userModel", user);
		RequestDispatcher rd = req.getRequestDispatcher("/views/seller/profile/updateProfile.jsp");
		rd.forward(req, resp);
	}

	private void createUserModel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		int userID = Integer.parseInt(req.getParameter("UserID"));
		String firstName = req.getParameter("FirstName");
		String lastName = req.getParameter("LastName");
		String address = req.getParameter("Address");
		int gender = Integer.parseInt(req.getParameter("Gender"));
		String phone = req.getParameter("Phone");
		String dobString = req.getParameter("Dob");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = null;
		try {
			dob = sdf.parse(dobString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String cid = req.getParameter("Cid");
		// int type = Integer.parseInt(req.getParameter("Type"));
		String email = req.getParameter("Email");
		// int kpi = Integer.parseInt(req.getParameter("KPI"));
		// String area = req.getParameter("Area");
		// String avatar = req.getParameter("image");

		UserModel user = userService.getInfoUser(userID);
		user.setUserID(userID);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setGender(gender);
		user.setPhone(phone);
		user.setDob(dob);
		user.setCid(cid);
		// user.setType(type);
		// user.setAvatar(avatar);
		// user.setKpi(kpi);
		// user.setArea(area);
		user.setEmail(email);

		userService.updateUser(user);
		user = userService.getInfoUser(userID);
		session.setAttribute("user", user);
	}

	private void createAccountModel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		int userID = userModel.getUserID();
		String userName = req.getParameter("UserName");
		String oldPassword = req.getParameter("OldPassWord");
		String password = req.getParameter("Password");

		if (accountService.checkPassword(userID, oldPassword)) {
			AccountModel newaccount = new AccountModel(userID, userName, password);
			userService.updateAccount(newaccount);
			resp.sendRedirect("sellerInfor");
		} else {
			String error = "Mật khẩu cũ không đúng. Vui lòng nhập lại";
			req.setAttribute("message", error);
			req.getRequestDispatcher("/views/seller/profile/updatePass.jsp").forward(req, resp);
		}
	}
}
