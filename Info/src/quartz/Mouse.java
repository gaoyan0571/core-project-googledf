package quartz;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import quartz.dao.JobTaskDao;
import quartz.vo.JobTaskVO;

/**
 * 入口类啊，业务系统如果需要启动的job引擎的话，
 * 只需要调用这个类的run接口就行了。
 * 
 * @author harison
 *
 */
public class Mouse {

	
	  public static void run() throws Exception{
		  
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail job = null;
		JobDataMap map = null;
		CronTrigger trigger = null;
		/**
		 * 把所有需要处理的启动的任务读出来噢。根据状态。
		 */
		
		JobTaskDao jobTaskDao =JobTaskDao.getInstance();
		Map<String,Object> whereMap =new HashMap<String,Object>();
		whereMap.put("STATE", "U");
		List<JobTaskVO>  jobTaskList =jobTaskDao.selectAllReturnObjectList(whereMap,JobTaskVO.class, 0, 1000);
		for(JobTaskVO vo :jobTaskList)
		{
			 map = new JobDataMap();   
			 map.put("TASK_ID",new Long(vo.getTaskId()));//这里把任务ID放到map里面，用于记录执行日志的时候使用。
			
			 /****************把JOB_TASK表的配置的变量传入JOB引擎***********************/
			if (StringUtils.isNotEmpty(vo.getParms())) {
				SAXReader saxReader = new SAXReader();
				Document document = saxReader
						.read(new ByteArrayInputStream(("<root>"
								+ vo.getParms() + "</root>").getBytes()));
				List l = document.selectNodes("/root/item");
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Element element = (Element) iter.next();
					Iterator iterator = element.elementIterator("key");
					String key = "";
					String value = "";
					while (iterator.hasNext()) {
						key = ((Element) iterator.next()).getTextTrim();
					}
					iterator = element.elementIterator("value");
					while (iterator.hasNext()) {
						value = ((Element) iterator.next()).getTextTrim();
					}
					map.put(key, value);
				}
			}
			/****************把JOB_TASK表的配置的变量传入JOB引擎***********************/
		      job = new JobDetail(vo.getTaskCode(),vo.getTaskType(), Class.forName(vo.getTaskImplClass()));
		      job.setJobDataMap(map);
		      trigger = new CronTrigger(vo.getTaskCode()+"trigger", vo.getTaskType()+"trigger", vo.getTaskCode(),
		        vo.getTaskType(),vo.getTaskExpress() );
		      sched.addJob(job, true);
		      sched.scheduleJob(trigger);
		}
		
		 //启动咯。开始
	    sched.start();
	  }
	  
	  public static void main(String args[]) throws Exception{
		    Mouse.run();
	  }
}
