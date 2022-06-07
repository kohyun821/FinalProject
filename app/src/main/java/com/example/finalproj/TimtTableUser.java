package com.example.finalproj;

public class TimtTableUser {
    String PK;
    String name;
    String where;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public TimtTableUser(String PK, String name, String where) {
        this.PK = PK;
        this.name = name;
        this.where = where;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimtTableUser(String PK, String name) {
        this.PK = PK;
        this.name = name;
    }
}
