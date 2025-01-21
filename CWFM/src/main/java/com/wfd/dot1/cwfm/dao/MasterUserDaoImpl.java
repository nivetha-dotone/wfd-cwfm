package com.wfd.dot1.cwfm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.queries.MasterUserQueryBank;
@Repository
public class MasterUserDaoImpl implements MasterUserDao{

	private static final Logger log = LoggerFactory.getLogger(MasterUserDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Value("${GET_MASTERUSER_BY_USERNAME_AND_PASSWROD}")
	    private String getMasterUserByUsernameAndPassword;
	
	@Override
	public MasterUser findMasterUserDetailsByUserName(String username, String password) {
		log.info("Entering into findMasterUserDetailsByUserName dao method "+username+" "+password);
		MasterUser user =null;
		log.info("Query to findMasterUserDetailsByUserName "+getMasterUserByUsernameAndPassword);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(getMasterUserByUsernameAndPassword, username,password);
		if (rs.next()) {
			user = new MasterUser();
			user.setUserId(rs.getString("UserId"));
			user.setUserName(rs.getString("UserName"));
			user.setEmailId(rs.getString("EmailId"));
			user.setFirstName(rs.getString("FirstName"));
			user.setLastName(rs.getString("LastName"));
			user.setRoleName(rs.getString("RoleName"));
			user.setContactNumber(rs.getString("ContactNumber"));
			user.setStatus(rs.getString("Status"));
			user.setBusinessType(rs.getString("BusinessType"));
			log.info("Exiting from findMasterUserDetailsByUserName dao method "+user.toString());
		}
		
		return user;
	}
	@Override
    public String getPasswordByUserId(String userId) {
        String sql = "SELECT Password FROM MASTERUSER WHERE UserId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        String sql = "UPDATE MASTERUSER SET Password = ? WHERE UserId = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }
}
