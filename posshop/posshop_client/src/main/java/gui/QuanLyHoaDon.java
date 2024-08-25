package gui;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhuyenMaiDAO;
import dao.SanPhamDAO;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.SanPham;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;

public class QuanLyHoaDon extends JPanel implements ActionListener {
	private JTextField txtTimKiemHoaDon;
	private JButton btnTimKiemHoaDon;
	private JComboBox<String> comboBoxTongTien, comboBoxThangHoaDon, comboBoxNamHoaDon;
	private JTable tblHoaDon, tblChiTietHoaDon;
	private DefaultTableModel modelHoaDon, modelChiTietHoaDon, modelHoaDonTraHang, modelChiTietHoaDonTraHang;
	private HoaDonDAO dshd;
	private ChiTietHoaDonDAO dscthd;
	private Registry registry;

	/**
	 * Create the panel.
	 */
	public QuanLyHoaDon() {
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
			dshd = (HoaDonDAO) registry.lookup("HoaDonDAO");
			dscthd = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initComponents();
	}

	public void initComponents() {
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(255, 255, 255));
		setLayout(new CardLayout(0, 0));
		setPreferredSize(new Dimension(934, 687));

		JPanel pnlMain = new JPanel();
		pnlMain.setForeground(new Color(255, 255, 255));
		pnlMain.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlMain.setBackground(new Color(255, 255, 255));
		add(pnlMain, "name_195669266279000");

		JTabbedPane tabbedPaneHoaDon = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneHoaDon.setBorder(null);
		tabbedPaneHoaDon.setToolTipText("g");
		GroupLayout gl_pnlMain = new GroupLayout(pnlMain);
		gl_pnlMain.setHorizontalGroup(gl_pnlMain.createParallelGroup(Alignment.TRAILING).addComponent(tabbedPaneHoaDon,
				Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE));
		gl_pnlMain.setVerticalGroup(gl_pnlMain.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_pnlMain.createSequentialGroup()
						.addComponent(tabbedPaneHoaDon, GroupLayout.PREFERRED_SIZE, 699, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		JPanel pnlHoaDon = new JPanel();
		tabbedPaneHoaDon.addTab("Hóa Đơn", null, pnlHoaDon, null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		txtTimKiemHoaDon = new JTextField();
		txtTimKiemHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTimKiemHoaDon.setColumns(10);

		JLabel lblTimKiemHoaDon = new JLabel("Tìm kiếm hóa đơn :");
		lblTimKiemHoaDon.setFont(new Font("Arial", Font.BOLD, 12));

		btnTimKiemHoaDon = new JButton("Tìm");
		btnTimKiemHoaDon.setIcon(new ImageIcon(QuanLyHoaDon.class.getResource("/icon/search.png")));
		btnTimKiemHoaDon.setFont(new Font("Arial", Font.BOLD, 11));
		btnTimKiemHoaDon.setBackground(new Color(192, 192, 192));
		btnTimKiemHoaDon.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(new Color(255, 255, 255));
		panel_1_1.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));

		comboBoxThangHoaDon = new JComboBox<>();
		comboBoxThangHoaDon.setFont(new Font("Arial", Font.BOLD, 11));
		comboBoxThangHoaDon.setModel(new DefaultComboBoxModel(
				new String[] { "All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

		JLabel lblThang = new JLabel("Tháng :");
		lblThang.setHorizontalAlignment(SwingConstants.LEFT);
		lblThang.setFont(new Font("Arial", Font.BOLD, 12));

		JLabel lblNam = new JLabel("Năm :");
		lblNam.setHorizontalAlignment(SwingConstants.LEFT);
		lblNam.setFont(new Font("Arial", Font.BOLD, 12));

		comboBoxNamHoaDon = new JComboBox<>();
		comboBoxNamHoaDon.setFont(new Font("Arial", Font.BOLD, 11));
		comboBoxNamHoaDon.setModel(new DefaultComboBoxModel(
				new String[] { "All", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015" }));
		GroupLayout gl_panel_1_1 = new GroupLayout(panel_1_1);
		gl_panel_1_1
				.setHorizontalGroup(
						gl_panel_1_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1_1.createSequentialGroup().addContainerGap()
										.addGroup(gl_panel_1_1.createParallelGroup(Alignment.LEADING)
												.addComponent(comboBoxThangHoaDon, 0, 66, Short.MAX_VALUE)
												.addComponent(lblThang, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
										.addGap(27)
										.addGroup(gl_panel_1_1.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblNam, GroupLayout.PREFERRED_SIZE, 66,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(comboBoxNamHoaDon, GroupLayout.PREFERRED_SIZE, 66,
														GroupLayout.PREFERRED_SIZE))
										.addGap(20)));
		gl_panel_1_1.setVerticalGroup(gl_panel_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1_1.createSequentialGroup()
						.addGroup(gl_panel_1_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblThang, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNam, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
						.addGroup(gl_panel_1_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBoxThangHoaDon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxNamHoaDon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panel_1_1.setLayout(gl_panel_1_1);

		JScrollPane scrollPane_HoaDon = new JScrollPane();
		scrollPane_HoaDon.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(229).addGroup(gl_panel
						.createParallelGroup(Alignment.TRAILING,
								false)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblTimKiemHoaDon, GroupLayout.PREFERRED_SIZE, 108,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtTimKiemHoaDon, GroupLayout.PREFERRED_SIZE, 246,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnTimKiemHoaDon,
										GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addGap(18)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_1_1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
								.addGap(15)))
						.addContainerGap(242, Short.MAX_VALUE))
				.addComponent(scrollPane_HoaDon, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTimKiemHoaDon, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTimKiemHoaDon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTimKiemHoaDon, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panel_1_1, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
						.addGap(28).addComponent(scrollPane_HoaDon, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)));

		tblHoaDon = new JTable();
		tblHoaDon.setForeground(new Color(0, 0, 0));
		tblHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane_HoaDon.setViewportView(tblHoaDon);
		tblHoaDon.setModel(modelHoaDon = new DefaultTableModel(new Object[][] {

		}, new String[] { "M\u00E3 h\u00F3a \u0111\u01A1n", "Ng\u00E0y l\u1EADp", "M\u00E3 nh\u00E2n vi\u00EAn",
				"T\u00EAn nh\u00E2n vi\u00EAn", "M\u00E3 kh\u00E1ch h\u00E0ng", "T\u00EAn kh\u00E1ch h\u00E0ng",
				"T\u1ED5ng ti\u1EC1n" }));

		JLabel lblTongTien = new JLabel("Tổng tiền :");
		lblTongTien.setHorizontalAlignment(SwingConstants.LEFT);
		lblTongTien.setFont(new Font("Arial", Font.BOLD, 12));

		comboBoxTongTien = new JComboBox<>();
		comboBoxTongTien.setFont(new Font("Arial", Font.BOLD, 11));
		comboBoxTongTien.setModel(new DefaultComboBoxModel(new String[] { "All", "0 NVĐ - 500000 VNĐ",
				"500000 VNĐ - 1000000 VNĐ", "1000000 VNĐ - 1500000 VNĐ", "1500000 VNĐ - 2000000 VNĐ" }));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						gl_panel_1.createSequentialGroup().addContainerGap()
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTongTien, GroupLayout.PREFERRED_SIZE, 66,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(comboBoxTongTien, GroupLayout.PREFERRED_SIZE, 171,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(lblTongTien, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBoxTongTien,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Chi Ti\u1EBFt H\u00F3a \u0110\u01A1n",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				null));

		JScrollPane scrollPane_ChiTietHoaDon = new JScrollPane();
		scrollPane_ChiTietHoaDon.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_ChiTietHoaDon, GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addComponent(scrollPane_ChiTietHoaDon,
						GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)));

		tblChiTietHoaDon = new JTable();
		tblChiTietHoaDon.setFont(new Font("Arial", Font.PLAIN, 12));
		tblChiTietHoaDon.setModel(modelChiTietHoaDon = new DefaultTableModel(new Object[][] {},
				new String[] { "M\u00E3 s\u1EA3n ph\u1EA9m", "T\u00EAn s\u1EA3n ph\u1EA9m", "Lo\u1EA1i",
						"\u0110\u01A1n gi\u00E1", "S\u1ED1 l\u01B0\u1EE3ng", "Gi\u1EA3m gi\u00E1",
						"Ch\u1EA5t li\u1EC7u", "Ki\u1EC3u d\u00E1ng", "M\u00E0u s\u1EAFc", "K\u00EDch th\u01B0\u1EDBc",
						"Th\u00E0nh ti\u1EC1n" }));
		scrollPane_ChiTietHoaDon.setViewportView(tblChiTietHoaDon);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_pnlHoaDon = new GroupLayout(pnlHoaDon);
		gl_pnlHoaDon.setHorizontalGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlHoaDon
				.createSequentialGroup()
				.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(panel,
								Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_pnlHoaDon.setVerticalGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlHoaDon.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		pnlHoaDon.setLayout(gl_pnlHoaDon);
		pnlMain.setLayout(gl_pnlMain);

		/*
		 * ==== EVENT ====
		 */
		updateTableHoaDon();
		btnTimKiemHoaDon.addActionListener(this);
		comboBoxTongTien.addActionListener(this);
		comboBoxThangHoaDon.addActionListener(this);
		comboBoxNamHoaDon.addActionListener(this);
		tblHoaDon.addMouseListener(new MouseListener() {

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
				int row = tblHoaDon.getSelectedRow();
				if (row != -1) {
					String selectedMaHD = (String) tblHoaDon.getValueAt(row, 0);
					ChiTietHoaDonDAO ds = null;
					try {
						ds = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					List<ChiTietHoaDon> list = null;
					try {
						list = ds.getChiTietHoaDonTheoMaHD(selectedMaHD);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					modelChiTietHoaDon.setRowCount(0);
					for (ChiTietHoaDon cthd : list) {
						String thanhTien = dinhDangTien(String.valueOf(cthd.getThanhTien()));
						Object data[] = { cthd.getSanPham().getMaSP(), cthd.getSanPham().getTenSP(),
								cthd.getSanPham().getPhanLoai().getPhanLoai(), cthd.getSanPham().getGiaNhap(),
								cthd.getSoLuong(), cthd.getPhanTramKhuyenMai() + "%",
								cthd.getSanPham().getChatLieu().getChatLieu(),
								cthd.getSanPham().getKieuDang().getKieuDang(),
								cthd.getSanPham().getMauSac().getMauSac(),
								cthd.getSanPham().getKichThuoc().getKichThuoc(), thanhTien };
						modelChiTietHoaDon.addRow(data);
					}
					tblChiTietHoaDon.setModel(modelChiTietHoaDon);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnTimKiemHoaDon)) { // Sự kiện tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) kết
											// hợp tháng, năm hoặc tổng tiền
			String tuKhoa = txtTimKiemHoaDon.getText();
			String selectedTongTien = (String) comboBoxTongTien.getSelectedItem();
			String selectedThang = (String) comboBoxThangHoaDon.getSelectedItem();
			String selectedNam = (String) comboBoxNamHoaDon.getSelectedItem();

			String khoangTien = (String) comboBoxTongTien.getSelectedItem();
			// Tách chuỗi bằng dấu "-" và loại bỏ khoảng trắng xung quanh
			String[] chiaKhoangTien = khoangTien.split("\\s*-\\s*");

			// Ô inut là rỗng thì reset các comBoBox và tải lại table hóa đơn
			if (tuKhoa.equals("")) {
				comboBoxTongTien.setSelectedIndex(0);
				comboBoxThangHoaDon.setSelectedIndex(0);
				comboBoxNamHoaDon.setSelectedIndex(0);
			} // Nếu nhập từ khóa và chọn tổng tiền, không chọn tháng và năm thì tìm kiếm theo
				// từ khóa và tổng tiền
			else if (!selectedTongTien.equals("All") && selectedThang.equals("All") && selectedNam.equals("All")) {
				// loại bỏ mọi ký tự không phải là số từ mỗi phần tử của mảng sau khi tách, Lưu
				// giá trị tối thiểu và tối đa vào biến
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaTongTien(tuKhoa, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa và chọn tháng, không chọn tổng tiền và năm thì tìm kiếm theo
				// từ khóa và tháng
			else if (selectedTongTien.equals("All") && !selectedThang.equals("All") && selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaThang(tuKhoa, thang);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa và chọn năm, không chọn tổng tiền và tháng thì tìm kiếm theo
				// từ khóa và năm
			else if (selectedTongTien.equals("All") && selectedThang.equals("All") && !selectedNam.equals("All")) {
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaNam(tuKhoa, nam);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa chọn tổng tiền và chọn tháng, không chọn năm thì tìm kiếm
				// theo
				// từ khóa tổng tiền và tháng
			else if (!selectedTongTien.equals("All") && !selectedThang.equals("All") && selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaThangTongTien(tuKhoa, thang, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa chọn tổng tiền và chọn năm, không chọn tháng thì tìm kiếm
				// theo
				// từ khóa tổng tiền và năm
			else if (!selectedTongTien.equals("All") && selectedThang.equals("All") && !selectedNam.equals("All")) {
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaNamTongTien(tuKhoa, nam, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa chọn tổng tiền chọn tháng và chọn năm, thì tìm kiếm theo
				// từ khóa tổng tiền tháng và năm
			else if (!selectedTongTien.equals("All") && !selectedThang.equals("All") && !selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaThangNamTongTien(tuKhoa, thang, nam, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu nhập từ khóa chọn tháng và chọn năm, không chọn tổng tiền thì tìm kiếm
				// theo
				// từ khóa tháng và năm
			else if (selectedTongTien.equals("All") && !selectedThang.equals("All") && !selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoaThangNam(tuKhoa, thang, nam);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} else { // Nếu chỉ nhập từ khóa, không chọn tổng tiền, tháng và năm
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTuKhoa(tuKhoa);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			}

			// Sự kiện lọc hóa đơn theo tổng tiền tháng,năm hoặc kết hợp cả tổng tiền, tháng
			// và năm
		} else if (o.equals(comboBoxThangHoaDon) || o.equals(comboBoxNamHoaDon) || o.equals(comboBoxTongTien)) {
			String selectedTongTien = (String) comboBoxTongTien.getSelectedItem();
			String selectedThang = (String) comboBoxThangHoaDon.getSelectedItem();
			String selectedNam = (String) comboBoxNamHoaDon.getSelectedItem();

			String khoangTien = (String) comboBoxTongTien.getSelectedItem();
			// Tách chuỗi bằng dấu "-" và loại bỏ khoảng trắng xung quanh
			String[] chiaKhoangTien = khoangTien.split("\\s*-\\s*");

			// Nếu tổng tiền được chon, tháng và năm không được chọn thì tìm theo tổng tiền
			if (!selectedTongTien.equals("All") && selectedThang.equals("All") && selectedNam.equals("All")) {
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTongTien(giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu tháng được chọn, tổng tiền và năm không được chọn thì tìm theo tháng
			else if (selectedTongTien.equals("All") && !selectedThang.equals("All") && selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoThang(thang);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu năm được chọn, tổng tiền và tháng không được chọn thì tìm theo năm
			else if (selectedTongTien.equals("All") && selectedThang.equals("All") && !selectedNam.equals("All")) {
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoNam(nam);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu cả tổng tiền và tháng đều được chọn, năm không được chọn thì tìm theo cả
				// tổng tiền và tháng
			else if (!selectedTongTien.equals("All") && !selectedThang.equals("All") && selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTongTienThang(thang, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu cả tổng tiền và năm đều được chọn, tháng không được chọn thì tìm theo cả
				// tổng tiền và năm
			else if (!selectedTongTien.equals("All") && selectedThang.equals("All") && !selectedNam.equals("All")) {
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTongTienNam(nam, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu cả tổng tiền, tháng và năm đều được chọn thì tìm theo cả tổng tiền, tháng
				// và năm
			else if (!selectedTongTien.equals("All") && !selectedThang.equals("All") && !selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				int giaMin = Integer.parseInt(chiaKhoangTien[0].replaceAll("[^\\d]+", ""));
				int giaMax = Integer.parseInt(chiaKhoangTien[1].replaceAll("[^\\d]+", ""));
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoTongTienThangNam(thang, nam, giaMin, giaMax);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list.size() == 0) {
					modelHoaDon.setRowCount(0);
				} else {
					modelHoaDon.setRowCount(0);
					for (HoaDon hd : list) {
						String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
						Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
								hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
								tongTien };
						modelHoaDon.addRow(data);
					}
					tblHoaDon.setModel(modelHoaDon);
				}
			} // Nếu cả tháng và năm đều được chọn, tổng tiền không được chọn thì tìm theo
				// tháng và năm
			else if (selectedTongTien.equals("All") && !selectedThang.equals("All") && !selectedNam.equals("All")) {
				int thang = Integer.parseInt((String) comboBoxThangHoaDon.getSelectedItem());
				int nam = Integer.parseInt((String) comboBoxNamHoaDon.getSelectedItem());
				HoaDonDAO ds = null;
				try {
					ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<HoaDon> list = null;
				try {
					list = ds.getHoaDonTheoThangNam(thang, nam);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modelHoaDon.setRowCount(0);
				for (HoaDon hd : list) {
					String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
					Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(),
							hd.getNhanVien().getTenNV(), hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(),
							tongTien };
					modelHoaDon.addRow(data);
				}
				tblHoaDon.setModel(modelHoaDon);
			} else { // Nếu cả hai comBoBox đều là All thì hiển thị toàn bộ danh sách hóa đơn
				updateTableHoaDon();
			}
		} else if (o.equals(comboBoxTongTien)) {

		}
	}

	private void updateTableHoaDon() {
		modelHoaDon.setRowCount(0);
		HoaDonDAO ds = null;
		try {
			ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HoaDon> list = null;
		try {
			list = ds.doTuBang();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (HoaDon hd : list) {
			String tongTien = dinhDangTien(String.valueOf(hd.getTongTien()));
			Object data[] = { hd.getMaHD(), hd.getNgayLap(), hd.getNhanVien().getMaNV(), hd.getNhanVien().getTenNV(),
					hd.getKhachHang().getMaKH(), hd.getKhachHang().getTenKH(), tongTien };
			modelHoaDon.addRow(data);

		}
		tblHoaDon.setModel(modelHoaDon);
	}

	// Hàm đinh dạng tiền
	private String dinhDangTien(String tien) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

		// Chuyển đổi tien sang kiểu double trước khi định dạng
		double amount = Double.parseDouble(tien);
		String formattedAmount = currencyVN.format(amount);
		return formattedAmount;
	}
}
