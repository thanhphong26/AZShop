package com.azshop.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.azshop.dao.IAccountDAO;
import com.azshop.dao.ICustomerDAO;
import com.azshop.dao.impl.AccountDAOImpl;
import com.azshop.dao.impl.CustomerDAOImpl;
import com.azshop.models.UserModel;
import com.azshop.service.ICustomerService;

import other.City;

public class CustomerServiceImpl implements ICustomerService {
	ICustomerDAO customerDao = new CustomerDAOImpl();
	IAccountDAO accDAO = new AccountDAOImpl();

	@Override
	public List<UserModel> getAllCustomer() {
		return customerDao.getAllCustomer();
	}

	@Override
	public UserModel getOneCustomer(int id) {
		return customerDao.getOneCustomer(id);
	}

	@Override
	public boolean insertCustomer(UserModel customerMd) {
		createAvatar(customerMd);
		return customerDao.insertCustomer(customerMd);
	}

	@Override
	public boolean updateCustomer(UserModel customerMd) {
		return customerDao.updateCustomer(customerMd);
	}

	@Override
	public boolean deleteCustomer(UserModel customerMd) {
		return customerDao.deleteCustomer(customerMd);
	}

	@Override
	public int createCustomerID() {
		List<UserModel> listCustomer = customerDao.getAllCustomer();
		int id = listCustomer.get(listCustomer.size() - 1).getUserID();
		return id + 1;
	}

	public void createAvatar(UserModel user) {
		if (user.getAvatar() == null || user.getAvatar() == "") {
			user.setAvatar("https://storage.googleapis.com/web-budget/Image/Avatar/first.png");
		}
	}

	@Override
	public void checkValidInfoCustomer(String firstName, String lastName, String address, String gender, String phone,
			String dob, String area, String email, String username, String pass, String passcheck) {
		if (username == null || username.equals(""))
			throw new IllegalArgumentException("Tên đăng nhập không hợp lệ");
		if (pass == null || pass.equals(""))
			throw new IllegalArgumentException("Mật khẩu không hợp lệ");
		if (firstName == null || firstName.equals(""))
			throw new IllegalArgumentException("Họ không hợp lệ");
		if (lastName == null || lastName.equals(""))
			throw new IllegalArgumentException("Tên không hợp lệ");
		if (!gender.equals("1") && !gender.equals("0"))
			throw new IllegalArgumentException("Giới tính không hợp lệ");
		if (address == null || address.equals(""))
			throw new IllegalArgumentException("Địa chỉ không hợp lệ");
		if (area == null || !City.getListCity().contains(area))
			throw new IllegalArgumentException("Thành phố không hợp lệ");
		if (phone == null || phone.length() != 10 || !phone.chars().allMatch(Character::isDigit))
			throw new IllegalArgumentException("Số điện thoại không hợp lệ");
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.parse(dob);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Ngày sinh không hợp lệ");
		}
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new IllegalArgumentException("Email không hợp lệ");
		}
		if (accDAO.findByUsername(username).getUserID() != 0)
			throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
		if (accDAO.findByEmail(email).getUserID() != 0)
			throw new IllegalArgumentException("Email đã tồn tại");
		if (!pass.equals(passcheck))
			throw new IllegalArgumentException("Mật khẩu không trùng khớp");
	}

	public void checkValidEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new IllegalArgumentException("Email không hợp lệ");
		}
		if (accDAO.findByEmail(email).getUserID() == 0)
			throw new IllegalArgumentException("Email không tồn tại");
	}
}
