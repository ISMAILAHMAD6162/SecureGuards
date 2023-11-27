package com.secure.secureguards.Models;

import java.io.Serializable;

public class Shift implements Serializable {
    public String     startTime;
    public String     endTime;
    public String     year;
    public String     month;
    public String     day;
    public int state;
    public int guardsNo;
    //siteIdDateTime+random+number
    public String shiftId;
    public String siteId;


    public Shift()
    {

    }

    public Shift(String startTime,String endTime,String year,String month,String day,int State,int guardsNo,String shiftId,String siteId)
    {
        this.startTime=startTime;
        this.endTime=endTime;
        this.year=year;
        this.month=month;
        this.day=day;
        this.state=State;
        this.guardsNo=guardsNo;
        this.shiftId=shiftId;
        this.siteId=siteId;
    }



}
