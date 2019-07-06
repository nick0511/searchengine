
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果:<%= session.getAttribute("key").toString() %></title>
</head>
<body>
	<form method="Post" action="./search_function">
	<input type="text" name="key" value=<%= session.getAttribute("key").toString() %>>
	<input type="submit"  >

	</form>
	
<%
	int total_size =Integer.valueOf(session.getAttribute("total_size").toString());//总条数
	out.print("总条数:"+total_size);
	
	int size = Integer.valueOf(session.getAttribute("size").toString());//一页显示多少条
	if(size>0){
	for(int i=0;i<size;i++){											//循环显示
		String url = session.getAttribute("url"+i).toString();
		String title = session.getAttribute("title"+i).toString();
		String body = session.getAttribute("body"+i).toString();
		
	out.print("<div><a href=\""+url+"\">"+title+"</a><p>"+body+"</div>");	
		}
	out.print("一共有"+total_size/size+"页");
	}
	else{
		
		out.print("数据库太少，查询不到关键字");
		out.print("一共有0页");
	}

	%>
	
	
	<% 
	if(size>0){
	int f=Integer.valueOf(session.getAttribute("from").toString());
	int end=f;
	int i=0;
	out.print("<a href=\"?from="+(i)+"\">"+"首页"+"</a>&nbsp&nbsp");
	if(f!=0){
		out.print("<a href=\"?from="+(f-1)+"\">"+"上一页"+"</a>&nbsp&nbsp");
	}
	int number=(int)total_size/size;
	System.out.println("number is "+number+"\n");
	//int max=total_size/size;
	if(number<10){
		
	for(i=0;i<number;i++){
		out.print("<a href=\"?from="+i+"\">"+(i+1)+"</a>&nbsp&nbsp");
		System.out.println("<10"+i);
		}
	
	}
	else {
		
		if(f+10<number){
		for(i=f;i<end+10;i++){
			out.print("<a href=\"?from="+(i)+"\">"+(i+1)+"</a>&nbsp&nbsp");
			System.out.println(">10"+i);
			}
		}else{
			for(i=f;i<number;i++){
				out.print("<a href=\"?from="+(i)+"\">"+(i+1)+"</a>&nbsp&nbsp");
				System.out.println("to end"+i);
				}
			
			
		}
		if(f<number){
			out.print("<a href=\"?from="+(f+1)+"\">"+"下一页"+"</a>&nbsp&nbsp");
		}
		out.print("<a href=\"?from="+(number)+"\">"+"尾页"+"</a>&nbsp&nbsp");
		
	}
	}
	// 《a href="http://localhost:8080/sever/search_function?from=1"》
%>
 
	

</body>
</html>