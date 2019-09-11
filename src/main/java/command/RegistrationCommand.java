package command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.IUserDAO;
import dao.IUserTypeDAO;
import entity.User;
import entity.UserType;

/**
 * @author SergeyK
 */
public class RegistrationCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");				
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		IUserTypeDAO<UserType> userTypeDao = DAOFactory.getUserTypeDAO();
		User user = new User();
		user.setName(userName);
		user.setLogin(email);
		user.setPassword(password);
		user.setIdUserType(userTypeDao.findUserType("cashier"));	//по-умолчанию при регистрации права кассира
		userDAO.insert(user);
		user.setPassword(null);
		req.getSession().setAttribute("user", user);
		Logger log = (Logger)req.getServletContext().getAttribute("log4");
		log.info("Регистрация нового пользователя " + userName);
		return "check";
	}
}
