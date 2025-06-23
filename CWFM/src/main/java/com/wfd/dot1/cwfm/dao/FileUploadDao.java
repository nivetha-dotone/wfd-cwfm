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
import com.wfd.dot1.cwfm.pojo.MimumWageMasterTemplate;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
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

}
