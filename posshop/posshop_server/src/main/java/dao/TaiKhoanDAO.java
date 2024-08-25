package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.TaiKhoan;


public interface TaiKhoanDAO extends Remote{
	public boolean addTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;
	public boolean updateTaiKhoan(TaiKhoan taiKhoan)throws RemoteException;
	public boolean deleteTaiKhoan(String id)throws RemoteException;
	public List<TaiKhoan> getAllTaiKhoan()throws RemoteException;
	List<TaiKhoan> getAllTaiKhoanTimKiem(String timkiem) throws RemoteException;
	//
	List<TaiKhoan> getTaiKhoan(String tk, String mk) throws RemoteException;
	String getEmailTheoTenTaiKhoan(String tenTK) throws RemoteException;
	boolean updateMatKhau(String matKhau, String tenTK) throws RemoteException;
	String getTenTaiKhoanTheoMatKhau(String matKhau) throws RemoteException;
}
