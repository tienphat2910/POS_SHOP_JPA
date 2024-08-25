package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.KhachHangDAO;
import entities.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class KhachHangService extends UnicastRemoteObject implements KhachHangDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public KhachHangService(EntityManager entityManager) throws RemoteException {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public boolean addKhachHang(KhachHang khachHang) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(khachHang);
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
	public boolean updateKhachHang(KhachHang khachHang) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(khachHang);
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
	public boolean deleteKhachHang(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			KhachHang khachHang = entityManager.find(KhachHang.class, id);
			entityManager.remove(khachHang);
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
	public List<KhachHang> getAllKhachHang() throws RemoteException {
		return entityManager.createNamedQuery("KhachHang.findAll", KhachHang.class).getResultList();
	}

	@Override
	public List<KhachHang> getKhachHangTheoTen(String name) throws RemoteException {
		return entityManager.createNamedQuery("KhachHang.getKhachHangTheoTen", KhachHang.class)
				.setParameter("name", "%" + name + "%").getResultList();
	}
	@Override
    public KhachHang getKhachHangById(String id) throws RemoteException {
        TypedQuery<KhachHang> query = entityManager.createNamedQuery("KhachHang.findById", KhachHang.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	@Override
	public double getTongTienDaMuaCuaKH(String maKH) throws RemoteException {
	    double tongTien = 0.0;
	    try {
	        entityManager.getTransaction().begin();

	        // Sử dụng named query để lấy tổng tiền đã mua của khách hàng có mã là maKH
	        TypedQuery<Double> query = entityManager.createNamedQuery("KhachHang.getTongTienDaMuaCuaKH", Double.class);
	        query.setParameter("maKH", maKH);
	        List<Double> resultList = query.getResultList();

	        // Nếu có kết quả, lấy tổng tiền từ kết quả đầu tiên
	        if (!resultList.isEmpty()) {
	            tongTien = resultList.get(0);
	        }

	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	    return tongTien;
	}
	@Override
    public List<KhachHang> timkh(String kh) throws RemoteException {
        try {
            TypedQuery<KhachHang> query = entityManager.createQuery(
                "SELECT kh FROM KhachHang kh WHERE kh.maKH LIKE :kh OR kh.tenKH LIKE :kh OR kh.email LIKE :kh OR kh.SDT LIKE :kh", 
                KhachHang.class);
            query.setParameter("kh", "%" + kh + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
