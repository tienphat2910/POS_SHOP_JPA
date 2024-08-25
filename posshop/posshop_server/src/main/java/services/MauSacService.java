package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.MauSacDAO;
import entities.MauSac;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class MauSacService extends UnicastRemoteObject implements MauSacDAO {
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	public MauSacService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addMauSac(MauSac mauSac) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(mauSac);
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
	public boolean updateMauSac(MauSac mauSac) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(mauSac);
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
	public boolean deleteMauSac(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			MauSac mauSac = entityManager.find(MauSac.class, id);
			entityManager.remove(mauSac);
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
	public List<MauSac> getAllMauSac() throws RemoteException {
		return entityManager.createNamedQuery("MauSac.findAll", MauSac.class).getResultList();
	}
	@Override
    public MauSac getMauSacByName(String name) throws RemoteException {
        try {
            TypedQuery<MauSac> query = entityManager.createNamedQuery("MauSac.findByName", MauSac.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
