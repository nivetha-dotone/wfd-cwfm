package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface MasterUserService {

	public MasterUser findMasterUserDetailsByUserName(String username,String password);
}
