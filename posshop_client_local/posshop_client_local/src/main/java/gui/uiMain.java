package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import main.Login;

//import component.LoadingDialog;
//import entity.KhuyenMai;
//import main.Login;

public class uiMain{

	public JFrame frame;
	public JPanel mainPanel = new JPanel();
	private JPanel btnSanPham;
	private JPanel btnHoaDon;
	private JPanel btnKhuyenMai;
	private JPanel btnNhanVien;
	private JPanel btnKhachHang;
	private JPanel btnThongKe;
	private JPanel btnBanHang;
//	private KhuyenMai km = new KhuyenMai();
	private QuanLyBanHang qlbh;
	private QuanLyBanHang qlbhang = new QuanLyBanHang();
	private uiSanPham qlsp;
	private QuanLyHoaDon qlhd;
	private QuanLyKhuyenMai qlkm;
	private QuanLyNhanVien qlnv;
	private QuanLyKhachHang qlkh;
	public QuanLyThongKe qltk;
//	public TabThongKeDoanhThu doanhThu;
//	public TabThongKeSanPham keSanPham;
	public JLabel tenNV, chucVu;
	private JPanel btnNhaCC;
	private QuanLyNhaCungCap qlncc;

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					uiMain window = new uiMain();
//					window.frame.setVisible(true);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public uiMain() {
		loadDataInBackground(qlbh);
		loadDataInBackground(qlsp);
		loadDataInBackground(qlnv);
		loadDataInBackground(qlkh);
		loadDataInBackground(qltk);
		loadDataInBackground(qlkm);
		loadDataInBackground(qlhd);
		loadDataInBackground(qlncc);
//		km.xoaKhuyenMaiKhiHetHan();
		qlbh = new QuanLyBanHang();
		qlbh.setVisible(true);
		qlsp = new uiSanPham();
		qlsp.setVisible(true);
		qlhd = new QuanLyHoaDon();
		qlhd.setVisible(true);
		qlkm = new QuanLyKhuyenMai();
		qlkm.setVisible(true);
		qlnv = new QuanLyNhanVien();
		qlnv.setVisible(true);
		qlkh = new QuanLyKhachHang();
		qlkh.setVisible(true);
		qltk = new QuanLyThongKe();
		qltk.setVisible(true);
		
		qlncc = new QuanLyNhaCungCap();
		qlncc.setVisible(true);
		initialize();
//		layTenChucVu(, null);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void loadDataInBackground(final JPanel panel) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate loading data
//                Thread.sleep(2000); // For demonstration purposes

                // Add the panel to the tab
                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.add(panel, BorderLayout.CENTER);
                panel.setVisible(true);
                panel.revalidate();

                // Remove loading animation or message if necessary

                return null;
            }
        };

        worker.execute();
    }
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));

		JPanel pnlMenu = new JPanel();
		pnlMenu.setPreferredSize(new Dimension(947, 703));

		pnlMenu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlMenu.setBackground(new Color(152, 251, 152));
//		setIconImage(new ImageIcon(uiMain.class.getResource("/icon/logofinal.png")).getImage());
		JPanel mainPanel = new JPanel();
		QuanLyBanHang qlbh_1 = new QuanLyBanHang();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(pnlMenu, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE).addContainerGap())
				.addComponent(pnlMenu, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE));
		mainPanel.setLayout(new CardLayout(0, 0));

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(uiMain.class.getResource("/icon/mainScreen.jpg")));
		mainPanel.add(lblNewLabel_3, "name_2182919992199");

		JPanel pnlListMenu = new JPanel();
		pnlListMenu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlListMenu.setBackground(new Color(255, 255, 255));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(152, 251, 152));
		GroupLayout gl_pnlMenu = new GroupLayout(pnlMenu);
		gl_pnlMenu.setHorizontalGroup(gl_pnlMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlMenu.createSequentialGroup()
						.addGroup(gl_pnlMenu.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING,
										gl_pnlMenu.createSequentialGroup().addContainerGap().addComponent(pnlListMenu,
												GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
								.addGroup(gl_pnlMenu.createSequentialGroup().addGap(5).addComponent(panel,
										GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_pnlMenu.setVerticalGroup(gl_pnlMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMenu.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(pnlListMenu, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE).addContainerGap()));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(uiMain.class.getResource("/icon/information-employee.png")));

		tenNV = new JLabel();
		tenNV.setFont(new Font("Arial", Font.BOLD, 15));

		chucVu = new JLabel();
		chucVu.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING).addComponent(tenNV).addComponent(chucVu))
						.addContainerGap(29, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(tenNV)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chucVu).addGap(25))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addComponent(lblNewLabel)
						.addContainerGap(22, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
//		mainPanel.removeAll();
//		mainPanel.revalidate(); 
		// Cập nhật lại mainPanel để hiển thị giao diện mới
		/// chuyển giao diện
		btnBanHang = new JPanel();
		btnBanHang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlbh  = new QuanLyBanHang();
				qlbh.lbltennv.setText(tenNV.getText());
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlbh, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(34, 139, 34));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnBanHang.setBackground(new Color(144, 238, 144));
//		btnBanHang.setBackground(new Color(34, 139, 34));
		// Phím tắt F1
		btnBanHang.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
		btnBanHang.getActionMap().put("F1", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				qlbh  = new QuanLyBanHang();
				qlbh.lbltennv.setText(tenNV.getText());
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlbh, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(34, 139, 34));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnSanPham = new JPanel();
		btnSanPham.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlsp = new uiSanPham();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlsp, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(34, 139, 34));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
			}
		});
		btnSanPham.setBackground(new Color(144, 238, 144));
		// Phím tắt F2
		btnSanPham.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
		btnSanPham.getActionMap().put("F2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				qlsp = new uiSanPham();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlsp, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(34, 139, 34));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});

		JLabel lblSanPham = new JLabel("<html>Quản Lý<br>Sản Phẩm</html>");
		lblSanPham.setBounds(60, 4, 129, 52);
		lblSanPham.setForeground(Color.WHITE);
		lblSanPham.setFont(new Font("Arial", Font.BOLD, 22));
		lblSanPham.setBackground(Color.WHITE);

		JLabel iconSanPham = new JLabel("");
		iconSanPham.setBounds(10, 12, 40, 40);
		iconSanPham.setIcon(new ImageIcon(uiMain.class.getResource("/icon/box.png")));

		btnHoaDon = new JPanel();
		btnHoaDon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlhd = new QuanLyHoaDon();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlhd, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(34, 139, 34));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnHoaDon.setBackground(new Color(144, 238, 144));
		// Phím tắt F3
		btnHoaDon.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "F3");
		btnHoaDon.getActionMap().put("F3", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				qlhd = new QuanLyHoaDon();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlhd, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(34, 139, 34));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		JLabel iconSanPham_1 = new JLabel("");
		iconSanPham_1.setBounds(10, 12, 40, 40);
		iconSanPham_1.setIcon(new ImageIcon(uiMain.class.getResource("/icon/bill.png")));

		JLabel lblHoaDon = new JLabel("<html>Quản Lý<br>Hóa Đơn</html>");
		lblHoaDon.setBounds(60, 4, 129, 52);
		lblHoaDon.setForeground(Color.WHITE);
		lblHoaDon.setFont(new Font("Arial", Font.BOLD, 22));
		lblHoaDon.setBackground(Color.WHITE);

		btnKhuyenMai = new JPanel();
		btnKhuyenMai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlkm = new QuanLyKhuyenMai();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlkm, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(34, 139, 34));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnKhuyenMai.setBackground(new Color(144, 238, 144));
		// Phím tắt F4
		btnKhuyenMai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0),
				"F4");
		btnKhuyenMai.getActionMap().put("F4", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				qlkm = new QuanLyKhuyenMai();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlkm, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(34, 139, 34));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		JLabel iconSanPham_1_1 = new JLabel("");
		iconSanPham_1_1.setBounds(10, 12, 40, 40);
		iconSanPham_1_1.setIcon(new ImageIcon(uiMain.class.getResource("/icon/sale2.png")));

		JLabel lblKhuyenMai = new JLabel("<html>Quản Lý<br>Khuyến Mãi</html>");
		lblKhuyenMai.setBounds(60, 4, 129, 52);
		lblKhuyenMai.setForeground(Color.WHITE);
		lblKhuyenMai.setFont(new Font("Arial", Font.BOLD, 22));
		lblKhuyenMai.setBackground(Color.WHITE);

		btnNhanVien = new JPanel();
		btnNhanVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlnv = new QuanLyNhanVien();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlnv, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(34, 139, 34));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnNhanVien.setBackground(new Color(144, 238, 144));
		// Phím tắt F5
		btnNhanVien.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5");
		btnNhanVien.getActionMap().put("F5", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				qlnv = new QuanLyNhanVien();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlnv, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(34, 139, 34));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		JLabel iconNhanVien = new JLabel("");
		iconNhanVien.setBounds(10, 12, 40, 40);
		iconNhanVien.setIcon(new ImageIcon(uiMain.class.getResource("/icon/emlpyee.png")));

		JLabel lblNhanvien = new JLabel("<html>Quản Lý<br>Nhân Viên</html>");
		lblNhanvien.setBounds(60, 4, 129, 52);
		lblNhanvien.setForeground(Color.WHITE);
		lblNhanvien.setFont(new Font("Arial", Font.BOLD, 22));
		lblNhanvien.setBackground(Color.WHITE);

		btnKhachHang = new JPanel();
		btnKhachHang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlkh = new QuanLyKhachHang();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlkh, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(34, 139, 34));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnKhachHang.setBackground(new Color(144, 238, 144));
		// Phím tắt F6
		btnKhachHang.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0),
				"F6");
		btnKhachHang.getActionMap().put("F6", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				qlkh = new QuanLyKhachHang();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlkh, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(34, 139, 34));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		JLabel iconKhachHang = new JLabel("");
		iconKhachHang.setBounds(10, 12, 40, 40);
		iconKhachHang.setIcon(new ImageIcon(uiMain.class.getResource("/icon/customer.png")));

		JLabel lblKhachHang = new JLabel("<html>Quản Lý<br>Khách Hàng</html>");
		lblKhachHang.setBounds(60, 4, 131, 52);
		lblKhachHang.setForeground(Color.WHITE);
		lblKhachHang.setFont(new Font("Arial", Font.BOLD, 22));
		lblKhachHang.setBackground(Color.WHITE);

		btnThongKe = new JPanel();
		btnThongKe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qltk, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(34, 139, 34));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		btnThongKe.setBackground(new Color(144, 238, 144));
		// Phím tắt F7
		btnThongKe.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "F7");
		btnThongKe.getActionMap().put("F7", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qltk, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnNhanVien.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(34, 139, 34));
				btnNhaCC.setBackground(new Color(144, 238, 144));
			}
		});
		JLabel iconThongKe = new JLabel("");
		iconThongKe.setBounds(10, 12, 40, 40);
		iconThongKe.setIcon(new ImageIcon(uiMain.class.getResource("/icon/statistical.png")));

		JLabel lblThongKe = new JLabel("<html>Quản Lý<br>Thống Kê</html>");
		lblThongKe.setBounds(60, 4, 129, 52);
		lblThongKe.setForeground(Color.WHITE);
		lblThongKe.setFont(new Font("Arial", Font.BOLD, 22));
		lblThongKe.setBackground(Color.WHITE);

		JPanel btnDangXuat = new JPanel();
		btnDangXuat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?",
						"Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					Login lg = new Login();
					lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					lg.setLocationRelativeTo(null);
					lg.setVisible(true);
					frame.setVisible(false);
				}
			}
		});
		btnDangXuat.setBackground(new Color(255, 0, 0));

		JLabel iconDangXuat = new JLabel("");
		iconDangXuat.setBounds(12, 4, 36, 40);
		iconDangXuat.setIcon(new ImageIcon(uiMain.class.getResource("/icon/logout3.png")));
		// Phím tắt ESC
		btnDangXuat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Esc");
		btnDangXuat.getActionMap().put("Esc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?",
						"Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					Login lg = new Login();
					lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					lg.setLocationRelativeTo(null);
					lg.setVisible(true);
					frame.setVisible(false);
				}
			}
		});
		JLabel lblDangXuat = new JLabel("Đăng Xuất");
		lblDangXuat.setBounds(56, 12, 111, 26);
		lblDangXuat.setForeground(Color.WHITE);
		lblDangXuat.setFont(new Font("Arial", Font.BOLD, 22));
		lblDangXuat.setBackground(Color.WHITE);

		JPanel btnTroGiup = new JPanel();
		btnTroGiup.setLayout(null);
		btnTroGiup.setBackground(new Color(144, 238, 144));
		btnTroGiup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Mở trang web chỉ định khi JPanel được click
				if (JOptionPane.showConfirmDialog(null,
						"Bạn sẽ được chuyển hướng đến trang web trợ giúp trên trình duyệt !", "Cảnh Báo !!",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						String filePath = "troGiup/troGiup.pdf";
						try {
				            File file = new File(filePath);
				            URI uri = file.toURI();
				            Desktop desktop = Desktop.getDesktop();
				            desktop.browse(uri);
				        } catch (IOException e1) {
				            e1.printStackTrace();
				        }
					}
			}
		});
		// Phím tắt F8
		btnTroGiup.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "F8");
		btnTroGiup.getActionMap().put("F8", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Mở trang web chỉ định khi JPanel được click
				if (JOptionPane.showConfirmDialog(null,
						"Bạn sẽ được chuyển hướng đến trang web trợ giúp trên trình duyệt !", "Cảnh Báo !!",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						String filePath = "troGiup/troGiup.pdf";
						try {
				            File file = new File(filePath);
				            URI uri = file.toURI();
				            Desktop desktop = Desktop.getDesktop();
				            desktop.browse(uri);
				        } catch (IOException e2) {
				            e2.printStackTrace();
				        }
					}		
			}
		});
		JLabel iconThongKe_1 = new JLabel("");
		iconThongKe_1.setIcon(new ImageIcon(uiMain.class.getResource("/icon/help.png")));
		iconThongKe_1.setBounds(10, 0, 40, 40);
		btnTroGiup.add(iconThongKe_1);

		JLabel lblTroGiup = new JLabel("Trợ Giúp");
		lblTroGiup.setForeground(Color.WHITE);
		lblTroGiup.setFont(new Font("Arial", Font.BOLD, 22));
		lblTroGiup.setBackground(Color.WHITE);
		lblTroGiup.setBounds(60, 4, 129, 32);
		btnTroGiup.add(lblTroGiup);
		
		btnNhaCC = new JPanel();
		btnNhaCC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				qlncc = new QuanLyNhaCungCap();
				mainPanel.removeAll(); // Xóa tất cả các thành phần con khỏi mainPanel
				mainPanel.add(qlncc, BorderLayout.CENTER); // Đặt giao diện quản lý nhân viên vào mainPanel
				mainPanel.revalidate(); // Cập nhật lại mainPanel để hiển thị giao diện mới

				btnBanHang.setBackground(new Color(144, 238, 144));
				btnSanPham.setBackground(new Color(144, 238, 144));
				btnHoaDon.setBackground(new Color(144, 238, 144));
				btnKhuyenMai.setBackground(new Color(144, 238, 144));
				btnKhachHang.setBackground(new Color(144, 238, 144));
				btnThongKe.setBackground(new Color(144, 238, 144));
				btnNhaCC.setBackground(new Color(34, 139, 34));
			}
		});
		btnNhaCC.setLayout(null);
		btnNhaCC.setBackground(new Color(144, 238, 144));
		
		JLabel iconThongKe_2 = new JLabel("");
		iconThongKe_2.setIcon(new ImageIcon(uiMain.class.getResource("/icon/ncc1.png")));
		iconThongKe_2.setBounds(10, 12, 40, 40);
		btnNhaCC.add(iconThongKe_2);
		
		JLabel lblqunLnhCung = new JLabel("<html>Quản Lý<br>NCC</html>");
		lblqunLnhCung.setForeground(Color.WHITE);
		lblqunLnhCung.setFont(new Font("Arial", Font.BOLD, 22));
		lblqunLnhCung.setBackground(Color.WHITE);
		lblqunLnhCung.setBounds(60, 4, 123, 52);
		btnNhaCC.add(lblqunLnhCung);
		GroupLayout gl_pnlListMenu = new GroupLayout(pnlListMenu);
		gl_pnlListMenu.setHorizontalGroup(
			gl_pnlListMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlListMenu.createSequentialGroup()
					.addGroup(gl_pnlListMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_pnlListMenu.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlListMenu.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSanPham, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(btnBanHang, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_pnlListMenu.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_pnlListMenu.createParallelGroup(Alignment.LEADING)
								.addComponent(btnKhachHang, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 193, Short.MAX_VALUE)
								.addComponent(btnNhanVien, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
								.addComponent(btnThongKe, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 193, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_pnlListMenu.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlListMenu.createParallelGroup(Alignment.LEADING)
								.addComponent(btnKhuyenMai, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(btnHoaDon, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_pnlListMenu.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnDangXuat, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
						.addGroup(gl_pnlListMenu.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnTroGiup, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_pnlListMenu.createSequentialGroup()
							.addContainerGap(12, Short.MAX_VALUE)
							.addComponent(btnNhaCC, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlListMenu.setVerticalGroup(
			gl_pnlListMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlListMenu.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnBanHang, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSanPham, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnHoaDon, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnKhuyenMai, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNhanVien, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnKhachHang, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnThongKe, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNhaCC, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnTroGiup, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDangXuat, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
		);
		btnDangXuat.setLayout(null);
		btnDangXuat.add(iconDangXuat);
		btnDangXuat.add(lblDangXuat);
		btnThongKe.setLayout(null);
		btnThongKe.add(iconThongKe);
		btnThongKe.add(lblThongKe);
		btnKhachHang.setLayout(null);
		btnKhachHang.add(iconKhachHang);
		btnKhachHang.add(lblKhachHang);
		btnNhanVien.setLayout(null);
		btnNhanVien.add(iconNhanVien);
		btnNhanVien.add(lblNhanvien);
		btnKhuyenMai.setLayout(null);
		btnKhuyenMai.add(iconSanPham_1_1);
		btnKhuyenMai.add(lblKhuyenMai);
		btnHoaDon.setLayout(null);
		btnHoaDon.add(iconSanPham_1);
		btnHoaDon.add(lblHoaDon);
		btnSanPham.setLayout(null);
		btnSanPham.add(iconSanPham);
		btnSanPham.add(lblSanPham);

		JLabel lblIconBanHang = new JLabel("");
		lblIconBanHang.setBounds(10, 12, 40, 40);
		lblIconBanHang.setIcon(new ImageIcon(uiMain.class.getResource("/icon/buy.png")));

		JLabel lblBanHang = new JLabel("<html>Quản Lý<br>Bán Hàng</html>");
		lblBanHang.setBounds(60, 4, 129, 52);
		lblBanHang.setForeground(new Color(255, 255, 255));
		lblBanHang.setBackground(new Color(255, 255, 255));
		lblBanHang.setFont(new Font("Arial", Font.BOLD, 22));
		btnBanHang.setLayout(null);
		btnBanHang.add(lblIconBanHang);
		btnBanHang.add(lblBanHang);
		pnlListMenu.setLayout(gl_pnlListMenu);
		pnlMenu.setLayout(gl_pnlMenu);
		frame.getContentPane().setLayout(groupLayout);
//		frame.setBounds(100, 100, 1215, 777);
		frame.setSize(new Dimension(1215, 777));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	public void layTenChucVu(String ten, String chuc) {
		qlbh.layTenChucVu(ten);	
		tenNV.setText(ten);
		chucVu.setText("Chức Vụ: " + chuc);
		qlbhang.layTenChucVu(ten);
	}

}
