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
	 * Добавить запись в таблицу чеки в транзакции
	 * @param connection connection для транзакций
	 * @param check добавляемая запись
	 * @return Id добавленной записи
	 */
	Long insert(Connection connection, Check check);

	/**
	 * Обновить запись в таблице в транзакции
	 * @param connection connection для транзакций
	 * @param field имя поля
	 * @param value новое значение
	 * @return количество обработанных строк
	 */
	int update(Connection connection, String field, Object value, String where);
}
