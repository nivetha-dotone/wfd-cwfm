package com.wfd.dot1.cwfm.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dao.MinimumWageDAO;
import com.wfd.dot1.cwfm.entity.CMSMinimumWage;
import com.wfd.dot1.cwfm.entity.CMSWage;

@Service
public class MinimumWageServiceImpl implements MinimumWageService {

    @Autowired
    private MinimumWageDAO minimumWageDAO;
    @Override
    @Transactional(readOnly = true)
    public List<CMSMinimumWage> getMinimumWagesByPrincipalEmployer(long principalEmployerId) {
        List<CMSMinimumWage> minimumWages = minimumWageDAO.findByPrincipalEmployerId(principalEmployerId);
//        List<CMSMinimumWage> minimumWageDTOs = new ArrayList<>();

//        for (CMSMinimumWage minimumWage : minimumWages) {
//            // Fetch related entity information and set it in CMSMinimumWage entity if needed
//            minimumWage.setTradeName(fetchTradeName(minimumWage.getTradeId()));
//            minimumWage.setSkillName(fetchSkillName(minimumWage.getSkillId()));
//            minimumWage.setStateName(fetchStateName(minimumWage.getStateId()));
//            // Add other properties to the entity as needed
//            minimumWageDTOs.add(minimumWage);
//        }

        return minimumWages;
    }
    @Override
    @Transactional(readOnly = true)
    public String fetchSkillName(long skillId) {
        return minimumWageDAO.fetchSkillName(skillId);
    }

    @Override
    @Transactional(readOnly = true)
    public String fetchStateName(long stateId) {
        return minimumWageDAO.fetchStateName(stateId);
    }
    // Methods to fetch related entity names
    @Override
    @Transactional(readOnly = true)
    public String fetchTradeName(long tradeId) {
        return minimumWageDAO.fetchTradeName(tradeId);
    }
    @Override
    public CMSWage fetchCMSWageBasedOnWageId(int wageId) {
        return minimumWageDAO.fetchCMSWageBasedOnWageId(wageId);
    }
	@Override
	public List<CMSMinimumWage> getMinimumWagesByPrincipalEmployerAndEffectiveDate(long principalEmployerId,
			Date effectiveDate) {
		// TODO Auto-generated method stub
		return minimumWageDAO.getMinimumWagesByPrincipalEmployerAndEffectiveDate(principalEmployerId, effectiveDate);
	}
	@Override
	public CMSWage fetchCMSWage(long wageId) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
//    public String fetchSkillName(long skillId) {
//        List<String> results = (List<String>) hibernateTemplate.find("select name from Skill where id = ?", skillId);
//        return results.isEmpty() ? null : results.get(0);
//    }
//
//    public String fetchStateName(long stateId) {
//        List<String> results = (List<String>) hibernateTemplate.find("select name from State where id = ?", stateId);
//        return results.isEmpty() ? null : results.get(0);
//    }
}
