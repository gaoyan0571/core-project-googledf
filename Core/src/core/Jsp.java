package core;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import util.JspUtil;

public  class Jsp implements HttpJspPage {

	protected JspUtil jspUtil= null;

	private ServletConfig config = null;

	private long startTime = 0; //页面开始时间
	
	

	public ServletConfig getServletConfig() {
		return config;
	}

	

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		jspInit();
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		this.startTime = System.currentTimeMillis();

		//System.out.println("service...");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		jspUtil = new JspUtil(httpRequest,httpResponse);
		_jspService(httpRequest, httpResponse);
	}
	
	/**
	 * 获取页面开始到现在的时间,单位为毫秒.<p>
	 * @return 执行耗时,单位毫秒
	 */
	public long getTime() {
		long currentTime = System.currentTimeMillis();
		return currentTime - this.startTime;
	}

	public void destroy() {
		System.out.println("destroy...");
		jspDestroy();
	}
	
	public void jspDestroy() {

	}



	public void _jspService(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}



	public void jspInit() {
		// TODO Auto-generated method stub
		
	}



	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
