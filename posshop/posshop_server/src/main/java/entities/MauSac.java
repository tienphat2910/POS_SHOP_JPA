package entities;

import java.io.Serializable;

import jakarta.persistence.*;
@NamedQueries({
	@NamedQuery(name = "MauSac.findAll", query = "SELECT c FROM MauSac c"),
	@NamedQuery(name = "MauSac.findByName", query = "SELECT ms FROM MauSac ms WHERE ms.mauSac = :name")
})
@Entity
@Table(name = "MauSac")
public class MauSac implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4989170566312997338L;

	@Id
    @Column(name = "maMS")
    private String maMS;

    @Column(name = "mauSac")
    private String mauSac;
    public MauSac() {
		// TODO Auto-generated constructor stub
	}
	public MauSac(String maMS, String mauSac) {
		super();
		this.maMS = maMS;
		this.mauSac = mauSac;
	}

	public String getMaMS() {
		return maMS;
	}

	public void setMaMS(String maMS) {
		this.maMS = maMS;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	@Override
	public String toString() {
		return "MauSac [maMS=" + maMS + ", mauSac=" + mauSac + "]";
	}

}

