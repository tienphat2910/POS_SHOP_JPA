package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.KhuyenMaiDAO;

@NamedQueries({ @NamedQuery(name = "KhuyenMai.findAll", query = "SELECT k FROM KhuyenMai k"),
		@NamedQuery(name = "KhuyenMai.getSanPhamMaKMIsNull", query = "SELECT sp FROM SanPham sp WHERE sp.khuyenMai IS NULL"),
		@NamedQuery(name = "KhuyenMai.getKhuyenMaiByPhanTram", query = "SELECT km FROM KhuyenMai km WHERE km.phanTramKhuyenMai = :phanTram"),
		@NamedQuery(name = "SanPham.getSanPhanTheoMaKM", query = "SELECT sp FROM SanPham sp WHERE sp.khuyenMai.maKM = :maKM"),
		@NamedQuery(name = "KhuyenMai.updateKhuyenMai1", query = "UPDATE KhuyenMai km SET km.phanTramKhuyenMai = :phanTram, km.tenKhuyenMai = :tenKhuyenMai, km.ngayBatDau = :ngayBatDau, km.ngayKetThuc = :ngayKetThuc WHERE km.maKM = :maKM"),
		@NamedQuery(name = "KhuyenMai.updateKhuyenMai2", query = "UPDATE SanPham sp SET sp.khuyenMai.maKM = :maKM WHERE sp.maSP = :maSP"),
		@NamedQuery(name = "KhuyenMai.deleteMaKMChoSanPham", query = "UPDATE SanPham sp SET sp.khuyenMai.maKM = NULL WHERE sp.maSP = :maSP") })

@Entity
@Table(name = "KhuyenMai")
public class KhuyenMai implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1682171728742898090L;

	@Id
	@Column(name = "maKM")
	private String maKM;

	@Column(name = "phanTramKhuyenMai")
	private double phanTramKhuyenMai;

	@Column(name = "tenKhuyenMai")
	private String tenKhuyenMai;

	@Column(name = "ngayBatDau")
	private LocalDate ngayBatDau;

	@Column(name = "ngayKetThuc")
	private LocalDate ngayKetThuc;

	@OneToMany(mappedBy = "khuyenMai")
	private Set<SanPham> sanPhams;

	public KhuyenMai() {
		// TODO Auto-generated constructor stub
	}

	public KhuyenMai(String maKM, double phanTramKhuyenMai, String tenKhuyenMai, LocalDate ngayBatDau,
			LocalDate ngayKetThuc) {
		super();
		this.maKM = maKM;
		this.phanTramKhuyenMai = phanTramKhuyenMai;
		this.tenKhuyenMai = tenKhuyenMai;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}

	public double getPhanTramKhuyenMai() {
		return phanTramKhuyenMai;
	}

	public void setPhanTramKhuyenMai(double phanTramKhuyenMai) {
		this.phanTramKhuyenMai = phanTramKhuyenMai;
	}

	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}

	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}

	public LocalDate getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(LocalDate ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public LocalDate getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(LocalDate ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

//	public void xoaKhuyenMaiKhiHetHan() throws RemoteException {
//		LocalDate currentDate = LocalDate.now();
//		KhuyenMaiDAO ds = new KhuyenMaiDAO() {
//
//			@Override
//			public boolean updateMaKMChoSanPHam(KhuyenMai km, String maKM, String maSP) throws RemoteException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public List<SanPham> getSanPhanTheoMaKM(String maKM) throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public List<SanPham> getSanPhamMaKMIsNull() throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public KhuyenMai getKhuyenMaiByPhanTram(Double phanTram) throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public KhuyenMai getKhuyenMai(String id) throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public String getAutoID() throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public List<KhuyenMai> doTuBang() throws RemoteException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean deleteMaKMChoSanPham(KhuyenMai km, String maSP) throws RemoteException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean deleteMaKMChoSanPHamHetHanKM(String maSP) throws RemoteException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean deleteKhuyenMai(String maKM) throws RemoteException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean createKhuyenMai(KhuyenMai km) throws RemoteException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		};
//		List<KhuyenMai> list = ds.doTuBang();
//		List<String> danhSachKhuyenMaiHetHan = new ArrayList<>();
//
//		// Lặp qua danh sách các khuyến mãi
//		for (KhuyenMai km : list) {
//			LocalDate endDate = km.getNgayKetThuc();
//			// Nếu ngày kết thúc lớn hơn hoặc bằng ngày hiện tại
//			if (currentDate.isAfter(endDate) || currentDate.isEqual(endDate)) {
//				// Thêm mã khuyến mãi hết hạn vào danh sách
//				danhSachKhuyenMaiHetHan.add(km.getMaKM());
//			}
//		}
//
//		// Nếu danh sách không rỗng
//		if (!danhSachKhuyenMaiHetHan.isEmpty()) {
//			for (String maKM : danhSachKhuyenMaiHetHan) {
//				// Lấy danh sách sản phẩm theo mã khuyến mãi
//				List<SanPham> danhSanPhamHetHanMaKM = ds.getSanPhanTheoMaKM(maKM);
//
//				// Xóa mã khuyến mãi cho các sản phẩm hết hạn khuyến mãi
//				for (SanPham sp : danhSanPhamHetHanMaKM) {
//					ds.deleteMaKMChoSanPHamHetHanKM(sp.getMaSP());
//				}
//
//				// Xóa khuyến mãi
//				ds.deleteKhuyenMai(maKM);
//			}
//		}
//	}

	@Override
	public String toString() {
		return "KhuyenMai [maKM=" + maKM + ", phanTramKhuyenMai=" + phanTramKhuyenMai + ", tenKhuyenMai=" + tenKhuyenMai
				+ ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + "]";
	}

}
