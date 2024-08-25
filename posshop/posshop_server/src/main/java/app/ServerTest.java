package app;

//Import các thư viện cần thiết
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import entities.SanPham;
import jakarta.persistence.EntityManager;
import services.EntityManagerFactoryUtil;
import services.SanPhamService;

public class ServerTest {

 public static void main(String[] args) {
     try {
    	 Registry registry = LocateRegistry.createRegistry(1233);

         // Tạo EntityManager từ EntityManagerFactoryUtil
         EntityManagerFactoryUtil entityManagerFactoryUtil = new EntityManagerFactoryUtil();
         // Tạo EntityManager
         EntityManager entityManager = entityManagerFactoryUtil.createEntityManager();
         // Tạo một đối tượng SanPhamService
         SanPhamService sanPhamService = new SanPhamService(entityManager);
         
         // Đăng ký đối tượng SanPhamService với registry
         registry.rebind("SanPhamService", sanPhamService);
         
         // Gọi phương thức testGetSanPhanTheoMaHD để kiểm tra
         testGetSanPhanTheoMaHD(sanPhamService);
         
         System.out.println("Server is ready.");
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public static void testGetSanPhanTheoMaHD(SanPhamService sanPhamService) throws RemoteException {
     // Gọi hàm getSanPhanTheoMaHD với một mã hóa đơn cụ thể
     String maHD = "HD001";
     List<SanPham> sanPhams = sanPhamService.getSanPhanTheoMaHD(maHD);
     
     // In ra thông tin của các sản phẩm trong danh sách
     for (SanPham sanPham : sanPhams) {
         System.out.println("Mã sản phẩm: " + sanPham.getMaSP());
         System.out.println("Tên sản phẩm: " + sanPham.getTenSP());
         System.out.println("Giá bán: " + sanPham.getGiaBan());
         System.out.println("---------------");
     }
 }
}
