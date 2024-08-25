package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import dao.TaiKhoanDAO;

import entities.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class TaiKhoanService extends UnicastRemoteObject implements TaiKhoanDAO {
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public TaiKhoanService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addTaiKhoan(TaiKhoan taiKhoan) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(taiKhoan);
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
	public boolean updateTaiKhoan(TaiKhoan taiKhoan) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(taiKhoan);
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
	public boolean deleteTaiKhoan(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			TaiKhoan taiKhoan = entityManager.find(TaiKhoan.class, id);
			entityManager.remove(taiKhoan);
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
	public List<TaiKhoan> getAllTaiKhoan() throws RemoteException {
		return entityManager.createNamedQuery("TaiKhoan.findAll", TaiKhoan.class).getResultList();
	}

	@Override
    public List<TaiKhoan> getAllTaiKhoanTimKiem(String timkiem) throws RemoteException {
        try {
            TypedQuery<TaiKhoan> query = entityManager.createNamedQuery("TaiKhoan.findByNameOrNhanVien", TaiKhoan.class);
            query.setParameter("timkiem", "%" + timkiem + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	//
	public List<TaiKhoan> getTaiKhoan(String tk, String mk) throws RemoteException {
		List<TaiKhoan> listTaiKhoan = new ArrayList<>();
		try {
			TypedQuery<TaiKhoan> query = entityManager.createNamedQuery("TaiKhoan.getTaiKhoan", TaiKhoan.class);
			query.setParameter("tenTaiKhoan", tk);
			query.setParameter("matKhau", mk);
			listTaiKhoan = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTaiKhoan;
	}

	public String getEmailTheoTenTaiKhoan(String tenTK) throws RemoteException {
		String result = null;
		try {
			TypedQuery<String> query = entityManager.createNamedQuery("TaiKhoan.getEmailTheoTenTaiKhoan", String.class);
			query.setParameter("tenTaiKhoan", tenTK);
			result = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateMatKhau(String matKhau, String tenTK) throws RemoteException {
		try {
			entityManager.createNamedQuery("TaiKhoan.updateMatKhau").setParameter("matKhau", matKhau)
					.setParameter("tenTaiKhoan", tenTK).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getTenTaiKhoanTheoMatKhau(String matKhau) throws RemoteException {
		String result = null;
		try {
			TypedQuery<String> query = entityManager.createNamedQuery("TaiKhoan.getTenTaiKhoanTheoMatKhau",
					String.class);
			query.setParameter("matKhau", matKhau);
			result = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}