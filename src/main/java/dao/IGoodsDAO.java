package dao;

import java.util.List;

import entity.Goods;

public interface IGoodsDAO<T> extends IDAO<T> {

	/**
	 * Найти товар по коду товара
	 * @param code код товара
	 * @return товар
	 */
	Goods findGoods(int code);
	
	/**
	 * Найти товар по наименованию товара
	 * @param name наименование товара
	 * @return товар
	 */
	Goods findGoods(String name);
	
	
	/**
	 * Найти все записи из таблицы для pagination для page страницы с заданным количеством записей на страницы
	 * @param page заданная страница
	 * @param recordsPerPage количество записей на странице
	 * @return List<Goods> список записей
	 */
	public List<Goods> findAll(Integer page, Integer recordsPerPage);
	
	/**
	 * Найти количество записей в таблице
	 * @return количество записей
	 */
	public long count();
}
