package quartz.dao;

import org.apache.log4j.Logger;

import util.Constant;
import dao.BaseDAO;

public class JobTaskDao extends BaseDAO {

	
	public JobTaskDao()
	{
		this.table="job_task";
		this.project=Constant.Project_name;
		this.db_master_id=Constant.Db_master_id;
		this.db_slave_id=Constant.DB_slave_id;
	}
	
	private static Logger logger = Logger.getLogger(JobTaskDao.class);
	private static JobTaskDao instance;

	public synchronized static JobTaskDao getInstance() {
		if (null == instance) {
			instance = new JobTaskDao();
		}
		return instance;
	}
}
