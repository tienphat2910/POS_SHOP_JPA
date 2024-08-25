package entities;

import java.io.Serializable;

import jakarta.persistence.*;
@NamedQueries({
		@NamedQuery(name = "ChatLieu.findAll", query = "SELECT c FROM ChatLieu c"),
		@NamedQuery(name = "ChatLieu.findByName", query = "SELECT cl FROM ChatLieu cl WHERE cl.chatLieu = :name")
})
@Entity
@Table(name = "ChatLieu")
public class ChatLieu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 557083901264384085L;

	@Id
    @Column(name = "maCL")
    private String maCL;

    @Column(name = "chatLieu")
    private String chatLieu;
    public ChatLieu() {
        // Không cần thực hiện gì trong constructor này
    }
	public ChatLieu(String maCL, String chatLieu) {
		this.maCL = maCL;
		this.chatLieu = chatLieu;
	}

	public String getMaCL() {
		return maCL;
	}

	public void setMaCL(String maCL) {
		this.maCL = maCL;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}

	@Override
	public String toString() {
		return "ChatLieu [maCL=" + maCL + ", chatLieu=" + chatLieu + "]";
	}

    
}

