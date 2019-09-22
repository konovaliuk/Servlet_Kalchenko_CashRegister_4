package daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DAOManager;
import dao.ICheckDAO;
import entity.Check;

public class CheckDAO implements ICheckDAO<Check> {
	
	private static CheckDAO instance;
	
	private CheckDAO() {
	}
	
	public static ICheckDAO<Check> getInstance() {
		if (instance == null) {
			instance = new CheckDAO();
		}
		return instance;		
	}

	@Override
	public Long insert(Check check) {
		return insert(null, check);
	}
	
	@Override
	public Long insert(Connection connection, Check check) {
		if (check != null) {
			Connection conn = (connection == null ? DAOManager.getConnection() : connection);
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
				if (conn != null && connection == null) {
			        try {
			            conn.close();
			        } catch (SQLException e) { e.printStackTrace();}
			    }
			}
		}
		return null;
	}

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
				check.setRegistration(resultSet.getInt("registration"));
				checks.add(check);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checks;
    }
	
	@Override
	public void update(Check check) {
		if (check != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE cashreg.check SET creator = ?, total = ?, discount = ?, canceled = ?, registration = ? WHERE id = ?")) {
				statement.setLong(1, check.getCreator());
				statement.setDouble(2, check.getTotal());
				statement.setDouble(3, check.getDiscount());
				statement.setInt(4, check.getCanceled());
				statement.setObject(5, check.getRegistration());
				statement.setLong(6, check.getId());
				statement.executeUpdate();
				System.out.println("Update result: Check id " + check.getId());
			} catch (SQLException e) {
				System.out.println("CheckDAO.update() error" + e.getMessage());
			}
		}
	}

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
				check.setRegistration((Integer)resultSet.getObject("registration"));
				return check;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		return null;
	}

	@Override
	public int update(Connection connection, String field, Object value, String where) {
		int rows = 0;
		Connection conn = (connection == null ? DAOManager.getConnection() : connection);
		try(PreparedStatement statement = conn.prepareStatement(
					"UPDATE cashreg.check SET " + field + " = ? " + (where != null ? " WHERE " + where : ""))) {
			statement.setObject(1, value);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("CheckDAO.update() error" + e.getMessage());
		} finally {
			if (conn != null && connection == null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { e.printStackTrace();}
		    }
		}
		return rows;	
	}
}
