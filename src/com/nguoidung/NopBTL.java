package com.nguoidung;

import com.thucthe.DeTai;
import com.thucthe.Nhom;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class NopBTL extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelNopBTL;
    private JLabel labelDeTai;
    private JLabel labelNhom;
    private JTextField textFieldLink;
    private JButton buttonLuu;
    private Nhom nhom;
    private LinkedList<Nhom> nhomLinkedList;
    private LinkedList<DeTai> deTaiLinkedList;

    public NopBTL(Nhom nhom) throws Exception {
        setTitle("NỘP BÀI TẬP LỚN");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.6;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.65;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelNopBTL);
        setResizable(false);

        this.nhom = nhom;

        docDuLieuDeTai();
        docDuLieuNhom();

        init();
        logic();
    }

    private void init() {
        buttonLuu.setFocusPainted(false);
        buttonLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        labelNhom.setText("Nhóm: " + nhom.getSoNhom());
        labelDeTai.setText("Đề tài: " + deTaiLinkedList.stream().filter(i -> i.getMaDeTai().equals(nhom.getMaDeTai())).findFirst().get().getTenDeTai());
        textFieldLink.setText(nhom.getLinkDeTai());
    }

    private void logic() {
        buttonLuu.addActionListener(e -> {
            nhomLinkedList = nhomLinkedList.stream().map(i -> {
                if (i.getMaLop().equals(nhom.getMaLop())) {
                    if (i.getSoNhom() == nhom.getSoNhom()) {
                        i.setLinkDeTai(textFieldLink.getText());
                    }
                }
                return i;
            }).collect(Collectors.toCollection(LinkedList::new));
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));
                objectOutputStream.writeObject(nhomLinkedList);
                dispose();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void docDuLieuNhom() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/nhom.dat"));
        nhomLinkedList = (LinkedList<Nhom>) objectInputStream.readObject();
    }

    private void docDuLieuDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));
        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
