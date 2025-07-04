
function redirectToContractorRenew() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/renewal/create", true);
    xhr.send();
}

function redirectToContractorRenewView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }

    var selectedRow = selectedCheckboxes[0].closest('tr');
    var contractorregId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/renewal/view/" + contractorregId, true);
    xhr.send();
}

function setValueIfPresent(elementId, value) {
    var input = document.getElementById(elementId);
    input.value = value || "";
}

function getAllContractorDetailForRenewal(contractorId) {
    var principalEmployerSelect = document.getElementById("principalEmployerId");
    var unitId = principalEmployerSelect.value;
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/renewal/getAllContractorDetailForRenewal?unitId=" + unitId + "&contractorId=" + contractorId;

    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var contractors = JSON.parse(xhr.responseText);
            console.log("Response:", contractors);

            setValueIfPresent("managerNameId", contractors.managerName);
            setValueIfPresent("locofWorkId", contractors.locofWork);
            setValueIfPresent("totalStrengthId", contractors.totalStrength);
            setValueIfPresent("rcMaxEmpId", contractors.rcMaxEmp);
            setValueIfPresent("pfNumId", contractors.pfNum);
            setValueIfPresent("natureOfWorkId", contractors.natureOfWork);
            const dateFrom = contractors.contractFrom.split(" ")[0];
            setValueIfPresent("contractFromId", dateFrom);
            const dateTo = contractors.contractTo.split(" ")[0];
            setValueIfPresent("contractToId", dateTo);
            setValueIfPresent("contractorNameId", contractors.contractorName);
            setValueIfPresent("emailId", contractors.email);
            setValueIfPresent("mobileId", contractors.mobile);
            setValueIfPresent("aadharId", contractors.aadhar);
            setValueIfPresent("panId", contractors.pan);
            setValueIfPresent("gstId", contractors.gst);
            setValueIfPresent("addressId", contractors.address);
            setValueIfPresent("contractTypeId", contractors.contractType);
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
    };

    xhr.send();
}

document.addEventListener('click', function (e) {
    if (e.target.matches('button.addRowNew')) {
        e.preventDefault();
        e.stopImmediatePropagation();
        setTimeout(() => addRowContNew(), 0); // prevent recursion
    } else if (e.target.matches('button.removeRowNew')) {
		e.preventDefault();
		       e.stopImmediatePropagation();
		       setTimeout(() => deleteRowContNew(e.target), 0); // prevent recursion
        //deleteRowContNew(e.target);
    }
});

function addRowContNew() {
    const tbody = document.getElementById("licenseBody");
    const today = new Date().toISOString().split('T')[0];

    const row = document.createElement("tr");
    row.innerHTML = `
        <td><button type="button" class="btn btn-success addRowNew" style="color:white;background-color:green;">+</button></td>
        <td><button type="button" class="btn btn-danger removeRowNew" style="color:white;background-color:red;">−</button></td>
        <td></td>
        <td><input type="text" class="form-control documentNumber" name="documentNumber" /></td>
        <td><input type="number" class="form-control coverage" name="coverage" min="0" step="1" /></td>
        <td><input type="date" class="form-control validFrom" name="validFrom" min="${today}" /></td>
        <td><input type="date" class="form-control validTo" name="validTo" min="${today}" /></td>
        <td><input type="file" class="form-control attachment" name="attachment" /></td>
    `;

    const originalDropdown = document.querySelector('#licenseBody tr:first-child select.documentType');
    if (originalDropdown) {
        const clonedDropdown = originalDropdown.cloneNode(true);
        clonedDropdown.classList.add("documentType");
        clonedDropdown.name = "documentType";
        row.cells[2].appendChild(clonedDropdown);
    }

    tbody.appendChild(row);
}

function deleteRowContNew(buttonElement) {
    const row = buttonElement.closest('tr');
    const tbody = document.getElementById("licenseBody");

    const dataRows = Array.from(tbody.querySelectorAll('tr')).filter(r => r.querySelector('button.removeRowNew'));
	console.log(dataRows.length);
    if (dataRows.length > 1) {
        row.remove();
    } else {
        alert("At least one row must be present.");
    }
}
