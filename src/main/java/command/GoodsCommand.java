package command;

import java.util.List;

import javax.servlet.http.*;

import entity.Goods;
import service.GoodsService;

/**
 * Класс для добавления товара в базу данных, изменения количества 
 * отображения всех товаров с pagination
 * @author SergeyK
 */
public class GoodsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {		

		if (req.getParameter("btnSaveGood") != null) {
			int code = Integer.valueOf(req.getParameter("code"));
			String name = req.getParameter("name");
			Long goodsId = GoodsService.addGoods(code, name, Double.valueOf(req.getParameter("quant")), 
					req.getParameter("measure"), req.getParameter("comments"));
			if (goodsId > 0) {
				req.setAttribute("addedGood", name);				
			} else if (goodsId == -1) {
				req.setAttribute("addedGood", null);
				req.setAttribute("existsCode", code);
			} else if (goodsId == -2) {
				req.setAttribute("addedGood", null);
				req.setAttribute("existsName", name);
			}
		}
		if (req.getParameter("btnChangeGoods") != null) {
			GoodsService.changeGoods(Integer.valueOf(req.getParameter("changecode")), Double.valueOf(req.getParameter("changequant")));			
		}
        Integer page =1;
        if (req.getParameter("page") != null) {
        	try {
        		page = Integer.parseInt(req.getParameter("page"));
			} catch (NumberFormatException e) {
				req.setAttribute("wronginput", true);
			}
        }
        int recordsPerPage = 10;
		List<Goods> goods = GoodsService.view(page, recordsPerPage);
		req.setAttribute("viewGoods", goods);
		req.setAttribute("currentPage", page);
		long countGoods = GoodsService.count();
		req.setAttribute("maxPages", countGoods / recordsPerPage + Math.min(countGoods % recordsPerPage, 1));		
		return null;
	}
}
