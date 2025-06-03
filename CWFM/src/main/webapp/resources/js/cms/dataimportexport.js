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
      
        
        
        