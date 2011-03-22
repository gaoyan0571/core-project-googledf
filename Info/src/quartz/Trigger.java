package quartz;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Trigger  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993535025824325091L;
	
	private Logger  logger =Logger.getLogger(Trigger.class);
	public void runTask2() throws SchedulerException, ParseException
	{
		SchedulerFactory sf = new StdSchedulerFactory();
		
		
        Scheduler sched = sf.getScheduler();
        
        JobDetail job = new JobDetail("job1", "group1", Task2.class);
        CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1",
                "group1", "0/20 * * * * ?");
        sched.addJob(job, true);
        
        Date ft = sched.scheduleJob(trigger);
        logger.info(job.getFullName() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());
        
        sched.start();
      
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			this.runTask2();
		} catch (SchedulerException e) {
			logger.error("初始化定时器出错",e);
		} catch (ParseException e) {
			logger.error("初始化定时器出错",e);
		}
	}
	
	
	
}
