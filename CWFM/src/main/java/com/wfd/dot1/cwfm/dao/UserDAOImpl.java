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

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public MasterUser getUserById(String userId) {
        String sql = "SELECT userId, userAccount, emailId, firstName, lastName, contactNumber,  status, password FROM MASTERUSER WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {
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
        String sql = "SELECT userId, userAccount, emailId, firstName, lastName, contactNumber,  status, password FROM MASTERUSER";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
    
    public void saveUser(MasterUser user, List<Long> roleIds) {
        String insertUserQuery = "INSERT INTO MASTERUSER (FirstName, LastName, EmailId, ContactNumber, Password, Status, userAccount) VALUES (?, ?, ?, ?, ?, 'A', ?)";
        
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
            PreparedStatement ps = connection.prepareStatement(insertUserQuery, new String[] {"UserId"});
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
    
//    @Override
//    public int saveUser(MasterUser user, List<Long> roleIds) {
//        // Save user details
//        System.out.println("UserId: " + user.getUserId());
//        System.out.println("FirstName: " + user.getFirstName());
//        System.out.println("LastName: " + user.getLastName());
//        System.out.println("EmailId: " + user.getEmailId());
//        System.out.println("ContactNumber: " + user.getContactNumber());
//        System.out.println("Password: " + user.getPassword());
//        System.out.println("Status: " + user.getStatus());
//        System.out.println("UserAccount: " + user.getUserAccount());
//
//        // Encrypt password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // Insert user into MASTERUSER table and retrieve the generated UserId
//        String insertUserQuery = "INSERT INTO MASTERUSER (FirstName, LastName, EmailId, ContactNumber, Password, Status, userAccount) VALUES (?, ?, ?, ?, ?, 'A', ?)";
//        System.out.println("insertUserQuery: " + insertUserQuery);
////        int userId = jdbcTemplate.update(insertUserQuery, 
////            user.getFirstName(), 
////            user.getLastName(), 
////            user.getEmailId(), 
////            user.getContactNumber(), 
////            user.getPassword(), 
//////            'A', // Use a constant char value
////            user.getUserAccount());
////
////        // Update the UserId in the user object as a String
////        user.setUserId(userId);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        // Execute the query with KeyHolder
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(insertUserQuery, new String[] {"UserId"});
//            ps.setString(1, user.getFirstName());
//            ps.setString(2, user.getLastName());
//            ps.setString(3, user.getEmailId());
//            ps.setString(4, user.getContactNumber());
//            ps.setString(5, user.getPassword());
//            ps.setString(6, user.getUserAccount());
//            return ps;
//        }, keyHolder);
//        // Save role mappings
//        int userId = keyHolder.getKey().intValue();
//        user.setUserId(userId);
//        if (roleIds != null) {
//            for (Long roleId : roleIds) {
//                String insertRoleMappingQuery = "INSERT INTO UserRoleMapping (UserId, RoleId) VALUES (?, ?)";
//                jdbcTemplate.update(insertRoleMappingQuery, user.getUserId(), roleId);
//            }
//        }
//        return userId;
//    }


	
    
}
