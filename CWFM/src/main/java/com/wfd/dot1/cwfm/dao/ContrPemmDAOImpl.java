package com.wfd.dot1.cwfm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.CMSContrPemm;
@Repository
public class ContrPemmDAOImpl implements ContrPemmDAO {

    private DataSource dataSource;

    public ContrPemmDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<CMSContrPemm> getMappingsByContractorId(long contractorId) {
        List<CMSContrPemm> mappings = new ArrayList<>();
        String sql = "SELECT * FROM CMSCONTRPEMM WHERE CONTRACTORID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, contractorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CMSContrPemm mapping = new CMSContrPemm();
                    mapping.setRefId(resultSet.getLong("REFID"));
                    mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
                    mapping.setUnitId(resultSet.getLong("UNITID"));
                    mapping.setManagerNm(resultSet.getString("MANAGERNM"));
                    mapping.setManagerEmail(resultSet.getString("MANAGEREMAIL"));
                    mapping.setManagerMobile(resultSet.getString("MANAGERMOBILE"));
                    mapping.setPeriodStartDt(resultSet.getDate("PERIODSTARTDT"));
                    mapping.setPeriodEndDt(resultSet.getDate("PERIODENDDT"));
                    mapping.setPfNum(resultSet.getString("PFNUM"));
                    mapping.setPfApplyDt(resultSet.getDate("PFAPPLYDT"));
                    mapping.setEsicNum(resultSet.getString("ESICNUM"));
                    mapping.setRcValidated(resultSet.getString("RCVALIDATED").equals("Y"));
                    mappings.add(mapping);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mappings;
    }
    
    @Override
    public List<CMSContrPemm> getMappingsByContractorIdAndUnitId(long contractorId, long unitId) {
        List<CMSContrPemm> mappings = new ArrayList<>();
        String sql = "SELECT * FROM CMSCONTRPEMM WHERE CONTRACTORID = ? AND UNITID = ?";
        System.out.println("Executing SQL query: " + sql + " with parameters: " + contractorId + ", " + unitId);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, contractorId);
            statement.setLong(2, unitId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CMSContrPemm mapping = new CMSContrPemm();
                    mapping.setRefId(resultSet.getLong("REFID"));
                    mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
                    mapping.setUnitId(resultSet.getLong("UNITID"));
                    mapping.setManagerNm(resultSet.getString("MANAGERNM"));
                    mapping.setManagerEmail(resultSet.getString("MANAGEREMAIL"));
                    mapping.setManagerMobile(resultSet.getString("MANAGERMOBILE"));
                    mapping.setPeriodStartDt(resultSet.getDate("PERIODSTARTDT"));
                    mapping.setPeriodEndDt(resultSet.getDate("PERIODENDDT"));
                    mapping.setPfNum(resultSet.getString("PFNUM"));
                    mapping.setPfApplyDt(resultSet.getDate("PFAPPLYDT"));
                    mapping.setEsicNum(resultSet.getString("ESICNUM"));
                    mapping.setRcValidated(resultSet.getString("RCVALIDATED").equals("Y"));
                    mappings.add(mapping);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mappings;
    }
    @Override
    public CMSContrPemm getMappingByContractorIdAndUnitId(Long id, Long principalEmployerId) {
        CMSContrPemm mapping = null;
        String sql = "SELECT * FROM CMSCONTRPEMM WHERE CONTRACTORID = ? AND UNITID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setLong(2, principalEmployerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    mapping = new CMSContrPemm();
                    mapping.setRefId(resultSet.getLong("REFID"));
                    mapping.setContractorId(resultSet.getLong("CONTRACTORID"));
                    mapping.setUnitId(resultSet.getLong("UNITID"));
                    mapping.setManagerNm(resultSet.getString("MANAGERNM"));
                    mapping.setManagerEmail(resultSet.getString("MANAGEREMAIL"));
                    mapping.setManagerMobile(resultSet.getString("MANAGERMOBILE"));
                    mapping.setPeriodStartDt(resultSet.getDate("PERIODSTARTDT"));
                    mapping.setPeriodEndDt(resultSet.getDate("PERIODENDDT"));
                    mapping.setPfNum(resultSet.getString("PFNUM"));
                    mapping.setPfApplyDt(resultSet.getDate("PFAPPLYDT"));
                    mapping.setEsicNum(resultSet.getString("ESICNUM"));
                    mapping.setRcValidated(resultSet.getString("RCVALIDATED").equals("Y"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapping;
    }


    
}

