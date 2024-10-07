<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CMSPRINCIPALEMPLOYER List</title>
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
        Principal Employer List
        <div class="header-buttons">
        <button type="submit" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        <button type="submit" onclick="redirectToPEView('${cmSPRINCIPALEMPLOYER.UNITID}')">View</button>
         <button type="button" onclick="exportToCSV()">Export</button>
        </div>
        </div>
    <form id="searchForm">
    <input type="text" id="searchInput" name="searchQuery" placeholder="Search...">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
</form>    

        <!-- <form action="/CWFM/principalEmployer/search" method="GET">
    <input type="text" name="query" placeholder="Search...">
    <button type="submit">Search</button>
</form> -->

     <form id="updateForm" action="/CWFM/workmenwage/update" method="POST" >
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
                        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAll()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">Name<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(2)">Address<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(3)">Manager Name<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(4)">Manager Address<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(5)">Business Type<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Max Workmen<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(7)">Max Contract Workmen<span id="sortIndicatorMaxCntrWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(8)">BOCW Applicability<span id="sortIndicatorBocwApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(9)">ISMW Applicability<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(10)">Code<span id="sortIndicatorCode" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(11)">Organization<span id="sortIndicatorOrganization" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(12)">PF Code<span id="sortIndicatorPfCode" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(13)">License Number<span id="sortIndicatorLicenseNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(14)">WC Number<span id="sortIndicatorWcNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(15)">ESIC Number<span id="sortIndicatorEsicNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(16)">PTREGNO<span id="sortIndicatorPtReg" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(17)">LWFREGNO<span id="sortIndicatorLwfReg" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(18)">Factory License Number<span id="sortIndicatorFactoryLicNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(19)">ISRC Applicable<span id="sortIndicatorIsrApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(20)">RC Number<span id="sortIndicatorRcNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(21)">Attachment Name<span id="sortIndicatorAttachment" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(22)">State ID<span id="sortIndicatorStateId" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <!-- Add more headers as needed -->
                <!-- <td style="border: 1px solid black;">ISACTIVE</td>
                <td style="border: 1px solid black;">UPDATEDTM</td>
                <td style="border: 1px solid black;">UPDATEDBY</td> -->
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cmSPRINCIPALEMPLOYERs}" var="cmSPRINCIPALEMPLOYER">
                <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedUnitIds" value="${cmSPRINCIPALEMPLOYER.UNITID}"></td>
                    <%-- <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UNITID}</td> --%>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.NAME}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ADDRESS}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MANAGERNAME}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MANAGERADDRS}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MAXWORKMEN}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MAXCNTRWORKMEN}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.BOCWAPPLICABILITY}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISMWAPPLICABILITY}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.CODE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ORGANIZATION}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.PFCODE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.LICENSENUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.WCNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ESICNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.PTREGNO}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.LWFREGNO}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.FACTORYLICENCENUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISRCAPPLICABLE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.RCNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ATTACHMENTNM}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.STATEID}</td>
                  <%--   <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISACTIVE}</td> --%>
                    <%-- <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UPDATEDTM}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UPDATEDBY}</td> --%>
                </tr>
                <input type="hidden" id="globalUnitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
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
