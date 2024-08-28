<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 
  <title>Principal Employeer List</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
      <script src="resources/js/commonjs.js"></script>
     <style>
     thead th {
  font-size: 0.8em;
 
  height: 15px;
}
     </style>
</head>
<body>
<div class="container mt-3"">
        <div class="header-buttons">
      <%--   <button type="submit" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>  --%>
        <button type="submit" onclick="redirectToPEView('${cmSPRINCIPALEMPLOYER.UNITID}')" class="btn btn-sm btn-success">View</button>
         <button type="button" onclick="exportToCSV()" class="btn btn-sm btn-success" >Export</button> 
    
</div>


         
        </div>
        </div>
    <form id="searchForm">
    <input type="text" id="searchInput" name="searchQuery" placeholder="Search...">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')" class="btn btn-sm btn-success">Search</button>
</form>  

<%-- <form id="searchForm">
    <input placeholder="Search" id="search-in-table" onkeypress="return event.keyCode != 13;">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
</form>  --%> 

        <!-- <form action="/CWFM/principalEmployer/search" method="GET">
    <input type="text" name="query" placeholder="Search...">
    <button type="submit">Search</button>
</form> -->

     <form id="updateForm" action="/CWFM/principalEmployer/update" method="POST" >
                         <div class="table-responsive">
    <table id="principalEmployerTable"  class="table table-hover">
        <thead class="table-success">
        
           
<tr>
                    <td >
                        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAll()">
                    </td> 
                   
                    <th    >Name</th>
<th   >Address</th>
<th   >Manager Name</th>
<th   >Manager Address</th>
<th   >Business Type</th>
                    <th    >Max Workmen</th>
                    <th   >Max Contract Workmen</th>
                    <th    >BOCW Applicability</th>
                    <th    >ISMW Applicability</th>
                    <th  >Code</th>
                    <th    >Organization</th>
                    <th    >PF Code</th>
                    <th    >License Number</th>
                    <th    >WC Number</th>
                    <th    >ESIC Number</th>
                    <th    >PTREGNO</th>
                    <th   >LWFREGNO</th>
                    <th   >Factory License Number</th>
                    <th    >ISRC Applicable</th>
                    <th    >RC Number</th>
                    <th   >Attachment Name</th>
                    <th    >State ID</th>
                  
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cmSPRINCIPALEMPLOYERs}" var="cmSPRINCIPALEMPLOYER">
                <tr>
                 <td ><input type="checkbox" name="selectedUnitIds" value="${cmSPRINCIPALEMPLOYER.UNITID}"></td>
                    <%-- <td >${cmSPRINCIPALEMPLOYER.UNITID}</td> --%>
                    <td >${cmSPRINCIPALEMPLOYER.NAME}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ADDRESS}</td>
                    <td >${cmSPRINCIPALEMPLOYER.MANAGERNAME}</td>
                    <td >${cmSPRINCIPALEMPLOYER.MANAGERADDRS}</td>
                    <td >${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}</td>
                    <td >${cmSPRINCIPALEMPLOYER.MAXWORKMEN}</td>
                    <td >${cmSPRINCIPALEMPLOYER.MAXCNTRWORKMEN}</td>
                    <td >${cmSPRINCIPALEMPLOYER.BOCWAPPLICABILITY}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ISMWAPPLICABILITY}</td>
                    <td >${cmSPRINCIPALEMPLOYER.CODE}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ORGANIZATION}</td>
                    <td >${cmSPRINCIPALEMPLOYER.PFCODE}</td>
                    <td >${cmSPRINCIPALEMPLOYER.LICENSENUMBER}</td>
                    <td >${cmSPRINCIPALEMPLOYER.WCNUMBER}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ESICNUMBER}</td>
                    <td >${cmSPRINCIPALEMPLOYER.PTREGNO}</td>
                    <td >${cmSPRINCIPALEMPLOYER.LWFREGNO}</td>
                    <td >${cmSPRINCIPALEMPLOYER.FACTORYLICENCENUMBER}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ISRCAPPLICABLE}</td>
                    <td >${cmSPRINCIPALEMPLOYER.RCNUMBER}</td>
                    <td >${cmSPRINCIPALEMPLOYER.ATTACHMENTNM}</td>
                    <td >${cmSPRINCIPALEMPLOYER.STATEID}</td>
                  <%--   <td >${cmSPRINCIPALEMPLOYER.ISACTIVE}</td> --%>
                    <%-- <td >${cmSPRINCIPALEMPLOYER.UPDATEDTM}</td>
                    <td >${cmSPRINCIPALEMPLOYER.UPDATEDBY}</td> --%>
                </tr>
                <input type="hidden" id="globalUnitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
            </c:forEach>
        </tbody>
    </table>
     </div>
                        </form>
                        <script>
  $(document).ready(function() {
    $('#principalEmployerTable').DataTable({
        "paging": true,       // Enable pagination
        "searching": true,    // Enable searching
        "ordering": true,     // Enable sorting
        "info": true,         // Show table information
        "stripeClasses": ['table-striped', 'table-light']  // Alternating row colors
    });
});
  </script>
</body>
</html>
