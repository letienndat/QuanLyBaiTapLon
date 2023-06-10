package com.thucthe;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    private String tenTK;
    private String matKhau;

    public TaiKhoan() {}

    public TaiKhoan(String tenTK, String matKhau) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenTK='" + tenTK + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
