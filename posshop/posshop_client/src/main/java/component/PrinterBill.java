package component;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import component.*;

public class PrinterBill {
    public static void main(String[] args) {
        // Tạo danh sách sản phẩm
        List<sanPhamPrinter> sanPhamList = new ArrayList<>();
        sanPhamList.add(new sanPhamPrinter("Sản phẩm 1", 10.5, 2, 21.0));
        sanPhamList.add(new sanPhamPrinter("Sản phẩm 2", 15.75, 1, 15.75));

        // Tạo đối tượng hoaDonPrinter
        hoaDonPrinter hoaDon = new hoaDonPrinter("Tên nhân viên", "Tên khách hàng", 36.75, 0.0, 1.5, 38.25, 40.0, 1.75, sanPhamList);

        // In thông tin hóa đơn
        System.out.println(hoaDon.toString());

        // Tạo và in hóa đơn ra file PDF
        generatePDF("hd01",hoaDon, "src/component/viethoa.pdf");
        System.out.println("Hóa đơn đã được tạo thành công.");
    }

    public static void generatePDF(String hd, hoaDonPrinter hoaDon, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Sử dụng font Unicode (VnTime) để hỗ trợ tiếng Việt
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);

            document.open();

            document.add(new Paragraph("================= HÓA ĐƠN BÁN HÀNG TOM MEANSWEAR =================", font));
            document.add(new Paragraph("Nhân viên: " + hoaDon.getTennv(), font) );
            document.add(new Paragraph("Khách hàng: " + hoaDon.getTenkh(), font));
         // Lấy ngày giờ hiện tại
            LocalDateTime now = LocalDateTime.now();

            // Định dạng ngày giờ theo format mong muốn (ví dụ: "yyyy-MM-dd HH:mm:ss")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            // In ngày giờ đã định dạng
            System.out.println("Ngày giờ hiện tại: " + formattedDateTime);
            NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(new Locale("vi", "VN"));
            // Định dạng ngày giờ theo kiểu Việt Nam
            DateTimeFormatter vietnamFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("vi", "VN"));
            String vietnamDateTime = now.format(vietnamFormatter);

            // In ngày giờ theo kiểu Việt Nam
            document.add(new Paragraph(String.format("Mã Hóa đơn : %s - %s", hd,vietnamDateTime), font));
            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------", font));

            document.add(new Paragraph(String.format("%-20s \n %50s %20s %35s", "Tên sản phẩm", "Giá bán", "Số lượng", "Thành tiền"), font));
            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------", font));

            List<sanPhamPrinter> sanPhamList = hoaDon.getFields();
            for (sanPhamPrinter sanPham : sanPhamList) {
                document.add(new Paragraph(String.format("%-20s \n %50s%20d%40s",
                        sanPham.getTensp(), currencyFormat.format(sanPham.getGiaban()), sanPham.getSl(), currencyFormat.format(sanPham.getThanhtien())), font));
                
            }

            document.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------", font));

            document.add(new Paragraph(String.format("%-40s %70s", "Tổng tiền:",currencyFormat.format( hoaDon.getTongtien())), font));
            document.add(new Paragraph(String.format("%-40s %70s", "Giảm giá:",currencyFormat.format( hoaDon.getGiamgia())), font));
            document.add(new Paragraph(String.format("%-40s %70s", "Thuế:",currencyFormat.format( hoaDon.getThue())), font));
            document.add(new Paragraph(String.format("%-40s %70s", "Tổng thanh toán:",currencyFormat.format( hoaDon.getTienthanhtoan())), font));
            document.add(new Paragraph(String.format("%-40s %70s", "Khách đưa:",currencyFormat.format( hoaDon.getTienkhachdua())), font));
            document.add(new Paragraph(String.format("%-40s %70s", "Tiền thừa:",currencyFormat.format( hoaDon.getTienthua())), font));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}