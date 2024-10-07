package com.wfd.dot1.cwfm.enums;

public enum GatePassType {

	CREATE("1","CREATE"),
	RENEW("2","RENEW"),
	EXPAT("3","EXPAT"),
	BLOCK("4","BLOCK"),
	UNBLOCK("5","UNBLOCK"),
	BLACKLIST("6","BLACKLIST"),
	DEBLACKLIST("7","DEBLACKLIST"),
	LOSTORDAMAGE("8","LOST OR DAMAGE"),
	CANCEL("9","CANCEL");
	
	private String status;
	private String name;
	private GatePassType(String status, String name) {
		this.status = status;
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
