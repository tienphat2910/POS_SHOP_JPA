package entities;

import java.io.Serializable;

import jakarta.persistence.*;

@NamedQuery(name = "TaiKhoan.findAll", query = "SELECT c FROM TaiKhoan c")
@NamedQuery(name = "TaiKhoan.findByNameOrNhanVien", query = "SELECT tk FROM TaiKhoan tk WHERE tk.tenTaiKhoan LIKE :timkiem OR tk.nhanVien.tenNV LIKE :timkiem")
//
@NamedQuery(name = "TaiKhoan.getTaiKhoan", query = "SELECT tk FROM TaiKhoan tk JOIN FETCH tk.nhanVien nv WHERE tk.tenTaiKhoan = :tenTaiKhoan AND tk.matKhau = :matKhau")
@NamedQuery(name = "TaiKhoan.getEmailTheoTenTaiKhoan", query = "SELECT nv.email FROM TaiKhoan tk JOIN tk.nhanVien nv WHERE tk.tenTaiKhoan = :tenTaiKhoan")
@NamedQuery(name = "TaiKhoan.updateMatKhau", query = "UPDATE TaiKhoan tk SET tk.matKhau = :matKhau WHERE tk.tenTaiKhoan = :tenTaiKhoan")
@NamedQuery(name = "TaiKhoan.getTenTaiKhoanTheoMatKhau", query = "SELECT tk.tenTaiKhoan FROM TaiKhoan tk WHERE tk.matKhau = :matKhau")
@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan implements Serializable {

	private static final long serialVersionUID = -5828313673992848492L;

	@Id
	@Column(name = "tenTaiKhoan")
	private String tenTaiKhoan;

	@Column(name = "matKhau")
	private String matKhau;

	@OneToOne
	@JoinColumn(name = "maNV")
	private NhanVien nhanVien;

	public TaiKhoan() {
	}

	public TaiKhoan(String tenTaiKhoan, String matKhau) {
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
	}

	public String getTenTaiKhoan() {
		if (nhanVien != null) {
			return nhanVien.getMaNV();
		}
		return null;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public String toString() {
		return "TaiKhoan [tenTaiKhoan=" + tenTaiKhoan + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + "]";
	}
}
