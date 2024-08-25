package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.HoaDonDAO;
import entities.HoaDon;
import entities.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class HoaDonService extends UnicastRemoteObject implements HoaDonDAO{
	private EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	public HoaDonService(EntityManager entityManager) throws RemoteException {
		super();
		this.entityManager = entityManager;
	}
	@Override
	public boolean addHoaDon(HoaDon hoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(hoaDon);
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
	public boolean updateHoaDon(HoaDon hoaDon) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteHoaDon(String id) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			HoaDon hoaDon = entityManager.find(HoaDon.class, id);
			entityManager.remove(hoaDon);
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
	public List<HoaDon> getAllHoaDon() throws RemoteException {
		return entityManager.createNamedQuery("HoaDon.findAll", HoaDon.class).getResultList();
	}
	@Override
	public HoaDon findById(String id) throws RemoteException {
		try {
            return entityManager.createNamedQuery("HoaDon.findById", HoaDon.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
	}
	@Override
    public int editNVTrongHD(HoaDon hoaDon) throws RemoteException {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            int result = entityManager.createNamedQuery("HoaDon.updateMaKH")
                                       .setParameter("maKH", hoaDon.getKhachHang().getMaKH())
                                       .setParameter("maHD", hoaDon.getMaHD())
                                       .executeUpdate();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;
        }
    }
	@Override
    public List<HoaDon> getHDCho() throws RemoteException {
        try {
            return entityManager.createNamedQuery("HoaDon.findUnprocessed", HoaDon.class)
                                .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
	@Override
    public boolean updateHoaDon(String maHD, int trangthai, double tongtien) throws RemoteException {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            String jpql = "UPDATE HoaDon hd SET hd.trangThai = :trangThai, hd.tongTien = :tongTien WHERE hd.maHD = :maHD";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("trangThai", trangthai);
            query.setParameter("tongTien", tongtien);
            query.setParameter("maHD", maHD);

            int result = query.executeUpdate();

            transaction.commit();

            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
	@Override
    public List<HoaDon> getAllHoaDonTheoTenNVKH(String tenNhanVien, String tenKhachHang) throws RemoteException{
        List<HoaDon> listHoaDon = new ArrayList<>();
        
        try {
            // Sử dụng JPQL để thực hiện truy vấn
            String jpql = "SELECT h FROM HoaDon h "
                         + "JOIN h.nhanVien nv "
                         + "JOIN h.khachHang kh "
                         + "WHERE LOWER(nv.tenNV) LIKE LOWER(:tenNhanVien) "
                         + "AND LOWER(kh.tenKH) LIKE LOWER(:tenKhachHang) "
                         + "AND h.maHD NOT LIKE 'HDC%'";
            
            Query query = entityManager.createQuery(jpql);
            query.setParameter("tenNhanVien", "%" + tenNhanVien + "%");
            query.setParameter("tenKhachHang", "%" + tenKhachHang + "%");
            
            // Lấy danh sách kết quả
            listHoaDon = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return listHoaDon;
    }
	@Override
    public List<HoaDon> getAllHoaDonTheoTenKHandNVTrongNgay(String tenKhachHang, String tenNhanVien, String tuNgay, String denNgay) throws RemoteException{
        TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.findAllByTenKhachHangAndTenNhanVienAndNgayLapBetween", HoaDon.class);
        query.setParameter("tenKhachHang", "%" + tenKhachHang + "%");
        query.setParameter("tenNhanVien", "%" + tenNhanVien + "%");
        query.setParameter("tuNgay","%"+ tuNgay + "%");
        query.setParameter("denNgay","%" + denNgay + "%");
        return query.getResultList();
    }
	
	//
	//
	@Override
	public List<HoaDon> doTuBang() throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonByTrangThai", HoaDon.class);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH)
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoa(String tukhoa) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoa", HoaDon.class);
			query.setParameter("tukhoa", tukhoa);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền
	@Override
	public List<HoaDon> getHoaDonTheoTongTien(int giaMin, int giaMax) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTongTien", HoaDon.class);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}
	
	// Tìm danh sách hóa đơn theo tháng
	@Override
	public List<HoaDon> getHoaDonTheoThang(int thang) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoThang", HoaDon.class);
			query.setParameter("thang", thang);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo năm
	@Override
	public List<HoaDon> getHoaDonTheoNam(int nam) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoNam", HoaDon.class);
			query.setParameter("nam", nam);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo cả tháng và năm
	@Override
	public List<HoaDon> getHoaDonTheoThangNam(int thang, int nam) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoThangNam", HoaDon.class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền và tháng
	@Override
	public List<HoaDon> getHoaDonTheoTongTienThang(int thang, int giaMin, int giaMax) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTongTienThang",
					HoaDon.class);
			query.setParameter("thang", thang);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền và năm
	@Override
	public List<HoaDon> getHoaDonTheoTongTienNam(int nam, int giaMin, int giaMax) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTongTienNam", HoaDon.class);
			query.setParameter("nam", nam);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm danh sách hóa đơn theo tổng tiền, tháng và năm
	@Override
	public List<HoaDon> getHoaDonTheoTongTienThangNam(int thang, int nam, int giaMin, int giaMax)
			throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTongTienThangNam",
					HoaDon.class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tháng
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaThang(String tukhoa, int thang) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaThang", HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("thang", thang);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và năm
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaNam(String tukhoa, int nam) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaNam", HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("nam", nam);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH) và tổng tiền
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaTongTien(String tukhoa, int giaMin, int giaMax) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaTongTien",
					HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), tháng và tổng
	// tiền
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaThangTongTien(String tukhoa, int thang, int giaMin, int giaMax)
			throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaThangTongTien",
					HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("thang", thang);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), năm và tổng
	// tiền
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaNamTongTien(String tukhoa, int nam, int giaMin, int giaMax)
			throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaNamTongTien",
					HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("nam", nam);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang, năm và
	// tổng tiền
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaThangNamTongTien(String tukhoa, int thang, int nam, int giaMin, int giaMax)
			throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaThangNamTongTien",
					HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("giaMin", giaMin);
			query.setParameter("giaMax", giaMax);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	// Tìm kiếm hóa đơn theo từ khóa (maHD, maNV, tenNV, maKH, tenKH), thang và năm
	@Override
	public List<HoaDon> getHoaDonTheoTuKhoaThangNam(String tukhoa, int thang, int nam) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<>();
		try {
			TypedQuery<HoaDon> query = entityManager.createNamedQuery("HoaDon.getHoaDonTheoTuKhoaThangNam",
					HoaDon.class);
			query.setParameter("tukhoa", "%" + tukhoa + "%");
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}
}
