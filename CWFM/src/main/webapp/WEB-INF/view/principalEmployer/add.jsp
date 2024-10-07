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
        <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
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
.custom-select {
    display: inline-block;
    width: 85%; /* Reduced the width to 50%, adjust as needed */
    height: 25px;
    padding: 5px;
    font-size: 12px;
    font-family: Arial, sans-serif;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

    .custom-select:focus {
        border-color: #80bdff;
        outline: 0;
        box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
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
        Principal Employer Add Page
        <div class="header-buttons">
            <button type="submit" onclick="submitForm()">Save</button>
        </div>
    </div>
    <div id="errorContainer"></div>
<div id="principalEmployerContent">
<f:form id="addPEForm" action="/CWFM/principalEmployer/add" modelAttribute="principalEmployer" method="post" enctype="multipart/form-data" onsubmit="return validateForm()" >
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
                         <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
<td>
    <div>
        <f:input type="text" id="unitName" path="NAME" style="height: 20px;" size="30" maxlength="30"/>
       <%--  <f:errors path="NAME" cssClass="error-message"/> --%>
    </div>
</td>
                            <th><label class="custom-label"><span class="required-field">*</span>PE Inactive</label></th>
                        <td>
                         <input type="checkbox" id="ISACTIVE" name="ISACTIVE" ${principalEmployer.ISACTIVE ? 'checked' : ''}/>
                       <input type="hidden" name="ISACTIVE" value="false"/>
 <%--                        <input type="hidden" name="ISACTIVE_HIDDEN" value="${principalEmployer.ISACTIVE ? 'true' : 'false'}">
<input type="checkbox" id="ISACTIVE_CHECKBOX" name="ISACTIVE_CHECKBOX" ${principalEmployer.ISACTIVE ? 'checked' : ''} onclick="updateCheckboxValue('ISACTIVE_CHECKBOX', 'ISACTIVE_HIDDEN')">
 --%></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Code</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="text" id="unitCode" path="CODE"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Organization</label></th>
                            <td><f:input type="text" path="ORGANIZATION"  style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Address</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="text" id="address" path="ADDRESS"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                            <%-- <td>
                            <select name="selectedState">
    <c:forEach var="state" items="${states}">
        <option value="${state.stateId}">${state.stateName}</option>
    </c:forEach>
</select>
                            <f:input type="text" 	path="STATEID" value="${principalEmployer.STATEID}" style="height: 20px;"  size="30" maxlength="30" />
                            
                            </td> --%>
                       <td>
   <select name="selectedState"  class="custom-select">
    <c:forEach var="state" items="${states}">
        <option value="${state.stateId}" ${state.stateId == principalEmployer.STATEID ? 'selected' : ''}>${state.stateName}</option>
    </c:forEach>
</select>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td><f:input type="text" path="MANAGERNAME" value="${principalEmployer.MANAGERNAME}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                            <td><f:input type="text" path="MANAGERADDRS" value="${principalEmployer.MANAGERADDRS}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Type of Business</label></th>
                            <td><f:input type="text" path="BUSINESSTYPE" value="${principalEmployer.BUSINESSTYPE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Maximum Number of Workmen</label></th>
                            <td><f:input type="text" path="MAXWORKMEN" value="${principalEmployer.MAXWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Maximun Number of Contract Workmen</label></th>
                            <td><f:input type="text" path="MAXCNTRWORKMEN" value="${principalEmployer.MAXCNTRWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                            <th><label class="custom-label">Current count of Contract Workmen</label></th>
                            <td><input type="text"  value="0" style="height: 20px;"  size="30" maxlength="30" disabled="true" /></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>BOCWAct Applicability</label></th>
                        <td>
                         <input type="checkbox" id="BOCWAPPLICABILITY" name="BOCWAPPLICABILITY" ${principalEmployer.BOCWAPPLICABILITY ? 'checked' : ''}/>
                       <input type="hidden" name="BOCWAPPLICABILITY" value="false"/>
   <%--                      <input type="hidden" name="BOCWAPPLICABILITY_HIDDEN" value="${principalEmployer.BOCWAPPLICABILITY ? 'true' : 'false'}">
<input type="checkbox" id="BOCWAPPLICABILITY_CHECKBOX" name="BOCWAPPLICABILITY_CHECKBOX" ${principalEmployer.BOCWAPPLICABILITY ? 'checked' : ''} onclick="updateCheckboxValue('BOCWAPPLICABILITY_CHECKBOX', 'BOCWAPPLICABILITY_HIDDEN')">
 --%>
</td>
                       <th><label class="custom-label"><span class="required-field">*</span>ISMW Act Applicability</label></th>
                        <td>
                         <input type="checkbox" id="ISMWAPPLICABILITY" name="ISMWAPPLICABILITY" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''}/>
                       <input type="hidden" name="ISMWAPPLICABILITY" value="false"/>
 <%--                        <input type="hidden" name="ISMWAPPLICABILITY_HIDDEN" value="${principalEmployer.ISMWAPPLICABILITY ? 'true' : 'false'}">
<input type="checkbox" id="ISMWAPPLICABILITY_CHECKBOX" name="ISMWAPPLICABILITY_CHECKBOX" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''} onclick="updateCheckboxValue('ISMWAPPLICABILITY_CHECKBOX', 'ISMWAPPLICABILITY_HIDDEN')">
 --%></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                            <td><f:input type="text" path="LICENSENUMBER" value="${principalEmployer.LICENSENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                            <td><f:input type="text" path="PFCODE" value="${principalEmployer.PFCODE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>WC Number</label></th>
                            <td><f:input type="text" path="WCNUMBER" value="${principalEmployer.WCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>ESIC Number</label></th>
                            <td><f:input type="text" path="ESICNUMBER" value="${principalEmployer.ESICNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>PT Registration No.</label></th>
                            <td><f:input type="text" path="PTREGNO" value="${principalEmployer.PTREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>LWF Registration No.</label></th>
                            <td><f:input type="text" path="LWFREGNO" value="${principalEmployer.LWFREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                            <td><f:input type="text" path="FACTORYLICENCENUMBER" value="${principalEmployer.FACTORYLICENCENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       <th><label class="custom-label"><span class="required-field">*</span>IS RC Applicable ?</label></th>
                      <td>
                       <input type="checkbox" id="ISRCAPPLICABLE" name="ISRCAPPLICABLE" ${principalEmployer.ISRCAPPLICABLE ? 'checked' : ''}/>
                       <input type="hidden" name="ISRCAPPLICABLE" value="false"/>
     <%--                    <input type="hidden" name="ISRCAPPLICABLE_HIDDEN" value="${principalEmployer.ISRCAPPLICABLE ? true : false}">
<input type="checkbox" id="ISRCAPPLICABLE_CHECKBOX" name="ISRCAPPLICABLE_CHECKBOX" ${principalEmployer.ISRCAPPLICABLE ? 'checked' : ''} onclick="updateCheckboxValue('ISRCAPPLICABLE_CHECKBOX', 'ISRCAPPLICABLE_HIDDEN')">
 --%>
</td>
                        
                          <%--  <td>
  <!--   <input type="checkbox" id="isRcApplicable" name="ISRCAPPLICABLE" value="true" /> -->
  <input type="checkbox" id="ISRCAPPLICABLE" name="ISRCAPPLICABLE" ${principalEmployer.ISRCAPPLICABLE ? 'checked' : ''} />
   </td> --%>
    <%--        onclick="updateCheckbox(this)" style="height: 20px;" 
           <c:if test="${principalEmployer.ISRCAPPLICABLE  == true}">
               checked="checked"
           </c:if> />
</td> --%>
                        </tr>
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>RC Number</label></th>
                            <td><f:input type="text" path="RCNUMBER" value="${principalEmployer.RCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Attachment Name</label></th>
                            <%-- <td><f:input type="text" path="ATTACHMENTNM" value="${principalEmployer.ATTACHMENTNM}" style="height: 20px;"  size="30" maxlength="30" /></td> --%>
                            <td><!-- <input type="file" name="file" /> -->
                             <input type="file" name="file" />
        <f:errors path="ATTACHMENTNM" cssClass="error-message"/>
                            </td>
                        
                        </tr>
                </td>
            </tr>
        </tbody>
    </table>
    </f:form>
</div>
<!-- <div class="col-sm-6 col-md-6"><div ng-show="EmailVisible"><div class="form-group"><div class="col-sm-4 col-md-4 label"><span ng-bind-template="Email" title="Email" class="ng-binding">Email</span></div><div class="col-sm-8 col-md-8"><input type="text" ng-disabled="EmailReadonly || !config.sharedData.enableFields" id="wfs.peopleeditor.inputtype.email" ng-trim="true" maxlength="230" class="krn-common-focus ng-pristine ng-valid ng-empty ng-valid-maxlength ng-touched" ng-model="config.email" ng-change="updateDirtyFlag()" style=""></div></div></div><div ng-show="TelephoneVisible"><div class="form-group"><div class="col-sm-4 col-md-4 label"><span ng-bind-template="Phone 1" title="Phone 1" class="ng-binding">Phone 1</span></div><div class="col-sm-8 col-md-8"><input type="text" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields" id="wfs.peopleeditor.inputtype.phone1" class="pull-left krn-common-focus ng-pristine ng-untouched ng-valid ng-empty ng-valid-maxlength" ng-trim="true" maxlength="35" ng-model="config.phone1" ng-change="updateDirtyFlag()" style=""><div class="checkbox padl10"><input type="checkbox" id="wfs.peopleeditor.checkbox.sms1" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields || !config.phone1" class="krn-common-focus ng-pristine ng-untouched ng-valid ng-empty" ng-model="config.phone1Sms" ng-change="updateDirtyFlag()" disabled="disabled" style=""> <span class="label ng-binding" ng-bind-template="SMS" title="SMS">SMS</span></div></div></div><div class="form-group"><div class="col-sm-4 col-md-4 label"><span ng-bind-template="Work" title="Work" class="ng-binding">Work</span></div><div class="col-sm-8 col-md-8"><input type="text" class="pull-left krn-common-focus ng-pristine ng-untouched ng-valid ng-empty ng-valid-maxlength" id="wfs.peopleeditor.inputtype.phone2" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields" ng-trim="true" maxlength="35" ng-model="config.phone2" ng-change="updateDirtyFlag()" style=""><div class="checkbox padl10"><input type="checkbox" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields || !config.phone2" class="krn-common-focus ng-pristine ng-untouched ng-valid ng-empty" id="wfs.peopleeditor.checkbox.sms" ng-model="config.phone2Sms" ng-change="updateDirtyFlag()" disabled="disabled" style=""> <span class="label ng-binding" ng-bind-template="SMS" title="SMS">SMS</span></div></div></div><div class="form-group"><div class="col-sm-4 col-md-4 label"><span ng-bind-template="Mobile" title="Mobile" class="ng-binding">Mobile</span></div><div class="col-sm-8 col-md-8"><input type="text" id="wfs.peopleeditor.input.phone3" class="pull-left krn-common-focus ng-pristine ng-untouched ng-valid ng-empty ng-valid-maxlength" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields" ng-trim="true" maxlength="35" ng-model="config.phone3" ng-change="updateDirtyFlag()" style=""><div class="checkbox padl10"><input type="checkbox" ng-disabled="TelephoneReadonly || !config.sharedData.enableFields || !config.phone3" id="wfs.peopleeditor.checkbox.sms3" ng-model="config.phone3Sms" class="krn-common-focus ng-pristine ng-untouched ng-valid ng-empty" ng-change="updateDirtyFlag()" disabled="disabled" style=""> <span class="label ng-binding" ng-bind-template="SMS" title="SMS">SMS</span></div></div></div></div></div>
 -->