package com.secure.secureguards.Model;

public class UserRecord {

    String FirstName,LastName,DOB,Mail,PhoneNumber,LicenceNumber,LicenceExpireDate,ProfilePicture
            ,BackSideBadge,FrontSideBadge,Address,City;

    public UserRecord(String firstName, String lastName, String DOB, String mail, String phoneNumber, String licenceNumber, String licenceExpireDate, String profilePicture, String backSideBadge, String frontSideBadge, String address, String city) {
        FirstName = firstName;
        LastName = lastName;
        this.DOB = DOB;
        Mail = mail;
        PhoneNumber = phoneNumber;
        LicenceNumber = licenceNumber;
        LicenceExpireDate = licenceExpireDate;
        ProfilePicture = profilePicture;
        BackSideBadge = backSideBadge;
        FrontSideBadge = frontSideBadge;
        Address = address;
        City = city;
    } public UserRecord() {

    }

    public String getLastName() {
        return LastName;
    }

    public String getDOB() {
        return DOB;
    }

    public String getMail() {
        return Mail;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getLicenceNumber() {
        return LicenceNumber;
    }

    public String getLicenceExpireDate() {
        return LicenceExpireDate;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public String getBackSideBadge() {
        return BackSideBadge;
    }

    public String getFrontSideBadge() {
        return FrontSideBadge;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getFirstName() {
        return FirstName;
    }
}
