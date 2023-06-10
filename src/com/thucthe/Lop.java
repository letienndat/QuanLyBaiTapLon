package com.thucthe;

import java.io.Serializable;
import java.util.LinkedList;

public class Lop implements Serializable {
    private String maLop;
    private String maGiangVien;
    private int siSo;
    private String maHP;
    private LinkedList<String> danhSachMaSV;

    public Lop() {}

    public Lop(String maLop, String maGiangVien, int siSo, String maHP, LinkedList<String> danhSachMaSV) {
        this.maLop = maLop;
        this.maGiangVien = maGiangVien;
        this.siSo = siSo;
        this.maHP = maHP;
        this.danhSachMaSV = danhSachMaSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public int getSiSo() {
        return siSo;
    }

    public void setSiSo(int siSo) {
        this.siSo = siSo;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public LinkedList<String> getDanhSachMaSV() {
        return danhSachMaSV;
    }

    public void setDanhSachMaSV(LinkedList<String> danhSachMaSV) {
        this.danhSachMaSV = danhSachMaSV;
    }

    @Override
    public String toString() {
        return "Lop{" +
                "maLop='" + maLop + '\'' +
                ", maGiangVien='" + maGiangVien + '\'' +
                ", siSo=" + siSo +
                ", maHP='" + maHP + '\'' +
                ", danhSachMaSV=" + danhSachMaSV +
                '}';
    }
}
