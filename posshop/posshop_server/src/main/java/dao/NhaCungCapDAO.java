package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.NhaCungCap;

public interface NhaCungCapDAO extends Remote{
	public List<NhaCungCap> getAllNhaCungCap()throws RemoteException;
	public NhaCungCap getNhaCungCapByName(String name) throws RemoteException;
	//
	NhaCungCap getNhaCungCap(String id) throws RemoteException;
	List<NhaCungCap> getNhaCungCapByTen(String tenNhaCungCap) throws RemoteException;
	List<NhaCungCap> timKiemNCC(String tenNhaCungCap) throws RemoteException;
	int updateNhaCungCap(NhaCungCap nhaCungCap) throws RemoteException;
	public int editNhaCungCap(NhaCungCap nhaCungCap) throws RemoteException;
	boolean addNhaCungCap(NhaCungCap ncc) throws RemoteException;
	String getAutoID() throws RemoteException;
	
}
