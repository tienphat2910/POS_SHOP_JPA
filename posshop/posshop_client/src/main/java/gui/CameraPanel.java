package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.opencv.imgcodecs.Imgcodecs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CameraPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String temp;
    private JLabel cameraViewLabel;
    private VideoCapture capture;
    private List<QRCodeListener> listeners = new ArrayList<>();

    public CameraPanel() {
    	
        setLayout(new BorderLayout());
        
        cameraViewLabel = new JLabel();
        cameraViewLabel.setPreferredSize(new Dimension(300, 300));
        add(cameraViewLabel, BorderLayout.CENTER);
    }

    public void startCamera() {
        capture = new VideoCapture(0);

        SwingWorker<Void, Mat> worker = new SwingWorker<Void, Mat>() {

			@Override
            protected Void doInBackground() {
                if (!capture.isOpened()) {
                    System.out.println("Không thể mở camera.");
                } else {
                    System.out.println("Camera đang mở...");
                    
                    Mat frame = new Mat();
                    temp = "";
                    while (!isCancelled()) {
                        capture.read(frame);
                        if (!frame.empty()) {
                            BufferedImage image = convertMatToBufferedImage(frame);
                            cameraViewLabel.setIcon(new ImageIcon(image));
                            
                            // Quét mã QR từ hình ảnh
                            String qrText = readQRCode(image);
                            if (qrText != null) {
                            	if(!temp.equals(qrText)) {
                            		temp = qrText;
//                            		System.out.println("Mã QR: " + temp);
                            		playBeepSound();
                            		notifyListeners(temp);
                            	}
                                
                            }
                        }
                    }
                    capture.release();
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Mat> chunks) {
                if (!chunks.isEmpty()) {
                    Mat latestFrame = chunks.get(chunks.size() - 1);
                    BufferedImage image = convertMatToBufferedImage(latestFrame);
                    cameraViewLabel.setIcon(new ImageIcon(image));
                }
            }
        };

        worker.execute();
    }
    public void addQRCodeListener(QRCodeListener listener) {
        listeners.add(listener);
    }

    public void removeQRCodeListener(QRCodeListener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners(String qrCode) {
        for (QRCodeListener listener : listeners) {
            listener.onQRCodeRead(qrCode);
        }
    }
    public String getQRCodeValue() {
        return temp;
    }
    public interface QRCodeListener {
        void onQRCodeRead(String qrCode);
    }
    private static void playBeepSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data/amThanh/beep.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private String readQRCode(BufferedImage image) {
        if (image != null) {
            try {
                // Chuyển đổi BufferedImage thành hình ảnh grayscale để cải thiện việc đọc mã QR
                BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                grayscaleImage.getGraphics().drawImage(image, 0, 0, null);

                BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(grayscaleImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);
                return result.getText();
            } catch (NotFoundException e) {
                // Mã QR không được tìm thấy
                // Có thể in ra hoặc xử lý lỗi tại đây
                return null;
            }
        }
        return null;
    }

    private BufferedImage convertMatToBufferedImage(Mat frame) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (frame.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = frame.channels() * frame.cols() * frame.rows();
        byte[] buffer = new byte[bufferSize];
        frame.get(0, 0, buffer);

        BufferedImage image = new BufferedImage(frame.cols(), frame.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        int targetWidth = 174;
        int targetHeight = 118;
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledBufferedImage = new BufferedImage(targetWidth, targetHeight, type);
        scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

        return scaledBufferedImage;
    }
    private void displayImage(Mat frame) {
        BufferedImage image = convertMatToBufferedImage(frame);

        // Thay đổi kích thước của ảnh
        int targetWidth = 300;
        int targetHeight = 300;
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        // Hiển thị ảnh đã thay đổi kích thước trên cameraViewLabel
        cameraViewLabel.setIcon(imageIcon);
    }
    
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100, 100, 600, 400);

                    CameraPanel cameraPanel = new CameraPanel();
                    frame.getContentPane().add(cameraPanel);

                    frame.setVisible(true);
                    cameraPanel.startCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}