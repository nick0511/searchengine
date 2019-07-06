<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Index</title>
</head>

	<script src="plugins/jquery-3.4.1.min.js"></script>
        <script type="text/javascript"> 
            $(document).ready(function(){
                $("#flip").click(function(){
                    $("#zz").fadeIn("slow");
                   
                    
                   $("#mm").fadeIn("slow");
                    $("#mm").animate({left:'45%'});
                    
                    $("#tt").slideDown("slow");
                });
                
            });
</script>

<body>
<!-- 
	<form method="Post" action="./search_function">
	<input type="text" name="key" >
	<input type="submit"  >
	</form>


 <input name="type" type="radio" value="txt" checked="checked">文章
	<input name="type" type="radio" value="img">图片
-->	
		<div id = "zz" >
            <img src="image/u1.png"	style = "width:100%;height:auto;">
		</div>	
         <%
         	session.setAttribute("from", 0);
            session.setAttribute("type","img");
            %>
        <!--搜索框模块-->
              <form method="Post" action="./search_function">
        <div class="container" >
            
		
				<input type="text" name="key" id="flip" placeholder="Search..." >
				
				 
            <div class="search"></div>
           
            
        </div>
        
  </form>
           
		<div id = "mm">
				<img src="image/u15.jpg"	style = "width:100%;height:auto;">			
		</div>	
            
		<div id = "tt" >	
			<center>关于智搜</center>
		</div>




</body>
</html>