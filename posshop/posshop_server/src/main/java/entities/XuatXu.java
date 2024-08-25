package entities;

import java.io.Serializable;

import jakarta.persistence.*;
@NamedQueries({
	@NamedQuery(name = "XuatXu.findAll", query = "SELECT c FROM XuatXu c"),
	@NamedQuery(name = "XuatXu.findByName", query = "SELECT xx FROM XuatXu xx WHERE xx.xuatXu = :name")
})
@Entity
@Table(name = "XuatXu")
public class XuatXu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8005897282520726608L;

	@Id
    @Column(name = "maXX")
    private String maXX;

    @Column(name = "xuatXu")
    private String xuatXu;
    public XuatXu() {
		// TODO Auto-generated constructor stub
	}
	public XuatXu(String maXX, String xuatXu) {
		super();
		this.maXX = maXX;
		this.xuatXu = xuatXu;
	}

	public String getMaXX() {
		return maXX;
	}

	public void setMaXX(String maXX) {
		this.maXX = maXX;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	@Override
	public String toString() {
		return "XuatXu [maXX=" + maXX + ", xuatXu=" + xuatXu + "]";
	}

    
}

