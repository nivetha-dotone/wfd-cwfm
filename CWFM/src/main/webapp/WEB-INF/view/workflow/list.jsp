<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Workflow</title>
   <script src="resources/js/cms/workflow.js"></script>
    <style>
        /* Add your styles here */
        .success {
            color: green;
            font-weight: bold;
            padding: 10px;
            background-color: #e0ffe0;
            border: 1px solid green;
            margin-bottom: 1rem;
        }

        .error {
            color: red;
            font-weight: bold;
            padding: 10px;
            background-color: #ffe0e0;
            border: 1px solid red;
            margin-bottom: 1rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #DDF3FF;
            color: #005151;
        }

        .checkbox-cell input[type="checkbox"] {
            margin: 0;
        }

        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
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
         .success {
        color: green;
        font-weight: bold;
        padding: 10px;
        background-color: #e0ffe0;
        border: 1px solid green;
        margin-bottom: 1rem;
    }
    .error {
        color: red;
        font-weight: bold;
        padding: 10px;
        background-color: #ffe0e0;
        border: 1px solid red;
        margin-bottom: 1rem;
    }
 label {
    color: black;
}
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
        width: 30%;
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
        justify-content: flex-start; /* Align elements to the left */
    gap: 10px;  /* Distribute space between search and buttons */
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
            width: 100%; 
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
   	 <label for="gmTypeId">Select Module:</label>
         <select id="gmTypeId" name="gmTypeId" onchange="getModuleData()" required>
        <option value="">-- Select Module --</option>
        <c:forEach items="${Modules}" var="module">
            <option value="${module.gmTypeId}" <c:if test="${module.gmTypeId == gmTypeId}">selected</c:if>>${module.gmType}</option>
        </c:forEach>
    </select>
        <button type="button" class="btn btn-default"  onclick="addNewRow()">Add New Row</button>
        <button type="submit" class="btn btn-default" onclick="submitOrgLevel()">Save</button>
        <button type="submit" class="btn btn-default" onclick="deleteSelectedOrgLevel()">Delete Selected</button>
        <button type="submit" class="btn btn-default" onclick="exportOrgLevelCSV()">Export</button>
    </div>

       <div class="table-container">
        <form method="post" action="/save">
    <table id="orgLevelTable">
         <thead>
                <tr>
                    <th class="checkbox-cell">
                        <input type="checkbox" id="selectAllOrgLevelCheckbox" onchange="toggleSelectAllOrgLevel()">
                    </th>
                    <th>Org Level Name</th>
                    <th>Short Name</th>
                    <th>Hierarchy</th>
                </tr>
            </thead>
            <tbody>
                <!-- Render rows from server -->
                <c:forEach items="${orgLevels}" var="orgLevel">
                    <tr data-row-id="${orgLevel.orgLevelDefId}">
                        <td class="checkbox-cell">
                        <input type="hidden" name="orgLevelDefId[]" value="${orgLevel.orgLevelDefId}">
                            <input type="checkbox" name="selectedOrgLevels" value="${orgLevel.name}" data-row-id="${orgLevel.orgLevelDefId}">
                        </td>
                        <td><input type="text" name="orgLevelName[]" value="${orgLevel.name}" required></td>
                        <td><input type="text" name="shortName[]" value="${orgLevel.shortName}" required></td>
                        <td><input type="number" name="hierarchy[]" value="${orgLevel.orgHierarchyLevel}" required></td>
                        
                    </tr>
                </c:forEach>

                <!-- Default empty row when no data -->
                <c:if test="${orgLevels == null || orgLevels.isEmpty()}">
                    <tr>
                        <td class="checkbox-cell">
                            <input type="checkbox" name="selectedOrgLevels">
                        </td>
                        <td><input type="text" name="orgLevelName[]" placeholder="Org Level Name" required></td>
                        <td><input type="text" name="shortName[]" placeholder="Short Name" required></td>
                        <td><input type="number" name="hierarchy[]" placeholder="Hierarchy" required></td>
                    </tr>
                </c:if>
            </tbody>
    </table>
</form>

    </div>

</body>
</html>
