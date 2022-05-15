package com.example.finalproj;

public class ListItem {
    private String listitem_name;
    private String listitem_userauth;
    private String listitem_term;
    private String listitem_lostterm;
    private String UID;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getListitem_name() {
        return listitem_name;
    }

    public void setListitem_name(String listitem_name) {
        this.listitem_name = listitem_name;
    }

    public String getListitem_userauth() {
        return listitem_userauth;
    }

    public void setListitem_userauth(String listitem_userauth) {
        this.listitem_userauth = listitem_userauth;
    }

    public String getListitem_term() {
        return listitem_term;
    }

    public void setListitem_term(String listitem_term) {
        this.listitem_term = listitem_term;
    }

    public String getListitem_lostterm() {
        return listitem_lostterm;
    }

    public void setListitem_lostterm(String listitem_lostterm) {
        this.listitem_lostterm = listitem_lostterm;
    }

    public ListItem(String listitem_name, String listitem_userauth, String listitem_term, String listitem_lostterm, String UID) {
        this.listitem_name = listitem_name;
        this.listitem_userauth = listitem_userauth;
        this.listitem_term = listitem_term;
        this.listitem_lostterm = listitem_lostterm;
        this.UID=UID;
    }

}
