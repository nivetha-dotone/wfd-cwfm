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
   <script src="resources/js/cms/bill.js"></script>

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
    <h2>Bill Verification Setup</h2>

    <form action="saveReportConfig" method="post">

        <div class="accordion">

            <!-- Kronos Reports -->
            <h3 onclick="toggleSection('kronosSection')">Kronos Reports</h3>
            <div class="accordion-content" id="kronosSection">
                <div id="kronosReportsContainer">
                    <c:forEach var="report" items="${kronosReports}">
                        <div class="report-row">
                            <input type="text" name="kronosReports" value="${report}" placeholder="Enter report name" />
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" onclick="addReportRow('kronos')">Add Kronos Report</button>
            </div>

            <!-- Statutory Reports -->
            <h3 onclick="toggleSection('statutorySection')">Statutory Regulatory Attachments</h3>
            <div class="accordion-content" id="statutorySection">
                <div id="statutoryReportsContainer">
                    <c:forEach var="report" items="${statutoryReports}">
                        <div class="report-row">
                            <input type="text" name="statutoryReports" value="${report}" placeholder="Enter report name" />
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" onclick="addReportRow('statutory')">Add Statutory Attachment</button>
            </div>

            <!-- HR Checklist -->
            <h3 onclick="toggleSection('hrChecklistSection')">Checklist HR Clearance</h3>
            <div class="accordion-content" id="hrChecklistSection">
                <div id="checklistConfigContainer">
                    <c:forEach var="item" items="${checklistItems}" varStatus="loop">
                        <div class="checklist-row">
                            <input type="text" name="checklistNames" value="${item.checkpointName}" placeholder="Check Point Name" required />
                            <label><input type="checkbox" name="licenseRequired${loop.index}" ${item.licenseRequired ? "checked" : ""}/> Licence No. Required</label>
                            <label><input type="checkbox" name="validUptoRequired${loop.index}" ${item.validUptoRequired ? "checked" : ""}/> Valid Upto Required</label>
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" onclick="addChecklistRow()">Add Checklist Point</button>
            </div>

        </div>

        <br/><br/>
        <button type="button" onclick="saveReportConfig()">Save Configuration</button>
<div id="saveStatusMsg" style="color: green; margin-top: 10px;"></div>
    </form>
</body>
</html>
