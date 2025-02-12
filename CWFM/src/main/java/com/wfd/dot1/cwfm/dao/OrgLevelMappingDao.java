package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;

public interface OrgLevelMappingDao {
	 List<OrgLevelMapping> findAll();
	    void save(OrgLevelMapping mapping);
	    OrgLevelMapping findById(Long id);
	    void delete(int id);
	    List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId);
	    List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId);
		void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings);
		int getNextOrgAcctSetId();
		int saveOrgAcctSet(OrgLevelMapping orgAcctSet);
		void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping);
		List<OrgLevelMapping> findAvailableMappings(Long id);
		List<OrgLevelMapping> findSelectedMappings(Long id);
		OrgLevelMapping findBasicInfo(Long id);
		List<OrgLevelMapping> findAllMaps();
}
