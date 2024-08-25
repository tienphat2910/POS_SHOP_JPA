package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.ChatLieu;

public interface ChatLieuDAO extends Remote{
	public boolean addChatLieu(ChatLieu chatLieu) throws RemoteException;
	public boolean updateChatLieu(ChatLieu chatLieu)throws RemoteException;
	public boolean deleteChatLieu(String id)throws RemoteException;
	public List<ChatLieu> getAllChatLieu()throws RemoteException;
	public ChatLieu getChatLieuByName(String name) throws RemoteException;
	
}
