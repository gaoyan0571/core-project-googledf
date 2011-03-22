package log4j;

import java.security.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet  extends HttpServlet{

	
	private static final String NETWORKADDRESS_CACHE_TTL = "networkaddress.cache.ttl";
	private static final String NETWORKADDRESS_CACHE_TTL_VALUE = "0";
	
	@Override
	public void init() throws ServletException {
		
		String dir = getServletContext().getRealPath("/");
		String configFile = getInitParameter("log4j.init.filename");
		System.out.println((new StringBuilder("[系统初始化][初始化日志组件]初始参数log4j.init.filename=")).append(dir).append(configFile).toString());
		PropertyConfigurator.configure((new StringBuilder(String.valueOf(dir))).append(configFile).toString());
		Security.setProperty("networkaddress.cache.ttl", "0");
		System.out.println("[系统初始化][JVM DNS缓存设置]DNS缓存值=0");
	}
}
