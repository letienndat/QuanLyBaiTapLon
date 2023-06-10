package com.thucthe;

import java.io.Serializable;

public class SinhVien extends NguoiDangNhap implements Serializable {
    public SinhVien(String ma, String hoTen, String tenTK) {
        super(ma, hoTen, tenTK);
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "ma='" + getMa() + '\'' +
                ", hoTen='" + getHoTen() + '\'' +
                ", tenTK='" + getTenTK() + '\'' +
                '}';
    }
}
