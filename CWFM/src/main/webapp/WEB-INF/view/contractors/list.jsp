<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contractor</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/contractor.js"></script>
      <script src="resources/js/commonjs.js"></script>
</head>
<body>
<div class="page-header">
        Contractor List
        <div class="header-buttons">
        <!-- <button type="submit">Add</button>  
        <button type="submit" >Edit</button> --> <!--onclick="redirectToContrAdd()" onclick="redirectToContrEdit()" -->
        <button type="submit" onclick="redirectToContrView('${selectedPrincipalEmployerId}')">View</button>
         <button type="button" onclick="ContrExportToCSV()">Export</button>
        </div>
        </div>
   <form action="/CWFM/contractor/list" method="GET">
        <label for="principalEmployerId">Principal Employer:</label>
        
        <select id="principalEmployerId" name="principalEmployerId">
         <option value="">Select Principal Employer</option>
    <c:forEach items="${principalEmployers}" var="principalEmployer">
        <option value="${principalEmployer.UNITID}" ${principalEmployer.UNITID == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.NAME}
        </option>
    </c:forEach>
</select>

 <input type="hidden" id="principalEmployerId" name="principalEmployerId">
        <button type="submit" onclick="searchWithPEInContractor('<%= request.getContextPath() %>')">Search</button>
    </form>

   <table border="1">
        <thead>
            <tr>
             <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAllContrcators()">
                    </td> 
                <th>Contractor Code</th>
                  <th>Name</th>
               <th>Address</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${contList}" var="contList">
                <tr>
                 <%-- <td style="border: 1px solid black;"><input type="checkbox" name="selectedUnitIds" value="${cmSPRINCIPALEMPLOYER.UNITID}"></td>
                 --%>
                 <td><input type="checkbox" name="selectedContractors" value="${contList.contractorId}"></td>
                
                    <td>${contList.code}</td>
                      <td>${contList.name}</td>
            <td>${contList.address}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
