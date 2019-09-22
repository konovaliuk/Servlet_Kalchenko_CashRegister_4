package service;

import java.net.URI;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;


import dao.DAOManager;
import net.sf.jasperreports.engine.*;

/**
 * Класс печати отчетов в формате html с использованием JasperReport
 * @author SergeyK
 */
public class ReportGenerator {	
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

	/**
	 * Печать X-отчета
	 */
	public static void printXReport() {
		print("xReport.jrxml");		
	}	
	
	private static void print(String nameReport) {
		Map<String, Object> prepPars = new HashMap<>();

		  try {
			  System.out.println("Start printing..");
			  URI jrxmlFileName = new URI(Thread.currentThread().getContextClassLoader().getResource(nameReport).getFile());
			  String htmlFile = System.getProperty("user.home") + System.getProperty("file.separator") + formatter.format(Calendar.getInstance().getTime()) + ".html";
	
			  JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFileName.getPath());
			  Connection conn = DAOManager.getConnection();
			  JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperReport, prepPars, conn);
			  conn.close();
	
			  JasperExportManager.exportReportToHtmlFile(jprint, htmlFile);
			  System.out.println("Done exporting reports to htmlFile " + htmlFile);	
		  } catch (Exception e) {
			  System.out.print("Exceptiion" + e);
		  }
	}
}
