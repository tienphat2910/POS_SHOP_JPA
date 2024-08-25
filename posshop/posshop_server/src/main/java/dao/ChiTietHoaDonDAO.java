package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.ChiTietHoaDon;

public interface ChiTietHoaDonDAO extends Remote{
//	public double getTongTien(String id)throws RemoteException;
	public boolean updateSoLuongSPTrongGio(ChiTietHoaDon chiTietHoaDon) throws RemoteException;
	public List<ChiTietHoaDon> getChiTietHoaDonByMaHD(String maHD) throws RemoteException;
	public boolean deleteMotSP(ChiTietHoaDon chiTietHoaDon) throws RemoteException;
	public boolean addSanPhamVaoHD(ChiTietHoaDon chiTietHoaDon) throws RemoteException;
	double getTongTienByMaHD(String maHD) throws RemoteException;
	//
	List<ChiTietHoaDon> getChiTietHoaDonTheoMaHD(String maHD) throws RemoteException;
}
