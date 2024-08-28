package com.wfd.dot1.cwfm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wfd.dot1.cwfm.dao.CMSPRINCIPALEMPLOYERDAO;
import com.wfd.dot1.cwfm.entity.CMSPrincipalEmployer;

@Component
public class PrincipalEmployerValidator implements Validator {
	@Autowired
	private CMSPRINCIPALEMPLOYERDAO cmSPRINCIPALEMPLOYERDAO;
    @Override
    public boolean supports(Class<?> clazz) {
        return CMSPrincipalEmployer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CMSPrincipalEmployer principalEmployer = (CMSPrincipalEmployer) target;
        System.out.println("Validation method called");
        // Example validation rule
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "NAME", "field.required", "Unit Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "CODE", "field.required", "Unit Code is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ORGANIZATION", "field.required", "Organization is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ADDRESS", "field.required", "Address is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "STATEID", "field.required", "State is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "MANAGERNAME", "field.required", "Manager Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "MANAGERADDRS", "field.required", "Manager Address is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "BUSINESSTYPE", "field.required", "Business Type is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "MAXWORKMEN", "field.required", "Maximum Number of Workmen is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "MAXCNTRWORKMEN", "field.required", "Maximum Number of Contract Workmen is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "LICENSENUMBER", "field.required", "License Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "PFCODE", "field.required", "PF Code is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "WCNUMBER", "field.required", "WC Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ESICNUMBER", "field.required", "ESIC Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "PTREGNO", "field.required", "PT Registration Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "LWFREGNO", "field.required", "LWF Registration Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "FACTORYLICENCENUMBER", "field.required", "Factory License Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "RCNUMBER", "field.required", "RC Number is required");
       // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ATTACHMENTNM", "field.required", "Attachment Name is required");
        CMSPrincipalEmployer existingEmployer = cmSPRINCIPALEMPLOYERDAO.getCMSPRINCIPALEMPLOYERById(principalEmployer.getUNITID());
        System.out.println("Validation method called6666  "+principalEmployer.getUNITID());
        
        if (existingEmployer != null) {
        	 System.out.println("Validation method ert  "+existingEmployer.getUNITID());
        	 System.out.println("Validation method ert  "+existingEmployer.getORGANIZATION());
        	 System.out.println("Validation method ert  "+principalEmployer.getORGANIZATION());
            if (!existingEmployer.getCODE().equals(principalEmployer.getCODE()) && cmSPRINCIPALEMPLOYERDAO.existsByUnitCode(principalEmployer.getCODE())) {
                errors.rejectValue("CODE", "duplicate.CODE", "Unit 1 code already exists");
            } 
            // Check if unit name already exists (only if it's different from the existing value)
            if (!existingEmployer.getNAME().equals(principalEmployer.getNAME()) && cmSPRINCIPALEMPLOYERDAO.existsByUnitName(principalEmployer.getNAME())) {
                errors.rejectValue("NAME", "duplicate.NAME", "Unit name already exists");
            }

            // Check if organization already exists (only if it's different from the existing value)
            if (!existingEmployer.getORGANIZATION().equals(principalEmployer.getORGANIZATION()) && cmSPRINCIPALEMPLOYERDAO.existsByOrganization(principalEmployer.getORGANIZATION())) {
                errors.rejectValue("ORGANIZATION", "duplicate.ORGANIZATION", "Organization already exists");
            }
            if (cmSPRINCIPALEMPLOYERDAO.existsByOrganizationAndNotUnitId(principalEmployer.getORGANIZATION(), principalEmployer.getUNITID())) {
                errors.rejectValue("ORGANIZATION", "duplicate.ORGANIZATION", "Organization already exists for another unit");
            }
            if (cmSPRINCIPALEMPLOYERDAO.existsByUnitCodeAndNotUnitId(principalEmployer.getCODE(), principalEmployer.getUNITID())) {
                errors.rejectValue("CODE", "duplicate.CODE", "Unit code already exists for another unit");
            }

            // Check if unit name already exists for any other unit
            if (cmSPRINCIPALEMPLOYERDAO.existsByUnitNameAndNotUnitId(principalEmployer.getNAME(), principalEmployer.getUNITID())) {
                errors.rejectValue("NAME", "duplicate.NAME", "Unit name already exists for another unit");
            }
        }else {
        	  if (cmSPRINCIPALEMPLOYERDAO.existsByUnitCode(principalEmployer.getCODE())) {
                  errors.rejectValue("CODE", "duplicate.CODE", "Unit code already exists");
              }

              // Check if unitName already exists
              if (cmSPRINCIPALEMPLOYERDAO.existsByUnitName(principalEmployer.getNAME())) {
                  errors.rejectValue("NAME", "duplicate.NAME", "Unit name already exists");
              }

              // Check if organization already exists
              if (cmSPRINCIPALEMPLOYERDAO.existsByOrganization(principalEmployer.getORGANIZATION())) {
                  errors.rejectValue("ORGANIZATION", "duplicate.ORGANIZATION", "Organization already exists");
              }
        }
        
        if ((principalEmployer.getISRCAPPLICABLE() != null && principalEmployer.getISRCAPPLICABLE().booleanValue()) && (principalEmployer.getATTACHMENTNM() == null || principalEmployer.getATTACHMENTNM().isEmpty())) {
            errors.rejectValue("ATTACHMENTNM", "field.required", "Attachment Name is required when RC Verified is checked");
        }
      
        Integer  maxworkmen=principalEmployer.getMAXWORKMEN();
        Integer maxcontworkmen=principalEmployer.getMAXCNTRWORKMEN();
        Integer stateid=principalEmployer.getSTATEID();
        
        if(maxworkmen==0 || maxworkmen==null) {
        	  errors.rejectValue("MAXWORKMEN", "field.required", "Maximum Number of Workmen is required");
        }
        if(maxcontworkmen==0) {
      	  errors.rejectValue("MAXCNTRWORKMEN", "field.required", "Maximum Number of Contract Workmen is required");
      }if(stateid==0 || stateid==-1) {
      	  errors.rejectValue("STATEID", "field.required", "State is required");
      }
        // Add more validation rules as needed
    }
}
