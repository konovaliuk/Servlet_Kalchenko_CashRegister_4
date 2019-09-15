package command;

import java.util.List;
import javax.servlet.http.*;
import dao.*;
import entity.*;
import service.ReportGenerator;
import service.ServiceUtil;

/**
 * Command отмены чека / спецификации чека,
 * печати X- отчета, Z-отчета 
 * @author SergeyK
 */
public class CancelCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String url = "/";
		HttpSession session = req.getSession();
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
			url = "cancel";
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
			url = "cancel";
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
				}
			}
			url = "cancel";
		}
		String btnXReport = req.getParameter("btnXReport");
		String btnZReport = req.getParameter("btnZReport");
		if (btnXReport != null) {			
			Report xReport = ServiceUtil.getDataReport();
			session.setAttribute("xReport", xReport);
			ReportGenerator.printXReport();
			url = "report";
		} else if (btnZReport != null) {
			//session.setAttribute("zReport", zReport);
			url = "report";
		}
		return url;		
	}
}
