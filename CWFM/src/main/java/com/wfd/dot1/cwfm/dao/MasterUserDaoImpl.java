package com.wfd.dot1.cwfm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public MasterUser findMasterUserDetailsByUserName(String username, String password) {
		log.info("Entering into findMasterUserDetailsByUserName dao method "+username+" "+password);
		MasterUser user =null;
		log.info("Query to findMasterUserDetailsByUserName "+MasterUserQueryBank.GET_MASTERUSER_BY_USERNAME_AND_PASSWROD);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(MasterUserQueryBank.GET_MASTERUSER_BY_USERNAME_AND_PASSWROD, username,password);
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
			log.info("Exiting from findMasterUserDetailsByUserName dao method "+user.toString());
		}
		
		return user;
	}

}
