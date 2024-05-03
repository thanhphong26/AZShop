package com.azshop.controller.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.encoder.Encode;

import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;
import com.azshop.service.IAccountService;
import com.azshop.service.ICustomerService;
import com.azshop.service.impl.AccountServiceImpl;
import com.azshop.service.impl.CustomerServiceImpl;
import com.azshop.utils.PasswordUtils;

import other.City;
import other.SendMail;



@WebServlet(urlPatterns = { "/signup", "/verification", "/resend"})
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IAccountService accService = new AccountServiceImpl();
	ICustomerService cusService = new CustomerServiceImpl();
	PasswordUtils pwutils=new PasswordUtils();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestedUrl = Encode.forUriComponent(req.getRequestURL().toString());

		String hash = req.getQueryString();
		if (hash != null && hash.startsWith("#")) {
			hash = hash.substring(1); // Loại bỏ ký tự '#'
			hash = Encode.forUriComponent(hash); // Mã hóa chuỗi hash
		}
		if (requestedUrl.contains("signup"))
			showPageSignup(req, resp);
		else if (requestedUrl.contains("verification"))
			showVerificationPage(req, resp);
		else if (requestedUrl.contains("resend")) {
			sendVerificationEmail(req);
			showVerificationPage(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestedUrl = Encode.forUriComponent(req.getRequestURL().toString());
		if (requestedUrl.contains("signup"))
			checkInfoSignup(req, resp);
		else if (requestedUrl.contains("verification"))
			insertCus(req, resp);
	}

	private void showPageSignup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> listcity = City.getListCity();
		req.setAttribute("listcity", listcity);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/signup.jsp");
		rd.forward(req, resp);
	}

	private void showVerificationPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("verification") != null) {
			RequestDispatcher rd = req.getRequestDispatcher("/views/web/verification.jsp");
			rd.forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void checkInfoSignup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			String firstName = Encode.forHtml(req.getParameter("firstname"));
            String lastName = Encode.forHtml(req.getParameter("lastname"));
            String address = Encode.forHtml(req.getParameter("address"));
            String gender = Encode.forHtml(req.getParameter("gender"));
            String phone = Encode.forHtml(req.getParameter("phone"));
            String dob = Encode.forHtml(req.getParameter("dob"));
            String area = Encode.forHtml(req.getParameter("area"));
            String email = Encode.forHtml(req.getParameter("email"));
            String userName = Encode.forHtml(req.getParameter("usernamesignup"));
            String passSignup = Encode.forHtml(req.getParameter("passsignup"));
            String passCheck = Encode.forHtml(req.getParameter("passcheck"));

            cusService.checkValidInfoCustomer(firstName, lastName, address, gender, phone, dob, area, email, userName, passSignup, passCheck);
            req.removeAttribute("exception");

            UserModel user = new UserModel();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            user.setUserID(cusService.createCustomerID());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setArea(area);
            user.setAddress(address);
            user.setGender(Integer.parseInt(gender));
            user.setDob(formatter.parse(dob));

			AccountModel acc = new AccountModel();
			acc.setUserID(user.getUserID());
	        acc.setUserName(Encode.forJavaScript(userName)); // Mã hóa dữ liệu người dùng trong biểu thức JavaScript
			String password=pwutils.hashPassword(passSignup);
			System.out.println(password);
			acc.setPassword(password);
			HttpSession session = req.getSession();
			session.setAttribute("newuser", user);
			session.setAttribute("newacc", acc);

			sendVerificationEmail(req);

			resp.sendRedirect("verification");

		} catch (IllegalArgumentException e) {
			req.setAttribute("exception", e.getMessage());
			req.setAttribute("usernamesignup", req.getParameter("usernamesignup"));
			req.setAttribute("firstname", req.getParameter("firstname"));
			req.setAttribute("lastname", req.getParameter("lastname"));
			req.setAttribute("email", req.getParameter("email"));
			req.setAttribute("phone", req.getParameter("phone"));
			req.setAttribute("area", req.getParameter("area"));
			req.setAttribute("address", req.getParameter("address"));
			req.setAttribute("gender", req.getParameter("gender"));
			req.setAttribute("dob", req.getParameter("dob"));

			showPageSignup(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void insertCus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String verification = (String) session.getAttribute("verification");
		String usercode = req.getParameter("usercode");
		if (verification.equals(usercode)) {
			UserModel user = (UserModel) session.getAttribute("newuser");
			AccountModel acc = (AccountModel) session.getAttribute("newacc");
			cusService.insertCustomer(user);
			accService.insertAccount(acc);
			session.invalidate();
			resp.sendRedirect("login");
		} else {
			req.setAttribute("mess", "Mã xác thực chưa đúng");
			showVerificationPage(req, resp);
		}
	}

	private void sendVerificationEmail(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UserModel user = (UserModel) session.getAttribute("newuser");
		Random rnd = new Random();
		String verification = String.valueOf(rnd.nextInt(100000, 999999));
		SendMail.sendMail(user.getEmail(), "Mã xác nhận: "+ verification);
		session.setAttribute("verification", verification);
	}
	
}
