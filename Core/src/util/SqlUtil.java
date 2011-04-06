package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * SQL 工具类
 * @author 
 * @teme 2010-6-1 下午12:08:49
 */
public final class SqlUtil {
	
	/**
	 * date format
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 设置date format
	 * @param format -- date format
	 */
	public static void setDateFormat(String format) { 
		sdf =  new SimpleDateFormat(format); 
	}
	
	/**
	 * 拼接 select * SQL语句
	 * @param tableName -- db 表名称
	 * @param where -- where 子句map
	 * @return -- SQL语句
	 */
	public static String makeSelectAllSql(String tableName,  Map<String, Object> where) {

		int ws = (null != where) ? where.size() : 0;
		StringBuilder sql = new StringBuilder(64 + ws * 32);
		sql.append("\n select * from ").append(tableName);
		sql.append("\n where ");// 拼接where子句
		int index = 0;
		for (String key : where.keySet()) {
			Object v = where.get(key);
			sql.append(key).append("=").append(sqlValue(v));
			index++;
			if (index < ws) {
				sql.append("\n   and ");
			}
		}		
		return sql.toString();
	}

	/**
	 * 拼接 insert SQL语句
	 * @param tableName -- db 表名称
	 * @param columns -- 列集合map
	 * @return -- SQL语句
	 */
	public static String makeInsertSql(String tableName, Map<String, Object> columns) {
		
		int columnSize = columns.size();
		StringBuilder sql = new StringBuilder(64 + columnSize * 32);
		sql.append("\n insert into ").append(tableName);
		sql.append(" ( ");
		int index = 0;
		for (String item : columns.keySet()) {
			sql.append(item);
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" )\n");
		sql.append(" values ( ");
		index = 0;
		for (String item : columns.keySet()) {
			Object value = columns.get(item);
			sql.append(sqlValue(value));
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" )");
		return sql.toString();
	}
	
	/**
	 * 拼接update SQL语句
	 * @param tableName -- db表名称
	 * @param set -- update子句map
	 * @param where -- where 子句map
	 * @return -- SQL语句
	 */
	public static String makeUpdateSql(String tableName, Map<String, Object> set, Map<String, Object> where) {
		
		if (null == set) {
			throw new IllegalArgumentException("update的字段集合不能为null");
		}
		int ss = set.size();
		int ws = (null != where) ? where.size() : 0;
		StringBuilder sql = new StringBuilder(64 + ss * 32 + ws * 32);
		// 拼接set子句
		sql.append("\n update ").append(tableName).append("\n set ");
		int index = 0;
		for (String key : set.keySet()) {
			Object v = set.get(key);
			sql.append("\t").append(key).append("=").append(sqlValue(v));
			index++;
			if (index < ss) {
				sql.append(",\n");
			}
		}
		// 没有where子句
		if (ws == 0) {
			return sql.toString();
		}
		// 拼接where子句
		sql.append("\n where ");
		index = 0;
		for (String key : where.keySet()) {
			Object v = where.get(key);
			sql.append(key).append("=").append(sqlValue(v));
			index++;
			if (index < ws) {
				sql.append("\n   and ");
			}
		}		
		return sql.toString();
	}

	/**
	 * 拼接delete SQL语句
	 * @param tableName -- db表名称
	 * @param where -- where子句map
	 * @return -- SQL语句
	 */
	public static String makeDeleteSql(String tableName, Map<String, Object> where) {
		
		int ws = (null != where) ? where.size() : 0;
		StringBuilder sql = new StringBuilder(64 + ws * 32);
		sql.append("\n delete from ").append(tableName);
		if (0 == ws) {
			return sql.toString();
		}
		sql.append("\n where ");
		int index = 0;
		for (String key : where.keySet()) {
			Object v = where.get(key);
			sql.append(key).append("=").append(sqlValue(v));
			index++;
			if (index < ws) {
				sql.append("\n   and ");
			}
		}		
		return sql.toString();
	}

	/**
	 * 拼接 动态SQL
	 * @param dynamicSql
	 * @param params
	 * @return
	 * @see makeDynamicSql
	 */
	@SuppressWarnings("unchecked")
	public static String makeDynamicSql(String dynamicSql, Object params) {
		
		Map<String, Object> b = null;
		if (params instanceof Map) {
			b = (Map<String, Object>)params;
		} else {
			try {
				b = BeanUtils.describe(params);
			} catch (Exception ex) { 
				throw new IllegalArgumentException("BeanUtils.describe(bean)异常", ex);
			}
		}
		return makeDynamicSql(dynamicSql, b);
	}
	
	/**
	 * 拼接 动态SQL
	 * <pre>
	 *    String dynamicSql = "select * from article where {id = #id } {and val = $val } {vas in($vas )} {and creation > $createTime}";
	 *    <1>使用map作为参数
	 *    Map<String, Object> params = new HashMap<String, Object>();
	 *    params.put("id", 20060601);
	 *    params.put("val", "demo_value");
	 *    String sql = makeDynamicSql(dynamicSql, params);
	 *    // sql --> "select * from article where id = 20060601 and val = 'demo_value'"
	 *    
	 *    
	 *     <2>使用java bean作为参数
	 *     public static class MyParamBean {	 *  		
	 *  		private String id = null;
	 *  		private String val = null;	 *  		
	 *  		public String getId() {
	 *  			return id;
	 *  		}
	 *  		public void setId(String id) {
	 *  			this.id = id;
	 *  		}
	 *  		public String getVal() {
	 *  			return val;
	 *  		}
	 *  		public void setVal(String val) {
	 *  			this.val = val;
	 *  		}
	 *  	}	 *  
	 *     MyParamBean param = new MyParamBean();
     *		param.setId("demo_id");
	 *		param.setVal("demo_value");
	 *		sql = SqlUtil.makeDynamicSql(dynamicSql, param.setVal);
	 *     //  sql --> "select * from article where id = 'demo_id' and val = 'demo_value'"
	 * </pre>
	 * @param dynamicSql -- 动态SQL语句
	 * @param bean -- 替换数据源
	 * @return -- SQL语句
	 */
	public static String makeDynamicSql(String dynamicSql, Map<String, Object> params) {
		 		
		int ps = dynamicSql.length(); 
		StringBuilder sql = new StringBuilder(128 + ps * 2);
		StringBuilder item = new StringBuilder(128);
		
		boolean isDynamicStart = false;
		char c = 0;
		for (int i = 0; i < ps; i++) {
			c = dynamicSql.charAt(i);
			if (isDynamicStart) {
				if ('}' == c) {
					isDynamicStart = false;
					String ii = item.toString();
					item.setLength(0);
					sql.append(makeDynamicItem(ii, params));
				} else {
					item.append(c);
				}
			} else {
				if ('{' == c) {
					isDynamicStart = true;
				} else {
					sql.append(c);
				}
			}
		} 
		if (item.length() > 0) {
			String ii = item.toString();
			sql.append(makeDynamicItem(ii, params));
		}
		return sql.toString();
	}
	
	/**
	 * 拼接动态SQL中的一个动态项
	 * <br>备注: 动态SQL中 在一对'{'和'}'之间的字符串就是一个动态性
	 * @param dynamicItem -- 动态项字符串
	 * @param params -- 替换变量的参数
	 * @return -- SQL语句片段
	 */
	private static String makeDynamicItem(String dynamicItem, Map<String, Object> params) {
						
		int ps = dynamicItem.length();
		StringBuilder sqlItem = new StringBuilder(64 + ps * 2);
		StringBuilder param = new StringBuilder(64);
	
		boolean isParamStart = false;
		char c = 0;
		char flag = 0;
		for (int i = 0; i < ps; i++) {
			c = dynamicItem.charAt(i);
			if (isParamStart) {
				if (' ' == c || '\n' == c || '\t' == c || '\r' == c) {
					isParamStart = false;
					String p = param.toString();
					param.setLength(0);
					Object v = params.get(p);
					if ('$' == flag) {
						if (null == v) {
							return "";
						}
						sqlItem.append(sqlValue(v));
					}  else if ('#' == flag) {
						sqlItem.append(sqlValue(v));
					} else if ('&' == flag) {
						if (null == v) {
							return "";
						}
						sqlItem.append(v);
					}
				} else {
					param.append(c);
				}
			} else {
				if ('$' == c || '#' == c || '&' == c || '?' == c || '@' == c) {
					flag = c;
					isParamStart = true;
				} else {
					sqlItem.append(c);
				}
			}
		}
		if (param.length() > 0) {
			String p = param.toString();
			Object v = params.get(p);
			if ('$' == flag) {
				if (null == v) {
					return "";
				}
				sqlItem.append(sqlValue(v));
			} else if ('#' == flag) {
				sqlItem.append(sqlValue(v));
			}
		}
		return sqlItem.toString();
	}
	
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(int value) {		
		return Integer.toString(value);
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(long value) {		
		return Long.toString(value);
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(short value) {		
		return Short.toString(value);
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(float value) {		
		return Float.toString(value);
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(double value) {		
		return Double.toString(value);
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(String value) {		

		//return "'" + value.trim().replaceAll("\'", "\'\'").replace("\\", "\\\\") + "'"; // 防止sql注入
		//下面的代码等价于上面一行的代码, 
		//目的: 性能优化, 测试数据显示性能可以提高一,两个数量级(100w次调用时间只用上面代码的1/100 ~ 1/20)
		if (null == value) {
			return "''";
		}
		String v = value.trim();
		int vs = v.length();
		StringBuilder sb = new StringBuilder(2 + vs * 2);
		char c = 0;
		sb.append('\'');
		for (int i = 0; i < vs; i++) { // 防止sql注入
			c = v.charAt(i);
			if ('\'' == c) {
				sb.append('\'');
				sb.append('\'');
			} else if ('\\' == c) {
				sb.append('\\');
				sb.append('\\');
			} else {
				sb.append(c);
			}
		}
		sb.append('\'');
		return sb.toString();
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	@SuppressWarnings("unchecked")
	public static String sqlValue(Object value) {
		
		if (null == value) {
			return "''";
		} else if (value instanceof String) {
			String v = (String)value;
			return sqlValue(v);
		} else if (value instanceof Integer) {
			Integer v = (Integer)value;
			return  v.toString();
		} else if (value instanceof Date) {
			Date v = (Date)value;
			return  "'" + sdf.format(v) + "'";
		}  else if (value instanceof Timestamp) {
			Timestamp v = (Timestamp)value;
			return "'" + v + "'";
		}  else if (value instanceof List) {
			List v = (List)value;
			return sqlValue(v);
		}  else if (value.getClass().isArray()) { // 数组类型
			Class ct = value.getClass().getComponentType();
			if (ct == String.class) { // 是否是String数组
				String[] va = (String[])value;
				return sqlValue(va);
			} else if (ct.isPrimitive()) { // 是否是原始类型数组
				if (ct == int.class) {
					int[] va = (int[])value;
					return sqlValue(va);
				} else if (ct == long.class) {
					long[] va = (long[])value;
					return sqlValue(va);
				} else if (ct == short.class) {
					short[] va = (short[])value;
					return sqlValue(va);
				} else if (ct == float.class) {
					float[] va = (float[])value;
					return sqlValue(va);
				} else if (ct == double.class) {
					double[] va = (double[])value;
					return sqlValue(va);
				}
			}
			Object[] v = (Object[])value; // 默认,转成Object对象数组
			return sqlValue(v);
		} else if (value instanceof Long || value instanceof Short || value instanceof Float || value instanceof Double) { 
			return  value.toString();
		}  else { 
			return  "'" + value.toString() + "'";
		} 
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(Object[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	} 
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(String[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	} 
	
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(List<Object> value) {
		
		if (null == value) {
			return "''";
		}
		int size = value.size();
		StringBuilder sql = new StringBuilder(64 + size * 32);
		for (int i = 0; i < size; i++) {
			sql.append(sqlValue(value.get(i)));
			if(i < size - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	} 
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(int[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(short[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(long[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(float[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	}
 
	/**
	 * 拼接SQL语法的字段字符串值
	 * @param value -- 数据
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(double[] value) {
		
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if(i < value.length - 1) {
				sql.append(","); 
			}
		}
		return sql.toString();
	} 
	
	/**
	 * 防止非法实例化
	 */
	private SqlUtil() { }
}
