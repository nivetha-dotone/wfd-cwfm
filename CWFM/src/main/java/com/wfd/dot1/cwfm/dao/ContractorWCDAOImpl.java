package com.wfd.dot1.cwfm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.CMSContractorWC;
@Repository
public class ContractorWCDAOImpl implements ContractorWCDAO {

    private DataSource dataSource;

    public ContractorWCDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<CMSContractorWC> getWorkOrdersByContractorId(long contractorId) {
        List<CMSContractorWC> workOrders = new ArrayList<>();
        String sql = "SELECT * FROM CMSCONTRACTOR_WC WHERE CONTRACTORID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, contractorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CMSContractorWC workOrder = new CMSContractorWC();
                    workOrder.setWcId(resultSet.getLong("WCID"));
                    workOrder.setWcCode(resultSet.getString("WC_CODE"));
                    workOrder.setContractorId(resultSet.getLong("CONTRACTORID"));
                    workOrder.setUnitId(resultSet.getLong("UNITID"));
                    workOrder.setNatureOfId(resultSet.getInt("NATURE_OF_ID"));
                    workOrder.setWcFromDtm(resultSet.getTimestamp("WC_FROM_DTM"));
                    workOrder.setWcToDtm(resultSet.getTimestamp("WC_TO_DTM"));
                    workOrder.setWcTotal(resultSet.getInt("WC_TOTAL"));
                    workOrder.setDeleteSw(resultSet.getInt("DELETE_SW"));
                    workOrder.setLicenceType(resultSet.getString("LICENCE_TYPE"));
                    workOrder.setIsVerified(resultSet.getString("ISVERIFIED"));
                    workOrder.setAttachmentNm(resultSet.getString("ATTACHMENTNM"));
                    workOrder.setExtendToSubcontractor(resultSet.getInt("EXTENDTOSUBCONTRACTOR"));
                    workOrder.setCreatedDtm(resultSet.getTimestamp("CREATED_DTM"));
                    workOrder.setUpdatedDtm(resultSet.getTimestamp("UPDATED_DTM"));
                    workOrders.add(workOrder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workOrders;
    }

}
