package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import command.Command;
import command.CommandFactory;
import command.CommandFactory.Commands;
import dao.*;

public class MainController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		/* logfilename - получает имя файла конфигурации из пар-ра инициализации сервлета*/
		String logfilename = getInitParameter("logfile"); 
		 
		String pref = getServletContext().getRealPath("/");

		//pref – указывает на путь к файлу конфигурации
		PropertyConfigurator.configure(pref+logfilename);
		Logger logger = Logger.getRootLogger();
		getServletContext().setAttribute(new String("log4"), logger);		
		//getServletContext().setAttribute("logfilename", logfilename);
		logger.info("Load-onstart-up Servlet");
		try {
			DAOManager.getInstance();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String lang= req.getParameter("lang");		
		javax.servlet.http.HttpSession session = req.getSession();
		if (lang != null) {
			session.setAttribute("language", lang);
		}
		String url = "/WEB-INF/view/";
		switch (req.getServletPath()) {
		    case "/":
		    case "/login":
		    	url += "index.jsp";
		        break;
		    case "/registration":
		    	url += "registration.jsp";
		        break;
		    case "/goods":
				Command command = CommandFactory.getInstance().getCommand(Commands.GOODS);
				command.execute(req, resp);		//для pagination
		    	url += "goods.jsp";
		        break;
		    case "/check":
		    	url += "check.jsp";
		        break;
		    case "/cancel":
		    	url += "cancel.jsp";
		        break;
		    case "/logout":
				lang = (String)session.getAttribute("language");
		    	session.invalidate();
				if (lang != null) {
					req.getSession().setAttribute("language", lang);
				}
		    	url += "index.jsp";
		        break;
		}
		req.getRequestDispatcher(url).forward(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CommandFactory commands = CommandFactory.getInstance();		
		switch (req.getServletPath()) {
		    case "/":
		    case "/login":
		    case "/logout":		
				if (req.getParameter("btnLogin") != null && req.getParameter("email") != null) {
					Command command = commands.getCommand(Commands.LOGIN);
					String path = command.execute(req, resp);
					resp.sendRedirect(path);		//вместо getRequestDispatcher, что путь в url изменился
				}
		        break;
		    case "/registration":
				if (req.getParameter("btnReg") != null) {
					Command command = commands.getCommand(Commands.REGISTRATION);
					String path = command.execute(req, resp);
					resp.sendRedirect(path);
				}
		        break;
		    case "/goods":
				Command command = commands.getCommand(Commands.GOODS);
				command.execute(req, resp);
				req.getRequestDispatcher("/WEB-INF/view/goods.jsp").forward(req, resp);
				//resp.sendRedirect(path);//ранее заполненные данные POST не передаются повторно, когда пользователь неосознанно нажимает F5		
		        break;
		    case "/check":
				if (req.getParameter("btnAddCheckspec") != null) {
					command = commands.getCommand(Commands.CHECKSPEC);
					command.execute(req, resp);
				} else {
					command = commands.getCommand(Commands.CHECK);
					command.execute(req, resp);
				}
				req.getRequestDispatcher("/WEB-INF/view/check.jsp").forward(req, resp);	
		        break;
		    case "/cancel":
				command = commands.getCommand(Commands.CANCEL);
				String page = command.execute(req, resp);
				//resp.sendRedirect(path);
				req.getRequestDispatcher("/WEB-INF/view/" + page + ".jsp").forward(req, resp);
		        break;
		}
	}
}

