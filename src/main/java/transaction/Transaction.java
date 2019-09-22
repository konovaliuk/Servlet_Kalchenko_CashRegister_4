package transaction;

import java.sql.Connection;

/**
 * интерфейс для работы с транзакцией
 * @author SergeyK
 */
@FunctionalInterface
public interface Transaction {
	void execute(Connection connection);
}
