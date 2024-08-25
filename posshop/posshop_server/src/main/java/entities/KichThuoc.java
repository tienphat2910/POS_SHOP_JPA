package entities;

import java.io.Serializable;

import jakarta.persistence.*;
@NamedQueries({
	@NamedQuery(name = "KichThuoc.findAll", query = "SELECT c FROM KichThuoc c"),
	@NamedQuery(name = "KichThuoc.findByName", query = "SELECT kt FROM KichThuoc kt WHERE kt.kichThuoc = :name")
})
@Entity
@Table(name = "KichThuoc")
public class KichThuoc implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1893117332597677978L;

	@Id
    @Column(name = "maKT")
    private String maKT;

    @Column(name = "kichThuoc")
    private String kichThuoc;
    public KichThuoc() {
		// TODO Auto-generated constructor stub
	}
	public KichThuoc(String maKT, String kichThuoc) {
		super();
		this.maKT = maKT;
		this.kichThuoc = kichThuoc;
	}

	public String getMaKT() {
		return maKT;
	}

	public void setMaKT(String maKT) {
		this.maKT = maKT;
	}

	public String getKichThuoc() {
		return kichThuoc;
	}

	public void setKichThuoc(String kichThuoc) {
		this.kichThuoc = kichThuoc;
	}

	@Override
	public String toString() {
		return "KichThuoc [maKT=" + maKT + ", kichThuoc=" + kichThuoc + "]";
	}

    
}

