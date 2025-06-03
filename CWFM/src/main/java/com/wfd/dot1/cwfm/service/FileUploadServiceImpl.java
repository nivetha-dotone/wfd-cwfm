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
                if (!headerLine.equalsIgnoreCase("CONTRACTOR NAME,CONTRACTOR ADDRESS,City,Contractor MANAGER NAME,LICENSE NUM,LICENCSE VALID FROM,LICENCSE VALID TO,"
                        + "LICENCSE COVERAGE,TOTAL STRENGTH,MAXIMUM NUMBER OF WORKMEN,NATURE OF WORK,LOCATION OF WORK,CONTRACTOR VALIDITY START DATE,CONTRACTOR VALIDITY END DATE,"
                        + "CONTRACTOR ID,PF CODE,EC/WC number,EC/WC Validity Start Date,EC/WC Validity End Date,Coverage,PF NUMBER,PF APPLY DATE,Reference,Mobile Number,ESI NUMBER,"
                        + "ESI VALID FROM,ESI VALID TO,Main Contractor Code,Work Order Number")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                 savedData = processContractor(reader);
                break;
            case "workorder":
                if (!headerLine.equalsIgnoreCase("Work Order Number,Item,Short Text,Delivery Complition,Item Changed ON,Work Order Validitiy From,Work Order Validitiy To,Work Order Type,"
                        + "G/L Code,Cost Center,Nature of Job,Rate,Quantity,PM Order No,WBS Element,Quantity Completed,Work Order Release Date,Service Entry Created Date,Service Entry Updated Date")) {
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
                        + "ISMWAPPLICABILITY,LICENSENUMBER,PFCODE,ESWC,FACTORY LICENSE NUMBER")) {
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

        while ((line = reader.readLine()) != null) {
            rowNum++;

            line = line.replaceAll("[\\x00-\\x1F\\x7F]", ""); // Clean control chars

            if (line.trim().isEmpty()) {
                continue; // Skip blank lines
            }

            String[] rawFields = line.split(",", -1);
            String[] fields = new String[rawFields.length];
            for (int i = 0; i < rawFields.length; i++) {
                fields[i] = rawFields[i].trim().replaceAll("\"", "");
            }

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            if (fields.length < 15) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "error", "Insufficient columns. Expected at least 15."
                ));
                continue;
            }

            try {
                // Read individual fields
                String organization = fields[0];
                String code = fields[1];
                String name = fields[2];
                String address = fields[3];
                String managerName = fields[4];
                String managerAddrs = fields[5];
                String businessType = fields[6];
                String maxWorkmenStr = fields[7];
                String maxCntrWorkmenStr = fields[8];
                String bocwApplicabilityStr = fields[9];
                String isMwApplicabilityStr = fields[10];
                String licenseNumber = fields[11];
                String pfCode = fields[12];
                String wcNumber = fields[13];
                String factoryLicenseNumber = fields[14];

                // Validate required fields
                if (organization.isEmpty()) fieldErrors.put("organization", "is required");
                if (code.isEmpty()) fieldErrors.put("code", "is required");
                if (name.isEmpty()) fieldErrors.put("name", "is required");
                if (address.isEmpty()) fieldErrors.put("address", "is required");
                if (managerName.isEmpty()) fieldErrors.put("managerName", "is required");
                if (managerAddrs.isEmpty()) fieldErrors.put("manager Address", "is required");
                if (maxWorkmenStr.isEmpty()) fieldErrors.put("maxWorkmen", "is required");
                if (isMwApplicabilityStr.isEmpty()) fieldErrors.put("isMwApplicability", "is required");
                if (maxCntrWorkmenStr.isEmpty()) fieldErrors.put("maxContractWorkmen", "is required");
                if (bocwApplicabilityStr.isEmpty()) fieldErrors.put("bocwApplicability", "is required");
                if (licenseNumber.isEmpty()) fieldErrors.put("licenseNumber", "is required");
                if (pfCode.isEmpty()) fieldErrors.put("pfCode", "is required");
                if (wcNumber.isEmpty()) fieldErrors.put("wcNumber", "is required");
                if (factoryLicenseNumber.isEmpty()) fieldErrors.put("factoryLicenseNumber", "is required");
               
                int maxWorkmen = 0, maxCntrWorkmen = 0, bocwApplicability = 0, isMwApplicability = 0;

               // try {
                //    maxWorkmen = Integer.parseInt(maxWorkmenStr);
                //    maxCntrWorkmen = Integer.parseInt(maxCntrWorkmenStr);
                //    bocwApplicability = Integer.parseInt(bocwApplicabilityStr);
                //    isMwApplicability = Integer.parseInt(isMwApplicabilityStr);
               // } catch (NumberFormatException e) {
               //     fieldErrors.put("numberParse", "Invalid number format in one of the numeric fields");
               // }

                if (!fieldErrors.isEmpty()) {
                    errorData.add(Map.of(
                        "row", rowNum,
                        "fieldErrors", fieldErrors
                    ));
                    continue;
                }

                // Save entity
                PrincipalEmployer pe = new PrincipalEmployer(
                    organization, code, name, address, managerName, managerAddrs,
                    businessType, maxWorkmen, maxCntrWorkmen, bocwApplicability, isMwApplicability,
                    licenseNumber, pfCode, wcNumber, factoryLicenseNumber
                );
                fileUploadDao.savePrincipalEmployer(pe);

                // Prepare success record
                Map<String, Object> success = new LinkedHashMap<>();
                success.put("organization", organization);
                success.put("code", code);
                success.put("name", name);
                success.put("address", address);
                success.put("managerName", managerName);
                success.put("managerAddrs", managerAddrs);
                success.put("businessType", businessType);
                success.put("maxWorkmen", maxWorkmen);
                success.put("maxCntrWorkmen", maxCntrWorkmen);
                success.put("bocwApplicability", bocwApplicability);
                success.put("isMwApplicability", isMwApplicability);
                success.put("licenseNumber", licenseNumber);
                success.put("pfCode", pfCode);
                success.put("wcNumber", wcNumber);
                success.put("factoryLicenseNumber", factoryLicenseNumber);

                successData.add(success);

            } catch (Exception e) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "error", "Unexpected error: " + e.getMessage()
                ));
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
        // Map column index to field name
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
            "workOrderNumber"        // 28
        };
        while ((line = reader.readLine()) != null) {
            rowNum++;

            line = line.replaceAll("[\\x00-\\x1F\\x7F]", "");

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] rawFields = line.split(",", -1); // Keep empty trailing fields
            String[] fields = new String[rawFields.length];
            for (int i = 0; i < rawFields.length; i++) {
                fields[i] = rawFields[i].trim().replaceAll("\"", "");
            }

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            // Validate field count
            if (fields.length < 29) {
                errorData.add(Map.of(
                    "row", rowNum,
                    "error", "Insufficient number of fields"
                ));
                continue;
            }

            // Check for empty fields
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
                Contractor contractor = new Contractor();
                contractor.setContractorName(fields[0]);
                contractor.setContractorAddress(fields[1]);
                contractor.setContractorCode(fields[27]);
                contractor.setReference(fields[22]);
                contractor.setCity(fields[2]);
                contractor.setMobileNumber(Long.parseLong(fields[23]));

                Long contractorId = fileUploadDao.saveContractor(contractor);
                contractor.setContractorId(String.valueOf(contractorId));

                CMSContrPemm pemm = new CMSContrPemm();
                pemm.setContractorId(contractorId);
                pemm.setManagerNm(fields[3]);
                pemm.setLicenseNumber(fields[4]);
                pemm.setLicenseValidFrom(dateFormat.parse(fields[5]));
                pemm.setLicenseValidTo(dateFormat.parse(fields[6]));
                pemm.setCoverage(fields[7]);
                pemm.setTotalStrength(Integer.parseInt(fields[8]));
                pemm.setMaxNoEmp(Integer.parseInt(fields[9]));
                pemm.setNatureofWork(fields[10]);
                pemm.setLocationofWork(fields[11]);
                pemm.setPeriodStartDt(dateFormat.parse(fields[12]));
                pemm.setPeriodEndDt(dateFormat.parse(fields[13]));
                pemm.setPfCode(fields[15]);
                pemm.setPfNum(fields[20]);
                pemm.setPfApplyDt(dateFormat.parse(fields[21]));
                pemm.setEsiwc(fields[24]);
                pemm.setEsiValidFrom(dateFormat.parse(fields[25]));
                pemm.setEsiValidTo(dateFormat.parse(fields[26]));

                Long unitId = fileUploadDao.savePemm(pemm);
                String unitIdString = String.valueOf(unitId);

                CMSSubContractor csc = new CMSSubContractor();
                csc.setUnitId(unitIdString);
                csc.setContractorId(fields[14]);
                csc.setWorkOrderNumber(fields[28]);
                fileUploadDao.savecsc(csc);

                CmsContractorWC wc = new CmsContractorWC();
                wc.setContractorId(String.valueOf(contractorId));
                wc.setUnitId(unitIdString);
                wc.setWcCode(fields[16]);
                wc.setWcFromDtm(fields[17]);
                wc.setWcToDtm(fields[18]);
                wc.setWcTotal(Integer.parseInt(fields[19]));
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



	
   }

