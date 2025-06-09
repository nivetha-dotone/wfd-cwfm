package com.wfd.dot1.cwfm.dao;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
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
		 public String getContractorListing() {
				return QueryFileWatcher.getQuery("GET_BVR_FOR_CREATOR");
			}
		@Override
		public List<CMSWageCostDTO> getBillVerificationList(String userId,String deptId,String unitId) {//deptID is contractorID
			List<CMSWageCostDTO> listDto= new ArrayList<CMSWageCostDTO>();
			String query =getContractorListing();
			log.info("Query to GET_BVR_FOR_CREATOR "+query);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,deptId,userId);
			while(rs.next()) {
				CMSWageCostDTO d = new CMSWageCostDTO();
				d.setWcTransId(Long.parseLong(rs.getString("WCTransID")));
				d.setUnitCode(rs.getString("UnitCode"));
				d.setContractorCode(rs.getString("ContractorCode"));
				d.setContractorName(rs.getString("ContractorName"));
				d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
				d.setStartDate(rs.getString("StartDate"));
				d.setEndDate(rs.getString("EndDate"));
				d.setStatus(rs.getInt("Status"));
				d.setServices(rs.getString("Services"));
				listDto.add(d);
			}
			return listDto;
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
		

		@Override
		public String getActionIdForBill() {
			String r=null;
			String sql =" select cgm.GMID,cgm.GMNAME from CMSGENERALMASTER cgm\r\n"
			   		+ "		    join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID\r\n"
			   		+ "		    where cgt.GMTYPE='ACTION' and cgm.GMNAME like 'Bill%'";
			   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			   while(rs.next()) {
			   r=rs.getString("GMID");
			   }
			   return r;
		}
		
		@Override
		public String saveBill(CMSWageCostDTO dto) {
		    String transId = null;
		    
		   String sql =" select cgm.GMID,cgm.GMNAME from CMSGENERALMASTER cgm\r\n"
		   		+ "		    join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID\r\n"
		   		+ "		    where cgt.GMTYPE='ACTION' and cgm.GMNAME like 'Bill%'";
		   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		   while(rs.next()) {
		   dto.setActionId(rs.getString("GMID"));
		   }
		    String query = "INSERT INTO CMSWageCostWorkFlow " +
		            "(WCTransID, Status, UnitId, UnitCode, UnitName, ContractorId, ContractorCode, ContractorName, " +
		            "WorkOrderNumber, StartDate, EndDate, Services, CreatedBy, CreatedDate, UpdatedDate, " +
		            "WOValidFrom, WOValidTo, BillType, UpdatedBy, Comments, PreComments, ActionPlan,ActionId) " +
		            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?, ?, ?, ?, ?, ?, ?,?)";

		    // Formatter to parse dd-MM-yyyy format coming from JSP
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    try {

		        Object[] parameters = new Object[]{
		            dto.getWcTransId(),
		            dto.getStatus(),
		            dto.getUnitId(),
		            dto.getUnitCode(),
		            dto.getUnitName(),
		            dto.getContractorId(),
		            dto.getContractorCode(),
		            dto.getContractorName(),
		            dto.getWorkOrderNumber(),
		            parseDate1(dto.getStartDate(), formatter1),
		            parseDate1(dto.getEndDate(), formatter1),
		            dto.getServices(),
		            dto.getCreatedBy(),
		            parseDate(dto.getWoValidFrom(), formatter),
		            parseDate(dto.getWoValidTo(), formatter),
		            dto.getBillType(),
		            dto.getUpdateBy(),
		            dto.getComments(),
		            dto.getPreComments(),
		            dto.getActionPlan(),
		            dto.getActionId()
		        };

		        int result = jdbcTemplate.update(query, parameters);

		        if (result > 0) {
		            log.info("Bill saved successfully for transId: " + dto.getWcTransId());
		            transId = String.valueOf(dto.getWcTransId());
		        } else {
		            log.warn("Failed to save Bill for transId: " + dto.getWcTransId());
		        }

		    } catch (Exception e) {
		        log.error("Error saving Bill for transId: " + dto.getWcTransId(), e);
		    }

		    log.info("Exiting from Bill for transId: " + dto.getWcTransId());
		    return transId;
		}
		private java.sql.Date parseDate(String dateStr, DateTimeFormatter formatter) {
		    if (dateStr == null || dateStr.trim().isEmpty()) return null;
		    LocalDate localDate = LocalDate.parse(dateStr, formatter);
		    return java.sql.Date.valueOf(localDate);
		}
		private java.sql.Date parseDate1(String dateStr, DateTimeFormatter formatter) {
		    if (dateStr == null || dateStr.trim().isEmpty()) return null;
		    LocalDate localDate = LocalDate.parse(dateStr, formatter);
		    return java.sql.Date.valueOf(localDate);
		}
		
		 public String getAllBVRForSquential() {
			    return QueryFileWatcher.getQuery("GET_BVR_FOR_SEQUENTIAL_APPROVER");
			}
		 
		 public String getAllBVRForParallel() {
			    return QueryFileWatcher.getQuery("GET_BVR_FOR_PARALLEL_APPROVER");
			}
		 
		@Override
		public List<CMSWageCostDTO> getBillVerificationListForApprovers(String roleId, int workFlowType, String deptId,
				String principalEmployerId) {
			String actionId=this.getActionIdForBill();
			
			List<CMSWageCostDTO> listDto= new ArrayList<CMSWageCostDTO>();
			SqlRowSet rs =null;
			String query=null;
			if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
				query=this.getAllBVRForSquential();
				log.info("Query to getBVRListingForApprovers "+query);
				
				 rs = jdbcTemplate.queryForRowSet(query,deptId,principalEmployerId,roleId,actionId);
			}else {
				query=this.getAllBVRForParallel();
				log.info("Query to getBVRListingForApprovers "+query);
				 rs = jdbcTemplate.queryForRowSet(query,roleId,actionId,roleId,actionId,deptId,principalEmployerId);
			}
			
			while(rs.next()) {
				CMSWageCostDTO d  = new CMSWageCostDTO();
				d.setWcTransId(Long.parseLong(rs.getString("WCTransID")));
				d.setUnitCode(rs.getString("UnitCode"));
				d.setContractorCode(rs.getString("ContractorCode"));
				d.setContractorName(rs.getString("ContractorName"));
				d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
				d.setStartDate(rs.getString("StartDate"));
				d.setEndDate(rs.getString("EndDate"));
				d.setStatus(rs.getInt("Status"));
				d.setServices(rs.getString("Services"));
				listDto.add(d);
			}
			
			return listDto;
		}

		 public String getWorkflowType() {
			    return QueryFileWatcher.getQuery("GET_WORKFLOWTYPE");
			}
		 
@Override
public int getWorkflowType(String module,String unitId) {
	log.info("Entering into getWorkFlowTYpeNew dao method ");
	int workflowTypeId = 0;
	String query = getWorkflowType();
	log.info("Query to getWorkFlowTYpeNew "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,module,unitId);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpeNew dao method "+unitId);
	return workflowTypeId;
}
public String getSaveBillStatusLog() {
	return QueryFileWatcher.getQuery("SAVE_BILL_STATUSLOG");
}
@Override
public void saveBillStatusLog(BillStatusLogDto dto) {
	log.info("Entered into saveBillStatusLog for gatePassId: "+dto.getTransactionId() );

    Object[] parameters = new Object[] {dto.getTransactionId(),dto.getStatus(),dto.getComments(),dto.getUpdatedBy()};

    try {
    	String query = getSaveBillStatusLog();
        int result = jdbcTemplate.update(query,parameters );
        if (result > 0) {
           // log.info("GatePass status log saved successfully for GatePassId: " + dto.getGatePassId());
        } else {
           // log.warn("Failed to save GatePass status log for GatePassId: " + dto.getGatePassId());
        }
    } catch (Exception e) {
        //log.error("Error saving GatePass status log for GatePassId: " + dto.getGatePassId(), e);
    }
}
public String getBVRDetails() {
	return QueryFileWatcher.getQuery("GET_BVR_VIEW");
}
@Override
public CMSWageCostDTO getIndividualBVRDetails(String transactionId) {
	CMSWageCostDTO d=new CMSWageCostDTO();
	String query = getBVRDetails();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
	while(rs.next()) {
		d.setWcTransId(rs.getLong("WCTransID"));
		d.setUnitName(rs.getString("UnitName"));
		d.setUnitCode(rs.getString("UnitCode"));
		d.setContractorName(rs.getString("ContractorName"));
		d.setContractorCode(rs.getString("ContractorCode"));
		d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
		d.setServices(rs.getString("Services"));
		d.setStartDate(rs.getString("StartDate"));
		d.setEndDate(rs.getString("EndDate"));
		d.setWoValidFrom(rs.getString("WOValidFrom"));
		d.setWoValidTo(rs.getString("WOValidTo"));
		d.setStatus(rs.getInt("Status"));
		d.setBillType(rs.getString("BillType"));
		d.setActionPlan(rs.getString("ActionPlan"));
		d.setComments(rs.getString("Comments"));
	}
	return d;
	
}
@Override
public void saveFile(BillReportFile file) {
	
	String sql = "INSERT INTO BillReportFiles (TransactionId, ReportType, ReportName, FileName) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, file.getTransactionId(), file.getReportType(), file.getReportName(), file.getFileName());

}
@Override
public List<BillReportFile> findByTransactionIdAndType(Long transactionId, String reportType) {
    String sql = "SELECT * FROM BillReportFiles WHERE TransactionId = ? AND ReportType = ?";
    return jdbcTemplate.query(sql, new Object[]{transactionId, reportType}, (rs, rowNum) -> {
        BillReportFile f = new BillReportFile();
        f.setId(rs.getInt("ID"));
        f.setTransactionId(rs.getLong("TransactionId"));
        f.setReportType(rs.getString("ReportType"));
        f.setReportName(rs.getString("ReportName"));
        f.setFileName(rs.getString("FileName"));
        return f;
    });
}
@Override
public void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId) {
	
}

}
