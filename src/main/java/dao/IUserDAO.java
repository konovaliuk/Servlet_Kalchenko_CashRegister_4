package dao;

import entity.User;

public interface IUserDAO<T> extends IDAO<T> {

	/**
	 * Найти пользователя по логину и паролю
	 * @param login логин пользователя
	 * @param password пароль пользователя
	 * @return user пользователь  
	 */
	public User findUser(String login, String password);

	/**
	 * Найти пользователя по логину
	 * @param login логин пользователя
	 * @return user пользователь
	 */
	public User findUserByLogin(String login);
}
