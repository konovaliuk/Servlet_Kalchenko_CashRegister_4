package service;

import java.net.URI;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;


import dao.DAOManager;
import net.sf.jasperreports.engine.*;

/**
 * @author SergeyK
 */
public class ReportGenerator {	
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	private static void print(String nameReport) {
		Map<String, Object> prepPars = new HashMap<>();

		  try {
		   System.out.println("Start printing..");		   
		   URI jrxmlFileName = new URI(Thread.currentThread().getContextClassLoader().getResource(nameReport).getFile());
		   System.out.println(jrxmlFileName);
		   System.out.println(new URI(Thread.currentThread().getContextClassLoader().getResource("").getFile()));
		   //String pdfFile = "D:\\xReport" + formatter.format(Calendar.getInstance().getTime()) + ".pdf";
		   String htmlFile = "D:\\xReport" + formatter.format(Calendar.getInstance().getTime()) + ".html";

		   JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFileName.getPath());
		   Connection conn = DAOManager.getConnection();
		   JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperReport, prepPars, conn);
		   conn.close();

		   //ByteArrayOutputStream out = new ByteArrayOutputStream();		   
		   //JasperExportManager.exportReportToPdfStream(jprint, out);
		   //JasperExportManager.exportReportToPdfFile(jprint, pdfFile);
		   JasperExportManager.exportReportToHtmlFile(jprint, htmlFile);
		   //System.out.println("Done exporting reports to pdf " + pdfFile);
		   System.out.println("Done exporting reports to htmlFile " + htmlFile);	
		  } catch (Exception e) {
		   System.out.print("Exceptiion" + e);
		  }
	}

	/**
	 * 
	 */
	public static void printXReport() {
		print("xReport.jrxml");		
	}
	
	public static void printZReport() {
		print("zReport.jrxml");		
	}
}
