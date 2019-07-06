package redis_search.redis_search;


public class trans {
    public  String title;
    public  String url;
    public  String body;

    trans(String title,String url,String body){
        this.title=title;
        this.url=url;
        this.body=body;

    }
    public String getTitle(){
        return this.title;
    }

    public  String getUrl(){
        return this.url;
    }

    public  String getBody(){
        return  this.body;
    }
}
