package gui;

//import dao.ChiTietHoaDonDAO;
//import dao.HoaDonDAO;
import dao.KichThuocDAO;
import dao.MauSacDAO;
import dao.PhanLoaiDAO;
import dao.SanPhamDAO;
import entities.KichThuoc;
import entities.MauSac;
import entities.PhanLoai;
import entities.SanPham;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Font;

public class TabThongKeSanPham extends JPanel {
private Registry registry;
//    private SanPhamDAO sanPham_DAO = new SanPhamDAO();
////    private ChiTietHoaDonDAO cthd_DAO = new ChiTietHoaDonDAO();
////    private HoaDonDAO hoaDon_DAO = new HoaDonDAO();
//    private KichThuocDAO kichThuoc_DAO = new KichThuocDAO();
//    private MauSacDAO mauSac_DAO = new MauSacDAO();
//    private PhanLoaiDAO phanLoai_DAO = new PhanLoaiDAO();
//	private SanPhamDAO sanPhamDAO;
	
   
    public TabThongKeSanPham() {
    	try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initComponents();
        designTable();
        tblDanhSachSanPham();
        khoiTaoGiaTri();
    }
    
    private void khoiTaoGiaTri(){
        try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			List<KichThuoc>listKichThuoc = kichThuocDAO.getAllKichThuoc();
			for(KichThuoc kt : listKichThuoc){
			    cb_KichThuoc.addItem(kt.getKichThuoc());
			}
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			List<MauSac>listMauSac = mauSacDAO.getAllMauSac();
			for(MauSac ms : listMauSac){
			    cb_MauSac.addItem(ms.getMauSac());
			}
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			List<PhanLoai>listPhanLoai = phanLoaiDAO.getAllPhanLoai();
			for(PhanLoai pl : listPhanLoai){
			    cb_PhanLoai.addItem(pl.getPhanLoai());
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void designTable() {
        tbl_DanhSachSanPham.getTableHeader().setFont(new Font("Arial", 0, 12));
        tbl_DanhSachSanPham.getTableHeader().setOpaque(false);
        tbl_DanhSachSanPham.getTableHeader().setBackground(new Color(144,238,144));
        tbl_DanhSachSanPham.getTableHeader().setForeground(Color.WHITE);
        tbl_DanhSachSanPham.setDefaultEditor(Object.class, null);
        tbl_DanhSachSanPham.setRowHeight(30);
   
    }
    
    private void clearTable(){
        DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
        dtm.setRowCount(0);
    }
    
    private void tblDanhSachSanPham(){
        clearTable();
        
        try {
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			List<SanPham> listsp = sanPhamDAO.getAllSanPhamTheoThuocTinh("", "", phanLoai, mauSac, kichThuoc);

			for(SanPham sp : listsp){

			    Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
			                        sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format( sp.getGiaNhap()) };
			    dtm.addRow(rowData);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
    }
    private void tblDssp() {
    	try {
			clearTable();
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			List<SanPham> listSanPham = sanPhamDAO.getAllSanPhamTheoThuocTinh("", "", phanLoai, mauSac, kichThuoc);
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
			
			for(SanPham sp : listSanPham) {
				Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
									sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format(sp.getGiaNhap())};
				dtm.addRow(rowData);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }
    
    private void tblDanhSachSanPhamVuotDinhMuc(int soLuongDinhMuc){
        try {
			clearTable();
			
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
//        ArrayList<SanPham> listSanPham = cthd_DAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc, thang, nam);
			List<SanPham> listSanPham = sanPhamDAO.getAllSanPhamVuotDinhMuc(soLuongDinhMuc);
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
			
			for(SanPham sp : listSanPham){
    
			    Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
			                        sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format(sp.getGiaNhap()) };
			    dtm.addRow(rowData);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    private void tblDanhSachSanPhamDuoiDinhMuc(int soLuongDinhMuc){
        try {
			clearTable();
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
//        ArrayList<SanPham> listSanPham = cthd_DAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc, thang, nam);
			List<SanPham> listSanPham = sanPhamDAO.getAllSanPhamDuoiDinhMuc(soLuongDinhMuc);
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();

			for(SanPham sp : listSanPham){
			   
			    Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
			                        sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format(sp.getGiaNhap()) };
			    dtm.addRow(rowData);
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
        

    private int duLieuDinhMuc(){
        int soLuong = 0;
        try {
            soLuong = Integer.parseInt(txt_DinhMuc.getText());
            if(soLuong < 0){
            	lblthongbaonhap.setText("");
                JOptionPane.showMessageDialog(null, "Định mức phải là số lớn hơn 0");
                return -1;
            }
            if(txt_DinhMuc.getText() != null) {
            	lblthongbaonhap.setText("");
            }

        } catch (Exception e) {
        	lblthongbaonhap.setForeground(Color.RED);
        	lblthongbaonhap.setText("nhập số !!!");
//            JOptionPane.showMessageDialog(null, "Định mức phải là 1 con số");
            return -1;
        }
        return soLuong;
    }


    private void initComponents() {
		setPreferredSize(new Dimension(932, 667));
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBackground(new Color(255, 255, 255));
        lbl_1 = new JLabel();
        btng = new ButtonGroup();
        scr_1 = new JScrollPane();
        tbl_DanhSachSanPham = new JTable();
        tbl_DanhSachSanPham.setBackground(new Color(255, 255, 255));
        lbl_MauSac = new JLabel();
        cb_MauSac = new JComboBox<>();
        cb_MauSac.setBackground(new Color(255, 255, 255));
        lbl_PhanLoai = new JLabel();
        cb_PhanLoai = new JComboBox<>();
        cb_PhanLoai.setBackground(new Color(255, 255, 255));
        lbl_KichThuoc = new javax.swing.JLabel();
        cb_KichThuoc = new JComboBox<>();
        cb_KichThuoc.setBackground(new Color(255, 255, 255));
        pnl_4 = new JPanel();
        lbl_2 = new JLabel();
        lbl_2.setBounds(25, 66, 77, 31);
        lbl_3 = new JLabel();
        lbl_3.setBounds(160, 11, 138, 44);
        txt_DinhMuc = new JTextField();
        txt_DinhMuc.setBounds(106, 72, 172, 18);
        txt_DinhMuc.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        rbtn_duoiDinhMuc = new JRadioButton();
        rbtn_duoiDinhMuc.setBounds(25, 115, 129, 23);
        rbtn_vuocDinhMuc = new JRadioButton();
        rbtn_vuocDinhMuc.setBounds(25, 141, 129, 23);
        rbtn_tatCa = new JRadioButton();
        rbtn_tatCa.setBounds(313, 70, 115, 23);
        btn_topspbancham = new JButton();
        btn_topspbancham.setBackground(new Color(255, 255, 255));
        btn_topspbancham.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btn_topspbanchay = new JButton();
        btn_topspbanchay.setBackground(new Color(255, 255, 255));
        btn_topspbanchay.setBorder(new LineBorder(new Color(0, 0, 0), 2));

        lbl_1.setText("lbl_1");

        setBackground(new Color(255, 255, 255));
        

        tbl_DanhSachSanPham.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Phân loại", "Kích cỡ", "Màu sắc", "Số lượng tồn kho", "Giá bán"
            }
        ));
        tbl_DanhSachSanPham.setRowHeight(30);
        scr_1.setViewportView(tbl_DanhSachSanPham);

        lbl_MauSac.setFont(new Font("Arial", 0, 12)); // NOI18N
        lbl_MauSac.setText("Màu sắc:");

        cb_MauSac.setFont(new Font("Arial", Font.PLAIN, 12)); // NOI18N
        cb_MauSac.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_MauSac.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_MauSacItemStateChanged(evt);
            }
        });

        lbl_PhanLoai.setFont(new Font("Arial", 0, 12)); // NOI18N
        lbl_PhanLoai.setText("Phân loại:");

        cb_PhanLoai.setFont(new Font("Arial", Font.PLAIN, 12)); // NOI18N
        cb_PhanLoai.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_PhanLoai.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_PhanLoaiItemStateChanged(evt);
            }
        });

        lbl_KichThuoc.setFont(new Font("Arial", 0, 12)); // NOI18N
        lbl_KichThuoc.setText("Kích thước:");

        cb_KichThuoc.setFont(new Font("Arial", Font.PLAIN, 12)); // NOI18N
        cb_KichThuoc.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_KichThuoc.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_KichThuocItemStateChanged(evt);
            }
        });
        cb_KichThuoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cb_KichThuocActionPerformed(evt);
            }
        });

        pnl_4.setBackground(new Color(144, 238, 144));

        lbl_2.setFont(new Font("Arial", 0, 12)); // NOI18N
        lbl_2.setForeground(new Color(255, 255, 255));
        lbl_2.setText("Định mức tồn:");

        lbl_3.setFont(new Font("Arial", 1, 28)); // NOI18N
        lbl_3.setForeground(new Color(255, 255, 255));
        lbl_3.setText("Tồn kho");

        txt_DinhMuc.setFont(new Font("Arial", 0, 12)); // NOI18N
        txt_DinhMuc.addInputMethodListener(new InputMethodListener() {
        	
            public void caretPositionChanged(InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(InputMethodEvent evt) {
                txt_DinhMucInputMethodTextChanged(evt);
            }
        });
        txt_DinhMuc.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
            	
                txt_DinhMucKeyReleased(evt);
            }
        });

        rbtn_duoiDinhMuc.setBackground(new Color(144, 238, 144));
        btng.add(rbtn_duoiDinhMuc);
        rbtn_duoiDinhMuc.setFont(new Font("Arial", 0, 12)); // NOI18N
        rbtn_duoiDinhMuc.setForeground(new Color(255, 255, 255));
        rbtn_duoiDinhMuc.setText("Dưới định mức tồn");
        rbtn_duoiDinhMuc.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                rbtn_duoiDinhMucItemStateChanged(evt);
            }
        });

        rbtn_vuocDinhMuc.setBackground(new Color(144, 238, 144));
        btng.add(rbtn_vuocDinhMuc);
        rbtn_vuocDinhMuc.setFont(new Font("Arial", 0, 12)); // NOI18N
        rbtn_vuocDinhMuc.setForeground(new Color(255, 255, 255));
        rbtn_vuocDinhMuc.setText("Vược định mức tồn");
        rbtn_vuocDinhMuc.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                rbtn_vuocDinhMucItemStateChanged(evt);
            }
        });

        rbtn_tatCa.setBackground(new Color(144, 238, 144));
        btng.add(rbtn_tatCa);
        rbtn_tatCa.setFont(new Font("Arial", 0, 12)); // NOI18N
        rbtn_tatCa.setForeground(new Color(255, 255, 255));
        rbtn_tatCa.setSelected(true);
        rbtn_tatCa.setText("Tất cả");
        rbtn_tatCa.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                rbtn_tatCaItemStateChanged(evt);
            }
        });

        btn_topspbancham.setFont(new Font("Arial", 0, 12)); // NOI18N
        btn_topspbancham.setText("Top 10 sản phẩm bán chậm");
        btn_topspbancham.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btn_topspbanchamMouseClicked(evt);
            }
        });

        btn_topspbanchay.setFont(new Font("Arial", 0, 12)); // NOI18N
        btn_topspbanchay.setText("Top 10 sản phẩm bán chạy");
        btn_topspbanchay.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btn_topspbanchayMouseClicked(evt);
            }
        });
        
        btnXuatBaoCao = new JButton("Xuất báo cáo");
        btnXuatBaoCao.setBackground(new Color(255, 255, 255));
        btnXuatBaoCao.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btnXuatBaoCao.setFont(new Font("Arial", Font.PLAIN, 13));
        btnXuatBaoCao.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent evt) {
                btnXuatBaoCaoMouseClicked(evt);
            }
		});
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(scr_1, GroupLayout.PREFERRED_SIZE, 911, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(pnl_4, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(lbl_MauSac, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(cb_MauSac, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lbl_PhanLoai, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        								.addComponent(lbl_KichThuoc, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(cb_KichThuoc, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(cb_PhanLoai, 0, 112, Short.MAX_VALUE))))
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE)
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        								.addComponent(btn_topspbanchay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(btn_topspbancham, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(18)
        							.addComponent(btnXuatBaoCao, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(17)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lbl_MauSac, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(cb_MauSac, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        							.addComponent(btn_topspbanchay, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cb_PhanLoai, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lbl_PhanLoai)
        						.addComponent(btn_topspbancham, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(lbl_KichThuoc, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        								.addComponent(cb_KichThuoc, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(18)
        							.addComponent(btnXuatBaoCao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        					.addGap(48))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(pnl_4, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addComponent(scr_1, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        			.addContainerGap())
        );
        pnl_4.setLayout(null);
        pnl_4.add(lbl_2);
        pnl_4.add(txt_DinhMuc);
        pnl_4.add(rbtn_tatCa);
        pnl_4.add(rbtn_vuocDinhMuc);
        pnl_4.add(rbtn_duoiDinhMuc);
        pnl_4.add(lbl_3);
        
        lblthongbaonhap = new JLabel("");
        lblthongbaonhap.setForeground(Color.RED);
        lblthongbaonhap.setBounds(106, 50, 148, 14);
        pnl_4.add(lblthongbaonhap);
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents

    protected void btnXuatBaoCaoMouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		exportPDF();
	}

	private void exportPDF() {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            Document document = new Document();
    	try {
            // Tạo file PDF mới
    		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileToSave.getAbsolutePath() + ".pdf"));
            document.open();
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 12);

            // Thêm tiêu đề
            if(rbtn_tatCa.isSelected()) {
            	Paragraph title = new Paragraph("BẢNG THỐNG KÊ SẢN PHẨM TỒN", font);
                title.setFont(new com.itextpdf.text.Font(BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, com.itextpdf.text.Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);
            }else           
            if(rbtn_duoiDinhMuc.isSelected()) {
            	Paragraph title1 = new Paragraph("BẢNG THỐNG KÊ SẢN PHẨM DƯỚI ĐỊNH MỨC TỒN", font);
                title1.setFont(new com.itextpdf.text.Font(BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, com.itextpdf.text.Font.BOLD));
                title1.setAlignment(Element.ALIGN_CENTER);
                title1.setSpacingAfter(20);
                document.add(title1);
                Paragraph dmt = new Paragraph("Định mức tồn: " + txt_DinhMuc.getText(), font);
                dmt.setSpacingAfter(10);
                document.add(dmt);
            }
            if(rbtn_vuocDinhMuc.isSelected()) {
            	Paragraph title1 = new Paragraph("BẢNG THỐNG KÊ SẢN PHẨM VƯỢC ĐỊNH MỨC TỒN", font);
                title1.setFont(new com.itextpdf.text.Font(BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, com.itextpdf.text.Font.BOLD));
                title1.setAlignment(Element.ALIGN_CENTER);
                title1.setSpacingAfter(20);
                document.add(title1);
                Paragraph dmt = new Paragraph("Định mức tồn: " + txt_DinhMuc.getText(), font);
                dmt.setSpacingAfter(10);
                document.add(dmt);
            }

            // Thêm dữ liệu từ bảng
            PdfPTable table = new PdfPTable(tbl_DanhSachSanPham.getColumnCount());
            table.setWidthPercentage(100);
            
            for (int col = 0; col < tbl_DanhSachSanPham.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(tbl_DanhSachSanPham.getColumnName(col)), font));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (int i = 0; i < tbl_DanhSachSanPham.getRowCount(); i++) {
                for (int j = 0; j < tbl_DanhSachSanPham.getColumnCount(); j++) {
                    table.addCell(new Phrase(tbl_DanhSachSanPham.getValueAt(i, j).toString(), font));
                }
            }

            document.add(table);

            // Thêm thông tin thống kê
            
            Paragraph printDateTime = new Paragraph("Ngày giờ in: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), font);
            printDateTime.setAlignment(Element.ALIGN_LEFT);
            document.add(printDateTime);

            // Đóng tài liệu
            document.close();

            JOptionPane.showMessageDialog(this, "Xuất báo cáo thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xuất báo cáo: " + e.getMessage());
        }
        }
	}

	private void cb_KichThuocActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cb_KichThuocActionPerformed
        // TODO add your handling code here:
    	
    	if(rbtn_tatCa.isSelected()) {
     	   tblDanhSachSanPham();
        }else if(!rbtn_tatCa.isSelected()) {
     	   tblDssp();
        }
    	tblDanhSachSanPham();
    }//GEN-LAST:event_cb_KichThuocActionPerformed

    private void cb_MauSacItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_MauSacItemStateChanged
        // TODO add your handling code here:
       if(rbtn_tatCa.isSelected()) {
    	   tblDanhSachSanPham();
       }else if(!rbtn_tatCa.isSelected()) {
    	   tblDssp();
       }
    	
    }//GEN-LAST:event_cb_MauSacItemStateChanged

    private void cb_PhanLoaiItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_PhanLoaiItemStateChanged
        // TODO add your handling code here:
    	if(rbtn_tatCa.isSelected()) {
     	   tblDanhSachSanPham();
        }else if(!rbtn_tatCa.isSelected()) {
     	   tblDssp();
        }
    }//GEN-LAST:event_cb_PhanLoaiItemStateChanged

    private void cb_KichThuocItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_KichThuocItemStateChanged
        // TODO add your handling code here:
    	if(rbtn_tatCa.isSelected()) {
     	   tblDanhSachSanPham();
        }else if(!rbtn_tatCa.isSelected()) {
     	   tblDssp();
        }
    }//GEN-LAST:event_cb_KichThuocItemStateChanged

    private void txt_DinhMucKeyReleased(KeyEvent evt) {//GEN-FIRST:event_txt_DinhMucKeyReleased
    	// TODO add your handling code here:
		if(txt_DinhMuc.getText().equals("")) {
//		    txt_DinhMuc.setText("");  
		}

		int soLuong = duLieuDinhMuc();
		
		if(soLuong == -1) return;
		
		if(rbtn_tatCa.isSelected()){
		    tblDanhSachSanPham();
		}
		else if(rbtn_vuocDinhMuc.isSelected()){
		    tblDanhSachSanPhamVuotDinhMuc(soLuong);
		}
		else if(rbtn_duoiDinhMuc.isSelected()){
		    tblDanhSachSanPhamDuoiDinhMuc(soLuong);
		}
    }//GEN-LAST:event_txt_DinhMucKeyReleased

    private void txt_DinhMucInputMethodTextChanged(InputMethodEvent evt) {//GEN-FIRST:event_txt_DinhMucInputMethodTextChanged
    	// TODO add your handling code here:
		 int soLuong = duLieuDinhMuc();
		if(soLuong == -1) return;
		
		if(rbtn_tatCa.isSelected()){
		    tblDanhSachSanPham();
		}
		else if(rbtn_vuocDinhMuc.isSelected()){
		    tblDanhSachSanPhamVuotDinhMuc(soLuong);
		}
		else if(rbtn_duoiDinhMuc.isSelected()){
		    tblDanhSachSanPhamDuoiDinhMuc(soLuong);
		}
    }//GEN-LAST:event_txt_DinhMucInputMethodTextChanged

    private void rbtn_duoiDinhMucItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_rbtn_duoiDinhMucItemStateChanged
        // TODO add your handling code here:
         int soLuong = duLieuDinhMuc();
        if(soLuong == -1) return;
        
        if(evt.getStateChange() == ItemEvent.SELECTED){
            tblDanhSachSanPhamDuoiDinhMuc(soLuong);
        }
    }//GEN-LAST:event_rbtn_duoiDinhMucItemStateChanged

    private void rbtn_vuocDinhMucItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_rbtn_vuocDinhMucItemStateChanged
        // TODO add your handling code here:
         int soLuong = duLieuDinhMuc();
        if(soLuong == -1) return;
        
        if(evt.getStateChange() == ItemEvent.SELECTED){
            tblDanhSachSanPhamVuotDinhMuc(soLuong);
        }
    }//GEN-LAST:event_rbtn_vuocDinhMucItemStateChanged

    private void rbtn_tatCaItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_rbtn_tatCaItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            tblDanhSachSanPham();
        }
        txt_DinhMuc.setText("");
    }//GEN-LAST:event_rbtn_tatCaItemStateChanged

    private void btn_topspbanchayMouseClicked(MouseEvent evt) {//GEN-FIRST:event_btn_topspbanchayMouseClicked
        // TODO add your handling code here:
    	try {
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			List<SanPham>listSanPham = sanPhamDAO.topNSanPham();
			new FormDanhSachSanPhamBanChay(listSanPham).setVisible(true);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//GEN-LAST:event_btn_topspbanchayMouseClicked
//
    private void btn_topspbanchamMouseClicked(MouseEvent evt) {//GEN-FIRST:event_btn_topspbanchamMouseClicked
       // TODO add your handling code here:
    		try {
				SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
		        List<SanPham>listSanPham = sanPhamDAO.topNSanPhamBanCham();
		        new FormDanhSachSanPhamBanCham(listSanPham).setVisible(true);
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }//GEN-LAST:event_btn_topspbanchamMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btng;
    private javax.swing.JComboBox<String> cb_KichThuoc;
    private javax.swing.JComboBox<String> cb_MauSac;
    private javax.swing.JComboBox<String> cb_PhanLoai;
    private javax.swing.JButton btn_topspbancham;
    private javax.swing.JButton btn_topspbanchay;
    private javax.swing.JLabel lbl_1;
    private javax.swing.JLabel lbl_2;
    private javax.swing.JLabel lbl_3;
    private javax.swing.JPanel pnl_4;
    private javax.swing.JScrollPane scr_1;
    private javax.swing.JLabel lbl_KichThuoc;
    private javax.swing.JLabel lbl_MauSac;
    private javax.swing.JLabel lbl_PhanLoai;
    private javax.swing.JRadioButton rbtn_duoiDinhMuc;
    private javax.swing.JRadioButton rbtn_tatCa;
    private javax.swing.JRadioButton rbtn_vuocDinhMuc;
    private javax.swing.JTable tbl_DanhSachSanPham;
    private javax.swing.JTextField txt_DinhMuc;
    private JButton btnXuatBaoCao;
    private JLabel lblthongbaonhap;
}
