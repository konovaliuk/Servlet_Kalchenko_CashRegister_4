package command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import entity.*;
import service.CheckService;

/**
 * Класс Command для формирования спецификации по коду товара или наименованию товара 
 * @author SergeyK
 */
public class CheckSpecCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
		if (checkspecs == null) {
			checkspecs = new ArrayList<>();
			session.setAttribute("checkspecs", checkspecs);
		}
		String xcode = req.getParameter("xcode");
		String xname = req.getParameter("xname");
		Checkspec spec = CheckService.addCheckSpec(xcode, xname, Double.valueOf(req.getParameter("quant")), 
				Double.valueOf(req.getParameter("price")), req.getParameter("nds"));
		if (spec != null) {
			checkspecs.add(spec);
			req.setAttribute("checkspecs", checkspecs);
		} else {
			if (xcode != null && !xcode.isEmpty()) {
				req.setAttribute("goodCodeNotFound", xcode);
			} else {
				req.setAttribute("goodNameNotFound", xname);
			}
		}		
		return "check";				
	}
}
