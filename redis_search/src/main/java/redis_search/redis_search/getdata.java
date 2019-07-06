package redis_search.redis_search;

import java.util.ArrayList;
import org.apache.http.HttpHost;

import org.elasticsearch.action.search.SearchRequest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
	


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;


import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;
public class getdata {
	public  ArrayList<trans> resdata =null;
	public int from =0;
	public long total_size=0;
//  public static String result=null;
    public RestClient lowLevelRestClient=RestClient.builder(new HttpHost("192.168.120.110",9200,"http")).build();
    public ArrayList<trans> restextdata =new ArrayList<trans>();
    public returnresult returnresult;
    public ArrayList<transimg> resimgdata=new ArrayList<transimg>();
    
    
    getdata(String data,int from){



        try {
            resimgdata=getimgdata(data,from);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        try {
            close();
        }
        catch (IOException e){
            System.out.println(e);
        }

        returnresult=createreturnresult(restextdata,resimgdata);
    }

    getdata(String data,int from,Boolean isimg){



        try {
            restextdata=gettextdata(data, from);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        try {
            close();
        }
        catch (IOException e){
            System.out.println(e);
        }

        returnresult=createreturnresult(restextdata,resimgdata);
    }

    
    private RestHighLevelClient getclient(){

        RestHighLevelClient client=new RestHighLevelClient(lowLevelRestClient);
        return client;
    }

    private void close() throws IOException{
        lowLevelRestClient.close();
    }

    private returnresult createreturnresult(ArrayList<trans> restextdata,ArrayList<transimg> resimgdata){
        return new returnresult(restextdata,resimgdata);
    }

public returnresult getReturnresult() {
    return returnresult;
}

public ArrayList<trans> gettextdata(String data,int from) throws Exception{
	System.out.println("进行文章查询");
    ArrayList<trans> resdata =new ArrayList<trans>();

        RestHighLevelClient client=getclient();

    SearchRequest searchRequest = new SearchRequest("tech","finance","all_img","history");
    searchRequest.types("news");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//    QueryBuilder mqb = QueryBuilders.matchQuery("title",data);
    QueryBuilder mqb = QueryBuilders.multiMatchQuery(data, "title","body");
    searchSourceBuilder.query(mqb);
    searchSourceBuilder.size(15);
    searchSourceBuilder.from(from);
    searchRequest.source(searchSourceBuilder);
    try {
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits res=searchResponse.getHits();
        total_size = searchResponse.getHits().getTotalHits();
        for (SearchHit hit:res){                        //for(a b:c)
//                                                        // 是循环遍历的一种方式，在《java编程思想》一书中称为“增强的for循环”
//                                                        //意思大概是：把a类型的集合c中的每个元素赋值给b。
//
            Map<String,Object> aa= hit.getSourceAsMap();

            String title= aa.get("title").toString();
            String url= aa.get("url").toString();
            String body= aa.get("body").toString();

            trans asd=new trans(title,url,body);

            resdata.add(asd);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }


return resdata;
}



public ArrayList<transimg> getimgdata(String data,int from) throws Exception{
    ArrayList<transimg> imgreturn =new ArrayList<transimg>();

    RestHighLevelClient client=getclient();

    SearchRequest searchRequest = new SearchRequest("all_img");
    searchRequest.types("img");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    QueryBuilder mqb = QueryBuilders.matchQuery("title",data);
    searchSourceBuilder.query(mqb);
    searchSourceBuilder.size(15);
    searchSourceBuilder.from(from);
    searchRequest.source(searchSourceBuilder);
    try {
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits res=searchResponse.getHits();
        
        total_size = searchResponse.getHits().getTotalHits();
        System.out.println(total_size);
        for (SearchHit hit:res){                        //for(a b:c)
//                                                        // 是循环遍历的一种方式，在《java编程思想》一书中称为“增强的for循环”
//                                                        //意思大概是：把a类型的集合c中的每个元素赋值给b。
//
            Map<String,Object> aa= hit.getSourceAsMap();

//            String title= aa.get("title").toString();
//            String fromurl= aa.get("from_url").toString();
//            String imgurl= aa.get("img_url").toString();
          String title= aa.get("title").toString();
          String fromurl= aa.get("url").toString();
          String imgurl= aa.get("img_url").toString();
            transimg asd=new transimg(title,fromurl,imgurl);

            imgreturn.add(asd);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }


    return imgreturn;
}


}
