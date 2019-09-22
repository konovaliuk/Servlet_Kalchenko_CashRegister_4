package daoimpl;

import java.sql.*;
import java.util.List;

import dao.DAOManager;
import dao.IFiscalDAO;
import entity.Fiscal;

public class FiscalDAO implements IFiscalDAO<Fiscal> {
	
	private static FiscalDAO instance;
	
	private FiscalDAO() {
	}
	
	public static IFiscalDAO<Fiscal> getInstance() {
		if (instance == null) {
			instance = new FiscalDAO();
		}
		return instance;		
	}
	
	@Override
	public Long insert(Fiscal fiscal) {
		return insert(null, fiscal);
	}
	
	@Override
	public Long insert(Connection connection ,Fiscal fiscal) {

		Connection conn = (connection == null ? DAOManager.getConnection() : connection);
		try (PreparedStatement statement = conn.prepareStatement("INSERT INTO fiscal (total) values(?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setDouble(1, fiscal.getTotal());			
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
		return null;
	}

	@Override
    public List<Fiscal> findAll(String where) {
        return null;
    }
	
	@Override
	public void update(Fiscal fiscal) {
	}

	@Override
	public void delete(Fiscal fiscal) {		
		if (fiscal != null) {			
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM fiscal WHERE id = ?")) {
				statement.setLong(1, fiscal.getId());
				statement.executeUpdate();
				System.out.println("Delete result: User id " + fiscal.getId());
			} catch (SQLException e) {
				System.out.println("FiscalDAO.delete() error");
			}
		}
	}
}