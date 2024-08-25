package entities;

import java.io.Serializable;
import jakarta.persistence.*;

@NamedQueries({
	@NamedQuery(name = "KieuDang.findAll", query = "SELECT c FROM KieuDang c"),
	@NamedQuery(name = "KieuDang.findByName", query = "SELECT kd FROM KieuDang kd WHERE kd.kieuDang = :name")
})
@Entity
@Table(name = "KieuDang")
public class KieuDang implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7507542034165380907L;

	@Id
    @Column(name = "maKD")
    private String maKD;

    @Column(name = "kieuDang")
    private String kieuDang;
    public KieuDang() {
		// TODO Auto-generated constructor stub
	}
	public KieuDang(String maKD, String kieuDang) {
		super();
		this.maKD = maKD;
		this.kieuDang = kieuDang;
	}

	public String getMaKD() {
		return maKD;
	}

	public void setMaKD(String maKD) {
		this.maKD = maKD;
	}

	public String getKieuDang() {
		return kieuDang;
	}

	public void setKieuDang(String kieuDang) {
		this.kieuDang = kieuDang;
	}

	@Override
	public String toString() {
		return "KieuDang [maKD=" + maKD + ", kieuDang=" + kieuDang + "]";
	}

    
}
