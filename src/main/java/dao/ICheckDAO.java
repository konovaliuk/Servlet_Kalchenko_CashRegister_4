package dao;

import java.sql.Connection;

public interface ICheckDAO<Check> extends IDAO<Check> {

	/**
	 * Найти запись по Id
	 * @param id записи
	 * @return объект по найденной записи
	 */
	Check findCheck(Long id);

	
	/**
	 * Добавить запись в таблицу чеки
	 * @param connection для транзакций
	 * @param check добавляемая запись
	 * @return Id добавленной записи
	 */
	Long insert(Connection connection, Check check);
}
