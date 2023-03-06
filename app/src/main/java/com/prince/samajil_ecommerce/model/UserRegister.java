package com.prince.samajil_ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class UserRegister {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm_password")
    private String repassword;

    public UserRegister(String username, String password, String repassword) {
        this.username = username;
        this.password = password;
        this.repassword = repassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
