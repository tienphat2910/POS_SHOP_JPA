package entities;

import java.io.Serializable;

import jakarta.persistence.*;

@NamedQueries({
	@NamedQuery(name = "PhanLoai.findAll", query = "SELECT c FROM PhanLoai c"),
	@NamedQuery(name = "PhanLoai.findByName", query = "SELECT pl FROM PhanLoai pl WHERE pl.phanLoai = :name")
})
@Entity
@Table(name = "PhanLoai")
public class PhanLoai implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5082905038108396190L;

	@Id
    @Column(name = "maPL")
    private String maPL;

    @Column(name = "phanLoai")
    private String phanLoai;
    public PhanLoai() {
		// TODO Auto-generated constructor stub
	}
	public PhanLoai(String maPL, String phanLoai) {
		super();
		this.maPL = maPL;
		this.phanLoai = phanLoai;
	}

	public String getMaPL() {
		return maPL;
	}

	public void setMaPL(String maPL) {
		this.maPL = maPL;
	}

	public String getPhanLoai() {
		return phanLoai;
	}

	public void setPhanLoai(String phanLoai) {
		this.phanLoai = phanLoai;
	}

	@Override
	public String toString() {
		return "PhanLoai [maPL=" + maPL + ", phanLoai=" + phanLoai + "]";
	}

    
}

