package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.KhuyenMai;
import entities.SanPham;


public interface KhuyenMaiDAO extends Remote{
	List<KhuyenMai> doTuBang() throws RemoteException;

	boolean createKhuyenMai(KhuyenMai km) throws RemoteException;

	boolean updateMaKMChoSanPHam(KhuyenMai km, String maKM, String maSP) throws RemoteException;

	List<SanPham> getSanPhamMaKMIsNull() throws RemoteException;

	String getAutoID() throws RemoteException;

	KhuyenMai getKhuyenMai(String id) throws RemoteException;

	KhuyenMai getKhuyenMaiByPhanTram(Double phanTram) throws RemoteException;

	List<SanPham> getSanPhanTheoMaKM(String maKM) throws RemoteException;

	boolean deleteMaKMChoSanPham(KhuyenMai km, String maSP) throws RemoteException;

	boolean deleteMaKMChoSanPHamHetHanKM(String maSP) throws RemoteException;

	boolean deleteKhuyenMai(String maKM) throws RemoteException;
}
