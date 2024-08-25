package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.KhuyenMaiDAO;
import entities.KhuyenMai;
import entities.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class KhuyenMaiService extends UnicastRemoteObject implements KhuyenMaiDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public KhuyenMaiService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	// Lấy danh sách Khuyến Mãi từ SQL
	@Override
	public List<KhuyenMai> doTuBang() throws RemoteException {
		return entityManager.createNamedQuery("KhuyenMai.findAll", KhuyenMai.class).getResultList();
	}

	// Thêm khuyến mãi
	@Override
	public boolean createKhuyenMai(KhuyenMai km) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(km);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getAutoID() throws RemoteException {
		String maxID = null;
		try {
			// Sử dụng EntityManager để thao tác với CSDL
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<String> cq = cb.createQuery(String.class);
			Root<KhuyenMai> root = cq.from(KhuyenMai.class);
			cq.select(root.get("maKM"));
			cq.orderBy(cb.desc(root.get("maKM"))); // Sắp xếp giảm dần theo maKM
			TypedQuery<String> query = entityManager.createQuery(cq).setMaxResults(1);

			List<String> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				maxID = resultList.get(0);
				int num = Integer.parseInt(maxID.substring(2)) + 1;
				if (num < 10) {
					maxID = "KM0" + num;
				} else {
					maxID = "KM" + num;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxID;
	}

	// Lấy danh sách Sản phẩm có maKM = null từ SQL
	@Override
	public List<SanPham> getSanPhamMaKMIsNull() throws RemoteException {
		List<SanPham> dssp = new ArrayList<>();
		try {
			TypedQuery<SanPham> query = entityManager.createNamedQuery("KhuyenMai.getSanPhamMaKMIsNull", SanPham.class);
			dssp = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dssp;
	}

	// Tìm khuyến mãi bằng id
	@Override
	public KhuyenMai getKhuyenMai(String id) throws RemoteException {
		try {
			// Sử dụng EntityManager để thao tác với CSDL
			KhuyenMai khuyenMai = entityManager.find(KhuyenMai.class, id);
			return khuyenMai;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Tìm khuyến mãi theo phần trăm
	@Override
	public KhuyenMai getKhuyenMaiByPhanTram(Double phanTram) throws RemoteException {
		try {
			TypedQuery<KhuyenMai> query = entityManager.createNamedQuery("KhuyenMai.getKhuyenMaiByPhanTram",
					KhuyenMai.class);
			query.setParameter("phanTram", phanTram);
			List<KhuyenMai> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Tìm sản phẩm theo mã khuyến mãi
	@Override
	public List<SanPham> getSanPhanTheoMaKM(String maKM) throws RemoteException {
		List<SanPham> dssp = new ArrayList<>();

		try {
			TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.getSanPhanTheoMaKM", SanPham.class);
			query.setParameter("maKM", maKM);
			List<SanPham> resultList = query.getResultList();

			dssp.addAll(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dssp;
	}
	

	// Thêm mã khuyến mãi cho sản phẩm và Cập nhật các trường của chương trình
	// khuyến
	// mãi
	@Override
	public boolean updateMaKMChoSanPHam(KhuyenMai km, String maKM, String maSP) throws RemoteException {
		EntityTransaction trans = null;
		try {
			// Bắt đầu giao dịch
			trans = entityManager.getTransaction();
			trans.begin();

			// Thực hiện câu truy vấn cập nhật KhuyenMai
			Query updateKhuyenMaiQuery = entityManager.createNamedQuery("KhuyenMai.updateKhuyenMai1");
			updateKhuyenMaiQuery.setParameter("phanTram", km.getPhanTramKhuyenMai());
			updateKhuyenMaiQuery.setParameter("tenKhuyenMai", km.getTenKhuyenMai());
			updateKhuyenMaiQuery.setParameter("ngayBatDau", km.getNgayBatDau());
			updateKhuyenMaiQuery.setParameter("ngayKetThuc", km.getNgayKetThuc());
			updateKhuyenMaiQuery.setParameter("maKM", maKM);
			updateKhuyenMaiQuery.executeUpdate();

			// Thực hiện câu truy vấn cập nhật SanPham
			Query updateSanPhamQuery = entityManager.createNamedQuery("KhuyenMai.updateKhuyenMai2");
			updateSanPhamQuery.setParameter("maKM", maKM);
			updateSanPhamQuery.setParameter("maSP", maSP);
			updateSanPhamQuery.executeUpdate();

			// Commit giao dịch
			trans.commit();

			return true;
		} catch (Exception e) {
			// Nếu có lỗi xảy ra, rollback giao dịch
			if (trans != null && trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	

	// Xóa mã khuyến mãi cho sản phẩm và Cập nhật các trường của chương trình khuyến
	// mãi
	@Override
	public boolean deleteMaKMChoSanPham(KhuyenMai km, String maSP) throws RemoteException {
		EntityTransaction trans = null;
		try {
			trans = entityManager.getTransaction();
			trans.begin();

			// Thực hiện câu truy vấn cập nhật KhuyenMai
			Query updateKhuyenMaiQuery = entityManager.createNamedQuery("KhuyenMai.updateKhuyenMai1");
			updateKhuyenMaiQuery.setParameter("phanTram", km.getPhanTramKhuyenMai());
			updateKhuyenMaiQuery.setParameter("tenKhuyenMai", km.getTenKhuyenMai());
			updateKhuyenMaiQuery.setParameter("ngayBatDau", km.getNgayBatDau());
			updateKhuyenMaiQuery.setParameter("ngayKetThuc", km.getNgayKetThuc());
			updateKhuyenMaiQuery.setParameter("maKM", km.getMaKM());
			updateKhuyenMaiQuery.executeUpdate();

			// Update SanPham entity
			Query updateSanPhamQuery = entityManager.createNamedQuery("KhuyenMai.deleteMaKMChoSanPham");
			updateSanPhamQuery.setParameter("maSP", maSP);
			updateSanPhamQuery.executeUpdate();

			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans != null && trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	// Xóa khuyến mãi cho sản phẩm khi hết hạn
	@Override
	public boolean deleteMaKMChoSanPHamHetHanKM(String maSP) throws RemoteException {
		EntityTransaction trans = null;
		try {
			trans = entityManager.getTransaction();
			trans.begin();

			Query updateSanPhamQuery = entityManager.createNamedQuery("KhuyenMai.deleteMaKMChoSanPham");
			updateSanPhamQuery.setParameter("maSP", maSP);
			int n = updateSanPhamQuery.executeUpdate();

			trans.commit();
			return n > 0;
		} catch (Exception e) {
			if (trans != null && trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteKhuyenMai(String maKM) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			KhuyenMai khuyenMai = entityManager.find(KhuyenMai.class, maKM);
			entityManager.remove(khuyenMai);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
}
