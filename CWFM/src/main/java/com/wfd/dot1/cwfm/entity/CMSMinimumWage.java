package com.wfd.dot1.cwfm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "CMSMINIMUMWAGE")
public class CMSMinimumWage {
	public CMSMinimumWage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int minimumWageId;
    private String name;
    private String zone;
    private Date frmDtm;
    private Date toDtm;
    private int tradeId;
    private int skillId;
    private int wageId;
    private int stateId;
    private Short activeFlag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADEID", referencedColumnName = "TRADEID", insertable = false, updatable = false)
    private Trade trade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKILLID", referencedColumnName = "SKILLID", insertable = false, updatable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATEID", referencedColumnName = "STATEID", insertable = false, updatable = false)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAGEID", referencedColumnName = "WAGEID", insertable = false, updatable = false)
    private CMSWage cmsWage;
    
    public Date getFrmDtm() {
		return frmDtm;
	}

	public void setFrmDtm(Date frmDtm) {
		this.frmDtm = frmDtm;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

//	@ManyToOne
//    @JoinColumn(name = "TRADEID")
//    private Trade trade;
//
//    @ManyToOne
//    @JoinColumn(name = "STATEID")
//    private State state;
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

//    private String tradeName;
//    public String getTradeName() {
//		return tradeName;
//	}
//
//	public void setTradeName(String tradeName) {
//		this.tradeName = tradeName;
//	}
//
//	public String getSkillName() {
//		return skillName;
//	}
//
//	public void setSkillName(String skillName) {
//		this.skillName = skillName;
//	}
//
//	public String getStateName() {
//		return stateName;
//	}
//
//	public void setStateName(String stateName) {
//		this.stateName = stateName;
//	}
//
//	private String skillName;
//    private String stateName;
    public int getMinimumWageId() {
        return minimumWageId;
    }

    public void setMinimumWageId(int minimumWageId) {
        this.minimumWageId = minimumWageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Date getFromDtm() {
//        return frmDtm;
//    }
//
//    public void setFromDtm(Date fromDtm) {
//        this.frmDtm = fromDtm;
//    }

    public Date getToDtm() {
        return toDtm;
    }

    public void setToDtm(Date toDtm) {
        this.toDtm = toDtm;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getWageId() {
        return wageId;
    }

    public void setWageId(int wageId) {
        this.wageId = wageId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
    }

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public CMSWage getCmsWage() {
		return cmsWage;
	}

	public void setCmsWage(CMSWage cmsWage) {
		this.cmsWage = cmsWage;
	}
	
	
}

