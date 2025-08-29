// Function to add a row
function addRow() {
    const container = document.getElementById("rowsContainer");
    const firstRow = container.querySelector(".row-block");

    if (!firstRow) {
        alert("No template row found!");
        return;
    }

    // Clone the first row
    const newRow = firstRow.cloneNode(true);

    // Reset select values in cloned row
    newRow.querySelectorAll("select").forEach(sel => sel.value = "");

    // Append the new row
    container.appendChild(newRow);
}

function deleteRow(button) {
    let container = document.getElementById("rowsContainer");
    let totalRows = container.querySelectorAll(".row-block").length;

    if (totalRows === 1) {
        // ✅ Clear values instead of deleting the last row
        alert("At least one row is required.");
        let row = container.querySelector(".row-block");

        // Reset selects to default
        row.querySelectorAll("select").forEach(select => {
            select.selectedIndex = 0;
        });
    } else {
        // ✅ Safe to delete
        button.closest(".row-block").remove();
    }
}


    
    function saveData() {
    const rows = document.querySelectorAll("#rowsContainer .row-block");

    if (rows.length === 0) {
        alert("Please add at least one row before saving.");
        return;
    }

    let data = [];
    let isValid = true;
    let seen = new Set();

    rows.forEach((row, index) => {
        const principalEmployerId = row.querySelector("select[name='principalEmployerId']").value;
        const departmentId = row.querySelector("select[name='departmentId']").value;
        const subDepartmentId = row.querySelector("select[name='subDepartmentId']").value;

        row.style.backgroundColor = "";

        if (!principalEmployerId && departmentId) {
            alert("Please select Principal Employer and Department in row " + (index + 1));
            isValid = false;
            return;
        }

        if (!principalEmployerId && !departmentId && !subDepartmentId) {
            alert("Please select Principal Employer, Department and Subdepartment in row " + (index + 1));
            isValid = false;
            return;
        }

        if (!departmentId && subDepartmentId) {
            alert("Please select Department in row " + (index + 1) + " before selecting Sub Department");
            isValid = false;
            return;
        }

       // ✅ Duplicate check (PE + Dept combination must be unique)
        let key = principalEmployerId + "-" + departmentId;
        if (seen.has(key)) {
            alert("Duplicate found in row " + (index + 1) + ": Principal Employer + Department must be unique.");
            isValid = false;
            row.style.backgroundColor = "lightcoral"; // highlight duplicate row
            return;
        }
        seen.add(key);

        // ✅ Push valid row to data array
        data.push({
            principalEmployerId,
            departmentId,
            subDepartmentId
        });
    });

    if (!isValid || data.length === 0) {
        return;
    }

    console.log("Saving data:", data);

    fetch("/CWFM/departmentMapping/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(resp => resp.text().then(msg => {
        if (!resp.ok) {
            throw new Error(msg); // ❌ server error → pass message to catch
        }
        return msg; // ✅ success → return msg
    }))
    .then(msg => {
        alert("Saved successfully: " + msg);
        loadCommonList('/departmentMapping/list', 'Department Mapping');
    })
    .catch(err => {
        console.error("Error saving:", err);
        alert(err.message); // show server error directly
    });
}


    function refreshTable() {
        // If table has at least 1 data row, hide the "no records" row
        if ($("#myTable tbody tr").length > 1) {
            $("#myTable .no-records-found").hide();
        } else {
            $("#myTable .no-records-found").show();
        }
    }
    
    function redirectToMappingAdd() {
   
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/departmentMapping/list", true);
	    xhr.send();
	}
	
	function goBackToDeptMappingList() {
    	 loadCommonList('/departmentMapping/existinglist', 'Department Mapping');
    }
    
    function redirectToTradeSkillMappingAdd() {
   
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/departmentMapping/addTradeSkill", true);
	    xhr.send();
	}
	function addTradeSkillRow() {
    const container = document.getElementById("rowsContainer");
    const firstRow = container.querySelector(".row-block");

    if (!firstRow) {
        alert("No template row found!");
        return;
    }

    // Clone the first row
    const newRow = firstRow.cloneNode(true);

    // Reset select values in cloned row
    newRow.querySelectorAll("select").forEach(sel => sel.value = "");

    // Append the new row
    container.appendChild(newRow);
}
	function saveTradeSkillData() {
    const rows = document.querySelectorAll("#rowsContainer .row-block");

    if (rows.length === 0) {
        alert("Please add at least one row before saving.");
        return;
    }

    let data = [];
    let isValid = true;
    let seen = new Set();

    rows.forEach((row, index) => {
        const principalEmployerId = row.querySelector("select[name='principalEmployerId']").value;
        const tradeId = row.querySelector("select[name='tradeId']").value;
        const skillId = row.querySelector("select[name='skillId']").value;

        row.style.backgroundColor = "";

        /*if (!principalEmployerId && tradeId) {
            alert("Please select Principal Employer and Trade in row " + (index + 1));
            isValid = false;
            return;
        }
*/
        if (!principalEmployerId && !tradeId && !skillId) {
            alert("Please select Principal Employer, Trade and Skill in row " + (index + 1));
            isValid = false;
            return;
        }

        if (!tradeId && skillId) {
            alert("Please select Trade in row " + (index + 1) + " before selecting Skill");
            isValid = false;
            return;
        }

       // ✅ Duplicate check (PE + Dept combination must be unique)
        let key = principalEmployerId + "-" + tradeId + "-" +skillId;
        if (seen.has(key)) {
            alert("Duplicate found in row " + (index + 1) + ": Principal Employer + Trade + Skill must be unique.");
            isValid = false;
            row.style.backgroundColor = "lightcoral"; // highlight duplicate row
            return;
        }
        seen.add(key);

        // ✅ Push valid row to data array
        data.push({
            principalEmployerId,
            tradeId,
            skillId
        });
    });

    if (!isValid || data.length === 0) {
        return;
    }

    console.log("Saving data:", data);

    fetch("/CWFM/departmentMapping/saveTradeSkill", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(resp => resp.text().then(msg => {
        if (!resp.ok) {
            throw new Error(msg); // ❌ server error → pass message to catch
        }
        return msg; // ✅ success → return msg
    }))
    .then(msg => {
        alert("Saved successfully: " + msg);
        loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Mapping');
    })
    .catch(err => {
        console.error("Error saving:", err);
        alert(err.message); // show server error directly
    });
}

function goBackToTradeMappingList() {
    	 loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Mapping');
    }