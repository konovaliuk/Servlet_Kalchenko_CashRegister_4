package daoimpl;

import java.sql.*;
import java.util.List;

import dao.DAOManager;
import dao.IUserTypeDAO;
import entity.UserType;

public class UserTypeDAO implements IUserTypeDAO<UserType> {
	
	public UserTypeDAO() {
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
			e.printStackTrace();
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
				statement.setLong(1, usertype.getIdUserType());
				statement.executeUpdate();
				System.out.println("Delete result: User id " + usertype.getIdUserType());
			} catch (SQLException e) {
				System.out.println("UserDAO.delete() error");
			}
		}
	}
	
	@Override
	public Long findUserType(String type) {		
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT id_user_type FROM user_type "
					+ "WHERE type = ?")) {
			statement.setString(1, type);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getLong("id_user_type");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}