  function fetchTemplateOptions() {
    var dropdown = document.getElementById("templateType");
    var selectedTemplate = dropdown.value;
    if (selectedTemplate) {
        var xhr = new XMLHttpRequest();
       /* xhr.open("GET", "/data/getTemplateOptions?selectedTemplate=" + selectedTemplate, true);*/
        var url = contextPath + "/data/getTemplateOptions?selectedTemplate=" + selectedTemplate ;
        xhr.open("GET", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);

                document.getElementById("templateOptions").style.display = "block";
                document.getElementById("templateMessage").innerText = "What would you like to do with this template?";
                document.getElementById("viewTemplate").href = response.viewUrl;
                document.getElementById("downloadTemplate").href = response.downloadUrl;
            }
        };
        xhr.send();
    } else {
        document.getElementById("templateOptions").style.display = "none";
    }
}

function renderErrors(errorData, uploadedFileName) {
    const container = document.getElementById("errorContainer");

    // Always clear old content first
    container.innerHTML = "";

    // If no errors → hide and exit
    if (!Array.isArray(errorData) || errorData.length === 0) {
        container.style.display = "none";
        return;
    }

    // Filter valid structured errors
    const validErrors = errorData.filter(
        err =>
            err &&
            typeof err === "object" &&
            (err.error || (err.fieldErrors && Object.keys(err.fieldErrors).length > 0))
    );
    // If no valid errors → hide again
    if (validErrors.length === 0) {
        container.style.display = "none";
        return;
    }
    // Now we actually show the container
    container.style.display = "block";

    // Download button
    const downloadBtn = document.createElement("a");
    downloadBtn.textContent = "⬇️ Download Error Report";
    downloadBtn.href = "#";
    downloadBtn.style.float = "right";
    downloadBtn.style.marginBottom = "8px";
    downloadBtn.style.color = "#0066cc";
    downloadBtn.style.fontWeight = "bold";
    downloadBtn.style.textDecoration = "underline";

    downloadBtn.addEventListener("click", e => {
        e.preventDefault();
        downloadErrorCSV(validErrors, uploadedFileName);
    });

    container.appendChild(downloadBtn);
    // Build table
    const table = document.createElement("table");
    table.classList.add("table", "table-bordered");
    table.style.marginTop = "10px";
    table.style.width = "100%";

    const headerRow = document.createElement("tr");
    ["Row", "Field", "Error Message"].forEach(text => {
        const th = document.createElement("th");
        th.innerText = text;
        th.style.border = "1px solid #999";
        th.style.padding = "6px";
        th.style.color = "#cc0000";
        headerRow.appendChild(th);
    });

    table.appendChild(headerRow);

    validErrors.forEach(error => {
        const rowNum = error.row || "-";

        if (error.fieldErrors) {
            Object.entries(error.fieldErrors).forEach(([field, message]) => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td style="border:1px solid #999;padding:6px;">${rowNum}</td>
                    <td style="border:1px solid #999;padding:6px;">${field}</td>
                    <td style="border:1px solid #999;padding:6px;color:red;">${message}</td>
                `;
                table.appendChild(tr);
            });
        } else if (error.error) {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td style="border:1px solid #999;padding:6px;">${rowNum}</td>
                <td style="border:1px solid #999;padding:6px;">-</td>
                <td style="border:1px solid #999;padding:6px;color:red;">${error.error}</td>
            `;
            table.appendChild(tr);
        }
    });
    container.appendChild(table);
}
/**
 * ✅ CSV download with correct filename
 */
function downloadErrorCSV(errorData, uploadedFileName) {
    if (!errorData || errorData.length === 0) {
        alert("No errors to download!");
        return;
    }

    let csvContent = "Row,Field,Error Message\n";

    errorData.forEach(error => {
        const rowNum = error.row || "-";
        if (error.fieldErrors && typeof error.fieldErrors === "object") {
            Object.entries(error.fieldErrors).forEach(([field, message]) => {
                csvContent += `${rowNum},${field},"${message}"\n`;
            });
        } else if (error.error) {
            csvContent += `${rowNum},-,"${error.error}"\n`;
        }
    });

    //  Get base name safely
    const baseName = uploadedFileName
        ? uploadedFileName.replace(/^error_/, "").replace(/\.[^/.]+$/, "")
        : "upload";

    const fileName = `error_${baseName}.csv`;

    // Download logic
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = fileName;
    a.click();
    URL.revokeObjectURL(url);
}
   
    function fetchTemplateOptions() {
        var selectedTemplate = $("#templateType").val();

        // Hide the message initially
        $("#templateOptions").hide();

        if (selectedTemplate) {
            $.ajax({
                url: "/CWFM/data/getTemplateOptions",
                type: "GET",
                data: { selectedTemplate: selectedTemplate },
                
                success: function(response) {
                	console.log(1);
                    if (response) {
                        $("#templateMessage").text(response);  // Set response text
                        $("#templateOptions").fadeIn();  // Show message smoothly
                    }
                },
                error: function() {
                    console.error("Error fetching template options");
                }
            });
        }
    }
    
    function fetchTemplateInfo() {
        var selectedTemplate = document.getElementById("templateType").value;
        if (!selectedTemplate) {
            alert("Please select a template first.");
            return;
        }
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/CWFM/data/getTemplateInfo?templateType=" + encodeURIComponent(selectedTemplate), true);
        xhr.setRequestHeader("Content-Type", "application/json");
       console.log(2);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                console.log(5);
                document.getElementById("modalTitle").innerText = response.title;
                document.getElementById("modalDescription").innerText = response.description;

                var tableHtml = "<table border='1' style='width:100%;'>";
                tableHtml += "<tr><th>Field Name</th><th>Field Type</th><th>Example</th></tr>";

                response.fields.forEach(function (field) {
                    tableHtml += "<tr><td>" + field.name + "</td><td>" + field.type + "</td><td>" + field.example + "</td></tr>";
                });

                tableHtml += "</table>";
                document.getElementById("modalTable").innerHTML = tableHtml;

                document.getElementById("templateModal").style.display = "block"; // Show modal
            } else if (xhr.readyState === 4) {
                alert("Error fetching template details.");
            }
        };
        xhr.send();
    }

    function closeTemplateModal() {
        document.getElementById("templateModal").style.display = "none"; // Hide modal
    }
    function viewTemplateInfo() {
		 if (window.jQuery && $.fn.DataTable.isDataTable('#viewtable')) {
        $('#viewtable').DataTable().destroy();
    }
        const select = document.getElementById("templateType");
        const selectedTemplate = select.value.trim();
        const selectedText = select.options[select.selectedIndex].text.trim();

        if (!selectedTemplate) {
            alert("Please select at least one template.");
            return;
        }
        
        // Hide sidebar and other elements
        const sidebar = document.getElementById("sidebar");
        const buttonContainer = document.querySelector(".button-container");
        const arrowContainer = document.querySelector(".arrow-container");

        if (sidebar) sidebar.style.display = "none";
        if (buttonContainer) buttonContainer.style.display = "none";
        if (arrowContainer) arrowContainer.style.display = "none";

        // Show the template container
        const tableContainer = document.getElementById("viewTemplateContainer");
        tableContainer.style.display = "block";

        // Get header and body elements
        const tableHeaderRow = document.getElementById("tableHeaderRow");
        const tableBody = document.getElementById("tableBody");

        // Clear old data
        tableHeaderRow.innerHTML = "";
        tableBody.innerHTML = "";
        // Define headers by template name (gmName text)
        let headers = [];

        switch (selectedText.toLowerCase()) {
            case "data-general master":
                headers = ["GM Name", "GM Description", "GM Type ID"];
                break;

            case "data-minimum wage":
                headers = ["Trade", "Skill", "Basic", "DA", "Allowance", "From Date", "Unit Code", "Organization"];
                break;

            case "data-work order":
                headers = ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed On","Vendor Code","Vendor Name","Vendor Address","Blocked Vendor","Work Order Validity From","Work Order Validity To","Work Order Type","Plant Code","Section Code","Department Code","G/L Code","Cost Center","Nature of Job","Rate/Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed","Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company Code"];
                break;

            case "data-contractor":
                headers = ["Work Order Number", "Plant Code", "Organisation", "Main Contractor Code","Contractor Code", "Contractor Name", "Contractor Address","City", "Contractor Manager Name", "Total Workmen Strength","Maximum Number of Workmen", "Labour License Number", "License Valid From","License Valid To", "License Coverage","WC Number", "WC Valid From", "WC Valid To", "WC Coverage","ESIC Number", "ESIC Valid From", "Nature of Work", "PF Number","PF Apply Date"];
                break;

            case "data-principal employer":
                headers = ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability", "Is MW Applicability", "License Number", "PF Code", "ESWC", "Factory License Number","State"];
                break;

            case "data-workmen bulk upload":
                headers = ["First Name*", "Last Name*", "Father's Name or Husband's Name*", "Date of Birth*", "Trade*","Skill*","Nature of Work*", "Hazardous Area*", "Aadhar/Id Proof Number*", "Vendor Code*", "Gender*", "Date of Joining", "Department*", "Area", "Work Order Number*","PF A/C Number","Marital Status*","Technical/Non Technical*","Academic","Blood Group","Accommodation*","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number*","Police Verification Date","Health Chekup Date","Access Levels*","ESIC Number","Unit Code*","Organization Name","EIC Number*","EC Number*","UAN Number","Emergency Contact Person*","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark*"];
                break;

            case "data-workmen bulk upload draft":
                headers = ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id Proof Number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical/Non Technical","Academic","Blood Group","Accommodation","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number","Police Verification Date","Health Chekup Date","Access Levels","ESIC Number","Unit Code","Organization Name","EIC Number","EC Number","UAN Number","Emergency Contact Person","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark"];
                break;

            case "data-trade skill":
                headers = ["Plant Code", "Trade", "Skill"];
                break;

            case "data-department area":
                headers = ["Plant Code", "Department", "Sub Department"];
                break;

            default:
                alert("Template configuration not found for: " + selectedText);
                return;
        }

// ⬇️ Create table headers (with checkbox column)
const selectAllTh = document.createElement("th");
selectAllTh.style.border = "1px solid #ddd";
selectAllTh.style.padding = "8px";

const selectAllCheckbox = document.createElement("input");
selectAllCheckbox.type = "checkbox";

// 👉 Select/Deselect all rows
selectAllCheckbox.addEventListener("change", function () {
    document.querySelectorAll("#tableBody input.rowCheck").forEach(cb => {
        cb.checked = selectAllCheckbox.checked;
    });
});

selectAllTh.appendChild(selectAllCheckbox);
tableHeaderRow.appendChild(selectAllTh);

// ➡️ Real data headers (not including checkbox)
headers.forEach(header => {
    const th = document.createElement("th");
    th.style.border = "1px solid #ddd";
    th.style.padding = "8px";
    th.textContent = header;
    tableHeaderRow.appendChild(th);
});

// ⬇️ Create editable rows (checkbox + data cells)
for (let i = 0; i < 9; i++) {
    const tr = document.createElement("tr");

    // checkbox column
    const selectTd = document.createElement("td");
    selectTd.style.border = "1px solid #ddd";
    selectTd.style.textAlign = "left";

    const rowCheckbox = document.createElement("input");
    rowCheckbox.type = "checkbox";
    rowCheckbox.classList.add("rowCheck");

    selectTd.appendChild(rowCheckbox);
    tr.appendChild(selectTd);

    // real editable cells
    headers.forEach(() => {
        const td = document.createElement("td");
        td.style.border = "1px solid #ddd";
        td.style.padding = "8px";
        td.contentEditable = "true";
        tr.appendChild(td);
    });

    tableBody.appendChild(tr);
}

    }

    function openFileSidebar() {
        document.getElementById("fileUploadSidebar").style.width = "300px"; // Open sidebar
    }

    function closeFileSidebar() {
        document.getElementById("fileUploadSidebar").style.width = "0"; // Close sidebar
    }
   
// track manual editing
let tableEdited = false;
let originalFile = null;
let originalHeaders = []; 

// file picker
document.getElementById("fileInput").addEventListener("change", function () {
    const file = this.files[0];
    if (!file) return;

    const templateSelect = document.getElementById("templateType");
    const selectedText = templateSelect.options[templateSelect.selectedIndex].text.trim();

    if (!selectedText) {
        alert("Please select a template first.");
        this.value = "";
        return;
    }
    const reader = new FileReader();

    reader.onload = function (e) {
        const lines = e.target.result.split(/\r?\n/).filter(l => l.trim() !== "");
        if (lines.length === 0) {
            alert("File is empty.");
            return;
        }
        // -------- Detect delimiter (comma OR pipe) ----------
        const headerLine = lines[0];
        const delimiter = (headerLine.split(",").length > headerLine.split("|").length)
            ? ","
            : "|";
        // -------- FILE HEADERS ----------
        const fileHeaders = headerLine.split(delimiter).map(h => h.trim());
        // -------- EXPECTED HEADERS ----------
        const expectedHeaders = getHeadersByTemplate(selectedText);
        // -------- COMPARE ----------
        const same =
            expectedHeaders.length === fileHeaders.length &&
            expectedHeaders.every((h, i) => h === fileHeaders[i]);
        if (!same) {
            alert(
                "Invalid template format.\n\n" +
                "Selected Template: " + selectedText +
                "\nFile headers do NOT match."
            );

            document.getElementById("fileInput").value = "";
            return;   //  IMPORTANT — DO NOT RENDER TABLE
        }
        // -------- OK — render data ----------
        const rows = lines.slice(1).map(r => r.split(delimiter));
        renderTable(fileHeaders, rows);
        originalFile = file;
    };
    reader.readAsText(file);
});

function uploadTemplateFile() {
    const templateType = document.getElementById("templateType").value;

    // detect manual table data
    const hasTableData = Array.from(
        document.querySelectorAll("#viewtable tbody tr td")
    ).some(td => td.innerText.trim() !== "");

    if (!templateType) {
        alert("Please select a template.");
        return;
    }

    // allow: file OR edited table OR manual table
    if (!originalFile && !tableEdited && !hasTableData) {
        alert("Please upload a file OR edit the table before uploading.");
        return;
    }

    const formData = new FormData();
    formData.append("templateType", templateType);

    if (tableEdited || hasTableData) {
        // build CSV from table
        const csv = buildCsvFromTable();
        const blob = new Blob([csv], { type: "text/csv" });
        const editedFile = new File([blob], "edited_upload.csv", { type: "text/csv" });

        formData.append("file", editedFile);
    } else {
        formData.append("file", originalFile);
    }

    fetch("/CWFM/data/uploadTemplate", {
        method: "POST",
        body: formData
    })
    .then(r  => r.json())
    .then(result => {

        const messageDiv = document.getElementById("uploadMessage");
        messageDiv.style.display = "block";

        messageDiv.style.color =
            result.status === "success" ? "green" :
            result.status === "partial" ? "orange" : "red";

        messageDiv.innerText = result.message;

        setTimeout(() => messageDiv.style.display = "none", 4000);
        if (result.data?.errorData?.length) {
            renderErrors(result.data.errorData);
            return;
        }
        if (result.data?.successData?.length) {
            renderUploadedData(result.data.successData, templateType);
        }
        closeFileSidebar();
         renderErrors([], null);
    })
    .catch(err => {
        console.error("Network/Server error:", err);
        alert("Server error. Please try again later.");
    });
}

document.addEventListener("input", function (e) {
    if (e.target.closest("#viewtable")) {
        tableEdited = true;
    }
});

    function renderUploadedData(data, templateType) {
        const tableBody = document.getElementById("tableBody");
        const tableHeaderRow = document.getElementById("tableHeaderRow");
        tableBody.innerHTML = "";
        tableHeaderRow.innerHTML = "";

        /*if (!data || data.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 10;
            td.textContent = "No data found.";
            tr.appendChild(td);
            tableBody.appendChild(tr);
            return;
        }
*/
        let headers = [];
        let fieldMap = [];

        if (templateType === "Data-General Master") {
            headers = ["GM Name", "GM Description", "GM Type ID"];
            fieldMap = ["gmName", "gmDescription", "gmTypeId"];
        }
        else if (templateType === "Data-minimumWage") {
            headers = ["Trade", "Skill", "Basic", "Da", "Allowance", "From Date", "Unit Code", "Organization"];
            fieldMap = ["trade", "skill", "basic", "da", "allowamce", "fromDate", "unitCode", "organization"];
        }
        else if (templateType === "Data-Work Order") {
            headers = ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed On","Vendor Code","Vendor Name","Vendor Address","Blocked Vendor","Work Order Validity From","Work Order Validity To","Work Order Type","Plant Code","Section Code","Department Code","G/L Code","Cost Center","Nature of Job","Rate/Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed","Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company Code"];
            fieldMap = ["workOrderNumber", "item", "line", "lineNumber", "serviceCode", "shortText", "deliveryCompletion","itemChangedON", "vendorCode", "vendorName", "vendorAddress", "blockedVendor","workOrderValiditiyFrom", "workOrderValiditiyTo", "workOrderType", "plantcode", "sectionCode","departmentCode", "GLCode", "costCenter", "natureofJob", "rateUnit", "quantity", "baseUnitofMeasure","workOrderReleased", "PMOrderNo", "WBSElement", "qtyCompleted", "workOrderReleaseDate","serviceEntryCreatedDate", "serviceEntryUpdatedDate", "purchaseOrgLevel",  "companycode"];
        } 
         else if (templateType === "Data-Contractor") {
            headers = ["Work Order Number", "Plant Code", "Organisation", "Main Contractor Code","Contractor Code", "Contractor Name", "Contractor Address","City", "Contractor Manager Name", "Total Workmen Strength","Maximum Number of Workmen", "Labour License Number", "License Valid From","License Valid To", "License Coverage","WC Number", "WC Valid From", "WC Valid To", "WC Coverage","ESIC Number", "ESIC Valid From", "Nature of Work", "PF Number","PF Apply Date"];
            fieldMap = ["workOrderNumber", "plantCode", "organization", "contractorCode", "contractorId", "contractorName", "contractorAddress", "city", "managerNm", "totalStrength", "maxNoEmp", "licenseNumber", "licenseValidFrom", "licenseValidTo", "coverage", "wcCode", "wcFromDtm", "wcToDtm", "wcTotal", "esiwc", "esiValidFrom", "natureofWork", "pfNum", "pfApplyDt"];
        } 
        else if (templateType === "Data-Principal Employer") {
            headers = ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability", "Is MW Applicability", "License Number", "PF Code", "ESWC", "Factory License Number","State"];
            fieldMap = ["organization", "code", "name", "address", "managerName", "managerAddrs", "businessType", "maxWorkmen", "maxCntrWorkmen", "bocwApplicability", "isMwApplicability", "licenseNumber", "pfCode", "wcNumber", "factoryLicenseNumber","stateNM"];
        }
        else if (templateType === "Data-Workmen Bulk Upload Draft") {
            headers = ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id Proof Number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical/Non Technical","Academic","Blood Group","Accommodation","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number","Police Verification Date","Health Chekup Date","Access Levels","ESIC Number","Unit Code","Organization Name","EIC Number","EC Number","UAN Number","Emergency Contact Person","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark"];
            fieldMap = ["firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork", "hazardousArea",  "aadhaarNumber", "vendorCode", "gender", "doj", "department", "area", "workorderNumber","pfNumber", "maritalStatus", "technical", "academic","bloodGroup", "accommodation", "bankName", "accountNumber", "mobileNumber", "emergencyNumber", "policeVerificationDate", "healthCheckDate", "accessArea", "esicNumber", "unitCode", "organizationName","EICNumber", "ECnumber", "uanNumber", "emergencyName", "pfApplicable", "specializationName", "insuranceType", "LLnumber","address","zone","idMark"];
        }
        else if (templateType === "Data-Workmen Bulk Upload") {
            headers = ["First Name*", "Last Name*", "Father's Name or Husband's Name*", "Date of Birth*", "Trade*","Skill*","Nature of Work*", "Hazardous Area*", "Aadhar/Id Proof Number*", "Vendor Code*", "Gender*", "Date of Joining", "Department*", "Area", "Work Order Number*","PF A/C Number","Marital Status*","Technical/Non Technical*","Academic","Blood Group","Accommodation*","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number*","Police Verification Date","Health Chekup Date","Access Levels*","ESIC Number","Unit Code*","Organization Name","EIC Number*","EC Number*","UAN Number","Emergency Contact Person*","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark*"];
            fieldMap = ["firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork", "hazardousArea",  "aadhaarNumber", "vendorCode", "gender", "doj", "department", "area", "workorderNumber","pfNumber", "maritalStatus", "technical", "academic","bloodGroup", "accommodation", "bankName", "accountNumber", "mobileNumber", "emergencyNumber", "policeVerificationDate", "healthCheckDate", "accessArea", "esicNumber", "unitCode", "organizationName","EICNumber", "ECnumber", "uanNumber", "emergencyName", "pfApplicable", "specializationName", "insuranceType", "LLnumber","address","zone","idMark"];
        }  
        else if (templateType === "Data-Trade Skill") {
            headers = ["Plant Code","Trade", "Skill"];
            fieldMap = ["plantCode","trade","skill"];
        }
        else if (templateType === "Data-Department Area") {
            headers = ["Plant Code","Department","Sub Department"];
            fieldMap = ["plantCode","department","subDepartment"];
        }
        const checkTh = document.createElement("th");
        checkTh.style.border = "1px solid #ddd";
        checkTh.innerHTML = `<input type="checkbox" id="selectAll">`;
        tableHeaderRow.appendChild(checkTh);
    
    
        headers.forEach(header => {
            const th = document.createElement("th");
            th.style.border = "1px solid #ddd";
            th.style.padding = "8px";
            th.textContent = header;
            tableHeaderRow.appendChild(th);
        });
   
const safe = v => (v ?? "");

      data.forEach(row => {
        const tr = document.createElement("tr");
        //  ➜ checkbox column
        const cb = document.createElement("input");
        cb.type = "checkbox";
        cb.className = "rowCheck";

        const cbTd = document.createElement("td");
        cbTd.appendChild(cb);
        tr.appendChild(cbTd);

    fieldMap.forEach(field => {
        const td = document.createElement("td");
        td.contentEditable = "true";        // ✅ editable
        td.dataset.field = field;           // track field
        td.style.border = "1px solid #ddd";
        td.style.padding = "8px";
        td.textContent = safe(row[field]);
        td.addEventListener("input", () => {
            tableEdited = true;             // ✅ mark edited
        });

        tr.appendChild(td);

    });
    tableBody.appendChild(tr);
});
    }       

function previewFileData() {
    const fileInput = document.getElementById("fileInput");
    let templateType = document.getElementById("templateType").value;

    if (!fileInput.files[0] || !templateType) {
        alert("Please select template and file");
        return;
    }
    templateType = templateType.split(",")[0].trim();
    originalFile = fileInput.files[0];

    const reader = new FileReader();
    reader.onload = function (e) {
        const text = e.target.result;
        const lines = text.split(/\r?\n/).filter(x => x.trim() !== "");

        if (lines.length === 0) {
            alert("File is empty.");
            return;
        }
        const headerLine = lines[0];
        // detect delimiter
        const delimiter =
            headerLine.split(",").length >= headerLine.split("|").length
                ? ","
                : "|";

        // FILE HEADERS (normalize)
        const fileHeaders = headerLine
            .split(delimiter)
            .map(h =>
                h.replace(/^"|"$/g, "")      // remove quotes
                 .trim()
                 .toLowerCase()
            )
            .filter(h => h !== "");          // ignore blank tail columns

        // EXPECTED HEADERS (from dropdown)
        const expectedHeaders = getHeadersByTemplate(
            document.getElementById("templateType")
                .options[
                    document.getElementById("templateType").selectedIndex
                ].text
        ).map(h => h.trim().toLowerCase());
        // compare
        let isMatch =
            expectedHeaders.length === fileHeaders.length &&
            expectedHeaders.every((h, i) => h === fileHeaders[i]);

        if (!isMatch) {
            let diff = "";
            expectedHeaders.forEach((h, i) => {
                diff += `\n${i + 1}) Expected: ${h}   |   File: ${fileHeaders[i] || "(missing)"}`;
            });

            alert(
                "Invalid template format.\n\n" +
                "Selected Template: " + templateType 
            );
            fileInput.value = "";
            return;
        }
        originalHeaders = fileHeaders;
        // OK — send to backend preview
        const formData = new FormData();
        formData.append("file", originalFile);
        formData.append("templateType", templateType);

        fetch("/CWFM/data/previewTemplateFile", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(data => {
                renderPreviewData(data);
                closeFileSidebar();
            })
            .catch(console.error);
    };

    reader.readAsText(originalFile);
}

function storeOriginalHeaders() {
    if (originalHeaders.length > 0) return; // store only once
    const ths = document.querySelectorAll("#tableHeaderRow th");
    originalHeaders = Array.from(ths).map(th =>
        th.textContent.trim()
    );
}

function renderPreviewData(data) {
    const body = document.getElementById("tableBody");
    body.innerHTML = "";

    data.forEach(row => {
        const tr = document.createElement("tr");

        // checkbox column
        const cb = document.createElement("input");
        cb.type = "checkbox";
        cb.className = "rowCheck";

        const cbTd = document.createElement("td");
        cbTd.appendChild(cb);
        tr.appendChild(cbTd);

        // data columns
        Object.values(row).forEach(val => {
            const td = document.createElement("td");
            td.contentEditable = "true";
            td.textContent = val ?? "";
            td.style.border = "1px solid #ccc";
            td.style.padding = "6px";

            td.addEventListener("input", () => tableEdited = true);

            tr.appendChild(td);
        });

        body.appendChild(tr);
    });

    tableEdited = false;
}

function buildCsvFromTable() {

    const headerCells = Array.from(document.querySelectorAll("#tableHeaderRow th"));
    const rows = document.querySelectorAll("#tableBody tr");

    if (!headerCells.length) {
        throw new Error("No table headers found");
    }

    //  detect checkbox column(s)
    const checkboxCols = headerCells
        .map((th, idx) => th.querySelector('input[type="checkbox"]') ? idx : -1)
        .filter(idx => idx !== -1);

    //  CSV HEADERS  (ignore checkbox col)
    const headers = headerCells
        .filter((_, idx) => !checkboxCols.includes(idx))
        .map(th => th.innerText.trim());

    let csv = headers.join(",") + "\n";

    //  CSV ROWS  (ignore checkbox col)
    rows.forEach(tr => {
        const tds = Array.from(tr.querySelectorAll("td"));

        const row = tds
            .filter((_, idx) => !checkboxCols.includes(idx))
            .map(td => `"${td.innerText.trim().replace(/"/g, '""')}"`);

        // skip blank rows
        if (row.join("").replace(/"/g, "").trim() !== "") {
            csv += row.join(",") + "\n";
        }
    });

    return csv;
}

function getHeadersByTemplate(selectedText) {
    switch (selectedText.toLowerCase()) {
		
        case "data-general master":
            return ["GM Name", "GM Description", "GM Type ID"];
            
        case "data-minimum wage":
            return ["Trade", "Skill", "Basic", "DA", "Allowance", "From Date", "Unit Code", "Organization"];
        
        case "data-work order":
            return ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed On","Vendor Code","Vendor Name","Vendor Address","Blocked Vendor","Work Order Validity From","Work Order Validity To","Work Order Type","Plant Code","Section Code","Department Code","G/L Code","Cost Center","Nature of Job","Rate/Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed","Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company Code"];

        case "data-contractor":
            return ["Work Order Number", "Plant Code", "Organisation", "Main Contractor Code","Contractor Code", "Contractor Name", "Contractor Address","City", "Contractor Manager Name", "Total Workmen Strength","Maximum Number of Workmen", "Labour License Number", "License Valid From","License Valid To", "License Coverage","WC Number", "WC Valid From", "WC Valid To", "WC Coverage","ESIC Number", "ESIC Valid From", "Nature of Work", "PF Number","PF Apply Date"];

        case "data-principal employer":
            return ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability", "Is MW Applicability", "License Number", "PF Code", "ESWC", "Factory License Number","State"];
        
        case "data-workmen bulk upload":
            return ["First Name*", "Last Name*", "Father's Name or Husband's Name*", "Date of Birth*", "Trade*","Skill*","Nature of Work*", "Hazardous Area*", "Aadhar/Id Proof Number*", "Vendor Code*", "Gender*", "Date of Joining", "Department*", "Area", "Work Order Number*","PF A/C Number","Marital Status*","Technical/Non Technical*","Academic","Blood Group","Accommodation*","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number*","Police Verification Date","Health Chekup Date","Access Levels*","ESIC Number","Unit Code*","Organization Name","EIC Number*","EC Number*","UAN Number","Emergency Contact Person*","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark*"];

        case "data-workmen bulk upload draft":
            return ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id Proof Number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical/Non Technical","Academic","Blood Group","Accommodation","Bank Branch Name","Account Number","Mobile Number","Emergency Contact Number","Police Verification Date","Health Chekup Date","Access Levels","ESIC Number","Unit Code","Organization Name","EIC Number","EC Number","UAN Number","Emergency Contact Person","Is Eligible for PF","SpecializationName","Insurance Type","LL Number","Address","Zone","IdMark"];

        case "data-trade skill":
            return ["Plant Code", "Trade", "Skill"];

        case "data-department area":
            return ["Plant Code", "Department", "Sub Department"];

        default:
            return [];
    }
}

   function fileUploadTemplateSideBar() {
        $("#sidebar").css("width", "300px");
    }
    // ✅ Delegated close handler (IMPORTANT)
    $(document).on("click", "#closeSidebar", function () {
        $("#sidebar").css("width", "0");
    });
    
    
// SELECT-ALL — works for dynamically added rows too
  document.addEventListener("change", function (e) {

    if (e.target.id === "selectAll") {

        const checked = e.target.checked;

        document
            .querySelectorAll("#tableBody .rowCheck")
            .forEach(cb => cb.checked = checked);
    }
});

function deleteSelectedRows() {

    const rows = document.querySelectorAll("#tableBody tr");
    rows.forEach(tr => {
        const cb = tr.querySelector(".rowCheck");
        if (cb && cb.checked) {
            tr.remove();
            tableEdited = true;
        }
    });

    // reset select-all checkbox
    const selectAll = document.getElementById("selectAll");
    if (selectAll) selectAll.checked = false;
}

function insertRow() {

    const table = document.getElementById("viewtable");
    const tbody = document.getElementById("tableBody");
    const totalCols = table.querySelectorAll("thead tr th").length;
    const tr = document.createElement("tr");

    // checkbox cell
    const chkTd = document.createElement("td");
    const chk = document.createElement("input");
    chk.type = "checkbox";
     chk.style.border = "1px solid #ddd";
    //chk.style.textAlign = "center";
    chk.className = "rowCheck";
    chkTd.appendChild(chk);
    tr.appendChild(chkTd);

    // editable cells
    for (let i = 1; i < totalCols; i++) {
        const td = document.createElement("td");
        td.contentEditable = "true";
        td.style.border = "1px solid #ddd";
        td.style.padding = "8px";
        tr.appendChild(td);
    }

    tbody.appendChild(tr);
}
