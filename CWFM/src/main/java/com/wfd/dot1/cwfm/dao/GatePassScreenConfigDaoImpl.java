package com.wfd.dot1.cwfm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class GatePassScreenConfigDaoImpl implements GassPassScreenConfigDao{
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	@Override
    public Map<String, Object> getGatePassCreationConfig(String screenName) {
        String query = "SELECT FieldName, IsVisible, IsMandatory FROM SCREENFIELDCONFIG WHERE ScreenName = ?";

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, screenName);

        Map<String, Object> configMap = new HashMap<>();
        for (Map<String, Object> row : resultList) {
            String fieldName = (String) row.get("FieldName");
            Map<String, Object> fieldConfig = new HashMap<>();
            fieldConfig.put("IsVisible", row.get("IsVisible"));
            fieldConfig.put("IsMandatory", row.get("IsMandatory"));

            configMap.put(fieldName, fieldConfig);
        }

        return configMap;
    }

}
