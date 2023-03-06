package com.prince.samajil_ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class UpdateProfile {
    @SerializedName("full_name")
    private String fullname;
    @SerializedName("contact_number")
    private String contactnumber;
    @SerializedName("address")
    private String address;
    @SerializedName("instagram_id")
    private String instagramid;
    @SerializedName("facebook_id")
    private String facebookid;
    @SerializedName("gender")
    private String gender;

    public UpdateProfile(String fullname, String contactnumber, String address, String instagramid, String facebookid, String gender) {
        this.fullname = fullname;
        this.contactnumber = contactnumber;
        this.address = address;
        this.instagramid = instagramid;
        this.facebookid = facebookid;
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstagramid() {
        return instagramid;
    }

    public void setInstagramid(String instagramid) {
        this.instagramid = instagramid;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
