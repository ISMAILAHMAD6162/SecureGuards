package com.secure.secureguards.Models;

public class Site
{

    public String title;
    public String siteClientId;
    public String supervisorName;
    public String locationLatitude;
    public String locationLongitude;
    public int state;
    public String email;
    public String phoneNo;
    public String address;
    public String startDate;
    public  String endDate;

    public Site()
    {

    }
    public Site(String title,String supervisorName,String locationLatitude,String locationLongitude,int state,String siteClientId, String email, String phoneNo, String address, String startDate, String endDate)
    {
        this.title=title;
        this.supervisorName=supervisorName;
        this.locationLatitude=locationLatitude;
        this.locationLongitude=locationLongitude;
        this.state=state;
        this.siteClientId=siteClientId;
        this.email=email;
        this.phoneNo=phoneNo;
        this.address=address;
        this.startDate=startDate;
        this.endDate=endDate;
    }




}
