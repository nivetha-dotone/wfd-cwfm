package com.wfd.dot1.cwfm.queries;

public interface PrincipalEmployerQueryBank {

	String GET_ALL_PES=" select UNITID,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY, "
			+ " CODE,ORGANIZATION,PFCODE,LICENSENUMBER,WCNUMBER,ESICNUMBER,PTREGNO,LWFREGNO,FACTORYLICENCENUMBER,ISRCAPPLICABLE,RCNUMBER,ATTACHMENTNM,STATEID,ISACTIVE"
			+ " from CMSPRINCIPALEMPLOYER";
	
	String GET_PE_BY_UNITID=" select UNITID,NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY, "
			+ " CODE,ORGANIZATION,PFCODE,LICENSENUMBER,WCNUMBER,ESICNUMBER,PTREGNO,LWFREGNO,FACTORYLICENCENUMBER,ISRCAPPLICABLE,RCNUMBER,ATTACHMENTNM,STATEID,ISACTIVE"
			+ " from CMSPRINCIPALEMPLOYER WHERE UNITID=?";
}
