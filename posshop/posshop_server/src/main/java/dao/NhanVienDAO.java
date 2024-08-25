package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.NhanVien;

public interface NhanVienDAO extends Remote{
	public boolean addNhanVien(NhanVien nhanVien) throws RemoteException;
	public boolean updateNhanVien(NhanVien nhanVien) throws RemoteException;
	public boolean deleteNhanVien(String id)throws RemoteException;
	public List<NhanVien> getAllNhanVien()throws RemoteException;
	public NhanVien getNhanVienByID(String id) throws RemoteException;
	public NhanVien getNhanVienByName(String name) throws RemoteException;
	public List<NhanVien> getAllNhanVienConHoatDong() throws RemoteException;
	public List<NhanVien> timnv(String manvtensdt, boolean gioitinh, boolean chucvu) throws RemoteException;
	public List<NhanVien> getAllNhanVienNgungLam() throws RemoteException;
}
