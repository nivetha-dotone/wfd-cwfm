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
<script>
        function showFieldsBasedOnRole() {
            var role = document.getElementById("userRole").value;
            var unitField = document.getElementById("unitField");
            var contractorField = document.getElementById("contractorField");
            var departmentField = document.getElementById("departmentField");

            // Hide all fields first
            unitField.style.display = "none";
            contractorField.style.display = "none";
            departmentField.style.display = "none";

            // Show fields based on role
            if (role === "admin") {
                // Admin role - no need to display unit and contractor
            } else if (role === "EIC") {
                // EIC role - unit and department are mandatory
                unitField.style.display = "block";
                departmentField.style.display = "block";
            } else if (role === "contractor supervisor") {
                // Contractor Supervisor role - unit and contractor are mandatory
                unitField.style.display = "block";
                contractorField.style.display = "block";
            } else {
                // Default - show unit field only
                unitField.style.display = "block";
            }
        }
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
<f:form id="addPEForm" action="/CWFM/users/add" modelAttribute="user" method="post" onsubmit="return validateForm()" >
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
                         <th><label class="custom-label"><span class="required-field">*</span>Login ID</label></th>
<td>
    <div>
        <f:input type="text" id="loginId" path="loginId" style="height: 20px;" size="30" maxlength="30"/>
       <%--  <f:errors path="NAME" cssClass="error-message"/> --%>
    </div>
</td>
                            <th><label class="custom-label"><span class="required-field">*</span>Username</label></th>
                        <td>
                        <f:input type="text" id="username" path="username" style="height: 20px;" size="30" maxlength="30"/>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Password</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="password" id="password" path="password"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Contact Email</label></th>
                            <td><f:input type="text" path="contactEmail" name="contactEmail" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contact Number</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="text" id="contactNumber" path="contactNumber"  style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                        </tr>
                        <tr>
                       <th><label class="custom-label"><span class="required-field">*</span>User Role</label></th>
   <td> <select name="userRole" id="userRole" onchange="showFieldsBasedOnRole()">
        <c:forEach items="${roles}" var="role">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select></td>
    </tr>
    <div id="unitField" style="display: none;">
    <tr>
         <th><label class="custom-label"><span class="required-field">*</span>Unit</label></th>
         <td><f:input type="text" path="unitId"  style="height: 20px;"  size="30" maxlength="30" /></td>
     </tr>            
    </div>
    <div id="contractorField" style="display: none;">
          <tr>
         <th><label class="custom-label"><span class="required-field">*</span>Contractor ID</label></th>
         <td><f:input type="text" path="contractorId"  style="height: 20px;"  size="30" maxlength="30" /></td>
     </tr> 
    </div>
    <div id="departmentField" style="display: none;">
         <tr>
         <th><label class="custom-label"><span class="required-field">*</span>Department</label></th>
         <td><f:input type="text" path="departmentId"  style="height: 20px;"  size="30" maxlength="30" /></td>
     </tr> 
    </div>
        </tbody>
    </table>
    </f:form>
</div>

