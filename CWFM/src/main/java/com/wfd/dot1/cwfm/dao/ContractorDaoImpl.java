package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.queries.ContractorQueryBank;
@Repository
public class ContractorDaoImpl implements ContractorDao{
	
	private static final Logger log = LoggerFactory.getLogger(ContractorDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;

	@Override
	public Contractor getContractorById(String contractorId) {
		SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_CONTRACOTR_BY_ID,contractorId);
        
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
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+ContractorQueryBank.GET_ALL_WORKORDER_BY_PE_AND_CONT);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_ALL_WORKORDER_BY_PE_AND_CONT,contractorId,unitId);
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
		log.info("Query to getcontrsByContractorIdAndUnitIdAndLicenseType "+ContractorQueryBank.GET_MAPPING_BY_PE_CONT);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_MAPPING_BY_PE_CONT,contractorId,principalEmployerId,string);
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
			SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_CONTRPEMM_BY_PE_AND_CONT,contractorId,principalEmployerId);
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


}
