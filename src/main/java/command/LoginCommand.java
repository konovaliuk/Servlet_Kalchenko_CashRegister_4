package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import entity.User;
import service.UserService;

/**
 * Класс Command для авторизации пользователя
 * @author SergeyK
 */
public class LoginCommand implements Command {

	private static Logger logger = Logger.getLogger(LoginCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		User dbUser = UserService.findUser(req.getParameter("email"),  req.getParameter("password"));
		if (dbUser != null) {
			session.setAttribute("userNotExists", null);
			session.setAttribute("user", dbUser);
			if (dbUser.getIdUserType() == 4) {		//товаровед
				return "goods";
			} else if (dbUser.getIdUserType() == 3) {	//кассир
				return "check";
			} else if (dbUser.getIdUserType() == 2) {	//старший кассир
				return "cancel";
			}
			logger.info("Авторизация пользователя " + dbUser.getName());
		} else {
			if (session != null) {	//проверка на null для mockito 
				session.setAttribute("userNotExists", true);
				session.setAttribute("user", null);
			}
		}
		return "login";
	}
}
