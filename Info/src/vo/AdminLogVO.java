package vo;
/**
 * @author 陈俊昊
 * @version 创建时间：2010-10-12 上午10:31:35
 * 类说明
 */
public class AdminLogVO {


	private Integer id;
	private Integer logType;
	private Integer adminId;
	private String adminUsername;
	private String logDesc;
	private String logTime;
	
	private String admin_username;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getLogType() {
		return logType;
	}


	public void setLogType(Integer logType) {
		this.logType = logType;
	}


	public Integer getAdminId() {
		return adminId;
	}


	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	public String getAdminUsername() {
		return adminUsername;
	}


	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}


	public String getLogDesc() {
		return logDesc;
	}


	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}


	public String getLogTime() {
		return logTime;
	}


	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	

	public String getAdmin_username() {
		return admin_username;
	}


	public void setAdmin_username(String adminUsername) {
		admin_username = adminUsername;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s=" admin_username ="+this.getAdminUsername()+" admin_id ="+this.getAdminId()
		+" logDesc ="+this.getLogDesc()+" logTime="+this.getLogType()+" logType="+this.getLogType()+" id ="+this.getId()+" admin_username="+this.getAdmin_username();
		return s;
	}

}
