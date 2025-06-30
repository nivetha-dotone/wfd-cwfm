package com.wfd.dot1.cwfm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.BillConfigDao;
import com.wfd.dot1.cwfm.dao.DotTypeDao;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.MasterUser;

@Service
public class DotTypeServiceImpl implements DotTypeService {
	@Autowired
    private DotTypeDao dotTypedao;
	
	 @Override
	    public void saveWorkflowType(Long principalEmployerId,Long businessTypeId, int dotType, String createdBy) {
		 Long buid = dotTypedao.findBuidByUnitId(principalEmployerId);
		 if (buid == null) {
			 throw new IllegalStateException("There is no Business Type for this Principal Employer");
			 }
	        dotTypedao.insertWorkflowType(buid, dotType, createdBy);
	    }
	
}
