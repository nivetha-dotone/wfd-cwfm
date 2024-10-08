package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface MasterUserDao {

	public MasterUser findMasterUserDetailsByUserName(String username,String password);
}
