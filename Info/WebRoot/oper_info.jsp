<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@page import="java.util.List,
java.util.ArrayList,java.net.URLEncoder,
java.util.Random,
dao.*,
vo.*,
util.*

"%>

<%

if("post".equalsIgnoreCase(request.getMethod()))
{
	
	String title =request.getParameter("title");
	String content =request.getParameter("content");
	String type =request.getParameter("type");
    String source =request.getParameter("source");
    String source_url =request.getParameter("source_url");
   
	InfoVO vo =new InfoVO();
	vo.setTitle(title);
	vo.setContent(content);
	vo.setType(type);
	
	InfoDAO infoDao =InfoDAO.getInstance();
	
	if(infoDao.save(vo))
	{
		out.println(JspUtil.getJs("添加成功",true,""));
		return ;
	}
	else{
		out.println(JspUtil.getJs("添加失败",true,""));
		return ;
	}
}
%>