package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.entity.State;

public interface ReportWtkSessionIdDao {
    State getStateById(int stateId);
    List<State> getAllStates();
    // Add other methods as needed for ReportWtkSessionId entity
}
