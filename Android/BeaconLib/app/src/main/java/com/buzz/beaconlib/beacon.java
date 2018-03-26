package com.buzz.beaconlib;

/**
 * Created by NickChung on 2/14/15.
 */
public class beacon {
    private String id;

    private String beaconid;

    private String displayname;

    private String major;

    private String minor;

    private String priority;

    private String effectiverange;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){return this.id;}

    public void setBeaconid(String beaconid){
        this.beaconid = beaconid;
    }

    public String getBeaconid(){return this.beaconid;}

    public void setDisplayname(String displayname){
        this.displayname = displayname;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public void setMinor(String minor){
        this.minor = minor;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public void setEffectiverange(String effectiverange){
        this.effectiverange = effectiverange;
    }

    public String getDisplayname(){
        return this.displayname;
    }

    public String getMajor(){
        return this.major;
    }

    public String getMinor(){
        return this.minor;
    }

    public String getPriority(){
        return this.priority;
    }

    public String getEffectiverange(){
        return this.effectiverange;
    }

    @Override
    public String toString() {
        return "Major:" + this.getMajor() + ","
                + "Minor:" + this.getMinor() + ","
                + "Beaconid:" + this.getBeaconid() + ","
                + "Displayname:" + this.getDisplayname() + ","
                + "Priority:" + this.getPriority() + ","
                + "Effectiverange:" + this.getEffectiverange();
    }
}
