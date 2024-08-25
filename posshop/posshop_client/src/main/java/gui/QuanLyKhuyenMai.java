package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import com.toedter.calendar.JDateChooser;

import dao.ChiTietHoaDonDAO;
//import ConnectDB.KetNoiSQL;
import dao.KhuyenMaiDAO;
import dao.PhanLoaiDAO;
import dao.SanPhamDAO;
import entities.ChiTietHoaDon;
import entities.KhuyenMai;
import entities.PhanLoai;
import entities.SanPham;

public class QuanLyKhuyenMai extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaKhuyenMai, txtTenKhuyenMai, txtMucKhuyenMai, txtTimKiemSanPham;
	private JCheckBox checkBoxChonTatCa;
	private JComboBox<String> comboBoxPhanLoai;
	private JDateChooser dateChooserThoiGianBatDauGiamGia, dateChooserThoiGianKetThucGiamGia;
	private JButton btnTimKiemSanPham, btnThemKhuyenMai, btnSuaKhuyenMai, btnLamMoi;
	private JTable tblSanPham, tblKhuyenMai;
	private DefaultTableModel modelSanPham, modelKhuyenMai;
	private KhuyenMaiDAO dskm;
	private SanPhamDAO dssp;
	private PhanLoaiDAO dspl;
	private Registry registry;

	/**
	 * Create the panel.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public QuanLyKhuyenMai() {
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
			dskm = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
			dssp = (SanPhamDAO) registry.lookup("SanPhamDAO");
			dspl = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initComponents();
	}

	public void initComponents() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(934, 687));

		JPanel pnlKhuyenMai = new JPanel();
		pnlKhuyenMai.setBackground(new Color(255, 255, 255));
		pnlKhuyenMai.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2),
				"Khuy\u1EBFn M\u00E3i", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Danh S\u00E1ch Khuy\u1EBFn M\u00E3i",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE)
								.addComponent(pnlKhuyenMai, GroupLayout.PREFERRED_SIZE, 934, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(pnlKhuyenMai, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)));

		JScrollPane scrollPane_DanhSachKhuyenMai = new JScrollPane();
		scrollPane_DanhSachKhuyenMai.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(
				scrollPane_DanhSachKhuyenMai, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_DanhSachKhuyenMai, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE));

		tblKhuyenMai = new JTable();
		tblKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 11));
		tblKhuyenMai.setModel(modelKhuyenMai = new DefaultTableModel(new Object[][] {},
				new String[] { "M\u00E3 khuy\u1EBFn m\u00E3i", "T\u00EAn khuy\u1EBFn m\u00E3i",
						"Ph\u1EA7n tr\u0103n khuy\u1EBFn m\u00E3i", "Ng\u00E0y b\u1EAFt \u0111\u1EA7u",
						"Ng\u00E0y k\u1EBFt th\u00FAc" }));
		tblKhuyenMai.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblKhuyenMai.getColumnModel().getColumn(2).setPreferredWidth(90);
		scrollPane_DanhSachKhuyenMai.setViewportView(tblKhuyenMai);
		panel_2.setLayout(gl_panel_2);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), null));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Th\u1EDDi Gian Khuy\u1EBFn M\u00E3i",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				null));
		GroupLayout gl_pnlKhuyenMai = new GroupLayout(pnlKhuyenMai);
		gl_pnlKhuyenMai.setHorizontalGroup(gl_pnlKhuyenMai.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlKhuyenMai.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 693, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_pnlKhuyenMai.setVerticalGroup(gl_pnlKhuyenMai.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlKhuyenMai.createSequentialGroup()
						.addGroup(gl_pnlKhuyenMai.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));

		JLabel lblThoiGianBatDauKhuyenMai = new JLabel("Thời gian bắt đầu giảm giá :");
		lblThoiGianBatDauKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));

		JLabel lblThoiGianKetThucKhuyenMai = new JLabel("Thời gian kết thúc giảm giá :");
		lblThoiGianKetThucKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));

		btnThemKhuyenMai = new JButton("Thêm");
		btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
		btnThemKhuyenMai.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThemKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/add.png")));
		btnThemKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));
		btnThemKhuyenMai.setBackground(new Color(65, 105, 255));

		// Sự kiện sử dụng phím tắt Ctrl + C cho button Thêm khuyến mãi, Ctrl + S cho
		// button Lưu Khuyến mãi
		// Nếu là button Thêm
		btnThemKhuyenMai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "CtrlC");
		btnThemKhuyenMai.getActionMap().put("CtrlC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnThemKhuyenMai.getText().equals("Thêm"))
					btnThemKhuyenMai.doClick();
			}
		});
		// Ngược lại nếu là button Lưu
		btnThemKhuyenMai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "CtrlS");
		btnThemKhuyenMai.getActionMap().put("CtrlS", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnThemKhuyenMai.getText().equals("Lưu"))
					btnThemKhuyenMai.doClick();
			}
		});
		btnSuaKhuyenMai = new JButton("Sửa");
		btnSuaKhuyenMai.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnSuaKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/sua.png")));
		btnSuaKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));
		btnSuaKhuyenMai.setBackground(new Color(255, 255, 0));

		// Sự kiện sử dụng phím tắt Ctrl + U cho button Sửa khuyến mãi, Ctrl + S cho
		// button Lưu khuyến mãi
		// Nếu là button Sửa
		btnSuaKhuyenMai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK), "CtrlU");
		btnSuaKhuyenMai.getActionMap().put("CtrlU", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSuaKhuyenMai.doClick();

			}
		});
		// Ngược lại nếu là button Lưu
		btnSuaKhuyenMai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "CtrlS");
		btnSuaKhuyenMai.getActionMap().put("CtrlS", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnSuaKhuyenMai.getText().equals("Lưu"))
					btnSuaKhuyenMai.doClick();

			}
		});
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLamMoi.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/loading.png")));
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
		btnLamMoi.setBackground(new Color(152, 251, 152));

		// Sự kiện sử dụng phím tắt Ctrl + R cho button Làm Mới, Ctrl + D cho button Hủy
		// Nếu là button Làm mới
		btnLamMoi.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "CtrlR");
		btnLamMoi.getActionMap().put("CtrlR", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnLamMoi.getText().equals("Làm mới"))
					btnLamMoi.doClick();

			}
		});
		// Ngược lại nếu là button Hủy
		btnLamMoi.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "CtrlD");
		btnLamMoi.getActionMap().put("CtrlD", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnLamMoi.getText().equals("Hủy"))
					btnLamMoi.doClick();

			}
		});

		// Sự kiện nhấn button Hủy thì chuyển thành button Làm Mới
		btnLamMoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnLamMoi.getText().equals("Hủy")) {
					btnLamMoi.setText("Làm mới");
					tblSanPham.setEnabled(false);
					btnLamMoi.setBackground(new Color(152, 251, 152));
					btnLamMoi.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/loading.png")));

					btnThemKhuyenMai.setText("Thêm");
					btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
					btnThemKhuyenMai.setBackground(new Color(65, 105, 255));
					btnThemKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/add.png")));

					btnSuaKhuyenMai.setText("Sửa");
					btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
					btnSuaKhuyenMai.setBackground(new Color(255, 255, 0));
					btnSuaKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/sua.png")));

					txtTenKhuyenMai.setEditable(false);
					txtMucKhuyenMai.setEditable(false);

					btnThemKhuyenMai.setEnabled(true);
					btnSuaKhuyenMai.setEnabled(true);
					dateChooserThoiGianBatDauGiamGia.setEnabled(false);
					dateChooserThoiGianKetThucGiamGia.setEnabled(false);
				}
			}
		});
		dateChooserThoiGianBatDauGiamGia = new JDateChooser();
		dateChooserThoiGianBatDauGiamGia.setBackground(new Color(255, 255, 255));
		dateChooserThoiGianBatDauGiamGia.setEnabled(false);
		dateChooserThoiGianKetThucGiamGia = new JDateChooser();
		dateChooserThoiGianKetThucGiamGia.setBackground(new Color(255, 255, 255));
		dateChooserThoiGianKetThucGiamGia.setEnabled(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap(10, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING,
								gl_panel_1.createSequentialGroup()
										.addComponent(btnThemKhuyenMai, GroupLayout.PREFERRED_SIZE, 85,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(btnSuaKhuyenMai, GroupLayout.PREFERRED_SIZE, 87,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addComponent(dateChooserThoiGianKetThucGiamGia, Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblThoiGianKetThucKhuyenMai, GroupLayout.PREFERRED_SIZE, 170,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(dateChooserThoiGianBatDauGiamGia, Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
										.addComponent(lblThoiGianBatDauKhuyenMai)))
				.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup().addGap(41)
						.addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(42, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addGap(18)
				.addComponent(lblThoiGianBatDauKhuyenMai, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(dateChooserThoiGianBatDauGiamGia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(lblThoiGianKetThucKhuyenMai, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(dateChooserThoiGianKetThucGiamGia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSuaKhuyenMai, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnThemKhuyenMai, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(101, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		JLabel lblMaKhuyenMai = new JLabel("Mã khuyến mãi :");
		lblMaKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));

		txtMaKhuyenMai = new JTextField();
		txtMaKhuyenMai.setBackground(new Color(255, 255, 255));
		txtMaKhuyenMai.setEditable(false);
		txtMaKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 12));
		txtMaKhuyenMai.setColumns(10);

		JLabel lblTenKhuyenMai = new JLabel("Tên khuyến mãi :");
		lblTenKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));

		txtTenKhuyenMai = new JTextField();
		txtTenKhuyenMai.setBackground(new Color(255, 255, 255));
		txtTenKhuyenMai.setEditable(false);
		txtTenKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTenKhuyenMai.setColumns(10);

		JLabel lblMucKhuyenMai = new JLabel("Mức khuyến mãi ( % ) :");
		lblMucKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));

		txtMucKhuyenMai = new JTextField();
		txtMucKhuyenMai.setBackground(new Color(255, 255, 255));
		txtMucKhuyenMai.setEditable(false);
		txtMucKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 12));
		txtMucKhuyenMai.setColumns(10);

		checkBoxChonTatCa = new JCheckBox("Select All");
		checkBoxChonTatCa.setBackground(new Color(255, 255, 255));
		checkBoxChonTatCa.setFont(new Font("Arial", Font.BOLD, 12));

		JLabel lblPhanLoai = new JLabel("Phân loại :");
		lblPhanLoai.setFont(new Font("Arial", Font.BOLD, 12));

		comboBoxPhanLoai = new JComboBox<>();
		comboBoxPhanLoai.setFont(new Font("Arial", Font.BOLD, 11));

		JLabel lblTimKiemSanPham = new JLabel("Tìm kiếm sản phẩm :");
		lblTimKiemSanPham.setFont(new Font("Arial", Font.BOLD, 12));

		txtTimKiemSanPham = new JTextField();
		txtTimKiemSanPham.setFont(new Font("Arial", Font.PLAIN, 13));
		txtTimKiemSanPham.setColumns(10);

		btnTimKiemSanPham = new JButton("Tìm");
		btnTimKiemSanPham.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnTimKiemSanPham.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/search.png")));
		btnTimKiemSanPham.setFont(new Font("Arial", Font.BOLD, 11));
		btnTimKiemSanPham.setBackground(Color.LIGHT_GRAY);

		btnTimKiemSanPham.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), "CtrlF");
		btnTimKiemSanPham.getActionMap().put("CtrlF", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnTimKiemSanPham.doClick();
			}
		});

		JScrollPane scrollPane_SanPham = new JScrollPane();
		scrollPane_SanPham.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(txtMaKhuyenMai, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup().addComponent(checkBoxChonTatCa)
												.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
												.addComponent(lblPhanLoai).addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(comboBoxPhanLoai, GroupLayout.PREFERRED_SIZE, 87,
														GroupLayout.PREFERRED_SIZE)
												.addGap(38)
												.addComponent(lblTimKiemSanPham, GroupLayout.PREFERRED_SIZE, 122,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtTimKiemSanPham, GroupLayout.PREFERRED_SIZE, 144,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(btnTimKiemSanPham, GroupLayout.PREFERRED_SIZE,
														83, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblMaKhuyenMai)
										.addComponent(lblTenKhuyenMai, GroupLayout.PREFERRED_SIZE, 104,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblMucKhuyenMai)
										.addComponent(txtTenKhuyenMai, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
										.addComponent(txtMucKhuyenMai, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
										.addComponent(scrollPane_SanPham, GroupLayout.DEFAULT_SIZE, 669,
												Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap().addComponent(lblMaKhuyenMai)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtMaKhuyenMai, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(lblTenKhuyenMai, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtTenKhuyenMai, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(lblMucKhuyenMai, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtMucKhuyenMai, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(checkBoxChonTatCa)
						.addComponent(txtTimKiemSanPham, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTimKiemSanPham, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxPhanLoai, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPhanLoai, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTimKiemSanPham, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
				.addComponent(scrollPane_SanPham, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)));

		modelSanPham = new DefaultTableModel(new Object[][] {}, new String[] { "Select", "Mã sản phẩm", "Tên sản phẩm",
				"Lời (%)", "Mã khuyến mãi", "Đơn giá", "Giá khuyến mãi" });
		tblSanPham = new JTable(modelSanPham) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class getColumnClass(int column) { // Class<?> là kiểu trả về cho phương thức getColumnClass để xác
														// định kiểu dữ liệu của từng cột.
				if (column == 0) // Nếu là cột đầu tiên
					return Boolean.class; // Trả về kiểu dữ liệu cụ thể là Boolean.class (cho checkbox).
				return Object.class; // Ngược lại trả về kiểu dữ liệu Object.class cho các cột khác (dữ liệu văn
										// bản).
			}
		};
		tblSanPham.setEnabled(false);

		// Sử dụng checkbox trong ô kiểm
		TableCellRenderer checkBoxRenderer = new TableCellRenderer() {
			JCheckBox checkBox = new JCheckBox();
			{
				checkBox.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho ô kiểm trong cột table
			}

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value != null) {
					checkBox.setSelected((Boolean) value);
				}
				return checkBox;
			}
		};
		tblSanPham.getColumnModel().getColumn(0).setCellRenderer(checkBoxRenderer);
		tblSanPham.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		scrollPane_SanPham.setViewportView(tblSanPham);
		panel.setLayout(gl_panel);
		pnlKhuyenMai.setLayout(gl_pnlKhuyenMai);
		setLayout(groupLayout);

		/*
		 * ==== EVENT ====
		 */
		checkBoxChonTatCa.addActionListener(this);
		comboBoxPhanLoai.addActionListener(this);
		btnTimKiemSanPham.addActionListener(this);
		btnThemKhuyenMai.addActionListener(this);
		btnSuaKhuyenMai.addActionListener(this);
		btnLamMoi.addActionListener(this);
		// Chương trình chạy , lấy dữ liệu đưa vào table, comBoBox
//		updateTableSanPham();
		updateTableKhuyenMai();
		updateComboBox();

		// Sự kiện Click getValueAt
		tblKhuyenMai.addMouseListener(new MouseListener() {

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
				int row = tblKhuyenMai.getSelectedRow();
				txtMaKhuyenMai.setText(modelKhuyenMai.getValueAt(row, 0).toString());
				txtTenKhuyenMai.setText(modelKhuyenMai.getValueAt(row, 1).toString());
				txtMucKhuyenMai.setText(modelKhuyenMai.getValueAt(row, 2).toString());
				LocalDate nbd = (LocalDate) modelKhuyenMai.getValueAt(row, 3);
				dateChooserThoiGianBatDauGiamGia
						.setDate(Date.from(nbd.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				LocalDate nkt = (LocalDate) modelKhuyenMai.getValueAt(row, 4);
				dateChooserThoiGianKetThucGiamGia
						.setDate(Date.from(nkt.atStartOfDay(ZoneId.systemDefault()).toInstant()));

				// Sự kiện click chuột vào bảng KhuyenMai thì hiển thị danh sách SanPham được
				// khuyến mãi
				KhuyenMaiDAO ds = null;
				try {
					ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String selectedMaKM = (String) tblKhuyenMai.getValueAt(row, 0);
				List<SanPham> list = null;
				try {
					list = ds.getSanPhanTheoMaKM(selectedMaKM);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (row != -1 && btnThemKhuyenMai.getText().equals("Thêm") && btnSuaKhuyenMai.getText().equals("Sửa")) {
					modelSanPham.setRowCount(0);
					for (SanPham sp : list) {
						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String phanTram = modelKhuyenMai.getValueAt(row, 2).toString();
						String xoaKiTuPhanTram = phanTram.replace("%", ""); // Sử dụng phương thức replace để bỏ ký tự %
						Double mucKM = Double.parseDouble(xoaKiTuPhanTram);
						String maKM = modelKhuyenMai.getValueAt(row, 0).toString();
						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), mucKM)));
						Object data[] = { Boolean.TRUE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKM, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				} else if (row != -1 && btnSuaKhuyenMai.getText().equals("Lưu")) { // Đang quá trình sửa, thì hiển thị
																					// danh sách có KM và không có KM
					// Danh sách sản phẩm có maKM
					modelSanPham.setRowCount(0);
					list.clear();
					try {
						list = ds.getSanPhanTheoMaKM(selectedMaKM);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list) {
						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(String.valueOf(
								sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), sp.getKhuyenMai().getPhanTramKhuyenMai())));
						Object data[] = { Boolean.TRUE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								sp.getKhuyenMai().getMaKM(), giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
					list.clear();
					// Danh sách sản phẩm không có maKM
					try {
						list = ds.getSanPhamMaKMIsNull();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list) {
						String maKM = "Null"; // Mặc định là "null" nếu khuyến mãi là null
						double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
						Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKM, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(checkBoxChonTatCa)) {
			boolean selected = checkBoxChonTatCa.isSelected();
			// Duyệt qua tất cả các hàng và đặt giá trị của cột "Select" cho từng hàng
			for (int row = 0; row < modelSanPham.getRowCount(); row++) {
				modelSanPham.setValueAt(selected, row, 0);
			}
		} else if (o.equals(comboBoxPhanLoai)) {
			String phanLoai = (String) comboBoxPhanLoai.getSelectedItem();
			SanPhamDAO ds = null;
			try {
				ds = (SanPhamDAO) registry.lookup("SanPhamDAO");
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<SanPham> list = null;
			try {
				list = ds.getSanPhanTheoPhanLoaiNull(phanLoai);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // Lấy danh sách sản phẩm thêm tên phân loại
			if (btnSuaKhuyenMai.getText().equals("Sửa")) {
				if (phanLoai == "All") { // Nếu comBoBox phân loại là All thì hiển thị tất cả danh sách sản phẩm
					updateTableSanPham();
				} else { // Ngược lại thì tìm kiếm các sản phẩm phân loại tương ứng
					modelSanPham.setRowCount(0);
					for (SanPham sp : list) {
						String maKM = "Null"; // Mặc định là "null" nếu khuyến mãi là null
						double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
						Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKM, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				}
			} else {
				String maKM = txtMaKhuyenMai.getText();
				KhuyenMaiDAO ds2 = null;
				try {
					ds2 = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<SanPham> list2 = null;
				try {
					list2 = ds2.getSanPhamMaKMIsNull();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (phanLoai == "All") {
					modelSanPham.setRowCount(0);
					list2.clear();
					try {
						list2 = ds2.getSanPhanTheoMaKM(maKM);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list2) {
						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(String.valueOf(
								sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), sp.getKhuyenMai().getPhanTramKhuyenMai())));
						Object data[] = { Boolean.TRUE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								sp.getKhuyenMai().getMaKM(), giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
					list2.clear();
					// Danh sách sản phẩm không có maKM
					try {
						list2 = ds2.getSanPhamMaKMIsNull();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list2) {
						String maKMSP = "Null"; // Mặc định là "null" nếu khuyến mãi là null
						double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
						Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKMSP, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				} else {
					modelSanPham.setRowCount(0);
					list2.clear();
					try {
						list2 = ds2.getSanPhanTheoMaKM(maKM);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list2) {
						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(String.valueOf(
								sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), sp.getKhuyenMai().getPhanTramKhuyenMai())));
						Object data[] = { Boolean.TRUE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								sp.getKhuyenMai().getMaKM(), giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
					list.clear();
					// Danh sách sản phẩm không có maKM tìm theo phân loại
					try {
						list = ds.getSanPhanTheoPhanLoaiNull(phanLoai);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list) {
						String maKMSP = "Null"; // Mặc định là "null" nếu khuyến mãi là null
						double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến
																						// mãi
						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
						Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKMSP, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				}
			}
		} else if (o.equals(btnTimKiemSanPham)) {
			String maSPTim = txtTimKiemSanPham.getText().trim();
			if (maSPTim.equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập mã sản phẩm cần tìm !");
			} else {
				boolean find = false; // Kiểm tra xem có tìm thấy hay không
				for (int i = 0; i < tblSanPham.getRowCount(); i++) {
					String maSP = (String) tblSanPham.getValueAt(i, 1);
					if (maSPTim.equalsIgnoreCase(maSP)) {
						// // Nếu sản phẩm được tìm thấy, đưa sản phẩm đó lên đầu bảng
						Vector<Object> rowData = new Vector<Object>();
						for (int col = 0; col < modelSanPham.getColumnCount(); col++) {
							rowData.add(tblSanPham.getValueAt(i, col));
						}
						modelSanPham.removeRow(i);
						modelSanPham.insertRow(0, rowData);
						modelSanPham.setValueAt(true, 0, 0);
						find = true;
						JOptionPane.showMessageDialog(null, "Tìm thấy, Đã chọn sản phẩm và đưa lên đầu bảng !");
						txtTimKiemSanPham.setText("");
						break;
					}
				}
				if (!find) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy !");
				}
			}
		} else if (o.equals(btnThemKhuyenMai)) {
//			 Nếu là button lưu thì thực hiện thêm khuyến mãi
			if (btnThemKhuyenMai.getText().equalsIgnoreCase("Lưu")) {
				if (validData()) {
					// Lấy dữ liệu từ JtexFiled, JDateChooser thêm vào danh sách khuyến mãi
					KhuyenMaiDAO ds = null;
					try {
						ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					;
					KhuyenMai km = reverSPFfromTextFile();
					int row = tblSanPham.getSelectedRow();
					// Nếu muốn thêm chương trình khuyến mãi mà không chọn sản phẩm thì hỏi người
					// dùng có chắc chắn không ?
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để áp khuyến mãi !");
					} else if (row != -1) {
						List<String> danhSachSanPhamVuotKM = new ArrayList<>(); // Dánh sách sản phẩm vượt quá Lời
						for (row = 0; row < tblSanPham.getRowCount(); row++) {
							boolean isChecked = (boolean) tblSanPham.getValueAt(row, 0);
							if (isChecked) {
								// Xóa tự phần trăm và kiểm tra mức khuyến mãi có vượt qua lời không
								Double mucKM = Double.parseDouble(txtMucKhuyenMai.getText().replace("%", ""));
								Double loi = Double
										.parseDouble(((String) tblSanPham.getValueAt(row, 3)).replace("%", ""));
								if (mucKM > loi) {
									String maSP = (String) tblSanPham.getValueAt(row, 1);
									danhSachSanPhamVuotKM.add(maSP);
								}
							}
						}
						if (!danhSachSanPhamVuotKM.isEmpty()) { // Nếu có sản phẩm vượt quá lời
							JOptionPane.showMessageDialog(null,
									"Mức khuyến mãi " + txtMucKhuyenMai.getText() + " vượt quá 'Lời' sản phẩm "
											+ danhSachSanPhamVuotKM
											+ " vui lòng chọn sản phẩm khác hoặc thay đổi mức khuyến mãi !");
							txtMucKhuyenMai.requestFocus();
						} else
							try {
								if (ds.createKhuyenMai(km)) { // Thêm khuyến mãi vào SQL
									btnThemKhuyenMai.setText("Thêm");
									btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
									btnThemKhuyenMai.setBackground(new Color(65, 105, 255));
									btnThemKhuyenMai
											.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/add.png")));

									btnLamMoi.setText("Làm mới");
									btnLamMoi.setBackground(new Color(152, 251, 152));
									btnLamMoi.setIcon(
											new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/loading.png")));
									btnSuaKhuyenMai.setEnabled(true);
									txtTenKhuyenMai.setEditable(false);
									txtMucKhuyenMai.setEditable(false);
									dateChooserThoiGianBatDauGiamGia.setEnabled(false);
									dateChooserThoiGianKetThucGiamGia.setEnabled(false);

									List<String> danhSachMaSPDuocChon = new ArrayList<>();
									String maKM = txtMaKhuyenMai.getText().toString();
									// Cập nhật, thêm mã khuyến mãi cho sản phẩm được chọn
									for (row = 0; row < tblSanPham.getRowCount(); row++) {
										// Kiểm tra cột 0 hàng hiện tại có được tick vào ô kiểm hay không
										boolean isChecked = (boolean) tblSanPham.getValueAt(row, 0);
										if (isChecked) { // Nếu ô kiểm đã được tick
											// Lấy ra mã sản phẩm ở ô kiểm đã được tick
											String maSP = (String) tblSanPham.getValueAt(row, 1);
											danhSachMaSPDuocChon.add(maSP);
											tblSanPham.setEnabled(false);
											btnThemKhuyenMai.setText("Thêm");
											btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
											btnThemKhuyenMai.setBackground(new Color(65, 105, 255));
											btnThemKhuyenMai.setIcon(
													new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/add.png")));

											btnLamMoi.setText("Làm mới");
											btnLamMoi.setBackground(new Color(152, 251, 152));
											btnLamMoi.setIcon(new ImageIcon(
													QuanLyKhuyenMai.class.getResource("/icon/loading.png")));
											txtTenKhuyenMai.setEditable(false);
											txtMucKhuyenMai.setEditable(false);
											dateChooserThoiGianBatDauGiamGia.setEnabled(false);
											dateChooserThoiGianKetThucGiamGia.setEnabled(false);
										}
									}
									for (String maSP : danhSachMaSPDuocChon) {
										ds.updateMaKMChoSanPHam(km, maKM, maSP);
									}
									JOptionPane.showMessageDialog(null, "Thêm thành công !!");
									updateTableSanPham();
									updateTableKhuyenMai();
									xoaRong();
								} else {
									JOptionPane.showMessageDialog(null, "Không thể thêm do trùng mã !");
									updateTableSanPham();
								}
							} catch (HeadlessException | RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				}
			} else { // Ngược lại khi nhấn vào nút thêm
				xoaRong();
				updateTableSanPham();
				tblSanPham.setEnabled(true);
				txtTenKhuyenMai.requestFocus();
				try {
					txtMaKhuyenMai.setText(dskm.getAutoID());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Khi nhấn nút thêm thì tự động phát sinh maKM

				btnThemKhuyenMai.setText("Lưu");
				btnThemKhuyenMai.setForeground(new Color(0, 0, 0));
				btnThemKhuyenMai.setBackground(new Color(210, 105, 30));
				btnThemKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/luulienket.png")));

				btnLamMoi.setText("Hủy");
				btnLamMoi.setForeground(new Color(0, 0, 0));
				btnLamMoi.setBackground(new Color(255, 0, 0));
				btnLamMoi.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/x.png")));

				txtTenKhuyenMai.setEditable(true);
				txtMucKhuyenMai.setEditable(true);
				btnSuaKhuyenMai.setEnabled(false);
				dateChooserThoiGianBatDauGiamGia.setEnabled(true);
				dateChooserThoiGianKetThucGiamGia.setEnabled(true);
			}
		} else if (o.equals(btnSuaKhuyenMai)) {
			int rowKhuyenMai = tblKhuyenMai.getSelectedRow();
			if (btnSuaKhuyenMai.getText().equalsIgnoreCase("Sửa")) {
				if (rowKhuyenMai == -1) {
					JOptionPane.showMessageDialog(null, "Hãy một chương trình khuyến mãi muốn sửa !");
				} else {
					tblSanPham.setEnabled(true);
					btnSuaKhuyenMai.setText("Lưu");
					btnSuaKhuyenMai.setForeground(new Color(0, 0, 0));
					btnSuaKhuyenMai.setBackground(new Color(210, 105, 30));
					btnSuaKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/luulienket.png")));
					txtTenKhuyenMai.setEditable(true);
					txtMucKhuyenMai.setEditable(true);
					btnThemKhuyenMai.setEnabled(false);
					dateChooserThoiGianBatDauGiamGia.setEnabled(true);
					dateChooserThoiGianKetThucGiamGia.setEnabled(true);

					btnLamMoi.setText("Hủy");
					btnLamMoi.setForeground(new Color(0, 0, 0));
					btnLamMoi.setBackground(new Color(255, 0, 0));
					btnLamMoi.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/x.png")));
					// Khi bấm vào btnSua thì sẽ hiển thị lại danh sách sản phẩm chưa có mã khuyến
					// mãi
					KhuyenMaiDAO ds = null;
					try {
						ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					List<SanPham> list = null;
					try {
						list = ds.getSanPhamMaKMIsNull();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (SanPham sp : list) {
						String maKM = "Null"; // Mặc định là "null" nếu khuyến mãi là null
						double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

						String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan()));

						String giaSauKhuyenMai = dinhDangTien(
								String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
						Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%",
								maKM, giaBan, giaSauKhuyenMai };
						modelSanPham.addRow(data);
					}
					tblSanPham.setModel(modelSanPham);
				}
			} else { // Nếu là btnLuu
				if (validData()) {
					KhuyenMai km = reverSPFfromTextFile();
					KhuyenMaiDAO ds = null;
					try {
						ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Boolean isChecked = false;
					Boolean capNhatThanhCong = false;
					String maKM = txtMaKhuyenMai.getText().toString(); // Mã khuyến mãi trong textFiled maKM
					List<String> danhSachMaSPDuocChon = new ArrayList<>(); // Danh sách sản phẩm chưa có maKM (null)
					List<String> danhSachMaSPHuyMaKM = new ArrayList<>(); // Danh sách sản phẩm đã có maKM nhưng bị hủy
					List<String> danhSachSanPhamVuotKM = new ArrayList<>(); // Danh sách sản phẩm vướt quá Lời
					// Cập nhật, thêm mã khuyến mãi cho sản phẩm được chọn
					for (int row = 0; row < tblSanPham.getRowCount(); row++) {
						// Kiểm tra cột 0 hàng hiện tại có được tick vào ô kiểm hay không
						isChecked = (boolean) tblSanPham.getValueAt(row, 0);
						String maSP = (String) tblSanPham.getValueAt(row, 1);
						String maKMSP = (String) tblSanPham.getValueAt(row, 4); // Mã khuyến mãi của sản phẩm
						// Xóa tự phần trăm và kiểm tra mức khuyến mãi có vượt qua lời không
						Double mucKM = Double.parseDouble(txtMucKhuyenMai.getText().replace("%", ""));
						Double loi = Double.parseDouble(((String) tblSanPham.getValueAt(row, 3)).replace("%", ""));

						if (isChecked && mucKM > loi) { // Nếu có sản phẩm vượt quá lời
							danhSachSanPhamVuotKM.add(maSP);
						} else if (isChecked && maKMSP.equals("Null")) {// Nếu sản phẩm chưa có khuyến mãi và được chọn
							danhSachMaSPDuocChon.add(maSP);
						} else if (!isChecked && maKMSP.equals(maKM)) { // Nếu muốn hủy maKM của sản phẩm đó
							danhSachMaSPHuyMaKM.add(maSP);
						}
					}

					if (!danhSachSanPhamVuotKM.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Mức khuyến mãi " + txtMucKhuyenMai.getText() + " vượt quá 'Lời' sản phẩm "
										+ danhSachSanPhamVuotKM
										+ " vui lòng chọn sản phẩm khác hoặc thay đổi mức khuyến mãi !");
						txtMucKhuyenMai.requestFocus();
					} else {
						for (int row = 0; row < tblSanPham.getRowCount(); row++) {
							isChecked = (boolean) tblSanPham.getValueAt(row, 0);
							if (isChecked) {
								isChecked = true;
								break;
							}
						}
						if (!isChecked) { // Nếu hủy KM của tất sản đang được KM và không thêm KM cho sản phẩm nào khác
							if (JOptionPane.showConfirmDialog(this,
									"Bạn có chắc chắn muốn xóa khuyến mãi của tất cả sản phẩm của chương trình khuyến mãi "
											+ maKM + " ?",
									"Cảnh Báo !!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
								for (int row = 0; row < modelSanPham.getRowCount(); row++) {
									for (String maSP : danhSachMaSPHuyMaKM) {
										String maSP2 = (String) modelSanPham.getValueAt(row, 1);
										if (maSP.equals(maSP2)) {
											modelSanPham.setValueAt(Boolean.TRUE, row, 0);
										}
									}
								}
							} else {
								JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
								for (String maSP : danhSachMaSPHuyMaKM) {
									try {
										ds.deleteMaKMChoSanPham(km, maSP);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								if (JOptionPane.showConfirmDialog(this,
										"Không còn sản phẩm nào được áp chương trình khuyến mãi " + maKM
												+ " bạn có muốn xóa chương trình khuyến mãi này ?",
										"Cảnh Báo !!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
									JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
									try {
										ds.deleteKhuyenMai(maKM);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								capNhatThanhCong = true;
							}
						} else if (!danhSachMaSPHuyMaKM.isEmpty()) {
							if (JOptionPane.showConfirmDialog(this,
									"Bạn có chắc chắn muốn hủy khuyến mãi " + maKM + " của sản phẩm "
											+ danhSachMaSPHuyMaKM + " ?",
									"Cảnh Báo !!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
								for (int row = 0; row < modelSanPham.getRowCount(); row++) {
									for (String maSP : danhSachMaSPHuyMaKM) {
										String maSP2 = (String) modelSanPham.getValueAt(row, 1);
										if (maSP.equals(maSP2)) {
											modelSanPham.setValueAt(Boolean.TRUE, row, 0);
										}
									}
								}
							} else {
								for (String maSP : danhSachMaSPHuyMaKM) {
									try {
										ds.deleteMaKMChoSanPham(km, maSP);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								capNhatThanhCong = true;
							}
						}
						if (!danhSachMaSPDuocChon.isEmpty()) {
							if (JOptionPane.showConfirmDialog(this,
									"Bạn có chắc chắn muốn thêm khuyến mãi " + maKM + " cho sản phẩm "
											+ danhSachMaSPDuocChon + " ?",
									"Cảnh Báo !!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								for (String maSP : danhSachMaSPDuocChon) {
									try {
										ds.updateMaKMChoSanPHam(km, maKM, maSP);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								capNhatThanhCong = true;
							}
						}
						if (isChecked && danhSachMaSPHuyMaKM.isEmpty() && danhSachMaSPDuocChon.isEmpty()) {
							try {
								ds.updateMaKMChoSanPHam(km, maKM, "");
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							capNhatThanhCong = true;
						}
						if (capNhatThanhCong) {
							JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
							xoaRong();
							updateTableSanPham();
							updateTableKhuyenMai();
							tblKhuyenMai.setModel(modelKhuyenMai);
							tblSanPham.setEnabled(false);
							btnSuaKhuyenMai.setText("Sửa");
							btnSuaKhuyenMai.setForeground(new Color(0, 0, 0));
							btnSuaKhuyenMai.setBackground(new Color(255, 255, 0));
							btnSuaKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/sua.png")));

							btnThemKhuyenMai.setText("Thêm");
							btnThemKhuyenMai.setForeground(new Color(255, 255, 255));
							btnThemKhuyenMai.setBackground(new Color(65, 105, 255));
							btnThemKhuyenMai.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/add.png")));
							btnThemKhuyenMai.setEnabled(true);

							btnLamMoi.setText("Làm mới");
							btnLamMoi.setBackground(new Color(152, 251, 152));
							btnLamMoi.setIcon(new ImageIcon(QuanLyKhuyenMai.class.getResource("/icon/loading.png")));
							txtTenKhuyenMai.setEditable(false);
							txtMucKhuyenMai.setEditable(false);
							dateChooserThoiGianBatDauGiamGia.setEnabled(false);
							dateChooserThoiGianKetThucGiamGia.setEnabled(false);
						}
					}
				}
			}
		} else if (o.equals(btnLamMoi)) {
			xoaRong();
			updateTableSanPham();
			updateTableKhuyenMai();
		}
	}

	private void xoaRong() {
		txtMaKhuyenMai.setText("");
		txtTenKhuyenMai.setText("");
		txtMucKhuyenMai.setText("");
		dateChooserThoiGianBatDauGiamGia.setDate(null);
		dateChooserThoiGianKetThucGiamGia.setDate(null);
		checkBoxChonTatCa.setSelected(false);
		comboBoxPhanLoai.setSelectedIndex(0);
		for (int row = 0; row < modelSanPham.getRowCount(); row++) {
			modelSanPham.setValueAt(false, row, 0);
		}
	}

	private void showMessage(String mess, JTextField txt) {
		JOptionPane.showMessageDialog(null, mess);
		txt.requestFocus();
	}

	// Kiểm tra Regex
	private boolean validData() {
		String tenKM = txtTenKhuyenMai.getText().trim();
		String mucKM = txtMucKhuyenMai.getText().trim();
		String xoaKiTuPhanTramMucKM = mucKM.replace("%", "");
		Date nbd = dateChooserThoiGianBatDauGiamGia.getDate();
		Date nkt = dateChooserThoiGianKetThucGiamGia.getDate();

		// \\p{L} Bất kỳ ký tự chữ nào trong bảng mã, cho phép chuỗi chứa bất kỳ chữ
		// cái, số, dấu và có thể có kí tự %
		if (!(tenKM.length() > 0)) {
			showMessage("Tên Khuyến Mãi không được để rỗng !", txtTenKhuyenMai);
			return false;
		}
		if (!(tenKM.matches("^[a-zA-Z].*"))) {
			showMessage("Tên Khuyến Mãi phải bắt đầu là kí tự chữ !", txtTenKhuyenMai);
			return false;
		}
		if (!(mucKM.length() > 0)) {
			showMessage("Mức Khuyến Mãi không được để rỗng !", txtMucKhuyenMai);
			return false;
		}
		if (!(xoaKiTuPhanTramMucKM.matches("^-?\\d+(\\.\\d+)?$"))) {
			showMessage("Mức Khuyến Mãi phải là số !", txtMucKhuyenMai);
			return false;
		}
		Double soKhuyenMai = Double.parseDouble(xoaKiTuPhanTramMucKM);
		if (!(soKhuyenMai > 0 && soKhuyenMai < 100)) {
			showMessage("Mức khuyến mãi phải lớn hơn 0% và bé hơn loi (%) của sản phẩm !", txtMucKhuyenMai);
			return false;
		}
		if (nbd == null) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu khuyến mãi !");
			return false;
		}
		if (nbd != null) {
			// Chuyển đổi Date thành LocalDate
			Instant chuyenNBD = nbd.toInstant();
			LocalDate ngayBatDau = chuyenNBD.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			LocalDate ngayHienTai = LocalDate.now();
			// Kiểm tra Ngày bắt đầu có lớn hoặc bằng ngày hiện tại không
			if (!(ngayBatDau.isAfter(ngayHienTai) || ngayBatDau.isEqual(ngayHienTai))) {
				showMessage("Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại !", txtMucKhuyenMai);
				return false;
			}
		}
		if (nkt == null) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày kết thúc khuyến mãi !");
			return false;
		}
		if (nbd != null) {
			// Chuyển đổi Date thành LocalDate
			Instant chuyenNBD = nbd.toInstant();
			LocalDate ngayBatDau = chuyenNBD.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			Instant chuyenNKT = nkt.toInstant();
			LocalDate ngayKetThuc = chuyenNKT.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			// Kiểm tra Ngày kết thúc có lớn hoặc bằng ngày Bắt đầu không
			if (!(ngayKetThuc.isAfter(ngayBatDau))) {
				showMessage("Ngày kết thúc phải lớn hơn ngày bắt đầu !", txtMucKhuyenMai);
				return false;
			}
		}
		return true;
	}

	// Lấy dữ liệu từ JtexField khi nhập vào
	private KhuyenMai reverSPFfromTextFile() {
		String maKM = txtMaKhuyenMai.getText().toString();
		String tenKM = txtTenKhuyenMai.getText().toString();

		String phanTram = txtMucKhuyenMai.getText().toString();
		String xoaKiTuPhanTram = phanTram.replace("%", ""); // Sử dụng phương thức replace để bỏ ký tự %
		Double mucKM = Double.parseDouble(xoaKiTuPhanTram);

		// Chuyển ngày bắt đầu và ngày kết thúc thành java.sql.Date để insert vào SQL
		java.util.Date ngayBatDau = dateChooserThoiGianBatDauGiamGia.getDate();
		LocalDate nbd = ngayBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		java.util.Date ngayKetThuc = dateChooserThoiGianKetThucGiamGia.getDate();
		LocalDate nkt = ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return new KhuyenMai(maKM, mucKM, tenKM, nbd, nkt);
	}

	// Đưa dữ liệu vào ComboBox
	private void updateComboBox() {
		comboBoxPhanLoai.removeAllItems(); // Xóa tất cả các item cũ để cập nhật lại sau khi(Thêm, Xóa)
		List<PhanLoai> list = null;
		try {
			list = dspl.getAllPhanLoai();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comboBoxPhanLoai.addItem("All");
		for (PhanLoai pl : list) {
			comboBoxPhanLoai.addItem(pl.getPhanLoai());
		}
	}

	// Đưa dữ liệu vào table KhuyenMAi
	private void updateTableKhuyenMai() {
		KhuyenMaiDAO ds = null;
		try {
			ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<KhuyenMai> list = null;
		try {
			list = ds.doTuBang();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelKhuyenMai.setRowCount(0);
		for (KhuyenMai km : list) {
			Object data[] = { km.getMaKM(), km.getTenKhuyenMai(), km.getPhanTramKhuyenMai() + "%", km.getNgayBatDau(),
					km.getNgayKetThuc() };
			modelKhuyenMai.addRow(data);
		}
		tblKhuyenMai.setModel(modelKhuyenMai);
	}

	// Đưa dữ liệu vào table SanPham
	private void updateTableSanPham() {
		KhuyenMaiDAO ds = null;
		try {
			ds = (KhuyenMaiDAO) registry.lookup("KhuyenMaiDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SanPham> list = null;
		try {
			list = ds.getSanPhamMaKMIsNull();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelSanPham.setRowCount(0);
		for (SanPham sp : list) {
			String maKM = "Null"; // Mặc định là "null" nếu khuyến mãi là null
			double phanTramKhuyenMai = 0.0; // Mặc định là 0.0 nếu khuyến mãi là null

			String giaBan = dinhDangTien(String.valueOf(sp.getGiaBan())); // Định dạng giá gốc, giá khuyến mãi
			String giaSauKhuyenMai = dinhDangTien(
					String.valueOf(sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), phanTramKhuyenMai)));
			Object data[] = { Boolean.FALSE, sp.getMaSP(), sp.getTenSP(), sp.getLoiTheoPhanTram() + "%", maKM, giaBan,
					giaSauKhuyenMai };
			modelSanPham.addRow(data);
		}
		tblSanPham.setModel(modelSanPham);
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
