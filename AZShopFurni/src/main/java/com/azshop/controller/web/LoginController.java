package com.azshop.controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.encoder.Encode;

import com.azshop.models.UserModel;
import com.azshop.service.IAccountService;
import com.azshop.service.ICustomerService;
import com.azshop.service.impl.AccountServiceImpl;
import com.azshop.service.impl.CustomerServiceImpl;


@WebServlet(urlPatterns = { "/login", "/waiting", "/logout" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH=100;
	IAccountService accService = new AccountServiceImpl();
	ICustomerService cusService = new CustomerServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Frame-Options", "SAMEORIGIN");
		String requestedUrl = req.getRequestURI().toString();
		if (requestedUrl.contains("login"))
			showPageLogin(req, resp);
		else if (requestedUrl.contains("waiting"))
			waiting(req, resp);
		else if (requestedUrl.contains("logout"))
			logout(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Frame-Options", "SAMEORIGIN");
	    String requestedUrl = req.getRequestURI().toString();
		if (requestedUrl.contains("login"))
			checkLogin(req, resp);
	}

	private void showPageLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userID")) {
					UserModel user = cusService.getOneCustomer(Integer.parseInt(cookie.getValue()));
					session = req.getSession(true);
					session.setAttribute("user",user);
					resp.sendRedirect(req.getContextPath() + "/waiting");
					return;
				}
			}
		}
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
		rd.forward(req, resp);
	}

	private void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if(!isSafeInput(username) || !isSafeInput(password)) return;

		UserModel user = accService.login(username, password);
		if (user == null) {
			req.setAttribute("mess", "Tài khoản hoặc mật khẩu chưa đúng");
			req.setAttribute("username", username);
			showPageLogin(req, resp);
		} else {
			HttpSession session = req.getSession(true);
			session.setAttribute("user", user);
			if ("on".equals(req.getParameter("remember"))) {
				saveRemeberMe(resp, user.getUserID());
			}
			resp.sendRedirect(req.getContextPath() + "/waiting");
		}
	}
	
	private void waiting(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			String url = null;
			if (user.getType() == 0)
				url = "/home";
			else if (user.getType() == 1)
				url = "/sellerInfor";
			else if (user.getType() == 2)
				url = "/shipper-list-shipping";
			else if (user.getType() == 3)
				url = "/adminAccount";
			resp.sendRedirect(req.getContextPath() + url);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userID")) {
					cookie.setMaxAge(0); 
					resp.addCookie(cookie);
					break;
				}
			}
		}
		resp.sendRedirect(req.getContextPath() + "/login");
	}
	
	private void saveRemeberMe(HttpServletResponse resp, int userID) {
		Cookie cookie = new Cookie("userID",String.valueOf(userID));
		cookie.setMaxAge(30 * 62);
		resp.addCookie(cookie);

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

