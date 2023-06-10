package com;

import com.dangnhap.DangNhap;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new DangNhap().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
