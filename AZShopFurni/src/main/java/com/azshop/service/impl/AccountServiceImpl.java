package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IAccountDAO;
import com.azshop.dao.ICustomerDAO;
import com.azshop.dao.IUserDAO;
import com.azshop.dao.impl.AccountDAOImpl;
import com.azshop.dao.impl.CustomerDAOImpl;
import com.azshop.dao.impl.UserDAOImpl;
import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;
import com.azshop.service.IAccountService;
import com.azshop.utils.PasswordUtils;

public class AccountServiceImpl implements IAccountService {

	IAccountDAO accountDao = new AccountDAOImpl();
	ICustomerDAO cusDAO = new CustomerDAOImpl();
	PasswordUtils pwutils=new PasswordUtils();
	@Override
	public List<AccountModel> getAllAccount() {
		return accountDao.getAllAccount();
	}

	@Override
	public AccountModel getOneAccount(int id) {
		return accountDao.getOneAccount(id);
	}
	@Override
	public boolean insertAccount(AccountModel account) {
		return accountDao.insertAccount(account);
	}

	@Override
	public boolean updateAccount(AccountModel account) {
		return accountDao.updateAccount(account);
		
	}

	@Override
	public boolean deleteAccount(AccountModel account) {
		return accountDao.deleteAccount(account);
		
	}

	@Override
	public int getTypeAccount(String username,String password) {
		AccountModel acc = accountDao.findByUsernameAndPass(username, password);
		if(acc != null)
			return accountDao.getTypeAccount(acc.getUserID());
		else
			return -1;
	}
	
	@Override
	public UserModel findUserByUsername(String username) {
		AccountModel acc =  accountDao.findByUsername(username);
		return cusDAO.getOneCustomer(acc.getUserID());
	}

	@Override
	public UserModel login(String username, String password) {
		AccountModel acc =  accountDao.findByUsername(username);
		
		if(acc.getUserID() == 0 )
			acc = accountDao.findByEmail(username);
		if(acc.getUserID() != 0 && pwutils.verifyPassword(password, acc.getPassword())) {
			return cusDAO.getOneCustomer(acc.getUserID());
		}
		return null;
	}
	
	@Override
	public AccountModel findByEmail(String email) {
		return accountDao.findByEmail(email);
	}

	@Override
	public boolean checkPassword(int userID, String password) {
		AccountModel account=accountDao.getOneAccount(userID);
		if(account!=null && account.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	
}
