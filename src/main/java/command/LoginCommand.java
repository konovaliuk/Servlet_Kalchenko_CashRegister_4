package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.IUserDAO;
import entity.User;

/**
 * @author SergeyK
 */
public class LoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String url = null;		
		String email = req.getParameter("email");
		User user = new User();
		user.setLogin(email);
		user.setPassword(req.getParameter("password"));
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		User dbUser = userDAO.findUser(user);
		HttpSession session = req.getSession();
		if (dbUser != null) {
			session.setAttribute("userNotExists", null);
			session.setAttribute("user", dbUser);
			if (dbUser.getIdUserType() == 4) {
				url = "goods";
			} else if (dbUser.getIdUserType() == 3) {
				url = "check";
			} else if (dbUser.getIdUserType() == 2) {
				url = "cancel";
			}
			Logger log = (Logger)req.getServletContext().getAttribute("log4");
			log.info("Авторизация пользователя " + dbUser.getName());
		} else {
			session.setAttribute("userNotExists", true);
			session.setAttribute("user", null);
			url = "login";
		}		
		return url;		
	}
}
