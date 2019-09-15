package service;

import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.IGoodsDAO;
import entity.Goods;

/**
 * @author SergeyK
 */
public class GoodsService {

	private static Logger logger = null;
	
	public static Long addGoods(int code, String name, double quant, String measure, String comments) {
		
		Goods goods = new Goods();
		goods.setCode(code);
		goods.setName(name);
		goods.setQuant(quant);
		goods.setMeasure(measure);
		goods.setComments(comments);
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods existsGood = goodsDAO.findGoods(code);
		if (existsGood == null) {
			logger.info("Товар добавлен");
			return goodsDAO.insert(goods);			
		} else {
			logger.info("Товар с кодом " + code + " уже существует");
		}
		return null;
	}

	/**
	 * 
	 */
	public static List<Goods> view(int page, int recordsPerPage) {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		List<Goods> goods = goodsDAO.findAll(page, recordsPerPage);
		return (goods.size() > 0 ? goods : null);		
	}
	
	public static Long count() {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();		
		return goodsDAO.count();		
	}
	
	/**
	 * @param logger
	 */
	public static void setLogger(Logger log) {
		logger = log;		
	}
}
