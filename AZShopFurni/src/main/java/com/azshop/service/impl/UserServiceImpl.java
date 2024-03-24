package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IUserDAO;
import com.azshop.dao.impl.UserDAOImpl;
import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;
import com.azshop.service.IUserService;

public class UserServiceImpl implements IUserService {
	IUserDAO userDAO = new UserDAOImpl();	
	
	@Override
	public UserModel getInfoUser(int userID) {
		return userDAO.getInfoUser(userID);
	}

	@Override
	public void updateUser(UserModel userMd) {
		 userDAO.updateUser(userMd);
	}
	
	@Override
	public AccountModel getInfAccount(int userID) {
		return userDAO.getInfAccount(userID);
	}

	@Override
	public void updateAccount(AccountModel accountMd) {
		userDAO.updateAccount(accountMd);
	}
	
	@Override
	public boolean checkPassword(String oldPassword, String newPassword) {
		if(oldPassword.equals(newPassword)) {
			return true;
		}
		else return false;
	}
	@Override
	public void updateAvatar(int userID, String avatar) {
		userDAO.updateAvatar(userID, avatar);
	}

	
}
