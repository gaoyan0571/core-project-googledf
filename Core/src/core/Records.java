package core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class Records {
	
	/**
	 * 将 ResultSet 中的记录转换成 Record 的列表
	 * 
	 * @param rs	保存记录的 ResultSet 对象
	 * @return	List<Record> 类型表示的记录列表
	 * @throws Exception	对 ResultSet 的操作可能产生 SQLException
	 */
	public static List<Record> toRecordList(ResultSet rs) throws Exception {
		
		List<Record> recordList = new ArrayList<Record>();
		
		if (rs == null) {
			return recordList;
		}
		
		// 得到 ResutlSet 对象的元数据对象
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		// 得到记录中所有属性的名称
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = metaData.getColumnName(i);
		}
		
		// 将 ResultSet 对象中的每条记录转换成 Record 对象，并添加到 List 对象中
		while (rs.next()) {
			Record record = new Record();
			for (int i = 0; i < columnCount; i++) {
				record.set(columnNames[i], rs.getObject(columnNames[i]));
			}
			recordList.add(record);
		}
		
		return recordList;
	}

	/**
	 * 将 ResultSet 中的记录转换成 Record 的数组
	 * 
	 * @param rs	保存记录的 ResultSet 对象
	 * @return	保存记录内容的 Record 数组
	 * @throws Exception	对 ResultSet 的操作可能产生 SQLException
	 */
	public static Record[] toRecords(ResultSet rs) throws Exception {
		
		// 调用 toRecordList 得到 ResultSet 转换的 List 
		List<Record> recordList = toRecordList(rs);
		Record[] records = new Record[recordList.size()];
		
		// 使用返回值保证转换正确（当作为参数的 Record 数组长度小于 List 长度时，方法会重新创建 Record 数组）
		records = recordList.toArray(records);
		return records;
	}
	
	public static void main(String[] args) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = new Record();
		record.set("a", 1);
		record.set("b", "123");
		Record record2 = new Record();
		record2.set("a", 3);
		record2.set("b", "44343");
		recordList.add(record);
		recordList.add(record2);
		
	}
}
