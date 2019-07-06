
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/search.css" >
    <script src="plugins/jquery-3.4.1.min.js"></script>
        <script type="text/javascript"> 
            $(document).ready(function(){
                $("#flip").click(function(){
        $(".panel").slideDown("slow");
                });
                
            });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果:<%= session.getAttribute("key").toString() %></title>
</head>
<body>
   <style>
        body {
            background: white;
        }
#mm{
    margin:0;
    top:3%;
    position: absolute;
    width: 100%;
    height: 80px;       
}

#zz{
    margin:1px auto;
    margin-top:200px;
    text-align: left;
    
    height: 150px;
    display:flex;
}
    *{margin: 0; padding: 0; list-style: none; border: none;}
    div{            
        display: flex;/*显示模式设置为弹性盒子*/
        flex-wrap: wrap;/*进行强制换行*/
    }        
    div:after{            
        /*对最后一个伪元素进行最大限度伸缩*/            
        content: ' ';            
        flex-grow: 999999999999999999999999999999999999;        
    }        
    img{             
        height: 300px;/*高度*/            
        width: auto;            
        margin: 2px;            
        flex-grow: 1;/*进行按比例伸缩*/            
        object-fit: cover;/*进行裁切，并且图片按比例缩放*/        
    }   
    </style>

	<form method="Post" action="./search_function">

	
	
	
	
	  <div id="mm">
        <!--搜索框模块-->
        <div class="container">
             
            <input type="text"  name="key" id="flip" placeholder="Search..." value=<%= session.getAttribute("key").toString() %>>
            <div class="panel">
                <button id="btn1" type="submit" name="type" value="txt" ><b>网页</b></button>
                <button id="btn2" type="submit" name="type" value="img" >图片</button>
            </div>
            <div class="search"></div>
            
            
        </div>

    </div> 
    </form>
	
	
	
<%

	out.print("<div id=\"zz\">");
	
	
	int size = Integer.valueOf(session.getAttribute("size").toString());		//返回多少条数据
	if(size>0){
	for(int i=0;i<size;i++){
		String fromurl = session.getAttribute("fromurl"+i).toString();
		String title = session.getAttribute("title"+i).toString();
		String imgurl = session.getAttribute("imgurl"+i).toString();
	//	<a href=\""+fromurl+"\">"+title+"</a>
	out.print("<img src=\""+imgurl+"\" alt="+title+" href=\""+imgurl+"\">+<p>");	
	}
	out.print("</div>");
	}
	%>
	
<div style="position:absolute;bottom:0%;backgroud:black;left:10%">	
	<% 
	if(size>0){
	int total_size =Integer.valueOf(session.getAttribute("total_size").toString());//总条数
	out.print("总条数:"+total_size);//分页div
	out.print("一共有"+total_size/size+"页");
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
</div> 
	

</body>
</html>