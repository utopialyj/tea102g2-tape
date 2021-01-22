package com.coupon_info.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TestCoupon_InfoJDBCDAO {

	public static void main(String[] args) {
		Coupon_InfoDAO_interface dao = new Coupon_InfoJDBCDAO();
		
		System.out.println("------------getAll------------");
		List<Coupon_InfoVO> list = dao.getAll();
		for (Coupon_InfoVO coupon_infoVO : list) {
			System.out.println(coupon_infoVO);
		}

		System.out.println("------------insert------------");
		byte[] pic1;
		try {
			FileInputStream fis = new FileInputStream("C:/TEA102G2/TEA102G2/WebContent/back-end/coupon_info/images/1.jpg");
			pic1 = new byte[fis.available()];
			fis.read(pic1);
			fis.close();
		Coupon_InfoVO coupon_infoVO1 = new Coupon_InfoVO("", "Starbucks Latte", "Starbucks", 140, 180, 
				pic1, "星巴克那堤", "美食", "01", 999, 0, "下架", java.sql.Date.valueOf(LocalDate.now()), null);
		dao.insert(coupon_infoVO1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------update------------");
		byte[] pic2;
		try {
			FileInputStream fis = new FileInputStream("C:/TEA102G2/TEA102G2/WebContent/back-end/coupon_info/images/1.jpg");
			pic2 = new byte[fis.available()];
			fis.read(pic2);
			fis.close();
		Coupon_InfoVO coupon_infoVO2 = new Coupon_InfoVO("1", "Starbucks Latte", "Starbucks", 140, 180, 
				pic2, "星巴克那堤", "美食", "01", 999, 999, "下架", java.sql.Date.valueOf(LocalDate.now()), null);
		dao.update(coupon_infoVO2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------findByPK----------");
		Coupon_InfoVO coupon_infoVO3 = dao.findByPrimaryKey("1");
		System.out.println(coupon_infoVO3);
		
//		dao.delete("2");
	}

}
