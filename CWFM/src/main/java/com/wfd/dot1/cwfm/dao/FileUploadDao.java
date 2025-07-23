package com.wfd.dot1.cwfm.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSSubContractor;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLN;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorWorkorderTYP;
import com.wfd.dot1.cwfm.pojo.KTCWorkorderStaging;
import com.wfd.dot1.cwfm.pojo.MimumWageMasterTemplate;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface FileUploadDao {
    void saveData(String[] data);

    void saveGeneralMaster(CmsGeneralMaster gm);
    void saveMinimumWage(MimumWageMasterTemplate mw);
    Long savePrincipalEmployer(PrincipalEmployer p);
    Long insertIntoWageTable(MinimumWageDTO dto);
    void insertIntoMinimumWageTable(LocalDate fromDate, Long wageId);
    Long saveContractor(Contractor contractor);
    void savePemm(CMSContrPemm pemm);
   // int savepe(String code);
	void savewc(CmsContractorWC wc);
	void savecsc(CMSSubContractor csc);

	Long saveWorkorder(Workorder workorder);

	void saveWorkorderLN(CMSWorkorderLN woln);

	void saveWorkorderTyp(ContractorWorkorderTYP wotyp);

	Long getUnitIdByPlantCodeAndOrg(String plantCode, String organization);

	Long getContractorIdbyUnitId(Long unitId);

	//Long getWorkorderIdBySapNumber(String sapWorkorderNumber);	
	
	String getCSVHeaders(String templateType);
	
	boolean isPrincipalEmployerCodeExists(String code);
	
	boolean isContractorCodeExists(String contractorCode);
	
	Long getStateIdByName(String stateName);
	
	void savePEState(Long unitId, Long stateId);
	
	void saveWorkorderToStaging(KTCWorkorderStaging workorderStaging);
	
	void callWorkorderProcessingSP();

	boolean isGmNameGmDescriptionExists(String gmName, String gmDescription);

	void saveWorkmenBulkUploadDraftToStaging(WorkmenBulkUpload staging);

	void callWorkmenBulkUploadDraftProcessingSP();

	Integer getUnitIdByName(String unitCode);
	
	Integer getContractorIdByName(String vendorCode);

	Integer getTradeIdByName(String tradeName);

    // Get Workmen Wage Category ID by name
    Integer getWageCategoryId(String EICNumber);

    // Get GMID (Generic Master ID) by GMNAME and master type (like skill, dept, access area, etc.)
    Integer getGeneralMasterId(String gmName);

	Integer getWorkorderId(String workorderNumber);

	Integer getSkillIdByName(String skill);

	Integer geteicId(String department, Integer unitId, String ECnumber);

	//void saveToGatePassMain(WorkmenBulkUpload record);

	

	



}
