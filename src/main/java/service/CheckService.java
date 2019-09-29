package service;

import java.math.BigDecimal;
import java.util.List;
import dao.*;
import entity.*;
import transaction.*;

/**
 * Класс сервиса для чеков 
 * @author SergeyK
 */
public class CheckService {
	
	/**
	 * Сформировать спецификацию по найденному коду товара или наименованию товара 
	 * @param xcode код товара
	 * @param xname наименование товара
	 * @param quant количество товара
	 * @param nds ставка НДС
	 * @return спецификация чека
	 */
	public static Checkspec addCheckSpec(String xcode, String xname, Double quant, String nds) throws NumberFormatException {
		
		Goods existsGoods = null;
		Integer code = null;
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		if (xcode != null && !xcode.isEmpty()) {
			code = Integer.valueOf(xcode);
			existsGoods = goodsDAO.findGoods(code);
		} else if (xname != null && !xname.isEmpty()) {					
			existsGoods = goodsDAO.findGoods(xname);
		}					
		if (existsGoods != null) {
			Checkspec spec = new Checkspec();
			spec.setIdGood(existsGoods.getId());
			spec.setXname(existsGoods.getName());
			spec.setXcode(existsGoods.getCode());
			spec.setQuant(quant);
			spec.setPrice(existsGoods.getPrice());						
			spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
			spec.setNds(nds != null ? Integer.valueOf(nds) : 0);
			spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
			return spec;
		}
		return null;
	}

	/**
	 * Добавить чек со спецификациями (в транзакции)
	 * @param user пользователь, который создал чек
	 * @param checkspecs список спецификаций чека
	 * @return кол-во добавленных записей
	 * @throws TransactionException 
	 */
	public static void addCheck(User user, List<Checkspec> checkspecs) throws TransactionException {
		
		ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
		ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Check check = new Check();		
		check.setCreator(user.getId());
		
		double total = checkspecs.stream().mapToDouble(o -> o.getTotal()).sum();
		check.setTotal(total);
		//check.setCheckspecs(checkspecs);
		TransactionHandler.execute(connection -> {
			Long idCheck = checkDAO.insert(connection, check);
			checkspecs.stream().forEach(o -> {
				o.setIdCheck(idCheck);
				Goods goods = goodsDAO.findById(o.getIdGood());
				if (goods != null) {
					goods.setQuant(goods.getQuant() - o.getQuant());
					goodsDAO.update(connection, goods);
				}
				});
			checkspecDAO.insertAll(connection, checkspecs);
		});
	}
}
