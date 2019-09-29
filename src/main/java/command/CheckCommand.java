package command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import entity.Checkspec;
import entity.User;
import service.CheckService;
import service.GoodsService;
import transaction.TransactionException;

/**
 * Класс Command для добавления чека со спецификациями
 * @author SergeyK
 */
public class CheckCommand implements Command {

	private static Logger logger = Logger.getLogger(GoodsService.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("addcheckspecs");
		if (req.getParameter("btnCreateCheck") != null && checkspecs != null && checkspecs.size() > 0) {
			try {
				CheckService.addCheck((User) session.getAttribute("user"), checkspecs);
				req.setAttribute("addedCheck", true);
				//session.setAttribute("addcheckspecs", null);
				checkspecs.clear();
			} catch (TransactionException e) {
				req.setAttribute("addedCheck", false);
				logger.error("Ошибка транзакции при добавлении чека и спецификаций. ", e);
			}
		}
		if (req.getParameter("btnCancelCheck") != null && checkspecs != null) {
			checkspecs.clear();
		}
		return null;
	}
}
