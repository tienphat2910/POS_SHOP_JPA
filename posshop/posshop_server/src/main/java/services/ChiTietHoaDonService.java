package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietHoaDonDAO;
import entities.ChiTietHoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ChiTietHoaDonService extends UnicastRemoteObject implements ChiTietHoaDonDAO{
	private EntityManager entityManager;
	
	private static final long serialVersionUID = 1L;
	public ChiTietHoaDonService(EntityManager entityManager) throws RemoteException {
		super();
		
		this.entityManager = entityManager;
	}
//	@Override
//    public double getTongTien(String id) throws RemoteException {
//        double tongTien = 0;
//        try {
//            // Gọi named query đã được đặt tên là "ChiTietHoaDon.getTongTienById"
//            Query query = entityManager.createNamedQuery("ChiTietHoaDon.getTongTienById");
//            query.setParameter("id", id);
//
//            // Thực hiện truy vấn và lấy ra tổng tiền
//            Object result = query.getSingleResult();
//            if (result != null) {
//                tongTien = (Double) result;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Xử lý exception nếu cần
//        }
//        return tongTien;
//    }
	@Override
    public boolean updateSoLuongSPTrongGio(ChiTietHoaDon chiTietHoaDon) throws RemoteException {
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("UPDATE ChiTietHoaDon c SET c.soLuong = :soLuong, c.thanhTien = :thanhTien " +
                    "WHERE c.sanPham.maSP = :maSP AND c.hoaDon.maHD = :maHD")
                    .setParameter("soLuong", chiTietHoaDon.getSoLuong())
                    .setParameter("thanhTien", chiTietHoaDon.getThanhTien())
                    .setParameter("maSP", chiTietHoaDon.getSanPham().getMaSP())
                    .setParameter("maHD", chiTietHoaDon.getHoaDon().getMaHD())
                    .executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }
	
	@Override
    public List<ChiTietHoaDon> getChiTietHoaDonByMaHD(String maHD) throws RemoteException {
		EntityTransaction transaction = null;
        try {
        	transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<ChiTietHoaDon> query = entityManager.createQuery("SELECT cthd FROM ChiTietHoaDon cthd WHERE cthd.hoaDon.maHD LIKE :maHD", ChiTietHoaDon.class);
            query.setParameter("maHD", maHD);
            
            transaction.commit();
            return query.getResultList();
            
        } catch (Exception ex) {
        	if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return null;
        }
    }
	@Override
    public boolean deleteMotSP(ChiTietHoaDon chiTietHoaDon) throws RemoteException {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            
            String jpql = "DELETE FROM ChiTietHoaDon c WHERE c.sanPham.maSP = :maSP AND c.hoaDon.maHD = :maHD";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("maSP", chiTietHoaDon.getSanPham().getMaSP());
            query.setParameter("maHD", chiTietHoaDon.getHoaDon().getMaHD());
            
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            
            return rowsAffected > 0;
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return false;
        }
    }
	@Override
    public boolean addSanPhamVaoHD(ChiTietHoaDon chiTietHoaDon) throws RemoteException {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            
            entityManager.persist(chiTietHoaDon);
            
            transaction.commit();
            return true;
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return false;
        }
    }
	@Override
	 public double getTongTienByMaHD(String maHD) throws RemoteException {
	     try {
	         Query query = entityManager.createNamedQuery("ChiTietHoaDon.getTongTienByMaHD");
	         query.setParameter("maHD", maHD);
	         Double result = (Double) query.getSingleResult();
	         return result != null ? result : -1;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return -1;
	     }
	 }
	
	@Override
	public List<ChiTietHoaDon> getChiTietHoaDonTheoMaHD(String maHD) throws RemoteException {
		List<ChiTietHoaDon> dscthd = new ArrayList<>();
		try {
			TypedQuery<ChiTietHoaDon> query = entityManager.createNamedQuery("ChiTietHoaDon.findByMaHD",
					ChiTietHoaDon.class);
			query.setParameter("maHD", maHD);
			List<ChiTietHoaDon> results = query.getResultList();
			dscthd.addAll(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dscthd;
	}

}
