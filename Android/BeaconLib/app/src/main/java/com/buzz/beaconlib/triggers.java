package com.buzz.beaconlib;

/**
 * Created by NickChung on 2/14/15.
 */
public class triggers {
    private String id;

    private String triggertype;

    private String triggercount;

    private String triggerfrequency;

    public void setId(String id){
        this.id = id;
    }

    public void setTriggertype(String triggertype){
        this.triggertype = triggertype;
    }

    public void setTriggercount(String triggercount){
        this.triggercount = triggercount;
    }

    public void setTriggerfrequency(String triggerfrequency){
        this.triggerfrequency = triggerfrequency;
    }

    public String getId(){
        return this.id;
    }

    public String getTriggertype(){
        return this.triggertype;
    }

    public String getTriggercount(){
        return this.triggercount;
    }

    public String getTriggerfrequency(){
        return this.triggerfrequency;
    }

    @Override
    public String toString() {
        return "Triggertype:" + this.getTriggertype() + ","
                + "Triggercount:" + this.getTriggercount() + ","
                + "Triggerfrequency:" + this.getTriggerfrequency();
    }
}