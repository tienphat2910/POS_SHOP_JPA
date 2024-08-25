package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.NhaCungCapDAO;
import entities.KhuyenMai;
import entities.NhaCungCap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class NhaCungCapService extends UnicastRemoteObject implements NhaCungCapDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public NhaCungCapService(EntityManager entityManager) throws RemoteException {
		super();
		this.entityManager = entityManager;
	}
	

	@Override
	public List<NhaCungCap> getAllNhaCungCap() throws RemoteException {
		return entityManager.createNamedQuery("NhaCungCap.findAll", NhaCungCap.class).getResultList();
	}
	
	@Override
    public NhaCungCap getNhaCungCapByName(String name) throws RemoteException {
        try {
            TypedQuery<NhaCungCap> query = entityManager.createNamedQuery("NhaCungCap.findByName", NhaCungCap.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
					maxID = "NCC0" + num;
				} else {
					maxID = "NCC" + num;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxID;
	}
	
	//
	@Override
	public NhaCungCap getNhaCungCap(String id) throws RemoteException {
		try {
			// Sử dụng EntityManager để thao tác với CSDL
			NhaCungCap nhaCungCap = entityManager.find(NhaCungCap.class, id);
			return nhaCungCap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//
	@Override
	public List<NhaCungCap> getNhaCungCapByTen(String tenNhaCungCap) throws RemoteException {
		List<NhaCungCap> listNhaCungCap = new ArrayList<>();
		try {
			TypedQuery<NhaCungCap> query = entityManager.createNamedQuery("NhaCungCap.findByTenNCC", NhaCungCap.class);
			query.setParameter("tenNhaCungCap", "%" + tenNhaCungCap + "%");
			listNhaCungCap = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listNhaCungCap;
	}

	@Override
	public List<NhaCungCap> timKiemNCC(String tenNhaCungCap) throws RemoteException {
		List<NhaCungCap> listNhaCungCap = new ArrayList<>();
		try {
			TypedQuery<NhaCungCap> query = entityManager.createNamedQuery("NhaCungCap.timKiemNCC", NhaCungCap.class);
			query.setParameter("searchString", "%" + tenNhaCungCap + "%");
			listNhaCungCap = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("Error occurred while searching for NhaCungCap with name: " + tenNhaCungCap, e);
		}
		return listNhaCungCap;
	}

	//
	public int updateNhaCungCap(NhaCungCap nhaCungCap) throws RemoteException {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("NhaCungCap.updateNhaCungCap");
            query.setParameter("tenNCC", nhaCungCap.getTenNCC());
            query.setParameter("diaChi", nhaCungCap.getDiaChi());
            query.setParameter("email", nhaCungCap.getEmail());
            query.setParameter("sdt", nhaCungCap.getSDT());
            query.setParameter("maNCC", nhaCungCap.getMaNCC());

            int result = query.executeUpdate();
            transaction.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

	//
	public int editNhaCungCap(NhaCungCap nhaCungCap) throws RemoteException {
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();

			Query query = entityManager.createNamedQuery("NhaCungCap.editNhaCungCap");
			query.setParameter("tenNCC", nhaCungCap.getTenNCC());
			query.setParameter("sdt", nhaCungCap.getSDT());
			query.setParameter("email", nhaCungCap.getEmail());
			query.setParameter("diaChi", nhaCungCap.getDiaChi());
			query.setParameter("maNCC", nhaCungCap.getMaNCC());

			int result = query.executeUpdate();
			transaction.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//
	@Override
	public boolean addNhaCungCap(NhaCungCap ncc) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(ncc);
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
