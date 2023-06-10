package com.khoitaodulieu.khaibao;

import com.thucthe.Nhom;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class KhoiTaoNhom {
    public KhoiTaoNhom() throws Exception {
        LinkedList<Nhom> nhomLinkedList = new LinkedList<>();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));

//        nhomLinkedList.add(new Nhom(1, List.of("SV01", "SV04").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L01", "https://linkdetai", 6.5f, "", -1));
//        nhomLinkedList.add(new Nhom(2, List.of("SV03", "SV012").stream().collect(Collectors.toCollection(LinkedList::new)), "DT01", "L01", "https://linkdetai", 6.4f, "", -1));
////        nhomLinkedList.add(new Nhom(3, List.of("SV06", "SV016", "SV029").stream().collect(Collectors.toCollection(LinkedList::new)), "DT05", "L01", "https://linkdetai", 8.5f, "", -1));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV02", "SV012").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L02", "https://linkdetai", 6.5f, "", 2));
//        nhomLinkedList.add(new Nhom(2, List.of("SV08", "SV026", "SV028").stream().collect(Collectors.toCollection(LinkedList::new)), "", "L02", "https://linkdetai", 8.5f, "", 3));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV05").stream().collect(Collectors.toCollection(LinkedList::new)), "DT07", "L03", "https://linkdetai", 6.5f, "", 1));
//        nhomLinkedList.add(new Nhom(2, List.of("SV06", "SV015").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L03", "https://linkdetai", 6.4f, "", 2));
//        nhomLinkedList.add(new Nhom(3, List.of("SV016", "SV018", "SV029").stream().collect(Collectors.toCollection(LinkedList::new)), "DT03", "L03", "https://linkdetai", 8.5f, "", 3));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV07", "SV010").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L04", "https://linkdetai", 6.5f, "", 2));
//        nhomLinkedList.add(new Nhom(2, List.of("SV019", "SV026", "SV028", "SV030").stream().collect(Collectors.toCollection(LinkedList::new)), "DT09", "L04", "https://linkdetai", 6.4f, "", 4));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV02", "SV04").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L05", "https://linkdetai", 6.5f, "", 2));
//        nhomLinkedList.add(new Nhom(2, List.of("SV09").stream().collect(Collectors.toCollection(LinkedList::new)), "DT01", "L05", "https://linkdetai", 6.4f, "", 1));
//        nhomLinkedList.add(new Nhom(3, List.of("SV014", "SV022", "SV027").stream().collect(Collectors.toCollection(LinkedList::new)), "DT05", "L05", "https://linkdetai", 8.5f, "", 3));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV08").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L06", "https://linkdetai", 6.5f, "", 1));
//        nhomLinkedList.add(new Nhom(2, List.of("SV010", "SV011", "SV026").stream().collect(Collectors.toCollection(LinkedList::new)), "DT01", "L06", "https://linkdetai", 6.4f, "", 3));
//        nhomLinkedList.add(new Nhom(3, List.of("SV027", "SV028", "SV030").stream().collect(Collectors.toCollection(LinkedList::new)), "DT05", "L06", "https://linkdetai", 8.5f, "", 3));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV03", "SV09").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L07", "https://linkdetai", 6.5f, "", 2));
//        nhomLinkedList.add(new Nhom(2, List.of("SV013", "SV014").stream().collect(Collectors.toCollection(LinkedList::new)), "DT01", "L07", "https://linkdetai", 6.4f, "", 2));
//        nhomLinkedList.add(new Nhom(3, List.of("SV017", "SV020", "SV029").stream().collect(Collectors.toCollection(LinkedList::new)), "DT05", "L07", "https://linkdetai", 8.5f, "", 3));
//
//        nhomLinkedList.add(new Nhom(1, List.of("SV05").stream().collect(Collectors.toCollection(LinkedList::new)), "DT02", "L08", "https://linkdetai", 6.5f, "", 1));
//        nhomLinkedList.add(new Nhom(2, List.of("SV06", "SV012", "SV018").stream().collect(Collectors.toCollection(LinkedList::new)), "DT03", "L08", "https://linkdetai", 6.4f, "", 3));
//        nhomLinkedList.add(new Nhom(3, List.of("SV019", "SV021", "SV024").stream().collect(Collectors.toCollection(LinkedList::new)), "DT05", "L08", "https://linkdetai", 8.5f, "", 3));

        objectOutputStream.writeObject(nhomLinkedList);

        objectOutputStream.close();
    }
}
