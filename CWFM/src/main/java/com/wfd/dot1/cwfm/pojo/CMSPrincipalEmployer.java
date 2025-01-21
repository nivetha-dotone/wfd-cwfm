package com.wfd.dot1.cwfm.pojo;

import java.util.Date;
public class CMSPrincipalEmployer {
   
	private long UNITID;
	private String NAME;
    private String ADDRESS;
    private String MANAGERNAME;
    private String MANAGERADDRS;
    private String BUSINESSTYPE;
    private int MAXWORKMEN;
    private int MAXCNTRWORKMEN;
    private Boolean  BOCWAPPLICABILITY;
    private Boolean  ISMWAPPLICABILITY;
    private String CODE;
    private String ORGANIZATION;
    private String PFCODE;
    private String LICENSENUMBER;
    private String WCNUMBER;
    private String ESICNUMBER;
    private String PTREGNO;
    private String LWFREGNO;
    private String FACTORYLICENCENUMBER;
    private Boolean  ISRCAPPLICABLE;
    private String RCNUMBER;
    private String ATTACHMENTNM;
    private Integer STATEID;
    private Boolean  ISACTIVE;
    private Date UPDATEDTM;
    private String UPDATEDBY;

//    private MultipartFile attachmentFile;
//
//    public MultipartFile getAttachmentFile() {
//        return attachmentFile;
//    }
//
//    public void setAttachmentFile(MultipartFile attachmentFile) {
//        this.attachmentFile = attachmentFile;
//    }
    // Getters and setters
    public long getUNITID() {
        return UNITID;
    }

    public void setUNITID(long UNITID) {
        this.UNITID = UNITID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getMANAGERNAME() {
        return MANAGERNAME;
    }

    public void setMANAGERNAME(String MANAGERNAME) {
        this.MANAGERNAME = MANAGERNAME;
    }

    public String getMANAGERADDRS() {
        return MANAGERADDRS;
    }

    public void setMANAGERADDRS(String MANAGERADDRS) {
        this.MANAGERADDRS = MANAGERADDRS;
    }

    public String getBUSINESSTYPE() {
        return BUSINESSTYPE;
    }

    public void setBUSINESSTYPE(String BUSINESSTYPE) {
        this.BUSINESSTYPE = BUSINESSTYPE;
    }

    public int getMAXWORKMEN() {
        return MAXWORKMEN;
    }

    public void setMAXWORKMEN(int MAXWORKMEN) {
        this.MAXWORKMEN = MAXWORKMEN;
    }

    public int getMAXCNTRWORKMEN() {
        return MAXCNTRWORKMEN;
    }

    public void setMAXCNTRWORKMEN(int MAXCNTRWORKMEN) {
        this.MAXCNTRWORKMEN = MAXCNTRWORKMEN;
    }

    public Boolean  getBOCWAPPLICABILITY() {
        return BOCWAPPLICABILITY;
    }

    public void setBOCWAPPLICABILITY(Boolean  BOCWAPPLICABILITY) {
        this.BOCWAPPLICABILITY = BOCWAPPLICABILITY;
    }

    public Boolean  getISMWAPPLICABILITY() {
        return ISMWAPPLICABILITY;
    }

    public void setISMWAPPLICABILITY(Boolean  ISMWAPPLICABILITY) {
        this.ISMWAPPLICABILITY = ISMWAPPLICABILITY;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getORGANIZATION() {
        return ORGANIZATION;
    }

    public void setORGANIZATION(String ORGANIZATION) {
        this.ORGANIZATION = ORGANIZATION;
    }

    public String getPFCODE() {
        return PFCODE;
    }

    public void setPFCODE(String PFCODE) {
        this.PFCODE = PFCODE;
    }

    public String getLICENSENUMBER() {
        return LICENSENUMBER;
    }

    public void setLICENSENUMBER(String LICENSENUMBER) {
        this.LICENSENUMBER = LICENSENUMBER;
    }

    public String getWCNUMBER() {
        return WCNUMBER;
    }

    public void setWCNUMBER(String WCNUMBER) {
        this.WCNUMBER = WCNUMBER;
    }

    public String getESICNUMBER() {
        return ESICNUMBER;
    }

    public void setESICNUMBER(String ESICNUMBER) {
        this.ESICNUMBER = ESICNUMBER;
    }

    public String getPTREGNO() {
        return PTREGNO;
    }

    public void setPTREGNO(String PTREGNO) {
        this.PTREGNO = PTREGNO;
    }

    public String getLWFREGNO() {
        return LWFREGNO;
    }

    public void setLWFREGNO(String LWFREGNO) {
        this.LWFREGNO = LWFREGNO;
    }

    public String getFACTORYLICENCENUMBER() {
        return FACTORYLICENCENUMBER;
    }

    public void setFACTORYLICENCENUMBER(String FACTORYLICENCENUMBER) {
        this.FACTORYLICENCENUMBER = FACTORYLICENCENUMBER;
    }

    public Boolean getISRCAPPLICABLE() {
        return ISRCAPPLICABLE;
    }

    public void setISRCAPPLICABLE(Boolean ISRCAPPLICABLE) {
        this.ISRCAPPLICABLE = ISRCAPPLICABLE;
    }

    public String getRCNUMBER() {
        return RCNUMBER;
    }

    public void setRCNUMBER(String RCNUMBER) {
        this.RCNUMBER = RCNUMBER;
    }

    public String getATTACHMENTNM() {
        return ATTACHMENTNM;
    }

    public void setATTACHMENTNM(String ATTACHMENTNM) {
        this.ATTACHMENTNM = ATTACHMENTNM;
    }

    public Integer getSTATEID() {
        return STATEID;
    }

    public void setSTATEID(Integer STATEID) {
        this.STATEID = STATEID;
    }

    public Boolean  getISACTIVE() {
        return ISACTIVE;
    }

    public void setISACTIVE(Boolean  ISACTIVE) {
        this.ISACTIVE = ISACTIVE;
    }

    public Date getUPDATEDTM() {
        return UPDATEDTM;
    }

    public void setUPDATEDTM(Date UPDATEDTM) {
        this.UPDATEDTM = UPDATEDTM;
    }

    public String getUPDATEDBY() {
        return UPDATEDBY;
    }

    public void setUPDATEDBY(String UPDATEDBY) {
        this.UPDATEDBY = UPDATEDBY;
    }
    
    public CMSPrincipalEmployer(long uNITID, String nAME, String aDDRESS, String mANAGERNAME, String mANAGERADDRS,
			String bUSINESSTYPE, int mAXWORKMEN, int mAXCNTRWORKMEN, Boolean  bOCWAPPLICABILITY, Boolean  iSMWAPPLICABILITY,
			String cODE, String oRGANIZATION, String pFCODE, String lICENSENUMBER, String wCNUMBER, String eSICNUMBER,
			String pTREGNO, String lWFREGNO, String fACTORYLICENCENUMBER, Boolean iSRCAPPLICABLE, String rCNUMBER,
			String aTTACHMENTNM, Integer sTATEID, Boolean  iSACTIVE, Date uPDATEDTM, String uPDATEDBY) {
		super();
		UNITID = uNITID;
		NAME = nAME;
		ADDRESS = aDDRESS;
		MANAGERNAME = mANAGERNAME;
		MANAGERADDRS = mANAGERADDRS;
		BUSINESSTYPE = bUSINESSTYPE;
		MAXWORKMEN = mAXWORKMEN;
		MAXCNTRWORKMEN = mAXCNTRWORKMEN;
		BOCWAPPLICABILITY = bOCWAPPLICABILITY;
		ISMWAPPLICABILITY = iSMWAPPLICABILITY;
		CODE = cODE;
		ORGANIZATION = oRGANIZATION;
		PFCODE = pFCODE;
		LICENSENUMBER = lICENSENUMBER;
		WCNUMBER = wCNUMBER;
		ESICNUMBER = eSICNUMBER;
		PTREGNO = pTREGNO;
		LWFREGNO = lWFREGNO;
		FACTORYLICENCENUMBER = fACTORYLICENCENUMBER;
		ISRCAPPLICABLE = iSRCAPPLICABLE;
		RCNUMBER = rCNUMBER;
		ATTACHMENTNM = aTTACHMENTNM;
		STATEID = sTATEID;
		ISACTIVE = iSACTIVE;
		UPDATEDTM = uPDATEDTM;
		UPDATEDBY = uPDATEDBY;
	}
	 public CMSPrincipalEmployer() {
		super();
		// TODO Auto-generated constructor stub
	}
}

