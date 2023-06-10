package com.nguoidung;

import com.thucthe.Lop;
import com.thucthe.NguoiDangNhap;
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

public class ChonNhom extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelChonNhom;
    private JScrollPane scrollPaneChonNhom;
    private JButton buttonThamGia;
    private JTable tableChonNhom;
    private DefaultTableModel defaultTableModel;
    private Lop lop;
    private LinkedList<Nhom> nhomLinkedList;
    private Nhom nhomSelected;
    private NguoiDangNhap nguoiDangNhap;

    public ChonNhom(NguoiDangNhap nguoiDangNhap, Lop lop) throws Exception {
        setTitle("CHỌN NHÓM");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.5;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelChonNhom);

        this.nguoiDangNhap = nguoiDangNhap;
        this.lop = lop;

        docDuLieuNhom();

        init();
        logic();
    }

    private void init() {
        buttonThamGia.setFocusPainted(false);
        buttonThamGia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableChonNhom = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTableHeader tableHeader = tableChonNhom.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableChonNhom.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Số nhóm", "Số lượng thành viên"});
        nhomLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getSoNhom(), i.getDanhSachMaSV().size() + "/" + i.getSoThanhVien()}));
        tableChonNhom.setModel(defaultTableModel);
        scrollPaneChonNhom.setViewportView(tableChonNhom);
    }

    private void logic() {
        tableChonNhom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nhomSelected = nhomLinkedList.stream().filter(i -> i.getSoNhom() == Integer.parseInt(tableChonNhom.getValueAt(tableChonNhom.getSelectedRow(), 0).toString())).findFirst().get();
            }
        });

        buttonThamGia.addActionListener(e -> {
            if (nhomSelected != null) {
                if (nhomSelected.getDanhSachMaSV().size() < nhomSelected.getSoThanhVien()) {
                    if (!nhomSelected.getDanhSachMaSV().contains(nguoiDangNhap.getMa())) {
                        nhomLinkedList = nhomLinkedList.stream().map(i -> {
                            if (i.getDanhSachMaSV().contains(nguoiDangNhap.getMa())) {
                                i.getDanhSachMaSV().removeIf(i1 -> i1.equals(nguoiDangNhap.getMa()));
                                if (i.getDanhSachMaSV().size() == 0) {
                                    i.setDiem(0);
                                    i.setLinkDeTai("");
                                    i.setGhiChu("");
                                    i.setMaDeTai("");
                                }
                            }
                            return i;
                        }).collect(Collectors.toCollection(LinkedList::new));
                        nhomSelected.getDanhSachMaSV().add(nguoiDangNhap.getMa());

                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/com/data/nhom.dat"));
                            objectOutputStream.writeObject(nhomLinkedList);
                            nhomSelected = null;

                            defaultTableModel.setRowCount(0);
                            nhomLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getSoNhom(), i.getDanhSachMaSV().size() + "/" + i.getSoThanhVien()}));
                            scrollPaneChonNhom.setViewportView(tableChonNhom);

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bạn đã ở trong nhóm này rôi!");
                    }
                }
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
