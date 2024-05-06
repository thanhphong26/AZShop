package com.azshop.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.owasp.encoder.Encode;

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
import com.azshop.utils.MessageUtil;

import Orther.UploadImage;

@WebServlet(urlPatterns = { "/infoUser", "/updateUser", "/updateAccount", "/updateAvatar" })
@MultipartConfig
public class PersonalInformationController extends HttpServlet {
	IUserService userService = new UserServiceImpl();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			String url = req.getRequestURI().toString();
			if (url.contains("infoUser")) {
				getInfUser(req, resp);
			} else if (url.contains("updateUser")) {
				updateInfUser(req, resp);
			} else if (url.contains("updateAccount")) {
				updateInfAccount(req, resp);
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
	    String requestedUrl = Encode.forHtml(req.getRequestURL().toString());
		//String url = req.getRequestURI().toString();
		if (requestedUrl.contains("updateUser")) {
			createUserModel(req, resp);
			resp.sendRedirect("infoUser");
		} else if (requestedUrl.contains("updateAccount")) {
			createAccountModel(req, resp);
			updateInfAccount(req,resp);
		} else if (requestedUrl.contains("updateAvatar")) {
			updateAvatar(req, resp);
			resp.sendRedirect("infoUser");
		}
	}

	private void getInfUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		req.setAttribute("userModel", user);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/user/infoUser.jsp");
		rd.forward(req, resp);
	}

	private void updateInfUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("userID"));
		UserModel user = userService.getInfoUser(userID);

		req.setAttribute("userModel", user);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/user/updateUser.jsp");
		rd.forward(req, resp);
	}

	private void updateInfAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel user = (UserModel) session.getAttribute("user");
		AccountModel account = userService.getInfAccount(user.getUserID());
		
		req.setAttribute("accountModel", account);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/user/updateAccount.jsp");
		rd.forward(req, resp);
	}

	private void createUserModel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("UserID"));
		String firstName = Encode.forHtml(req.getParameter("FirstName"));
        String lastName = Encode.forHtml(req.getParameter("LastName"));
        String address = Encode.forHtml(req.getParameter("Address"));
        int gender = Integer.parseInt(req.getParameter("Gender"));
        String phone = Encode.forHtml(req.getParameter("Phone"));
        String dobString = Encode.forHtml(req.getParameter("Dob"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = null;
		try {
			dob = sdf.parse(dobString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*
		 * String cid = req.getParameter("Cid"); int type =
		 * Integer.parseInt(req.getParameter("Type")); String email =
		 * req.getParameter("Email"); int kpi =
		 * Integer.parseInt(req.getParameter("KPI")); String area =
		 * req.getParameter("Area"); String avatar = req.getParameter("image");
		 */
		String cid = Encode.forHtml(req.getParameter("Cid"));
        int type = Integer.parseInt(req.getParameter("Type"));
        String email = Encode.forHtml(req.getParameter("Email"));
        int kpi = Integer.parseInt(req.getParameter("KPI"));
        String area = Encode.forHtml(req.getParameter("Area"));
        String avatar = Encode.forHtml(req.getParameter("image"));

		UserModel user = new UserModel();
		user.setUserID(userID);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setGender(gender);
		user.setPhone(phone);
		user.setDob(dob);
		user.setCid(cid);
		user.setType(type);
		user.setAvatar(avatar);
		user.setKpi(kpi);
		user.setArea(area);
		user.setEmail(email);
		if (!isValidInput(user)) {
            // Handle invalid input
            resp.sendRedirect("infoUser");
            return;
        }
		userService.updateUser(user);
		HttpSession session = req.getSession(true);
		session.setAttribute("user",userService.getInfoUser(userID));
		getInfUser(req, resp);
	}

	private void createAccountModel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("UserID"));
		String userName = req.getParameter("UserName");
		String oldPassword = req.getParameter("OldPassWord");
		String password = req.getParameter("Password");
		AccountModel account = userService.getInfAccount(userID);

		if (userService.checkPassword(oldPassword, account.getPassword())) {
			AccountModel newaccount = new AccountModel(userID, userName, password);
			userService.updateAccount(newaccount);
			MessageUtil.showMessage(req,"updateAccountTrue");
		} else {
			MessageUtil.showMessage(req,"updateAccountFail");
		}
	}

	private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userID = Integer.parseInt(req.getParameter("UserID"));
		Part filepart = req.getPart("image");
		Random rnd = new Random();
		String rdCode = String.valueOf(rnd.nextInt(100, 999));
		UploadImage.uploadImage("mysql-web", "web-budget", "Image/Avatar/" + userID + rdCode + ".jpg", filepart.getInputStream());
		String avatar = "https://storage.googleapis.com/web-budget/Image/Avatar/" + userID + rdCode + ".jpg";
		userService.updateAvatar(userID, avatar);
		HttpSession session = req.getSession(true);
		session.setAttribute("user",userService.getInfoUser(userID));
		getInfUser(req, resp);
	}
	private boolean isValidInput(UserModel user) {
	    // Validate user input here
	    // Return true if input is valid, false otherwise

	    // Example validation: Check if the first name and last name are not empty
	    String firstName = user.getFirstName().trim();
	    String lastName = user.getLastName().trim();
	    if (firstName.isEmpty() || lastName.isEmpty()) {
	        return false;
	    }

	    // Validate email format
	    String email = user.getEmail().trim();
	    if (!email.isEmpty()) {
	        // Use a regular expression to validate the email format
	        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	        if (!email.matches(emailRegex)) {
	            return false;
	        }
	    }

	    // Add more validation logic here based on your requirements

	    return true; // Input is valid
	}
}
