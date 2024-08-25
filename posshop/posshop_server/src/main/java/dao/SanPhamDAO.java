package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entities.SanPham;

public interface SanPhamDAO extends Remote{
	public boolean addSanPham(SanPham sanPham)throws RemoteException;
	public boolean updateSanPham(SanPham sanPham)throws RemoteException;
	public boolean deleteSanPham(String id)throws RemoteException;
	public List<SanPham> getAllSanPham()throws RemoteException;
	public SanPham findById(String id)throws RemoteException;
	public List<SanPham> findByTenSP(String tenSP)throws RemoteException;
	public List<SanPham> getSanPhanTheoMaHD(String id) throws RemoteException;
	public List<SanPham> dsSPBanHang() throws RemoteException;
	public List<SanPham> getDSSPTheoMaSP(String name) throws RemoteException;
	public boolean SuaSlSP(int sl, String maSP) throws RemoteException;
	public List<SanPham> getAllSanPhamTheoThuocTinh(String maSanPham, String tenSanPham, String loaiSanPham, String mauSac,
			String kichCo) throws RemoteException;
	public List<SanPham> getAllSanPhamVuotDinhMuc(int soluong) throws RemoteException;
	public List<SanPham> getAllSanPhamDuoiDinhMuc(int soluong) throws RemoteException;
	public ArrayList<SanPham> topNSanPham() throws RemoteException;
	public ArrayList<SanPham> topNSanPhamBanCham() throws RemoteException;
	public ArrayList<SanPham> thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc)
			throws RemoteException;
	 ArrayList<SanPham> thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc,
			String tuNgay, String denNgay) throws RemoteException;
	 public List<SanPham> getSanPhanTheoPhanLoaiNull(String name) throws RemoteException;
	
}
