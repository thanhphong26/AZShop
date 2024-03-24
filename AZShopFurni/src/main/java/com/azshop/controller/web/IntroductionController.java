package com.azshop.controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.azshop.service.ICartService;
import com.azshop.service.ICategoryService;
import com.azshop.service.IDetailService;
import com.azshop.service.impl.CartServiceImpl;
import com.azshop.service.impl.CategoryServiceImpl;
import com.azshop.service.impl.DetailServiceImpl;

@WebServlet(urlPatterns = "/introduction")
public class IntroductionController extends HttpServlet {

	IDetailService detailService = new DetailServiceImpl();
	ICartService cartService = new CartServiceImpl();
	ICategoryService categoryService = new CategoryServiceImpl();
	
	private static final long serialVersionUID = 4317368494648713183L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/introduction.jsp");
		rd.forward(req, resp);
	}
}
