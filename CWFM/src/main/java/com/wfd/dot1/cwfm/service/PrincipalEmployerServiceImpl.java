package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.PrincipalEmployerDao;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
@Service
public class PrincipalEmployerServiceImpl implements PrincipalEmployerService{

	@Autowired
	PrincipalEmployerDao peDao;
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userId) {
		// TODO Auto-generated method stub
		return peDao.getAllPrincipalEmployer( userId);
	}
	@Override
	public PrincipalEmployer getIndividualPEDetailByUnitId(String id) {
		// TODO Auto-generated method stub
		return peDao.getIndividualPEDetailByUnitId(id);
	}

}