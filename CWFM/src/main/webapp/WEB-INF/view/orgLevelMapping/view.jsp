<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit CMSPRINCIPALEMPLOYER</title>
</head>
<body>
    <h1>Edit CMSPRINCIPALEMPLOYER</h1>
    <form action="update" method="post">
        <input type="hidden" id="unitId" name="unitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
        <label for="name">Unit Name:</label>
        <input type="text" id="name" name="name" value="${cmSPRINCIPALEMPLOYER.NAME}" required><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${cmSPRINCIPALEMPLOYER.ADDRESS}" required><br>
        <label for="managerName">Manager Name:</label>
        <input type="text" id="managerName" name="managerName" value="${cmSPRINCIPALEMPLOYER.MANAGERNAME}" required><br>
        <label for="managerAddress">Manager Address:</label>
        <input type="text" id="managerAddress" name="managerAddress" value="${cmSPRINCIPALEMPLOYER.MANAGERADDRS}" required><br>
        <label for="businessType">Type of Business:</label>
        <input type="text" id="businessType" name="businessType" value="${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}" required><br>
        <label for="maxWorkmen">Maximum Number of Workmen:</label>
        <input type="text" id="maxWorkmen" name="maxWorkmen" value="${cmSPRINCIPALEMPLOYER.MAXWORKMEN}" required><br>
        <!-- Add more input fields for other fields if needed -->
        <button type="submit">Update</button>
    </form>
</body>
</html> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
   
   </head>
<body>
<div class="page-header">
 <input type="hidden" name="orgAcctSetId" value="${basicInfo.orgAcctSetId}" />
<label>Short Name:</label>
<input type="text" id="name" name="name" value="${basicInfo.shortName}" readonly required />
<label>Long Description:</label>
<input type="text" id="description" name="description" readonly value="${basicInfo.longDescription}" required />
        <button type="submit"  class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/org-level-mapping/list', 'View')">Cancel</button>
</div>


<!-- Dynamic Tabs -->
<div id="orgMappingContainer">
    <div id="tabs">
        <ul>
            <c:forEach var="orgLevel" items="${orgLevels}">
                <li><a href="#tab-${orgLevel.orgLevelDefId}">${orgLevel.name}</a></li>
            </c:forEach>
        </ul>

        <c:forEach var="orgLevel" items="${orgLevels}">
            <div id="tab-${orgLevel.orgLevelDefId}" class="tab-content" style="display: none;">
                <h3>${orgLevel.name}</h3>

                <div class="multi-select-container">
                    <!-- Available Entries -->
                    <div class="multi-select-group">
                        <label for="available-${orgLevel.orgLevelDefId}" class="multi-select-label">Available Entries</label>
                        <select id="available-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple disabled>
                            <c:forEach var="entry" items="${orgLevel.availableEntries}">
                                <option value="${entry.orgLevelEntryId}">${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Move Buttons -->
                    <div class="button-group">
                        <button type="button" onclick="moveRight('${orgLevel.orgLevelDefId}')"disabled>&gt;</button>
                        <button type="button" onclick="moveLeft('${orgLevel.orgLevelDefId}')"disabled>&lt;</button>
                    </div>

                    <!-- Selected Entries -->
                    <div class="multi-select-group">
                        <label for="selected-${orgLevel.orgLevelDefId}" class="multi-select-label">Selected Entries</label>
                        <select id="selected-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple disabled>
                            <c:forEach var="entry" items="${orgLevel.selectedEntries}">
                                <option value="${entry.orgLevelEntryId}" selected>${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>




