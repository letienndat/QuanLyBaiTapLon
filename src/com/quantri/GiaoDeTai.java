package com.quantri;

import com.thucthe.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GiaoDeTai extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelGiaoDeTai;
    private JScrollPane scrollPaneGiaoDeTai;
    private JButton buttonQuanLyBTL;
    private JButton buttonChiaNhom;
    private JButton buttonChonDeTai;
    private JButton buttonLamMoi;
    private JTable tableGiaoDeTai;
    private DefaultTableModel defaultTableModel;
    private Lop lop;
    private LinkedList<SinhVien> sinhVienLinkedList;
    private LinkedList<Nhom> nhomLinkedList;
    private LinkedList<DeTai> deTaiLinkedList;
    private boolean selected = false;
    private Nhom nhomSelected;

    public GiaoDeTai(Lop lop) throws Exception {
        setTitle("GIAO ĐỀ TÀI");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.5;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelGiaoDeTai);

        this.lop = lop;

        docDanhSachDeTai();
        docDanhSachNhom();
        docDanhSachSinhVien();
        init();
        logic();
    }

    private void init() {
        buttonQuanLyBTL.setFocusPainted(false);
        buttonQuanLyBTL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonChiaNhom.setFocusPainted(false);
        buttonChiaNhom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonChonDeTai.setFocusPainted(false);
        buttonChonDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonLamMoi.setFocusPainted(false);
        buttonLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableGiaoDeTai = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTableHeader tableHeader = tableGiaoDeTai.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableGiaoDeTai.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Số thứ tự", "Mã sinh viên", "Tên sinh viên", "Nhóm", "Ghi chú", "Đề tài"});
        AtomicInteger stt = new AtomicInteger(1);
        sinhVienLinkedList.forEach(i -> {
            Optional<Nhom> nhom = nhomLinkedList.stream().filter(i1 -> i1.getDanhSachMaSV().contains(i.getMa())).findFirst();
            defaultTableModel.addRow(new Object[]{stt.getAndIncrement(), i.getMa(), i.getHoTen(), nhom.isEmpty() ? "" : nhom.get().getSoNhom(), nhom.isEmpty() ? "" : nhom.get().getGhiChu(), (nhom.isEmpty() || nhom.get().getMaDeTai().equals("")) ? "" : deTaiLinkedList.stream().filter(i1 -> i1.getMaDeTai().equals(nhom.get().getMaDeTai())).findFirst().get().getTenDeTai()});
        });

        tableGiaoDeTai.setModel(defaultTableModel);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(defaultTableModel);
        sorter.setComparator(3, (o1, o2) -> {
            String s1 = o1 != null ? o1.toString() : "";
            String s2 = o2 != null ? o2.toString() : "";

            if (s1.equals(s2)) {
                return 0; // Các giá trị bằng nhau
            } else if (s1.equals("") && !s2.equals("")) {
                return 1; // Giá trị "" sẽ đặt ở cuối cùng
            } else if (!s1.equals("") && s2.equals("")) {
                return -1; // Giá trị "" sẽ đặt ở cuối cùng
            } else {
                return s1.compareTo(s2); // So sánh các giá trị khác nhau theo thứ tự tăng dần
            }
        });
        tableGiaoDeTai.setRowSorter(sorter);
        sorter.sort();

        scrollPaneGiaoDeTai.setViewportView(tableGiaoDeTai);
    }

    private void logic() {
        tableGiaoDeTai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = true;
                Optional<Nhom> nhomSelectedTemp = nhomLinkedList.stream()
                        .filter(i -> i.getDanhSachMaSV().contains(tableGiaoDeTai.getValueAt(tableGiaoDeTai.getSelectedRow(), 1)))
                        .findFirst();
                if (nhomSelectedTemp.isPresent()) {
                    nhomSelected = nhomSelectedTemp.get();
                    selected = true;
                } else {
                    nhomSelected = null;
                    selected = false;
                }
            }
        });

        buttonChonDeTai.addActionListener(e -> {
            if (selected) {
                try {
                    new ChonDeTai(nhomSelected.getSoNhom()).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonQuanLyBTL.addActionListener(e -> {
            try {
                new DanhSachDeTai().setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonChiaNhom.addActionListener(e -> {
            if (nhomLinkedList != null) {
                try {
                    new ChiaNhom(lop).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonLamMoi.addActionListener(e -> {
            try {
                docDanhSachDeTai();
                docDanhSachNhom();
                docDanhSachSinhVien();

                defaultTableModel.setRowCount(0);
                AtomicInteger stt = new AtomicInteger(1);
                sinhVienLinkedList.forEach(i -> {
                    Optional<Nhom> nhom = nhomLinkedList.stream().filter(i1 -> i1.getDanhSachMaSV().contains(i.getMa())).findFirst();
                    defaultTableModel.addRow(new Object[]{stt.getAndIncrement(), i.getMa(), i.getHoTen(), nhom.isEmpty() ? "" : nhom.get().getSoNhom(), nhom.isEmpty() ? "" : nhom.get().getGhiChu(), (nhom.isEmpty() || nhom.get().getMaDeTai().equals("")) ? "" : deTaiLinkedList.stream().filter(i1 -> i1.getMaDeTai().equals(nhom.get().getMaDeTai())).findFirst().get().getTenDeTai()});
                });

                tableGiaoDeTai.setModel(defaultTableModel);

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(defaultTableModel);
                sorter.setComparator(3, (o1, o2) -> {
                    String s1 = o1 != null ? o1.toString() : "";
                    String s2 = o2 != null ? o2.toString() : "";

                    if (s1.equals(s2)) {
                        return 0; // Các giá trị bằng nhau
                    } else if (s1.equals("") && !s2.equals("")) {
                        return 1; // Giá trị "" sẽ đặt ở cuối cùng
                    } else if (!s1.equals("") && s2.equals("")) {
                        return -1; // Giá trị "" sẽ đặt ở cuối cùng
                    } else {
                        return s1.compareTo(s2); // So sánh các giá trị khác nhau theo thứ tự tăng dần
                    }
                });
                tableGiaoDeTai.setRowSorter(sorter);
                sorter.sort();

                scrollPaneGiaoDeTai.setViewportView(tableGiaoDeTai);

                selected = false;
                nhomSelected = null;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void docDanhSachSinhVien() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/thongtinnguoidung.dat"));

        LinkedList<SinhVien> objects = new LinkedList<>();
        for (NguoiDangNhap i : ((LinkedList<NguoiDangNhap>) objectInputStream.readObject())) {
            if (lop.getDanhSachMaSV().contains(i.getMa())) {
                objects.add((SinhVien) i);
            }
        }
        sinhVienLinkedList = objects;
    }

    private void docDanhSachNhom() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/nhom.dat"));

        nhomLinkedList = ((LinkedList<Nhom>) objectInputStream.readObject()).stream()
                .filter(i -> i.getMaLop().equals(lop.getMaLop()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void docDanhSachDeTai() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/detai.dat"));

        deTaiLinkedList = (LinkedList<DeTai>) objectInputStream.readObject();
    }
}
