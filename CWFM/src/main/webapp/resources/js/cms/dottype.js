function saveDotSelection() {
    const principalEmployerId = document.getElementById("principalEmployer").value;
    const businessTypeId = document.getElementById("businessType").value;
    const dotType = document.querySelector('input[name="dotType"]:checked')?.value;

    if (!principalEmployerId || !businessTypeId || !dotType) {
        alert("Please select Principal Employer, Business Type, and Dot Type.");
        return;
    }

    const formData = new URLSearchParams();
    formData.append("principalEmployerId", principalEmployerId);
    formData.append("businessTypeId", businessTypeId);
    formData.append("dotType", dotType);

    fetch("/CWFM/dottype/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData
    })
    .then(async response => {
    const text = await response.text();
    if (!response.ok) {
        throw new Error(text);
    }

    // ✅ Show message in green
    const box = document.getElementById("messageBox");
    const msg = document.getElementById("messageText");
    msg.textContent = text;
    //msg.style.backgroundColor = "#d4edda";  // light green
    msg.style.color = "green";            // dark green text
   // msg.style.border = "1px solid #c3e6cb"; // border
    box.style.display = "block";

    // Auto-hide after 5 seconds
    setTimeout(() => {
        box.style.display = "none";
    }, 5000);

    // ✅ Optional: clear form after success
    document.getElementById("principalEmployer").value = "";
    document.getElementById("businessType").innerHTML = '<option value="">Please select Business Type</option>';
    document.querySelectorAll('input[name="dotType"]').forEach(el => el.checked = false);
})

   .catch(error => {
    const box = document.getElementById("messageBox");
    const msg = document.getElementById("messageText");
    msg.textContent = error.message;
    //msg.style.backgroundColor = "#f8d7da";  // light red
    msg.style.color = "red";            // dark red
    //msg.style.border = "1px solid #f5c6cb";
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 5000);
});

}
