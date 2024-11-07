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
			                    var row = '<tr style="border: 1px solid black;">' +
										'<td style="border: 1px solid black;"><input type="checkbox" name="selectedContractorIds" value="' + contr.contractorId + '"></td>'+
			                              '<td style="border: 1px solid black;">' + contr.contractorCode + '</td>' +
			                              '<td style="border: 1px solid black;">' + contr.contractorName + '</td>' +
										  '<td style="border: 1px solid black;">' + contr.contractorAddress + '</td>' +				                             
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
            var selectedRows = document.querySelectorAll('input[name="selectedContractors"]:checked');
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