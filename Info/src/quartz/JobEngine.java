package quartz;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import quartz.dao.JobTaskDao;
import quartz.vo.JobTaskVO;

/**
 * 这只是一个简单的JOB而已，名字貌似很NB，这个任务用于实时的检测任务列表的状态，用于更新JOB。
 * 这样你改了个配置，可以立马生效的噢。
 * 这个类用于在JOB运行期间，使得修改立马生效。
 * @author harison
 *
 */
public class JobEngine implements Job {

	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		

		Scheduler inScheduler = arg0.getScheduler();
		JobDetail job = null;
		JobDataMap map = null;
		CronTrigger trigger = null;
		
		String sql ="SELECT * FROM JOB_TASK WHERE STATE='U' AND STATE_DATE>now()";
		Map<String,Object> whereMap =new HashMap<String,Object>();
		
		whereMap.put("STATE", "U");
		whereMap.put("STATE_DATE>","now()");
		
		JobTaskDao jobTaskDao =JobTaskDao.getInstance();
		List<JobTaskVO>  jobTaskList =jobTaskDao.selectAllReturnObjectList(whereMap,JobTaskVO.class, 0, 1000);
		
		
		for(JobTaskVO vo :jobTaskList)
		{
			Map<String,Object> whereMap2 =new HashMap<String,Object>();
			Map<String,Object> setMap2 =new HashMap<String,Object>();
			
			whereMap2.put("TASK_ID", ":TASK_ID");
			setMap2.put("STATE_DATE", "now()");
			boolean flag  =jobTaskDao.edit(whereMap2, setMap2);
			
			HashMap m = new HashMap();
	        m.put( "TASK_ID", new Long( vo.getTaskId() ) );
			
	        map = new JobDataMap();
	        map.put( "TASK_ID", new Long(vo.getTaskId() ) );//这里把任务ID放到map里面，用于记录执行日志的时候使用。
		
	        if( StringUtils.isNotEmpty(vo.getParms()) )
	        {
	            /******************把业务系统配置的变量读出来,放到job的上下文里面**/
	            SAXReader saxReader = new SAXReader();
	            try {
					Document document = saxReader.read( new ByteArrayInputStream( ("<root>" + vo.getParms() + "</root>").getBytes()));
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
	            } catch (DocumentException e) {
					
					e.printStackTrace();
				}
	        }
	        /******************把业务系统配置的变量读出来**/
	        try {
	        	/******************把老的任务给停止，然后删除**/
				inScheduler.unscheduleJob( vo.getTaskCode() + "trigger", vo.getTaskType() + "trigger" );
			    inScheduler.deleteJob( vo.getTaskCode(),vo.getTaskType() );
			    /******************把老的任务给停止，然后删除**/
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   //这里如果是在运行过程中，添加新的任务，而不是修改任务，这里会出错的噢，你说对不对，新加的任务怎么能删除呢？呵呵。
			}
			
			  /******************重新添加任务**/
	        try {
				job = new JobDetail(vo.getTaskCode(), vo.getTaskType(), Class
						.forName(vo.getTaskImplClass()));
				job.setJobDataMap(map);
				trigger = new CronTrigger(vo.getTaskCode() + "trigger", vo
						.getTaskType()
						+ "trigger", vo.getTaskCode(), vo.getTaskType(), vo
						.getTaskExpress());
				inScheduler.addJob(job, true);
				inScheduler.scheduleJob(trigger);
			        /******************重新添加任务**/
	        
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      
		}
		
	}

	
	
}
