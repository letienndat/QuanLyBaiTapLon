package com.thucthe;

import java.io.Serializable;
import java.util.LinkedList;

public class Nhom implements Serializable {
    private int soNhom;
    private LinkedList<String> danhSachMaSV;
    private String maDeTai;
    private String maLop;
    private String linkDeTai;
    private float diem;
    private String ghiChu;
    private int soThanhVien = -1;

    public Nhom() {}

    public Nhom(int soNhom, LinkedList<String> danhSachMaSV, String maDeTai, String maLop, String linkDeTai, float diem, String ghiChu, int soThanhVien) {
        this.soNhom = soNhom;
        this.danhSachMaSV = danhSachMaSV;
        this.maDeTai = maDeTai;
        this.maLop = maLop;
        this.linkDeTai = linkDeTai;
        this.diem = diem;
        this.ghiChu = ghiChu;
        this.soThanhVien = soThanhVien;
    }

    public int getSoNhom() {
        return soNhom;
    }

    public void setSoNhom(int soNhom) {
        this.soNhom = soNhom;
    }

    public LinkedList<String> getDanhSachMaSV() {
        return danhSachMaSV;
    }

    public void setDanhSachMaSV(LinkedList<String> danhSachMaSV) {
        this.danhSachMaSV = danhSachMaSV;
    }

    public String getMaDeTai() {
        return maDeTai;
    }

    public void setMaDeTai(String maDeTai) {
        this.maDeTai = maDeTai;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getLinkDeTai() {
        return linkDeTai;
    }

    public void setLinkDeTai(String linkDeTai) {
        this.linkDeTai = linkDeTai;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getSoThanhVien() {
        return soThanhVien;
    }

    public void setSoThanhVien(int soThanhVien) {
        this.soThanhVien = soThanhVien;
    }

    @Override
    public String toString() {
        return "Nhom{" +
                "soNhom=" + soNhom +
                ", danhSachMaSV=" + danhSachMaSV +
                ", maDeTai='" + maDeTai + '\'' +
                ", maLop='" + maLop + '\'' +
                ", linkDeTai='" + linkDeTai + '\'' +
                ", diem=" + diem +
                ", ghiChu='" + ghiChu + '\'' +
                ", soThanhVien=" + soThanhVien +
                '}';
    }
}
