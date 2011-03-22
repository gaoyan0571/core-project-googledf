<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="dao.*,util.*,java.util.*,vo.*" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page extends="core.Jsp" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>

 <title>

</title>

<style type="text/css">

.content {
 width: 850px
}
.title {
color: red;
}

</style>
</head>
<body>

<%! int pageCount; %>
<%! int toPage=1; %>
<%! int pageSize=20; %>
<%! String pageUrl ="list.jsp"; %>

<%
   InfoDAO infoDao =InfoDAO.getInstance();

	Map<String,Object> map =new HashMap<String,Object>();
	
 	toPage =(jspUtil.getInt("toPage")==-1)?1:jspUtil.getInt("toPage");
 	
 	
 	if("post".equalsIgnoreCase(request.getMethod()))
 	{
 		String title =jspUtil.getNotNull("title");
 		String type=jspUtil.getNotNull("type");
 		String content =jspUtil.getNotNull("content");
 		
 		if(StringUtils.isNotBlank(title))
 		{
 			map.put("title",title);
 		}
 		
 		if(StringUtils.isNotBlank(type))
 		{
 			map.put("type",type);
 		}
 		if(StringUtils.isNotBlank(content))
 		{
 			map.put("content",content);
 		}
 	}
 	

 	
	List<InfoVO> list =infoDao.selectAllReturnObjectList(map,InfoVO.class,(toPage-1)*pageSize,pageSize);

	
	int rowCount =infoDao.getCount(map);

	pageCount=(rowCount+pageSize-1)/pageSize;//有多少页;
	//List<Map<String,Object>> mapList =infoDao.selectAll(map,0,10)	;
%>



<form action=""  name="selectForm" id="selectForm" >
	类型：<input type="text" name="type" value="">
	标题：<input type="text" name="title" value="">
	内容：<input type="text" name ="content" value="">
	<input type="submit" name="submit" value="sumbit">
</form>


   

<form name="add_form" id="add_form"  method ="post" action="oper_info.jsp" >
  	<input type="hidden"' name="op_type" value="add"></input>
 <table>
 	<tr>
 		<td> 标题:</td>
 		<td><input type="text" name="title" id ="title" value=""/></td>
 	</tr>
 	<tr>
 		<td> 来源URL:</td>
 		<td><input type="text" name="source_url" id ="source_url" value=""/></td>
 	</tr>
	<tr>
 		<td> 来源:</td>
 		<td><input type="text" name="source" id ="source" value=""/></td>
 	</tr>
 	
 	<tr>
 		<td> 类型:</td>
 		<td>
 		<select name="type" id ="type">
 			<option value="jsp">jsp</option>
 			<option value="javascript">javascript</option>
 			<option value="健身">健身</option>
 			<option value="ssh常见问题">ssh常见问题</option>
 			<option value="哲学">哲学</option>
 			<option value="笑话">笑话</option>
 			<option value="python">python</option>
 			<option value="数据库">数据库</option>
 			<option value="生活技巧">生活技巧</option>
 			<option value="工作技巧">工作技巧</option>
 			<option value="新闻评论">新闻评论</option>
 		</select>
 		
 	</tr>
 	
	<tr>
 		<td> 内容:</td>
 		<td><textarea rows="12" cols="150" name="content" id="content"></textarea></td>
 	</tr>
 	
 	<tr>
 		<td></td>
 		<td><input type="submit" name ="提交" ></td>
 	</tr>
 	
 </table>
 
</form>
 
 
 <div>
 <%
 
  for(InfoVO vo :list)
  {

	 int rowslen=2;
	 if(StringUtils.isNotEmpty(vo.getContent()))
	 {
		 rowslen =vo.getContent().length()/300;
		 
		 
	 }
	  %>
		<div class="title">
			 标题:<%=vo.getTitle()%><br></br>
		</div>
		
			<XMP> 
			内容：<%=vo.getContent() %>
			</XMP>
		
		
		  		
		 
	 
	  <%
  }
 %>
 </div>


<%=PageSplit.simpleSeacherSplit(pageCount,toPage,"list.jsp",map) %>
</body>
</html>