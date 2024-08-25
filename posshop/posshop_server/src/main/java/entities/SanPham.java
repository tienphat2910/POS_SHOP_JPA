package entities;

import java.io.Serializable;

import jakarta.persistence.*;
import services.SanPhamService;

@NamedQueries({ @NamedQuery(name = "SanPham.findAll", query = "SELECT sp FROM SanPham sp"),
		@NamedQuery(name = "SanPham.findById", query = "SELECT s FROM SanPham s WHERE s.maSP = :id"),
		@NamedQuery(name = "SanPham.findByTenSP", query = "SELECT s FROM SanPham s WHERE s.tenSP = :tenSP"),
		@NamedQuery(name = "SanPham.findByMaHD", query = "SELECT cthd.sanPham FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.maHD = :id"),
		@NamedQuery(name = "SanPham.dsSPBanHang", query = "SELECT sp FROM SanPham sp WHERE sp.soLuong > 0"),
		@NamedQuery(name = "SanPham.getDSSPTheoMaSP", query = "SELECT sp FROM SanPham sp WHERE sp.soLuong > 0 AND (sp.maSP LIKE :name OR sp.tenSP LIKE :name)"),
		@NamedQuery(name = "SanPham.getAllSanPhamTheoThuocTinh", query = "SELECT sp FROM SanPham sp "
				+ "INNER JOIN sp.kichThuoc kt " + "INNER JOIN sp.mauSac ms " + "INNER JOIN sp.phanLoai pl "
				+ "WHERE sp.maSP LIKE :maSP " + "AND kt.kichThuoc LIKE :kichThuoc " + "AND pl.phanLoai LIKE :phanLoai "
				+ "AND ms.mauSac LIKE :mauSac " + "AND sp.tenSP LIKE :tenSP"),
		@NamedQuery(name = "SanPham.findAllOverLimit", query = "SELECT sp FROM SanPham sp WHERE sp.soLuong > :soluong"),
		@NamedQuery(name = "SanPham.getSanPhamTheoPhanLoaiNull", query = "SELECT sp FROM SanPham sp JOIN FETCH sp.phanLoai pl WHERE pl.phanLoai = :name AND sp.khuyenMai IS NULL") })

@Entity
@Table(name = "SanPham")
public class SanPham implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "maSP")
	private String maSP;

	@Column(name = "tenSP")
	private String tenSP;

	@Column(name = "giaNhap")
	private double giaNhap;

	@Column(name = "giaBan")
	private double giaBan;

	@ManyToOne
	@JoinColumn(name = "maNCC")
	private NhaCungCap nhaCungCap;

	@ManyToOne
	@JoinColumn(name = "maKM")
	private KhuyenMai khuyenMai;

	@Column(name = "soLuong")
	private int soLuong;

	@ManyToOne
	@JoinColumn(name = "maCL")
	private ChatLieu chatLieu;

	@ManyToOne
	@JoinColumn(name = "maKD")
	private KieuDang kieuDang;

	@ManyToOne
	@JoinColumn(name = "maKT")
	private KichThuoc kichThuoc;

	@ManyToOne
	@JoinColumn(name = "maMS")
	private MauSac mauSac;

	@ManyToOne
	@JoinColumn(name = "maXX")
	private XuatXu xuatXu;

	@ManyToOne
	@JoinColumn(name = "maPL")
	private PhanLoai phanLoai;

	@Column(name = "hinhAnh")
	private String hinhAnh;

	@Column(name = "loiTheoPhanTram")
	private int loiTheoPhanTram;

	@Column(name = "trangThai")
	private int trangThai;

	public SanPham() {
		// Không cần thực hiện gì trong constructor này
	}

	public SanPham(String maSP, String tenSP, double giaNhap, double giaBan, int soLuong, String hinhAnh,
			int loiTheoPhanTram, int trangThai) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.giaNhap = giaNhap;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.hinhAnh = hinhAnh;
		this.loiTheoPhanTram = loiTheoPhanTram;
		this.trangThai = trangThai;
	}

	public SanPham(String maSP, String tenSP, PhanLoai pl, double giaNhap, int loi, KhuyenMai khuyenMai, double giaBan,
			KichThuoc kichThuoc, int soLuong, MauSac mauSac, ChatLieu chatLieu, NhaCungCap nhaCungCap,
			KieuDang kieuDang, XuatXu xuatXu, String hinhAnh, int trangThai) {
		this.maSP = maSP; // Bạn cần đảm bảo phương thức getAutoID() đã được định nghĩa và hoạt động đúng
		this.tenSP = tenSP;
		this.phanLoai = pl;
		this.giaNhap = giaNhap;
		this.loiTheoPhanTram = loi;
		this.khuyenMai = khuyenMai;
		this.giaBan = giaBan;
		this.kichThuoc = kichThuoc;
		this.soLuong = soLuong;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
		this.nhaCungCap = nhaCungCap;
		this.kieuDang = kieuDang;
		this.xuatXu = xuatXu;
		this.hinhAnh = hinhAnh;
		this.trangThai = trangThai;
	}

	public SanPham(String maSP) {
		super();
		this.maSP = maSP;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public double getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public KhuyenMai getKhuyenMai() {
		return khuyenMai;
	}

	public void setKhuyenMai(KhuyenMai khuyenMai) {
		this.khuyenMai = khuyenMai;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ChatLieu getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(ChatLieu chatLieu) {
		this.chatLieu = chatLieu;
	}

	public KieuDang getKieuDang() {
		return kieuDang;
	}

	public void setKieuDang(KieuDang kieuDang) {
		this.kieuDang = kieuDang;
	}

	public KichThuoc getKichThuoc() {
		return kichThuoc;
	}

	public void setKichThuoc(KichThuoc kichThuoc) {
		this.kichThuoc = kichThuoc;
	}

	public MauSac getMauSac() {
		return mauSac;
	}

	public void setMauSac(MauSac mauSac) {
		this.mauSac = mauSac;
	}

	public XuatXu getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(XuatXu xuatXu) {
		this.xuatXu = xuatXu;
	}

	public PhanLoai getPhanLoai() {
		return phanLoai;
	}

	public void setPhanLoai(PhanLoai phanLoai) {
		this.phanLoai = phanLoai;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public int getLoiTheoPhanTram() {
		return loiTheoPhanTram;
	}

	public void setLoiTheoPhanTram(int loiTheoPhanTram) {
		this.loiTheoPhanTram = loiTheoPhanTram;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

//	public String getAutoID() {
////		SanPhamDAO sanPham_DAO = new SanPhamDAO();
//		SanPhamService sanPham_DAO = new SanPhamService()
//		String idPrefix = "SP";
//		int length = sanPham_DAO.doTuBang().size();
//		String finalId = idPrefix + String.format("%02d", length + 1);
//		return finalId;
//	}
	public double tinhGiaSauKhuyenMai(double giaSauLoi, double giamGiaTheoPhanTram) {
		// Áp dụng giảm giá
		double giaSauGiamGia = giaSauLoi - giaSauLoi * giamGiaTheoPhanTram / 100;

		return giaSauGiamGia;
	}

	@Override
	public String toString() {
		return "SanPham [maSP=" + maSP + ", tenSP=" + tenSP + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan
				+ ", nhaCungCap=" + nhaCungCap + ", khuyenMai=" + khuyenMai + ", soLuong=" + soLuong + ", chatLieu="
				+ chatLieu + ", kieuDang=" + kieuDang + ", kichThuoc=" + kichThuoc + ", mauSac=" + mauSac + ", xuatXu="
				+ xuatXu + ", phanLoai=" + phanLoai + ", hinhAnh=" + hinhAnh + ", loiTheoPhanTram=" + loiTheoPhanTram
				+ ", trangThai=" + trangThai + "]";
	}

}
