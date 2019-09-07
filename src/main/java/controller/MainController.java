package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import dao.DAOFactory;
import dao.DAOManager;
import dao.ICheckDAO;
import dao.ICheckSpecDAO;
import dao.IGoodDAO;
import dao.IUserDAO;
import dao.IUserTypeDAO;
import entity.Check;
import entity.Checkspec;
import entity.Good;
import entity.User;
import entity.UserType;

public class MainController extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			DAOManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet");
		String lang= req.getParameter("lang");
		
		javax.servlet.http.HttpSession session = req.getSession();
		if (lang != null) {
			session.setAttribute("language", lang);	    
			//req.setAttribute("language", lang);
		}
		
	    System.out.println("lang  =" + lang);
		System.out.println(req.getLocale());
 		String userPath = req.getServletPath();
		String url = "/WEB-INF/view/";
		
		if (userPath.equals("/") || userPath.equals("/login")) {
			url += "index.jsp";
		} else if (userPath.equals("/registration")) {
			System.out.println("POST userPath.equals(\"/registration\")");
			url += "registration.jsp";
		} else if (userPath.equals("/goods")) {
			System.out.println("POST userPath.equals(\"/goods\")");
			url += "goods.jsp";
		} else if (userPath.equals("/check")) {
			System.out.println("POST userPath.equals(\"/check\")");
			url += "check.jsp";
		} else if (userPath.equals("/cancel")) {
			System.out.println("POST userPath.equals(\"/cancel\")");
			url += "cancel.jsp";
		} else if (userPath.equals("/logout")) {
			session.setAttribute("user", null);
			url += "index.jsp";
		} 	
		req.getRequestDispatcher(url).forward(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Enumeration<String> h = req.getHeaderNames();
		while (h.hasMoreElements()) {
			String s = h.nextElement();
			//System.out.println(s + " =" + req.getHeader(s));			
		};
		for (int i = 0; i < req.getCookies().length; i++) {
			Cookie k = req.getCookies()[i];
			System.out.println(i + " =" + k.getName() + " "+ k.getValue());
		}
		String url = req.getRequestURI();
		String userPath = req.getServletPath();
		javax.servlet.http.HttpSession session = req.getSession();
		System.out.println("---doPost");
		if (userPath.equals("/") || userPath.equals("/login")) {
			String buttonLogin = req.getParameter("btnLogin");			
			String email = req.getParameter("email");
			if (buttonLogin != null && email != null) {
				User user = new User();
				user.setLogin(email);
				user.setPassword(req.getParameter("password"));
				IUserDAO<User> userDAO = DAOFactory.getUserDAO();
				User dbUser = userDAO.findUser(user);
				if (dbUser != null) {
					session = req.getSession(true);
					req.setAttribute("userNotExists", null);
					session.setAttribute("user", dbUser);
					if (dbUser.getIdUserType() == 4) {
						url = "/goods.jsp";
						resp.sendRedirect("goods");	//вместо getRequestDispatcher, что путь в url изменился
						//req.getRequestDispatcher("/WEB-INF/view/goods.jsp").forward(req, resp);
					} else if (dbUser.getIdUserType() == 3) {
						url = "/check.jsp";
						resp.sendRedirect("check");
					} else if (dbUser.getIdUserType() == 2) {
						url = "/cancel.jsp";
						resp.sendRedirect("cancel");
					}					
				} else {
					req.setAttribute("userNotExists", true);
					session.setAttribute("user", null);
					req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
				}
			}
		} else if (userPath.equals("/registration")) {
			String buttonReg = req.getParameter("btnReg");
			if (buttonReg != null) {
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
				session.setAttribute("user", user);
				resp.sendRedirect("check");
			}
		} else if (userPath.equals("/goods")) {
			String buttonSave = req.getParameter("btnSaveGood");
			if (buttonSave != null) {
				int code = Integer.valueOf(req.getParameter("code"));
				String name = req.getParameter("name");
				Good good = new Good();
				good.setCode(Integer.valueOf(req.getParameter("code")));
				good.setName(name);
				good.setQuant(Double.valueOf(req.getParameter("quant")));
				good.setMeasure(req.getParameter("measure"));
				good.setComments(req.getParameter("comments"));
				IGoodDAO<Good> goodDAO = DAOFactory.getGoodDAO();
				Good existsGood = goodDAO.findGood(code);
				if (existsGood == null) {
					goodDAO.insert(good);
					req.setAttribute("addedGood", name);
				} else {
					req.setAttribute("addedGood", null);
					req.setAttribute("code", code);
				}
			}
			req.getRequestDispatcher("/WEB-INF/view/goods.jsp").forward(req, resp);
		} else if (userPath.equals("/check")) {
			String btnAddCheckspec = req.getParameter("btnAddCheckspec");
			String btnCreateCheck = req.getParameter("btnCreateCheck");
			@SuppressWarnings("unchecked")
			List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
			if (checkspecs == null) {
				checkspecs = new ArrayList<>();
				session.setAttribute("checkspecs", checkspecs);
			}
			String xcode = req.getParameter("xcode");
			String xname = req.getParameter("xname");
			if (btnAddCheckspec != null) {
				Good existsGood = null;
				Integer code = null;
				IGoodDAO<Good> goodDAO = DAOFactory.getGoodDAO();
				if (xcode != null && !xcode.isEmpty()) {
					code = Integer.valueOf(xcode);
					existsGood = goodDAO.findGood(code);
				} else if (xname != null && !xname.isEmpty()) {					
					existsGood = goodDAO.findGood(xname);
				}					
				if (existsGood != null) {
					Checkspec spec = new Checkspec();
					spec.setIdGood(existsGood.getId());
					spec.setXname(existsGood.getName());
					spec.setXcode(existsGood.getCode());
					spec.setQuant(Double.valueOf(req.getParameter("quant")));
					spec.setPrice(Double.valueOf(req.getParameter("price")));						
					spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
					String nds = req.getParameter("nds");
					spec.setNds(nds != null ? Integer.valueOf(nds) : 0);
					spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
					checkspecs.add(spec);
					//req.setAttribute("addedCheckSpec", code);
				} else {
					if (code != null) {
						req.setAttribute("goodCodeNotFound", code);
					} else {
						req.setAttribute("goodNameNotFound", xname);
					}
				}			
			} else if (btnCreateCheck != null && session.getAttribute("user") != null && checkspecs.size() > 0) {
				ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
				ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
				Check check = new Check();
				User user = (User) session.getAttribute("user");
				check.setCreator(user.getId());
				double total = checkspecs.stream().mapToDouble(o -> o.getTotal()).sum();
				check.setTotal(total);
				check.setCheckspecs(checkspecs);
				Connection conn = DAOManager.getConnection();
				try {
					conn.setAutoCommit(false);
					checkDAO.setConnection(conn);
					Long idCheck = checkDAO.insert(check);
					checkspecs.stream().peek(o -> o.setIdCheck(idCheck)).collect(Collectors.toList());
					checkspecDAO.setConnection(conn);
					if (checkspecDAO.insertAll(checkspecs) > -1) {						
						req.setAttribute("addedCheck", true);
					}
					conn.commit();
				} catch (SQLException e) {
					req.setAttribute("addedCheck", false);
					try {						
						conn.rollback();
					} catch (SQLException ex) {	ex.printStackTrace();	}
					e.printStackTrace();
				} finally {
					try {
						conn.setAutoCommit(true);
						checkDAO.setConnection(null);
						checkspecDAO.setConnection(null);
						conn.close();
					} catch (SQLException e) { e.printStackTrace();		}
				}			
				session.setAttribute("checkspecs", null);				
				checkspecs.clear();
			}
			req.getRequestDispatcher("/WEB-INF/view/check.jsp").forward(req, resp);
		} else if (userPath.equals("/cancel")) {
			String buttonSearch = req.getParameter("btnSearchCheck");
			if (buttonSearch != null) {
				Long checkid = Long.valueOf(req.getParameter("checkid"));
				ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
				ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
				Check check = checkDAO.findCheck(checkid);
				List<Checkspec> checkspecs = checkspecDAO.findAll("id_check = " + checkid + "");
				if (check != null) {
					session.setAttribute("check", check);
					session.setAttribute("checkspecs", checkspecs);
				} else {
					session.setAttribute("check", null);
					req.setAttribute("checkfind", false);
				}
			}
			String btnCancelCheck = req.getParameter("btnCancelCheck");
			String btnCancelCheckspec = req.getParameter("btnCancelCheckspec");
			if (btnCancelCheck != null) {
				Check check = (Check)session.getAttribute("check");
				if (check != null) {
					check.setCanceled(1);
					ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
					checkDAO.update(check);
					session.setAttribute("check", check);
				}
			} else if (btnCancelCheckspec != null) {
				Check check = (Check)session.getAttribute("check");
				if (check != null) {
					int checkspecnum = Integer.valueOf(req.getParameter("checkspecnum"));
					@SuppressWarnings("unchecked")
					List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
					if (checkspecs != null) {
						checkspecs.get(checkspecnum - 1).setCanceled(1);
						ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
						checkspecDAO.update(checkspecs.get(checkspecnum - 1));
						double total = checkspecs.stream()
								.filter(spec -> spec.getCanceled() == 0)
								.mapToDouble(o -> o.getTotal()).sum();
						check.setTotal(total);
						ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
						checkDAO.update(check);
						//req.setAttribute("checkspecs", checkspecs);						
					}
				}
			}
			String btnXReport = req.getParameter("btnXReport");
			String btnZReport = req.getParameter("btnZReport");
			if (btnXReport != null) {
				ReportGenerator.printXReport();
			} else if (btnZReport != null) {
				ReportGenerator.printZReport();
			}
			req.getRequestDispatcher("/WEB-INF/view/cancel.jsp").forward(req, resp);
		}
	}
}

