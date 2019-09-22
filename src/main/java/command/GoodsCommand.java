package command;

import java.util.List;

import javax.servlet.http.*;

import entity.Goods;
import service.GoodsService;

/**
 * Класс для добавления товара в базу данных, 
 * отображения всех товаров с pagination
 * @author SergeyK
 */
public class GoodsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
        Integer page =1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        String url = "goods" + (page > 1 ? "?page=" + page : "");
		if (req.getParameter("btnSaveGood") != null) {
			int code = Integer.valueOf(req.getParameter("code"));
			String name = req.getParameter("name");
			Long goodsId = GoodsService.addGoods(code, name, Double.valueOf(req.getParameter("quant")), 
					req.getParameter("measure"), req.getParameter("comments"));
			if (goodsId != null) {
				req.setAttribute("addedGood", name);				
			} else {
				req.setAttribute("addedGood", null);
				req.setAttribute("code", code);				
			}
		}
        int recordsPerPage = 10;
		List<Goods> goods = GoodsService.view(page, recordsPerPage);
		req.setAttribute("viewGoods", goods);
		req.setAttribute("currentPage", page);
		long countGoods = GoodsService.count();
		req.setAttribute("maxPages", countGoods / recordsPerPage + Math.min(countGoods % recordsPerPage, 1));		
		return url;
	}
}
