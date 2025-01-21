package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.MasterUser;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
   
    @Override
    public MasterUser getUserById(String userId) {
        String sql = "SELECT userId, userAccount, emailId, firstName, lastName, contactNumber,  status, password FROM MASTERUSER WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(rs.getString("userId"));
            user.setUserAccount(rs.getString("userAccount"));
            user.setEmailId(rs.getString("emailId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setStatus(rs.getString("status"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("firstName") + " " + rs.getString("lastName")); // Calculate fullName
            return user;
        });
    }

    @Override
    public List<MasterUser> getAllUsers() {
        String sql = "SELECT userId, userAccount, emailId, firstName, lastName, contactNumber,  status, password FROM MASTERUSER";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(rs.getString("userId"));
            user.setUserAccount(rs.getString("userAccount"));
            user.setEmailId(rs.getString("emailId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setStatus(rs.getString("status"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("firstName") + " " + rs.getString("lastName")); // Calculate fullName
            return user;
        });
    }


    @Override
    public void saveUser(MasterUser user) {
        String sql = "INSERT INTO MASTERUSER (userId, userAccount, emailId, firstName, lastName, fullName, contactNumber,  status, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            user.getUserId(),
            user.getUserAccount(),
            user.getEmailId(),
            user.getFirstName(),
            user.getLastName(),
            user.getFullName(),
            user.getContactNumber(),
            user.getStatus(),
            user.getPassword()
        );
    }

    @Override
    public void updateUser(MasterUser user) {
        String sql = "UPDATE MASTERUSER SET userAccount = ?, emailId = ?, firstName = ?, lastName = ?, fullName = ?, contactNumber = ?,  status = ?, password = ? WHERE userId = ?";
        jdbcTemplate.update(sql, 
            user.getUserAccount(),
            user.getEmailId(),
            user.getFirstName(),
            user.getLastName(),
            user.getFullName(),
            user.getContactNumber(),
            user.getStatus(),
            user.getPassword(),
            user.getUserId()
        );
    }

    @Override
    public void deleteUser(String userId) {
        String sql = "DELETE FROM MASTERUSER WHERE userId = ?";
        jdbcTemplate.update(sql, userId);
    }
    
    @Override
    public void saveUser(MasterUser user, List<Long> roleIds) {
        // Save user details
        user.setPassword(user.getPassword()); // Encrypt password
        String insertUserQuery = "INSERT INTO MASTERUSER (UserId, FirstName,LastName, EmailId, ContactNumber, Password,Status,userAccount) VALUES (?, ?, ?, ?, ?,?,?,?)";
        jdbcTemplate.update(insertUserQuery, user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmailId(), user.getContactNumber(), user.getPassword(),'A',user.getUserAccount());

        // Save role mappings
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                String insertRoleMappingQuery = "INSERT INTO UserRoleMapping (UserId, RoleId) VALUES (?, ?)";
                jdbcTemplate.update(insertRoleMappingQuery, user.getUserId(), roleId); // Set User ID and Role ID
            }
        }
    }

	@Override
	public void saveUser(User user, List<Long> roleIds) {
		// TODO Auto-generated method stub
		
	}
    
}
