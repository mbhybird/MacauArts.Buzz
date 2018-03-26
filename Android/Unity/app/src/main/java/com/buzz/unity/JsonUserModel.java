package com.buzz.unity;

/**
 * Created by NickChung on 4/8/15.
 */
public class JsonUserModel {
    private String userid = "";
    private String state = "";
    private String value = "";

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
