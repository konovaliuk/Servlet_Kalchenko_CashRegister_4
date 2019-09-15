package daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DAOManager;
import dao.IUserDAO;
import entity.User;

public class UserDAO implements IUserDAO<User> {

	private static UserDAO instance;
	
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
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
    public List<User> findAll(String where) {
		List<User> users = new ArrayList<>();
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user" + (where != null ? " WHERE " + where : ""))) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setIdUserType(rs.getLong("id_user_type"));
				//user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				users.add(user);					
			}				
		} catch (SQLException e) {
			e.printStackTrace();				
		}
		return users;
    }
	 
	@Override
	public void update(User user) {
	}

	@Override
	public void delete(User user) {
		if (user != null) {
			try(Connection connection = DAOManager.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
				statement.setLong(1, user.getId());
				statement.executeUpdate();
				System.out.println("Delete result: User id " + user.getId());
			} catch (SQLException e) {
				System.out.println("UserDAO.delete() error");
			}
		}
	}

	@Override
	public User findUser(User user) {
		try (Connection connection = DAOManager.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM user WHERE login = ? AND password = ?")) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first()) {
				user.setId(resultSet.getLong("id"));
				user.setIdUserType(resultSet.getLong("id_user_type"));
				//user.setPassword(resultSet.getString("password"));
				user.setName(resultSet.getString("name"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
