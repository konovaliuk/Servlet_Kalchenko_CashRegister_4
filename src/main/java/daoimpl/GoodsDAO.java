package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.DAOManager;
import dao.IGoodsDAO;
import entity.Goods;

public class GoodsDAO implements IGoodsDAO<Goods> {

	public GoodsDAO() {
	}
	
	@Override
	public Long insert(Goods goods) {
		if (goods != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO goods "
						+ "(code, name, quant, measure, comments) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, goods.getCode());
				statement.setString(2, goods.getName());
				statement.setDouble(3, goods.getQuant());
				statement.setString(4, goods.getMeasure());
				statement.setString(5, goods.getComments());
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
    public List<Goods> findAll(String where) {
        return null;
    }
	
	@Override
	public void update(Goods goods) {
	}

	@Override
	public void delete(Goods goods) {
		if (goods != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM goods WHERE id = ?")) {
				statement.setLong(1, goods.getId());
				statement.executeUpdate();
				System.out.println("Delete result: Goods id " + goods.getId());
			} catch (SQLException e) {
				System.out.println("GoodsDAO.delete() error");
			}
		}
	}

	@Override
	public Goods findGoods(int code) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM goods WHERE code = ?")) {
			statement.setInt(1, code);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				Goods goods = new Goods();
				goods.setId(resultSet.getLong("id"));
				goods.setCode(resultSet.getInt("code"));
				goods.setName(resultSet.getString("name"));
				goods.setQuant(resultSet.getInt("quant"));
				goods.setMeasure(resultSet.getString("measure"));
				goods.setComments(resultSet.getString("comments"));
				return goods;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Goods findGoods(String name) {
		if (name != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM goods WHERE lower(name) = ?")) {
				statement.setString(1, name.toLowerCase());
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.first()) {
					Goods goods = new Goods();
					goods.setId(resultSet.getLong("id"));
					goods.setCode(resultSet.getInt("code"));
					goods.setName(resultSet.getString("name"));
					goods.setQuant(resultSet.getInt("quant"));
					goods.setMeasure(resultSet.getString("measure"));
					goods.setComments(resultSet.getString("comments"));
					return goods;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
