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
        Principal Employer Edit Page
        <div class="header-buttons">
            <button type="submit" onclick="editPEData(contextPath, '${principalEmployer.UNITID}')">Save</button>
        </div>
    </div>
<input type="hidden" name="id" value="${basicInfo.orgAcctSetId}" />
<label>Short Name:</label>
<input type="text" name="shortName" value="${basicInfo.shortName}" required />

<label>Long Description:</label>
<input type="text" name="longDescription" value="${basicInfo.longDescription}" required />

<!-- Dynamic Tabs -->

<div id="tabs">
    <ul>
        <c:forEach var="level" items="${orgLevelDefs}">
            <li><a href="#tab-${level.orgLevelDefId}">${level.name}</a></li>
        </c:forEach>
    </ul>

    <c:forEach var="level" items="${orgLevelDefs}">
        <div id="tab-${level.orgLevelDefId}" class="tab-content" style="display: none;">
            <h3>${level.name} Mappings</h3>

            <div class="multi-select-container">
                <!-- Available Entries -->
                <div class="multi-select-group">
                    <label for="available-${level.orgLevelDefId}" class="multi-select-label">Available Entries</label>
                    <select id="available-${level.orgLevelDefId}" class="multi-select-box" size="10" multiple>
                        <c:forEach var="entry" items="${level.availableEntries}">
                            <option value="${entry.orgLevelEntryId}">${entry.orgLevelEntryId} - ${entry.shortName}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Buttons to Move Items -->
                <div class="button-group">
                    <button type="button" onclick="moveRight('${level.orgLevelDefId}')">&gt;</button>
                    <button type="button" onclick="moveLeft('${level.orgLevelDefId}')">&lt;</button>
                </div>

                <!-- Selected Entries -->
                <div class="multi-select-group">
                    <label for="selected-${level.orgLevelDefId}" class="multi-select-label">Selected Entries</label>
                    <select id="selected-${level.orgLevelDefId}" class="multi-select-box" size="10" multiple>
                        <%-- <c:forEach var="mapping" items="${selectedMappings}">
                            <c:if test="${mapping.orgLevelEntryId == level.orgLevelDefId}">
                                <option value="${mapping.orgAcctSetId}">${mapping.shortName} - ${mapping.longDescription}</option>
                            </c:if>
                        </c:forEach> --%>
                        <c:forEach var="mapping" items="${selectedMappings}">
    <option value="${mapping.orgAcctSetId}">${mapping.shortName} - ${mapping.longDescription}</option>
</c:forEach>
                        
                    </select>
                    <c:forEach var="mapping" items="${selectedMappings}">
    <p>Mapping Entry ID: ${mapping.orgLevelEntryId} | Level Def ID: ${level.orgLevelDefId}</p>
</c:forEach>
                </div>
            </div>
        </div>
    </c:forEach>
</div>


<button type="submit">Save Changes</button>
