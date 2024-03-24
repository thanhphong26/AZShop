package com.azshop.service;

import java.util.List;

import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;

public interface IAccountService {
	List<AccountModel> getAllAccount();
	AccountModel getOneAccount(int id); 
	boolean insertAccount(AccountModel account); 
	boolean updateAccount(AccountModel account); 
	boolean deleteAccount(AccountModel account);  
	int getTypeAccount(String username,String password);
	UserModel findUserByUsername(String username);
	UserModel login(String username,String password);
	AccountModel findByEmail(String email);
	boolean checkPassword(int userID, String password);
}
