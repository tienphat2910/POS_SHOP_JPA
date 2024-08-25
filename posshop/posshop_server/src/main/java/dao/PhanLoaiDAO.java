package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.PhanLoai;

public interface PhanLoaiDAO extends Remote{
	public boolean addPhanLoai(PhanLoai phanLoai) throws RemoteException;
	public boolean updatePhanLoai(PhanLoai phanLoai)throws RemoteException;
	public boolean deletePhanLoai(String id)throws RemoteException;
	public List<PhanLoai> getAllPhanLoai()throws RemoteException;
	public PhanLoai getPhanLoaiByName(String name) throws RemoteException;
}
