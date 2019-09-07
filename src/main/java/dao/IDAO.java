package dao;

import java.util.List;

public interface IDAO<T> {
	
	public List<T> findAll(String where);
    public Long insert(T item);
    public void update(T item);
    public void delete(T item);
}
