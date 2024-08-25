package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.MauSac;


public interface MauSacDAO extends Remote{
	public boolean addMauSac(MauSac mauSac) throws RemoteException;
	public boolean updateMauSac(MauSac mauSac) throws RemoteException;
	public boolean deleteMauSac(String id)throws RemoteException;
	public List<MauSac> getAllMauSac()throws RemoteException;
	public MauSac getMauSacByName(String name) throws RemoteException;
}
