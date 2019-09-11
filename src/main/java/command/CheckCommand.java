package command;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import dao.*;
import entity.*;

/**
 * @author SergeyK
 */
public class CheckCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();
		String btnAddCheckspec = req.getParameter("btnAddCheckspec");
		String btnCreateCheck = req.getParameter("btnCreateCheck");
		@SuppressWarnings("unchecked")
		List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
		if (checkspecs == null) {
			checkspecs = new ArrayList<>();
			session.setAttribute("checkspecs", checkspecs);
		}
		String xcode = req.getParameter("xcode");
		String xname = req.getParameter("xname");
		if (btnAddCheckspec != null) {
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
				spec.setQuant(Double.valueOf(req.getParameter("quant")));
				spec.setPrice(Double.valueOf(req.getParameter("price")));						
				spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
				String nds = req.getParameter("nds");
				spec.setNds(nds != null ? Integer.valueOf(nds) : 0);
				spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
				checkspecs.add(spec);
				//req.setAttribute("addedCheckSpec", code);
			} else {
				if (code != null) {
					req.setAttribute("goodCodeNotFound", code);
				} else {
					req.setAttribute("goodNameNotFound", xname);
				}
			}			
		} else if (btnCreateCheck != null && session.getAttribute("user") != null && checkspecs.size() > 0) {
			Logger log = (Logger)req.getServletContext().getAttribute("log4");
			
			ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
			ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
			Check check = new Check();
			User user = (User) session.getAttribute("user");
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
				if (checkspecDAO.insertAll(checkspecs) > -1) {						
					req.setAttribute("addedCheck", true);
				}
				conn.commit();
			} catch (SQLException e) {
				req.setAttribute("addedCheck", false);
				log.error("Ошибка транзакции при добавлении чека и спецификаций. ", e);
				try {						
					conn.rollback();
				} catch (SQLException ex) {	
					log.error("Ошибка отмены транзакции. ", e);
				}				
			} finally {
				try {
					conn.setAutoCommit(true);
					checkDAO.setConnection(null);
					checkspecDAO.setConnection(null);
					conn.close();
				} catch (SQLException e) { log.error("Ошибка транзакции. ", e);		}
			}			
			session.setAttribute("checkspecs", null);				
			checkspecs.clear();
		}	
		return "check";		
	}
}
