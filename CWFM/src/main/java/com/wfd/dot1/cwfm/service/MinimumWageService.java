package com.wfd.dot1.cwfm.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.entity.CMSMinimumWage;
import com.wfd.dot1.cwfm.entity.CMSWage;

@Service
public interface MinimumWageService {
    List<CMSMinimumWage> getMinimumWagesByPrincipalEmployer(long principalEmployerId);
    String fetchTradeName(long tradeId);
    String fetchSkillName(long skillId);
    String fetchStateName(long stateId);
	CMSWage fetchCMSWageBasedOnWageId(int wageId);
	CMSWage fetchCMSWage(long wageId);
	 List<CMSMinimumWage> getMinimumWagesByPrincipalEmployerAndEffectiveDate(long principalEmployerId, Date effectiveDate);
	
}
