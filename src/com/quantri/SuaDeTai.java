package com.quantri;

import com.thucthe.DeTai;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SuaDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelSuaDeTai;
    private JTextField textFieldMaDeTai;
    private JTextField textFieldTenDeTai;
    private JButton buttonLuu;
    private JTextArea textAreaGoiYThucHien;
    private JTextArea textAreaGhiChu;
    private DeTai deTai;
    private LinkedList<DeTai> deTaiLinkedList;

    public SuaDeTai(DeTai detai) throws Exception {
        setTitle("SỬA ĐỀ TÀI");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.4;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelSuaDeTai);

        this.deTai = detai;

        docDuLieuDeTai();

        init();
        logic();
    }

    private void init() {
        buttonLuu.setFocusPainted(false);
        buttonLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        textFieldMaDeTai.setText(deTai.getMaDeTai());
        textFieldTenDeTai.setText(deTai.getTenDeTai());
        textAreaGoiYThucHien.setText(deTai.getGoiYThucHien());
        textAreaGhiChu.setText(deTai.getGhiChu());
    }

    private void logic() {
        buttonLuu.addActionListener(e -> {
            if (textFieldMaDeTai.getText().trim().equals("") || textFieldTenDeTai.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng thông tin yêu cầu!");
            } else {
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/detai.dat"));

                    objectOutputStream.writeObject(deTaiLinkedList.stream().map(i -> {
                            if (i.getMaDeTai().equals(deTai.getMaDeTai())) {
                                i.setTenDeTai(textFieldTenDeTai.getText());
                                i.setGoiYThucHien(textAreaGoiYThucHien.getText());
                                i.setGhiChu(textAreaGhiChu.getText());
                            }
                            return i;
                        }).collect(Collectors.toCollection(LinkedList::new))
                    );
                    dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private void docDuLieuDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));

        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
