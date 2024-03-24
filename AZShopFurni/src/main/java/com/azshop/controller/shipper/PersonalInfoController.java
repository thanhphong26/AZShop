package com.azshop.controller.shipper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.azshop.dao.impl.ShipperDAOImpl;
import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;
import com.azshop.service.IAccountService;
import com.azshop.service.IUserService;
import com.azshop.service.impl.AccountServiceImpl;
import com.azshop.service.impl.UserServiceImpl;

import Orther.UploadImage;
import other.Assignment;

@WebServlet(urlPatterns = { "/shipper-info", "/shipper-update-info", "/shipper-update-avatar", "/shipper-update-pass" })
@MultipartConfig
public class PersonalInfoController extends HttpServlet {
	IUserService userService = new UserServiceImpl();
	IAccountService accountService = new AccountServiceImpl();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 2) {
				updatesesstion(req, resp);
				String url = req.getRequestURI().toString();
				if (url.contains("shipper-info")) {
					showInfoPage(req, resp);
				} else if (url.contains("shipper-update-info")) {
					showUpdateInfoPage(req, resp);
				} else if (url.contains("shipper-update-avatar")) {
					updateInfAccount(req, resp);
				} else if (url.contains("shipper-update-pass")) {
					updatePassword(req, resp);
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
		String url = req.getRequestURI().toString();
		if (url.contains("shipper-update-info")) {
			createUserModel(req, resp);
		} else if (url.contains("shipper-update-avatar")) {
			updateAvatar(req, resp);
		}else if (url.contains("shipper-update-pass")) {
			createAccountModel(req, resp);
		}
		resp.sendRedirect("shipper-info");
	}

	private void showInfoPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		UserModel shipper = userService.getInfoUser(user.getUserID());

		req.setAttribute("user", shipper);
		RequestDispatcher rd = req.getRequestDispatcher("/views/shipper/infoShipper.jsp");
		rd.forward(req, resp);
	}

	private void showUpdateInfoPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		UserModel shipper = userService.getInfoUser(user.getUserID());
		

		List<String> listAssign = new ArrayList<String>(Assignment.getAssign().keySet());
		req.setAttribute("listAssign", listAssign);
		req.setAttribute("shipper", shipper);
		RequestDispatcher rd = req.getRequestDispatcher("/views/shipper/updateInfoShipper.jsp");
		rd.forward(req, resp);
	}

	private void updateInfAccount(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("userID"));
		AccountModel account = userService.getInfAccount(userID);
		req.setAttribute("accountModel", account);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/updateAccount.jsp");
		rd.forward(req, resp);
	}

	private void createUserModel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int userID = Integer.parseInt(req.getParameter("userID"));
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String address = req.getParameter("address");
		int gender = Integer.parseInt(req.getParameter("gender"));
		String phone = req.getParameter("phone");
		String dobString = req.getParameter("dob");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = null;
		try {
			dob = sdf.parse(dobString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String cid = req.getParameter("cid");
		// int type = Integer.parseInt(req.getParameter("type"));
		String email = req.getParameter("email");
		// int kpi = Integer.parseInt(req.getParameter("KPI"));
		String area = req.getParameter("area");
		// String avatar = req.getParameter("avatar");

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
		user.setArea(area);
		user.setEmail(email);

		new ShipperDAOImpl().updateShipper(user);
	}

	private void createAccountModel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("UserID"));
		String userName = req.getParameter("UserName");
		String oldPassword = req.getParameter("OldPassWord");
		String password = req.getParameter("Password");

		if (accountService.checkPassword(userID, oldPassword)) {
			AccountModel newaccount = new AccountModel(userID, userName, password);
			userService.updateAccount(newaccount);
			resp.sendRedirect("shipper-info");
		} else {
			String error = "Mật khẩu cũ không đúng. Vui lòng nhập lại";
			req.setAttribute("message", error);
			req.getRequestDispatcher("/views/shipper/updatePass.jsp").forward(req, resp);
		}
	}

	private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		Part filepart = req.getPart("image");
		Random rnd = new Random();
		String rdCode = String.valueOf(rnd.nextInt(100, 999));
		UploadImage.uploadImage("mysql-web", "web-budget", "Image/Avatar/" + user.getUserID() + rdCode + ".jpg",
				filepart.getInputStream());
		String avatar = "https://storage.googleapis.com/web-budget/Image/Avatar/" + user.getUserID() + rdCode + ".jpg";
		userService.updateAvatar(user.getUserID(), avatar);

	}

	private void updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		AccountModel account = userService.getInfAccount(user.getUserID());
		req.setAttribute("accountModel", account);
		RequestDispatcher rd = req.getRequestDispatcher("/views/shipper/updatePass.jsp");
		rd.forward(req, resp);
	}

	private void updatesesstion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		UserModel shipper = userService.getInfoUser(user.getUserID());
		HttpSession session1 = req.getSession();
		session1.setAttribute("user", shipper);

	}
}
