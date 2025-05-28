package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.HrChecklistItem;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.StatutoryAttachment;

@Repository
public class BillConfigDaoImpl implements BillConfigDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void clearExisting() {
        jdbcTemplate.update("DELETE FROM BillConfigKronos");
        jdbcTemplate.update("DELETE FROM BillConfigStatutory");
        jdbcTemplate.update("DELETE FROM BillConfigHrChecklist");
    }

    @Override
    public void saveKronosReports(List<String> reports) {
        String sql = "INSERT INTO BillConfigKronos (REPORTNAME) VALUES (?)";
        for (String report : reports) {
            jdbcTemplate.update(sql, report);
        }
    }

    @Override
    public void saveStatutoryReports(List<String> reports) {
        String sql = "INSERT INTO BillConfigStatutory (ATTACHMENTNAME) VALUES (?)";
        for (String report : reports) {
            jdbcTemplate.update(sql, report);
        }
    }

    @Override
    public void saveChecklistItems(List<HrChecklistItem> items) {
        String sql = "INSERT INTO BillConfigHrChecklist (CHECKPOINTNAME, LICENSEREQUIRED, VALIDUPTOREQUIRED) VALUES (?, ?, ?)";
        for (HrChecklistItem item : items) {
            jdbcTemplate.update(sql, item.getCheckpointName(), item.isLicenseRequired(), item.isValidUptoRequired());
        }
    }

    @Override
    public List<String> fetchKronosReports() {
        String sql = "SELECT REPORTNAME FROM BillConfigKronos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("REPORTNAME"));
    }

    @Override
    public List<String> fetchStatutoryReports() {
        String sql = "SELECT ATTACHMENTNAME FROM BillConfigStatutory";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("ATTACHMENTNAME"));
    }

    @Override
    public List<HrChecklistItem> fetchChecklistItems() {
        String sql = "SELECT ID,CHECKPOINTNAME, LICENSEREQUIRED, VALIDUPTOREQUIRED FROM BillConfigHrChecklist";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            HrChecklistItem item = new HrChecklistItem();
            item.setId(rs.getInt("ID"));
            item.setCheckpointName(rs.getString("CHECKPOINTNAME"));
            item.setLicenseRequired(rs.getBoolean("LICENSEREQUIRED"));
            item.setValidUptoRequired(rs.getBoolean("VALIDUPTOREQUIRED"));
            return item;
        });
    }
    @Override
    public List<KronosReport> fetchKronosReportsWithId() {
        String sql = "SELECT ID,REPORTNAME FROM BillConfigKronos";
        return jdbcTemplate.query(sql, (rs, rowNum) ->{
        KronosReport kr = new KronosReport();
        kr.setReportName( rs.getString("REPORTNAME"));
        kr.setId(rs.getInt("ID"));
        return kr;
        });
    }

    @Override
    public List<StatutoryAttachment> fetchStatutoryReportsWithId() {
        String sql = "SELECT ID,ATTACHMENTNAME FROM BillConfigStatutory";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
        {
        	StatutoryAttachment kr = new StatutoryAttachment();
            kr.setAttachmentName( rs.getString("ATTACHMENTNAME"));
            kr.setId(rs.getInt("ID"));
            return kr;
            });
    }
}

