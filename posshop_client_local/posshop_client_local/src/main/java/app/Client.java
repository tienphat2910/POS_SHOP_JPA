package app;

import dao.ChatLieuDAO;
import dao.HoaDonDAO;
import dao.NhaCungCapDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import dao.TaiKhoanDAO;
import entities.ChatLieu;
import entities.HoaDon;
import entities.NhaCungCap;
import entities.NhanVien;
import entities.SanPham;
import entities.TaiKhoan;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class Client {
    public static void main(String[] args) {
        try {
            // Tạo kết nối tới registry RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1232);

//            NhanVienDAO nhanVienDAO = (NhanVienDAO) registry.lookup("NhanVienDAO");
//			 List<NhanVien> listNhanVien = nhanVienDAO.getAllNhanVien();
//			 for(NhanVien nv : listNhanVien) {
//				 System.out.println(nv);
//			 }
//            NhaCungCapDAO nhaCungCapDAO = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
//            NhaCungCap nhaCungCaps = nhaCungCapDAO.getNhaCungCapByName("Nhà cung cấp 3");
//            System.out.println(nhaCungCaps);

//			 NhanVien nv = nhanVienDAO.getNhanVienByName("iloveu");
//			 System.out.println(nv);
			 TaiKhoanDAO taiKhoanDAO = (TaiKhoanDAO) registry.lookup("TaiKhoanDAO");
			 List<TaiKhoan> taiKhoans = taiKhoanDAO.getAllTaiKhoan();
			 for(TaiKhoan tk : taiKhoans) {
				 System.out.println(tk);
			 }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
