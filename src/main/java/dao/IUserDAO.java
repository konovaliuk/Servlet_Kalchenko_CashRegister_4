package dao;

import entity.User;

public interface IUserDAO<T> extends IDAO<T> {

	public User findUser(User user);
    //public int insert(User user);
    //public void update();
    //public void delete(User user);
}
