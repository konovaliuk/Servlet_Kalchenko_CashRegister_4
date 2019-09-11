package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import command.Command;
import command.CommandFactory;
import command.CommandFactory.Commands;
import dao.*;
import entity.*;

public class MainController extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			DAOManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* logfilename - получает имя файла конфигурации из пар-ра инициализации сервлета*/
		String logfilename = getInitParameter("logfile"); 
		 
		String pref = getServletContext().getRealPath("/");

		//pref – указывает на путь к файлу конфигурации
		PropertyConfigurator.configure(pref+logfilename);
		Logger loger = Logger.getRootLogger();

		getServletContext().setAttribute(new String("log4"), loger);
		getServletContext().setAttribute("logfilename", logfilename);
		loger.info("Load-onstart-up Servlet");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String lang= req.getParameter("lang");		
		javax.servlet.http.HttpSession session = req.getSession();
		if (lang != null) {
			session.setAttribute("language", lang);
		}
 		String userPath = req.getServletPath();
		String url = "/WEB-INF/view/";
		
		if (userPath.equals("/") || userPath.equals("/login")) {
			url += "index.jsp";
		} else if (userPath.equals("/registration")) {
			url += "registration.jsp";
		} else if (userPath.equals("/goods")) {
			url += "goods.jsp";
		} else if (userPath.equals("/check")) {
			url += "check.jsp";
		} else if (userPath.equals("/cancel")) {
			url += "cancel.jsp";
		} else if (userPath.equals("/report")) {
			url += "report.jsp";
		} else if (userPath.equals("/logout")) {			
			session.invalidate();	//session.setAttribute("user", null);
			url += "index.jsp";
		} 	
		req.getRequestDispatcher(url).forward(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userPath = req.getServletPath();
		CommandFactory commands = CommandFactory.getInstance();
		
		if (userPath.equals("/") || userPath.equals("/login")) {
			String buttonLogin = req.getParameter("btnLogin");			
			String email = req.getParameter("email");			
			if (buttonLogin != null && email != null) {
				Command command = commands.getCommand(Commands.LOGIN);
				String path = command.execute(req, resp);
				resp.sendRedirect(path);		//вместо getRequestDispatcher, что путь в url изменился
			}
		} else if (userPath.equals("/registration")) {
			String buttonReg = req.getParameter("btnReg");
			if (buttonReg != null) {
				Command command = commands.getCommand(Commands.REGISTRATION);
				String path = command.execute(req, resp);
				resp.sendRedirect(path);
			}
		} else if (userPath.equals("/goods")) {
			String buttonSave = req.getParameter("btnSaveGood");
			if (buttonSave != null) {
				Command command = commands.getCommand(Commands.GOODS);
				command.execute(req, resp);
			}
			req.getRequestDispatcher("/WEB-INF/view/goods.jsp").forward(req, resp);
		} else if (userPath.equals("/check")) {			
			Command command = commands.getCommand(Commands.CHECK);
			command.execute(req, resp);			
			req.getRequestDispatcher("/WEB-INF/view/check.jsp").forward(req, resp);
		} else if (userPath.equals("/cancel")) {
			Command command = commands.getCommand(Commands.CANCEL);
			String path = command.execute(req, resp);
			resp.sendRedirect(path);
			//req.getRequestDispatcher("/WEB-INF/view/cancel.jsp").forward(req, resp);
		}
	}
}

