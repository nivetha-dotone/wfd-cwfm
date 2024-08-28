package com.wfd.dot1.cwfm.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
import com.wfd.dot1.cwfm.entity.CMSGMType;
import com.wfd.dot1.cwfm.entity.CMSPerson;
import com.wfd.dot1.cwfm.entity.CMSPersonCustomData;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;
import com.wfd.dot1.cwfm.entity.CMSWorkOrder;
import com.wfd.dot1.cwfm.entity.GeneralMaster;
import com.wfd.dot1.cwfm.entity.State;
import com.wfd.dot1.cwfm.service.CMSPRINCIPALEMPLOYERService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;

@Repository
public class CommonDaoImpl implements CommonDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
	@Autowired
	private DataSource dataSource;
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	  private CommonService commonService;
	@Autowired
    private ContractorService contractorService;
	@Autowired
    private CMSPRINCIPALEMPLOYERService principalEmployerService;
	@Autowired
    private HibernateTemplate hibernateTemplate;
	
	 @Override
	    public State getStateById(int stateId) {
	        return entityManager.find(State.class, stateId);
	    }

	 @Transactional(readOnly = true)
	 public List<State> getAllStates() {
	     try {
	         List<State> states = hibernateTemplate.loadAll(State.class);
	         // Log the number of states retrieved
	         LOGGER.info("Number of states retrieved: {}", states.size());
	         // Log each state's details
	         for (State state : states) {
	             LOGGER.info("State ID: {}, State Name: {}", state.getStateId(), state.getStateName());
	         }
	         return states;
	     } catch (Exception e) {
	         // Log the exception
	         LOGGER.error("Failed to retrieve all states", e);
	         // Rethrow the exception
	         throw new RuntimeException("Failed to retrieve all states", e);
	     }
	 }
	   public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseType(Long contractorId, Long unitId, String licenseType) {
	        List<CMSContractorWC> mappings = new ArrayList<>();
	        String sql = "SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE = ?";
	        System.out.println("Executing SQL query: " + sql + " with parameters: " + contractorId + ", " + unitId + ", " + licenseType);
	        
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setLong(1, contractorId);
	            statement.setLong(2, unitId);
	            statement.setString(3, licenseType);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                	  CMSContractorWC mapping = new CMSContractorWC();
	                      // Set values for CMSContractorWC object
	                	  mapping.setWcCode(resultSet.getString("WC_CODE"));
	                      mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
	                      mapping.setUnitId(resultSet.getLong("UNITID"));
	                      mapping.setNatureOfId(resultSet.getInt("NATURE_OF_ID"));
	                      mapping.setWcFromDtm(resultSet.getDate("WC_FROM_DTM"));
	                      mapping.setWcToDtm(resultSet.getDate("WC_TO_DTM"));
	                      mapping.setWcTotal(resultSet.getInt("WC_TOTAL"));
	                      mapping.setDeleteSw(resultSet.getInt("DELETE_SW"));
	                      mapping.setLicenceType(resultSet.getString("LICENCE_TYPE"));
	                      mapping.setIsVerified(resultSet.getString("ISVERIFIED"));
	                      mapping.setAttachmentNm(resultSet.getString("ATTACHMENTNM"));
	                      mapping.setExtendToSubcontractor(resultSet.getInt("EXTENDTOSUBCONTRACTOR"));
	                      
	                      // Add logging here to print out values
	                      System.out.println("Retrieved WC Code: " + mapping.getWcCode());
	                      // Add the populated object to the list
	                      mappings.add(mapping);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return mappings;
	    }
	    public List<CMSContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(Long contractorId, Long unitId, List<String> licenseTypes) {
	        List<CMSContractorWC> mappings = new ArrayList<>();
	        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE IN (");
	        for (int i = 0; i < licenseTypes.size(); i++) {
	            if (i != 0) {
	                sqlBuilder.append(", ");
	            }
	            sqlBuilder.append("?");
	        }
	        sqlBuilder.append(")");
	        
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
	            statement.setLong(1, contractorId);
	            statement.setLong(2, unitId);
	            for (int i = 0; i < licenseTypes.size(); i++) {
	                statement.setString(3 + i, licenseTypes.get(i));
	            }

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    CMSContractorWC mapping = new CMSContractorWC();
	                    mapping.setWcCode(resultSet.getString("WC_CODE"));
	                    mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
	                    mapping.setUnitId(resultSet.getLong("UNITID"));
	                    mapping.setNatureOfId(resultSet.getInt("NATURE_OF_ID"));
	                    mapping.setWcFromDtm(resultSet.getDate("WC_FROM_DTM"));
	                    mapping.setWcToDtm(resultSet.getDate("WC_TO_DTM"));
	                    mapping.setWcTotal(resultSet.getInt("WC_TOTAL"));
	                    mapping.setDeleteSw(resultSet.getInt("DELETE_SW"));
	                    mapping.setLicenceType(resultSet.getString("LICENCE_TYPE"));
	                    mapping.setIsVerified(resultSet.getString("ISVERIFIED"));
	                    mapping.setAttachmentNm(resultSet.getString("ATTACHMENTNM"));
	                    mapping.setExtendToSubcontractor(resultSet.getInt("EXTENDTOSUBCONTRACTOR"));
	                    mappings.add(mapping);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return mappings;
	    }

	    public List<CMSWorkOrder> getWorkOrdersByContractorIdAndUnitId(Long contractorId, Long unitId) {
	        List<CMSWorkOrder> workOrders = new ArrayList<>();
	        String sql = "SELECT WORKORDERID, SAP_WORKORDER_NUM, CONTRACTORID, VALIDFROM, VALIDDT, UNITID FROM CMSWORKORDER WHERE CONTRACTORID = ? AND UNITID = ?";
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setLong(1, contractorId);
	            statement.setLong(2, unitId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    CMSWorkOrder workOrder = new CMSWorkOrder();
	                    workOrder.setWorkOrderId(resultSet.getLong("WORKORDERID"));
	                    workOrder.setSapWorkOrderNum(resultSet.getString("SAP_WORKORDER_NUM"));
	                    workOrder.setContractorId(resultSet.getLong("CONTRACTORID"));
	                    workOrder.setValidFrom(resultSet.getDate("VALIDFROM"));
	                    workOrder.setValidDt(resultSet.getDate("VALIDDT"));
	                    workOrder.setUnitId(resultSet.getLong("UNITID"));
	                    workOrders.add(workOrder);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return workOrders;
	    }
	    
	    @Override
	    public CMSWorkOrder getWorkorderById(Long id) {
	        return entityManager.find(CMSWorkOrder.class, id);
	    }

		@Override
		public CMSGMType findById(Long gmTypeId) {
			 return entityManager.find(CMSGMType.class, gmTypeId);
		}
		@Transactional
		@Override
		public List<CMSPerson> getAllPersons() {
		    Session session = entityManager.unwrap(Session.class);
		    String sqlQuery = "SELECT CAST(p.employeeId AS INTEGER) AS employeeId, " +
		            "p.employeeCode, " +
		            "p.firstName, " +
		            "p.relationName, " +
		            "p.lastName, " +
		            "p.dateOfBirth, " +
		            "p.dateOfJoining, " +
		            "p.dateOfTermination, " +
		            "p.bloodGroup, " +
		            "p.hazardousArea, " +
		            "p.gender, " +
		            "p.academics, " +
					 "p.ACCOMODATION, " + 
		            "p.bankBranch, " +
		            "p.accountNo, " +
		            "p.emergencyName, " +
		            "p.emergencyNumber, " +
		            "p.mobileNumber, " +
		            "p.accessLevel, " +
		            "p.esicNumber, " +
		            "p.uanNumber, " +
		            "p.ISPFELIGIBLE, " +
		            "p.idMark, " +
		            "p.PANNO, " +
//		            "p.CREATEDTM, " +
//		            "p.UPDATEDTM, " +
//		            "p.UPDATEDBY, " +
		            "cdUnit.CUSTOMDATATEXT AS unitId, " +
		            "cdDepartment.CUSTOMDATATEXT AS departmentId, " +
		            "cdContractor.CUSTOMDATATEXT AS contractorId, " +
		            "cdSkill.CUSTOMDATATEXT AS skillId, " +
		            "cdTrade.CUSTOMDATATEXT AS tradeId, " +
		            "p.AADHARNUMBER " +
		            "FROM CMSPERSON p " +
		            "JOIN CMSPERSONCUSTOMDATA cdUnit ON p.EMPLOYEEID = cdUnit.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddUnit ON cdUnit.CSTMDEFID = cddUnit.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdDepartment ON p.EMPLOYEEID = cdDepartment.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddDepartment ON cdDepartment.CSTMDEFID = cddDepartment.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdContractor ON p.EMPLOYEEID = cdContractor.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddContractor ON cdContractor.CSTMDEFID = cddContractor.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdSkill ON p.EMPLOYEEID = cdSkill.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddSkill ON cdSkill.CSTMDEFID = cddSkill.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdTrade ON p.EMPLOYEEID = cdTrade.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddTrade ON cdTrade.CSTMDEFID = cddTrade.CSTMDEFID " +
		            "WHERE cddUnit.CSTMDEFNAME = 'UnitId' " +
		            "AND cddDepartment.CSTMDEFNAME = 'DepartmentId' " +
		            "AND cddContractor.CSTMDEFNAME = 'ContractorId' " +
		            "AND cddSkill.CSTMDEFNAME = 'SkillId' " +
		            "AND cddTrade.CSTMDEFNAME = 'TradeId'";

		    Query query = session.createNativeQuery(sqlQuery)
		            .addScalar("employeeId")
		            .addScalar("employeeCode")
		            .addScalar("firstName")
		            .addScalar("relationName")
		            .addScalar("lastName")
		            .addScalar("dateOfBirth")
		            .addScalar("dateOfJoining")
		            .addScalar("dateOfTermination")
		            .addScalar("bloodGroup")
		            .addScalar("hazardousArea")
		            .addScalar("gender")
		            .addScalar("academics")
					.addScalar("accomodation") 
		            .addScalar("bankBranch")
		            .addScalar("accountNo")
		            .addScalar("emergencyName")
		            .addScalar("emergencyNumber")
		            .addScalar("mobileNumber")
		            .addScalar("accessLevel")
		            .addScalar("esicNumber")
		            .addScalar("uanNumber")
		            .addScalar("ispfEligible")
		            .addScalar("idMark")
		            .addScalar("panno")
//		            .addScalar("createdDateTime")
//		            .addScalar("updatedDateTime")
//		            .addScalar("updatedBy")
		            .addScalar("unitId")
		            .addScalar("departmentId")
		            .addScalar("contractorId")
		            .addScalar("skillId")
		            .addScalar("tradeId")
		            .addScalar("aadharNumber");
		            //.setResultTransformer(Transformers.aliasToBean(CMSPerson.class));

		    List<Object[]> resultList = query.getResultList();

		    // Iterate over the result list and map each row to a CMSPerson object
		    List<CMSPerson> cmsPersons = new ArrayList<>();
		    for (Object[] row : resultList) {
		        // Code to map each row to a CMSPerson object
		        CMSPerson cmsPerson = new CMSPerson();
		        // Set properties of CMSPerson
		        cmsPerson.setEmployeeId((Integer) row[0]); // Assuming employeeId is at index 0
		        cmsPerson.setEmployeeCode((String) row[1]); // Assuming employeeCode is at index 1
		        cmsPerson.setFirstName((String) row[2]); // Assuming firstName is at index 2
		        cmsPerson.setRelationName((String) row[3]); // Assuming relationName is at index 3
		        cmsPerson.setLastName((String) row[4]); // Assuming lastName is at index 4
		        cmsPerson.setDateOfBirth((Date) row[5]); // Assuming dateOfBirth is at index 5
		        cmsPerson.setDateOfJoining((Date) row[6]); // Assuming dateOfJoining is at index 6
		        cmsPerson.setDateOfTermination((Date) row[7]); // Assuming dateOfTermination is at index 7
		        BigInteger bloodGroupIdBigInteger = (BigInteger) row[8];
		        Integer bloodGroupId = bloodGroupIdBigInteger.intValue();
		        GeneralMaster bloodGroup = this.findByGMId(bloodGroupId);
		        cmsPerson.setBloodGroup(bloodGroup);
		        cmsPerson.setHazardousArea((String) row[9]); // Assuming hazardousArea is at index 9
		        BigInteger genderIdBigInteger = (BigInteger) row[10];
		        Integer genderId = genderIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the genderId
		        GeneralMaster gender = this.findByGMId(genderId);
		        cmsPerson.setGender(gender); // Assuming gender is at index 10
		        
		        BigInteger academicIdBigInteger = (BigInteger) row[11];
		        Integer academicId = academicIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the academicId
		        GeneralMaster academic = this.findByGMId(academicId);
		        cmsPerson.setAcademics(academic);  // Assuming academics is at index 11
		        BigInteger accommodationIdBigInteger = (BigInteger) row[12];
		        Integer accommodationId = accommodationIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the accommodationId
		        GeneralMaster accommodation = this.findByGMId(accommodationId);
		        cmsPerson.setAccomodation(accommodation);  // Assuming accomodation is at index 12
		        cmsPerson.setBankBranch((String) row[13]); // Assuming bankBranch is at index 13
		        cmsPerson.setAccountNo((String) row[14]); // Assuming accountNo is at index 14
		        cmsPerson.setEmergencyName((String) row[15]); // Assuming emergencyName is at index 15
		        cmsPerson.setEmergencyNumber((String) row[16]); // Assuming emergencyNumber is at index 16
		        cmsPerson.setMobileNumber((String) row[17]); // Assuming mobileNumber is at index 17
//		        String accessLevelString = (String) row[18]; 
//		        Byte accessLevelByte = Byte.valueOf(accessLevelString); // Convert String to Byte
//		        cmsPerson.setAccessLevel(accessLevelByte);
		        Byte accessLevelByte = (Byte) row[18]; // Assuming accessLevel is at index 18
		        cmsPerson.setAccessLevel(accessLevelByte);
		     //   cmsPerson.setAccessLevel((String) row[18]); // Assuming accessLevel is at index 18
		        cmsPerson.setEsicNumber((String) row[19]); // Assuming esicNumber is at index 19
		        cmsPerson.setUanNumber((String) row[20]); // Assuming uanNumber is at index 20
//		        String ispfEligibleString = (String) row[21]; 
//		        Byte ispfEligibleByte = Byte.valueOf(ispfEligibleString); // Convert String to Byte
//		        cmsPerson.setIspfEligible(ispfEligibleByte);
		        Byte ispfEligibleByte = (Byte) row[21]; // Assuming ispfEligible is at index 19
		        cmsPerson.setIspfEligible(ispfEligibleByte);
		       // cmsPerson.setIspfEligible((String) row[21]); // Assuming ispfEligible is at index 21
		        cmsPerson.setIdMark((String) row[22]); // Assuming idMark is at index 22
		        cmsPerson.setPanNo((String) row[23]); // Assuming PANNO is at index 23
		        cmsPerson.setUnitId((String) row[24]); // Assuming unitId is at index 24
		        String unitId = (String) row[24]; // Assuming unitId is at index 24
		        Long employerId = Long.valueOf(unitId);
		        CMSPrincipalEmployer unit = principalEmployerService.getCMSPRINCIPALEMPLOYERById(employerId); // Assuming findById() method fetches Unit entity by ID
		        cmsPerson.setUnit(unit);
		        cmsPerson.setDepartmentId((String) row[25]); // Assuming departmentId is at index 25
		        String contId = (String) row[26]; // Assuming unitId is at index 24
		        Long contrcatorId = Long.valueOf(contId);
		        CMSContractor cont = contractorService.getContractorById(contrcatorId);
		        cmsPerson.setContractor(cont); // Assuming contractorId is at index 26
		        cmsPerson.setSkillId((String) row[27]); // Assuming skillId is at index 27
		        cmsPerson.setTradeId((String) row[28]); // Assuming tradeId is at index 28
		        cmsPerson.setAadharNumber((String) row[29]);
		        cmsPersons.add(cmsPerson);
		    }

		    return cmsPersons;
		}
		
		@Transactional
		@Override
		public List<CMSPerson>  getAllPersonsByPrincipalEmployerAndContractor(Long uId, Long cId) {
		    Session session = entityManager.unwrap(Session.class);
		    String sqlQuery = "SELECT CAST(p.employeeId AS INTEGER) AS employeeId, " +
		            "p.employeeCode, " +
		            "p.firstName, " +
		            "p.relationName, " +
		            "p.lastName, " +
		            "p.dateOfBirth, " +
		            "p.dateOfJoining, " +
		            "p.dateOfTermination, " +
		            "p.bloodGroup, " +
		            "p.hazardousArea, " +
		            "p.gender, " +
		            "p.academics, " +
					 "p.ACCOMODATION, " + 
		            "p.bankBranch, " +
		            "p.accountNo, " +
		            "p.emergencyName, " +
		            "p.emergencyNumber, " +
		            "p.mobileNumber, " +
		            "p.accessLevel, " +
		            "p.esicNumber, " +
		            "p.uanNumber, " +
		            "p.ISPFELIGIBLE, " +
		            "p.idMark, " +
		            "p.PANNO, " +
//		            "p.CREATEDTM, " +
//		            "p.UPDATEDTM, " +
//		            "p.UPDATEDBY, " +
		            "cdUnit.CUSTOMDATATEXT AS unitId, " +
		            "cdDepartment.CUSTOMDATATEXT AS departmentId, " +
		            "cdContractor.CUSTOMDATATEXT AS contractorId, " +
		            "cdSkill.CUSTOMDATATEXT AS skillId, " +
		            "cdTrade.CUSTOMDATATEXT AS tradeId, " +
		            "p.AADHARNUMBER " +
		            "FROM CMSPERSON p " +
		            "JOIN CMSPERSONCUSTOMDATA cdUnit ON p.EMPLOYEEID = cdUnit.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddUnit ON cdUnit.CSTMDEFID = cddUnit.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdDepartment ON p.EMPLOYEEID = cdDepartment.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddDepartment ON cdDepartment.CSTMDEFID = cddDepartment.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdContractor ON p.EMPLOYEEID = cdContractor.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddContractor ON cdContractor.CSTMDEFID = cddContractor.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdSkill ON p.EMPLOYEEID = cdSkill.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddSkill ON cdSkill.CSTMDEFID = cddSkill.CSTMDEFID " +
		            "JOIN CMSPERSONCUSTOMDATA cdTrade ON p.EMPLOYEEID = cdTrade.EMPLOYEEID " +
		            "JOIN CMSPERSONCUSTOMDATADEFINITION cddTrade ON cdTrade.CSTMDEFID = cddTrade.CSTMDEFID " +
		            "WHERE cddUnit.CSTMDEFNAME = 'UnitId' " +
		            "AND cddDepartment.CSTMDEFNAME = 'DepartmentId' " +
		            "AND cddContractor.CSTMDEFNAME = 'ContractorId' " +
		            "AND cddSkill.CSTMDEFNAME = 'SkillId' " +
		            "AND cddTrade.CSTMDEFNAME = 'TradeId' AND cdUnit.CUSTOMDATATEXT = :uId AND cdContractor.CUSTOMDATATEXT = :cId";

		    Query query = session.createNativeQuery(sqlQuery)
		            .addScalar("employeeId")
		            .addScalar("employeeCode")
		            .addScalar("firstName")
		            .addScalar("relationName")
		            .addScalar("lastName")
		            .addScalar("dateOfBirth")
		            .addScalar("dateOfJoining")
		            .addScalar("dateOfTermination")
		            .addScalar("bloodGroup")
		            .addScalar("hazardousArea")
		            .addScalar("gender")
		            .addScalar("academics")
					.addScalar("accomodation") 
		            .addScalar("bankBranch")
		            .addScalar("accountNo")
		            .addScalar("emergencyName")
		            .addScalar("emergencyNumber")
		            .addScalar("mobileNumber")
		            .addScalar("accessLevel")
		            .addScalar("esicNumber")
		            .addScalar("uanNumber")
		            .addScalar("ispfEligible")
		            .addScalar("idMark")
		            .addScalar("panno")
//		            .addScalar("createdDateTime")
//		            .addScalar("updatedDateTime")
//		            .addScalar("updatedBy")
		            .addScalar("unitId")
		            .addScalar("departmentId")
		            .addScalar("contractorId")
		            .addScalar("skillId")
		            .addScalar("tradeId")
		            .addScalar("aadharNumber")
		    .setParameter("uId", uId.toString()) // Set value for :uId parameter
		    .setParameter("cId", cId.toString());
		            //.setResultTransformer(Transformers.aliasToBean(CMSPerson.class));

		    List<Object[]> resultList = query.getResultList();

		    // Iterate over the result list and map each row to a CMSPerson object
		    List<CMSPerson> cmsPersons = new ArrayList<>();
		    for (Object[] row : resultList) {
		        // Code to map each row to a CMSPerson object
		        CMSPerson cmsPerson = new CMSPerson();
		        // Set properties of CMSPerson
		        cmsPerson.setEmployeeId((Integer) row[0]); // Assuming employeeId is at index 0
		        cmsPerson.setEmployeeCode((String) row[1]); // Assuming employeeCode is at index 1
		        cmsPerson.setFirstName((String) row[2]); // Assuming firstName is at index 2
		        cmsPerson.setRelationName((String) row[3]); // Assuming relationName is at index 3
		        cmsPerson.setLastName((String) row[4]); // Assuming lastName is at index 4
		        cmsPerson.setDateOfBirth((Date) row[5]); // Assuming dateOfBirth is at index 5
		        cmsPerson.setDateOfJoining((Date) row[6]); // Assuming dateOfJoining is at index 6
		        cmsPerson.setDateOfTermination((Date) row[7]); // Assuming dateOfTermination is at index 7
		        BigInteger bloodGroupIdBigInteger = (BigInteger) row[8];
		        Integer bloodGroupId = bloodGroupIdBigInteger.intValue();
		        GeneralMaster bloodGroup = this.findByGMId(bloodGroupId);
		        cmsPerson.setBloodGroup(bloodGroup);
		        cmsPerson.setHazardousArea((String) row[9]); // Assuming hazardousArea is at index 9
		        BigInteger genderIdBigInteger = (BigInteger) row[10];
		        Integer genderId = genderIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the genderId
		        GeneralMaster gender = this.findByGMId(genderId);
		        cmsPerson.setGender(gender); // Assuming gender is at index 10
		        
		        BigInteger academicIdBigInteger = (BigInteger) row[11];
		        Integer academicId = academicIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the academicId
		        GeneralMaster academic = this.findByGMId(academicId);
		        cmsPerson.setAcademics(academic);  // Assuming academics is at index 11
		        BigInteger accommodationIdBigInteger = (BigInteger) row[12];
		        Integer accommodationId = accommodationIdBigInteger.intValue(); 
		        // Fetch the corresponding GeneralMaster entity using the accommodationId
		        GeneralMaster accommodation = this.findByGMId(accommodationId);
		        cmsPerson.setAccomodation(accommodation);  // Assuming accomodation is at index 12
		        cmsPerson.setBankBranch((String) row[13]); // Assuming bankBranch is at index 13
		        cmsPerson.setAccountNo((String) row[14]); // Assuming accountNo is at index 14
		        cmsPerson.setEmergencyName((String) row[15]); // Assuming emergencyName is at index 15
		        cmsPerson.setEmergencyNumber((String) row[16]); // Assuming emergencyNumber is at index 16
		        cmsPerson.setMobileNumber((String) row[17]); // Assuming mobileNumber is at index 17
//		        String accessLevelString = (String) row[18]; 
//		        Byte accessLevelByte = Byte.valueOf(accessLevelString); // Convert String to Byte
//		        cmsPerson.setAccessLevel(accessLevelByte);
		        Byte accessLevelByte = (Byte) row[18]; // Assuming accessLevel is at index 18
		        cmsPerson.setAccessLevel(accessLevelByte);
		     //   cmsPerson.setAccessLevel((String) row[18]); // Assuming accessLevel is at index 18
		        cmsPerson.setEsicNumber((String) row[19]); // Assuming esicNumber is at index 19
		        cmsPerson.setUanNumber((String) row[20]); // Assuming uanNumber is at index 20
//		        String ispfEligibleString = (String) row[21]; 
//		        Byte ispfEligibleByte = Byte.valueOf(ispfEligibleString); // Convert String to Byte
//		        cmsPerson.setIspfEligible(ispfEligibleByte);
		        Byte ispfEligibleByte = (Byte) row[21]; // Assuming ispfEligible is at index 19
		        cmsPerson.setIspfEligible(ispfEligibleByte);
		       // cmsPerson.setIspfEligible((String) row[21]); // Assuming ispfEligible is at index 21
		        cmsPerson.setIdMark((String) row[22]); // Assuming idMark is at index 22
		        cmsPerson.setPanNo((String) row[23]); // Assuming PANNO is at index 23
		        cmsPerson.setUnitId((String) row[24]); // Assuming unitId is at index 24
		        String uid = (String) row[24]; // Assuming unitId is at index 24
		        Long employerId = Long.valueOf(uid);
		        CMSPrincipalEmployer unit = principalEmployerService.getCMSPRINCIPALEMPLOYERById(employerId); // Assuming findById() method fetches Unit entity by ID
		        cmsPerson.setUnit(unit);
		        cmsPerson.setDepartmentId((String) row[25]); // Assuming departmentId is at index 25
		        String contId = (String) row[26]; // Assuming unitId is at index 24
		        Long contrcatorId = Long.valueOf(contId);
		        CMSContractor cont = contractorService.getContractorById(contrcatorId);
		        cmsPerson.setContractor(cont); // Assuming contractorId is at index 26
		        cmsPerson.setSkillId((String) row[27]); // Assuming skillId is at index 27
		        cmsPerson.setTradeId((String) row[28]); // Assuming tradeId is at index 28
		        cmsPerson.setAadharNumber((String) row[29]);
		        cmsPersons.add(cmsPerson);
		    }

		    return cmsPersons;
		}
//		@Transactional
//		    @Override
//		    public List<CMSPerson> getAllPersonsByPrincipalEmployerAndContractor(Long unitId, Long contractorId) {
//		        Session session = entityManager.unwrap(Session.class);
//		        String sqlQuery = "SELECT CAST(p.employeeId AS INTEGER) AS employeeId, " +
//		                "p.employeeCode, " +
//		                "p.firstName, " +
//		                "p.relationName, " +
//		                "p.lastName, " +
//		                "p.dateOfBirth, " +
//		                "p.dateOfJoining, " +
//		                "p.dateOfTermination, " +
//		                "p.bloodGroup, " +
//		                "p.hazardousArea, " +
//		                "p.gender, " +
//		                "p.academics, " +
//		                "p.ACCOMODATION, " +
//		                "p.bankBranch, " +
//		                "p.accountNo, " +
//		                "p.emergencyName, " +
//		                "p.emergencyNumber, " +
//		                "p.mobileNumber, " +
//		                "p.accessLevel, " +
//		                "p.esicNumber, " +
//		                "p.uanNumber, " +
//		                "p.ISPFELIGIBLE, " +
//		                "p.idMark, " +
//		                "p.PANNO, " +
////		                "p.CREATEDTM, " +
////		                "p.UPDATEDTM, " +
////		                "p.UPDATEDBY, " +
//		                "cdUnit.CUSTOMDATATEXT AS unitId, " +
//		                "cdDepartment.CUSTOMDATATEXT AS departmentId, " +
//		                "cdContractor.CUSTOMDATATEXT AS contractorId, " +
//		                "cdSkill.CUSTOMDATATEXT AS skillId, " +
//		                "cdTrade.CUSTOMDATATEXT AS tradeId " +
//		                "FROM CMSPERSON p " +
//		                "JOIN CMSPERSONCUSTOMDATA cdUnit ON p.EMPLOYEEID = cdUnit.EMPLOYEEID " +
//		                "JOIN CMSPERSONCUSTOMDATADEFINITION cddUnit ON cdUnit.CSTMDEFID = cddUnit.CSTMDEFID " +
//		                "JOIN CMSPERSONCUSTOMDATA cdDepartment ON p.EMPLOYEEID = cdDepartment.EMPLOYEEID " +
//		                "JOIN CMSPERSONCUSTOMDATADEFINITION cddDepartment ON cdDepartment.CSTMDEFID = cddDepartment.CSTMDEFID " +
//		                "JOIN CMSPERSONCUSTOMDATA cdContractor ON p.EMPLOYEEID = cdContractor.EMPLOYEEID " +
//		                "JOIN CMSPERSONCUSTOMDATADEFINITION cddContractor ON cdContractor.CSTMDEFID = cddContractor.CSTMDEFID " +
//		                "JOIN CMSPERSONCUSTOMDATA cdSkill ON p.EMPLOYEEID = cdSkill.EMPLOYEEID " +
//		                "JOIN CMSPERSONCUSTOMDATADEFINITION cddSkill ON cdSkill.CSTMDEFID = cddSkill.CSTMDEFID " +
//		                "JOIN CMSPERSONCUSTOMDATA cdTrade ON p.EMPLOYEEID = cdTrade.EMPLOYEEID " +
//		                "JOIN CMSPERSONCUSTOMDATADEFINITION cddTrade ON cdTrade.CSTMDEFID = cddTrade.CSTMDEFID " +
//		                "WHERE cddUnit.CSTMDEFNAME = 'UnitId' " +
//		                "AND cddDepartment.CSTMDEFNAME = 'DepartmentId' " +
//		                "AND cddContractor.CSTMDEFNAME = 'ContractorId' " +
//		                "AND cddSkill.CSTMDEFNAME = 'SkillId' " +
//		                "AND cddTrade.CSTMDEFNAME = 'TradeId' AND cdUnit.CUSTOMDATATEXT = :unitId AND cdContractor.CUSTOMDATATEXT = :contractorId";
//		        
//		        Query query = session.createNativeQuery(sqlQuery)
//		                .addScalar("employeeId")
//		                .addScalar("employeeCode")
//		                .addScalar("firstName")
//		                .addScalar("relationName")
//		                .addScalar("lastName")
//		                .addScalar("dateOfBirth")
//		                .addScalar("dateOfJoining")
//		                .addScalar("dateOfTermination")
//		                .addScalar("bloodGroup")
//		                .addScalar("hazardousArea")
//		                .addScalar("gender")
//		                .addScalar("academics")
//		                .addScalar("accommodation")
//		                .addScalar("bankBranch")
//		                .addScalar("accountNo")
//		                .addScalar("emergencyName")
//		                .addScalar("emergencyNumber")
//		                .addScalar("mobileNumber")
//		                .addScalar("accessLevel")
//		                .addScalar("esicNumber")
//		                .addScalar("uanNumber")
//		                .addScalar("ispfEligible")
//		                .addScalar("idMark")
//		                .addScalar("panno")
//						/*
//						 * .addScalar("createdDateTime") .addScalar("updatedDateTime")
//						 * .addScalar("updatedBy")
//						 */
//		                .addScalar("unitId")
//		                .addScalar("departmentId")
//		                .addScalar("contractorId")
//		                .addScalar("skillId")
//		                .addScalar("tradeId")
//		                .setResultTransformer(Transformers.aliasToBean(CMSPerson.class));
//
//		        return query.getResultList();
//		    }
		 @Override
		    public List<CMSPersonCustomData> getCustomDataByEmployeeId(Long employeeId) {
		        String queryString = "SELECT cd.CUSTOMDATATEXT, cdd.CSTMDEFNAME " +
		                             "FROM CMSPERSONCUSTOMDATA cd " +
		                             "INNER JOIN CMSPERSONCUSTOMDATADEFINITION cdd ON cd.CSTMDEFID = cdd.CSTMDEFID " +
		                             "WHERE cd.EMPLOYEEID = :employeeId";

		        TypedQuery<CMSPersonCustomData> query = entityManager.createQuery(queryString, CMSPersonCustomData.class);
		        query.setParameter("employeeId", employeeId);

		        return query.getResultList();
		    }

		 @Override
		 public GeneralMaster findByGMId(Integer bloodGroupId) {
		     Session session = entityManager.unwrap(Session.class);
		     String sqlQuery = "SELECT GMNAME, GMDESCRIPTION FROM CMSGENERALMASTER WHERE GMID = :bloodGroupId";
		     Query query = session.createNativeQuery(sqlQuery)
		             .setParameter("bloodGroupId", bloodGroupId);
		     List<Object[]> resultList = query.getResultList();

		     // Iterate over the result list and map each row to a GeneralMaster object
		     List<GeneralMaster> generalMasters = new ArrayList<>();
		     for (Object[] row : resultList) {
		         GeneralMaster generalMaster = new GeneralMaster();
		         generalMaster.setGmname((String) row[0]); // Assuming GMNAME is at index 0
		         generalMaster.setGmdescription((String) row[1]); // Assuming GMDESCRIPTION is at index 1
		         generalMasters.add(generalMaster);
		     }

		     return generalMasters.isEmpty() ? null : generalMasters.get(0);
		 }
		 public List<GeneralMaster> getGeneralMasterOptionsByName(String typeName) {
			 String query = "SELECT gm " +
		               "FROM GeneralMaster gm " +
		               "JOIN gm.gmType gmt " +
		               "WHERE gmt.gmType = :typeName";
			    
			    Query optionsQuery = entityManager.createQuery(query);
			    optionsQuery.setParameter("typeName", typeName);
			    return optionsQuery.getResultList();
			}



}
