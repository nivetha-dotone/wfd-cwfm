<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Org Level Mapping</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   
</head>
<body>
    <h1>Org Level Mapping</h1>
    
     <!-- Form to enter Name and Description -->
     <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <span id="nameError" class="error-message"></span>
    </div>

    <div class="form-group">
        <label for="description">Description:</label>
        <input type="text" id="description" name="description" required>
        <span id="descriptionError" class="error-message"></span>
    </div>
    <button type="button" onclick="saveOrgLevelMapping()">Save</button>

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
                    <div class="multi-select-group">
                        <label for="available-${orgLevel.orgLevelDefId}" class="multi-select-label">Available Entries</label>
                        <select id="available-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple>
                            <c:forEach var="entry" items="${orgLevel.availableEntries}">
                                <option value="${entry.orgLevelEntryId}">${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="button-group">
                        <button type="button" onclick="moveRight('${orgLevel.orgLevelDefId}')">&gt;</button>
                        <button type="button" onclick="moveLeft('${orgLevel.orgLevelDefId}')">&lt;</button>
                        
                    </div>

                    <div class="multi-select-group">
                        <label for="selected-${orgLevel.orgLevelDefId}" class="multi-select-label">Selected Entries</label>
                        <select id="selected-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple>
                            <!-- Dynamically populated when moving items -->
                        </select>
                    </div>
                   
                </div>
            </div>
        </c:forEach>
        
    </div>
</div>




           
</body>
</html>
