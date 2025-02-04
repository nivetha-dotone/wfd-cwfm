package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;

public interface PrincipalEmployerService {
	
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount);

	public PrincipalEmployer getIndividualPEDetailByUnitId(String id);

}
