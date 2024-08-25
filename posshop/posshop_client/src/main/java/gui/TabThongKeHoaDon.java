package gui;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;

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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class TabThongKeHoaDon extends JPanel {
//    private KhachHangDAO khachHang_DAO = new KhachHangDAO();
//    private NhanVienDAO nhanVien_DAO = new NhanVienDAO();
//    private ChiTietHoaDonDAO cthd_DAO = new ChiTietHoaDonDAO();
//    private HoaDonDAO hoaDon_DAO = new HoaDonDAO();
    
    private Registry registry;
	/**
     * Creates new form TabThongKeHoaDon
     */
    public TabThongKeHoaDon() {
    	try {
			registry = LocateRegistry.getRegistry("26.52.102.222", 1232);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initComponents();
        designTable();       
        khoiTaoGiaTri();
        tableHoaDon();
    }
    
     private void designTable() {
        tbl_DanhSachHoaDon.getTableHeader().setFont(new Font("Calibri", 0, 12));
        tbl_DanhSachHoaDon.getTableHeader().setOpaque(false);
        tbl_DanhSachHoaDon.getTableHeader().setBackground(new Color(144,238,144));
        tbl_DanhSachHoaDon.getTableHeader().setForeground(Color.WHITE);
        tbl_DanhSachHoaDon.setDefaultEditor(Object.class, null); // Không cho phép edit

//        tbl_danhSachNhanVien.getTableHeader()
    }
     
     private void khoiTaoGiaTri(){
    	 try {
			NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
			 List<NhanVien> listNhanVien = nhanVienDAO.getAllNhanVien();
			 
			 for(NhanVien nv : listNhanVien){
			     cb_TenNhanVien.addItem(nv.getTenNV());
			 }
			 KhachHangDAO khachHangDAO = (KhachHangDAO) registry.lookup("KhachHangDAO");
			 List<KhachHang> listKhachHang = khachHangDAO.getAllKhachHang();
			 for(KhachHang kh : listKhachHang){
			     cb_TenKhachHang.addItem(kh.getTenKH());
			 }
			 
			 cb_TatCa.setSelected(true);
			 dc_TuNgay.setDate(Calendar.getInstance().getTime());
			 dc_DenNgay.setDate(Calendar.getInstance().getTime());
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     private void clearTable(){
        DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachHoaDon.getModel();
        dtm.setRowCount(0);
     }
     
     private void tableHoaDon(){
         try {
			clearTable();
			 
			 String tenNhanVien = cb_TenNhanVien.getSelectedItem().toString();
			 if(tenNhanVien.equals("Tất cả")) tenNhanVien = "";
			 
			 String tenKhachHang = cb_TenKhachHang.getSelectedItem().toString();
			 if(tenKhachHang.equals("Tất cả")) tenKhachHang = "";
			 HoaDonDAO ds = (HoaDonDAO) registry.lookup("HoaDonDAO");
			 List<HoaDon> listHoaDon = ds.getAllHoaDonTheoTenNVKH(tenNhanVien,tenKhachHang );
			 double tongHoaDon = listHoaDon.size();
			 double tongThanhTien = 0;
			 DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachHoaDon.getModel();
			 for(HoaDon hd : listHoaDon) {
				 if(hd.getNhanVien() != null) {
					 	ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
				        double tongTien = chiTietHoaDonDAO.getTongTienByMaHD(hd.getMaHD());
				        tongThanhTien += tongTien;
				        Object[] rowData = {hd.getMaHD(), hd.getNhanVien().getTenNV(), hd.getKhachHang().getTenKH(), 
				         hd.getNgayLap(), NumberFormat.getInstance().format(tongTien)};
				        dtm.addRow(rowData);          	       
				 }       
			 }
			 
			 lbl_KQTongDoanhThu.setText(NumberFormat.getInstance().format(tongThanhTien));
			 lbl_KQTongHD.setText(NumberFormat.getInstance().format(tongHoaDon));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     private void tableHoaDonTheoTime(){
         try {
			clearTable();
			 
			 String tenNhanVien = cb_TenNhanVien.getSelectedItem().toString();
			 if(tenNhanVien.equals("Tất cả")) tenNhanVien = "";
			 
			 String tenKhachHang = cb_TenKhachHang.getSelectedItem().toString();
			 if(tenKhachHang.equals("Tất cả")) tenKhachHang = "";
			         
			 String tuNgay = new SimpleDateFormat("yyyy-MM-dd").format( dc_TuNgay.getDate());
	         String denNgay = new SimpleDateFormat("yyyy-MM-dd").format( dc_DenNgay.getDate());
			 
			 HoaDonDAO hoaDonDAO = (HoaDonDAO) registry.lookup("HoaDonDAO");
			 List<HoaDon> listHoaDon = hoaDonDAO.getAllHoaDonTheoTenKHandNVTrongNgay(tenKhachHang, tenNhanVien, tuNgay, denNgay);
			 double tongHoaDon = listHoaDon.size();
			 double tongThanhTien = 0;
			 
			 DefaultTableModel dtm = (DefaultTableModel) tbl_DanhSachHoaDon.getModel();
			 for(HoaDon hd : listHoaDon){
				ChiTietHoaDonDAO chiTietHoaDonDAO = (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
			    double tongTien = chiTietHoaDonDAO.getTongTienByMaHD(hd.getMaHD());
			    tongThanhTien += tongTien;
			    Object[] rowData = {hd.getMaHD(), hd.getNhanVien().getTenNV(), hd.getKhachHang().getTenKH(), 
			        new SimpleDateFormat("dd/MM/yyyy").format(hd.getNgayLap()), NumberFormat.getInstance().format(tongTien)};
			    dtm.addRow(rowData);
			 }
			 
			 lbl_KQTongDoanhThu.setText(NumberFormat.getInstance().format(tongThanhTien));
			 lbl_KQTongHD.setText(NumberFormat.getInstance().format(tongHoaDon));
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     private boolean isTuNgayValid(){
         Date currentDate = Calendar.getInstance().getTime();
         if(dc_TuNgay.getDate().getTime() - currentDate.getTime() > 0){
             JOptionPane.showMessageDialog(null, "Ngày phải bé hơn hoặc bằng ngày hiện tại");
             return false;
         }
         
         return true;
     }
     
     private boolean isDenNgayValid(){
         Date currentDate = Calendar.getInstance().getTime();
         Date tuNgay = dc_TuNgay.getDate();
         Date denNgay = dc_DenNgay.getDate();
         
         if(denNgay.getTime() - currentDate.getTime() > 0){
             JOptionPane.showMessageDialog(null, "Ngày phải bé hơn hoặc bằng ngày hiện tại");
             return false;
         }
         
         if(tuNgay.getTime() - denNgay.getTime() > 0){
             JOptionPane.showMessageDialog(null, "Đến ngày phải có giá trị nhỏ hơn từ ngày");
                     
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
    	setPreferredSize(new Dimension(932, 685));
        scr_1 = new JScrollPane( );
        
        
        scr_1.setBounds(10, 222, 909, 372);
        tbl_DanhSachHoaDon = new JTable();
        lbl_1 = new JLabel();
        lbl_1.setBounds(455, 46, 72, 38);
        lbl_2 = new JLabel();
        lbl_2.setBounds(700, 43, 53, 41);
        pnl_1 = new JPanel();
        pnl_1.setBounds(10, 11, 192, 205);
        lbl_TongSoHoaDon = new JLabel();
        lbl_KQTongHD = new JLabel();
        pnl_2 = new JPanel();
        pnl_2.setBounds(208, 11, 243, 204);
        lbl_TongDoanhThu = new JLabel();
        lbl_KQTongDoanhThu = new JLabel();

        dc_TuNgay = new JDateChooser();
        dc_TuNgay.setBounds(531, 46, 151, 38);
        dc_DenNgay = new JDateChooser();
        dc_DenNgay.setBounds(757, 43, 151, 41);
        //chuyển định dạng ngày tháng đầu vào thành dd/MM/yyyy
        dc_TuNgay.setDateFormatString("dd/MM/yyyy");
        dc_DenNgay.setDateFormatString("dd/MM/yyyy");
        
        cb_TatCa = new JCheckBox();
        cb_TatCa.setBounds(778, 86, 130, 33);
        lbl_TenNhanVien = new JLabel();
        lbl_TenNhanVien.setBounds(455, 122, 80, 40);
        cb_TenNhanVien = new JComboBox<>();
        cb_TenNhanVien.setBounds(575, 121, 333, 40);
        lbl_TenKhachHang = new JLabel();
        lbl_TenKhachHang.setBounds(455, 169, 88, 40);
        cb_TenKhachHang = new JComboBox<>();
        cb_TenKhachHang.setBounds(575, 168, 334, 40);

        setBackground(new Color(255, 255, 255));

        tbl_DanhSachHoaDon.setModel(new DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Ngày lập", "Thành tiền"
            }
        ));
        tbl_DanhSachHoaDon.setRowHeight(30);
        scr_1.setViewportView(tbl_DanhSachHoaDon);

        lbl_1.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_1.setText("Từ ngày:");

        lbl_2.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_2.setText("Đến ngày:");

        pnl_1.setBackground(new Color(255,255,255));
        pnl_1.setBorder(BorderFactory.createLineBorder(Color.black));

        lbl_TongSoHoaDon.setFont(new Font("Calibri", 1, 24)); // NOI18N
        lbl_TongSoHoaDon.setForeground(new Color(0,0,0));
        lbl_TongSoHoaDon.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TongSoHoaDon.setText("Tổng số hóa đơn");

        lbl_KQTongHD.setFont(new Font("Calibri", 1, 48)); // NOI18N
        lbl_KQTongHD.setForeground(new Color(0,0,0));
        lbl_KQTongHD.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_KQTongHD.setText("0");

        GroupLayout pnl_1Layout = new GroupLayout(pnl_1);
        pnl_1.setLayout(pnl_1Layout);
        pnl_1Layout.setHorizontalGroup(
            pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_KQTongHD, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_TongSoHoaDon, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_1Layout.setVerticalGroup(
            pnl_1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbl_TongSoHoaDon, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_KQTongHD, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_2.setBackground(new Color(255,255,255));
        pnl_2.setBorder(BorderFactory.createLineBorder(Color.black));
        pnl_2.setPreferredSize(new Dimension(215, 177));

        lbl_TongDoanhThu.setFont(new Font("Calibri", 1, 24)); // NOI18N
        lbl_TongDoanhThu.setForeground(new Color(0,0,0));
        lbl_TongDoanhThu.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TongDoanhThu.setText("Tổng doanh thu");

        lbl_KQTongDoanhThu.setFont(new Font("Calibri", 1, 48)); // NOI18N
        lbl_KQTongDoanhThu.setForeground(new Color(0,0,0));
        lbl_KQTongDoanhThu.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_KQTongDoanhThu.setText("0");

        GroupLayout pnl_2Layout = new GroupLayout(pnl_2);
        pnl_2.setLayout(pnl_2Layout);
        pnl_2Layout.setHorizontalGroup(
            pnl_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_KQTongDoanhThu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_TongDoanhThu, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_2Layout.setVerticalGroup(
            pnl_2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pnl_2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbl_TongDoanhThu, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_KQTongDoanhThu, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dc_TuNgay.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dc_TuNgayPropertyChange(evt);
            }
        });

        dc_DenNgay.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dc_DenNgayPropertyChange(evt);
            }
        });

        cb_TatCa.setBackground(new Color(255, 255, 255));
        cb_TatCa.setFont(new Font("Calibri", 0, 12)); // NOI18N
        cb_TatCa.setText("Tất cả");
        cb_TatCa.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_TatCaItemStateChanged(evt);
            }
        });

        lbl_TenNhanVien.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_TenNhanVien.setText("Tên nhân viên:");

        cb_TenNhanVien.setFont(new Font("Calibri", 0, 18)); // NOI18N
        cb_TenNhanVien.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_TenNhanVien.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_TenNhanVienItemStateChanged(evt);
            }
        });

        lbl_TenKhachHang.setFont(new Font("Calibri", 0, 12)); // NOI18N
        lbl_TenKhachHang.setText("Tên khách hàng:");

        cb_TenKhachHang.setFont(new Font("Calibri", 0, 18)); // NOI18N
        cb_TenKhachHang.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cb_TenKhachHang.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cb_TenKhachHangItemStateChanged(evt);
            }
        });
        cb_TenKhachHang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cb_TenKhachHangActionPerformed(evt);
            }
        });
        setLayout(null);
        add(scr_1);
        add(pnl_1);
        add(pnl_2);
        add(lbl_TenNhanVien);
        add(cb_TenNhanVien);
        add(lbl_1);
        add(dc_TuNgay);
        add(lbl_2);
        add(dc_DenNgay);
        add(cb_TatCa);
        add(lbl_TenKhachHang);
        add(cb_TenKhachHang);
        
        JButton btnXuatBaoCao = new JButton("Xuất Báo Cáo");
        btnXuatBaoCao.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btnXuatBaoCao.setBackground(new Color(255, 255, 255));
        btnXuatBaoCao.setFont(new Font("Arial", Font.PLAIN, 13));
        btnXuatBaoCao.setBounds(423, 620, 130, 54);
        add(btnXuatBaoCao);
        btnXuatBaoCao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuatBaoCaoPDF();
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    protected void xuatBaoCaoPDF() {
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
            Paragraph title = new Paragraph("BẢNG THỐNG KÊ HOÁ ĐƠN", font);
            title.setFont(new com.itextpdf.text.Font(BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, com.itextpdf.text.Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Thêm dữ liệu từ bảng
            PdfPTable table = new PdfPTable(tbl_DanhSachHoaDon.getColumnCount());
            table.setWidthPercentage(100);
            
            for (int col = 0; col < tbl_DanhSachHoaDon.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(tbl_DanhSachHoaDon.getColumnName(col)), font));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            // Thêm dữ liệu từ bảng tbl_DanhSachHoaDon
            for (int i = 0; i < tbl_DanhSachHoaDon.getRowCount(); i++) {
                for (int j = 0; j < tbl_DanhSachHoaDon.getColumnCount(); j++) {
                    table.addCell(new Phrase(tbl_DanhSachHoaDon.getValueAt(i, j).toString(), font));
                }
            }

            document.add(table);

            // Thêm thông tin thống kê
            document.add(new Paragraph("Tổng doanh thu: " + lbl_KQTongDoanhThu.getText(), font));
            document.add(new Paragraph("Tổng hóa đơn: " + lbl_KQTongHD.getText(),font));
            if (!cb_TenNhanVien.getSelectedItem().toString().equals("Tất cả")) {
                document.add(new Paragraph("Tên nhân viên: " + cb_TenNhanVien.getSelectedItem().toString(),font));
            }
            if (!cb_TatCa.isSelected() && dc_TuNgay.getDate() != null && dc_DenNgay.getDate() != null) {
                Paragraph timeRange = new Paragraph("Thời gian từ Ngày: " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dc_TuNgay.getDate()) +
                        " đến Ngày: " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dc_DenNgay.getDate()), font);
                document.add(timeRange);
            }
            
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

	private void cb_TenNhanVienItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_TenNhanVienItemStateChanged
        if(cb_TatCa.isSelected()){
            tableHoaDon();
        }else if(!cb_TatCa.isSelected()){
            tableHoaDonTheoTime();
        }
    }//GEN-LAST:event_cb_TenNhanVienItemStateChanged

    private void cb_TenKhachHangItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_TenKhachHangItemStateChanged
        // TODO add your handling code here:
        if(cb_TatCa.isSelected()){
            tableHoaDon();
        }else if(!cb_TatCa.isSelected()){
            tableHoaDonTheoTime();
        }
    }//GEN-LAST:event_cb_TenKhachHangItemStateChanged

    private void cb_TatCaItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cb_TatCaItemStateChanged
        // TODO add your handling code here:
        if(cb_TatCa.isSelected()){
            tableHoaDon();
        }else if(!cb_TatCa.isSelected()){
            tableHoaDonTheoTime();
        }
    }//GEN-LAST:event_cb_TatCaItemStateChanged

    private void dc_TuNgayPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dc_TuNgayPropertyChange
        // TODO add your handling code here:
        if(!isTuNgayValid()) return; 
        
         if(cb_TatCa.isSelected()){
            tableHoaDon();
        }else if(!cb_TatCa.isSelected()){
            tableHoaDonTheoTime();
        }
    }//GEN-LAST:event_dc_TuNgayPropertyChange

    private void dc_DenNgayPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dc_DenNgayPropertyChange
        // TODO add your handling code here:
        if(!isDenNgayValid()) return;
        
         if(cb_TatCa.isSelected()){
            tableHoaDon();
        }else if(!cb_TatCa.isSelected()){
            tableHoaDonTheoTime();
        }
    }//GEN-LAST:event_dc_DenNgayPropertyChange

    private void cb_TenKhachHangActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cb_TenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_TenKhachHangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox cb_TatCa;
    private JComboBox<String> cb_TenKhachHang;
    private JComboBox<String> cb_TenNhanVien;
    private JDateChooser dc_DenNgay;
    private JDateChooser dc_TuNgay;
    private JLabel lbl_1;
    private JLabel lbl_2;
    private JPanel pnl_1;
    private JPanel pnl_2;
    private JScrollPane scr_1;
    private JLabel lbl_KQTongDoanhThu;
    private JLabel lbl_KQTongHD;
    private JLabel lbl_TenKhachHang;
    private JLabel lbl_TenNhanVien;
    private JLabel lbl_TongDoanhThu;
    private JLabel lbl_TongSoHoaDon;
    private JTable tbl_DanhSachHoaDon;
}
