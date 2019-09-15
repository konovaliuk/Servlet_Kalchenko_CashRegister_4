package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.DAOManager;
import dao.ICheckDAO;
import dao.ICheckSpecDAO;
import dao.IGoodsDAO;
import entity.Check;
import entity.Checkspec;
import entity.Goods;
import entity.User;

/**
 * @author SergeyK
 */
public class CheckService {	

	private static Logger logger = null;
	/**
	 * @param logger
	 */
	public static void setLogger(Logger log) {
		logger = log;		
	}
	
	public static Checkspec addCheckSpec(String xcode, String xname, Double quant, Double price, String nds) {
		
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
			spec.setPrice(price);						
			spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
			//String nds = req.getParameter("nds");
			spec.setNds(nds != null ? Integer.valueOf(nds) : 0);
			spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
			//checkspecs.add(spec);
			//req.setAttribute("addedCheckSpec", code);
			return spec;
		}
		return null;
	}

	/**
	 * 
	 */
	public static int addCheck(User user, List<Checkspec> checkspecs) {
		
		int countAdd = 0;
		ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
		ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
		Check check = new Check();		
		check.setCreator(user.getId());
		
		double total = checkspecs.stream().mapToDouble(o -> o.getTotal()).sum();
		check.setTotal(total);
		check.setCheckspecs(checkspecs);
		Connection conn = DAOManager.getConnection();
		try {	//транзакция (добавление заголовка счета и спецификаций)
			conn.setAutoCommit(false);
			checkDAO.setConnection(conn);
			Long idCheck = checkDAO.insert(check);
			checkspecs.stream().peek(o -> o.setIdCheck(idCheck)).collect(Collectors.toList());
			checkspecDAO.setConnection(conn);
			countAdd = checkspecDAO.insertAll(checkspecs);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Ошибка транзакции при добавлении чека и спецификаций. ", e);
			try {						
				conn.rollback();
			} catch (SQLException ex) {	
				logger.error("Ошибка транзакции. ", e);
			}				
		} finally {
			try {
				conn.setAutoCommit(true);
				checkDAO.setConnection(null);
				checkspecDAO.setConnection(null);
				conn.close();
			} catch (SQLException e) { logger.error("Ошибка транзакции. ", e);		}
		}
		return countAdd;
	}
}
