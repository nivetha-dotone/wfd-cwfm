package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.entity.State;

public interface ReportWtkSessionIdService {
    State getStateById(int stateId);
    List<State> getAllStates();
    // Add other methods as needed for ReportWtkSessionId entity
}

