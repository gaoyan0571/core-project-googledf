<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="dao.*,util.*,
java.util.*,
org.apache.commons.lang.StringUtils,
vo.*" %>

<html>
<head>
<title>时间记录</title>
</head>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/DateUtil.js"></script>
<script type="text/javascript" src="js/thickbox.js"></script>
<link rel="stylesheet" href="css/thickbox.css" type="text/css" media="screen" />
<body>


  <%
  
  	TimeInfoDAO dao =TimeInfoDAO.getInstance();
  	
  	Map<String,Object> whereMap =new HashMap<String,Object>();
  	System.out.println(request.getRequestURL());
 	long id =Long.parseLong(request.getParameter("id"));
 	whereMap.put("id",id);
 	
 	
 	
 	Map<String,Object> map =dao.getOne(whereMap);
  %>	




<form  name="form1" id="form1" action="oper_time_info.jsp" method="post">
		
		<input type="hidden"  name="op_type" value="edit_conf"></input>
 <table>
 	<tr>
 		<td> 标题:</td>
 		<td><input type="text" name="title" id ="title" size="100" maxlength="100" value="<%=map.get("title") %>"/></td>
 	</tr>
 	<tr>
 		<td> 开始时间：</td>
 		<td><input type="text" name="start_time" id ="start_time" value="<%=map.get("start_time") %>"/></td>
 	</tr>
	<tr>
 		<td> 结束时间：</td>
 		<td><input type="text" name="end_time" id ="end_time" value="<%=map.get("end_time") %>"/></td>
 	</tr>
 	
 	<tr>
 		<td> 类型:</td>
 		<td>
 		<select name="type" id ="type">
 			<option value="工作">工作</option>
 			<option value="学习">学习</option>
 			<option value="娱乐">娱乐</option>
 			<option value="购物">购物</option>
 			<option value="代码">代码</option>
 			<option value="必须时间">必须时间</option>
 		</select>
 	</tr>
	
 	<tr>
 		<td></td>
 		<td><input type="submit" value="提交" ></td>
 	</tr>
 	
</table>
</form>
 
 
 
 
</body>
</html>