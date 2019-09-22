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
	
	public static IFiscalDAO<Fiscal> getFiscalDAO() {
		return FiscalDAO.getInstance();
	}
}
