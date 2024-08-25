package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import dao.KhuyenMaiDAO;
import dao.TaiKhoanDAO;
//import ConnectDB.KetNoiSQL;
//import dao.loginDAO;
import entities.TaiKhoan;
import gui.FormQuenMatKhau;
import gui.QuanLyBanHang;
//import gui.QuanLyThongKe;
import gui.uiMain;
import gui.uiMain2;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTenTK;
	private JPasswordField passwordField;
	private JButton btnDangNhap, btnXemMatKhau, btnQuenMatKhau;
	private String tenNV, chucVu;
	private int trangThai;
	private JFrame frame;
	private FormQuenMatKhau lmxn;
	private uiMain otherUI;
	private boolean isPasswordVisible = false;
	private uiMain2 otherUI2;
	private Registry registry;
	
	/**
	 * Launch the application.
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

	}

	/**
	 * Create the frame.
	 */
	public Login() {
		try {
			local_host local = new local_host(); 
			registry = LocateRegistry.getRegistry(local.host(), 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		try {
		    BufferedImage image = ImageIO.read(getClass().getResource("/icon/logoEntertaiment.png"));
		    setIconImage(image);
		} catch (IOException e) {
		    e.printStackTrace();
		}
//		setIconImage(new ImageIcon(uiMain.class.getResource("/icon/logofinal.png")).getImage());
//		setIconImage(new ImageIcon(uiMain2.class.getResource("/icon/logofinal.png")).getImage());
		setContentPane(contentPane);

		JPanel panelLogo = new JPanel();
		panelLogo.setBounds(5, 5, 329, 601);
		panelLogo.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelLogo.setBackground(new Color(34, 139, 34));

		JPanel pnlDangNhap = new JPanel();
		pnlDangNhap.setForeground(new Color(144, 238, 144));
		pnlDangNhap.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		pnlDangNhap.setBounds(333, 5, 646, 601);
		pnlDangNhap.setBackground(new Color(144, 238, 144));
		contentPane.setLayout(null);
		contentPane.add(panelLogo);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/icon/logoshop3.png")));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/icon/manghinh.png")));

		JLabel lblNewLabel_2 = new JLabel("POS SYSTEM");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 40));
		GroupLayout gl_panelLogo = new GroupLayout(panelLogo);
		gl_panelLogo.setHorizontalGroup(gl_panelLogo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLogo.createSequentialGroup()
						.addGroup(gl_panelLogo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelLogo.createSequentialGroup().addContainerGap()
										.addGroup(gl_panelLogo.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_1).addComponent(lblNewLabel)))
								.addGroup(gl_panelLogo.createSequentialGroup().addGap(40).addComponent(lblNewLabel_2)))
						.addContainerGap(40, Short.MAX_VALUE)));
		gl_panelLogo.setVerticalGroup(gl_panelLogo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLogo.createSequentialGroup().addContainerGap()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
						.addGap(82).addComponent(lblNewLabel_2).addContainerGap(110, Short.MAX_VALUE)));
		panelLogo.setLayout(gl_panelLogo);
		contentPane.add(pnlDangNhap);

		JLabel lblNewLabel_2_1 = new JLabel("Đăng Nhập");
		lblNewLabel_2_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_2_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 30));

		JLabel lblNewLabel_2_1_1 = new JLabel("tên tài khoản");
		lblNewLabel_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_2_1_1.setBackground(Color.BLACK);

		txtTenTK = new JTextField();
		txtTenTK.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTenTK.setColumns(10);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("mật khẩu ");
		lblNewLabel_2_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_2_1_1_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_2_1_1_1.setBackground(Color.BLACK);

		btnDangNhap = new JButton("ĐĂNG NHẬP");
		// Phím tắt Enter
		btnDangNhap.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		btnDangNhap.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDangNhap.doClick();
			}
		});
		btnDangNhap.setIcon(new ImageIcon(Login.class.getResource("/icon/login.png")));
		btnDangNhap.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		btnDangNhap.setForeground(new Color(255, 255, 255));
		btnDangNhap.setBackground(new Color(255, 0, 0));
		btnDangNhap.setFont(new Font("Arial", Font.BOLD, 13));

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 12));

		btnXemMatKhau = new JButton("");
		btnXemMatKhau.setForeground(new Color(255, 255, 255));
		btnXemMatKhau.setBackground(new Color(144, 238, 144));
		btnXemMatKhau.setBorder(new LineBorder(new Color(144, 238, 144), 2));
		btnXemMatKhau.setIcon(new ImageIcon(Login.class.getResource("/icon/conmat.png")));

		btnQuenMatKhau = new JButton("Quên mật khẩu ?");
		btnQuenMatKhau.setBackground(new Color(144, 238, 144));
		btnQuenMatKhau.setFont(new Font("Arial", Font.BOLD, 14));
		btnQuenMatKhau.setBorder(new LineBorder(new Color(144, 238, 144), 2));
		GroupLayout gl_pnlDangNhap = new GroupLayout(pnlDangNhap);
		gl_pnlDangNhap.setHorizontalGroup(gl_pnlDangNhap.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlDangNhap.createSequentialGroup().addGap(142)
						.addGroup(gl_pnlDangNhap.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
								.addComponent(btnDangNhap, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
								.addGroup(gl_pnlDangNhap.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblNewLabel_2_1_1_1, Alignment.LEADING,
												GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2_1_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
												158, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2_1, Alignment.LEADING).addComponent(txtTenTK,
												Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
						.addGap(18)
						.addComponent(btnXemMatKhau, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addGap(65))
				.addGroup(gl_pnlDangNhap.createSequentialGroup().addContainerGap(252, Short.MAX_VALUE)
						.addComponent(btnQuenMatKhau, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
						.addGap(230)));
		gl_pnlDangNhap.setVerticalGroup(gl_pnlDangNhap.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlDangNhap
				.createSequentialGroup().addGap(153)
				.addGroup(gl_pnlDangNhap.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnXemMatKhau, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDangNhap.createSequentialGroup().addComponent(lblNewLabel_2_1).addGap(23)
								.addComponent(lblNewLabel_2_1_1, GroupLayout.PREFERRED_SIZE, 28,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtTenTK, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_2_1_1_1, GroupLayout.PREFERRED_SIZE, 28,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(passwordField,
										GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)))
				.addGap(45).addComponent(btnDangNhap, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(btnQuenMatKhau, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(121, Short.MAX_VALUE)));
		pnlDangNhap.setLayout(gl_pnlDangNhap);

		txtTenTK.setText("NV01");
		passwordField.setText("123456");
		btnDangNhap.addActionListener(this);
		btnXemMatKhau.addActionListener(this);
		btnQuenMatKhau.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			String pass = new String(passwordField.getPassword());
			if (txtTenTK.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Tên tài khoản không được để trống !");
				txtTenTK.requestFocus();
			} else if (pass.equals("")) {
				JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống !");
				txtTenTK.requestFocus();
			} else {
				TaiKhoanDAO ds = null;
				try {
					ds = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<TaiKhoan> list = null;
				try {
					list = ds.getTaiKhoan(txtTenTK.getText(), pass);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (list == null) {
					JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc mật khẩu không đúng vui lòng thử lại !");
					txtTenTK.requestFocus();
				} else {
					for (TaiKhoan tk : list) {
						tenNV = tk.getNhanVien().getTenNV();
						boolean chucVu = tk.getNhanVien().isChucVu();
						trangThai = tk.getNhanVien().getTrangThai();
						if (chucVu == false) {
							this.chucVu = "Nhân viên";
						} else {
							this.chucVu = "Quản lý";
						}
						if (trangThai == 1) {
							continue;
						} else if (trangThai == 0){
							JOptionPane.showMessageDialog(null, "Tài khoản này đã bị vô hiệu hóa (do nhân viên nghỉ việc) !");
							txtTenTK.requestFocus();
							break;
						} else {
							JOptionPane.showMessageDialog(null, "Tài khoản này đã được bảo lưu, Vui lòng đăng nhập tài khoản quản lý để thay đổi !");
							txtTenTK.requestFocus();
							break;
						}
					}

					// Nếu là chức vụ là nhân viên thì mở giao diện role nhân viên ngược lại thì mở
					// role quản lý
					if (chucVu.equals("Nhân viên") && trangThai == 1) {
						otherUI2 = new uiMain2();
						otherUI2.layTenChucVu(tenNV, chucVu);
						otherUI2.frame.setVisible(true);
						try {
						    BufferedImage image = ImageIO.read(getClass().getResource("/icon/logoEntertaiment.png"));
						    otherUI2.frame.setIconImage(image);
						} catch (IOException e1) {
						    e1.printStackTrace();
						}
						dispose();
					} else if (chucVu.equals("Quản lý") && trangThai == 1){
						otherUI = new uiMain();
						otherUI.layTenChucVu(tenNV, chucVu);
						otherUI.frame.setVisible(true);
						try {
						    BufferedImage image = ImageIO.read(getClass().getResource("/icon/logoEntertaiment.png"));
						    otherUI.frame.setIconImage(image);
						} catch (IOException e1) {
						    e1.printStackTrace();
						}
						dispose();
					}
				}
			}
		} else if (o.equals(btnXemMatKhau)) {
			isPasswordVisible = !isPasswordVisible;
			updatePasswordFieldEchoChar();
		} else if (o.equals(btnQuenMatKhau)) {
			lmxn = new FormQuenMatKhau();
			lmxn.setVisible(true);
			dispose();
			lmxn.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					// Hiển thị lại giao diện Login khi FormLayMaXacNhan được đóng
					Login lg = new Login();
					lg.setVisible(true);
				}

				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	private void updatePasswordFieldEchoChar() {
		if (isPasswordVisible) {
			passwordField.setEchoChar((char) 0);
		} else {
			passwordField.setEchoChar('\u2022');
		}
	}
}