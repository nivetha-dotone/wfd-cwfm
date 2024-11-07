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
.Tabular {
    border-collapse: collapse;
    width: 100%;
}

.Tabular th, .Tabular td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
}
.custom-input-container {
    padding-left: 10px;
}
</style>
<script>  
var contextPath = '<%= request.getContextPath() %>';


</script>
   </head>
<body>

<div id="principalEmployerContent">

    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                           
                        
                         <th><label class="custom-label"><span class="required-field">*</span>Work order Number</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="sapWorkorderNumber" value="${workorder.sapWorkorderNumber}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="name" value="${principalEmployer.name}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                              <td >  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/workorders/list','Work Order');">Cancel</button>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor Name</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="contractorname" value="${contractor.contractorName}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Contractor Code</label></th>
                            <td><input type="text" name="contractorcode" value="${contractor.contractorCode}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Valid From</label></th>
                            <td><input type="text" name="validFrom" value="${workorder.validFrom}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Valid To</label></th>
                            <td><input type="text" name="validTo" value="${workorder.validTo}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Workorder Type</label></th>
                            <td><input type="text" name="typeId" value="BSR" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Department Name</label></th>
                            <td><input type="text" name="depId" value="Mechanical" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Area Name</label></th>
                            <td><input type="text" name="secId" value="Mechanical" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Cost Centre</label></th>
                            <td><input type="text" name="costCenter" value="${workorder.costCenter}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>GL Code</label></th>
                            <td><input type="text" name="glCode" value="${workorder.glCode}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Job Description</label></th>
                            <td><input type="text" name="job" value="${workorder.glCode}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
        </tbody>
    </table>
    <div class="panel second-child">	
	<table  class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
					<th>Job</th>
					<th>Service Code</th>
					<th>Trade</th>
					<th>Skill</th>
					<th>Item Quantity</th>
					<th>Rate</th>
					<th>Service Entry Qty</th>
				    <th>WBS Code</th>
				    <th>UOM</th>
					
		</tr>
	</thead>
	<tbody>
	<tr>
            <td>ENGG - BLR</td>
            <td>5000001</td>
            <td>Helper</td>
            <td>Skilled</td>
            <td>1</td>
            <td>990</td>
            <td>20</td>
            <td>350</td>
            <td>EA</td>
        </tr>
        <tr>
            <td>HR - BLR</td>
            <td>5000002</td>
            <td>Fitter</td>
            <td>Unskilled</td>
            <td>20</td>
            <td>18</td>
            <td>20</td>
            <td>270</td>
            <td>M3</td>
        </tr>
        <tr>
            <td>QA - BLR</td>
            <td>5000003</td>
            <td>Welder</td>
            <td>Semi Skilled</td>
            <td>10</td>
            <td>456</td>
            <td>10</td>
            <td>310</td>
               <td>NOS</td>
        </tr>
				
	</tbody>
</table>
</div>
</div>

