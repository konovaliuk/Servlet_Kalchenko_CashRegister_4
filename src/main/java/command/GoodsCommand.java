package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import dao.*;
import entity.*;

/**
 * @author SergeyK
 */
public class GoodsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {	
		Logger log = (Logger)req.getServletContext().getAttribute("log4");
		int code = Integer.valueOf(req.getParameter("code"));
		String name = req.getParameter("name");
		Goods goods = new Goods();
		goods.setCode(Integer.valueOf(req.getParameter("code")));
		goods.setName(name);
		goods.setQuant(Double.valueOf(req.getParameter("quant")));
		goods.setMeasure(req.getParameter("measure"));
		goods.setComments(req.getParameter("comments"));
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods existsGood = goodsDAO.findGoods(code);
		if (existsGood == null) {
			goodsDAO.insert(goods);
			req.setAttribute("addedGood", name);
			log.info("Товар добавлен");
		} else {
			req.setAttribute("addedGood", null);
			req.setAttribute("code", code);
			log.info("Товар с кодом " + code + " уже существует");
		}		
		return "goods";		
	}
}
