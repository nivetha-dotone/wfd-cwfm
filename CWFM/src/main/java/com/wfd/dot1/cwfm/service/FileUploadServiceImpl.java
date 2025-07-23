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
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.wfd.dot1.cwfm.pojo.KTCWorkorderStaging;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.State;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
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
                savedData = processGeneralMaster(reader);
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
                if (!headerLine.equalsIgnoreCase("Work Order Number,Item,Line,Line Number,Service Code,Short Text,Delivery Completion,Item Changed ON,Vendor Code,Vendor Name,Vendor Address,Blocked Vendor,Work Order Validitiy From,Work Order Validitiy To,Work Order Type,Plant code,Section Code,Department Code,G/L Code,Cost Center,Nature of Job,Rate / Unit,Quantity,Base Unit of Measure,Work Order Released,PM Order No,WBS Element,Qty Completed,Work Order Release Date,Service Entry Created Date,Service Entry Updated Date,Purchase Org Level,Company_code")) {
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
            case "workmenbulkupload":
                if (!headerLine.equalsIgnoreCase("First Name*,Last Name*,Father's Name or Husband's Name*,Date of Birth*,Trade*,Skill*,Nature of Work*,Hazardous Area*,"
                		+ "Aadhar/Id proof number*,Vendor Code*,Gender*,Date of Joining,Department*,Area,Work Order Number*,PF A/C Number,Marital Status*,"
                		+ "Technical Technical/Non Technical*,Academic,Blood Group,Accommodation*,Bank Name Branch,Account Number,"
                		+ "Mobile Number,Emergency Contact Number*,Police verification Date Valid To,Health chekup Date,Access Levels*,ESIC Number,UNIT CODE*,Organization name,"
                		+ "EIC Number*,EC number*,UAN Number,Emergency Contact Person*,Is eligible for PF,SpecializationName,Insurance type,LL number,Address,Zone,IdMark*")) {
                    throw new Exception("File can not upload due to incorrect format.");
                }
                savedData = processworkmenbulkuploaddraft(reader);
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



    private Map<String, Object> processGeneralMaster(BufferedReader reader) throws IOException {
    	 List<Map<String, Object>> successData = new ArrayList<>();
         List<Map<String, Object>> errorData = new ArrayList<>();

         String line;
         int rowNum = 0;
         String[] fieldNames = {
                 "gmName", "gmDescription", "gmTypeId", "isActive", "createdTM","updatedTM","updatedBy"};
                
         Set<String> mandatoryFields = Set.of("gmName", "gmDescription", "gmTypeId");

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

                 if (fields.length < 7) {
                     errorData.add(Map.of("row", rowNum, "error", "Insufficient number of fields"));
                     continue;
                 }

                 // Check mandatory fields
                 for (int i = 0; i < fieldNames.length; i++) {
                     if (mandatoryFields.contains(fieldNames[i]) && fields[i].isBlank()) {
                         fieldErrors.put(fieldNames[i], "is mandatory");
                     }
                 }

                 if (!fieldErrors.isEmpty()) {
                     errorData.add(Map.of("row", rowNum, "fieldErrors", fieldErrors));
                     continue;
                 }
                 try {
                	 String gmName = fields[0];
                     String gmDescription = fields[1];
                     
                	// Duplicate check for code (contractorCode)
                     if (fileUploadDao.isGmNameGmDescriptionExists(gmName,gmDescription)) {
                         errorData.add(Map.of(
                             "row", rowNum,
                             "error", "Duplicate GMName: " + gmName + " and GMDescription: "+ gmDescription +" already exists"
                         ));
                         continue;
                     }
                     
                	 CmsGeneralMaster gm = new CmsGeneralMaster();
                	 gm.setGmName(gmName);
                	 gm.setGmDescription(gmDescription);
                	 gm.setGmTypeId(Integer.parseInt((fields[2])));
                	 boolean isActive = "1".equals(fields[3].trim());
                	 gm.setCreatedTM(fields[4]);
                	 gm.setUpdatedTM(fields[5]);
                	 gm.setUpdatedBy(fields[6]);
                	 
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
        int rowNum = 0;

        String[] fieldNames = {
            "workOrderNumber", "item", "line", "lineNumber", "serviceCode", "shortText", "deliveryCompletion",
            "itemChangedON", "vendorCode", "vendorName", "vendorAddress", "blockedVendor",
            "workOrderValiditiyFrom", "workOrderValiditiyTo", "workOrderType", "plantcode", "sectionCode",
            "departmentCode", "GLCode", "costCenter", "natureofJob", "rateUnit", "quantity", "baseUnitofMeasure",
            "workOrderReleased", "PMOrderNo", "WBSElement", "qtyCompleted", "workOrderReleaseDate",
            "serviceEntryCreatedDate", "serviceEntryUpdatedDate", "purchaseOrgLevel", "companycode"
        };

        Set<String> mandatoryFields = Set.of(
            "workOrderNumber", "vendorCode", "vendorName", "vendorAddress",
            "workOrderValiditiyFrom", "workOrderValiditiyTo",
            "plantcode", "sectionCode"
        );

        //Set<String> numericFields = Set.of("rateUnit", "quantity", "qtyCompleted");

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

            if (fields.length < fieldNames.length) {
                errorData.add(Map.of("row", rowNum, "error", "Insufficient number of fields"));
                continue;
            }

            // Check mandatory fields
            for (int i = 0; i < fieldNames.length; i++) {
                if (mandatoryFields.contains(fieldNames[i]) && fields[i].isBlank()) {
                    fieldErrors.put(fieldNames[i], "is mandatory");
                }
            }

            // Check numeric fields
           // for (int i = 0; i < fieldNames.length; i++) {
             //   if (numericFields.contains(fieldNames[i]) && !fields[i].isBlank()) {
                //    try {
               //         Double.parseDouble(fields[i]);
               //     } catch (NumberFormatException ex) {
                       // fieldErrors.put(fieldNames[i], "Invalid numeric value");
                //    }
                //}
           // }

            if (!fieldErrors.isEmpty()) {
                errorData.add(Map.of("row", rowNum, "fieldErrors", fieldErrors));
                continue;
            }

            try {
            	 
                KTCWorkorderStaging staging = new KTCWorkorderStaging();
                staging.setWorkOrderNumber(fields[0]);
                staging.setItem(fields[1]);
                staging.setLine(fields[2]);
                staging.setLineNumber(fields[3]);
                staging.setServiceCode(fields[4]);
                staging.setShortText(fields[5]);
                staging.setDeliveryCompletion(fields[6]);
                staging.setItemChangedON(fields[7]);
                staging.setVendorCode(fields[8]);
                staging.setVendorName(fields[9]);
                staging.setVendorAddress(fields[10]);
                staging.setBlockedVendor(fields[11]);
                staging.setWorkOrderValiditiyFrom(fields[12]);
                staging.setWorkOrderValiditiyTo(fields[13]);
                staging.setWorkOrderType(fields[14]);
                staging.setPlantcode(fields[15]);
                staging.setSectionCode(fields[16]);
                staging.setDepartmentCode(fields[17]);
                staging.setGLCode(fields[18]);
                staging.setCostCenter(fields[19]);
                staging.setNatureofJob(fields[20]);
                staging.setRateUnit(fields[21]);
                staging.setQuantity(fields[22]);
                staging.setBaseUnitofMeasure(fields[23]);
                staging.setWorkOrderReleased(fields[24]);
                staging.setPMOrderNo(fields[25]);
                staging.setWBSElement(fields[26]);
                staging.setQtyCompleted(fields[27]);
                staging.setWorkOrderReleaseDate(fields[28]);
                staging.setServiceEntryCreatedDate(fields[29]);
                staging.setServiceEntryUpdatedDate(fields[30]);
                staging.setPurchaseOrgLevel(fields[31]);
                staging.setCompanycode(fields[32]);

                fileUploadDao.saveWorkorderToStaging(staging);

                Map<String, Object> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < fieldNames.length; i++) {
                    rowMap.put(fieldNames[i], fields[i]);
                }
                successData.add(rowMap);

            } catch (Exception e) {
                // fallback if DB insert fails despite validations
               /// Map<String, String> dbFieldErrors = new LinkedHashMap<>();
                //for (String field : numericFields) {
                 //   dbFieldErrors.put(field, "Invalid numeric value");
                //}
                ///errorData.add(Map.of(
                 //   "row", rowNum,
                 //   "error", "-",
                 //   "fieldErrors", dbFieldErrors
               // ));
            	 errorData.add(Map.of("row", rowNum, "error", "Exception while processing row: " + e.getMessage()));
            }
        }

        try {
            fileUploadDao.callWorkorderProcessingSP();
        } catch (Exception e) {
            errorData.add(Map.of("row", "Procedure", "error", "Stored Procedure Failed: " + e.getMessage()));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("successData", successData);
        result.put("errorData", errorData);
        return result;
    }
    private Map<String, Object> processworkmenbulkuploaddraft(BufferedReader reader) throws IOException {
        List<Map<String, Object>> successData = new ArrayList<>();
        List<Map<String, Object>> errorData = new ArrayList<>();

        String line;
        int rowNum = 0;

        String[] fieldNames = {
            "firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork",
            "hazardousArea", "aadhaarNumber", "vendorCode", "gender", "doj", "department", "area",
            "workorderNumber", "pfNumber", "maritalStatus", "technical", "academic",
            "bloodGroup", "accommodation", "bankName", "accountNumber", "mobileNumber", "emergencyNumber", "policeVerificationDate",
            "healthCheckDate", "accessArea", "esicNumber", "unitCode", "organizationName",
            "EICNumber", "ECnumber", "uanNumber", "emergencyName", "pfApplicable", "specializationName", "insuranceType", "LLnumber","address","zone","idMark"
        };

        Set<String> mandatoryFields = Set.of(
            "firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork",
            "hazardousArea", "aadhaarNumber", "vendorCode", "gender", "department", "workorderNumber",
            "maritalStatus", "technical", "accommodation", "accountNumber", "emergencyNumber",
            "accessArea", "unitCode", "EICNumber", "ECnumber", "emergencyName","idMark"
        );
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

            if (fields.length < fieldNames.length) {
                errorData.add(Map.of("row", rowNum, "error", "Insufficient number of fields"));
                continue;
            }

            for (int i = 0; i < fieldNames.length; i++) {
                if (mandatoryFields.contains(fieldNames[i]) && fields[i].isBlank()) {
                    fieldErrors.put(fieldNames[i], "is mandatory");
                }
            }
           
            // Validate Date Format
               String[] dateFields = { fields[3], fields[11], fields[25], fields[26] }; // DOB, DOJ, PoliceVerificationDate, HealthCheckDate
               String[] dateFieldNames = { "dateOfBirth", "doj", "policeVerificationDate", "healthCheckDate" };

               for (int i = 0; i < dateFields.length; i++) {
                   if (!dateFields[i].isBlank()) {
                       try {
                           LocalDate.parse(dateFields[i], dateFormatter);
                       } catch (DateTimeParseException e) {
                           fieldErrors.put(dateFieldNames[i], "Invalid date format, expected yyyy-MM-dd");
                       }
                   }
               }

            // Lookups
            Integer unitId = fileUploadDao.getUnitIdByName(fields[29]);
            Integer contractorId = fileUploadDao.getContractorIdByName( fields[9]);
            Integer tradeId = fileUploadDao.getTradeIdByName(fields[4]);
            Integer skillId = fileUploadDao.getSkillIdByName(fields[5]);
            Integer departmentId = fileUploadDao.getGeneralMasterId(fields[12]);
            Integer accessAreaId = fileUploadDao.getGeneralMasterId(fields[27]);
            Integer bloodGroupId = fileUploadDao.getGeneralMasterId(fields[19]);
            Integer areaId = fileUploadDao.getGeneralMasterId(fields[13]);
            Integer academicId = fileUploadDao.getGeneralMasterId(fields[18]);
            Integer wageCategoryId = fileUploadDao.getWageCategoryId(fields[32]);
            Integer workorderId = fileUploadDao.getWorkorderId(fields[14]);
            Integer zoneId = fileUploadDao.getGeneralMasterId(fields[40]);
            Integer genderId = fileUploadDao.getGeneralMasterId(fields[10]);
            //Integer ecId = fileUploadDao.geteicId(fields[12],unitId,fields[32]);


            if (tradeId == null) fieldErrors.put("trade", "Invalid or not found");
            if (skillId == null) fieldErrors.put("skill", "Invalid or not found");
            if (departmentId == null) fieldErrors.put("department", "Invalid or not found");
            if (accessAreaId == null) fieldErrors.put("accessArea", "Invalid or not found");
            if (bloodGroupId == null) fieldErrors.put("bloodGroup", "Invalid or not found");
            if (areaId == null) fieldErrors.put("area", "Invalid or not found");
            if (academicId == null) fieldErrors.put("academic", "Invalid or not found");
            if (zoneId == null) fieldErrors.put("zone", "Invalid or not found");
            if (genderId == null) fieldErrors.put("Gender", "Invalid or not found");
            //if (ecId == null) fieldErrors.put("EIC", "Invalid or not found");


            if (!fieldErrors.isEmpty()) {
                errorData.add(Map.of("row", rowNum, "fieldErrors", fieldErrors));
                continue;
            }

            try {
                WorkmenBulkUpload staging = new WorkmenBulkUpload();
                staging.setFirstName(fields[0]);
                staging.setLastName(fields[1]);
                staging.setRelationName(fields[2]);
                staging.setDateOfBirth(fields[3]);
                staging.setTrade(String.valueOf(tradeId));               
                staging.setSkill(String.valueOf(skillId));
                staging.setNatureOfWork(fields[6]);
                staging.setHazardousArea(fields[7]);
                staging.setAadhaarNumber(fields[8]);
                staging.setVendorCode(String.valueOf(contractorId));
                staging.setGender(String.valueOf(genderId));
                staging.setDoj(fields[11]);
                staging.setDepartment(String.valueOf(departmentId));
                staging.setArea(String.valueOf(areaId));
                staging.setWorkorderNumber(String.valueOf(workorderId));
                staging.setPfNumber(fields[15]);
                staging.setMaritalStatus(fields[16]);
                staging.setTechnical(fields[17]);
                staging.setAcademic(String.valueOf(academicId));
                staging.setBloodGroup(String.valueOf(bloodGroupId));
                staging.setAccommodation(fields[20]);
                staging.setBankName(fields[21]);
                staging.setAccountNumber(fields[22]);
                staging.setMobileNumber(fields[23]);
                staging.setEmergencyNumber(fields[24]);
                staging.setPoliceVerificationDate(fields[25]);
                staging.setHealthCheckDate(fields[26]);
                staging.setAccessArea(String.valueOf(accessAreaId));
                staging.setEsicNumber(fields[28]);
                staging.setUnitCode(String.valueOf(unitId));
                staging.setOrganizationName(fields[30]);
                staging.setEICNumber(String.valueOf(wageCategoryId));
                staging.setECnumber(fields[32]);
                staging.setUanNumber(fields[33]);
                staging.setEmergencyName(fields[34]);
                staging.setPfApplicable(fields[35]);
                staging.setSpecializationName(fields[36]);
                staging.setInsuranceType(fields[37]);
                staging.setLLnumber(fields[38]);
                staging.setAddress(fields[39]);
                staging.setZone(String.valueOf(zoneId));
                staging.setIdMark(fields[41]);

                fileUploadDao.saveWorkmenBulkUploadDraftToStaging(staging);

                Map<String, Object> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < fieldNames.length; i++) {
                    rowMap.put(fieldNames[i], fields[i]);
                }
                successData.add(rowMap);

            } catch (Exception e) {
                errorData.add(Map.of("row", rowNum, "error", "Exception while processing row: " + e.getMessage()));
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

