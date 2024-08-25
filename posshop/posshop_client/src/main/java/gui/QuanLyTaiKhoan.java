package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.CardLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.NhaCungCapDAO;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entities.TaiKhoan;


public class QuanLyTaiKhoan extends JPanel implements ActionListener, MouseListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4055170345382380015L;
	private JTextField txtTimKiem;
	private JTextField txtTenTK;
	private JTextField txtMatKhau;
	private JTextField txtTenNV;
	private JTextField txtChucVu;
	private JTable tblTaiKhoan;
	private JButton btnDoiMK;
	private Registry registry;

	/**
	 * Create the panel.
	 */
	public QuanLyTaiKhoan() {
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uiTaiKhoan();
		upDataTaiKhoan();
	}
	private void uiTaiKhoan() {
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(934, 685));
		setLayout(new CardLayout(0, 0));
		
		JPanel pnlTaiKhoan = new JPanel();
		add(pnlTaiKhoan, "name_35496697360300");
		pnlTaiKhoan.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "T\u00E0i Kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 912, 237);
		pnlTaiKhoan.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tìm Kiếm Tài Khoản : ");
		lblNewLabel.setBounds(29, 22, 146, 14);
		panel.add(lblNewLabel);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(185, 19, 421, 20);
		panel.add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		btnDoiMK = new JButton("Đổi Tài Khoản");
		btnDoiMK.setBackground(new Color(152, 251, 152));
		btnDoiMK.setIcon(new ImageIcon(QuanLyTaiKhoan.class.getResource("/icon/refesh.png")));
		btnDoiMK.setBounds(703, 18, 154, 29);
		panel.add(btnDoiMK);
		
		JLabel lblTenTaiKhoan = new JLabel("Tên Tài Khoản:");
		lblTenTaiKhoan.setBounds(29, 75, 101, 14);
		panel.add(lblTenTaiKhoan);
		
		txtTenTK = new JTextField();
		txtTenTK.setEditable(false);
		txtTenTK.setColumns(10);
		txtTenTK.setBounds(29, 100, 379, 20);
		panel.add(txtTenTK);
		
		JLabel lblMtKhu = new JLabel("Mật Khẩu");
		lblMtKhu.setBounds(466, 75, 101, 14);
		panel.add(lblMtKhu);
		
		txtMatKhau = new JTextField();
		txtMatKhau.setColumns(10);
		txtMatKhau.setBounds(466, 100, 379, 20);
		panel.add(txtMatKhau);
		
		JLabel lblTnNhnVin = new JLabel("Tên Nhân Viên:");
		lblTnNhnVin.setBounds(29, 147, 101, 14);
		panel.add(lblTnNhnVin);
		
		txtTenNV = new JTextField();
		txtTenNV.setEditable(false);
		txtTenNV.setColumns(10);
		txtTenNV.setBounds(29, 172, 379, 20);
		panel.add(txtTenNV);
		
		JLabel lblChcV = new JLabel("chức Vụ:");
		lblChcV.setBounds(466, 147, 101, 14);
		panel.add(lblChcV);
		
		txtChucVu = new JTextField();
		txtChucVu.setEditable(false);
		txtChucVu.setColumns(10);
		txtChucVu.setBounds(466, 172, 191, 20);
		panel.add(txtChucVu);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Danh S\u00E1ch T\u00E0i Kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 259, 912, 413);
		pnlTaiKhoan.add(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "name_38441986007700");
		
		tblTaiKhoan = new JTable();
		tblTaiKhoan.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"T\u00EAn T\u00E0i Kho\u1EA3n", "M\u1EADt Kh\u1EA9u", "T\u00EAn Nh\u00E2n Vi\u00EAn", "Ch\u1EE9c V\u1EE5"
			}
		));
		tblTaiKhoan.getColumnModel().getColumn(0).setPreferredWidth(115);
		tblTaiKhoan.getColumnModel().getColumn(2).setPreferredWidth(101);
		scrollPane.setViewportView(tblTaiKhoan);
		tblTaiKhoan.addMouseListener(this);
		btnDoiMK.addActionListener(this);
		
		txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upDataTimKiem();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upDataTimKiem();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				upDataTimKiem();
			}
		});
	}
	public void upDataTaiKhoan() {

		try {
			TaiKhoanDAO taiKhoanDAO = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
			List<TaiKhoan> list = taiKhoanDAO.getAllTaiKhoan();
			clearTable();
			DefaultTableModel dtm = (DefaultTableModel) tblTaiKhoan.getModel();
//			for(TaiKhoan tk : list) {
//				Object[] rowData = {tk.getTenTaiKhoan(), tk.getMatKhau(), tk.getNhanVien().getTenNV(), tk.getNhanVien().isChucVu() == false ? "Quản Lý" : "Nhân Viên"};
//				dtm.addRow(rowData);
//			}
			for(TaiKhoan tk : list) {
			    String tenNV = (tk.getNhanVien() != null) ? tk.getNhanVien().getTenNV() : "";
			    String chucVu = (tk.getNhanVien() != null && tk.getNhanVien().isChucVu() == false) ? "Quản Lý" : "Nhân Viên";
			    Object[] rowData = {tk.getTenTaiKhoan(), tk.getMatKhau(), tenNV, chucVu};
			    dtm.addRow(rowData);
			}

		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void upDataTimKiem() {
		if(txtTimKiem.equals("")) {
			upDataTaiKhoan();
		}else {
			try {
				TaiKhoanDAO taiKhoanDAO  = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
				List<TaiKhoan> list = taiKhoanDAO.getAllTaiKhoanTimKiem(txtTimKiem.getText());
				clearTable();
				DefaultTableModel dtm = (DefaultTableModel) tblTaiKhoan.getModel();
				for(TaiKhoan tk : list) {
					Object[] rowData = {tk.getTenTaiKhoan(), tk.getMatKhau(), tk.getNhanVien().getTenNV(), tk.getNhanVien().isChucVu() == false ? "Quản Lý" : "Nhân Viên"};
					dtm.addRow(rowData);
				}
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void clearTable(){
        DefaultTableModel dtm = (DefaultTableModel) tblTaiKhoan.getModel();
        dtm.setRowCount(0);
    }
	private boolean validData() {

		String tenTK = txtTenTK.getText().trim();
		String mk = txtMatKhau.getText().trim();

		if (tenTK.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản.");
	        return false;
	    }

	    if (mk.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Vui lòng không để trống mật khẩu");
	        return false;
	    }

	    return true;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		int row = tblTaiKhoan.getSelectedRow();
//		txtTenTK.setText(tblTaiKhoan.getValueAt(row, 0).toString());
//		txtMatKhau.setText(tblTaiKhoan.getValueAt(row, 1).toString());
////		txtTenNV.setText(tblTaiKhoan.getValueAt(row, 2).toString());
//		txtChucVu.setText(tblTaiKhoan.getValueAt(row, 3).toString());
		int row = tblTaiKhoan.getSelectedRow();
	    Object tenTK = tblTaiKhoan.getValueAt(row, 0);
	    Object matKhau = tblTaiKhoan.getValueAt(row, 1);
	    Object tenNV = tblTaiKhoan.getValueAt(row, 2);
	    Object chucVu = tblTaiKhoan.getValueAt(row, 3);

	    txtTenTK.setText(tenTK != null ? tenTK.toString() : "");
	    txtMatKhau.setText(matKhau != null ? matKhau.toString() : "");
	    txtTenNV.setText(tenNV != null ? tenNV.toString() : "");
	    txtChucVu.setText(chucVu != null ? chucVu.toString() : "");
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
		if(o.equals(btnDoiMK)) {
			if(validData()== true) {
				int choose = JOptionPane.showConfirmDialog(null,
						"Bạn có chắc muốn đổi mật khẩu không ?", "Cảnh Báo !!",
						JOptionPane.YES_NO_OPTION);
				System.out.println(choose);
				if(choose == 0) {
					try {
						TaiKhoanDAO taiKhoanDAO = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
						String tenTK = txtTenTK.getText().trim();
						String mk = txtMatKhau.getText().trim();
						TaiKhoan tk = new TaiKhoan(tenTK, mk);
						taiKhoanDAO.updateTaiKhoan(tk);
						upDataTaiKhoan();
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
