package dao;

import daoimpl.*;
import entity.*;

public class DAOFactory {

	// The list of supported databases.
	public enum Factories {
		MYSQL
	}
	
	public static IUserDAO<User> getUserDAO() {
		return UserDAO.getInstance();
	}
	
	public static IUserTypeDAO<UserType> getUserTypeDAO() {
		return UserTypeDAO.getInstance();
	}
	
	public static IGoodsDAO<Goods> getGoodsDAO() {
		return GoodsDAO.getInstance();
	}
	
	public static ICheckDAO<Check> getCheckDAO() {
		return CheckDAO.getInstance();
	}

	public static ICheckSpecDAO<Checkspec> getCheckSpecDAO() {
		return CheckSpecDAO.getInstance();
	}

	/*
	public static IDAO getDAO(Table table) {
		switch (table) {
		case USER:
			return new UserDAO();			
		case USER_TYPE:
			return new UserTypeDAO();
		case GOOD:
			return new GoodDAO();
		default:
			return null;
		}
	}*/
}
