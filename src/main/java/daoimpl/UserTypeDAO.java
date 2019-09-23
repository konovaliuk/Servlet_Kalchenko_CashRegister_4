package daoimpl;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOManager;
import dao.IUserTypeDAO;
import entity.UserType;

public class UserTypeDAO implements IUserTypeDAO<UserType> {
	
	private static UserTypeDAO instance;
	private static Logger logger = Logger.getLogger(GoodsDAO.class);
	
	private UserTypeDAO() {
	}
	
	public static IUserTypeDAO<UserType> getInstance() {
		if (instance == null) {
			instance = new UserTypeDAO();
		}
		return instance;		
	}
	
	@Override
	public Long insert(UserType usertype) {

		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO user_type "
					+ "(type, description) values(?, ?)")) {
			statement.setString(1, usertype.getType());
			statement.setString(2, usertype.getDescription());			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}

	@Override
    public List<UserType> findAll(String where) {
        return null;
    }
	
	@Override
	public void update(UserType usertype) {
	}

	@Override
	public void delete(UserType usertype) {		
		if (usertype != null) {			
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM user_type WHERE id = ?")) {
				statement.setLong(1, usertype.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public Long findUserType(String type) {		
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM user_type WHERE type = ?")) {
			statement.setString(1, type);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getLong("id");	
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
}