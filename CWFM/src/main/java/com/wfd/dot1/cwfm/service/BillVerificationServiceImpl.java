package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wfd.dot1.cwfm.dao.BillVerificationDao;
import com.wfd.dot1.cwfm.dao.ContractorDao;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
@Service
public class BillVerificationServiceImpl implements BillVerificationService{

	@Autowired
	BillVerificationDao billDao;
	
	@Override
	public List<BillVerification> getBillVerificationList(String userId) {
		// TODO Auto-generated method stub
		return billDao.getBillVerificationList(userId);
	}
	@Override
	 public BillVerification viewbillDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillReportsDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillReportsDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillhrclearanceDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillhrclearanceDetails(transactionId);
	    }
	@Override
	 public BillVerification viewbillprecomments( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbillprecomments(transactionId);
	    }
	@Override
	 public BillVerification viewbilleicDetails( String transactionId) {
		// TODO Auto-generated method stub
	        return billDao.viewbilleicDetails(transactionId);
	    }
	
	@Override
    public String generateWCTransactionId() {
    	return billDao.generateWCTransactionId();
    }
	@Override
	public List<PrincipalEmployer> getPEDetailByUser(String userAccount) {
		// TODO Auto-generated method stub
		return billDao.getPEDetailByUser(userAccount);
	}
}
