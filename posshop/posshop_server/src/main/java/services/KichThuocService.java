package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.KichThuocDAO;
import entities.KichThuoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class KichThuocService extends UnicastRemoteObject implements KichThuocDAO {
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public KichThuocService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;

	}

	@Override
	public boolean addKichThuoc(KichThuoc kichThuoc) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(kichThuoc);
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
	public boolean updateKichThuoc(KichThuoc kichThuoc) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(kichThuoc);
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
	public boolean deleteKichThuoc(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			KichThuoc kichThuoc = entityManager.find(KichThuoc.class, id);
			entityManager.remove(kichThuoc);
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
	public List<KichThuoc> getAllKichThuoc() throws RemoteException {
		return entityManager.createNamedQuery("KichThuoc.findAll", KichThuoc.class).getResultList();
	}
	
	@Override
    public KichThuoc getKichThuocByName(String name) throws RemoteException {
        try {
            TypedQuery<KichThuoc> query = entityManager.createNamedQuery("KichThuoc.findByName", KichThuoc.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
