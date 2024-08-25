package gui;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.imageio.ImageIO;
import javax.management.StringValueExp;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.ScrollPane;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import component.PrinterBill;
import component.hoaDonPrinter;
import component.sanPhamPrinter;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import entities.ChatLieu;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;
import entities.SanPham;
import gui.CameraPanel.QRCodeListener;
import jakarta.persistence.EntityManager;
import main.local_host;
import services.EntityManagerFactoryUtil;
import services.HoaDonService;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuanLyBanHang extends JPanel implements ActionListener, MouseListener, QRCodeListener{
	private JTextField txtTienKhachDua;
	private JTable tblHoaDonCho;
	private JTable tblGioHang;
	private JTextField txtSoLuong;
	private JTable tblDSSanPham;
	private JTextField txtTimKiemSP;
//	private SanPhamDAO sanPhamDAO;
	private JButton btnThemVaoGio;
	private String masp;
	private int soluong;
	private JButton btnTaoHD;
	private JButton btnTim;
	private JPanel contentPane;
//	private HoaDonDAO HoaDonDAO;
//	private HoaDonDAO HoaDonDAO1;
//	KhachHangDAO khachHangDAO = new KhachHangDAO();
//	NhanVienDAO nhanVienDAO = new NhanVienDAO();
	private JLabel lblmakh;
	private JLabel lbltenkh;
	private String mahd;
	private String maspGioHang, tenNV;
	private double phantramKM;
//	private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
	private double dongia, giaSauKhuyenMai;
	private JButton btnXoaSP;
	private JLabel lblMaHDpush;
	private JLabel lblTongTienpush;
	private JLabel lblGiamGiapush;
	private JLabel lblThuepush;
	private JLabel lblThanhToanpush;
	private JButton btnHuyHoaDon;
	private JLabel lbltienthua;
	private VideoCapture capture;
	private JPanel cam;
	private JPanel pnlCamera;
	private JLabel cameraViewLabel;
	private String qrCodeValue;
	private JLabel lblGimGi;
	private int giamTheoPhanTramChoKhachHang;
	private double tienThanhToan;
	private String tenString;
	public JLabel lbltennv;
	private JPanel pnlHoaDon;
	private Registry registry;
//	private SanPhamDAO sanPhamDAO;
//	private HoaDonDAO hoaDonDAO;
//	private ChiTietHoaDonDAO chiTietHoaDonDAO;
	
	/**
	 * Create the panel.
	 */
	public QuanLyBanHang() {
		
		try {
			local_host local = new local_host();
			registry = LocateRegistry.getRegistry(local.host(), 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		layTenChucVu(lbltennv);
//		System.out.println(lbltennv.getText());
		UiBanHang();
		tblDanhSachSanPham();
		updateTableHoaDonCho();
		
//		thread.start();
		
		
		//xử lý
		if(tblHoaDonCho.getRowCount()>0) {
			tblHoaDonCho.setRowSelectionInterval(0, 0);
			int row=tblHoaDonCho.getSelectedRow();
			mahd = tblHoaDonCho.getValueAt(row, 0).toString();
			 updateTableGioHang(mahd);
			 try {
				 ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
				 double tongtien = 0.0;
					for(int t = 0; t < tblGioHang.getRowCount(); t++) {
						tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
					}
					lblTongTienpush.setText(tongtien+"");
				setLblGiamGia();
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
		}

//		cameraViewLabel.setPreferredSize(new Dimension(170, 110));
		cam.removeAll();
		cam.setLayout(new BorderLayout());
		CameraPanel cameraPanel = new CameraPanel();
		cam.add(cameraPanel);
		cameraPanel.addQRCodeListener(this);
		cameraPanel.startCamera();
	}
    private void clearTableDSSP() {
        DefaultTableModel dtm = (DefaultTableModel) tblDSSanPham.getModel();
        dtm.setRowCount(0);
    }
    private void clearTableGioHang() {
        DefaultTableModel dtm = (DefaultTableModel) tblGioHang.getModel();
        dtm.setRowCount(0);
    }
    private void clearTableDSHDC() {
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDonCho.getModel();
        dtm.setRowCount(0);
    }
	private void tblDanhSachSanPham() {
		try {
			clearTableDSSP();
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			List<SanPham> sanPhams = sanPhamDAO.getAllSanPham();
			DefaultTableModel dtm = (DefaultTableModel) tblDSSanPham.getModel();
			for(SanPham sp : sanPhams) {
				Object[] rowdata = {sp.getMaSP(),sp.getTenSP(),sp.getPhanLoai().getPhanLoai(),sp.getKichThuoc(),sp.getGiaBan(),sp.getSoLuong(),sp.getMauSac().getMauSac(),sp.getKichThuoc().getKichThuoc()};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void layTenChucVu(String ten) {
//		tenNV = ten;
		lbltennv.setText(ten);
	}
	
	private void UiBanHang() {
		setPreferredSize(new Dimension(934, 701));
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBackground(new Color(255, 255, 255));
		
		pnlHoaDon = new JPanel();
		pnlHoaDon.setBackground(new Color(255, 255, 255));
		pnlHoaDon.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "\u0110\u01A1n H\u00E0ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		
		JPanel pnlHoaDonCho = new JPanel();
		pnlHoaDonCho.setBackground(new Color(255, 255, 255));
		pnlHoaDonCho.setBorder(new CompoundBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "H\u00F3a \u0110\u01A1n Ch\u1EDD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null), null));
		
		pnlCamera = new JPanel();
		pnlCamera.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JPanel pnlGioHang = new JPanel();
		pnlGioHang.setBackground(new Color(255, 255, 255));
		pnlGioHang.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Gi\u1ECF H\u00E0ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		
		JPanel pnlDanhSachSanPham = new JPanel();
		pnlDanhSachSanPham.setBackground(new Color(255, 255, 255));
		pnlDanhSachSanPham.setBorder(new CompoundBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Danh S\u00E1ch S\u1EA3n Ph\u1EA9m", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null), null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlGioHang, 0, 0, Short.MAX_VALUE)
						.addComponent(pnlDanhSachSanPham, 0, 0, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlHoaDonCho, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlCamera, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlHoaDon, GroupLayout.PREFERRED_SIZE, 330, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(8)
									.addComponent(pnlCamera, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
								.addComponent(pnlHoaDonCho, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlGioHang, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlDanhSachSanPham, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addComponent(pnlHoaDon, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)))
					.addContainerGap())
		);
		pnlCamera.setLayout(new CardLayout(0, 0));
		
		cam = new JPanel();
		pnlCamera.add(cam, "name_58835019045600");
		cam.setLayout(new CardLayout(0, 0));
//		cameraViewLabel = new JLabel();
//		cam.add(cameraViewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_1 = new JLabel("Tìm kiêm sản phẩm : ");
		
		txtTimKiemSP = new JTextField();
		txtTimKiemSP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTimKiemSP.setColumns(10);
		
		btnThemVaoGio = new JButton("Thêm Sản Phẩm");
		btnThemVaoGio.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThemVaoGio.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/add.png")));
		btnThemVaoGio.setForeground(new Color(255, 255, 255));
		btnThemVaoGio.setFont(new Font("Arial", Font.BOLD, 11));
		btnThemVaoGio.setBackground(new Color(65, 105, 225));
		btnThemVaoGio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout gl_pnlDanhSachSanPham = new GroupLayout(pnlDanhSachSanPham);
		gl_pnlDanhSachSanPham.setHorizontalGroup(
			gl_pnlDanhSachSanPham.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDanhSachSanPham.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtTimKiemSP, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(btnThemVaoGio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(88))
				.addGroup(gl_pnlDanhSachSanPham.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 572, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_pnlDanhSachSanPham.setVerticalGroup(
			gl_pnlDanhSachSanPham.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDanhSachSanPham.createSequentialGroup()
					.addGroup(gl_pnlDanhSachSanPham.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtTimKiemSP, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnThemVaoGio))
					.addGap(11)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
		);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "name_87070151630200");
		
		tblDSSanPham = new JTable();
		tblDSSanPham.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"M\u00E3 s\u1EA3n ph\u1EA9m", "T\u00EAn s\u1EA3n ph\u1EA9m", "Ph\u00E2n lo\u1EA1i", "K\u00EDch th\u01B0\u1EDBc", "\u0110\u01A1n gi\u00E1", "S\u1ED1 l\u01B0\u1EE3ng", "M\u00E0u s\u1EAFc", "k\u00EDch th\u01B0\u1EDBc"
			}
		));
		tblDSSanPham.getColumnModel().getColumn(0).setPreferredWidth(87);
		tblDSSanPham.getColumnModel().getColumn(1).setPreferredWidth(116);
		tblDSSanPham.getColumnModel().getColumn(2).setPreferredWidth(89);
		scrollPane_1.setViewportView(tblDSSanPham);
		pnlDanhSachSanPham.setLayout(gl_pnlDanhSachSanPham);
		
		JPanel panel = new JPanel();
		
		txtSoLuong = new JTextField();
		txtSoLuong.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtSoLuong.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Số Lượng      ");
		
		JButton btnOk = new JButton("OK");
		btnOk.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
					HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
					ChiTietHoaDonDAO chiTietHoaDonDAO =  (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
					String msp = maspGioHang;
					SanPham sp = sanPhamDAO.findById(msp);
					HoaDon hd = hoaDonDAO.findById(mahd);
					double phantram = phantramKM;
					int sl = Integer.parseInt(txtSoLuong.getText());
					ChiTietHoaDon cthd = new ChiTietHoaDon();
					giaSauKhuyenMai = sp.tinhGiaSauKhuyenMai(dongia, phantram)*sl;
//					cthd = new ChiTietHoaDon(sp, hd, phantram, sl, giaSauKhuyenMai);
					
					cthd = new ChiTietHoaDon(hd, sp, sl, phantram, giaSauKhuyenMai);
					
					if(chiTietHoaDonDAO.updateSoLuongSPTrongGio(cthd)) {
						System.out.println("da sua");
					}else {
						System.out.println("phat code sai");
					}
					updateTableGioHang(mahd);
//					System.out.println(cthd.toStringChiTietHoaDon());
					System.out.println(mahd);
					double tongtien = 0.0;
					for(int t = 0; t < tblGioHang.getRowCount(); t++) {
						tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
					}
					lblTongTienpush.setText(tongtien+"");
					setLblGiamGia();
					
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnOk.setForeground(new Color(255, 255, 255));
		btnOk.setBackground(new Color(144, 238, 144));
		btnOk.setFont(new Font("Arial", Font.BOLD, 9));
		
		btnXoaSP = new JButton("Xóa Sản Phẩm");

		btnXoaSP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SanPhamDAO sanPhamDAO;
				try {
					
					sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
					HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
					ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
					String msp = maspGioHang;
					SanPham sp = sanPhamDAO.findById(msp);
					HoaDon hd = hoaDonDAO.findById(mahd);
					double phantram = phantramKM;
					int sl = Integer.parseInt(txtSoLuong.getText());
					ChiTietHoaDon cthd = new ChiTietHoaDon();
					giaSauKhuyenMai = sp.tinhGiaSauKhuyenMai(dongia, phantram)*sl;
					cthd = new ChiTietHoaDon(hd, sp, sl, phantram, giaSauKhuyenMai);
					chiTietHoaDonDAO.deleteMotSP(cthd);
					updateTableGioHang(mahd);
					double tongtien = 0.0;
					for(int t = 0; t < tblGioHang.getRowCount(); t++) {
						tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
					}
					lblTongTienpush.setText(tongtien+"");
					setLblGiamGia();
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnXoaSP.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnXoaSP.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/xoaall.png")));
		btnXoaSP.setForeground(new Color(255, 255, 255));
		btnXoaSP.setBackground(new Color(255, 0, 0));
		btnXoaSP.setFont(new Font("Arial", Font.BOLD, 10));
		GroupLayout gl_pnlGioHang = new GroupLayout(pnlGioHang);
		gl_pnlGioHang.setHorizontalGroup(
			gl_pnlGioHang.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGioHang.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
					.addComponent(btnXoaSP, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addGap(55))
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
		);
		gl_pnlGioHang.setVerticalGroup(
			gl_pnlGioHang.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlGioHang.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlGioHang.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlGioHang.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnXoaSP, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane_GioHang = new JScrollPane();
		panel.add(scrollPane_GioHang, "name_86272947197200");
		
		tblGioHang = new JTable();
		tblGioHang.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 S\u1EA3n Ph\u1EA9m", "T\u00EAn S\u1EA3n Ph\u1EA9m", "K\u00EDch Th\u01B0\u1EDBc", "Khuy\u1EBFn m\u00E3i", "\u0110\u01A1n Gi\u00E1", "s\u1ED1 l\u01B0\u1EE3ng", "Th\u00E0nh ti\u1EC1n"
			}
		));
		tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(95);
		tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(63);
		tblGioHang.getColumnModel().getColumn(3).setPreferredWidth(77);
		tblGioHang.getColumnModel().getColumn(6).setPreferredWidth(61);
		scrollPane_GioHang.setViewportView(tblGioHang);
		pnlGioHang.setLayout(gl_pnlGioHang);
		pnlHoaDonCho.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlHoaDonCho.add(scrollPane, "name_85655507217700");
		
		tblHoaDonCho = new JTable();
		tblHoaDonCho.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 H\u00F3a \u0110\u01A1n", "Ng\u00E0y T\u1EA1o", "T\u00EAn Nh\u00E2n Vi\u00EAn", "Kh\u00E1ch H\u00E0ng"
			}
		));
		tblHoaDonCho.getColumnModel().getColumn(2).setPreferredWidth(99);
		tblHoaDonCho.getColumnModel().getColumn(3).setPreferredWidth(119);
		scrollPane.setViewportView(tblHoaDonCho);
		
		JPanel pnlKhachHang = new JPanel();
		pnlKhachHang.setBackground(new Color(255, 255, 255));
		pnlKhachHang.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		btnTaoHD = new JButton("Tạo HĐ");
		btnTaoHD.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnTaoHD.setFont(new Font("Arial", Font.BOLD, 11));
		btnTaoHD.setForeground(new Color(255, 255, 255));
		btnTaoHD.setBackground(new Color(65, 105, 225));
		btnTaoHD.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/save.png")));
		btnTaoHD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblMaHD = new JLabel("Mã hóa đơn :");
		lblMaHD.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblTngTin = new JLabel("Tổng tiền : ");
		lblTngTin.setFont(new Font("Arial", Font.BOLD, 12));
		
		lblGimGi = new JLabel("Giảm giá : ");
		lblGimGi.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblThuVat = new JLabel("Thuế VAT (10%): ");
		lblThuVat.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblT = new JLabel("Thanh Toán : ");
		lblT.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblTinKhcha = new JLabel("Tiền khách đưa : ");
		lblTinKhcha.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblTinKhcha_1 = new JLabel("Tiền thừa : ");
		lblTinKhcha_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTinKhcha_1.setFont(new Font("Arial", Font.BOLD, 12));
		
		txtTienKhachDua = new JTextField();
		txtTienKhachDua.setColumns(10);
		
		lblMaHDpush = new JLabel("0");
		
		lblTongTienpush = new JLabel("0");
		
		lblGiamGiapush = new JLabel("0");
		
		lblThuepush = new JLabel("0");
		
		lblThanhToanpush = new JLabel("0");
		
		lbltienthua = new JLabel("0");
		
		JLabel lblTien = new JLabel("VNĐ");
		lblTien.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTien_1 = new JLabel("VNĐ");
		lblTien_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTien_2 = new JLabel("VNĐ");
		lblTien_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTien_3 = new JLabel("VNĐ");
		lblTien_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTien_4 = new JLabel("VNĐ");
		lblTien_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTien_5 = new JLabel("VNĐ");
		lblTien_5.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTinKhcha_1_1 = new JLabel("Ghi Chú:");
		lblTinKhcha_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTinKhcha_1_1.setFont(new Font("Arial", Font.BOLD, 12));
		
		JPanel pnlGhiChu = new JPanel();
		pnlGhiChu.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnHuyHoaDon = new JButton("Hủy Hóa Đơn");
		btnHuyHoaDon.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnHuyHoaDon.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/stop2.png")));
		btnHuyHoaDon.setForeground(new Color(255, 255, 255));
		btnHuyHoaDon.setBackground(new Color(255, 0, 0));
		btnHuyHoaDon.setFont(new Font("Arial", Font.BOLD, 12));
		
		JButton btnThanhToan = new JButton("Thanh Toán");
		btnThanhToan.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThanhToan.setForeground(new Color(255, 255, 255));
		btnThanhToan.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/thanhtoan.png")));
		btnThanhToan.setBackground(new Color(50, 205, 50));
		btnThanhToan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(lbltienthua.getText().startsWith("-"));
				if(lbltienthua.getText().startsWith("-")) {
					JOptionPane.showMessageDialog(null, "vui lòng nhập số tiền khách hàng lớn hơn số tiền thanh toán");
				}else {
					showLoadingDialog(pnlHoaDon);
//					HoaDonDAO hoaDonDAO = new HoaDonDAO();
					ChiTietHoaDonDAO chiTietHoaDonDAO;
					try {
						SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
						HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
						chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
						NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
						double tongtien = 0.0;
						for(int t = 0; t < tblGioHang.getRowCount(); t++) {
							tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
						}
						lblTongTienpush.setText(tongtien+"");
						hoaDonDAO.updateHoaDon(mahd,1, tongtien);
						int cnt = tblGioHang.getRowCount();
						List<sanPhamPrinter> sanPhamList = new ArrayList<>();
						for(int i  = 0; i < cnt; i++) {
							String tensp =  tblGioHang.getValueAt(i, 1).toString()+"-"+tblGioHang.getValueAt(i, 2).toString();
							double giaban = Double.parseDouble(tblGioHang.getValueAt(i, 4).toString());
							int slg = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString());
							double thanhtien = Double.parseDouble(tblGioHang.getValueAt(i, 6).toString());
							sanPhamList.add(new sanPhamPrinter(tensp, giaban, slg, thanhtien));
							System.out.println(tblGioHang.getValueAt(i, 0));
							System.out.println(tblGioHang.getValueAt(i, 5));
							String ma = tblGioHang.getValueAt(i, 0).toString();
							int sl = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString());
							sanPhamDAO.SuaSlSP(sl, ma);
						}
						double tongTien =  Double.parseDouble(lblTongTienpush.getText().replace(".", ""));
						double giamGia = Double.parseDouble(lblGiamGiapush.getText().replace(".", ""));
						double thue = Double.parseDouble(lblThuepush.getText().replace(".", ""));
						double thanhToan = Double.parseDouble(lblThanhToanpush.getText().replace(".", ""));
						double tienKhachDua = 0;
						if(txtTienKhachDua.getText().equals("")) {
							tienKhachDua = 0.0;
						}else {
							tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
						}
						double tienThua = 0;
						if(txtTienKhachDua.getText().equals("")) {
							tienThua = 0.0;
						}else {
							tienThua = Double.parseDouble(txtTienKhachDua.getText());
						}
						NhanVien nv = nhanVienDAO.getNhanVienByName(lbltennv.getText());
						hoaDonPrinter hdprt = new hoaDonPrinter(nv.getTenNV(), lbltenkh.getText(), tongTien, giamGia, thue, thanhToan, tienKhachDua, tienThua, sanPhamList);
						System.out.println(hdprt.toString());
						String path = "printer/hoadon/"+mahd+".pdf";
						PrinterBill.generatePDF(mahd, hdprt, path);
						// Đường dẫn đến file PDF cần in
						String pdfFilePath = path;

						// Load document
						PDDocument document = PDDocument.load(new File(pdfFilePath));

						// Tạo một PDFPrintable từ tài liệu
						PDFPrintable pdfPrintable = new PDFPrintable(document);

						// Tạo một PrinterJob
						PrinterJob job = PrinterJob.getPrinterJob();

						// Đặt PageFormat
						PageFormat pageFormat = job.defaultPage();
						pageFormat.setOrientation(PageFormat.PORTRAIT);

						// Đặt PDFPrintable cho công việc in
						job.setPrintable(pdfPrintable, pageFormat);

						// Hiển thị hộp thoại in để người dùng có thể chọn máy in và cấu hình in
						if (job.printDialog()) {
						    // Bắt đầu công việc in
						    job.print();
						}

						// Đóng tài liệu
						document.close();
					} catch (HeadlessException | IllegalArgumentException | NotBoundException | IOException
							| PrinterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					mahd = "";
					// In thông tin hóa đơn
			        System.out.println("Hóa đơn đã được tạo thành công.");
					updateTableHoaDonCho();
					updateTableGioHang("");
					tblDanhSachSanPham();
					setLblGiamGia();
					
					lblTongTienpush.setText("0");
					lblGimGi.setText("Giảm giá:");
					lblThuepush.setText("0");
					lblGiamGiapush.setText("0");
					lblThanhToanpush.setText("0");
					txtTienKhachDua.setText("");
					
					if(tblHoaDonCho.getRowCount()>0) {
						
						 try {
							 	chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
								tblHoaDonCho.setRowSelectionInterval(0, 0);
								int row=tblHoaDonCho.getSelectedRow();
								mahd = tblHoaDonCho.getValueAt(row, 0).toString();
								updateTableGioHang(mahd);
								double tongtien = 0.0;
								for(int t = 0; t < tblGioHang.getRowCount(); t++) {
									tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
								}
								lblTongTienpush.setText(tongtien+"");
								 setLblGiamGia();
						} catch (RemoteException | NotBoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					closeLoadingDialog();
					JOptionPane.showMessageDialog(null, "Đã thanh toán thành công");
				}
				
			}
		});
		btnThanhToan.setFont(new Font("Arial", Font.BOLD, 22));
		
		JButton btnLamMoi = new JButton("Làm mới ");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLblGiamGia();
			}
		});
		btnLamMoi.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLamMoi.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/refesh.png")));
		btnLamMoi.setBackground(new Color(152, 251, 152));
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
		
		lbltennv = new JLabel("1111111111");
		lbltennv.setBackground(Color.WHITE);
		lbltennv.setForeground(Color.RED);
		GroupLayout gl_pnlHoaDon = new GroupLayout(pnlHoaDon);
		gl_pnlHoaDon.setHorizontalGroup(
			gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlHoaDon.createSequentialGroup()
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlHoaDon.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
								.addComponent(pnlKhachHang, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlHoaDon.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_pnlHoaDon.createSequentialGroup()
											.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
														.addComponent(lblTinKhcha)
														.addComponent(lblTinKhcha_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
													.addGap(18)
													.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
														.addComponent(txtTienKhachDua, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_pnlHoaDon.createSequentialGroup()
															.addComponent(lbltienthua, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
															.addGap(41)
															.addComponent(lblTien_5))))
												.addComponent(lblTinKhcha_1_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
											.addGap(18))
										.addGroup(gl_pnlHoaDon.createSequentialGroup()
											.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
												.addComponent(lblMaHD, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblTngTin, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblThuVat, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblT, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblGimGi, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addComponent(lblTongTienpush, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
													.addComponent(lblTien, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addComponent(lblThuepush, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(lblTien_2, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addComponent(lblGiamGiapush, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(lblTien_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addComponent(lblMaHDpush, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
													.addGap(18)
													.addComponent(btnTaoHD, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
													.addGap(18))
												.addGroup(gl_pnlHoaDon.createSequentialGroup()
													.addComponent(lblThanhToanpush, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
														.addComponent(lblTien_4, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
														.addComponent(lblTien_3, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))))
									.addGap(19))))
						.addGroup(gl_pnlHoaDon.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnThanhToan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pnlGhiChu, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlHoaDon.createSequentialGroup()
									.addComponent(btnHuyHoaDon, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlHoaDon.createSequentialGroup()
							.addContainerGap()
							.addComponent(lbltennv)))
					.addContainerGap())
		);
		gl_pnlHoaDon.setVerticalGroup(
			gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlHoaDon.createSequentialGroup()
					.addComponent(pnlKhachHang, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblMaHD)
							.addComponent(lblMaHDpush))
						.addComponent(btnTaoHD, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTngTin)
						.addComponent(lblTongTienpush)
						.addComponent(lblTien))
					.addGap(18)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGimGi)
						.addComponent(lblGiamGiapush)
						.addComponent(lblTien_1))
					.addGap(18)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThuVat)
						.addComponent(lblThuepush)
						.addComponent(lblTien_2))
					.addGap(18)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblT)
						.addComponent(lblThanhToanpush)
						.addComponent(lblTien_3))
					.addGap(18)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTinKhcha)
						.addComponent(txtTienKhachDua, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTien_4))
					.addGap(28)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTinKhcha_1)
						.addComponent(lbltienthua)
						.addComponent(lblTien_5))
					.addGap(31)
					.addComponent(lblTinKhcha_1_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlGhiChu, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_pnlHoaDon.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHuyHoaDon, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLamMoi, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnThanhToan, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(lbltennv)
					.addContainerGap())
		);
		GroupLayout gl_pnlGhiChu = new GroupLayout(pnlGhiChu);
		gl_pnlGhiChu.setHorizontalGroup(
			gl_pnlGhiChu.createParallelGroup(Alignment.LEADING)
				.addGap(0, 279, Short.MAX_VALUE)
		);
		gl_pnlGhiChu.setVerticalGroup(
			gl_pnlGhiChu.createParallelGroup(Alignment.LEADING)
				.addGap(0, 91, Short.MAX_VALUE)
		);
		pnlGhiChu.setLayout(gl_pnlGhiChu);
		
		JLabel lblMaKH = new JLabel("Mã khách hàng : ");
		lblMaKH.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblTenKH = new JLabel("Tên khách hàng : ");
		lblTenKH.setFont(new Font("Arial", Font.BOLD, 12));
		
		lblmakh = new JLabel("KH01");
		lblmakh.setForeground(new Color(255, 0, 0));
		
		lbltenkh = new JLabel("ẨN DANH");
		lbltenkh.setForeground(Color.RED);
		
		btnTim = new JButton("Tìm");
		
		//===================================
		///them jframe để tìm khách hàng
		btnTim.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UiTimKhachHang timKhachHangUI = new UiTimKhachHang();
		        timKhachHangUI.setKhachHangSelectedListener(new UiTimKhachHang.KhachHangSelectedListener() {
		            private int giamTheoPhanTramChoKhachHang;

					@Override
		            public void onKhachHangSelected(String makh, String tenkh) {
						
		                try {
		                	KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
		                	NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
		                	HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
		                	hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
							lblmakh.setText(makh);
							if(!lblmakh.getText().trim().equals("")) {
								lbltenkh.setText(tenkh);
								KhachHang kh = khachHangDAO.getKhachHangById(makh);
								NhanVien nv = nhanVienDAO.getNhanVienByName(lbltennv.getText());
//								HoaDon hd = new HoaDon(mahd, kh, nv);
								HoaDon hd = new HoaDon(tenkh, nv, kh);
								hoaDonDAO.editNVTrongHD(hd);
								updateTableHoaDonCho();
								giamTheoPhanTramChoKhachHang = giamGiaKhachHangThanThiet(makh); /// chưa sửa hàm này
								lblGimGi.setText("Giảm giá: ("  + giamTheoPhanTramChoKhachHang + "%)");
								setLblGiamGia();
							}else {
								lbltenkh.setText(tenkh);
								KhachHang kh = khachHangDAO.getKhachHangById("KH01");
								NhanVien nv = nhanVienDAO.getNhanVienByName(lbltennv.getText());
//								HoaDon hd = new HoaDon(mahd, kh, nv);
								HoaDon hd = new HoaDon(tenkh, nv, kh);
								hoaDonDAO.editNVTrongHD(hd);
								updateTableHoaDonCho();
								giamTheoPhanTramChoKhachHang = giamGiaKhachHangThanThiet(makh); /// chưa sửa hàm này
								lblGimGi.setText("Giảm giá: ("  + giamTheoPhanTramChoKhachHang + "%)");
								lblmakh.setText(kh.getMaKH());
								lbltenkh.setText(kh.getTenKH());
								setLblGiamGia();
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "vui lòng chọn đúng thông tin khách hàng");
						}
		                
		            }
		        });
		        timKhachHangUI.setVisible(true);
		        disable();
			}
		});
		//===================================
		//===================================
		btnTim.setBackground(new Color(192, 192, 192));
		btnTim.setIcon(new ImageIcon(QuanLyBanHang.class.getResource("/icon/search.png")));
		btnTim.setFont(new Font("Arial", Font.BOLD, 11));
		GroupLayout gl_pnlKhachHang = new GroupLayout(pnlKhachHang);
		gl_pnlKhachHang.setHorizontalGroup(
			gl_pnlKhachHang.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlKhachHang.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlKhachHang.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlKhachHang.createSequentialGroup()
							.addComponent(lblTenKH, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbltenkh, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlKhachHang.createSequentialGroup()
							.addComponent(lblMaKH)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblmakh, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(btnTim, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(50)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlKhachHang.setVerticalGroup(
			gl_pnlKhachHang.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlKhachHang.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_pnlKhachHang.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaKH)
						.addComponent(lblmakh)
						.addComponent(btnTim, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlKhachHang.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTenKH)
						.addComponent(lbltenkh))
					.addContainerGap())
		);
		pnlKhachHang.setLayout(gl_pnlKhachHang);
		pnlHoaDon.setLayout(gl_pnlHoaDon);
		setLayout(groupLayout);
		
		//vong lập lbl
		
		//su kien hoa don cho va san pham
		///su kien tim kiếm liên tục (auto find load table)
		txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				TienKhachDua();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				TienKhachDua();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				TienKhachDua();
			}
		});
		txtTimKiemSP.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1);
					updateTableTimKiemSP();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1);
					updateTableTimKiemSP();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					Thread.sleep(1);
					updateTableTimKiemSP();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		///tìm kiếm sản phẩm
		//them sư kiện
		btnThemVaoGio.addActionListener(this);
		btnTaoHD.addActionListener(this);
		tblHoaDonCho.addMouseListener(this);
		btnHuyHoaDon.addActionListener(this);
		//table gio hang
		tblGioHang.addMouseListener(new MouseListener() {


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
				int row = tblGioHang.getSelectedRow();
				maspGioHang = tblGioHang.getValueAt(row, 0).toString();
				System.out.println(maspGioHang);
				phantramKM = Double.parseDouble(tblGioHang.getValueAt(row, 3).toString());
				System.out.println(phantramKM);
				dongia = Double.parseDouble(tblGioHang.getValueAt(row, 4).toString());
				System.out.println(dongia);
				String soluong = tblGioHang.getValueAt(row, 5).toString();
				txtSoLuong.setText(soluong);
			}
		});
		
		
		///table san pham
		tblDSSanPham.addMouseListener(new MouseListener() {
			
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
				int row = tblDSSanPham.getSelectedRow();
				masp = tblDSSanPham.getValueAt(row, 0).toString();
				System.out.println(masp);
			}
		});
	}
	private static JDialog loadingDialog;

	private static void showLoadingDialog(JPanel owner) {
	    // Create a new JDialog with the specified JPanel as the owner
	    loadingDialog = new JDialog(SwingUtilities.windowForComponent(owner), "Loading...");
	    
	    // Create a new JPanel for the loading content
	    JPanel loadingPanel = new JPanel(new BorderLayout());
	    JLabel titleLabel = new JLabel("đang tiến trình lưu dữ liệu và hóa đơn", SwingConstants.CENTER);
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
	private String chuyenDoiTien(double number) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

		// Chuyển đổi tien sang kiểu double trước khi định dạng
		String formattedAmount = currencyVN.format(number);

		// Xóa chữ "đ" từ chuỗi (mục đích sau để tiền Insert dữ liệu vào database)
		String processedAmount = formattedAmount.replace(" ₫", "");
		return processedAmount;
	}
	private static double parseDoubleWithoutCommas(String stringValue) {
        // Loại bỏ dấu chấm phân cách
        String stringWithoutCommas = stringValue.replace(".", "");

        // Chuyển đổi thành double
        return Double.parseDouble(stringWithoutCommas);
    }
	private void setLblGiamGia() {
		try {
			updateTableGioHang(mahd);
			ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
			double tongtien = 0.0;
			for(int t = 0; t < tblGioHang.getRowCount(); t++) {
				tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
			}
			lblTongTienpush.setText(tongtien+"");
			String makh = lblmakh.getText().trim();
			giamTheoPhanTramChoKhachHang = giamGiaKhachHangThanThiet(makh);
	        lblGimGi.setText("Giảm giá: ("  + giamTheoPhanTramChoKhachHang + "%)");
	        double giamgia = tongtien * giamTheoPhanTramChoKhachHang/100;
	        System.out.println(giamgia);
	        lblGiamGiapush.setText(chuyenDoiTien(giamgia));
	        double thueVT = tongtien * 10/100;
	        lblThuepush.setText(chuyenDoiTien(thueVT));
	        tienThanhToan = tongtien - giamgia + thueVT;
	        lblThanhToanpush.setText(chuyenDoiTien(tienThanhToan));
	        tienThanhToan = parseDoubleWithoutCommas(chuyenDoiTien(tienThanhToan));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private int giamGiaKhachHangThanThiet(String maKH) {
		try {
			KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
			double tongtien = khachHangDAO.getTongTienDaMuaCuaKH(maKH);
			int sale = 0;
			if(maKH.equals("KH01")) {
				return sale;
			}
			if(tongtien >= 3000000.0) {
				sale = 3;
			}
			if(tongtien >= 5000000.0) {
				sale = 5;
			}
			if(tongtien >= 10000000.0) {
				sale = 10;
			}
			return sale;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	private void updateTableGioHang(String mahd){
		
		try {
			Thread.sleep(200);
			lblMaHDpush.setText(mahd);
//			sanPhamDAO = new SanPhamDAO();
			clearTableGioHang();
			DefaultTableModel dtm = (DefaultTableModel) tblGioHang.getModel();
			dtm.getDataVector().removeAllElements();

			ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
			
//			List<SanPham> listsp = new ArrayList<SanPham>();
			List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDonDAO.getChiTietHoaDonByMaHD(mahd);
			for(ChiTietHoaDon cthd : chiTietHoaDons) {
				SanPham sp = cthd.getSanPham();
				double khuyenmai = 0.0;
				if(sp.getKhuyenMai() == null) {
					khuyenmai = 0.0;
				}else {
					khuyenmai = sp.getKhuyenMai().getPhanTramKhuyenMai();
				}
				giaSauKhuyenMai = sp.tinhGiaSauKhuyenMai(sp.getGiaBan(), khuyenmai);
				Object[] rowdata = {sp.getMaSP(),sp.getTenSP(),sp.getKichThuoc().getKichThuoc(),khuyenmai,sp.getGiaBan(),cthd.getSoLuong(), giaSauKhuyenMai*cthd.getSoLuong()};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void updateTableHoaDonCho() {
		try {
			clearTableDSHDC();
			DefaultTableModel dtm = (DefaultTableModel) tblHoaDonCho.getModel();
			dtm.getDataVector().removeAllElements();
			HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");

            // Gọi các phương thức từ DAO
            List<HoaDon> hoaDons = hoaDonDAO.getHDCho();
            
			for(HoaDon hd : hoaDons) {
				if(hd.getNhanVien() != null) {
					Object[] rowdata = {hd.getMaHD(),hd.getNgayLap(),hd.getNhanVien().getTenNV(),hd.getKhachHang().getTenKH()};
					dtm.addRow(rowdata);
				}else {
					JOptionPane.showMessageDialog(null, "khong tim thay nhan vien trong hoa don(loi nghiep vu)");
				}
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void TienKhachDua() {
//		double tienkhachdua = Double.parseDouble(txtTienKhachDua.getText());
		double thua = 0;
//		
		String input = txtTienKhachDua.getText().trim();
		if(!input.isEmpty()) {
			try {
				double tienkhachdua = Double.parseDouble(input);
				if(tienkhachdua < tienThanhToan) {
					thua = tienkhachdua -tienThanhToan;
					lbltienthua.setText(" -"+ chuyenDoiTien(thua)+"");
					lbltienthua.setForeground(new Color(255,0,0));
				}else {
					thua = tienkhachdua -tienThanhToan;
					txtTienKhachDua.setForeground(new Color(0,0,0));
					lbltienthua.setText(chuyenDoiTien(thua));
					lbltienthua.setForeground(new Color(34, 139, 34));
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "vui lòng nhập số tiền của khách");
			}
		}else {
			lbltienthua.setText("");
		}
		
	}
	public String getAutoIDHD() {		
		try {
	        HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
	        String idPrefix = "HD";
			int length = hoaDonDAO.getAllHoaDon().size();
			String finalId = idPrefix + String.format("%02d", length + 1);
	        return finalId;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
       
    }
	private void updateTableTimKiemSP(){
		String masp = txtTimKiemSP.getText();

		try {
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			if(txtTimKiemSP.getText().equals("")) {
				clearTableDSSP();
				DefaultTableModel dtm = (DefaultTableModel) tblDSSanPham.getModel();
				List<SanPham> listsp = sanPhamDAO.dsSPBanHang();
				for(SanPham sp : listsp) {
					Object[] rowdata = {sp.getMaSP(),sp.getTenSP(),sp.getPhanLoai().getPhanLoai(),sp.getKichThuoc(),sp.getGiaBan(),sp.getSoLuong(),sp.getMauSac().getMauSac(),sp.getKichThuoc().getKichThuoc()};
					dtm.addRow(rowdata);
				}
			}else {
				sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
				clearTableDSSP();
				DefaultTableModel dtm = (DefaultTableModel) tblDSSanPham.getModel();
				List<SanPham> listsp = sanPhamDAO.getDSSPTheoMaSP(masp);
				for(SanPham sp : listsp) {
					Object[] rowdata = {sp.getMaSP(),sp.getTenSP(),sp.getPhanLoai().getPhanLoai(),sp.getKichThuoc(),sp.getGiaBan(),sp.getSoLuong(),sp.getMauSac().getMauSac(),sp.getKichThuoc().getKichThuoc()};
					dtm.addRow(rowdata);
				}
			}
		} catch (RemoteException | NotBoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//khai bao
		
		
		//bat dau su kien
		Object o = e.getSource();
		
		if(o.equals(btnThemVaoGio)) {
			soluong = 1;
			boolean check = true;

			int cnt = tblGioHang.getRowCount();
			for(int i = 0; i < cnt; i++) {
				if(tblGioHang.getValueAt(i, 0).equals(masp)) {
					try {
						
						SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
						HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
						ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
						String soluong = tblGioHang.getValueAt(i, 5).toString();
						System.out.println("so luong cua sp :"+soluong);
						int sl = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString());
						SanPham sp = sanPhamDAO.findById(masp);
						HoaDon hd = hoaDonDAO.findById(mahd);
						double phantram = phantramKM;
						System.out.println(phantram);
						sl++;
						ChiTietHoaDon cthd = new ChiTietHoaDon();
						giaSauKhuyenMai = sp.tinhGiaSauKhuyenMai(dongia, phantram)*sl;
						System.out.println(giaSauKhuyenMai);
//						cthd = new ChiTietHoaDon(sp, hd, phantram, sl, giaSauKhuyenMai);

						cthd = new ChiTietHoaDon(hd, sp, sl, phantram, giaSauKhuyenMai);
						chiTietHoaDonDAO.updateSoLuongSPTrongGio(cthd);
					
						
						updateTableGioHang(mahd);
						double tongtien = 0.0;
						for(int t = 0; t < tblGioHang.getRowCount(); t++) {
							tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
						}
						lblTongTienpush.setText(tongtien+"");
						check = false;
						setLblGiamGia();
					} catch (NumberFormatException | RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this, "vui lòng chọn sản phẩm để thêm vào giỏ !!!");
					}
				}
			}
			if(check) {
				try {
					SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
					HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
					ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
					SanPham sp = sanPhamDAO.findById(masp);
					HoaDon hd = hoaDonDAO.findById(mahd);
//					ChiTietHoaDon cthd = new ChiTietHoaDon(sp, hd, 0.0, 1, sp.getGiaBan());
					System.out.println(sp);
//					ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, 1, 0.0, sp.getGiaBan()*1);
					ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, 1, sp.getKhuyenMai().getPhanTramKhuyenMai(), sp.getGiaBan());
					chiTietHoaDonDAO.addSanPhamVaoHD(cthd);
					updateTableGioHang(mahd);
					double tongtien = 0.0;
					for(int t = 0; t < tblGioHang.getRowCount(); t++) {
						tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
					}
					lblTongTienpush.setText(tongtien+"");
					setLblGiamGia();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "vui lòng chọn sản phẩm để thêm vào giỏ !!!");
					e2.printStackTrace();
				}
			}
			
		}
		if(o.equals(btnTaoHD)) {
//			HoaDonDAO hoaDonDAO = new HoaDonDAO();
			try {
				HoaDonDAO hoaDonDAO2 = (HoaDonDAO) registry.lookup("HoaDonDAO");
				KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
				NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
				ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
				System.out.println(hoaDonDAO2.getHDCho().size());
				if(hoaDonDAO2.getHDCho().size() > 3) {
					JOptionPane.showMessageDialog(null, "Bạn không thể tạo quá 4 hóa đơn chờ");
					updateTableHoaDonCho();
				}else {
					KhachHang kh = new KhachHang();
					LocalDate localDate = LocalDate.now();
					if(!lblmakh.getText().equals("")) {
						kh = khachHangDAO.getKhachHangById(lblmakh.getText()) ;
					}else kh = null;
					
					NhanVien nv = nhanVienDAO.getNhanVienByName(lbltennv.getText());
//					NhanVien nv = nhanVienDAO.getNhanVienByID("NV01");
					System.out.println(nv.getTenNV());
			        // Chuyển đổi LocalDate sang Date
//			        Date date = Date.valueOf(localDate);
			        HoaDon hd1 = new HoaDon();
//					HoaDon hd = new HoaDon(hd1.getAutoID(), date, kh, nv, 0,0.0);
			        
			        HoaDon hd = new HoaDon(getAutoIDHD(), localDate, nv, kh, 0, 0.0);
			        double tongtien = 0.0;
					for(int t = 0; t < tblGioHang.getRowCount(); t++) {
						tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
					}
					lblTongTienpush.setText(tongtien+"");
					hoaDonDAO2.addHoaDon(hd);
					updateTableHoaDonCho();

				}
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			
//			
		}
		if(o.equals(btnHuyHoaDon)) {
			try {
				HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
				ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
				SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
				int desiredColumnIndex = 0;
				int rowCount = tblGioHang.getRowCount();
				for(int i = 0; i < rowCount; i++) {
					
					String msp = tblGioHang.getValueAt(i, desiredColumnIndex).toString();
					SanPham sp = sanPhamDAO.findById(msp);
					HoaDon hd = hoaDonDAO.findById(mahd);
					double phantram = phantramKM;
					int sl = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString());
					ChiTietHoaDon cthd = new ChiTietHoaDon();
					giaSauKhuyenMai = sp.tinhGiaSauKhuyenMai(dongia, phantram)*sl;
					cthd = new ChiTietHoaDon(hd, sp, sl, phantram, giaSauKhuyenMai);
					chiTietHoaDonDAO.deleteMotSP(cthd);
					
				}
				updateTableGioHang(mahd);
				double tongtien = 0.0;
				for(int t = 0; t < tblGioHang.getRowCount(); t++) {
					tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
				}
				lblTongTienpush.setText(tongtien+"");
				setLblGiamGia();
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			int rowhdc = tblHoaDonCho.getSelectedRow();
			mahd = tblHoaDonCho.getValueAt(rowhdc, 0).toString();
			System.out.println(mahd);
			ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
			double tongtien = 0.0;
			for(int t = 0; t < tblGioHang.getRowCount(); t++) {
				tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
			}
			lblTongTienpush.setText(tongtien+"");
			updateTableGioHang(mahd);
			System.out.println(qrCodeValue);
			String tenkh = tblHoaDonCho.getValueAt(rowhdc, 3).toString();
			lbltenkh.setText(tenkh);
			KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
			lblmakh.setText(khachHangDAO.getKhachHangTheoTen(tenkh).get(0).getTenKH());
			setLblGiamGia();
		} catch (RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

//		
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
	///them sp bang qr
	@Override
	public void onQRCodeRead(String qrCode) {
		try {
		    SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
		    ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
		    HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");

		    // Lấy danh sách sản phẩm theo mã hóa đơn
		    List<SanPham> listsp = sanPhamDAO.getSanPhanTheoMaHD(mahd);

		    // Kiểm tra xem sản phẩm có trong giỏ hàng không
		    boolean spExistsInCart = false;
		    for (SanPham sp : listsp) {
		        if (sp.getMaSP().equals(qrCode)) {
		            spExistsInCart = true;
		        }
		    }

		    if (qrCode.startsWith("SP")) {
		        if (spExistsInCart) {
		            // Nếu sản phẩm đã có trong giỏ hàng, không cần tăng số lượng lên
		            // Thực hiện các công việc cập nhật giỏ hàng ở đây
		            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
		                if (tblGioHang.getValueAt(i, 0).equals(qrCode)) {
		                    SanPham sp = sanPhamDAO.findById(qrCode);
		                    HoaDon hd = hoaDonDAO.findById(mahd);
		                    double phantram = phantramKM;
		                    int l  = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString()) ;
		                    l++;
		                    // Cập nhật giỏ hàng với sản phẩm đã có
		                    ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, l, phantram, sp.getGiaBan());
		                    chiTietHoaDonDAO.updateSoLuongSPTrongGio(cthd);
		                    updateTableGioHang(mahd);

		                    // Tính lại tổng tiền
		                    double tongtien = 0.0;
		                    for (int t = 0; t < tblGioHang.getRowCount(); t++) {
		                        tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
		                    }
		                    lblTongTienpush.setText(String.valueOf(tongtien));
		                    System.out.println("đã tăng");
		                }
		            }
		        } else {
		            // Nếu sản phẩm chưa có trong giỏ hàng, thêm vào giỏ hàng
		            SanPham sp = sanPhamDAO.findById(qrCode);
		            HoaDon hd = hoaDonDAO.findById(mahd);
		            double phantram = phantramKM;

		            // Thêm sản phẩm vào giỏ hàng
		            ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, 1, sp.getKhuyenMai().getPhanTramKhuyenMai(), sp.getGiaBan());
		            chiTietHoaDonDAO.addSanPhamVaoHD(cthd);
		            updateTableGioHang(mahd);

		            // Tính lại tổng tiền
		            double tongtien = 0.0;
		            for (int t = 0; t < tblGioHang.getRowCount(); t++) {
		                tongtien += Double.parseDouble(tblGioHang.getValueAt(t, 6).toString());
		            }
		            lblTongTienpush.setText(String.valueOf(tongtien));
		        }
		    }
		} catch (RemoteException | NotBoundException e) {
		    // Xử lý ngoại lệ
		    e.printStackTrace();
		}

	}
}
