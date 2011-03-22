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
  	
  	Map<String,Object> map =new HashMap<String,Object>();
  //	List<Map<String,Object>>  list =dao.selectAll(map,"add_time desc", 0,10);
  	
  	List<TimeInfoVO> list =dao.selectAllReturnObjectList(map,TimeInfoVO.class,0,100);
  %>	

<form name="add_form" id="add_form"  method ="post" action="oper_time_info.jsp" >
  	<input type="hidden"  name="op_type" value="add"></input>
 <table>
 	<tr>
 		<td> 标题:</td>
 		<td><input type="text" name="title" id ="title" size="100" maxlength="100" value=""/></td>
 	</tr>
 	<tr>
 		<td> 开始时间：</td>
 		<td><input type="text" name="start_time" id ="start_time" value=""/></td>
 	</tr>
	<tr>
 		<td> 结束时间：</td>
 		<td><input type="text" name="end_time" id ="end_time" value=""/></td>
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
 <div id="epiClock"></div>
<script language="javascript">
<!--
function init()
{
	var date = (new Date()).format("yyyy-MM-dd hh:mm:ss");
    $("#start_time").val(date);
}
init();


function editInfo(id){
	 var url = "edit_time_info_iframe.jsp?id="+id;
	 url+="&TB_iframe=true&height=180&width=500&keepThis=true";
	 url+="&modal=true";
	 var title = "修改信息";
	 tb_show(title,url);
}

//-->
</script>

<div id="list_info" >

	<table>
		<tr>
		  <td>标题</td>
		  <td>开始时间</td>
		  <td> 结束时间</td>
		  <td>操作</td>
		  <td>所用时间</td>
		</tr>
	

<%
	for(TimeInfoVO vo:list)
	{
		%>
		<tr>
			<td><%=vo.getTitle() %></td>
			<td ><%=vo.getStart_time() %></td>
			<td ><%=vo.getEnd_time() %></td>
			<td ><a href="oper_time_info.jsp?id=<%=vo.getId() %>&op_type=finish">完成</a> / <a  href ="#" onclick="javascript:editInfo(<%=vo.getId() %>)" >修改</a></td>
			<td ><%
				
			if(StringUtils.isNotEmpty(vo.getStart_time())&StringUtils.isNotEmpty(vo.getEnd_time()))
			{
				out.println( DateUtil.getUseHour(vo.getStart_time(),vo.getEnd_time(),DateUtil.yyyy_MM_dd_hh_mm_ss)+"小时"); 
			}
			
			%></td>
		</tr>
			 
			
		
		<%
	
	}
%>


</table>
</div>

 
 
 
</body>
</html>