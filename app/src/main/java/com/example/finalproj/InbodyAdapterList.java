package com.example.finalproj;

public class InbodyAdapterList {
    String fat;
    String fatmass;
    String skeletalmass;

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFatmass() {
        return fatmass;
    }

    public void setFatmass(String fatmass) {
        this.fatmass = fatmass;
    }

    public String getSkeletalmass() {
        return skeletalmass;
    }

    public void setSkeletalmass(String skeletalmass) {
        this.skeletalmass = skeletalmass;
    }

    public InbodyAdapterList(String fat, String fatmass, String skeletalmass) {
        this.fat = fat;
        this.fatmass = fatmass;
        this.skeletalmass = skeletalmass;
    }
}
