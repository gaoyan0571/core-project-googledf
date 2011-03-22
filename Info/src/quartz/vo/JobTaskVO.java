package quartz.vo;

import java.sql.Timestamp;

public class JobTaskVO {

	  private long taskId = -1;
	  private String taskCode = "";
	  private String taskType = "";
	  private String taskImplClass = "";
	  private String taskExpress = "";
	  private Timestamp stateDate = null;
	  private String state = "";
	  private String parms = "";
	  private String remark = "";
	  private Timestamp createDate = null;
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskImplClass() {
		return taskImplClass;
	}
	public void setTaskImplClass(String taskImplClass) {
		this.taskImplClass = taskImplClass;
	}
	public String getTaskExpress() {
		return taskExpress;
	}
	public void setTaskExpress(String taskExpress) {
		this.taskExpress = taskExpress;
	}
	public Timestamp getStateDate() {
		return stateDate;
	}
	public void setStateDate(Timestamp stateDate) {
		this.stateDate = stateDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getParms() {
		return parms;
	}
	public void setParms(String parms) {
		this.parms = parms;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	  
	  
}
