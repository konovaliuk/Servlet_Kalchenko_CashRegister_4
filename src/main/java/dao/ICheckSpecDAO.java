package dao;

import java.sql.Connection;
import java.util.List;

import entity.Checkspec;

public interface ICheckSpecDAO<CheckSpec> extends IDAO<CheckSpec> {

	/**
	 * @param id
	 * @return
	 */
	CheckSpec findCheckSpec(Long id);

	/**
	 * @param checkspecs
	 */
	int insertAll(List<Checkspec> specifications);

	/**
	 * @param connection
	 */
	void setConnection(Connection connection);
}