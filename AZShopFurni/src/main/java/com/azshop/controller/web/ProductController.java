package com.azshop.controller.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.CsrfGuardException;
import org.owasp.csrfguard.CsrfGuardFilter;
import org.owasp.encoder.Encode;

import com.azshop.dao.ICustomerDAO;
import com.azshop.models.CartModel;
import com.azshop.models.CategoryModel;
import com.azshop.models.DetailModel;
import com.azshop.models.ProductModel;
import com.azshop.models.SearchHistoryModel;
import com.azshop.models.SupplierModel;
import com.azshop.models.UserModel;
import com.azshop.models.submodels.RatingModel;
import com.azshop.service.ICartService;
import com.azshop.service.ICategoryService;
import com.azshop.service.ICustomerService;
import com.azshop.service.IDetailService;
import com.azshop.service.IProductService;
import com.azshop.service.IRatingService;
import com.azshop.service.ISearchHistoryService;
import com.azshop.service.ISupplierService;
import com.azshop.service.impl.CartServiceImpl;
import com.azshop.service.impl.CategoryServiceImpl;
import com.azshop.service.impl.CustomerServiceImpl;
import com.azshop.service.impl.DetailServiceImpl;
import com.azshop.service.impl.ProductServiceImpl;
import com.azshop.service.impl.RatingServiceImpl;
import com.azshop.service.impl.SearchHistoryServiceImpl;
import com.azshop.service.impl.SupplierServiceImpl;
import com.azshop.utils.CsrfTokenManager;

@WebServlet(urlPatterns = { "/products", "/search" })
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH=100;
	IProductService productService = new ProductServiceImpl();
	ICustomerService customerService = new CustomerServiceImpl();
	ICategoryService categoryService = new CategoryServiceImpl();

	ISearchHistoryService searchHistoryService = new SearchHistoryServiceImpl();
	ICartService cartService = new CartServiceImpl();
	IDetailService detailService = new DetailServiceImpl();
	IRatingService ratingService = new RatingServiceImpl();
	RequestDispatcher rd = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Frame-Options", "SAMEORIGIN");
		resp.setContentType("text/htm");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		showHistorySearch(req, resp);
		String url = req.getRequestURI().toString();
	    //resp.setHeader("Content-Security-Policy", "default-src 'none'; script-src 'self'; img-src 'self' https://storage.googleapis.com; style-src 'self'; base-uri 'self'; form-action 'self'");
		
		if (url.contains("products")) {
			
			if (session.getAttribute("user") != null) {
				UserModel user = (UserModel) session.getAttribute("user");
				List<CartModel> listCart = cartService.findByCustomerId(user.getUserID());

				int subTotal = 0;
				for (CartModel cart : listCart) {
					subTotal += cart.getTotalPrice();
				}

				getServletContext().setAttribute("carts", listCart);
				getServletContext().setAttribute("subTotal", subTotal);
			}
			String idString = req.getParameter("id");
			if (idString != null) {
				 if (!isValidIdString(idString)) {
					 RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
						rDispatcher. forward(req, resp);
						return;
                 }
				int id = Integer.parseInt(req.getParameter("id"));
				ProductModel productModel = productService.findOne(id);

				List<ProductModel> cateProList = productService.findByCategoryID(productModel.getCategoryID());
				List<ProductModel> supProList = productService.findBySupplierID(productModel.getSupplierID());
				List<DetailModel> detailList = detailService.findDetailByProductID(productModel.getProductID());
				List<RatingModel> ratingList = ratingService.findRatinglByProductID(productModel.getProductID());

				req.setAttribute("ratingList", ratingList);
				req.setAttribute("detailList", detailList);
				req.setAttribute("cateProList", cateProList);
				req.setAttribute("supProList", supProList);
				req.setAttribute("product", productModel);

				rd = req.getRequestDispatcher("/views/web/products/productdetail.jsp");
			} else {
				
				String cateIdString = req.getParameter("cateId");
				String pageString = req.getParameter("page");
				int page = pageString != null ? Integer.parseInt(pageString) : 1;
				int pageSize = 12;
				List<ProductModel> listProduct = new ArrayList<ProductModel>();
				List<CategoryModel> listRootCategory = categoryService.getRootCategories();

				if (cateIdString != null) {
					 if (!isValidIdString(cateIdString)) {
						 RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
							rDispatcher. forward(req, resp);
							return;
	                 }
					 if (!isValidIdString(pageString)) {
						 RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
							rDispatcher. forward(req, resp);
							return;
	                 }
					int cateId = Integer.parseInt(cateIdString);
					List<CategoryModel> listCateChild = categoryService.geChidlCategories(cateId);
					CategoryModel categoryModel = categoryService.findOne(cateId);
					CategoryModel rootCategory = categoryService.findRootCategoryByCategoryId(cateId);
					listProduct = productService.findByCategoryID(cateId);

					req.setAttribute("childCategories", listCateChild);
					req.setAttribute("category", categoryModel);
					req.setAttribute("rootcategory", rootCategory);
				} else {
					listProduct = productService.findAll();
				}

				req.setAttribute("cateId", cateIdString);
				String filterPrice = req.getParameter("price");
				String filterRating = req.getParameter("rating");
				String sortProduct = req.getParameter("sort");
				listProduct = filterAndSortProduct(listProduct, filterPrice, filterRating, sortProduct);

				int totalPage = listProduct.size() > 0 ? listProduct.size() / pageSize : 0;
				int start = (page - 1) * pageSize;
				int end = Math.min(start + pageSize, listProduct.size());

				req.setAttribute("price", filterPrice);
				req.setAttribute("sort", sortProduct);
				req.setAttribute("rating", filterRating);

				req.setAttribute("products", listProduct.subList(start, end));
				req.setAttribute("page", page);
				req.setAttribute("totalPage", totalPage);
				req.setAttribute("rootCategories", listRootCategory);

				rd = req.getRequestDispatcher("/views/web/products/products.jsp");
			}

			rd.forward(req, resp);
		} else if (url.contains("/search")) {
			if(!doAction(req, resp)) {
				RequestDispatcher rDispatcher = req.getRequestDispatcher("/views/web/404.jsp");
				rDispatcher. forward(req, resp);
				return;
			}
			List<ProductModel> listProduct = new ArrayList<ProductModel>();
			String keyword = req.getParameter("keyword");
			if (keyword != null) {
				if (session != null && session.getAttribute("user") != null) {
					UserModel user = (UserModel) session.getAttribute("user");
					SearchHistoryModel historySearch = new SearchHistoryModel();
					historySearch.setCustomerID(user.getUserID());
					historySearch.setCreatedAt(new Timestamp(new Date().getTime()));
					historySearch.setHistory(keyword);
					searchHistoryService.insertSearchHistory(historySearch);
				}
				listProduct = productService.searchProductByName(keyword);

			}
			String filterPrice = req.getParameter("price");
			String filterRating = req.getParameter("rating");
			String sortProduct = req.getParameter("sort");
			listProduct = filterAndSortProduct(listProduct, filterPrice, filterRating, sortProduct);

            req.setAttribute("keyword", Encode.forHtmlAttribute(keyword));
			req.setAttribute("price", filterPrice);
			req.setAttribute("sort", sortProduct);
			req.setAttribute("rating", filterRating);
			req.setAttribute("products", listProduct);
			req.getRequestDispatcher("/views/web/products/products.jsp").forward(req, resp);
		}
	}

	private void showHistorySearch(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			List<SearchHistoryModel> searchHistory = searchHistoryService.getHistorySearchByCID(user.getUserID());
			req.setAttribute("history", searchHistory);
		}
	}

	private List<ProductModel> filterAndSortProduct(List<ProductModel> listProduct, String filterPrice, String rating,
			String sortProduct) {
		if (filterPrice != null && !filterPrice.isEmpty()) {
			listProduct = filterByPrice(listProduct, filterPrice);
		}
		if (rating != null && !rating.isEmpty()) {
			listProduct = filterByRating(listProduct, rating);
		}
		if (sortProduct != null && !sortProduct.isEmpty()) {
			listProduct = sortProduct(listProduct, sortProduct);
		}
		return listProduct;
	}

	private List<ProductModel> filterByPrice(List<ProductModel> listProduct, String priceRange) {
		String[] priceRangeValue = priceRange.split("-");
		int minPrice = Integer.parseInt(priceRangeValue[0]);
		int maxPrice = Integer.parseInt(priceRangeValue[1]);
		return listProduct.stream().filter(ProductModel -> ProductModel.getDisplayedOriginalPrice() >= minPrice
				&& ProductModel.getDisplayedOriginalPrice() <= maxPrice).collect(Collectors.toList());

	}

	private List<ProductModel> filterByRating(List<ProductModel> listProduct, String rating) {
		Double rate = Double.parseDouble(rating);
		return listProduct.stream().filter(ProductModel -> ProductModel.getAvgRating() >= rate)
				.collect(Collectors.toList());
	}

	private List<ProductModel> sortProduct(List<ProductModel> listProduct, String sortProduct) {
		if (sortProduct.equals("asc")) {
			Collections.sort(listProduct, Comparator.comparing(ProductModel::getDisplayedOriginalPrice));
		} else {
			Collections.sort(listProduct, Comparator.comparing(ProductModel::getDisplayedOriginalPrice).reversed());
		}
		return listProduct;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	public boolean doAction(HttpServletRequest request, HttpServletResponse response) {
		// get the CSRF cookie
		String csrfCookie = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("csrf")) {
				csrfCookie = cookie.getValue();
			}
		}

		String csrfField = request.getParameter("csrfToken");

		// validate CSRF
		if (csrfCookie == null || csrfField == null || !csrfCookie.equals(csrfField)) {
			return false;
		}
		return true;
	}
	private boolean isValidIdString(String idString) {
	    if (idString == null || idString.isEmpty()) {
	        return false;
	    }
	    
	    String sanitizedIdString = Encode.forXml(idString);
	    
	    return sanitizedIdString.equals(idString) && idString.length() <= MAX_LENGTH;
	}
}
