package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.PhanLoaiDAO;
import entities.PhanLoai;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PhanLoaiService extends UnicastRemoteObject implements PhanLoaiDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public PhanLoaiService(EntityManager entityManager) throws RemoteException{
		this.entityManager = entityManager;
	}
	@Override
	public boolean addPhanLoai(PhanLoai phanLoai) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updatePhanLoai(PhanLoai phanLoai) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deletePhanLoai(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<PhanLoai> getAllPhanLoai() throws RemoteException {
		return entityManager.createNamedQuery("PhanLoai.findAll", PhanLoai.class).getResultList();
	}
	@Override
    public PhanLoai getPhanLoaiByName(String name) throws RemoteException {
        try {
            TypedQuery<PhanLoai> query = entityManager.createNamedQuery("PhanLoai.findByName", PhanLoai.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
