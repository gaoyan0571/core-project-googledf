package core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * @author 陈俊昊
 * @version 创建时间：2010-10-13 上午11:46:20
 * 类说明
 */
public class OperDBBase {

	
	
	/**
	 * select count
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int count(Connection conn, String sql) throws SQLException {
		QueryRunner qr = new QueryRunner();
	    Long count = (Long) qr.query(conn, sql, new ScalarHandler(1));
		return count.intValue();
	}
	
	
	/**
	 * 返回List Map
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getList(Connection conn,String sql) throws SQLException
	{
		QueryRunner qr = new QueryRunner();
		List<Map<String,Object>> lMap = (List<Map<String,Object>>) qr.query(conn,sql, new MapListHandler());
		return lMap;
	}
	
	
	/**
	 * 添加数据
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int add(Connection conn,String sql) throws SQLException
	{
		return this.update(conn, sql);
	}
	
	
	/**
	 * 更新信息
	 */
	public int update(Connection conn,String sql) throws SQLException
	{
		  QueryRunner qr = new QueryRunner();
		  int result =  qr.update(conn,sql);
		  return result;
	}
	
	
	/**
	 * 返回 list Bean  
	 * @param conn
	 * @param sql
	 * @param clazz
	 * VO 中的类的属性要写数据库中的字段相同才能用;
	 * @return
	 * @throws SQLException
	 */
	public List getBeanList(Connection conn,String sql ,Class clazz) throws SQLException
	{
		  QueryRunner qr = new QueryRunner();
		  
		  List lBeans = (List) qr.query(conn,sql, new BeanListHandler(clazz));
		  return lBeans;
	}
	
	
	
	
	
}
