package service;

import dao.DAOFactory;
import dao.IUserDAO;
import dao.IUserTypeDAO;
import entity.User;
import entity.UserType;

/**
 * Класс сервиса для пользователей 
 * @author SergeyK
 */
public class UserService {
	
	/**
	 * Найти пользователя по логину и паролю
	 * @param login логин(email) пользователя
	 * @param password пароль пользователя
	 * @return user пользователь
	 */
	public static User findUser(String login, String password) {
		
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		return userDAO.findUser(login, password);
	}
	
	/**
	 * Зарегистрировать нового пользователя (по-умолчанию с правами кассира)
	 * @param userName имя пользователя
	 * @param login логин(email) пользователя
	 * @param password пароль пользователя
	 * @return user пользователь  
	 */
	public static User registration(String userName, String login, String password) {
		
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		IUserTypeDAO<UserType> userTypeDao = DAOFactory.getUserTypeDAO();
		if (userDAO.findUserByLogin(login) != null) {
			return null;
		}
		User user = new User();
		user.setName(userName);
		user.setLogin(login);
		user.setPassword(password);
		user.setIdUserType(userTypeDao.findUserType("cashier"));	//по-умолчанию при регистрации права кассира
		userDAO.insert(user);
		user.setPassword(null);
		return user;		
	}
}
