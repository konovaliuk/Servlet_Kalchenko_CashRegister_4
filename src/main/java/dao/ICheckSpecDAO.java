package dao;

import java.sql.Connection;
import java.util.List;

import entity.Checkspec;

public interface ICheckSpecDAO<CheckSpec> extends IDAO<CheckSpec> {

	/**
	 * Найти запись по Id
	 * @param id записи
	 * @return объект по найденной записи
	 */
	CheckSpec findCheckSpec(Long id);

	/**
	 * Добавить запись в таблицу чеки
	 * @param specifications добавляемая запись
	 * @return количество добавленных записей
	 */
	int insertAll(List<Checkspec> specifications);

	/**
	 * Добавить запись в таблицу чеки
	 * @param connection для транзакций
	 * @param specifications добавляемая запись
	 * @return количество добавленных записей
	 */
	int insertAll(Connection connection, List<Checkspec> specifications);
}