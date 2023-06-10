package com.quantri;

import com.thucthe.DeTai;
import com.thucthe.Nhom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ChonDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel contentPane;
    private JButton buttonOK;
    private JScrollPane scrollPaneDanhSachDeTai;
    private JTable tableDanhSachDeTai;
    private DefaultTableModel defaultTableModel;
    private LinkedList<DeTai> deTaiLinkedList;
    private int soNhom;
    private DeTai deTaiSelected;
    private LinkedList<Nhom> nhomLinkedList;

    public ChonDeTai(int soNhom) throws Exception {
        setTitle("CHỌN ĐỀ TÀI");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.6;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.6;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);

        this.soNhom = soNhom;

        docDuLieuDeTai();

        init();
        logic();
    }

    private void init() {
        buttonOK.setFocusPainted(false);
        buttonOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        defaultTableModel.setColumnIdentifiers(new Object[]{"Mã đề tài", "Tên đề tài"});
        deTaiLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getMaDeTai(), i.getTenDeTai()}));
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

        buttonOK.addActionListener(e -> {
            if (deTaiSelected != null) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/nhom.dat"));

                    nhomLinkedList = ((LinkedList<Nhom>) objectInputStream.readObject()).stream()
                            .map(i -> {
                                if (i.getSoNhom() == soNhom) {
                                    i.setMaDeTai(deTaiSelected.getMaDeTai());
                                }
                                return i;
                            })
                            .collect(Collectors.toCollection(LinkedList::new));

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));
                    objectOutputStream.writeObject(nhomLinkedList);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                dispose();
            }
        });
    }

    private void docDuLieuDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));

        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
