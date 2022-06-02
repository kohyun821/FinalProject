package com.example.finalproj;

public class Listitem_healthy {

    //운동 기록 추가 할 시에 ㄹㅣ사이클러 뷰와 연결 되는 아이템 리스트트

   private String listitem_healthyname;
    private String listitem_healthycount;
    private String listitem_healthyset;

    public String getListitem_KG() {
        return listitem_KG;
    }

    public void setListitem_KG(String listitem_KG) {
        this.listitem_KG = listitem_KG;
    }

    private String listitem_KG;

    public String getListitem_healthyname() {
        return listitem_healthyname;
    }

    public void setListitem_healthyname(String listitem_healthyname) {
        this.listitem_healthyname = listitem_healthyname;
    }

    public String getListitem_healthycount() {
        return listitem_healthycount;
    }

    public void setListitem_healthycount(String listitem_healthycount) {
        this.listitem_healthycount = listitem_healthycount;
    }

    public String getListitem_healthyset() {
        return listitem_healthyset;
    }

    public void setListitem_healthyset(String listitem_healthyset) {
        this.listitem_healthyset = listitem_healthyset;
    }

    public Listitem_healthy(String listitem_healthyname, String listitem_healthycount, String listitem_healthyset, String listitem_KG) {
        this.listitem_healthyname = listitem_healthyname;
        this.listitem_healthycount = listitem_healthycount;
        this.listitem_healthyset = listitem_healthyset;
        this.listitem_KG = listitem_KG;
    }
}
