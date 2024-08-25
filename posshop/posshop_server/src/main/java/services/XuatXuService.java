package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.XuatXuDAO;
import entities.TaiKhoan;
import entities.XuatXu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class XuatXuService extends UnicastRemoteObject implements XuatXuDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public XuatXuService(EntityManager entityManager) throws RemoteException {
		super();
		this.entityManager = entityManager;
	}
	@Override
	public boolean addXuatXu(XuatXu xuatXu) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(xuatXu);
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
	public boolean updateXuatXu(XuatXu xuatXu) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(xuatXu);
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
	public boolean deleteXuatXu(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			XuatXu xuatXu = entityManager.find(XuatXu.class, id);
			entityManager.remove(xuatXu);
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
	public List<XuatXu> getAllXuatXu() throws RemoteException {
		return entityManager.createNamedQuery("XuatXu.findAll", XuatXu.class).getResultList();
	}
	@Override
    public XuatXu getXuatXuByName(String name) throws RemoteException {
        try {
            TypedQuery<XuatXu> query = entityManager.createNamedQuery("XuatXu.findByName", XuatXu.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

}
