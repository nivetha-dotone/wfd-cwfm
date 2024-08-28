<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workorder List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
      <script src="resources/js/commonjs.js"></script>
      <script>
     
    </script>
      <style>
  /*  #principalEmployerTable {
    border-collapse: collapse;
    width: 100%;
}

#principalEmployerTable th,
#principalEmployerTable td {
    border: 1px solid black;
    padding: 8px; 
}

/* Header styles */
/* #principalEmployerTable th {
    background-color: lightgreen; 
} */

/* Row styles */
/* .row-odd {
    background-color: lightgray; 
}

.row-even {
    background-color: lightblue; 
}  */*/
      </style>
</head>
<body>
<div class="page-header">
        Workorder List
        <div class="header-buttons">
        <%-- <button type="submit" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button> --%>
        <button type="submit" onclick="redirectToWOView()">View</button>
         <button type="button" onclick="woListExportToCSV()">Export</button>
        </div>
        </div>
    <form id="searchForm"  class="search-container">
    <label for="principalEmployerId">Principal Employer :</label>
        
        <select id="principalEmployerId" name="principalEmployerId">
         <option value="">Select Principal Employer</option>
    <c:forEach items="${principalEmployers}" var="principalEmployer">
        <option value="${principalEmployer.UNITID}" ${principalEmployer.UNITID == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.NAME}
        </option>
    </c:forEach>
</select>

 <label for="contractorId">Contractor :</label>
        
        <select id="contractorId" name="contractorId">
         <option value="">Select Contractor</option>
    <c:forEach items="${contractors}" var="contractor">
        <option value="${contractor.contractorId}" ${contractor.contractorId == selectedContractorId ? 'selected' : ''}>
            ${contractor.name}
        </option>
    </c:forEach>
</select>
<input type="hidden" id="principalEmployerId" name="principalEmployerId">
<input type="hidden" id="contractorId" name="contractorId">
        <button type="submit" onclick="searchWithPEContractorInWO('<%= request.getContextPath() %>')">Search</button>
   <%--  <input type="text" id="searchInput" name="searchQuery" placeholder="Search...">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button> --%>
</form>    

        <!-- <form action="/CWFM/principalEmployer/search" method="GET">
    <input type="text" name="query" placeholder="Search...">
    <button type="submit">Search</button>
</form> -->

     <form id="updateForm" action="/CWFM/workorders/update" method="POST" >
                         <div class="table-container">
    <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
        <thead>
        
            <!-- <tr>
            <td style="border: 1px solid black;">
    <label class="toggle">
        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAll()">
        <span class="slider"></span>
    </label>
</td> -->
<tr>
                    <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllWOCheckbox" onchange="toggleSelectAllWOS()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">Workorder Number<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(2)">Job<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(3)">Workorder Type<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(4)">Area Name<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(5)">Valid From<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Valid To<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(7)">Contractor Name<span id="sortIndicatorMaxCntrWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(8)">Vendor Code<span id="sortIndicatorBocwApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(9)">Unit Name<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(10)">Status<span id="sortIndicatorCode" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <!-- Add more headers as needed -->
                <!-- <td style="border: 1px solid black;">ISACTIVE</td>
                <td style="border: 1px solid black;">UPDATEDTM</td>
                <td style="border: 1px solid black;">UPDATEDBY</td> -->
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${woList}" var="wo">
                <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedWOs" value="${wo.workOrderId}"></td>
                    <%-- <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UNITID}</td> --%>
                    <td style="border: 1px solid black;">${wo.sapWorkOrderNum}</td>
                    <td style="border: 1px solid black;">${wo.typeId}</td>
                    <td style="border: 1px solid black;">${wo.typeId}</td>
                    <td style="border: 1px solid black;">${wo.secId}</td>
                    <td style="border: 1px solid black;">${wo.validFrom}</td>
                    <td style="border: 1px solid black;">${wo.validDt}</td>
                    <td style="border: 1px solid black;">${contractor.name}</td>
                    <td style="border: 1px solid black;">${contractor.code}</td>
                    <td style="border: 1px solid black;">${principalEmployer.NAME}</td>
                    <td style="border: 1px solid black;">${wo.status}</td>
                </tr>
                <input type="hidden" id="globalUnitId" value="${wo.workOrderId}">
            </c:forEach>
        </tbody>
    </table>
     </div>
                        </form>
               <!--          <div id="paginationButtons">
    <button type="button" id="prevPageBtn">Previous</button>
    <button type="button" id="nextPageBtn">Next</button>
</div> -->
</body>
</html>
