package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import dao.KhachHangDAO;
import dao.NhaCungCapDAO;
import entities.KhachHang;

import javax.swing.JTabbedPane;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

public class QuanLyKhachHang extends JPanel implements ActionListener, MouseListener {
	private JTextField txtmaKH;
	private JTextField txttenKh;
	private JTextField txtsdt;
	private JTextField txtemail;
	private JTextField txtTimkiem;
	private JTable tblKH;
	private JRadioButton rdbgtinh;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnLuu;
	private JButton btnHuy;
	private int trangthainut;
	private Registry registry;
	/**
	 * Create the panel.
	 */
	public QuanLyKhachHang() {
		
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uiKhachHang();
		upLoadDataKH();
		
	}
	private void uiKhachHang() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(932, 685));
		JPanel pnThietlapTT = new JPanel();
		pnThietlapTT.setLayout(null);
		pnThietlapTT.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Thi\u1EBFt L\u1EADp Th\u00F4ng Tin Kh\u00E1ch H\u00E0ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnThietlapTT.setBackground(Color.WHITE);
		
		JLabel lbmakh = new JLabel("Mã Khách Hàng:");
		lbmakh.setFont(new Font("Arial", Font.BOLD, 12));
		lbmakh.setBounds(16, 30, 97, 14);
		pnThietlapTT.add(lbmakh);
		
		txtmaKH = new JTextField();
		txtmaKH.setBackground(new Color(255, 255, 255));
		txtmaKH.setEditable(false);
		txtmaKH.setColumns(10);
		txtmaKH.setBounds(133, 27, 255, 20);
		pnThietlapTT.add(txtmaKH);
		
		JLabel lblTnKhchHng = new JLabel("Tên Khách Hàng:");
		lblTnKhchHng.setFont(new Font("Arial", Font.BOLD, 12));
		lblTnKhchHng.setBounds(16, 64, 107, 14);
		pnThietlapTT.add(lblTnKhchHng);
		
		txttenKh = new JTextField();
		txttenKh.setBackground(new Color(255, 255, 255));
		txttenKh.setEditable(false);
		txttenKh.setColumns(10);
		txttenKh.setBounds(133, 61, 320, 20);
		pnThietlapTT.add(txttenKh);
		
		JLabel lblgt = new JLabel("Giới Tính:");
		lblgt.setFont(new Font("Arial", Font.BOLD, 12));
		lblgt.setBounds(16, 92, 89, 14);
		pnThietlapTT.add(lblgt);
		
		JLabel lblsdt = new JLabel("Số Điện Thoại:");
		lblsdt.setFont(new Font("Arial", Font.BOLD, 12));
		lblsdt.setBounds(16, 123, 89, 14);
		pnThietlapTT.add(lblsdt);
		
		txtsdt = new JTextField();
		txtsdt.setBackground(new Color(255, 255, 255));
		txtsdt.setFont(new Font("Arial", Font.PLAIN, 12));
		txtsdt.setEditable(false);
		txtsdt.setColumns(10);
		txtsdt.setBounds(133, 120, 200, 20);
		pnThietlapTT.add(txtsdt);
		
		JLabel lbmail = new JLabel("Email:");
		lbmail.setFont(new Font("Arial", Font.BOLD, 12));
		lbmail.setBounds(390, 92, 89, 14);
		pnThietlapTT.add(lbmail);
		
		txtemail = new JTextField();
		txtemail.setBackground(new Color(255, 255, 255));
		txtemail.setFont(new Font("Arial", Font.PLAIN, 12));
		txtemail.setEditable(false);
		txtemail.setColumns(10);
		txtemail.setBounds(390, 120, 310, 20);
		pnThietlapTT.add(txtemail);
		
		rdbgtinh = new JRadioButton("Nam");
		rdbgtinh.setBackground(new Color(255, 255, 255));
		rdbgtinh.setSelected(true);
		rdbgtinh.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbgtinh.setBounds(133, 88, 99, 23);
		pnThietlapTT.add(rdbgtinh);
		
		JPanel pnttinKH = new JPanel();
		pnttinKH.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Th\u00F4ng Tin Kh\u00E1ch H\u00E0ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnttinKH.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnttinKH, GroupLayout.PREFERRED_SIZE, 910, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnThietlapTT, GroupLayout.PREFERRED_SIZE, 910, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(pnThietlapTT, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnttinKH, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(16, 37, 884, 448);
		
		JPanel pntTinCN = new JPanel();
		pntTinCN.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Thông Tin Cá Nhân", null, pntTinCN, null);
		
		JLabel lblTim = new JLabel("Tìm Kiếm:");
		lblTim.setBounds(10, 14, 65, 14);
		lblTim.setFont(new Font("Arial", Font.BOLD, 12));
		
		txtTimkiem = new JTextField();
		txtTimkiem.setBounds(79, 11, 801, 20);
		txtTimkiem.setBackground(new Color(255, 255, 255));
		txtTimkiem.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTimkiem.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 859, 366);
		
		tblKH = new JTable();
		tblKH.setFont(new Font("Arial", Font.PLAIN, 12));
		tblKH.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"M\u00E3 KH", "T\u00EAn Kh\u00E1ch H\u00E0ng", "S\u0110T", "Email ", "Gi\u1EDBi T\u00EDnh"
			}
		));
		tblKH.getColumnModel().getColumn(1).setPreferredWidth(118);
		pnttinKH.setLayout(null);
		scrollPane.setViewportView(tblKH);
		pntTinCN.setLayout(null);
		pntTinCN.add(lblTim);
		pntTinCN.add(txtTimkiem);
		pntTinCN.add(scrollPane);
		pnttinKH.add(tabbedPane);
		
		btnThem = new JButton("Thêm");
		btnThem.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnThem.setIcon(new ImageIcon(QuanLyKhachHang.class.getResource("/icon/add.png")));
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThem.setFont(new Font("Arial", Font.BOLD, 12));
		btnThem.setBackground(new Color(0, 0, 255));
		btnThem.setBounds(540, 30, 98, 37);
		pnThietlapTT.add(btnThem);
		
		btnSua = new JButton("Sửa");
		btnSua.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnSua.setIcon(new ImageIcon(QuanLyKhachHang.class.getResource("/icon/sua.png")));
		btnSua.setFont(new Font("Arial", Font.BOLD, 12));
		btnSua.setBackground(new Color(255, 255, 0));
		btnSua.setBounds(660, 30, 97, 37);
		pnThietlapTT.add(btnSua);
		
		btnLuu = new JButton("Lưu");
		btnLuu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnLuu.setForeground(new Color(255, 255, 255));
		btnLuu.setEnabled(false);
		btnLuu.setIcon(new ImageIcon(QuanLyKhachHang.class.getResource("/icon/save.png")));
		btnLuu.setFont(new Font("Arial", Font.BOLD, 12));
		btnLuu.setBackground(new Color(255, 128, 64));
		btnLuu.setBounds(780, 30, 97, 37);
		pnThietlapTT.add(btnLuu);
		
		btnHuy = new JButton("Hủy");
		btnHuy.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnHuy.setEnabled(false);
		btnHuy.setIcon(new ImageIcon(QuanLyKhachHang.class.getResource("/icon/x.png")));
		btnHuy.setFont(new Font("Arial", Font.BOLD, 12));
		btnHuy.setBackground(new Color(255, 0, 0));
		btnHuy.setBounds(780, 88, 97, 37);
		pnThietlapTT.add(btnHuy);
		setLayout(groupLayout);
		tblKH.addMouseListener(this);
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnHuy.addActionListener(this);
		btnLuu.addActionListener(this);
		txtTimkiem.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upLoadDataKHTimKiem();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upLoadDataKHTimKiem();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upLoadDataKHTimKiem();
			}
		});
	}
	private void clearTableDSSP() {
        DefaultTableModel dtm = (DefaultTableModel) tblKH.getModel();
        dtm.setRowCount(0);
    }
	private void upLoadDataKHTimKiem() {
		if(txtTimkiem.equals("")) {
			upLoadDataKH();
		}else {
			try {
				KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
				clearTableDSSP();
				DefaultTableModel dtm = (DefaultTableModel) tblKH.getModel();
				List<KhachHang> listkh = khachHangDAO.timkh(txtTimkiem.getText().trim());
				for(KhachHang kh : listkh) {
					String mail = "";
					if(kh.getEmail() == null) {
						mail = "";
					}else {
						mail = kh.getEmail();
					}
					Object[] rowdata = {kh.getMaKH(),kh.getTenKH(),kh.getSDT(), mail,kh.isGioiTinh() == true ? "Nam":"Nữ"};
					dtm.addRow(rowdata);
				}
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void upLoadDataKH() {
		try {
			KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
			clearTableDSSP();
			DefaultTableModel dtm = (DefaultTableModel) tblKH.getModel();
			List<KhachHang> listkh = khachHangDAO.getAllKhachHang();
			for(KhachHang kh : listkh) {
				String mail = "";
				if(kh.getEmail() == null) {
					mail = "";
				}else {
					mail = kh.getEmail();
				}
				Object[] rowdata = {kh.getMaKH(),kh.getTenKH(),kh.getSDT(), mail,kh.isGioiTinh() == true ? "Nam":"Nữ"};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tblKH.getSelectedRow();
		txtmaKH.setText(tblKH.getValueAt(row, 0).toString());
		txttenKh.setText(tblKH.getValueAt(row, 1).toString());
		txtsdt.setText(tblKH.getValueAt(row, 2).toString());
		if(tblKH.getValueAt(row, 3).toString().equals("")) {
			txtemail.setText("");
		}else {
			txtemail.setText(tblKH.getValueAt(row, 3).toString());
		}
		
		rdbgtinh.setSelected(tblKH.getValueAt(row, 4).toString().equals("Nam") ? true : false);
//		.setText(tblKhachHang.getValueAt(row, 3).toString());
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
		
		if(o.equals(btnThem)) {
			
			trangthainut = 1;
			txtmaKH.setText("");
			txttenKh.setText("");
			txtsdt.setText("");
			txtemail.setText("");
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
			KhachHang kh = new KhachHang();
			txtmaKH.setText(getAutoIDKH());
//			txtmaKH.setEditable(true);
			txttenKh.setEditable(true);
			txtsdt.setEditable(true);
			txtemail.setEditable(true);
		}
		if(o.equals(btnSua)) {
			trangthainut =2;
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
			txttenKh.setEditable(true);
			txtsdt.setEditable(true);
			txtemail.setEditable(true);
		}
		if(o.equals(btnLuu)) {
			try {
				if(trangthainut == 1) {
					String makh = txtmaKH.getText();
					String tenkh = txttenKh.getText();
					String sdt = txtsdt.getText();
					String email = txtemail.getText();
					boolean gt = rdbgtinh.isSelected();
					KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
//				KhachHang kh = new KhachHang(makh, tenkh, email, sdt, gt);
					KhachHang kh = new KhachHang(makh, tenkh, gt, sdt, email);
					khachHangDAO.addKhachHang(kh);
					upLoadDataKH();
				}
				if(trangthainut == 2) {
					String makh = txtmaKH.getText();
					String tenkh = txttenKh.getText();
					String sdt = txtsdt.getText();
					String email = txtemail.getText();
					boolean gt = rdbgtinh.isSelected();
					KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
					KhachHang kh = new KhachHang(makh, tenkh, gt, sdt, email);
					khachHangDAO.updateKhachHang(kh);
					upLoadDataKH();
				}
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnLuu.setEnabled(false);
				btnHuy.setEnabled(false);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(o.equals(btnHuy)) {
			txtmaKH.setText("");
			txttenKh.setText("");
			txtsdt.setText("");
			txtemail.setText("");
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		}
	}
	private String getAutoIDKH() {
		// TODO Auto-generated method stub
		try {
			KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
			String idPrefix = "KH";
			int length = khachHangDAO.getAllKhachHang().size();
			String finalId = idPrefix + String.format("%02d", length + 1);
			return finalId;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
