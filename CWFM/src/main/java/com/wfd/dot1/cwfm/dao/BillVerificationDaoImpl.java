package com.wfd.dot1.cwfm.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class BillVerificationDaoImpl implements BillVerificationDao {

	private static final Logger log = LoggerFactory.getLogger(ContractorDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getBillList() {
		    return QueryFileWatcher.getQuery("GET_BILL_LIST");
		}
	 public String getAllbillDetailsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_DETAIL_VIEW");
		}
	 public String getAllbillReportsDetailsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_REPORTS_DETAIL_VIEW");
		}
	 public String getAllhrclearanceView() {
		    return QueryFileWatcher.getQuery("GET_BILL_HR_CLEARANCE_VIEW");
		}
	 public String getAllbillPrecommentsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_PRECOMMENTS_VIEW");
		}
	 public String getAllbilleicView() {
		    return QueryFileWatcher.getQuery("GET_BILL_EIC_VIEW");
		}
	 
	
	 
	 public String getMaxId() {
			return QueryFileWatcher.getQuery("GET_MAX_WC_TNX_ID");
		}
		
		@Override
		public String generateWCTransactionId() {
			String WCTransID = null;
		    try {
		    	String query = getMaxId();
		        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		        if (rs.next()) {
		        	WCTransID = rs.getString("WCTransID");
		        }
		    } catch (Exception e) {
		        log.error("Error generating WCTransID", e);
		    }
		    return WCTransID;
		}
		@Override
		public List<BillVerification> getBillVerificationList(String contractorregId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillReportsDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillhrclearanceDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillprecomments(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbilleicDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		
		 public String getAllPEBasedOnUser() {
			    return QueryFileWatcher.getQuery("GET_PE_DETAIL_FOR_BILL");
			}
		 
		@Override
		public List<PrincipalEmployer> getPEDetailByUser(String userAccount) {
			List<PrincipalEmployer> list= new ArrayList<PrincipalEmployer>();
			String query=getAllPEBasedOnUser();
			log.info("Query to getAllContractorBasedOnPE "+query);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userAccount);
			while(rs.next()) {
				PrincipalEmployer pe = new PrincipalEmployer();
				pe.setUnitId(Integer.parseInt(rs.getString("Id")));
				pe.setName(rs.getString("Description"));
				pe.setCode(rs.getString("Code"));
				list.add(pe);
				
			}
			log.info("Exiting from getAllContractorBasedOnPE dao method "+list.size());
			return list;
		}
}
