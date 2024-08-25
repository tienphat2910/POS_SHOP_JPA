package entities;

import java.io.Serializable;

import jakarta.persistence.*;

@NamedQueries({
		@NamedQuery(name = "ChiTietHoaDon.getTongTienById", query = "SELECT SUM(cthd.thanhTien) FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.maHD = :id GROUP BY cthd.hoaDon.maHD"),
		@NamedQuery(name = "ChiTietHoaDon.updateSoLuongSPTrongGio", query = "UPDATE ChiTietHoaDon c SET c.soLuong = :soLuong, c.thanhTien = :thanhTien WHERE c.sanPham.maSP = :maSP AND c.hoaDon.maHD = :maHD"),
		@NamedQuery(name = "ChiTietHoaDon.getTongTienByMaHD", query = "SELECT SUM(cthd.thanhTien) FROM ChiTietHoaDon cthd "
				+ "WHERE cthd.hoaDon.maHD = :maHD"),
		//
		@NamedQuery(name = "ChiTietHoaDon.findByMaHD", query = "SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.maHD = :maHD") })

@Entity
@Table(name = "ChiTietHoaDon")
public class ChiTietHoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2486409991798763916L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "maHD")
	private HoaDon hoaDon;

	@ManyToOne
	@JoinColumn(name = "maSP")
	private SanPham sanPham;

	@Column(name = "soLuong")
	private int soLuong;
	@Column(name = "phanTramKhuyenMai")
	private double phanTramKhuyenMai;
	@Column(name = "thanhTien")
	private double thanhTien;

	public ChiTietHoaDon() {
		// TODO Auto-generated constructor stub
	}

	public ChiTietHoaDon(Long id, HoaDon hoaDon, int soLuong, double phanTramKhuyenMai, double thanhTien) {
		super();
		this.id = id;
		this.hoaDon = hoaDon;
		this.soLuong = soLuong;
		this.phanTramKhuyenMai = phanTramKhuyenMai;
		this.thanhTien = thanhTien;
	}

	public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, double phanTramKhuyenMai, double thanhTien) {
		super();
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.phanTramKhuyenMai = phanTramKhuyenMai;
		this.thanhTien = thanhTien;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getPhanTramKhuyenMai() {
		return phanTramKhuyenMai;
	}

	public void setPhanTramKhuyenMai(double phanTramKhuyenMai) {
		this.phanTramKhuyenMai = phanTramKhuyenMai;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	
	public double thanhTien() {
		if (sanPham != null) {
			double giaSanPham = sanPham.getGiaBan(); // Giả sử có phương thức getGia() trong class SanPham
			return soLuong * giaSanPham;
		} else {
			throw new IllegalArgumentException("Sản phẩm không được null");
		}
	}

	public Double tinhGiaSauKhuyenMai(Double giaBanGoc) {
		if (phanTramKhuyenMai == 0) {
			// Nếu không có khuyến mãi, giá sau khuyến mãi bằng giá gốc
			return giaBanGoc;
		} else {
			// Tính giá sau khuyến mãi
			return giaBanGoc * (1 - phanTramKhuyenMai / 100);
		}
	}

	public String toStringChiTietHoaDon() {
		return "ChiTietHoaDon [id=" + id + ", hoaDon=" + hoaDon + ", sanPham=" + sanPham + ", soLuong=" + soLuong
				+ ", phanTramKhuyenMai=" + phanTramKhuyenMai + ", thanhTien=" + thanhTien + "]";
	}

	@Override
	public String toString() {
		return String.format(
				"ChiTietHoaDon{id=%d, hoaDon='%s', sanPham='%s', soLuong=%d, phanTramKhuyenMai=%.2f, thanhTien=%.2f}",
				id, (hoaDon != null) ? hoaDon.getMaHD() : "null", (sanPham != null) ? sanPham.getTenSP() : "null",
				soLuong, phanTramKhuyenMai, thanhTien);
	}

}
