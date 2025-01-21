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
        String sql = "SELECT om.*, os.SHORTNM, os.LONGDSC " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
    }

    @Override
    public OrgLevelMapping findById(int id) {
        String sql = "SELECT om.*, os.SHORTNM, os.LONGDSC " +
                     "FROM OLACCTSETMM om " +
                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
                     "WHERE om.ORGLEVELENTRYID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
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
                Object orgLevelDefIdObj = mapping.get("orgLevelDefId");
                if (!(orgLevelDefIdObj instanceof String)) {
                    throw new IllegalArgumentException("orgLevelDefId must be a string");
                }
                String orgLevelDefId = (String) orgLevelDefIdObj;

                // Extract 'selected' entries
                Object selectedEntriesObj = mapping.get("selected");
                if (!(selectedEntriesObj instanceof List)) {
                    throw new IllegalArgumentException("'selected' must be a list of maps");
                }

                List<Map<String, String>> selectedEntries = (List<Map<String, String>>) selectedEntriesObj;

                // Step 1: Delete existing records for this orgLevelDefId
                deleteExistingRecords(orgLevelDefId);

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
                    insertIntoOLACCTSETMM(orgLevelDefId, orgAcctSetId);
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

        
}
