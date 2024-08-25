package entities;

import java.io.Serializable;

import jakarta.persistence.*;
@NamedQueries({
	@NamedQuery(name = "NhaCungCap.findAll", query = "SELECT c FROM NhaCungCap c"),
	@NamedQuery(name = "NhaCungCap.findByName", query = "SELECT ncc FROM NhaCungCap ncc WHERE ncc.tenNCC LIKE :name"),
	// @NamedQuery(name = "NhaCungCap.findByMaNCC", query = "SELECT ncc FROM NhaCungCap ncc WHERE ncc.maNCC = :id"),
	@NamedQuery(name = "NhaCungCap.findByTenNCC", query = "SELECT ncc FROM NhaCungCap ncc WHERE ncc.tenNCC LIKE :tenNhaCungCap"),
	@NamedQuery(name = "NhaCungCap.timKiemNCC", query = "SELECT ncc FROM NhaCungCap ncc WHERE ncc.maNCC LIKE :searchString OR ncc.tenNCC LIKE :searchString OR ncc.SDT LIKE :searchString OR ncc.email LIKE :searchString"),
	@NamedQuery(name = "NhaCungCap.findByMaNCC", query = "SELECT n FROM NhaCungCap n WHERE n.maNCC = :maNCC"),
	@NamedQuery(name = "NhaCungCap.updateNhaCungCap", query = "UPDATE NhaCungCap n SET n.tenNCC = :tenNCC, n.diaChi = :diaChi, n.email = :email, n.SDT = :sdt WHERE n.maNCC = :maNCC"),
	@NamedQuery(name = "NhaCungCap.editNhaCungCap", query = "UPDATE NhaCungCap n SET n.tenNCC = :tenNCC, n.SDT = :sdt, n.email = :email, n.diaChi = :diaChi WHERE n.maNCC = :maNCC")
})
@Entity
@Table(name = "NhaCungCap")
public class NhaCungCap implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2886077324378456027L;

	@Id
    @Column(name = "maNCC")
    private String maNCC;

    @Column(name = "tenNCC")
    private String tenNCC;

    @Column(name = "SDT")
    private String SDT;

    @Column(name = "email")
    private String email;

    @Column(name = "diaChi")
    private String diaChi;
    public NhaCungCap() {
		// TODO Auto-generated constructor stub
	}
	public NhaCungCap(String maNCC, String tenNCC, String sDT, String email, String diaChi) {
		super();
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		SDT = sDT;
		this.email = email;
		this.diaChi = diaChi;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
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

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@Override
	public String toString() {
		return "NhaCungCap [maNCC=" + maNCC + ", tenNCC=" + tenNCC + ", SDT=" + SDT + ", email=" + email + ", diaChi="
				+ diaChi + "]";
	}

    
}

