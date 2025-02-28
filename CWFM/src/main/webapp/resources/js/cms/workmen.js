var divIndex=0;
let selectedDocumentTypes = new Set();
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
function getContractorsAndTrades(unitId, userAccount) {
    if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }

    // Fetch contractors
    getContractors(unitId, userAccount);

    // Fetch trades
    getTrades(unitId);
}


function getContractors(unitId, userAccount) {
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
    //alert("URL: " + url+" "+unitId+" "+contractorId);
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
	function initializeDatePicker1() {
	    $('.datetimepickerformat1').datepicker({
	        dateFormat: 'yy-mm-dd', // Set the date format
	        changeMonth: true,      // Allow changing month via dropdown
	        changeYear: true,       // Allow changing year via dropdown
	        yearRange: "0:+100", 
	        minDate: 0              // Prevent selecting future dates
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
    const firstnameRegex = /^[A-Za-z\s]{2,}$/;
    if (!firstnameRegex.test(firstName)) {
    $("#error-firstName").show(); 
    isValid = false;
    } else {
    $("#error-firstName").hide(); 
    }
    const lastName = $("#lastName").val().trim();
    const lastnameRegex = /^[A-Za-z\s]{1,}$/;
    if (!lastnameRegex.test(lastName)) {
        $("#error-lastName").show();
        isValid = false;
    }else{
		 $("#error-lastName").hide();
	}
	if (firstName.toLowerCase() === lastName.toLowerCase()) {
    $("#error-equalNames").show();
    isValid = false;
    } else {
    $("#error-equalNames").hide();
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
    const relationnameRegex = /^[A-Za-z\s]{2,}$/;  // Only alphabetic characters, at least 2 letters
    if (!relationnameRegex.test(relationName)) {
    $("#error-relationName").show(); // Show error if invalid
    isValid = false;
    } else {
    $("#error-relationName").hide(); // Hide error if valid
    }
    const idMark = $("#idMark").val().trim();
    const idmarkRegex=/^[A-Za-z\s]+$/;
    if (!idmarkRegex.test(idMark)) {
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
	const address=$("#address").val().trim();
	const addressRegex=/^[A-Za-z0-9\s,.'-]{5,}$/;
	if (!addressRegex.test(address)) {
                 $("#error-address").show();
        			isValid = false;
     }else{
		 $("#error-address").hide();
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
	 const nojRegex = /^[A-Za-z]{2,}$/;
    if (!nojRegex.test(noj)) {
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
	 const uanRegex = /^\d{12}$/;
    if (!uanRegex.test(uan)) {
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
	const doj = $("#doj").val().trim();
    if (doj === "") {
        $("#error-doj").show();
        isValid = false;
    }else{
		 $("#error-doj").hide();
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
	const firstnameRegex = /^[A-Za-z\s]{2,}$/;
    if (!firstnameRegex.test(emergencyName)) {
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
	const allowanceRegex = /^[0-9]{1,3}(\.[0-9]{1,2})?$/;
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
		var profilePic =$("imageFile").prop("files")[0];
        // Validate the files (optional)
        if (!validateFiles(aadharFile, policeFile,profilePic)) {
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
		if(profilePic){
			formData.append("profilePic",profilePic);
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
    function validateFiles(aadharFile, policeFile,profilePc) {
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
		
		if (profilePc && profilePc.size > 5 * 1024 * 1024) { // Check if file size is more than 5MB
		           $("#profilePcError").text("Photo/Image must be less than 5MB").css("color", "red");
		           valid = false;
		       } else {
		           $("#profilePcError").text(""); // Clear error if valid
		       }

        return valid; // Return the validation result
    }
    
  
	
	function redirectToWorkmenView() {
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
    xhr.open("GET", "/CWFM/contractworkmen/view/" + transactionId, true);
    xhr.send();
}

 function preventEdit(event) {
        // Prevent further changes to the dropdown
        event.target.disabled = true;
    }
    
    function approveRejectGatePass(status){
		let isValid=true;
		 const approvercomments = $("#approvercomments").val().trim();
    if (approvercomments === "") {
        $("#error-approvercomments").show();
        isValid = false;
    }else{
		$("#error-approvercomments").hide();
	}
	if(isValid){
		const data = {
			approverId : $("#userId").val().trim(),
			comments : $("#approvercomments").val().trim(),
			status : status,
			transactionId : $("#transactionId").val().trim(),
			gatePassId : $("#gatePassId").val().trim(),
			approverRole : $("#roleName").val().trim(),
			roleId :$("#roleId").val().trim(),
			gatePassType : '1',
		};
			  const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
    xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Handle successful response
            console.log("Data saved successfully:", xhr.responseText);
            loadCommonList('/contractworkmen/list', 'Quick On-Bording List');
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
		}//eofunc
		
		
		
		

    

	function submitGatePass(userId) {
    let basicValid = true;
    let employmentValid = true;
    let otherValid = true;
    let wagesValid = true;
    let documentValid = true;

    var aadharFile = $("#aadharFile").prop("files")[0];
    var policeFile = $("#policeFile").prop("files")[0];
	var profilePic = $("#imageFile").prop("files")[0];
	
    // Validate the files (optional)
    if (!validateFiles(aadharFile, policeFile,profilePic)) {
        documentValid = false; // Stop the upload if validation fails
    }

    if (!validateBasicData()) {
        basicValid = false;
    }

    if (!validateEmploymentInformation()) {
        employmentValid = false;
    }

    if (!validateOtherInformation()) {
        otherValid = false;
    }

    if (!validateWages()) {
        wagesValid = false;
    }

    console.log("basicValid: " + basicValid);
    console.log("employmentValid: " + employmentValid);
    console.log("otherValid: " + otherValid);
    console.log("wagesValid: " + wagesValid);
    console.log("documentValid: " + documentValid);

    if (basicValid && employmentValid && otherValid && wagesValid && documentValid) {
        const data = new FormData();
        const jsonData = {
			transactionId:$("#transactionId").val().trim(),
            aadhaarNumber: $("#aadharNumber").val().trim(),
            firstName: $("#firstName").val().trim(),
            lastName: $("#lastName").val().trim(),
            dateOfBirth: $("#dateOfBirth").val().trim(),
            gender: $("#gender").val(),
            relationName: $("#relationName").val().trim(),
            idMark: $("#idMark").val().trim(),
            mobileNumber: $("#mobileNumber").val().trim(),
            maritalStatus: $("#maritalStatus").val(),
            principalEmployer: $("#principalEmployer").val(),
            contractor: $("#contractor").val(),
            workorder: $("#workorder").val(),
            trade: $("#trade").val(),
            skill: $("#skill").val(),
            department: $("#department").val(),
            subdepartment: $("#subdepartment").val(),
            eic: $("#eic").val(),
            natureOfJob: $("#natureOfJob").val().trim(),
            wcEsicNo: $("#wc").val(),
            hazardousArea: $("#hazardousArea").val(),
            accessArea: $("#accessArea").val(),
            uanNumber: $("#uanNumber").val().trim(),
            healthCheckDate: $("#healthCheckDate").val().trim(),
            bloodGroup: $("#bloodGroup").val(),
            accommodation: $("#accommodation").val(),
            academic: $("#academic").val(),
            technical: $("#technical").val(),
            ifscCode: $("#ifscCode").val().trim(),
            accountNumber: $("#accountNumber").val().trim(),
            emergencyName: $("#emergencyName").val().trim(),
            emergencyNumber: $("#emergencyNumber").val().trim(),
            wageCategory: $("#wageCategory").val(),
            bonusPayout: $("#bonusPayout").val(),
            pfCap: $("#pfCap").val(),
            zone: $("#zone").val(),
            basic: $("#basic").val().trim(),
            da: $("#da").val().trim(),
            hra: $("#hra").val().trim(),
            washingAllowance: $("#washingAllowance").val().trim(),
            otherAllowance: $("#otherAllowance").val().trim(),
            uniformAllowance: $("#uniformAllowance").val().trim(),
            userId: userId,
            gatePassAction: "save",
            comments: $("#comments").val().trim(),
			address:$("#address").val().trim(),
			doj:$("#doj").val(),
        };

        // Serialize the JSON object to a string
		const jsonString = JSON.stringify(jsonData);

		// Append the JSON data to FormData
		data.append("jsonData", jsonString);

        // Append the files to the FormData
        if (aadharFile) {
            data.append("aadharFile", aadharFile);
        }
        if (policeFile) {
            data.append("policeFile", policeFile);
        }
		
		
		if(profilePic){
			data.append("profilePic",profilePic);
		}
		
    	const additionalFields = document.querySelectorAll('.document-field');
    additionalFields.forEach((field, index) => {
        const docType = field.querySelector('select[name="documentType"]').value;
        const fileInput = field.querySelector('input[type="file"]');

        if (docType && fileInput.files[0]) {
            data.append('additionalFiles', fileInput.files[0]);
            data.append('documentTypes', docType);
        }
    });
        
		/*// Log FormData content
for (const [key, value] of data.entries()) {
    console.log(key, value instanceof File ? value.name : value); // Log filename if it's a File
}*/
        // Send the data to the server using AJAX
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/contractworkmen/saveGatePass", true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
                loadCommonList('/contractworkmen/list', 'On-Boarding List');
				
            } else {
                console.error("Error saving data:", xhr.status, xhr.responseText);
            }
        };

        xhr.onerror = function () {
            console.error("Request failed");
        };

        // Send the FormData object
        xhr.send(data);
    } else {
        console.error("Validation failed for one or more fields.");
    }
}
	
	function downloadDoc(transactionId, userId, docType) {
    const baseUrl = '/CWFM/contractworkmen/downloadFile';
    
    // Construct the URL based on gatePassId, userId, and docType
    const url = `${baseUrl}/${transactionId}/${userId}/${docType}`;
	alert("url is"+url);
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


     function additionalDocUpload(){
      
         var currentCount = $(".document-field").length;
         if (currentCount < 7) {
           divIndex++;
        $("#additionalDoc").append(`
    <div class="document-field" id="document-field-${divIndex}" style="display: flex; align-items: center; margin-bottom: 10px;">
        <select name="documentType" style="margin-right: 10px;color:black;">
            <option value="">Select Document Type</option>
            <option value="Bank">Bank</option>
            <option value="Id2">Id2</option>
            <option value="Other">Other</option>
            <option value="Medical">Medical</option>
            <option value="Education">Education</option>
            <option value="Training">Training</option>
            <option value="Form11">Form11</option>
        </select>
        <input type="file" accept="application/pdf" style="margin-right: 10px;" onchange="displayFileName(this, 'fileName-${divIndex}')">
        <span id="fileName-${divIndex}" style="margin-right: 10px;color:black;"></span>
        <button type="button" onclick="removeDocument(${divIndex})" style="color:black;">Remove</button>
    </div>
`);

    } else {
        alert("You can add a maximum of 7 additional documents.");
    }
}
    function removeDocument(index) {
        // Remove the document field
        $(`#document-field-${index}`).remove();
    }
   


function displayFileName(inputElement, displayId) {
    const displayElement = document.getElementById(displayId);

    // Get the selected file's name
    if (inputElement.files.length > 0) {
        const fileName = inputElement.files[0].name;
        displayElement.textContent = fileName; // Display the file name
    } else {
        displayElement.textContent = ''; // Clear the display if no file is selected
    }
}

function displayFileName1(inputId, displayId) {
    const fileInput = document.getElementById(inputId);
    const displayElement = document.getElementById(displayId);

    // Get the selected file's name
    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        displayElement.textContent = fileName; // Display the file name
    } else {
        displayElement.textContent = ''; // Clear the display if no file is selected
    }
}
function submitCancel(userId,gatePassType){
	let isValid=true;
	 const comments = $("#comments").val()?.trim();
   if (!comments ) {
       $("#error-comments").show();
       isValid = false;
   }else{
	$("#error-comments").hide();
}
if(isValid){
	const data = {
		createdBy : userId,
		comments : $("#comments").val()?.trim(),
		gatePassId : $("#gatePassId").val().trim(),
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
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
	}//eofunc
function approveRejectCancel(status,gatePassType){
	let isValid=true;
	 const approvercomments = $("#approvercomments").val().trim();
   if (approvercomments === "") {
       $("#error-approvercomments").show();
       isValid = false;
   }else{
	$("#error-approvercomments").hide();
}
if(isValid){
	const data = {
		approverId : $("#userId").val().trim(),
		comments : $("#approvercomments").val().trim(),
		status : status,
		gatePassId : $("#gatePassId").val().trim(),
		approverRole : $("#roleName").val().trim(),
		roleId :$("#roleId").val().trim(),
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
         loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
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
	}//eofunc
	function submitBlock(userId,gatePassType){
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
			createdBy : userId,
			comments : $("#comments").val().trim(),
			gatePassId : $("#gatePassId").val().trim(),
			gatePassType : gatePassType,
		};
			  const xhr = new XMLHttpRequest();
	   xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
	   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
	   xhr.onload = function() {
	       if (xhr.status === 200) {
	           // Handle successful response
	           console.log("Data saved successfully:", xhr.responseText);
			  
	         loadCommonList('/contractworkmen/blockListFilter', 'Block List');
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
		}//eofunc
	function approveRejectBlock(status,gatePassType){
		let isValid=true;
		 const approvercomments = $("#approvercomments").val().trim();
	   if (approvercomments === "") {
	       $("#error-approvercomments").show();
	       isValid = false;
	   }else{
		$("#error-approvercomments").hide();
	}
	if(isValid){
		const data = {
			approverId : $("#userId").val().trim(),
			comments : $("#approvercomments").val().trim(),
			status : status,
			gatePassId : $("#gatePassId").val().trim(),
			approverRole : $("#roleName").val().trim(),
			roleId :$("#roleId").val().trim(),
			gatePassType : gatePassType,
		};
			  const xhr = new XMLHttpRequest();
	   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
	   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
	   xhr.onload = function() {
	       if (xhr.status === 200) {
	           // Handle successful response
	           console.log("Data saved successfully:", xhr.responseText);
	         loadCommonList('/contractworkmen/blockListFilter', 'Block List');
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
		}//eofunc
		function submitUnblock(userId,gatePassType){
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
					createdBy : userId,
					comments : $("#comments").val().trim(),
					gatePassId : $("#gatePassId").val().trim(),
					gatePassType : gatePassType,
				};
					  const xhr = new XMLHttpRequest();
			   xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
			   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
			   xhr.onload = function() {
			       if (xhr.status === 200) {
			           // Handle successful response
			           console.log("Data saved successfully:", xhr.responseText);
					  
			         loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
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
				}//eofunc
		function approveRejectUnblock(status,gatePassType){
			let isValid=true;
			 const approvercomments = $("#approvercomments").val().trim();
		   if (approvercomments === "") {
		       $("#error-approvercomments").show();
		       isValid = false;
		   }else{
			$("#error-approvercomments").hide();
		}
		if(isValid){
			const data = {
				approverId : $("#userId").val().trim(),
				comments : $("#approvercomments").val().trim(),
				status : status,
				gatePassId : $("#gatePassId").val().trim(),
				approverRole : $("#roleName").val().trim(),
				roleId :$("#roleId").val().trim(),
				gatePassType : gatePassType,
			};
				  const xhr = new XMLHttpRequest();
		   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
		   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
		   xhr.onload = function() {
		       if (xhr.status === 200) {
		           // Handle successful response
		           console.log("Data saved successfully:", xhr.responseText);
		         loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
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
			}//eofunc
			function submitBlack(userId,gatePassType){
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
		createdBy : userId,
		comments : $("#comments").val().trim(),
		gatePassId : $("#gatePassId").val().trim(),
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
xhr.onload = function() {
    if (xhr.status === 200) {
        // Handle successful response
        console.log("Data saved successfully:", xhr.responseText);
		  
      loadCommonList('/contractworkmen/blackListFilter', 'Black List');
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
	}//eofunc
			function approveRejectBlack(status,gatePassType){
let isValid=true;
 const approvercomments = $("#approvercomments").val().trim();
					   if (approvercomments === "") {
					       $("#error-approvercomments").show();
					       isValid = false;
					   }else{
$("#error-approvercomments").hide();
					}
					if(isValid){
const data = {
	approverId : $("#userId").val().trim(),
	comments : $("#approvercomments").val().trim(),
	status : status,
	gatePassId : $("#gatePassId").val().trim(),
	approverRole : $("#roleName").val().trim(),
	roleId:$("#roleId").val().trim(),
	gatePassType : gatePassType,
};
	  const xhr = new XMLHttpRequest();
					   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
					   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
					   xhr.onload = function() {
					       if (xhr.status === 200) {
					           // Handle successful response
					           console.log("Data saved successfully:", xhr.responseText);
					         loadCommonList('/contractworkmen/blackListFilter', 'Black List');
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
}//eofunc
function submitDeblack(userId,gatePassType){
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
					createdBy : userId,
					comments : $("#comments").val().trim(),
					gatePassId : $("#gatePassId").val().trim(),
					gatePassType : gatePassType,
				};
					  const xhr = new XMLHttpRequest();
			xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
			xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
			xhr.onload = function() {
			    if (xhr.status === 200) {
			        // Handle successful response
			        console.log("Data saved successfully:", xhr.responseText);
					  
			      loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
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
				}//eofunc
function approveRejectDeblacklist(status,gatePassType){
			let isValid=true;
			 const approvercomments = $("#approvercomments").val().trim();
		if (approvercomments === "") {
		    $("#error-approvercomments").show();
		    isValid = false;
		}else{
			$("#error-approvercomments").hide();
		}
		if(isValid){
			const data = {
				approverId : $("#userId").val().trim(),
				comments : $("#approvercomments").val().trim(),
				status : status,
				gatePassId : $("#gatePassId").val().trim(),
				approverRole : $("#roleName").val().trim(),
				roleId :$("#roleId").val().trim(),
				gatePassType : gatePassType,
			};
				  const xhr = new XMLHttpRequest();
		xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
		xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
		xhr.onload = function() {
		    if (xhr.status === 200) {
		        // Handle successful response
		        console.log("Data saved successfully:", xhr.responseText);
		      loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
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
			}//eofunc
			function submitLostOrDamage(userId,gatePassType){
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
	createdBy : userId,
	comments : $("#comments").val().trim(),
	gatePassId : $("#gatePassId").val().trim(),
	gatePassType : gatePassType,
};
	  const xhr = new XMLHttpRequest();
xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
xhr.onload = function() {
    if (xhr.status === 200) {
        // Handle successful response
        console.log("Data saved successfully:", xhr.responseText);
	  
	   loadCommonList('/contractworkmen/lostordamageFilter', 'Lost or Damage List');
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
}//eofunc
function redirectToWorkmenCancelView() {
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
    xhr.open("GET", "/CWFM/contractworkmen/cancelview/" + gatePassId, true);
    xhr.send();
}
function redirectToWorkmenBlockView() {
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
 xhr.open("GET", "/CWFM/contractworkmen/blockview/" + gatePassId, true);
 xhr.send();
 }
 function redirectToWorkmenUnblockView() {
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
  xhr.open("GET", "/CWFM/contractworkmen/unblockview/" + gatePassId, true);
  xhr.send();
  }
  function redirectToWorkmenBlackView() {
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
    xhr.open("GET", "/CWFM/contractworkmen/blackview/" + gatePassId, true);
    xhr.send();
    }
	function redirectToWorkmenDeblackView() {
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
	  xhr.open("GET", "/CWFM/contractworkmen/deblackview/" + gatePassId, true);
	  xhr.send();
	  }
	  function redirectToWorkmenLostView() {
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
	    xhr.open("GET", "/CWFM/contractworkmen/lostordamageview/" + gatePassId, true);
	    xhr.send();
	    }	
function searchWorkmenWithGatePassId(){
 var gatePassId = $('#searchInput').val();

 $.ajax({
     url: '/CWFM/contractworkmen/getWorkmenDetailBasedOnId',
     type: 'POST',
     data: {
         gatePassId: gatePassId
     },
     success: function(response) {
         var tableBody = $('#workmenTable tbody');
         tableBody.empty();
			if (response.length > 0) {
             $.each(response, function(index, wo) {
                 var row = '<tr  >' +
	'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
                           '<td  >' + wo.gatePassId + '</td>' +
                           '<td  >' + wo.firstName + '</td>' +
	  '<td  >'+ wo.lastName + '</td>' +	
	  '<td  >' + wo.gender + '</td>' +	
	  '<td  >' + wo.dateOfBirth + '</td>' +	
	  '<td  >' + wo.aadhaarNumber + '</td>' +	
	  '<td  >' + wo.contractorName + '</td>' +	
	  '<td  >' + wo.vendorCode + '</td>' +	
	  '<td  >' + wo.unitName + '</td>' +	
	  '<td  >' + wo.gatePassType + '</td>' +	
	  '<td  >' + wo.status + '</td>' +				                             
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


function previewImage(event,inputId,displayId) {
	const fileInput = document.getElementById(inputId);
	   const displayElement = document.getElementById(displayId);

	   // Get the selected file's name
	   if (fileInput.files.length > 0) {
	       const fileName = fileInput.files[0].name;
	       displayElement.textContent = fileName; // Display the file name
	   } else {
	       displayElement.textContent = ''; // Clear the display if no file is selected
	   }
            const file = event.target.files[0];
            if (file && file.type.startsWith("image/")) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const previewDiv = document.getElementById("preview");
                    previewDiv.innerHTML = `<img src="${e.target.result}" alt="Image Preview">`;
                };
                reader.readAsDataURL(file);
            }
        }
        
        function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            console.log("selectedRows.length"+selectedRows.length);
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "GatePass Id,First Name,Last Name,Gender,Date of Birth,Aadhar Number,Contractor Name,Vendor Code,Unit Name,GatePass Type,Status\n"; // Add headers here
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
            link.setAttribute("download", "QuickOnboarding.csv");
            document.body.appendChild(link);
            link.click();
        }
		function searchGatePassBasedOnPE() {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/contractworkmen/quickOBList',
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
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
												'<td  >' + wo.transactionId + '</td>' +
												 '<td  >' + wo.gatePassId + '</td>' +
					                              '<td  >' + wo.firstName + '</td>' +
												  '<td  >' + wo.lastName + '</td>' +	
												  
												  '<td  >' + wo.gender + '</td>' +
												  '<td  >' + wo.dateOfBirth + '</td>' +
												  '<td  >' + wo.aadhaarNumber + '</td>' +	
												  '<td  >' + wo.contractorName + '</td>' +	
												  '<td  >' +wo.vendorCode + '</td>' +	
												  '<td  >' +wo.unitName + '</td>' +	
												  '<td  >' + wo.gatePassType + '</td>' +	
												  '<td  >' + wo.status + '</td>' +				                             
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
					function searchBlockList() {
										    var principalEmployerId = $('#principalEmployerId').val();
										    
											var deptId=$("#deptId").val();
										    $.ajax({
										        url: '/CWFM/contractworkmen/blockList',
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
																	'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                              '<td  >' + wo.gatePassId + '</td>' +
										                              '<td  >' + wo.firstName + '</td>' +
																	  '<td  >' + wo.lastName + '</td>' +	
																	  
																	  '<td  >' + wo.gender + '</td>' +
																	  '<td  >' + wo.dateOfBirth + '</td>' +
																	  '<td  >' + wo.aadhaarNumber + '</td>' +	
																	  '<td  >' + wo.contractorName + '</td>' +	
																	  '<td  >' +wo.vendorCode + '</td>' +	
																	  '<td  >' +wo.unitName + '</td>' +	
																	  '<td  >' + wo.gatePassType + '</td>' +	
																	  '<td  >' + wo.status + '</td>' +				                             
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
										function searchUnBlockList() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/unblockList',
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
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                          '<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + '</td>' +
																 '<td  >' + wo.lastName + '</td>' +	
																											  
																'<td  >' + wo.gender + '</td>' +
																'<td  >' + wo.dateOfBirth + '</td>' +
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 '<td  >' +wo.vendorCode + '</td>' +	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
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
										function searchBlackList() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/blackList',
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
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                          '<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + '</td>' +
																 '<td  >' + wo.lastName + '</td>' +	
																											  
																'<td  >' + wo.gender + '</td>' +
																'<td  >' + wo.dateOfBirth + '</td>' +
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 '<td  >' +wo.vendorCode + '</td>' +	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
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
										
										function searchDeBlackList() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/deblackList',
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
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                          '<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + '</td>' +
																 '<td  >' + wo.lastName + '</td>' +	
																											  
																'<td  >' + wo.gender + '</td>' +
																'<td  >' + wo.dateOfBirth + '</td>' +
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 '<td  >' +wo.vendorCode + '</td>' +	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
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
										function searchCancel() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/cancel',
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
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                          '<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + '</td>' +
																 '<td  >' + wo.lastName + '</td>' +	
																											  
																'<td  >' + wo.gender + '</td>' +
																'<td  >' + wo.dateOfBirth + '</td>' +
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 '<td  >' +wo.vendorCode + '</td>' +	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
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
										function searchLost() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/lostordamage',
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
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
										                          '<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + '</td>' +
																 '<td  >' + wo.lastName + '</td>' +	
																											  
																'<td  >' + wo.gender + '</td>' +
																'<td  >' + wo.dateOfBirth + '</td>' +
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 '<td  >' +wo.vendorCode + '</td>' +	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
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
										
										function getEic() {
											 var principalEmployerSelect = document.getElementById("principalEmployer");
										    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
										    var deptSelect = document.getElementById("department");
										    var deptId = deptSelect.value; // Get the selected contractor value
										    
										 if (!unitId) {
										        alert("Please select a Principal Employer.");
										        return;
										    }
										    
										  if(!deptId){
											 alert("Please select a Department.");
										        return;
										  }

										    getEicList(unitId, deptId);

										    
										}
										function getEicList(unitId,deptId) {
										    var xhr = new XMLHttpRequest();
										    var url = contextPath + "/contractworkmen/getAllEic?unitId=" + unitId + "&deptId=" + deptId;
										    //alert("URL: " + url);
										    xhr.open("GET", url, true);

										    xhr.onload = function() {
										        if (xhr.status === 200) {
										            // Parse the response as a JSON array of workorder objects
										            var eics = JSON.parse(xhr.responseText);
										            console.log("Response:", eics);
										            
										            // Find the workorder select element
										            var eicSelect = document.getElementById("eic");
										            
										            // Clear existing options
										            eicSelect.innerHTML = '<option value="">Please select EIC</option>';
										            
										            // Populate the dropdown with the new list of workorders
										            eics.forEach(function(eiclist) {
										                var option = document.createElement("option");
										                option.value = eiclist.userId;
										                option.text = eiclist.fullName;
										                eicSelect.appendChild(option);
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
										function draftGatePass(userId) {
										const data = new FormData();
										        const jsonData = {
													transactionId:$("#transactionId").val().trim(),
										            aadhaarNumber: $("#aadharNumber").val().trim(),
										            firstName: $("#firstName").val().trim(),
										            lastName: $("#lastName").val().trim(),
										            dateOfBirth: $("#dateOfBirth").val().trim(),
										            gender: $("#gender").val(),
										            relationName: $("#relationName").val().trim(),
										            idMark: $("#idMark").val().trim(),
										            mobileNumber: $("#mobileNumber").val().trim(),
										            maritalStatus: $("#maritalStatus").val(),
										            principalEmployer: $("#principalEmployer").val(),
										            contractor: $("#contractor").val(),
										            workorder: $("#workorder").val(),
										            trade: $("#trade").val(),
										            skill: $("#skill").val(),
										            department: $("#department").val(),
										            subdepartment: $("#subdepartment").val(),
										            eic: $("#eic").val(),
										            natureOfJob: $("#natureOfJob").val().trim(),
										            wcEsicNo: $("#wc").val(),
										            hazardousArea: $("#hazardousArea").val(),
										            accessArea: $("#accessArea").val(),
										            uanNumber: $("#uanNumber").val().trim(),
										            healthCheckDate: $("#healthCheckDate").val().trim(),
										            bloodGroup: $("#bloodGroup").val(),
										            accommodation: $("#accommodation").val(),
										            academic: $("#academic").val(),
										            technical: $("#technical").val(),
										            ifscCode: $("#ifscCode").val().trim(),
										            accountNumber: $("#accountNumber").val().trim(),
										            emergencyName: $("#emergencyName").val().trim(),
										            emergencyNumber: $("#emergencyNumber").val().trim(),
										            wageCategory: $("#wageCategory").val(),
										            bonusPayout: $("#bonusPayout").val(),
										            pfCap: $("#pfCap").val(),
										            zone: $("#zone").val(),
										            basic: $("#basic").val().trim(),
										            da: $("#da").val().trim(),
										            hra: $("#hra").val().trim(),
										            washingAllowance: $("#washingAllowance").val().trim(),
										            otherAllowance: $("#otherAllowance").val().trim(),
										            uniformAllowance: $("#uniformAllowance").val().trim(),
										            userId: userId,
										            gatePassAction: "save",
										            comments: $("#comments").val().trim(),
													address:$("#address").val().trim(),
													doj:$("#doj").val(),
										        };

										        // Serialize the JSON object to a string
												const jsonString = JSON.stringify(jsonData);

												// Append the JSON data to FormData
												data.append("jsonData", jsonString);
										   const xhr = new XMLHttpRequest();
										        xhr.open("POST", "/CWFM/contractworkmen/draftGatePass", true);

										        xhr.onload = function () {
										            if (xhr.status === 200) {
										                console.log("Data saved successfully:", xhr.responseText);
										                loadCommonList('/contractworkmen/list', 'On-Boarding List');
														
										            } else {
										                console.error("Error saving data:", xhr.status, xhr.responseText);
										            }
										        };

										        xhr.onerror = function () {
										            console.error("Request failed");
										        };

										        // Send the FormData object
										        xhr.send(data);
										    } 
											
												
												function redirectToWorkmenEdit() {
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
											    xhr.open("GET", "/CWFM/contractworkmen/getDraftDetails/" + transactionId, true);
											    xhr.send();
											}
