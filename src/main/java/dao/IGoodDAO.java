package dao;

import entity.Good;

public interface IGoodDAO<T> extends IDAO<T> {

	/**
	 * @param code
	 * @return
	 */
	Good findGood(int code);
	
	/**
	 * @param name
	 * @return
	 */
	Good findGood(String name);
}
