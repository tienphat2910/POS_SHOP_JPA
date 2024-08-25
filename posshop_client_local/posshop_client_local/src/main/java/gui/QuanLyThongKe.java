package gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class QuanLyThongKe extends JPanel{
	private TabThongKeDoanhThu tabDT = new TabThongKeDoanhThu();
	private TabThongKeSanPham tabTKSP = new TabThongKeSanPham();
	private TabThongKeHoaDon tabHD = new TabThongKeHoaDon();
	

	public QuanLyThongKe() {
		loadDataInBackground(tabTKSP);
        loadDataInBackground(tabDT);
        loadDataInBackground(tabHD);
//		tabTKSP.setVisible(true);
//		tabDT.setVisible(true);
//		tabHD.setVisible(true);
//		tabDT.setVisible(true);
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(934, 685));
		setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "name_34377947137900");
		
		JPanel pnlSanPham = new JPanel();
		pnlSanPham.setFont(new Font("Arial", Font.BOLD, 15));
		pnlSanPham.setVisible(true);
		tabbedPane.addTab("Sản Phẩm", null, pnlSanPham, null);
		pnlSanPham.setLayout(new CardLayout(0, 0));
		tabbedPane.setEnabledAt(0, true);
		pnlSanPham.removeAll();
		pnlSanPham.add(tabTKSP, BorderLayout.CENTER);
		pnlSanPham.revalidate();
		
		JPanel pnlDoanhThu = new JPanel();
		tabbedPane.addTab("Doanh Thu", null, pnlDoanhThu, null);
		pnlDoanhThu.setLayout(new CardLayout(0, 0));
		pnlDoanhThu.removeAll();
		pnlDoanhThu.setVisible(true);
		pnlDoanhThu.add(tabDT, BorderLayout.CENTER);
		pnlDoanhThu.revalidate();
		
		JPanel pnlHoaDon = new JPanel();
		tabbedPane.addTab("Hóa đơn", null, pnlHoaDon, null);
		pnlHoaDon.setLayout(new CardLayout(0, 0));
		pnlHoaDon.removeAll();
		pnlHoaDon.setVisible(true);
		pnlHoaDon.add(tabHD, BorderLayout.CENTER);
		pnlHoaDon.revalidate();
	}
	private void loadDataInBackground(final JPanel panel) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate loading data
//                Thread.sleep(2000); // For demonstration purposes

                // Add the panel to the tab
                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.add(panel, BorderLayout.CENTER);
                panel.setVisible(true);
                panel.revalidate();

                // Remove loading animation or message if necessary

                return null;
            }
        };

        worker.execute();
    }
}