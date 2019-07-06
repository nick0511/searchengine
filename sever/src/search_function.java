

import java.awt.print.Pageable;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.oracle.jrockit.jfr.RequestDelegate;
import com.sun.glass.ui.Application;

import javafx.scene.chart.PieChart.Data;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class search_function
 */
@WebServlet("/search_function")
public class search_function extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public String key="";
	
	public int from=0;
	
	public String tp; 
    public search_function() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();									//从session 获取页数和关键词
		
//		String page = request.getParameter("from");
		
		from=Integer.valueOf(request.getParameter("from"));							//doget方法用于分页跳转
		
		session.setAttribute("page", from);											//设置页数
		
		key = request.getParameter("key");											//获取上一页的关键字
		
//        getServletConfig().getServletContext().
        
//        getRequestDispatcher("/test.jsp").forward(request, response);//跳转
		
			doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");						//设置字符编码
		 key = request.getParameter("key");	
		 
		 tp=request.getParameter("type");
		 
		 
		 if (tp == null) {
			tp = session.getAttribute("type").toString();
		}			
		 
		 // 从上个页面继承type值  用于分页跳转
//		 System.out.println("选择的查找类型"+tp);
		 String tString="txt";
		 if(tp.equals(tString)) 												//判断是否文章类型
		 {			 
		 
		 if (key==null) {
			key = session.getAttribute("key").toString();						// 从上个页面继承key值  用于分页跳转
		}
		System.out.println(key);
		
		try {
			Jedis jedis = new Jedis("192.168.120.110", 6379, 50000);

		    jedis.auth("5zhpass");												//数据库登陆
		    
		    String SID = (String.valueOf(System.currentTimeMillis()).substring(5, 9));			//用秒数来命名协定返回的ID值
		    System.out.println(SID);
		    jedis.lpush("user-request", "ID1");									//插入
	        jedis.hset("ID1","SID",SID);										//插入需求要求返回ID
	        jedis.hset("ID1","key",key);										//插入需求关键字
	        jedis.hset("ID1","page_num",Integer.toString(from*15));				//插入页数
	        jedis.hset("ID1", "type","txt");									//插入搜索类型 img/txt
	        
	        
	       

	        do {
	        																	//循环检测服务端是否写入数据
	        session.setAttribute("key", key);
	        
	        Boolean Flag = jedis.exists(SID+key);
	        if (Flag) {															//通过SID+key来查询服务器是否写入数据
			jedis.del(SID+key);
	        int size = Integer.parseInt(jedis.hget(SID, "size"));				//获取客户端返回的size：一页显示多少条
	        
	        int total_size = Integer.valueOf(jedis.hget(SID, "total_size"));	//获取客户端返回的total size：匹配到的全部条数
	        System.out.println("size is "+total_size);
//	        jedis.hdel(SID, "size");											
//	        jedis.hdel(SID, "total_size");
	        
	        																		//从库里取出来就删掉，避免下一次查询出错
	        
	        session.setAttribute("size", size);
	        session.setAttribute("total_size", total_size);
	        
	        
	        for(int i=0;i<size;i++) {
	        	String body = jedis.hget(SID, "body"+i);
	        	String title = jedis.hget(SID, "title"+i);							//把文章类型的body、title、url获取出来
	        	String url = jedis.hget(SID, "url"+i);								//给session设置，为了给jsp传值
//	        	jedis.hdel(SID, "body"+i);
//	        	jedis.hdel(SID, "title"+i);
//	        	jedis.hdel(SID, "url"+i);
	        	session.setAttribute("body"+i, body);
	        	session.setAttribute("title"+i, title);
	        	session.setAttribute("url"+i, url);
	        
	        }
	        
	        session.setAttribute("from",from);									
	        session.setAttribute("type", "txt");
	        
	        jedis.close();
	        break;
	        }

			} while (true);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		ServletContext app = this.getServletContext();
//		RequestDelegate rd = app.getRequestDispatcher("./showpage.jsp");
		getServletConfig().getServletContext().getRequestDispatcher("/showpage.jsp").forward(request, response);
		 }
		 
		 else 
			//图片类型 	 
		 {
			 if (key==null) {
					key = session.getAttribute("key").toString();// 从上个页面继承key值  用于分页跳转
				}
				System.out.println(key);
				
				try {
					Jedis jedis = new Jedis("192.168.120.110", 6379, 50000);

				    jedis.auth("5zhpass");
				    String SID = String.valueOf(System.currentTimeMillis()).substring(5, 9);
				    System.out.println(SID);
				    jedis.lpush("user-request", "ID1");			//插入
			        jedis.hset("ID1","SID",SID);	//插入需求要求返回ID
			        jedis.hset("ID1","key",key);	//插入需求关键字
			        jedis.hset("ID1","page_num",Integer.toString(from*15));	//插入页数
			        jedis.hset("ID1", "type","img");//插入搜索类型 img/txt
			        
			        
			       

			        session.setAttribute("key", key);			//给下一次分页用
			        do {
			        Boolean Flag = jedis.exists(SID+key);
			        System.out.println(Flag);
			        if (Flag) {
					
			        int size = Integer.parseInt(jedis.hget(SID, "size"));
			      
			        int total_size = Integer.valueOf(jedis.hget(SID, "total_size"));
			        System.out.println("size is "+total_size);
//			        jedis.hdel(SID, "size");
//			        jedis.hdel(SID, "total_size");
			        //从库里取出来就删掉，避免下一次查询出错
			        session.setAttribute("size", size);
			        session.setAttribute("total_size", total_size);
			        for(int i=0;i<size;i++) {
			        	String body = jedis.hget(SID, "fromurl"+i);
			        	String title = jedis.hget(SID, "title"+i);
			        	String url = jedis.hget(SID, "imgurl"+i);
//			        	jedis.hdel(SID, "fromurl"+i);
//			        	jedis.hdel(SID, "title"+i);
//			        	jedis.hdel(SID, "imgurl"+i);
			        	session.setAttribute("fromurl"+i, body);
			        	session.setAttribute("title"+i, title);
			        	session.setAttribute("imgurl"+i, url);
			        
			        }
			        
			        session.setAttribute("from",from);
			        session.setAttribute("type", "img");
			        jedis.close();
			        break;
			        }

					} while (true);
				} catch (Exception e) {
					// TODO: handle exception
				}
//				ServletContext app = this.getServletContext();
//				RequestDelegate rd = app.getRequestDispatcher("./showpage.jsp");
				getServletConfig().getServletContext().getRequestDispatcher("/showimgpage.jsp").forward(request, response);
		}
	}

}
