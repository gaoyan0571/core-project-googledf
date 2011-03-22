<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*,java.net.*" %>
 <%@page import="util.DateUtil"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">

	
</script>
<form name="PushForm"  action="">
<textarea rows="2" cols="100" name="PushURLid">


</textarea>
<input type="button"   value="提交清除 " onclick="return test(this.form)"/>
</form>


<%
  String hostName=request.getServerName();
  String Addr =request.getRemoteAddr();
  int port =request.getServerPort();
  
  
  
  InetAddress host=null;
  host=InetAddress.getLocalHost();

 
%>
<%=hostName %>
<%=port %>
<%=DateUtil.getCurrent() %>
</body>
</html>