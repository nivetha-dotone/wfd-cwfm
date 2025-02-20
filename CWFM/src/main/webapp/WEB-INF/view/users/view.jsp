<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Users View</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="resources/js/jquery.min.js"></script>
    <!-- <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>  -->

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
/* th label {
    text-align: left;
    display: block;
    font-weight: normal; /* Remove bold effect */
} */
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    /* Add spacing between <td> elements */
    /* td {
        padding-bottom: 10px; /* Adjust the value as needed */
    } */
   .custom-label {
    font-family: 'Noto Sans', sans-serif;
    text-align: left;
    /* display: block; */
    margin-bottom: 5px;
     color: gray;/* Label text color */ 
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
      .page-header {
        display: flex;
        align-items: center;
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
    }
    label {
    vertical-align: middle; /* Align label text with radio button */
    display: inline-block; /* Ensure label appears on the same line as radio button */
    color: gray; /* Set the text color to a dark shade */
    font-family: Arial, sans-serif;
}
textarea {
            color: gray; /* Set text color to gray */
            width: 300px; /* Optional width */
            height: 150px; /* Optional height */
        }
    </style>
</head>

<body>
    
<!-- <h1>Organization Level Entry</h1> -->
<div class="page-header">
  <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="goBackToUserList()">Return</button>
</div>
   <form id="userFormID">
    <table class="ControlLayout">
        <!-- Form fields -->
         <%-- <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractFrom"/></label></th>
                <td><input type="text" name="contractFrom" value="${principalEmployer.contractFrom}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractTo"/></label></th>
                <td><input type="text" name="contractTo" value="${principalEmployer.contractTo}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr> --%>
        <tr>
            <th><label class="custom-label"><span class="required-field"></span>First Name:</label></th>
            <td><input type="text" name="firstName" value="${users.firstName}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
            <th><label class="custom-label"><span class="required-field"></span>Last Name:</label></th>
            <td><input type="text" name="lastName" value="${users.lastName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
        </tr>
        <tr>
             <th><label class="custom-label"><span class="required-field"></span>Email:</label></th>
        <td><input type="email" name="emailId" value="${users.emailId}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            <th><label class="custom-label"><span class="required-field"></span>Contact Number:</label></th>
            <td><input type="text" name="contactNumber" value="${users.contactNumber}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
        </tr>
        <tr>
            <th><label class="custom-label"><span class="required-field"></span>Password:</label></th>
            <td><input type="password" name="password" value="${users.password}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
            <th><label class="custom-label"><span class="required-field"></span>User Account:</label></th>
            <td><input type="text" name="userAccount" value="${users.userAccount}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
        </tr>
        <tr> 
    <th><label class="custom-label"><span class="required-field"></span>Roles:</label></th>
    <td>
        <table style="width: 100%;">
            <tr>
                <th style="text-align: left;">Select</th>
                <th style="text-align: left;">Role Name</th>
            </tr>
            <c:forEach var="role" items="${roles}">
                <c:set var="isChecked" value="false"/>
                
                <!-- Loop through assignedRoles to check if the role is assigned -->
                <c:forEach var="assignedRole" items="${assignedRoles}">
                    <c:if test="${assignedRole == role.gmId}">
                        <c:set var="isChecked" value="true"/>
                    </c:if>
                </c:forEach>

                <tr>
                    <td>
                        <input type="checkbox" name="roleIds" value="${role.gmId}" 
                               <c:if test="${isChecked}">checked</c:if> disabled />
                    </td>
                    <td>${role.gmName}</td>
                </tr>
            </c:forEach>
        </table>
    </td>
</tr>
    </table>
</form>



<!-- Form for Adding/Editing Entries -->
<!-- <h3>Add Entry</h3> -->
<%-- <div>
    <input type="hidden" name="orgLevelEntryId" id="orgLevelEntryId" value="0">
    <input type="hidden" name="orgLevelDefId" id="orgLevelDefId" value="${param.orgLevelDefId}">

    <label for="entryName">Entry Name:</label>
    <input type="text" name="entryName" id="entryName" required>

    <label for="description">Description:</label>
    <input type="text" name="description" id="description">

    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveOrgEntries()">Save</button>
</div>
<hr/> --%>

<!-- Table for displaying entries -->
<%-- <table border="1" id="table-body">
    <thead>
        <tr>
            <th>ID</th>
            <th>Entry Name</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty entries}">
                <c:forEach var="entry" items="${entries}">
                    <tr id="row-${entry.orgLevelEntryId}">
                        <td>${entry.orgLevelEntryId}</td>
                        <td>${entry.name}</td>
                        <td>${entry.description}</td>
                        <td>
                            <button onclick="editEntry(${entry.orgLevelEntryId}, '${entry.name}', '${entry.description}', ${param.orgLevelDefId})">Edit</button>
                            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteOrgLevelEntry(${entry.orgLevelEntryId}, ${orgLevelDefId})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No entries available for the selected organization level.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table> --%>
</body>
</html>
