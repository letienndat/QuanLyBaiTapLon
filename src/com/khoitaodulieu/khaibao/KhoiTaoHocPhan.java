package com.khoitaodulieu.khaibao;

import com.thucthe.HocPhan;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class KhoiTaoHocPhan {
    public KhoiTaoHocPhan() throws Exception {
        LinkedList<HocPhan> hocPhanLinkedList = new LinkedList<>();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/hocphan.dat"));

        hocPhanLinkedList.add(new HocPhan("HP01", "Java", 3, 2013));
        hocPhanLinkedList.add(new HocPhan("HP02", "PHP", 3, 2015));
        hocPhanLinkedList.add(new HocPhan("HP03", "Python", 3, 2020));


        objectOutputStream.writeObject(hocPhanLinkedList);

        objectOutputStream.close();
    }
}
