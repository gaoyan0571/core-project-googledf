package dao;

import java.util.HashMap;
import java.util.Map;

import util.Constant;
import util.DateUtil;
import vo.InfoVO;

public class InfoDAO extends BaseDAO {

	private static InfoDAO instance;

	public synchronized static InfoDAO getInstance() {
		if (null == instance) {
			instance = new InfoDAO();
		}
		return instance;
	}
	
	public InfoDAO() {
		this.table="info";
		this.project=Constant.Project_name;
		this.db_master_id=Constant.Db_master_id;
		this.db_slave_id=Constant.DB_slave_id;
	}
	

	
	public boolean save(InfoVO vo)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",System.currentTimeMillis());
		map.put("type", vo.getType());
		map.put("title",vo.getTitle());
		map.put("source_url",vo.getSource_url());
		map.put("source",vo.getSource());
		map.put("content", vo.getContent());
		map.put("add_time",DateUtil.getCurrent());
		return super.insert(map);
	}
	
	public void dump()
	{
		String sql ="select column_name,column_comment,column_type,column_default from information_schema.columns where table_name='"+this.table+"'";
		
		Map<String,Object> obj=super.getOne(sql);
		
		
	}
	
	
	
	
	
}
