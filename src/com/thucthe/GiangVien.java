package com.thucthe;

import java.io.Serializable;

public class GiangVien extends NguoiDangNhap implements Serializable {
    public GiangVien(String ma, String hoTen, String tenTK) {
        super(ma, hoTen, tenTK);
    }

    @Override
    public String toString() {
        return "GiangVien{" +
                "ma='" + getMa() + '\'' +
                ", hoTen='" + getHoTen() + '\'' +
                ", tenTK='" + getTenTK() + '\'' +
                '}';
    }
}
