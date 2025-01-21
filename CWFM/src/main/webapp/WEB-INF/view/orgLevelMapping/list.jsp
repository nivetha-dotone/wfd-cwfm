8<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Org Level Mapping</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"> <!-- Adjust the path to your CSS file -->
</head>
<body>
<div class="page-header">
    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToOrgMapAdd()">New</button>
    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="editMapping()">Edit</button>
</div>
<div class="container">
    <table class="table">
        <!-- <thead>
            <tr>
                <th>Short Name</th>
                <th>Long Description</th>
            </tr>
        </thead> -->
       <body>
    <h1>Org Level Set List</h1>

    <c:choose>
        <c:when test="${empty orgLevelMappings}">
            <p>No data to display</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Short Name</th>
                        <th>Long Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="map" items="${mappings}">
                        <tr>
                            <td>${map.shortName}</td>
                            <td>${map.longDescription}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
    </table>
</div>
</body>
</html>
