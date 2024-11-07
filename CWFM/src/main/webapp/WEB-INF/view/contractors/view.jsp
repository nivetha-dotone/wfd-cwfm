<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/jquery.min.js"></script>
<style>
    body {
        margin: 0;
        overflow-x: hidden;
    }

     #principalEmployerContent {
        padding: 20px;
        box-sizing: border-box;
        
        height: calc(100vh - 20px);
    } 
th label {
    text-align: left;
    display: block;
    font-weight: normal; /* Remove bold effect */
}
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }
 .Tabular {
    border-collapse: collapse;
    width: 100%; /* Set table width to 100% of the page */
  }

  .Tabular th {
    background-color: #005151;
    color: white;
    padding: 8px;
    text-align: left;
  }

  .Tabular td {
    padding: 8px;
  }
</style>
<script>  
var contextPath = '<%= request.getContextPath() %>';


</script>
   </head>
<body>

     <!--    <div  style="margin-top:50px;float:right;">
<table  class="Tabular" cellpadding="0" cellspacing="0">
     <tr> <td colspan="6">  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/list','Contractor');">Cancel</button>
     </td></tr></table>
        </div> -->

<div id="principalEmployerContent">


    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Code</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="code" value="${principalEmployer.code}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Organization</label></th>
                            <td><input type="text" name="organization" value="${principalEmployer.organization}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       <td >  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/list','Contractor');">Cancel</button>
     </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Address</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="code" value="${contractor.contractorCode}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                            <td><input type="text" name="name" value="${contractor.contractorName}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td><input type="text" name="managerNm" value="${contractorPEMM.managerNm}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                            <td><input type="text" name="pfNum" value="${contractorPEMM.pfNum}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Email Address</label></th>
                            <td><input type="text" name="managerEmail" value="${contractorPEMM.managerEmail}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Mobile Number</label></th>
                            <td><input type="text" name="managerMobile" value="${contractorPEMM.managerMobile}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>ESIC Registration</label></th>
                            <td><input type="text" name="esicNum" value="${contractorPEMM.esicNum}" style="height: 20px;"  size="30" maxlength="30" /></td>
                            <th><label class="custom-label">Contract Valid Till</label></th>
                            <td><input type="text" name="" value="0" style="height: 20px;"  size="30" maxlength="30" disabled="true" /></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Is RC Verified</label></th>
                            <td>
    <c:choose>
        <c:when test="${contractorPEMM.rcValidated}">
    <input type="checkbox" name="rcValidated" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="rcValidated" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        </tr>
                     <%--    <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                            <td><input type="text" name="LICENSENUMBER" value="${principalEmployer.LICENSENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                            <td><input type="text" name="PFCODE" value="${principalEmployer.PFCODE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>WC Number</label></th>
                            <td><input type="text" name="WCNUMBER" value="${principalEmployer.WCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>ESIC Number</label></th>
                            <td><input type="text" name="ESICNUMBER" value="${principalEmployer.ESICNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>PT Registration No.</label></th>
                            <td><input type="text" name="PTREGNO" value="${principalEmployer.PTREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>LWF Registration No.</label></th>
                            <td><input type="text" name="LWFREGNO" value="${principalEmployer.LWFREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                            <td><input type="text" name="FACTORYLICENCENUMBER" value="${principalEmployer.FACTORYLICENCENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       <th><label class="custom-label"><span class="required-field">*</span>IS RC Applicable ?</label></th>
                           <td>
    <c:choose>
        <c:when test="${principalEmployer.ISRCAPPLICABLE}">
    <input type="checkbox" name="ISRCAPPLICABLE" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="ISRCAPPLICABLE" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        
                        </tr>
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>RC Number</label></th>
                            <td><input type="text" name="RCNUMBER" value="${principalEmployer.RCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Attachment Name</label></th>
                            <td>
                        <c:if test="${not empty principalEmployer.ATTACHMENTNM}">
    <a href="#" onclick="viewFile('/CWFM/principalEmployer/viewFile?unitCode=${principalEmployer.CODE}&fileName=${principalEmployer.ATTACHMENTNM}')">${principalEmployer.ATTACHMENTNM}</a>
</c:if>
                            <input type="text" name="ATTACHMENTNM" value="${principalEmployer.ATTACHMENTNM}" style="height: 20px;"  size="30" maxlength="30" />
                            </td>
                        
                        </tr> --%>
        </tbody>
    </table>
    
<div class="panel second-child">
<table  class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th> Labor License Number</th>
		<th>From Date</th>
		<th>To Date</th> 
		<th>Total</th>
		<th>Active Workmen Count</th>
		</tr> 		
	</thead>
	<tbody >
	<c:forEach items="${laborLicenses}" var="laborLicenses">
                <tr>
                    <td>${laborLicenses.wcCode}</td>
                      <td>${laborLicenses.wcFromDtm}</td>
            <td>${laborLicenses.wcToDtm}</td>
                   <td>${laborLicenses.wcTotal}</td>
            <td></td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>L.C.6/CLRA/Licence/CLRA/GNR/2023/CLL/20 </td>
            <td> 28/9/2023 </td>
            <td> 01/01/2025 </td>
            <td>50</td>
            <td>10</td>
        </tr>
        <tr>
             <td>L.C.6/CLRA/Licence/CLRA/GNR/2024/CLL/20 </td>
            <td> 01/01/2024 </td>
            <td> 01/01/2025 </td>
            <td>70</td>
            <td>10</td>
        </tr> -->
	</tbody>
</table>
</div>


<div class="panel second-child">
<table class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th>WC Policy/ESIC Reg Number <%-- <fmt:message key="label.wc.code" /> --%></th>
		<th>License Type</th>
		<th>Job Name</th>
			<th>From Date</th>
		<th>To Date</th> 
		<th>Total</th>
		<th>Active Workmen Count</th>
		</tr> 		
	</thead>
	<tbody >
	<c:forEach items="${contractorWCList}" var="contractorWCList">
                <tr>
                    <td>${contractorWCList.wcCode}</td>
                     <td>${contractorWCList.licenceType}</td>
                      <td>${contractorWCList.natureOfId}</td>
                      <td>${contractorWCList.wcFromDtm}</td>
            <td>${contractorWCList.wcToDtm}</td>
                   <td>${contractorWCList.wcTotal}</td>
            <td></td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>12312317</td>
            <td>WC</td>
            <td>OPERATION, MECHANICAL, CIVIL, SERVICES </td>
            <td> 28/9/2021 </td>
            <td> 01/01/2025 </td>
            <td>50</td>
            <td>10</td>
        </tr>
        <tr>
             <td>130522127110005000</td>
            <td>ESIC</td>
            <td>Operational Support </td>
            <td> 01/01/2021 </td>
            <td> 01/01/2025 </td>
            <td>70</td>
            <td>10</td>
        </tr>
       <tr>
            <td>123123189</td>
            <td>WC</td>
            <td>OPERATION, MECHANICAL, CIVIL, SERVICES </td>
            <td> 28/9/2022 </td>
            <td> 01/01/2025 </td>
            <td>40</td>
            <td>10</td>
        </tr>
        <tr>
             <td>WERTYUIOPQ1234567</td>
            <td>ESIC</td>
            <td>Operational Support </td>
            <td> 01/01/2023 </td>
            <td> 01/01/2025 </td>
            <td>80</td>
            <td>20</td>
        </tr> -->
	</tbody>
</table>
</div>

<div class="panel second-child">
<table class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th> Workorder Number</th>
			<th>From Date</th>
		<th>To Date</th> 
		<th>Active Workmen Count</th>
		 <th>Contract Classification</th> 
		</tr> 		
	</thead>
	<tbody >
	<c:forEach items="${workOrderList}" var="workOrderList">
                <tr>
                    <td>${workOrderList.sapWorkorderNumber}</td>
                     <td>${workOrderList.validFrom}</td>
                      <td>${workOrderList.validTo}</td>
                      <td></td>
            <td>Manpower Supply</td>
            
                  <%--  <td>${workOrderList.wcTotal}
                   <c:if test="${item.cType == null}">
				&nbsp;
			</c:if>
			<c:if test="${item.cType == '0'}">
				SLA with headcount
			</c:if>
			<c:if test="${item.cType == '1'}">
				Manpower Services
			</c:if>
			<c:if test="${item.cType == '2'}">
				SLA without headcount
			</c:if>
                   </td> --%>
            <td></td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>9876543210</td>
            <td> 28/9/2021 </td>
            <td> 01/01/2025 </td>
            <td>20</td>
            <td>Manpower Services</td>
        </tr>
        <tr>
            <td>9876543210</td>
            <td>01/01/2021 </td>
            <td> 28/4/2025 </td>
            <td>30</td>
            <td>Manpower Services</td>
        </tr> -->
	</tbody>
</table>
</div>
</div>

