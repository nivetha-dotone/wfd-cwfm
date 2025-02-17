/*Contractor*/
 
  function loadContractorList(contextPath) {
      //  var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/contractor/list';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                  resetSessionTimer();
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    
	function searchContractorsBasedOnPE() {
			    var principalEmployerId = $('#principalEmployerId').val();
			    

			    $.ajax({
			        url: '/CWFM/contractor/getAllContractorsBasedOnPE',
			        type: 'POST',
			        data: {
			            principalEmployerId: principalEmployerId
			        },
			        success: function(response) {
			            var tableBody = $('#contractorTable tbody');
			            tableBody.empty();
			            if (response.length > 0) {
			                $.each(response, function(index, contr) {
			                    var row = '<tr >' +
										'<td ><input type="checkbox" name="selectedContractorIds" value="' + contr.contractorId + '"></td>'+
			                              '<td >' + contr.contractorCode + '</td>' +
			                              '<td >' + contr.contractorName + '</td>' +
										  '<td >' + contr.contractorAddress + '</td>' +				                             
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
 
function redirectToContrAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractor/add", true);
    xhr.send();
}

function redirectToContrEdit() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to edit.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedContractors"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractor/edit/" + unitId, true);
    xhr.send();
}

function redirectToContrView() {
	var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	    if (selectedCheckboxes.length !== 1) {
	        alert("Please select exactly one row to view.");
	        return;
	    }
	    
	    var selectedRow = selectedCheckboxes[0].closest('tr');
	    var contractorId = selectedRow.querySelector('[name="selectedContractorIds"]').value;
		var principalEmployerId = $('#principalEmployerId').val();
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractor/view/" + contractorId+ "?principalEmployerId=" + principalEmployerId, true);
    xhr.send();
}
 function ContrExportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedContractorIds"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "CODE,NAME,ADDRESS\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorList.csv");
            document.body.appendChild(link);
            link.click();
        }
        
        function toggleSelectAllContrcators() {
            var selectAllCheckbox = document.getElementById('selectAllCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedContractors"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }
        
         function goBackToContractorList(contextPath,principalEmployerId) {
			  var url = contextPath + "/contractor/list?principalEmployerId=" + principalEmployerId;
    
   $.ajax({
        type: "GET", // Use GET method to fetch the list page
        url:url,// contextPath + "/contractor/list", // URL of the list page
        success: function(response) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading list page:", error);
            // Handle error if needed
        }
    });// This will navigate back to the previous page in the browser history
}
function saveRegistrationDetails(){
	
	const data = {
		contractorregId:$("#registrationid").val().trim(),
		principalEmployer:$("#principalEmployerId").val(),
		vendorCode:$("#vendorCode").val().trim(),
		managerName:$("#managername").val().trim(),
		locofWork:$("#locofwork").val().trim(),
		totalStrength:$("#totalStrength").val().trim(),
		rcMaxEmp:$("#rcmaxemployees").val().trim(),
		pfNum:$("#pfnumber").val().trim(),
		natureOfWork:$("#natureOfWork").val().trim(),
		contractFrom:$("#contractFrom").val(),
		contractTo:$("#contractTo").val(),
		contractType:$("#contractType").val(),
		rcVerified:$("#isRcVerified").val().trim(),
		
		
	};
	
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractor/saveReg", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractor/contRegList', 'Contractor Registration');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}//eofunc
	
	function getContractor(unitId, userId) {
    if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }

    // Fetch contractors
    getContractor(unitId, userId);

   
}
 function addTaskRow() {
	   $('#tabularid tbody').append(`
			   <tr>
			   <td style="width:2%;border-top: thin solid; text-align:center"><button class="btn btn-sucess" onclick="addTaskRow()">+</button></td>
			   <td style="width:2%;border-top: thin solid; text-align:center"><button class="btn btn-danger" onclick="deleteRow(this)">-</button></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option> Work Order1</option><option> Work Order2</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option>job 1</option><option>job 2</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option>ESIC</option><option>LL</option><option>WC</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="text" class="form-control" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="text" class="form-control" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="date" class="form-control datetimepickerformat" onclick="initializeDatePicker()" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="date" class="form-control datetimepickerformat" onclick="initializeDatePicker()"/></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="file" class="form-control" /></td>
           </tr>
           `);
	        $('.datepicker').datepicker();
   }
   function deleteRow(button) {
    const tableBody = $('#tabularid tbody');
    const rowCount = tableBody.find('tr').length;

    if (rowCount > 1) {
        $(button).closest('tr').remove();
    } else {
        alert('At least one row must remain in the table.');
    }
    }
     /*function generateContractorID() {
        const date = new Date();
        const contractorID = date.getTime(); // Use current timestamp as unique ID
        // Check if contractorID already has a value (set by the server)
        if (!document.getElementById("contractorID").value) {
            document.getElementById("contractorID").value = contractorID; // Set the value if not set by server
        }
    }

    // Call generateContractorID when the page loads
    window.onload = function() {
        generateContractorID();
    }*/
   
 
// Main function to handle redirection
function redirectToContractorView() {
    // Select all checked checkboxes
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    // Validate single row selection
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }

    // Retrieve the selected row and its data
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var contractorRenewId = selectedRow.querySelector('[name="selectedWOs"]').value.trim();
    var contractorRequest = selectedRow.querySelector('[name="requestType"]').value.trim();

    console.log("Contractor ID:", contractorRenewId, "Request Type:", contractorRequest);

    // Ensure contractorRenewId is not empty
    if (!contractorRenewId) {
        alert("Invalid contractor ID. Please try again.");
        return;
    }

    // Determine the URL based on the request type (case-sensitive comparison for uppercase and lowercase)
    var url = "";
    if (contractorRequest === "registration" || contractorRequest === "Registration" || contractorRequest === "REGISTRATION") {
        url = "/CWFM/contractor/contractorview/" + contractorRenewId;
    } else if (contractorRequest === "renewal" || contractorRequest === "Renewal" || contractorRequest === "RENEWAL") {
        url = "/CWFM/contractor/contractorRenewalview/" + contractorRenewId;
    } else {
        alert("Invalid request type. Please try again.");
        console.error("Unexpected request type:", contractorRequest);
        return;
    }

    // Perform the AJAX request
    performAjaxRequest(url);
}

// Function to perform the AJAX request and update the page content
function performAjaxRequest(url) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Update the content area with the response
                document.getElementById("mainContent").innerHTML = xhr.responseText;
            } else {
                // Handle errors
                alert("Failed to load content. Please check the server or your request.");
                console.error("Error loading content: ", xhr.status, xhr.statusText, "URL:", url, "Response:", xhr.responseText);
            }
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}





 function exportToCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "CONTRACTORREGID,UNITCODE,CODE,CONTRACTORNAME,STATUS,TYPE\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6),td:nth-child(7)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorRegistration.csv");
            document.body.appendChild(link);
            link.click();
        }
function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "CONTRACTORREGID,UNITCODE,CODE,CONTRACTORNAME,STATUS\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorRenewal.csv");
            document.body.appendChild(link);
            link.click();
        }
        
 function saveRoleDetails(){
	
	const data = {
		firstName:$("#firstname").val().trim,
		lastName:$("#lastname").val().trim,
		userId:$("#userid").val().trim,
		emailId:$("#email").val.trim,
		contactNumber:$("#mobilenumber").val().trim,
		password:$("#password").val().trim,
		roleName:$("#role").val(),
	}
	
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractor/saveRole", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractor/role', 'Role');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}
function validateFormData() {
    let isValid = true;
    const principalemployer = $("#principalemployer").val();
    if (principalemployer === "") {
        $("#error-principalemployer").show();
        isValid = false;
    }else{
		$("#error-principalemployer").hide();
	}
    const vendorCode = $("#vendorCode").val().trim();
    if (vendorCode === "") {
        $("error-vendorCode").show();
        isValid = false;
    }else{
		 $("error-vendorCode").hide();
	}
    const managername = $("#managername").val().trim();
    const nameRegex = /^[A-Za-z\s]+$/;
    if (!nameRegex.test(managername)) {
                 $("#error-managername").show();
        			isValid = false;
     }else{
		 $("#error-managername").hide();
	 }
    const locofwork = $("#locofwork").val();
     const locofworkRegex = /^[A-Za-z\s]+$/;
     if (!locofworkRegex.test(locofwork)) {
                 $("#error-locofwork").show();
        			isValid = false;
     }else{
		 $("#error-locofwork").hide();
	 }
    const totalStrength = $("#totalStrength").val().trim();
    const totalStrengthRegex = /^[0-9]+$/;
    if (!totalStrengthRegex.test(totalStrength)) {
                 $("#error-totalStrength").show();
        			isValid = false;
     }else{
		 $("#error-totalStrength").hide();
	 }
	const rcmaxemployees = $("#rcmaxemployees").val().trim();
    const rcmaxemployRegex = /^[0-9]+$/;
    if (!rcmaxemployRegex.test(rcmaxemployees)) {
                 $("#error-rcmaxemployees").show();
        			isValid = false;
     }else{
		 $("#error-rcmaxemployees").hide();
	 }
	
	const pfInput = $("#pfnumber").val().trim();
	const pfNumberRegex = /^[A-Z]{2}[0-9]{5}[A-Z]{1}[0-9]{4}$/; 
	if (!pfNumberRegex.test(pfInput)) {
                 $("#error-pfnumber").show();
        			isValid = false;
     }else{
		 $("#error-pfnumber").hide();
	 }
	const natureOfWork = $("#natureOfWork").val();
	 if (natureOfWork === "") {
        $("#error-natureOfWork").show();
        isValid = false;
    }else{
		$("#error-natureOfWork").hide();
	}
	const contractFrom = $("#contractFrom").val();
	 if (contractFrom === "") {
        $("#error-contractFrom").show();
        isValid = false;
    }else{
		$("#error-contractFrom").hide();
	}
	const contractTo = $("#contractTo").val();
	 if (contractTo === "") {
        $("#error-contractTo").show();
        isValid = false;
    }else{
		$("#error-contractTo").hide();
	}
	const contractType = $("#contractType").val();
	 if (contractType === "") {
        $("#error-contractType").show();
        isValid = false;
    }else{
		$("#error-contractType").hide();
	}
	const isRcVerified = $("#isRcVerified").val();
	 if (isRcVerified === "") {
        $("#error-isRcVerified").show();
        isValid = false;
    }else{
		$("#error-isRcVerified").hide();
	}
    return isValid;

}
function saveRenewDetails(){
	
	const data = {
		contractorRenewId:$("#renewalid").val(),
		unitCode:$("#unitcode").val(),
		organization:$("#organization").val(),
		contractorCode:$("#contractorcode").val().trim(),
		contractorName:$("#contractorname").val().trim(),
		managerName:$("#managername").val().trim(),
		locationOfWork:$("#locationofwork").val().trim(),
		supervisorName:$("#supervisorname").val().trim(),
		pfCode:$("#pfcode").val().trim(),
		emailAdd:$("#emailaddress").val(),
		mobileNumber:$("#mobilenumber").val(),
		esicReg:$("#esicregistration").val(),
		contractorValidTo:$("#contractvalidtill").val().trim(),
		contractorClass:$("#contractorclass").val().trim(),
		contractorType:$("#contractortype").val().trim(),
		/*documentType:$("#documentDropId").val().trim(),
		documentNumber:$("#documentnumber").val().trim(),
		coverage:$("#coverage").val().trim(),
		validFrom:$("#validfrom").val().trim(),
		validTo:$("#validto").val().trim(),
		attachment:$("#attachments").val().trim(),
		panIndia:$("#panindia").val().trim(),
		subContractor:$("#subcontractor").val().trim(),
		*/
	};
	console.log(data);
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractor/saveRenew", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractor/contRegList', 'Contractor Renewal');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}//eofunc
function validateRenewFormData() {
    let isValid = true;
    const renewalid = $("#renewalid").val();
    if (renewalid === "") {
        $("#error-renewalid").show();
        isValid = false;
    }else{
		$("#error-renewalid").hide();
	}
    const unitcode = $("#unitcode").val().trim();
    if (unitcode === "") {
        $("error-unitcode").show();
        isValid = false;
    }else{
		 $("error-unitcode").hide();
	}
    const organization = $("#organization").val().trim();
    const organizationRegex = /^[A-Za-z\s]+$/;
    if (!organizationRegex.test(organization)) {
                 $("#error-organization").show();
        			isValid = false;
     }else{
		 $("#error-organization").hide();
	 }
    const contractorcode = $("#contractorcode").val();
     const contractorcodeRegex = /^[0-9]+$/;
     if (!contractorcodeRegex.test(contractorcode)) {
                 $("#error-contractorcode").show();
        			isValid = false;
     }else{
		 $("#error-contractorcode").hide();
	 }
    const contractorname = $("#contractorname").val().trim();
    const contractornameRegex = /^[A-Za-z\s]+$/;
    if (!contractornameRegex.test(contractorname)) {
                 $("#error-contractorname").show();
        			isValid = false;
     }else{
		 $("#error-contractorname").hide();
	 }
	const managername = $("#managername").val().trim();
    const managernameRegex = /^[A-Za-z\s]+$/;
    if (!managernameRegex.test(managername)) {
                 $("#error-managername").show();
        			isValid = false;
     }else{
		 $("#error-managername").hide();
	 }
	
	const locationofwork = $("#locationofwork").val().trim();
	const locationofworkRegex = /^[A-Za-z\s]+$/; 
	if (!locationofworkRegex.test(locationofwork)) {
                 $("#error-locationofwork").show();
        			isValid = false;
     }else{
		 $("#error-locationofwork").hide();
	 }
	const supervisorname = $("#supervisorname").val();
	const supervisornameRegex = /^[A-Za-z\s]+$/; 
	if (!supervisornameRegex.test(supervisorname)) {
                 $("#error-supervisorname").show();
        			isValid = false;
     }else{
		 $("#error-supervisorname").hide();
	 }
	 const pfInput = $("#pfcode").val().trim();
	const pfNumberRegex = /^[A-Z]{2}[0-9]{5}[A-Z]{1}[0-9]{4}$/; 
	if (!pfNumberRegex.test(pfInput)) {
                 $("#error-pfcode").show();
        			isValid = false;
     }else{
		 $("#error-pfcode").hide();
	 }
	 
	const email = $("#emailaddress").val().trim();
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/; 
	if (!emailRegex.test(email)) {
                 $("#error-emailaddress").show();
        			isValid = false;
     }else{
		 $("#error-emailaddress").hide();
	 }
	
	const mobile = $("#mobilenumber").val().trim();
	const mobilenumberRegex = /^[6-9]\d{9}$/; 
	if (!mobilenumberRegex.test(mobile)) {
                 $("#error-mobilenumber").show();
        			isValid = false;
     }else{
		 $("#error-mobilenumber").hide();
	 }
	 
	const esicregistration = $("#esicregistration").val().trim();
	const esicregistrationRegex = /^[A-Za-z0-9]*\\d{10}[A-Za-z0-9]*$/; 
	if (!esicregistrationRegex.test(esicregistration)) {
                 $("#error-esicregistration").show();
        			isValid = false;
     }else{
		 $("#error-esicregistration").hide();
	 }
	const contractvalidtill = $("#contractvalidtill").val();
	 if (contractvalidtill === "") {
        $("#error-contractvalidtill").show();
        isValid = false;
    }else{
		$("#error-contractvalidtill").hide();
	}
	const classification = $("#contractorclass").val();
	const classificationRegex = /^[A-Za-z\s]+$/; 
	if (!classificationRegex.test(classification)) {
                 $("#error-contractorclass").show();
        			isValid = false;
     }else{
		 $("#error-contractorclass").hide();
	 }
	const contractortype = $("#contractortype").val();
	const contractortypeRegex = /^[A-Za-z\s]+$/; 
	if (!contractortypeRegex.test(contractortype)) {
                 $("#error-contractortype").show();
        			isValid = false;
     }else{
		 $("#error-contractortype").hide();
	 }
	
    return isValid;
}
function toggleMainContractorRow() {
        // Get the selected value of contractor type
        var contractorType = document.getElementById("contractType").value;
        var mainContractorRow = document.getElementById("mainContractorRow");

        // Show or hide Main Contractor row based on the selected type
        if (contractorType === "Sub Contractor") {
            mainContractorRow.style.display = "table-row"; // Show the row
        } else {
            mainContractorRow.style.display = "none"; // Hide the row
            document.getElementById("mainContractor").value = ""; // Clear the input
        }
    }