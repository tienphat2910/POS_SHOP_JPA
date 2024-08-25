package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.TaiKhoan;
import entities.XuatXu;



public interface XuatXuDAO extends Remote{
	public boolean addXuatXu(XuatXu xuatXu) throws RemoteException;
	public boolean updateXuatXu(XuatXu xuatXu)throws RemoteException;
	public boolean deleteXuatXu(String id)throws RemoteException;
	public List<XuatXu> getAllXuatXu()throws RemoteException;
	public XuatXu getXuatXuByName(String name) throws RemoteException;
	List<TaiKhoan> getAllTaiKhoanTimKiem(String timkiem) throws RemoteException;
}
