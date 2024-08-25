package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.TaiKhoanDAO;
import main.Login;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Timer;

public class FormQuenMatKhau extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JLabel lblNhapTenDangNhap, lblXacNhanMatKhau;
	private JTextField txtTenTaiKhoan;
	private JPasswordField pswMatKhau, pswXacNhanMatKhau;
	private JButton btnAction, btnGuiLaiMaXacNhan, btnQuayLai, btnHienThiMatKhau, btnHienThiXacNhanMatKhau;
	private String tenTK, email, maXacNhan, maXacNhanLai;
	private Timer timer;
	private int seconds;
	private boolean btnGuiLaiMaXacNhanClick = false;
	private boolean isPasswordVisible = false;
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

	public FormQuenMatKhau() {
		
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initComponents();
	}

	/**
	 * Create the frame.
	 */
	public void initComponents() {
		setBounds(100, 100, 825, 370);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		JPanel panelLoGo = new JPanel();
		panelLoGo.setBounds(0, 0, 328, 341);
		panelLoGo.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelLoGo.setBackground(new Color(31, 139, 31));

		JPanel panelForm = new JPanel();
		panelForm.setBounds(327, 0, 487, 333);
		panelForm.setBackground(new Color(144, 238, 144));

		lblNhapTenDangNhap = new JLabel("Nhập tên đăng nhập:");
		lblNhapTenDangNhap.setBounds(58, 65, 386, 18);
		lblNhapTenDangNhap.setForeground(Color.BLACK);
		lblNhapTenDangNhap.setFont(new Font("Arial", Font.BOLD, 15));
		lblNhapTenDangNhap.setBackground(Color.BLACK);

		txtTenTaiKhoan = new JTextField();
		txtTenTaiKhoan.setBounds(58, 93, 360, 25);
		txtTenTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTenTaiKhoan.setColumns(10);

		pswMatKhau = new JPasswordField();
		pswMatKhau.setBounds(58, 93, 360, 25);
		pswMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
		pswMatKhau.setColumns(10);
		pswMatKhau.setVisible(false);

		lblXacNhanMatKhau = new JLabel("Xác nhận mật khẩu:");
		lblXacNhanMatKhau.setBounds(58, 136, 147, 18);
		lblXacNhanMatKhau.setForeground(Color.BLACK);
		lblXacNhanMatKhau.setFont(new Font("Arial", Font.BOLD, 15));
		lblXacNhanMatKhau.setBackground(Color.BLACK);
		lblXacNhanMatKhau.setVisible(false);

		pswXacNhanMatKhau = new JPasswordField();
		pswXacNhanMatKhau.setBounds(58, 172, 360, 25);
		pswXacNhanMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
		pswXacNhanMatKhau.setColumns(10);
		pswXacNhanMatKhau.setVisible(false);

		btnAction = new JButton("LẤY MÃ XÁC NHẬN");
		btnAction.setBounds(58, 136, 364, 52);
		btnAction.setIcon(new ImageIcon(FormQuenMatKhau.class.getResource("/icon/login.png")));
		btnAction.setForeground(Color.WHITE);
		btnAction.setFont(new Font("Arial", Font.BOLD, 13));
		btnAction.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		btnAction.setBackground(Color.RED);
		// Sự kiện nhấn phím EnTer btnAction
		btnAction.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		btnAction.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAction.doClick();
			}
		});

		btnQuayLai = new JButton("Quay lại");
		btnQuayLai.setBounds(312, 206, 110, 29);
		btnQuayLai.setIcon(new ImageIcon(FormQuenMatKhau.class.getResource("/icon/refesh.png")));
		btnQuayLai.setForeground(new Color(31, 139, 31));
		btnQuayLai.setFont(new Font("Arial", Font.BOLD, 12));
		btnQuayLai.setBackground(new Color(255, 255, 255));
		// Sự kiện phím tắt cho btnQuayLai
		btnQuayLai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "CtrlZ");
		btnQuayLai.getActionMap().put("CtrlZ", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQuayLai.doClick();
			}
		});

		btnGuiLaiMaXacNhan = new JButton("Gửi lại mã (40)");
		btnGuiLaiMaXacNhan.setBounds(58, 206, 122, 29);
		btnGuiLaiMaXacNhan.setVisible(false);
		btnGuiLaiMaXacNhan.setForeground(new Color(31, 139, 31));
		btnGuiLaiMaXacNhan.setFont(new Font("Arial", Font.BOLD, 12));
		btnGuiLaiMaXacNhan.setBackground(Color.WHITE);
		btnGuiLaiMaXacNhan.setEnabled(false);

		btnHienThiMatKhau = new JButton("");
		btnHienThiMatKhau.setBorder(new LineBorder(new Color(144, 238, 144), 2));
		btnHienThiMatKhau.setBackground(new Color(144, 238, 144));
		btnHienThiMatKhau.setIcon(new ImageIcon(FormQuenMatKhau.class.getResource("/icon/conmat.png")));
		btnHienThiMatKhau.setBounds(428, 87, 46, 37);
		btnHienThiMatKhau.setVisible(false);

		btnHienThiXacNhanMatKhau = new JButton("");
		btnHienThiXacNhanMatKhau.setIcon(new ImageIcon(FormQuenMatKhau.class.getResource("/icon/conmat.png")));
		btnHienThiXacNhanMatKhau.setBorder(new LineBorder(new Color(144, 238, 144), 2));
		btnHienThiXacNhanMatKhau.setBackground(new Color(144, 238, 144));
		btnHienThiXacNhanMatKhau.setBounds(428, 166, 46, 37);
		btnHienThiXacNhanMatKhau.setVisible(false);

		mainPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FormQuenMatKhau.class.getResource("/icon/logoshop3.png")));
		GroupLayout gl_panelLoGo = new GroupLayout(panelLoGo);
		gl_panelLoGo.setHorizontalGroup(gl_panelLoGo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLoGo.createSequentialGroup().addContainerGap()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(14, Short.MAX_VALUE)));
		gl_panelLoGo.setVerticalGroup(gl_panelLoGo.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelLoGo.createSequentialGroup().addContainerGap(84, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addGap(82)));
		panelLoGo.setLayout(gl_panelLoGo);
		mainPanel.add(panelLoGo);
		mainPanel.add(panelForm);
		panelForm.setLayout(null);
		panelForm.add(lblNhapTenDangNhap);
		panelForm.add(txtTenTaiKhoan);
		panelForm.add(pswMatKhau);
		panelForm.add(lblXacNhanMatKhau);
		panelForm.add(pswXacNhanMatKhau);
		panelForm.add(btnGuiLaiMaXacNhan);
		panelForm.add(btnQuayLai);
		panelForm.add(btnAction);
		panelForm.add(btnHienThiMatKhau);
		panelForm.add(btnHienThiXacNhanMatKhau);

		btnAction.addActionListener(this);
		btnQuayLai.addActionListener(this);
		btnGuiLaiMaXacNhan.addActionListener(this);
		btnHienThiMatKhau.addActionListener(this);
		btnHienThiXacNhanMatKhau.addActionListener(this);
		String ma = maXacNhan();
		maXacNhan = ma;
	}

	@Override
	public void actionPerformed(ActionEvent q) {
		Object o = q.getSource();
		if (o.equals(btnGuiLaiMaXacNhan)) {
			btnGuiLaiMaXacNhanClick = true;
			maXacNhanLai = maXacNhan();
			guiMaXacNhan(email, maXacNhanLai);
			JOptionPane.showMessageDialog(null, "Đã gửi lại mã xác nhận !");
			btnGuiLaiMaXacNhan.setEnabled(false);
			seconds = 25;
			demNguoc();
			txtTenTaiKhoan.requestFocus();
		} else if (o.equals(btnAction)) {
			if (btnAction.getText().equals("LẤY MÃ XÁC NHẬN")) {
				tenTK = txtTenTaiKhoan.getText();
				TaiKhoanDAO ds = null;
				try {
					ds = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
				} catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					email = ds.getEmailTheoTenTaiKhoan(tenTK);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (tenTK.equals("")) {
					JOptionPane.showMessageDialog(null, "Tên tài khoản không được để rỗng !");
					txtTenTaiKhoan.requestFocus();
				} else {
					if (email == null) {
						JOptionPane.showMessageDialog(null, "Tên tài khoản không tồn tại, Vui lòng thử lại !");
						txtTenTaiKhoan.requestFocus();
					} else {
						txtTenTaiKhoan.setText("");
						btnAction.setText("XÁC NHẬN");
						btnAction.setEnabled(true);
						lblNhapTenDangNhap.setText("Nhập mã xác nhận cho email " + email);
						guiMaXacNhan(email, maXacNhan);
						JOptionPane.showMessageDialog(null, "Mã xác nhận đã được gửi tới " + email);
						btnGuiLaiMaXacNhan.setVisible(true);
						btnGuiLaiMaXacNhan.setEnabled(false);
						seconds = 25;
						demNguoc();
						txtTenTaiKhoan.requestFocus();
					}
				}
			} else if (btnAction.getText().equals("XÁC NHẬN")) {
				if (!btnGuiLaiMaXacNhanClick) {
					if (!txtTenTaiKhoan.getText().equals(maXacNhan)) {
						JOptionPane.showMessageDialog(null, "Mã xác nhận không hợp lệ !");
						txtTenTaiKhoan.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "Xác nhận thành công !");
						txtTenTaiKhoan.setVisible(false);
						btnAction.setText("HOÀN TẤT");
						lblNhapTenDangNhap.setText("Mật khẩu mới");
						btnGuiLaiMaXacNhan.setVisible(false);
						// Di chuyển các nút
						btnAction.setBounds(58, 223, 364, 52);
						btnQuayLai.setBounds(312, 293, 110, 29);
						// Hiển thị lablel và passWordField xác nhận mật khẩu
						lblXacNhanMatKhau.setVisible(true);
						pswMatKhau.setVisible(true);
						pswXacNhanMatKhau.setVisible(true);
						btnHienThiMatKhau.setVisible(true);
						btnHienThiXacNhanMatKhau.setVisible(true);
						pswMatKhau.requestFocus();
					}
				} else {
					if (!txtTenTaiKhoan.getText().equals(maXacNhanLai)) {
						JOptionPane.showMessageDialog(null, "Mã xác nhận không hợp lệ !");
						txtTenTaiKhoan.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "Xác nhận thành công !");
						txtTenTaiKhoan.setVisible(false);
						;
						btnAction.setText("HOÀN TẤT");
						lblNhapTenDangNhap.setText("Mật khẩu mới");
						btnGuiLaiMaXacNhan.setVisible(false);
						// Di chuyển các nút
						btnAction.setBounds(58, 223, 364, 52);
						btnQuayLai.setBounds(312, 293, 110, 29);
						// Hiển thị lablel và passWordField xác nhận mật khẩu
						lblXacNhanMatKhau.setVisible(true);
						pswMatKhau.setVisible(true);
						pswXacNhanMatKhau.setVisible(true);
						btnHienThiMatKhau.setVisible(true);
						btnHienThiXacNhanMatKhau.setVisible(true);
						pswMatKhau.requestFocus();
					}
				}

			} else {
				if (validData()) {
					String matKhau = new String(pswMatKhau.getPassword());
					TaiKhoanDAO ds = null;
					try {
						ds = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
					} catch (RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String tonTaiMatKhau = null;
					try {
						tonTaiMatKhau = ds.getTenTaiKhoanTheoMatKhau(matKhau);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (tonTaiMatKhau == null) {
						try {
							ds.updateMatKhau(matKhau, tenTK);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công !");
						Login lg = new Login();
						lg.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Mật khẩu này đã tồn tại, Vui lòng nhập mật khẩu khác !");
						pswMatKhau.requestFocus();
						pswXacNhanMatKhau.setText("");
					}
				}
			}
		} else if (o.equals(btnHienThiMatKhau)) {
			isPasswordVisible = !isPasswordVisible;
			hienThiMatKhau(pswMatKhau);
		} else if (o.equals(btnHienThiXacNhanMatKhau)) {
			isPasswordVisible = !isPasswordVisible;
			hienThiMatKhau(pswXacNhanMatKhau);
		} else if (o.equals(btnQuayLai)) {
			if (btnAction.getText().equals("XÁC NHẬN") || btnAction.getText().equals("HOÀN TẤT")) {
				lblNhapTenDangNhap.setText("Nhập tên đăng nhập:");
				txtTenTaiKhoan.setVisible(true);
				txtTenTaiKhoan.setText("");
				pswMatKhau.setVisible(false);
				lblXacNhanMatKhau.setVisible(false);
				pswXacNhanMatKhau.setVisible(false);
				btnGuiLaiMaXacNhan.setVisible(false);
				btnAction.setText("LẤY MÃ XÁC NHẬN");
				btnAction.setBounds(58, 136, 364, 52);
				btnQuayLai.setBounds(312, 206, 110, 29);
				txtTenTaiKhoan.requestFocus();
				seconds = 0;
			} else {
				Login lg = new Login();
				lg.setVisible(true);
				dispose();
			}
		}
	}

	// Thời gian đếm ngược để gửi lại mã
	private void demNguoc() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				seconds--;
				if (seconds >= 0) {
					btnGuiLaiMaXacNhan.setText("Gửi lại mã (" + seconds + ")");
				} else {
					timer.stop();
					btnGuiLaiMaXacNhan.setEnabled(true);
					btnGuiLaiMaXacNhan.setText("Gửi lại mã");
				}
			}
		});
		timer.start();
	}

	// Hàm sinh mã xác nhận ngẫu nhiên
	private String maXacNhan() {
		Random random = new Random();
		// Sinh ra một số ngẫu nhiên gồm 5 chữ số từ 0 đến 99999 (bao gồm cả 0 và 99999)
		int maXacNhan = random.nextInt(100000);
		// Định dạng số ngẫu nhiên thành chuỗi với 5 chữ số
		return String.format("%05d", maXacNhan);
	}

	// Hàm gửi mã cho email xác nhận
	private void guiMaXacNhan(String email, String maXacNhan) {
		// Email: posshop95@gmail.com
		// Pasword: awazzbudolknwanx
		String from = "posshop95@gmail.com"; // Người gửi
		String password = "gbrp adoe fcvq bnsh";

		// Cấu hình SMTP server và thông tin kết nối
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); // Phải đăng nhập
		props.put("mail.smtp.starttls.enable", "true"); // Sử dụng giao thức TSL
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
		props.put("mail.smtp.port", "587"); // TLS 587 (google) SSL 465

		// Tạo Authenticator (Trình xác thực) -> Đăng nhập vào gmail
		Authenticator auth = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(from, password);
			}

		};
		// Tạo đối tượng Session để kết nối đến SMTP server và đăng nhập
		Session session = Session.getInstance(props, auth);

		// Tạo đối tượng MimeMessage (để tạo tin nhắn)
		MimeMessage msg = new MimeMessage(session);
		try {
			InternetAddress senderAddress = new InternetAddress(from);
			msg.setFrom(senderAddress); // Gửi từ ...
			// Người nhận (bcc, cc, to)
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			msg.setSubject("Mã xác minh cho email khôi phục:" + maXacNhan); // Tiêu đề email
			msg.setSentDate(new Date()); // Ngày gửi
			// Nội dung email kiểu HTML
			String htmlContent = "<div style=\"width: 100%; display: flex; justify-content: center; align-items: center;\">\r\n"
					+ "  <div style=\"width: 450px; margin: 0 auto; border: 1px solid #ccc; border-radius: 5px; padding: 20px;\">\r\n"
					+ "    <img src=\"https://scontent.xx.fbcdn.net/v/t1.15752-9/370139880_914036113420812_8414365613681779412_n.png?stp=cp0_dst-png&_nc_cat=110&ccb=1-7&_nc_sid=510075&_nc_eui2=AeG0mEg37O5ZBdT9OsjPlmyzKU5YQod5xicpTlhCh3nGJySNlhZ4huHobIeNi_O5N7GK-2DfJtoEyjun-orfhr_t&_nc_ohc=Zj6QDGLfggYAX9NeCOq&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdQg20GiRL-YQP8IDzF5LE22_UtGJOfG8IG-ca5LuYGKhA&oe=65800E0B\" alt=\"logoPosShop\" style=\"display: block; margin: 0 auto;\">\r\n"
					+ "    <div style=\"text-align: center;\">\r\n"
					+ "      <h2 style=\"font-weight: 500; margin: 0; padding-bottom: 10px; border-bottom: 1px solid #ccc;\">Xác minh email của bạn</h2>\r\n"
					+ "      <p style=\"margin: 10px 0;\">Sử dụng mã này để tiếp tục việc thiết lập mật khẩu:</p>\r\n"
					+ "      <h1 style=\"margin: 0;\"><strong>" + maXacNhan + "</strong></h1>\r\n"
					+ "      <p style=\"margin: 10px 0;\">Mã này sẽ hết hạn sau 24 giờ.</p>\r\n"
					+ "      <p style=\"margin: 0;\">PosShop, trân trọng!</p>\r\n" + "</div>\r\n" + "</div>\r\n"
					+ "</div>";

			// Gửi mail
			msg.setContent(htmlContent, "text/html; charset=utf-8");
			// Gửi mail đi
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Kiểm tra Regex mật khẩu mới
	private boolean validData() {
		String matKhauMoi = new String(pswMatKhau.getPassword());
		String xacNhanMatKhau = new String(pswXacNhanMatKhau.getPassword());
		if (!(matKhauMoi.length() > 0)) {
			JOptionPane.showMessageDialog(null, "Mật khẩu không được bỏ trống !");
			pswMatKhau.requestFocus();
			return false;
		}
		if (!(matKhauMoi.matches("\\d+"))) {
			JOptionPane.showMessageDialog(null, "Mật khẩu phải là kí tự số !");
			pswMatKhau.requestFocus();
			return false;
		}
		if (!(matKhauMoi.matches("\\d{5,}"))) {
			JOptionPane.showMessageDialog(null, "Mật khẩu phải chứa ít nhất 5 kí tự số!");
			pswMatKhau.requestFocus();
			return false;
		}
		if (!(xacNhanMatKhau.equals(matKhauMoi))) {
			JOptionPane.showMessageDialog(null, "Không trùng khớp mật khẩu !");
			pswXacNhanMatKhau.requestFocus();
			return false;
		}
		return true;
	}

	// Hàm hiển thị mật khẩu
	private void hienThiMatKhau(JPasswordField pass) {
		if (isPasswordVisible) {
			pass.setEchoChar((char) 0);
		} else {
			pass.setEchoChar('\u2022');
		}
	}
}