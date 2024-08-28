<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
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
        /* overflow-y: auto; */
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

    /* Add spacing between <td> elements */
    /* td {
        padding-bottom: 10px; /* Adjust the value as needed */
    } */
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}
.required-field {
    color: red; /* Change this to your desired color */
    margin-right: 4px; 
}
        .page-header {
            background-color: #005151;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
        }

        .header-buttons {
            float: right;
          /*   margin-top: -40px; */
            margin-right: 10px;
        }
        .error-message {
    color: red;
    font-family: Arial, sans-serif;
    font-size: 12px;
}
 .error {
    background-color: #ffcccc; /* Light red background */
} 
</style>
<script>  
var contextPath = '<%= request.getContextPath() %>';
/* var id = extractIdFromURL(); // Assuming you have a function to extract the id
function extractIdFromURL() {alert(1)
    var url = window.location.href;
    var lastSlashIndex = url.lastIndexOf('/');
    var id = url.substring(lastSlashIndex + 1);
    return id; alert(id);
} */
/* onsubmit="return validateForm()" */

</script>
   </head>
<body>
 <div class="page-header">
        Bill Verification
        <div class="header-buttons">
            <button type="submit" >Save</button>
        </div>
    </div>
    <div id="errorContainer"></div>
<div id="principalEmployerContent">
<f:form id="addPEForm"  >
     <div id="errorContainer">
<%--       <c:out value="${errorsLength}" /> --%>
      <c:if test="${not empty errors}">
      <p><span class="required-field">*</span> Indicates Required fields.</p>
        <div class="error-message">
             <f:errors value="*" cssClass="error" element="div" />
        </div> 
    </c:if>
    </div>
   
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                         <td>TransactionID</td>
<td>
     <div style="padding-right: 15px;">
        <input type="text" id="transacationid" value="6727389231"  style="width: calc(100% - 10px); height: 20px;" size="30" maxlength="30"/>
       <%--  <f:errors value="NAME" cssClass="error-message"/> --%>
    </div>
</td>
                        </tr>
                        <tr>
                            <td>Unit Code</td>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="unitCode" value="CODE001" readonly="true" disabled="true"  style="width: calc(100% - 10px); height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <td>Unit Name</td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text" value="DOT1 Solutions Pvt Ltd"   style="width: calc(100% - 10px); height: 20px;" readonly="true" disabled="true" size="30" maxlength="30" />
                             </div></td>
                       
                        </tr>
                        <tr>
                            <td>Vendor Code</td>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="address" value="C001"   style="width: calc(100% - 10px); height: 20px;" readonly="true" disabled="true" size="30" maxlength="30" />
                              </div></td>
                         <td>Contractor Name</td>
                        <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="address" value="Contractor 1"   style="width: calc(100% - 10px); height: 20px;" readonly="true" disabled="true" size="30" maxlength="30" />
                              </div></td>
                        </tr>
                        <tr>
                            <td>Bill Start Date <span class="required-field">*</span></td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text" value=""  style="width: calc(100% - 10px); height: 20px;" placeholder="DD/MM/YYYY" size="30" maxlength="30" />
                             </div></td>
                        <td>Bill End Date <span class="required-field">*</span></td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text" value=""  style="width: calc(100% - 10px); height: 20px;" placeholder="DD/MM/YYYY" size="30" maxlength="30" />
                             </div></td>
                       
                        </tr>
                        
                        <tr>
                            <td>Work Order Code <span class="required-field">*</span></td>
                            <td>  <div style="padding-right: 15px;">  <select name="pe"  style="width: calc(103% - 10px); height: 20px;"   >
   <option value="">Please select Workorder</option>
</select>
</div></td>
 <td>Nature of Job</td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text" value=""  style="width: calc(100% - 10px); height: 20px;"  size="30" maxlength="30" />
                             </div></td>
                        
                        </tr>
                        <tr>
                            <td>Work Order Valid From</td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text"  value="" readonly="true" disabled="true"  style="width: calc(100% - 10px); height: 20px;"  size="30" maxlength="30" />
                             </div></td>
                            <td>Work Order Valid To</td>
                            <td>
                             <div style="padding-right: 15px;">
                             <input type="text"  value=""  style="width: calc(100% - 10px); height: 20px;"  size="30" maxlength="30" readonly="true"  disabled="true" />
                             </div></td>
                        </tr>
                        <tr>
                            <td>Bill Category <span class="required-field">*</span></td>
                           <td>    <div style="padding-right: 15px;">
                           <select name="pe" style="width: calc(103% - 10px); height: 20px;"    >
   <option value="">Please select Bill Category</option>
</select>
</div>
</td>
<td>Bill Type</td>
                           <td> 
                            <div style="padding-right: 15px;">
                              <select name="pe" style="width: calc(103% - 10px); height: 20px;"   >
   <option value="">Please select Bill Type</option>
</select>
</div></td>
                        </tr>
                        <tr>
    <td><label for="khtmlNameInput">Engineering-in-Charge <span style="color:red"> *</span></label></td>
    <td>
     <div style="padding-right: 15px;">
        <select id="ListBox1" name="ListBox11" multiple="multiple" onchange="validateFunction();" style="width: calc(103% - 10px); height: 150px;" >
            <c:forEach var="eic" items="${availEicNames}">
                <option <c:if test="${eic != eicName}"></c:if> value="<c:out value="${eic}"/>" <c:if test="${eic == eicName}">selected</c:if>><c:out value="${eic}" /></option>
            </c:forEach>
        </select>
        </div>
    </td>
    <td style="text-align: left;">
        <ul style="list-style-type:none; padding-top:3px;padding-left:75px;">
            <br></br>
            <li><input type="button" onclick="Add();validateFunction();" value=">" style="width: 100px; height: 20px;background-color:#005151;color: white;" /> </li> </br>
            <li><input type="button" onclick="AddAll();validateFunction();" value=">>" style="width: 100px; height: 20px;background-color:#005151;color: white;" /> </li></br>
            <li><input type="button" onclick="Remove();validateFunction();" style="width: 100px; height: 20px;background-color:#005151;color: white;" value="<" /></li></br>
            <li><input type="button" onclick="RemoveAll();validateFunction();" style="width: 100px; height: 20px;background-color:#005151;color: white;" value="<<" /></li></br> 
        </ul> 
    </td>
    <td>
     <div style="padding-right: 15px;">
        <select id="ListBox2" name="ListBox22" multiple="multiple" style="width: calc(103% - 10px); height: 150px;" >
            <c:forEach var="eic2" items="${selEicNames}">
                <option <c:if test="${eic2 != eicName}"></c:if> value="<c:out value="${eic2}"/>" <c:if test="${eic2 == eicName}">selected</c:if>><c:out value="${eic2}" /></option>
            </c:forEach> 
        </select>
        </div>
        </li>
    </td>
</tr>

                        
               					
<tr> <%-- <fmt:message key="label.Documents" /> --%>
				<td><label for="khtmlNameInput"><span><font color="#005151" size="2"><b>Kronos Reports :</b></font></span></label></td>
			</tr>
			
			
			 <tr>  
				<td><span></span> Standardize Muster roll Report</td>
		        <td><input type="file" name="theFile[1]" accept="application/pdf,application/vnd.ms-excel"/></td>
		         <td><span></span> Bill verification Abstract Report</td>
		        <td ><input type="file" name="theFile[2]" accept="application/pdf,application/vnd.ms-excel"/></td>
		    
			</tr>
			 
			<tr>    
		        <td><span class="Required"> Wage Cost Report</span></td>
		        <td><input type="file" name="theFile[3]" accept="application/pdf,application/vnd.ms-excel"/></td>
		        <td><span class="Required"> Bonus Report</span></td>
		        <td><input type="file" name="theFile[20]" accept="application/pdf,application/vnd.ms-excel"/></td>
      		 
			</tr>
			
			<tr>    
		        <td><span class="Required"> Extra Hours Report</span></td>
		        <td><input type="file" name="theFile[21]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
		    <tr> 
				<td><label for="khtmlNameInput"><span><font color="#005151" size="2"><b>Statutory Regulatory Attachments :</b></font></span></label></td>
			</tr>
			
			<tr>
				<td><span class="Required"> Form A</span></td>
		        <td><input type="file" name="theFile[4]" accept="application/pdf,application/vnd.ms-excel"/></td>
		         <td ><span class="Required"> Form B</span></td>
		        <td ><input type="file" name="theFile[5]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>	
			
      		
      		 <tr >
      		 <td ><span class="Required"> Form C</span></td>
		        <td ><input type="file" name="theFile[6]" accept="application/pdf,application/vnd.ms-excel"/></td>
				<td><span class="Required"> Form D</span></td>
		        <td><input type="file" name="theFile[7]" accept="application/pdf,application/vnd.ms-excel"/></td>
		    </tr>
		    
		    <tr>    
		        <td><span class="Required"> ECR PF</span></td>
		        <td><input type="file" name="theFile[8]" accept="application/pdf,application/vnd.ms-excel"/></td>
				<td><span class="Required"> Challan and Copy of Remittance PF</span></td>
		        <td><input type="file" name="theFile[9]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
			<tr >
				<td><span class="Required"> ECR ESIC</span></td>
		        <td><input type="file" name="theFile[10]" accept="application/pdf,application/vnd.ms-excel"/></td>
		         <td><span class="Required"> Challan and Copy of remittance ESIC</span></td>
		        <td><input type="file" name="theFile[11]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
      		<tr >    
		        <td><span class="Required"> Bank Statement</span></td>
		        <td><input type="file" name="theFile[12]" accept="application/pdf,application/vnd.ms-excel"/></td>
 				<td><span class="Required"> Annual Return</span></td>
		        <td><input type="file" name="theFile[13]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
			<tr >    
		        <td><span class="Required"> Bonus register</span></td>
		        <td><input type="file" name="theFile[14]" accept="application/pdf,application/vnd.ms-excel"/></td>
		        <td><span class="Required"> LWF Challan and Remittance</span></td>
		        <td><input type="file" name="theFile[15]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			<tr>    
		        <td><span class="Required"> Challan and copy of remittance PT</span></td>
		        <td><input type="file" name="theFile[16]" accept="application/pdf,application/vnd.ms-excel"/></td>
		        <td><span class="Required"> User Attachment 1</span></td>
		        <td><input type="file" name="theFile[17]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
			<tr>    
		        <td><span class="Required"> User Attachment 2</span></td>
		        <td><input type="file" name="theFile[18]" accept="application/pdf,application/vnd.ms-excel"/></td>
		        <td><span class="Required"> User Attachment 3</span></td>
		        <td><input type="file" name="theFile[19]" accept="application/pdf,application/vnd.ms-excel"/></td>
			</tr>
			
      		         
                  	<tr> <%-- <fmt:message key="label.Documents" /> --%>
				<td><label for="khtmlNameInput"><span><font color="#005151" size="2"><b>Check List - HR Clearance :</b></font></span></label></td>
			</tr>
						
 <table  class="ControlLayout" cellpadding="0" cellspacing="0">
 <thead> 
 <tr>
    <td style=" border-top: thin solid; border-left: thin solid;border-bottom: thin solid;border-right: thin solid;" style="text-align: left;"  colspan="5"><b>Part A - Statutory Compliance Status</b></td>
    
  </tr>
</thead> 

	 <tbody> 
  <tr>
    <td style=" border-left: thin solid;border-bottom: thin solid;border-right: thin solid;border-top: thin solid;"><b>S.N</b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid;"><b>Check Points</b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid;"><b>Status Y/N</b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid; text-align: left;"><b>Licence No./ Policy No/ Code No.</b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid;"><b>Date of Compliance /valid upto </b></td>
   <!--  <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid;width:8%;"><b>Action Plan</b></td> -->
    <!--  <td style=" border-top: thin solid; border-bottom: thin solid;border-right: thin solid;width:12%;"><b>Date</b></td> -->
  </tr>
     <%String LLStatusID = (String) request.getAttribute("LLStatusID");
    
     String EmpRegID = (String) request.getAttribute("EmpRegID");
     String WageRegID = (String) request.getAttribute("WageRegID");
     String LoanRecoveryID = (String) request.getAttribute("LoanRecoveryID");
     String AttenRegID = (String) request.getAttribute("AttenRegID");
     String AnnualReturnID = (String) request.getAttribute("AnnualReturnID");
     String PFSlipID = (String) request.getAttribute("PFSlipID");
     String PFEcrID = (String) request.getAttribute("PFEcrID");
     String ESICSlipID = (String) request.getAttribute("ESICSlipID");
     String ESICEcrID = (String) request.getAttribute("ESICEcrID");
     String PTaxChallanID = (String) request.getAttribute("PTaxChallanID");
     String AccidentPolicyID = (String) request.getAttribute("AccidentPolicyID");
   /*   String AccidentPolicyValidToID = (String) request.getAttribute("AccidentPolicyValidToID"); */
   String LaborWelfareID = (String) request.getAttribute("LaborWelfareID");
     String BonusRegID = (String) request.getAttribute("BonusRegID");
     String LeaveWihtWagesID = (String) request.getAttribute("LeaveWihtWagesID");
     String BankStmntStatusID = (String) request.getAttribute("BankStmntStatusID");
     String PreMonWageID = (String) request.getAttribute("PreMonWageID");
     
     %>
   <tr class="Even">
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >1</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Licence copy obtained by Vendor under Contract Labour Act 1970.</td>
    			<td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: 100px;">
					<select id="value(LLStatus)"  name="value(LLStatus)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${LLStatus == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != LLStatusID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == LLStatusID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != LLStatusID}"></c:if> value="No" <c:if test="${'No' == LLStatusID}">selected</c:if>>No</option>
						<option  <c:if test="${'Yes' != LLStatusID}"></c:if> value="Yes" <c:if test="${'Yes' == LLStatusID}">selected</c:if>>Yes</option>
					</select>
				</td> 
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" > <c:out value='${LLPolicy}' /> <!-- <label for="khtmlNameInput" property="value(LLPolicy)" ></label> -->  </td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" > <c:out value='${LLValidTo}' /> <%-- <html:text property="value(LLValidTo)"    /> --%></td>
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
   
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >2</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Employee Register ( Form  A ) Rule 2(I)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(EmpReg)"  name="value(EmpReg)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${EmpReg == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != EmpRegID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == EmpRegID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != EmpRegID}"></c:if> value="No" <c:if test="${'No' == EmpRegID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != EmpRegID}"></c:if> value="Yes" <c:if test="${'Yes' == EmpRegID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >- <%-- <c:out value='${WCPolicy}' />  --%> </td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >- <%-- <c:out value='${WCValidTo}' /> --%> <%-- <html:text property="value(WCValidTo)"    /> --%></td>
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >3</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Wages Register (Form B, Under Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(WageReg)"  name="value(WageReg)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${WageReg == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != WageRegID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == WageRegID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != WageRegID}"></c:if> value="No" <c:if test="${'No' == WageRegID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != WageRegID}"></c:if> value="Yes" <c:if test="${'Yes' == WageRegID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >- <%-- <c:out value='${PFCode}' /> --%> <%-- <html:text property="value(PFCode)"    /> --%></td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >4</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Register of Loan/ Recovery ( Form  C, Under  Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(LoanRecovery)"  name="value(LoanRecovery)" style="height:20px;">
					<%-- 	<option   value="No"  <c:if test="${LoanRecovery == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != LoanRecoveryID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == LoanRecoveryID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != LoanRecoveryID}"></c:if> value="No" <c:if test="${'No' == LoanRecoveryID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != LoanRecoveryID}"></c:if> value="Yes" <c:if test="${'Yes' == LoanRecoveryID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >- <%-- <c:out value='${ESICCode}' /> --%> <%-- <html:text property="value(ESICCode)"    /> --%></td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >5</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Attendance Register  (Form D  Under Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(AttenReg)"  name="value(AttenReg)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AttenReg == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != AttenRegID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == AttenRegID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != AttenRegID}"></c:if> value="No" <c:if test="${'No' == AttenRegID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != AttenRegID}"></c:if> value="Yes" <c:if test="${'Yes' == AttenRegID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid;border-bottom: thin solid;border-right: thin solid;text-align: center;" >6</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;white-space: normal;" >Unified Annual Return under Contract Labour Act Rules 1971( In the month of December every year)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(AnnualReturn)"  name="value(AnnualReturn)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AnnualReturn == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != AnnualReturnID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == AnnualReturnID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != AnnualReturnID}"></c:if> value="No" <c:if test="${'No' == AnnualReturnID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != AnnualReturnID}"></c:if> value="Yes" <c:if test="${'Yes' == AnnualReturnID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >7</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Challan & Remittance Confirmation Slip of PF</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(PFSlip)"  name="value(PFSlip)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${PFSlip == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != PFSlipID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == PFSlipID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != PFSlipID}"></c:if> value="No" <c:if test="${'No' == PFSlipID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != PFSlipID}"></c:if> value="Yes" <c:if test="${'Yes' == PFSlipID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(PFSlipDate)"
								name="value(PFSlipDate)" onchange="setDataChanged(true);" onkeypress="return isNumberKey(event)"
								style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" maxlength="10"  value="<c:out value="${PFSlipD}"/>"/><!--  <b> DD/MM/YYYY</b> --></td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid;border-bottom: thin solid;border-right: thin solid;text-align: center;" >8</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >ECR copy of PF</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(PFEcr)"  name="value(PFEcr)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${PFEcr == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != PFEcrID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == PFEcrID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != PFEcrID}"></c:if> value="No" <c:if test="${'No' == PFEcrID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != PFEcrID}"></c:if> value="Yes" <c:if test="${'Yes' == PFEcrID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(PFECRDate)"
								name="value(PFECRDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								maxlength="10" style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${PFECRD}"/>"/>  <!-- <b> DD/MM/YYYY</b> --></td>	
  <!--   <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
    <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >9</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Challan & Remittance Confirmation Slip of ESIC</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(ESICSlip)"  name="value(ESICSlip)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${ESICSlip == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != ESICSlipID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == ESICSlipID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != ESICSlipID}"></c:if> value="No" <c:if test="${'No' == ESICSlipID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != ESICSlipID}"></c:if> value="Yes" <c:if test="${'Yes' == ESICSlipID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(ESICSlipDate)"
								name="value(ESICSlipDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								maxlength="10" style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${ESICSlipD}"/>"/>  <!-- <b> DD/MM/YYYY</b> --></td>	
  <!--   <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >10</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >ECR copy of ESIC</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(ESICEcr)"  name="value(ESICEcr)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${ESICEcr == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != ESICEcrID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == ESICEcrID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != ESICEcrID}"></c:if> value="No" <c:if test="${'No' == ESICEcrID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != ESICEcrID}"></c:if> value="Yes" <c:if test="${'Yes' == ESICEcrID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(ESICECRDate)"
								name="value(ESICECRDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								maxlength="10" style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${ESICECRD}"/>"/> <!--  <b> DD/MM/YYYY</b> --></td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
 <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >11</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Bank Statement of wages paid to workmen submitted</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;"><select id="value(BankStmntStatus)"  name="value(BankStmntStatus)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AccidentPolicyValidTo == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != BankStmntStatusID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == BankStmntStatusID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != BankStmntStatusID}"></c:if> value="No" <c:if test="${'No' == BankStmntStatusID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != BankStmntStatusID}"></c:if> value="Yes" <c:if test="${'Yes' == BankStmntStatusID}">selected</c:if>>Yes</option>
						
					</select></td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">-</td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >12</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Challan Copy of Professional Tax</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(PTaxChallan)"  name="value(PTaxChallan)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${PTaxChallan == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != PTaxChallanID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == PTaxChallanID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != PTaxChallanID}"></c:if> value="No" <c:if test="${'No' == PTaxChallanID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != PTaxChallanID}"></c:if> value="Yes" <c:if test="${'Yes' == PTaxChallanID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(PTaxDate)"
								name="value(PTaxDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								 maxlength="10" style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${PTaxD}"/>"/> <!-- <b> DD/MM/YYYY</b> --></td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid;border-bottom: thin solid;border-right: thin solid;text-align: center;" >13</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;white-space: normal;" >Accident Policy under Workmen Compensation Act ,in case of workers drawing salary more than Rs 21,000/= Gross Per Month</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(AccidentPolicy)"  name="value(AccidentPolicy)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AccidentPolicy == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != AccidentPolicyID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == AccidentPolicyID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != AccidentPolicyID}"></c:if> value="No" <c:if test="${'No' == AccidentPolicyID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != AccidentPolicyID}"></c:if> value="Yes" <c:if test="${'Yes' == AccidentPolicyID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
 <!--    <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
   <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >14</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Labour Welfare Fund Act (Only in the Month of June & December every year)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(LaborWelfare)"  name="value(LaborWelfare)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AccidentPolicyValidTo == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != LaborWelfareID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == LaborWelfareID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != LaborWelfareID}"></c:if> value="No" <c:if test="${'No' == LaborWelfareID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != LaborWelfareID}"></c:if> value="Yes" <c:if test="${'Yes' == LaborWelfareID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(LabWelFundActDate)"
								name="value(LabWelFundActDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								maxlength="10"  style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${LabWelFundActD}"/>"/> <!--  <b> DD/MM/YYYY</b> --></td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >15</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Bonus Register Form C  Rule 4(c ) ( Under Bonus Act 1965)</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(BonusReg)"  name="value(BonusReg)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AccidentPolicyValidTo == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != BonusRegID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == BonusRegID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != BonusRegID}"></c:if> value="No" <c:if test="${'No' == BonusRegID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != BonusRegID}"></c:if> value="Yes" <c:if test="${'Yes' == BonusRegID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >16</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Leave with Wages</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(LeaveWihtWages)"  name="value(LeaveWihtWages)" style="height:20px;">
						<%-- <option   value="No"  <c:if test="${AccidentPolicyValidTo == 'No'}">selected</c:if>>No</option>
						<option  value="Yes"  >Yes</option> --%>
						<option  <c:if test="${'Not Applicable' != LeaveWihtWagesID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == LeaveWihtWagesID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != LeaveWihtWagesID}"></c:if> value="No" <c:if test="${'No' == LeaveWihtWagesID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != LeaveWihtWagesID}"></c:if> value="Yes" <c:if test="${'Yes' == LeaveWihtWagesID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >17</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >Previous Month Wages paid on</td>
   <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">
					<select id="value(PreMonWage)"  name="value(PreMonWage)" style="height:20px;">
						<option  <c:if test="${'Not Applicable' != PreMonWageID}"></c:if> value="Not Applicable" <c:if test="${'Not Applicable' == PreMonWageID}">selected</c:if>>Not Applicable</option>
						<option  <c:if test="${'No' != PreMonWageID}"></c:if> value="No" <c:if test="${'No' == PreMonWageID}">selected</c:if>>No</option>
						<option <c:if test="${'Yes' != PreMonWageID}"></c:if> value="Yes" <c:if test="${'Yes' == PreMonWageID}">selected</c:if>>Yes</option>
						
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;width: auto;"><input id="value(PreMonWageDate)"
								name="value(PreMonWageDate)" onchange="setDataChanged(true);"  onkeypress="return isNumberKey(event)"
								maxlength="10" style="width:140px;height:20px;text-align: center;" placeholder="DD/MM/YYYY" value="<c:out value="${PreMonWageD}"/>"/>  <!-- <b> DD/MM/YYYY</b> --></td>	
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >18</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;width: auto;" >
    <div><input type="text" 
									style="width:98%" 	maxlength="200" /></div></td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >19</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >
    <div><input type="text" 
									style="width:98%" 	maxlength="200" /></div></td>
     <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
   <!--  <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr>
  <tr class="Even" >
    <td style="border-left: thin solid; border-bottom: thin solid;border-right: thin solid;text-align: center;" >20</td>
    <td  style="text-align: left;border-bottom: thin solid;border-right: thin solid;" >  <span></span> 
    <div><input type="text" 
								style="width:98%" 	maxlength="200" /></div></td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> 
    <td style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td>
    <!-- <td  style="text-align: center;border-bottom: thin solid;border-right: thin solid;" >-</td> -->
  </tr> 
  
   </tbody>
  
  </table> 
   <div class="Panel">
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		<tr> 
				<td><label for="khtmlNameInput"><span><b>Action Plan</b></span></label></td>
				<td> <input type="text" property="value(ActionPlan)" readonly="true"
									style="height:25px;width:707px"  size="100"
									maxlength="200"/> 
								</td>
			</tr>
			
			</tbody>
  </table> 
  </div>
 <div>
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		
			<tr>
			
				<td style="padding-right:50px;"><span></span> Previous Comment</td>
				<td style="padding-right:50px;"><input type="textarea" style="width:265px;height: 100px;" readonly="true"  /></td>
				<td style="padding-right:100px;"><span></span> Comment</td>
				<td ><input type="textarea" path="" style="width:265px;height: 100px;"  /></td>
			</tr>
					
  
			</tbody>
  </table>  
  </div>    
        </tbody>
    </table>
       	
    </f:form>
</div>

