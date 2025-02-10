package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.queries.ContractorQueryBank;
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
	            contr.setRefId(rs.getLong("REFID"));
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
	@Value("${SAVE_INSERT_CONTRACTOR_DETAILS}")
	 private String saveContractorDetails;
	@Override
	public String saveReg(ContractorRegistration contreg) {
		String result = null; 

		int status=0;
        Object[] parameters = new Object[] {contreg.getContractorregId(),contreg.getPrincipalEmployer(),contreg.getVendorCode(),contreg.getManagerName(),contreg.getLocofWork(),contreg.getTotalStrength(),contreg.getRcMaxEmp(),contreg.getPfNum(),contreg.getNatureOfWork(),contreg.getContractFrom(),contreg.getContractTo(),contreg.getContractType(),contreg.getRcVerified()}; 

        try {
           status  = jdbcTemplate.update(saveContractorDetails, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
            
        }
    

    return String.valueOf(status);
	}
	
	 @Value("${GET_ALL_CONTRACTORS}")
	 private String getAllContractors;
	
	@Override
	public List<ContractorRegistration> getContractorRegistrationList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(getAllContractors);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		
		peList.add(pe);
	
	}
	return peList;
	}
	 @Value("${GET_CONTRACTOR_DETAILS}")
	 private String getContractorsDetails;
	@Override
	public ContractorRenewal getContractorDetails(String contractorRenewId) {
		ContractorRenewal contr = null;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getContractorsDetails, contractorRenewId);
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
	 @Value("${GET_ALL_CONTRACTOR_DETAIL_VIEW}")
	 private String getAllContractorsDetailsView;
	@Override
	public ContractorRegistration viewContractorDetails(String contractorregId) {
		ContractorRegistration contr = new ContractorRegistration();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getAllContractorsDetailsView,contractorregId);
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
	@Value("${GET_ALL_RENEWAL_LIST_CONTRACTORS}")
	 private String getAllRenewalContractors;
	@Override
	public List<ContractorRegistration> getContractorRenewalList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(getAllRenewalContractors);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		
		
		peList.add(pe);
	
	}
	return peList;
	}
	@Value("${GET_ALL_CONTRACTOR_ADD_DETAIL_VIEW}")
	 private String getAllContractorsAdditionalDetails;
	@Override
	public List<ContractorRegistration> viewContractorAddDetails(String userId) {
	List<ContractorRegistration> conList= new ArrayList<ContractorRegistration>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(getAllContractorsAdditionalDetails);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setWorkOrderNum(rs.getString("WONUMBER"));
		pe.setNatureOfJob(rs.getString("JOB_NAME"));
		pe.setDocumentType(rs.getString("LICENCETYPE"));
		pe.setDocumentNum(rs.getString("WCCODE"));
		pe.setCoverage(rs.getString("WCTOTAL"));
		pe.setValidFrom(rs.getString("WCFROMDTM"));
		pe.setValidTo(rs.getString("WCTODTM"));
		pe.setAttachments(rs.getString("ATTACHMENTNAME"));
		
		conList.add(pe);
	
	}
	return conList;
	}
	@Value("${GET_RENEWAL_CONTRACTORS_VIEW}")
	 private String getRenewalView;
	@Override
	public ContractorRenewal viewContractorRenewDetails(String contractorRenewId) {
		ContractorRenewal contr = new ContractorRenewal();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getRenewalView,contractorRenewId);
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
	@Value("${GET_RENEW_CONTRACTOR_ADD_DETAIL_VIEW}")
	 private String getRenewalContractorsView;
	@Override
	public List<ContractorRenewal> viewContractorRenewAddDetails(String userId) {
	List<ContractorRenewal> conList= new ArrayList<ContractorRenewal>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(getRenewalContractorsView);
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
	@Value("${SET_RENEWAL_DETAILS}")
	 private String setRenewalDetails;
	@Override
	public String saveRenew(ContractorRenewal contrenew) {
		String result = null; 

		int status=0;
        Object[] parameters = new Object[] {contrenew.getContractorRenewId(),contrenew.getUnitCode(),contrenew.getOrganisation(),contrenew.getContractorCode(),contrenew.getContractorName(),contrenew.getManagerName(),contrenew.getLocationOfWork(),contrenew.getSupervisorName(),contrenew.getPfCode(),contrenew.getEmailAdd(),contrenew.getMobileNumber(),contrenew.getEsicReg(),contrenew.getContractorValidTo(),contrenew.getContractorClass(),contrenew.getContractorType()}; 

        try {
           status  = jdbcTemplate.update(setRenewalDetails, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
            
        }
    

    return String.valueOf(status);
	}
	@Value("${SET_UNIQUE_REGISTRATION_ID}")
	private String setuniqueregistrationid;
	@Override
    public boolean checkIfIdExists(String contractorregId) {
        String sql = setuniqueregistrationid;
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{contractorregId}, Integer.class);
        return count != null && count > 0; // True if the ID exists
    }
}

