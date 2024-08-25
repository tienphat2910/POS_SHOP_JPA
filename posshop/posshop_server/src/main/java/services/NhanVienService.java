package services;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.NhanVienDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import entities.KichThuoc;
import entities.NhanVien;

public class NhanVienService extends UnicastRemoteObject implements NhanVienDAO {
    private EntityManager entityManager;
    private static final long serialVersionUID = 1L;
    public NhanVienService(EntityManager entityManager) throws RemoteException {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public NhanVien getNhanVienByID(String id) throws RemoteException {
        try {
            return entityManager.createNamedQuery("NhanVien.findById", NhanVien.class)
                                 .setParameter("id", id)
                                 .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

	@Override
	public boolean addNhanVien(NhanVien nhanVien) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(nhanVien);
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
	public boolean updateNhanVien(NhanVien nhanVien) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(nhanVien);
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
	public boolean deleteNhanVien(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			NhanVien nhanVien = entityManager.find(NhanVien.class, id);
			entityManager.remove(nhanVien);
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
	public List<NhanVien> getAllNhanVien() throws RemoteException {
		// TODO Auto-generated method stub
		return entityManager.createNamedQuery("NhanVien.findAll", NhanVien.class).getResultList();
	}
	@Override
    public NhanVien getNhanVienByName(String name) throws RemoteException {
        try {
            return entityManager.createNamedQuery("NhanVien.findByName", NhanVien.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
        	ex.printStackTrace();
            return null;
        }
    }
	
	@Override
    public List<NhanVien> getAllNhanVienConHoatDong() throws RemoteException {
        try {
            TypedQuery<NhanVien> query = entityManager.createNamedQuery("NhanVien.findActive", NhanVien.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	@Override
    public List<NhanVien> timnv(String manvtensdt, boolean gioitinh, boolean chucvu) throws RemoteException {
        try {
            TypedQuery<NhanVien> query = entityManager.createNamedQuery("NhanVien.findByConditions", NhanVien.class);
            query.setParameter("manvtensdt", "%" + manvtensdt + "%");
            query.setParameter("gioitinh", gioitinh);
            query.setParameter("chucvu", chucvu);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	@Override
	public List<NhanVien> getAllNhanVienNgungLam() throws RemoteException {
        TypedQuery<NhanVien> query = entityManager.createNamedQuery("NhanVien.getAllNhanVienNgungLam", NhanVien.class);
        query.setParameter("trangThai", 1);
        return query.getResultList();
    }
}

