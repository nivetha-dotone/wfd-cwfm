 
 
 function loadWOList(contextPath) {
      //  var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/workorders/list';
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
 function toggleSelectAllWOS() {
            var selectAllCheckbox = document.getElementById('selectAllWOCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedWOs"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }
        
 
function redirectToWOView() {
	var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    
    var unitId = selectedRow.querySelector('[name="selectedWOs"]').value;
    
    // Construct the URL with unit ID, principal employer ID, and contractor ID as query parameters
    var url = "/CWFM/workorders/view/" + unitId + "?principalEmployerId=" + principalEmployerId + "&contractorId=" + contractorId;
    
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}
function goBackToWOList(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
    // Construct the URL with principal employer ID and contractor ID as query parameters
    var url = contextPath + "/workorders/list?principalEmployerId=" + principalEmployerId + "&contractorId=" + contractorId;
    
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading work order list page:", error);
            // Handle error if needed
        }
    });
}

function searchWithPEContractorInWO(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url: contextPath + "/workorders/list",
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
 
   function woListExportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "Workorder Number,Job,Workorder Type,Area Name,Valid From,Valid To,Contractor Name,Vendor Code,Unit Name,Status\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7), td:nth-child(8), td:nth-child(9), td:nth-child(10), td:nth-child(11)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "WorkOrderList.csv");
            document.body.appendChild(link);
            link.click();
        }
        
 