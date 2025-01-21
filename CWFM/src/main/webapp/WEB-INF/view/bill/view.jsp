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
textarea {
            color: gray; /* Set text color to gray */
            width: 300px; /* Optional width */
            height: 150px; /* Optional height */
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
    
    <div id="principalEmployerContent">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">Kronos Reports</button> 
            <button data-target="tab3" onclick="showTabOther('tab3')">Statuory Regulatory Attachments</button> 
            <button data-target="tab4" onclick="showTabOther('tab4')">Check List - HR Clearance</button> 
             <button data-target="tab5" onclick="showTabOther('tab5')">Comments</button> 
            
    </div>
     <div class="action-buttons" >
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/contRegList','Contractor Registration List')">Cancel</button>
    </div>
        <div id="tab1" class="tab-content active">
        
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
           <tbody>
			<tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.transactioId"/></label></th>
                <td><input type="text" name="transactionId" value="${billverification.transactionId}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
                 </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitCode"/></label></th>
                <td><input type="text" name="unitCode" value="${billverification.unitCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitName"/></label></th>
                <td><input type="text" name="unitName" value="${billverification.unitName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
                <td><input type="text" name="vendorCode" value="${billverification.vendorCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td><input type="text" name="contractorName" value="${billverification.contractorName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billStartDate"/></label></th>
                <td><input type="text" name="billStartDate" value="${billverification.billStartDate}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billEndDate"/></label></th>
                <td><input type="text" name="billEndDate" value="${billverification.billEndDate}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderCode"/></label></th>
                <td><input type="text" name="workOrderCode" value="${billverification.workOrderCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billType"/></label></th>
                <td><input type="text" name="billType" value="${billverification.billType}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderValidFrom"/></label></th>
                <td><input type="text" name="workOrderValidFrom" value="${billverification.workOrderValidFrom}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderValidTo"/></label></th>
                <td><input type="text" name="workOrderValidTo" value="${billverification.workOrderValidTo}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
			<tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billCategory"/></label></th>
                <td><input type="text" name="billCategory" value="${billverification.billCategory}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
                 </tr>
			<tr>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.engineeringInCharge"/></label></th>
				<td>
					<select id="value(engincharge)" name="value(engincharge)" style="width: 256px;height: 150px;"></select>
				</td>	
				<td>
				<ul  style="list-style-type:none; padding-top:3px;padding-left:70px;"><br>
					 <li><input type="button" onclick="Add();validateFunction();"  value="Add"  style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li> </br>
                     <li> <input type="button" onclick="AddAll();validateFunction();" value="Add All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li></br>
                     <li> <input type="button" onclick="Remove();validateFunction();" value="Remove" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"  /></li></br>
                     <li> <input type="button" onclick="RemoveAll();validateFunction();" value="Remove All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"   /></li></br> 
      			  </ul> 
				</td>
				<td ><select id="ListBox2" name="ListBox22"    style="width: 265px;height: 150px;">
				 </select></td>
			</tr>
			</tbody>
    </table>
   

        </div>
    <div id="tab2" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <tbody>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.standardMusterRollReport"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('standardMusterRollReport','${billreports.billStartDate }','musterroll')">Download standardMusterRollReport</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.billVerificationAbstractReport"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('billVerificationAbstractReport','${billreports.billStartDate }','billverification')">Download billVerificationAbstractReport</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.wageCostReport"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('wageCostReport','${billreports.billStartDate }','wagecost')">Download wageCostReport</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.bonusReport"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('bonusReport','${billreports.billStartDate }','bonusreport')">Download bonusReport</a>
                </td>
              </tr>
             <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.extraHoursReport"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('extraHoursReport','${billreports.billStartDate }','extrahours')">Download extraHoursReport</a>
                </td>
              
              </tr>
			
           
			</tbody>
                </table>
            </div>
            <div id="tab3" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
            <tbody>
            <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.formA"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('forma','${billreports.billStartDate }','forma')">Download formA</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.formB"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('formb','${billreports.billStartDate }','formb')">Download formB</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.formC"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('formc','${billreports.billStartDate }','formc')">Download formC</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.formD"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('formd','${billreports.billStartDate }','formd')">Download formD</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.ecrpf"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('ecrpf','${billreports.billStartDate }','ecrpf')">Download ecrpf</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.challanandCopyofRemittancePF"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('challanpf','${billreports.billStartDate }','challanpf')">Download challanandCopyofRemittancePF</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.esicpf"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('ecresic','${billreports.billStartDate }','ecresic')">Download esicpf</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.challanandCopyofRemittanceesic"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('challanesic','${billreports.billStartDate }','challanesic')">Download challanandCopyofRemittanceesic</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.bankStatement"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('bankstatement','${billreports.billStartDate }','bankstatement')">Download bankStatement</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.annualReturn"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('annualreturn','${billreports.billStartDate }','annualreturn')">Download annualReturn</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.bonusRegister"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('bonusregister','${billreports.billStartDate }','bonusregister')">Download bonusRegister</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.lwfChallanandRemittance"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('lwfchallan','${billreports.billStartDate }','lwfchallan')">Download lwfChallanandRemittance</a>
                </td>
              </tr>
              <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.challanandCopyofRemittancePT"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('challanpt','${billreports.billStartDate }','challanpt')">Download challanandCopyofRemittancePT</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.userAttachment1"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('userattachment1','${billreports.billStartDate }','userattachment1')">Download userAttachment1</a>
                </td>
              </tr>
		   <tr>
                		<td style="color:black"><label class="custom-label"><spring:message code="label.userAttachment2"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('userattachment2','${billreports.billStartDate }','userattachment2')">Download userAttachment2</a>
                </td>
               <td style="color:black"><label class="custom-label"><spring:message code="label.userAttachment3"/></label></td>
                <td>
                   <a href="#" onclick="downloadDoc('userattachment3','${billreports.billStartDate }','userattachment3')">Download userAttachment3</a>
                </td>
              </tr>
		</tbody>
                </table>
            </div>
        <div id="tab4" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
            <tbody>
		<tr>
    <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><label class="custom-label"><b><spring:message code="label.s.no"/></b></label></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:40%;"><label class="custom-label"><b><spring:message code="label.checkPoints"/></b></label></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:30%;"><label class="custom-label"><b><spring:message code="label.statusy/n"/></b></label></td>
    <td style=" border-top: thin solid; border-bottom: thin solid; text-align: left;width:24%;"><label class="custom-label"><b><spring:message code="label.licenseNoPolicyCodeNo"/></b></label></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:30%;"><label class="custom-label"><b><spring:message code="label.dateofCompilanceValidUpto"/> </b></label></td>
   </tr>
   <tr>
	 <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">1</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Licence copy obtained by Vendor under Contract Labour Act 1970.</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.llStatus}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
	<td><input type="text" name="workOrderValidTo" value="${billhrclearance.llCopy}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.llValidTo}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          		
  </tr> 
 <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">2</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Employee Register ( Form  A ) Rule 2(I)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.empReg}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
  </tr>
 <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">3</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Wages Register (Form B, Under Central Rule)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.wageReg}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">4</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Register of Loan/ Recovery ( Form  C, Under  Central Rule)</label></td>
   <td><input type="text" name="workOrderValidTo" value="${billhrclearance.loanRecovery}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">5</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Attendance Register  (Form D  Under Central Rule)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.attenReg}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
     <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">6</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Unified Annual Return under Contract Labour Act Rules 1971( In the month of December every year)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.annualReturnCL}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">7</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Challan & Remittance Confirmation Slip of PF</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pfSlip}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pfSlipDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">8</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">ECR copy of PF</label></td>
   <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pfEcr}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pfECRDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">9</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Challan & Remittance Confirmation Slip of ESIC</label></td>
   <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pfSlipDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.esicSlipDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">10</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">ECR copy of ESIC</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.esicSlip}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.esicECRDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">11</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Bank Statement of wages paid to workmen submitted</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.bankStmntStatus}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;color: black;">-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" ><label class="custom-label">12</label></td>
    <td  style="text-align: left;border-bottom: thin solid;" ><label class="custom-label">Challan Copy of Professional Tax</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pTaxChallan}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border-bottom: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.pTaxDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
          
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">13</label></td>
    <td  style="text-align: left;border: thin solid;" ><label class="custom-label">Accident Policy under Workmen Compensation Act ,in case of workers drawing salary more than Rs 21,000/= Gross Per Month</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.accidentPolicy}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
    <td style="text-align: center;border: thin solid;color: black;">- </td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">14</label></td>
    <td  style="text-align: left;border: thin solid;" ><label class="custom-label">Labour Welfare Fund Act (Only in the Month of June & December every year)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.laborWelfare}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.labWelFundActDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">15</label></td>
    <td  style="text-align: left;border: thin solid;" ><label class="custom-label">Bonus Register Form C  Rule 4(c ) ( Under Bonus Act 1965)</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.bonusRegFormC}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border: thin solid;color: black;">-</td>
    <td style="text-align: center;border: thin solid;color: black;">-</td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">16</label></td>
    <td  style="text-align: left;border: thin solid;" ><label class="custom-label">Leave with Wages</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.leaveWihtWages}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border: thin solid;color: black;">-</td>
    <td style="text-align: center;border: thin solid;color: black;">-</td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;color: black;" ><label class="custom-label">17</label></td>
    <td  style="text-align: left;border: thin solid;color: black;" ><label class="custom-label">Previous Month Wages paid on</label></td>
    <td><input type="text" name="workOrderValidTo" value="${billhrclearance.preMonWage}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
     <td><input type="text" name="workOrderValidTo" value="${billhrclearance.preMonWageDate}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">18</label></td>
    <td  style="text-align: left;border: thin solid;width: auto;color: black;" ><input type="text"  onchange="setDataChanged();" size="80" maxlength="200" style="width:100% " /></td>
     <td style="text-align: center;border: thin solid;color: black;">-</td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">19</label></td>
    <td  style="text-align: left;border: thin solid;width: auto;color: black;" ><input type="text"   onchange="setDataChanged();" size="80" maxlength="200" style="width:100% "/></td>
     <td style="text-align: center;border: thin solid;color: black;">-</td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
    <td style="text-align: center;border: thin solid;color: black;" >-</td>
  </tr>
  <tr>
    <td style="border: thin solid;text-align: center;" ><label class="custom-label">20</label></td>
    <td  style="border: thin solid;width: auto;color: black;" ><input type="text"   onchange="setDataChanged();" size="80" maxlength="200" style="width:100% " /></td>
     <td style="border: thin solid;color: black;">-</td>
    <td style="border: thin solid;color: black;" >-</td>
    <td style="border: thin solid;color: black;" >-</td>
  </tr>
      </tbody>
     </table>
    </div> 
  <div id="tab5" class="tab-content">
            <div class="Panel">
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
				<th  style="width: 100px;height:20px;"><label class="custom-label"> Action Plan</label></th>
				 <td><input type="text" name="workOrderValidTo" value="${billhrclearance.actionPlan}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
 
		</tr>
			
		
			<tr>
				<th><label class="custom-label"> <spring:message code="label.previousComment"/></label></th>
			<td><input type="text" name="workOrderValidTo" value="${billprecomments.precomments}"  style="width: 265px;height: 150px;"  readonly  /></td>
 	
				<th><label class="custom-label"><spring:message code="label.comment"/></label></th>
				<td><input type="text" value=" "   onchange="setDataChanged();" style="width: 265px;height: 150px;" /></td>
				</tr>
		</tbody>
 </table> 
	
</div>
</div>	


            </div>  
                    
  
</body>
</html>
