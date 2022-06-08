package com.example.finalproj;

public class UserinformationSearchingList {
    public String userpk;
    public String username;
    public String userphonenum;

    public String getUserpk() {
        return userpk;
    }

    public void setUserpk(String userpk) {
        this.userpk = userpk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphonenum() {
        return userphonenum;
    }

    public void setUserphonenum(String userphonenum) {
        this.userphonenum = userphonenum;
    }

    public UserinformationSearchingList(String userpk, String username, String userphonenum) {
        this.userpk = userpk;
        this.username = username;
        this.userphonenum = userphonenum;
    }
}
