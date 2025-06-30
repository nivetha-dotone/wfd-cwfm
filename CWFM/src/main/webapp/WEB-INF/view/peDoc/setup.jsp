<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Bill Verification Setup</title>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- JS file -->
   <script src="resources/js/cms/principalEmployer.js"></script>

    <style>
        .accordion h3 {
            background-color: #f1f1f1;
            padding: 10px;
            margin: 0;
            border: 1px solid #ccc;
            cursor: pointer;
        }
        .accordion-content {
            display: none;
            border: 1px solid #ccc;
            border-top: none;
            padding: 15px;
            margin-bottom: 10px;
        }
        .accordion-content input[type="text"] {
            margin-bottom: 5px;
        }
        .remove-btn {
            color: red;
            cursor: pointer;
            margin-left: 10px;
        }
        input.error {
    border: 2px solid red !important;
}
        
    </style>
</head>
<body>
    <h2>PrincipalEmployer Documents</h2>

    <form action="saveReportConfig" method="post">
        <div class="accordion">
            <h3 onclick="toggleSection('peDocSection')">Documents</h3>
            <div class="accordion-content" id="peDocSection">
                <div id="documentsContainer">
                    <c:forEach var="report" items="${Documents}">
                        <div class="document-row">
                            <input type="text" name="peDoc" value="${report}" placeholder="Enter Document name" autocomplete="off" style="text-transform:capitalize;"/>
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" onclick="addReportRow()" >Add Documents</button>
            </div>
        </div>
        <br/><br/>
        <button type="button" onclick="saveDocuments()">Save Configuration</button>
        <div id="saveStatusMsg" style="margin-top: 10px; font-weight: bold;"></div>

    </form>
</body>
</html>
