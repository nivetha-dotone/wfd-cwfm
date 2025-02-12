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
@Repository
public class OrgLevelMappingDaoImpl implements OrgLevelMappingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @Override
    public List<OrgLevelMapping> findAll() {
        String sql = "SELECT om.*, os.SHORTNM as shortName, os.LONGDSC as longDescription " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID";
        List<OrgLevelMapping> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
        System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
        return result;
    }

    @Override
    public OrgLevelMapping findById(Long id) {
        String sql = "SELECT om.*, os.SHORTNM, os.LONGDSC " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
                     "WHERE om.ORGACCTSETID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    public List<OrgLevelMapping> findOrgById(Long id) {
        String sql = "SELECT om.*, os.SHORTNM, os.LONGDSC " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
                     "WHERE om.ORGACCTSETID = ?";

        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }
    @Override
    public void save(OrgLevelMapping mapping) {
        String sql = "INSERT INTO OLACCTSETMM (ORGLEVELENTRYID, ORGACCTSETID) VALUES (?, ?)";
        jdbcTemplate.update(sql, mapping.getOrgLevelEntryId(), mapping.getOrgAcctSetId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM OLACCTSETMM WHERE ORGLEVELENTRYID = ?";
        jdbcTemplate.update(sql, id);
    }
    private static final String GET_AVAILABLE_ENTRIES_QUERY = 
            "SELECT ORGLEVELENTRYID, NAME, DESCRIPTION " +
            "FROM ORGLEVELENTRY " +
            "WHERE ORGLEVELDEFID = ? AND (INACTIVE = 0 OR INACTIVE IS NULL)";

        // SQL query for fetching selected entries
        private static final String GET_SELECTED_ENTRIES_QUERY = 
            "SELECT ORGLEVELENTRYID, NAME, DESCRIPTION " +
            "FROM ORGLEVELENTRY " +
            "WHERE ORGLEVELDEFID = ? AND INACTIVE = 1";

        @Override
        public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
            return jdbcTemplate.query(GET_AVAILABLE_ENTRIES_QUERY, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
        }

        @Override
        public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
            return jdbcTemplate.query(GET_SELECTED_ENTRIES_QUERY, new Object[]{orgLevelDefId}, new OrgLevelEntryRowMapper());
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
				/*
				 * if (!(orgLevelDefIdObj instanceof String)) { throw new
				 * IllegalArgumentException("orgLevelDefId must be a string"); } String
				 * orgLevelDefId = (String) orgLevelDefIdObj;
				 * 
				 * // Extract 'selected' entries Object selectedEntriesObj =
				 * mapping.get("selected"); if (!(selectedEntriesObj instanceof List)) { throw
				 * new IllegalArgumentException("'selected' must be a list of maps"); }
				 */

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
            String insertQuery = "INSERT INTO ORGACCTSET (SHORTNM, LONGDSC, ALLOWALLSW, VERSIONCNT, USEVALIDACCTSW) "
                               + "VALUES (?, ?, 0, 1, 0)";

            // Use KeyHolder to get the generated ORGACCTSETID
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, shortName);
                ps.setString(2, longDescription);
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        }

        private void deleteExistingRecords(String orgLevelDefId) {
            String deleteOLACCTSETMMQuery = "DELETE FROM OLACCTSETMM WHERE ORGLEVELENTRYID = ?";
            jdbcTemplate.update(deleteOLACCTSETMMQuery, orgLevelDefId);
        }

        private void insertIntoOLACCTSETMM(String orgLevelDefId, int orgAcctSetId) {
            String insertQuery = "INSERT INTO OLACCTSETMM (ORGLEVELENTRYID, ORGACCTSETID, UPDATEDTM) VALUES (?, ?, GETDATE())";
            jdbcTemplate.update(insertQuery, orgLevelDefId, orgAcctSetId);
        }
        public int getNextOrgAcctSetId() {
            // Query to get the maximum OrgAcctSetId from the ORGACCTSET table
            String query = "SELECT MAX(ORGACCTSETID) FROM ORGACCTSET";

            // Execute the query to get the maximum ID
            Integer maxOrgAcctSetId = jdbcTemplate.queryForObject(query, Integer.class);

            // If no data exists, return 1 as the starting point
            if (maxOrgAcctSetId == null) {
                return 1; // First ID to be created
            }
            
            // Otherwise, return the next available ID by incrementing the max ID by 1
            return maxOrgAcctSetId + 1;
        }
        
        @Override
        public int saveOrgAcctSet(OrgLevelMapping orgAcctSet) {
            // Use OUTPUT clause to retrieve the inserted ID
            String insertSql = "INSERT INTO ORGACCTSET (SHORTNM, LONGDSC) " +
                               "OUTPUT INSERTED.ORGACCTSETID " +
                               "VALUES (?, ?)";

            // Perform the insert operation and retrieve the generated ID
            Integer generatedId = jdbcTemplate.queryForObject(insertSql,
                    new Object[] { orgAcctSet.getShortName(), orgAcctSet.getLongDescription() },
                    Integer.class);

            // Check if the generatedId is null
            if (generatedId == null) {
                System.out.println("Failed to retrieve the generated ID after insert.");
                throw new RuntimeException("Failed to retrieve the generated ID after insert.");
            }

            // Log the generated ID
            System.out.println("Generated ID: " + generatedId);

            // Return the generated ID
            return generatedId;
        }


        
		/*
		 * public void saveOrgAcctSet(OrgLevelMapping orgAcctSet) { String insertQuery =
		 * "INSERT INTO ORGACCTSET ( SHORTNM, LONGDSC) VALUES ( ?, ?)";
		 * jdbcTemplate.update(insertQuery, orgAcctSet.getShortName(),
		 * orgAcctSet.getLongDescription()); }
		 */

        // Save mapping in the OLACCTSETMM table
        public void saveOrgLevelMapping(OrgLevelMapping orgLevelMapping) {
            String insertMappingQuery = "INSERT INTO OLACCTSETMM (ORGLEVELENTRYID, ORGACCTSETID, UPDATEDTM) VALUES (?, ?, getdate())";
            jdbcTemplate.update(insertMappingQuery, orgLevelMapping.getOrgLevelEntryId(), orgLevelMapping.getOrgAcctSetId());
        }
        public List<OrgLevelMapping> findAvailableMappings(Long id) {
            String sql = "SELECT om.* " +
                         "FROM OLACCTSETMM om " +
                         "WHERE om.ORGACCTSETID NOT IN " +
                         "(SELECT ORGACCTSETID FROM ORGACCTSET WHERE ORGACCTSETID = ?)";

            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelMapping.class), id);
        }

        /**
         * Fetch the selected mappings for the given ID.
         */
        public List<OrgLevelMapping> findSelectedMappings(Long id) {
        	 String sql = "SELECT om.ORGLEVELENTRYID, om.ORGACCTSETID, os.SHORTNM, os.LONGDSC " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
                     "WHERE om.ORGACCTSETID = ?";

//        List<OrgLevelMapping> mappings = jdbcTemplate.query(sql, new OrgLevelMappingRowMapper(), id);
//
//        // Debug log the mappings to ensure correct mapping of shortName and longDescription
//        for (OrgLevelMapping mapping : mappings) {
//            System.out.println("Mapping ID: " + mapping.getOrgLevelEntryId() + 
//                               ", shortName: " + mapping.getShortName() + 
//                               ", longDescription: " + mapping.getLongDescription());
//        }

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelMapping.class), id);
         }
        
     //Admin

        public OrgLevelMapping findBasicInfo(Long id) {
            String sql = "SELECT TOP 1 os.SHORTNM, os.LONGDSC " +
                         "FROM OLACCTSETMM om " +
                         "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
                         "WHERE om.ORGACCTSETID = ?";
            
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
        }
        @Override
        public List<OrgLevelMapping> findAllMaps() {
            String sql = "SELECT distinct top 1 os.ORGACCTSETID, os.SHORTNM as shortName, os.LONGDSC as longDescription " +
                         "FROM OLACCTSETMM om " +
                         "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID";
            List<OrgLevelMapping> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
            System.out.println("Fetched OrgLevelMappings: " + result);  // Log the fetched data
            return result;
        }
		
}
