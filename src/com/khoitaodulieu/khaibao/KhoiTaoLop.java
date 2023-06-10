package com.khoitaodulieu.khaibao;

import com.thucthe.Lop;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class KhoiTaoLop {
    public KhoiTaoLop() throws Exception {
        LinkedList<Lop> lopLinkedList = new LinkedList<>();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/lop.dat"));

        lopLinkedList.add(new Lop("L01", "GV02", 75, "HP01", List.of("SV01", "SV03", "SV04", "SV06", "SV012", "SV016", "SV029").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L02", "GV04", 71, "HP01", List.of("SV02", "SV08", "SV012", "SV026", "SV028").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L03", "GV01", 76, "HP02", List.of("SV05", "SV06", "SV015", "SV016", "SV018", "SV029").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L04", "GV05", 65, "HP02", List.of("SV07", "SV010", "SV019", "SV026", "SV028", "SV030").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L05", "GV03", 78, "HP02", List.of("SV02", "SV04", "SV09", "SV014", "SV022", "SV027").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L06", "GV02", 70, "HP03", List.of("SV08", "SV010", "SV011", "SV026", "SV027", "SV028", "SV030").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L07", "GV01", 79, "HP03", List.of("SV03", "SV09", "SV013", "SV014", "SV017", "SV020", "SV029").stream().collect(Collectors.toCollection(LinkedList::new))));
        lopLinkedList.add(new Lop("L08", "GV04", 85, "HP03", List.of("SV05", "SV06", "SV012", "SV018", "SV019", "SV021", "SV024").stream().collect(Collectors.toCollection(LinkedList::new))));

        objectOutputStream.writeObject(lopLinkedList);

        objectOutputStream.close();
    }
}
