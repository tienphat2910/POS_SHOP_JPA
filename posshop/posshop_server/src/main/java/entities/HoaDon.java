package entities;

import jakarta.persistence.*;
import services.EntityManagerFactoryUtil;
import services.HoaDonService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;

import dao.HoaDonDAO;

@NamedQueries({ @NamedQuery(name = "HoaDon.findAll", query = "SELECT hd FROM HoaDon hd"),
		@NamedQuery(name = "HoaDon.findById", query = "SELECT hd FROM HoaDon hd WHERE hd.maHD = :id"),
		@NamedQuery(name = "HoaDon.updateMaKH", query = "UPDATE HoaDon h SET h.khachHang.maKH = :maKH WHERE h.maHD = :maHD"),
		@NamedQuery(name = "HoaDon.findUnprocessed", query = "SELECT h FROM HoaDon h WHERE h.trangThai = 0"),
		@NamedQuery(name = "HoaDon.findAllByTenKhachHangAndTenNhanVienAndNgayLapBetween", query = "SELECT h FROM HoaDon h "
				+ "JOIN h.nhanVien nv " + "JOIN h.khachHang kh " + "WHERE LOWER(nv.tenNV) LIKE LOWER(:tenNhanVien) "
				+ "AND LOWER(kh.tenKH) LIKE LOWER(:tenKhachHang) " + "AND h.maHD NOT LIKE 'HDC%' "
				+ "AND h.ngayLap BETWEEN :tuNgay AND :denNgay"),
		@NamedQuery(name = "HoaDon.getHoaDonByTrangThai", query = "SELECT hd FROM HoaDon hd JOIN hd.nhanVien nv JOIN hd.khachHang kh WHERE hd.trangThai = 1"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH)
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoa", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE CONCAT('%', :tukhoa, '%') OR hd.nhanVien.maNV LIKE CONCAT('%', :tukhoa, '%') OR nv.tenNV LIKE CONCAT('%', :tukhoa, '%') OR hd.khachHang.maKH LIKE CONCAT('%', :tukhoa, '%') OR kh.tenKH LIKE CONCAT('%', :tukhoa, '%'))"),
		// Tìm danh sách hóa đơn theo tổng tiền
		@NamedQuery(name = "HoaDon.getHoaDonTheoTongTien", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm danh sách hóa đơn theo tháng
		@NamedQuery(name = "HoaDon.getHoaDonTheoThang", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND FUNCTION('MONTH', hd.ngayLap) = :thang"),
		// Tìm danh sách hóa đơn theo năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND FUNCTION('YEAR', hd.ngayLap) = :nam"),
		// Tìm danh sách hóa đơn theo cả tháng và năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoThangNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND FUNCTION('MONTH', hd.ngayLap) = :thang AND FUNCTION('YEAR', hd.ngayLap) = :nam"),
		// Tìm danh sách hóa đơn theo tổng tiền và tháng
		@NamedQuery(name = "HoaDon.getHoaDonTheoTongTienThang", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND MONTH(hd.ngayLap) = :thang AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm danh sách hóa đơn theo tổng tiền và năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoTongTienNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND YEAR(hd.ngayLap) = :nam AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm danh sách hóa đơn theo tổng tiền, tháng và năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoTongTienThangNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND MONTH(hd.ngayLap) = :thang AND YEAR(hd.ngayLap) = :nam AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tháng
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaThang", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND MONTH(hd.ngayLap) = :thang"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND YEAR(hd.ngayLap) = :nam"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tổng tiền
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaTongTien", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), tháng và tổng
		// tiền
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaThangTongTien", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND MONTH(hd.ngayLap) = :thang AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), năm và tổng
		// tiền
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaNamTongTien", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND YEAR(hd.ngayLap) = :nam AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang, năm và
		// tổng tiền
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaThangNamTongTien", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND MONTH(hd.ngayLap) = :thang AND YEAR(hd.ngayLap) = :nam AND hd.tongTien BETWEEN :giaMin AND :giaMax"),
		// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang và năm
		@NamedQuery(name = "HoaDon.getHoaDonTheoTuKhoaThangNam", query = "SELECT hd FROM HoaDon hd JOIN FETCH hd.nhanVien nv JOIN FETCH hd.khachHang kh WHERE hd.trangThai = 1 AND (hd.maHD LIKE :tukhoa OR hd.nhanVien.maNV LIKE :tukhoa OR nv.tenNV LIKE :tukhoa OR hd.khachHang.maKH LIKE :tukhoa OR kh.tenKH LIKE :tukhoa) AND MONTH(hd.ngayLap) = :thang AND YEAR(hd.ngayLap) = :nam") })
@Entity
@Table(name = "HoaDon")
public class HoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 360350516991167283L;

	@Id
	@Column(name = "maHD")
	private String maHD;

	@Column(name = "ngayLap")
	private LocalDate ngayLap;

	@ManyToOne
	@JoinColumn(name = "maNV")
	private NhanVien nhanVien;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maKH")
	private KhachHang khachHang;

	@Column(name = "trangThai")
	private int trangThai;
	@Column(name = "tongTien")
	private double tongTien;
	@OneToMany(mappedBy = "hoaDon", fetch = FetchType.EAGER)
	private List<ChiTietHoaDon> chiTietHoaDon;

	public HoaDon() {
		// TODO Auto-generated constructor stub
	}

	public HoaDon(String maHD, LocalDate ngayLap, int trangThai, double tongTien) {
		super();
		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.trangThai = trangThai;
		this.tongTien = tongTien;
	}

//	HoaDon(mahd, kh, nv)
	public HoaDon(String maHD, NhanVien nhanVien, KhachHang khachHang) {
		super();
		this.maHD = maHD;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
	}

	public HoaDon(String maHD, LocalDate ngayLap, NhanVien nhanVien, KhachHang khachHang, int trangThai,
			double tongTien) {
		super();
		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.trangThai = trangThai;
		this.tongTien = tongTien;

	}

	public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public LocalDate getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public List<ChiTietHoaDon> getChiTietHoaDon() {
		return chiTietHoaDon;
	}

	public void setChiTietHoaDon(List<ChiTietHoaDon> chiTietHoaDon) {
		this.chiTietHoaDon = chiTietHoaDon;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	@Override
	public String toString() {
		return String.format(
				"HoaDon{maHD='%s', ngayLap='%s', nhanVien='%s', khachHang='%s', trangThai='%s', tongTien=%f, chiTietHoaDon='%s'}",
				maHD, ngayLap, nhanVien, khachHang, trangThai, tongTien, chiTietHoaDon);
	}

}
