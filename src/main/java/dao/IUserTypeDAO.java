package dao;

public interface IUserTypeDAO<T> extends IDAO<T> {

	/**
	 * Найти тип пользователя(роль) по наименованию 
	 * @param type тип пользователя
	 * @return Id пользователя
	 */
	public Long findUserType(String type);
}
