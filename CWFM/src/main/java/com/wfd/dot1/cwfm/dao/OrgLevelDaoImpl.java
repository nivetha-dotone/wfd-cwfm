package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.pojo.OrgLevelMapping;

@Repository
public class OrgLevelDaoImpl implements OrgLevelDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveOrgLevel(OrgLevelDefDTO orgLevel) {
    	 System.out.println("Saving org level: " + orgLevel.getName() + ", " + orgLevel.getShortName() + ", " + orgLevel.getOrgHierarchyLevel());

        String sql = "INSERT INTO ORGLEVELDEF (NAME, SHORTNAME, ORGHIERARCHYLEVEL, MINIMUMLENGTH, MAXIMUMLENGTH,UPDATEDBYUSRACCTID) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel(),
                            orgLevel.getMinimumLength(), orgLevel.getMaximumLength(),orgLevel.getUpdatedByUsrAcctId());
    }
    public OrgLevel findByNameAndShortName(String name, String shortName) {
        try {
            String sql = "SELECT * FROM ORGLEVELDEF WHERE NAME = ? AND SHORTNAME = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name, shortName}, new BeanPropertyRowMapper<>(OrgLevel.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // No matching record found
        }
    }
    @Override
    public boolean existsByNameShortNameAndHierarchy(String name, String shortName, int hierarchyLevel) {
        // Trim inputs to avoid leading/trailing space issues
        name = name.trim();
        shortName = shortName.trim();

        // Log the values being checked
        System.out.println("Checking for existing record with Name: " + name + ", Short Name: " + shortName + ", Hierarchy Level: " + hierarchyLevel);

        // Perform case-insensitive check for duplicates based on Name, Short Name, and Hierarchy Level
        String sql = "SELECT COUNT(*) FROM ORGLEVELDEF WHERE LOWER(NAME) = LOWER(?) AND LOWER(SHORTNAME) = LOWER(?) AND ORGHIERARCHYLEVEL = ? and INACTIVE=1";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, name, shortName, hierarchyLevel);

        // Return true if any matching records are found (to indicate a duplicate), false otherwise
        return count > 0;  // If count > 0, that means there is a match, and the validation fails
    }

    public void saveOrUpdate(OrgLevel orgLevel) {
        String checkSql = "SELECT COUNT(*) FROM ORGLEVELDEF WHERE NAME = ? AND SHORTNAME = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, orgLevel.getName(), orgLevel.getShortName());

        if (count > 0) {
            // Update logic
            String updateSql = "UPDATE ORGLEVELDEF SET ORGHIERARCHYLEVEL = ?, MINIMUMLENGTH = ?, MAXIMUMLENGTH = ?, UPDATEDBYUSRACCTID = ? WHERE NAME = ? AND SHORTNAME = ?";
            jdbcTemplate.update(updateSql, orgLevel.getOrgHierarchyLevel(), orgLevel.getMinimumLength(),
                    orgLevel.getMaximumLength(), orgLevel.getUpdatedByUsrAcctId(),
                    orgLevel.getName(), orgLevel.getShortName());
        } else {
            // Insert logic
            String insertSql = "INSERT INTO ORGLEVELDEF (NAME, SHORTNAME, ORGHIERARCHYLEVEL, MINIMUMLENGTH, MAXIMUMLENGTH,  UPDATEDBYUSRACCTID) VALUES (?, ?, ?, ?, ?,  ?)";
            jdbcTemplate.update(insertSql, orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel(),
                    orgLevel.getMinimumLength(), orgLevel.getMaximumLength(),
                    orgLevel.getUpdatedByUsrAcctId());
        }
    }
    @Override
    public boolean isHierarchyLevelExists(int hierarchyLevel) {
        String sql = "SELECT COUNT(*) FROM ORGLEVELDEF WHERE ORGHIERARCHYLEVEL = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, hierarchyLevel) > 0;
    }

    @Override
    public boolean hasDependencies(int orgLevelDefId) {
        String sql = "SELECT COUNT(*) FROM ORGLEVELENTRY WHERE ORGLEVELDEFID = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, orgLevelDefId) > 0;
    }

    @Override
    public void deleteOrgLevel(int id) {
        String sql = "UPDATE ORGLEVELDEF SET INACTIVE=0 WHERE ORGLEVELDEFID = ?";

        try {
            int rowsAffected = jdbcTemplate.update(sql, id);
            if (rowsAffected > 0) {
                System.out.println("Org Level with ID " + id + " successfully marked as inactive.");
            } else {
                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
            }
        } catch (Exception e) {
            System.err.println("Error during delete operation: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public List<OrgLevel> fetchAllOrgLevels() {
        String query = "SELECT * FROM ORGLEVELDEF where INACTIVE=1";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            OrgLevel orgLevel = new OrgLevel();
            orgLevel.setOrgLevelDefId(rs.getInt("ORGLEVELDEFID"));
            orgLevel.setName(rs.getString("NAME"));
            orgLevel.setShortName(rs.getString("SHORTNAME"));
            orgLevel.setOrgHierarchyLevel(rs.getInt("ORGHIERARCHYLEVEL"));
            orgLevel.setUpdateDtm(rs.getTimestamp("UPDATE_DTM"));
            orgLevel.setUpdatedByUsrAcctId(rs.getInt("UPDATEDBYUSRACCTID"));
            orgLevel.setMinimumLength(rs.getInt("MINIMUMLENGTH"));
            orgLevel.setMaximumLength(rs.getInt("MAXIMUMLENGTH"));
            orgLevel.setInactive(rs.getInt("INACTIVE"));
            return orgLevel;
        });
    }
    @Override
    public void updateOrgLevel(int id, String newName, String newShortName, int newHierarchyLevel) {
        System.out.println("updateOrgLevel method called for ID: " + id); // Basic log to check if method is entered

        // SQL Update statement
        String sql = "UPDATE ORGLEVELDEF SET ORGLEVELNAME = ?, SHORTNAME = ?, ORGHIERARCHYLEVEL = ? WHERE ORGLEVELDEFID = ?";

        // Log the attempt to update
        System.out.println("Attempting to update Org Level with ID: " + id);
        System.out.println("Executing SQL: " + sql + " with ID: " + id + ", newName: " + newName + ", newShortName: " + newShortName + ", newHierarchyLevel: " + newHierarchyLevel);

        try {
            // Executing the update query
            int rowsAffected = jdbcTemplate.update(sql, newName, newShortName, newHierarchyLevel, id);

            // Log the number of rows affected
            System.out.println("Rows affected by the update: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Org Level with ID " + id + " successfully updated.");
            } else {
                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
            }
        } catch (Exception e) {
            // Log detailed error message for further debugging
            System.err.println("Error during update operation: " + e.getMessage());
            e.printStackTrace();
        }
    }
	@Override
	public void updateOrgLevel(int id) {
		  String sql = "UPDATE ORGLEVELDEF SET INACTIVE=0 WHERE ORGLEVELDEFID = ?";

	        try {
	            int rowsAffected = jdbcTemplate.update(sql, id);
	            if (rowsAffected > 0) {
	                System.out.println("Org Level with ID " + id + " successfully marked as inactive.");
	            } else {
	                System.out.println("No Org Level found with ID " + id + ". No rows were updated.");
	            }
	        } catch (Exception e) {
	            System.err.println("Error during update operation: " + e.getMessage());
	            e.printStackTrace();
	        }
		
	}
	public void updateOrgLevel(List<OrgLevelDefDTO> orgLevels) {
	    String sql = "UPDATE ORGLEVELDEF SET NAME = ?, SHORTNAME = ?, ORGHIERARCHYLEVEL = ?, MINIMUMLENGTH = ?, MAXIMUMLENGTH = ?, UPDATEDBYUSRACCTID = ?, UPDATE_DTM = CURRENT_TIMESTAMP WHERE ORGLEVELDEFID = ?";

	    // Use batchUpdate for better performance when updating multiple rows
	    List<Object[]> batchArgs = new ArrayList<>();

	    for (OrgLevelDefDTO orgLevel : orgLevels) {
	        batchArgs.add(new Object[]{
	            orgLevel.getName(),
	            orgLevel.getShortName(),
	            orgLevel.getOrgHierarchyLevel(),
	            orgLevel.getMinimumLength(),
	            orgLevel.getMaximumLength(),
	            orgLevel.getUpdatedByUsrAcctId(),
	            orgLevel.getOrgLevelDefId()
	        });
	    }

	    // Execute the batch update
	    jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public List<OrgLevelDefDTO> saveOrgLevels(List<OrgLevelDefDTO> orgLevels) {
	    List<OrgLevelDefDTO> savedEntries = new ArrayList<>();
	    
	    // Loop through each orgLevel and save it using saveOrgLevel method
	    for (OrgLevelDefDTO orgLevel : orgLevels) {
	        try {
	            System.out.println("Saving org level: " + orgLevel.getName() + ", " + orgLevel.getShortName() + ", " + orgLevel.getOrgHierarchyLevel());
	            saveOrgLevel(orgLevel);  // Save each orgLevel using the existing saveOrgLevel method
	            savedEntries.add(orgLevel);  // Track the successfully saved entries
	        } catch (Exception e) {
	            System.err.println("Error saving org level: " + orgLevel.getName());
	            e.printStackTrace();
	        }
	    }

	    // Return the list of successfully saved orgLevels
	    return savedEntries;
	}
	
	 public boolean existsById(Long id) {
	        String sql = "SELECT COUNT(*) FROM ORGLEVELDEF WHERE ORGLEVELDEFID = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
	        return count != null && count > 0;
	    }
	 
	 public List<OrgLevelDefDTO> getActiveOrgLevels() {
		    String sql = "SELECT ORGLEVELDEFID, NAME FROM ORGLEVELDEF WHERE INACTIVE = 1";
		    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrgLevelDefDTO.class));
		}

		public List<OrgLevelEntryDTO> getOrgLevelEntriesByDefId(int orgLevelDefId) {
		    String sql = "SELECT * FROM ORGLEVELENTRY WHERE ORGLEVELDEFID = ? AND INACTIVE = 1";
		    return jdbcTemplate.query(sql, new Object[]{orgLevelDefId}, new BeanPropertyRowMapper<>(OrgLevelEntryDTO.class));
		}

		public void saveOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
		    String sql = "INSERT INTO ORGLEVELENTRY (ORGLEVELDEFID, NAME, DESCRIPTION, INACTIVE, UPDATEDBYUSRACCTID) VALUES (?, ?, ?, ?, ?)";
		    jdbcTemplate.update(sql, orgLevelEntry.getOrgLevelDefId(), orgLevelEntry.getName(), orgLevelEntry.getDescription(), 1, orgLevelEntry.getUpdatedByUsrAcctId());
		}

		public void updateOrgLevelEntry(OrgLevelEntryDTO orgLevelEntry) {
		    String sql = "UPDATE ORGLEVELENTRY SET NAME = ?, DESCRIPTION = ?, UPDATEDBYUSRACCTID = ? WHERE ORGLEVELENTRYID = ?";
		    jdbcTemplate.update(sql, orgLevelEntry.getName(), orgLevelEntry.getDescription(), orgLevelEntry.getUpdatedByUsrAcctId(), orgLevelEntry.getOrgLevelEntryId());
		}

		public void deleteOrgLevelEntry(int orgLevelEntryId) {
		    String sql = "UPDATE ORGLEVELENTRY SET INACTIVE = 0 WHERE ORGLEVELENTRYID = ?";
		    jdbcTemplate.update(sql, orgLevelEntryId);
		}
		public OrgLevelEntryDTO getOrgLevelEntryById(int orgLevelEntryId) {
		    String sql = "SELECT * FROM ORGLEVELENTRY WHERE ORGLEVELENTRYID = ? AND INACTIVE = 1";
		    return jdbcTemplate.queryForObject(sql, new Object[]{orgLevelEntryId}, new BeanPropertyRowMapper<>(OrgLevelEntryDTO.class));
		}
		  @Override
		    public List<OrgLevelEntryDTO> getAvailableEntries(Long orgLevelDefId) {
		        String sql = "SELECT * FROM ORGLEVELENTRY WHERE ORGLEVELDEFID = ? AND INACTIVE = 1";
		        return jdbcTemplate.query(sql, new Object[]{orgLevelDefId}, (rs, rowNum) -> {
		            OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		            entry.setOrgLevelEntryId((int) rs.getLong("ORGLEVELENTRYID"));
		            entry.setName(rs.getString("NAME"));
		            entry.setDescription(rs.getString("DESCRIPTION"));
		            return entry;
		        });
		    }

		    @Override
		    public List<OrgLevelEntryDTO> getSelectedEntries(Long orgLevelDefId) {
		        String sql = "SELECT * FROM ORGLEVELENTRY WHERE ORGLEVELDEFID = ? AND INACTIVE = 0"; // Example condition
		        return jdbcTemplate.query(sql, new Object[]{orgLevelDefId}, (rs, rowNum) -> {
		            OrgLevelEntryDTO entry = new OrgLevelEntryDTO();
		            entry.setOrgLevelEntryId((int) rs.getLong("ORGLEVELENTRYID"));
		            entry.setName(rs.getString("NAME"));
		            entry.setDescription(rs.getString("DESCRIPTION"));
		            return entry;
		        });
		    }
		    public OrgLevelMapping findBasicInfo(Long id) {
		        String sql = "SELECT TOP 1 os.SHORTNM, os.LONGDSC " +
		                     "FROM OLACCTSETMM om " +
		                     "LEFT JOIN ORGACCTSET os ON om.ORGACCTSETID = os.ORGACCTSETID " +
		                     "WHERE om.ORGACCTSETID = ?";
		        
		        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrgLevelMapping.class));
		    }

}

