package com.khoitaodulieu.khaibao;

import com.thucthe.DeTai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class KhoiTaoDeTai {
    public KhoiTaoDeTai() throws Exception {
        LinkedList<DeTai> deTaiLinkedList = new LinkedList<>();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/detai.dat"));

        for (int i = 0; i < 10; i++) {
            deTaiLinkedList.add(new DeTai("DT0" + (i + 1), "Đề tài quản lý " + (i + 1), "Không có gợi ý", "Không có ghi chú!"));
        }

        objectOutputStream.writeObject(deTaiLinkedList);

        objectOutputStream.close();
    }
}
