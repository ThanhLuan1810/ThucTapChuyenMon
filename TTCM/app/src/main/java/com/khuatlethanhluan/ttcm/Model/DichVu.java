package com.khuatlethanhluan.ttcm.Model;

public class DichVu {

    private String MaDV;
    private String TEN_DV;
    private String CACH_TINH;
    private String DON_GIA;


    public void setMaDV(String maDV) {
        MaDV = maDV;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setTEN_DV(String TEN_DV) {
        this.TEN_DV = TEN_DV;
    }

    public String getTEN_DV() {
        return TEN_DV;
    }

    public void setCACH_TINH(String CACH_TINH) {
        this.CACH_TINH = CACH_TINH;
    }

    public String getCACH_TINH() {
        return CACH_TINH;
    }

    public void setDON_GIA(String DON_GIA) {
        this.DON_GIA = DON_GIA;
    }

    public String getDON_GIA() {
        return DON_GIA;
    }
}
