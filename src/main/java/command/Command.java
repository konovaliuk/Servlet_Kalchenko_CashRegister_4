package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SergeyK
 */
public interface Command {

	String execute(HttpServletRequest req, HttpServletResponse resp);
}
