package redis_search.redis_search;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import org.joda.time.TimeOfDay;

import redis.clients.jedis.Jedis;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    { 
    	 
    
    	Jedis jedis = null;
	    do {

		    jedis = new Jedis("192.168.120.110" ,6379);
		    jedis.auth("5zhpass");									//连接redis
//	        String alive = jedis.ping();
//	        System.out.println(alive);								//查看服务是否运行       
	        

	        	
		    										//连接检测
			try {
				Thread.sleep(1000);			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			//等待客户发来信息
		      
        
	    
            String ID = jedis.rpop("user-request");
            //获取来的ID
  			//弹出队列第一个
	    if (ID != null) {
	    	System.out.println(ID+"  "+Calendar.getInstance().getTime());
	    	System.out.println("获取一个请求");								//做为日志存放，便于找错
	        String SID = jedis.hget(ID, "SID");
	        String Key = jedis.hget(ID, "key");
	        String page = jedis.hget(ID, "page_num");						//取出客户附带信息。
	        String type = jedis.hget(ID, "type");							//取出来即删掉
	        
	        jedis.hdel(ID, "SID");											//去除即删掉
	        jedis.hdel(ID, "key");
	        jedis.hdel(ID, "page_num");
	        jedis.hdel(ID, "type");
	        //通过ID获取信息
	       
	 	   try {
	 		   
		       
	 		   
	 		   
//		       getdata g=new getdata();
//		       g.from=(Integer.valueOf(page)*15);
//		       System.out.println(Key+"第"+page+"页");
//		       
		       
		       //连接查询
		       if (type.toString().equals("img")) {										//如果是查询是img
		    	   
		    	   getdata g=new getdata(Key,Integer.valueOf(page));
			       g.from=(Integer.valueOf(page)*15);
			       System.out.println(Key+page);
			       returnresult a = g.getReturnresult();
		   
//			       ArrayList<transimg> img = g.getimgdata(Key,Integer.valueOf(page));//调用elasticsearch查询接口
			       ArrayList<transimg> img = a.getResimgdata();					//调用elasticsearch查询接口
		       
			       jedis.hset(SID, "size",String.valueOf(img.size()));			//写入这次返回的信息条数
			       jedis.hset(SID, "total_size",String.valueOf(g.total_size));  //写入总匹配条数    	
			       for(int i=0;i<img.size();i++) {
			    	
			       	jedis.hset(SID,"fromurl"+i,img.get(i).fromurl);	//插入信息主体
			        jedis.hset(SID,"title"+i,img.get(i).title);	//插入信息标题
			        jedis.hset(SID,"imgurl"+i,img.get(i).imgurl);	//插入信息url
			        System.out.println("img-site");
			        System.out.println(i);
				//结果插入数据库
			}
			       System.out.println(SID+Key+"size:"+img.size());
			       System.out.println(type.toString());
			       jedis.set(SID+Key, Key);
		       }
			       else {
			    	   getdata g=new getdata(Key,Integer.valueOf(page),false);
				       g.from=(Integer.valueOf(page)*15);
				       System.out.println(Key+page);
				       returnresult a = g.getReturnresult();
				       
			    	   //查询是文章模式
				       ArrayList<trans> news = a.getRestextdata();	
				       System.out.println(news.size());
				       jedis.hset(SID, "size",String.valueOf(news.size()));
				       jedis.hset(SID, "total_size",String.valueOf(g.total_size));
				       for(int i=0;i<news.size();i++) {
				    	System.out.println(news.get(i).body);
				    	System.out.println(news.get(i).title);
				    	System.out.println(news.get(i).url);
				       	jedis.hset(SID,"body"+i,news.get(i).body);	//插入信息主体
				        jedis.hset(SID,"title"+i,news.get(i).title);	//插入信息标题
				        jedis.hset(SID,"url"+i,news.get(i).url);	//插入信息url
				        System.out.println("txt-site");    
	
				}
				       System.out.println(SID+Key);
				       jedis.set(SID+Key, Key);
				       System.out.println(type.toString());
		       }
		       
		   }catch (Exception e){
		       ;
	}
        }
	    jedis.close();	    
		} while (true);

    	
        
    }
    
    }
