package other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment {

	// Hàm trả về danh sách các tỉnh ở miền Bắc
	private static List<String> danhSachTinhMienBac() {
		List<String> danhSach = new ArrayList<>();
		danhSach.add("Hà Nội");
		danhSach.add("Hải Phòng");
		danhSach.add("Quảng Ninh");
		danhSach.add("Bắc Ninh");
		danhSach.add("Hải Dương");
		danhSach.add("Hưng Yên");
		danhSach.add("Nam Định");
		danhSach.add("Ninh Bình");
		danhSach.add("Thái Bình");
		danhSach.add("Vĩnh Phúc");
		danhSach.add("Phú Thọ");
		danhSach.add("Bắc Giang");
		danhSach.add("Lào Cai");
		danhSach.add("Yên Bái");
		danhSach.add("Lạng Sơn");
		danhSach.add("Bắc Ninh");
		danhSach.add("Cao Bằng");
		danhSach.add("Bắc Kạn");
		danhSach.add("Hà Giang");
		danhSach.add("Bắc Ninh");
		danhSach.add("Hà Nam");
		danhSach.add("Hòa Bình");
		danhSach.add("Sơn La");
		danhSach.add("Lai Châu");
		danhSach.add("Điện Biên");
		return danhSach;
	}

	// Hàm trả về danh sách các tỉnh ở miền Trung
	private static List<String> danhSachTinhMienTrung() {
		List<String> danhSach = new ArrayList<>();
		danhSach.add("Đà Nẵng");
		danhSach.add("Quảng Nam");
		danhSach.add("Quảng Ngãi");
		danhSach.add("Bình Định");
		danhSach.add("Phú Yên");
		danhSach.add("Khánh Hòa");
		danhSach.add("Ninh Thuận");
		danhSach.add("Bình Thuận");
		danhSach.add("Quảng Bình");
		danhSach.add("Quảng Trị");
		danhSach.add("Thừa Thiên Huế");
		danhSach.add("Gia Lai");
		danhSach.add("Kon Tum");
		danhSach.add("Đắk Lắk");
		danhSach.add("Đắk Nông");
		return danhSach;
	}

	// Hàm trả về danh sách các tỉnh ở miền Nam
	private static List<String> danhSachTinhMienNam() {
		List<String> danhSach = new ArrayList<>();
		danhSach.add("Hồ Chí Minh");
		danhSach.add("Long An");
		danhSach.add("Tiền Giang");
		danhSach.add("Bến Tre");
		danhSach.add("Trà Vinh");
		danhSach.add("Vĩnh Long");
		danhSach.add("Đồng Tháp");
		danhSach.add("An Giang");
		danhSach.add("Kiên Giang");
		danhSach.add("Cần Thơ");
		danhSach.add("Hậu Giang");
		danhSach.add("Sóc Trăng");
		danhSach.add("Bạc Liêu");
		danhSach.add("Cà Mau");
		danhSach.add("Phú Quốc");
		danhSach.add("Kiên Giang");
		danhSach.add("Bà Rịa - Vũng Tàu");
		danhSach.add("Bình Dương");
		danhSach.add("Bình Phước");
		danhSach.add("Đồng Nai");
		danhSach.add("Tây Ninh");
		return danhSach;
	}

	public static Map<String, List<String>> getAssign(){

		Map<String, List<String>> mienVietNam = new HashMap<>();

		// Thêm dữ liệu vào HashMap
		mienVietNam.put("Miền Bắc", danhSachTinhMienBac());
		mienVietNam.put("Miền Trung", danhSachTinhMienTrung());
		mienVietNam.put("Miền Nam", danhSachTinhMienNam());

		return mienVietNam;
	}

}
