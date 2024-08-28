package com.wfd.dot1.cwfm.dao;

import java.util.Date;
import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSMinimumWage;
import com.wfd.dot1.cwfm.entity.CMSWage;

public interface MinimumWageDAO {
    List<CMSMinimumWage> findByPrincipalEmployerId(long principalEmployerId);
    String fetchTradeName(long tradeId);
    String fetchSkillName(long skillId);
    String fetchStateName(long stateId);
    CMSWage fetchCMSWage(long wageId);
    List<CMSMinimumWage> getMinimumWagesByPrincipalEmployerAndEffectiveDate(long principalEmployerId, Date effectiveDate);
	CMSWage fetchCMSWageBasedOnWageId(int wageId);
}
