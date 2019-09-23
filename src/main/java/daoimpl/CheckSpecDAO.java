package daoimpl;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import dao.DAOManager;
import dao.ICheckSpecDAO;
import entity.Checkspec;

public class CheckSpecDAO implements ICheckSpecDAO<Checkspec> {

	private static CheckSpecDAO instance;
	private static Logger logger = Logger.getLogger(CheckDAO.class);
	
	private CheckSpecDAO() {
	}
	
	public static ICheckSpecDAO<Checkspec> getInstance() {
		if (instance == null) {
			instance = new CheckSpecDAO();
		}
		return instance;		
	}
	
	@Override
	public Long insert(Checkspec checkspec) {
		if (checkspec != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO checkspec "
						+ "(id_check, id_good, quant, price, total, nds, ndstotal, canceled) VALUES (?, ?, ?, ?)")) {
				statement.setLong(1, checkspec.getIdCheck());
				statement.setLong(2, checkspec.getIdGood());
				statement.setDouble(3, checkspec.getQuant());
				statement.setDouble(4, checkspec.getPrice());
				statement.setDouble(5, checkspec.getTotal());
				statement.setDouble(6, checkspec.getNds());
				statement.setDouble(7, checkspec.getNdstotal());				
				statement.setInt(8, checkspec.getCanceled());
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

	@Override
	public int insertAll(List<Checkspec> specifications) {
		return insertAll(null, specifications);
	}
	
	public int insertAll(Connection connection, List<Checkspec> specifications) {
		if (specifications != null && specifications.size() > 0) {
			Connection conn = (connection == null ? DAOManager.getConnection() : connection);
			try (PreparedStatement statement = conn.prepareStatement("INSERT INTO checkspec "
						+ "(id_check, id_good, quant, price, total, nds, ndstotal, canceled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
				for (Checkspec checkspec : specifications) {
					statement.setLong(1, checkspec.getIdCheck());
					statement.setLong(2, checkspec.getIdGood());
					statement.setDouble(3, checkspec.getQuant());
					statement.setDouble(4, checkspec.getPrice());
					statement.setDouble(5, checkspec.getTotal());
					statement.setDouble(6, checkspec.getNds());
					statement.setDouble(7, checkspec.getNdstotal());				
					statement.setInt(8, checkspec.getCanceled());
					statement.addBatch();
				}
				int [] count = statement.executeBatch();
				return count.length;
			} catch (SQLException e) {
				logger.error(e);
			} finally {
				if (conn != null && connection == null) {
			        try {
			            conn.close();
			        } catch (SQLException e) { logger.error(e);}
			    }
			}
		}
		return -1;
	}	
	
    public List<Checkspec> findAll(String where) {
    	List<Checkspec> checkspecs = new ArrayList<>();
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM vcheckspec" + (where != null ? " WHERE " + where : "") + " ORDER BY id")) {
			ResultSet resultSet = statement.executeQuery();			
			while (resultSet.next()) {
				Checkspec checkspec = new Checkspec();
				checkspec.setId(resultSet.getLong("id"));
				checkspec.setIdCheck(resultSet.getLong("id_check"));
				checkspec.setIdGood(resultSet.getLong("id_good"));
				checkspec.setQuant(resultSet.getDouble("quant"));
				checkspec.setPrice(resultSet.getDouble("price"));
				checkspec.setTotal(resultSet.getDouble("total"));
				checkspec.setNds(resultSet.getInt("nds"));
				checkspec.setNdstotal(resultSet.getDouble("ndstotal"));
				checkspec.setCanceled(resultSet.getInt("canceled"));
				checkspec.setXcode(resultSet.getInt("xcode"));
				checkspec.setXname(resultSet.getString("xname"));
				checkspecs.add(checkspec);				
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return checkspecs;
    }
	
	@Override
	public void update(Checkspec checkspec) {
		if (checkspec != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE checkspec SET id_check = ?, id_good = ?, quant = ?, price = ?, total = ?, nds = ?, ndstotal = ?, canceled = ? "
						+ "WHERE id = ?")) {
				statement.setLong(1, checkspec.getIdCheck());
				statement.setLong(2, checkspec.getIdGood());
				statement.setDouble(3, checkspec.getQuant());
				statement.setDouble(4, checkspec.getPrice());
				statement.setDouble(5, checkspec.getTotal());
				statement.setInt(6, checkspec.getNds());
				statement.setDouble(7, checkspec.getNdstotal());
				statement.setInt(8, checkspec.getCanceled());
				statement.setLong(9, checkspec.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error("Checkspec update() error", e);
			}
		}
	}

	@Override
	public void delete(Checkspec checkspec) {
		if (checkspec != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM checkspec WHERE id = ?")) {
				statement.setLong(1, checkspec.getId());
				statement.executeUpdate();
				System.out.println("Delete result: Check id " + checkspec.getId());
			} catch (SQLException e) {
				System.out.println("CheckspecDAO.delete() error");
			}
		}
	}

	@Override
	public Checkspec findCheckSpec(Long id) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM checkspec WHERE id = ?")) {
			statement.setLong(1, id);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				Checkspec checkspec = new Checkspec();
				checkspec.setId(resultSet.getLong("id"));
				checkspec.setIdCheck(resultSet.getLong("id_check"));
				checkspec.setIdGood(resultSet.getLong("id_good"));
				checkspec.setQuant(resultSet.getInt("quant"));
				checkspec.setPrice(resultSet.getInt("price"));
				checkspec.setTotal(resultSet.getInt("total"));
				checkspec.setNds(resultSet.getInt("nds"));
				checkspec.setNdstotal(resultSet.getInt("ndstotal"));
				checkspec.setCanceled(resultSet.getInt("canceled"));
				return checkspec;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
}
