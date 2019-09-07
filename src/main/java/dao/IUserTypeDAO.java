package dao;

public interface IUserTypeDAO<T> extends IDAO<T> {
    //public int insert(UserType user);
    //public void update(UserType user);
    //public void delete(UserType user);
	public Long findUserType(String type);
}
