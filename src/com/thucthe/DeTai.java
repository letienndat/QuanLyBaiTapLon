package com.thucthe;

import java.io.Serializable;

public class DeTai implements Serializable {
    private String maDeTai;
    private String tenDeTai;
    private String goiYThucHien;
    private String ghiChu;

    public DeTai() {}

    public DeTai(String maDeTai, String tenDeTai, String goiYThucHien, String ghiChu) {
        this.maDeTai = maDeTai;
        this.tenDeTai = tenDeTai;
        this.goiYThucHien = goiYThucHien;
        this.ghiChu = ghiChu;
    }

    public String getMaDeTai() {
        return maDeTai;
    }

    public void setMaDeTai(String maDeTai) {
        this.maDeTai = maDeTai;
    }

    public String getTenDeTai() {
        return tenDeTai;
    }

    public void setTenDeTai(String tenDeTai) {
        this.tenDeTai = tenDeTai;
    }

    public String getGoiYThucHien() {
        return goiYThucHien;
    }

    public void setGoiYThucHien(String goiYThucHien) {
        this.goiYThucHien = goiYThucHien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "DeTai{" +
                "maDeTai='" + maDeTai + '\'' +
                ", tenDeTai='" + tenDeTai + '\'' +
                ", goiYThucHien='" + goiYThucHien + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
