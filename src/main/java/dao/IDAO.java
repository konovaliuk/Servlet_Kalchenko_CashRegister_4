package dao;

import java.util.List;

public interface IDAO<T> {
	
	/**
	 * Добавить запись в таблицу
	 * @param item добавляемая запись
	 * @return Id добавленной записи
	 */
    public Long insert(T item);
    
	/**
	 * Обновить запись в таблице
	 * @param item обновляемая запись
	 */
    public void update(T item);
    
	/**
	 * Удалить запись в таблице чеки
	 * @param item удаляемая запись
	 */
    public void delete(T item);
    
	/**
	 * Найти все записи из таблицы
	 * @param where строка запроса where для поиска
	 * @return список записей
	 */
	public List<T> findAll(String where);
}
