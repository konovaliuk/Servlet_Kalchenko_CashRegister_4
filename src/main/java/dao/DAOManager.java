package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DAOManager {

	private static DAOManager dao;
	private static DataSource ds;

	private DAOManager() throws Exception {
		try {
			System.out.println(System.getProperty(Context.PROVIDER_URL));
			InitialContext ctx = new InitialContext();
			if (ctx == null) {
				throw new Exception("No context!");
			}
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/cashreg");
			if (ds == null) {
				throw new Exception("Data sourse not found!");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}	
	
	public static DAOManager getInstance() throws Exception {
		if (dao == null) {
			dao = new DAOManager();
		}
		return dao;		
	}
	
	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}