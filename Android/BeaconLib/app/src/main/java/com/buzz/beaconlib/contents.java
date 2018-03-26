package com.buzz.beaconlib;

/**
 * Created by NickChung on 2/14/15.
 */
public class contents {
    private String id;

    private String contenttype;

    private String contentlocation;

    private String contentpath;

    public void setId(String id){
        this.id = id;
    }

    public void setContenttype(String contenttype){
        this.contenttype = contenttype;
    }

    public void setContentlocation(String contentlocation){
        this.contentlocation = contentlocation;
    }

    public void setContentpath(String contentpath){
        this.contentpath = contentpath;
    }

    public String getId(){
        return this.id;
    }

    public String getContenttype(){
        return this.contenttype;
    }

    public String getContentlocation(){
        return this.contentlocation;
    }

    public String getContentpath(){
        return this.contentpath;
    }

    @Override
    public String toString() {
        return "Contenttype:" + this.getContenttype() + ","
                + "Contentlocation:" + this.getContentlocation() + ","
                + "Contentpath:" + this.getContentpath();
    }
}
