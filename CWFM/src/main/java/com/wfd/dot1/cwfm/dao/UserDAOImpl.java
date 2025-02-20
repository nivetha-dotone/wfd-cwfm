package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
  
    public String getuserbyid() {
	    return QueryFileWatcher.getQuery("GET_USER_BY_ID");
    }
    public String getallusers() {
	    return QueryFileWatcher.getQuery("GET_ALL_USERS");
    }
    public String saveuser() {
	    return QueryFileWatcher.getQuery("SAVE_USERS");
    }
    public String updateuser() {
	    return QueryFileWatcher.getQuery("UPDATE_USERS");
    }
    public String deleteuser() {
	    return QueryFileWatcher.getQuery("DELETE_USERS");
    }
    public String saveusers() {
	    return QueryFileWatcher.getQuery("SAVE_USER");
    }
    @Override
    public MasterUser getUserById(String userId) {
    	String query=getuserbyid();
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(rs.getInt("userId"));
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
    	String query=getallusers();
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            MasterUser user = new MasterUser();
            user.setUserId(rs.getInt("userId"));
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
    	String query=saveuser();
        jdbcTemplate.update(query, 
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
    	String query=updateuser();
        jdbcTemplate.update(query, 
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
    	String query=deleteuser();
        jdbcTemplate.update(query, userId);
    }
    
    public void saveUser(MasterUser user, List<Long> roleIds) {
        String query=saveusers();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println("UserId: " + user.getUserId());
        System.out.println("FirstName: " + user.getFirstName());
        System.out.println("LastName: " + user.getLastName());
        System.out.println("EmailId: " + user.getEmailId());
        System.out.println("ContactNumber: " + user.getContactNumber());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Status: " + user.getStatus());
        System.out.println("UserAccount: " + user.getUserAccount());
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Insert the user and retrieve the generated key
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {"UserId"});
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmailId());
            ps.setString(4, user.getContactNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getUserAccount());
            return ps;
        }, keyHolder);
        // Set the generated UserId in the user object
        int userId = keyHolder.getKey().intValue();
        user.setUserId(userId);
        // Save role mappings if provided
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                String insertRoleMappingQuery = "INSERT INTO UserRoleMapping (UserId, RoleId) VALUES (?, ?)";
                jdbcTemplate.update(insertRoleMappingQuery, user.getUserId(), roleId);
            }
        }

    }
    
}
