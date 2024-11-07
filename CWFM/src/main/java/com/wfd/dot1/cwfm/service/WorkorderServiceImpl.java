package com.wfd.dot1.cwfm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.WorkorderDao;
import com.wfd.dot1.cwfm.pojo.Workorder;
@Service
public class WorkorderServiceImpl implements WorkorderService{

	
	@Autowired
	WorkorderDao woDao;
	@Override
	public Workorder getWorkorderById(String id) {
		// TODO Auto-generated method stub
		return woDao.getWorkorderById(id);
	}

}
