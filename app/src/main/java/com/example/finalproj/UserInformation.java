package com.example.finalproj;

public class UserInformation {

    private String userkey;
    private String username;
    private String usernumber;



    public UserInformation(String userkey ,String username, String usernumber ) {
        this.userkey = userkey;
        this.username = username;
        this.usernumber = usernumber;

    }

    public UserInformation(){

    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }
}
