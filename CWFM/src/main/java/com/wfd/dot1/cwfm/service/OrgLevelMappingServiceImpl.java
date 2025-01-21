package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.OrgLevelMappingDao;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;

@Service
public class OrgLevelMappingServiceImpl implements OrgLevelMappingService {

    @Autowired
    private OrgLevelMappingDao orgLevelMappingDao;

    @Override
    public List<OrgLevelMapping> listMappings() {
        return orgLevelMappingDao.findAll();
    }

    @Override
    public void save(OrgLevelMapping mapping) {
        orgLevelMappingDao.save(mapping);
    }

    @Override
    public OrgLevelMapping getMappingById(int id) {
        return orgLevelMappingDao.findById(id);
    }

    @Override
    public void delete(int id) {
        orgLevelMappingDao.delete(id);
    }

    @Override
    public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
        return orgLevelMappingDao.getAvailableEntries(orgLevelDefId);
    }

    @Override
    public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
        return orgLevelMappingDao.getSelectedEntries(orgLevelDefId);
    }

	@Override
	public void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings) {
		orgLevelMappingDao.saveOrgLevelEntries(orgLevelMappings);
		
	}
    
    
}
