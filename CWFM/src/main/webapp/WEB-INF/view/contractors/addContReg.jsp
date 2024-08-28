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
        overflow-y: auto;
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
    font-family: 'Arial', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: 'Arial', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: 'Arial', sans-serif; /* Same font as the label */
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
        Contractor Registration Page
        <div class="header-buttons">
            <button type="submit" onclick="submitForm()">Save</button>
        </div>
    </div>
    <div id="errorContainer"></div>
<div id="principalEmployerContent">
<f:form id="addPEForm" action="/CWFM/contractor/registration" modelAttribute="contReg" method="post" enctype="multipart/form-data" >
     <div id="errorContainer">
<%--       <c:out value="${errorsLength}" /> --%>
      <c:if test="${not empty errors}">
      <p><span class="required-field">*</span> Indicates Required fields.</p>
        <div class="error-message">
             <f:errors path="*" cssClass="error" element="div" />
        </div> 
    </c:if>
    </div>
   
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>Contractor Registration ID</label></th>
<td>
    <div>
        <input type="text" id="unitName" style="height: 20px;" size="30" maxlength="30"/>
       <%--  <f:errors path="NAME" cssClass="error-message"/> --%>
    </div>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Principal Employer</label></th>
                            <td>
   <select name="pe" style="width: 78%;height: 20px;"   >
   <option value="">Please select Principal Employer</option>
</select>
</td>
                         <th><label class="custom-label"><span class="required-field">*</span>Vendor Code</label></th>
                            <td>
   <select name="pe" style="width: 78%;height: 20px;"   >
   <option value="">Please select Vendor Code</option>
</select>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="address" path="ADDRESS"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Location of Work</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="address" path="ADDRESS"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Total Strength</label></th>
                            <td><input type="text" path="MANAGERNAME" value="${principalEmployer.MANAGERNAME}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>RC Max Employees </label></th>
                            <td><input type="text" path="MANAGERADDRS" value="${principalEmployer.MANAGERADDRS}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>PF Number</label></th>
                            <td><input type="text" path="BUSINESSTYPE" value="${principalEmployer.BUSINESSTYPE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span> 	Nature of Work </label></th>
                            <td><input type="text" path="MAXWORKMEN" value="${principalEmployer.MAXWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contract From</label></th>
                            <td><input type="text" path="MAXCNTRWORKMEN" value="${principalEmployer.MAXCNTRWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                            <th><label class="custom-label"> Contract To </label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" id="address" path="ADDRESS"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                               </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor Type</label></th>
                       <td>
   <select name="pe" style="width: 78%;height: 20px;"   >
   <option value="">Please select Contract Type</option>
</select>
</td>
                       <th><label class="custom-label"><span class="required-field">*</span>Is RC Verified?</label></th>
                        <td>
                         <input type="checkbox" id="ISMWAPPLICABILITY" name="ISMWAPPLICABILITY" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''}/>
                       <input type="hidden" name="ISMWAPPLICABILITY" value="false"/>
 <%--                        <input type="hidden" name="ISMWAPPLICABILITY_HIDDEN" value="${principalEmployer.ISMWAPPLICABILITY ? 'true' : 'false'}">
<input type="checkbox" id="ISMWAPPLICABILITY_CHECKBOX" name="ISMWAPPLICABILITY_CHECKBOX" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''} onclick="updateCheckboxValue('ISMWAPPLICABILITY_CHECKBOX', 'ISMWAPPLICABILITY_HIDDEN')">
 --%></td>
                        </tr>
                       
        </tbody>
    </table>
    
<table  cellspacing="0" cellpadding="0">
<thead> 
<tr>		
 <td colspan="2"><label ><span><font color="darkblue" ><b>  Additional Details Required<span style="color:red"> *</span></b></font></span></label></td>
	</tr>			</thead>
</table>

<table>
    <thead>
        <tr>
         <th>Action</th>
            <th>Work Order Number</th>
            <th>Nature of Job</th>
            <th>Document Type</th>
            <th>WC Code</th>
            <th>WC Total</th>
            <th>WC From</th>
            <th>WC To</th>
            <th>Attachment</th>
        </tr>
    </thead>
    <tbody>
        <tr>
           <td class="actionColumn">
                <!-- "+" and "-" buttons -->
                <button type="button" class="addRow">+</button>
                <button type="button" class="removeRow">-</button>
            </td>
                <!-- Input for Work Order Number -->
               <!--  <input type="text" name="workOrderNumber" /> -->
               <td>
                <select name="pe" style="width: 150px; height: 20px;">
                    <option value="">Please select Workorder</option>
                </select>
            </td>
            <td>
                <select name="pe" style="width: 150px; height: 20px;">
                    <option value="">Please select Nature of Job</option>
                </select>
            </td>
            <td>
                <select name="pe" style="width: 150px; height: 20px;">
                    <option value="">Please select Document Type</option>
                </select>
            </td>
            <td>
                <!-- Input for WC Code -->
                <input type="text" name="wcCode" />
            </td>
            <td>
                <!-- Input for WC Total -->
                <input type="text" name="wcTotal" />
            </td>
            <td>
                <!-- Input for WC From -->
                <input type="text" name="wcFrom" placeholder="DD/MM/YYYY" />
            </td>
            <td>
                <!-- Input for WC To -->
                <input type="text" name="wcTo" placeholder="DD/MM/YYYY" />
            </td>
            <td>
                <!-- Input for Attachment -->
                <input type="file" name="attachment" />
            </td>
          <!--   <td><button type="button" class="removeRow">-</button></td> -->
        </tr>
        <!-- Rows will be added dynamically here -->
    </tbody>
</table>
<!-- <button type="button" id="addRow">+</button> -->

    </f:form>
</div>

