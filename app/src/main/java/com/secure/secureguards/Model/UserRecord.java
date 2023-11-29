package com.secure.secureguards.Model;

public class UserRecord {

    String firstName,  lastName,  dob, mail, phoneNumber,licenceNumber, licenceExpireDate,profilePicture, backSideBadge,frontSideBadge,address, city;

    public UserRecord(String firstName, String lastName, String dob, String mail,
                      String phoneNumber, String licenceNumber,
                      String licenceExpireDate, String profilePicture,
                      String backSideBadge, String frontSideBadge, String address, String city) {
       this. firstName = firstName;
        this. lastName = lastName;
        this. dob = dob;
        this. mail = mail;
        this.  phoneNumber = phoneNumber;
        this.  licenceNumber = licenceNumber;
        this.  licenceExpireDate = licenceExpireDate;
        this. profilePicture = profilePicture;
        this.  backSideBadge = backSideBadge;
        this. frontSideBadge = frontSideBadge;
        this. address = address;
        this.  city = city;
    }
    public UserRecord() {

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setLicenceExpireDate(String licenceExpireDate) {
        this.licenceExpireDate = licenceExpireDate;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setBackSideBadge(String backSideBadge) {
        this.backSideBadge = backSideBadge;
    }

    public void setFrontSideBadge(String frontSideBadge) {
        this.frontSideBadge = frontSideBadge;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getLicenceExpireDate() {
        return licenceExpireDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getBackSideBadge() {
        return backSideBadge;
    }

    public String getFrontSideBadge() {
        return frontSideBadge;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
