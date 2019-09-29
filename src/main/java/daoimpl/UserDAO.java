package daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOManager;
import dao.IUserDAO;
import entity.User;
import entity.UserType;

public class UserDAO implements IUserDAO<User> {

	private static UserDAO instance;
	private static Logger logger = Logger.getLogger(UserDAO.class);
	
	private UserDAO() {
	}
	
	public static IUserDAO<User> getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;		
	}
	
	@Override
	public Long insert(User user) {
		if (user != null) {
			try (Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO user "
						+ "(login, password, name, id_user_type) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getName());
				statement.setLong(4, user.getIdUserType());
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
	public List<User> findAll() {
		return findAll(null);
	}
	
	@Override
    public List<User> findAll(String where) {
		List<User> users = new ArrayList<>();
		try (Connection connection = DAOManager.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM user" + (where != null ? " WHERE " + where : ""));
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setIdUserType(rs.getLong("id_user_type"));
				//user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				users.add(user);					
			}				
		} catch (SQLException e) {
			logger.error(e);				
		}
		return users;
    }
	 
	@Override
	public void update(User user) {
		if (user != null) {
			try(Connection connection = DAOManager.getConnection();
					PreparedStatement statement = connection.prepareStatement("UPDATE user SET login=?, password=?, name=?, id_user_type=? WHERE id=?")) {
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getName());
				statement.setLong(4, user.getIdUserType());
				statement.setLong(5, user.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void delete(User user) {
		if (user != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
				statement.setLong(1, user.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public User findUserByLogin(String login) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE login = ?")) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();			
			if (resultSet.first()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setLogin(login);
				user.setIdUserType(resultSet.getLong("id_user_type"));
				user.setName(resultSet.getString("name"));
				return user;
			}
		} catch (SQLException e) {
			logger.error("Ошибка при поиске пользователя", e);
		}
		return null;
	}
	
	@Override
	public User findUser(String login, String password) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT u.*, t.id AS tid, t.type, t.description FROM user u "
					+ "INNER JOIN user_type t ON t.id = u.id_user_type "
					+ "WHERE u.login = ? AND u.password = ? ")) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();			
			if (resultSet.first()) {
				User user = new User();
				UserType userType = new UserType();
				user.setId(resultSet.getLong("id"));
				user.setLogin(login);
				user.setIdUserType(resultSet.getLong("id_user_type"));
				user.setName(resultSet.getString("name"));
				userType.setId(resultSet.getLong("tid"));
				userType.setType(resultSet.getString("type"));
				userType.setDescription(resultSet.getString("description"));
				user.setUserType(userType);
				return user;
			}
		} catch (SQLException e) {
			logger.error("Ошибка при поиске пользователя", e);
		}
		return null;
	}
}
