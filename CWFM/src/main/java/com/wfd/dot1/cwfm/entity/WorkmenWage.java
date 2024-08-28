package com.wfd.dot1.cwfm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CMS_WORKMEN_WAGE_MASTER")
public class WorkmenWage {
	public WorkmenWage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private String WORKMEN_ID;
	   
		public String getWORKMEN_ID() {
		return WORKMEN_ID;
	}
	public void setWORKMEN_ID(String wORKMEN_ID) {
		WORKMEN_ID = wORKMEN_ID;
	}

		private String INSURANCE_TYPE; 
	    private String WAGE_TYPE;
	    private String WKMEN_CATOGERY;
	    private String BASICPAY;
	    private String DA;
	    private String HRA;
	    private String Other_Allowances;
	    private String Uniform_Allowance;
	    private String Washing_allowance;
	    private String Statutory_Bonus;
	    private String Leave_Encashment;
	    private String EX_Serviceman_Allowance;
	    private String Supervisor_Allowance;
	    private String Hardship_Allowance;
	    private String Gunman_Allowance;
	    private String Technical_Allowance;
	    private String Driver_Allowance;
	    private String QRT_Allowance;
	    private String VALID_FROM;
	    private String VALID_TO;
	    private String ELIG_STATE;
	    private String ACTIVE_STATUS;
	    private String RECORD_UPDATED;
	    private String PF_CAP;
	    @Transient
	    private String UNIQID;
	    private String BonusPayout;
	    private String PF;
		public String getINSURANCE_TYPE() {
			return INSURANCE_TYPE;
		}
		public void setINSURANCE_TYPE(String iNSURANCE_TYPE) {
			INSURANCE_TYPE = iNSURANCE_TYPE;
		}
		public String getWAGE_TYPE() {
			return WAGE_TYPE;
		}
		public void setWAGE_TYPE(String wAGE_TYPE) {
			WAGE_TYPE = wAGE_TYPE;
		}
		public String getWKMEN_CATOGERY() {
			return WKMEN_CATOGERY;
		}
		public void setWKMEN_CATOGERY(String wKMEN_CATOGERY) {
			WKMEN_CATOGERY = wKMEN_CATOGERY;
		}
		public String getBASICPAY() {
			return BASICPAY;
		}
		public void setBASICPAY(String bASICPAY) {
			BASICPAY = bASICPAY;
		}
		public String getDA() {
			return DA;
		}
		public void setDA(String dA) {
			DA = dA;
		}
		public String getHRA() {
			return HRA;
		}
		public void setHRA(String hRA) {
			HRA = hRA;
		}
		public String getOther_Allowances() {
			return Other_Allowances;
		}
		public void setOther_Allowances(String other_Allowances) {
			Other_Allowances = other_Allowances;
		}
		public String getUniform_Allowance() {
			return Uniform_Allowance;
		}
		public void setUniform_Allowance(String uniform_Allowance) {
			Uniform_Allowance = uniform_Allowance;
		}
		public String getWashing_allowance() {
			return Washing_allowance;
		}
		public void setWashing_allowance(String washing_allowance) {
			Washing_allowance = washing_allowance;
		}
		public String getStatutory_Bonus() {
			return Statutory_Bonus;
		}
		public void setStatutory_Bonus(String statutory_Bonus) {
			Statutory_Bonus = statutory_Bonus;
		}
		public String getLeave_Encashment() {
			return Leave_Encashment;
		}
		public void setLeave_Encashment(String leave_Encashment) {
			Leave_Encashment = leave_Encashment;
		}
		public String getEX_Serviceman_Allowance() {
			return EX_Serviceman_Allowance;
		}
		public void setEX_Serviceman_Allowance(String eX_Serviceman_Allowance) {
			EX_Serviceman_Allowance = eX_Serviceman_Allowance;
		}
		public String getSupervisor_Allowance() {
			return Supervisor_Allowance;
		}
		public void setSupervisor_Allowance(String supervisor_Allowance) {
			Supervisor_Allowance = supervisor_Allowance;
		}
		public String getHardship_Allowance() {
			return Hardship_Allowance;
		}
		public void setHardship_Allowance(String hardship_Allowance) {
			Hardship_Allowance = hardship_Allowance;
		}
		public String getGunman_Allowance() {
			return Gunman_Allowance;
		}
		public void setGunman_Allowance(String gunman_Allowance) {
			Gunman_Allowance = gunman_Allowance;
		}
		public String getTechnical_Allowance() {
			return Technical_Allowance;
		}
		public void setTechnical_Allowance(String technical_Allowance) {
			Technical_Allowance = technical_Allowance;
		}
		public String getDriver_Allowance() {
			return Driver_Allowance;
		}
		public void setDriver_Allowance(String driver_Allowance) {
			Driver_Allowance = driver_Allowance;
		}
		public String getQRT_Allowance() {
			return QRT_Allowance;
		}
		public void setQRT_Allowance(String qRT_Allowance) {
			QRT_Allowance = qRT_Allowance;
		}
		public String getVALID_FROM() {
			return VALID_FROM;
		}
		public void setVALID_FROM(String vALID_FROM) {
			VALID_FROM = vALID_FROM;
		}
		public String getVALID_TO() {
			return VALID_TO;
		}
		public void setVALID_TO(String vALID_TO) {
			VALID_TO = vALID_TO;
		}
		public String getELIG_STATE() {
			return ELIG_STATE;
		}
		public void setELIG_STATE(String eLIG_STATE) {
			ELIG_STATE = eLIG_STATE;
		}
		public String getACTIVE_STATUS() {
			return ACTIVE_STATUS;
		}
		public void setACTIVE_STATUS(String aCTIVE_STATUS) {
			ACTIVE_STATUS = aCTIVE_STATUS;
		}
		public String getRECORD_UPDATED() {
			return RECORD_UPDATED;
		}
		public void setRECORD_UPDATED(String rECORD_UPDATED) {
			RECORD_UPDATED = rECORD_UPDATED;
		}
		public String getPF_CAP() {
			return PF_CAP;
		}
		public void setPF_CAP(String pF_CAP) {
			PF_CAP = pF_CAP;
		}
		public String getUNIQID() {
			return UNIQID;
		}
		public void setUNIQID(String uNIQID) {
			UNIQID = uNIQID;
		}
		public String getBonusPayout() {
			return BonusPayout;
		}
		public void setBonusPayout(String bonusPayout) {
			BonusPayout = bonusPayout;
		}
		public String getPF() {
			return PF;
		}
		public void setPF(String pF) {
			PF = pF;
		}
		
		public WorkmenWage(String WORKMEN_ID, String INSURANCE_TYPE, String WAGE_TYPE, String WKMEN_CATOGERY, String BASICPAY,
			    String DA, String HRA, String Other_Allowances, String Uniform_Allowance, String Washing_allowance,
			    String Statutory_Bonus, String Leave_Encashment, String EX_Serviceman_Allowance, String Supervisor_Allowance,
			    String Hardship_Allowance, String Gunman_Allowance, String Technical_Allowance, String Driver_Allowance,
			    String QRT_Allowance, String VALID_FROM, String VALID_TO, String ELIG_STATE, String ACTIVE_STATUS,
			    String RECORD_UPDATED, String PF_CAP, String UNIQID, String BonusPayout, String PF) {
			        this.WORKMEN_ID = WORKMEN_ID;
			        this.INSURANCE_TYPE = INSURANCE_TYPE;
			        this.WAGE_TYPE = WAGE_TYPE;
			        this.WKMEN_CATOGERY = WKMEN_CATOGERY;
			        this.BASICPAY = BASICPAY;
			        this.DA = DA;
			        this.HRA = HRA;
			        this.Other_Allowances = Other_Allowances;
			        this.Uniform_Allowance = Uniform_Allowance;
			        this.Washing_allowance = Washing_allowance;
			        this.Statutory_Bonus = Statutory_Bonus;
			        this.Leave_Encashment = Leave_Encashment;
			        this.EX_Serviceman_Allowance = EX_Serviceman_Allowance;
			        this.Supervisor_Allowance = Supervisor_Allowance;
			        this.Hardship_Allowance = Hardship_Allowance;
			        this.Gunman_Allowance = Gunman_Allowance;
			        this.Technical_Allowance = Technical_Allowance;
			        this.Driver_Allowance = Driver_Allowance;
			        this.QRT_Allowance = QRT_Allowance;
			        this.VALID_FROM = VALID_FROM;
			        this.VALID_TO = VALID_TO;
			        this.ELIG_STATE = ELIG_STATE;
			        this.ACTIVE_STATUS = ACTIVE_STATUS;
			        this.RECORD_UPDATED = RECORD_UPDATED;
			        this.PF_CAP = PF_CAP;
			        this.UNIQID = UNIQID;
			        this.BonusPayout = BonusPayout;
			        this.PF = PF;
			}

}
