package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.KichThuoc;


public interface KichThuocDAO extends Remote{
	public boolean addKichThuoc(KichThuoc kichThuoc) throws RemoteException;
	public boolean updateKichThuoc(KichThuoc kichThuoc)throws RemoteException;
	public boolean deleteKichThuoc(String id)throws RemoteException;
	public List<KichThuoc> getAllKichThuoc()throws RemoteException;
	KichThuoc getKichThuocByName(String name) throws RemoteException;
}
