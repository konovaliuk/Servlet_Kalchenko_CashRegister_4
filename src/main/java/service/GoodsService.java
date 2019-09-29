package service;

import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.IGoodsDAO;
import entity.Goods;

/**
 * Класс сервиса для товаров
 * @author SergeyK
 */
public class GoodsService {

	private static Logger logger = Logger.getLogger(GoodsService.class);
	
	/**
	 * Добавить товар в базу данных (если товара с заданным кодом нет в наличии)
	 * @param code код товара
	 * @param name наименование
	 * @param quant количество товара
	 * @param price цена товара
	 * @param measure единица измерения
	 * @param comments комментарии к товару
	 * @return Id добавленно записи
	 */
	public static Long addGoods(int code, String name, double quant, double price, String measure, String comments) {
		
		Goods goods = new Goods();
		goods.setCode(code);
		goods.setName(name);
		goods.setQuant(quant);
		goods.setPrice(price);
		goods.setMeasure(measure);
		goods.setComments(comments);
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods existsGood = goodsDAO.findGoods(code);
		if (existsGood != null) {
			logger.info("Товар с кодом " + code + " уже существует");
			return -1l;
		} else {
			existsGood = goodsDAO.findGoods(name);		
			if (existsGood != null) {
				logger.info("Товар " + name + " уже существует");
				return -2l;
			} else {
				logger.info("Товар добавлен");
				return goodsDAO.insert(goods);			
			}
		}
	}

	/**
	 * Получить список товаров для pagination для page страницы с заданным количеством записей на страницы
	 * @param page заданная страница
	 * @param recordsPerPage количество записей на странице
	 * @return List<Goods> список записей
	 */
	public static List<Goods> view(int page, int recordsPerPage) {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		List<Goods> goods = goodsDAO.findAll(page, recordsPerPage);
		return (goods.size() > 0 ? goods : null);		
	}
	
	/**
	 * Найти количество записей в таблице
	 * @return количество записей
	 */
	public static Long count() {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();		
		return goodsDAO.count();		
	}

	/**
	 * Изменить товар по коду товара
	 * @param changecode код товара
	 * @param changequant количество
	 * @param changeprice цена
	 */
	public static void changeGoods(Integer changecode, Double changequant, Double changeprice) {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods goods = goodsDAO.findGoods(changecode);
		if (goods != null) {
			if (changequant != null) {
				goods.setQuant(changequant);
			}
			if (changeprice != null) {
				goods.setPrice(changeprice);
			}
			goodsDAO.update(goods);
		}
	}
}
