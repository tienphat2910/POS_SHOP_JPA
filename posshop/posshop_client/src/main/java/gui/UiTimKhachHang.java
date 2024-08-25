package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.KhachHangDAO;
import entities.KhachHang;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class UiTimKhachHang extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblKhachHang;
	private JTextField txtTimKiemKH;
	private JTextField txtMaKH;
	private JTextField txtSDT;
	private JTextField txtTenKH;
	private JButton btnChonKH;
    private KhachHangSelectedListener callback;
	private Registry registry;
	private KhachHangDAO khachHangDAO;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UiTimKhachHang frame = new UiTimKhachHang();
		
	}

	/**
	 * Create the frame.
	 */
    private void clearTableDSSP() {
        DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
        dtm.setRowCount(0);
    }
	private void tblDanhSachSanPham() {
		try {
			khachHangDAO = (dao.KhachHangDAO) registry.lookup("KhachHangDAO");
			clearTableDSSP();
			DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
			 List<KhachHang> listkh = khachHangDAO.getAllKhachHang();
			
			for(KhachHang kh : listkh) {
				Object[] rowdata = {kh.getMaKH(),kh.getTenKH(),kh.getSDT(), kh.getEmail(),kh.isGioiTinh()};
				dtm.addRow(rowdata);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public UiTimKhachHang() {
		try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 493);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		
		JPanel pnlKhachHang = new JPanel();
		
		JLabel lblTim = new JLabel("Tìm kiếm Khách Hàng");
		lblTim.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtTimKiemKH = new JTextField();
		txtTimKiemKH.setColumns(10);
		
		btnChonKH = new JButton("Chọn");
		btnChonKH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String makh = txtMaKH.getText();
				String tenkh = txtTenKH.getText();
				khachHangDuocChon(makh, tenkh);
				dispose();
			}
		});
		btnChonKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChonKH.setBackground(new Color(211, 211, 211));
		
		JLabel lblNewLabel = new JLabel("mã khách hàng :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtMaKH = new JTextField();
		txtMaKH.setColumns(10);
		
		JLabel lblSinThoi = new JLabel("số điện thoại :");
		lblSinThoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		
		JLabel lblTnKhchHng = new JLabel("tên khách hàng :");
		lblTnKhchHng.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtTenKH = new JTextField();
		txtTenKH.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlKhachHang, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtMaKH, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTnKhchHng, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtTenKH)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblTim)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtTimKiemKH))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(169)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtSDT, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSinThoi, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))))
							.addGap(50)
							.addComponent(btnChonKH, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTim)
								.addComponent(txtTimKiemKH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(lblSinThoi)))
						.addComponent(btnChonKH, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMaKH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSDT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTnKhchHng)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtTenKH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(pnlKhachHang, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlKhachHang.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlKhachHang.add(scrollPane, "name_108405605451800");
		
		tblKhachHang = new JTable();
		tblKhachHang.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"maKH", "t\u00EAnKH", "SDT", "Email", "Gi\u1EDBi t\u00EDnh"
			}
		));
		scrollPane.setViewportView(tblKhachHang);
		contentPane.setLayout(gl_contentPane);
		tblDanhSachSanPham();
		
		tblKhachHang.addMouseListener(this);
	}
	public interface KhachHangSelectedListener {
        void onKhachHangSelected(String makh, String tenkh); // Điều chỉnh kiểu dữ liệu theo dữ liệu bạn muốn trả về
    }


    //... Các phương thức khác của giao diện

    // Gọi khi khách hàng được chọn
    private void khachHangDuocChon(String makh, String tenKh) {
        if (callback != null) {
            callback.onKhachHangSelected(makh,tenKh);
        }
    }

    // Thiết lập callback
    public void setKhachHangSelectedListener(KhachHangSelectedListener callback) {
        this.callback = callback;
    }
 // Trong sự kiện khi khách hàng được chọn

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = tblKhachHang.getSelectedRow();
		txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
		txtTenKH.setText(tblKhachHang.getValueAt(row, 1).toString());
		txtSDT.setText(tblKhachHang.getValueAt(row, 3).toString());
		
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
		
	}

}
