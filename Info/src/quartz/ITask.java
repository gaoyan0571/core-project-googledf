package quartz;

/**
 *  业务系统需要实现的接口方法。 
 * @author harison
 *
 */

import java.util.HashMap;

/**
 *  业务系统需要实现的接口方法。
 * @author harison
 *
 */
public interface ITask{
  /**
   * 这里留了一个execute接口给业务系统使用，业务系统写任务的时候，只需要继承Task类就可以了。
   * 
   * @param map 这里的map功能可强劲了噢，因为很多任务执行可能需要传入参数，这个参数可以配置在JOB_TASK表的PARMS字段里面
   * 比如配置 
   * <item>
   * <key>sss</key>
   * <value>vvv</value>
   * </item>
   * <item>
   * <key>ss</key>
   * <value>vv</value>
   * </item>
   * 这里在程序运行中，启动业务系统job的时候，会吧上面的记录已MAP的形式传递过去。
   * 这个在PARMS字段里面，那么在业务系统实现这个方法的时候，可以通过
   * map.get("ss") 获得 vv 值
   */
  public void execute( HashMap map );
}
