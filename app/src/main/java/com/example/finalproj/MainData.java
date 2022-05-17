package com.example.finalproj;

public class MainData {

    private int iv_ex_profile;
    private String ex_name;
    private String ex_info;

    public MainData(int iv_ex_profile, String ex_name, String ex_info) {
        this.iv_ex_profile = iv_ex_profile;
        this.ex_name = ex_name;
        this.ex_info = ex_info;
    }

    public int getIv_profile() {
        return iv_ex_profile;
    }

    public void setIv_ex_profile(int iv_ex_profile) {
        this.iv_ex_profile = iv_ex_profile;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public String getEx_info() {
        return ex_info;
    }

    public void setEx_info(String ex_info) {
        this.ex_info = ex_info;
    }
}
