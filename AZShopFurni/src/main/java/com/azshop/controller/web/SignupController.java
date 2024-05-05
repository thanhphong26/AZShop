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
	private static final int MAX_LENGTH=100;
	PasswordUtils pwutils=new PasswordUtils();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Frame-Options", "SAMEORIGIN");
		String url = req.getRequestURI().toString();
		if (url.contains("signup"))
			showPageSignup(req, resp);
		else if (url.contains("verification"))
			showVerificationPage(req, resp);
		else if (url.contains("resend")) {
			sendVerificationEmail(req);
			showVerificationPage(req, resp);}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Frame-Options", "SAMEORIGIN");
		String url = req.getRequestURI().toString();
		if (url.contains("signup"))
			checkInfoSignup(req, resp);
		else if (url.contains("verification"))
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
		System.out.println(isSafeInput(req.getParameter("dob")));
		System.out.println(isSafeInput(req.getParameter("email")));
		System.out.println(isSafeInput(req.getParameter("firstname")));
		System.out.println(isSafeInput(req.getParameter("lastname")));
		System.out.println(isSafeInput(req.getParameter("address")));
		System.out.println(isSafeInput(req.getParameter("gender")));
		System.out.println(isSafeInput(req.getParameter("phone")));
		System.out.println(isSafeInput(req.getParameter("area")));
		System.out.println(isSafeInput(req.getParameter("usernamesignup")));
		System.out.println(isSafeInput(req.getParameter("passsignup")));
		System.out.println(isSafeInput(req.getParameter("passsignup")));
		
		
		try {
			if(!isSafeInput(req.getParameter("firstname")) || 
					!isSafeInput(req.getParameter("lastname")) || 
					!isSafeInput(req.getParameter("address")) || 
					!isSafeInput(req.getParameter("gender")) || 
					!isSafeInput(req.getParameter("phone")) || 
					!isSafeInput(req.getParameter("dob")) ||
					!isSafeInput(req.getParameter("area")) || 
					!isSafeInput(req.getParameter("email")) ||
					!isSafeInput(req.getParameter("usernamesignup")) || 
					!isSafeInput(req.getParameter("passsignup")) ||
					!isSafeInput(req.getParameter("passcheck"))) {
				RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
				rDispatcher. forward(req, resp);
				return;
			}
			cusService.checkValidInfoCustomer(req.getParameter("firstname"), req.getParameter("lastname"),
					req.getParameter("address"), req.getParameter("gender"), req.getParameter("phone"),
					req.getParameter("dob"), req.getParameter("area"), req.getParameter("email"),
					req.getParameter("usernamesignup"), req.getParameter("passsignup"), req.getParameter("passcheck"));
			req.removeAttribute("exception");

			UserModel user = new UserModel();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			user.setUserID(cusService.createCustomerID());
			user.setFirstName(req.getParameter("firstname"));
			user.setLastName(req.getParameter("lastname"));
			user.setEmail(req.getParameter("email"));
			user.setPhone(req.getParameter("phone"));
			user.setArea(req.getParameter("area"));
			user.setAddress(req.getParameter("address"));
			user.setGender(Integer.parseInt(req.getParameter("gender")));
			user.setDob(formatter.parse(req.getParameter("dob")));

			AccountModel acc = new AccountModel();
			acc.setUserID(user.getUserID());
			acc.setUserName(req.getParameter("usernamesignup"));
			String password=pwutils.hashPassword(req.getParameter("passsignup"));
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
	
	private boolean containsXsltDangerousCharacters(String input) {
	    // Kiểm tra input có chứa các ký tự đặc biệt nguy hiểm trong ngữ cảnh XSLT
	    // Đây chỉ là một ví dụ đơn giản, bạn cần cân nhắc sử dụng các phương pháp phức tạp hơn
	    String[] xsltDangerousCharacters = { "<", ">", "&", "'", "\"" };

	    for (String character : xsltDangerousCharacters) {
	        if (input.contains(character)) {
	            return true;
	        }
	    }

	    return false;
	}

	private boolean isSafeInput(String input) {
	    if (input == null || input.isEmpty()) {
	        return false;
	    }

	    if (input.length() > MAX_LENGTH) {
	        return false;
	    }

	    // Kiểm tra ký tự nguy hiểm trong ngữ cảnh DOM-based XSS
	    String sanitizedInput = Encode.forHtml(input); // Sử dụng Encode.forHtml() hoặc Encode.forAttribute()

	    if (!sanitizedInput.equals(input)) {
	        return false;
	    }

	    // Kiểm tra ký tự nguy hiểm trong ngữ cảnh XSLT
	    if (containsXsltDangerousCharacters(input)) {
	        return false;
	    }

	    return true;
	}
}
