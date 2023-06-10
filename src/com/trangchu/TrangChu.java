package com.trangchu;

import com.dangnhap.DangNhap;
import com.quantri.ThongTinHocPhan;
import com.thucthe.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TrangChu extends JFrame {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean isAdmin;
    private JPanel panelTrangChu;
    private JLabel labelMa;
    private JLabel labelChucVu;
    private JLabel labelHoTen;
    private JComboBox comboBoxNamHoc;
    private JComboBox comboBoxTenHocPhan;
    private JTable tableThongTinHocPhan;
    private JButton buttonDangXuat;
    private JScrollPane scrollPaneThongTinHocPhan;
    private DefaultTableModel defaultTableModel;
    private NguoiDangNhap nguoiDangNhap;
    private String tenTK;
    private LinkedList<HocPhan> hocPhanLinkedList;

    public TrangChu(String tenTK) throws Exception {
        super("TRANG CHỦ");
        double width = screenSize.getWidth() - screenSize.getWidth() * 0.2;
        double height = screenSize.getHeight() - screenSize.getHeight() * 0.2;
        setSize(new Dimension((int) width, (int) height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panelTrangChu);
        this.tenTK = tenTK;

        docThongTinNguoiDangNhap();
        docThongTinDanhSachHocPhan();
        init();
        logic();
    }

    private void init() {
        buttonDangXuat.setFocusPainted(false);
        buttonDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        defaultTableModel.setColumnIdentifiers(new Object[]{"Mã học phần", "Tên học phần", "Số tín chỉ"});
        hocPhanLinkedList.forEach(i -> {
            defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
        });
        tableThongTinHocPhan.setModel(defaultTableModel);
        scrollPaneThongTinHocPhan.setViewportView(tableThongTinHocPhan);
    }

    private void logic() {
        labelMa.setText("Mã: " + nguoiDangNhap.getMa());
        labelHoTen.setText("Họ tên: " + nguoiDangNhap.getHoTen());
        labelChucVu.setText("Chức vụ: " + (nguoiDangNhap instanceof GiangVien ? "Giảng viên" : "Sinh viên"));

        hocPhanLinkedList.stream().map(HocPhan::getTenHP).distinct().forEach(i -> comboBoxTenHocPhan.addItem(i));

        comboBoxNamHoc.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultTableModel.setRowCount(0);
                if (comboBoxNamHoc.getSelectedItem().equals("...") && comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.forEach(i -> {
                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                    });
                } else if (!comboBoxNamHoc.getSelectedItem().equals("...") && !comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getNam() == Integer.parseInt(comboBoxNamHoc.getSelectedItem().toString()) && i.getTenHP().equals(comboBoxTenHocPhan.getSelectedItem()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                } else if (comboBoxNamHoc.getSelectedItem().equals("...") && !comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getTenHP().equals(comboBoxTenHocPhan.getSelectedItem()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                } else {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getNam() == Integer.parseInt(comboBoxNamHoc.getSelectedItem().toString()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                }
                scrollPaneThongTinHocPhan.setViewportView(tableThongTinHocPhan);
            }
        });

        comboBoxTenHocPhan.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultTableModel.setRowCount(0);
                if (comboBoxNamHoc.getSelectedItem().equals("...") && comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.forEach(i -> {
                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                    });
                } else if (!comboBoxNamHoc.getSelectedItem().equals("...") && !comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getNam() == Integer.parseInt(comboBoxNamHoc.getSelectedItem().toString()) && i.getTenHP().equals(comboBoxTenHocPhan.getSelectedItem()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                } else if (comboBoxNamHoc.getSelectedItem().equals("...") && !comboBoxTenHocPhan.getSelectedItem().equals("...")) {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getTenHP().equals(comboBoxTenHocPhan.getSelectedItem()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                } else {
                    hocPhanLinkedList.stream()
                            .filter(i -> i.getNam() == Integer.parseInt(comboBoxNamHoc.getSelectedItem().toString()))
                            .forEach(i -> {
                                        defaultTableModel.addRow(new Object[]{i.getMaHP(), i.getTenHP(), i.getSoTinChi()});
                                    }
                            );
                }
                scrollPaneThongTinHocPhan.setViewportView(tableThongTinHocPhan);
            }
        });

        tableThongTinHocPhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    HocPhan hocPhan = hocPhanLinkedList.stream()
                            .filter(i -> i.getMaHP().equals(tableThongTinHocPhan.getValueAt(tableThongTinHocPhan.getSelectedRow(), 0)))
                            .findFirst()
                            .get();
                    if (isAdmin) {
                        try {
                            new ThongTinHocPhan(hocPhan).setVisible(true);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        try {
                            new com.nguoidung.ThongTinHocPhan(nguoiDangNhap, hocPhan).setVisible(true);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new DangNhap().setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void docThongTinNguoiDangNhap() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/thongtinnguoidung.dat"));

        nguoiDangNhap = ((LinkedList<NguoiDangNhap>) objectInputStream.readObject()).stream().filter(i -> i.getTenTK().equals(this.tenTK)).findFirst().get();
        if (nguoiDangNhap instanceof SinhVien) {
            isAdmin = false;
        } else {
            isAdmin = true;
        }
    }

    private void docThongTinDanhSachHocPhan() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/com/data/hocphan.dat"));

        if (isAdmin) {
            hocPhanLinkedList = (LinkedList<HocPhan>) objectInputStream.readObject();
        } else {
            ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream("src/com/data/lop.dat"));
            List<String> maHocPhanLinkedList = ((LinkedList<Lop>) objectInputStream1.readObject()).stream()
                    .filter(i -> i.getDanhSachMaSV().contains(nguoiDangNhap.getMa()))
                    .map(Lop::getMaHP)
                    .collect(Collectors.toList());

            hocPhanLinkedList = ((LinkedList<HocPhan>) objectInputStream.readObject()).stream().filter(i -> maHocPhanLinkedList.contains(i.getMaHP())).collect(Collectors.toCollection(LinkedList::new));
        }
    }
}
