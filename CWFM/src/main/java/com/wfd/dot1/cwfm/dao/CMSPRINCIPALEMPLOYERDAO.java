package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;

public interface CMSPRINCIPALEMPLOYERDAO {
    List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs();
    CMSPrincipalEmployer getCMSPRINCIPALEMPLOYERById(Long id);
    void addCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer);
    void updateCMSPRINCIPALEMPLOYER(CMSPrincipalEmployer principalEmployer);
    void deleteCMSPRINCIPALEMPLOYER(Long id);
    List<CMSPrincipalEmployer> searchCMSPRINCIPALEMPLOYERs(String query);
    boolean existsByUnitCode(String unitCode);

    boolean existsByUnitName(String unitName);

    boolean existsByOrganization(String organization);
	boolean existsByOrganizationAndNotUnitId(String organization, long unitId);
	boolean existsByUnitCodeAndNotUnitId(String code, long unitId);
	boolean existsByUnitNameAndNotUnitId(String name, long unitId);
	List<CMSPrincipalEmployer> getAllCMSPRINCIPALEMPLOYERs(int page, int pageSize);
	int getTotalRecords();
	List<CMSPrincipalEmployer> getEmployeesAfterId(Long lastSeenId, int pageSize);
}
