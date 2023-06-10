package com.quantri;

import com.thucthe.DeTai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedList;

public class DanhSachDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelDanhSachDeTai;
    private JScrollPane scrollPaneDanhSachDeTai;
    private JButton buttonThemDeTai;
    private JButton buttonSuaDeTai;
    private JButton buttonXoaDeTai;
    private JButton buttonLamMoi;
    private JTable tableDanhSachDeTai;
    private DefaultTableModel defaultTableModel;
    private LinkedList<DeTai> deTaiLinkedList;
    private DeTai deTaiSelected;

    public DanhSachDeTai() throws Exception {
        setTitle("DANH SÁCH ĐỀ TÀI");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.5;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelDanhSachDeTai);

        docDuLieuDeTai();

        init();
        logic();
    }

    private void init() {
        buttonThemDeTai.setFocusPainted(false);
        buttonThemDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonSuaDeTai.setFocusPainted(false);
        buttonSuaDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonXoaDeTai.setFocusPainted(false);
        buttonXoaDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonLamMoi.setFocusPainted(false);
        buttonLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableDanhSachDeTai = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTableHeader tableHeader = tableDanhSachDeTai.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableDanhSachDeTai.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Mã đề tài", "Tên đề tài", "Gợi ý thực hiện", "Ghi chú"});
        deTaiLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getMaDeTai(), i.getTenDeTai(), i.getGoiYThucHien(), i.getGhiChu()}));
        tableDanhSachDeTai.setModel(defaultTableModel);
        scrollPaneDanhSachDeTai.setViewportView(tableDanhSachDeTai);
    }

    private void logic() {
        tableDanhSachDeTai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deTaiSelected = deTaiLinkedList.stream()
                        .filter(i -> i.getMaDeTai().equals(tableDanhSachDeTai.getValueAt(tableDanhSachDeTai.getSelectedRow(), 0)))
                        .findFirst()
                        .get();
            }
        });

        buttonXoaDeTai.addActionListener(e -> {
            if (deTaiSelected != null) {
                deTaiLinkedList.removeIf(i -> i.getMaDeTai().equals(deTaiSelected.getMaDeTai()));

                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/detai.dat"));
                    objectOutputStream.writeObject(deTaiLinkedList);
                    deTaiSelected = null;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        buttonSuaDeTai.addActionListener(e -> {
            if (deTaiSelected != null) {
                try {
                    new SuaDeTai(deTaiSelected).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonThemDeTai.addActionListener(e -> {
            try {
                new ThemDeTai().setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonLamMoi.addActionListener(e -> {
            try {
                docDuLieuDeTai();
                defaultTableModel.setRowCount(0);
                deTaiLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getMaDeTai(), i.getTenDeTai(), i.getGoiYThucHien(), i.getGhiChu()}));
                scrollPaneDanhSachDeTai.setViewportView(tableDanhSachDeTai);
                deTaiSelected = null;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void docDuLieuDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));

        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
