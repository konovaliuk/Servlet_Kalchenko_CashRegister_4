package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import entity.User;
import service.UserService;

/**
 * Класс Command для регистрации нового пользователя
 * @author SergeyK
 */
public class RegistrationCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		Logger logger = (Logger)req.getServletContext().getAttribute("log4");
		User user = UserService.registration(req.getParameter("name"), req.getParameter("email"), req.getParameter("password"));
		if(user != null) {
			req.getSession().setAttribute("user", user);
			logger.info("Регистрация нового пользователя " + req.getParameter("name"));
			return "check";
		} else {
			logger.info("Регистрация не удалась");
			return "registration";
		}		
	}
}
