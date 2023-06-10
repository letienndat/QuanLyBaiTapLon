package com.khoitaodulieu.khaibao;

import com.thucthe.GiangVien;
import com.thucthe.NguoiDangNhap;
import com.thucthe.SinhVien;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class KhoiTaoNguoiDung {
    public KhoiTaoNguoiDung() throws Exception {
        LinkedList<NguoiDangNhap> nguoiDangNhapLinkedList = new LinkedList<>();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/thongtinnguoidung.dat"));

        for (int i = 0; i < 5; i++) {
            nguoiDangNhapLinkedList.add(new GiangVien("GV0" + (i + 1), "Nguyễn Văn A", "tk_admin" + (i + 1)));
        }

        for (int i = 0; i < 30; i++) {
            nguoiDangNhapLinkedList.add(new SinhVien("SV0" + (i + 1), "Nguyễn Văn AA", "tk" + (i + 1)));
        }
        objectOutputStream.writeObject(nguoiDangNhapLinkedList);

        objectOutputStream.close();
    }
}
