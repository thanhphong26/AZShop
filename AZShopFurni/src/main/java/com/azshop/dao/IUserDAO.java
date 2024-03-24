package com.azshop.dao;


import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;

public interface IUserDAO {
	UserModel getInfoUser(int userID);
	void updateUser(UserModel user);
	void updateAvatar(int userID, String avatar);
	AccountModel getInfAccount(int userID);
	void updateAccount(AccountModel account);
}

