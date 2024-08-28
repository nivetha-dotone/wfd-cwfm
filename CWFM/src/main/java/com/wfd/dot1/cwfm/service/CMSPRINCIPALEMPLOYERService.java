package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import java.util.List;
public interface CMSPRINCIPALEMPLOYERService {
    List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs();
    CMSPrincipalEmployer getCMSPRINCIPALEMPLOYERById(Long id);
    void addCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer);
    void updateCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer);
    void deleteCMSPRINCIPALEMPLOYER(Long id);
    List<CMSPrincipalEmployer> searchCMSPRINCIPALEMPLOYERs(String query);
    List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize);
	int getTotalRecords();
	List<CMSPrincipalEmployer> getEmployeesAfterId(Long lastSeenId, int pageSize);
}
