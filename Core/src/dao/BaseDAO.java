package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import util.SqlUtil;
import core.DBToolkit;
import core.OperDBBase;

/**
 * @author 陈俊昊
 * @version 创建时间：2010-10-13 下午02:53:01
 * 类说明 
 * 增加，删除，修改，查询（事务与非事务)
 * 查询排序(未完成)
 *
 */
public class BaseDAO extends OperDBBase{

	protected String table;
	
	protected String project;
	
	protected Logger logger =null;
	
	protected String sql;
	
	protected String db_master_id;
	
	protected String db_slave_id;
	
	public BaseDAO() {
		this.logger=Logger.getLogger(this.getClass());
	}
	
	/**
	 * 插入要传入connection 用于事务
	 * @param conn
	 * @param sql
	 * @return
	 */
	public boolean insert(Connection conn, String sql)
	{
		boolean flag=false;
		int result =0;
		try {
			result =super.update(conn, sql);
			if(result==1)
			{
				flag =true;
			}
			logger.debug("result code ="+result);
		} catch (SQLException e) {
			logger.error("["+this.project+" ] insert fail  and result code ="+result+" & sql=" +sql+" \n",e);
		}
		return flag;
	}
	
	/**
	 * 插入要传入connection 用于事务,最后要关闭 conn
	 * @param conn
	 * @param map
	 * @return
	 */
	public boolean insert(Connection conn,Map<String,Object> map)
	{
	    sql = SqlUtil.makeInsertSql(this.table, map);
		return this.insert(conn, sql);
	}
	
	
	
	/**
	 * 单一插入,不用事务
	 * @param sql
	 * @return
	 */
	public boolean insert(String sql)
	{
		Connection conn =DBToolkit.getConn(this.db_master_id);
		boolean flag =this.insert(conn, sql);
		DbUtils.closeQuietly(conn);	
		return flag;
	}
	
	
	
	/**
	 * 单一插入,不用事务
	 * @param sql
	 * @return
	 */
	public boolean insert(Map<String,Object> map)
	{
	    sql = SqlUtil.makeInsertSql(this.table, map);
		return this.insert(sql);
	}
	
	
	/**
	 * 更新数据,用于事务,最后要关闭 conn
	 * @param conn
	 * @param sql
	 * @return
	 */
	public boolean edit(Connection conn,String sql)
	{
		boolean flag =false;
		int result =0;
		try {
		    result =super.update(conn, sql);
			if(result==1)
			{
				flag =true;
			}
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] update  fail & result code = "+result+" & sql =" +sql+" \n",e);
		}
		return flag;	
	}
	
	/**
	 * 更新数据,用于事务,最后要关闭 conn
	 * @param conn
	 * @param where
	 * @param set
	 * @return
	 */
	public boolean edit(Connection conn,Map<String,Object> where,Map<String,Object> set)
	{
		 sql = SqlUtil.makeUpdateSql(this.table, set, where);
		 return this.edit(conn, sql);
	}
	
	/**
	 * 更新数据,不用事务
	 * @param sql
	 * @return
	 */
	public boolean edit(String sql)
	{
		Connection conn =DBToolkit.getConn(this.db_master_id);
		boolean flag =this.edit(conn, sql);
		DbUtils.closeQuietly(conn);
		return flag;
	}
	
	/**
	 * 更新数据,用于事务,最后要关闭 conn
	 * @param conn
	 * @param where
	 * @param set
	 * @return
	 */
	public boolean edit(Map<String,Object> where,Map<String,Object> set)
	{
		 sql = SqlUtil.makeUpdateSql(this.table, set, where);
		 Connection conn =DBToolkit.getConn(this.db_master_id);
		 return this.edit(conn, sql);
	}
	
	
	/**
	 * 得到列表 ,用于事务,最后要关闭 conn
	 * @param conn
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> selectAll(Connection conn,String sql)
	{
		List<Map<String,Object>>  list =null;
		try {
			list =super.getList(conn, sql);
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] queryList  fail & " +sql+" \n",e);
		}
		return list;
	}
	
	/**
	 * 得到列表 
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> selectAll(String sql)
	{
		Connection conn =DBToolkit.getConn(this.db_slave_id);
		List<Map<String,Object>>  list =null;
		try {
			list =super.getList(conn, sql);
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] queryList  fail & " +sql+" \n",e);
		}
		return list;
	}
	
	
	/**
	 * 得到列表,用于事务,
	 * @param conn
	 * @param whereMap
	 * 当其为null时
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAll(Connection conn,Map<String,Object> whereMap,int start,int len)
	{
		if (null != whereMap && whereMap.size() > 0) {
			sql = SqlUtil.makeSelectAllSql(this.table, whereMap) + " limit "
					+ start + "," + len;
		} else {
			sql = "select * from " + this.table + " limit " + start + "," + len;
		}
		
		return this.selectAll(conn, sql);
	}
	
	/**
	 * 得到列表,用于事务
	 * @param conn
	 * @param whereMap
	 * @param orderBy
	 *  可以直接在字段后加入 desc 
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAll(Connection conn,Map<String,Object> whereMap,String orderBy,int start,int len)
	{
		if(null==orderBy)
		{
			orderBy="";
		}
		
		if (null != whereMap && whereMap.size() > 0) {
			sql = SqlUtil.makeSelectAllSql(this.table, whereMap) + " order by "+orderBy+" limit "
					+ start + "," + len;
		} else {
			sql = "select * from " + this.table +" order by "+orderBy+ " limit " + start + "," + len;
		}
		
		return this.selectAll(conn, sql);
	}
	
	
	/**
	 * 查询列表.
	 * @param whereMap
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAll(Map<String,Object> whereMap,int start,int len)
	{
		 Connection conn =DBToolkit.getConn(this.db_slave_id);
		 List<Map<String,Object>> lMap =this.selectAll(conn,whereMap, start, len);
		 DbUtils.closeQuietly(conn);
		 return lMap;
	}
	
	/**
	 *  查询列表.有排序
	 * @param whereMap
	 * @param orderBy
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAll(Map<String,Object> whereMap,String orderBy,int start,int len)
	{
		 Connection conn =DBToolkit.getConn(this.db_slave_id);
		 List<Map<String,Object>> lMap =this.selectAll(conn,whereMap,orderBy, start, len);
		 DbUtils.closeQuietly(conn);
		 return lMap;
	}
	
	
	
	
	/**
	 * 得到单条记录
	 * @param sql
	 * @return
	 */
	public Map<String,Object> getOne(String sql)
	{
		 Connection conn =DBToolkit.getConn(this.db_slave_id);
		 List<Map<String,Object>> lMap= this.selectAll(conn, sql);
		 DbUtils.closeQuietly(conn);
		 if(null!=lMap&&lMap.size()>0)
		 {
			 return lMap.get(0);
		 }
		 return null;
	}
	
	/**
	 * 得到单条记录
	 * @param whereMap
	 * @return
	 */
	public Map<String,Object> getOne(Map<String,Object> whereMap)
	{
		 Connection conn =DBToolkit.getConn(this.db_slave_id);
		 List<Map<String,Object>> lMap =this.selectAll(conn,whereMap, 0, 1);
		 DbUtils.closeQuietly(conn);
		 if(null!=lMap&&lMap.size()>0)
		 {
			 return lMap.get(0);
		 }
		 return null;
	}
	
	/**
	 * 得到单条记录,用于事务,要关闭connection 
	 * @param whereMap
	 * @return
	 */
	public Map<String,Object> getOne(Connection conn,Map<String,Object> whereMap)
	{
		 List<Map<String,Object>> lMap =this.selectAll(conn,whereMap, 0, 1);
		 DbUtils.closeQuietly(conn);
		 if(null!=lMap&&lMap.size()>0)
		 {
			 return lMap.get(0);
		 }
		 return null;
	}
	
	/**
	 * 得到单条记录,用于事务,要关闭connection ,有排序
	 * @param conn
	 * @param whereMap
	 * @param orderBy
	 * 直接在要排序的字段后加入 asc /desc 如 
	 *  select * from test order by { time desc } limit 1;
	 * @return
	 */
	public Map<String,Object> getOne(Connection conn,Map<String,Object> whereMap,String orderBy)
	{
		 List<Map<String,Object>> lMap =this.selectAll(conn,whereMap,orderBy, 0, 1);
		 DbUtils.closeQuietly(conn);
		 if(null!=lMap&&lMap.size()>0)
		 {
			 return lMap.get(0);
		 }
		 return null;
	}
	
	
	
	
	/**
	 * 更新数据,用于事务,最后要关闭 conn
	 * @param conn
	 * @param sql
	 * @return
	 */
	public boolean del(Connection conn,String sql)
	{
		boolean flag =false;
		int result =0;
		try {
		    result =super.update(conn, sql);
			if(result==1)
			{
				flag =true;
			}
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] del  fail & result code = "+result+" & sql =" +sql+" \n",e);
		}
		return flag;	
	}
	
	/**
	 * 删除数据,用于事务,最后要关闭 conn
	 * @param conn
	 * @param where
	 * @param set
	 * @return
	 */
	public boolean del(Connection conn,Map<String,Object> where)
	{
		 sql = SqlUtil.makeDeleteSql(this.table, where);
		 return this.del(conn, sql);
	}
	
	/**
	 * 删除数据,不用事务
	 * @param sql
	 * @return
	 */
	public boolean del(String sql)
	{
		Connection conn =DBToolkit.getConn(this.db_master_id);
		boolean flag =this.del(conn, sql);
		DbUtils.closeQuietly(conn);
		return flag;
	}
	
	
	
	/**
	 * 换Vo中的Bean返回结果
	 * @param conn
	 * @param sql
	 * @param voClazz
	 * @return
	 */
	public List selectAllReturnBeans(Connection conn,String sql,Class voClazz)
	{
		List list =null;
		try {
			list =super.getBeanList(conn, sql, voClazz);
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] queryList  fail & " +sql+" \n",e);
		}
		return list;
	}
	
	
	/**
	 * 换Vo中的Bean返回结果,不用事务
	 * @param conn
	 * @param sql
	 * @param voClazz
	 * @return
	 */
	public List selectAllReturnBeans(String sql,Class voClazz)
	{
		Connection conn =DBToolkit.getConn(this.db_slave_id);
		List list =null;
		try {
			list =super.getBeanList(conn, sql, voClazz);
		} catch (SQLException e) {
			logger.error("[  "+this.project+" ] queryList  fail & " +sql+" \n",e);
		}
		DbUtils.closeQuietly(conn);
		return list;
	}
	
	/**
	 * 得到列表,用于事务,
	 * @param conn
	 * @param whereMap
	 * 当其为null时
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAllReturnBeans(Connection conn,Map<String,Object> whereMap,Class voClazz,int start,int len)
	{
		if (null != whereMap && whereMap.size() > 0) {
			sql = SqlUtil.makeSelectAllSql(this.table, whereMap) + " limit "
					+ start + "," + len;
		} else {
			sql = "select * from " + this.table + " limit " + start + "," + len;
		}
		return this.selectAllReturnBeans(conn, sql, voClazz);
	}
	
	
	/**
	 * 得到列表,用VO对象返回.
	 * @param whereMap
	 * @param voClazz
	 * @param start
	 * @param len
	 * @return
	 */
	public List<Map<String,Object>> selectAllReturnBeans(Map<String,Object> whereMap,Class voClazz,int start,int len)
	{
		
		
		if (null != whereMap && whereMap.size() > 0) {
			sql = SqlUtil.makeSelectAllSql(this.table, whereMap) + " limit "
					+ start + "," + len;
		} else {
			sql = "select * from " + this.table + " limit " + start + "," + len;
		}
		return this.selectAllReturnBeans(sql, voClazz);
	}
	

	/**
	 * 得到列表,用VO对象返回.
	 * @param whereMap
	 * @param voClazz
	 * @param start
	 * @param len
	 * @return
	 */
	public List selectAllReturnObjectList(Map<String,Object> whereMap,Class voClazz,int start,int len)
	{
		
		if (null != whereMap && whereMap.size() > 0) {
			sql = SqlUtil.makeSelectAllSql(this.table, whereMap) + " limit "
					+ start + "," + len;
		} else {
			sql = "select * from " + this.table + " limit " + start + "," + len;
		}
		return this.selectAllReturnBeans(sql, voClazz);
	}
	
	/**
	 * 执行select count语句
	 * 
	 * @param whereMap
	 * @return
	 */
	public int getCount(Map<String, Object> whereMap) {

		int ws = (null != whereMap) ? whereMap.size() : 0;
		StringBuilder sql = new StringBuilder(64 + ws * 32);
		sql.append("\n select count(*)  from ").append(this.table);
		if (ws != 0) {
			sql.append("\n where ");// 拼接where子句
			
			int index = 0;
			for (String key : whereMap.keySet()) {
				Object v = whereMap.get(key);
				sql.append(key).append("=").append(SqlUtil.sqlValue(v));
				index++;
				if (index < ws) {
					sql.append("\n   and ");
				}
			}
		}
		Connection conn =DBToolkit.getConn(this.db_slave_id);
		try {
			return super.count(conn, sql.toString());
		} catch (SQLException e) {
			logger.error(this.project+"  getCount  fail & " +sql+" \n",e);
			return -1;
		}
	}

	

	
}
