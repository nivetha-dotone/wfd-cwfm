package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;

public interface PrincipalEmployerDao {

	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount);

	public PrincipalEmployer getIndividualPEDetailByUnitId(String id);

	public List<PrincipalEmployer> getAllPrincipalEmployerForAdmin();

	
}
