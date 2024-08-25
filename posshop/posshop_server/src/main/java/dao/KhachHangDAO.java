package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.KhachHang;


public interface KhachHangDAO extends Remote{
	public boolean addKhachHang(KhachHang khachHang) throws RemoteException;
	public boolean updateKhachHang(KhachHang khachHang)throws RemoteException;
	public boolean deleteKhachHang(String id)throws RemoteException;
	public List<KhachHang> getAllKhachHang()throws RemoteException;
	public List<KhachHang> getKhachHangTheoTen(String name) throws RemoteException;
	public KhachHang getKhachHangById(String id) throws RemoteException;
	public double getTongTienDaMuaCuaKH(String maKH) throws RemoteException;
	public List<KhachHang> timkh(String kh) throws RemoteException;
}
