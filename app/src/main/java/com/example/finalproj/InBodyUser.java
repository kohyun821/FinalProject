package com.example.finalproj;

public class InBodyUser {
    private String date;
    private String fat;
    private String skeletal_mass;
    private String fat_mass;

    public InBodyUser(){

    }
    public InBodyUser(String date, String fat, String fat_mass, String skeletal_mass){
        this.date = date;
        this.fat = fat;
        this.fat_mass = fat_mass;
        this.skeletal_mass = skeletal_mass;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getfat() {
        return fat;
    }

    public void setfat(String fat) {
        this.fat = fat;
    }

    public String getskeletal_mass() {
        return skeletal_mass;
    }

    public void setskeletal_mass(String skeletal_mass) {
        this.skeletal_mass = skeletal_mass;
    }

    public String getfat_mass() {
        return fat_mass;
    }

    public void setfat_mass(String fat_mass) {
        this.fat_mass = fat_mass;
    }
}
