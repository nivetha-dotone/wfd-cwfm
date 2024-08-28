package com.wfd.dot1.cwfm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.entity.State;

@Repository
public class ReportWtkSessionIdDaoImpl implements ReportWtkSessionIdDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportWtkSessionIdDaoImpl.class);

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public State getStateById(int stateId) {
        return entityManager.find(State.class, stateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<State> getAllStates() {
        try {
            List<State> states = hibernateTemplate.loadAll(State.class);
            // Log the number of states retrieved
            LOGGER.info("Number of states retrieved: {}", states.size());
            // Log each state's details
            for (State state : states) {
                LOGGER.info("State ID: {}, State Name: {}", state.getStateId(), state.getStateName());
            }
            return states;
        } catch (Exception e) {
            // Log the exception
            LOGGER.error("Failed to retrieve all states", e);
            // Rethrow the exception
            throw new RuntimeException("Failed to retrieve all states", e);
        }
    }

    // Implement other methods as needed for ReportWtkSessionId entity
}
