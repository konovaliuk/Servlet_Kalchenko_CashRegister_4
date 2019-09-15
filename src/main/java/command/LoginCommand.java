package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import entity.User;
import service.UserService;

/**
 * @author SergeyK
 */
public class LoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		User dbUser = UserService.login(req.getParameter("email"),  req.getParameter("password"));
		if (dbUser != null) {
			session.setAttribute("userNotExists", null);
			session.setAttribute("user", dbUser);
			if (dbUser.getIdUserType() == 4) {
				return "goods";
			} else if (dbUser.getIdUserType() == 3) {
				return "check";
			} else if (dbUser.getIdUserType() == 2) {
				return "cancel";
			}
			Logger logger = (Logger)req.getServletContext().getAttribute("log4");
			logger.info("Авторизация пользователя " + dbUser.getName());
		} else {
			session.setAttribute("userNotExists", true);
			session.setAttribute("user", null);			
		}
		return "login";
	}
}
