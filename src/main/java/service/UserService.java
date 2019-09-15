package service;

import dao.DAOFactory;
import dao.IUserDAO;
import dao.IUserTypeDAO;
import entity.User;
import entity.UserType;

/**
 * @author SergeyK
 */
public class UserService {
	
	public static User login(String userName, String password) {
		
		User user = new User();
		user.setLogin(userName);
		user.setPassword(password);
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		return userDAO.findUser(user);
	}
	
	public static User registration(String userName, String email, String password) {
		
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		IUserTypeDAO<UserType> userTypeDao = DAOFactory.getUserTypeDAO();
		User user = new User();
		user.setName(userName);
		user.setLogin(email);
		user.setPassword(password);
		user.setIdUserType(userTypeDao.findUserType("cashier"));	//по-умолчанию при регистрации права кассира
		userDAO.insert(user);
		user.setPassword(null);
		return user;		
	}
}
