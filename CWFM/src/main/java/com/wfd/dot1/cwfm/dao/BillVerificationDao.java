package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface BillVerificationDao {

	public List<BillVerification> getBillVerificationList(String contractorregId);
	
	public BillVerification viewbillDetails(String transactionId);

	public BillVerification viewbillReportsDetails(String transactionId);

	public BillVerification viewbillhrclearanceDetails(String transactionId);

	public BillVerification viewbillprecomments(String transactionId);
}
