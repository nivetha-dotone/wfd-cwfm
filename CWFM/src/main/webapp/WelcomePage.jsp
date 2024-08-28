<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
    
    <title>User Home</title>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
    <script src="resources/js/cms/users.js"></script>
       <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
<!--  <link rel="stylesheet" href="resources/bootstrap-4.5.3/css/bootstrap.min.css">
 <script src="resources/bootstrap-4.5.3/js/bootstrap.min.js"></script> -->
  <style>
  
    </style>
<script>
/* function loadRunReport(contextPath) {
    var url = contextPath + '/reportSetup/getReports';
    console.log("Constructed URL:", url);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                try {
                    var responseData = JSON.parse(this.responseText);
                    console.log("Received JSON data:", responseData);

                    // Get the ul element for the run reports sublist
                    var runReportsSublist = document.getElementById("run-reports-sublist");
                    
                    // Clear any existing content
                    runReportsSublist.innerHTML = "";

                    // Iterate through the reports and create list items
                    responseData.forEach(function(report) {
                        var li = document.createElement("li");
                        var a = document.createElement("a");
                        a.href = "#";
                        a.textContent = report.rptName;
                        li.appendChild(a);
                        runReportsSublist.appendChild(li);
                    });
                    
                    // Display the sublist
                    runReportsSublist.style.display = "block";
                } catch (error) {
                    console.error("Error parsing JSON:", error);
                }
            } else {
                console.error("HTTP error:", this.status);
                document.getElementById("mainContent").innerHTML = "HTTP error: " + this.status;
            }
        }
    };

    xhttp.open("GET", url, true);
    xhttp.send();
} */
/* 
function loadRunReport(contextPath) {
    var url = contextPath + '/reportSetup/getReports';
    console.log("Constructed URL:", url);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                try {
                    var responseData = JSON.parse(this.responseText);
                    console.log("Received JSON data:", responseData);

                    // Get the ul element for the run reports sublist
                    var runReportsSublist = document.getElementById("run-reports-sublist");
                    
                    // Clear any existing content
                    runReportsSublist.innerHTML = "";

                    // Iterate through the reports and create list items
                    responseData.forEach(function(report) {
                        var li = document.createElement("li");
                        var a = document.createElement("a");
                        a.href = "#";
                        a.textContent = report.rptName;
                        a.onclick = function() {
                            redirectToController(report.rptRefId); // Redirect to controller with report ID
                        };
                        li.appendChild(a);
                        runReportsSublist.appendChild(li);
                    });
                    
                    // Toggle the display of the sublist when clicking on "Run Reports"
                    var runReportsLink = document.querySelector("#run-reports-sublist > li > a");
                    runReportsLink.onclick = function() {
                        toggleRunReportsSubMenu();
                    };

                    // Display the sublist
                    runReportsSublist.style.display = "block";
                } catch (error) {
                    console.error("Error parsing JSON:", error);
                }
            } else {
                console.error("HTTP error:", this.status);
                document.getElementById("mainContent").innerHTML = "HTTP error: " + this.status;
            }
        }
    };

    xhttp.open("GET", url, true);
    xhttp.send();
}

function toggleRunReportsSubMenu() {
    var runReportsSublist = document.getElementById("run-reports-sublist");
    var display = runReportsSublist.style.display;
    runReportsSublist.style.display = (display === "block") ? "none" : "block";
}

function redirectToController(reportId) {
    // Make an AJAX request to the server to fetch report data
    $.ajax({
        type: "GET",
        url: "/CWFM/reportSetup/reportPreferences",
        data: { reportRefId: reportId }, // Send report ID as a parameter
        success: function(response) {
            // Update HTML elements with the received report data
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading report:", error);
        }
    }); bootstrap CMSPRINCIPALEMPLOYER
} */
/* document.addEventListener("DOMContentLoaded", function() {
    var exportDropdownBtn = document.getElementById("exportDropdownBtn");
    var exportDropdownMenu = document.getElementById("exportDropdownMenu");
    
    if (exportDropdownBtn && exportDropdownMenu) {
        exportDropdownBtn.addEventListener("click", function() {
            exportDropdownMenu.classList.toggle("show");
        });
    }
}); */
/* function exportData(format) {
    var selectedRows = document.querySelectorAll('input[name="selectedUnitIds"]:checked');
    if (selectedRows.length === 0) {
        alert("Please select at least one record to export.");
        return;
    }
    var content = "";
    if (format === 'csv') {
        // CSV export logic
    } else if (format === 'excel') {
        // Excel export logic
    }

    var encodedUri = encodeURI(content);
    var link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    if (format === 'csv') {
        link.setAttribute("download", "PrincipalEmployerList.csv");
    } else if (format === 'excel') {
        link.setAttribute("download", "PrincipalEmployerList.xlsx");
    }
    document.body.appendChild(link);
    link.click();
}
 */
$(document).ready(function() {
    // Add row
    $("#addRow").on("click", function() {
        var $row = $("#rowTemplate").clone();
        $row.removeAttr("id").removeAttr("style");
        $row.find(".removeRow").on("click", function() {
            $(this).closest("tr").remove();
        });
        $("#dataTable tbody").append($row);
    });

    // Remove row
    $(".removeRow").on("click", function() {
        $(this).closest("tr").remove();
    });
});

function openFilePicker() {
    document.getElementById('fileInput').click();
}
function handleFileSelect(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    
    reader.onload = function(event) {
        const imgElement = document.getElementById('imageId');
        imgElement.src = event.target.result;
    };
    
    reader.readAsDataURL(file);
}
function toggleSubOnboard() {
    var submenu = document.querySelector('.sub-onboard');
    submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    var mainmenuLink = document.querySelector('.onboard-link');
    mainmenuLink.classList.toggle('active');

    var arrowUpIcon = document.querySelector('.onboard-link .arrow-up');
    var arrowDownIcon = document.querySelector('.onboard-link .arrow-down');
    if (submenu.style.display === 'block') {
        arrowUpIcon.style.display = 'inline-block';
        arrowDownIcon.style.display = 'none';
    } else {
        arrowUpIcon.style.display = 'none';
        arrowDownIcon.style.display = 'inline-block';
    }
}

function loadCommonList(contextPath,path) {

    // Construct the URL using the contextPath variable
    var url = contextPath + path;
    console.log("Constructed URL:", url); // Log the constructed URL to the console
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        	console.error("Error loading report:",this.responseText);
            document.getElementById("mainContent").innerHTML = this.responseText;
              resetSessionTimer();
        }
        
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}
/* $(function() {
    $('#principalEmployerId').on('change', function() {
        console.log('Dropdown value changed');
        var principalEmployerId = $(this).val();
        console.log('Selected Principal Employer ID:', principalEmployerId);
        $.ajax({
            type: 'GET',
            url: '/workorders/contractors', // Endpoint to fetch contractors based on principal employer
            data: { principalEmployerId: principalEmployerId }, // Send principal employer id as data
            success: function(data) {
                console.log(data); // Print the response data to the console
                $('#contractorId').empty(); // Clear existing options in contractor dropdown
                $.each(data, function(index, contractor) {
                    // Append new options for contractors
                    $('#contractorId').append('<option value="' + contractor.contractorId + '">' + contractor.name + '</option>');
                });
            }
        });
    });
}); */
$(document).on('change', '#principalEmployerId', function() {
    console.log('Dropdown value changed');
    var principalEmployerId = $(this).val();
    console.log('Selected Principal Employer ID:', principalEmployerId);
    $.ajax({
        type: 'GET',
        url: '/CWFM/workorders/contractors', // Endpoint to fetch contractors based on principal employer
        data: { principalEmployerId: principalEmployerId }, // Send principal employer id as data
        success: function(data) {
            console.log(data); // Print the response data to the console
            $('#contractorId').empty(); // Clear existing options in contractor dropdown
            $('#contractorId').append('<option value="">Select Contractor</option>');
            
            
            $.each(data, function(index, contractor) {
                // Append new options for contractors
                $('#contractorId').append('<option value="' + contractor.contractorId + '">' + contractor.name + '</option>');
            });
        }
    });
});

function loadRunReport(contextPath) {
    var url = contextPath + '/reportSetup/getReports';
    console.log("Constructed URL:", url);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                try {
                    var responseData = JSON.parse(this.responseText);
                    console.log("Received JSON data:", responseData);

                    // Get the ul element for the run reports sublist
                    var runReportsSublist = document.getElementById("run-reports-sublist");

                    // Clear any existing content
                    runReportsSublist.innerHTML = "";

                    // Iterate through the reports and create list items
                    responseData.forEach(function(report) {
                        var li = document.createElement("li");
                        var a = document.createElement("a");
                        a.href = "#";
                        a.textContent = report.rptName;
                        a.onclick = function() {
                           // redirectToController(report.rptRefId); // Redirect to controller with report ID
                            fetchReportParameters(contextPath, report.rptRefId); // Fetch report parameters
                        };
                        li.appendChild(a);
                        runReportsSublist.appendChild(li);
                    });

                    // Toggle the display of the sublist when clicking on "Run Reports"
                    var runReportsLink = document.querySelector("#run-reports-sublist > li > a");
                    runReportsLink.onclick = function() {
                        toggleRunReportsSubMenu();
                    };

                    // Display the sublist
                    runReportsSublist.style.display = "block";
                } catch (error) {
                    console.error("Error parsing JSON:", error);
                }
            } else {
                console.error("HTTP error:", this.status);
                document.getElementById("mainContent").innerHTML = "HTTP error: " + this.status;
            }
        }
    };

    xhttp.open("GET", url, true);
    xhttp.send();
}
function fetchReportParameters(contextPath, reportRefId) {
    // Make an AJAX request to fetch report parameters based on the selected report reference ID
    $.ajax({
        type: "GET",
        url: contextPath + "/reportSetup/fetchReportParameters",
        data: { reportRefId: reportRefId },
        success: function(response) {
            // Load the runReport.jsp into the mainContent div
             console.error("response fetching report parameters:", response);
             document.getElementById("mainContent").innerHTML = response;
           // $("#mainContent").load(contextPath + "/reports/reportRun");
        },
        error: function(xhr, status, error) {
            console.error("Error fetching report parameters:", error);
        }
    });
}


/* function fetchReportParameters(contextPath, reportRefId) {
    // Make an AJAX request to fetch report parameters based on the selected report reference ID
    $.ajax({
        type: "GET",
        url: contextPath + "/reportSetup/fetchReportParameters",
        data: { reportRefId: reportRefId },
        success: function(response) {
            console.log("Received report parameters:", response); // Log the received data
            
            // Clear the existing content of the mainContent div
            $("#mainContent").empty();
            
            // Initialize variables to hold report name and description
            var rptName = "";
            var rptDescription = "";

            // Check if the response is a string (indicating an error message)
            if (typeof response === 'string') {
                console.error("Error fetching report parameters:", response);
                return;
            }

            // Iterate over the received report parameters
            response.forEach(function(parameter) {
                // Extract parameter values from the array
                var parameterName = parameter[2];
                var parameterDescription = parameter[3];
                var parameterTypeID = parameter[4];
                var parameterType = parameter[5];

                // If it's the first parameter in the loop, set report name and description
                if (rptName === "") {
                    rptName = parameter[0];
                    rptDescription = parameter[1];
                }

                // Create HTML elements to display the parameters
                var parameterElement = $("<div>");
                parameterElement.append("<p>Report Name: " + rptName + "</p>");
                parameterElement.append("<p>Report Description: " + rptDescription + "</p>");
                parameterElement.append("<p>Parameter Name: " + parameterName + "</p>");
                parameterElement.append("<p>Parameter Description: " + parameterDescription + "</p>");
                parameterElement.append("<p>Parameter Type ID: " + parameterTypeID + "</p>");
                parameterElement.append("<p>Parameter Type: " + parameterType + "</p>");

                // Check if parameter values are available
                if (parameter.length > 6) {
                    // Extract and format parameter values
                    var parameterValues = parameter.slice(6).join(", ");
                    parameterElement.append("<p>Parameter Values: " + parameterValues + "</p>");
                }

                // Append the parameter element to the mainContent div
                $("#mainContent").append(parameterElement);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error fetching report parameters:", error);
        }
    });
} */



function toggleRunReportsSubMenu() {
    var runReportsSublist = document.getElementById("run-reports-sublist");
    var display = runReportsSublist.style.display;
    runReportsSublist.style.display = (display === "block") ? "none" : "block";
}

function redirectToController(reportId) {
    // Make an AJAX request to the server to fetch report data
    $.ajax({
        type: "GET",
        url: "/CWFM/reportSetup/reportPreferences",
        data: { reportRefId: reportId }, // Send report ID as a parameter
        success: function(response) {
            try {
                // Parse the JSON response if necessary
                var reportParameters = JSON.parse(response);
                
                // Clear the existing content of the report parameters div
                document.getElementById("mainContent").innerHTML = "";
                
                // Iterate over the report parameters and display them
                reportParameters.forEach(function(parameter) {
                    // Create a new <p> element for each parameter
                    var parameterElement = document.createElement("p");
                    
                    // Set the text content of the <p> element with the parameter data
                    parameterElement.textContent = "Parameter Name: " + parameter.parameterName + ", Parameter Value: " + parameter.parameterValue;
                    
                    // Append the <p> element to the report parameters div
                    document.getElementById("mainContent").appendChild(parameterElement);
                });
            } catch (error) {
                console.error("Error parsing JSON:", error);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error loading report:", error);
        }
    });
}


function toggleRunReportsSubMenu() {
    var subMenu = document.getElementById("run-reports-sublist");
    if (subMenu.style.display === "none") {
        subMenu.style.display = "block";
    } else {
        subMenu.style.display = "none";
    }
}


/* function generateReportHTML(report) {
    return `<li><a href="#" onclick="loadReportDetails(${report.id})">${report.name}</a></li>`;
} */

// Function to render the list of reports in the sidebar
/* function renderReportsSidebar(reports) {
    const sidebar = document.getElementById('sidebar');
    const reportsList = document.createElement('ul');
    reportsList.setAttribute('id', 'reportsList');

    // Iterate over the reports array and generate HTML for each report
    reports.forEach(report => {
        const reportHTML = generateReportHTML(report);
        reportsList.innerHTML += reportHTML;
    });

    // Append the reports list to the sidebar
    sidebar.appendChild(reportsList);
} */

// Assuming 'reports' is fetched from the backend and passed to this function
/* renderReportsSidebar(reports); */
</script>
<!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> -->
 <script>
 
 function removeFile() {
	    // Remove the current file attachment
	    document.getElementById("attachmentContainer").innerHTML = `
	        <input type="file" id="file" name="file">
	    `;
	}
        var contextPath = '<%= request.getContextPath() %>';
        function validateForm() {
            var requiredFields = document.querySelectorAll('[required]');
            var firstEmptyField = null;

            for (var i = 0; i < requiredFields.length; i++) {
                var field = requiredFields[i];
                if (!field.value.trim()) {
                    // Field is empty, display error message
                    alert(field.name + " is required!");
                    // Set focus to the first empty field
                    if (!firstEmptyField) {
                        firstEmptyField = field;
                    }
                    // Highlight the column containing the empty field
                    var column = field.closest('td'); // Find the closest parent td element
                    column.classList.add('error'); // Apply a CSS class to highlight the column
                } else {
                    // Remove error class if field is not empty
                    var column = field.closest('td');
                    column.classList.remove('error');
                }
            }

            // Set focus to the first empty field, if any
            if (firstEmptyField) {
                firstEmptyField.focus();
                return false; // Prevent form submission
            }

            return true; // Allow form submission
        }
               
        $(document).ready(function() {
            var rowsPerPage = 2;
            var currentPage = 1;
            var totalRows = $('#principalEmployerTable tbody tr').length;
            var totalPages = Math.ceil(totalRows / rowsPerPage);

            showPage(1);

            $('#nextPageBtn').click(function() {
                if (currentPage < totalPages) {
                    showPage(++currentPage);
                    console.log("Showing page:", currentPage);
                }
            });

            $('#prevPageBtn').click(function() {
                if (currentPage > 1) {
                    showPage(--currentPage);
                    console.log("Showing page:", currentPage);
                }
            });

            function showPage(page) {
                $('#principalEmployerTable tbody tr').hide();
                var startIndex = (page - 1) * rowsPerPage;
                var endIndex = startIndex + rowsPerPage;
                $('#principalEmployerTable tbody tr').slice(startIndex, endIndex).show();
            }
        });
 function viewFile(url) {
     // Open the file URL in a new tab
     window.open(url, '_blank');
 }
 function updateCheckboxValue(checkboxId, hiddenFieldName) {alert(hiddenFieldName);
	    var checkbox = document.getElementById(checkboxId);
	    var hiddenField = document.getElementsByName(hiddenFieldName)[0];
	    hiddenField.value = checkbox.checked ? "true" : "false";alert(hiddenField.value);
	}


   

    </script>
  
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="left-menu">
        <span style="margin-right: 8px;"></span>
            <a href="#" onclick="toggleSidebar()">
                <img src="resources/img/menu.png" alt="Menu" style="width: 15px; height: 15px;">
            </a>
            <span style="margin-right: 8px;"></span>
            <a href="WelcomePage.jsp" ><img src="resources/img/home.png" alt="Home" style="width: 15px; height: 15px;"></a>
        </div>
        <div class="welcome-logout">
            <!-- Welcome message and logout link -->
            <a href="#" onclick="logout(contextPath);" class="logout">Logout</a>
        </div>
    </div>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
    <ul>
        
    <li id="main-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleSubMenu()" class="mainmenu-link">
        <span class="menu-text">CLMS</span>
        <img src="resources/img/uarrow.png" alt="After CLMS" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After CLMS" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-menu">
        <li id="principalEmployerMenuItem" style="margin-top: 10px;"><a href="#" onclick="loadPrincipalEmployerList(contextPath)">Principal Employer</a></li>
        <li id="contractorMenuItem"><a href="#" onclick="loadContractorList(contextPath)">Contractor</a></li>
        <li id="workOrderMenuItem"><a href="#" onclick="loadWOList(contextPath)">Work Order</a></li>
       <!--  <li><a href="#" onclick="loadMinimumwageList(contextPath)">Minimum Wage Master</a></li> -->
        <li id="contractWorkmenMenuItem"><a href="#" onclick="loadContent('workmenDetail.jsp')">Contract Workmen</a></li>
         <!-- <li><a href="#" onclick="loadWorkmenWageList(contextPath)">Workmen Wages</a></li> -->
    </ul>
</li>

 <li id="onboard-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleSubOnboard()" class="onboard-link">
        <span class="menu-text">Workmen Onboarding</span>
        <img src="resources/img/uarrow.png" alt="After CLMS" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After CLMS" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-onboard">
        <li style="margin-top: 10px;"><a href="#" onclick="loadCommonList(contextPath,'/contractworkmen/aadharOnbordingLists')">Aadhar based on-boarding</a></li>
        <li><a href="#" onclick="loadCommonList(contextPath,'/contractworkmen/quickOnbordingList')">Quick on-boarding</a></li>
        <!-- <li><a href="#" onclick="loadCommonList(contextPath,'/contractworkmen/approvalOnbordingList')">Approval Mechanism</a></li> -->
    </ul>
</li>

<li id="onboard-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="loadCommonList(contextPath,'/contractor/contReg')">
    <span class="menu-text">Contractor Registration</span>
    </a>
       
</li>

<li id="onboard-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="loadCommonList(contextPath,'/bvr/add')">
    <span class="menu-text">Bill Verification</span>
    </a>
       
</li>
       <!-- working <li id="reportSetup-item" style="margin-top: 20px;">
            <a href="#" onclick="toggleComplianceSubMenu()" class="mainmenu-link">
                <span class="menu-text">Reports</span>
                <img src="resources/img/uarrow.png" alt="After Compliance" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
                <img src="resources/img/darrow.png" alt="After Compliance" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
            </a>
            <ul class="sub-menu" id="compliance-submenu" style="display: none;">
                <li><a href="#" onclick="loadReportsList(contextPath);">Report Setup</a></li>
                <li><a href="#" onclick="loadRunReport(contextPath); toggleRunReportsSubMenu();">Run Reports</a>
                    <ul id="run-reports-sublist" style="display: none;">
                        Report names will be dynamically inserted here
                    </ul>
                </li>
            </ul>
        </li> -->

  <!-- <li id="reports-item" style="margin-top: 20px;">
    <a href="#" onclick="showReports()" class="reports-link">
        <span class="menu-text">Reports</span>
      <img src="resources/img/uarrow.png" alt="After reports" class="menu2-icon arrow-up" style="width: 10px; height: 8px; display: none;">
    <img src="resources/img/darrow.png" alt="After reports" class="menu2-icon arrow-down" style="width: 10px; height: 8px; display: inline-block;">
    </a>
    <ul class="sub-report" >
       <li id="statutory-reports">
    <a href="#" onclick="toggleStatutoryReports()" class="reports-link">
        <span class="menu-text">Statutory Reports</span>
        <img src="resources/img/uarrow.png" alt="Up Arrow" class="menu2-icon arrow-up" style="width: 10px; height: 8px; display: none;">
        <img src="resources/img/darrow.png" alt="Down Arrow" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-report" id="statutory-reports-list" style="display: none; margin-left: 20px;">
        <li><a href="#" onclick="loadReport('Form A Report', 'This is the content for the Form A Report which is Work in Progress.');">Form A</a></li>
        <li><a href="#" onclick="loadReport('Form B Report', 'This is the content for the Wage Register Report which is Work in Progress.');">Form B</a></li>
        <li><a href="#" onclick="loadReport('Form C Report', 'This is the content for the BONUS PAID TO EMPLOYEES Report which is Work in Progress.');">Form C</a></li>
        <li><a href="#" onclick="loadReport('Form V Report', 'This is the content for the Form of Certificate By Principal Employer Report which is Work in Progress.');">Form V</a></li>
        <li><a href="#" onclick="loadReport('PF Report', 'This is the content for the PF Report which is Work in Progress.');">PF Report</a></li>
    </ul>
</li>
        
   <li id="compliance-reports-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleComplianceReports()" class="reports-link">
        <span class="menu-text">Compliance Report</span>
        <img src="resources/img/uarrow.png" alt="After reports" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After reports" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-report" id="compliance-reports-list" style="display: none;margin-left: 20px;">
        <li><a href="#" onclick="loadSampleReport1()">Form 12</a></li>
        <li><a href="#" onclick="loadSampleReport2()">Form 13</a></li>
        <li><a href="#" onclick="loadSampleReport3()">Form 18</a></li>
    </ul>
</li>

<li id="operational-reports-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleOperationalReports()" class="reports-link">
        <span class="menu-text">Operational Report</span>
        <img src="resources/img/uarrow.png" alt="After reports" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After reports" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-report" id="operational-reports-list" style="display: none;margin-left: 20px;">
       <li><a href="#" onclick="loadReport('Attendance Register Report', 'This is the content for the Attendance Register Report which is Work in Progress.');">Attendance Register</a></li>
                <li><a href="#" onclick="loadReport('Bill Verification Report ', 'This is the content for the Bill Verification Report  which is Work in Progress.');">Bill Verification Report</a></li>
                 <li><a href="#" onclick="loadReport('Entry  Pass Printing', 'This is the content for the Entry  Pass Printing Report which is Work in Progress.');">Entry  Pass Printing</a></li>
                <li><a href="#" onclick="loadReport('Labor Deployment Report', 'This is the content for the Labor Deployment Report which is Work in Progress.');">Labor Deployment Report</a></li>
                <li><a href="#" onclick="loadReport('Workmen Present Report', 'This is the content for the Workmen Present Report which is Work in Progress.');">Workmen Present</a></li>
              
    </ul>
</li>

    </ul>
</li> -->
       
  <li id="compliance-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleComplianceSubMenu()" class="mainmenu-link">
        <span class="menu-text">Compliance</span>
        <img src="resources/img/uarrow.png" alt="After Compliance" class="menu2-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After Compliance" class="menu2-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-menu" id="compliance-submenu" style="display: none;">
        <li><a href="#" onclick="loadReport('Aadhar Validation', 'This is the content for the Aadhar Validation which is Work in Progress.');">Aadhar Validation </a></li>
        <li><a href="#" onclick="loadReport('ESIC Validation', 'This is the content for the ESIC Validation which is Work in Progress.');">ESIC Validation</a></li>
        <li><a href="#" onclick="loadReport('PF Validation', 'This is the content for the PF Validation which is Work in Progress.');">PF Validation</a></li>
        <li><a href="#" onclick="loadReport('UAN Validation', 'This is the content for the UAN Validation which is Work in Progress.');">UAN Validation </a></li>
          <li><a href="#" onclick="loadReport('ECR vs Actual PF& ESIC', 'This is the content for the ECR vs Actual PF& ESIC which is Work in Progress.');">ECR vs Actual PF & ESIC</a></li>
    </ul>
</li>
  <li id="onboard-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="loadCommonList(contextPath,'/user/list')">
    <span class="menu-text">Users</span>
    </a>
       
</li>   
            
 <!-- <li id="users-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleUsersSubMenu()" class="users-link">
        <span class="menu-text">Users</span>
        <img src="resources/img/uarrow.png" alt="After users" class="users-icon arrow-up" style="width: 10px; height: 8px;">
        <img src="resources/img/darrow.png" alt="After users" class="users-icon arrow-down" style="width: 10px; height: 8px;">
    </a>
    <ul class="sub-users">
        <li><a href="#" onclick="loadCommonList(contextPath,'/user/list')">User List</a></li>
    </ul> 
</li> --> 
        </ul>
    </div>
 <!-- Pie charts for principal employer, contractor, work order, and workmen -->
        <%-- <h2>Dashboard</h2>
        <div style="display: flex; justify-content: space-around;">
            <canvas id="principalEmployerChart" width="200" height="200"></canvas>
            <canvas id="contractorChart" width="200" height="200"></canvas>
            <canvas id="workOrderChart" width="200" height="200"></canvas>
            <canvas id="workmenChart" width="200" height="200"></canvas>
        </div> --%>
   <div id="mainContent" class="form-content" >
       
    </div>
    <script>
</script>
</body>
</html>
