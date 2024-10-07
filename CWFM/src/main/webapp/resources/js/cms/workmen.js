
function searchWithPEContractorInWorkmenAadharList(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url: contextPath + "/contractworkmen/aadharOnbordingList",
         data: { principalEmployerId: principalEmployerId,contractorId:contractorId}, // Pass the search query as data
         success: function(response) {
             // Handle success response
           //  console.log("Search results:", response);
             document.getElementById("mainContent").innerHTML = response;
         },
         error: function(xhr, status, error) {
             // Handle error response
             console.error("Error searching:", error);
         }
     });
 }
 
 function redirectToWorkmenAadharOBAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/addAadharOB", true);
    xhr.send();
}

function redirectToWorkmenQuickOBAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/addQuickOB", true);
    xhr.send();
}
function getContractorsAndTrades(unitId, userId) {
    if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }

    // Fetch contractors
    getContractors(unitId, userId);

    // Fetch trades
    getTrades(unitId);
}


function getContractors(unitId, userId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllContractors?unitId=" + unitId + "&userId=" + userId;
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

function getWorkordersAndWC() {
	 var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var contractorSelect = document.getElementById("contractor");
    var contractorId = contractorSelect.value; // Get the selected contractor value
    
 if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }
    
  if(!contractorId){
	 alert("Please select a Contractor.");
        return;
  }

    getWorkorders(unitId, contractorId);

    getWC(unitId, contractorId);
}

function getWorkorders(unitId,contractorId) {
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

function getWC(unitId,contractorId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllWC?unitId=" + unitId + "&contractorId=" + contractorId;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            var wcs = JSON.parse(xhr.responseText);
            console.log("Response:", wcs);
            
            var wcSelect = document.getElementById("wc");
            
            wcSelect.innerHTML = '<option value="">Please select WC Policy/ESIC Reg Number</option>';
            
            wcs.forEach(function(wc) {
                var option = document.createElement("option");
                option.value = wc.wcId;
                option.text = wc.wcCode;
                wcSelect.appendChild(option);
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


function getTrades(unitId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllTrades?unitId=" + unitId;
    console.log("Fetching trades from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var trades = JSON.parse(xhr.responseText);
            console.log("Trades:", trades);
            var tradeSelect = document.getElementById("trade");

            // Clear existing options
            tradeSelect.innerHTML = '<option value="">Please select Trade</option>';

            // Populate the trade dropdown
            trades.forEach(function (trade) {
                var option = document.createElement("option");
                option.value = trade.tradeId;
                option.text = trade.tradeName;
                tradeSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching trades:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching trades");
    };

    xhr.send();
}

function initializeDatePicker() {
        $('.datetimepickerformat').datepicker({
            dateFormat: 'yy-mm-dd', // Set the date format
            changeMonth: true,      // Allow changing month via dropdown
            changeYear: true,       // Allow changing year via dropdown
            yearRange: "-100:+0",   // Set the year range from 100 years ago to the current year
            maxDate: 0              // Prevent selecting future dates
        });
    }
    
    function validateBasicData() {
    let isValid = true;
    const aadharNumber = $("#aadharNumber").val().trim();
    if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {
        $("#error-aadhar").show();
        isValid = false;
    }else{
		  $("#error-aadhar").hide();
	}

    const firstName = $("#firstName").val().trim();
    if (firstName === "") {
        $("#error-firstName").show();
        isValid = false;
    }else{
		$("#error-firstName").hide();
	}
    const lastName = $("#lastName").val().trim();
    if (lastName === "") {
        $("#error-lastName").show();
        isValid = false;
    }else{
		 $("#error-lastName").hide();
	}
    const dateOfBirth = $("#dateOfBirth").val().trim();
    if (dateOfBirth === "") {
        $("#error-dateOfBirth").show();
        isValid = false;
    }else{
		 $("#error-dateOfBirth").hide();
	}
    const gender = $("#gender").val();
    if (gender === "") {
        $("#error-gender").show();
        isValid = false;
    }else{
		$("#error-gender").hide();
	}
    const relationName = $("#relationName").val().trim();
    if (relationName === "") {
        $("#error-relationName").show();
        isValid = false;
    }else{
		 $("#error-relationName").hide();
	}
    const idMark = $("#idMark").val().trim();
    if (idMark === "") {
        $("#error-idMark").show();
        isValid = false;
    }else{
		 $("#error-idMark").hide();
	}
	
	const mobileInput = $("#mobileNumber").val().trim();
	const mobileNumberRegex = /^[6-9]\d{9}$/;
	if (!mobileNumberRegex.test(mobileInput)) {
                 $("#error-mobileNumber").show();
        			isValid = false;
     }else{
		 $("#error-mobileNumber").hide();
	 }
	 
	 const maritalInput = $("#maritalStatus").val();
	 if (maritalInput === "") {
        $("#error-maritalStatus").show();
        isValid = false;
    }else{
		$("#error-maritalStatus").hide();
	}
    return isValid;

}

function validateEmploymentInformation(){
	let isValid = true;
    const principalEmp = $("#principalEmployer").val();
     if (principalEmp === "") {
        $("#error-principalEmployer").show();
        isValid = false;
    }else{
		$("#error-principalEmployer").hide();
	}
	const cont = $("#contractor").val();
     if (cont === "") {
        $("#error-contractor").show();
        isValid = false;
    }else{
		$("#error-contractor").hide();
	}
	const wo = $("#workorder").val();
     if (wo === "") {
        $("#error-workorder").show();
        isValid = false;
    }else{
		$("#error-workorder").hide();
	}
	const trade = $("#trade").val();
     if (trade === "") {
        $("#error-trade").show();
        isValid = false;
    }else{
		$("#error-trade").hide();
	}
	const skill = $("#skill").val();
     if (skill === "") {
        $("#error-skill").show();
        isValid = false;
    }else{
		$("#error-skill").hide();
	}
	const dept = $("#department").val();
     if (dept === "") {
        $("#error-department").show();
        isValid = false;
    }else{
		$("#error-department").hide();
	}
	const subdept = $("#subdepartment").val();
     if (subdept === "") {
        $("#error-area").show();
        isValid = false;
    }else{
		$("#error-area").hide();
	}
	const eic = $("#eic").val();
     if (eic === "") {
        $("#error-eic").show();
        isValid = false;
    }else{
		$("#error-eic").hide();
	}
	 const noj = $("#natureOfJob").val().trim();
    if (noj === "") {
        $("#error-natureOfJob").show();
        isValid = false;
    }else{
		 $("#error-natureOfJob").hide();
	}
	const wc = $("#wc").val();
     if (wc === "") {
        $("#error-wc").show();
        isValid = false;
    }else{
		$("#error-wc").hide();
	}
	const ha = $("#hazardousArea").val();
     if (ha === "") {
        $("#error-hazardous").show();
        isValid = false;
    }else{
		$("#error-hazardous").hide();
	}
	const aa = $("#accessArea").val();
     if (aa === "") {
        $("#error-accessArea").show();
        isValid = false;
    }else{
		$("#error-accessArea").hide();
	}
	 const uan = $("#uanNumber").val().trim();
    if (uan === "") {
        $("#error-uanNumber").show();
        isValid = false;
    }else{
		 $("#error-uanNumber").hide();
	}
	const healthCheckDate = $("#healthCheckDate").val().trim();
    if (healthCheckDate === "") {
        $("#error-healthCheckDate").show();
        isValid = false;
    }else{
		 $("#error-healthCheckDate").hide();
	}
	return isValid;
}

function validateOtherInformation(){
	let isValid = true;
    const bg = $("#bloodGroup").val();
     if (bg === "") {
        $("#error-bloodGroup").show();
        isValid = false;
    }else{
		$("#error-bloodGroup").hide();
	}
	 const accom = $("#accommodation").val();
     if (accom === "") {
        $("#error-accommodation").show();
        isValid = false;
    }else{
		$("#error-accommodation").hide();
	}
	 const academic = $("#academic").val();
     if (academic === "") {
        $("#error-academic").show();
        isValid = false;
    }else{
		$("#error-academic").hide();
	}
	const tech = $("#technical").val();
     if (tech === "") {
        $("#error-technical").show();
        isValid = false;
    }else{
		$("#error-technical").hide();
	}
	const ifscInput = $("#ifscCode").val().trim();
    const ifscCodeRegex = /^[A-Z]{4}0[A-Z0-9]{6}$/;
    if (!ifscCodeRegex.test(ifscInput)) {
          $("#error-ifscCode").show();
          isValid = false;
    }else{
		 $("#error-ifscCode").hide();
	}
	const accountInput = $("#accountNumber").val().trim();
    const accountNumberRegex = /^[0-9]{9,18}$/;
    if (!accountNumberRegex.test(accountInput)) {
          $("#error-accountNumber").show();
          isValid = false;
    }else{
		$("#error-accountNumber").hide();
	}
	const emergencyName = $("#emergencyName").val().trim();
    if (emergencyName === "") {
        $("#error-emergencyName").show();
        isValid = false;
    }else{
		$("#error-emergencyName").hide();
	}
	const emergencyNoInput = $("#emergencyNumber").val().trim();
	const mobileNumberRegex = /^[6-9]\d{9}$/;
	if (!mobileNumberRegex.test(emergencyNoInput)) {
                 $("#error-emergencyNumber").show();
        			isValid = false;
     }else{
		 $("#error-emergencyNumber").hide();
	 }
	return isValid;
}

function validateWages(){
	let isValid = true;
    const wage = $("#wageCategory").val();
     if (wage === "") {
        $("#error-wageCategory").show();
        isValid = false;
    }else{
		$("#error-wageCategory").hide();
	}
	const bonus = $("#bonusPayout").val();
     if (bonus === "") {
        $("#error-bonusPayout").show();
        isValid = false;
    }else{
		$("#error-bonusPayout").hide();
	}
	const zone = $("#zone").val();
     if (zone === "") {
        $("#error-zone").show();
        isValid = false;
    }else{
		$("#error-zone").hide();
	}
	const allowanceRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
	const basic = $("#basic").val().trim();
	const da = $("#da").val().trim();
	const hra = $("#hra").val().trim();
	const washing = $("#washingAllowance").val().trim();
	const other = $("#otherAllowance").val().trim();
	const uniform = $("#uniformAllowance").val().trim();
	
	if ( !allowanceRegex.test(basic) ) {
             $("#error-basic").show();
       		 isValid = false;    
      }else{
		 $("#error-basic").hide();
	  }
	  if ( !allowanceRegex.test(da) ) {
             $("#error-da").show();
       		 isValid = false;    
      }else{
		 $("#error-da").hide();
	  }
	  if ( !allowanceRegex.test(hra) ) {
             $("#error-hra").show();
       		 isValid = false;    
      }else{
		 $("#error-hra").hide();
	  }
	  if ( !allowanceRegex.test(washing) ) {
             $("#error-washingAllowance").show();
       		 isValid = false;    
      }else{
		 $("#error-washingAllowance").hide();
	  }
	  if ( !allowanceRegex.test(other) ) {
             $("#error-otherAllowance").show();
       		 isValid = false;    
      }else{
		 $("#error-otherAllowance").hide();
	  }
	  if ( !allowanceRegex.test(uniform) ) {
             $("#error-uniformAllowance").show();
       		 isValid = false;    
      }else{
		 $("#error-uniformAllowance").hide();
	  }
	  
	return isValid;
}

function fileUpload(){
	let isValid = true;
        // Get the selected files
        var aadharFile = $("#aadharFile").prop("files")[0];
        var policeFile = $("#policeFile").prop("files")[0];

        // Validate the files (optional)
        if (!validateFiles(aadharFile, policeFile)) {
            isValid=false; // Stop the upload if validation fails
        }

        // Create a FormData object
        var formData = new FormData();
        if (aadharFile) {
            formData.append("aadharFile", aadharFile);
        }
        if (policeFile) {
            formData.append("policeFile", policeFile);
        }

        // Submit the form data using AJAX
        $.ajax({
            url: "/CWFM/contractworkmen/uploadDocuments", // Your server-side upload handling URL
            type: "POST",
            data: formData,
            contentType: false, // Tell jQuery not to set contentType
            processData: false, // Tell jQuery not to process the data
            success: function(response) {
                // Display success message
                $("#uploadMessage").text("Documents uploaded successfully!").css("color", "green");
            },
            error: function(xhr, status, error) {
                // Display error message
                $("#uploadMessage").text("Error uploading documents: " + xhr.responseText).css("color", "red");
            }
        });
   return isValid;
}
    function validateFiles(aadharFile, policeFile) {
        let valid = true;
        
        // Example validation for file size and type
        if (aadharFile && aadharFile.size > 5 * 1024 * 1024) { // Check if file size is more than 5MB
            $("#aadharError").text("Aadhar file must be less than 5MB").css("color", "red");
            valid = false;
        } else {
            $("#aadharError").text(""); // Clear error if valid
        }

        if (policeFile && policeFile.size > 5 * 1024 * 1024) { // Check if file size is more than 5MB
            $("#policeError").text("Police file must be less than 5MB").css("color", "red");
            valid = false;
        } else {
            $("#policeError").text(""); // Clear error if valid
        }

        return valid; // Return the validation result
    }
    
    function submitGatePass(userId){
		let basicValid = true;
		let employmentValid = true;
		let otherValid = true;
		let wagesValid = true;
		let documentValid = true;
		
		var aadharFile = $("#aadharFile").prop("files")[0];
        var policeFile = $("#policeFile").prop("files")[0];

        // Validate the files (optional)
        if (!validateFiles(aadharFile, policeFile)) {
            documentValid=false; // Stop the upload if validation fails
        }
        
        if(!validateBasicData()){
			basicValid = false;
		}
		
		if(!validateEmploymentInformation()){
			employmentValid = false;
		}
		
		if(!validateOtherInformation()){
			otherValid = false;
		}
		
		if(!validateWages()){
			wagesValid = false;
		}
		
		if(basicValid && employmentValid && otherValid && wagesValid && documentValid){
			const data = {
        		aadhaarNumber : $("#aadharNumber").val().trim(),
				firstName : $("#firstName").val().trim(),
				lastName :  $("#lastName").val().trim(),
				dateOfBirth :  $("#dateOfBirth").val().trim(),
				gender : $("#gender").val(),
				relationName :  $("#relationName").val().trim(),
				idMark : $("#idMark").val().trim(),
				mobileNumber :  $("#mobileNumber").val().trim(),
				maritalStatus :  $("#maritalStatus").val(),
				principalEmployer : $("#principalEmployer").val(),
				contractor : $("#contractor").val(),
				workorder : $("#workorder").val(),
				trade : $("#trade").val(),
				skill : $("#skill").val(),
				department : $("#department").val(),
				subdepartment : $("#subdepartment").val(),
				eic : $("#eic").val(),
				natureOfJob : $("#natureOfJob").val().trim(),
				wcEsicNo : $("#wc").val(),
				hazardousArea : $("#hazardousArea").val(),
				accessArea : $("#accessArea").val(),
				uanNumber : $("#uanNumber").val().trim(),
				healthCheckDate : $("#healthCheckDate").val().trim(),
				bloodGroup : $("#bloodGroup").val(),
				accommodation : $("#accommodation").val(),
				academic : $("#academic").val(),
				technical : $("#technical").val(),
				ifscCode : $("#ifscCode").val().trim(),
				accountNumber :  $("#accountNumber").val().trim(),
				emergencyName : $("#emergencyName").val().trim(),
				emergencyNumber : $("#emergencyNumber").val().trim(),
				wageCategory : $("#wageCategory").val(),
				bonusPayout : $("#bonusPayout").val(),
				pfCap : $("#pfCap").val(),
				zone : $("#zone").val(),
				basic : $("#basic").val().trim(),
				da : $("#da").val().trim(),
				hra : $("#hra").val().trim(),
				washingAllowance : $("#washingAllowance").val().trim(),
				otherAllowance : $("#otherAllowance").val().trim(),
				uniformAllowance : $("#uniformAllowance").val().trim(),
				userId : userId,
				gatePassAction : "save",
        		
    		};
    		// Send the data to the server using AJAX
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/saveGatePass", true); // Replace with your actual controller URL
    xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Handle successful response
            console.log("Data saved successfully:", xhr.responseText);
            loadCommonList('/contractworkmen/quickOBList', 'Quick On-Bording List');
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
		}else{
			//error in tabs
		}
	}
	
	
	function redirectToWorkmenView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/view/" + gatePassId, true);
    xhr.send();
}

 function preventEdit(event) {
        // Prevent further changes to the dropdown
        event.target.disabled = true;
    }
    
    function approveRejectGatePass(status){
		let isValid=true;
		 const comments = $("#comments").val().trim();
    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    }else{
		$("#error-comments").hide();
	}
	if(isValid){
		const data = {
			approverId : $("#userId").val().trim(),
			comments : $("#comments").val().trim(),
			status : status,
			gatePassId : $("#gatePassId").val().trim(),
			approverRole : $("#roleName").val().trim(),
		};
			  const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
    xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Handle successful response
            console.log("Data saved successfully:", xhr.responseText);
            loadCommonList('/contractworkmen/quickOBList', 'Quick On-Bording List');
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
		}else{
			//error 
		}
		}//eof
		
		
		