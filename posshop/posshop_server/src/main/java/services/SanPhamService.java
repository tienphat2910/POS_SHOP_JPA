package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.SanPhamDAO;
import entities.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class SanPhamService extends UnicastRemoteObject implements SanPhamDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public SanPhamService(EntityManager entityManager) throws RemoteException{
		this.entityManager = entityManager;
	}
	
	@Override
	public List<SanPham> getSanPhanTheoPhanLoaiNull(String name) throws RemoteException{
	    List<SanPham> dssp = new ArrayList<>();
	    try {
	        TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.getSanPhamTheoPhanLoaiNull", SanPham.class);
	        query.setParameter("name", name);
	        dssp = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dssp;
	}

	@Override
	public boolean addSanPham(SanPham sanPham) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(sanPham);
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
	public boolean updateSanPham(SanPham sanPham) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(sanPham);
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
	public boolean deleteSanPham(String id) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			SanPham sanPham = entityManager.find(SanPham.class, id);
			entityManager.remove(sanPham);
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
	 public List<SanPham> getAllSanPham() throws RemoteException{
		 return entityManager.createNamedQuery("SanPham.findAll", SanPham.class).getResultList();
	 }

	@Override
	public SanPham findById(String id) throws RemoteException{
		try {
            return entityManager.createNamedQuery("SanPham.findById", SanPham.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
	}

	@Override
	public List<SanPham> findByTenSP(String tenSP) throws RemoteException{
		return entityManager.createNamedQuery("SanPham.findByTenSP", SanPham.class)
                .setParameter("tenSP", tenSP)
                .getResultList();
	}

	@Override
    public List<SanPham> getSanPhanTheoMaHD(String id) {
        try {
            TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.findByMaHD", SanPham.class);
            query.setParameter("id", id);
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
	@Override
	 public List<SanPham> dsSPBanHang() throws RemoteException {
	     EntityTransaction trans = entityManager.getTransaction();
	     List<SanPham> dssp = new ArrayList<>();
	     try {
	         trans.begin();
	         TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.dsSPBanHang", SanPham.class);
	         dssp = query.getResultList();
	         trans.commit();
	     } catch (Exception e) {
	         if (trans.isActive()) {
	             trans.rollback();
	         }
	         e.printStackTrace();
	     }
	     return dssp;
	 }
	
	@Override
	 public List<SanPham> getDSSPTheoMaSP(String name) throws RemoteException {
	     EntityTransaction trans = entityManager.getTransaction();
	     List<SanPham> dssp = new ArrayList<>();
	     try {
	         trans.begin();
	         TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.getDSSPTheoMaSP", SanPham.class);
	         query.setParameter("name", "%" + name + "%");
	         dssp = query.getResultList();
	         trans.commit();
	     } catch (Exception e) {
	         if (trans.isActive()) {
	             trans.rollback();
	         }
	         e.printStackTrace();
	     }
	     return dssp;
	 }
	@Override
    public boolean SuaSlSP(int sl, String maSP) throws RemoteException {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            String jpql = "UPDATE SanPham sp SET sp.soLuong = sp.soLuong - :sl WHERE sp.maSP = :maSP";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("sl", sl);
            query.setParameter("maSP", maSP);

            int result = query.executeUpdate();

            transaction.commit();

            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
	@Override
    public List<SanPham> getAllSanPhamTheoThuocTinh(String maSanPham, String tenSanPham, String loaiSanPham, String mauSac, String kichCo) throws RemoteException {
        TypedQuery<SanPham> query = entityManager.createNamedQuery("SanPham.getAllSanPhamTheoThuocTinh", SanPham.class);
        query.setParameter("maSP", "%" + maSanPham + "%");
        query.setParameter("kichThuoc", "%" + kichCo + "%");
        query.setParameter("phanLoai", "%" + loaiSanPham + "%");
        query.setParameter("mauSac", "%" + mauSac + "%");
        query.setParameter("tenSP", "%" + tenSanPham + "%");
        return query.getResultList();
    }
	
	@Override
	public List<SanPham> getAllSanPhamVuotDinhMuc(int soluong) throws RemoteException {
	    try {
	        String jpql = "SELECT sp FROM SanPham sp " +
	                      "WHERE sp.soLuong > :soluong";
	        TypedQuery<SanPham> query = entityManager.createQuery(jpql, SanPham.class);
	        query.setParameter("soluong", soluong);

	        return query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>(); // Hoặc xử lý ngoại lệ theo ý của bạn
	    }
	}
	@Override
	public List<SanPham> getAllSanPhamDuoiDinhMuc(int soluong) throws RemoteException {
	    try {
	        String jpql = "SELECT sp FROM SanPham sp " +
	                      "WHERE sp.soLuong < :soluong";
	        TypedQuery<SanPham> query = entityManager.createQuery(jpql, SanPham.class);
	        query.setParameter("soluong", soluong);

	        return query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>(); // Hoặc xử lý ngoại lệ theo ý của bạn
	    }
	}
	
	@Override
    public ArrayList<SanPham> topNSanPham() throws RemoteException {
        try {
            String jpql = "SELECT sp, SUM(cthd.soLuong) AS tongSoLuong " +
                          "FROM ChiTietHoaDon cthd " +
                          "INNER JOIN cthd.hoaDon hd " +
                          "INNER JOIN cthd.sanPham sp " +
                          "GROUP BY sp.maSP " +
                          "ORDER BY SUM(cthd.soLuong) DESC";
            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setMaxResults(10);
            List<Object[]> resultList = query.getResultList();

            ArrayList<SanPham> listSanPham = new ArrayList<>();
            for (Object[] result : resultList) {
                SanPham sanPham = (SanPham) result[0];
                int tongSoLuong = ((Number) result[1]).intValue();
                sanPham.setSoLuong(tongSoLuong);
                listSanPham.add(sanPham);
            }
            return listSanPham;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Hoặc xử lý ngoại lệ theo ý của bạn
        }
    }

	@Override
	public ArrayList<SanPham> topNSanPhamBanCham() throws RemoteException {
	    try {
	        String jpql = "SELECT sp, SUM(cthd.soLuong) AS tongSoLuong " +
	                      "FROM ChiTietHoaDon cthd " +
	                      "INNER JOIN cthd.hoaDon hd " +
	                      "INNER JOIN cthd.sanPham sp " +
	                      "GROUP BY sp.maSP " +
	                      "ORDER BY SUM(cthd.soLuong) ASC";
	        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
	        query.setMaxResults(10);
	        List<Object[]> resultList = query.getResultList();

	        ArrayList<SanPham> listSanPham = new ArrayList<>();
	        for (Object[] result : resultList) {
	            SanPham sanPham = (SanPham) result[0];
	            int tongSoLuong = ((Number) result[1]).intValue();
	            sanPham.setSoLuong(tongSoLuong);
	            listSanPham.add(sanPham);
	        }
	        return listSanPham;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>(); // Hoặc xử lý ngoại lệ theo ý của bạn
	    }
	}
	
	@Override
    public ArrayList<SanPham> thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc) throws RemoteException {
        try {
            TypedQuery<SanPham> query = entityManager.createQuery(
                "SELECT sp FROM SanPham sp "
                + "INNER JOIN ChiTietHoaDon cthd ON cthd.sanPham.maSP = sp.maSP "
                + "INNER JOIN sp.kichThuoc kt "
                + "INNER JOIN sp.mauSac ms "
                + "INNER JOIN sp.phanLoai pl "
                + "WHERE kt.kichThuoc LIKE :kt "
                + "AND ms.mauSac LIKE :ms "
                + "AND pl.phanLoai LIKE :pl "
                + "GROUP BY sp.maSP", SanPham.class);
            query.setParameter("kt", "%" + kichThuoc + "%");
            query.setParameter("ms", "%" + mauSac + "%");
            query.setParameter("pl", "%" + phanLoai + "%");
            return new ArrayList<>(query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
	@Override
    public ArrayList<SanPham> thongKeDanhSachSanPhamVoiSoLuongBanDuoc(String mauSac, String phanLoai, String kichThuoc, String tuNgay, String denNgay) throws RemoteException {
        try {
            TypedQuery<SanPham> query = entityManager.createQuery(
                "SELECT sp FROM SanPham sp "
                + "JOIN sp.kichThuoc kt "
                + "JOIN sp.mauSac ms "
                + "JOIN sp.phanLoai pl "
                + "JOIN sp.chiTietHoaDon cthd "
                + "JOIN cthd.hoaDon hd "
                + "WHERE kt.kichThuoc LIKE :kichThuoc "
                + "AND ms.mauSac LIKE :mauSac "
                + "AND pl.phanLoai LIKE :phanLoai "
                + "AND hd.ngayLap >= :tuNgay "
                + "AND hd.ngayLap <= :denNgay "
                + "GROUP BY sp.maSP",
                SanPham.class
            );
            query.setParameter("kichThuoc", "%" + kichThuoc + "%");
            query.setParameter("mauSac", "%" + mauSac + "%");
            query.setParameter("phanLoai", "%" + phanLoai + "%");
            query.setParameter("tuNgay", tuNgay);
            query.setParameter("denNgay", denNgay);
            return new ArrayList<>(query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
