package command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Checkspec;
import entity.User;
import service.CheckService;

/**
 * Класс Command для добавления чека со спецификациями
 * @author SergeyK
 */
public class CheckCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
		int countAdd = CheckService.addCheck((User) session.getAttribute("user"), checkspecs);
		req.setAttribute("addedCheck", countAdd > 0);
		session.setAttribute("checkspecs", null);				
		checkspecs.clear();
		return "check";
	}
}
