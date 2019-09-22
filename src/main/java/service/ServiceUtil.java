package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import dao.DAOFactory;
import dao.DAOManager;
import entity.Fiscal;
import service.Report;
import service.Report.Detail;
import transaction.TransactionException;
import transaction.TransactionHandler;

/**
 * Класс формиварония X-отчета, Z-отчета для вывода на печать
 * @author SergeyK
 */
public class ServiceUtil {
	
	private static Logger logger = Logger.getLogger(ServiceUtil.class);
	
	private static String QueryXReport = "SELECT current_timestamp() AS printtime," +
			"	cancel.countcanceled," + 
			"	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," + 
			"	s.nds," +
			"	SUM(s.total) AS total," + 
			"	SUM(s.ndstotal) AS ndstotal, " +
			"	SUM(SUM(s.total)) OVER() AS sumtotal, " +  
			"	SUM(SUM(s.ndstotal)) OVER() AS sumndstotal " +
			"	FROM checkspec s" + 
			"	INNER JOIN cashreg.check c ON c.id = s.id_check" +
			"	LEFT JOIN (SELECT COUNT(c1.canceled) AS countcanceled FROM cashreg.check c1 " + 
			"			 		WHERE c1.canceled = 1 /*AND cast(c1.crtime as date) = current_date()*/) cancel ON true" +
			"	WHERE c.canceled = 0 AND s.canceled = 0 /*AND cast(c.crtime as date) = current_date()*/" + 	//закоментировано для debug-а
			"	GROUP BY s.nds, cancel.countcanceled";
	
	private static String QueryZReport = "SELECT current_timestamp() AS printtime," +
			"	(SELECT COUNT(c1.canceled) FROM cashreg.check c1 " + 
			"		WHERE c1.canceled = 1 /*AND cast(c1.crtime as date) = current_date()*/) AS countcanceled, " + 
			"	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," + 
			"	SUM(CASE WHEN s.nds = 20 THEN s.total ELSE 0 END) AS totalA," + 
			"	SUM(CASE WHEN s.nds = 20 THEN s.ndstotal ELSE 0 END) AS ndstotalA," +
			"	SUM(CASE WHEN s.nds = 7 THEN s.total ELSE 0 END) AS totalB," + 
			"	SUM(CASE WHEN s.nds = 7 THEN s.ndstotal ELSE 0 END) AS ndstotalB," +
			"	SUM(CASE WHEN s.nds = 0 THEN s.total ELSE 0 END) AS totalC," + 
			"	SUM(CASE WHEN s.nds = 0 THEN s.ndstotal ELSE 0 END) AS ndstotalC," +
			"	SUM(SUM(s.total)) OVER() AS sumtotal, " +  
			"	SUM(SUM(s.ndstotal)) OVER() AS sumndstotal " +
			"	FROM checkspec s" + 
			"	INNER JOIN cashreg.check c ON c.id = s.id_check" +
			"	WHERE c.canceled = 0 AND s.canceled = 0 /*AND cast(c.crtime as date) = current_date()*/" + 	//закоментировано для debug-а
			"		AND c.registration IS NULL";
			
	public static Report getDataReport() {
		Report rep = new Report();
		try (Connection connection = DAOManager.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(QueryXReport);
			
			List<Detail> detail = rep.getDetail();
			while (rs.next()) {				
				rep.setPrinttime(rs.getTimestamp("printtime"));
				rep.setCountCheck(rs.getInt("countcheck"));
				rep.setCountCancelCheck(rs.getInt("countcanceled"));
				rep.setSumTotal(rs.getDouble("sumtotal"));
				rep.setSumNdsTotal(rs.getDouble("sumndstotal"));
				detail.add(rep.new Detail(rs.getInt("nds"),
						rs.getDouble("ndstotal"), 
						rs.getDouble("total")));		
			}				
		} catch (SQLException e) {
			logger.error("Не удалось сформировать X-отчет", e);			
		}
		return rep;
	}
	
	/**
	 * Получить данные для Z-отчета
	 * @return Report данные для Z-отчета
	 * @throws TransactionException 
	 */
	public static Report getDataZReport() throws TransactionException {
		
		Report zReport = null;
		try (Connection connection = DAOManager.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(QueryZReport);
			while (rs.next()) {
				Fiscal fiscal = new Fiscal();
				fiscal.setTotal(rs.getDouble("sumtotal"));
				Long repNumber[] = new Long[1];
				TransactionHandler.execute(conn -> {
					DAOFactory.getCheckDAO().update(conn, "registration", 1, 
							"canceled = 0 AND cast(crtime as date) = current_date() AND registration IS NULL");
					repNumber[0] = DAOFactory.getFiscalDAO().insert(conn, fiscal);
				});
				//Long repNumber = DAOFactory.getFiscalDAO().insert(conn, fiscal);
				zReport = new Report().new Builder()
					.addNumber(repNumber[0])
		            .addPrinttime(rs.getTimestamp("printtime"))
		            .addCountCheck(rs.getInt("countcheck"))
		            .addCountCancelCheck(rs.getInt("countcanceled"))
		            .addSumTotal(rs.getDouble("sumtotal"))
		            .addSumNdsTotal(rs.getDouble("sumndstotal"))
		            .addTotalA(rs.getDouble("totalA"))
		            .addTotalB(rs.getDouble("totalB"))
		            .addTotalC(rs.getDouble("totalC"))
		            .addNdsTotalA(rs.getDouble("ndstotalA"))
		            .addNdsTotalB(rs.getDouble("ndstotalB"))
		            .addNdsTotalC(rs.getDouble("ndstotalC"))
		            .build();
			}
		} catch (SQLException e) {
			logger.error("Не удалось сформировать Z-отчет", e);				
		}
		return zReport;
	}
}
