package com.example.finalproj;

public class UserInformation {

    private String username;
    private String usernumber;
    private String useraddress;

    public UserInformation(String username, String usernumber, String useraddress) {
        this.username = username;
        this.usernumber = usernumber;
        this.useraddress = useraddress;
    }

    public UserInformation(){

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

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }
}
