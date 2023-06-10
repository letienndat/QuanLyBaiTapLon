package com.khoitaodulieu.khaibao;

import com.thucthe.TaiKhoan;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class KhoiTaoTaiKhoan {
    public KhoiTaoTaiKhoan() throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/taikhoan.dat"));

        LinkedList<TaiKhoan> taiKhoanLinkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            taiKhoanLinkedList.addLast(new TaiKhoan("tk_admin" + (i + 1), "mk_admin" + (i + 1)));
        }

        for (int i = 0; i < 30; i++) {
            taiKhoanLinkedList.addLast(new TaiKhoan("tk" + (i + 1), "mk" + (i + 1)));
        }
        objectOutputStream.writeObject(taiKhoanLinkedList);

        objectOutputStream.close();
    }
}
