package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.DateUtil;

public class Task2 implements Job {

	
	public Task2()
	{
		
	}
	public Task2(String taskName)
	{
		
	}
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.print("------ context ----"+DateUtil.getCurrent());
		
	}

}
