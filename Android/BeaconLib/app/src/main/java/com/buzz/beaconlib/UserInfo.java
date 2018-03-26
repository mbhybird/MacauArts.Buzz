package com.buzz.beaconlib;

/**
 * Created by NickChung on 2/14/15.
 */
public class UserInfo {
    private String id;

    private String userid;

    private String usernamecn;

    private String usernameen;

    private String isauthorized;

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUsernamecn(String usernamecn) {
        this.usernamecn = usernamecn;
    }

    public String getUsernamecn() {
        return this.usernamecn;
    }

    public void setUsernameen(String usernameen) {
        this.usernameen = usernameen;
    }

    public String getUsernameen() {
        return this.usernameen;
    }

    public void setIsauthorized(String isauthorized) {
        this.isauthorized = isauthorized;
    }

    public String getIsauthorized() {
        return this.isauthorized;
    }

    @Override
    public String toString() {
        return "Userid:" + this.getUserid() + ","
                + "Usernameen:" + this.getUsernameen() + ","
                + "Usernamecn:" + this.getUsernamecn();
    }
}
