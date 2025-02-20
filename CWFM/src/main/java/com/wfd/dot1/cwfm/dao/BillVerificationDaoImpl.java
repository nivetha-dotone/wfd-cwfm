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
	 
	 @Override
		public List<BillVerification> getBillVerificationList(String userId) {
		List<BillVerification> peList= new ArrayList<BillVerification>();
		String query = getBillList();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			BillVerification pe = new BillVerification();
			pe.setTransactionId(rs.getString("WCTransID"));
			pe.setUnitCode(rs.getString("UnitCode"));
			pe.setVendorCode(rs.getString("ContractorCode"));
			pe.setContractorName(rs.getString("ContractorName"));
			pe.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
			pe.setBillStartDate(rs.getString("StartDate"));
			pe.setBillEndDate(rs.getString("EndDate"));
			pe.setStatus(rs.getString("Status"));
			pe.setBillCategory(rs.getString("Services"));
			/*
			 * pe.setLastApprover(rs.getString("")); pe.setNextApprover(rs.getString(""));
			 */
			peList.add(pe);
		
		}
		return peList;
		}
	 
	 @Override
		public BillVerification viewbillDetails(String transactionId) {
			BillVerification bill = new BillVerification();
			String query = getAllbillDetailsView();
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
				if (rs.next()) {
					bill = new BillVerification();
					bill.setTransactionId(rs.getString("WCTransID"));
					bill.setUnitCode(rs.getString("UnitCode"));
					bill.setUnitName(rs.getString("UnitName"));
					bill.setVendorCode(rs.getString("ContractorCode"));
					bill.setContractorName(rs.getString("ContractorName"));
					bill.setBillStartDate(rs.getString("StartDate"));
					bill.setBillEndDate(rs.getString("EndDate"));
					bill.setWorkOrderCode(rs.getString("WorkOrderNumber"));
					bill.setBillType(rs.getString("BILLTYPENAME"));
					bill.setWorkOrderValidFrom(rs.getString("WOValidFrom"));
					bill.setWorkOrderValidTo(rs.getString("WOValidTo"));
					bill.setBillCategory(rs.getString("WCTransID")); 
		           
		        }
				return bill;
		}
	 
	 @Override
		public BillVerification viewbillReportsDetails(String transactionId) {
			BillVerification bill = new BillVerification();
			String query = getAllbillReportsDetailsView();
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
				if (rs.next()) {
					bill = new BillVerification();
					bill.setMrReport(rs.getString("MRReport"));
					bill.setBvrReport(rs.getString("BVRReport"));;
					bill.setBonusReport(rs.getString("BonusReport"));
					bill.setEhReport(rs.getString("ExtraHoursReport"));
					bill.setWcReport(rs.getString("WCReport"));
					bill.setFormAReport(rs.getString("FormA"));
					bill.setFormBReport(rs.getString("FormB"));
					bill.setFormCReport(rs.getString("FormC"));
					bill.setFormDReport(rs.getString("FormD"));
					bill.setEcrPF(rs.getString("EcrPF"));
					bill.setPfChallan(rs.getString("PFChallan"));
					bill.setEcrESIC(rs.getString("EcrESIC")); 
					bill.setEsicChallan(rs.getString("ESICChallan")); 
					bill.setBankStatement(rs.getString("BankStatement")); 
					bill.setAnnualReturn(rs.getString("AnnualReturn")); 
					bill.setBonusReg(rs.getString("BonusRegister")); 
					bill.setLwfChallan(rs.getString("LWFChallan")); 
					bill.setPtChallan(rs.getString("PTChallan"));
					bill.setUser1(rs.getString("UserAttachment1")); 
					bill.setUser2(rs.getString("UserAttachment2")); 
					bill.setUser3(rs.getString("UserAttachment3")); 
					
		           
		        }
				return bill;
		}

	 @Override
		public BillVerification viewbillhrclearanceDetails(String transactionId) {
			BillVerification bill = new BillVerification();
			String query = getAllhrclearanceView();
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
				if (rs.next()) {
					bill = new BillVerification();
					bill.setLlStatus(rs.getString("LLStatus"));
					bill.setLlCopy(rs.getString("LLCopy"));
					bill.setLlValidTo(rs.getString("LLValidTo"));;
					bill.setEmpReg(rs.getString("EmpReg"));;
					bill.setWageReg(rs.getString("WageReg"));
					bill.setLoanRecovery(rs.getString("LoanRecovery"));
					bill.setAttenReg(rs.getString("AttenReg"));
					bill.setAnnualReturnCL(rs.getString("AnnualReturn"));
					bill.setPfSlip(rs.getString("PFSlip"));
					bill.setPfSlipDate(rs.getString("PFSlipDate"));
					bill.setPfEcr(rs.getString("PFEcr"));
					bill.setPfECRDate(rs.getString("PFECRDate"));
					bill.setEsicSlip(rs.getString("ESICSlip"));
					bill.setEsicSlipDate(rs.getString("ESICSlipDate")); 
					bill.setEsicEcr(rs.getString("ESICEcr")); 
					bill.setEsicECRDate(rs.getString("ESICECRDate")); 
					bill.setBankStatement(rs.getString("BankStmntStatus")); 
					bill.setpTaxChallan(rs.getString("PTaxChallan")); 
					bill.setpTaxDate(rs.getString("PTaxDate")); 
					bill.setAccidentPolicy(rs.getString("AccidentPolicy"));
					bill.setAccidentPolicyValidTo(rs.getString("AccidentPolicyValidTo"));
					bill.setLaborWelfare(rs.getString("LaborWelfare")); 
					bill.setLabWelFundActDate(rs.getString("LabWelFundActDate")); 
					bill.setBonusRegFormC(rs.getString("BonusReg")); 
					bill.setLeaveWihtWages(rs.getString("LeaveWihtWages")); 
					bill.setPreMonWage(rs.getString("PreMonWage")); 
					bill.setPreMonWageDate(rs.getString("PreMonWageDate")); 
					bill.setActionPlan(rs.getString("ActionPlan")); 
					
		           
		        }
				return bill;
		}
		
	 @Override
		public BillVerification viewbillprecomments(String transactionId) {
			BillVerification bill = new BillVerification();
			String query = getAllbillPrecommentsView();
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
				if (rs.next()) {
					bill = new BillVerification();
					bill.setPrecomments(rs.getString("precomments"));
		        }
				return bill;
		}
	 
	 @Override
		public BillVerification viewbilleicDetails(String transactionId) {
			BillVerification bill = new BillVerification();
			String query = getAllbilleicView();
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
				if (rs.next()) {
					bill = new BillVerification();
					bill.setAvailableeic(rs.getString("AvailableEIC"));
		        }
				return bill;
		}
}
