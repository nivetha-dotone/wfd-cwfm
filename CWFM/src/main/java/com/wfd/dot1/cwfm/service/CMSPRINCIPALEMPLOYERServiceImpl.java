package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dao.CMSPRINCIPALEMPLOYERDAO;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
@Service
public class CMSPRINCIPALEMPLOYERServiceImpl implements CMSPRINCIPALEMPLOYERService {
    private CMSPRINCIPALEMPLOYERDAO cmSPRINCIPALEMPLOYERDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(CMSPRINCIPALEMPLOYERServiceImpl.class);
    
    @Autowired
    public CMSPRINCIPALEMPLOYERServiceImpl(CMSPRINCIPALEMPLOYERDAO cmSPRINCIPALEMPLOYERDAO) {
        this.cmSPRINCIPALEMPLOYERDAO = cmSPRINCIPALEMPLOYERDAO;
    }
    

    @Transactional
    @Override
    public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs() {
        return cmSPRINCIPALEMPLOYERDAO.getAllCMSPRINCIPALEMPLOYERs();
    }
    
    @Transactional
    @Override
    public CMSPrincipalEmployer getCMSPRINCIPALEMPLOYERById(Long id) {
        return cmSPRINCIPALEMPLOYERDAO.getCMSPRINCIPALEMPLOYERById(id);
    }

    @Transactional
    @Override
    public void addCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
        cmSPRINCIPALEMPLOYERDAO.addCMSPRINCIPALEMPLOYER(principalEmployer);
    }
    @Transactional
    @Override
    public List<CMSPrincipalEmployer> searchCMSPRINCIPALEMPLOYERs(String query) {
    	return cmSPRINCIPALEMPLOYERDAO.searchCMSPRINCIPALEMPLOYERs(query);
    }
    
    @Transactional
    @Override
    public void updateCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer) {
    	 cmSPRINCIPALEMPLOYERDAO.updateCMSPRINCIPALEMPLOYER(principalEmployer);
		/*
		 * try { if (principalEmployer != null && principalEmployer.getUNITID() != -1L)
		 * { sessionFactory.getCurrentSession().merge(principalEmployer); } else {
		 * LOGGER.error("Cannot update principal employer. Invalid input or ID is null."
		 * ); } } catch (Exception e) {
		 * LOGGER.error("Error occurred while updating principal employer", e); }
		 */
    }
    

//    @Transactional
//    @Override
//    public void updateCMSPRINCIPALEMPLOYER(CMSPRINCIPALEMPLOYER principalEmployer) {
//        cmSPRINCIPALEMPLOYERDAO.updateCMSPRINCIPALEMPLOYER(principalEmployer);
//    }
//    
//    public void updateCMSPRINCIPALEMPLOYER(CMSPRINCIPALEMPLOYER principalEmployer) {
//        // Ensure that the entity exists in the database before attempting to update
//        if (principalEmployer != null && principalEmployer.getUNITID() != null) {
//            entityManager.merge(principalEmployer); // Merge the entity with the current persistence context
//        } else {
//            LOGGER.error("Cannot update principal employer. Invalid input or ID is null.");
//        }
//    }

    @Transactional
    @Override
    public void deleteCMSPRINCIPALEMPLOYER(Long id) {
        cmSPRINCIPALEMPLOYERDAO.deleteCMSPRINCIPALEMPLOYER(id);
    }

    @Transactional
	@Override
	public List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize) {
		return cmSPRINCIPALEMPLOYERDAO.getAllCMSPRINCIPALEMPLOYERs(page,pageSize);
	}

    @Transactional
	@Override
	public int getTotalRecords() {
		// TODO Auto-generated method stub
		return cmSPRINCIPALEMPLOYERDAO.getTotalRecords();
	}

    @Transactional
	@Override
	public List<CMSPrincipalEmployer> getEmployeesAfterId(Long lastSeenId, int pageSize) {
		// TODO Auto-generated method stub
		return cmSPRINCIPALEMPLOYERDAO.getEmployeesAfterId(lastSeenId,pageSize);
	}
}
