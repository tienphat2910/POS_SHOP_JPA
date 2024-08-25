package component;

import java.util.List;

public class hoaDonPrinter {
	private String tennv;
	private String tenkh; 
	private double tongtien;
	private double Giamgia;
	private double thue;
	private double tienthanhtoan;
	private double tienkhachdua;
	private double tienthua;
	List<sanPhamPrinter> fields;
	public hoaDonPrinter(String tennv, String tenkh, double tongtien, double giamgia, double thue, double tienthanhtoan,
			double tienkhachdua, double tienthua, List<sanPhamPrinter> fields) {
		super();
		this.tennv = tennv;
		this.tenkh = tenkh;
		this.tongtien = tongtien;
		Giamgia = giamgia;
		this.thue = thue;
		this.tienthanhtoan = tienthanhtoan;
		this.tienkhachdua = tienkhachdua;
		this.tienthua = tienthua;
		this.fields = fields;
	}
	public String getTennv() {
		return tennv;
	}
	public void setTennv(String tennv) {
		this.tennv = tennv;
	}
	public String getTenkh() {
		return tenkh;
	}
	public void setTenkh(String tenkh) {
		this.tenkh = tenkh;
	}
	public double getTongtien() {
		return tongtien;
	}
	public void setTongtien(double tongtien) {
		this.tongtien = tongtien;
	}
	public double getGiamgia() {
		return Giamgia;
	}
	public void setGiamgia(double giamgia) {
		Giamgia = giamgia;
	}
	public double getThue() {
		return thue;
	}
	public void setThue(double thue) {
		this.thue = thue;
	}
	public double getTienthanhtoan() {
		return tienthanhtoan;
	}
	public void setTienthanhtoan(double tienthanhtoan) {
		this.tienthanhtoan = tienthanhtoan;
	}
	public double getTienkhachdua() {
		return tienkhachdua;
	}
	public void setTienkhachdua(double tienkhachdua) {
		this.tienkhachdua = tienkhachdua;
	}
	public double getTienthua() {
		return tienthua;
	}
	public void setTienthua(double tienthua) {
		this.tienthua = tienthua;
	}
	public List<sanPhamPrinter> getFields() {
		return fields;
	}
	public void setFields(List<sanPhamPrinter> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "hoaDonPrinter [tennv=" + tennv + ", tenkh=" + tenkh + ", tongtien=" + tongtien + ", Giamgia=" + Giamgia
				+ ", thue=" + thue + ", tienthanhtoan=" + tienthanhtoan + ", tienkhachdua=" + tienkhachdua
				+ ", tienthua=" + tienthua + ", fields=" + fields + "]";
	}
}
