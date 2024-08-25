package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.MacSpi;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.zxing.WriterException;

import component.BarcodeGenerator;
import dao.ChatLieuDAO;
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
import entities.SanPham;
import entities.XuatXu;

//import ConnectDB.KetNoiSQL;
//import component.BarcodeGenerator;
//import dao.ChatLieuDAO;
//import dao.KhuyenMaiDAO;
//import dao.KichThuocDAO;
//import dao.KieuDangDAO;
//import dao.MauSacDAO;
//import dao.NhaCungCapDAO;
//import dao.PhanLoaiDAO;
//import dao.SanPhamDAO;
//import dao.XuatXuDAO;
//import entity.ChatLieu;
//import entity.KhuyenMai;
//import entity.KichThuoc;
//import entity.KieuDang;
//import entity.MauSac;
//import entity.NhaCungCap;
//import entity.PhanLoai;
//import entity.SanPham;
//import entity.XuatXu;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import javax.swing.JDesktopPane;

public class QuanLySanPham extends JPanel implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenSP;
	private JTextField txtSoLuongSP;
	private JTextField txtGiaNhap;
	private JTable tblXemTruoc;
	private JTextField txtThayDoiSoLuong;
	private JTextField txtTimKiemSP;
	private JTable tbllistSanPham;
//	private SanPhamDAO sanPhamDAO;
//    private MauSacDAO mauSacDAO;
//    private KichThuocDAO kichThuocDAO;
//    private KieuDangDAO kieuDangDAO;
//    private ChatLieuDAO chatLieuDAO;
//    private PhanLoaiDAO phanLoaiDAO;
//    private XuatXuDAO xuatXuDAO;
//    private NhaCungCapDAO nhaCungCapDAO;
//    private KhuyenMaiDAO khuyenMaiDAO;
	private JComboBox cboMauSac;
	private JComboBox cboLoaiSanPham;
	private JComboBox cboChatLieu;
	private JComboBox cboKieuDang;
	private JComboBox cboxuatXu;
	private JComboBox cboKichThuocBatDau;
	private JComboBox cboGiaLoi;
	private JComboBox cboNCC;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnLuu;
	public JButton btnLamMoi;
	private JButton btnHuy;
	private JButton btnXemTruoc;
//	private SanPhamDAO listSP;
	private JTextField txtMaSP;
	private int btn;
	private JCheckBox checkbox_xuatAllKichThuoc;
	private JButton btnHinhAnh;
	private File file;
	private JPanel pnlHinhAnh;
	private JLabel lblHinhAnh;
	private JButton btnLuuTatCa;
	private DefaultTableModel dtmxemtruoc;
	private int dongcuabangxemtruoc;
	private JPanel mainPanel;
	private Registry registry;
	private NhaCungCapDAO nhaCungCapDAO;
	/**
	 * Create the panel.
	 */
	public QuanLySanPham() {

//		KetNoiSQL.getInstance().connect();
		
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uiSanPhan();
		tblDanhSachSanPham();
		loadComboBoxThuocTinh();
	}
	///
	private void loadComboBoxThuocTinh() {
		
		cboMauSac.removeAllItems();
		cboLoaiSanPham.removeAllItems();
		cboChatLieu.removeAllItems();
		cboKieuDang.removeAllItems();
		cboxuatXu.removeAllItems();
		cboKichThuocBatDau.removeAllItems();
		cboNCC.removeAllItems();
		
		try {
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			ArrayList<MauSac> listMauSac = (ArrayList<MauSac>) mauSacDAO.getAllMauSac();
			listMauSac.forEach(mauSac -> cboMauSac.addItem(mauSac.getMauSac()));
//
//        kichThuocDAO = new KichThuocDAO();
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			ArrayList<KichThuoc> listKichThuoc = (ArrayList<KichThuoc>) kichThuocDAO.getAllKichThuoc();
			listKichThuoc.forEach(kichThuoc -> cboKichThuocBatDau.addItem(kichThuoc.getKichThuoc()));
//
//        kieuDangDAO = new KieuDangDAO();
			KieuDangDAO kieuDangDAO = (KieuDangDAO) registry.lookup("KieuDangDAO");
			ArrayList<KieuDang> listKieuDang = (ArrayList<KieuDang>) kieuDangDAO.getAllKieuDang();
			listKieuDang.forEach(kieuDang -> cboKieuDang.addItem(kieuDang.getKieuDang()));
//
//        chatLieuDAO = new ChatLieuDAO();
			ChatLieuDAO chatLieuDAO = (ChatLieuDAO) registry.lookup("ChatLieuDAO");
			ArrayList<ChatLieu> listChatLieu = (ArrayList<ChatLieu>) chatLieuDAO.getAllChatLieu();
			listChatLieu.forEach(chatLieu -> cboChatLieu.addItem(chatLieu.getChatLieu()));
//
//        phanLoaiDAO = new PhanLoaiDAO();
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			ArrayList<PhanLoai> listPhanLoai = (ArrayList<PhanLoai>) phanLoaiDAO.getAllPhanLoai();
			listPhanLoai.forEach(phanLoai -> cboLoaiSanPham.addItem(phanLoai.getPhanLoai()));
//
//        xuatXuDAO = new XuatXuDAO();
			XuatXuDAO xuatXuDAO = (XuatXuDAO) registry.lookup("XuatXuDAO");
			ArrayList<XuatXu> listXuatXu = (ArrayList<XuatXu>) xuatXuDAO.getAllXuatXu();
			listXuatXu.forEach(xuatXu -> cboxuatXu.addItem(xuatXu.getXuatXu()));
//
//        nhaCungCapDAO = new NhaCungCapDAO();
			List<NhaCungCap> listNhaCungCap = new ArrayList<NhaCungCap>();
			NhaCungCapDAO cungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
			for(NhaCungCap nhaCungCap: cungCapDAO.getAllNhaCungCap()) {
				listNhaCungCap.add(nhaCungCap);
			}
			listNhaCungCap.forEach(nhaCungCap -> cboNCC.addItem(nhaCungCap.getTenNCC()));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    private void clearTable() {
        DefaultTableModel dtm = (DefaultTableModel) tbllistSanPham.getModel();
        dtm.setRowCount(0);
    }
    private void clearTableXemTruoc() {
        DefaultTableModel dtm = (DefaultTableModel) tblXemTruoc.getModel();
        dtm.setRowCount(0);
    }
    private void tblXemTruocSanPham() {
    	
		try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
	    	dtmxemtruoc = (DefaultTableModel) tblXemTruoc.getModel();
	    	List<KichThuoc> listkt = kichThuocDAO.getAllKichThuoc();
	    	SanPham sp = new SanPham();
//	    	SanPhamDAO sanPham_DAO = new SanPhamDAO();
	    	SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
	        String idPrefix = "SP";
	        int length;
			length = sanPhamDAO.getAllSanPham().size();
			int cnt = 1;
	    	double dongia = Double.parseDouble(txtGiaNhap.getText()) + Double.parseDouble(txtGiaNhap.getText())*Integer.parseInt(cboGiaLoi.getSelectedItem().toString()) /100;
	    	for(KichThuoc kt : listkt) {
	    		if(kiemTraChuoi(kt.getKichThuoc())) {
	    			Object[] rowdata = {idPrefix + String.format("%02d", length + cnt),txtTenSP.getText().toString(), kt.getKichThuoc(),dongia,txtSoLuongSP.getText()};
	        		cnt++;
//	        		Object[] rowdata = {sp.getAutoID(),txtTenSP, kt.getKichThuoc(),dongia,txtSoLuongSP};
	        		dtmxemtruoc.addRow(rowdata);
	    		}
	    	}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    public static boolean kiemTraChuoi(String input) {
        // Biểu thức chính quy: Chứa chữ cái và không chứa số
        String regex = "^[a-zA-Z]*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Kiểm tra xem chuỗi có khớp với biểu thức chính quy hay không
        return matcher.matches();
    }
	private void tblDanhSachSanPham() {
		try {
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			clearTable();
			DefaultTableModel dtm = (DefaultTableModel) tbllistSanPham.getModel();
			List<SanPham> listsp = sanPhamDAO.getAllSanPham();
			for(SanPham sp : listsp) {
				Object[] rowdata = {sp.getMaSP(), sp.getTenSP(),sp.getPhanLoai().getPhanLoai(),sp.getGiaNhap(),sp.getLoiTheoPhanTram(), (sp.getKhuyenMai() != null) ? sp.getKhuyenMai().getPhanTramKhuyenMai(): "",
						sp.getGiaBan(),sp.getKichThuoc().getKichThuoc(),sp.getSoLuong(), sp.getMauSac().getMauSac(),sp.getChatLieu().getChatLieu(),sp.getNhaCungCap().getTenNCC(),sp.getHinhAnh()};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void uiSanPhan() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(934, 687));
		setLayout(new CardLayout(0, 0));
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		add(mainPanel, "name_13998316339700");
		
		JPanel pnlXemTruoc = new JPanel();
		pnlXemTruoc.setBackground(new Color(255, 255, 255));
		pnlXemTruoc.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Th\u00F4ng tin s\u1EA3n ph\u1EA9m", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		
		JPanel pnlThongTinSanPham = new JPanel();
		pnlThongTinSanPham.setBackground(new Color(255, 255, 255));
		pnlThongTinSanPham.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Xem tr\u01B0\u1EDBc", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		
		JPanel pnlSanPham = new JPanel();
		pnlSanPham.setBackground(new Color(255, 255, 255));
		pnlSanPham.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Danh s\u00E1ch s\u1EA3n ph\u1EA9m", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		
		btnThem = new JButton("thêm");
		btnThem.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThem.setBackground(new Color(65, 105, 225));
		btnThem.setForeground(new Color(255, 255, 255));
		btnThem.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/add.png")));
		btnThem.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnSua = new JButton("Sửa");
		btnSua.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnSua.setBackground(new Color(255, 255, 0));
		btnSua.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/sua.png")));
		btnSua.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnLuu = new JButton("Lưu");
		btnLuu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLuu.setBackground(new Color(255, 165, 0));
		btnLuu.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/luulienket.png")));
		btnLuu.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLamMoi.setBackground(new Color(144, 238, 144));
		btnLamMoi.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/refesh.png")));
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnHuy = new JButton("Hủy");
		btnHuy.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnHuy.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/x.png")));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setBackground(new Color(255, 0, 0));
		btnHuy.setFont(new Font("Arial", Font.BOLD, 12));
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_mainPanel.createSequentialGroup()
							.addGap(111)
							.addComponent(btnThem, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(73)
							.addComponent(btnSua, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
							.addComponent(btnLuu, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlXemTruoc, GroupLayout.PREFERRED_SIZE, 477, Short.MAX_VALUE))
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addGap(47)
							.addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.addGap(45)
							.addComponent(btnHuy, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlThongTinSanPham, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(8, Short.MAX_VALUE))
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addComponent(pnlSanPham, GroupLayout.PREFERRED_SIZE, 924, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlXemTruoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlThongTinSanPham, 0, 0, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHuy, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLuu, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnThem, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSua, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlSanPham, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(6))
		);
		
		JPanel panel = new JPanel();
		
		JLabel lblNewLabel_3 = new JLabel("tìm kiếm sản phẩm : ");
		
		txtTimKiemSP = new JTextField();
		txtTimKiemSP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTimKiemSP.setColumns(10);
		
		txtMaSP = new JTextField();
		txtMaSP.setBackground(new Color(255, 255, 255));
		txtMaSP.setEditable(false);
		txtMaSP.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Mã Sản Phẩm: ");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 11));
		GroupLayout gl_pnlSanPham = new GroupLayout(pnlSanPham);
		gl_pnlSanPham.setHorizontalGroup(
			gl_pnlSanPham.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
				.addGroup(gl_pnlSanPham.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtTimKiemSP, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtMaSP, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(65))
		);
		gl_pnlSanPham.setVerticalGroup(
			gl_pnlSanPham.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSanPham.createSequentialGroup()
					.addGroup(gl_pnlSanPham.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlSanPham.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_pnlSanPham.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_3)
								.addComponent(txtTimKiemSP, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlSanPham.createSequentialGroup()
							.addGap(3)
							.addComponent(txtMaSP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlSanPham.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
		);
		panel.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(scrollPane_1, "name_25621687000400");
		
		tbllistSanPham = new JTable();
		tbllistSanPham.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"m\u00E3 s\u1EA3n ph\u1EA9m", "t\u00EAn s\u1EA3n ph\u1EA9m", "lo\u1EA1i", "gi\u00E1 g\u1ED1c", "l\u1EDDi %", "khuy\u1EBFn m\u00E3i", "gi\u00E1 b\u00E1n", "k\u00EDch th\u01B0\u1EDBc", "t\u1ED3n kho", "m\u00E0u s\u1EAFc", "ch\u1EA5t li\u1EC7u", "nh\u00E0 cung c\u1EA5p"
			}
		));
		scrollPane_1.setViewportView(tbllistSanPham);
		pnlSanPham.setLayout(gl_pnlSanPham);
		
		JPanel pnlBangXemTruoc = new JPanel();
		pnlBangXemTruoc.setBackground(new Color(255, 255, 255));
		pnlBangXemTruoc.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JLabel lblNewLabel_2 = new JLabel("Thay đổi số lượng : ");
		
		txtThayDoiSoLuong = new JTextField();
		txtThayDoiSoLuong.setBackground(new Color(255, 255, 255));
		txtThayDoiSoLuong.setEditable(false);
		txtThayDoiSoLuong.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtThayDoiSoLuong.setColumns(10);
		
		btnLuuTatCa = new JButton("Thêm Tất Cả Sản Phẩm");
		btnLuuTatCa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLoadingDialog(mainPanel);
				
                // Simulate adding a product (replace this with your actual logic)
                SwingUtilities.invokeLater(() -> {
                    try {
						DefaultTableModel model = (DefaultTableModel) tblXemTruoc.getModel();
						int rowCount = model.getRowCount();
						
						for (int i = 0; i < rowCount; i++) {
						    String kichThuoc = model.getValueAt(i, 2).toString(); // Lấy giá trị từ cột kích thước (index 2)
						    String soLuong = model.getValueAt(i, 4).toString(); // Lấy giá trị từ cột số lượng (index 4)
						    // Sử dụng kết quả lấy được ở đây (ví dụ: in ra hoặc xử lý tiếp)
						    SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
						    sanPhamDAO.addSanPham(addListOjbect(kichThuoc, Integer.parseInt(soLuong)));
						    
						    System.out.println("Kích thước: " + kichThuoc + ", Số lượng: " + soLuong);
						    tblDanhSachSanPham();
						}
						clearTableXemTruoc();
						closeLoadingDialog();
					} catch (NumberFormatException | RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                });
                btnThem.setEnabled(true);
    			btnSua.setEnabled(true);
    			btnLuu.setEnabled(false);
    			btnHuy.setEnabled(false);
                setEnibleChooses(false);
				
			}
		});
		btnLuuTatCa.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLuuTatCa.setBackground(new Color(65, 105, 225));
		btnLuuTatCa.setForeground(new Color(255, 255, 255));
		btnLuuTatCa.setIcon(new ImageIcon(QuanLySanPham.class.getResource("/icon/save.png")));
		btnLuuTatCa.setFont(new Font("Arial", Font.BOLD, 12));
		GroupLayout gl_pnlThongTinSanPham = new GroupLayout(pnlThongTinSanPham);
		gl_pnlThongTinSanPham.setHorizontalGroup(
			gl_pnlThongTinSanPham.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlThongTinSanPham.createSequentialGroup()
					.addContainerGap(141, Short.MAX_VALUE)
					.addComponent(btnLuuTatCa, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addGap(96))
				.addGroup(gl_pnlThongTinSanPham.createSequentialGroup()
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtThayDoiSoLuong, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(276, Short.MAX_VALUE))
				.addGroup(gl_pnlThongTinSanPham.createSequentialGroup()
					.addComponent(pnlBangXemTruoc, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlThongTinSanPham.setVerticalGroup(
			gl_pnlThongTinSanPham.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlThongTinSanPham.createSequentialGroup()
					.addComponent(pnlBangXemTruoc, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlThongTinSanPham.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtThayDoiSoLuong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLuuTatCa, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
		);
		pnlBangXemTruoc.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlBangXemTruoc.add(scrollPane, "name_17081090334300");
		
		tblXemTruoc = new JTable();
		tblXemTruoc.setBackground(new Color(255, 255, 255));
		tblXemTruoc.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 s\u1EA3n ph\u1EA9m", "T\u00EAn s\u1EA3n ph\u1EA9m", "K\u00EDch Th\u01B0\u1EDBc", "\u0110\u01A1n gi\u00E1", "S\u1ED1 l\u01B0\u1EE3ng"
			}
		));
		tblXemTruoc.getColumnModel().getColumn(1).setPreferredWidth(165);
		scrollPane.setViewportView(tblXemTruoc);
		pnlThongTinSanPham.setLayout(gl_pnlThongTinSanPham);
		
		JLabel lblNewLabel = new JLabel("Tên sản phẩm : ");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 11));
		
		txtTenSP = new JTextField();
		txtTenSP.setBackground(new Color(255, 255, 255));
		txtTenSP.setEditable(false);
		txtTenSP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTenSP.setColumns(10);
		
		JLabel lblHnhA = new JLabel("Hình ảnh:");
		
		pnlHinhAnh = new JPanel();
		pnlHinhAnh.setSize(new Dimension(159,108));
		pnlHinhAnh.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JLabel lblNewLabel_1 = new JLabel("Số Lượng : ");
		
		txtSoLuongSP = new JTextField();
		txtSoLuongSP.setEditable(false);
		txtSoLuongSP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtSoLuongSP.setBackground(new Color(255, 255, 255));
		txtSoLuongSP.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Màu sắc :");
		
		cboMauSac = new JComboBox();
		cboMauSac.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboMauSac.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Loại sản phẩm :");
		
		cboLoaiSanPham = new JComboBox();
		cboLoaiSanPham.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboLoaiSanPham.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Chất liệu : ");
		
		cboChatLieu = new JComboBox();
		cboChatLieu.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboChatLieu.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Kiểu dáng :");
		
		cboKieuDang = new JComboBox();
		cboKieuDang.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboKieuDang.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Xuất Xứ : ");
		
		cboxuatXu = new JComboBox();
		cboxuatXu.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboxuatXu.setBackground(new Color(255, 255, 255));
		
		btnHinhAnh = new JButton("Chọn");
		btnHinhAnh.setEnabled(false);
		btnHinhAnh.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnHinhAnh.setBackground(new Color(144, 238, 144));
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Nhà Cung Cấp :");
		
		cboNCC = new JComboBox();
		cboNCC.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboNCC.setBackground(new Color(255, 255, 255));
		
		JLabel lblGiNhp = new JLabel("Giá Nhập : ");
		
		txtGiaNhap = new JTextField();
		txtGiaNhap.setBackground(new Color(255, 255, 255));
		txtGiaNhap.setEditable(false);
		txtGiaNhap.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtGiaNhap.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_2_2 = new JLabel("Lời Theo :");
		
		cboGiaLoi = new JComboBox();
		cboGiaLoi.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboGiaLoi.setBackground(new Color(255, 255, 255));
		
		checkbox_xuatAllKichThuoc = new JCheckBox("Xuất tất cả kích thước");
		checkbox_xuatAllKichThuoc.setEnabled(false);
		checkbox_xuatAllKichThuoc.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1_1_1_2_3 = new JLabel("Kích thước :");
		
		cboKichThuocBatDau = new JComboBox();
		cboKichThuocBatDau.setBorder(new LineBorder(new Color(0, 0, 0)));
		cboKichThuocBatDau.setBackground(new Color(255, 255, 255));
		
		btnXemTruoc = new JButton("Xem Trước");
		btnXemTruoc.setEnabled(false);
		btnXemTruoc.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnXemTruoc.setForeground(new Color(0, 0, 0));
		btnXemTruoc.setBackground(new Color(192, 192, 192));
		btnXemTruoc.setFont(new Font("Arial", Font.BOLD, 13));
		GroupLayout gl_pnlXemTruoc = new GroupLayout(pnlXemTruoc);
		gl_pnlXemTruoc.setHorizontalGroup(
			gl_pnlXemTruoc.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlXemTruoc.createSequentialGroup()
					.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlXemTruoc.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_1_1_1_2_3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cboKichThuocBatDau, 0, 85, Short.MAX_VALUE)
							.addGap(295))
						.addGroup(gl_pnlXemTruoc.createSequentialGroup()
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlXemTruoc.createSequentialGroup()
											.addContainerGap()
											.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pnlXemTruoc.createSequentialGroup()
													.addComponent(lblNewLabel_1_1_1_2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_pnlXemTruoc.createSequentialGroup()
													.addComponent(cboKieuDang, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(cboxuatXu, 0, 111, Short.MAX_VALUE)))
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_pnlXemTruoc.createSequentialGroup()
											.addContainerGap()
											.addComponent(txtTenSP, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlXemTruoc.createSequentialGroup()
											.addGap(11)
											.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
											.addGap(59)
											.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlXemTruoc.createSequentialGroup()
											.addContainerGap()
											.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
												.addComponent(cboLoaiSanPham, 0, 120, Short.MAX_VALUE)
												.addComponent(lblNewLabel_1_1_1)
												.addComponent(txtSoLuongSP, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
												.addComponent(cboMauSac, 0, 114, Short.MAX_VALUE)
												.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
												.addComponent(cboChatLieu, 0, 114, Short.MAX_VALUE))))
									.addGap(18))
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblNewLabel))
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblNewLabel_1_1_1_2_1, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addContainerGap()
									.addComponent(cboNCC, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addContainerGap()
									.addComponent(checkbox_xuatAllKichThuoc)))
							.addGap(18)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addComponent(lblHnhA, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnHinhAnh, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblGiNhp, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1_2_2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtGiaNhap, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
								.addComponent(pnlHinhAnh, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
								.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnXemTruoc, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboGiaLoi, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
							.addGap(19)))
					.addGap(1))
		);
		gl_pnlXemTruoc.setVerticalGroup(
			gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlXemTruoc.createSequentialGroup()
					.addGap(9)
					.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlXemTruoc.createSequentialGroup()
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHnhA)
								.addComponent(btnHinhAnh))
							.addGap(3)
							.addComponent(pnlHinhAnh, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblGiNhp)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtGiaNhap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlXemTruoc.createSequentialGroup()
									.addGap(20)
									.addComponent(cboGiaLoi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel_1_1_1_2_2))
							.addGap(27)
							.addComponent(btnXemTruoc, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlXemTruoc.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(10)
							.addComponent(txtTenSP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_1_1))
							.addGap(3)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtSoLuongSP, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(cboMauSac, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_1_1)
								.addComponent(lblNewLabel_1_1_1_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(cboLoaiSanPham, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cboChatLieu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_1_1_2)
								.addComponent(lblNewLabel_1_1_1_1_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(cboKieuDang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cboxuatXu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1_1_2_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cboNCC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(checkbox_xuatAllKichThuoc)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlXemTruoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_1_1_2_3)
								.addComponent(cboKichThuocBatDau, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(24))
		);	pnlHinhAnh.setLayout(null);
		
		lblHinhAnh = new JLabel("");
		lblHinhAnh.setLocation(2, 2);
		lblHinhAnh.setSize(new Dimension(155, 104));
		pnlHinhAnh.add(lblHinhAnh);
		pnlXemTruoc.setLayout(gl_pnlXemTruoc);
		mainPanel.setLayout(gl_mainPanel);
		cboGiaLoi.addItem("10");
		cboGiaLoi.addItem("20");
		cboGiaLoi.addItem("30");
		cboGiaLoi.addItem("40");
		cboGiaLoi.addItem("50");
		cboGiaLoi.addItem("60");
		cboGiaLoi.addItem("70");
		cboGiaLoi.addItem("80");
		cboGiaLoi.addItem("90");
		cboGiaLoi.addItem("100");
		cboGiaLoi.addItem("110");
		cboGiaLoi.addItem("120");
		//them su kien cho nut 
		btnLuu.setEnabled(false);
		btnHuy.setEnabled(false);
		//su kien button
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnHuy.addActionListener(this);
		btnHinhAnh.addActionListener(this);
		checkbox_xuatAllKichThuoc.addActionListener(this);
		btnXemTruoc.addActionListener(this);
		
		//su kien click table
		tbllistSanPham.addMouseListener(this);
		tblXemTruoc.addMouseListener(new MouseListener() {
			


			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				dongcuabangxemtruoc = tblXemTruoc.getSelectedRow();
				String soluong = tblXemTruoc.getValueAt(dongcuabangxemtruoc, 4).toString();
				txtThayDoiSoLuong.setText(soluong);
				txtThayDoiSoLuong.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						updataSoLuongXemTruoc();
						
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						updataSoLuongXemTruoc();
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						updataSoLuongXemTruoc();
					}
				});
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
		
		tblXemTruoc.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
		txtThayDoiSoLuong.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
		btnLuuTatCa.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
		
		try {
			txtTimKiemSP.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					updateTableTimKiemSP();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					updateTableTimKiemSP();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					updateTableTimKiemSP();
				}
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "nhập đúng dữ liệu số");
			e1.printStackTrace();
		}
	}
	private void updataSoLuongXemTruoc() {
		if(txtThayDoiSoLuong.getText().equalsIgnoreCase("") || Integer.parseInt(txtThayDoiSoLuong.getText()) < 0 ) {
			tblXemTruoc.setValueAt("0", dongcuabangxemtruoc, 4);
			txtThayDoiSoLuong.addFocusListener(null);
		}else {
			String soluong = txtThayDoiSoLuong.getText();
			tblXemTruoc.setValueAt(soluong, dongcuabangxemtruoc, 4);
		}
	}
	private void updateTableTimKiemSP(){
		try {
			String masp = txtTimKiemSP.getText();
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			clearTable();
			DefaultTableModel dtm = (DefaultTableModel) tbllistSanPham.getModel();
			List<SanPham> listsp = sanPhamDAO.getDSSPTheoMaSP(masp);
			for(SanPham sp : listsp) {
				Object[] rowdata = {sp.getMaSP(), sp.getTenSP(),sp.getPhanLoai().getPhanLoai(),sp.getGiaNhap(),sp.getLoiTheoPhanTram(), (sp.getKhuyenMai() != null) ? sp.getKhuyenMai().getPhanTramKhuyenMai(): "",
						sp.getGiaBan(),sp.getKichThuoc().getKichThuoc(),sp.getSoLuong(), sp.getMauSac().getMauSac(),sp.getChatLieu().getChatLieu(),sp.getNhaCungCap().getTenNCC(),sp.getHinhAnh()};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public ImageIcon ResizeImage(String imgPath) {
        ImageIcon myImage = new ImageIcon(imgPath);
        Image img = myImage.getImage();
        Image newImg = img.getScaledInstance(pnlHinhAnh.getWidth(), pnlHinhAnh.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);

        return image;
    }
    class ImagePreviewAccessory extends JPanel {
        private JLabel imageLabel;

        public ImagePreviewAccessory(JFileChooser fileChooser) {
            setPreferredSize(new Dimension(200, 200));
            setBorder(BorderFactory.createEtchedBorder());
            setLayout(null);

            imageLabel = new JLabel();
            imageLabel.setBounds(0, 0, 200, 200);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            add(imageLabel);

            fileChooser.addPropertyChangeListener(evt -> {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    File selectedFile = (File) evt.getNewValue();
                    if (selectedFile != null && isImageFile(selectedFile)) {
                        ImageIcon icon = new ImageIcon(selectedFile.getPath());
                        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(image));
                    }
                }
            });
        }

        private boolean isImageFile(File file) {
            String name = file.getName().toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png");
        }
    }
//
//	
    public void chonHinhAnh() {
        JFileChooser fileChooser = new JFileChooser("data/hinhAnhSP");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "gif", "png");
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setMultiSelectionEnabled(false);

        ImagePreviewAccessory accessory = new ImagePreviewAccessory(fileChooser);
        fileChooser.setAccessory(accessory);

        int x = fileChooser.showDialog(this, "Chọn Ảnh");
        if (x == JFileChooser.APPROVE_OPTION) {
        	file = fileChooser.getSelectedFile();
            lblHinhAnh.setText("");
            lblHinhAnh.setIcon(ResizeImage(file.getAbsolutePath()));
        }
    }

	private SanPham addObject() {
		// TODO Auto-generated method stub
//		SanPham sp = new SanPham();
		try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			KieuDangDAO kieuDangDAO = (KieuDangDAO) registry.lookup("KieuDangDAO");
			ChatLieuDAO chatLieuDAO = (ChatLieuDAO) registry.lookup("ChatLieuDAO");
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			XuatXuDAO xuatXuDAO = (XuatXuDAO) registry.lookup("XuatXuDAO");
			NhaCungCapDAO nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			String tensp = txtTenSP.getText().toString();
			PhanLoai phanLoai = phanLoaiDAO.getPhanLoaiByName(cboLoaiSanPham.getSelectedItem().toString());
			double gianhap = Double.parseDouble(txtGiaNhap.getText());
			int loi = Integer.parseInt(cboGiaLoi.getSelectedItem().toString());
//		KhuyenMai khuyenMai = khuyenMaiDAO.getKhuyenMaiByPhanTram(0);
			double giaban =gianhap + gianhap * loi/100;
			KichThuoc kichThuoc = kichThuocDAO.getKichThuocByName(cboKichThuocBatDau.getSelectedItem().toString());
			int sl = Integer.parseInt(txtSoLuongSP.getText());
			
			System.out.println(cboMauSac.getSelectedItem().toString());
			MauSac mauSac = mauSacDAO.getMauSacByName(cboMauSac.getSelectedItem().toString());
			System.out.println(cboMauSac.getSelectedItem().toString());
			ChatLieu chatLieu = chatLieuDAO.getChatLieuByName(cboChatLieu.getSelectedItem().toString());
			NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCapByName(cboNCC.getSelectedItem().toString());
			KieuDang kieuDang = kieuDangDAO.getKieuDangByName(cboKieuDang.getSelectedItem().toString());
			XuatXu xuatXu = xuatXuDAO.getXuatXuByName(cboxuatXu.getSelectedItem().toString());
			System.out.println(cboxuatXu.getSelectedItem().toString());
			String hinhAnh = "";
			if (file != null) {
			    hinhAnh = file.getName();
			}
			System.out.println(lblHinhAnh.getText());
			int trangthai = 0; 
			if(sl > 0) {
				trangthai = 1;
			}
			BarcodeGenerator xuatQRcode = new BarcodeGenerator();
			String tenQrCode = getAutoID()+" - "+ tensp +" - "+cboKichThuocBatDau.getSelectedItem().toString();
			String file = "printer/sanpham/"+ getAutoID() +".png";
			try {
				xuatQRcode.generateBarcode(getAutoID(),tenQrCode , giaban+"", file );
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String masp = getAutoID();
//		SanPham sanPham = new SanPham(tensp, phanLoai, gianhap, loi, null, giaban, kichThuoc, sl, mauSac, chatLieu, nhaCungCap, kieuDang, xuatXu, hinhAnh, trangthai);
			SanPham sanPham = new SanPham(masp,tensp, phanLoai, gianhap, loi, null, giaban, kichThuoc, sl, mauSac, chatLieu, nhaCungCap, kieuDang, xuatXu, hinhAnh, trangthai);
			return sanPham;
		} catch (NumberFormatException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getAutoID() {
		try {
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			String idPrefix = "SP";
			int length = sanPhamDAO.getAllSanPham().size();
			String finalId = idPrefix + String.format("%02d", length + 1);
			return finalId;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private SanPham addListOjbect(String kichthuoc, int soluong) {
		try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			KieuDangDAO kieuDangDAO = (KieuDangDAO) registry.lookup("KieuDangDAO");
			ChatLieuDAO chatLieuDAO = (ChatLieuDAO) registry.lookup("ChatLieuDAO");
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			XuatXuDAO xuatXuDAO = (XuatXuDAO) registry.lookup("XuatXuDAO");
			NhaCungCapDAO nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
			
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			String tensp = txtTenSP.getText().toString();
			PhanLoai phanLoai = phanLoaiDAO.getPhanLoaiByName(cboLoaiSanPham.getSelectedItem().toString());
			double gianhap = Double.parseDouble(txtGiaNhap.getText());
			int loi = Integer.parseInt(cboGiaLoi.getSelectedItem().toString());
//		KhuyenMai khuyenMai = khuyenMaiDAO.getKhuyenMaiByPhanTram(0);
			double giaban = gianhap + gianhap * loi / 100;
			KichThuoc kichThuoc = kichThuocDAO.getKichThuocByName(kichthuoc);
			int sl = soluong;
			System.out.println(cboMauSac.getSelectedItem().toString());
			MauSac mauSac = mauSacDAO.getMauSacByName(cboMauSac.getSelectedItem().toString());
			ChatLieu chatLieu = chatLieuDAO.getChatLieuByName(cboChatLieu.getSelectedItem().toString());
			System.out.println(cboNCC.getSelectedItem().toString());
			NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCapByName(cboNCC.getSelectedItem().toString());
			System.out.println(nhaCungCap);
			KieuDang kieuDang = kieuDangDAO.getKieuDangByName(cboKieuDang.getSelectedItem().toString());
			XuatXu xuatXu = xuatXuDAO.getXuatXuByName(cboxuatXu.getSelectedItem().toString());
			System.out.println(cboxuatXu.getSelectedItem().toString());
			String hinhAnh = "";
			if (file != null) {
			    hinhAnh = file.getName();
			}
			System.out.println(lblHinhAnh.getText());
			int trangthai = 0; 
			if(sl > 0) {
				trangthai = 1;
			}
			BarcodeGenerator xuatQRcode = new BarcodeGenerator();
			String tenQrCode = getAutoID()+" - "+ tensp +" - "+cboKichThuocBatDau.getSelectedItem().toString();
			String file = "printer/sanpham/"+ getAutoID() +".png";
			try {
				xuatQRcode.generateBarcode(getAutoID(),tenQrCode , giaban+"", file );
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SanPham sanPham = new SanPham(getAutoID(),tensp, phanLoai, gianhap, loi, null, giaban, kichThuoc, sl, mauSac, chatLieu, nhaCungCap, kieuDang, xuatXu, hinhAnh, trangthai);
			return sanPham;
		} catch (NumberFormatException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private SanPham editObject() {
		try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			KieuDangDAO kieuDangDAO = (KieuDangDAO) registry.lookup("KieuDangDAO");
			ChatLieuDAO chatLieuDAO = (ChatLieuDAO) registry.lookup("ChatLieuDAO");
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			XuatXuDAO xuatXuDAO = (XuatXuDAO) registry.lookup("XuatXuDAO");
			NhaCungCapDAO nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			// TODO Auto-generated method stub
			String masp = txtMaSP.getText().toString();
			SanPham  sp = sanPhamDAO.findById(masp);
			String tensp = txtTenSP.getText().toString();
			PhanLoai phanLoai = phanLoaiDAO.getPhanLoaiByName(cboLoaiSanPham.getSelectedItem().toString());
			double gianhap = Double.parseDouble(txtGiaNhap.getText());
			System.out.println(gianhap);
			int loi = Integer.parseInt(cboGiaLoi.getSelectedItem().toString());
//		KhuyenMai khuyenMai = khuyenMaiDAO.getKhuyenMaiByPhanTram(0);
			double giaban = gianhap + gianhap * loi / 100;
			KichThuoc kichThuoc = kichThuocDAO.getKichThuocByName(cboKichThuocBatDau.getSelectedItem().toString());
			int sl = Integer.parseInt(txtSoLuongSP.getText());
			System.out.println(cboMauSac.getSelectedItem());
			MauSac mauSac = mauSacDAO.getMauSacByName(cboMauSac.getSelectedItem()+"");
			ChatLieu chatLieu = chatLieuDAO.getChatLieuByName(cboChatLieu.getSelectedItem().toString());
			String nhacungcapString = cboNCC.getSelectedItem().toString().trim();
			NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCapByName(nhacungcapString);
			System.out.println(nhaCungCap);
			KieuDang kieuDang = kieuDangDAO.getKieuDangByName(cboKieuDang.getSelectedItem().toString());
			XuatXu xuatXu = xuatXuDAO.getXuatXuByName(cboxuatXu.getSelectedItem().toString());
			System.out.println(cboxuatXu.getSelectedItem().toString());
			String hinhAnh = "";
			if (file != null) {
			    hinhAnh = file.getName();
			}
			System.out.println(hinhAnh);
			int trangthai = 0; 
			if(sl > 0) {
				trangthai = 1;
			}
			BarcodeGenerator xuatQRcode = new BarcodeGenerator();
			String tenQrCode = masp+" - "+ tensp +" - "+cboKichThuocBatDau.getSelectedItem().toString();
			String file = "printer/sanpham/"+ masp +".png";
			try {
				xuatQRcode.generateBarcode(masp,tenQrCode , giaban+"", file );
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SanPham sanPham = new SanPham(masp, tensp, phanLoai, gianhap, loi, null, giaban, kichThuoc, sl, mauSac, chatLieu, nhaCungCap, kieuDang, xuatXu, hinhAnh, trangthai);
			return sanPham;
		} catch (NumberFormatException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// su kien các nút
	private void xoaRongTextField() {
		// TODO Auto-generated method stub
		txtTenSP.setText("");
		txtSoLuongSP.setText("");
		txtGiaNhap.setText("");
	}
	private void setEnibleChooses(boolean check) {
		txtTenSP.setEditable(check);
		txtSoLuongSP.setEditable(check);
		btnHinhAnh.setEnabled(check);
		txtGiaNhap.setEditable(check);
		checkbox_xuatAllKichThuoc.setEnabled(check);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		
		if(o.equals(btnThem)) {
			btn = 1; //chuyen trang thai cua nut luu
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
			xoaRongTextField();

			txtMaSP.setText(getAutoID());
			setEnibleChooses(true);
			
		}
		if(o.equals(btnSua)) {
			if(!txtTenSP.getText().equals("")) {
				btn = 2; //chuyen trang thai cua nut luu
				btnThem.setEnabled(false);
				btnSua.setEnabled(false);
				btnLuu.setEnabled(true);
				btnHuy.setEnabled(true);
				setEnibleChooses(true);
				checkbox_xuatAllKichThuoc.setEnabled(false);
			}else {
				JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Sản Phẩm Trước Khi Sửa");
			}
			
		}
		if(o.equals(btnLuu)) {
			boolean check = true;
			String tenspString =  txtTenSP.getText();
			if(tenspString.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui lòng điền tên");
				check = false;
			}
			String soLuongSP = txtSoLuongSP.getText();
			if(soLuongSP.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui long điền số lượng");
				check = false;
			}
			String giaNhap = txtGiaNhap.getText();
			if(giaNhap.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui long điền giá nhập");
				check = false;
			}
//			(txtGiaNhap.getText());
			double gianhap =  Double.parseDouble((txtGiaNhap.getText()));
			if(gianhap < 0) {
				JOptionPane.showMessageDialog(null, "nhập giá > 0");
				btn = 3;
			}
			int sl = Integer.parseInt(txtSoLuongSP.getText());
			if(sl < 0) {
				JOptionPane.showMessageDialog(null, "nhâp số lượng > 0");
				btn = 3;
			}
			if (btn == 1) {
			    if (check) {
			    	try {
						SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
						showLoadingDialog(mainPanel);
						sanPhamDAO.addSanPham(addObject());
						tblDanhSachSanPham();
						btnThem.setEnabled(true);
						btnSua.setEnabled(true);
						btnLuu.setEnabled(false);
						btnHuy.setEnabled(false);
						setEnibleChooses(false);
						closeLoadingDialog();
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			} else if (btn == 2) { // Sửa ở đây
			    if (check) {
			    	try {
						SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
						showLoadingDialog(mainPanel);
						sanPhamDAO.updateSanPham(editObject());
						tblDanhSachSanPham();
						btnThem.setEnabled(true);
						btnSua.setEnabled(true);
						btnLuu.setEnabled(false);
						btnHuy.setEnabled(false);
						setEnibleChooses(false);
						closeLoadingDialog();
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			}
		}
		if(o.equals(btnLamMoi)) {
			System.out.println("đã làm mới");
			resetGui();
			uiSanPhan();
			tblDanhSachSanPham();
			loadComboBoxThuocTinh();
			setEnibleChooses(false);
			txtTenSP.setText("");
			txtSoLuongSP.setText("");
			txtGiaNhap.setText("");
			
		}else if(o.equals(btnHuy)) {
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
			setEnibleChooses(false);
			
		}else if(o.equals(checkbox_xuatAllKichThuoc)) {
			btnXemTruoc.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
			cboKichThuocBatDau.setEnabled(!checkbox_xuatAllKichThuoc.isSelected());
			tblXemTruoc.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
			txtThayDoiSoLuong.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
			txtThayDoiSoLuong.setEditable(checkbox_xuatAllKichThuoc.isSelected());
			btnLuuTatCa.setEnabled(checkbox_xuatAllKichThuoc.isSelected());
			
		}
		if(o.equals(btnHinhAnh)) {
			chonHinhAnh();
		}
		if(o.equals(btnXemTruoc)) {
			btnXemTruoc.setEnabled(false);
			boolean check = true;
			String tenspString =  txtTenSP.getText();
			if(tenspString.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui lòng điền tên");
				check = false;
			}
			String soLuongSP = txtSoLuongSP.getText();
			if(soLuongSP.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui long điền số lượng");
				check = false;
			}
			String giaNhap = txtGiaNhap.getText();
			if(giaNhap.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "vui long điền giá nhập");
				check = false;
			}
			if(check)
			tblXemTruocSanPham();
		}
	}
	private void resetGui() {
		txtMaSP.setText("");
		txtTenSP.setText("");
		txtGiaNhap.setText("");
		txtSoLuongSP.setText("");
		cboxuatXu.setSelectedIndex(0);
		cboLoaiSanPham.setSelectedItem(0);
		cboGiaLoi.setSelectedItem(0);
		cboKichThuocBatDau.setSelectedItem(0);
		cboMauSac.setSelectedIndex(0);
		cboChatLieu.setSelectedIndex(0);
		cboNCC.setSelectedIndex(0);
		cboKieuDang.setSelectedIndex(0);
		cboxuatXu.setSelectedIndex(0);
		lblHinhAnh.setIcon(new ImageIcon());
		
		//set lại nút button
		btnThem.setEnabled(true);
		btnSua.setEnabled(true);
		btnLuu.setEnabled(false);
		btnHuy.setEnabled(false);
	}
	//loading
	private static JDialog loadingDialog;

	private static void showLoadingDialog(JPanel owner) {
	    // Create a new JDialog with the specified JPanel as the owner
	    loadingDialog = new JDialog(SwingUtilities.windowForComponent(owner), "Loading...");
	    
	    // Create a new JPanel for the loading content
	    JPanel loadingPanel = new JPanel(new BorderLayout());
	    JLabel titleLabel = new JLabel("Đang thêm sản phẩm và tạo QR", SwingConstants.CENTER);
        loadingPanel.add(BorderLayout.NORTH, titleLabel);
	    // Create a JProgressBar and add it to the loadingPanel
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    loadingPanel.add(BorderLayout.CENTER, progressBar);

	    // Add the loadingPanel to the JDialog
	    loadingDialog.getContentPane().add(loadingPanel);

	    loadingDialog.setSize(200, 75);
	    loadingDialog.setLocationRelativeTo(owner);
	    loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    
	    // Make the JDialog visible
	    loadingDialog.setVisible(true);
	}

    private static void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }
	// sư kiện click vào bảng
	@Override
	public void mouseClicked(MouseEvent e) {
        File file = new File("");
        String path= file.getAbsolutePath();
		// TODO Auto-generated method stub
		int row = tbllistSanPham.getSelectedRow();
		String masp = tbllistSanPham.getValueAt(row, 0).toString();
		txtMaSP.setText(masp);
		txtTenSP.setText(tbllistSanPham.getValueAt(row, 1).toString());
		cboLoaiSanPham.setSelectedItem(tbllistSanPham.getValueAt(row, 2));
		txtGiaNhap.setText(tbllistSanPham.getValueAt(row, 3).toString());
		cboGiaLoi.setSelectedItem(tbllistSanPham.getValueAt(row, 4));
		//khuyen mai 5
		//gia ban 6
		cboKichThuocBatDau.setSelectedItem(tbllistSanPham.getValueAt(row, 7));
		txtSoLuongSP.setText(tbllistSanPham.getValueAt(row, 8).toString());
		cboMauSac.setSelectedItem(tbllistSanPham.getValueAt(row, 9));
		cboChatLieu.setSelectedItem(tbllistSanPham.getValueAt(row, 10));
		cboNCC.setSelectedItem(tbllistSanPham.getValueAt(row, 11));

		//tbl chua co kieu dang de lay
		try {
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			SanPham sp = sanPhamDAO.findById(masp);
			cboKieuDang.setSelectedItem(sp.getKieuDang().getKieuDang());
			cboxuatXu.setSelectedItem(sp.getXuatXu().getXuatXu());
			lblHinhAnh.setIcon(new ImageIcon());
			lblHinhAnh.setSize(new Dimension(159,108));
			lblHinhAnh.setIcon(ResizeImage(path + "/data/hinhAnhSP/"+sp.getHinhAnh()));
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
