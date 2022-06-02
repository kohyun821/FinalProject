package com.example.finalproj;

public class HealthyList {
    //운동 종목 조회 시에 사용 됨
    private String HealthyList_name;
    private String img_uri;

    public String getHealthyList_name() {
        return HealthyList_name;
    }

    public void setHealthyList_name(String healthyList_name) {
        this.HealthyList_name = healthyList_name;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }

    public HealthyList(String healthyList_name, String img_uri) {
        this.HealthyList_name = healthyList_name;
        this.img_uri = img_uri;
    }
}
