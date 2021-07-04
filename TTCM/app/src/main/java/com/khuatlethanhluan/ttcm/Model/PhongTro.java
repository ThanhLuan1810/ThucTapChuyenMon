package com.khuatlethanhluan.ttcm.Model;

import java.io.Serializable;

public class PhongTro implements Serializable {

    private String ID;
    private String TEN_PHONG;
    private String SO_NGUOI;
    private String DIEN_TICH;
    private String GIA;
    private String THONG_TIN_KHAC;
    private String SO_DIEN;
    private String SO_NUOC;


    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setTEN_PHONG(String TEN_PHONG) {
        this.TEN_PHONG = TEN_PHONG;
    }

    public String getTEN_PHONG() {
        return TEN_PHONG;
    }

    public void setSO_NGUOI(String SO_NGUOI) {
        this.SO_NGUOI = SO_NGUOI;
    }

    public String getSO_NGUOI() {
        return SO_NGUOI;
    }

    public void setDIEN_TICH(String DIEN_TICH) {
        this.DIEN_TICH = DIEN_TICH;
    }

    public String getDIEN_TICH() {
        return DIEN_TICH;
    }

    public void setGIA(String GIA) {
        this.GIA = GIA;
    }

    public String getGIA() {
        return GIA;
    }

    public void setTHONG_TIN_KHAC(String THONG_TIN_KHAC) {
        this.THONG_TIN_KHAC = THONG_TIN_KHAC;
    }

    public String getTHONG_TIN_KHAC() {
        return THONG_TIN_KHAC;
    }

    public void setSO_DIEN(String SO_DIEN) {
        this.SO_DIEN = SO_DIEN;
    }

    public String getSO_DIEN() {
        return SO_DIEN;
    }

    public void setSO_NUOC(String SO_NUOC) {
        this.SO_NUOC = SO_NUOC;
    }

    public String getSO_NUOC() {
        return SO_NUOC;
    }
}
