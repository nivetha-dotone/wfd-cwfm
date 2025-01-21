<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
      <!--  <script src="resources/js/commonjs.js"></script> -->
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
       <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
     <script src="resources/js/cms/bill.js"></script>
    <script src="resources/js/jquery.min.js"></script>
    <script>
    var contextPath = '<%= request.getContextPath() %>';
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
function redirectToPEAdd() {
    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/add", true);
    xhr.send();
}
function loadCommonList(path,heading) {
	 updateHeading(heading);
	    var url = contextPath + path;
    // Construct the URL using the contextPath variable
  //  var url = contextPath + path;
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
function loadQobAdd(path,heading,userId) {
	//alert("loadQobAdd"+userId);
	 updateHeading(heading);
	    var url = contextPath + path + "?userId=" + encodeURIComponent(userId);
   // Construct the URL using the contextPath variable
 //  var url = contextPath + path;
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
function checkSessionStatus() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (!response.sessionActive) {
                // Session has expired, redirect to login page
                window.location.href = "/CWFM";
            }
        }
    };
    xhr.open("GET", "/checkSessionStatus", true);
    xhr.send();
}
 function showTabNew(tabId) {
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
 // Show the Approve Reject button only on the Documents tab
    var approveButton = document.getElementById('approveButton');
    var rejectButton = document.getElementById('rejectButton');
    var actionButton = document.getElementById('actionButton');
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
    	approveButton.style.display = 'inline-block'; // Show Save button
    	rejectButton.style.display = 'inline-block'; // Show Save button
    	actionButton.style.display = 'inline-block'; // Show Save button
    } else {
    	approveButton.style.display = 'none'; // Hide Save button
    	rejectButton.style.display = 'none'; // Hide Save button
    	actionButton.style.display = 'none'; // Hide Save button
    }
} 
function validateCurrentTab() {
    let isValid = true;
    const activeTabId = document.querySelector('.tab-content.active').id;
    if (activeTabId === 'tab1') {
        isValid = validateBasicData();
    } 
    else if(activeTabId === 'tab2'){
    	isValid = validateEmploymentInformation();
    }else if(activeTabId === 'tab3'){
    	isValid = validateOtherInformation();
    }else if(activeTabId === 'tab4'){
    	isValid = validateWages();
    }else{
    	isValid = true;
    }

    return isValid;
}

// Function to show the selected tab
function showTab(tabId) {
    // Check if current tab fields are valid before switching tabs
    if (!validateCurrentTab()) {
     return; // Prevent tab switch if validation fails
    }

    // Hide all tab contents
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });

    // Remove active class from all tabs
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });

    // Show the selected tab content and add active class to the clicked tab
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    
 // Show the Save button only on the Documents tab
    var saveButton = document.getElementById('saveButton');
 	
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
        saveButton.style.display = 'inline-block'; // Show Save button
    } else {
        saveButton.style.display = 'none'; // Hide Save button
    }
}

function showTabOther(tabId) {
    // Check if current tab fields are valid before switching tabs
    

    // Hide all tab contents
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });

    // Remove active class from all tabs
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });

    // Show the selected tab content and add active class to the clicked tab
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    
 // Show the Save button only on the Documents tab
    var saveButton = document.getElementById('saveButton');
 	
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
        saveButton.style.display = 'inline-block'; // Show Save button
    } else {
        saveButton.style.display = 'none'; // Hide Save button
    }
}
function redirectToOrgMapAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/org-level-mapping/new", true);
    xhr.send();
}

function moveOptions(sourceId, targetId) {
    var source = document.getElementById(sourceId);
    var target = document.getElementById(targetId);
    var selectedOptions = Array.from(source.selectedOptions);
    
    selectedOptions.forEach(function(option) {
        source.removeChild(option);
        target.appendChild(option);
    });
}

function moveRight(orgLevelDefId) {
    moveOptions('available-' + orgLevelDefId, 'selected-' + orgLevelDefId);
}

function moveLeft(orgLevelDefId) {
    moveOptions('selected-' + orgLevelDefId, 'available-' + orgLevelDefId);
}
function saveOrgLevelMappings() {
    var formData = [];

    // Loop through all select elements with class 'selected-options'
    document.querySelectorAll('.selected-options').forEach(function(selectElement) {
        var orgLevelDefId = selectElement.getAttribute('data-org-level-id');

        // Collect selected options
        var selectedOptions = Array.from(selectElement.options)
            .filter(option => option.selected)
            .map(option => option.value);

        // Ensure we only add valid data
        if (selectedOptions.length > 0) {
            formData.push({
                orgLevelDefId: orgLevelDefId,
                selectedValues: selectedOptions
            });
        }
    });

    // Send data to the server
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/org-level-mapping/saveOrgLevelEntries", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Handle successful response
            console.log('Data saved successfully');
        } else if (xhr.readyState == 4) {
            // Handle error
            console.error('Error:', xhr.status, xhr.responseText);
        }
    };

    xhr.send(JSON.stringify(formData));
}



function fetchGmData() {
    const gmTypeId = document.getElementById('gmTypeId').value;
    if (!gmTypeId) {
        alert("Please select a valid General Type.");
        return;
    }
    document.getElementById("mainContent").innerHTML = "<p>Loading data...</p>";
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/generalController/getGmData/" + gmTypeId, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        } else {
            alert("Failed to load organization level data. Please try again.");
            document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please try again.</p>";
        }
    };
    xhr.onerror = function () {
        alert("An error occurred while making the request.");
        document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please check your connection and try again.</p>";
    };
    xhr.send();
}

function deleteGMManager(gmId,gmTypeId) {
    if (confirm("Are you sure you want to delete this entry?")) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/generalController/deleteGmData", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        
        var data = "gmId=" + encodeURIComponent(gmId) + "&gmTypeId=" + encodeURIComponent(gmTypeId);
        
        xhr.onload = function() {
            if (xhr.status == 200) {
            	document.getElementById("mainContent").innerHTML = xhr.responseText;
            } else {
                alert("Failed to delete entry. Please try again.");
            }
        };
        
        xhr.send(data);
    }
}
function saveGMMaster() {
    const gmTypeId = document.getElementById('gmTypeId').value;
    const masterName = document.getElementById('masterName').value;
    const masterValue = document.getElementById('masterValue').value;

    // Validate fields
    if (!gmTypeId) {
        alert('Please select GMType.');
        return;
    }

    if (!masterName || !masterValue) {
        alert('Please enter Name and Value.');
        return;
    }

    const data = JSON.stringify({
        gmTypeId: gmTypeId,
        gmName: masterName,
        gmDescription: masterValue,
    });

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/generalController/saveGeneralMaster", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        } else if (xhr.status === 409) { // Conflict status for duplicate data
            alert("Duplicate data not allowed");
        } else {
            alert("Error: " + xhr.statusText);
        }
    };

    xhr.onerror = function () {
        alert("An error occurred while saving the General Master.");
    };

    xhr.send(data);
}



/* function saveRoleRights() {
    const form = document.getElementById('roleRightsForm'); // Now selects a <form>
    const formData = new FormData(form);

    fetch('/CWFM/roleRights/saveRoleRights', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            // Load the specific list page with default styles
            loadCommonList('/roleRights/roleRightsList', 'Role Rights');
        } else {
            console.error('Failed to save role rights.');
        }
    })
    .catch(error => {
        console.error('Error saving role rights:', error);
    });
} */

/* function saveRoleRights() {
    var form = document.getElementById("roleRightsForm");
    var formData = new FormData(form);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/roleRights/saveRoleRights", true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
            	loadCommonList('/roleRights/roleRightsList', 'Role Rights')
               // window.location.href = "/CWFM/roleRights/roleRightsList";
            } else {
                alert("Error: " + xhr.status + " - " + xhr.statusText);
            }
        }
    };

    xhr.send(formData);
} */



/* let rowCount = 1; // Start with 1 because the default row is already present.

function addRow() {
    const table = document.getElementById("roleRightsTable");
    const row = table.insertRow(-1); // Add new row at the end
    const rowIndex = table.rows.length - 2; // Adjust for header and 0-based index

    row.innerHTML = `
        <td>
            <select name="roleRightsForm.roleRights[${rowIndex}].roleId" required>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.gmId}">${role.gmName}</option>
                </c:forEach>
            </select>
        </td>
        <td>
            <select name="roleRightsForm.roleRights[${rowIndex}].page.gmId" required>
                <c:forEach var="page" items="${pages}">
                    <option value="${page.gmId}">${page.gmName}</option>
                </c:forEach>
            </select>
        </td>
        <td><input type="checkbox" name="roleRightsForm.roleRights[${rowIndex}].viewRights" value="1" /></td>
        <td><input type="checkbox" name="roleRightsForm.roleRights[${rowIndex}].addRights" value="1" /></td>
        <td><input type="checkbox" name="roleRightsForm.roleRights[${rowIndex}].editRights" value="1" /></td>
        <td><input type="checkbox" name="roleRightsForm.roleRights[${rowIndex}].deleteRights" value="1" /></td>
        <td><input type="checkbox" name="roleRightsForm.roleRights[${rowIndex}].exportRights" value="1" /></td>
        <td><button type="button" onclick="removeRow(this)">Remove</button></td>
    `;
}

function removeRow(button) {
    const row = button.parentElement.parentElement;
    row.parentNode.removeChild(row);
}

// Form validation to ensure roles and pages are selected
function validateRRForm() {
    const rows = document.querySelectorAll('#roleRightsTable tbody tr');
    for (const row of rows) {
        const roleSelect = row.querySelector("select[name^='roleRights'][name$='.roleId']");
        const pageSelect = row.querySelector("select[name^='roleRights'][name$='.pageId']");
        if (!roleSelect.value || !pageSelect.value) {
            alert("Please select both Role and Page for each entry.");
            return false;
        }
    }
    return true;
} */
function addNewRowFromTemplate() {
    const placeholder = document.getElementById('placeholderRow');
    const newRow = placeholder.cloneNode(true);
    newRow.removeAttribute('id'); // Remove the ID from the cloned row
    const tableBody = document.getElementById('roleRightsTable').querySelector('tbody');
    tableBody.appendChild(newRow);
}
/* var rowIndex = 0; // Initial row index */

function addNewRow() {
    var table = document.getElementById('roleRightsTable').getElementsByTagName('tbody')[0];
    var newRow = table.rows[0].cloneNode(true); // Clone the first row
    var rowIndex = table.rows.length;

    // Update the index for cloned rows and clear the values
    newRow.querySelectorAll('input, select').forEach(function (element) {
        // Update the name index
        element.name = element.name.replace(/\[\d+\]/, '[' + rowIndex + ']');

        // Clear the value for input fields
        if (element.tagName === 'INPUT') {
            if (element.type === 'text' || element.type === 'number' || element.type === 'email') {
                element.value = ''; // Clear text, number, or email inputs
            }
            if (element.type === 'checkbox' || element.type === 'radio') {
                element.checked = false; // Uncheck checkboxes and radio buttons
            }
        }

        // Reset the selection for dropdown fields
        if (element.tagName === 'SELECT') {
            element.selectedIndex = 0; // Set to the first option
        }

        // Clear custom attributes if needed (e.g., data attributes)
        if (element.hasAttribute('data-some-attribute')) {
            element.removeAttribute('data-some-attribute'); // Example of clearing a custom attribute
        }
    });

    table.appendChild(newRow); // Append the cleared row to the table
}

function saveRoleRights() {
    const roleId = document.getElementById('roleId')?.value.trim();
    const pageId = document.getElementById('pageId')?.value.trim();

    if (!roleId) {
        alert('Please select a Role.');
        return;
    }
    if (!pageId) {
        alert('Please select a Page.');
        return;
    }

    // Gather selected permissions
    const checkboxes = document.querySelectorAll('input[name="permissions"]:checked');
    console.log('Selected checkboxes:', checkboxes); // Debugging log

    const permissions = Array.from(checkboxes).map(checkbox => checkbox.value);
    console.log('Collected permissions:', permissions); // Debugging log

    if (permissions.length === 0) {
        alert('Please select at least one permission.');
        return;
    }

    const data = {
        roleId: roleId,
        pageId: pageId,
        permissions: permissions
    };

    console.log('Data to send:', data); // Debugging log

    fetch('/roleRights/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status === 409) {
                document.getElementById('error-message').innerText = 'Duplicate Role-Page combination is not allowed.';
            } else if (response.ok) {
                alert('Role rights saved successfully.');
                location.reload();
            } else {
                throw new Error('Unexpected error occurred.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while saving role rights.');
        });
}


/* function removeRow(button) {
    const row = button.closest('tr');
    row.remove();
} */



// Function to remove a row
function removeRow(button) {
    const row = button.closest('tr');
    const tableBody = document.querySelector('#roleRightsTable tbody');
    if (tableBody.rows.length > 1) {
        row.remove();
    } else {
        alert('At least one row is required.');
    }
}

// Form validation
function validateRRForm() {
    const rows = document.querySelectorAll('#roleRightsTable tbody tr');
    for (let row of rows) {
        const roleSelect = row.querySelector("select[name*='.roleId']");
        const pageSelect = row.querySelector("select[name*='.pageId']");
        if (roleSelect.value === "" || pageSelect.value === "") {
            alert("Please select both Role and Page for each row.");
            return false;
        }
    }
    return true;
}
function redirectToRRAdd() {
	alert(1);
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/roleRights/add", true);
	    xhr.send();
	}
function redirectToUserAdd() {
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/usersController/new", true);
	    xhr.send();
	}
function saveUser() {
    const form = document.getElementById('userFormID'); // Now selects a <form>
    const formData = new FormData(form);

    // Collecting specific input values manually
    formData.append('firstName', form.elements['firstName'].value);
    formData.append('lastName', form.elements['lastName'].value);
    formData.append('email', form.elements['email'].value);
    formData.append('contactNumber', form.elements['contactNumber'].value);
    formData.append('password', form.elements['password'].value);
    formData.append('userAccount', form.elements['userAccount'].value);

    // Assuming roleIds are checkboxes and multiple checked roles exist
    const checkedRoles = [];
    form.querySelectorAll('input[name="roleIds"]:checked').forEach(input => {
        checkedRoles.push(input.value);
    });
    formData.append('roleIds', JSON.stringify(checkedRoles));

    fetch('/CWFM/usersController/saveUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Ensure content type matches your server's expected format
        },
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            alert('User saved successfully!');
            // Load the specific list page with default styles
            loadCommonList('/users/userList', 'Users');
        } else {
            alert(`Failed to save role rights: ${response.statusText}`);
            console.error('Failed to save role rights.');
        }
    })
    .catch(error => {
        console.error('Error saving role rights:', error);
    });
}
function submitGMTYPE() {
    // Create a new FormData object and append the input values
    const data = new FormData();
    const gmTypeName = document.getElementById("gmTypeName").value; // Get the GM Type Name input value
    data.append("gmTypeName", gmTypeName); // Append the gmTypeName to FormData

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/generalController/saveGMType", true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log("Data saved successfully:", xhr.responseText);
            loadCommonList('/generalController/gmType', 'General Type');
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
function submitOrgLevel() {
    const orgLevelRows = document.querySelectorAll('#orgLevelTable tbody tr'); // Select all rows in the table
    let orgLevelsData = [];
    let validData = true; // Track validity of the entire form

    orgLevelRows.forEach((row, index) => {
        const orgLevelDefIdElem = row.querySelector('input[name="orgLevelDefId[]"]');
        const orgLevelNameElem = row.querySelector('input[name="orgLevelName[]"]');
        const shortNameElem = row.querySelector('input[name="shortName[]"]');
        const hierarchyElem = row.querySelector('input[name="hierarchy[]"]');

        // Extract values or set defaults for missing elements
        const orgLevelDefId = orgLevelDefIdElem ? orgLevelDefIdElem.value.trim() : null;
        const orgLevelName = orgLevelNameElem ? orgLevelNameElem.value.trim() : '';
        const shortName = shortNameElem ? shortNameElem.value.trim() : '';
        const hierarchy = hierarchyElem ? hierarchyElem.value.trim() : '';

        console.log(`Processing Row ${index + 1}:`);
        console.log('OrgLevelDefId:', orgLevelDefId || 'New Entry (null)');
        console.log('Name:', orgLevelName || 'Not Found');
        console.log('Short Name:', shortName || 'Not Found');
        console.log('Hierarchy:', hierarchy || 'Not Found');

        // Validate fields for each row
        if (!orgLevelName || !shortName || !hierarchy) {
            console.error(`Row ${index + 1} is missing required fields.`);
            alert(`Please fill in all fields for row ${index + 1}.`);
            validData = false;
            return; // Skip processing this row
        }

        // Push valid data to the array
        orgLevelsData.push({
            orgLevelDefId: orgLevelDefId || null, // Assign null for new rows
            name: orgLevelName,
            shortName: shortName,
            orgHierarchyLevel: parseInt(hierarchy, 10)
        });
    });

    if (!validData || orgLevelsData.length === 0) {
        alert('Please fix errors before submitting.');
        return;
    }

    console.log('Collected Org Levels Data:', orgLevelsData);

    // AJAX request to send data to the server
    $.ajax({
        type: 'POST',
        url: '/CWFM/org-level/save',
        contentType: 'application/json',
        data: JSON.stringify(orgLevelsData),
        success: function (response) {
            console.log('Server Response:', response);
            alert('Org Levels saved successfully!');
            loadCommonList('/org-level/list', 'Org Levels');
        },
        error: function (xhr, status, error) {
            console.error('Error during request:', error);
            alert('Error during request: ' + xhr.responseText);
        }
    });
}
function validateMasterValue() {
    const gmTypeId = document.getElementById("gmTypeId").value.trim();
    const masterValue = document.getElementById("masterValue").value.trim();
    const existingMasterValues = document.getElementById("existingMasterValues").value.split(',');

    if (!gmTypeId || !masterValue) {
        alert("Please fill all the required fields.");
        return false; // Prevent form submission
    }

    if (existingMasterValues.includes(masterValue)) {
        alert("Duplicate Master Value is not allowed for the selected GM Type.");
        return false; // Prevent form submission
    }

    return true; // Allow form submission
}
function deleteSelectedGMTYPE() {
    const selectedIds = Array.from(document.querySelectorAll('input[name="selectedGMTIds"]:checked')).map(cb => parseInt(cb.value, 10));
    if (selectedIds.length === 0) {
        alert("Please select at least one GM Type to delete.");
        return;
    }

   

    // Log the selected IDs to see what is being sent
    console.log("Selected GM Type IDs:", selectedIds);

    $.ajax({
        type: 'POST',
        url: '/CWFM/generalController/deleteGMTypes',
        contentType: 'application/json', // Set content type to JSON
        data: JSON.stringify(selectedIds), // Send data as JSON string
        success: function(response) {
            if (response.success) {
                alert("Selected items deleted successfully.");
                loadCommonList('/generalController/gmType', 'General Type');
            } else {
                alert(response.message || "Error deleting selected items.");
            }
        },
        error: function(xhr, status, error) {
            // Log the error response for debugging
            console.error("Error details:", xhr.responseText, status, error);
            alert("An unexpected error occurred. Please check the console for details.");
        }
    });
}
 function editRow(gmId) {
    if (!gmId) {
        alert("Error: gmId is not provided. Cannot edit this row.");
        console.error("editRow called without gmId.");
        return;
    }

    // Get the input fields and button for the row
    const nameInput = document.getElementById(`name`);
    const valueInput = document.getElementById(`value`);
    const editButton = document.getElementById(`editBtn-${gmId}`);
    // Validate if the elements exist
    if (!nameInput & !valueInput & !editButton) {
        alert(`Error: Unable to find elements for gmId: ${gmId}.`);
        console.error(`Missing elements for gmId: ${gmId}. Check your HTML structure.`);
        return;
    }

    // Enable editing for the name and value fields
    nameInput.readOnly = false;
    valueInput.readOnly = false;

    // Optionally change the background color to indicate edit mode
    nameInput.style.backgroundColor = "#e8f0fe";
    valueInput.style.backgroundColor = "#e8f0fe";

    // Change the button text to "Editing..."
    editButton.textContent = "Editing...";
    editButton.disabled = true;

 // Toggle button visibility
    actionEdit.style.display = "none";
    actionSave.style.display = "block";
    // Log success
    console.log(`Row with gmId: ${gmId} is now editable.`);
}
 
 /* function editRow(gmId) {
	    // Select elements based on dynamic gmId
	    const nameInput = document.getElementById(`name-${gmId}`);
	    const valueInput = document.getElementById(`value-${gmId}`);
	    const actionEdit = document.getElementById(`action-edit-${gmId}`);
	    const actionSave = document.getElementById(`action-save-${gmId}`);

	    // Check if elements exist
	    if (!nameInput || !valueInput || !actionEdit || !actionSave) {
	        alert(`Error: Unable to edit. Missing elements for gmId: ${gmId}.`);
	        return;
	    }

	    // Enable editing for the fields
	    nameInput.readOnly = false;
	    valueInput.readOnly = false;

	    // Change background for visual feedback
	    nameInput.style.backgroundColor = "#e8f0fe";
	    valueInput.style.backgroundColor = "#e8f0fe";

	    // Toggle button visibility
	    actionEdit.style.display = "none";
	    actionSave.style.display = "block";
	}
 */
	function saveRow(gmId) {
	    // Select elements
	    const nameInput = document.getElementById(`name-${gmId}`);
	    const valueInput = document.getElementById(`value-${gmId}`);
	    const actionEdit = document.getElementById(`action-edit-${gmId}`);
	    const actionSave = document.getElementById(`action-save-${gmId}`);

	    // Ensure elements exist
	    if (!nameInput || !valueInput || !actionEdit || !actionSave) {
	        alert(`Error: Unable to save. Missing elements for gmId: ${gmId}.`);
	        return;
	    }

	    // Get updated values
	    const updatedName = nameInput.value.trim();
	    const updatedValue = valueInput.value.trim();

	    // Validate non-empty values
	    if (!updatedName || !updatedValue) {
	        alert("Error: Both fields are required.");
	        return;
	    }

	    // Simulate AJAX call
	    fetch(`/CWFM/generalController/updateGeneralMaster`, {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({
	            gmId: gmId,
	            gmName: updatedName,
	            gmDescription: updatedValue,
	        }),
	    })
	        .then(response => response.json())
	        .then(result => {
	            if (result.success) {
	                alert("Data updated successfully.");

	                // Revert fields to read-only
	                nameInput.readOnly = true;
	                valueInput.readOnly = true;
	                nameInput.style.backgroundColor = "";
	                valueInput.style.backgroundColor = "";

	                // Toggle button visibility
	                actionEdit.style.display = "block";
	                actionSave.style.display = "none";
	            } else {
	                alert(`Error: ${result.message}`);
	            }
	        })
	        .catch(error => {
	            console.error("Error:", error);
	            alert("An error occurred while saving data.");
	        });
	}

	function cancelEdit(gmId) {
	    // Select elements
	    const nameInput = document.getElementById(`name-${gmId}`);
	    const valueInput = document.getElementById(`value-${gmId}`);
	    const actionEdit = document.getElementById(`action-edit-${gmId}`);
	    const actionSave = document.getElementById(`action-save-${gmId}`);

	    // Ensure elements exist
	    if (!nameInput || !valueInput || !actionEdit || !actionSave) {
	        alert(`Error: Unable to cancel. Missing elements for gmId: ${gmId}.`);
	        return;
	    }

	    // Reset to original values
	    nameInput.value = nameInput.defaultValue;
	    valueInput.value = valueInput.defaultValue;

	    // Revert fields to read-only
	    nameInput.readOnly = true;
	    valueInput.readOnly = true;
	    nameInput.style.backgroundColor = "";
	    valueInput.style.backgroundColor = "";

	    // Toggle button visibility
	    actionEdit.style.display = "block";
	    actionSave.style.display = "none";
	}

	 /* function redirectToBillView() {
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
		    xhr.open("GET", "/CWFM/bill/view" + unitId , true);
		    xhr.send();
		}    */

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

</script>
    <style>
     /* General Styles */
   body {
    margin: 0;
    padding: 0;
    font-family: 'Titillium Web', sans-serif;
    overflow-y: scroll; /* Adds a vertical scroll bar */
}

/* Top Navigation Bar Styles */
.top-nav {
    background-color: rgb(0, 81, 81);
    color: white;
    height: 50px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 10px;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1001;
    box-sizing: border-box;
    font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
    font-size: 1.25rem;
    font-weight: 600;
    line-height: 1.5rem;
}

.top-nav .logo {
    height: 40px;
    width: auto;
    margin: 0 5px;
}

.top-nav .logo.dot1 {
    height: 30px;
}

.top-nav .heading {
    color: white;
    font-size: 1rem;
    font-weight: bold;
    text-align: center;
    flex-grow: 1;
}

.top-nav .dropdown span {
    color: white;
    font-size: 1rem;
    font-weight: bold;
}

.top-nav .dropdown-content {
    display: none;
    position: absolute;
    background-color: rgb(0, 81, 81);
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
    right: 0;
}

.top-nav .dropdown-content a {
    color: white;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-family: 'Noto Sans', sans-serif;
    font-size: .875rem;
    font-weight: 500;
    line-height: 2.5rem;
}

.top-nav .dropdown-content a:hover {
    background-color: #004d40;
}

.top-nav .dropdown:hover .dropdown-content {
    display: block;
}

/* Side Navigation Bar Styles */
.main-menu {
    background: rgb(0, 81, 81);
    position: fixed;
    top: 50px;
    bottom: 0;
    height: calc(100% - 50px);
    left: 0;
    width: 60px;
    overflow: hidden;
    transition: width 0.3s ease;
    z-index: 1000;
    color: white;
}

.main-menu.expanded {
    width: 250px;
}

.main-menu > ul {
    margin: 7px 0;
    padding: 0;
    list-style: none;
}

.main-menu li {
    position: relative;
    width: 100%;
}

.main-menu li > a {
    display: flex;
    align-items: center;
    color: white;
    font-size: 14px;
    text-decoration: none;
    padding: 10px 15px;
    transition: background-color 0.1s linear;
}

.main-menu .nav-icon {
    font-size: 20px;
    color: white;
    margin-right: 15px;
}

.main-menu .nav-text {
    flex-grow: 1;
    overflow: hidden;
    white-space: nowrap;
}

.main-menu .sub-menu {
    display: none;
    padding: 0;
    padding-left:55px;
    margin: 0;
    background: #00796b;
    list-style: none;
}

.main-menu .sub-menu li a {
    padding: 10px 30px;
    font-size: 13px;
    color: white;
    text-decoration: none;
    margin: 0;
    display: block;
    box-sizing: border-box;
}

.main-menu .sub-menu li a:hover {
    background: #004d40;
}

.main-menu li.active > a,
.main-menu li:hover > a {
    background-color: #009688;
}

.main-menu li.active .sub-menu {
    display: block;
}

/* Content Area */
.form-content {
    float: left;
    background: #ffffff;
    height: calc(100% - 50px);
    margin-top: 50px;
    margin-left: 60px;
    width: calc(100% - 60px);
    transition: margin-left 0.3s ease, width 0.3s ease;
    box-sizing: border-box;
}

.main-menu.expanded + .form-content {
    margin-left: 250px;
    width: calc(100% - 250px);
}

.main-menu:not(.expanded) + .form-content {
    margin-left: 60px;
    width: calc(100% - 60px);
}

/* Table Header Size Adjustment */
table th {
    font-size: 0.875rem;
    padding: 8px;
}

/* Navigation Pills Styles */
.navmenu .nav-pills a, .navmenu .nav-pills .expander {
    font-family: 'Noto Sans Display', 'Noto Sans', sans-serif;
    font-size: .875rem;
    font-weight: 500;
    line-height: 1.125rem;
    letter-spacing: normal;
    border-radius: 0 999px 999px 0;
    margin: 0;
    min-height: 2.75rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
}

.navmenu a, .navmenu .expander {
    padding: .5rem .5rem .5rem 1.5rem;
    min-height: 2.75rem;
    text-overflow: ellipsis;
    overflow: hidden;
    word-wrap: break-word;
    color: var(--zed_sys_color_mainMenuAction_enabled);
}

.menu-links-container div {
    width: 100%;
}

*, :after, :before {
    box-sizing: border-box;
}

.navmenu .nav-pills {
    flex: 1;
    padding-right: 1rem;
}

.nav {
    display: flex;
    flex-wrap: wrap;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
}

.navmenu {
    position: absolute;
    left: -19.5rem;
    transform: translate(0);
    z-index: 1033;
    transition: all 0.3s ease;
}

.navmenu.show {
    left: 0;
}

/* Responsive Styles */
@media (max-width: 768px) {
    .top-nav, .main-menu {
        display: block;
    }

    .top-nav {
        position: relative;
        height: auto;
    }

    .main-menu {
        width: 100%;
        height: auto;
        position: static;
        display: block;
    }

    .main-menu.expanded {
        width: 100%;
    }

    .main-menu .nav-icon {
        font-size: 18px;
        margin-right: 10px;
    }
}

.active {
    background-color: #004d40;
    color: white;
}
.user-info {
    display: flex;
    align-items: center;
}

.user-logo {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-color: #005151; /* Default background color for initials */
    margin-right: 10px;
}

.user-logo-img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    display: none; /* Hide by default */
}

.user-initials {
    color: #fff;
    font-weight: bold;
    font-size: 16px;
    display: none; /* Hide by default */
}

.user-name {
    font-size: 16px;
    color: #005151;
    font-family: Volte Rounded, Noto Sans, sans-serif;
}

/* Show user logo if available, hide initials */
.user-logo img[src*="user.logo"] {
    display: block;
}

.user-logo img[src=""] + .user-initials {
    display: block; /* Show initials if logo is not available */
}
.dropdown {
    position: relative;
    display: inline-block;
    cursor: pointer;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {
    background-color: #f1f1f1;
}
.user-initials {
    width: 40px;
    height: 40px;
    background-color: #005151;
    color: white;
    text-align: center;
    line-height: 40px;
    border-radius: 50%;
    font-weight: bold;
    font-size: 20px;
}
.initials-icon {
            display: inline-block;
            width: 40px; /* Adjust size as needed */
            height: 40px; /* Adjust size as needed */
            border-radius: 50%;
            background-color: #3498db; /* Background color of the circle */
            color: white; /* Text color */
            text-align: center;
            line-height: 40px; /* Center text vertically */
            font-size: 18px; /* Font size of initials */
            font-weight: bold; /* Make initials bold */
            margin-right: 10px; /* Space between icon and text */
        }
        /* Side Navigation Bar Styles */
.main-menu {
    background: rgb(0, 81, 81);
    position: fixed;
    top: 50px;
    bottom: 0;
    height: calc(100% - 50px);
    left: 0;
    width: 60px;
    overflow-y: auto; /* Enable vertical scrolling */
    overflow-x: hidden; /* Hide horizontal scroll if not needed */
    transition: width 0.3s ease;
    z-index: 1000;
    color: white;
}

.main-menu.expanded {
    width: 250px;
}

/* Optional: Add a scrollbar style for webkit browsers */
.main-menu::-webkit-scrollbar {
    width: 8px;
}

.main-menu::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.5);
    border-radius: 4px;
}

.main-menu::-webkit-scrollbar-thumb:hover {
    background: rgba(0, 0, 0, 0.7);
}
.arrow-up, .arrow-down {
    width: 10px;
    height: 8px;
    filter: invert(100%); /* Turns black arrows white */
}
.home-icon {
    padding: 10px;
    cursor: pointer;
}

.home-icon i {
    font-size: 24px; /* Adjust size as needed */
    color: #ffffff; /* Change color as needed */
}

    </style>
</head>
<body>
<% 
    String initials = (String) session.getAttribute("userInitials");
    MasterUser user = (MasterUser) session.getAttribute("loginuser");
    
%>

    <!-- Top Navigation Bar -->
    <div class="top-nav">
    
        <!-- <img src="resources/img/Adani_2012_logo.png" alt="Company Logo" class="logo"> -->
        <div class="heading">Contract Labor Management System</div>
         <div class="dropdown">
             <span class="initials-icon"><c:out value="${sessionScope.userInitials}" /></span> <span><c:out value="${sessionScope.loginuser.firstName}" /><c:out value="${sessionScope.loginuser.lastName}" /></span>
            <div class="dropdown-content">
                <a href="#" onclick="loadCommonList('/password/change','Change Password')">Change Password</a>
                <a href="#" onclick="loadLogout()">Logout</a>
            </div>
        </div>
        <img src="resources/img/Dot1.png" alt="Dot1 Logo" class="logo dot1">
    </div>

    <!-- Side Navigation Bar -->
    <nav class="main-menu">
        <ul>
            <li class="main-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleSubMenu('clms-sub-menu'); return false;" class="mainmenu-link">
        <i class="fa fa-dashboard nav-icon"></i>
        <span class="nav-text">CLMS</span>
         <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="display: none;">
         <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="display: inline-block;">
    </a>
    <ul class="sub-menu" id="clms-sub-menu" style="display: none;">
        <li><a href="#" onclick="loadCommonList('/principalEmployer/list','Principal Employer')">Principal Employer</a></li>
        <li><a href="#" onclick="loadCommonList('/contractor/list','Contractor')">Contractor</a></li>
        <li><a href="#" onclick="loadCommonList('/workorders/list', 'Work Order')">Work Order</a></li>
       <!--  <li><a href="#" onclick="loadCommonList('/minimumWage/list', 'Minimum Wage Master')">Minimum Wage Master</a></li> -->
       <!--  <li><a href="#" onclick="loadCommonList('/contractworkmen/quickOBList', 'Contract Workmen List')">Contract Workmen</a></li> -->
        <!-- <li><a href="#" onclick="loadCommonList('/workmenWage/list', 'Workmen Wages')">Workmen Wages</a></li> -->
    </ul>
</li>
   <li>
            <a href="#" class="nav-link" onclick="toggleSubMenu('general-management-sub-menu')">
                <i class="fa fa-cog nav-icon"></i> <!-- Icon for General Management -->
                <span class="nav-text">General Management</span>
                <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
            </a>
            <ul class="sub-menu" id="general-management-sub-menu">
              <li><a href="#" onclick="loadCommonList('/generalController/gmType', 'General Type')">General Type</a></li>
<li><a href="#" onclick="loadCommonList('/generalController/generalMaster', 'General Master')">General Master</a></li>
<li><a href="#" onclick="loadCommonList('/roleRights/roleRightsList', 'Role Rights')">Role Rights</a></li>
<li><a href="#" onclick="loadCommonList('/usersController/userList', 'Users')">Users</a></li>
            </ul>
        </li>
        
        <li>
            <a href="#" class="nav-link" onclick="toggleSubMenu('admin-sub-menu')">
                <i class="fa fa-cog nav-icon"></i> <!-- Icon for General Management -->
                <span class="nav-text">Admin</span>
                <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
            </a>
            <ul class="sub-menu" id="admin-sub-menu">
              <li><a href="#" onclick="loadCommonList('/org-level/list', 'Org Levels')">Org Levels</a></li>
              <li><a href="#" onclick="loadCommonList('/org-level-entryController/org-level-entry', 'Org Level Entries')">Org Level Entries</a></li>
              <li><a href="#" onclick="loadCommonList('/org-level-mapping/list', 'Org Level Mappings')">Org Level Mappings</a></li>
            </ul>
        </li> 
  <li>
                <a href="#" class="nav-link" onclick="toggleSubMenu('contractor-sub-menu')">
                    <i class="fa fa-users nav-icon"></i>
                    <span class="nav-text">Contractor</span>
                    <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                    <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
                </a>
                <ul class="sub-menu" id="contractor-sub-menu">
                    <li><a href="#" onclick="loadCommonList('/contractor/contReg','Contractor Registration')"> Registration</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractor/contRegList','Contractor Registration List')"> Registration List</a></li>
                    <%-- <!--  <li><a href="#" onclick="loadCommonList('/contractor/view','Contractor Registration view')">Contractor View</a></li>   -->
                        <li><a href="#" onclick="loadQobAdd('/contractor/view', 'Contractor Registration view','${sessionScope.loginuser.userId}')">Contractor View</a></li> --%>
                    <li><a href="#" onclick="loadCommonList('/contractor/contRenewal','Contractor Renewal')">Renewal</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractor/contRenewalList','Contractor Renewal List')"> Renewal List</a></li>
                </ul>
            </li> 
            <li>
                <a href="#" class="nav-link" onclick="toggleSubMenu('workmen-onboarding-sub-menu')">
                    <i class="fa fa-briefcase nav-icon"></i>
                    <span class="nav-text">Workmen Onboarding</span>
                    <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                    <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
                </a>
                <ul class="sub-menu" id="workmen-onboarding-sub-menu">
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/quickOBList', 'On-Bording List')">List</a></li>
                    <li><a href="#" onclick="loadQobAdd('/contractworkmen/addQuickOB', 'On-Boarding','${sessionScope.loginuser.userId}')">On-Boarding</a></li>
                   <!--  <li><a href="#" onclick="loadWorkmenRenew()">Renew</a></li> -->
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/blockList', 'Block List')">Block</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/unblockList', 'Unblock List')">Unblock</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/blackList', 'Black List')">Blacklist</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/deblackList', 'Deblack List')">De-blacklist</a></li>
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/cancel', 'Cancel List')">Cancel</a></li>
                    <!-- <li><a href="#" onclick="loadWorkmenExpat()">Expat</a></li> -->
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/lostordamage', 'Lost Or Damage List')">Lost or Damage</a></li>
                </ul>
            </li>
           <li>
            <ul>
             <li><a href="#" onclick="loadCommonList('/billVerification/viewlist', 'Bill Verification')">
                <i class="fa fa-credit-card nav-icon"></i> <!-- Updated icon for Bill Verification -->
                <span class="nav-text">Bill Verification</span>
            </a></li>
           <li><a href="#" onclick="loadCommonList('/billVerification/List','Bill Verification List')">  List</a></li>
        </ul>
        </li>
        <!-- <li>
            <a href="#" onclick="loadCommonList('/contractor/contReg', 'Contractor Registration')">
                <i class="fa fa-users nav-icon"></i> Updated icon for Contractor Registration
                <span class="nav-text">Contractor Registration</span>
            </a>
        </li> -->
        <li>
            <a href="#" onclick="loadCommonList('/reports/list', 'Reports')">
                <i class="fa fa-bar-chart nav-icon"></i> <!-- Updated icon for Reports -->
                <span class="nav-text">Reports</span>
            </a>
        </li>
        </ul>
    </nav>
    

    <!-- Main Content Area -->
    <div id="mainContent" class="form-content"></div>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
  

    function toggleSubMenu(menuId) {
        const submenu = document.getElementById(menuId);
        const arrowUpIcon = submenu.previousElementSibling.querySelector('.arrow-up');
        const arrowDownIcon = submenu.previousElementSibling.querySelector('.arrow-down');

        if (submenu.style.display === 'block') {
            submenu.style.display = 'none';
            arrowUpIcon.style.display = 'none';
            arrowDownIcon.style.display = 'inline-block';
        } else {
            submenu.style.display = 'block';
            arrowUpIcon.style.display = 'inline-block';
            arrowDownIcon.style.display = 'none';
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        let submenuVisible = false;
        let submenuSelected = false;

        function toggleSubMenu() {
            const submenu = document.querySelector('#clms-sub-menu');
            const arrowUpIcon = document.querySelector('.arrow-up');
            const arrowDownIcon = document.querySelector('.arrow-down');

            submenuVisible = !submenuVisible;
            submenu.style.display = submenuVisible ? 'block' : 'none';
            arrowUpIcon.style.display = submenuVisible ? 'inline-block' : 'none';
            arrowDownIcon.style.display = submenuVisible ? 'none' : 'inline-block';
        }

        const clmsMenuLink = document.querySelector('.mainmenu-link');
        if (clmsMenuLink) {
            clmsMenuLink.addEventListener('click', (event) => {
                event.preventDefault(); // Prevent default link behavior
                toggleSubMenu();
            });
        }

        // Ensure submenu closes when the sidebar is hovered out
        const mainMenu = document.querySelector('.main-menu');
        mainMenu.addEventListener('mouseenter', function() {
            this.classList.add('expanded');
        });

        mainMenu.addEventListener('mouseleave', function() {
            if (!submenuSelected) {
                this.classList.remove('expanded');
            }
        });

        // Handle submenu selection
        document.querySelectorAll('#clms-sub-menu a').forEach(link => {
            link.addEventListener('click', function() {
                submenuSelected = true; // Mark submenu as selected
                highlightMenuItem(this.parentElement);
                loadContent(this.textContent);
                setTimeout(() => submenuSelected = false, 300); // Reset after a short delay
            });
        });

        function highlightMenuItem(menuItem) {
            const menuItems = document.querySelectorAll('#clms-sub-menu li');
            menuItems.forEach(item => item.classList.remove('active'));
            menuItem.classList.add('active');
        }

        function loadContent(text) {
           const headingText = text;
           updateHeading(headingText);
         //   document.getElementById("mainContent").innerHTML = `<h1>${text}</h1><p>Content for ${text} will go here.</p>`;
            resetSessionTimer();
       }

        function updateHeading(text) {
            const headingElement = document.querySelector('.top-nav .heading');
            headingElement.textContent = text;
        }

        function resetSessionTimer() {
            console.log('Session timer reset');
        }
    });


    function loadContent(link) {
        const headingText = link.textContent;
        updateHeading(headingText);
        const urlMatch = link.getAttribute('onclick').match(/'(.*?)'/);
        if (urlMatch) {
            const url = urlMatch[1]; // Extract URL from onclick
            console.log("Constructed URL:", url);
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                }
            };
            xhttp.open("GET", url, true);
            xhttp.send();
        } else {
            console.error("Failed to extract URL from onclick attribute");
        }
    }
   

    function updateHeading(text) {
        const headingElement = document.querySelector('.top-nav .heading');
        headingElement.textContent = text;
    }

   
    function resetSidebar() {
        // Hide all sub-menus
        const subMenus = document.querySelectorAll('.sub-menu');
        subMenus.forEach(menu => {
            menu.style.display = 'none';
        });

        // Reset all menu item selections (optional, depends on how selections are handled)
        const menuItems = document.querySelectorAll('.main-menu-item');
        menuItems.forEach(item => {
            item.classList.remove('selected'); // Adjust according to your CSS class for selected items
        });

        // Show the default menu or reset to initial state
        // You might want to set a specific state here, or just leave it empty
    }
    
   
    function loadPrincipalEmployerList() {
        updateHeading('Principal Employer');
        var url = contextPath + '/principalEmployer/list';
        console.log("Constructed URL:", url);

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                } else {
                    console.error("Failed to load content:", this.status, this.statusText);
                }
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    function loadWorkmenQOB() {
        updateHeading('Quick On-boarding');
        var url = contextPath + '/contractworkmen/quickOnbordingList';
        console.log("Constructed URL:", url);

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                } else {
                    console.error("Failed to load content:", this.status, this.statusText);
                }
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }

    function loadContractorList() {
        updateHeading('Contractor');
        var url = contextPath + '/contractor/list';
        console.log("Constructed URL:", url);

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

    function loadChangePassword() {
        console.log("Change Password clicked");
        window.location.href = window.location.origin + '/CWFM/changePassword'; 
     }
    function loadLogout() {
    	console.log("Logout clicked");
        document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        
        // Optionally, clear any additional session storage or local storage
        sessionStorage.clear();
        localStorage.clear();
        
        // Redirect to the login page or a logout API endpoint
        window.location.href = 'UserLogin.jsp';
    }

    function resetSessionTimer() {
        // Placeholder for session timer reset logic
    }
    function changePassword() {
        const oldPassword = document.getElementById("oldPassword").value.trim();
        const newPassword = document.getElementById("newPassword").value.trim();
        const confirmPassword = document.getElementById("confirmPassword").value.trim();
        const messageElement = document.getElementById("message");

        // Reset error message
        messageElement.innerText = "";

        // Validate inputs
        if (!oldPassword || !newPassword || !confirmPassword) {
            messageElement.innerText = "All fields are required.";
            return;
        }

        if (newPassword !== confirmPassword) {
            messageElement.innerText = "New Password and Confirm Password do not match.";
            return;
        }

        // Prepare data for submission
        const data = JSON.stringify({
            oldPassword: oldPassword,
            newPassword: newPassword
        });

        // AJAX request to the server
        fetch("/changePassword", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: data
        })
            .then(response => {
                if (response.ok) {
                    alert("Password changed successfully!");
                    window.location.href = "UserLogin.jsp"; // Redirect on success
                } else if (response.status === 400) {
                    messageElement.innerText = "Old password is incorrect.";
                } else {
                    throw new Error("Unexpected error occurred.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                messageElement.innerText = "Failed to change password. Please try again.";
            });
    }


    </script>
</body>

</html>
