package dao;

import daoimpl.*;
import entity.*;

public class DAOFactory {

	// The list of supported databases.
	public enum Factories {
		MYSQL
	}
	
	public static IUserDAO<User> getUserDAO() {
		return new UserDAO();
	}
	
	public static IUserTypeDAO<UserType> getUserTypeDAO() {
		return new UserTypeDAO();
	}
	
	public static IGoodsDAO<Goods> getGoodsDAO() {
		return new GoodsDAO();
	}
	
	public static ICheckDAO<Check> getCheckDAO() {
		return new CheckDAO();
	}

	public static ICheckSpecDAO<Checkspec> getCheckSpecDAO() {
		return new CheckSpecDAO();
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
