package com.nguoidung;

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

public class ThongTinHocPhan extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelThongTinHocPhan;
    private JScrollPane scrollPaneThongTinHocPhan;
    private JButton buttonChonNhom;
    private JButton buttonNopBTL;
    private JButton buttonLamMoi;
    private JLabel labelMaSV;
    private JLabel labelHoTen;
    private JLabel labelTenHP;
    private JTable tableThongTinHocPhan;
    private DefaultTableModel defaultTableModel;
    private HocPhan hocPhan;
    private LinkedList<SinhVien> sinhVienLinkedList;
    private LinkedList<DeTai> deTaiLinkedList;
    private LinkedList<Nhom> nhomLinkedList;
    private Lop lop;
    private NguoiDangNhap nguoiDangNhap;

    public ThongTinHocPhan(NguoiDangNhap nguoiDangNhap, HocPhan hocPhan) throws Exception {
        setTitle("THÔNG TIN HỌC PHẦN");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.4;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.4;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelThongTinHocPhan);

        this.nguoiDangNhap = nguoiDangNhap;
        this.hocPhan = hocPhan;

        timDuLieuLop();
        docDanhSachDeTai();
        docDanhSachNhom();
        docDanhSachSinhVien();

        init();
        logic();
    }

    private void init() {
        labelMaSV.setText("Mã SV: " + nguoiDangNhap.getMa());
        labelHoTen.setText("Họ Tên: " + nguoiDangNhap.getHoTen());
        labelTenHP.setText("Học phần: " + hocPhan.getTenHP());

        buttonChonNhom.setFocusPainted(false);
        buttonChonNhom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonNopBTL.setFocusPainted(false);
        buttonNopBTL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonLamMoi.setFocusPainted(false);
        buttonLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableThongTinHocPhan = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTableHeader tableHeader = tableThongTinHocPhan.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableThongTinHocPhan.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Số thứ tự", "Mã sinh viên", "Tên sinh viên", "Nhóm", "Đề tài", "Điểm", "Ghi chú"});
        AtomicInteger stt = new AtomicInteger(1);
        sinhVienLinkedList.forEach(i -> {
            Optional<Nhom> nhom = nhomLinkedList.stream().filter(i1 -> i1.getDanhSachMaSV().contains(i.getMa())).findFirst();
            defaultTableModel.addRow(new Object[]{stt.getAndIncrement(), i.getMa(), i.getHoTen(), nhom.isEmpty() ? "" : nhom.get().getSoNhom(), (nhom.isEmpty() || nhom.get().getMaDeTai().equals("")) ? "" : deTaiLinkedList.stream().filter(i1 -> i1.getMaDeTai().equals(nhom.get().getMaDeTai())).findFirst().get().getTenDeTai(), nhom.isEmpty() ? "" : nhom.get().getDiem(), nhom.isEmpty() ? "" : nhom.get().getGhiChu()});
        });

        tableThongTinHocPhan.setModel(defaultTableModel);

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
        tableThongTinHocPhan.setRowSorter(sorter);
        sorter.sort();

        scrollPaneThongTinHocPhan.setViewportView(tableThongTinHocPhan);
    }

    private void logic() {

        buttonChonNhom.addActionListener(e -> {
            try {
                new ChonNhom(nguoiDangNhap, lop).setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttonNopBTL.addActionListener(e -> {
            Optional<Nhom> nhomNopBai = nhomLinkedList.stream().filter(i -> i.getDanhSachMaSV().contains(nguoiDangNhap.getMa())).findFirst();
            if (nhomNopBai.isPresent()) {
                try {
                    if (!nhomNopBai.get().getMaDeTai().equals("")) {
                        new NopBTL(nhomNopBai.get()).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Chưa được giao đề tài!");
                    }
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
                    defaultTableModel.addRow(new Object[]{stt.getAndIncrement(), i.getMa(), i.getHoTen(), nhom.isEmpty() ? "" : nhom.get().getSoNhom(), (nhom.isEmpty() || nhom.get().getMaDeTai().equals("")) ? "" : deTaiLinkedList.stream().filter(i1 -> i1.getMaDeTai().equals(nhom.get().getMaDeTai())).findFirst().get().getTenDeTai(), nhom.isEmpty() ? "" : nhom.get().getDiem(), nhom.isEmpty() ? "" : nhom.get().getGhiChu()});
                });

                tableThongTinHocPhan.setModel(defaultTableModel);

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
                tableThongTinHocPhan.setRowSorter(sorter);
                sorter.sort();

                scrollPaneThongTinHocPhan.setViewportView(tableThongTinHocPhan);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void timDuLieuLop() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/lop.dat"));

        lop = ((LinkedList<Lop>) objectInputStream.readObject()).stream()
                .filter(i -> i.getMaHP().equals(hocPhan.getMaHP()))
                .findFirst()
                .get();

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
