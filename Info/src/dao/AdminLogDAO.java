package dao;

import java.util.HashMap;
import java.util.Map;

import util.DateUtil;
import vo.AdminLogVO;

public class AdminLogDAO extends BaseDAO {

	
	public AdminLogDAO() {
		this.table="admin_log";
		this.project="≤‚ ‘";
	}
	
	
	public boolean save(AdminLogVO vo)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("log_type", 1);
		map.put("log_time",DateUtil.getCurrent());
		map.put("admin_id","333");
		map.put("admin_username","admin_1");
		map.put("log_desc", "teset");
		map.put("oper_ip", "127.0.0.1");
		return super.insert(map);
	}
	
	
	
	
}
