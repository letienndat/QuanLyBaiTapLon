package com.dangnhap;

import com.thucthe.TaiKhoan;
import com.trangchu.TrangChu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Optional;

public class DangNhap extends JFrame {
    private JPanel panelDangNhap;
    private JTextField textFieldTenDangNhap;
    private JPasswordField passwordFieldMatKhau;
    private JButton buttonDangNhap;
    private LinkedList<TaiKhoan> taiKhoanLinkedList;

    public DangNhap() throws Exception {
        super("ĐĂNG NHẬP");
        setSize(new Dimension(500, 300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panelDangNhap);
        setResizable(false);
        init();
        logic();
        docDuLieuTaiKhoan();
    }

    private void init() {
        buttonDangNhap.setFocusPainted(false);
        buttonDangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void logic() {
        buttonDangNhap.addActionListener(e -> {
            Optional<TaiKhoan> taiKhoanFind = taiKhoanLinkedList.stream().filter(i -> i.getTenTK().equals(textFieldTenDangNhap.getText())).findFirst();
            if (taiKhoanFind.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Đăng nhập không thành công, vui lòng thử lại!");
            } else {
                if (taiKhoanFind.get().getMatKhau().equals(passwordFieldMatKhau.getText())) {
                    dispose();
                    try {
                        new TrangChu(textFieldTenDangNhap.getText()).setVisible(true);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng nhập không thành công, vui lòng thử lại!");
                }
            }
        });

        textFieldTenDangNhap.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if ((int) e.getKeyChar() == 10) {
                    buttonDangNhap.doClick();
                }
            }
        });

        passwordFieldMatKhau.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if ((int) e.getKeyChar() == 10) {
                    buttonDangNhap.doClick();
                }
            }
        });
    }

    private void docDuLieuTaiKhoan() throws Exception {
        taiKhoanLinkedList = (LinkedList<TaiKhoan>) new ObjectInputStream(new FileInputStream("src/com/data/taikhoan.dat")).readObject();
    }
}
