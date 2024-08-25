package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NamedQueries({
	@NamedQuery(name = "NhanVien.findAll", query = "SELECT nv FROM NhanVien nv"),
	@NamedQuery(name = "NhanVien.findById",query = "SELECT nv FROM NhanVien nv WHERE nv.maNV = :id"),
	@NamedQuery(name = "NhanVien.findByName", query = "SELECT nv FROM NhanVien nv WHERE nv.tenNV LIKE :name"),
	@NamedQuery(name = "NhanVien.findActive", query = "SELECT nv FROM NhanVien nv WHERE nv.trangThai = 0"),
	@NamedQuery(name = "NhanVien.findByConditions", 
	query = "SELECT nv FROM NhanVien nv WHERE nv.trangThai = 0 AND (nv.maNV LIKE :manvtensdt OR nv.tenNV LIKE :manvtensdt OR nv.SDT LIKE :manvtensdt) AND nv.gioiTinh = :gioitinh AND nv.chucVu = :chucvu"),
	@NamedQuery(
		    name = "NhanVien.getAllNhanVienNgungLam",
		    query = "SELECT nv FROM NhanVien nv WHERE nv.trangThai = :trangThai"
		)
})
@Entity
@Table(name = "NhanVien")
public class NhanVien implements Serializable{

	private static final long serialVersionUID = 6473162071359987461L;

	@Id
    @Column(name = "maNV")
    private String maNV;

    @Column(name = "tenNV")
    private String tenNV;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @Column(name = "SDT")
    private String SDT;

    @Column(name = "email")
    private String email;

    @Column(name = "CMND")
    private String CMND;

    @Column(name = "gioiTinh")
    private boolean gioiTinh;

    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = "chucVu")
    private boolean chucVu;

    @Column(name = "trangThai")
    private int trangThai;

    @OneToOne(mappedBy = "nhanVien")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> hoaDon;
    public NhanVien() {
		// TODO Auto-generated constructor stub
	}
	public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String sDT, String email, String cMND,
			boolean gioiTinh, String diaChi, boolean chucVu, int trangThai) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		SDT = sDT;
		this.email = email;
		CMND = cMND;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.chucVu = chucVu;
		this.trangThai = trangThai;
	}
	

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		CMND = cMND;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public boolean isChucVu() {
		return chucVu;
	}

	public void setChucVu(boolean chucVu) {
		this.chucVu = chucVu;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public List<HoaDon> getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(List<HoaDon> hoaDon) {
		this.hoaDon = hoaDon;
	}

	@Override
	public String toString() {
	    return String.format("NhanVien{maNV='%s', tenNV='%s', ngaySinh='%s', SDT='%s', email='%s', CMND='%s', gioiTinh='%s', diaChi='%s', chucVu='%s', trangThai='%s'}",
	            maNV, tenNV, ngaySinh, SDT, email, CMND, gioiTinh, diaChi, chucVu, trangThai);
	}

    
}

