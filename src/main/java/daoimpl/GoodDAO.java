package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.DAOManager;
import dao.IGoodDAO;
import entity.Good;

public class GoodDAO implements IGoodDAO<Good> {

	public GoodDAO() {
	}
	
	@Override
	public Long insert(Good good) {
		if (good != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO goods "
						+ "(code, name, quant, measure, comments) VALUES (?, ?, ?, ?, ?)")) {
				statement.setInt(1, good.getCode());
				statement.setString(2, good.getName());
				statement.setDouble(3, good.getQuant());
				statement.setString(4, good.getMeasure());
				statement.setString(5, good.getComments());
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				return rs.getLong(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
    public List<Good> findAll(String where) {
        return null;
    }
	
	@Override
	public void update(Good good) {
	}

	@Override
	public void delete(Good good) {
		if (good != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM goods WHERE id = ?")) {
				statement.setLong(1, good.getId());
				statement.executeUpdate();
				System.out.println("Delete result: Good id " + good.getId());
			} catch (SQLException e) {
				System.out.println("GoodDAO.delete() error");
			}
		}
	}

	@Override
	public Good findGood(int code) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM goods WHERE code = ?")) {
			statement.setInt(1, code);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				Good good = new Good();
				good.setId(resultSet.getLong("id"));
				good.setCode(resultSet.getInt("code"));
				good.setName(resultSet.getString("name"));
				good.setQuant(resultSet.getInt("quant"));
				good.setMeasure(resultSet.getString("measure"));
				good.setComments(resultSet.getString("comments"));
				return good;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Good findGood(String name) {
		if (name != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM goods WHERE lower(name) = ?")) {
				statement.setString(1, name.toLowerCase());
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.first()) {
					Good good = new Good();
					good.setId(resultSet.getLong("id"));
					good.setCode(resultSet.getInt("code"));
					good.setName(resultSet.getString("name"));
					good.setQuant(resultSet.getInt("quant"));
					good.setMeasure(resultSet.getString("measure"));
					good.setComments(resultSet.getString("comments"));
					return good;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
