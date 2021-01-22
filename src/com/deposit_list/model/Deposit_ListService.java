package com.deposit_list.model;

import java.sql.Date;
import java.util.List;

public class Deposit_ListService {
	private Deposit_ListDAO_interface dao;
	
	public Deposit_ListService() {
		dao = new Deposit_ListJDBCDAO();
	}
	
	public Deposit_ListVO addDeposit_List(Integer deposit_num, Date deposit_date, String deposit_memid, 
			String pay_type, String credit_num, Integer credit_expiry_yy, Integer credit_expiry_mm, 
			Integer credit_security_num, String atm_id, String payment_sts) {
		Deposit_ListVO deposit_listVO = new Deposit_ListVO("", deposit_num, 
				deposit_date, deposit_memid, pay_type, credit_num, credit_expiry_yy, 
				credit_expiry_mm, credit_security_num, atm_id, payment_sts);
		dao.insert(deposit_listVO);
		
		return deposit_listVO;
	}
	
	public Deposit_ListVO updateDeposit_List(String deposit_code, Integer deposit_num, Date deposit_date, 
			String deposit_memid, String pay_type, String credit_num, Integer credit_expiry_yy, 
			Integer credit_expiry_mm, Integer credit_security_num, String atm_id, String payment_sts) {
		Deposit_ListVO deposit_listVO = new Deposit_ListVO(deposit_code, deposit_num, 
				deposit_date, deposit_memid, pay_type, credit_num, credit_expiry_yy, 
				credit_expiry_mm, credit_security_num, atm_id, payment_sts);
		dao.update(deposit_listVO);
		
		return deposit_listVO;
	}
	
	public void deleteDeposit_List(String deposit_code) {
		dao.delete(deposit_code);
	}
	
	public Deposit_ListVO getOneDeposit_List(String deposit_code) {
		return dao.findByPrimaryKey(deposit_code);
	}
	
	public List<Deposit_ListVO> getAll(){
		return dao.getAll();
	}
	
}
