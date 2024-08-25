package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entities.NhanVien;
import entities.TaiKhoan;
import main.local_host;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JDateChooser;

public class QuanLyNhanVien extends JPanel implements ActionListener, MouseListener {
	private JTable tableDSTK, tableNgungLV, tableDangLV;
	private JTextField txtManhanvien, txtTenNV, txtDiachi, txtSDT, txtEmail, txtTimnv, txtTimmNV;
	private JButton btnThem, btnSua, btnLuu;
	private JLabel txtMess;
	private DefaultTableModel modelnv;
	JComboBox cbxgt;
	JComboBox cbxcv;
	private JTextField txtCMND;
	private DefaultTableModel modeltk;
	private JButton btnHuy;
	private JRadioButton rdbtnGioitinh;
	private JComboBox cbxChucvu;
	private JDateChooser dateChooserNgaySinh;
	private JRadioButton rdbtnTrangthai;
	private int trangthainut;
	private QuanLyTaiKhoan qltk;
	private JPanel pnTaikhoan;
	private Registry registry;

	/**
	 * Create the panel.
	 */
	public QuanLyNhanVien() {
		try {
			local_host local = new local_host();
			registry = LocateRegistry.getRegistry(local.host(), 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UiNhanvien();
		updateTableData();
		updateTableDataNgungLamViec();
	}

	private void UiNhanvien() {
		setPreferredSize(new Dimension(934, 687));
		setLayout(new CardLayout(0, 0));

		JTabbedPane tabbedNVTK = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedNVTK, "name_369984908895700");

		JPanel pnNhanvien = new JPanel();
		pnNhanvien.setBackground(new Color(255, 255, 255));
		tabbedNVTK.addTab("Nhân Viên", null, pnNhanvien, null);

		JPanel pnThietlapTT = new JPanel();
		pnThietlapTT.setBackground(new Color(255, 255, 255));
		pnThietlapTT.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2),
				"Thi\u1EBFt L\u1EADp Th\u00F4ng Tin Nh\u00E2n Vi\u00EAn", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		JTabbedPane tabbedLamviec = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_pnNhanvien = new GroupLayout(pnNhanvien);
		gl_pnNhanvien.setHorizontalGroup(gl_pnNhanvien.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING,
						gl_pnNhanvien.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pnThietlapTT, GroupLayout.PREFERRED_SIZE, 910, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addGroup(gl_pnNhanvien.createSequentialGroup().addContainerGap()
						.addComponent(tabbedLamviec, GroupLayout.PREFERRED_SIZE, 907, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(12, Short.MAX_VALUE)));
		gl_pnNhanvien.setVerticalGroup(gl_pnNhanvien.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnNhanvien.createSequentialGroup()
						.addComponent(pnThietlapTT, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedLamviec, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JPanel pnLamviec = new JPanel();
		pnLamviec.setBackground(new Color(255, 255, 255));
		tabbedLamviec.addTab("Đang Làm Việc", null, pnLamviec, null);

		JLabel lbTimNV = new JLabel("Tìm Kiếm Nhân Viên:");
		lbTimNV.setFont(new Font("Arial", Font.BOLD, 12));

		txtTimmNV = new JTextField();
		txtTimmNV.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTimmNV.setColumns(10);

		JLabel lbLocgt = new JLabel("Lọc Theo Giới Tính :");
		lbLocgt.setFont(new Font("Arial", Font.BOLD, 12));

		cbxgt = new JComboBox();
		cbxgt.setModel(new DefaultComboBoxModel(new String[] { "Nam ", "Nữ" }));
		cbxgt.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lbLoccv = new JLabel("Lọc Theo Chức Vụ :");
		lbLoccv.setFont(new Font("Arial", Font.BOLD, 12));

		cbxcv = new JComboBox();
		cbxcv.setModel(new DefaultComboBoxModel(new String[] { "Nhân Viên ", "Quản Lý" }));
		cbxcv.setFont(new Font("Arial", Font.PLAIN, 12));

		JScrollPane scrollPaneDangLV = new JScrollPane();
		GroupLayout gl_pnLamviec = new GroupLayout(pnLamviec);
		gl_pnLamviec.setHorizontalGroup(gl_pnLamviec.createParallelGroup(Alignment.LEADING).addGroup(gl_pnLamviec
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnLamviec.createParallelGroup(Alignment.LEADING).addGroup(gl_pnLamviec
						.createSequentialGroup()
						.addComponent(lbTimNV, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtTimmNV, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(lbLocgt, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(cbxgt, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(lbLoccv, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cbxcv, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneDangLV, GroupLayout.PREFERRED_SIZE, 880, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(12, Short.MAX_VALUE)));
		gl_pnLamviec.setVerticalGroup(gl_pnLamviec.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnLamviec.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnLamviec.createParallelGroup(Alignment.BASELINE).addComponent(lbTimNV)
								.addComponent(txtTimmNV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lbLocgt)
								.addComponent(cbxgt, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbLoccv)
								.addComponent(cbxcv, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneDangLV, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(15, Short.MAX_VALUE)));

		tableDangLV = new JTable();
		modelnv = new DefaultTableModel();
		modelnv.addColumn("Mã NV");
		modelnv.addColumn("Họ Tên ");
		modelnv.addColumn("Ngày Sinh");
		modelnv.addColumn("Địa Chỉ");
		modelnv.addColumn("CMND");
		modelnv.addColumn("so dien thoai");
		modelnv.addColumn("email");
		modelnv.addColumn("gioi tinh");

		modelnv.addColumn("chuc vu");


		tableDangLV = new JTable(modelnv);

		scrollPaneDangLV.setViewportView(tableDangLV);
		pnLamviec.setLayout(gl_pnLamviec);

		JPanel pnNgunglamviec = new JPanel();
		pnNgunglamviec.setBackground(new Color(255, 255, 255));
		tabbedLamviec.addTab("Ngưng Làm Việc", null, pnNgunglamviec, null);

		JLabel lbTimkiem = new JLabel("Tìm Kiếm Nhân Viên:");
		lbTimkiem.setFont(new Font("Arial", Font.BOLD, 12));

		txtTimnv = new JTextField();
		txtTimnv.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTimnv.setColumns(10);

		JLabel lbLocGT = new JLabel("Lọc Theo Giới Tính :");
		lbLocGT.setFont(new Font("Arial", Font.BOLD, 12));

		JComboBox cbxGT = new JComboBox();
		cbxGT.setModel(new DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));
		cbxGT.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lbLocCV = new JLabel("Lọc Theo Chức Vụ :");
		lbLocCV.setFont(new Font("Arial", Font.BOLD, 12));

		JComboBox cbxGT_1 = new JComboBox();
		cbxGT_1.setModel(new DefaultComboBoxModel(new String[] { "Nhân Viên", "Quản Lý" }));
		cbxGT_1.setFont(new Font("Arial", Font.PLAIN, 12));

		JScrollPane scrollPaneDanglm = new JScrollPane();
		GroupLayout gl_pnNgunglamviec = new GroupLayout(pnNgunglamviec);
		gl_pnNgunglamviec.setHorizontalGroup(gl_pnNgunglamviec.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnNgunglamviec.createSequentialGroup().addContainerGap().addGroup(gl_pnNgunglamviec
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnNgunglamviec.createSequentialGroup().addComponent(lbTimkiem)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtTimnv, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE).addGap(18)
								.addComponent(lbLocGT).addGap(18)
								.addComponent(cbxGT, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lbLocCV, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(cbxGT_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneDanglm, GroupLayout.PREFERRED_SIZE, 879, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_pnNgunglamviec.setVerticalGroup(gl_pnNgunglamviec.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnNgunglamviec.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnNgunglamviec.createParallelGroup(Alignment.BASELINE).addComponent(lbTimkiem)
								.addComponent(txtTimnv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cbxGT_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbLocCV)
								.addComponent(cbxGT, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbLocGT))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneDanglm, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(12, Short.MAX_VALUE)));

		tableNgungLV = new JTable();
		tableNgungLV.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null, null }, },
						new String[] { "M\u00E3 Nh\u00E2n Vi\u00EAn", "H\u1ECD v\u00E0 T\u00EAn", "Ng\u00E0y Sinh",
								"\u0110\u1ECBa Ch\u1EC9", "CMND", "S\u0110T", "email", "Gi\u1EDBi T\u00EDnh",
								"Ch\u1EE9c V\u1EE5" }));
		scrollPaneDanglm.setViewportView(tableNgungLV);
		pnNgunglamviec.setLayout(gl_pnNgunglamviec);

		JLabel lbMaNV = new JLabel("Mã Nhân VIên:");
		lbMaNV.setBounds(16, 30, 80, 14);
		lbMaNV.setFont(new Font("Arial", Font.BOLD, 12));

		txtManhanvien = new JTextField();
		txtManhanvien.setBackground(new Color(255, 255, 255));
		txtManhanvien.setBounds(123, 27, 299, 20);
		txtManhanvien.setEditable(false);
		txtManhanvien.setColumns(10);

		JLabel lbTenNV = new JLabel("Tên Nhân Viên:");
		lbTenNV.setBounds(16, 64, 97, 14);
		lbTenNV.setFont(new Font("Arial", Font.BOLD, 12));

		txtTenNV = new JTextField();
		txtTenNV.setBackground(new Color(255, 255, 255));
		txtTenNV.setBounds(123, 61, 299, 20);
		txtTenNV.setEditable(false);
		txtTenNV.setColumns(10);

		JLabel lbVaitro = new JLabel("Chức Vụ:");
		lbVaitro.setBounds(16, 92, 89, 14);
		lbVaitro.setFont(new Font("Arial", Font.BOLD, 12));

		cbxChucvu = new JComboBox();
		cbxChucvu.setBounds(123, 89, 89, 20);
		cbxChucvu.setModel(new DefaultComboBoxModel(new String[] { "Nhân Viên ", "Quản Lý" }));
		cbxChucvu.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lbDiachi = new JLabel("Địa Chỉ:");
		lbDiachi.setBounds(16, 123, 89, 14);
		lbDiachi.setFont(new Font("Arial", Font.BOLD, 12));

		txtDiachi = new JTextField();
		txtDiachi.setBackground(new Color(255, 255, 255));
		txtDiachi.setBounds(123, 120, 299, 20);
		txtDiachi.setEditable(false);
		txtDiachi.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDiachi.setColumns(10);

		JLabel lbSĐT = new JLabel("Số Điện Thoại:");
		lbSĐT.setBounds(485, 30, 89, 14);
		lbSĐT.setFont(new Font("Arial", Font.BOLD, 12));

		txtSDT = new JTextField();
		txtSDT.setBackground(new Color(255, 255, 255));
		txtSDT.setBounds(578, 27, 299, 20);
		txtSDT.setEditable(false);
		txtSDT.setFont(new Font("Arial", Font.PLAIN, 12));
		txtSDT.setColumns(10);

		JLabel lbNS = new JLabel("Ngày Sinh:");
		lbNS.setBounds(485, 64, 89, 14);
		lbNS.setFont(new Font("Arial", Font.BOLD, 12));

		JLabel lbEmail = new JLabel("Email:");
		lbEmail.setBounds(485, 95, 89, 14);
		lbEmail.setFont(new Font("Arial", Font.BOLD, 12));

		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(255, 255, 255));
		txtEmail.setBounds(578, 92, 299, 20);
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		txtEmail.setColumns(10);

		JLabel lbGT = new JLabel("Giới Tính:");
		lbGT.setBounds(485, 157, 70, 14);
		lbGT.setFont(new Font("Arial", Font.BOLD, 12));

		rdbtnGioitinh = new JRadioButton("Nam");
		rdbtnGioitinh.setBounds(573, 153, 99, 23);
		rdbtnGioitinh.setSelected(true);
		rdbtnGioitinh.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lbTrangthai = new JLabel("Trạng Thái:");
		lbTrangthai.setBounds(678, 157, 70, 14);
		lbTrangthai.setFont(new Font("Arial", Font.BOLD, 12));

		rdbtnTrangthai = new JRadioButton("Đang làm việc");
		rdbtnTrangthai.setBounds(766, 153, 111, 23);
		rdbtnTrangthai.setSelected(true);
		rdbtnTrangthai.setFont(new Font("Arial", Font.PLAIN, 12));

		btnThem = new JButton("Thêm");
		btnThem.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThem.setBounds(176, 185, 99, 37);

		btnThem.setIcon(new ImageIcon(QuanLyNhanVien.class.getResource("/icon/add.png")));
		btnThem.setFont(new Font("Arial", Font.BOLD, 12));
		btnThem.setBackground(new Color(0, 128, 255));

		btnSua = new JButton("Sửa");
		btnSua.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSua.setBounds(365, 185, 100, 37);
		btnSua.setIcon(new ImageIcon(QuanLyNhanVien.class.getResource("/icon/sua.png")));
		btnSua.setFont(new Font("Arial", Font.BOLD, 12));
		btnSua.setBackground(new Color(255, 255, 128));

		btnLuu = new JButton("Lưu");
		btnLuu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLuu.setBounds(559, 185, 100, 37);
		btnLuu.setEnabled(false);
		btnLuu.setIcon(new ImageIcon(QuanLyNhanVien.class.getResource("/icon/luulienket.png")));

		btnLuu.setFont(new Font("Arial", Font.BOLD, 12));
		btnLuu.setBackground(new Color(255, 128, 64));

		JLabel lbCMND = new JLabel("CMND:");
		lbCMND.setBounds(485, 126, 89, 14);
		lbCMND.setHorizontalAlignment(SwingConstants.LEFT);
		lbCMND.setFont(new Font("Arial", Font.BOLD, 12));

		txtCMND = new JTextField();
		txtCMND.setBackground(new Color(255, 255, 255));
		txtCMND.setBounds(578, 123, 299, 20);
		txtCMND.setEditable(false);
		txtCMND.setFont(new Font("Arial", Font.PLAIN, 12));
		txtCMND.setColumns(10);
		pnThietlapTT.setLayout(null);
		pnThietlapTT.add(lbMaNV);
		pnThietlapTT.add(txtManhanvien);
		pnThietlapTT.add(lbTenNV);
		pnThietlapTT.add(txtTenNV);
		pnThietlapTT.add(lbVaitro);
		pnThietlapTT.add(cbxChucvu);
		pnThietlapTT.add(lbDiachi);
		pnThietlapTT.add(txtDiachi);
		pnThietlapTT.add(lbCMND);
		pnThietlapTT.add(txtCMND);
		pnThietlapTT.add(lbNS);
		pnThietlapTT.add(lbSĐT);
		pnThietlapTT.add(txtSDT);
		pnThietlapTT.add(lbEmail);
		pnThietlapTT.add(txtEmail);
		pnThietlapTT.add(lbGT);
		pnThietlapTT.add(rdbtnGioitinh);
		pnThietlapTT.add(lbTrangthai);
		pnThietlapTT.add(rdbtnTrangthai);
		pnThietlapTT.add(btnThem);
		pnThietlapTT.add(btnSua);
		pnThietlapTT.add(btnLuu);

		btnHuy = new JButton("Hủy");
		btnHuy.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnHuy.setBounds(750, 183, 100, 37);
		btnHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setEditableTxT(false);

			}
		});
		btnHuy.setEnabled(false);
		btnHuy.setIcon(new ImageIcon(QuanLyNhanVien.class.getResource("/icon/x.png")));
		btnHuy.setFont(new Font("Arial", Font.BOLD, 12));
		btnHuy.setBackground(new Color(255, 0, 0));
		pnThietlapTT.add(btnHuy);

		dateChooserNgaySinh = new JDateChooser();
		dateChooserNgaySinh.setBounds(578, 58, 299, 20);
		pnThietlapTT.add(dateChooserNgaySinh);
		pnNhanvien.setLayout(gl_pnNhanvien);

		pnTaikhoan = new JPanel();
		pnTaikhoan.setBackground(new Color(255, 255, 255));
		tabbedNVTK.addTab("Tài Khoản", null, pnTaikhoan, null);
		pnTaikhoan.setLayout(new CardLayout(0, 0));
		
		
		qltk = new QuanLyTaiKhoan();
		qltk.setBackground(new Color(255, 255, 255));
		pnTaikhoan.removeAll();
		pnTaikhoan.add(qltk, BorderLayout.CENTER);
		pnTaikhoan.revalidate();

		tableDSTK = new JTable(modeltk);

		// tim kiem nhan vien
		txtTimmNV.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTableTimkiemNV();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTableTimkiemNV();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTableTimkiemNV();
			}
		});
		tableDangLV.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		tableNgungLV.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = tableNgungLV.getSelectedRow();
				txtManhanvien.setText(tableNgungLV.getValueAt(row, 0).toString());
				txtTenNV.setText(tableNgungLV.getValueAt(row, 1).toString());
//				txtNgaysinh.setText(modelnv.getValueAt(row, 2).toString());
//				Date ns = (Date) tableNgungLV.getValueAt(row, 2);
//				dateChooserNgaySinh.setDate(ns);
				
				LocalDate ns = (LocalDate) tableNgungLV.getValueAt(row, 2);
				dateChooserNgaySinh
						.setDate(Date.from(ns.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				txtDiachi.setText(tableNgungLV.getValueAt(row, 3).toString());
				txtCMND.setText(tableNgungLV.getValueAt(row, 4).toString());
				txtSDT.setText(tableNgungLV.getValueAt(row, 5).toString());
				txtEmail.setText(tableNgungLV.getValueAt(row, 6).toString());

				if (modelnv.getValueAt(row, 7).toString().equals("Nam")) {
					rdbtnGioitinh.setSelected(true);
				} else {
					rdbtnGioitinh.setSelected(false);
				}
				if (modelnv.getValueAt(row, 8).toString().equals("Nhan vien")) {
					cbxChucvu.setSelectedIndex(0);
				} else {
					cbxChucvu.setSelectedIndex(1);
				}
				
				try {
					NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
					NhanVien nv = nhanVienDAO.getNhanVienByID(tableNgungLV.getValueAt(row, 0).toString());
					rdbtnTrangthai.setSelected(nv.getTrangThai() == 1 ? false : true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		tableDangLV.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			    int row = tableDangLV.getSelectedRow();
			    if (row != -1) {
			        txtManhanvien.setText(modelnv.getValueAt(row, 0).toString());
			        txtTenNV.setText(modelnv.getValueAt(row, 1).toString());
			        //txtNgaysinh.setText(modelnv.getValueAt(row, 2).toString());

			        // Chuyển đổi từ LocalDate sang Date
			        LocalDate localDate = (LocalDate) modelnv.getValueAt(row, 2);
			        java.sql.Date ngaysinh = java.sql.Date.valueOf(localDate);
			        dateChooserNgaySinh.setDate(ngaysinh);

			        txtDiachi.setText(modelnv.getValueAt(row, 3).toString());
			        txtCMND.setText(modelnv.getValueAt(row, 4).toString());
			        txtSDT.setText(modelnv.getValueAt(row, 5).toString());
			        txtEmail.setText(modelnv.getValueAt(row, 6).toString());

			        if (modelnv.getValueAt(row, 7).toString().equals("Nam")) {
			            rdbtnGioitinh.setSelected(true);
			        } else {
			            rdbtnGioitinh.setSelected(false);
			        }
			        if (modelnv.getValueAt(row, 8).toString().equals("Nhan vien")) {
			            cbxChucvu.setSelectedIndex(0);
			        } else {
			            cbxChucvu.setSelectedIndex(1);
			        }
			        try {
			            NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
			            NhanVien nv = nhanVienDAO.getNhanVienByID(tableDangLV.getValueAt(row, 0).toString());
			            rdbtnTrangthai.setSelected(nv.getTrangThai() == 0 ? true : false);
			        } catch (RemoteException | NotBoundException e1) {
			            e1.printStackTrace();
			        }
			    }
			}

//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int row = tableDangLV.getSelectedRow();
//				txtManhanvien.setText(modelnv.getValueAt(row, 0).toString());
//				txtTenNV.setText(modelnv.getValueAt(row, 1).toString());
//				//txtNgaysinh.setText(modelnv.getValueAt(row, 2).toString());
//
//				// Chuyển đổi từ LocalDate sang Date
//				LocalDate localDate = (LocalDate) modelnv.getValueAt(row, 2);
//				java.sql.Date ngaysinh = java.sql.Date.valueOf(localDate);
//				dateChooserNgaySinh.setDate(ngaysinh);
//
//				txtDiachi.setText(modelnv.getValueAt(row, 3).toString());
//				txtCMND.setText(modelnv.getValueAt(row, 4).toString());
//				txtSDT.setText(modelnv.getValueAt(row, 5).toString());
//				txtEmail.setText(modelnv.getValueAt(row, 6).toString());
//
//				if (modelnv.getValueAt(row, 7).toString().equals("Nam")) {
//				    rdbtnGioitinh.setSelected(true);
//				} else {
//				    rdbtnGioitinh.setSelected(false);
//				}
//				if (modelnv.getValueAt(row, 8).toString().equals("Nhan vien")) {
//				    cbxChucvu.setSelectedIndex(0);
//				} else {
//				    cbxChucvu.setSelectedIndex(1);
//				}
//				try {
//				    NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
//				    NhanVien nv = nhanVienDAO.getNhanVienByID(tableDangLV.getValueAt(row, 0).toString());
//				    rdbtnTrangthai.setSelected(nv.getTrangThai() == 0 ? true : false);
//				} catch (RemoteException | NotBoundException e1) {
//				    e1.printStackTrace();
//				}
//				
//			}
		});

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		btnHuy.addActionListener(this);
	}

	//////////////////////////////////////
	/// các hàm
	//////////////////////////////////////
	private void clearTableNhanVien() {
		DefaultTableModel dtm = (DefaultTableModel) tableDangLV.getModel();
		dtm.setRowCount(0);
	}

	private void updateTableTimkiemNV() {
		String masptendt = txtTimmNV.getText();
		String gt = cbxgt.getSelectedItem().toString();
		boolean gtzz = true;
		if (gt == "Nam") {
			gtzz = true;
		} else {
			gtzz = false;
		}
		String cv = cbxcv.getSelectedItem().toString();
		boolean cvzz = true;
		if (cv == "Nhân Viên") {
			cvzz = true;
		} else {
			cvzz = false;
		}
//		 
		try {
			NhanVienDAO nvdao = (NhanVienDAO) registry.lookup("NhanVienDAO");
			modelnv.getDataVector().removeAllElements();
			for (NhanVien nz : nvdao.timnv(masptendt, gtzz, cvzz)) {
				String gt1 = "";
				if (nz.isGioiTinh() == true) {
					gt1 = "Nam";
				} else {
					gt1 = "Nu";
				}
				String cv1 = "";
				if (nz.isChucVu() == true) {
					cv1 = "Nhan vien";
				} else {
					cv1 = "Quan ly";
				}
				Object[] obj = { nz.getMaNV(), nz.getTenNV(), nz.getNgaySinh(), nz.getDiaChi(), nz.getCMND(), nz.getSDT(),
						nz.getEmail(), gt1, cv1 };

				modelnv.addRow(obj);
			}
			tableDangLV.setModel(modelnv);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateTableData() {
		try {
			// TODO Auto-generated method stub
			NhanVienDAO nvdao = (NhanVienDAO) registry.lookup("NhanVienDAO");
			clearTableNhanVien();
			DefaultTableModel dtm = (DefaultTableModel) tableDangLV.getModel();
			dtm.getDataVector().removeAllElements();
			for (NhanVien nz : nvdao.getAllNhanVienConHoatDong()) {
				String gt = "";
				if (nz.isGioiTinh() == false) {
					gt = "Nam";
				} else {
					gt = "Nu";
				}

				String cv = "";
				if (nz.isChucVu() == false) {
					cv = "Nhan vien";
				} else {
					cv = "Quan ly";
				}
				Object[] obj = { nz.getMaNV(), nz.getTenNV(), nz.getNgaySinh(), nz.getDiaChi(), nz.getCMND(), nz.getSDT(),
						nz.getEmail(), gt, cv };

				dtm.addRow(obj);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateTableDataNgungLamViec() {
		try {
			NhanVienDAO nvdao = (NhanVienDAO) registry.lookup("NhanVienDAO");
			DefaultTableModel dtm = (DefaultTableModel) tableNgungLV.getModel();
			dtm.setRowCount(0);
			// TODO Auto-generated method stub
			dtm.getDataVector().removeAllElements();
			for (NhanVien nz : nvdao.getAllNhanVienNgungLam()) {
				String gt = "";
				if (nz.isGioiTinh() == false) {
					gt = "Nam";
				} else {
					gt = "Nu";
				}

				String cv = "";
				if (nz.isChucVu() == false) {
					cv = "Nhan vien";
				} else {
					cv = "Quan ly";
				}
				Object[] obj = { nz.getMaNV(), nz.getTenNV(), nz.getNgaySinh(), nz.getDiaChi(), nz.getCMND(), nz.getSDT(),
						nz.getEmail(), gt, cv };

				dtm.addRow(obj);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setEditableTxT(boolean trangthai) {
		txtTenNV.setEditable(trangthai);
		txtDiachi.setEditable(trangthai);
		txtSDT.setEditable(trangthai);
//		txtNgaysinh.setEditable(trangthai);
		txtEmail.setEditable(trangthai);
		txtCMND.setEditable(trangthai);
	}

	private void setClearTxt() {
		txtTenNV.setText("");
		txtDiachi.setText("");
		txtSDT.setText("");
//		txtNgaysinh.setText("");
		txtEmail.setText("");
		txtCMND.setText("");
	}

	private NhanVien objectNhanVien() {
		String maNv = txtManhanvien.getText().trim();
		String tenNv = txtTenNV.getText().trim();
		java.util.Date ns = dateChooserNgaySinh.getDate();
		LocalDate ngaySinh = ns.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String sdt = txtSDT.getText().trim();
		String email = txtEmail.getText().trim();
		String cmnd = txtCMND.getText().trim();
		boolean gioiTinh = rdbtnGioitinh.isSelected();
		String diachi = txtDiachi.getText().trim();
		boolean chucvu = cbxChucvu.getSelectedItem().equals("Nhan vien") ? true : false;
		int trangthai = rdbtnTrangthai.isSelected() == true ? 1 : 0;
//		String ngaysinh = txtNgaysinh.getText().trim();
//		NhanVien nv = new NhanVien(maNv, tenNv, ngaysinh, sdt, email, cmnd, gioiTinh, diachi, chucvu, trangthai);
		
		NhanVien nv = new NhanVien(maNv, tenNv, ngaySinh, sdt, email, cmnd, gioiTinh, diachi, chucvu, trangthai);
		System.out.println(nv);
		return nv;
	}

	private boolean validData() {
		String maNv = txtManhanvien.getText().trim();
		String tenNv = txtTenNV.getText().trim();
		String diachi = txtDiachi.getText().trim();
		String email = txtEmail.getText().trim();
		String sdt = txtSDT.getText().trim();
		String cmnd = txtCMND.getText().trim();

		if (maNv.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền tên nhân viên.");
			return false;
		}

		if (tenNv.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền mã nhân viên.");
			return false;
		}

		if (diachi.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền địa chỉ.");
			return false;
		}

		if (sdt.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền số điện thoại.");
			return false;
		} else if (!isValidPhoneNumber(sdt)) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
			return false;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền email.");
			return false;
		} else if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(null, "Email không hợp lệ.");
			return false;
		}

		if (cmnd.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền CMND hoặc CCCD.");
			return false;
		} else if (!isValidCMND_CCCD(cmnd)) {
			JOptionPane.showMessageDialog(null, "Số CMND hoặc CCCD không hợp lệ.");
			return false;
		}

		return true;
	}

	private boolean isValidEmail(String email) {
		// Regex cho định dạng email cơ bản (đơn giản)
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// Regex cho định dạng số điện thoại (đơn giản)
		String phoneRegex = "^[0-9]{10}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	private boolean isValidCMND_CCCD(String cmnd) {
		// Regex cho định dạng số CMND hoặc CCCD
		String cmndRegex = "^[0-9]{9}$|^[0-9]{12}$";
		Pattern pattern = Pattern.compile(cmndRegex);
		Matcher matcher = pattern.matcher(cmnd);
		return matcher.matches();
	}

	private void clearTextfields() {
		txtManhanvien.setText("");
		txtTenNV.setText("");
		txtDiachi.setText("");
		txtEmail.setText("");
//		txtNgaysinh.setText("");
		txtSDT.setText("");
		txtManhanvien.setEditable(true);
		txtManhanvien.requestFocus();
	}
	/// nut

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			trangthainut = 1;
			tableDangLV.setEnabled(false);

			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
			NhanVien nv = new NhanVien();
			txtManhanvien.setText(getAutoIDNV());
			setEditableTxT(true);
			setClearTxt();
			updateTableData();
		}
		if (o.equals(btnSua)) {
			trangthainut = 2;
			setEditableTxT(true);
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
		}
		if (o.equals(btnLuu)) {
			tableDangLV.setEnabled(true);
			if (trangthainut == 1) {
				if (validData()) {
					try {
						NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
						nhanVienDAO.addNhanVien(objectNhanVien());
						TaiKhoanDAO taiKhoanDAO = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
//					taiKhoanDAO.addTaiKhoan(txtManhanvien.getText(), "1111");
						taiKhoanDAO.addTaiKhoan(new TaiKhoan(txtManhanvien.getText(), "1111"));
						QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
						qltk.upDataTaiKhoan();
						updateTableDataNgungLamViec();
						updateTableData();
						btnThem.setEnabled(true);
						btnSua.setEnabled(true);
						btnLuu.setEnabled(false);
						btnHuy.setEnabled(false);
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					btnThem.setEnabled(false);
					btnSua.setEnabled(false);
					btnLuu.setEnabled(true);
					btnHuy.setEnabled(true);
				}
			}
			if (trangthainut == 2) {
				if (validData()) {
					try {
						NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
						nhanVienDAO.updateNhanVien(objectNhanVien());
						updateTableDataNgungLamViec();
						updateTableData();
						btnThem.setEnabled(true);
						btnSua.setEnabled(true);
						btnLuu.setEnabled(false);
						btnHuy.setEnabled(false);
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		if (o.equals(btnHuy)) {
			setClearTxt();
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		}

	}

	private String getAutoIDNV() {
		// TODO Auto-generated method stub
		try {
			NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
			String idPrefix = "NV";
			int length = nhanVienDAO.getAllNhanVien().size();
			String finalId = idPrefix + String.format("%02d", length + 1);
			return finalId;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
