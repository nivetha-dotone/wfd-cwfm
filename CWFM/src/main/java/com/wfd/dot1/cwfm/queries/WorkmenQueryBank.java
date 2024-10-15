package com.wfd.dot1.cwfm.queries;

public interface WorkmenQueryBank {

	String GET_ALL_PRINCIPAL_EMPLOYER="select cpe.UNITID, cpe.NAME from USERAUTHRELINFO uari "
			+ " join CMSPRINCIPALEMPLOYER cpe on uari.AuthorizationValue=cpe.UNITID and uari.IsActive='A' "
			+ " where uari.UserId=? and uari.AuthorizationOn='unitId'";
	
	String GET_ALL_CONTRACTOR_BY_PE="SELECT ccp.contractorid,cc.NAME as contractorname "
			+ " FROM CMSCONTRPEMM ccp "
			+ " JOIN CMSPRINCIPALEMPLOYER cpe ON ccp.unitid = cpe.unitid "
			+ " JOIN USERAUTHRELINFO uari ON uari.AuthorizationValue = ccp.contractorid AND uari.IsActive='A' "
			+ " JOIN CMSCONTRACTOR cc ON cc.CONTRACTORID=ccp.CONTRACTORID "
			+ " WHERE cpe.unitid =? "
			+ " AND uari.UserId=? AND uari.AuthorizationOn = 'contractor'";
	
	String GET_ALL_WORKORDER_BY_PE_AND_CONT="select cwo.WORKORDERID,cwo.NAME from CMSWORKORDER cwo "
			+ " where UNITID=? and CONTRACTORID=? "
			+ " union "
			+ " select cwo.WORKORDERID,cwo.NAME from CMSWORKORDER cwo "
			+ " join CMSSUBCONTRACTOR csc on csc.WORKORDERID= cwo.WORKORDERID "
			+ " where csc.SUBCONTRACTORID=?";
	
	String GET_ALL_TRADES_BY_PE="select ct.TRADEID,ct.NAME  from CMSTRADE ct where ct.UNITID=?";
	
	String GET_ALL_SKILL ="select cs.SKILLID,cs.SKILLNM,cs.ISACTIVE from CMSSKILL cs";
	
	String GET_ALL_DEPT_AND_SUBDEPT="select cgm.GMID,cgm.GMNAME,uari.AuthorizationOn from USERAUTHRELINFO uari "
			+ " join CMSGENERALMASTER cgm ON cgm.GMID=uari.AuthorizationValue and uari.IsActive='A' "
			+ " join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID "
			+ " where cgt.GMTYPE in ('AREA','DEPARTMENT') and uari.AuthorizationOn in ('subdepartment','department') and uari.UserId=?";

	String GET_ALL_EIC="SELECT mu.UserId,	 "
			+ "    CONCAT(mu.FirstName, ' ',mu.LastName) AS FullName "
			+ " FROM USERAUTHRELINFO uari "
			+ "    join MASTERUSER mu ON uari.AuthorizationValue=mu.UserId and uari.IsActive='A' "
			+ "	where uari.UserId=? and uari.AuthorizationOn='eic'";
	
	String GET_ALL_WC="select ccwc.WCID,ccwc.WC_CODE from CMSCONTRACTOR_WC ccwc where ccwc.UNITID=? and ccwc.CONTRACTORID=?  AND ccwc.WC_TO_DTM > GETDATE()";

	String GET_ALL_GENDER=" select cgm.GMID,cgm.GMNAME, cgt.GMTYPE from CMSGENERALMASTER cgm "
			+ " join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID "
			+ " where cgt.GMTYPE='GENDER'";
	
	String GET_ALL_CMSGENERALMASTER="select cgm.GMID,cgm.GMNAME, cgt.GMTYPE from CMSGENERALMASTER cgm "
			+ " join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID ";
	
	 String GET_MAX_GATEPASS_ID = "SELECT COALESCE(MAX(GatePassId), 9100000000) + 1 AS newGatepassId FROM GATEPASSMAIN";
	   
	 String SAVE_CONTRACT_WORKMEN = " INSERT INTO GATEPASSMAIN "
	 		+ " (GatePassId, GatePassTypeId, GatePassStatus, AadharNumber, FirstName, LastName, DOB, Gender, RelativeName,  "
	 		+ " IdMark, MobileNumber , MaritalStatus , UnitId, ContractorId, WorkorderId, TradeId, SkillId, DepartmentId, AreaId, EicId, NatureOfJob, WcEsicNo, HazardousArea,   "
	 		+ " AccessAreaId, UanNumber, HealthCheckDate, BloodGroupId, Accommodation, AcademicId, Technical, IfscCode, AccountNumber,   "
	 		+ " EmergencyContactName,EmergencyContactNumber, WorkmenWageCategoryId, BonusPayoutId, PfCap,ZoneId, Basic, DA, HRA, WashingAllowance, OtherAllowance, UniformAllowance,   "
	 		+ " AadharDocName, PhotoName, BankDocName, PoliceVerificationDocName, IdProof2DocName, MedicalDocName, EducationDocName, Form11DocName,   "
	 		+ " TrainingDocName, OtherDocName,WorkFlowType,Comments, UpdatedBy, UpdatedDate)   "
	 		+ " VALUES  "
	 		+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	 		+ "			  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	 		+ "			  ?, ?, ?, ?, ?, ?, ?, ?, ?,?, "
	 		+ "			  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	 		+ "			  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  "
	 		+ "			  ?, ?, ?, ?, ?,?,?, GETDATE())";
	 
	 String GET_ALL_GATE_PASS_FOR_CREATOR = "select gpm.TransactionId,gpm.GatePassId,gpm.GatePassTypeId,gpm.FirstName, "
	 		+ " gpm.LastName,cgm.GMNAME,gpm.DOB,gpm.AadharNumber, cc.NAME as ContractorName, "
	 		+ " gpm.ContractorId as VendorCode,cpe.NAME as UnitName,gpm.GatePassStatus "
	 		+ " from GATEPASSMAIN gpm "
	 		+ " JOIN CMSCONTRACTOR cc ON cc.CONTRACTORID=gpm.ContractorId "
	 		+ " join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=gpm.UnitId "
	 		+ " join CMSGENERALMASTER cgm on cgm.GMID = TRY_CAST(gpm.Gender AS BIGINT)"
	 		+ " where gpm.UpdatedBy=?";
	 
	 String GET_ALL_GATE_PASS_FOR_PARALLEL_APPROVER = "select gpm.TransactionId,gpm.GatePassId,gpm.GatePassTypeId,gpm.FirstName, "
		 		+ " gpm.LastName,cgm.GMNAME,gpm.DOB,gpm.AadharNumber, cc.NAME as ContractorName, "
		 		+ " gpm.ContractorId as VendorCode,cpe.NAME as UnitName,gpm.GatePassStatus "
		 		+ " from GATEPASSMAIN gpm "
		 		+ " JOIN CMSCONTRACTOR cc ON cc.CONTRACTORID=gpm.ContractorId "
		 		+ " join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=gpm.UnitId "
		 		+ " join CMSGENERALMASTER cgm on cgm.GMID = TRY_CAST(gpm.Gender AS BIGINT)"
		 		+ "  join GATEPASSAPPROVERINFO gai on gai.GatePassId = gpm.GatePassId "
		 		+ " left join  GATEPASSAPPROVALSTATUS gas ON gas.GatePassId = gpm.GatePassId AND gas.UserId = ?"
		 		+ " where gai.UserId=? and gas.GatePassId IS NULL and gpm.GatePassStatus='3' ";
	 
	 String GET_ALL_GATE_PASS_FOR_SEQUENTIAL_APPROVER="select gpm.TransactionId,gpm.GatePassId,gpm.GatePassTypeId,gpm.FirstName,  "
	 		+ " gpm.LastName,cgm.GMNAME,gpm.DOB,gpm.AadharNumber, cc.NAME as ContractorName,  "
	 		+ " gpm.ContractorId as VendorCode,cpe.NAME as UnitName,gpm.GatePassStatus  "
	 		+ " from GATEPASSMAIN gpm  "
	 		+ " JOIN CMSCONTRACTOR cc ON cc.CONTRACTORID=gpm.ContractorId  "
	 		+ " join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=gpm.UnitId  "
	 		+ " join CMSGENERALMASTER cgm on cgm.GMID = TRY_CAST(gpm.Gender AS BIGINT) "
	 		+ " left join GATEPASSAPPROVERINFO gai on gai.gatePassId = gpm.gatePassId "
	 		+ " and gai.userId=? and gai.status=1 "
	 		+ " and gai.[Index] = (select count(gas.GatePassApprovalStatusId)+1 from GATEPASSAPPROVERINFO gai1 "
	 		+ " join GATEPASSAPPROVALSTATUS gas on gas.UserId=gai1.UserId and gas.status=4 and gpm.gatePassId=gas.gatePassId "
	 		+ " where gai1.status in (1) and gai1.gatePassId=gpm.gatePassId) "
	 		+ " where gai.UserId=? and  gpm.GatePassStatus='3'";
	 
	
	 
	 String GET_CONTRACT_WORKMEN_DETAILS = "SELECT TransactionId,GatePassId,GatePassTypeId,GatePassStatus,AadharNumber,gpm.FirstName,gpm.LastName,DOB,Gender, "
	 		+ " RelativeName,IdMark,MobileNumber,MaritalStatus "
	 		+ " ,cpe.NAME as UnitId,cc.NAME as ContractorId,cwo.NAME as WorkorderId,ct.NAME as TradeId,cs.SKILLNM AS SkillId,DepartmentId,AreaId, "
	 		+ " CONCAT(mu.FirstName,' ',mu.LastName) as EicId,NatureOfJob,ccwc.WC_CODE as WcEsicNo,HazardousArea, "
	 		+ " AccessAreaId,UanNumber,HealthCheckDate "
	 		+ " ,BloodGroupId,Accommodation,AcademicId,Technical,IfscCode,AccountNumber,EmergencyContactNumber,EmergencyContactName, "
	 		+ " WorkmenWageCategoryId,BonusPayoutId "
	 		+ " ,ZoneId,Basic,DA,HRA,WashingAllowance,OtherAllowance,UniformAllowance,PfCap,AadharDocName,PhotoName,BankDocName, "
	 		+ " PoliceVerificationDocName,IdProof2DocName "
	 		+ " ,MedicalDocName,EducationDocName,Form11DocName,TrainingDocName,OtherDocName,UpdatedDate,gpm.UpdatedBy,gpm.Comments   "
	 		+ " FROM GATEPASSMAIN gpm "
	 		+ " join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID  = gpm.UnitId "
	 		+ " JOIN CMSCONTRACTOR cc ON cc.CONTRACTORID=gpm.ContractorId "
	 		+ " join CMSWORKORDER cwo ON cwo.WORKORDERID = gpm.WorkorderId "
	 		+ " join CMSTRADE ct on ct.TRADEID = gpm.TradeId "
	 		+ " JOIN CMSSKILL cs on cs.SKILLID = gpm.SkillId "
	 		+ " join MASTERUSER mu on mu.UserId = gpm.EicId "
	 		+ " join CMSCONTRACTOR_WC ccwc on ccwc.WCID = gpm.WcEsicNo "
	 		+ " where  gpm.GatePassId=?";
	 
	 String GET_ALL_CMSGENERALMASTER_FOR_GATE_PASS="select cgm.GMID,cgm.GMNAME, cgt.GMTYPE from CMSGENERALMASTER cgm "
				+ " join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID "
				+ " where cgm.GMID in (?,?,?,?,?,?,?,?,?)";
	 
	 String GET_WORKFLOW_TYPE_BY_PE ="select distinct gpwft.WorkflowType "
	 		+ " from CMSPRINCIPALEMPLOYER CPE  "
	 		+ " join GATEPASSWORKFLOWTYPE gpwft on gpwft.BusinessTypeId = cpe.BUSINESSTYPE "
	 		+ " WHERE CPE.UNITID=?";
	 
	 String GET_APPROVERS_FOR_GATE_PASS="select mu.UserId, CONCAT(mu.FirstName, ' ',mu.LastName) AS FullName,uari.AuthorizationOn "
	 		+ " from MASTERUSER mu  "
	 		+ " join USERAUTHRELINFO uari on uari.AuthorizationValue=mu.UserId and uari.IsActive='A'  "
	 		+ " WHERE uari.UserId=? ";
	 
	 String SAVE_GATE_PASS_APPROVER="INSERT INTO GATEPASSAPPROVERINFO(GatePassId,UserRole,UserId,[Index],Status,CreatedBy,CreatedDate) VALUES (?,?, ?, ?, ?,?,GETDATE())";

	String SAVE_GATEPASS_APPROVAL_STATUS = "INSERT INTO GATEPASSAPPROVALSTATUS(GatePassId,UserId,UserRole,Status,Comments,LastUpdatedDate) VALUES (?,?,?,?,?,GETDATE())";
	
	 String GET_WORKFLOW_TYPE_BY_BT ="select distinct gpwft.WorkflowType "
		 		+ " from  GATEPASSWORKFLOWTYPE gpwft  WHERE gpwft.BusinessTypeId=?";
	 
	 String UPDATE_GATEPASSMAIN_STATUS="update GATEPASSMAIN SET GatePassStatus=? where GatePassId=?";
	 
	 String LAST_APPROVER = "select UserId from GATEPASSAPPROVERINFO gai where gai.gatePassId=? "
	 		+ " and gai.UserId=? "
	 		+ " and gai.status=1 and gai.[index] = (select max(gai1.[index]) "
	 		+ " from GATEPASSAPPROVERINFO gai1 where gai1.gatePassId=gai.gatePassId and gai1.status=1) ";
}
