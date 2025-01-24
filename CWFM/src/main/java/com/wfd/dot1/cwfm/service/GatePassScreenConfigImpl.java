package com.wfd.dot1.cwfm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.GassPassScreenConfigDao;
@Service
public class GatePassScreenConfigImpl implements GatePassScreenConfig{

	@Autowired
	GassPassScreenConfigDao dao;
	@Override
	public Map<String, Object> getGatePassCreationConfig(String screenName) {
		// TODO Auto-generated method stub
		return dao.getGatePassCreationConfig(screenName);
	}

}
