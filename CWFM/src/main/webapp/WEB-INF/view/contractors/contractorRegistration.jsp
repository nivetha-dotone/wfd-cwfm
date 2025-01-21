<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
    <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
    <script src="resources/js/jquery.min.js"></script>
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
    border: 2px solid #005151; /* Tab border color */
    outline: none;
    padding: 10px 20px; /* Reduced height */
    cursor: pointer;
    font-size: 17px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
}

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: #f9f9f9;
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
            font-weight: normal;
        }
        .custom-label {
            font-family: Arial, sans-serif;
            text-align: left;
            display: block;
        }
        .required-field {
            color: red;
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
            margin-right: 10px;
        }
        .tabs {
            display: flex;
            margin-bottom: 10px;
        }
       
        .tabs button {
        padding: 5px 10px; /* Adjust padding to decrease size */
        font-size: 12px; /* Decrease font size */
        margin-right: 5px; /* Space between tabs */
        border: 1px solid #ddd; /* Optional: add a border for visibility */
        border-radius: 3px; /* Optional: rounded corners */
    }
        .tabs button.active {
            background-color: #005151;
            color: white;
            border-bottom: 2px solid #005151;
        }
        .tab-content {
            display: none;
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
        }
        .tab-content.active {
            display: block;
        }
        .custom-select {
    display: inline-block;
    width: 100%; /* Reduced the width to 50%, adjust as needed */
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
    .image-container {
        text-align: center;
        padding: 10px;
    }

    .image-container img {
        margin: 10px;
        cursor: pointer;
    }
   

    .tabs-container {
        display: flex;
        justify-content: space-between; /* Distribute space between tabs and buttons */
        align-items: center; /* Align items vertically */
    }

    .tabs {
        display: flex;
        flex-wrap: nowrap; /* Prevent wrapping of tabs */
    }

    .tabs button {
        margin-right: 10px; /* Space between tabs */
    }

    .action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
    .custom-mb {
    margin-bottom: 2.5rem; /* Example custom margin */
}
.custom-radio {
    margin-right: 5px; /* Adjust the spacing between the radio button and the label */
    vertical-align: middle; /* Align the radio button with the label text */
}

.custom-label-inline {
    display: inline-block;
    vertical-align: middle; /* Align the label text with the radio button */
    margin-left: 3px; /* Adjust this value to control the space between the radio button and the label */

}
td {
    padding: 5px; /* Add padding to cells for spacing */
}

input[type="radio"] {
    margin-right: 5px; /* Space between radio button and label */
    vertical-align: middle; /* Align radio button with label text */
}

label {
    vertical-align: middle; /* Align label text with radio button */
    display: inline-block; /* Ensure label appears on the same line as radio button */
    color: #495057; /* Set the text color to a dark shade */
    font-family: Arial, sans-serif;
}

/* #preview {
            width: 200px;
            height: 200px;
            border: 1px solid #ddd;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        } */
        #preview img {
            max-width: 100%;
            max-height: 100%;
        }
        body {
	background-color: #FFFFFF; /* White background for the page */
	font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
	overflow-y: scroll; /* Adds a vertical scroll bar */
	 overflow-x: auto; 
}
        
    </style>
     <%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
        String userId = user != null ? user.getUserId() : "";
		%>
		<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
 // Function to validate fields in the current active tab
    function validateCurrentTab() {
        // Example of validation logic; customize based on your tab's fields
        let isValid = true;

        // Example: Validate fields in the current active tab
        const activeTabId = document.querySelector('.tab-content.active').id;

        // Validate specific fields based on the active tab
        if (activeTabId === 'tab1') {
            const aadharNumber = document.getElementById("aadharNumber").value.trim();
            if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {
                document.getElementById("error-aadhar").textContent = "Please enter a valid 12-digit Aadhar number.";
                isValid = false;
            } else {
                document.getElementById("error-aadhar").textContent = ""; // Clear previous error
            }

            // Add more validation logic for other fields in tab1
        } 
        // Repeat similar validation checks for other tabs if necessary

        return isValid;
    }

    // Function to show the selected tab
    function showTab(tabId) {
        // Check if current tab fields are valid before switching tabs
        if (!validateCurrentTab()) {
            return; // Prevent tab switch if validation fails
        }

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

   



    /* function showTab(tabId) {
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
    } */

    document.addEventListener("DOMContentLoaded", function() {
        // Set the default tab
        showTab('tab1');
        
    });
       
    function validateTab() {
        let isValid = true;

        // Call a validation function for the single tab
        isValid = validateFormData();

        return isValid;
    }
    
  
  

    </script>
    <!-- <script>
        function displayContractorRegId() {
            const contractorRegId = document.getElementById("registrationid").value;
            console.log("Generated Contractor Registration ID: " + contractorRegId);
        }

        window.onload = displayContractorRegId;
    </script> -->
</head>
<body>
        <!-- <div >
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass()">Save</button>
             <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button>
        </div>  -->
       
    <div id="principalEmployerContent">
        <div class="tabs-container">
        <div class="tabs">
            <button  data-target="tab1" onclick="showTab('tab1')">Basic Data</button>
           <!--  <button data-target="tab2" onclick="showTab('tab2')">Employment Information</button>
            <button data-target="tab3" onclick="showTab('tab3')">Other Information</button>
            <button data-target="tab4" onclick="showTab('tab4')">Wages</button>
            <button data-target="tab5" onclick="showTab('tab5')">Documents</button> -->
        </div>
         <div class="action-buttons" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveRegistrationDetails('${sessionScope.loginuser.userId}')">Save</button>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Return</button>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveRegistrationDetails()">Save</button>
      
        </div> 
    </div> 

        <%-- <f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post">
            <div id="errorContainer">
                <c:if test="${not empty errors}">
                    <p><span class="required-field">*</span> Indicates Required fields.</p>
                    <div class="error-message">
                        <f:errors path="*" cssClass="error" element="div" />
                    </div> 
                </c:if>
            </div>
            </f:form> --%>
<div id="tab1" class="tab-content active ">
    <table cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorRegistrationId"/> </label></th>
                <td>
                	<input id="registrationid" name="registrationid" value="${contractorregId}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly>
                	<label id="error-registrationid" style="color: red;display: none;">Registration ID is required</label>
                </td>
           </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
				 <td><select class="custom-select" id="principalEmployerId" name="principalEmployer" style="width: 100%;height: 25px; ">
                                <option value="">Please select Principal Employer</option>
                                <c:forEach var="pe" items="${PrincipalEmployer}">
                					<option value="${pe.unitId}">${pe.name}</option>
            					</c:forEach>
                                </select>
                    </td>
          <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
                <td>
                	<input id="vendorCode" name="vendorCode" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	 <label id="error-vendorCode" style="color: red;display: none;">Vendor Code is required</label>
                </td>
               
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                <td>
                	<input id="managername" name="managername" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-managername" style="color: red;display: none;">Manager name is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.locationOfWork"/></label></th>
                <td>
                	<input id="locofwork" name="locofwork" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-locofwork"style="color: red;display: none;">Location Of Work is required</label>
                </td>
                
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.totalStrength"/></label></th>
                <td>
                	<input id="totalStrength" name="totalStrength" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-totalStrength" style="color: red;display: none;">Total Strength is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.rcMaxEmployees"/></label></th>
                <td>
                	<input id="rcmaxemployees" name="rcmaxemployees" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-rcmaxemployees" style="color: red;display: none;">RC Max Employees is required</label>
                </td>
               
            </tr>
           <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfNumber"/></label></th>
                <td>
                	<input id="pfnumber" name="pfnumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-pfnumber" style="color: red;display: none;">PF Number is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.natureOfWork"/></label></th>
                <td>
                	<input id="natureOfWork" name="natureOfWork" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-natureOfWork" style="color: red;display: none;">Nature Of Work is required</label>
                </td>
               
           </tr>
            <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractFrom"/></label></th>
                <td>
                	<input id="contractFrom" name="contractFrom" style="width: 100%;height: 20px; color: black;" type="date" size="30" maxlength="30">
                	<label id="error-contractFrom" style="color: red;display: none;">Contract From is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractTo"/></label></th>
                <td>
                	<input id="contractTo" name="contractTo" style="width: 100%;height: 20px; color: black;" type="date" size="30" maxlength="30">
                	<label id="error-contractTo" style="color: red;display: none;">Contract To is required</label>
                </td>
               
           </tr>
         <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractType"/></label></th>
           <td>
    <!-- Dropdown for contractor type -->
              <select id="contractType" name="contractType" style="width: 100%; height: 25px;color: black;" onchange="toggleMainContractorRow()">
                <option value="">Select Contractor Type</option>
                <option value="Main Contractor">Main Contractor</option>
                <option value="Sub Contractor">Sub Contractor</option>
              </select>
             <label id="error-contractType" style="color: red; display: none;">Contract Type is required</label>
         </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.isRcVerified"/></label></th>
                <td>
                	<input id="isRcVerified" name="isRcVerified" style="width: 100%;height: 20px;color: black;" type="checkbox" size="30" maxlength="30">
                	<label id="error-isRcVerified" style="color: red;display: none;">Is RC Verified is required</label>
                </td>
               
        </tr>
           <!-- Main Contractor row -->
      <tr id="mainContractorRow" style="display: none;"><th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mainContractor"/></label></th>
    <td>
        <input id="mainContractor" name="mainContractor" style="width: 100%; height: 20px;" type="text" size="30" maxlength="30">
        <label id="error-mainContractor" style="color: red; display: none;">Main Contractor is required</label>
    </td>
   </tr>
   </tbody>
  </table>
 </div>

<div class="action-buttons z" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}')">Save</button>
           <!--  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button> -->
 </div>
 <div  class="tab-content "><spring:message code="label.additionalDocumets"/></div>
            <div id="tab2" class="tab-content active ">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <thead>
            <tr style=" border: 1px solid #ddd;">
            
                <th><label class="custom-label"></th>
                <th><label class="custom-label"></th>
                <th><label class="custom-label">  <spring:message code="label.workOrderNumber"/></th>
				<th><label class="custom-label"> <spring:message code="label.natureOfJob"/></th>
				<th><label class="custom-label"> <spring:message code="label.documentType"/></th>
				<th><label class="custom-label"> <spring:message code="label.documentNumber"/></th>
				<th><label class="custom-label"> <spring:message code="label.coverage"/></th>
				<th><label class="custom-label"><spring:message code="label.validFrom"/></th>
				<th><label class="custom-label"><spring:message code="label.validTo"/></th>
				<th><label class="custom-label"><spring:message code="label.attachment"/></th>
				
            </tr>
        </thead>
        <tbody>
            <%-- <c:forEach var="approver" items="${approvers}" varStatus="status"> --%>
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                   <td ><button class="btn btn-success" onclick="addTaskRow()">+</button></td>
                   <td ><button class="btn btn-danger" onclick="deleteRow(this)">-</button></td>
                   <td style="color:black">
                       <select id="documentDropId" name="value()" onchange="setDataChanged()">
                          <option value="-1">Select Document Type</option>
                          <option value="ESIC" >ESIC</option>
                          <option value="LL" >LL</option>
                          <option value="RC">RC</option>
                          <option value="WC" >WC</option>
                    </select>
                </td>
                 <td style= "color:black"> <input type="text"  value=" " id="documentnumber" style="width:100px;"/></td> 
				<td style="color:black"> <input type="text"  value=" " id="coverage" style="width:100px;"/> </td>
				<td style="color:black"><input type="date" value=" " id="validf"   " placeholder = "DD/MM/YYYY"  /></td>
				<td style="color:black"><input type="date" value=" "  id="validto"  " placeholder = "DD/MM/YYYY" /></td>
				<td style= "color:black"><input type="file" value="No file selected" id="attachments" style="width:100px;"/></td>
	            <td style= "color:black"><input type="checkbox" value=" " id="panindia"/></td>
	            <td style= "color:black"><input type="checkbox" value=" " id="subcontractor"/></td>
             </tr>
            <%-- </c:forEach> --%>
        </tbody>
                </table>
            </div>
 
        
 
 </div>
</body>
</html>
