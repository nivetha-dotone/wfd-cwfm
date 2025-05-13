function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "TRANSACTIONID,UNITCODE,VENDORCODE,CONTRACTORNAME,WORKORDERNUMBER,BILLSTARTDATE,BILLENDDATE,STATUS,BILLCATEGORY,LASTAPPROVER,NEXTAPPROVER\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6),td:nth-child(7),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "BillVerification.csv");
            document.body.appendChild(link);
            link.click();
        }
    function redirectToBillView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/billVerification/billview/" + unitId , true);
    xhr.send();
}   
function downloadDocTemp(gatePassId, userId, docType) {
    const baseUrl = '/CWFM/billVerification/downloadFile';
    
    // Construct the URL based on gatePassId, userId, and docType
    const url = `${baseUrl}/${docType}`;

    // Create a temporary anchor element
    const a = document.createElement('a');
    a.href = url;
    a.download = ''; // This attribute tells the browser to download the file

    // Append to the body to make it work in Firefox
    document.body.appendChild(a);

    // Programmatically click the anchor
    a.click();

    // Remove the anchor from the document
    document.body.removeChild(a);
}

// Toggle accordion section
function toggleSection(id) {
    $(".accordion-content").not("#" + id).slideUp();
    $("#" + id).slideToggle();
}

// Add Kronos or Statutory row
function addReportRow(section) {
    const containerId = section === 'kronos' ? '#kronosReportsContainer' : '#statutoryReportsContainer';
    const inputName = section === 'kronos' ? 'kronosReports' : 'statutoryReports';

    const row = `
        <div class="report-row">
            <input type="text" name="${inputName}" placeholder="Enter report name" required />
            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
        </div>
    `;
    $(containerId).append(row);
}

// Add HR Checklist row
function addChecklistRow() {
    const index = $('.checklist-row').length;
    const row = `
        <div class="checklist-row">
            <input type="text" name="checklistNames" placeholder="Check Point Name" required />
            <label><input type="checkbox" name="licenseRequired${index}" /> Licence No. Required</label>
            <label><input type="checkbox" name="validUptoRequired${index}" /> Valid Upto Required</label>
            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
        </div>
    `;
    $("#checklistConfigContainer").append(row);
}

function saveReportConfig() {
    let isValid = true;
    let errorSections = [];
    $("#saveStatusMsg").text("").css("color", "green");

    // Reset all borders first
    $("input").css("border", "");

    const kronosReports = [];
    const statutoryReports = [];
    const checklistItems = [];

    const kronosSet = new Set();
    const statutorySet = new Set();
    const checklistSet = new Set();

    // ==== Kronos Reports ====
    $('input[name="kronosReports"]').each(function () {
        const value = $(this).val().trim();
        if (value) {
            if (kronosSet.has(value.toLowerCase())) {
                $(this).css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Kronos Reports")) {
                    errorSections.push("Kronos Reports");
                }
            } else {
                kronosSet.add(value.toLowerCase());
                kronosReports.push(value);
            }
        }
    });

    // ==== Statutory Attachments ====
    $('input[name="statutoryReports"]').each(function () {
        const value = $(this).val().trim();
        if (value) {
            if (statutorySet.has(value.toLowerCase())) {
                $(this).css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Statutory Reports")) {
                    errorSections.push("Statutory Reports");
                }
            } else {
                statutorySet.add(value.toLowerCase());
                statutoryReports.push(value);
            }
        }
    });

    // ==== HR Checklist Items ====
    $('#checklistConfigContainer .checklist-row').each(function () {
        const checkpointName = $(this).find('input[name="checklistNames"]').val().trim();
        const licenseRequired = $(this).find('input[type="checkbox"]').eq(0).is(":checked");
        const validUptoRequired = $(this).find('input[type="checkbox"]').eq(1).is(":checked");

        if (checkpointName) {
            if (checklistSet.has(checkpointName.toLowerCase())) {
                $(this).find('input[name="checklistNames"]').css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Checklist Items")) {
                    errorSections.push("Checklist Items");
                }
            } else {
                checklistSet.add(checkpointName.toLowerCase());
                checklistItems.push({ checkpointName, licenseRequired, validUptoRequired });
            }
        }
    });

    // ==== Final validations ====
    if (!kronosReports.length && !statutoryReports.length && !checklistItems.length) {
        $("#saveStatusMsg")
            .html("Please enter at least one item in any of the sections: <b>Kronos Reports, Statutory Reports, or Checklist Items</b>.")
            .css("color", "red");
        return;
    }

    if (!isValid) {
        $("#saveStatusMsg")
            .html("Duplicate entries found in the following section(s): <b>" + errorSections.join(", ") + "</b>. Please fix them.")
            .css("color", "red");
        return;
    }

    // All entries are valid, continue saving
    const requestData = {
        kronosReports,
        statutoryReports,
        checklistItems
    };

    $.ajax({
        url: "/CWFM/billSetup/saveReportConfigAjax",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function () {
            alert("Configuration saved successfully!");
            loadCommonList('/billSetup/list', 'Bill Verification Setup');
        },
        error: function () {
            alert("Error saving configuration.");
        }
    });
}
