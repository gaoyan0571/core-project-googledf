package util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;


public class JspUtil {

	
	private HttpServletRequest request = null;

	private HttpServletResponse response = null;
	
	
	//默认构造函数不提供
	public JspUtil()
	{
		throw new IllegalArgumentException("不提供默认的构造函数，用new JspUtil(pageContext)实现对像");
	}
	
	public JspUtil(PageContext pageContext)
	{
		if(null==pageContext)
		{
			throw new NullPointerException("pageContext 不能为空");
		}
		this.request=(HttpServletRequest)pageContext.getRequest();
		this.response=(HttpServletResponse)pageContext.getResponse();
	}
	
	
	public JspUtil(HttpServletRequest request,HttpServletResponse response)
	{
		this.request=request;
		this.response=response;
	}
	
	
	public int getInt(String name) {
		String value = this.getString(name);
		try {
			return Integer.parseInt(value);
		}
		catch (Exception e) {
			return -1;
		}
	}
	
    /**
     * 返回值 不会为null
     * @param name
     * @return
     */
	public String getNotNull(String name) {
		String value = request.getParameter(name);
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public String getString(String name) {
		String value = request.getParameter(name);
		return value;
	}
	
	public String[] getStrings(String name) {
		return request.getParameterValues(name);
	}
	
	public double getDouble(String name) {
		String value = this.getString(name);
		try {
			return Double.parseDouble(value);
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public float getFloat(String name) {
		String value = this.getString(name);
		try {
			return Float.parseFloat(value);
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public long getLong(String name) {
		String value = this.getString(name);
		try {
			return Long.parseLong(value);
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public boolean getBool(String name) {
		String value = this.getString(name);
		if ("true".equals(value) || "y".equals(value) || "yes".equals(value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取?号后面的所有字符串
	 * @return
	 */
	public String getQueryString() {
		if(StringUtils.isEmpty(request.getQueryString())||StringUtils.isBlank(request.getQueryString()))
			return "";
		else
			return request.getQueryString();
		
	}
	
	/**
	 * 检查是否存在参数
	 * @param name
	 * @return
	 */
	public boolean isExist(String name) {
		return request.getParameter(name) != null;
	}
	
	
	
	/**
	 * 设置当前页面不要在浏览器建立缓存.<p>
	 * 
	 */
	public void setNoCache() {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}
	
	
	/**
	 * 获取当前页面的URL.<p>
	 * 
	 * 如果不用包含POST方式提交的参数，则需改用<a href="#getRequestURL()">getRequestURL()</a>方法获取.<br>
	 * 
	 * @return 页面的URL,包换参数(POST方式提交参数也会被获取到)
	 */
	public String getURL() {
		String result = this.request.getRequestURL().toString();
		Enumeration para = this.request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		if (para.hasMoreElements()) {
			while (para.hasMoreElements()) {
				String name = para.nextElement().toString();
				String[] values = request.getParameterValues(name);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (i > 0) {
							sb.append("&");
						}
						sb.append(name + "=" + values[i]);
					}
				}
			}
			result += "?" + sb.toString();
		}
		return result;
	}

	/**
	 * 获得页面跳转之前的地址
	 * @return 页面跳转之前的地址
	 */
	public String getRedirectUrl() {
		return getRedirectUrl("");
	}
	
	/**
	 * 获得页面跳转之前的地址
	 * @param custom自定义的地址 
	 * @return 页面跳转之前的地址,如果没有则返回自定义的地址.
	 */
	public String getRedirectUrl(String custom) {
		String url = request.getHeader("REDIRECT_URL");
		if (url == null) {
			url = custom;
		}
		return url;
	}
	
	/**
	 * jsp alert message
	 * @param alertMessage
	 * @param goBack
	 * @param go2Url
	 * @return
	 */
	public static String getJs(String alertMessage,boolean goBack ,String go2Url)
	{
		StringBuffer sb =new StringBuffer();
		sb.append("<script language='JavaScript' type='text/javascript'> ");
	
		if(StringUtils.isNotEmpty(alertMessage))
		{
			sb.append("alert('");
			sb.append(alertMessage);
			sb.append("');");
		}
		if(goBack==true&&StringUtils.isEmpty(go2Url))
		{
			sb.append("window.history.go(-1);");
		}
		if(StringUtils.isNotEmpty(go2Url))
		{
			sb.append(" window.location.href='");
			sb.append(go2Url);
			sb.append("'");
		}
		sb.append("</script>");
		return sb.toString();
	}
	
	public  static String subString(String str, int start, int len) {
		if (str == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = start; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 255) {
				counter++;
			}
			else {
				counter = counter + 2;
			}
			if (counter > len) {
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	
}

