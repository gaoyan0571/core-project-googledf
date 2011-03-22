<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*,
java.io.*,
 java.nio.*,
java.nio.channels.*" 

%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



<%!
public String pathConvert(String path) {
	
	
	String sRet = path.replace('\\', '/');
	File file = new File(path);
	
	if (file.getParent() != null) {
		if (file.isDirectory()) {
			if (! sRet.endsWith("/"))
				sRet += "/";
		}
	} else {
		if (! sRet.endsWith("/"))
			sRet += "/";	
	}
	
	return sRet;
}

/**
 * 读取文本数据,每读一行在后面加\n
 * @param filename
 * @param encode 文字编码
 * @return
 */
public static String read(String filename,String encode) {
	File ft = new File(filename);
	StringBuffer sb = new StringBuffer();
	try {
		InputStream is = new FileInputStream(ft);
		BufferedReader in = new BufferedReader(new InputStreamReader(is,encode));
		String aline = "";
		while (null != (aline = in.readLine())) {
			sb.append(aline + "\n");
		}
		is.close();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	return sb.toString();
}

public static String tailFile(String fileName,long readSize,String encode)
{
	StringBuffer sb=new StringBuffer();
	try {
		
		RandomAccessFile out = new RandomAccessFile(fileName, "r");
		
		long start =0;
		if(out.length()>readSize)
		{
			start =out.length()-readSize;
		}
		else
		{
			readSize=out.length();
		}
		MappedByteBuffer  mb =out.getChannel().map(FileChannel.MapMode.READ_ONLY,start, readSize);
		
		int foot=0;
		int byteSize =1024;
		byte[] b =new byte[byteSize];
		while(mb.hasRemaining())
		{
			
			if(foot>byteSize-1)
			{
				sb.append(new String(b,encode));
				
				foot =0;
			}
			b[foot++]=mb.get();
		}
		if(foot>0)
		{
			byte [] tb=new byte[foot];
			for(int i=0;i<foot;i++)
			{
				tb[i]=b[i];
			}
			sb.append(new String(tb,encode));
		}
		
	} catch (FileNotFoundException e) {
		System.out.println(" 没有找到文件");
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return sb.toString();

}

%>
<%


   
	String actionUrl =request.getRequestURI();
	String  curPath="";
	if(request.getParameter("curPath")==null)
	{
		curPath = request.getRealPath(request.getServletPath());
	}
	else{
		curPath =request.getParameter("curPath");
	}
	
	String type =request.getParameter("type");
	String curFileContent="";
	if(!"".equals(type)&&null!=type)
	{
		if(type.equals("openFile"))
		{
			curFileContent=read(curPath,"utf-8");
		}
		if(type.equals("tailFile"))
		{
			curFileContent=tailFile(curPath,102400l,"utf-8");
		}
		
	}

	boolean isRoot = curPath.equals("");
	
	File curFile = new File(curPath);
	
	
	
    
    
    
    String parenPath=curFile.getParent() == null?"":pathConvert(curFile.getParent());
    

%>


<a href ="<%out.println(actionUrl+"?curPath="+parenPath);%>">上级目录</a><br></br>
<div id ="listFiles" >
<form id="listFiles" name ="listFiles" action="">
	
  	
  	 
     <%
     	out.println("is root ="+isRoot+"<br>");
     	out.println("current Uri ="+request.getRequestURI()+"<br>");
     	
     	out.println("current path is :"+curPath+"<br>");
     	File[] subFiles ;
     	if(isRoot==true)
     	{
     		subFiles=File.listRoots();
     	
     	}else
     	{
    		subFiles =curFile.listFiles();
     	}
     		
   		if(null!=subFiles&&subFiles.length>0)
       	{
   			
   				for(File f:subFiles)
           		{
   					
   					if(isRoot==false)
   	     			{
   						
            			if(f.isDirectory())
            			{
            				out.println("<a href=\""+actionUrl+"?curPath="+f.getPath()+"\">"+f.getName()+"<a><br>");
            			}
            			else{
            				%>
            					
            					
            					BigFileUse:<a href="<%=actionUrl%>?type=tailFile&curPath=<%=f.getPath()%>"><%=f.getName()%> </a>,
            					size =<%=f.length()/(1024*1024) %>M
            					<br></br>
            				<%
            				
            				//
            		
            			}
   	     			}
   					else
   					{
   						
   						out.println("<a href=\""+actionUrl+"?curPath="+f.getPath()+"\">"+pathConvert(f.getPath())+"<a><br>");
   					}
           		}
       		
       	}
     
     	
     	
     	
     %>
  	 
</form>
</div>

<div>
<font color="red"> 当前文件的内容：</font>
<XMP> 

	<%=curFileContent %>
	</XMP>
</div>


</body>
</html>