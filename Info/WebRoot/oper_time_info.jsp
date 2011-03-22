
<%@page import="org.apache.commons.lang.StringUtils"%><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@page import="
java.util.*,
dao.*,
vo.*,
util.*
"%>

<%
TimeInfoDAO  dao =TimeInfoDAO.getInstance();
String op_type =request.getParameter("op_type");
if("post".equalsIgnoreCase(request.getMethod()))
{
	
	String title =request.getParameter("title");
	String content =request.getParameter("content");
	String type =request.getParameter("type");
	
    String start_time =request.getParameter("start_time");
    String end_time =request.getParameter("end_time");
   if("add".equalsIgnoreCase(op_type))
   {
	    Map<String,Object> map =new HashMap<String,Object>();
		
		map.put("id",System.currentTimeMillis());
		map.put("start_time",start_time);
		if(StringUtils.isNotEmpty(end_time))
		{
			map.put("end_time",end_time);
		}
		map.put("title",title);
		map.put("type",type);
		map.put("add_time",DateUtil.getCurrent());
		

		if(dao.insert(map))
		{
			out.println(JspUtil.getJs("添加成功",true,""));
			return ;
		}
		else{
			out.println(JspUtil.getJs("添加失败",true,""));
			return ;
		}
   }
   if("edit".equalsIgnoreCase(op_type))
   {
	   Long id =Long.parseLong(request.getParameter("id"));
		Map<String,Object> whereMap =new HashMap<String,Object>();
		whereMap.put("id",id);
		
		
	    Map<String,Object> setMap =new HashMap<String,Object>();
		
		
	    setMap.put("start_time",start_time);
		if(StringUtils.isNotEmpty(end_time))
		{
			setMap.put("end_time",end_time);
		}
		setMap.put("title",title);
		setMap.put("type",type);
		setMap.put("add_time",DateUtil.getCurrent());
		

		if(dao.edit(whereMap,setMap))
		{
			out.println(JspUtil.getJs("修改成功",true,""));
			return ;
		}
		else{
			out.println(JspUtil.getJs("修改失败",true,""));
			return ;
		}
   }
}
else
{
	
	Long id =Long.parseLong(request.getParameter("id"));
	
	if("finish".equalsIgnoreCase(op_type))
	{
		Map<String,Object> whereMap =new HashMap<String,Object>();
		Map<String,Object> setMap =new HashMap<String,Object>();
		whereMap.put("id",id);
		
		setMap.put("end_time",DateUtil.getCurrent());
		
		
		if(dao.edit(whereMap,setMap))
		{
			out.println(JspUtil.getJs("修改成功",true,""));
			return ;
		}
		else{
			out.println(JspUtil.getJs("修改失败",true,""));
			return ;
		}
	}

}
%>