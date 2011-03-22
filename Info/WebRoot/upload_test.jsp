<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*,java.net.*,
org.apache.commons.io.*,
java.io.*,
org.apache.commons.fileupload.disk.*,
org.apache.commons.fileupload.*,
org.apache.commons.fileupload.servlet.*

" %>
 <%@page import="util.DateUtil"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%

if("post".equalsIgnoreCase(request.getMethod()))
{
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	FileItemFactory factory = new DiskFileItemFactory();

	//Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	List /* FileItem */ items = upload.parseRequest(request);


	Iterator iter = items.iterator();
	while (iter.hasNext()) {
	    FileItem item = (FileItem) iter.next();

	    if (item.isFormField()) {
	    	String name = item.getFieldName();
	        String value = item.getString();

	    } else {
	    	   String fieldName = item.getFieldName();
	    	    String fileName = item.getName();
	    	    String contentType = item.getContentType();
	    	    boolean isInMemory = item.isInMemory();
	    	    long sizeInBytes = item.getSize();
	    	    
	    	  System.out.println("fieldName ="+fieldName);
	    	  System.out.println("fileName ="+fileName);
	    	  System.out.println("contentType ="+contentType);
	    	  
	    	  InputStream stream = item.getInputStream();
	    	  
	    	 
	    	  FileOutputStream fs = new FileOutputStream("D:\\temp\\test.xls");
	    		  int bytesum = 0;
	  			int byteread = 0;
	    	  byte[] buffer = new byte[1024 * 5];
				while ((byteread = stream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				stream.close();
	    }
	}	
}

%>

	<form name="uploadForm" action="upload_test.jsp" enctype="multipart/form-data" method="post">
			
			导入资源信息:<input type="file" name="upload" /><BR></BR>
			<input type="submit" value="提交">
		</form>

</body>
</html>