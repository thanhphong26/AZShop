package com.azshop.controller.admin;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.util.List;
import com.azshop.models.ItemImageModel;
import com.azshop.models.ItemModel;
import com.azshop.models.UserModel;
import com.azshop.service.IItemImageService;
import com.azshop.service.IItemService;
import com.azshop.service.impl.ItemImageServiceImpl;
import com.azshop.service.impl.ItemServiceImpl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import Orther.UploadImage;

@MultipartConfig
@WebServlet(urlPatterns = { "/adminItem", "/admininsertItem", "/admindeleteItem", "/adminupdateItem", "/adminviewItem",
		"/updateimage" })
public class ItemController extends HttpServlet {

	IItemService item = new ItemServiceImpl();
	IItemImageService itemImage = new ItemImageServiceImpl();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI().toString();
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			UserModel user = (UserModel) session.getAttribute("user");
			if (user.getType() == 3) {
				if (url.contains("adminItem")) {
					List(req, resp);
				} else if (url.contains("admininsertItem")) {
					insert(req, resp);
				} else if (url.contains("admindeleteItem")) {
					delete(req, resp);
				} else if (url.contains("adminupdateItem")) {
					update(req, resp);
				} else if (url.contains("adminviewItem")) {
					view(req, resp);
				} else if (url.contains("updateimage")) {
					deleteimage(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private void deleteimage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int Id = Integer.parseInt(req.getParameter("ItemID"));
		itemImage.deleteItemImage(Id);
		resp.sendRedirect("adminupdateItem?ItemID=" + Id);
	}

	private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int ID = Integer.parseInt(req.getParameter("ProductID"));
		req.setAttribute("listItem", item.findAllByProductID(ID));
		req.setAttribute("ProID", ID);
		req.getRequestDispatcher("/views/admin/item/ListItem.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if (url.contains("admininsertItem")) {
			postinsert(req, resp);
		} else if (url.contains("adminupdateItem")) {
			postupdate(req, resp);
		}
	}

	private void postupdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ItemModel itemModel = new ItemModel();

		itemModel.setItemID(Integer.parseInt(req.getParameter("itemID")));
		itemModel.setProductID(Integer.parseInt(req.getParameter("productID")));
		itemModel.setColor(req.getParameter("color"));
		itemModel.setColorCode(req.getParameter("colorCode"));
		itemModel.setSize(req.getParameter("size"));
		itemModel.setStock(Integer.parseInt(req.getParameter("stock")));
		itemModel.setOriginalPrice(Integer.parseInt(req.getParameter("originalPrice")));
		itemModel.setPromotionPrice(Integer.parseInt(req.getParameter("promotionPrice")));
		item.updateItem(itemModel);

		Image(req, resp, itemModel);
	}

	private void postinsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ItemModel itemModel = new ItemModel();
		int Proid = Integer.parseInt(req.getParameter("productID"));
		itemModel.setProductID(Proid);
		itemModel.setItemID(item.CreateItemID(Proid));
		itemModel.setColor(req.getParameter("color"));
		itemModel.setColorCode(req.getParameter("colorCode"));
		itemModel.setSize(req.getParameter("size"));
		itemModel.setStock(Integer.parseInt(req.getParameter("stock")));
		itemModel.setOriginalPrice(Integer.parseInt(req.getParameter("originalPrice")));
		itemModel.setPromotionPrice(Integer.parseInt(req.getParameter("promotionPrice")));
		item.insertItem(itemModel); // Thêm item

		Image(req, resp, itemModel);

	}

	private void Image(HttpServletRequest req, HttpServletResponse resp, ItemModel itemModel)
			throws ServletException, IOException {
		List<Part> parts = (List<Part>) req.getParts();
		int ItemID = itemModel.getItemID();
		int i = 0;
		for (Part part : parts) {
			String type = part.getContentType();

			if (type != null) {
				Random rnd = new Random();
				String rdCode = String.valueOf(rnd.nextInt(100, 999));
				ItemImageModel itemImageModel = new ItemImageModel();
				itemImageModel.setItemID(ItemID);
				int ImageID = itemImage.CreateItemimageID(ItemID);
				itemImageModel.setItemimageID(ImageID);
				UploadImage.uploadImage("mysql-web", "web-budget", "Image/Items/" + ImageID + "_" + rdCode + ".jpg",
						part.getInputStream());
				String image = "https://storage.googleapis.com/web-budget/Image/Items/" + ImageID + "_" + rdCode
						+ ".jpg";
				itemImageModel.setImage(image);
				itemImage.insertItemImage(itemImageModel);
			}
		}
		resp.sendRedirect("adminviewItem?ProductID=" + itemModel.getProductID());
	}

	private void List(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("listItem", item.findAll());
		req.getRequestDispatcher("/views/admin/item/ListItem.jsp").forward(req, resp);
	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("ProID", req.getParameter("ProID"));
		req.getRequestDispatcher("/views/admin/item/insertItem.jsp").forward(req, resp);
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int itemID = Integer.parseInt(req.getParameter("ItemID"));
		int Proid = Integer.parseInt(req.getParameter("ProductID"));
		item.deleteItem(itemID);
		itemImage.deleteItemImage(itemID);
		resp.sendRedirect("adminviewItem?ProductID=" + Proid);
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int itemID = Integer.parseInt(req.getParameter("ItemID"));
		ItemModel model = item.findOne(itemID);
		List<ItemImageModel> images = itemImage.findByProductID(itemID);
		req.setAttribute("item", model);
		req.setAttribute("images", images);
		req.getRequestDispatcher("/views/admin/item/updateItem.jsp").forward(req, resp);
	}
}
