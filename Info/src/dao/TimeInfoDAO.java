package dao;

import util.Constant;

public class TimeInfoDAO extends BaseDAO {

	private static TimeInfoDAO instance;

	public synchronized static TimeInfoDAO getInstance() {
		if (null == instance) {
			instance = new TimeInfoDAO();
		}
		return instance;
	}
	
	public TimeInfoDAO() {
		this.table="time_info";
		this.db_master_id=Constant.Db_master_id;
		this.db_slave_id=Constant.DB_slave_id;
		this.project=Constant.Project_name;
	}
}
