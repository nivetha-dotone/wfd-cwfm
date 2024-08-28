package com.wfd.dot1.cwfm.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.CMSContractor;
import com.wfd.dot1.cwfm.entity.CMSContractorWC;
@Repository
public class ContractorDAOImpl implements ContractorDAO {
    private DataSource dataSource;
    @PersistenceContext
    private EntityManager entityManager;
    public ContractorDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CMSContractor getContractorById(long contractorId) {
        String sql = "SELECT * FROM CMSCONTRACTOR WHERE CONTRACTORID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, contractorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CMSContractor contractor = new CMSContractor();
                    contractor.setContractorId(resultSet.getLong("CONTRACTORID"));
                    contractor.setName(resultSet.getString("NAME"));
                    contractor.setAddress(resultSet.getString("ADDRESS"));
                    contractor.setCode(resultSet.getString("CODE"));
                    contractor.setIsBlocked(resultSet.getInt("ISBLOCKED") == 1);
                    return contractor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CMSContractor> getAllContractors() {
        List<CMSContractor> contractors = new ArrayList<>();
        String sql = "SELECT * FROM CMSCONTRACTOR";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CMSContractor contractor = new CMSContractor();
                contractor.setContractorId(resultSet.getLong("CONTRACTORID"));
                contractor.setName(resultSet.getString("NAME"));
                contractor.setAddress(resultSet.getString("ADDRESS"));
                contractor.setCode(resultSet.getString("CODE"));
                contractor.setIsBlocked(resultSet.getInt("ISBLOCKED") == 1);
                contractors.add(contractor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractors;
    }
    // Other methods

    @Override
    public List<CMSContractor> getContractorsByPrincipalEmployerId(Long principalEmployerId) {
        List<CMSContractor> contractors = new ArrayList<>();
        String sql = "SELECT c.* " +
                     "FROM CMSCONTRACTOR c " +
                     "INNER JOIN CMSCONTRPEMM p ON c.CONTRACTORID = p.CONTRACTORID " +
                     "WHERE p.UNITID = ?";
        
        System.out.println("Executing SQL query: " + sql + " with parameters: " + principalEmployerId );
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, principalEmployerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CMSContractor contractor = new CMSContractor();
                    contractor.setContractorId(resultSet.getLong("CONTRACTORID"));
                    contractor.setName(resultSet.getString("NAME"));
                    contractor.setAddress(resultSet.getString("ADDRESS"));
                    contractor.setCode(resultSet.getString("CODE"));
                    contractor.setIsBlocked(resultSet.getInt("ISBLOCKED") == 1);
                    System.out.println("Retrieved WC Code: " + contractor.getCode());
                    // Set other properties as needed
                    contractors.add(contractor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractors;
    }
    @Override
    public List<CMSContractor> searchContractor(String query) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CMSContractor> criteriaQuery = criteriaBuilder.createQuery(CMSContractor.class);
        Root<CMSContractor> root = criteriaQuery.from(CMSContractor.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.like(root.get("code"), "%" + query + "%"));
        predicates.add(criteriaBuilder.like(root.get("name"), "%" + query + "%"));
        // Add additional predicates for other relevant fields as needed

        Predicate finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(finalPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
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

	@Override
	public void addContractor(CMSContractor principalEmployer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContractor(CMSContractor principalEmployer) {
		// TODO Auto-generated method stub
		
	}
}
