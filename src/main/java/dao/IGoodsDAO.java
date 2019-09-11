package dao;

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
}
