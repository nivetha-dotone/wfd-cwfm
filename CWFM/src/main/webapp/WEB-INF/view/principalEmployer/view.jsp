<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
      <!--  <script src="resources/js/commonjs.js"></script> -->
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
       <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
    <style>
  body {
    margin: 0;
    overflow-x: hidden;
    font-family: 'Noto Sans', sans-serif;
}

#principalEmployerContent {
    padding: 20px;
    box-sizing: border-box;
    overflow-y: auto;
    height: calc(100vh - 60px); /* Adjust based on header height */
}

.page-header {
    background-color: #005151;
    color: #fff;
    padding: 15px;
    text-align: center;
    font-size: 24px;
    font-family: 'Noto Sans', sans-serif;
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 1000;
}

.header-buttons {
    float: right;
    margin-right: 20px;
}

.tabs {
    overflow: hidden;
    border-bottom: 2px solid #005151;
    margin-bottom: 20px;
}

.tabs button {
    background-color: #fff; /* Tab background color */
    border: 1px solid #ddd; /* Optional: add a border for visibility */
     border-radius: 3px;
    outline: none;
    padding: 5px 10px;/* Reduced height */
    cursor: pointer;
    font-size: 12px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
     margin-right: 5px;
}
  

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: white;
    border: 1px solid #ddd;
}

.tab-content.active {
    display: block;
}

.custom-label {
    font-family: 'Noto Sans', sans-serif;
    text-align: left;
    display: block;
    margin-bottom: 5px;
    color: #898989;/* Label text color */
    display: inline;
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input, .custom-input-checkbox {
    height: 40px;
    font-family: 'Noto Sans', sans-serif;
    width: 100%;
    box-sizing: border-box;
}

.required-field {
    color: red;
    margin-right: 4px;
}



table.ControlLayout {
    border-collapse: separate; /* Ensure spacing is applied correctly */
    border-spacing: 10px; /* Adjust the value for the desired gap between cells */
}

table.ControlLayout th,
table.ControlLayout td {
    padding: 10px; /* Add padding inside cells for spacing around content */
    vertical-align: top; /* Align the content to the top of the cell */
}
    </style>
    <script>
        function showTab(tabId) {
            // Hide all tab contents
            var tabContents = document.querySelectorAll('.tab-content');
            tabContents.forEach(function(content) {
                content.classList.remove('active');
            });

            // Remove active class from all tabs
            var tabs = document.querySelectorAll('.tabs button');
            tabs.forEach(function(tab) {
                tab.classList.remove('active');
            });

            // Show the selected tab content and add active class to the clicked tab
            document.getElementById(tabId).classList.add('active');
            document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
        }

        document.addEventListener("DOMContentLoaded", function() {
            // Set the default tab
            showTab('tab1');
        });
    </script>
</head>
<body>
    <!-- <div class="page-header">
        Principal Employer View Page
        <div class="header-buttons">
            <button type="button" onclick="history.back()">Back</button>
        </div>
    </div> -->

    <div id="principalEmployerContent">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Unit Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">License Information</button>
        </div>

        <div id="tab1" class="tab-content active">
          <form id="editForm" action="/CWFM/principalEmployer/view/${principalEmployer.unitId
          }" method="post">
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitName"/></label></th>
                <td><input type="text" name="NAME" value="${principalEmployer.name}" style="height: 20px;" size="30" maxlength="30" readonly="true" /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.peInactive"/></label></th>
                <td>
                    <c:choose>
                        <c:when test="${principalEmployer.isActive == 1}}">
                            <input type="checkbox" name="isActive" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="isActive" value="0" style="height: 20px;" onclick="return false;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitCode"/></label></th>
                <td><input type="text" name="code" value="${principalEmployer.code}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.organization"/></label></th>
                <td><input type="text" name="organization" value="${principalEmployer.organization}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.address"/></label></th>
                <td><input type="text" name="address" value="${principalEmployer.address}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                <td><input type="text" name="stateId" value="${principalEmployer.stateId}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                <td><input type="text" name="managerName" value="${principalEmployer.managerName}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                <td><input type="text" name="managerAddrs" value="${principalEmployer.managerAddrs}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Type of Business</label></th>
                <td><input type="text" name="businessType" value="${principalEmployer.businessType}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Maximum Number of Workmen</label></th>
                <td><input type="text" name="maxWorkmen" value="${principalEmployer.maxWorkmen}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Maximun Number of Contract Workmen</label></th>
                <td><input type="text" name="maxCntrWorkmen" value="${principalEmployer.maxCntrWorkmen}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label">Current count of Contract Workmen</label></th>
                <td><input type="text" name="" value="0" style="height: 20px;" size="30" maxlength="30" disabled="true" /></td>
            </tr>
            
        </tbody>
    </table>
   <!--  <div style="text-align: center;">
        <button type="submit" class="btn btn-primary">Submit</button>
        <button type="button" class="btn btn-secondary" onclick="history.back();">Back</button>
    </div> -->
</form>

        </div>
        
         <div id="tab2" class="tab-content">
            <form id="additionalForm" action="/CWFM/principalEmployer/viewAdditional/${principalEmployer.unitId}" method="post">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                        <!-- Additional Info Content Here -->
                      <tr>
                <th><label class="custom-label"><span class="required-field">*</span>BOCW License Number</label></th>
                <td><input type="text" name="licenseNumber" value="BOCW12345" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Coverage</label></th>
                <td><input type="text" name="pfCode" value="150" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Validity From</label></th>
                <td><input type="text" name="licenseNumber" value="01/01/2024" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Validity To</label></th>
                <td><input type="text" name="pfCode" value="01/01/2030" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            
              <tr>
                <th><label class="custom-label"><span class="required-field">*</span>ISMW License Number</label></th>
                <td><input type="text" name="licenseNumber" value="ISMW12345" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Coverage</label></th>
                <td><input type="text" name="pfCode" value="100" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Validity From</label></th>
                <td><input type="text" name="licenseNumber" value="01/01/2024" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>Validity To</label></th>
                <td><input type="text" name="pfCode" value="01/01/2030" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
                     <%--   <tr>
                <th><label class="custom-label"><span class="required-field">*</span>BOCWAct Applicability</label></th>
                <td>
                    <c:choose>
                        <c:when test="${principalEmployer.BOCWAPPLICABILITY}">
                            <input type="checkbox" name="BOCWAPPLICABILITY" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="BOCWAPPLICABILITY" value="0" style="height: 20px;" onclick="return false;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span>ISMW Act Applicability</label></th>
                <td>
                    <c:choose>
                        <c:when test="${principalEmployer.ISMWAPPLICABILITY}">
                            <input type="checkbox" name="ISMWAPPLICABILITY" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="ISMWAPPLICABILITY" value="0" style="height: 20px;" onclick="return false;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr> --%>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                <td><input type="text" name="licenseNumber" value="${principalEmployer.licenseNumber}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                <td><input type="text" name="pfCode" value="${principalEmployer.pfCode}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>WC Number</label></th>
                <td><input type="text" name="wcNumber" value="${principalEmployer.wcNumber}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>ESIC Number</label></th>
                <td><input type="text" name="esicNumber" value="${principalEmployer.esicNumber}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>PT Registration No.</label></th>
                <td><input type="text" name="ptRegNo" value="${principalEmployer.ptRegNo}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>LWF Registration No.</label></th>
                <td><input type="text" name="lwfRegNo" value="${principalEmployer.lwfRegNo}" style="height: 20px;" size="30" maxlength="30" /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                <td><input type="text" name="factoryLicenseNumber" value="${principalEmployer.factoryLicenseNumber}" style="height: 20px;" size="30" maxlength="30" /></td>
                <th><label class="custom-label"><span class="required-field">*</span>IS RC Applicable ?</label></th>
                <td>
                    <c:choose>
                        <c:when test="${principalEmployer.isRcApplicable == 1}}">
                            <input type="checkbox" name="isRcApplicable" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="isRcApplicable" value="0" style="height: 20px;" onclick="return false;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>RC Number</label></th>
                <td><input type="text" name="rcNumber" value="${principalEmployer.rcNumber}" style="height: 20px;" size="30" maxlength="30" /></td>
                <%-- <th><label class="custom-label"><span class="required-field">*</span>RC Validity</label></th>
                <td><input type="text" name="RCVALIDITY" value="${principalEmployer.RCVALIDITY}" style="height: 20px;" size="30" maxlength="30" /></td>
             --%></tr>
                    </tbody>
                </table>
            </form>
        </div>
        
    </div>
</body>
</html>
