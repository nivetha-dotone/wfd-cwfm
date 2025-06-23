package com.wfd.dot1.cwfm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.FileUploadDao;
import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSSubContractor;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLLWC;
import com.wfd.dot1.cwfm.pojo.CMSWorkorderLN;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorWorkorderTYP;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.Workorder;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadDao fileUploadDao;

    @Override
    public void uploadFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Assuming each line represents a record to save in DB
                    String[] data = line.split(",");
                    fileUploadDao.saveData(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
	/*
	 * private static final List<String> EXPECTED_COLUMNS = Arrays.asList("GMTYPE",
	 * "MASTERNAME", "MASTERVALUE");
	 */

    @Override
    public Map<String, Object> processTemplateFile(MultipartFile file, String templateType) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String headerLine = reader.readLine();

        Map<String, Object> savedData = new HashMap<>();

        switch (templateType.toLowerCase()) {
            case "generalmaster":
                if (!headerLine.equalsIgnoreCase("GMNAME,GMDESCRIPTION,GMTYPEID,ISACTIVE,CREATEDTM,UPDATEDTM,UPDATEDBY")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                // savedData = processGeneralMaster(reader);
                break;
            case "contractor":
                if (!headerLine.equalsIgnoreCase("CONTRACTOR NAME,CONTRACTOR ADDRESS,City,Plant Code,Contractor MANAGER NAME,LICENSE NUM,LICENCSE VALID FROM,LICENCSE VALID TO,"
                        + "LICENCSE COVERAGE,TOTAL STRENGTH,MAXIMUM NUMBER OF WORKMEN,NATURE OF WORK,LOCATION OF WORK,CONTRACTOR VALIDITY START DATE,CONTRACTOR VALIDITY END DATE,"
                        + "CONTRACTOR ID,PF CODE,EC/WC number,EC/WC Validity Start Date,EC/WC Validity End Date,Coverage,PF NUMBER,PF APPLY DATE,Reference,Mobile Number,ESI NUMBER,"
                        + "ESI VALID FROM,ESI VALID TO,Organisation,Main Contractor Code,Work Order Number")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                 savedData = processContractor(reader);
                break;
            case "workorder":
                if (!headerLine.equalsIgnoreCase("Work Order Number,Item,Short Text,Delivery Complition,Item Changed ON,Work Order Validitiy From,Work Order Validitiy To,Work Order Type,"
                        + "G/L Code,Plant code,Cost Center,Nature of Job,Rate,Quantity,PM Order No,WBS Element,Quantity Completed,Work Order Release Date,Service Entry Created Date,Service Entry Updated Date,Organisation")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                savedData = processWorkorder(reader);
                break;
            case "minimumwage":
                if (!headerLine.equalsIgnoreCase("BASIC,DA,ALLOWANCE,FROMDATE")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                // savedData = saveMinimumWage(reader);
                break;
            case "principalemployer":
                if (!headerLine.equalsIgnoreCase("ORGANISATION,PLANTCODE,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAX CONTRACT WORKMEN,BOCWAPPLICABILITY,"
                        + "ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,ESWC,FACTORY LICENSE NUMBER,State")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                 savedData = processPrincipalEmployer(reader);
                break;

            default:
                throw new Exception("Unsupported template type: " + templateType);
        }

        // Use mutable map instead of Map.of
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "File uploaded successfully!");
        result.put("data", savedData);
        return result;
    }



    private List<Map<String, Object>> processGeneralMaster(BufferedReader reader) throws IOException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",", -1);
            if (fields.length < 7) continue;

            String gmName = fields[0].trim();
            String gmDescription = fields[1].trim();
            int gmTypeId = Integer.parseInt(fields[2].trim());
            boolean isActive = "1".equals(fields[3].trim());
            String createdTM = fields[4].trim();
            String updatedTM = fields[5].trim();
            String updatedBy = fields[6].trim();
            
            CmsGeneralMaster gm = new CmsGeneralMaster(gmName,gmDescription, gmTypeId,isActive,createdTM,updatedTM,updatedBy);
            fileUploadDao.saveGeneralMaster(gm);

            // Map each POJO to a simple map for JSON-friendly structure
            Map<String, Object> map = new HashMap<>();
            map.put("gmName", gm.getGmName());
            map.put("gmDescription", gm.getGmDescription());
            map.put("gmTypeId", gm.getGmTypeId());
            map.put("isActive", gm.isActive());
            map.put("createdTM", gm.getCreatedTM());
            map.put("updatedTM", gm.getUpdatedTM());
            map.put("updatedBy", gm.getUpdatedBy());
            
            dataList.add(map);
        }

        return dataList;
    }
    

    
    private List<Map<String, Object>> saveMinimumWage(BufferedReader reader) throws IOException {
        List<Map<String, Object>> savedData = new ArrayList<>();
        String line;

        // Skip the header
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",", -1);
            if (fields.length < 4) continue;

            MinimumWageDTO dto = new MinimumWageDTO();
            dto.setBasic(new BigDecimal(fields[0].trim()));
            dto.setDa(new BigDecimal(fields[1].trim()));
            dto.setAllowance(new BigDecimal(fields[2].trim()));
            dto.setFromDate(LocalDate.parse(fields[3].trim()));  // Format: yyyy-MM-dd

            // Save using service (inserts into CMSWAGE and CMSMINIMUMWAGE)
            FileUploadService.saveMinimumWageTemplate(dto);

            // Add to displayable result
            Map<String, Object> map = new HashMap<>();
            map.put("basic", dto.getBasic());
            map.put("da", dto.getDa());
            map.put("allowance", dto.getAllowance());
            map.put("fromDate", dto.getFromDate().toString());
            savedData.add(map);
        }

        return savedData;
    }

    private Map<String, Object> processPrincipalEmployer(BufferedReader reader) throws IOException {
        List<Map<String, Object>> successData = new ArrayList<>();
        List<Map<String, Object>> errorData = new ArrayList<>();

        String line;
        int rowNum = 0;

        String[] fieldNames = {
            "organization", "code", "name", "address", "managerName",
            "managerAddrs", "businessType", "maxWorkmen", "maxCntrWorkmen",
            "bocwApplicability", "isMwApplicability", "licenseNumber", "pfCode",
            "wcNumber", "factoryLicenseNumber", "stateNM"
        };

        while ((line = reader.readLine()) != null) {
            rowNum++;
            line = line.replaceAll("[\\x00-\\x1F\\x7F]", "");

            if (line.trim().isEmpty()) continue;

            String[] rawFields = line.split(",", -1);
            String[] fields = new String[rawFields.length];
            for (int i = 0; i < rawFields.length; i++) {
                fields[i] = rawFields[i].trim().replaceAll("\"", "");
            }

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            if (fields.length < 16) {
                errorData.add(Map.of("row", rowNum, "error", "Insufficient number of fields"));
                continue;
            }

            for (int i = 0; i < fieldNames.length; i++) {
                if (fields[i].isBlank()) {
                    fieldErrors.put(fieldNames[i], "is Mandatory");
                }
            }

            if (!fieldErrors.isEmpty()) {
                errorData.add(Map.of("row", rowNum, "fieldErrors", fieldErrors));
                continue;
            }

            try {
                String code = fields[1];
                String stateName = fields[15];

                // Duplicate plantCode check
                if (fileUploadDao.isPrincipalEmployerCodeExists(code)) {
                    errorData.add(Map.of("row", rowNum, "error", "Duplicate plantCode: " + code + " already exists"));
                    continue;
                }

                // Check if state exists in CMSSTATE table
                Long stateId = fileUploadDao.getStateIdByName(stateName);
                if (stateId == null) {
                    errorData.add(Map.of("row", rowNum, "error", "State not found: " + stateName));
                    continue;
                }

                // Save PrincipalEmployer
                PrincipalEmployer p = new PrincipalEmployer();
                p.setOrganization(fields[0]);
                p.setCode(code);
                p.setName(fields[2]);
                p.setAddress(fields[3]);
                p.setManagerName(fields[4]);
                p.setManagerAddrs(fields[5]);
                p.setBusinessType(fields[6]);
                p.setMaxWorkmen(Integer.parseInt(fields[7]));
                p.setMaxCntrWorkmen(Integer.parseInt(fields[8]));
                p.setBocwApplicability(Integer.parseInt(fields[9]));
                p.setIsMwApplicability(Integer.parseInt(fields[10]));
                p.setLicenseNumber(fields[11]);
                p.setPfCode(fields[12]);
                p.setWcNumber(fields[13]);
                p.setFactoryLicenseNumber(fields[14]);
                p.setStateNM(stateName);  // Save for display/reference

                Long unitId = fileUploadDao.savePrincipalEmployer(p);  // Save and get unitId

                // Save to CMSPrincipalEmployerState with unitId and stateId
                fileUploadDao.savePEState(unitId, stateId);

                // Prepare and add to success list
                Map<String, Object> success = new LinkedHashMap<>();
                success.put("organization", p.getOrganization());
                success.put("code", p.getCode());
                success.put("name", p.getName());
                success.put("address", p.getAddress());
                success.put("managerName", p.getManagerName());
                success.put("managerAddrs", p.getManagerAddrs());
                success.put("businessType", p.getBusinessType());
                success.put("maxWorkmen", p.getMaxWorkmen());
                success.put("maxCntrWorkmen", p.getMaxCntrWorkmen());
                success.put("bocwApplicability", p.getBocwApplicability());
                success.put("isMwApplicability", p.getIsMwApplicability());
                success.put("licenseNumber", p.getLicenseNumber());
                success.put("pfCode", p.getPfCode());
                success.put("wcNumber", p.getWcNumber());
                success.put("factoryLicenseNumber", p.getFactoryLicenseNumber());
                success.put("stateNM", stateName);

                successData.add(success);

            } catch (Exception e) {
                errorData.add(Map.of("row", rowNum, "error", "Exception while processing row: " + e.getMessage()));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("successData", successData);
        result.put("errorData", errorData);
        return result;
    }



   
    private Map<String, Object> processContractor(BufferedReader reader) throws IOException {
        List<Map<String, Object>> successData = new ArrayList<>();
        List<Map<String, Object>> errorData = new ArrayList<>();
        String line;
        int rowNum = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String[] fieldNames = {
            "contractorName",        // 0
            "contractorAddress",     // 1
            "city",                  // 2
            "managerNm",             // 3
            "licenseNumber",         // 4
            "licenseValidFrom",      // 5
            "licenseValidTo",        // 6
            "coverage",              // 7
            "totalStrength",         // 8
            "maxNoEmp",              // 9
            "natureofWork",          // 10
            "locationofWork",        // 11
            "periodStartDt",         // 12
            "periodEndDt",           // 13
            "contractorId",          // 14
            "pfCode",                // 15
            "wcCode",                // 16
            "wcFromDtm",             // 17
            "wcToDtm",               // 18
            "wcTotal",               // 19
            "pfNum",                 // 20
            "pfApplyDt",             // 21
            "reference",             // 22
            "mobileNumber",          // 23
            "esiwc",                 // 24
            "esiValidFrom",          // 25
            "esiValidTo",            // 26
            "contractorCode",        // 27
            "workOrderNumber",       // 28
            "plantCode",             // 29
            "organization"           // 30
        };

        while ((line = reader.readLine()) != null) {
            rowNum++;
            line = line.replaceAll("[\\x00-\\x1F\\x7F]", "");

            if (line.trim().isEmpty()) continue;

            String[] rawFields = line.split(",", -1);
            String[] fields = new String[rawFields.length];
            for (int i = 0; i < rawFields.length; i++) {
                fields[i] = rawFields[i].trim().replaceAll("\"", "");
            }

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            if (fields.length < 31) {
                errorData.add(Map.of("row", rowNum, "error", "Insufficient number of fields"));
                continue;
            }

            for (int i = 0; i < fieldNames.length; i++) {
                if (fields[i].isBlank()) {
                    fieldErrors.put(fieldNames[i], " is Mandatory");
                }
            }

            if (!fieldErrors.isEmpty()) {
                errorData.add(Map.of("row", rowNum, "fieldErrors", fieldErrors));
                continue;
            }

            try {
                // Step 1: Fetch unitId from CMSPrincipalEmployer
                String plantCode = fields[3];
                String organization = fields[28];
                String contractorCode = fields[29];
                
                Long unitId = fileUploadDao.getUnitIdByPlantCodeAndOrg(plantCode, organization);
                if (unitId == null) {
                    errorData.add(Map.of(
                        "row", rowNum,
                        "error", "No unitId found for plantCode: " + plantCode + " and organization: " + organization
                    ));
                    continue;
                }
             // Duplicate check for code (contractorCode)
                if (fileUploadDao.isContractorCodeExists(contractorCode)) {
                    errorData.add(Map.of(
                        "row", rowNum,
                        "error", "Duplicate contractorCode: " + contractorCode + " already exists"
                    ));
                    continue;
                }
                
                // Step 2: Save Contractor
                Contractor contractor = new Contractor();
                contractor.setContractorName(fields[0]);
                contractor.setContractorAddress(fields[1]);
                contractor.setContractorCode(contractorCode);
                contractor.setReference(fields[23]);
                contractor.setCity(fields[2]);
                contractor.setMobileNumber(Long.parseLong(fields[24]));
                Long contractorId = fileUploadDao.saveContractor(contractor);

                // Step 3: Save PEMM (with unitId)
                CMSContrPemm pemm = new CMSContrPemm();
                pemm.setContractorId(contractorId);
                pemm.setUnitId(unitId); // ✅ Save unitId here first
                pemm.setManagerNm(fields[4]);
                pemm.setLicenseNumber(fields[5]);
                pemm.setLicenseValidFrom(dateFormat.parse(fields[6]));
                pemm.setLicenseValidTo(dateFormat.parse(fields[7]));
                pemm.setCoverage(fields[8]);
                pemm.setTotalStrength(Integer.parseInt(fields[9]));
                pemm.setMaxNoEmp(Integer.parseInt(fields[10]));
                pemm.setNatureofWork(fields[11]);
                pemm.setLocationofWork(fields[12]);
                pemm.setPeriodStartDt(dateFormat.parse(fields[13]));
                pemm.setPeriodEndDt(dateFormat.parse(fields[14]));
                pemm.setPfCode(fields[16]);
                pemm.setPfNum(fields[21]);
                pemm.setPfApplyDt(dateFormat.parse(fields[22]));
                pemm.setEsiwc(fields[25]);
                pemm.setEsiValidFrom(dateFormat.parse(fields[26]));
                pemm.setEsiValidTo(dateFormat.parse(fields[27]));
                fileUploadDao.savePemm(pemm);

                // Step 4: Save SubContractor
                CMSSubContractor csc = new CMSSubContractor();
                csc.setUnitId(String.valueOf(unitId));
                csc.setContractorId(fields[15]);
                csc.setWorkOrderNumber(fields[30]);
                fileUploadDao.savecsc(csc);

                // Step 5: Save WC with same unitId
                CmsContractorWC wc = new CmsContractorWC();
                wc.setContractorId(String.valueOf(contractorId));
                wc.setUnitId(String.valueOf(unitId)); // ✅ Use same unitId again
                wc.setWcCode(fields[17]);
                wc.setWcFromDtm(fields[18]);
                wc.setWcToDtm(fields[19]);
                wc.setWcTotal(Integer.parseInt(fields[20]));
                fileUploadDao.savewc(wc);

                Map<String, Object> map = new HashMap<>();
                map.put("contractorName", contractor.getContractorName());
                map.put("contractorAddress", contractor.getContractorAddress());
                map.put("city", contractor.getCity());
                map.put("managerNm", pemm.getManagerNm());
                map.put("licenseNumber", pemm.getLicenseNumber());
                map.put("licenseValidFrom", pemm.getLicenseValidFrom());
                map.put("licenseValidTo", pemm.getLicenseValidTo());
                map.put("coverage", pemm.getCoverage());
                map.put("totalStrength", pemm.getTotalStrength());
                map.put("maxNoEmp", pemm.getMaxNoEmp());
                map.put("natureofWork", pemm.getNatureofWork());
                map.put("locationofWork", pemm.getLocationofWork());
                map.put("periodStartDt", pemm.getPeriodStartDt());
                map.put("periodEndDt", pemm.getPeriodEndDt());
                map.put("contractorId", csc.getContractorId());
                map.put("pfCode", pemm.getPfCode());
                map.put("wcCode", wc.getWcCode());
                map.put("wcFromDtm", wc.getWcFromDtm());
                map.put("wcToDtm", wc.getWcToDtm());
                map.put("wcTotal", wc.getWcTotal());
                map.put("pfNum", pemm.getPfNum());
                map.put("pfApplyDt", pemm.getPfApplyDt());
                map.put("reference", contractor.getReference());
                map.put("mobileNumber", contractor.getMobileNumber());
                map.put("esiwc", pemm.getEsiwc());
                map.put("esiValidFrom", pemm.getEsiValidFrom());
                map.put("esiValidTo", pemm.getEsiValidTo());
                map.put("contractorCode", contractor.getContractorCode());
                map.put("workOrderNumber", csc.getWorkOrderNumber());
                map.put("plantCode", plantCode);
                map.put("organization", organization);

                successData.add(map);

            } catch (Exception e) {
                errorData.add(Map.of("row", rowNum, "error", "Exception while processing row: " + e.getMessage()));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("successData", successData);
        result.put("errorData", errorData);
        return result;
    }


    
    private Map<String, Object> processWorkorder(BufferedReader reader) throws IOException {
        List<Map<String, Object>> successData = new ArrayList<>();
        List<Map<String, Object>> errorData = new ArrayList<>();
        String line;
        int rowNum = 0; // Header is assumed to be line 0

        // List of expected field names in order (length must match expected columns = 19)
        String[] fieldNames = {
            "sapWorkorderNumber", // 0
            "itemNum",            // 1
            "shortName",          // 2
            "deliveryCompletion", // 3
            "changedon",          // 4
            "validFrom",          // 5
            "validTo",            // 6
            "sapType",            // 7
            "glCode",             // 8
            "costCenter",         // 9
            "job",                // 10
            "rate",               // 11
            "quantity",                // 12
            "pmOrderNum",         // 13
            "wbsElement",         // 14
            "qtyCompleted",       // 15
            "releasedDate",       // 16
            "seCreatedOn",        // 17
            "seUpdatedOn"         // 18
        };

        while ((line = reader.readLine()) != null) {
            rowNum++;
            line = line.replaceAll("[\\x00-\\x1F\\x7F]", "");
            if (line.trim().isEmpty()) continue;

            String[] rawFields = line.split(",", -1);
            String[] fields = new String[rawFields.length];
            for (int i = 0; i < rawFields.length; i++) {
                fields[i] = rawFields[i].trim().replaceAll("\"", "");
            }

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            if (fields.length < 19) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "error", "Insufficient number of fields"
                ));
                continue;
            }

            // ❗ Check for mandatory fields using friendly names
            for (int i = 0; i < fieldNames.length; i++) {
                if (fields[i].isBlank()) {
                    fieldErrors.put(fieldNames[i],  " is Mandatory");
                }
            }

            if (!fieldErrors.isEmpty()) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "fieldErrors", fieldErrors
                ));
                continue;
            }

            try {
                // === Step 1: Contractor ===
                Workorder workorder = new Workorder();
                try {
                    workorder.setSapWorkorderNumber(fields[0]);
                } catch (Exception e) {
                    fieldErrors.put("sapWorkorderNumber", e.getMessage());
                }
                try {
                    workorder.setValidFrom(fields[5]);
                } catch (Exception e) {
                    fieldErrors.put("validFrom", e.getMessage());
                }
                try {
                    workorder.setValidTo(fields[6]);
                } catch (Exception e) {
                    fieldErrors.put("validTo", e.getMessage());
                }
                try {
                    workorder.setGlCode(fields[8]);
                } catch (Exception e) {
                    fieldErrors.put("glCode", e.getMessage());
                }
                try {
                    workorder.setCostCenter(fields[9]);
                } catch (Exception e) {
                    fieldErrors.put("costCenter", e.getMessage());
                }
                try {
                    workorder.setReleasedDate(fields[16]);
                } catch (Exception e) {
                    fieldErrors.put("releasedDate", e.getMessage());
                }

                Long workorderId = fileUploadDao.saveWorkorder(workorder);
                workorder.setWorkorderId(String.valueOf(workorderId));

                // === Step 2: Workorder LN ===
                CMSWorkorderLN woln = new CMSWorkorderLN();
                try {
                    woln.setWorkorderid(workorderId);
                    woln.setItemNum(Integer.parseInt(fields[1]));
                    woln.setDeliveryCompletion(fields[3]);
                    woln.setChangedon(fields[4]);
                    woln.setJob(fields[10]);
                    woln.setRate(Integer.parseInt(fields[11]));
                    woln.setQty(Integer.parseInt(fields[12]));
                    woln.setPmOrderNum(fields[13]);
                    woln.setWbsElement(fields[14]);
                    woln.setQtyCompleted(Long.parseLong(fields[15]));
                    woln.setSeCreatedOn(fields[17]);
                    woln.setSeUpdatedOn(fields[18]);
                } catch (Exception e) {
                    fieldErrors.put("workorderLN", e.getMessage());
                }

                fileUploadDao.saveWorkorderLN(woln);

                ContractorWorkorderTYP wotyp = new ContractorWorkorderTYP();
                try {
                    wotyp.setSapType(fields[7]);
                    wotyp.setShortName(fields[2]);
                } catch (Exception e) {
                    fieldErrors.put("workorderTYP", e.getMessage());
                }

                fileUploadDao.saveWorkorderTyp(wotyp);

                if (!fieldErrors.isEmpty()) {
                    errorData.add(Map.of(
                        "row", rowNum,
                        "fieldErrors", fieldErrors
                    ));
                    continue;
                }

                // Add to success data
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("sapWorkorderNumber", workorder.getSapWorkorderNumber());
                map.put("itemNum", woln.getItemNum());
                map.put("shortName", wotyp.getShortName());
                map.put("deliveryCompletion", woln.getDeliveryCompletion());
                map.put("changedon", woln.getChangedon());
                map.put("validFrom", workorder.getValidFrom());
                map.put("validTo", workorder.getValidTo());
                map.put("sapType", wotyp.getSapType());
                map.put("glCode", workorder.getGlCode());
                map.put("costCenter", workorder.getCostCenter());
                map.put("job", woln.getJob());
                map.put("rate", woln.getRate());
                map.put("qty", woln.getQty());
                map.put("pmOrderNum", woln.getPmOrderNum());
                map.put("wbsElement", woln.getWbsElement());
                map.put("qtyCompleted", woln.getQtyCompleted());
                map.put("releasedDate", workorder.getReleasedDate());
                map.put("seCreatedOn", woln.getSeCreatedOn());
                map.put("seUpdatedOn", woln.getSeUpdatedOn());

                successData.add(map);

            } catch (Exception e) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "error", "Exception while processing row: " + e.getMessage()
                ));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("successData", successData);
        result.put("errorData", errorData);
        return result;
    }

	
	@Override
    public String getTemplateCSV(String templateType) {
        return fileUploadDao.getCSVHeaders(templateType);
    }

	
   }

