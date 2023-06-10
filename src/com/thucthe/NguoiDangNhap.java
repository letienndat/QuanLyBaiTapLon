package com.thucthe;

import java.io.Serializable;

public class NguoiDangNhap implements Serializable {
    private String ma;
    private String hoTen;
    private String tenTK;

    public NguoiDangNhap() {}

    public NguoiDangNhap(String ma, String hoTen, String tenTK) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.tenTK = tenTK;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    @Override
    public String toString() {
        return "NguoiDangNhap{" +
                "ma='" + ma + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", tenTK='" + tenTK + '\'' +
                '}';
    }
}
