package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * @author 陈俊昊
 * @version 创建时间：2010-10-13 下午02:07:48
 * 类说明
 */
public class DBToolkit {



	private static Logger logger = Logger.getLogger(DBToolkit.class);
	
	
	/**
	 * 得到conn
	 * @param dbID
	 * @return
	 */
	public static Connection getConn(String dbID) {
		Connection conn =null;
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			 conn=DriverManager.getConnection(dbID);
		} catch (ClassNotFoundException e) {
			logger.error("proxool 连接方式出错",e);
			return null;
		} catch (SQLException e) {
			logger.error("proxool 连接方式出错",e);
			return null;
		}
		return conn;
	}
	
	
	
	
}
