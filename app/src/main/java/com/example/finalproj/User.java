package com.example.finalproj;

public class User {
    public String userName;
    public String userPassword;
    public String phoneNo;
    public String email;
    public String auth;
    public String term;

    public User(String userName, String password, String mobileNo, String email, String auth, String term){
        this.userName = userName;
        this.userPassword = password;
        this.phoneNo = mobileNo;
        this.email = email;
        this.auth = auth;
        this.term = term;
    }
}
