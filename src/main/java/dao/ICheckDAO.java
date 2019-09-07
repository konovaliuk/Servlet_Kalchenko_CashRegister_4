package dao;

import java.sql.Connection;

public interface ICheckDAO<Check> extends IDAO<Check> {

	/**
	 * @param id
	 * @return
	 */
	Check findCheck(Long id);
	
	/**
	 * @param connection
	 */
	void setConnection(Connection connection);
}
