var divIndex=0;
//let selectedDocumentTypes = new Set();
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
    
    getDepartments(unitId);
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

    //getWC(unitId, contractorId);
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

function getWC() {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var contractorSelect = document.getElementById("contractor");
    var contractorId = contractorSelect.value; 
     var workorderSelect =document.getElementById("workorder");
     var workorderId = workorderSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllWC?unitId=" + unitId + "&contractorId=" + contractorId +"&workorderId=" + workorderId;
    //alert("URL: " + url+" "+unitId+" "+contractorId+" "+workorderId);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            var wcs = JSON.parse(xhr.responseText);
            console.log("Response:", wcs);
            
            var wcSelect = document.getElementById("wc");
			var llSelect = document.getElementById("ll");
            wcSelect.innerHTML = '<option value="">Please select WC Policy/ESIC Reg Number</option>';
			llSelect.innerHTML = '<option value="">Please select Labor License</option>';
            wcs.forEach(function(wc) {
                var option = document.createElement("option");
                option.value = wc.wcId;
                option.text = wc.wcCode;
				if (wc.licenceType === "LL") {
				                    llSelect.appendChild(option);
				                } else if (wc.licenceType === "WC" || wc.licenceType === "ESIC") {
				                    wcSelect.appendChild(option);
				                }
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
function getSkills(unitId,tradeId) {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var tradeSelect = document.getElementById("trade");
    var tradeId = tradeSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllSkills?unitId=" + unitId + "&tradeId=" + tradeId;
    console.log("Fetching trades from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var skills = JSON.parse(xhr.responseText);
            console.log("Skills:", skills);
            var skillSelect = document.getElementById("skill");

            // Clear existing options
            skillSelect.innerHTML = '<option value="">Please select Skill</option>';

            // Populate the trade dropdown
            skills.forEach(function (skill) {
                var option = document.createElement("option");
                option.value = skill.skillId;
                option.text = skill.skill;
                skillSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching skills:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching skill");
    };

    xhr.send();
}

function getDepartments(unitId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllDepartments?unitId=" + unitId;
    console.log("Fetching departments from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var departments = JSON.parse(xhr.responseText);
            console.log("Departments:", departments);
            var departmentSelect = document.getElementById("department");

            // Clear existing options
            departmentSelect.innerHTML = '<option value="">Please select Department</option>';

            // Populate the trade dropdown
            departments.forEach(function (department) {
                var option = document.createElement("option");
                option.value = department.departmentId;
                option.text = department.department;
                departmentSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching departments:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching departments");
    };

    xhr.send();
}

function getAreabyDept(unitId,departmentId) {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var departmentSelect = document.getElementById("department");
    var departmentId = departmentSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllSubDepartments?unitId=" + unitId + "&departmentId=" + departmentId;
    console.log("Fetching subdepartment from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var subdepartment = JSON.parse(xhr.responseText);
            console.log("Areas:", subdepartment);
            var subdepartmentSelect = document.getElementById("subdepartment");

            // Clear existing options
            subdepartmentSelect.innerHTML = '<option value="">Please select Area</option>';

            // Populate the trade dropdown
            subdepartment.forEach(function (subdepartment) {
                var option = document.createElement("option");
                option.value = subdepartment.subDepartmentId;
                option.text = subdepartment.subDepartment;
                subdepartmentSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching subdepartments:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching subdepartment");
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
	        minDate: 0,
			maxDate: +15               // Prevent selecting future dates
	    });
	} 
    function validateBasicData() {
    let isValid = true;
	let aadharCheckPassed = false;
    const aadharNumber = $("#aadharNumber").val().trim();

	const transactionId=$("#transactionId").val().trim();
    if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {

        $("#error-aadhar").show();
        isValid = false;
    }else{
		 // $("#error-aadhar").hide();
		  

		         // Check in backend if Aadhar exists
		         $.ajax({
		             url: "/CWFM/contractworkmen/checkAadharExists",
		             type: "GET",
		             data: { aadharNumber: aadharNumber,transactionId : transactionId },
		             async: false, // NOTE: synchronous to block form submission
		             success: function (response) {
		                 if (response.exists) {
		                     $("#error-aadhar").text("Aadhar number already exists").show();
		                     isValid = false;
		                 } else {
		                     $("#error-aadhar").hide();
		                     aadharCheckPassed = true;
		                 }
		             },
		             error: function () {
		                 $("#error-aadhar").text("Error checking Aadhar").show();
		                 isValid = false;
		             }
		         });
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
	/*if (firstName.toLowerCase() === lastName.toLowerCase()) {
    $("#error-equalNames").show();
    isValid = false;
    } else {
    $("#error-equalNames").hide();
    }*/
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
console.log(isValid);
    return isValid && aadharCheckPassed;

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
	const nojRegex = /^(?=.*[A-Za-z]{2,})[A-Za-z\s]+$/;
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
	const ll = $("#ll").val();
     if (ll === "") {
        $("#error-ll").show();
        isValid = false;
    }else{
		$("#error-ll").hide();
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
	let uanCheckPassed = false;
	
	 const uan = $("#uanNumber").val().trim();
	 
	 const aadharNumber = $("#aadharNumber").val().trim();
	 const uanRegex = /^\d{12}$/;
    if (!uanRegex.test(uan)) {
        $("#error-uanNumber").show();
        isValid = false;
    }else if (uan === "000000000000") {
		    // UAN is 12 zeros, skip duplicate check
		    $("#error-uanNumber").hide();
		    uanCheckPassed = true;
		}else{
	  // Check in backend if Aadhar exists
		         $.ajax({
		             url: "/CWFM/contractworkmen/checkUanExists",
		             type: "GET",
		             data: { uan: uan,aadharNumber : aadharNumber },
		             async: false, // NOTE: synchronous to block form submission
		             success: function (response) {
		                 if (response.exists) {
                             $("#error-uanNumber").text("UAN already exists with Aadhaar Number: " + response.otherAadhar).show();
							 uanCheckPassed = true;
                              isValid = false;
                           } else {
		                     $("#error-uanNumber").hide();
		                     uanCheckPassed = true;
		                 }
		             },
		             error: function () {
		                 $("#error-uanNumber").text("Error checking UAN").show();
		                 isValid = false;
		             }
		         });
		     }       

	const healthCheckDate = $("#healthCheckDate").val().trim();
    if (healthCheckDate === "") {
        $("#error-healthCheckDate").show();
        isValid = false;
    }else{
		 $("#error-healthCheckDate").hide();
	}
	let pfNumberCheckPassed = false;
	const pfNumber = $("#pfNumber").val().trim();
	const cleanedPf = pfNumber.trim().replace(/\s+/g, '').toLowerCase();
	if (!cleanedPf ) {
	    $("#error-pfNumber").show();
	    isValid = false;
	} 
	else if (cleanedPf === "newjoinee") {
	    // Special case → skip validation, do NOT show error
	    $("#error-pfNumber").hide();
	    pfNumberCheckPassed = true;
	}else {
	    const pfRegex = /^[A-Z]{2}[A-Z]{3}\d{7}\d{2}[A-Z0-9]\d{7}$/;
	    
		const cmpPfRegex = /^CMPPF[A-Z0-9]+$/i;

	    let isPfValid = false;

	    if (pfNumber.length === 22) {
	        if (pfRegex.test(pfNumber)) {
	            isPfValid = true;
	        } else {
	            $("#error-pfNumber").text("Invalid PF Number format. Expected 22-character structured PF number.").show();
	            isValid = false;
	        }
	    } else if (cmpPfRegex.test(pfNumber)) {
	        isPfValid = true;
	    } else {
	        $("#error-pfNumber").text("Invalid PF Number. It should either be 22 characters in PF format or start with CMPPF.").show();
	        isValid = false;
	    }

	    // Only proceed with AJAX call if PF format is valid
	    if (isPfValid) {
	        $.ajax({
	            url: "/CWFM/contractworkmen/checkpfNumberExists",
	            type: "GET",
	            data: { pfNumber: pfNumber, aadharNumber: aadharNumber },
	            async: false,
	            success: function (response) {
	                if (response.exists) {
	                    $("#error-pfNumber").text("PF Number already exists with Aadhaar Number: " + response.otherAadhar).show();
	                    isValid = false;
	                } else {
	                    $("#error-pfNumber").hide();
	                    pfNumberCheckPassed = true;
	                }
	            },
	            error: function () {
	                $("#error-pfNumber").text("Error checking PF Number").show();
	                isValid = false;
	            }
	        });
	    }
	}
    
	const esicNumber = $("#esicNumber").val().trim();
    if (esicNumber === "") {
        $("#error-esicNumber").show();
        isValid = false;
    }else{
		 $("#error-esicNumber").hide();
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
       // $("#error-wageCategory").show();
       // isValid = false;
    }else{
		$("#error-wageCategory").hide();
	}
	const bonus = $("#bonusPayout").val();
     if (bonus === "") {
        //$("#error-bonusPayout").show();
        //isValid = false;
    }else{
		$("#error-bonusPayout").hide();
	}
	const zone = $("#zone").val();
     if (zone === "") {
       // $("#error-zone").show();
        //isValid = false;
    }else{
		$("#error-zone").hide();
	}
	const allowanceRegex = /^[0-9]{1,6}(\.[0-9]{1,2})?$/;
	const basic = $("#basic").val().trim();
	const da = $("#da").val().trim();
	const hra = $("#hra").val().trim();
	const washing = $("#washingAllowance").val().trim();
	const other = $("#otherAllowance").val().trim();
	const uniform = $("#uniformAllowance").val().trim();
	if(basic === ""){
		$("#basic").val("0.00"); 
	//not mandatory	
	}else 	if ( !allowanceRegex.test(basic) ) {
             $("#error-basic").show();
       		 isValid = false; 
			  
      }else{
		 $("#error-basic").hide();
	  }
	  if(da === ""){
		$("#da").val("0.00"); 
	  }else	  if ( !allowanceRegex.test(da) ) {
             $("#error-da").show();
       		 isValid = false;    
      }else{
		 $("#error-da").hide();
	  }
	  if(hra === ""){
		$("#hra").val("0.00"); 
	  }else	  if ( !allowanceRegex.test(hra) ) {
             $("#error-hra").show();
       		 isValid = false;    
      }else{
		 $("#error-hra").hide();
	  }
	  if(washing === ""){
		$("#washingAllowance").val("0.00"); 
	  }else	  if ( !allowanceRegex.test(washing) ) {
             $("#error-washingAllowance").show();
       		 isValid = false;    
      }else{
		 $("#error-washingAllowance").hide();
	  }
	  if(other === ""){
		$("#otherAllowance").val("0.00"); 
		  	  }else	  if ( !allowanceRegex.test(other) ) {
             $("#error-otherAllowance").show();
       		 isValid = false;    
      }else{
		 $("#error-otherAllowance").hide();
	  }
	  if(uniform === ""){
		$("#uniformAllowance").val("0.00"); 
	  }else	  if ( !allowanceRegex.test(uniform) ) {
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
function validateFiles(aadharFile, policeFile, profilePc) {
    let valid = true;

    // Aadhar File - Mandatory & Size check
    if (!aadharFile) {
        $("#aadharError").text("Aadhar file is required").addClass("error-bold");
        valid = false;
    } else if (aadharFile.size > 5 * 1024 * 1024) {
        $("#aadharError").text("Aadhar file must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#aadharError").text("");
    }

    // Police Verification File - Mandatory & Size check
    if (!policeFile) {
        $("#policeError").text("Police verification file is required").addClass("error-bold");
        valid = false;
    } else if (policeFile.size > 5 * 1024 * 1024) {
        $("#policeError").text("Police file must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#policeError").text("");
    }

// Police Verification Date - Mandatory  check
const policeVerificationDate = $("#policeVerificationDate").val().trim();
    if (policeVerificationDate === "") {
        $("#error-policeVerificationDate").show();
        valid = false;
    }else {
        $("#error-policeVerificationDate").hide("");
    }
    
    // Profile Photo - Mandatory & Size check
    if (!profilePc) {
        $("#profilePcError").text("Profile photo is required").addClass("error-bold");
        valid = false;
    } else if (profilePc.size > 5 * 1024 * 1024) {
        $("#profilePcError").text("Photo/Image must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#profilePcError").text("");
    }

	const comments = $("#comments").val().trim();
		    if (comments === "") {
		        $("#error-comments").show();
		        valid = false;
		    }else{
				 $("#error-comments").hide();
			}
			// Accept Checkbox validation
			    if (!$("#acceptCheck").is(":checked")) {
			        $("#acceptError").show();
			        valid = false;
			    } else {
			        $("#acceptError").hide();
			    }		
    return valid;
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
    
    function approveRejectGatePass(status,type){
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
			sessionStorage.setItem("successMessage", "Gatepass approved/rejected successfully!");
            if(type=== "regular"){
                    loadCommonList('/contractworkmen/list', 'On-Boarding List');
                }else{
                    loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
                }
        } else {
            // Handle error response
            console.error("Error saving data:", xhr.statusText);
			sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass!");
        }
    };
    
    xhr.onerror = function() {
        console.error("Request failed");
		sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass!");
    };
    
    // Send the data object as a JSON string
    xhr.send(JSON.stringify(data));
		}else{
			//error 
		}
		}//eofunc
		
		
		
	

    

	function submitGatePass(userId,type) {
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

	if(type=== "regular"){
        if (!validateOtherInformation()) {
            otherValid = false;
        }

        if (!validateWages()) {
            wagesValid = false;
        }
    }else{
		otherValid = true;
		wagesValid = true;
		$("#uniformAllowance").val("0.00"); 
		$("#washingAllowance").val("0.00");  	
		$("#hra").val("0.00");  	
		$("#da").val("0.00"); 
		$("#basic").val("0.00"); 
		$("#otherAllowance").val("0.00"); 
	}
  if (!validatePfForm11Requirement()) {
        documentValid = false;
    }
    console.log("basicValid: " + basicValid);
    console.log("employmentValid: " + employmentValid);
    console.log("otherValid: " + otherValid);
    console.log("wagesValid: " + wagesValid);
    console.log("documentValid: " + documentValid);

    // ✅ Utility function for Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }

    // ✅ Capital case transformation
    const firstName = toCapitalCase($("#firstName").val().trim());
    const lastName = toCapitalCase($("#lastName").val().trim());
    const relationName = toCapitalCase($("#relationName").val().trim());
    const natureOfJob = toCapitalCase($("#natureOfJob").val().trim());
    const emergencyName = toCapitalCase($("#emergencyName").val().trim());
    const address = toCapitalCase($("#address").val().trim());
    const idMark = toCapitalCase($("#idMark").val().trim());
    const pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";

    if (basicValid && employmentValid && otherValid && wagesValid && documentValid) {
        const data = new FormData();
        const jsonData = {
			transactionId:$("#transactionId").val().trim(),
            aadhaarNumber: $("#aadharNumber").val().trim(),
            firstName: firstName,
            lastName: lastName,
            dateOfBirth: $("#dateOfBirth").val().trim(),
            gender: $("#gender").val(),
            relationName: relationName,
            idMark: idMark,
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
            natureOfJob: natureOfJob,
            wcEsicNo: $("#wc").val(),
			llNo:$("#ll").val(),
            hazardousArea: $("#hazardousArea").val(),
            accessArea: $("#accessArea").val(),
            uanNumber: $("#uanNumber").val().trim(),
            healthCheckDate: $("#healthCheckDate").val().trim(),
            pfNumber:$("#pfNumber").val().trim(),
			esicNumber:$("#esicNumber").val().trim(),
            bloodGroup: $("#bloodGroup").val(),
            accommodation: $("#accommodation").val(),
            academic: $("#academic").val(),
            technical: $("#technical").val(),
            ifscCode: $("#ifscCode").val().trim(),
            accountNumber: $("#accountNumber").val().trim(),
            emergencyName: emergencyName,
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
			address: address,
			doj:$("#doj").val(),
			pfApplicable:pfApplicable,
            policeVerificationDate: $("#policeVerificationDate").val().trim(),
			onboardingType:type,
        };

        const jsonString = JSON.stringify(jsonData);
		data.append("jsonData", jsonString);

        if (aadharFile) data.append("aadharFile", aadharFile);
        if (policeFile) data.append("policeFile", policeFile);
		if(profilePic) data.append("profilePic",profilePic);

    	const additionalFields = document.querySelectorAll('.document-field');
        additionalFields.forEach((field) => {
            const docType = field.querySelector('select[name="documentType"]').value;
            const fileInput = field.querySelector('input[type="file"]');
            if (docType && fileInput.files[0]) {
                data.append('additionalFiles', fileInput.files[0]);
                data.append('documentTypes', docType);
            }
        });

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/contractworkmen/saveGatePass", true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
				sessionStorage.setItem("successMessage", "Gatepass saved successfully!");
                if(type=== "regular"){
                    loadCommonList('/contractworkmen/list', 'On-Boarding List');
                }else{
                    loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
                }
            } else {
                console.error("Error saving data:", xhr.status, xhr.responseText);
				sessionStorage.setItem("errorMessage", "Failed to save Gatepass!");
            }
        };

        xhr.onerror = function () {
            console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to save Gatepass!");
        };

        xhr.send(data);
    } else {
        console.error("Validation failed for one or more fields.");
    }
}

function downloadDoc(transactionId, userId, docType) {
    const baseUrl = '/CWFM/contractworkmen/downloadFile';
    const url = `${baseUrl}/${transactionId}/${userId}/${docType}`;

    // Open the document in a new browser tab
    window.open(url, '_blank');
    // Construct the URL based on gatePassId, userId, and docType
    //const url = `${baseUrl}/${transactionId}/${userId}/${docType}`;
	//alert("url is"+url);
    // Create a temporary anchor element
   /* const a = document.createElement('a');
    a.href = url;
    a.download = ''; // This attribute tells the browser to download the file

    // Append to the body to make it work in Firefox
    document.body.appendChild(a);

    // Programmatically click the anchor
    a.click();

    // Remove the anchor from the document
    document.body.removeChild(a);*/
}



//let divIndex = 0;

function additionalDocUpload() {
    const currentCount = $(".document-field").length;
    if (currentCount >= 7) {
        alert("You can add a maximum of 7 additional documents.");
        return;
    }

    divIndex++;

    const docOptions = [
        "Bank", "Id2", "Other", "Medical",
        "Education", "Training", "Form11"
    ];

    // Generate options dynamically while avoiding already selected ones
    const selectedValues = $(".document-field select").map(function () {
        return $(this).val();
    }).get();

    let optionsHtml = '<option value="">Select Document Type</option>';
    docOptions.forEach(function (opt) {
        if (!selectedValues.includes(opt)) {
            optionsHtml += '<option value="' + opt + '">' + opt + '</option>';
        }
    });

    // Unique IDs for the new row
    const fileNameId = 'fileName-' + divIndex;

    const newField = `
        <div class="document-field" id="document-field-${divIndex}" style="display: flex; align-items: center; margin-bottom: 10px;">
            <select name="documentType" onchange="updateDocTypeDropdowns()" style="margin-right: 10px;color:black;">
                ${optionsHtml}
            </select>
            <input type="file" accept="application/pdf" style="margin-right: 10px;" onchange="displayFileName(this, '${fileNameId}')">
            <span id="${fileNameId}" style="margin-right: 10px;color:black;"></span>
            <button type="button" onclick="removeDocument(${divIndex})" style="color:black;">Remove</button>
        </div>
    `;

    $("#additionalDoc").append(newField);
    updateDocTypeDropdowns(); // Refresh all dropdowns to disable already-selected options
}

function removeDocument(index) {
    $(`#document-field-${index}`).remove();
    updateDocTypeDropdowns(); // Update dropdowns after removal
}

function displayFileName(inputElement, displayId) {
    const displayElement = document.getElementById(displayId);
    if (inputElement.files.length > 0) {
        displayElement.textContent = inputElement.files[0].name;
    } else {
        displayElement.textContent = '';
    }
}

function updateDocTypeDropdowns() {
    const allOptions = [
        "Bank", "Id2", "Other", "Medical",
        "Education", "Training", "Form11"
    ];

    const selectedValues = $(".document-field select").map(function () {
        return $(this).val();
    }).get();

    $(".document-field select").each(function () {
        const currentSelect = $(this);
        const currentValue = currentSelect.val();

        currentSelect.empty().append('<option value="">Select Document Type</option>');

        allOptions.forEach(function (opt) {
            const isDisabled = selectedValues.includes(opt) && opt !== currentValue;
            const optionTag = $('<option>', {
                value: opt,
                text: opt,
                disabled: isDisabled,
                selected: opt === currentValue
            });
            currentSelect.append(optionTag);
        });
    });
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
		transactionId : $("#transactionId").val().trim(),
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
		   sessionStorage.setItem("successMessage", "Gatepass cancel request raised successfully!");
         loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
		   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass cancel request!");
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
	   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass cancel request!");
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
		transactionId : $("#transactionId").val().trim(),
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
		   sessionStorage.setItem("successMessage", "Gatepass cancel request approved/rejected successfully!");
         loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
		   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass cancel request!");
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
	   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass cancel request!");
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
			transactionId : $("#transactionId").val().trim(),
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
			   sessionStorage.setItem("successMessage", "Gatepass block request raised successfully!");
	         loadCommonList('/contractworkmen/blockListFilter', 'Block List');
	       } else {
	           // Handle error response
	           console.error("Error saving data:", xhr.statusText);
			   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass block request!");
	       }
	   };
	   
	   xhr.onerror = function() {
	       console.error("Request failed");
		   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass block request!");
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
			transactionId : $("#transactionId").val().trim(),
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
			   sessionStorage.setItem("successMessage", "Gatepass block request approved/rejected successfully!");
	         loadCommonList('/contractworkmen/blockListFilter', 'Block List');
	       } else {
	           // Handle error response
	           console.error("Error saving data:", xhr.statusText);
			   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass block request!");
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
					transactionId : $("#transactionId").val().trim(),
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
					   sessionStorage.setItem("successMessage", "Gatepass unblock request raised successfully!");
			         loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
			       } else {
			           // Handle error response
			           console.error("Error saving data:", xhr.statusText);
					   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass unblock request!");
			       }
			   };
			   
			   xhr.onerror = function() {
			       console.error("Request failed");
				   sessionStorage.setItem("errorMessage", "Failed to raise Gatepass unblock request!");
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
				transactionId : $("#transactionId").val().trim(),
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
				   sessionStorage.setItem("successMessage", "Gatepass unblock approved/rejected successfully!");
		         loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
		       } else {
		           // Handle error response
		           console.error("Error saving data:", xhr.statusText);
				   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass unblock request!");
		       }
		   };
		   
		   xhr.onerror = function() {
		       console.error("Request failed");
			   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass unblock request!");
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
		transactionId : $("#transactionId").val().trim(),
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
		sessionStorage.setItem("successMessage", "Gatepass blacklist request raised successfully!");
      loadCommonList('/contractworkmen/blackListFilter', 'Black List');
    } else {
        // Handle error response
        console.error("Error saving data:", xhr.statusText);
		sessionStorage.setItem("errorMessage", "Failed to raise Gatepass blacklist request!");
    }
};

xhr.onerror = function() {
    console.error("Request failed");
	sessionStorage.setItem("errorMessage", "Failed to raise Gatepass blacklist request!");
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
	transactionId : $("#transactionId").val().trim(),
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
							   sessionStorage.setItem("successMessage", "Gatepass blacklist request approved/rejected successfully!");
					         loadCommonList('/contractworkmen/blackListFilter', 'Black List');
					       } else {
					           // Handle error response
					           console.error("Error saving data:", xhr.statusText);
					       }
					   };
					   
					   xhr.onerror = function() {
					       console.error("Request failed");
						   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass blacklist request!");
					   };
					   
					   // Send the data object as a JSON string
					   xhr.send(JSON.stringify(data));
					   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass blacklist request!");
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
					transactionId : $("#transactionId").val().trim(),
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
					sessionStorage.setItem("successMessage", "Gatepass deblacklist request raised successfully!");
			      loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
			    } else {
			        // Handle error response
			        console.error("Error saving data:", xhr.statusText);
					sessionStorage.setItem("errorMessage", "Failed to raise Gatepass deblacklist request!");
			    }
			};
			
			xhr.onerror = function() {
			    console.error("Request failed");
				sessionStorage.setItem("errorMessage", "Failed to raise Gatepass deblacklist request!");
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
				transactionId : $("#transactionId").val().trim(),
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
				sessionStorage.setItem("successMessage", "Gatepass deblacklist request approved/rejected successfully!");
		      loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
		    } else {
		        // Handle error response
		        console.error("Error saving data:", xhr.statusText);
				sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass deblacklist request!");
		    }
		};
		
		xhr.onerror = function() {
		    console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass deblacklist request!");
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
	transactionId : $("#transactionId").val().trim(),
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
		sessionStorage.setItem("successMessage", "Gatepass lost or damage request raised successfully!");
	   loadCommonList('/contractworkmen/lostordamageFilter', 'Lost or Damage List');
    } else {
        // Handle error response
        console.error("Error saving data:", xhr.statusText);
		sessionStorage.setItem("errorMessage", "Failed to raise Gatepass lost or damage request!");
    }
};

xhr.onerror = function() {
    console.error("Request failed");
	sessionStorage.setItem("errorMessage", "Failed to raise Gatepass lost or damage request!");
};

// Send the data object as a JSON string
xhr.send(JSON.stringify(data));
}else{
	//error 
}
}//eofunc
function redirectToWorkmenCancelView(mode) {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	if(mode === "add"){
	 												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

	 												   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
	 												       alert("Cancel request already created.");
	 												       return;
	 												   }}


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/cancelview/" + gatePassId+"/"+mode, true);
    xhr.send();
}
function redirectToWorkmenBlockView(mode) {
 var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
 if (selectedCheckboxes.length !== 1) {
     alert("Please select exactly one row to view.");
     return;
 }
 
 var selectedRow = selectedCheckboxes[0].closest('tr');
 var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
 var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
 												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
												   if(mode === "add"){
 												   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
 												       alert("Block request already created.");
 												       return;
 												   }
												   }
 var xhr = new XMLHttpRequest();
 xhr.onreadystatechange = function() {
     if (xhr.readyState == 4 && xhr.status == 200) {
         document.getElementById("mainContent").innerHTML = xhr.responseText;
     }
 };
 xhr.open("GET", "/CWFM/contractworkmen/blockview/" + transactionId+ "/" + mode, true);
 xhr.send();
 }
 function redirectToWorkmenUnblockView(mode) {
  var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
  if (selectedCheckboxes.length !== 1) {
      alert("Please select exactly one row to view.");
      return;
  }
  
  var selectedRow = selectedCheckboxes[0].closest('tr');
  var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
  var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
  												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
												   if(mode === "add"){
  												   if (gatePassType.toLowerCase() !== "block" || status.toLowerCase() !== "approved") {
  												       alert("UnBlock request already created.");
  												       return;
  												   }}
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
          document.getElementById("mainContent").innerHTML = xhr.responseText;
      }
  };
  xhr.open("GET", "/CWFM/contractworkmen/unblockview/" + transactionId+"/"+mode, true);
  xhr.send();
  }
  function redirectToWorkmenBlackView(mode) {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
													   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
													   if(mode === "add"){
													   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
													       alert("Black request already created.");
													       return;
													   }}
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/blackview/" + transactionId+ "/" + mode, true);
    xhr.send();
    }
	function redirectToWorkmenDeblackView(mode) {
	  var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	  if (selectedCheckboxes.length !== 1) {
	      alert("Please select exactly one row to view.");
	      return;
	  }
	  
	  var selectedRow = selectedCheckboxes[0].closest('tr');
	  var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
	  var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	  												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
													   if(mode === "add"){
	  												   if (gatePassType.toLowerCase() !== "blacklist" || status.toLowerCase() !== "approved") {
	  												       alert("Deblack request already created.");
	  												       return;
	  												   }}
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
	      if (xhr.readyState == 4 && xhr.status == 200) {
	          document.getElementById("mainContent").innerHTML = xhr.responseText;
	      }
	  };
	  xhr.open("GET", "/CWFM/contractworkmen/deblackview/" + transactionId+"/"+mode, true);
	  xhr.send();
	  }
	  function redirectToWorkmenLostView(mode) {
	    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	    if (selectedCheckboxes.length !== 1) {
	        alert("Please select exactly one row to view.");
	        return;
	    }
	    
	    var selectedRow = selectedCheckboxes[0].closest('tr');
	    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
		var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
														   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
														   if(mode === "add"){
														   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
														       alert("Lost or damange request already created.");
														       return;
														   }}
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/contractworkmen/lostordamageview/" + transactionId+"/"+mode, true);
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


function previewImage(event, inputId, displayId) {
    const fileInput = document.getElementById(inputId);
    const displayElement = document.getElementById(displayId);

    // Display the file name
    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        displayElement.textContent = fileName;
    } else {
        displayElement.textContent = '';
    }

    // Preview image from file
    const file = event.target.files[0];
    if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const previewDiv = document.getElementById("preview");
            previewDiv.innerHTML = ""; // Clear previous content
            previewDiv.innerHTML = `<img src="${e.target.result}" alt="Image Preview" style="max-width: 100%; max-height: 100%;">`;
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
		function searchGatePassBasedOnPE(type) {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/contractworkmen/quickOBList',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId,
								deptId:deptId,
								type:type
					        },
					        success: function(response) {
					            var tableBody = $('#workmenTable tbody');
								// 🔄 Clear previous DataTable and its config
								           if ($.fn.DataTable.isDataTable('#workmenTable')) {
								               $('#workmenTable').DataTable().destroy();
								           }
										   tableBody.empty();
					            if (Array.isArray(response) &&response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
												'<td  >' + wo.transactionId + '</td>' +
												 '<td  >' + wo.gatePassId + '</td>' +
					                              '<td  >' + wo.firstName+' ' +wo.lastName + '</td>' +
												 
												  
												  
												  '<td  >' + wo.aadhaarNumber + '</td>' +	
												  '<td  >' + wo.contractorName + '</td>' +	
												 
												  '<td  >' +wo.unitName + '</td>' +	
												  '<td  >' + wo.gatePassType + '</td>' +
												  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
												  '<td  >' + wo.status + '</td>' +				                             
					                              '</tr>';
					                    tableBody.append(row);
					                });
									
					            } 								

																	            // ✅ Always init after rows are drawn
																	            initWorkmenTable("workmenTable");
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
													// 🔄 Clear previous DataTable and its config
																					           if ($.fn.DataTable.isDataTable('#workmenTable')) {
																					               $('#workmenTable').DataTable().destroy();
																					           }
										            tableBody.empty();
										            if (Array.isArray(response) &&response.length > 0) {
										                $.each(response, function(index, wo) {
										                    var row = '<tr  >' +
																	'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																	'<td  >' + wo.transactionId + '</td>' +
										                              '<td  >' + wo.gatePassId + '</td>' +
										                              '<td  >' + wo.firstName + ' '+wo.lastName +'</td>' +
																	  
																	  
																	  
																	  '<td  >' + wo.aadhaarNumber + '</td>' +	
																	  '<td  >' + wo.contractorName + '</td>' +	
																	
																	  '<td  >' +wo.unitName + '</td>' +	
																	  '<td  >' + wo.gatePassType + '</td>' +
																	  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																	  '<td  >' + wo.status + '</td>' +				                             
										                              '</tr>';
										                    tableBody.append(row);
										                });
										            }
													// ✅ Always init after rows are drawn
													 initWorkmenTable("workmenTable");
													 
										        },
										        error: function(xhr, status, error) {
										            console.error("Error fetching data:", error);
										        }
										    });
										}
										function searchUnBlockList() {
										    var principalEmployerId = $('#principalEmployerId').val();
										    var deptId = $("#deptId").val();

										    $.ajax({
										        url: '/CWFM/contractworkmen/unblockList',
										        type: 'POST',
										        data: {
										            principalEmployerId: principalEmployerId,
										            deptId: deptId
										        },
										        success: function(response) {
										            var tableBody = $('#workmenTable tbody');

										            if ($.fn.DataTable.isDataTable('#workmenTable')) {
										                $('#workmenTable').DataTable().destroy();
										            }

										            tableBody.empty();

										            if (Array.isArray(response) && response.length > 0) {
										                $.each(response, function(index, wo) {
										                    var row = '<tr>' +
										                        '<td><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>' +
										                        '<td>' + wo.transactionId + '</td>' +
										                        '<td>' + wo.gatePassId + '</td>' +
										                        '<td>' + wo.firstName + ' ' + wo.lastName + '</td>' +
										                        '<td>' + wo.aadhaarNumber + '</td>' +
										                        '<td>' + wo.contractorName + '</td>' +
										                        '<td>' + wo.unitName + '</td>' +
										                        '<td>' + wo.gatePassType + '</td>' +
										                        '<td>' + toCapitalCase(wo.onboardingType) + '</td>' +
										                        '<td>' + wo.status + '</td>' +
										                        '</tr>';
										                    tableBody.append(row);
										                });
										            } 

										            // ✅ Always init after rows are drawn
										            initWorkmenTable("workmenTable");
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
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
																			               $('#workmenTable').DataTable().destroy();
																			           }
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ' '+ wo.lastName +'</td>' +
																
																											  
															
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        }
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
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
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ''  + wo.lastName + '</td>' +
																
																											  
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        } 
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
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
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ' '+ wo.lastName +'</td>' +
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
														
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        }
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
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
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName +''+ wo.lastName + '</td>' +
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
															
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        } 
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
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
											    // ✅ Utility function for Capital Case
                                                   function toCapitalCase(str) {
                                                     return str
                                                    .toLowerCase()
                                                    .split(' ')
                                                    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                                                    .join(' ');
                                                   }
                                         // ✅ Capital case transformation
                                          const firstName = toCapitalCase($("#firstName").val().trim());
                                          const lastName = toCapitalCase($("#lastName").val().trim());
                                          const relationName = toCapitalCase($("#relationName").val().trim());
                                          const natureOfJob = toCapitalCase($("#natureOfJob").val().trim());
                                          const emergencyName = toCapitalCase($("#emergencyName").val().trim());
                                          const address = toCapitalCase($("#address").val().trim());
                                          const idMark = toCapitalCase($("#idMark").val().trim());
                                          const pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";
										  const data = new FormData();
										
										        const jsonData = {
													transactionId:$("#transactionId").val().trim(),
										            aadhaarNumber: $("#aadharNumber").val().trim(),
										            firstName: firstName,
										            lastName: lastName,
										            dateOfBirth: $("#dateOfBirth").val().trim(),
										            gender: $("#gender").val(),
										            relationName: relationName,
										            idMark: idMark,
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
										            natureOfJob: natureOfJob,
										            wcEsicNo: $("#wc").val(),
													llNo:$("#ll").val(),
										            hazardousArea: $("#hazardousArea").val(),
										            accessArea: $("#accessArea").val(),
										            uanNumber: $("#uanNumber").val().trim(),
										            healthCheckDate: $("#healthCheckDate").val().trim(),
										            pfNumber:$("#pfNumber").val().trim(),
			                                        esicNumber:$("#esicNumber").val().trim(),
										            bloodGroup: $("#bloodGroup").val(),
										            accommodation: $("#accommodation").val(),
										            academic: $("#academic").val(),
										            technical: $("#technical").val(),
										            ifscCode: $("#ifscCode").val().trim(),
										            accountNumber: $("#accountNumber").val().trim(),
										            emergencyName: emergencyName,
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
													address:address,
													doj:$("#doj").val(),
													pfApplicable:pfApplicable,
                                                    policeVerificationDate: $("#policeVerificationDate").val().trim(),
			
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
														sessionStorage.setItem("successMessage", "Gatepass drafted successfully!");
										                loadCommonList('/contractworkmen/list', 'On-Boarding List');
														
										            } else {
										                console.error("Error saving data:", xhr.status, xhr.responseText);
														sessionStorage.setItem("errorMessage", "Failed to draft Gatepass!");
														loadCommonList('/contractworkmen/list', 'On-Boarding List');
										            }
										        };

										        xhr.onerror = function () {
										            console.error("Request failed");
													sessionStorage.setItem("errorMessage", "Failed to draft Gatepass!");
													loadCommonList('/contractworkmen/list', 'On-Boarding List');
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
												var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

												   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "draft") {
												       alert("Edit is only allowed when Gate Pass Type is 'Create' and Status is 'Draft'.");
												       return;
												   }
											    var xhr = new XMLHttpRequest();
											    xhr.onreadystatechange = function() {
											        if (xhr.readyState == 4 && xhr.status == 200) {
											            document.getElementById("mainContent").innerHTML = xhr.responseText;
														setDateRange();
											        }
											    };
											    xhr.open("GET", "/CWFM/contractworkmen/getDraftDetails/" + transactionId, true);
											    xhr.send();
											}
											function searchRenew() {
											var principalEmployerId = $('#principalEmployerId').val();

																	var deptId=$("#deptId").val();
											$.ajax({
											    url: '/CWFM/contractworkmen/renewList',
											    type: 'POST',
											    data: {
											        principalEmployerId: principalEmployerId,
																			deptId:deptId
											    },
											    success: function(response) {
											        var tableBody = $('#workmenTable tbody');
													  if ($.fn.DataTable.isDataTable('#workmenTable')) {
														$('#workmenTable').DataTable().destroy();
													}
											        tableBody.empty();
											        if (Array.isArray(response) &&response.length > 0) {
											            $.each(response, function(index, wo) {
											                var row = '<tr  >' +
																							'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
																							'<td  >' + wo.transactionId + '</td>' +
																							 '<td  >' + wo.gatePassId + '</td>' +
											                          '<td  >' + wo.firstName + ' ' + wo.lastName +'</td>' +
																							 
																							  '<td  >' + wo.aadhaarNumber + '</td>' +	
																							  '<td  >' + wo.contractorName + '</td>' +	
																							
																							  '<td  >' +wo.unitName + '</td>' +	
																							  '<td  >' + wo.gatePassType + '</td>' +
																							  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																							  '<td  >' + wo.status + '</td>' +				                             
											                          '</tr>';
											                tableBody.append(row);
											            });
											        } 
													// ✅ Always init after rows are drawn
														initWorkmenTable("workmenTable");
											    },
											    error: function(xhr, status, error) {
											        console.error("Error fetching data:", error);
											    }
											});
																}
																
function redirectToWorkmenRenewEdit() {
	var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	if (selectedCheckboxes.length !== 1) {
			alert("Please select exactly one row to view.");
			return;
	}
																 
	var selectedRow = selectedCheckboxes[0].closest('tr');
	var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

	   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
	       alert("Edit is only allowed when Gate Pass Type is 'Create' and Status is 'Approved'.");
	       return;
	   }
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("mainContent").innerHTML = xhr.responseText;
	}
	};
	xhr.open("GET", "/CWFM/contractworkmen/renew/" + transactionId, true);
	xhr.send();
}
function renewGatePass(userId) {
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
 const pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";
    if (basicValid && employmentValid && otherValid && wagesValid && documentValid) {
        const data = new FormData();
        const jsonData = {
			transactionId:$("#transactionId").val().trim(),
			gatePassId:$("#gatePassId").val().trim(),
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
			llNo:$("#ll").val(),
            hazardousArea: $("#hazardousArea").val(),
            accessArea: $("#accessArea").val(),
            uanNumber: $("#uanNumber").val().trim(),
            healthCheckDate: $("#healthCheckDate").val().trim(),
            pfNumber:$("#pfNumber").val(),
			esicNumber:$("#esicNumber").val(),
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
			pfApplicable:pfApplicable,
            policeVerificationDate: $("#policeVerificationDate").val().trim(),
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
        xhr.open("POST", "/CWFM/contractworkmen/renewGatePass", true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
				sessionStorage.setItem("successMessage", "Gatepass renew request raised successfully!");
                loadCommonList('/contractworkmen/renewFilter', 'Renew List');
				
            } else {
                console.error("Error saving data:", xhr.status, xhr.responseText);
				sessionStorage.setItem("errorMessage", "Failed to raise Gatepass renew request!");
            }
        };

        xhr.onerror = function () {
            console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to raise Gatepass renew request!");
        };

        // Send the FormData object
        xhr.send(data);
    } else {
        console.error("Validation failed for one or more fields.");
    }
}

function approveRejectRenew(status,gatePassType){
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
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		   sessionStorage.setItem("successMessage", "Gatepass renew request approved/rejected successfully!");
         loadCommonList('/contractworkmen/renewFilter', 'Renew List');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
		   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass renew request!");
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
	   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass renew request!");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}else{
		//error 
	}
	}//eofunc
	

		function redirectToWorkmenRenewView() {
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
	    xhr.open("GET", "/CWFM/contractworkmen/renewview/" + transactionId, true);
	    xhr.send();
	}

	
	

	function generateOtp() {
	    const aadhaarNumber = document.getElementById("aadharNumber").value;

	    // Simple validation
	    if (!aadhaarNumber || aadhaarNumber.length !== 12 || !/^\d{12}$/.test(aadhaarNumber)) {
	        document.getElementById("otpError").innerText = "Please enter a valid 12-digit Aadhaar number.";
	        document.getElementById("otpMessage").innerText = "";
	        return;
	    }

	    var xhr = new XMLHttpRequest();
	    var url = "/CWFM/contractworkmen/generateOtp"; // Your mapped Spring Controller URL

	    xhr.open("POST", url, true);
	    xhr.setRequestHeader("Content-Type", "application/json");

	    xhr.onload = function () {
	        var response;
	        try {
	            response = JSON.parse(xhr.responseText);
	        } catch (e) {
	            document.getElementById("otpError").innerText = "Invalid response from server.";
	            document.getElementById("otpMessage").innerText = "";
	            return;
	        }

	        if (xhr.status === 200 || xhr.status === 422) {
	            if (response.success) {
	                document.getElementById("otpMessage").innerText = response.message;
	                document.getElementById("otpError").innerText = "";
	            } else {
	                document.getElementById("otpError").innerText = response.message +" "+response.status;
	                document.getElementById("otpMessage").innerText = "";
	            }
	        } else {
	            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
	            document.getElementById("otpMessage").innerText = "";
	        }
	    };

	    xhr.onerror = function () {
	        document.getElementById("otpError").innerText = "Request failed.";
	        document.getElementById("otpMessage").innerText = "";
	    };

	    var data = JSON.stringify({ aadhaarNumber: aadhaarNumber });
	    xhr.send(data);
	}
	
	function verifyOtp() {
	    const otp = document.getElementById("otp").value;

	    if (!otp || otp.length < 6 || !/^\d+$/.test(otp)) {
	        document.getElementById("otpError").innerText = "Please enter a valid numeric OTP.";
	        document.getElementById("otpMessage").innerText = "";
	        return;
	    }

	    const data = {
	        otp: otp,
	        clientId: "aadhaar_v2_vtzQlmkayhLKnPYgkEmy"
	    };

	    const xhr = new XMLHttpRequest();
	    xhr.open("POST", "/CWFM/contractworkmen/verifyOtp", true);
	    xhr.setRequestHeader("Content-Type", "application/json");

	    xhr.onload = function () {
	        let response;
	        try {
	            response = JSON.parse(xhr.responseText);
	        } catch (e) {
	            document.getElementById("otpError").innerText = "Invalid server response.";
	            return;
	        }

	        if (xhr.status === 200 && response.success) {
	            document.getElementById("otpMessage").innerText = "OTP Verified Successfully.";
	            document.getElementById("otpError").innerText = "";

	            // Populate form fields
	            document.getElementById("firstName").value = response.fullName?.split(" ")[0] || "";
	            document.getElementById("lastName").value = response.fullName?.split(" ").slice(1).join(" ") || "";
	            document.getElementById("dateOfBirth").value = response.dob || "";
	            document.getElementById("gender").value = response.gender || "";
	            document.getElementById("address").value = response.address || "";
	        } else {
	            document.getElementById("otpError").innerText = response.message || "OTP verification failed.";
	        }
	    };

	    xhr.onerror = function () {
	        document.getElementById("otpError").innerText = "Request failed.";
	    };

	    xhr.send(JSON.stringify(data));
	}

function formatToTwoDecimalPlaces(input) {
  let value = parseFloat(input.value);
  if (isNaN(value)) {
    input.value = '0.00';
  } else {
    input.value = value.toFixed(2);
  }
}
function redirectToWorkmenQuickAdd() {
console.log("redirectToWorkmenQuickAdd called");
    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
			setDateRange();
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/quickOnboardingCreation", true);
    xhr.send();
}
function redirectToWorkmenAdd(){
	// Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
				setDateRange();
	        }
	    };
	    xhr.open("GET", "/CWFM/contractworkmen/addQuickOB", true);
	    xhr.send();
}
function setDateRange() {
	const today = new Date();

	// Person must be between 18 and 70 years old
	const maxDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate()); // youngest allowed DOB
	const minDate = new Date(today.getFullYear() - 70, today.getMonth(), today.getDate()); // oldest allowed DOB

	$(".datetimepickerformat").datepicker({
	    dateFormat: 'yy-mm-dd',
	    changeMonth: true,
	    changeYear: true,
	    yearRange: `${minDate.getFullYear()}:${maxDate.getFullYear()}`, // dynamic range
	    minDate: minDate,
	    maxDate: maxDate,
	    defaultDate: maxDate // 👈 ensures calendar opens at the 18-year-old boundary
	});

    $('.datetimepickerformat1').datepicker({//date of joiing
        dateFormat: 'yy-mm-dd', // Set the date format
        changeMonth: true,      // Allow changing month via dropdown
        changeYear: true,       // Allow changing year via dropdown
        yearRange: "0:+100", 
        minDate: 0 ,
		maxDate: +15             // Prevent selecting future dates
    });
    const sixMonthsAgo = new Date();
    sixMonthsAgo.setMonth(today.getMonth() - 6);

    $(".datetimepickerformat2").datepicker({//health check date
        dateFormat: 'yy-mm-dd',
        changeMonth: true,
        changeYear: true,
        minDate: sixMonthsAgo,
        maxDate: today,
        yearRange: `${sixMonthsAgo.getFullYear()}:${today.getFullYear()}`
    });
   
const oneYearAgo = new Date();
oneYearAgo.setFullYear(today.getFullYear() - 1);
   $(".datetimepickerformat3").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    minDate: oneYearAgo,
    maxDate: today,
    yearRange: `${today.getFullYear() - 1}:${today.getFullYear()}`
});
}

function validatePfForm11Requirement() {
    const pfApplicable = document.getElementById("pfApplicable").checked;
    const form11ErrorContainer = document.getElementById("form11-error-message");
    let form11Present = false;

    if (!pfApplicable) {
        const additionalFields = document.querySelectorAll('.document-field');

        additionalFields.forEach((field) => {
            const docType = field.querySelector('select[name="documentType"]')?.value;
            const file = field.querySelector('input[type="file"]')?.files[0];

            if (docType === "Form11" && file) {
                form11Present = true;
            }
        });

        if (!form11Present) {
            form11ErrorContainer.textContent = "Form11 document is mandatory since PF is not applicable.";
            form11ErrorContainer.style.display = "block";
            return false;
        }
    }

    // Clear error if condition passes or PF is applicable
    form11ErrorContainer.textContent = "";
    form11ErrorContainer.style.display = "none";
    return true;
}

let streams;

  function previewImages(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        const img = document.getElementById("preview");
        img.src = e.target.result;
        img.style.display = "block";
      };
      reader.readAsDataURL(file);

      // ✅ Show file name
      document.getElementById("fileNameDisplay").textContent = file.name;
    }
  }

  function openCamera() {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then(s => {
        streams = s;
        const video = document.getElementById("webcam");
        video.srcObject = streams;
        video.style.display = "block";
        document.getElementById("cameraButtons").style.display = "flex";
        //document.getElementById("cameraButtons").style.gap = "5px";
      })
      .catch(err => {
        alert("Camera access denied.");
        console.error(err);
      });
  }

  function captureImage() {
    const video = document.getElementById("webcam");
    const canvas = document.getElementById("canvas");
    const previewDiv = document.getElementById("preview");

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;
    canvas.getContext("2d").drawImage(video, 0, 0, canvas.width, canvas.height);

    canvas.toBlob(function(blob) {
        // Create a File object with a dummy name
        const file = new File([blob], "captured_profile.png", { type: "image/png" });

        // Set this file into a hidden input (or use FormData later)
        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(file);
        document.getElementById("imageFile").files = dataTransfer.files;

        // Preview image
        const imageUrl = URL.createObjectURL(blob);
        previewDiv.innerHTML = `<img src="${imageUrl}" alt="Captured Image" style="max-width: 100%; max-height: 100%;">`;

        // Show filename under preview
        document.getElementById("imageFileName").textContent = file.name;

        closeCamera();
    }, "image/png");
}



  function closeCamera() {
    if (streams) {
      streams.getTracks().forEach(track => track.stop());
    }
    document.getElementById("webcam").style.display = "none";
    document.getElementById("cameraButtons").style.display = "none";
  }
  
  function goBackToonboardingList() {
    	 loadCommonList('/contractworkmen/list', 'On-Boarding List');
    }
    function goBackToquickonboardingList() {
    	 loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
    }

    
	function initWorkmenTable(tablename) {
	    const selector = '#' + tablename;

	    if ($.fn.DataTable.isDataTable(selector)) {
	        $(selector).DataTable().destroy();
	    }

	    $(selector).DataTable({
	        paging: true,
	        searching: true,
	        ordering: true
	    });
	}

   function searchGatePassStatus() {
    const transactionId = $('#transactionId').val().trim();
    const gatepassId = $('#gatePassId').val().trim();

   if (!transactionId && !gatepassId) {
    alert("Please enter Transaction ID or Gate Pass ID");
    return;
   }

    $.ajax({
        url: '/CWFM/entrypassstatus/statusList',
        type: 'POST',
        data: {
            transactionId: transactionId,
            gatepassId: gatepassId
        },
        success: function (response) {
            const tableBody = $('#workmenTable tbody');
            tableBody.empty();

            if (response.length > 0) {
                response.forEach(function (wo) {
                    const row = `<tr>
                        <td><input type="checkbox" name="selectedWOs" value="${wo.transactionId || ''}"></td>
                        <td>${wo.transactionId || ''}</td>
                        <td>${wo.firstName || ''} ${wo.lastName || ''}</td>
                        <td>${wo.lastName || ''}</td>
                        <td>${wo.aadhaarNumber || ''}</td>
                        <td>${wo.approvedBy || ''}</td>
                        <td>${wo.pendingWith || ''}</td>
                    </tr>`;
                    tableBody.append(row);
                });
            } else {
                tableBody.append('<tr><td colspan="8">No data found</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching data:", error);
        }
    });
}

 function searchGatePassHistory() {
    const aadharNumber = $('#aadharNumber').val().trim();

    $.ajax({
        url: '/CWFM/entrypassstatus/history',
        type: 'POST',
        data: {
            aadharNumber: aadharNumber
        },
        success: function (response) {
            const tableBody = $('#workmenTable tbody');
            tableBody.empty();

            if (response.length > 0) {
                response.forEach(function (wo) {
                    const row = `<tr>
                        <td> <input type="checkbox" name="selectedWOs" value="${wo.transactionId}"></td>
                        <td>${wo.transactionId || ''}</td>
                         <td>${wo.gatePassId || ''}</td>
                        <td>${wo.firstName || ''} ${wo.lastName || ''}</td>
                        <td>${wo.lastName || ''}</td>
                         <td>${wo.gatePassType || ''}</td>
                         <td>${wo.status || ''}</td>
                        <td>${wo.unitName || ''}</td>
                       
                    </tr>`;
                    tableBody.append(row);
                });
            } else {
                tableBody.append('<tr><td colspan="8">No data found</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching data:", error);
        }
    });
}  

function loadDepartments(unitId) {
    if (!unitId) {
        $("#deptId").html("<option value=''>Select Department</option>");
        return;
    }

    $.ajax({
        url: "/CWFM/contractworkmen/getAllDepartments",
        type: "GET",
        data: { unitId: unitId },
        success: function (departments) {
            var deptSelect = $("#deptId");
            deptSelect.empty();
            deptSelect.append("<option value=''>Select Department</option>");
            $.each(departments, function (i, dept) {
                deptSelect.append(
                    "<option value='" + dept.departmentId + "'>" + dept.department + "</option>"
                );
            });
        },
        error: function () {
            alert("Error loading departments!");
        }
    });
}
function verifyOtpDemo() {
	    const aadharNo = document.getElementById("aadharNumber").value;


	    
	    
	        if (aadharNo === '992158496671') {
	           
	            document.getElementById("firstName").value =  "Shashikant";
	            document.getElementById("lastName").value = "Shukla";
	            document.getElementById("dateOfBirth").value = "2002-07-07";
	            document.getElementById("gender").value = "11";
	            document.getElementById("address").value =  "Anakapalli rebaka";
				document.getElementById("mobileNumber").value =  "9876543210";
	        } else if(aadharNo === '550188039490'){
								document.getElementById("firstName").value =  "Nivetha";
					            document.getElementById("lastName").value = "Mohansingh";
					            document.getElementById("dateOfBirth").value = "1991-07-07";
					            document.getElementById("gender").value = "12";
					            document.getElementById("address").value =  "SVA anada nilaya,ist cross immadihalli road, nagondanahalli,Bengaluru-560066";
								document.getElementById("mobileNumber").value =  "9876543111";
	          }							else if(aadharNo === '880188039490'){
															document.getElementById("firstName").value =  "Hemalatha";
												            document.getElementById("lastName").value = "Karanam";
												            document.getElementById("dateOfBirth").value = "1991-08-07";
												            document.getElementById("gender").value = "12";
												            document.getElementById("address").value =  "Ardente Office One, nagondanahalli,Bengaluru-560066";
															document.getElementById("mobileNumber").value =  "8876543111";
								          }
	   

	  
	}
	
	function generateToken(){
		const aadhaarNumber = document.getElementById("aadharNumber").value;
		const transactionId= document.getElementById("transactionId").value;
		let valid=false;
			    // Simple validation
			    if (!aadhaarNumber || aadhaarNumber.length !== 12 || !/^\d{12}$/.test(aadhaarNumber)) {
			        document.getElementById("otpError").innerText = "Please enter a valid 12-digit Aadhaar number.";
			        document.getElementById("otpMessage").innerText = "";
			        return;
			    }else{
					valid = aadharDuplicateDBCheck(aadhaarNumber,transactionId);
				}				
if(valid){
			    var xhr = new XMLHttpRequest();
			    var url = "/CWFM/contractworkmen/generateToken"; // Your mapped Spring Controller URL

			    xhr.open("POST", url, true);
			    xhr.setRequestHeader("Content-Type", "application/json");

			    xhr.onload = function () {
			        var response;
			        try {
			            response = JSON.parse(xhr.responseText);
			        } catch (e) {
			            document.getElementById("otpError").innerText = "Invalid response from server.";
			            document.getElementById("otpMessage").innerText = "";
			            return;
			        }

			        if (xhr.status === 200 || xhr.status === 422) {
						if (response.url) {
							    openDigiModal(response.token);

						}

			           
						 else {
			                document.getElementById("otpError").innerText = response.message +" "+response.status;
			                document.getElementById("otpMessage").innerText = "";
			            }
			        } else {
			            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
			            document.getElementById("otpMessage").innerText = "";
			        }
			    };

			    xhr.onerror = function () {
			        document.getElementById("otpError").innerText = "Request failed.";
			        document.getElementById("otpMessage").innerText = "";
			    };

			    var data = JSON.stringify({ aadhaarNumber: aadhaarNumber });
			    xhr.send(data);
				}
	}
	
	function openDigiModal(token) {
	    const modal = document.getElementById("digiModal");
	    const container = document.getElementById("digilocker-sdk-button");

	    if (modal) {
	        modal.style.display = "block";
	    }

	    // 🔑 Clear old buttons before SDK re-renders
	    if (container) {
	        container.innerHTML = "";
	        container.style.display = "block"; // reset if hidden on success
	    }

	    // Initialize Digi SDK inside modal with token
	    window.DigiboostSdk({
	        gateway: "production", // sandbox or production
	        token: token, // directly from response
	        onSuccess: handleSuccess,
	        onFailure: handleFailure,
	        selector: "#digilocker-sdk-button",
	        style: {
	            width: "100%",
	            margin: "0px"
	        }
	    });
	}

	function closeModal() {
	    const modal = document.getElementById("digiModal");
	    const container = document.getElementById("digilocker-sdk-button");

	    if (modal) {
	        modal.style.display = "none";
	    }

	    // 🔑 Reset container on close too
	    if (container) {
	        container.innerHTML = "";
	        container.style.display = "block";
	    }
	}


	function handleSuccess(data) {
			//Do something here for success case
			console.log("Verification successful:", data);
	        document.getElementById("status").innerHTML = 
	            '<div class="success"> Verification completed successfully!</div>';
	            document.getElementById("digilocker-sdk-button").style.display = "none";
	        
	        //window.location.href = "/CWFM/contractworkmen/digiClientId?id=" + encodeURIComponent(data.client_id);
			
			var xhr = new XMLHttpRequest();
					    var url = "/CWFM/contractworkmen/digiClientId?id=" + encodeURIComponent(data.client_id);

					    xhr.open("GET", url, true);
					    xhr.setRequestHeader("Content-Type", "application/json");

					    xhr.onload = function () {
					        var response;
					        try {
					            response = JSON.parse(xhr.responseText);
					        } catch (e) {
					            document.getElementById("otpError").innerText = "Invalid response from server.";
					            document.getElementById("otpMessage").innerText = "";
					            return;
					        }

					        if (xhr.status === 200 || xhr.status === 422) {
								if (response.data) {
									  console.log(response.data.aadhaar_xml_data);
									  closeModal();
									  let firstname = response.data.aadhaar_xml_data.full_name ?.split(" ")[0] || "";
									  let lastname = response.data.aadhaar_xml_data.full_name?.split(" ").slice(1).join(" ") || "";
									  let dob = response.data.aadhaar_xml_data.dob|| "";
									 // let fathername = response.data.aadhaar_xml_data.care_of|| "";
									 let careOf = response.data.aadhaar_xml_data.care_of || "";
									 let match = careOf.match(/^\s*([A-Za-z]\/O:)?\s*(.*)$/i);
									 let relation = "";
									 let fathername = "";
									 if (match) {
									     relation = (match[1] || "").trim();  // e.g. "S/O:" or ""
									     fathername = (match[2] || "").trim(); // e.g. "Sankar Singh"
									 }
									  let g = response.data.aadhaar_xml_data.gender|| "";
									  let gender;
									  if(g === "F"){
										gender = "12";
									  }else{
										gender = "11";
									  }
									  let address= response.data.aadhaar_xml_data.full_address|| ""+" "+response.data.aadhaar_xml_data.zip|| "";
									  document.getElementById("firstName").value = firstname;
									   document.getElementById("lastName").value =lastname;
									  document.getElementById("dateOfBirth").value =dob;
									  	document.getElementById("gender").value = gender
									  document.getElementById("address").value =  address;
									  document.getElementById("relationName").value =  fathername;
									  
									  document.getElementById("aadharNumber").readOnly = true;
									  document.getElementById("firstName").readOnly = true;
									         
											 document.getElementById("dateOfBirth").disabled = true;

									         document.getElementById("gender").disabled = true;   // if it's a <select>
									         document.getElementById("address").readOnly = true;
									        
											 document.querySelector('button[onclick="generateToken()"]').disabled = true;
								}

					           
								 else {
					                document.getElementById("otpError").innerText = response.message +" "+response.status;
					                document.getElementById("otpMessage").innerText = "";
					            }
					        } else {
					            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
					            document.getElementById("otpMessage").innerText = "";
					        }
					    };

					    xhr.onerror = function () {
					        document.getElementById("otpError").innerText = "Request failed.";
					        document.getElementById("otpMessage").innerText = "";
					    };

					   
					    xhr.send();	
			
		}

		function handleFailure() {
			//Do something here for failure case
			console.log("Verification failed:", error);
	        document.getElementById("status").innerHTML = 
	            '<div class="error"> Verification failed or was cancelled</div>';
		}
		

		
		function aadharDuplicateDBCheck(aadharNumber, transactionId) {
		    let result = false;

		    $.ajax({
		        url: "/CWFM/contractworkmen/checkAadharExists",
		        type: "GET",
		        data: { aadharNumber: aadharNumber, transactionId: transactionId },
		        async: false, 
		        success: function (response) {
		            if (response.exists) {
		                $("#error-aadhar").text("Aadhar number already exists").show();
		                result = false;
		            } else {
		                $("#error-aadhar").hide();
		                result = true;
		            }
		        },
		        error: function () {
		            $("#error-aadhar").text("Error checking Aadhar").show();
		            result = false;
		        }
		    });

		    return result;  // ✅ return after ajax finishes
		}
