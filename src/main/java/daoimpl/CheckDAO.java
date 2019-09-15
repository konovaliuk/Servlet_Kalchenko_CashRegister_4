package daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DAOManager;
import dao.ICheckDAO;
import entity.Check;

public class CheckDAO implements ICheckDAO<Check> {

	/**connection используется для транзакций в разных DAO*/
	private Connection connection;
	
	private static CheckDAO instance;
	
	private CheckDAO() {
	}
	
	public static ICheckDAO<Check> getInstance() {
		if (instance == null) {
			instance = new CheckDAO();
		}
		return instance;		
	}
	
	/**
	 * Добавить запись в таблицу чеки
	 * @param check добавляемая запись
	 */
	@Override
	public Long insert(Check check) {
		if (check != null) {
			Connection conn = (this.connection == null ? DAOManager.getConnection() : this.connection);
			try (PreparedStatement statement = conn.prepareStatement("INSERT INTO cashreg.check (creator, total, discount, canceled) "
					+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				statement.setLong(1, check.getCreator());
				statement.setDouble(2, check.getTotal());
				statement.setDouble(3, check.getDiscount());
				statement.setInt(4, check.getCanceled());
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				return rs.getLong(1);
			} catch (SQLException e) {
				e.printStackTrace();				
			} finally {
				if (conn != null && this.connection == null) {
			        try {
			            conn.close();
			        } catch (SQLException e) { e.printStackTrace();}
			    }
			}
		}
		return null;
	}

	/**
	 * Найти все записи из таблицы чеки
	 * @param where строка запроса where для поиска
	 */
	@Override
    public List<Check> findAll(String where) {
    	List<Check> checks = new ArrayList<>();
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM cashreg.check" + (where != null ? " WHERE " + where : "") + " ORDER BY id")) {
			ResultSet resultSet = statement.executeQuery();			
			while (resultSet.next()) {
				Check check = new Check();
				check.setId(resultSet.getLong("id"));
				check.setCrtime(resultSet.getDate("crtime"));
				check.setCreator(resultSet.getLong("creator"));
				check.setTotal(resultSet.getDouble("total"));
				check.setDiscount(resultSet.getDouble("discount"));
				check.setCanceled(resultSet.getInt("canceled"));
				checks.add(check);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checks;
    }
	
	/**
	 * Обновить запись в таблице чеки
	 * @param check обновляемая запись
	 */
	@Override
	public void update(Check check) {
		if (check != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE cashreg.check SET creator = ?, total = ?, discount = ?, canceled = ? WHERE id = ?")) {
				statement.setLong(1, check.getCreator());
				statement.setDouble(2, check.getTotal());
				statement.setDouble(3, check.getDiscount());
				statement.setInt(4, check.getCanceled());
				statement.setLong(5, check.getId());
				statement.executeUpdate();
				System.out.println("Update result: Check id " + check.getId());
			} catch (SQLException e) {
				System.out.println("CheckDAO.update() error" + e.getMessage());
			}
		}
	}

	/**
	 * Удалить запись в таблице чеки
	 * @param check удаляемая запись
	 */
	@Override
	public void delete(Check check) {
		if (check != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM check WHERE id = ?")) {
				statement.setLong(1, check.getId());
				statement.executeUpdate();
				System.out.println("Delete result: Check id " + check.getId());
			} catch (SQLException e) {
				System.out.println("CheckDAO.delete() error");
			}
		}
	}

	/**
	 * Найти запись из таблицы чеки по id
	 * @param id номер записи
	 */
	@Override
	public Check findCheck(Long id) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM cashreg.check WHERE id = ?")) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				Check check = new Check();
				check.setId(resultSet.getLong("id"));
				check.setCrtime(resultSet.getDate("crtime"));
				check.setCreator(resultSet.getLong("creator"));
				check.setTotal(resultSet.getDouble("total"));
				check.setDiscount(resultSet.getDouble("discount"));
				check.setCanceled(resultSet.getInt("canceled"));
				return check;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		return null;
	}
	
	/**
	 * Получить connection для транзакций
	 * @return connection возвращаемый connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Установить connection для транзакций
	 * @param connection устанавливаемый connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
