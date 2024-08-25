package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import entities.KieuDang;

public interface KieuDangDAO extends Remote{
	public boolean addKieuDang(KieuDang kieuDang) throws RemoteException;
	public boolean updateKieuDang(KieuDang kieuDang) throws RemoteException;
	public boolean deleteKieuDang(String id)throws RemoteException;
	public List<KieuDang> getAllKieuDang()throws RemoteException;
	public KieuDang getKieuDangByName(String name) throws RemoteException;
}
