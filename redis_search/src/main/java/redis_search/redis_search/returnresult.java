package redis_search.redis_search;

import java.util.ArrayList;

public class returnresult {
    public ArrayList<trans> restextdata =new ArrayList<trans>();
    public ArrayList<transimg> resimgdata=new ArrayList<transimg>();

    returnresult(ArrayList<trans> restextdata,ArrayList<transimg> resimgdata){
        this.restextdata=restextdata;
        this.resimgdata=resimgdata;
    }

    public ArrayList<trans> getRestextdata() {
        return restextdata;
    }

    public ArrayList<transimg> getResimgdata() {
        return resimgdata;
    }
}
