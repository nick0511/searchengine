package redis_search.redis_search;

public class transimg {
    public  String title;
    public  String fromurl;
    public  String imgurl;

    transimg(String title,String fromurl,String imgurl){
        this.title=title;
        this.fromurl=fromurl;
        this.imgurl=imgurl;
    }

    public String getTitle() {
        return title;
    }

    public String getFromurl() {
        return fromurl;
    }

    public String getImgurl() {
        return imgurl;
    }
}
