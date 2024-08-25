package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.ChatLieuDAO;
import entities.ChatLieu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ChatLieuService extends UnicastRemoteObject implements ChatLieuDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public ChatLieuService(EntityManager entityManager) throws RemoteException{
		this.entityManager = entityManager;
	}

	@Override
	public boolean addChatLieu(ChatLieu chatLieu) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(chatLieu);
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
	public boolean updateChatLieu(ChatLieu chatLieu) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(chatLieu);
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
	public boolean deleteChatLieu(String id) throws RemoteException{
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			ChatLieu chatLieu = entityManager.find(ChatLieu.class, id);
			entityManager.remove(chatLieu);
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
	public List<ChatLieu> getAllChatLieu() throws RemoteException{
		return entityManager.createNamedQuery("ChatLieu.findAll", ChatLieu.class).getResultList();
	}
	@Override
    public ChatLieu getChatLieuByName(String name) throws RemoteException {
        try {
            TypedQuery<ChatLieu> query = entityManager.createNamedQuery("ChatLieu.findByName", ChatLieu.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
