package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@NamedQueries({
	@NamedQuery(name = "KhachHang.findAll", query = "SELECT kh FROM KhachHang kh"),
	@NamedQuery(name = "KhachHang.getKhachHangTheoTen", query = "SELECT kh FROM KhachHang kh WHERE kh.tenKH LIKE :name"),
	@NamedQuery(name = "KhachHang.findById",query = "SELECT kh FROM KhachHang kh WHERE kh.maKH = :id"),
	@NamedQuery(name = "KhachHang.getTongTienDaMuaCuaKH", query = "SELECT SUM(hd.tongTien) FROM HoaDon hd "
			+ "WHERE hd.trangThai = 1 AND hd.khachHang.maKH = :maKH GROUP BY hd.khachHang.maKH")
})
@Entity
@Table(name = "KhachHang")
public class KhachHang implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8876735701502938483L;

	@Id
    @Column(name = "maKH")
    private String maKH;

    @Column(name = "tenKH")
    private String tenKH;

    @Column(name = "gioiTinh")
    private boolean gioiTinh;

    @Column(name = "SDT")
    private String SDT;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.EAGER)
    private List<HoaDon> hoaDon;
    public KhachHang() {
		// TODO Auto-generated constructor stub
	}
	public KhachHang(String maKH, String tenKH, boolean gioiTinh, String sDT, String email) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.gioiTinh = gioiTinh;
		SDT = sDT;
		this.email = email;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
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

	public List<HoaDon> getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(List<HoaDon> hoaDon) {
		this.hoaDon = hoaDon;
	}

	@Override
	public String toString() {
	    return String.format("KhachHang{maKH='%s', tenKH='%s', SDT='%s', email='%s'}",
	            maKH, tenKH, SDT, email);
	}

    
}
