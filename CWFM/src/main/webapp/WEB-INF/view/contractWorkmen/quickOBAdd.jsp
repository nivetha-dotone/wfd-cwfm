<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
       

    
  
   

    </script>
</head>
<body>
        <!-- <div >
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass()">Save</button>
             <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button>
        </div>  -->
       
    <div id="principalEmployerContent">
       <div class="tabs-container">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTab('tab1')">Basic Data</button>
            <button data-target="tab2" onclick="showTab('tab2')">Employment Information</button>
            <button data-target="tab3" onclick="showTab('tab3')">Other Information</button>
            <button data-target="tab4" onclick="showTab('tab4')">Wages</button>
            <button data-target="tab5" onclick="showTab('tab5')">Documents</button>
        </div>
         <div class="action-buttons" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}')">Save</button>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button>
        </div> 
    </div>

        <f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post">
            <div id="errorContainer">
                <c:if test="${not empty errors}">
                    <p><span class="required-field">*</span> Indicates Required fields.</p>
                    <div class="error-message">
                        <f:errors path="*" cssClass="error" element="div" />
                    </div> 
                </c:if>
            </div>
            <div id="tab1" class="tab-content active">
    <table cellspacing="0" cellpadding="0">
        <tbody>
       <!--  <tr>
    <th><label class="custom-label"><span class="required-field">*</span>Entry Pass Type</label></th>
    <td>
        <input type="radio" name="entryPassType" id="quickOnboarding" value="quickOnboarding">
        <label class="custom-label-inline" for="quickOnboarding"> Quick Onboarding</label>
    </td>
    <td>
        <input type="radio" name="entryPassType" id="regular" value="regular">
        <label class="custom-label-inline" for="regular"> Regular</label>
    </td>
</tr> -->
            <tr>
    <th><label class="custom-label"><span class="required-field">*</span>Enter Aadhar</label></th>
    <td>
    	<input id="aadharNumber" name="aadharNumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12">
    	 <label id="error-aadhar" style="color: red;display: none;">Please enter a valid 12-digit Aadhar number</label>
    </td>
    <th></th>
    <td></td>
    <!--  <td rowspan="6" class="image-container">
        <img id="imageId" width="150" height="150" onclick="openFilePicker();">
        <input type="file" id="fileInput" style="display: none;" accept="image/*" onchange="handleFileSelect(event)">
    </td> -->
    </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>First Name</label></th>
                <td>
                	<input id="firstName" name="firstName" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-firstName" style="color: red;display: none;">First Name is required</label>
                </td>
           
            
                <th><label class="custom-label"><span class="required-field">*</span>Last Name</label></th>
                <td>
                	<input id="lastName" name="lastName" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	 <label id="error-lastName" style="color: red;display: none;">Last Name is required</label>
                </td>
          
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Date of Birth</label></th>
               <!--  <td><input id="dateOfBirth" name="dateOfBirth" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30"></td> -->
               <td>
    				<input id="dateOfBirth" name="dateOfBirth" class="datetimepickerformat" style="width: 100%; height: 20px;" type="text" size="30" maxlength="30"  onfocus="initializeDatePicker()" 
       onclick="initializeDatePicker()" >
					  <label id="error-dateOfBirth" style="color: red;display: none;">Date Of Birth is required</label>
			</td>
               
                <th><label class="custom-label"><span class="required-field">*</span>Gender</label></th>
                <td>
                    <select class="custom-select" name="gender" id="gender">
                        <option value="">Please select Gender</option>
                        <c:forEach var="option" items="${GenderOptions}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
                    </select>
                    <label id="error-gender" style="color: red;display: none;">Gender is required</label>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Father/Husband Name</label></th>
                <td>
                	<input id="relationName" name="relationName" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-relationName" style="color: red;display: none;">Father / Husband name is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span>ID Mark</label></th>
                <td>
                	<input id="idMark" name="idMark" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-idMark"style="color: red;display: none;">ID Mark is required</label>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Mobile Number</label></th>
                <td>
                	<input id="mobileNumber" name="mobileNumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                	<label id="error-mobileNumber" style="color: red;display: none;">Mobile Number is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span>Marital Status</label></th>
                <td>
                	<select class="custom-select" name="maritalStatus" id="maritalStatus">
                        <option value="">Please select Marital Status</option>
                        	<option value="Single">Single</option>
                            <option value="Married">Married</option>
                    </select>
                	<label id="error-maritalStatus"style="color: red;display: none;">Marital Status is required</label>
                </td>
            </tr>
           
        </tbody>
    </table>
   
</div>

            <div id="tab2" class="tab-content">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Principal Employer</label></th>
                            <td><select class="custom-select" id="principalEmployer" name="principalEmployerId" onchange="getContractorsAndTrades(this.value, '${sessionScope.loginuser.userId}')">
                                <option value="">Please select Principal Employer</option>
                                <c:forEach var="pe" items="${PrincipalEmployer}">
                					<option value="${pe.unitId}">${pe.name}</option>
            					</c:forEach>
                                </select>
                                <label id="error-principalEmployer"style="color: red;display: none;">Principal Employer is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor</label></th>
                            <td><select class="custom-select" id="contractor" name="contractorId" onchange="getWorkordersAndWC()">
            						<option value="">Please select Contractor</option>
        						</select>
        						<label id="error-contractor"style="color: red;display: none;">Contractor is required</label>
        					</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Workorder</label></th>
                            <td><select class="custom-select" id="workorder" name="workorderId" >
                                <option value="">Please select Workorder</option>
                                </select>
                                <label id="error-workorder" style="color: red;display: none;">Workorder is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Trade</label></th>
                            <td><select class="custom-select" id="trade" name="tradeId" >
                                <option value="">Please select Trade</option></select>
                                <label id="error-trade"style="color: red;display: none;">Trade is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Skill</label></th>
                            <td><select class="custom-select" id="skill" name="skillId" >
                                	<option value="">Please select Skill</option>
                                	 <c:forEach var="s" items="${Skills}">
                						<option value="${s.skillId}">${s.skillName}</option>
            						</c:forEach>
                                </select>
                                <label id="error-skill" style="color: red;display: none;">Skill is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Department</label></th>
                            <td><select class="custom-select" id="department" name="departmentId" >
                                <option value="">Please select Department</option>
                                 <c:forEach var="dept" items="${Dept}">
                						<option value="${dept.gmId}">${dept.gmName}</option>
            						</c:forEach>
                                </select>
                                <label id="error-department"style="color: red;display: none;">Department is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Area</label></th>
                            <td><select class="custom-select" id="subdepartment" name="subdepartmentId" >
                                <option value="">Please select Area</option>
                                 <c:forEach var="dept" items="${Subdept}">
                						<option value="${dept.gmId}">${dept.gmName}</option>
            						</c:forEach>
                                </select>
                                <label id="error-area"style="color: red;display: none;">Area is required</label>
                                </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Engineering-in-Charge</label></th>
                            <td><select class="custom-select" id="eic" name="eicId" >
                                <option value="">Please select EIC</option>
                                <c:forEach var="eic" items="${EIC}">
                						<option value="${eic.userId}">${eic.fullName}</option>
            						</c:forEach>
                                
                                </select>
                                <label id="error-eic"style="color: red;display: none;">EIC is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Nature of Job</label></th>
                            <td>
                            	<input id="natureOfJob" name="natureOfJob" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                            	  <label id="error-natureOfJob"style="color: red;display: none;">Nature of Job is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>WC Policy/ESIC Reg Number</label></th>
                            <td><select class="custom-select" id="wc" name="wcId" >
                                <option value="">Please select WC Policy/ESIC Reg Number</option>
                                
                                </select>
                                <label id="error-wc"style="color: red;display: none;">WC Policy/ESIC Reg Number is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Hazardous Area</label></th>
                            <td><select class="custom-select"  id="hazardousArea" name="hazardousArea">
                                <option value="">Please select Hazardous Area</option>
                                <option value="Yes">Yes</option>
        						<option value="No">No</option>
                                </select>
                                 <label id="error-hazardous"style="color: red;display: none;">Hazardous Area is required</label>
                                </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Access Area</label></th>
                            <td>  <select class="custom-select"  id="accessArea" name="accessArea" >
       								 <option value="">Please select Access Area</option>
        							<c:forEach var="option" items="${AccessArea}">
                            			<option value="${option.gmId}">${option.gmName}</option>
                        			</c:forEach>
    								</select>
    								 <label id="error-accessArea"style="color: red;display: none;">Access Area is required</label>
    						</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label">UAN Number</label></th>
                            <td>
                            	<input id="uanNumber" name="uanNumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30">
                            	<label id="error-uanNumber"style="color: red;display: none;">UAN Number is required</label>
                            </td>
                            <th><label class="custom-label">Health Check Up Date</label></th>
                            <td>
                            	<input id="healthCheckDate" name="healthCheckDate" class="datetimepickerformat" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" onfocus="initializeDatePicker()">
                            	<label id="error-healthCheckDate"style="color: red;display: none;">Health Check Up Date is required</label>
                            	</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div id="tab3" class="tab-content">
              <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
          	 
		   <tr>
<th><label class="custom-label"><span class="required-field">*</span>Blood Group</label></th>				
				<td >
				<select class="custom-select" id="bloodGroup"   name="bloodGroupId"  >
						<option value="" >Select Blood Group</option>
						<c:forEach var="option" items="${BloodGroup}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
						</select>
						<label id="error-bloodGroup"style="color: red;display: none;">Blood Group is required</label>
				</td>
				
			
<th><label class="custom-label"><span class="required-field">*</span>Accommodation</label></th>				
				<td ><select class="custom-select" id="accommodation"   name="accommodation"  >
						<option value="" >Select Accommodation</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						</select>
						<label id="error-accommodation"style="color: red;display: none;">Accommodation is required</label>
						</td>
				 </tr>
			<tr>
<th><label class="custom-label"><span class="required-field">*</span>Academic</label></th>				
				<td >
					<select class="custom-select"  id="academic"   name="academicId" >
					<option value="" >Select Educational Qualification</option>
							<c:forEach var="option" items="${Academics}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
					</select>
					<label id="error-academic"style="color: red;display: none;">Academic is required</label>
				</td>
				
<th><label class="custom-label"><span class="required-field">*</span>Technical</label></th>				
				<td >
					<select class="custom-select" id="technical"    name="technical"  >
						<option value="" >Select Technical</option>
						<option value="Technical">Technical</option>
						<option value="NonTechnical">Non Technical</option>
					</select>
					<label id="error-technical"style="color: red;display: none;">Technical is required</label>
				</td>
				</tr>
			
		   <tr>
<th><label class="custom-label"><span class="required-field">*</span>IFSC Code</label></th>
				<td >
				<input  style="width: 100%;height: 20px;" type="text" size="30" id="ifscCode" name="ifscCode"    maxlength="11"   />
				<label id="error-ifscCode"style="color: red;display: none;">IFSC Code is required</label>
				</td>
			<th><label class="custom-label"><span class="required-field">*</span>Account Number</label></th>
				<td >
				<input style="width: 100%;height: 20px;" type="text" size="30" id="accountNumber" name="accountNumber"    />
				<label id="error-accountNumber"style="color: red;display: none;">Account Number is required</label>
				</td>
			</tr>
			
		   <tr>
				<th><label class="custom-label"><span class="required-field"></span>Emergency Contact Name</label></th>
				<td>
				<input style="width: 100%;height: 20px;" type="text" size="30" id="emergencyName" name="emergencyName"    maxlength="30"  />
					<label id="error-emergencyName"style="color: red;display: none;">Emergency Name is required</label>
				</td>			
			<th><label class="custom-label"><span class="required-field"></span>Emergency Contact Number</label></th>
				<td><input style="width: 100%;height: 20px;" type="text" size="30" id="emergencyNumber" name="emergencyNumber"    maxlength="10"    />
					<label id="error-emergencyNumber"style="color: red;display: none;">Emergency Number is required</label>
				</td>				
			
			</tr>
			</tbody>
                </table>
</div>

            <div id="tab4" class="tab-content">
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                     
		   <tr>
				
				<th><label class="custom-label"><span class="required-field">*</span>Workmen Wage Category</label></th>
				<td >
					<select class="custom-select" id="wageCategory"    name="wageCategoryId"  >
						<option value=""> Select Workmen Wage Category</option>
						<c:forEach var="option" items="${WageCategory}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
					</select>
						<label id="error-wageCategory"style="color: red;display: none;">Workmen Wage Category is required</label>
				</td>
				
				<th><label class="custom-label"><span class="required-field">*</span>Bonus Payout</label></th>
				<td >
					<select class="custom-select" id="bonusPayout"    name="bonusPayoutId"  >
						<option value=""> Select Bonus Payout</option>
						<c:forEach var="option" items="${BonusPayout}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
					</select>
					<label id="error-bonusPayout"style="color: red;display: none;">Bonus Payout is required</label>
				</td>
				</tr>
        <tr>
				<th><label class="custom-label"><span class="required-field">*</span>Zone</label></th>
				<td >
					<select class="custom-select" id="zone"    name="zoneId"  >
						<option value=""> Select Zone</option>
						<c:forEach var="option" items="${Zone}">
                            <option value="${option.gmId}">${option.gmName}</option>
                        </c:forEach>
					</select>
					<label id="error-zone"style="color: red;display: none;">Zone is required</label>
				</td>
		
			<th><label class="custom-label"><span class="required-field"></span>Basic</label></th>
			
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="basic" id="basic"  />
				<label id="error-basic"style="color: red;display: none;">Enter valid Basic</label>
				</td>						
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span>DA</label></th>
				<td ><input style="width: 100%;height: 20px;" type="text" size="30" name="da" id="da"/>
				<label id="error-da"style="color: red;display: none;">Enter valid DA</label>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span>HRA</label></th>
				<td ><input style="width: 100%;height: 20px;" type="text" size="30" name="hra"  id="hra"/>
				<label id="error-hra"style="color: red;display: none;">Enter valid HRA</label>
				</td>				
			
			</tr>
         <tr>
			<th><label class="custom-label"><span class="required-field"></span>Washing Allowance</label></th>
				<td>
				<input style="width: 100%;height: 20px;" type="text" size="30" name="washingAllowance" id="washingAllowance" />
				<label id="error-washingAllowance"style="color: red;display: none;">Enter valid Washing Allowance</label>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span>Other Allowance</label></th>
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="otherAllowance" id="otherAllowance"  />
				<label id="error-otherAllowance"style="color: red;display: none;">Enter valid Other Allowance</label>
				</td>				
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span>Uniform Allowance</label></th>
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="uniformAllowance" id="uniformAllowance" />
				<label id="error-uniformAllowance"style="color: red;display: none;">Enter valid Uniform Allowance</label>
				</td>				
			
			<th><label class="custom-label"><span class="required-field"></span>PF Cap</label></th>
			 <td>
        <select class="custom-select" id="pfCap" name="pfCap"   >
            <option value="Yes" <c:if test="${pfCap == 'Yes'}">selected</c:if>>Yes</option>
            <option value="No" <c:if test="${pfCap == 'No'}">selected</c:if>>No</option>
        </select>
        <label id="error-pfCap"style="color: red;display: none;">PF Cap is required</label>
    </td>
			</tr>
               </tbody>
                </table>
            </div>

            <div id="tab5" class="tab-content">
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                   <tr>
                		<td>
                		 	<label for="aadharFile">Upload Aadhar Card (PDF):</label>
       					 	<input type="file" id="aadharFile" name="aadharFile" accept="application/pdf">
        					<div id="aadharError"></div> <!-- Error message for Aadhar file -->
                		</td>
                		<td>
                			<label for="policeFile">Upload PoliceVerification Report (PDF):</label> 
                			<input type="file"	id="policeFile" name="policeFile" accept="application/pdf">
							<div id="policeError"></div> <!-- Error message for Police file -->
						</td>
						<td>
						<button id="uploadButton" type="button" onclick="fileUpload()"> Upload Documents</button>
						</td>
						<td><div id="uploadMessage"></div></td>
            		</tr>
      		
        
        <tr>
            <td colspan="20">
                <div>
                    <p id="p3"><b><font color="darkblue" size="3">Comments</font></b></p>
                    <hr10 style="color:rgb(0, 102, 204);">
                </div>
            </td>
        </tr>
        <tr>
				<!-- <th><label class="custom-label">Previous Comment</label></th>
				<td><input type="textarea" name="value(prevComment)" style="width:220px;height:100px;text-transform: capitalize;" readonly="true" cols="35" rows="7"  onchange="setDataChanged();"/></td>
				 --><th><label class="custom-label">Comment</label></th>
				<td><input type="textarea" id="comments" name="comments" style="width:220px;height:100px;text-transform: capitalize;"  cols="35" rows="7"  /></td>
			</tr>
		<tr>
		</tr>
      		<tr >    	
				<td colspan="6" style="font-family: Arial, sans-serif; color: #898989; font-size: 14px; line-height: 1.5;"><b>
				I hereby certify that the details given above are true and correct to the best of my or our knowledge and belief, and nothing has been concealed herein. I or my company will take full responsibility for the conduct and behaviour of the persons engaged by me or our company to work or visit Adani premises. I/we will ensure that they are briefed on all traffic, safety, and security rules and procedures of Adani Group where they have been engaged by us for work. In case of any breach or violation of rules, regulations, safety policy, or other applicable procedures by the above person, we will be solely responsible and liable for suitable action as per Adani Group's safety and security policy
				</b></td>
			</tr>
				<tr >
				<td colspan="4"><input type="checkbox" name="acceptCheck" id="acceptCheck"  />
				<label class="custom-label"> I accept<!-- the <u>Terms and Conditions</u> --></label> 
				</td>
			   </tr>
        <!-- Add any additional rows for Approver Comments -->
                    
                </tbody>
                </table>
            </div>
        </f:form>
    </div>
   
   
</body>
 
</html>