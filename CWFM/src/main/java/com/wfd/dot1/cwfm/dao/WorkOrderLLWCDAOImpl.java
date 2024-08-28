package com.wfd.dot1.cwfm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.entity.WorkOrderLLWC;
@Repository
public class WorkOrderLLWCDAOImpl implements WorkOrderLLWCDAO {

	 private DataSource dataSource;

	    public WorkOrderLLWCDAOImpl(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    @Override
	    public List<WorkOrderLLWC> getWorkOrdersByContractorId(long contractorId) {
	        List<WorkOrderLLWC> workOrders = new ArrayList<>();
	        String sql = "SELECT * FROM CMSWORKORDER_LLWC WHERE CONTRACTORID = ?";
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setLong(1, contractorId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    WorkOrderLLWC workOrder = new WorkOrderLLWC();
	                    workOrder.setWollid(resultSet.getLong("WOLLID"));
	                    workOrder.setWoNumber(resultSet.getString("WONUMBER"));
	                    workOrder.setLicenseNumber(resultSet.getString("LICENSE_NUMBER"));
	                    workOrder.setLicenseType(resultSet.getString("LICENSE_TYPE"));
	                    workOrders.add(workOrder);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return workOrders;
	    }
}
