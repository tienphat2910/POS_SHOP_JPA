package component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeGenerator {

    public static void main(String[] args) throws WriterException {
//        generateBarcode("SP02","QUAN","SP02", "hinhanh/SP02.png");
    }

    public static void generateBarcode(String data, String barcodeName, String additionalInfo, String filePath) throws WriterException {
        Code128Writer writer = new Code128Writer();
        try {
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, 400, 200);

            int barcodeHeight = matrix.getHeight();
            int totalHeight = barcodeHeight + 80; // Tăng chiều cao thêm 80 pixel, 40 pixel trên và dưới mã vạch
            BufferedImage image = new BufferedImage(matrix.getWidth(), totalHeight, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrix.getWidth(), totalHeight);

            // Vẽ mã vạch với khoảng cách 40 pixel ở trên
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrix.getWidth(); i++) {
                for (int j = 40; j < matrix.getHeight() + 40; j++) {
                    if (matrix.get(i, j - 40)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            graphics.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int additionalInfoWidth = fontMetrics.stringWidth(additionalInfo);
            int x = (matrix.getWidth() - additionalInfoWidth) / 2;
            int y = totalHeight - 20;
            graphics.drawString(additionalInfo, x, y);

            graphics.drawString(barcodeName, 50, 20); // Vị trí dưới mã vạch

            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
