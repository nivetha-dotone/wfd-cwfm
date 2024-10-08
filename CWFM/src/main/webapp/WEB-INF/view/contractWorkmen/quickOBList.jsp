<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aadhar based Workmen on-boarding List</title>
      <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
 <style>
 
 
    body {
        background-color: #FFFFFF; /* White background for the page */
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
    }

    .action-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        background-color: #f8f8f8;
        margin-bottom: 1rem;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .action-buttons button {
        padding: 0.5rem 1rem;
        font-size: 1rem;
        cursor: pointer;
    }

    #searchForm {
        display: flex;
        align-items: center;
        flex-grow: 1;
        margin-right: 10px;
    }

    .search-box {
        width: 200px; /* Adjust width to fit layout */
        padding: 0.25rem; /* Reduced padding for height */
        font-size: 0.875rem; /* Smaller font size */
        border: 1px solid #ccc; /* Border to match design */
        border-radius: 4px; /* Slightly rounded corners */
        outline: none; /* Remove default outline */
        margin-right: 10px; /* Space between input and button */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }

    .table-container {
        overflow-x: auto;
        margin: 0; /* Remove space before the table */
        padding: 0; /* Remove padding if any */
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
    }

    th {
        background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
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

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column; /* Stack items vertically on small screens */
            align-items: flex-start; /* Align items to the start */
        }

        #searchForm {
            width: 100%; /* Full width for small screens */
            margin-right: 0; /* Remove margin on small screens */
        }

        .search-box {
            width: 100%; /* Full width for small screens */
        }

        .page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
    }
    .header-text-new {
        font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
        font-size: 14px; /* Adjusted font size to match typical grid header size */
        font-weight: 600; /* Bold text for prominence */
        border: 1px solid #ddd; /* Lighter border for a cleaner look */
        white-space: nowrap; /* Prevent text from wrapping */
        padding: 8px 10px; /* Adjusted padding for better spacing */
          background-color: #E0E0E0;  /* Light background color to match grid header */
        color: #333; /* Text color for readability */
    }
       table th {
        border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis); /* Top border color */
        border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis); /* Bottom border color */
        border-right: none; /* No right border */
        background-color: #DDF3FF; /* Light green background color */
        color: var(--zed_sys_color_tableHeader_text); /* Text color */
        font-size: 0.75rem; /* Smaller font size */
        line-height: 1.2rem; /* Reduced line height */
        letter-spacing: normal; /* Letter spacing */
        font-family: 'Noto Sans', sans-serif; /* Font family */
        font-weight: 400; /* Font weight */
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
</style>
</head>
<body>
<div class="page-header">
    <form id="searchForm">
        <input type="text" class="search-box ng-pristine ng-untouched ng-valid ng-empty" id="searchInput" name="searchQuery" placeholder="Search...">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
    </form>
    <div>
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToWorkmenView()">View</button>
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportToCSV()">Export</button>
    </div>
</div>
<%-- <div class="page-header">
       Quick on-boarding List
        <div class="header-buttons">
        <button type="submit" onclick="redirectToWorkmenQuickOBAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button> 
        <button type="submit" onclick="redirectToWOView()">View</button>
         <button type="button" onclick="woListExportToCSV()">Export</button>
        </div>
        </div> --%>
    <%-- <form id="searchForm">
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
        <button type="submit" onclick="searchWithPEContractorInWorkmenAadharList('<%= request.getContextPath() %>')">Search</button>
</form> --%>
     <form id="updateForm" action="/CWFM/workorders/update" method="POST" >
                         <div class="table-container">
    <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
        <thead>
<tr>
                    <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllAadharWorkmenCheckbox" onchange="toggleSelectAllAadharWorkmen()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">GatePass Id<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(2)">First Name<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(3)">Last Name<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(4)">Gender<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(5)">Date of Birth<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Aadhaar Number<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(7)">Contractor Name<span id="sortIndicatorMaxCntrWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(8)">Vendor Code<span id="sortIndicatorBocwApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(9)">Unit Name<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th> 
                    <th class="header-text"  onclick="sortTable(10)">Status<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${contractorWorkmen}" var="wo">
               <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedWOs" value="${wo.gatePassId}"></td>
                    <td style="border: 1px solid black;">${wo.gatePassId}</td>
                    <td style="border: 1px solid black;">${wo.firstName}</td>
                    <td style="border: 1px solid black;">${wo.lastName}</td>
                    <td style="border: 1px solid black;">${wo.gender}</td>
                    <td style="border: 1px solid black;">${wo.dateOfBirth}</td>
                     <td style="border: 1px solid black;">${wo.aadhaarNumber}</td>
                     
                    <td style="border: 1px solid black;">${wo.contractorName}</td>
                    <td style="border: 1px solid black;">${wo.vendorCode}</td>
                    <td style="border: 1px solid black;">${wo.unitName}</td> 
                    <td style="border: 1px solid black;">${wo.status}</td>
                    <%--<td style="border: 1px solid black;">${principalEmployer.NAME}</td>
                    <td style="border: 1px solid black;">${wo.status}</td> --%>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
                        </form>
                         </div>
</body>
</html>
