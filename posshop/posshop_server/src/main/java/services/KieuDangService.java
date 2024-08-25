package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.KieuDangDAO;
import entities.KieuDang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class KieuDangService extends UnicastRemoteObject implements KieuDangDAO {

	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public KieuDangService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addKieuDang(KieuDang kieuDang) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(kieuDang);
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
	public boolean updateKieuDang(KieuDang kieuDang) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(kieuDang);
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
	public boolean deleteKieuDang(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			KieuDang kieuDang = entityManager.find(KieuDang.class, id);
			entityManager.remove(kieuDang);
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
	public List<KieuDang> getAllKieuDang() throws RemoteException {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("KieuDang.findAll", KieuDang.class).getResultList();
	}
	@Override
    public KieuDang getKieuDangByName(String name) throws RemoteException {
        try {
            TypedQuery<KieuDang> query = entityManager.createNamedQuery("KieuDang.findByName", KieuDang.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}