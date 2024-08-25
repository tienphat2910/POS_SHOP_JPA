package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ChatLieuDAO;
import dao.KhuyenMaiDAO;
import dao.KichThuocDAO;
import dao.KieuDangDAO;
import dao.MauSacDAO;
import dao.NhaCungCapDAO;
import dao.PhanLoaiDAO;
import dao.SanPhamDAO;
import dao.XuatXuDAO;
import entities.ChatLieu;
import entities.KichThuoc;
import entities.KieuDang;
import entities.MauSac;
import entities.NhaCungCap;
import entities.PhanLoai;
import entities.XuatXu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class QuanLyNhaCungCap extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenNCC;
	private JTextField txtTimKiem;
	private JTable tblNCC;
	private ButtonGroup rd_group = new ButtonGroup();
	private NhaCungCapDAO dsncc;
	private MauSacDAO mauSac_Dao;
	private KichThuocDAO kichThuoc_Dao;
	private XuatXuDAO xuatXu_Dao;
	private ChatLieuDAO chatLieu_Dao;
	private PhanLoaiDAO phanLoai_Dao;
	private KieuDangDAO kieuDang_Dao;
	private JButton btnSua;
	private JButton btnLuu;
	private JButton btnThem;
	private JButton btnHuy;
	private DefaultTableModel dtm;
	private JButton btnLamMoi;
	private String trangThaiThuocTinh;
	private int trangthaibtn;
	private String maNCC;
	private JTextField txtDiaChi;
	private JTextField txtSDT;
	private JTextField txtEmail;
	private Registry registry;

	/**
	 * Create the panel.
	 */
	public QuanLyNhaCungCap() {
//		try {
//			registry = LocateRegistry.getRegistry("localhost", 1232);
//			mauSac_Dao = (MauSacDAO) registry.lookup("MauSacDAO");
//			kichThuoc_Dao = (KichThuocDAO) registry.lookup("KichThuocDAO");
//			xuatXu_Dao = (XuatXuDAO) registry.lookup("XuatXuDAO");
//			chatLieu_Dao = (ChatLieuDAO) registry.lookup("ChatLieuDAO");
//			phanLoai_Dao = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
//			kieuDang_Dao = (KieuDangDAO) registry.lookup("KieuDangDAO");
//		} catch (RemoteException | NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
			dsncc = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initComponents();
		tblNhaNCC();
	}

	private void initComponents() {

		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(932, 685));
		setLayout(new CardLayout(0, 0));

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		add(mainPanel, "name_115175246584900");

		JPanel pnlThuocTinh = new JPanel();
		pnlThuocTinh.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Thu\u1ED9c t\u00EDnh s\u1EA3n ph\u1EA9m",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				null));
		pnlThuocTinh.setBackground(new Color(255, 255, 255));

		JPanel pnlDanhSachNCC = new JPanel();
		pnlDanhSachNCC.setBorder(new CompoundBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Danh S\u00E1ch Nh\u00E0 Cung C\u1EA5p",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				null));
		pnlDanhSachNCC.setBackground(new Color(255, 255, 255));
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_mainPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(pnlDanhSachNCC, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 908,
										Short.MAX_VALUE)
								.addComponent(pnlThuocTinh, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 908,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap()
						.addComponent(pnlThuocTinh, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(pnlDanhSachNCC, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
						.addContainerGap()));

		JLabel lblTmKimThuc = new JLabel("Tìm kiếm NCC : ");
		lblTmKimThuc.setFont(new Font("Arial", Font.PLAIN, 12));

		txtTimKiem = new JTextField();
		txtTimKiem.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTimKiem.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(new Color(255, 255, 255));
		GroupLayout gl_pnlDanhSachNCC = new GroupLayout(pnlDanhSachNCC);
		gl_pnlDanhSachNCC.setHorizontalGroup(gl_pnlDanhSachNCC.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDanhSachNCC.createSequentialGroup().addGap(28)
						.addComponent(lblTmKimThuc, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtTimKiem, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(383, Short.MAX_VALUE))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE));
		gl_pnlDanhSachNCC.setVerticalGroup(gl_pnlDanhSachNCC.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDanhSachNCC.createSequentialGroup().addGap(14)
						.addGroup(gl_pnlDanhSachNCC.createParallelGroup(Alignment.BASELINE).addComponent(lblTmKimThuc)
								.addComponent(txtTimKiem, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)));
		panel.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane, "name_117489822752800");

		tblNCC = new JTable();
		tblNCC.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "M\u00E3 NCC", "T\u00EAn Nh\u00E0 Cung C\u1EA5p", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i ",
						"Email", "\u0110\u1ECBa Ch\u1EC9" }));
		tblNCC.getColumnModel().getColumn(1).setPreferredWidth(118);
		scrollPane.setViewportView(tblNCC);
		pnlDanhSachNCC.setLayout(gl_pnlDanhSachNCC);

		JLabel lblNewLabel = new JLabel("Tên Nhà Cung Cấp:");
		lblNewLabel.setBounds(16, 32, 109, 14);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));

		// ==========================================
		// CÁC NÚT
		// ==========================================

		btnThem = new JButton("thêm");
		btnThem.setBounds(74, 192, 71, 29);
		btnThem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				trangthaibtn = 1;
				setEnibleTXT(true);
				btnLuu.setEnabled(true);
				btnSua.setEnabled(false);
				btnThem.setEnabled(false);
				btnHuy.setEnabled(true);
				txtTenNCC.setText("");
				txtSDT.setText("");
				txtDiaChi.setText("");
				txtEmail.setText("");
			}
		});
		btnThem.setIcon(new ImageIcon(QuanLyNhaCungCap.class.getResource("/icon/add.png")));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFont(new Font("Arial", Font.BOLD, 12));
		btnThem.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThem.setBackground(new Color(65, 105, 225));

		btnSua = new JButton("Sửa");
		btnSua.setBounds(194, 192, 78, 29);
		btnSua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				trangthaibtn = 2;
				setEnibleTXT(true);
				btnLuu.setEnabled(true);
				btnSua.setEnabled(false);
				btnThem.setEnabled(false);
				btnHuy.setEnabled(true);
			}
		});
		btnSua.setIcon(new ImageIcon(QuanLyNhaCungCap.class.getResource("/icon/sua.png")));
		btnSua.setFont(new Font("Arial", Font.BOLD, 12));
		btnSua.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnSua.setBackground(Color.YELLOW);

		btnLuu = new JButton("Lưu");
		btnLuu.setBounds(337, 192, 77, 29);
		btnLuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (trangthaibtn == 1) {
					String tenNCC = txtTenNCC.getText();
					String diaChi = txtDiaChi.getText();
					String email = txtEmail.getText();
					String sdt = txtSDT.getText();
					String maNCC = null;
					try {
						maNCC = dsncc.getAutoID();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
					NhaCungCapDAO cungCapDAO = null;
					try {
						cungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						cungCapDAO.addNhaCungCap(ncc);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tblNhaNCC();
				}
				if (trangthaibtn == 2) {
					String tenNCC = txtTenNCC.getText();
					String diaChi = txtDiaChi.getText();
					String email = txtEmail.getText();
					String sdt = txtSDT.getText();
					NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
					NhaCungCapDAO cungCapDAO = null;
					try {
						cungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						cungCapDAO.editNhaCungCap(ncc);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tblNhaNCC();
				}
			}
		});
		btnLuu.setEnabled(false);
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnibleTXT(false);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnLuu.setEnabled(false);
				btnHuy.setEnabled(false);

			}
		});
		btnLuu.setIcon(new ImageIcon(QuanLyNhaCungCap.class.getResource("/icon/luulienket.png")));
		btnLuu.setFont(new Font("Arial", Font.BOLD, 12));
		btnLuu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLuu.setBackground(new Color(255, 165, 0));

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTenNCC.setText("");
				txtDiaChi.setText("");
				txtEmail.setText("");
				txtSDT.setText("");
			}
		});
		btnLamMoi.setBounds(465, 193, 137, 27);
		btnLamMoi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtTenNCC.setText("");
				setEnibleTXT(false);
				btnThem.setEnabled(true);
				btnLuu.setEnabled(true);
				btnHuy.setEnabled(false);
				btnLuu.setEnabled(false);
				txtTimKiem.setEnabled(false);
				trangthaibtn = 0;
			}
		});
		btnLamMoi.setIcon(new ImageIcon(QuanLyNhaCungCap.class.getResource("/icon/refesh.png")));
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
		btnLamMoi.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLamMoi.setBackground(new Color(144, 238, 144));

		btnHuy = new JButton("Hủy");
		btnHuy.setBounds(659, 193, 91, 27);
		btnHuy.setEnabled(false);
		btnHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setEnibleTXT(false);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnLuu.setEnabled(false);
				btnHuy.setEnabled(false);
				txtTenNCC.setText("");
				txtDiaChi.setText("");
				txtEmail.setText("");
				txtSDT.setText("");
				trangthaibtn = 0;
			}
		});
		btnHuy.setIcon(new ImageIcon(QuanLyNhaCungCap.class.getResource("/icon/x.png")));
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Arial", Font.BOLD, 12));
		btnHuy.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnHuy.setBackground(Color.RED);

		txtTenNCC = new JTextField();
		txtTenNCC.setBounds(16, 57, 453, 25);
		txtTenNCC.setEnabled(false);
//		txtTenThuocTinh.setBackground(Color.LIGHT_GRAY);
//		txtTenThuocTinh.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTenNCC.setColumns(10);
		// ===========================================
		// chạy tổng thể:
		pnlThuocTinh.setLayout(null);
		pnlThuocTinh.add(lblNewLabel);
		pnlThuocTinh.add(txtTenNCC);
		pnlThuocTinh.add(btnThem);
		pnlThuocTinh.add(btnSua);
		pnlThuocTinh.add(btnLuu);
		pnlThuocTinh.add(btnLamMoi);
		pnlThuocTinh.add(btnHuy);

		JLabel lblDaChNh = new JLabel("Dịa Chỉ Nhà Cung Cấp :");
		lblDaChNh.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDaChNh.setBounds(16, 109, 150, 14);
		pnlThuocTinh.add(lblDaChNh);

		txtDiaChi = new JTextField();
		txtDiaChi.setEnabled(false);
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(16, 136, 453, 25);
		pnlThuocTinh.add(txtDiaChi);

		txtSDT = new JTextField();
		txtSDT.setEnabled(false);
		txtSDT.setColumns(10);
		txtSDT.setBounds(556, 57, 313, 25);
		pnlThuocTinh.add(txtSDT);

		JLabel lblSinThoi = new JLabel("Số điện thoại :");
		lblSinThoi.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSinThoi.setBounds(556, 32, 109, 14);
		pnlThuocTinh.add(lblSinThoi);

		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(556, 111, 109, 14);
		pnlThuocTinh.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(556, 136, 313, 25);
		pnlThuocTinh.add(txtEmail);
		mainPanel.setLayout(gl_mainPanel);
		tblNCC.addMouseListener(this);

		// tim kiếm liên tục
		txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				timkiemNCC();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				timkiemNCC();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				timkiemNCC();
			}
		});
	}

	private void timkiemNCC() {
		String input = txtTimKiem.getText();
		if (input.equalsIgnoreCase("")) {
			tblNhaNCC();
		} else {
			NhaCungCapDAO nhaCungCapDAO = null;
			try {
				nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<NhaCungCap> list = null;
			try {
				list = nhaCungCapDAO.timKiemNCC(input);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clearTable();
			DefaultTableModel dtm = (DefaultTableModel) tblNCC.getModel();
			for (NhaCungCap ncc : list) {
				Object[] rowData = { ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getEmail(), ncc.getDiaChi() };
				dtm.addRow(rowData);
			}
		}
	}

	private void setEnibleTXT(boolean check) {
		txtTenNCC.setEnabled(check);
		txtDiaChi.setEnabled(check);
		txtSDT.setEnabled(check);
		txtEmail.setEnabled(check);
	}

	private void clearInput() {
		txtTenNCC.setText("");
	}

	private void clearTable() {
		DefaultTableModel dtm = (DefaultTableModel) tblNCC.getModel();
		dtm.setRowCount(0);
	}

	private void tblNhaNCC() {
		NhaCungCapDAO nhaCungCapDAO = null;
		try {
			nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<NhaCungCap> list = null;
		try {
			list = nhaCungCapDAO.getAllNhaCungCap();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clearTable();
		DefaultTableModel dtm = (DefaultTableModel) tblNCC.getModel();
		for (NhaCungCap ncc : list) {
			Object[] rowData = { ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getEmail(), ncc.getDiaChi() };
			dtm.addRow(rowData);
		}
	}

	public boolean validData() {
		String tenThuocTinh = txtTenNCC.getText().trim();

		if (tenThuocTinh.length() == 0) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập tên thuộc tính");
			return false;
		}

		return true;
	}
	/// them thuoc tinh

	private void themMauSac(String thuocTinh) {
//		try {
//			MauSacDAO listms = (MauSacDAO) registry.lookup("MauSacDAO");
//		} catch (RemoteException | NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String s1 = "MS00" +(listms)

//		MauSac ms = new MauSac(thuocTinh, thuocTinh);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tblNCC.getSelectedRow();
		maNCC = tblNCC.getValueAt(row, 0).toString();
		System.out.println(maNCC);
		txtTenNCC.setText(tblNCC.getValueAt(row, 1).toString());
		txtSDT.setText(tblNCC.getValueAt(row, 2).toString());
		txtEmail.setText(tblNCC.getValueAt(row, 3).toString());
		txtDiaChi.setText(tblNCC.getValueAt(row, 4).toString());
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
}
