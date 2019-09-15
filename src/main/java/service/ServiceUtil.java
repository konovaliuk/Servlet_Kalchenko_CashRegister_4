package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.DAOManager;
import entity.Report;
import entity.Report.Detail;

/**
 * Класс формиварония отчета для вывода на печать
 * @author SergeyK
 */
public class ServiceUtil {
	
	private static String QueryXReport = "	SELECT current_timestamp() AS printtime," +
			"	cancel.countcanceled," + 
			"	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," + 
			"	s.nds," +
			"	SUM(s.total) AS total," + 
			"	SUM(s.ndstotal) AS ndstotal" + 
			"	FROM checkspec s" + 
			"	INNER JOIN cashreg.check c ON c.id = s.id_check" +
			"	LEFT JOIN (SELECT COUNT(c1.canceled) AS countcanceled FROM cashreg.check c1 " + 
			"			 		WHERE c1.canceled = 1 /*AND cast(c1.crtime as date) = current_date()*/) cancel ON true" +
			"	WHERE c.canceled = 0 AND s.canceled = 0 /*AND cast(c.crtime as date) = current_date()*/" + 	//закоментировано для debug-а
			"	GROUP BY s.nds, cancel.countcanceled";
	
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
				detail.add(rep.new Detail(rs.getInt("nds"),
						rs.getDouble("ndstotal"), 
						rs.getDouble("total")));		
			}				
		} catch (SQLException e) {
			e.printStackTrace();				
		}
		return rep;
	}
}
