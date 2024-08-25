package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font.FontFamily;
import com.toedter.calendar.JDateChooser;




import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
//import dao.ChiTietHoaDonDAO;
//import dao.HoaDonDAO;
import dao.KichThuocDAO;
import dao.MauSacDAO;
import dao.PhanLoaiDAO;
import dao.SanPhamDAO;
import entities.KichThuoc;
import entities.MauSac;
import entities.PhanLoai;
//import entity.KichThuoc;
//import entity.MauSac;
//import entity.PhanLoai;
//import entity.SanPham;
import entities.SanPham;
import main.local_host;

public class TabThongKeDoanhThu extends javax.swing.JPanel {
    private boolean initial = true; 
//    private SanPhamDAO sanPham_DAO = new SanPhamDAO();
//    private ChiTietHoaDonDAO cthd_DAO = new ChiTietHoaDonDAO();
//    private HoaDonDAO hoaDon_DAO = new HoaDonDAO();
//    private KichThuocDAO kichThuoc_DAO = new KichThuocDAO();
//    private MauSacDAO mauSac_DAO = new MauSacDAO();
//    private PhanLoaiDAO phanLoai_DAO = new PhanLoaiDAO();
//    private final ArrayList<KichThuoc> listKichThuoc = kichThuoc_DAO.getAllKichThuoc();
//    private final ArrayList<MauSac> listMauSac = mauSac_DAO.getAllMauSac();
//    private final ArrayList<PhanLoai> listPhanLoai = phanLoai_DAO.getAllPhanLoai();

    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Registry registry;
    /**
     * Creates new form TabThongKeSanPham
     */
    public TabThongKeDoanhThu() {
    	try {
    		local_host local = new local_host();
			registry = LocateRegistry.getRegistry(local.host(), 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	setPreferredSize(new Dimension(932, 685));
    	setBorder(new LineBorder(new Color(0, 0, 0), 2));
        initComponents();
        designTable();
        khoiTaoGiaTri();
        tblDanhSachSanPham();
    }
    
    private void khoiTaoGiaTri(){
    	try {
			KichThuocDAO kichThuocDAO = (KichThuocDAO) registry.lookup("KichThuocDAO");
			MauSacDAO mauSacDAO = (MauSacDAO) registry.lookup("MauSacDAO");
			PhanLoaiDAO phanLoaiDAO = (PhanLoaiDAO) registry.lookup("PhanLoaiDAO");
			
			List<KichThuoc> kichThuocs = kichThuocDAO.getAllKichThuoc();
			for(KichThuoc kt : kichThuocs){
			    cb_KichThuoc.addItem(kt.getKichThuoc());
			}
			List<MauSac> mauSacs = mauSacDAO.getAllMauSac();
			for(MauSac ms : mauSacs){
			    cb_MauSac.addItem(ms.getMauSac());
			}
			List<PhanLoai> phanLoais = phanLoaiDAO.getAllPhanLoai();
			for(PhanLoai pl : phanLoais){
			    cb_PhanLoai.addItem(pl.getPhanLoai());
			}
			
			cb_TatCa.setSelected(true);
			
			// ngay hom nay
			dc_TuNgay.setDate(Calendar.getInstance().getTime());
			dc_DenNgay.setDate(Calendar.getInstance().getTime());
			initial = false;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void designTable() {
        tbl_DanhSachSanPham.getTableHeader().setFont(new java.awt.Font("Arial", 0, 12));
        tbl_DanhSachSanPham.getTableHeader().setOpaque(false);
        tbl_DanhSachSanPham.getTableHeader().setBackground(new Color(144,238,144));
        tbl_DanhSachSanPham.getTableHeader().setForeground(Color.WHITE);
        tbl_DanhSachSanPham.setDefaultEditor(Object.class, null);
    }
    
    private void clearTable(){
        DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
        dtm.setRowCount(0);
    }
    
    private double tongDoanhThu(String mauSac, String phanLoai, String kichThuoc){
        try {
			double tongDoanhThu = 0;
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			ArrayList<SanPham> listSanPham = sanPhamDAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc);
			
			 for(SanPham sp : listSanPham){
			     tongDoanhThu += sp.getGiaNhap();
			 }
			 return tongDoanhThu;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }
    
    private double tongDoanhThuFilter(String mauSac, String phanLoai, String kichThuoc, String tuNgay,String denNgay){
        try {
			double tongDoanhThu = 0;
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			 ArrayList<SanPham> listSanPham = sanPhamDAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc, tuNgay, denNgay);
			 for(SanPham sp : listSanPham){
			     tongDoanhThu += sp.getGiaNhap();
			 }
			 return tongDoanhThu;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }
    
    private void tblDanhSachSanPham(){
        try {
			clearTable();
			
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			ArrayList<SanPham> listSanPham = sanPhamDAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc);
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
			int tongSoSanPhamBanDuoc = 0;
			double tongDoanhThu = tongDoanhThu(mauSac, phanLoai, kichThuoc);
			for(SanPham sp : listSanPham){
			    tongSoSanPhamBanDuoc += sp.getSoLuong();
			    double tiLeDoanhThu = 0;

			    if (tongDoanhThu != 0) {
			        tiLeDoanhThu = (sp.getGiaNhap() / (double)tongDoanhThu) * 100;
			    }           
			    String tldt = decimalFormat.format(tiLeDoanhThu);//
			    Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
			                        sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format( sp.getGiaNhap()), 
			                        tldt};
			    dtm.addRow(rowData);
			}
			
			lbl_SoTongSanPhamDaBan.setText(tongSoSanPhamBanDuoc+"");
			lbl_SoTongSanPhamConLai.setText(NumberFormat.getInstance().format(tongDoanhThu));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   
    private void tblDanhSachSanPhamTheoTime(){
        try {
			clearTable();
			
			String mauSac = cb_MauSac.getSelectedItem().toString();
			if(cb_MauSac.getSelectedItem().toString().equals("Tất cả")) mauSac = "";
			
			String phanLoai = cb_PhanLoai.getSelectedItem().toString();
			if(cb_PhanLoai.getSelectedItem().toString().equals("Tất cả")) phanLoai = "";
			
			String kichThuoc = cb_KichThuoc.getSelectedItem().toString();
			if(cb_KichThuoc.getSelectedItem().toString().equals("Tất cả")) kichThuoc = "";
     

			String tuNgay = new SimpleDateFormat("yyyy-MM-dd").format( dc_TuNgay.getDate());
			String denNgay = new SimpleDateFormat("yyyy-MM-dd").format( dc_DenNgay.getDate());
			SanPhamDAO sanPhamDAO = (SanPhamDAO) registry.lookup("SanPhamDAO");
			ArrayList<SanPham> listSanPham = sanPhamDAO.thongKeDanhSachSanPhamVoiSoLuongBanDuoc(mauSac, phanLoai, kichThuoc, tuNgay, denNgay);
			
			DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachSanPham.getModel();
			int tongSoSanPhamBanDuoc = 0;
			double tongDoanhThu = tongDoanhThuFilter(mauSac, phanLoai, kichThuoc, tuNgay, denNgay);
			for(SanPham sp : listSanPham){
			    tongSoSanPhamBanDuoc += sp.getSoLuong();
			    double tiLeDoanhThu = 0;
			    DecimalFormat df = new DecimalFormat("#.##");//rút gọn số thập phân
			    if (tongDoanhThu != 0) {
			        tiLeDoanhThu = (sp.getGiaNhap() / (double)tongDoanhThu) * 100;
			    }           
			    String tldt = df.format(tiLeDoanhThu);//
			    Object[] rowData = {sp.getMaSP(), sp.getTenSP(), sp.getPhanLoai().getPhanLoai(), sp.getKichThuoc().getKichThuoc(),
			                        sp.getMauSac().getMauSac(), sp.getSoLuong(), NumberFormat.getInstance().format(sp.getGiaNhap()),
			                        tldt};
			    dtm.addRow(rowData);
			}
			
			lbl_SoTongSanPhamDaBan.setText(tongSoSanPhamBanDuoc+"");
			lbl_SoTongSanPhamConLai.setText(NumberFormat.getInstance().format(tongDoanhThu));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
     
//    private boolean dateValid(){
//        Date currentDate = Calendar.getInstance().getTime();
//         if(dc_TuNgay.getDate().getTime() - currentDate.getTime() > 0){
//             JOptionPane.showMessageDialog(null, "Từ ngày phải bé hơn hoặc bằng ngày hiện tại");
//             dc_TuNgay.setDate(dc_DenNgay.getDate());
//             return false;
//         }
//         
//         if(dc_TuNgay.getDate().getTime() - dc_DenNgay.getDate().getTime() > 0){
//             JOptionPane.showMessageDialog(null, "Từ ngày phải bé hơn hoặc bằng đến ngày");
//             dc_TuNgay.setDate(dc_DenNgay.getDate());
//             return false;
//         }
//         
//         if(dc_DenNgay.getDate().getTime() - currentDate.getTime() > 0){
//             JOptionPane.showMessageDialog(null, "Đến ngày phải bé hơn hoặc bằng ngày hiện tại");
//             dc_DenNgay.setDate(currentDate);
//             return false;
//         }
//            
//         return true;
//    }
    private boolean dateValid() {
        Date currentDate = Calendar.getInstance().getTime();

        if (dc_TuNgay.getDate().compareTo(currentDate) > 0) {
            dc_TuNgay.setDate(currentDate);
            JOptionPane.showMessageDialog(null, "Từ ngày phải bé hơn hoặc bằng ngày hiện tại");
            return false;
        }

        if (dc_TuNgay.getDate().compareTo(dc_DenNgay.getDate()) > 0) {
            dc_TuNgay.setDate(dc_DenNgay.getDate());
            JOptionPane.showMessageDialog(null, "Từ ngày phải bé hơn hoặc bằng đến ngày");
            return false;
        }

        if (dc_DenNgay.getDate().compareTo(currentDate) > 0) {
            dc_DenNgay.setDate(currentDate);
            JOptionPane.showMessageDialog(null, "Đến ngày phải bé hơn hoặc bằng ngày hiện tại");
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lbl_1 = new JLabel();
        scr_1 = new JScrollPane();
        tbl_DanhSachSanPham = new JTable();
        pnl_1 = new JPanel();
        lbl_TongSanPhamDaBan = new JLabel();
        lbl_SoTongSanPhamDaBan = new JLabel();
        pnl_2 = new JPanel();
        lbl_TongSanPhamConLai = new JLabel();
        lbl_SoTongSanPhamConLai = new JLabel();
        lbl_MauSac = new JLabel();
        cb_MauSac = new JComboBox<>();
        lbl_Thang = new JLabel();
        lbl_Nam = new JLabel();
        cb_TatCa = new JCheckBox();
        cb_PhanLoai = new JComboBox<>();
        lbl_PhanLoai = new JLabel();
        lbl_KichThuoc = new JLabel();
        cb_KichThuoc = new JComboBox<>();
        dc_TuNgay = new JDateChooser();
        dc_DenNgay = new JDateChooser();

        lbl_1.setText("lbl_1");

        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(932, 685));
        tbl_DanhSachSanPham.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Phân loại", "Kích cỡ", "Màu sắc", "Số lượng bán được", "Doanh thu", "Tỉ lệ doanh thu (%)"
            }
        ));
        tbl_DanhSachSanPham.setRowHeight(30);
        scr_1.setViewportView(tbl_DanhSachSanPham);

        pnl_1.setBackground(new Color(255,255,255));
        pnl_1.setBorder(BorderFactory.createLineBorder(Color.black));

        lbl_TongSanPhamDaBan.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_TongSanPhamDaBan.setForeground(new Color(0,0,0));
        lbl_TongSanPhamDaBan.setText("Sản phẩm đã bán được");

        lbl_SoTongSanPhamDaBan.setFont(new Font("Calibri", 1, 48)); // NOI18N
        lbl_SoTongSanPhamDaBan.setForeground(new Color(0,0,0));
        lbl_SoTongSanPhamDaBan.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_SoTongSanPhamDaBan.setText("0");
        lbl_SoTongSanPhamDaBan.setHorizontalTextPosition(SwingConstants.CENTER);

        GroupLayout pnl_1Layout = new GroupLayout(pnl_1);
        pnl_1.setLayout(pnl_1Layout);
        pnl_1Layout.setHorizontalGroup(
            pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_1Layout.createSequentialGroup()
                .addGroup(pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_SoTongSanPhamDaBan, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lbl_TongSanPhamDaBan)))
                .addGap(48, 48, 48))
        );
        pnl_1Layout.setVerticalGroup(
            pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_TongSanPhamDaBan, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lbl_SoTongSanPhamDaBan, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_2.setBackground(new Color(255,255,255));
        pnl_2.setBorder(BorderFactory.createLineBorder(Color.black));

        lbl_TongSanPhamConLai.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_TongSanPhamConLai.setForeground(new Color(0,0,0));
        lbl_TongSanPhamConLai.setText("Tổng doanh thu");

        lbl_SoTongSanPhamConLai.setFont(new Font("Calibri", 1, 48)); // NOI18N
        lbl_SoTongSanPhamConLai.setForeground(new Color(0,0,0));
        lbl_SoTongSanPhamConLai.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_SoTongSanPhamConLai.setText("0");
        lbl_SoTongSanPhamConLai.setHorizontalTextPosition(SwingConstants.CENTER);

        GroupLayout pnl_2Layout = new GroupLayout(pnl_2);
        pnl_2.setLayout(pnl_2Layout);
        pnl_2Layout.setHorizontalGroup(
            pnl_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_SoTongSanPhamConLai, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, pnl_2Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addComponent(lbl_TongSanPhamConLai)
                .addGap(89, 89, 89))
        );
        pnl_2Layout.setVerticalGroup(
            pnl_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_TongSanPhamConLai, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_SoTongSanPhamConLai, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbl_MauSac.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_MauSac.setText("Màu sắc:");

        cb_MauSac.setFont(new Font("Calibri", 0, 12)); // NOI18N
        cb_MauSac.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_MauSac.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_MauSacItemStateChanged(evt);
            }
        });

        lbl_Thang.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_Thang.setText("Từ ngày:");

        lbl_Nam.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_Nam.setText("Đến ngày:");

        cb_TatCa.setBackground(new Color(255, 255, 255));
        cb_TatCa.setFont(new Font("Calibri", 0, 12)); // NOI18N
        cb_TatCa.setSelected(true);
        cb_TatCa.setText("Tất cả");
        cb_TatCa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_TatCaItemStateChanged(evt);
            }
        });
        cb_TatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_TatCaActionPerformed(evt);
            }
        });

        cb_PhanLoai.setFont(new Font("Calibri", 0, 12)); // NOI18N
        cb_PhanLoai.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_PhanLoai.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_PhanLoaiItemStateChanged(evt);
            }
        });

        lbl_PhanLoai.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_PhanLoai.setText("Phân loại:");

        lbl_KichThuoc.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_KichThuoc.setText("Kích thước:");

        cb_KichThuoc.setFont(new Font("Calibri", 0, 12)); // NOI18N
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

        dc_TuNgay.setDateFormatString("dd/MM/yyyy");
        dc_TuNgay.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dc_TuNgayPropertyChange(evt);
            }
        });

        dc_DenNgay.setDateFormatString("dd/MM/yyyy");
        dc_DenNgay.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dc_DenNgayPropertyChange(evt);
            }
        });
        
        JButton btnXuatBaoCao = new JButton("Xuất báo cáo");
        btnXuatBaoCao.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btnXuatBaoCao.setBackground(new Color(255, 255, 255));
        btnXuatBaoCao.setFont(new Font("Arial", Font.PLAIN, 12));
        btnXuatBaoCao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnXuatBaoCaoActionPerformed(evt);
            }
        });
        
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(scr_1, GroupLayout.PREFERRED_SIZE, 929, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(pnl_1, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(pnl_2, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(lbl_MauSac, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(lbl_PhanLoai, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addGroup(layout.createSequentialGroup()
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(cb_MauSac, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
        								.addGroup(layout.createSequentialGroup()
        									.addGap(6)
        									.addComponent(cb_PhanLoai, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(lbl_KichThuoc)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(cb_KichThuoc, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
        					.addGap(9)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lbl_Thang)
        						.addComponent(lbl_Nam))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(cb_TatCa, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(btnXuatBaoCao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(dc_TuNgay, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        							.addComponent(dc_DenNgay, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(36)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(lbl_MauSac, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(lbl_Thang, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        								.addComponent(cb_MauSac, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
        						.addGroup(layout.createSequentialGroup()
        							.addContainerGap()
        							.addComponent(dc_TuNgay, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(dc_DenNgay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(lbl_PhanLoai)
        							.addComponent(cb_PhanLoai, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        							.addComponent(lbl_Nam)))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(lbl_KichThuoc, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        							.addComponent(cb_KichThuoc, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        						.addComponent(btnXuatBaoCao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cb_TatCa))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(pnl_1, 0, 0, Short.MAX_VALUE)
        						.addComponent(pnl_2, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))))
        			.addGap(4)
        			.addComponent(scr_1, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        			.addContainerGap())
        );
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnXuatBaoCaoActionPerformed(ActionEvent evt) {
        exportToPDF();
    }

    private void exportToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            Document document = new Document();

            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileToSave.getAbsolutePath() + ".pdf"));
                document.open();
                BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 12);

                // Thêm tiêu đề
                Paragraph title = new Paragraph("BÁO CÁO DOANH THU", font);
                title.setFont(new com.itextpdf.text.Font(BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, com.itextpdf.text.Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);
                
                
                
                // Thêm bảng
                PdfPTable table = new PdfPTable(tbl_DanhSachSanPham.getColumnCount());
                table.setWidthPercentage(100);

                // Thêm tiêu đề cột
                for (int col = 0; col < tbl_DanhSachSanPham.getColumnCount(); col++) {
                    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(tbl_DanhSachSanPham.getColumnName(col)), font));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                // Đổ dữ liệu từ bảng vào PDF
                for (int row = 0; row < tbl_DanhSachSanPham.getRowCount(); row++) {
                    for (int col = 0; col < tbl_DanhSachSanPham.getColumnCount(); col++) {
                        PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(tbl_DanhSachSanPham.getValueAt(row, col)), font));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }

                document.add(table);
                Paragraph dc = new Paragraph("Tổng doanh thu: " + lbl_SoTongSanPhamConLai.getText(), font);
                document.add(dc);
                
                Paragraph soSanPhamDaBan = new Paragraph("Số sản phẩm đã bán được: " + lbl_SoTongSanPhamDaBan.getText(), font);// Lấy dữ liệu từ bảng hoặc từ dữ liệu khác);
                document.add(soSanPhamDaBan);
                
                if (!cb_TatCa.isSelected() && dc_TuNgay.getDate() != null && dc_DenNgay.getDate() != null) {
                    Paragraph timeRange = new Paragraph("Thời gian từ Ngày: " +
                            new SimpleDateFormat("yyyy-MM-dd").format(dc_TuNgay.getDate()) +
                            " đến Ngày: " +
                            new SimpleDateFormat("yyyy-MM-dd").format(dc_DenNgay.getDate()), font);
                    document.add(timeRange);
                }
                // Add "Ngày giờ in" to the PDF
                Paragraph printDateTime = new Paragraph("Ngày giờ in: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), font);
                printDateTime.setAlignment(Element.ALIGN_LEFT);
                document.add(printDateTime);

                JOptionPane.showMessageDialog(this, "Xuất PDF thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF!");
            } finally {
                document.close();
            }
        }
    }



    
    private void cb_MauSacItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_MauSacItemStateChanged
        // TODO add your handling code here:
        if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
    }//GEN-LAST:event_cb_MauSacItemStateChanged

    private void cb_TatCaItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_TatCaItemStateChanged
        // TODO add your handling code here:
        if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
    }//GEN-LAST:event_cb_TatCaItemStateChanged

    private void cb_TatCaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cb_TatCaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_TatCaActionPerformed

    private void cb_PhanLoaiItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_PhanLoaiItemStateChanged
        // TODO add your handling code here:
        if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
    }//GEN-LAST:event_cb_PhanLoaiItemStateChanged

    private void cb_KichThuocItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_KichThuocItemStateChanged
        // TODO add your handling code here:

        if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
    }//GEN-LAST:event_cb_KichThuocItemStateChanged

    private void cb_KichThuocActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cb_KichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_KichThuocActionPerformed

    private void dc_TuNgayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dc_TuNgayPropertyChange
        // TODO add your handling code here:
        if(initial) return;
        if(!dateValid())return;
        
        if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
            
        
    
    }//GEN-LAST:event_dc_TuNgayPropertyChange

    private void dc_DenNgayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dc_DenNgayPropertyChange
        // TODO add your handling code here:
        if(initial) return;
        if(!dateValid())return;
        
       if(cb_TatCa.isSelected()){
            tblDanhSachSanPham();
        }
        else if(!cb_TatCa.isSelected()){
            tblDanhSachSanPhamTheoTime();
        }
   
    }//GEN-LAST:event_dc_DenNgayPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox<String> cb_KichThuoc;
    private JComboBox<String> cb_MauSac;
    private JComboBox<String> cb_PhanLoai;
    private JCheckBox cb_TatCa;
    private JDateChooser dc_DenNgay;
    private JDateChooser dc_TuNgay;
    private JLabel lbl_1;
    private JPanel pnl_1;
    private JPanel pnl_2;
    private JScrollPane scr_1;
    private JLabel lbl_KichThuoc;
    private JLabel lbl_MauSac;
    private JLabel lbl_Nam;
    private JLabel lbl_PhanLoai;
    private JLabel lbl_SoTongSanPhamConLai;
    private JLabel lbl_SoTongSanPhamDaBan;
    private JLabel lbl_Thang;
    private JLabel lbl_TongSanPhamConLai;
    private JLabel lbl_TongSanPhamDaBan;
    private JTable tbl_DanhSachSanPham;
}
