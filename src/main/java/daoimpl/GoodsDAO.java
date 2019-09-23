package daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOManager;
import dao.IGoodsDAO;
import entity.Goods;

public class GoodsDAO implements IGoodsDAO<Goods> {

	private static GoodsDAO instance;
	private static Logger logger = Logger.getLogger(GoodsDAO.class);
	
	private GoodsDAO() {
	}
	
	public static IGoodsDAO<Goods> getInstance() {
		if (instance == null) {
			instance = new GoodsDAO();
		}
		return instance;		
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
				logger.error(e);
			}
		}
		return null;
	}
	
	public List<Goods> findAll(Integer page, Integer recordsPerPage) {
		return findAll(null, page, recordsPerPage);
	}
	
	public List<Goods> findAll(String where) {
		return findAll(where, null, null);
	}
	
    private List<Goods> findAll(String where, Integer page, Integer recordsPerPage) {
    	List<Goods> goods = new ArrayList<>();
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM goods" + (where != null ? " WHERE " + where : "") 
					+ " ORDER BY code"
					+ (recordsPerPage != null ? " LIMIT " + recordsPerPage : "")
					+ (page != null && page > 1 ? " OFFSET " + (page-1) * recordsPerPage : ""))) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Goods product = new Goods();
				product.setId(resultSet.getLong("id"));
				product.setCode(resultSet.getInt("code"));
				product.setName(resultSet.getString("name"));
				product.setQuant(resultSet.getDouble("quant"));
				product.setMeasure(resultSet.getString("measure"));
				product.setComments(resultSet.getString("comments"));
				goods.add(product);				
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return goods;
    }
	
    @Override
    public long count() {
    	long count = 0;
		try (Connection connection = DAOManager.getConnection();
			Statement statement = connection.createStatement()) {			
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM cashreg.goods");
			if (resultSet.first()) {
				count = resultSet.getInt("count");
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return count;
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
			} catch (SQLException e) {
				logger.error(e);
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
			logger.error(e);
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
				logger.error(e);
			}
		}
		return null;
	}
}
