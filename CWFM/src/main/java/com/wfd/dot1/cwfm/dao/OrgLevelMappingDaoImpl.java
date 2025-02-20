package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class OrgLevelMappingDaoImpl implements OrgLevelMappingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public String findall() {
	    return QueryFileWatcher.getQuery("FIND_ALL");
    }
    public String findbyall() {
	    return QueryFileWatcher.getQuery("FIND_BY_ALL");
    }
    public String findorgbyid() {
	    return QueryFileWatcher.getQuery("FIND_ORG_BY_ID");
    }
    public String save() {
	    return QueryFileWatcher.getQuery("SAVE");
    }
    public String delete() {
	    return QueryFileWatcher.getQuery("DELETE");
    }
    public String getavailableentries() {
	    return QueryFileWatcher.getQuery("GET_AVAILABLE_ENTRIES_QUERY");
    }
    public String getselectedentries() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_ENTRIES_QUERY");
    }
    public String insertintoorgactset() {
	    return QueryFileWatcher.getQuery("INSERT_INTO_ORG_ACT_SET");
    }
    public String deleteexistingrecords() {
	    return QueryFileWatcher.getQuery("DELETE_EXISTING_RECORDS");
    }
    public String insertintoorgactsetmm() {
	    return QueryFileWatcher.getQuery("INSERT_INTO_ORG_ACT_SETMM");
    }
    public String getnextorgacctsetid() {
	    return QueryFileWatcher.getQuery("GET_NEXT_ORG_ACCT_SET_ID");
    }
    public String saveorgacctset() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_ACCT_SET");
    }
    public String saveorglevelmapping() {
	    return QueryFileWatcher.getQuery("SAVE_ORG_LEVEL_MAPPING");
    }
    public String findavailablemappings() {
	    return QueryFileWatcher.getQuery("FIND_AVAILABLE_MAPPINGS");
    }
    public String findselectedmappings() {
	    return QueryFileWatcher.getQuery("FIND_SELECTED_MAPPINGS");
    }
    public String findbasicinformation() {
	    return QueryFileWatcher.getQuery("FIND_BASIC_INFORMATION");
    }
    public String findallmaps() {
	    return QueryFileWatcher.getQuery("FIND_ALL_MAPS");
    }
    
    @Override
    public List<OrgLevelMapping> findAll() {
    	String query=findall();
        List<OrgLevelMapping> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
        System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
        return result;
    }

    @Override
    public OrgLevelMapping findById(Long id) {
    	String query=findbyall();
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    
    public List<OrgLevelMapping> findOrgById(Long id) {
    	String query=findorgbyid();
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    
    @Override
    public void save(OrgLevelMapping mapping) {
    	String query=save();
        jdbcTemplate.update(query, mapping.getOrgLevelEntryId(), mapping.getOrgAcctSetId());
    }

    @Override
    public void delete(int id) {
    	String query=delete();
        jdbcTemplate.update(query, id);
    }

        @Override
        public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
        	String query=getavailableentries();
            return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
        }

        @Override
        public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
        	String query=getselectedentries();
            return jdbcTemplate.query(query, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
        }

        // RowMapper to map ResultSet rows to OrgLevelEntryDTO
        private static class OrgLevelEntryRowMapper implements RowMapper<OrgLevelEntryDTO> {
            @Override
            public OrgLevelEntryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
                entry.setOrgLevelDefId((int) rs.getLong("ORGLEVELENTRYID"));
                entry.setName(rs.getString("NAME"));
                entry.setDescription(rs.getString("DESCRIPTION"));
                return entry;
            }
        }
        
        public void saveOrgLevelEntries(List<Map<String, Object>> orgLevelMappings) {
            if (orgLevelMappings == null || orgLevelMappings.isEmpty()) {
                throw new IllegalArgumentException("Input data cannot be null or empty");
            }

            for (Map<String, Object> mapping : orgLevelMappings) {
                // Extract orgLevelDefId with null and type check
                String orgLevelDefIdObj = (String) mapping.get("orgLevelDefId");
                List<Map<String, String>> selectedEntries = (List<Map<String, String>>) mapping.get("selected");
                // Step 1: Delete existing records for this orgLevelDefId
                deleteExistingRecords(orgLevelDefIdObj);
                // Step 2: Insert new records
                for (Map<String, String> entry : selectedEntries) {
                    String shortName = entry.get("shortName");
                    String longDescription = entry.get("longDescription");
                    if (shortName == null || longDescription == null) {
                        throw new IllegalArgumentException("shortName and longDescription cannot be null");
                    }
                    // Insert into ORGACCTSET
                    int orgAcctSetId = insertIntoORGACCTSET(shortName, longDescription);
                    // Insert into OLACCTSETMM
                    insertIntoOLACCTSETMM(orgLevelDefIdObj, orgAcctSetId);
                }
            }
        }
        
        private int insertIntoORGACCTSET(String shortName, String longDescription) {
        	String query=insertintoorgactset();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, shortName);
                ps.setString(2, longDescription);
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        }
        
        private void deleteExistingRecords(String orgLevelDefId) {
        	String query=insertintoorgactset();
            jdbcTemplate.update(query, orgLevelDefId);
        }
        
        private void insertIntoOLACCTSETMM(String orgLevelDefId, int orgAcctSetId) {
        	String query=insertintoorgactsetmm();
            jdbcTemplate.update(query, orgLevelDefId, orgAcctSetId);
        }
        
        public int getNextOrgAcctSetId() {
        	String query=getnextorgacctsetid();
            Integer maxOrgAcctSetId = jdbcTemplate.queryForObject(query, Integer.class);
            if (maxOrgAcctSetId == null) {
                return 1; // First ID to be created
            }
            return maxOrgAcctSetId + 1;
        }

        @Override
        public int saveOrgAcctSet(OrgLevelMapping orgAcctSet) {
        	String query=saveorgacctset();
            Integer generatedId = jdbcTemplate.queryForObject(query,
                    new Object[] { orgAcctSet.getShortName(), orgAcctSet.getLongDescription() },
                    Integer.class);
            if (generatedId == null) {
                System.out.println("Failed to retrieve the generated ID after insert.");
                throw new RuntimeException("Failed to retrieve the generated ID after insert.");
            }
            System.out.println("Generated ID: " + generatedId);
            return generatedId;
        }

        public void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping) {
        	String query=saveorglevelmapping();
            jdbcTemplate.update(query, orgLevelMapping.getOrgLevelEntryId(), orgLevelMapping.getOrgAcctSetId());
        }
        
        public List<OrgLevelMapping> findAvailableMappings(Long id) {
        	String query=findavailablemappings();
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class), id);
        }

        public List<OrgLevelMapping> findSelectedMappings(Long id) {
        	String query=findselectedmappings();
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class), id);
         }
        
        public OrgLevelMapping findBasicInfo(Long id) {
        	String query=findbasicinformation();
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
        }
        
        @Override
        public List<OrgLevelMapping> findAllMaps() {
        	String query=findallmaps();
            List<OrgLevelMapping> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OrgLevelMapping.class));

            System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
            return result;
        }
		
}
