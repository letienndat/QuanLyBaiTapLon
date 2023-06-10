package com.thucthe;

import java.io.Serializable;

public class HocPhan implements Serializable {
    private String maHP;
    private String tenHP;
    private double soTinChi;
    private int nam;

    public HocPhan() {}

    public HocPhan(String maHP, String tenHP, double soTinChi, int nam) {
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.soTinChi = soTinChi;
        this.nam = nam;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public double getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(double soTinChi) {
        this.soTinChi = soTinChi;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    @Override
    public String toString() {
        return "HocPhan{" +
                "maHP='" + maHP + '\'' +
                ", tenHP='" + tenHP + '\'' +
                ", soTinChi=" + soTinChi +
                ", nam=" + nam +
                '}';
    }
}
