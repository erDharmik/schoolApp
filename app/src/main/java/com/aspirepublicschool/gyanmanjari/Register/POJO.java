package com.aspirepublicschool.gyanmanjari.Register;

public class POJO {

    String cid,sc_id,std,div,med,board;

    public String getCid() {
        return cid;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getStd() {
        return std;
    }

    public String getDiv() {
        return div;
    }

    public String getMed() {
        return med;
    }

    public String getBoard() {
        return board;
    }

    public POJO(String cid, String sc_id, String std, String div, String med, String board) {
        this.cid = cid;
        this.sc_id = sc_id;
        this.std = std;
        this.div = div;
        this.med = med;
        this.board = board;
    }
}
