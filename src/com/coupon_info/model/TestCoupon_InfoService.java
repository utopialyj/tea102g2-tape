package com.coupon_info.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TestCoupon_InfoService {

	public static void main(String[] args) {
		Coupon_InfoService cis = new Coupon_InfoService();
		
		System.out.println("------------getAll------------");
		List<Coupon_InfoVO> list = cis.getAll();
		for (Coupon_InfoVO coupon_infoVO : list) {
			System.out.println(coupon_infoVO);
		}
		
		System.out.println("------------addCoupon_Info----");
		byte[] pic1;
		try {
			FileInputStream fis = new FileInputStream("C:/TEA102G2/TEA102G2/WebContent/back-end/coupon_info/images/1.jpg");
			pic1 = new byte[fis.available()];
			fis.read(pic1);
			fis.close();
			cis.addCoupon_Info("Starbucks Latte", "Starbucks", 140, 180, pic1, "星巴克那堤", 
					"美食", "01", 999, 0, "下架", java.sql.Date.valueOf(LocalDate.now()), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------updateCoupon_Info-");
		byte[] pic2;
		try {
			FileInputStream fis = new FileInputStream("C:/TEA102G2/TEA102G2/WebContent/back-end/coupon_info/images/1.jpg");
			pic2 = new byte[fis.available()];
			fis.read(pic2);
			fis.close();
			cis.updateCoupon_Info("1", "Starbucks Latte", "Starbucks", 140, 180, pic2, 
					"星巴克那堤", "美食", "01", 999, 999, "下架", java.sql.Date.valueOf(LocalDate.now()), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("------------getCoupon_Info----");
		Coupon_InfoVO coupon_infoVO1 = cis.getOneCoupon_Info("1");
		System.out.println(coupon_infoVO1);
		
//		cis.deleteCoupon_Info("3");
	}

}
