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
    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/billVerification/view/" + transactionId , true);
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
/////////////////////// Bill Admin Starts//////////////////////////
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
///////////////////////////Bill Admin ENds//////////////////////
function redirectToBillAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/billVerification/createBill", true);
    xhr.send();
}

function getContractorsForBill(selectElement, userAccount) {
	
	const selectedOption = selectElement.options[selectElement.selectedIndex];
	    const unitCode = selectedOption.getAttribute("data-code");
	    document.getElementById("unitCodeId").value = unitCode ? unitCode : "";
		
		const unitId = selectedOption.value;
		    const unitName = selectedOption.text;
		   
		
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllContractors?unitId=" + unitId + "&userAccount=" + userAccount;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of contractor objects
            var contractors = JSON.parse(xhr.responseText);
            console.log("Response:", contractors);
            
            // Find the contractor select element
            var contractorSelect = document.getElementById("contractor");
            
            // Clear existing options
            contractorSelect.innerHTML = '<option value="">Please select Contractor</option>';
            
            // Populate the dropdown with the new list of contractors
            contractors.forEach(function(contractor) {
                var option = document.createElement("option");
                option.value = contractor.contractorId;
                option.text = contractor.contractorName;
				option.setAttribute("data-code", contractor.contractorCode); 
                contractorSelect.appendChild(option);
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}
function formatDate(dateTimeStr) {
    if (!dateTimeStr) return "";

    const date = new Date(dateTimeStr);
    if (isNaN(date)) return "";

    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
    const year = date.getFullYear();

    return `${day}-${month}-${year}`;
}
function getWorkordersForBill(selectElement) {
		var principalEmployerSelect = document.getElementById("unitId");
	    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
		const selectedOption = selectElement.options[selectElement.selectedIndex];
		const contractorCode = selectedOption.getAttribute("data-code");
		document.getElementById("contractorCodeId").value = contractorCode ? contractorCode : "";
				
				const contractorId = selectedOption.value;
				const contractorName = selectedOption.text;
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllWorkOrders?unitId=" + unitId + "&contractorId=" + contractorId;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of workorder objects
            var workorders = JSON.parse(xhr.responseText);
            console.log("Response:", workorders);
            
            // Find the workorder select element
            var workorderSelect = document.getElementById("workorder");
            
            // Clear existing options
            workorderSelect.innerHTML = '<option value="">Please select Workorder</option>';
            
            // Populate the dropdown with the new list of workorders
            workorders.forEach(function(workorder) {
                var option = document.createElement("option");
                option.value = workorder.workorderId;
                option.text = workorder.name;
				const validFromFormatted = formatDate(workorder.validFrom);
				const validToFormatted = formatDate(workorder.validTo);
				option.setAttribute("data-fromDate", validFromFormatted); 
				option.setAttribute("data-toDate", validToFormatted);
                workorderSelect.appendChild(option);
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}

function setDates(selectElement){
	const selectedOption = selectElement.options[selectElement.selectedIndex];
			const validFrom = selectedOption.getAttribute("data-fromDate");
			document.getElementById("woValidFromId").value = validFrom ? validFrom : "";
			
			const validTo = selectedOption.getAttribute("data-toDate");
						document.getElementById("woValidToId").value = validTo ? validTo : "";
}

function saveBtn() {
    const formData = new FormData();
	const unitSelect = document.getElementById("unitId");
		 const unitId = unitSelect?.value;
		 const unitName = unitSelect?.options[unitSelect.selectedIndex]?.text;
		 
		 const contractorSelect = document.getElementById("contractor");
		 		 const contractorSelectId = contractorSelect?.value;
		 		 const contractorSelectName = contractorSelect?.options[contractorSelect.selectedIndex]?.text;
    // ===== Basic Info JSON Data =====
    const data = {
        wcTransId: $('#transactionId').val(),
        unitId: unitId,
        unitCode: $('#unitCodeId').val(),
		unitName: unitName,
        contractorId:contractorSelectId,
        contractorCode: $('#contractorCodeId').val(),
		contractorName:contractorSelectName,
        workOrderNumber: $('#workorder').val(),
        startDate: $('#billStartDateId').val(),
        endDate: $('#billEndDateId').val(),
        services: $('#billCategory').val(),
        woValidFrom: $('#woValidFromId').val(),
        woValidTo: $('#woValidToId').val(),
        billType: $('#billType').val(),
		actionPlan:$('#actionPlanId').val(),
		comments:$('#commentsId').val(),
		preComments:$('#preCommentsId').val()
    };

    // ✅ Append JSON directly as string
    formData.append("jsonData", JSON.stringify(data));

    // ===== Kronos Reports Files =====
    $("input[type='file'][name^='kronosFile_']").each(function () {
        if (this.files.length > 0) {
            formData.append($(this).attr("name"), this.files[0]);
        }
    });

    // ===== Statutory Attachments Files =====
    $("input[type='file'][name^='statutoryFile_']").each(function () {
        if (this.files.length > 0) {
            formData.append($(this).attr("name"), this.files[0]);
        }
    });

    // ===== Check List Values =====
    const checklist = [];

    $("#tab4 tbody tr").each(function () {
        const row = $(this);
        const id = row.find("input[type='hidden']").attr("name").split('_')[1];
        const statusValue = row.find("select[name='statusValue']").val();
        const licenseNumber = row.find("input[name^='licenseNumber_']").val() || "";
        const validUpto = row.find("input[name^='validUpto_']").val() || "";

        checklist.push({
            id: id,
            statusValue: statusValue,
            licenseNumber: licenseNumber,
            validUpto: validUpto
        });
    });

    // ✅ Append checklist JSON directly as string
    formData.append("checklistJson", JSON.stringify(checklist));

    // ===== Submit AJAX =====
    $.ajax({
        url: '/CWFM/billVerification/saveBill',
        method: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            alert('Saved successfully!');
			loadCommonList('/billVerification/listingFilter', 'Bill Verification List');
        },
        error: function (xhr) {
            alert('Error: ' + xhr.responseText);
        }
    });
}

function showFileNameBill(input, id) {
        const fileName = input.files[0]?.name || "No file chosen";
        document.getElementById('fileName_' + id).textContent = fileName;
    }
    
    function showFileNameBill0(input, id) {
        const fileName = input.files[0]?.name || "No file chosen";
        document.getElementById('statfileName_' + id).textContent = fileName;
    }
	function searchBillBasedOnPE() {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/billVerification/list',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId,
								deptId:deptId
					        },
					        success: function(response) {
					            var tableBody = $('#workmenTable tbody');
					            tableBody.empty();
					            if (response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.wcTransId + '"></td>'+
												'<td  >' + wo.wcTransId + '</td>' +
												 '<td  >' + wo.unitCode + '</td>' +
					                              '<td  >' + wo.contractorCode + '</td>' +
												  '<td  >' + wo.contractorName + '</td>' +	
												  
												  '<td  >' + wo.workOrderNumber + '</td>' +
												  '<td  >' + wo.startDate + '</td>' +
												  '<td  >' + wo.endDate + '</td>' +	
												  '<td  >' + wo.status + '</td>' +	
												  '<td  >' +wo.services + '</td>' +	
												 			                             
					                              '</tr>';
					                    tableBody.append(row);
					                });
					            } else {
					                tableBody.append('<tr><td colspan="3">No resources found</td></tr>');
					            }
					        },
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}  
					

						function downloadBill(reportType, transactionId, fileName) {
					    const baseUrl = '/CWFM/billVerification/downloadFile';
					    
					    // Construct the URL based on gatePassId, userId, and docType
					    const url = `${baseUrl}/${reportType}/${transactionId}/${fileName}`;
						//alert("url is"+url);
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
