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

	private static Logger logger = Logger.getLogger(RegistrationCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		User user = UserService.registration(req.getParameter("name"), req.getParameter("email"), req.getParameter("password"));
		if(user != null) {
			req.getSession().setAttribute("user", user);
			logger.info("Регистрация нового пользователя " + req.getParameter("name"));
			return "check";
		} else {
			logger.info("Регистрация не удалась. Пользователь с таким email уже существует");
			req.setAttribute("existsLogin",  req.getParameter("email"));
			return null;
		}		
	}
}
