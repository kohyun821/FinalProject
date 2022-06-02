package com.example.finalproj;

public class InBodyUser {
    private String userday;
    private String userifm;
    private String userifm2;
    private String userifm3;

    public InBodyUser(){

    }
    public InBodyUser(String user_day, String userifm, String userifm3, String userifm2){
        this.userday = user_day;
        this.userifm = userifm;
        this.userifm3 = userifm3;
        this.userifm2 = userifm2;
    }

    public String getUserday() {
        return userday;
    }

    public void setUserday(String userday) {
        this.userday = userday;
    }

    public String getUserifm() {
        return userifm;
    }

    public void setUserifm(String userifm) {
        this.userifm = userifm;
    }

    public String getUserifm2() {
        return userifm2;
    }

    public void setUserifm2(String userifm2) {
        this.userifm2 = userifm2;
    }

    public String getUserifm3() {
        return userifm3;
    }

    public void setUserifm3(String userifm3) {
        this.userifm3 = userifm3;
    }
}
