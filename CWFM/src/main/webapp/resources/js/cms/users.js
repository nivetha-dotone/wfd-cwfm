
function redirectToUserAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/user/add", true);
    xhr.send();
}


function redirectToUserEdit() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to edit.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedUsers"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/user/edit/" + unitId, true);
    xhr.send();
}

function redirectToUserView(principalEmployerId) {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedUsers"]').value;
 /*var principalEmployerId = document.getElementById("principalEmployerId").value;*/
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/user/view/" + unitId+ "?loginId=" + principalEmployerId, true);
    xhr.send();
}
 function UserExportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedUsers"]:checked');
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
            link.setAttribute("download", "UsersList.csv");
            document.body.appendChild(link);
            link.click();
        }
        
        function toggleSelectAllUsers() {
            var selectAllCheckbox = document.getElementById('selectAllCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedUsers"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }