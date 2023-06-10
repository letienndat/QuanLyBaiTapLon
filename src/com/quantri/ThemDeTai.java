package com.quantri;

import com.thucthe.DeTai;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

public class ThemDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelThemDeTai;
    private JTextField textFieldMaDeTai;
    private JTextField textFieldTenDeTai;
    private JTextArea textAreaGoiYThucHien;
    private JTextArea textAreaGhiChu;
    private JButton buttonThemDeTai;
    private LinkedList<DeTai> deTaiLinkedList;

    public ThemDeTai() throws Exception {
        setTitle("THÊM ĐỀ TÀI");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.4;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelThemDeTai);

        docDuLieuDeTai();

        init();
        logic();
    }

    private void init() {
        buttonThemDeTai.setFocusPainted(false);
        buttonThemDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void logic() {
        buttonThemDeTai.addActionListener(e -> {
            if (textFieldMaDeTai.getText().trim().equals("") || textFieldTenDeTai.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đúng thông tin yêu cầu!");
            } else if (!textFieldMaDeTai.getText().trim().equals("")) {
                if (deTaiLinkedList.stream().filter(i -> i.getMaDeTai().equals(textFieldMaDeTai.getText())).findFirst().isPresent()) {
                    JOptionPane.showMessageDialog(null, "Đã tồn tại mã đề tài này, vui lòng nhập lại!");
                } else {
                    deTaiLinkedList.add(new DeTai(textFieldMaDeTai.getText(), textFieldTenDeTai.getText(), textAreaGoiYThucHien.getText(), textAreaGhiChu.getText()));
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/detai.dat"));
                        objectOutputStream.writeObject(deTaiLinkedList);
                        dispose();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    private void docDuLieuDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));

        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
