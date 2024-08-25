package component;

public class sanPhamPrinter {
	private String tensp;
	private double giaban;
	private int sl;
	private double thanhtien;
	public sanPhamPrinter(String tensp, double giaban, int sl, double thanhtien) {
		super();
		this.tensp = tensp;
		this.giaban = giaban;
		this.sl = sl;
		this.thanhtien = thanhtien;
	}
	public String getTensp() {
		return tensp;
	}
	public void setTensp(String tensp) {
		this.tensp = tensp;
	}
	public double getGiaban() {
		return giaban;
	}
	public void setGiaban(double giaban) {
		this.giaban = giaban;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public double getThanhtien() {
		return thanhtien;
	}
	public void setThanhtien(double thanhtien) {
		this.thanhtien = thanhtien;
	}
	@Override
	public String toString() {
		return "sanPhamPrinter [tensp=" + tensp + ", giaban=" + giaban + ", sl=" + sl + ", thanhtien=" + thanhtien
				+ "]";
	}
	
}
