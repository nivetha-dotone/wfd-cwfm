package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.queries.ContractorQueryBank;
import com.wfd.dot1.cwfm.queries.WorkmenQueryBank;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class ContractorDaoImpl implements ContractorDao{
	
	private static final Logger log = LoggerFactory.getLogger(ContractorDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getContractorByIdQuery() {
		    return QueryFileWatcher.getQuery("GET_CONTRACOTR_BY_ID");
		}
	 
	 public String getContrpemmByPeAndCont() {
		    return QueryFileWatcher.getQuery("GET_CONTRPEMM_BY_PE_AND_CONT");
		}
	 
	 public String getMappingByPeCont() {
		    return QueryFileWatcher.getQuery("GET_MAPPING_BY_PE_CONT");
		}
	 
	 public String getWorkOrderQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_WORKORDER_BY_CONT");
		}
	 public String saveContractorDetails() {
		    return QueryFileWatcher.getQuery("SAVE_INSERT_CONTRACTOR_DETAILS");
		}
	 public String getAllContractors() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTORS");
		}
	 public String getContractorsDetails() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_DETAILS");
		}
	 public String getAllContractorsDetailsView() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_DETAIL_VIEW");
		}
	 public String getAllRenewalContractors() {
		    return QueryFileWatcher.getQuery("GET_ALL_RENEWAL_LIST_CONTRACTORS");
		}
	 public String getAllContractorsAdditionalDetails() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_ADD_DETAIL_VIEW");
		}
	 public String getRenewalView() {
		    return QueryFileWatcher.getQuery("GET_RENEWAL_CONTRACTORS_VIEW");
		}
	 public String getRenewalContractorsView() {
		    return QueryFileWatcher.getQuery("GET_RENEW_CONTRACTOR_ADD_DETAIL_VIEW");
		}
	 public String setRenewalDetails() {
		    return QueryFileWatcher.getQuery("SET_RENEWAL_DETAILS");
		}
	 public String setuniqueregistrationid() {
		    return QueryFileWatcher.getQuery("SET_UNIQUE_REGISTRATION_ID");
		}
	 
	@Override
	public Contractor getContractorById(String contractorId) {
		String query=getContractorByIdQuery();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId);
        
                if (rs.next()) {
                    Contractor contractor = new Contractor();
                    contractor.setContractorId(rs.getString("CONTRACTORID"));
                    contractor.setContractorName(rs.getString("NAME"));
                    contractor.setContractorAddress(rs.getString("ADDRESS"));
                    contractor.setContractorCode(rs.getString("CODE"));
                    contractor.setBlocked(rs.getInt("ISBLOCKED") == 1);
                    return contractor;
                }
        return null;
    }

	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId) {
		log.info("Entering into getAllWorkordersBasedOnPEAndContractor dao method "+unitId+" "+contractorId);
		List<Workorder> woList= new ArrayList<Workorder>();
		String query=getWorkOrderQuery();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,contractorId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            wo.setContractorId(rs.getString("CONTRACTORID"));
            wo.setValidFrom(rs.getString("VALIDFROM"));
            wo.setValidTo(rs.getString("VALIDDT"));
            wo.setUnitId(rs.getString("UNITID"));
            wo.setTypeId(String.valueOf(rs.getInt("TYPEID")));
            wo.setDepId(String.valueOf(rs.getInt("DEPID")));
            wo.setSecId(String.valueOf(rs.getInt("SECID")));
            wo.setStatus(String.valueOf(rs.getInt("STATUS")));
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}
	
	@Override
	public List<CmsContractorWC> getcontrsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string) {
		log.info("Entering into getcontrsByContractorIdAndUnitIdAndLicenseType dao method  "+contractorId);
		List<CmsContractorWC> contrWcList= new ArrayList<CmsContractorWC>();
		log.info("Query to getcontrsByContractorIdAndUnitIdAndLicenseType "+getMappingByPeCont());
		SqlRowSet rs = jdbcTemplate.queryForRowSet(getMappingByPeCont(),contractorId,principalEmployerId,string);
		while(rs.next()) {
			CmsContractorWC contr = new CmsContractorWC();
			contr.setWcCode(rs.getString("WC_CODE"));
			contr.setContractorId(rs.getString("CONTRACTORID"));
             contr.setUnitId(rs.getString("UNITID"));
             contr.setNatureOfId(rs.getInt("NATURE_OF_ID"));
             contr.setWcFromDtm(rs.getString("WC_FROM_DTM"));
             contr.setWcToDtm(rs.getString("WC_TO_DTM"));
             contr.setWcTotal(rs.getInt("WC_TOTAL"));
             contr.setDeleteSw(rs.getInt("DELETE_SW"));
             contr.setLicenceType(rs.getString("LICENCE_TYPE"));
             contr.setIsVerified(rs.getString("ISVERIFIED"));
             contr.setAttachmentNm(rs.getString("ATTACHMENTNM"));
             contr.setExtendToSubcontractor(rs.getInt("EXTENDTOSUBCONTRACTOR"));
             
            contrWcList.add(contr);
		}
		log.info("Exiting from getcontrsByContractorIdAndUnitIdAndLicenseType dao method "+contrWcList.size());
		return contrWcList;
	}

	@Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE IN (");
	    for (int i = 0; i < licenseTypes.size(); i++) {
	        if (i != 0) {
	            sqlBuilder.append(", ");
	        }
	        sqlBuilder.append("?");
	    }
	    sqlBuilder.append(")");
	    String finalQuery = sqlBuilder.toString();
	    List<Object> params = new ArrayList<>();
	    params.add(contractorId);
	    params.add(principalEmployerId);
	    params.addAll(licenseTypes);
	    SqlRowSet rs = jdbcTemplate.queryForRowSet(finalQuery, params.toArray());
	    List<CmsContractorWC> contrWcList = new ArrayList<>();
	    while(rs.next()) {
			CmsContractorWC contr = new CmsContractorWC();
			contr.setWcCode(rs.getString("WC_CODE"));
			contr.setContractorId(rs.getString("CONTRACTORID"));
             contr.setUnitId(rs.getString("UNITID"));
             contr.setNatureOfId(rs.getInt("NATURE_OF_ID"));
             contr.setWcFromDtm(rs.getString("WC_FROM_DTM"));
             contr.setWcToDtm(rs.getString("WC_TO_DTM"));
             contr.setWcTotal(rs.getInt("WC_TOTAL"));
             contr.setDeleteSw(rs.getInt("DELETE_SW"));
             contr.setLicenceType(rs.getString("LICENCE_TYPE"));
             contr.setIsVerified(rs.getString("ISVERIFIED"));
             contr.setAttachmentNm(rs.getString("ATTACHMENTNM"));
             contr.setExtendToSubcontractor(rs.getInt("EXTENDTOSUBCONTRACTOR"));
             
            contrWcList.add(contr);
		}
	    return contrWcList;
	}

	@Override
	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId) {
		 CMSContrPemm contr = null;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getContrpemmByPeAndCont(),contractorId,principalEmployerId);
			if (rs.next()) {
	            contr = new CMSContrPemm();
	            //contr.setRefId(rs.getLong("REFID"));
	            contr.setContractorId(rs.getLong("CONTRACTORID"));
	            contr.setUnitId(rs.getLong("UNITID"));
	            contr.setManagerNm(rs.getString("MANAGERNM"));
	            contr.setManagerEmail(rs.getString("MANAGEREMAIL"));
	            contr.setManagerMobile(rs.getString("MANAGERMOBILE"));
	            contr.setPeriodStartDt(rs.getDate("PERIODSTARTDT"));
	            contr.setPeriodEndDt(rs.getDate("PERIODENDDT"));
	            contr.setPfNum(rs.getString("PFNUM"));
	            contr.setPfApplyDt(rs.getDate("PFAPPLYDT"));
	            contr.setEsicNum(rs.getString("ESICNUM"));
	            contr.setRcValidated(rs.getString("RCVALIDATED").equals("Y"));
	        }
			return null;
	}
	
	@Override
	public String saveReg(ContractorRegistration contreg) {
		int status=0;
		
        Object[] parameters = new Object[] {
        		contreg.getContractorregId(),contreg.getContractorId(),contreg.getVendorCode(),contreg.getPrincipalEmployer(),contreg.getContractorName(),
        		contreg.getManagerName(),Integer.parseInt(contreg.getTotalStrength()),Integer.parseInt(contreg.getRcMaxEmp()),
        		contreg.getNatureOfWork(),contreg.getLocofWork(),contreg.getPfNum(),contreg.getRcVerified(),contreg.getMainContractor(),
        		contreg.getContractType(),contreg.getContractFrom(),contreg.getContractTo(),contreg.getRequestType(),contreg.getStatus(),contreg.getCreatedBy()
		};
        try {
           status  = jdbcTemplate.update(saveContractorDetails(), parameters);
           return contreg.getContractorregId();
        } catch (Exception e) {
        }
    return null;
	}
	
	@Override
	public List<ContractorRegistration> getContractorRegistrationList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	String query = getAllContractors();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		
		peList.add(pe);
	}
	return peList;
	}
	
	@Override
	public ContractorRenewal getContractorDetails(String contractorRenewId) {
		ContractorRenewal contr = null;
		String query = getContractorsDetails();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query, contractorRenewId);
			if (rs.next()) {
	            contr = new ContractorRenewal();
	            contr.setContractorRenewId(rs.getString("CONTRACTORREGID"));
	            contr.setUnitCode(rs.getString("UNITCODE"));
	            contr.setOrganisation(rs.getString("UNITNAME"));
	            contr.setContractorCode(rs.getString("CODE"));
	            contr.setContractorName(rs.getString("CONTRACTORNAME"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocationOfWork(rs.getString("LOCOFWORK"));
	            contr.setSupervisorName(rs.getString("SUPERVISORNAME"));
	            contr.setPfCode(rs.getString("PFNUM"));
	            contr.setEmailAdd(rs.getString("EMAILADDR"));
	            contr.setMobileNumber(rs.getString("MOBILENO"));
	            contr.setEsicReg(rs.getString("ESICREGNO"));
	            contr.setContractorValidTo(rs.getString("CONTRACTVALIDTILL"));
	            contr.setContractorClass(rs.getString("SERVICES"));
	            contr.setContractorType(rs.getString("CONTYPE"));
	        }
			return null;
	}
	
	@Override
	public List<ContractorRegistration> getContractorRegistrationList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ContractorRegistration viewContractorDetails(String contractorregId) {
		ContractorRegistration contr = new ContractorRegistration();
		String query = getAllContractorsDetailsView();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorregId);
			if (rs.next()) {
	            contr = new ContractorRegistration();
	            contr.setContractorregId(rs.getString("CONTRACTORREGID"));
	            contr.setPrincipalEmployer(rs.getString("UNITID"));
	            contr.setVendorCode(rs.getString("CODE"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocofWork(rs.getString("LOCOFWORK"));
	            contr.setTotalStrength(rs.getString("TOTALSTRENGTH"));
	            contr.setRcMaxEmp(rs.getString("MAXNOEMP"));
	            contr.setPfNum(rs.getString("PFNUM"));
	            contr.setNatureOfWork(rs.getString("NATUREOFWORK"));
	            contr.setContractFrom(rs.getString("PERIODSTARTDATE"));
	            contr.setContractTo(rs.getString("PERIODENDDATE"));
	            contr.setContractType(rs.getString("CONTTYPE"));
	            contr.setRcVerified(rs.getString("RCVALIDATED"));
	            contr.setMainContractor(rs.getString("MAINCONTRACTOR"));
	        }
			return contr;
	}
	@Override
	public List<ContractorRegistration> getContractorRenewalList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	String query = getAllRenewalContractors();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		peList.add(pe);
	}
	return peList;
	}
	
	@Override
	public List<ContractorRegistrationPolicy> viewContractorAddDetails(String contractorregId) {
	List<ContractorRegistrationPolicy> conList= new ArrayList<ContractorRegistrationPolicy>();
	String query = getAllContractorsAdditionalDetails();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorregId);
	while(rs.next()) {
		ContractorRegistrationPolicy pe = new ContractorRegistrationPolicy();
		
		pe.setContractorRegId(rs.getString("CONTRACTORREGID"));
		pe.setWoNumber(rs.getString("WONUMBER"));
		pe.setNatureOfJob(rs.getString("NATUREOFID"));
		pe.setDocumentType(rs.getString("LICENCETYPE"));
		pe.setDocumentNumber(rs.getString("WCCODE"));
		pe.setCoverage(rs.getInt("WCTOTAL"));
		pe.setValidFrom(rs.getString("WCFROMDTM"));
		pe.setValidTo(rs.getString("WCTODTM"));
		pe.setFileName(rs.getString("ATTACHMENTNAME"));
		conList.add(pe);
	}
	return conList;
	}
	
	@Override
	public ContractorRenewal viewContractorRenewDetails(String contractorRenewId) {
		ContractorRenewal contr = new ContractorRenewal();
		String query = getRenewalView();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorRenewId);
			if (rs.next()) {
	            contr = new ContractorRenewal();
	            contr.setContractorRenewId(rs.getString("CONTRACTORREGID"));
	            contr.setUnitCode(rs.getString("UNITCODE"));
	            contr.setOrganisation(rs.getString("UNITNAME"));
	            contr.setContractorCode(rs.getString("CONTRACTORID"));
	            contr.setContractorName(rs.getString("CONTRACTORNAME"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocationOfWork(rs.getString("LOCOFWORK"));
	            contr.setSupervisorName(rs.getString("SUPERVISORNAME"));
	            contr.setPfCode(rs.getString("PFNUM"));
	            contr.setEmailAdd(rs.getString("EMAILADDR"));
	            contr.setMobileNumber(rs.getString("MOBILENO"));
	            contr.setEsicReg(rs.getString("ESICREGNO"));
	            contr.setContractorValidTo(rs.getString("CONTRACTVALIDTILL"));
	            contr.setContractorClass(rs.getString("SERVICES"));
	            contr.setContractorType(rs.getString("CONTTYPE"));
	        }
			return contr;
	}
	
	@Override
	public List<ContractorRenewal> viewContractorRenewAddDetails(String userId) {
	List<ContractorRenewal> conList= new ArrayList<ContractorRenewal>();
	String query = getRenewalContractorsView();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRenewal pe = new ContractorRenewal();
		pe.setDocumentType(rs.getString("LICENCETYPE"));
		pe.setDocumentNumber(rs.getString("WCCODE"));
		pe.setCoverage(rs.getString("WCTOTAL"));
		pe.setValidFrom(rs.getString("WCFROMDTM"));
		pe.setValidTo(rs.getString("WCTODTM"));
		pe.setAttachment(rs.getString("ATTACHMENTNAME"));
		pe.setPanIndia(rs.getString("ISGLOBAL"));
		pe.setSubContractor(rs.getString("SUBCONTAPPL"));
		
		conList.add(pe);
	
	}
	return conList;
	}
	@Override
	public List<MasterUser> getRoleList(String userId) {
	List<MasterUser> peList= new ArrayList<MasterUser>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_ROLE_LIST);
	while(rs.next()) {
		MasterUser pe = new MasterUser();
		pe.setUserId(rs.getInt("UserId"));
		pe.setFirstName(rs.getString("FirstName"));
		pe.setLastName(rs.getString("LastName"));
		pe.setContactNumber(rs.getString("ContactNumber"));
		pe.setRoleName(rs.getString("RoleName"));
		
		peList.add(pe);
	}
	return peList;
	}

	
	@Override
	public String saveRole(MasterUser user) {
		String result = null; 
		int status=0;
        Object[] parameters = new Object[] {user.getFirstName(),user.getLastName(),user.getUserId(),user.getEmailId(),user.getContactNumber(),user.getPassword(),user.getRoleName()}; 
        try {
           status  = jdbcTemplate.update(ContractorQueryBank.SAVE_ROLE_FORM, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
        }
    return String.valueOf(status);
	}
	
	@Override
	public String saveRenew(ContractorRenewal contrenew) {
		String result = null; 
		String query = setRenewalDetails();
		int status=0;
        Object[] parameters = new Object[] {contrenew.getContractorRenewId(),contrenew.getUnitCode(),contrenew.getOrganisation(),contrenew.getContractorCode(),contrenew.getContractorName(),contrenew.getManagerName(),contrenew.getLocationOfWork(),contrenew.getSupervisorName(),contrenew.getPfCode(),contrenew.getEmailAdd(),contrenew.getMobileNumber(),contrenew.getEsicReg(),contrenew.getContractorValidTo(),contrenew.getContractorClass(),contrenew.getContractorType()}; 
        try {
           status  = jdbcTemplate.update(query, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
        }
    return String.valueOf(status);
	}
	
	@Override
    public boolean checkIfIdExists(String contractorregId) {
        String sql = setuniqueregistrationid();
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{contractorregId}, Integer.class);
        return count != null && count > 0; // True if the ID exists
    }

	public String getMaxContrRegId() {
		return QueryFileWatcher.getQuery("GET_MAX_CONTRACTOR_REG_ID");
	}
	
	@Override
	public String generateContractorRegistrationId() {
		String contrRegId = null;
	    try {
	    	String query = getMaxContrRegId();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	        if (rs.next()) {
	        	contrRegId = rs.getString("CONTRACTORREGID");
	        }
	    } catch (Exception e) {
	        log.error("Error generating contrRegId", e);
	    }
	    return contrRegId;
	}

	public String getAllContractorForReg() {
	    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_FOR_REG");
	}
	@Override
	public List<Contractor> getAllContractorForReg(String unitId) {
		log.info("Entering into getAllContractorForReg dao method "+unitId);
		List<Contractor> contList= new ArrayList<Contractor>();
		String query=getAllContractorForReg();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			Contractor cont = new Contractor();
			cont.setContractorId(rs.getString("CONTRACTORID"));
			cont.setContractorName(rs.getString("NAME"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("CODE"));
			cont.setContractorAddress(rs.getString("ADDRESS"));
			contList.add(cont);
		}
		log.info("Exiting from getAllContractorForReg dao method "+contList.size());
		return contList;
	}
	public String getAllContractorDetailForReg() {
	    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_DETAIL_FOR_REG");
	}
	@Override
	public Contractor getAllContractorDetailForReg(String unitId, String contractorId) {
		String query=getAllContractorDetailForReg();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId,unitId);
		Contractor cont = new Contractor();
		while(rs.next()) {
			
			cont.setContractorId(rs.getString("CONTRACTORID"));
			cont.setContractorName(rs.getString("NAME"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("CODE"));
			cont.setContractorAddress(rs.getString("ADDRESS"));
			cont.setManagerName(rs.getString("MANAGERNM"));
			cont.setEsiwc(rs.getString("ESIWC"));
			cont.setLicenseNumber(rs.getString("LICENSENUM"));
			cont.setValidFrom(rs.getString("VALIDFROMDT"));
			cont.setValidTo(rs.getString("VALIDTODT"));
			cont.setCoverage(rs.getInt("COVERAGE"));
			cont.setTotalStrength(rs.getInt("TOTALSTRENGTH"));
			cont.setMaxNoEmp(rs.getInt("MAXNOEMP"));
			cont.setNatureOfWork(rs.getString("NATUREOFWORK"));
			cont.setLocationOfWork(rs.getString("LOCOFWORK"));
			cont.setPeriodStartDate(rs.getString("PERIODSTARTDT"));
			cont.setPeriodEndDate(rs.getString("PERIODENDDT"));
			cont.setVendorCode(rs.getString("VENDORCODE"));
			cont.setPfCode(rs.getString("PFCODE"));
			cont.setEsiValidFrom(rs.getString("ESIVALIDFROM"));
			cont.setEsiValidTo(rs.getString("ESIVALIDTO"));
			cont.setEscWcCoverage(rs.getInt("ESIWCCOVERAGE"));
			cont.setPfNum(rs.getString("PFNUM"));
			cont.setPfApplyDate(rs.getString("PFAPPLYDT"));
			cont.setBlocked(rs.getString("IS_BLOCKED").equals("Y")?true:false);
			cont.setRcValidated(rs.getString("RCVALIDATED"));
			cont.setLlValidated(rs.getString("LLVALIDATED"));
			cont.setWcValidated(rs.getString("WCVALIDATED"));
		}
		return cont;
	}

//	@Override
//		public void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg) {
//		    String sql = "INSERT INTO CMSContractorRegPolicy (" +
//		    		" [CONTRACTORID],[UNITID],[WONUMBER] ,[LICENCETYPE],[WCCODE] ,[NATUREOFID] ,[WCTOTAL]  ,[WCFROMDTM] ,[WCTODTM] ,[ATTACHMENTNAME],[CREATEDDTM] ,[CREATEDBY],[CONTRACTORREGID]) " +
//		            " VALUES (?,?,?,?, ?,?,?,? ,?,?,GETDATE(),?,?)";
//
//		    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//		        public void setValues(PreparedStatement ps, int i) throws SQLException {
//		            ContractorRegistrationPolicy policy = policies.get(i);
//		          
//		            ps.setString(1, contreg.getContractorId());
//		            ps.setString(2, contreg.getPrincipalEmployer());
//		            ps.setString(3, policy.getWoNumber());
//		            ps.setString(4, policy.getDocumentType());
//		            ps.setString(5, policy.getDocumentNumber());
//		            ps.setString(6, policy.getNatureOfJob());
//		            ps.setInt(7, policy.getCoverage());
//		            ps.setString(8, policy.getValidFrom());
//		            ps.setString(9, policy.getValidTo());
//		            ps.setString(10, policy.getFileName());
//		            ps.setString(11, contreg.getCreatedBy());
//		            ps.setString(12, contreg.getContractorregId());
//		        }
//
//		        public int getBatchSize() {
//		            return policies.size();
//		        }
//		    });
//		}

	@Override
	public   void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg) {
		 String sql = "INSERT INTO CMSContractorRegPolicy (" +
		    		" [CONTRACTORID],[UNITID],[WONUMBER] ,[LICENCETYPE],[WCCODE] ,[NATUREOFID] ,[WCTOTAL]  ,[WCFROMDTM] ,[WCTODTM] ,[ATTACHMENTNAME],[CREATEDDTM] ,[CREATEDBY],[CONTRACTORREGID]) " +
		            " VALUES (?,?,?,?, ?,?,?,? ,?,?,GETDATE(),?,?)";
		int[] save = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setString(1, contreg.getContractorId());
	            ps.setString(2, contreg.getPrincipalEmployer());
	            ps.setString(3, policies.get(i).getWoNumber());
	            ps.setString(4, policies.get(i).getDocumentType());
	            ps.setString(5, policies.get(i).getDocumentNumber());
	            ps.setString(6, policies.get(i).getNatureOfJob());
	            ps.setInt(7, policies.get(i).getCoverage());
	            ps.setString(8, policies.get(i).getValidFrom());
	            ps.setString(9, policies.get(i).getValidTo());
	            ps.setString(10, policies.get(i).getFileName());
	            ps.setString(11, contreg.getCreatedBy());
	            ps.setString(12, contreg.getContractorregId());
				
			}

			public int getBatchSize() {
				return policies.size();
			}
		});
	}

	 public String getAllWoByPeAndCont() {
		    return QueryFileWatcher.getQuery("GET_WO_FOR_CONTRACTOR_REG");
		}
	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		List<Workorder> woList= new ArrayList<Workorder>();
		String query=getAllWoByPeAndCont();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, contractorId,unitId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
			wo.setName(rs.getString("SAP_WORKORDER_NUM"));
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}
}

