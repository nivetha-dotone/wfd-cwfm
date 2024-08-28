package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ReportWtkSessionIdDao;
import com.wfd.dot1.cwfm.entity.State;

@Service
public class ReportWtkSessionIdServiceImpl implements ReportWtkSessionIdService {
    @Autowired
    private ReportWtkSessionIdDao reportWtkSessionIdDao;

    @Override
    public State getStateById(int stateId) {
        return reportWtkSessionIdDao.getStateById(stateId);
    }

    @Override
    public List<State> getAllStates() {
        return reportWtkSessionIdDao.getAllStates();
    }

    // Implement other methods as needed for ReportWtkSessionId entity
}
