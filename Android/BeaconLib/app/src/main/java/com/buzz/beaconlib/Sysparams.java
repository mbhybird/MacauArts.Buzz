package com.buzz.beaconlib;

/**
 * Created by NickChung on 2/14/15.
 */
public class Sysparams {
    private String id;

    private String paramgroup;

    private String paramkey;

    private String paramvalue;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setParamgroup(String paramgroup) {
        this.paramgroup = paramgroup;
    }

    public String getParamgroup() {
        return this.paramgroup;
    }

    public void setParamkey(String paramkey) {
        this.paramkey = paramkey;
    }

    public String getParamkey() {
        return this.paramkey;
    }

    public void setParamvalue(String paramvalue) {
        this.paramvalue = paramvalue;
    }

    public String getParamvalue() {
        return this.paramvalue;
    }

    @Override
    public String toString() {
        return "Paramgroup:" + this.getParamgroup() + ","
                + "Paramkey:" + this.getParamkey() + ","
                + "Paramvalue:" + this.getParamvalue();
    }

}
