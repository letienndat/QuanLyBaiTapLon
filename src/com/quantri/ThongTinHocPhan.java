package com.quantri;

import com.thucthe.GiangVien;
import com.thucthe.HocPhan;
import com.thucthe.Lop;
import com.thucthe.NguoiDangNhap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ThongTinHocPhan extends JDialog {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel panelThongTinHocPhan;
    private JLabel labelNamHoc;
    private JLabel labelHocPhan;
    private JScrollPane scrollPaneThongTinLopHocPhan;
    private JButton buttonGiaoDeTai;
    private JButton buttonChamDeTai;
    private JTable tableThongTinLopHocPhan;
    private DefaultTableModel defaultTableModel;
    private HocPhan hocPhan;
    private LinkedList<Lop> lopLinkedList;
    private Lop lopSelected;
    private LinkedList<GiangVien> giangVienLinkedList;
    private boolean selected = false;

    public ThongTinHocPhan(HocPhan hocPhan) throws Exception {
        setTitle("THÔNG TIN HỌC PHẦN");
        setModal(true);
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.5;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelThongTinHocPhan);

        this.hocPhan = hocPhan;

        docDanhSachGiangVien();
        docDanhSachLop();
        init();
        logic();
    }

    private void init() {
        buttonGiaoDeTai.setFocusPainted(false);
        buttonGiaoDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonChamDeTai.setFocusPainted(false);
        buttonChamDeTai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tableThongTinLopHocPhan = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTableHeader tableHeader = tableThongTinLopHocPhan.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tableThongTinLopHocPhan.getTableHeader().setReorderingAllowed(false);
            }
        });

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"Mã lớp", "Giảng viên", "Sĩ số"});
        lopLinkedList.forEach(i -> defaultTableModel.addRow(new Object[]{i.getMaLop(), giangVienLinkedList.stream().filter(i1 -> i1.getMa().equals(i.getMaGiangVien())).findFirst().get().getHoTen(), i.getSiSo()}));
        tableThongTinLopHocPhan.setModel(defaultTableModel);
        scrollPaneThongTinLopHocPhan.setViewportView(tableThongTinLopHocPhan);
    }

    private void logic() {
        labelNamHoc.setText("Năm học: " + hocPhan.getNam());
        labelHocPhan.setText("Học phần: " + hocPhan.getTenHP());

        tableThongTinLopHocPhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = true;
                lopSelected = lopLinkedList.stream()
                        .filter(i -> i.getMaLop().equals(tableThongTinLopHocPhan.getValueAt(tableThongTinLopHocPhan.getSelectedRow(), 0)))
                        .findFirst()
                        .get();
            }
        });

        buttonGiaoDeTai.addActionListener(e -> {
            if (selected) {
                try {
                    new GiaoDeTai(lopSelected).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        buttonChamDeTai.addActionListener(e -> {
            if (selected) {
                try {
                    new ChamDeTai(lopSelected).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void docDanhSachLop() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/lop.dat"));
        lopLinkedList = ((LinkedList<Lop>) objectInputStream.readObject()).stream()
                .filter(i -> i.getMaHP().equals(hocPhan.getMaHP()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void docDanhSachGiangVien() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/thongtinnguoidung.dat"));

        LinkedList<GiangVien> objects = new LinkedList<>();
        for (NguoiDangNhap i : ((LinkedList<NguoiDangNhap>) objectInputStream.readObject())) {
            if (i instanceof GiangVien) {
                objects.add((GiangVien) i);
            }
        }
        giangVienLinkedList = objects;
    }
}
