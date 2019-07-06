

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
		HttpSession session = request.getSession();									//��session ��ȡҳ���͹ؼ���
		
//		String page = request.getParameter("from");
		
		from=Integer.valueOf(request.getParameter("from"));							//doget�������ڷ�ҳ��ת
		
		session.setAttribute("page", from);											//����ҳ��
		
		key = request.getParameter("key");											//��ȡ��һҳ�Ĺؼ���
		
//        getServletConfig().getServletContext().
        
//        getRequestDispatcher("/test.jsp").forward(request, response);//��ת
		
			doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");						//�����ַ�����
		 key = request.getParameter("key");	
		 
		 tp=request.getParameter("type");
		 
		 
		 if (tp == null) {
			tp = session.getAttribute("type").toString();
		}			
		 
		 // ���ϸ�ҳ��̳�typeֵ  ���ڷ�ҳ��ת
//		 System.out.println("ѡ��Ĳ�������"+tp);
		 String tString="txt";
		 if(tp.equals(tString)) 												//�ж��Ƿ���������
		 {			 
		 
		 if (key==null) {
			key = session.getAttribute("key").toString();						// ���ϸ�ҳ��̳�keyֵ  ���ڷ�ҳ��ת
		}
		System.out.println(key);
		
		try {
			Jedis jedis = new Jedis("192.168.120.110", 6379, 50000);

		    jedis.auth("5zhpass");												//���ݿ��½
		    
		    String SID = (String.valueOf(System.currentTimeMillis()).substring(5, 9));			//������������Э�����ص�IDֵ
		    System.out.println(SID);
		    jedis.lpush("user-request", "ID1");									//����
	        jedis.hset("ID1","SID",SID);										//��������Ҫ�󷵻�ID
	        jedis.hset("ID1","key",key);										//��������ؼ���
	        jedis.hset("ID1","page_num",Integer.toString(from*15));				//����ҳ��
	        jedis.hset("ID1", "type","txt");									//������������ img/txt
	        
	        
	       

	        do {
	        																	//ѭ����������Ƿ�д������
	        session.setAttribute("key", key);
	        
	        Boolean Flag = jedis.exists(SID+key);
	        if (Flag) {															//ͨ��SID+key����ѯ�������Ƿ�д������
			jedis.del(SID+key);
	        int size = Integer.parseInt(jedis.hget(SID, "size"));				//��ȡ�ͻ��˷��ص�size��һҳ��ʾ������
	        
	        int total_size = Integer.valueOf(jedis.hget(SID, "total_size"));	//��ȡ�ͻ��˷��ص�total size��ƥ�䵽��ȫ������
	        System.out.println("size is "+total_size);
//	        jedis.hdel(SID, "size");											
//	        jedis.hdel(SID, "total_size");
	        
	        																		//�ӿ���ȡ������ɾ����������һ�β�ѯ����
	        
	        session.setAttribute("size", size);
	        session.setAttribute("total_size", total_size);
	        
	        
	        for(int i=0;i<size;i++) {
	        	String body = jedis.hget(SID, "body"+i);
	        	String title = jedis.hget(SID, "title"+i);							//���������͵�body��title��url��ȡ����
	        	String url = jedis.hget(SID, "url"+i);								//��session���ã�Ϊ�˸�jsp��ֵ
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
			//ͼƬ���� 	 
		 {
			 if (key==null) {
					key = session.getAttribute("key").toString();// ���ϸ�ҳ��̳�keyֵ  ���ڷ�ҳ��ת
				}
				System.out.println(key);
				
				try {
					Jedis jedis = new Jedis("192.168.120.110", 6379, 50000);

				    jedis.auth("5zhpass");
				    String SID = String.valueOf(System.currentTimeMillis()).substring(5, 9);
				    System.out.println(SID);
				    jedis.lpush("user-request", "ID1");			//����
			        jedis.hset("ID1","SID",SID);	//��������Ҫ�󷵻�ID
			        jedis.hset("ID1","key",key);	//��������ؼ���
			        jedis.hset("ID1","page_num",Integer.toString(from*15));	//����ҳ��
			        jedis.hset("ID1", "type","img");//������������ img/txt
			        
			        
			       

			        session.setAttribute("key", key);			//����һ�η�ҳ��
			        do {
			        Boolean Flag = jedis.exists(SID+key);
			        System.out.println(Flag);
			        if (Flag) {
					
			        int size = Integer.parseInt(jedis.hget(SID, "size"));
			      
			        int total_size = Integer.valueOf(jedis.hget(SID, "total_size"));
			        System.out.println("size is "+total_size);
//			        jedis.hdel(SID, "size");
//			        jedis.hdel(SID, "total_size");
			        //�ӿ���ȡ������ɾ����������һ�β�ѯ����
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
