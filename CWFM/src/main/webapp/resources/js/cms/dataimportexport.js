 /*function uploadCSV() {
        var formData = new FormData();
        var fileInput = document.getElementById("csvFile");
        
        if (fileInput.files.length === 0) {
            alert("Please select a file to upload.");
            return;
        }

        formData.append("files", fileInput.files[0]);

        fetch("/CWFM/fileuploader/uploadfile", {
            method: "POST",
            body: formData
        })
        .then(response => response.text())
        .then(data => {
        	 alert("file uploaded  successfully!");
          //  alert(data); // Show success message
            loadCommonList('/fileuploader/upload', 'File Upload'); 
            //document.getElementById("csvFile").value = ""; // Clear file input
        })
        .catch(error => console.error("Error uploading file:", error));
    } */ 
     /*function openModal() {
            document.getElementById("myModal").style.display = "block";
        }

        function closeModal() {
            document.getElementById("myModal").style.display = "none";
        }

        // Close the modal when clicking outside of it
        window.onclick = function(event) {
            var modal = document.getElementById("myModal");
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }*/
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
    container.innerHTML = "";

    // 🔹 Validate input
    if (!Array.isArray(errorData) || errorData.length === 0) {
        container.textContent = "✅ No errors found in the uploaded file.";
        return;
    }

    // 🔹 Filter valid structured errors only
    const validErrors = errorData.filter(
        err =>
            err &&
            typeof err === "object" &&
            (err.error || (err.fieldErrors && Object.keys(err.fieldErrors).length > 0))
    );

    // 🔹 If no valid errors, show success and exit
    if (validErrors.length === 0) {
        container.textContent = "✅ No errors found in the uploaded file.";
        return;
    }

    // ✅ Show Download Error Report button only if real errors exist
    const downloadBtn = document.createElement("a");
    downloadBtn.textContent = "⬇️ Download Error Report";
    downloadBtn.href = "#";
    downloadBtn.style.float = "right";
    downloadBtn.style.marginBottom = "8px";
    downloadBtn.style.color = "#0066cc";
    downloadBtn.style.fontWeight = "bold";
    downloadBtn.style.textDecoration = "underline";

    downloadBtn.addEventListener("click", function (e) {
        e.preventDefault();
        downloadErrorCSV(validErrors, uploadedFileName);
    });

    container.appendChild(downloadBtn);

    // 🧾 Create the error table
    const table = document.createElement("table");
    table.classList.add("table", "table-bordered");
    table.style.marginTop = "10px";
    table.style.borderCollapse = "collapse";
    table.style.width = "100%";

    const headerRow = document.createElement("tr");
    ["Row", "Field", "Error Message"].forEach(text => {
        const th = document.createElement("th");
        th.innerText = text;
        th.style.border = "1px solid #999";
        th.style.padding = "6px";
       // th.style.backgroundColor = "#ffe6e6";
        th.style.color = "#cc0000";
        headerRow.appendChild(th);
    });
    table.appendChild(headerRow);

    validErrors.forEach(error => {
        const rowNum = error.row || "-";

        if (error.fieldErrors && typeof error.fieldErrors === "object") {
            Object.entries(error.fieldErrors).forEach(([field, message]) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td style="border:1px solid #999;padding:6px;">${rowNum}</td>
                    <td style="border:1px solid #999;padding:6px;">${field}</td>
                    <td style="border:1px solid #999;padding:6px;color:red;">${message}</td>
                `;
                table.appendChild(row);
            });
        } else if (error.error) {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td style="border:1px solid #999;padding:6px;">${rowNum}</td>
                <td style="border:1px solid #999;padding:6px;">-</td>
                <td style="border:1px solid #999;padding:6px;color:red;">${error.error}</td>
            `;
            table.appendChild(row);
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

    // 🧠 Get base name safely
    const baseName = uploadedFileName
        ? uploadedFileName.replace(/^error_/, "").replace(/\.[^/.]+$/, "")
        : "upload";

    const fileName = `error_${baseName}.csv`;

    // 📥 Download logic
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = fileName;
    a.click();
    URL.revokeObjectURL(url);
}

   /*  function fetchTemplateOptions() {
        var dropdown = document.getElementById("templateType");
        var selectedTemplate = dropdown.value;

        if (selectedTemplate) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/CWFM/data/getTemplateOptions?selectedTemplate=" + selectedTemplate, true);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);

                    document.getElementById("templateOptions").style.display = "block";
                    document.getElementById("templateMessage").innerText = "What would you like to do with this template?";
                    document.getElementById("infoTemplate").href = response.infoUrl;
                    document.getElementById("viewTemplate").href = response.viewUrl;
                    document.getElementById("downloadTemplate").href = response.downloadUrl;
                }
            };

            xhr.send();
        } else {
            document.getElementById("templateOptions").style.display = "none";
        }
    } */
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

            case "Data-work order":
                headers = ["Work Order Number", "Item", "Line", "Line Number", "Service Code", "Short Text", "Delivery Completion", "Item Changed On", "Vendor Code", "Vendor Name",
                    "Vendor Address", "Blocked Vendor", "Work Order Validity From", "Work Order Validity To", "Work Order Type", "Plant Code", "Section Code", "Department Code",
                    "G/L Code", "Cost Center", "Nature of Job", "Rate / Unit", "Quantity", "Base Unit of Measure", "Work Order Released", "PM Order No", "WBS Element", "Qty Completed",
                    "Work Order Release Date", "Service Entry Created Date", "Service Entry Updated Date", "Purchase Org Level", "Company Code"];
                break;

            case "data-contractor":
                headers = ["Contractor Name", "Contractor Address", "City", "Plant Code", "Contractor Manager Name", "License Num", "License Valid From", "License Valid To",
                    "License Coverage", "Total Strength", "Maximum Number of Workmen", "Nature of Work", "Location of Work", "Contractor Validity Start Date", "Contractor Validity End Date",
                    "Contractor Id", "PF Code", "EC/WC Number", "EC/WC Validity Start Date", "EC/WC Validity End Date", "Coverage", "PF Number", "PF Apply Date", "Reference", "Mobile Number",
                    "ESI Number", "ESI Valid From", "ESI Valid To", "Organisation", "Main Contractor Code", "Work Order Number"];
                break;

            case "data-principal employer":
                headers = ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability",
                    "Is MW Applicability", "License Number", "PF Code", "ESWC Number", "Factory License Number", "State"];
                break;

            case "data-workmen bulk upload":
                headers = ["First Name", "Last Name", "Father's/Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id Proof Number",
                    "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number", "PF A/C Number", "Marital Status", "Technical/Non Technical", "Academic",
                    "Blood Group", "Accommodation", "Bank Name", "Account Number", "Mobile Number", "Emergency Contact Number", "Police Verification Valid To", "Health Checkup Date",
                    "Access Levels", "ESIC Number", "Unit Code", "Organization Name", "EIC Number", "EC Number", "UAN Number", "Emergency Contact Person",
                    "Is Eligible for PF", "Specialization Name", "Insurance Type", "LL Number", "Address", "Zone", "Id Mark"];
                break;

            case "data-workmen bulk upload draft":
                headers = ["First Name", "Last Name", "Father's/Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id Proof Number",
                    "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number", "PF A/C Number", "Marital Status", "Technical/Non Technical", "Academic",
                    "Blood Group", "Accommodation", "Bank Name", "Account Number", "Mobile Number", "Emergency Contact Number", "Police Verification Valid To", "Health Checkup Date",
                    "Access Levels", "ESIC Number", "Unit Code", "Organization Name", "EIC Number", "EC Number", "UAN Number", "Emergency Contact Person",
                    "Is Eligible for PF", "Specialization Name", "Insurance Type", "LL Number", "Address", "Zone", "Id Mark"];
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

        // Create table headers
        headers.forEach(header => {
            const th = document.createElement("th");
            th.style.border = "1px solid #ddd";
            th.style.padding = "8px";
            th.textContent = header;
            tableHeaderRow.appendChild(th);
        });

        // Create sample rows (5 empty)
        for (let i = 0; i < 5; i++) {
            const tr = document.createElement("tr");
            headers.forEach(() => {
                const td = document.createElement("td");
                td.style.border = "1px solid #ddd";
                td.style.padding = "8px";
                td.textContent = "";
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
   
    /* function cancelButton() {
        // Hide all other sections (viewTemplate, sidebar, etc.)
        document.getElementById("viewTemplateContainer").style.display = "none";
        document.getElementById("fileUploadSidebar").style.width = "0";

        // Show the initial section
        document.querySelector(".button-container").style.display = "block";
    } */

   



   /*  function submitFile() {
        var fileInput = document.getElementById("fileInput");
        
        if (fileInput.files.length === 0) {
            alert("Please choose a file before uploading.");
        } else {
            alert("File uploaded successfully!");
            closeFileSidebar(); // Close sidebar after upload
        }
    } */

    
    function uploadTemplateFile() {
        const fileInput = document.getElementById("fileInput");
        const uploadedFileName = fileInput.files[0]?.name || "upload.csv";

        const templateType = document.getElementById("templateType").value;

        if (!fileInput.files[0] || !templateType) {
            alert("Please select a template and a file.");
            return;
        }

        const formData = new FormData();
        formData.append("file", fileInput.files[0]);
        formData.append("templateType", templateType);

        fetch("/CWFM/data/uploadTemplate", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(result => {
            console.log("Server Response:", result);

            const messageDiv = document.getElementById("uploadMessage");
            messageDiv.innerHTML = "";
            messageDiv.style.display = "block";

            let messageColor = "black";
            if (result.status === "success") {
                messageColor = "green";
            } else if (result.status === "partial") {
                messageColor = "orange";
            } else {
                messageColor = "red";
            }

            messageDiv.innerText = result.message || "Unknown result.";
            messageDiv.style.color = messageColor;

            // Automatically hide message after 5 seconds
            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 5000);

            // Clear previous error display
            document.getElementById("errorContainer").innerHTML = "";

            const successData = result.data?.successData ?? [];
            const errorData = result.data?.errorData ?? [];
            let anyDataProcessed = false;

            if (successData.length > 0) {
                renderUploadedData(successData, templateType);
                anyDataProcessed = true;
            }

            if (errorData.length > 0) {
                renderErrors(errorData,uploadedFileName);
                anyDataProcessed = true;
            }

            closeFileSidebar();
            fileInput.value = "";
        })
        .catch(err => {
            console.error("Upload error", err);
            const messageDiv = document.getElementById("uploadMessage");
            messageDiv.innerText = "Something went wrong during file upload.";
            messageDiv.style.color = "red";
            messageDiv.style.display = "block";

            // Automatically hide error after 5 seconds
            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 5000);
        });
    }





  


    function renderUploadedData(data, templateType) {
        const tableBody = document.getElementById("tableBody");
        const tableHeaderRow = document.getElementById("tableHeaderRow");
        tableBody.innerHTML = "";
        tableHeaderRow.innerHTML = "";

        if (!data || data.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 10;
            td.textContent = "No data found.";
            tr.appendChild(td);
            tableBody.appendChild(tr);
            return;
        }

        let headers = [];
        let fieldMap = [];

        if (templateType === "Data-General Master") {
            headers = ["GM Name", "GM Description", "GM TypeID"];
            fieldMap = ["gmName", "gmDescription", "gmTypeId"];
        }
        else if (templateType === "Data-minimumWage") {
            headers = ["Trade", "Skill", "Basic", "Da", "Allowance", "From Date", "Unit Code", "Organization"];
            fieldMap = ["trade", "skill", "basic", "da", "allowamce", "fromDate", "unitCode", "organization"];
        }
        else if (templateType === "Data-Work Order") {
            headers = ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed ON","Vendor Code","Vendor Name","Vendor Address","Blocked Vendor","Work Order Validitiy From","Work Order Validitiy To","Work Order Type","Plant code","Section Code","Department Code","G/L Code","Cost Center","Nature of Job","Rate / Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed","Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company_code"];
            fieldMap = ["workOrderNumber", "item", "line", "lineNumber", "serviceCode", "shortText", "deliveryCompletion","itemChangedON", "vendorCode", "vendorName", "vendorAddress", "blockedVendor","workOrderValiditiyFrom", "workOrderValiditiyTo", "workOrderType", "plantcode", "sectionCode","departmentCode", "GLCode", "costCenter", "natureofJob", "rateUnit", "quantity", "baseUnitofMeasure","workOrderReleased", "PMOrderNo", "WBSElement", "qtyCompleted", "workOrderReleaseDate","serviceEntryCreatedDate", "serviceEntryUpdatedDate", "purchaseOrgLevel",  "companycode"];
        } 
        else if (templateType === "Data-Contractor") {
            headers = ["CONTRACTOR NAME", "CONTRACTOR ADDRESS", "City", "Contractor MANAGER NAME", "LICENSE NUM", "LICENCSE VALID FROM", "LICENCSE VALID TO", "LICENCSE COVERAGE", "TOTAL STRENGTH", "MAXIMUM NUMBER OF WORKMEN", "NATURE OF WORK", "LOCATION OF WORK", "CONTRACTOR VALIDITY START DATE", "CONTRACTOR VALIDITY END DATE", "CONTRACTOR ID", "PF CODE", "EC/WC number", "EC/WC Validity Start Date", "EC/WC Validity End Date", "Coverage", "PF NUMBER", "PF APPLY DATE", "Reference", "Mobile Number", "ESI NUMBER", "ESI VALID FROM", "ESI VALID TO", "Main Contractor Code", "Work Order Number"];
            fieldMap = ["contractorName", "contractorAddress", "city", "managerNm", "licenseNumber", "licenseValidFrom", "licenseValidTo", "coverage", "totalStrength", "maxNoEmp", "natureofWork", "locationofWork", "periodStartDt", "periodEndDt", "contractorId", "pfCode", "wcCode", "wcFromDtm", "wcToDtm", "wcTotal", "pfNum", "pfApplyDt", "reference", "mobileNumber", "esiwc", "esiValidFrom", "esiValidTo", "contractorCode", "workOrderNumber"];
        } 
        else if (templateType === "Data-Principal Employer") {
            headers = ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability", "Is MW Applicability", "License Number", "PF Code", "ESWC", "Factory License Number","State"];
            fieldMap = ["organization", "code", "name", "address", "managerName", "managerAddrs", "businessType", "maxWorkmen", "maxCntrWorkmen", "bocwApplicability", "isMwApplicability", "licenseNumber", "pfCode", "wcNumber", "factoryLicenseNumber","stateNM"];
        }
        else if (templateType === "Data-Workmen Bulk Upload" || templateType === "Data-Workmen Bulk Upload Draft") {
            headers = ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id proof number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical","Academic","Blood Group","Accommodation","Bank Name Branch","Account Number","Mobile Number","Emergency Contact Number","Police verification Date","Health chekup Date","Access Levels","ESIC Number","UNIT CODE","Organization name","EIC Number","EC number","UAN Number","Emergency Contact Person","Is eligible for PF","SpecializationName","Insurance Type","LL number","Address","Zone","IdMark"];
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

        headers.forEach(header => {
            const th = document.createElement("th");
            th.style.border = "1px solid grey";
            th.style.padding = "8px";
            th.textContent = header;
            tableHeaderRow.appendChild(th);
        });

        const safe = val => (val !== null && val !== undefined ? val : "");

        data.forEach(row => {
            const tr = document.createElement("tr");
            fieldMap.forEach(field => {
                const td = document.createElement("td");
                td.style.border = "1px solid grey";
                td.style.padding = "8px";
                td.textContent = safe(row[field]);
                tr.appendChild(td);
            });
            tableBody.appendChild(tr);
        });
    }       
