package com.quantri;

import com.thucthe.Lop;
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

public class ChamDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelChamDeTai;
    private JScrollPane scrollPaneChamDeTai;
    private JButton buttonLuu;
    private JTable tableChamDeTai;
    private DefaultTableModel defaultTableModel;
    private Lop lop;
    private LinkedList<Nhom> nhomLinkedList;
    private Nhom nhomSelected;

    public ChamDeTai(Lop lop) throws Exception {
        setTitle("CHẤM ĐỀ TÀI");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.5;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelChamDeTai);

        this.lop = lop;

        docDuLieuNhom();

        init();
        logic();
    }

    private void init() {
        buttonLuu.setFocusPainted(false);
        buttonLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableChamDeTai = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                Nhom nhomSelected = nhomLinkedList.stream().filter(i -> i.getSoNhom() == Integer.parseInt(tableChamDeTai.getValueAt(row, 0).toString())).findFirst().get();
                if (nhomSelected.getMaDeTai().equals("")) {
                    return false;
                }
                return column > 2;
            }
        };

        JTableHeader tableHeader = tableChamDeTai.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableChamDeTai.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Nhóm", "Đề tài", "Link", "Điểm", "Ghi chú"});
        nhomLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getSoNhom(), i.getMaDeTai(), i.getLinkDeTai(), i.getDiem(), i.getGhiChu()}));
        tableChamDeTai.setModel(defaultTableModel);
        scrollPaneChamDeTai.setViewportView(tableChamDeTai);
    }

    private void logic() {
        tableChamDeTai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nhomSelected = nhomLinkedList.stream()
                        .filter(i -> i.getSoNhom() == (int) tableChamDeTai.getValueAt(tableChamDeTai.getSelectedRow(), 0))
                        .findFirst()
                        .get();
            }
        });

        buttonLuu.addActionListener(e -> {
            tableChamDeTai.editCellAt(-1, -1);

            for (int i = 0; i < tableChamDeTai.getRowCount(); i++) {
                nhomLinkedList.get(i).setDiem(Float.parseFloat(tableChamDeTai.getValueAt(i, 3).toString()));
                nhomLinkedList.get(i).setGhiChu(tableChamDeTai.getValueAt(i, 4).toString());
            }

            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));
                objectOutputStream.writeObject(nhomLinkedList);
                scrollPaneChamDeTai.setViewportView(tableChamDeTai);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void docDuLieuNhom() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/nhom.dat"));

        nhomLinkedList = ((LinkedList<Nhom>) objectInputStream.readObject()).stream()
                .filter(i -> i.getMaLop().equals(lop.getMaLop()))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
