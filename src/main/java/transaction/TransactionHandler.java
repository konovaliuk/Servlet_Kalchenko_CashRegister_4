package transaction;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dao.DAOManager;

/**
 * Класс для работы с транзакцией
 * @author SergeyK
 */
public class TransactionHandler {
	
	private static Logger logger = Logger.getLogger(TransactionHandler.class);
	
	/**
	 * Метод для выполнения блока кода в одной транзакции
	 * @param transaction Функциональный интерфейс
	 * @throws TransactionException
	 */
	public static void execute(Transaction transaction) throws TransactionException {
		
		Connection conn = DAOManager.getConnection();
		try {
			conn.setAutoCommit(false);
			transaction.execute(conn);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Ошибка транзакции. ", e);
			try {						
				conn.rollback();
			} catch (SQLException ex) {	
				logger.error("Ошибка транзакции. ", ex);
			}
			throw new TransactionException("Ошибка транзакции. ", e);
		} finally {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) { logger.error("Ошибка транзакции. ", e);		}
		}	
	}
}
