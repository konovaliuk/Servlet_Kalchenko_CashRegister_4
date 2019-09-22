package dao;

import java.sql.Connection;

public interface IFiscalDAO<Fiscal> extends IDAO<Fiscal> {

	/**
	 * Добавить запись в таблицу в транзакции
	 * @param connection для транзакций
	 * @param fiscal добавляемая запись
	 * @return Id добавленной записи
	 */
	Long insert(Connection connection, Fiscal fiscal);
}
