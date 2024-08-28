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
 function searchWithPEContractorInWorkmenList() {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url:  "/CWFM/contractworkmen/contractWorkmenList",
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
 
  function workmenListExportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedCWs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "Person Number,First Name,Last Name,Gender,Date of Birth,Aadhar Number,Contractor Name,Vendor Code,Unit Name\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7), td:nth-child(8), td:nth-child(9), td:nth-child(10)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractWorkmenList.csv");
            document.body.appendChild(link);
            link.click();
        }     
        
        function toggleSelectAllContractWorkmen() {
            var selectAllCheckbox = document.getElementById('selectAllContractWorkmenCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedCWs"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
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