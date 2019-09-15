package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command для работы с сервлетом
 * @author SergeyK
 */
public interface Command {

	/**
	 * Выполнить/запустить логику
	 * @param req request 
	 * @param resp response
	 * @param url путь для перенаправления  на страницу
	 */
	String execute(HttpServletRequest req, HttpServletResponse resp);
}
