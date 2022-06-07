package com.example.finalproj;

public class TrainerUser_guestpencil {
    String auth;
    String name;
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public TrainerUser_guestpencil(String auth, String name, String uid) {
        this.auth = auth;
        this.name = name;
        this.uid = uid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
