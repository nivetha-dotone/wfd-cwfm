<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Org Level Set Mapping</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    
   <script></script>
   
</head>
<body>
    <h1>Org Level Set Mapping</h1>
<!-- <h1>Create/Edit - Org Level Set Mapping</h1> -->
    
    <!-- Form to enter Name and Description -->
      <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveOrgLevelMappings()">Save</button>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        
        <label for="description">Description:</label>
        <input type="text" id="description" name="description" required>
        
      <!--   <input type="submit" value="Save"> -->
    <div id="tabs">
        <!-- Tabs Header -->
        <ul>
            <c:forEach var="orgLevel" items="${orgLevels}">
                <li><a href="#tab-${orgLevel.orgLevelDefId}">${orgLevel.name}</a></li>
            </c:forEach>
        </ul>

        <!-- Tabs Content -->
       <c:forEach var="orgLevel" items="${orgLevels}">
    <div class="tab" id="tab-${orgLevel.orgLevelDefId}">
        <h4>${orgLevel.name} - ${orgLevel.shortName}</h4>
        
        <!-- Available Entries -->
        <div class="multi-select-container">
            <label>Available Entries</label>
            <select id="available-${orgLevel.orgLevelDefId}" class="custom-select" multiple>
                <c:forEach var="entry" items="${orgLevel.availableEntries}">
                    <option value="${entry.orgLevelDefId}">${entry.name}</option>
                </c:forEach>
            </select>
            
            <div class="button-row">
                <button type="button" onclick="moveRight(${orgLevel.orgLevelDefId})"> > </button>
                <button type="button" onclick="moveRight(${orgLevel.orgLevelDefId})"> >> </button>
                <button type="button" onclick="moveLeft(${orgLevel.orgLevelDefId})"> < </button>
                <button type="button" onclick="moveLeft(${orgLevel.orgLevelDefId})"> << </button>
            </div>
        
        
        <!-- Selected Entries -->
        <div class="multi-select-container">
            <label>Selected Entries</label>
            <select id="selected-${orgLevel.orgLevelDefId}" class="custom-select" multiple>
                <c:forEach var="entry" items="${orgLevel.selectedEntries}">
                    <option value="${entry.orgLevelDefId}">${entry.name}</option>
                </c:forEach>
            </select>
        </div>
        </div>
    </div>
</c:forEach>

    </div>

    <!-- Submit Button -->
   <%--  <form action="/CWFM/saveOrgLevelEntries" method="post">
        <button type="submit">Save</button>
    </form> --%>
</body>
</html>
