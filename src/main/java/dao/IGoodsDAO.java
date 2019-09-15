package dao;

import java.util.List;

import entity.Goods;

public interface IGoodsDAO<T> extends IDAO<T> {

	/**
	 * @param code
	 * @return
	 */
	Goods findGoods(int code);
	
	/**
	 * @param name
	 * @return
	 */
	Goods findGoods(String name);
	
	/**
	 * @param page
	 * @param recordsPerPage
	 * @return
	 */
	public List<Goods> findAll(Integer page, Integer recordsPerPage);
	
	public long count();
}
