package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;

import com.wfd.dot1.cwfm.pojo.BillVerification;

public interface BillVerificationService {

	public List<BillVerification> getBillVerificationList(String userId) ;

	public BillVerification viewbillDetails(String transactionId);

	public BillVerification viewbillReportsDetails(String transactionId);

	public BillVerification viewbillhrclearanceDetails(String transactionId);
	
	public BillVerification viewbillprecomments(String transactionId);
		
	public BillVerification viewbilleicDetails(String transactionId);

}
