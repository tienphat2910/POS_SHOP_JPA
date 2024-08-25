package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import entities.HoaDon;

public interface HoaDonDAO extends Remote{
	public boolean addHoaDon(HoaDon hoaDon) throws RemoteException;
	public boolean updateHoaDon(HoaDon hoaDon)throws RemoteException;
	public boolean deleteHoaDon(String id)throws RemoteException;
	public List<HoaDon> getAllHoaDon()throws RemoteException;
	public HoaDon findById(String id) throws RemoteException;
	public int editNVTrongHD(HoaDon hoaDon) throws RemoteException;
	public List<HoaDon> getHDCho() throws RemoteException;
	public boolean updateHoaDon(String maHD, int trangthai, double tongtien) throws RemoteException;
	public List<HoaDon> getAllHoaDonTheoTenNVKH(String tenNhanVien, String tenKhachHang) throws RemoteException;

	List<HoaDon> getAllHoaDonTheoTenKHandNVTrongNgay(String tenKhachHang, String tenNhanVien, String tuNgay,
			String denNgay) throws RemoteException;
	//
	List<HoaDon> doTuBang() throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoa(String tukhoa) throws RemoteException;
	List<HoaDon> getHoaDonTheoTongTien(int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoThang(int thang) throws RemoteException;
	List<HoaDon> getHoaDonTheoNam(int nam) throws RemoteException;
	List<HoaDon> getHoaDonTheoThangNam(int thang, int nam) throws RemoteException;
	List<HoaDon> getHoaDonTheoTongTienThang(int thang, int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoTongTienNam(int nam, int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoTongTienThangNam(int thang, int nam, int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaThang(String tukhoa, int thang) throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaNam(String tukhoa, int nam) throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaTongTien(String tukhoa, int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaThangTongTien(String tukhoa, int thang, int giaMin, int giaMax)
			throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaNamTongTien(String tukhoa, int nam, int giaMin, int giaMax) throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaThangNamTongTien(String tukhoa, int thang, int nam, int giaMin, int giaMax)
			throws RemoteException;
	List<HoaDon> getHoaDonTheoTuKhoaThangNam(String tukhoa, int thang, int nam) throws RemoteException;
}
