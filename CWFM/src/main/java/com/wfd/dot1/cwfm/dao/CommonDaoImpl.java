package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.PageDto;
import com.wfd.dot1.cwfm.dto.SectionDto;
//import com.wfd.dot1.cwfm.pojo.CMSContractor;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
//import com.wfd.dot1.cwfm.pojo.CMSPerson;
//import com.wfd.dot1.cwfm.pojo.CMSPersonCustomData;
//import com.wfd.dot1.cwfm.pojo.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;
//import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
//import com.wfd.dot1.cwfm.service.ContractorService;

@Repository
public class CommonDaoImpl implements CommonDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
	private static final String INSERT_GMTYPE_QUERY = "INSERT INTO CMSGMTYPE (GMTYPE) VALUES (?)";
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	 @Autowired
	    private CommonDao commonDAO;
	 
//	@Autowired
//	private DataSource dataSource;
//	@PersistenceContext
//    private EntityManager entityManager;
	@Autowired
	  private CommonService commonService;
//	@Autowired
//    private ContractorService contractorService;
//	@Autowired
//    private CMSPRINCIPALEMPLOYERService principalEmployerService;
//	@Autowired
//    private HibernateTemplate hibernateTemplate;
//	
//	 @Override
//	    public State getStateById(int stateId) {
//	        return entityManager.find(State.class, stateId);
//	    }
//
//	 @Transactional(readOnly = true)
//	 public List<State> getAllStates() {
//	     try {
//	         List<State> states = hibernateTemplate.loadAll(State.class);
//	         // Log the number of states retrieved
//	         LOGGER.info("Number of states retrieved: {}", states.size());
//	         // Log each state's details
//	         for (State state : states) {
//	             LOGGER.info("State ID: {}, State Name: {}", state.getStateId(), state.getStateName());
//	         }
//	         return states;
//	     } catch (Exception e) {
//	         // Log the exception
//	         LOGGER.error("Failed to retrieve all states", e);
//	         // Rethrow the exception
//	         throw new RuntimeException("Failed to retrieve all states", e);
//	     }
//	 }
	   public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long contractorId, Long unitId, String licenseType) {
	        List<CmsContractorWC> mappings = new ArrayList<>();
	        String sql = "SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE = ?";
	        System.out.println("Executing SQL query: " + sql + " with parameters: " + contractorId + ", " + unitId + ", " + licenseType);
	        
//	        try (Connection connection = dataSource.getConnection();
//	             PreparedStatement statement = connection.prepareStatement(sql)) {
//	            statement.setLong(1, contractorId);
//	            statement.setLong(2, unitId);
//	            statement.setString(3, licenseType);
//	            try (ResultSet resultSet = statement.executeQuery()) {
//	                while (resultSet.next()) {
//	                	  CmsContractorWC mapping = new CmsContractorWC();
//	                      // Set values for CmsContractorWC object
//	                	  mapping.setWcCode(resultSet.getString("WC_CODE"));
//	                      mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
//	                      mapping.setUnitId(resultSet.getLong("UNITID"));
//	                      mapping.setNatureOfId(resultSet.getInt("NATURE_OF_ID"));
//	                      mapping.setWcFromDtm(resultSet.getDate("WC_FROM_DTM"));
//	                      mapping.setWcToDtm(resultSet.getDate("WC_TO_DTM"));
//	                      mapping.setWcTotal(resultSet.getInt("WC_TOTAL"));
//	                      mapping.setDeleteSw(resultSet.getInt("DELETE_SW"));
//	                      mapping.setLicenceType(resultSet.getString("LICENCE_TYPE"));
//	                      mapping.setIsVerified(resultSet.getString("ISVERIFIED"));
//	                      mapping.setAttachmentNm(resultSet.getString("ATTACHMENTNM"));
//	                      mapping.setExtendToSubcontractor(resultSet.getInt("EXTENDTOSUBCONTRACTOR"));
//	                      
//	                      // Add logging here to print out values
//	                      System.out.println("Retrieved WC Code: " + mapping.getWcCode());
//	                      // Add the populated object to the list
//	                      mappings.add(mapping);
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
	        return mappings;
	    }
	    public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long contractorId, Long unitId, List<String> licenseTypes) {
	        List<CmsContractorWC> mappings = new ArrayList<>();
	        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE IN (");
	        for (int i = 0; i < licenseTypes.size(); i++) {
	            if (i != 0) {
	                sqlBuilder.append(", ");
	            }
	            sqlBuilder.append("?");
	        }
	        sqlBuilder.append(")");
	        
//	        try (Connection connection = dataSource.getConnection();
//	             PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
//	            statement.setLong(1, contractorId);
//	            statement.setLong(2, unitId);
//	            for (int i = 0; i < licenseTypes.size(); i++) {
//	                statement.setString(3 + i, licenseTypes.get(i));
//	            }
//
////	            try (ResultSet resultSet = statement.executeQuery()) {
////	                while (resultSet.next()) {
////	                    CmsContractorWC mapping = new CmsContractorWC();
////	                    mapping.setWcCode(resultSet.getString("WC_CODE"));
////	                    mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
////	                    mapping.setUnitId(resultSet.getLong("UNITID"));
////	                    mapping.setNatureOfId(resultSet.getInt("NATURE_OF_ID"));
////	                    mapping.setWcFromDtm(resultSet.getDate("WC_FROM_DTM"));
////	                    mapping.setWcToDtm(resultSet.getDate("WC_TO_DTM"));
////	                    mapping.setWcTotal(resultSet.getInt("WC_TOTAL"));
////	                    mapping.setDeleteSw(resultSet.getInt("DELETE_SW"));
////	                    mapping.setLicenceType(resultSet.getString("LICENCE_TYPE"));
////	                    mapping.setIsVerified(resultSet.getString("ISVERIFIED"));
////	                    mapping.setAttachmentNm(resultSet.getString("ATTACHMENTNM"));
////	                    mapping.setExtendToSubcontractor(resultSet.getInt("EXTENDTOSUBCONTRACTOR"));
////	                    mappings.add(mapping);
////	                }
////	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
	        return mappings;
	    }

	    public List<Workorder> getWorkOrdersByContractorIdAndUnitId(Long contractorId, Long unitId) {
	        List<Workorder> workOrders = new ArrayList<>();
	        String sql = "SELECT WORKORDERID, SAP_WORKORDER_NUM, CONTRACTORID, VALIDFROM, VALIDDT, UNITID FROM Workorder WHERE CONTRACTORID = ? AND UNITID = ?";
//	        try (Connection connection = dataSource.getConnection();
//	             PreparedStatement statement = connection.prepareStatement(sql)) {
//	            statement.setLong(1, contractorId);
//	            statement.setLong(2, unitId);
////	            try (ResultSet resultSet = statement.executeQuery()) {
////	                while (resultSet.next()) {
////	                    Workorder workOrder = new Workorder();
////	                    workOrder.setWorkOrderId(resultSet.getLong("WORKORDERID"));
////	                    workOrder.setSapWorkOrderNum(resultSet.getString("SAP_WORKORDER_NUM"));
////	                    workOrder.setContractorId(resultSet.getLong("CONTRACTORID"));
////	                    workOrder.setValidFrom(resultSet.getDate("VALIDFROM"));
////	                    workOrder.setValidDt(resultSet.getDate("VALIDDT"));
////	                    workOrder.setUnitId(resultSet.getLong("UNITID"));
////	                    workOrders.add(workOrder);
////	                }
////	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
	        return workOrders;
	    }
	    
	    @Override
	    public Workorder getWorkorderById(Long id) {
	        String sql = "SELECT * FROM workorder WHERE id = ?";
	        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Workorder.class), id);
	    }

	    @Override
	    public CMSGMType findById(Long gmTypeId) {
	        String sql = "SELECT * FROM cmsgmtype WHERE id = ?";
	        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CMSGMType.class), gmTypeId);
	    }
//	

	    @Override
	    public CmsGeneralMaster findByGMId(Integer bloodGroupId) {
	        String sql = "SELECT GMNAME, GMDESCRIPTION FROM CMSCmsGeneralMaster WHERE GMID = ?";
	        return jdbcTemplate.queryForObject(sql, new Object[]{bloodGroupId}, (rs, rowNum) -> {
	            CmsGeneralMaster cmsGeneralMaster = new CmsGeneralMaster();
	            cmsGeneralMaster.setGmName(rs.getString("GMNAME"));
	            cmsGeneralMaster.setGmdescription(rs.getString("GMDESCRIPTION"));
	            return cmsGeneralMaster;
	        });
	    }
	    @Override
	    public List<CmsGeneralMaster> getCmsGeneralMasterOptionsByName(String typeName) {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm " +
	                     "JOIN CMSGMType gmt ON gm.gm_type_id = gmt.id " +
	                     "WHERE gmt.gmType = ?";
	        return jdbcTemplate.query(sql, new Object[]{typeName}, new BeanPropertyRowMapper<>(CmsGeneralMaster.class));
	    }
	   
	    public CMSGMType findByGMTypeName(String gmTypeName) {
	        String sql = "SELECT * FROM CMSGMTYPE WHERE GMTYPE = ?";
	        try {
	            return jdbcTemplate.queryForObject(sql, new Object[]{gmTypeName}, new BeanPropertyRowMapper<>(CMSGMType.class));
	        } catch (EmptyResultDataAccessException e) {
	            return null;  // Return null or handle the absence of a result in your service layer
	        }
	    }
	    @Override
	    public CmsGeneralMaster findByMasterNameAndValue(String masterName, String masterValue) {
	        String sql = "SELECT * FROM CmsGeneralMaster WHERE gmname = ? AND gmdescription = ?";
	        return jdbcTemplate.queryForObject(sql, new Object[]{masterName, masterValue}, new BeanPropertyRowMapper<>(CmsGeneralMaster.class));
	    }


//	    public boolean isGMTypeNameDuplicate(String gmTypeName) {
//	        String sql = "SELECT COUNT(*) FROM CMSGMTYPE WHERE GMTYPE = ?";
//	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, gmTypeName);
//	        return count != null && count > 0;
//	    }
	    @Transactional
	    public boolean isGMTypeNameDuplicate(String gmTypeName) {
	        CMSGMType gmType = commonDAO.findByGMTypeName(gmTypeName);
	        return gmType != null;  // If gmType is found, it’s a duplicate; otherwise, it’s not
	    }

	    @Override
	    public boolean isCmsGeneralMasterDuplicate(String masterName, String masterValue) {
	        String sql = "SELECT COUNT(*) FROM CmsGeneralMaster WHERE gmname = ? AND gmdescription = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{masterName, masterValue}, Integer.class);
	        return count != null && count > 0;  // Returns true if duplicate exists
	    }

	    @Override
	    public List<CMSGMType> getAllGMTypes() {
	        String sql = "SELECT * FROM CMSGMType";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CMSGMType.class));
	    }

	    @Override
	    public String saveGMType(String gmType) {
	    	LOGGER.info("Entering into saveGMType method");

	        if (gmType != null && !gmType.isEmpty()) {
	        	LOGGER.info("Inserting GMType: " + gmType);

	            try {
	                // Execute the insert query
	                int result = jdbcTemplate.update(INSERT_GMTYPE_QUERY, gmType);

	                if (result > 0) {
	                	LOGGER.info("GMType saved successfully: " + gmType);
	                    return gmType; // Returning the gmType name as confirmation
	                } else {
	                	LOGGER.warn("Failed to save GMType: " + gmType);
	                    return null;
	                }
	            } catch (Exception e) {
	            	LOGGER.error("Error saving GMType: " + gmType, e);
	                return null;
	            }
	        } else {
	        	LOGGER.error("Invalid GMType: " + gmType);
	            return null;
	        }
	    }

//	    @Override
//	    public void deleteGMType(Long gmTypeId) {
//	        String sql = "DELETE FROM CMSGMType WHERE gmTypeId = ?";
//	        jdbcTemplate.update(sql, gmTypeId);
//	        LOGGER.info("GM Type deleted with gmTypeId: {}", gmTypeId);
//	    }
//
	    @Override
	    public void deleteGMType(List<Long> gmTypeIds) {
	        List<Long> undeletableIds = new ArrayList<>();

	        for (Long gmTypeId : gmTypeIds) {
	            // Check if there are dependencies for this GM Type
	            String dependencyCheckSql = "SELECT COUNT(*) FROM CmsGeneralMaster WHERE gmTypeId = ?";
	            Integer count = jdbcTemplate.queryForObject(dependencyCheckSql, Integer.class, gmTypeId);

	            if (count != null && count > 0) {
	                undeletableIds.add(gmTypeId); // Add to list of undeletable IDs if dependencies exist
	            } else {
	                // If no dependencies, delete the GM Type
	            	 String updateSql = "DELETE FROM CMSGMType WHERE gmTypeId = ?";
	                 jdbcTemplate.update(updateSql, gmTypeId);
	                LOGGER.info("GM Type deleted with gmTypeId: {}", gmTypeId);
	            }
	        }

	        // Log or handle undeletable IDs as needed
	        if (!undeletableIds.isEmpty()) {
	            throw new DataIntegrityViolationException("Cannot delete GM Types due to dependencies: " + undeletableIds);
	        }
	    }
	    @Override
	    public List<GeneralMasterDTO> getAllCmsGeneralMasters() {
	        String sql = "SELECT * FROM CmsGeneralMaster";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GeneralMasterDTO.class));
	    }

	    @Override
	    public void saveCmsGeneralMaster(CmsGeneralMaster gm) {
	        String sql = "INSERT INTO CmsGeneralMaster (gmname, gmdescription, gmTypeId) VALUES (?, ?, ?)";
	        jdbcTemplate.update(sql, gm.getGmName(), gm.getGmdescription(), gm.getGmType());
	    }

	    @Override
	    public void deleteCmsGeneralMaster(Long gmId) {
	        String sql = "DELETE FROM CmsGeneralMaster WHERE gmId = ?";
	        jdbcTemplate.update(sql, gmId);
	    }

	    @Override
	    public CmsGeneralMaster findCmsGeneralMasterById(String id) {
	        String sql = "SELECT * FROM CmsGeneralMaster WHERE gmId = ?";
	        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), id);
	    }
	    @Override
	    public List<CMSRoleRights> getAllRoleRights() {
	        String sql = "SELECT rr.ROLE_RIGHT_ID, rr.ROLE_ID, rr.PAGE_ID, rr.ADD_RIGHTS, rr.EDIT_RIGHTS, rr.DELETE_RIGHTS, rr.IMPORT_RIGHTS, rr.EXPORT_RIGHTS, rr.VIEW_RIGHTS, rr.LIST_RIGHTS, rr.ENABLED_FLAG, rr.DELETED_FLAG, rr.CREATED_BY, rr.CREATION_DATE, rr.LAST_UPDATED_BY, rr.LAST_UPDATED_DATE, r.GMTYPEID as roleTypeId, p.GMTYPEID as pageTypeId, r.GMNAME as roleName, p.GMNAME as pageName " +
	                     "FROM CMSRoleRights rr " +
	                     "JOIN CMSGeneralMaster r ON rr.ROLE_ID = r.GMID " +
	                     "JOIN CMSGeneralMaster p ON rr.PAGE_ID = p.GMID " +
	                     "JOIN CMSGMTYPE rType ON r.GMTYPEID = rType.GMTYPEID " +
	                     "JOIN CMSGMTYPE pType ON p.GMTYPEID = pType.GMTYPEID " +
	                     "WHERE rType.GMTYPE = 'ROLE' AND pType.GMTYPE = 'PAGE'";
	        return jdbcTemplate.query(sql, (rs, rowNum) -> {
	            CMSRoleRights rights = new CMSRoleRights();
	            rights.setRoleRightId(rs.getLong("ROLE_RIGHT_ID"));
	            rights.setRoleId(rs.getLong("ROLE_ID"));
	            rights.setPageId(rs.getLong("PAGE_ID"));

	            CmsGeneralMaster role = new CmsGeneralMaster();
	            role.setGmId(String.valueOf(rs.getLong("ROLE_ID")));
	            role.setGmName(rs.getString("roleName"));
	            rights.setRole(role);

	            CmsGeneralMaster page = new CmsGeneralMaster();
	            page.setGmId(String.valueOf(rs.getLong("PAGE_ID")));
	            page.setGmName(rs.getString("pageName"));
	            rights.setPage(page);

	            rights.setAddRights(rs.getInt("ADD_RIGHTS"));
	            rights.setEditRights(rs.getInt("EDIT_RIGHTS"));
	            rights.setDeleteRights(rs.getInt("DELETE_RIGHTS"));
	            rights.setImportRights(rs.getInt("IMPORT_RIGHTS"));
	            rights.setExportRights(rs.getInt("EXPORT_RIGHTS"));
	            rights.setViewRights(rs.getInt("VIEW_RIGHTS"));
	            rights.setListRights(rs.getInt("LIST_RIGHTS"));
	            rights.setEnabledFlag(rs.getInt("ENABLED_FLAG"));
	            rights.setDeletedFlag(rs.getInt("DELETED_FLAG"));

	            return rights;
	        });
	    }


	    @Override
	    public List<CMSRoleRights> getRoleRightsByRoleId(Long roleId) {
	        String sql = "SELECT * FROM CMSRoleRights WHERE roleId = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CMSRoleRights.class), roleId);
	    }

	    @Override
	    public CMSRoleRights saveRoleRights(CMSRoleRights roleRights) {
	        if (roleRights.getRoleRightId() == null) {
	            // Insert new entry
	            String insertSql = "INSERT INTO CMSROLERIGHTS (ROLE_ID, PAGE_ID, ADD_RIGHTS, EDIT_RIGHTS, DELETE_RIGHTS, IMPORT_RIGHTS, EXPORT_RIGHTS, VIEW_RIGHTS, LIST_RIGHTS, ENABLED_FLAG, DELETED_FLAG, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) " +
	                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            jdbcTemplate.update(insertSql,
	                    roleRights.getRoleId(),
	                    roleRights.getPageId(),
	                    roleRights.getAddRights(),
	                    roleRights.getEditRights(),
	                    roleRights.getDeleteRights(),
	                    roleRights.getImportRights(),
	                    roleRights.getExportRights(),
	                    roleRights.getViewRights(),
	                    roleRights.getListRights(),
	                    roleRights.getEnabledFlag(),
	                    roleRights.getDeletedFlag(),
	                    roleRights.getCreatedBy(),
	                    roleRights.getCreationDate(),
	                    roleRights.getLastUpdatedBy(),
	                    roleRights.getLastUpdatedDate()
	            );
	            Long generatedId = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Long.class);
	            roleRights.setRoleRightId(generatedId);
	        } else {
	            // Update existing entry
	            String updateSql = "UPDATE CMSROLERIGHTS SET ROLE_ID = ?, PAGE_ID = ?, ADD_RIGHTS = ?, EDIT_RIGHTS = ?, DELETE_RIGHTS = ?, IMPORT_RIGHTS = ?, EXPORT_RIGHTS = ?, VIEW_RIGHTS = ?, LIST_RIGHTS = ?, ENABLED_FLAG = ?, DELETED_FLAG = ?, CREATED_BY = ?, CREATION_DATE = ?, LAST_UPDATED_BY = ?, LAST_UPDATED_DATE = ? " +
	                               "WHERE ROLE_RIGHT_ID = ?";
	            jdbcTemplate.update(updateSql,
	                    roleRights.getRoleId(),
	                    roleRights.getPageId(),
	                    roleRights.getAddRights(),
	                    roleRights.getEditRights(),
	                    roleRights.getDeleteRights(),
	                    roleRights.getImportRights(),
	                    roleRights.getExportRights(),
	                    roleRights.getViewRights(),
	                    roleRights.getListRights(),
	                    roleRights.getEnabledFlag(),
	                    roleRights.getDeletedFlag(),
	                    roleRights.getCreatedBy(),
	                    roleRights.getCreationDate(),
	                    roleRights.getLastUpdatedBy(),
	                    roleRights.getLastUpdatedDate(),
	                    roleRights.getRoleRightId()
	            );
	        }
	        return roleRights;
	    }


		/*
		 * @Override public void save(User user) { if (user.getId() == 0) { // Insert
		 * new user String insertSql =
		 * "INSERT INTO User (username, email, password, ...) VALUES (?, ?, ?, ...)";
		 * jdbcTemplate.update(insertSql, user.getUsername(), user.getEmail(),
		 * user.getPassword(), ...); Long generatedId =
		 * jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		 * user.setId(generatedId); } else { // Update existing user String updateSql =
		 * "UPDATE User SET username = ?, email = ?, password = ? WHERE id = ?";
		 * jdbcTemplate.update(updateSql, user.getUsername(), user.getEmail(),
		 * user.getPassword(), user.getId()); } }
		 */


	    @Override
	    public List<CmsGeneralMaster> getGMByType(String type) {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId WHERE gmt.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), type);
	    }

	    @Override
	    public CmsGeneralMaster getGMById(Long id) {
	        String sql = "SELECT * FROM CmsGeneralMaster WHERE gmId = ?";
	        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), id);
	    }
	    public List<CmsGeneralMaster> getAllPages() {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId WHERE gmt.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "PAGE");
	    }
	    public List<CmsGeneralMaster> getAllSections() {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId WHERE gmt.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "SECTION");
	    }

	    public List<CmsGeneralMaster> getAllRoles() {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId WHERE gmt.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "ROLE");
	    }
	    public List<CmsGeneralMaster> getRoles() {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm WHERE gm.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "ROLE");
	    }

	    public List<CmsGeneralMaster> getPages() {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm WHERE gm.gmType = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), "PAGE");
	    }

	    public List<CmsGeneralMaster> getPagesByRole(Long roleId) {
	        String sql = "SELECT gm.* FROM CmsGeneralMaster gm WHERE gm.gmType = 'PAGE' AND gm.roleId = ?";
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CmsGeneralMaster.class), roleId);
	    }
		@Override
		public void save(User user) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public List<State> getAllStates() {
			 String sql = "SELECT * FROM CMSSTATE";
		        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(State.class));
		}
		
		@Override
		public List<GeneralMasterDTO> getGeneralMastersWithTypeName(Long gmTypeId) {
		    String sql = "SELECT g.gmId, g.gmName, g.GMDESCRIPTION AS gmDescription, t.GMTYPE AS gmTypeName FROM CMSGENERALMASTER g JOIN CMSGMTYPE t ON g.gmTypeId = t.gmTypeId WHERE t.gmTypeId = ? and g.ISACTIVE=1 ";
		    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GeneralMasterDTO.class), gmTypeId);
		}
		@Override
		public void deleteGmDataById(Long gmId) {
		    String sql = "UPDATE CMSGENERALMASTER SET ISACTIVE = 0,UPDATEDTM=GETDATE() WHERE gmId = ?";
		    jdbcTemplate.update(sql, gmId);
		}
		 @Override
		    public void saveGeneralMaster(GeneralMasterDTO generalMasterDTO) {
		        String sql = "INSERT INTO CMSGENERALMASTER (gmTypeId, gmName, GMDESCRIPTION,ISACTIVE,UPDATEDBY) VALUES (?, ?, ?,1,?)";
		        jdbcTemplate.update(sql, generalMasterDTO.getGmTypeId(), generalMasterDTO.getGmName(), generalMasterDTO.getGmDescription(),"Admin");
		    }
		 @Override
		    public boolean checkDuplicateRoleRight(Long roleId, Long pageId) {
		        String sql = "SELECT COUNT(*) FROM CMSRoleRights WHERE role_id = ? AND page_id = ? and DELETED_FLAG=0";
		        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { roleId, pageId }, Integer.class);
		        return count > 0;
		    }
		@Override
		public List<CmsGeneralMaster> getRolesByUserId(Integer userId) {
			 String sql = 
				        "SELECT gm.* " +
				        "FROM UserRoleMapping urm " +
				        "INNER JOIN CmsGeneralMaster gm ON urm.RoleId = gm.gmId " +
				        "WHERE urm.UserId = ?";
			 
		        return jdbcTemplate.query(
		            sql,
		            new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
		            userId
		        );
		}
			
		 public List<CmsGeneralMaster> getRolesByUserId(Long userId) {
			 String sql = 
				        "SELECT gm.* " +
				        "FROM UserRoleMapping urm " +
				        "INNER JOIN CmsGeneralMaster gm ON urm.RoleId = gm.gmId " +
				        "WHERE urm.UserId = ?";
			 
		        return jdbcTemplate.query(
		            sql,
		            new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
		            userId
		        );
		    }
		 public List<CmsGeneralMaster> getPagesByRoleId(Long roleId) {
			    String sql = 
			        "SELECT gm.* " +
			        "FROM CMSROLERIGHTS rr " +
			        "INNER JOIN CMSGENERALMASTER gm ON rr.PAGE_ID = gm.gmId " +
			        "WHERE rr.ROLE_ID = ? AND rr.ENABLED_FLAG = 1 AND rr.DELETED_FLAG = 0";
			    LOGGER.info("Executing query with ROLE_ID: {}", roleId);
			    return jdbcTemplate.query(
			        sql,
			        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
			        roleId
			    );
			}
		@Override
		public List<CmsGeneralMaster> getPagesByRoleId(String roleId) {
			 String sql = 
				        "SELECT gm.* " +
				        "FROM CMSROLERIGHTS rr " +
				        "INNER JOIN CMSGENERALMASTER gm ON rr.PAGE_ID = gm.gmId " +
				        "WHERE rr.ROLE_ID = ? AND rr.ENABLED_FLAG = 1 AND rr.DELETED_FLAG = 0";
			 System.out.println("Controller hit: saveUsers"+roleId);
				    return jdbcTemplate.query(
				        sql,
				        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
				        roleId
				    );
		}
		@Override
		public List<CmsGeneralMaster> getAvailablePagesForSection(Long sectionId) {
			 String sql = 
				        "SELECT gm.* " +
				        "FROM CmsGeneralMaster gm " +
				        "INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId " +
				        "WHERE gmt.gmType = 'PAGE' " +
				        "AND gm.gmId NOT IN (" +
				        "    SELECT sp.page_id " +
				        "    FROM CMSSectionPage sp " +
				        "    WHERE sp.section_id = ? AND sp.is_active = 0" +
				        ")";
				    return jdbcTemplate.query(
				        sql,
				        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
				         sectionId
				    );
		}
		@Override
		public List<CmsGeneralMaster> getSelectedPagesForSection(Long sectionId) {
			String sql = 
			        "SELECT gm.* " +
			        "FROM CmsGeneralMaster gm " +
			        "INNER JOIN CMSGMType gmt ON gm.gmTypeId = gmt.gmTypeId " +
			        "WHERE gmt.gmType = 'PAGE' " +
			        "AND gm.gmId IN (" +
			        "    SELECT sp.page_id " +
			        "    FROM CMSSectionPage sp " +
			        "    WHERE sp.section_id = ? AND sp.is_active = 0" +
			        ")";
			    return jdbcTemplate.query(
			        sql,
			        new BeanPropertyRowMapper<>(CmsGeneralMaster.class),
			         sectionId
			    );
		}
		
		/*
		 * public void saveSectionPage(Long sectionId, Long pageId, String createdBy) {
		 * String sql =
		 * "INSERT INTO CMSSectionPage (section_id, page_id, is_active, created_by, updated_by) "
		 * + "VALUES (?, ?, ?, ?, ?)";
		 * 
		 * jdbcTemplate.update(sql, sectionId, pageId, 0, createdBy, createdBy); }
		 */
		public void saveSectionPage(Long sectionId, List<Long> pageIds, String createdBy) {
		    String selectSql = "SELECT COUNT(*) FROM CMSSectionPage WHERE section_id = ? AND page_id = ?";
		    String updateSql = "UPDATE CMSSectionPage SET is_active = 0, updated_by = ? WHERE section_id = ? AND page_id = ?";
		    String insertSql = "INSERT INTO CMSSectionPage (section_id, page_id, is_active, created_by, updated_by) " +
	                 "VALUES (?, ?, ?, ?, ?)";
		 //   String deactivateSql = "UPDATE CMSSectionPage SET is_active = 1, updated_by = ? WHERE section_id = ? AND page_id NOT IN (?)";

		    try {
		        for (Long pageId : pageIds) {
		            // Check if the record already exists
		            int count = jdbcTemplate.queryForObject(selectSql, Integer.class, sectionId, pageId);
		            System.out.println("count---"+count);
		            if (count > 0) {
		                // Update the existing record to set is_active = 1
		                jdbcTemplate.update(updateSql, createdBy, sectionId, pageId);
		            } else {
		                // Insert a new record
		                jdbcTemplate.update(insertSql, sectionId, pageId, 0, createdBy, createdBy);
		            }
		        }

		        // Deactivate pages that are not selected
		        String deactivateQuery = String.format(
		        	    "UPDATE CMSSectionPage SET is_active = 1 WHERE section_id = ? AND page_id NOT IN (%s)",
		        	    String.join(",", pageIds.stream().map(String::valueOf).collect(Collectors.toList()))
		        	);
		        	jdbcTemplate.update(deactivateQuery, sectionId);
		       // jdbcTemplate.update(deactivateSql, createdBy, sectionId, String.join(",", pageIds.stream().map(String::valueOf).collect(Collectors.toList())));
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error saving section-page mapping");
		    }
		}
		@Override
		public List<SectionDto> getAllSectionsWithPages() {
		    String sql =
		        "SELECT sp.section_id AS sectionId, gm_section.gmName AS sectionName, " +
		        "       gm_page.gmId AS pageId, gm_page.gmName AS pageName ,gm_page.GMDESCRIPTION as pageUrl " +
		        "FROM CMSSectionPage sp " +
		        "LEFT JOIN CmsGeneralMaster gm_page ON sp.page_id = gm_page.gmId " +
		        "LEFT JOIN CmsGeneralMaster gm_section ON sp.section_id = gm_section.gmId " +
		        "LEFT JOIN CMSGMType gmt ON gm_page.gmTypeId = gmt.gmTypeId " +
		        "WHERE gmt.gmType = 'PAGE' AND sp.is_active = 0";

		    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		    if (rows.isEmpty()) {
		        return Collections.emptyList();
		    }

		    Map<Long, SectionDto> sectionMap = rows.stream()
		        .collect(Collectors.groupingBy(
		            row -> ((Number) row.get("sectionId")).longValue(),
		            Collectors.collectingAndThen(Collectors.toList(), sectionRows -> {
		                SectionDto sectionDto = new SectionDto();
		                sectionDto.setSectionId(((Number) sectionRows.get(0).get("sectionId")).longValue());
		                sectionDto.setSectionName((String) sectionRows.get(0).get("sectionName"));

		                List<PageDto> pages = sectionRows.stream()
		                    .filter(row -> row.get("pageId") != null)
		                    .map(row -> {
		                        PageDto pageDto = new PageDto();
		                        pageDto.setPageId(((Number) row.get("pageId")).longValue());
		                        pageDto.setPageName((String) row.get("pageName"));
		                        pageDto.setPageUrl((String) row.get("pageUrl"));
		                        return pageDto;
		                    }).collect(Collectors.toList());

		                sectionDto.setPages(pages);
		                return sectionDto;
		            })
		        ));

		    return new ArrayList<>(sectionMap.values());
		}
		@Override
		public List<SectionDto> getSectionsByRoleId(String roleId) {
			  String sql =
				        "SELECT sp.section_id AS sectionId, " +
				        "       gm_section.gmName AS sectionName, " +
				        "       gm_page.gmId AS pageId, " +
				        "       gm_page.gmName AS pageName, " +
				        "       gm_page.GMDESCRIPTION AS pageUrl " +
				        "FROM CMSSectionPage sp " +
				        "LEFT JOIN CmsGeneralMaster gm_page ON sp.page_id = gm_page.gmId " +
				        "LEFT JOIN CmsGeneralMaster gm_section ON sp.section_id = gm_section.gmId " +
				        "LEFT JOIN CMSGMType gmt ON gm_page.gmTypeId = gmt.gmTypeId " +
				        "LEFT JOIN CMSROLERIGHTS rpm ON sp.page_id = rpm.page_id " +
				        "WHERE gmt.gmType = 'PAGE' AND sp.is_active = 0 AND rpm.role_id = ?";

				    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, roleId);

				    if (rows.isEmpty()) {
				        return Collections.emptyList();
				    }

				    // Group rows by sectionId and construct SectionDto objects
				    Map<Long, SectionDto> sectionMap = rows.stream()
				        .collect(Collectors.groupingBy(
				            row -> ((Number) row.get("sectionId")).longValue(),
				            Collectors.collectingAndThen(Collectors.toList(), sectionRows -> {
				                SectionDto sectionDto = new SectionDto();
				                sectionDto.setSectionId(((Number) sectionRows.get(0).get("sectionId")).longValue());
				                sectionDto.setSectionName((String) sectionRows.get(0).get("sectionName"));

				                // Collect pages for this section
				                List<PageDto> pages = sectionRows.stream()
				                    .filter(row -> row.get("pageId") != null)
				                    .map(row -> {
				                        PageDto pageDto = new PageDto();
				                        pageDto.setPageId(((Number) row.get("pageId")).longValue());
				                        pageDto.setPageName((String) row.get("pageName"));
				                        pageDto.setPageUrl((String) row.get("pageUrl"));
				                        return pageDto;
				                    }).collect(Collectors.toList());

				                sectionDto.setPages(pages);
				                return sectionDto;
				            })
				        ));

				    return new ArrayList<>(sectionMap.values());
		}

		@Override
		public List<Long> getPageIdsByRoleId(String roleId) {
		    String sql = 
		        "SELECT gm.gmId " + 
		        "FROM CMSROLERIGHTS rr " + 
		        "INNER JOIN CMSGENERALMASTER gm ON rr.PAGE_ID = gm.gmId " + 
		        "WHERE rr.ROLE_ID = ? AND rr.ENABLED_FLAG = 1 AND rr.DELETED_FLAG = 0";

		    System.out.println("Fetching page IDs for roleId: " + roleId);

		    // Use queryForList to fetch a list of Long values
		    return jdbcTemplate.queryForList(sql, Long.class, roleId);
		}
		
		public boolean hasPageAccessForRole(String roleId, Long pageId) {
			 System.out.println("common roleId: " + roleId);
			 System.out.println("common for pageId: " + pageId);
		    // Query your database or service to check if the role has access to this page
		    String sql = "SELECT COUNT(*) FROM CMSROLERIGHTS WHERE ROLE_ID = ? AND PAGE_ID = ? AND ENABLED_FLAG = 1 AND DELETED_FLAG = 0";
		    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roleId, pageId);
		    System.out.println("common for count: " + count);
		    return count > 0;
		}
}
