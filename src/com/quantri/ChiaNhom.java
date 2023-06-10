package com.quantri;

import com.thucthe.Lop;
import com.thucthe.Nhom;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ChiaNhom extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelChiaNhom;
    private JTextField textFieldSoNhom;
    private JTextField textFieldSoThanhVienMoiNhom;
    private JButton buttonTaoNhom;
    private Lop lop;
    private LinkedList<Nhom> nhomLinkedList;

    public ChiaNhom(Lop lop) throws Exception {
        setTitle("CHIA NHÓM");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.7;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.65;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelChiaNhom);
        setResizable(false);

        this.lop = lop;

        docDuLieuNhom();

        init();
        logic();
    }

    private void init() {
        buttonTaoNhom.setFocusPainted(false);
        buttonTaoNhom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void logic() {
        buttonTaoNhom.addActionListener(e -> {
            if(nhomLinkedList.stream().filter(i -> i.getMaLop().equals(lop.getMaLop())).findFirst().isEmpty()) {
                try {
                    int soNhom = Integer.parseInt(textFieldSoNhom.getText());
                    int soThanhVien = Integer.parseInt(textFieldSoThanhVienMoiNhom.getText());
                    if (soNhom * soThanhVien >= lop.getSiSo()) {
                        for (int i = 0; i < soNhom; i++) {
                            nhomLinkedList.add(new Nhom(i + 1, new LinkedList<>(), "", lop.getMaLop(), "", 0, "", soThanhVien));
                        }
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));
                        objectOutputStream.writeObject(nhomLinkedList);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Không thể xử lý, vui lòng nhập lại!");
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Không thể xử lý, vui lòng nhập lại!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Xin lỗi, lớp này đã được tạo nhóm trước đó!");
            }
        });
    }

    private void docDuLieuNhom() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/nhom.dat"));

        nhomLinkedList = (LinkedList<Nhom>) objectInputStream.readObject();
    }
}
