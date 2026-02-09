<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <%@ page isELIgnored="false" %>
            <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
                        <!DOCTYPE html>
                        <html>

                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <link rel="stylesheet"
                                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                            <!-- <script src="resources/js/cms/principalEmployer.js"></script>
                            <script src="resources/js/cms/contractor.js"></script>
                            <script src="resources/js/cms/workmen.js"></script> -->
                            <title>Requester Form</title>
                            <style>
                                body {font-family: Arial; margin:0; background:#f5f5f5;}
.container {max-width:800px;margin:auto;background:#fff;padding:20px;border-radius:6px;}

table {width:100%;border-spacing:10px;}
th {text-align:left;width:200px;}
.required-field{color:red}

#preview{width:200px;height:200px;border:1px solid #ccc;display:flex;align-items:center;justify-content:center;}
#preview img{max-width:100%;max-height:100%;}

video{width:200px;border:1px solid #ccc;margin-top:10px;}
button{padding:6px 12px;border:none;border-radius:4px;cursor:pointer}
.btn-primary{background:#007bff;color:white}
.btn-success{background:#28a745;color:white}
.btn-danger{background:#dc3545;color:white}

.loader{display:none;text-align:center;margin-top:15px}
.spinner{border:4px solid #f3f3f3;border-top:4px solid #007bff;border-radius:50%;width:30px;height:30px;animation:spin 1s linear infinite;margin:auto}
@keyframes spin{100%{transform:rotate(360deg)}}

@media(max-width:768px){
table,tbody,tr,th,td{display:block;width:100%}
th{margin-top:10px}
}
                                body {
                                    margin: 0;
                                    overflow-x: hidden;
                                    font-family: 'Noto Sans', sans-serif;
                                }

                                #principalEmployerContent {
                                    padding: 20px;
                                    box-sizing: border-box;
                                    overflow-y: auto;
                                    height: calc(100vh - 60px);
                                    /* Adjust based on header height */
                                }

                                .page-header {
                                    background-color: #005151;
                                    color: #fff;
                                    padding: 15px;
                                    text-align: center;
                                    font-size: 24px;
                                    font-family: 'Noto Sans', sans-serif;
                                    position: fixed;
                                    width: 100%;
                                    top: 0;
                                    left: 0;
                                    z-index: 1000;
                                }

                                .header-buttons {
                                    float: right;
                                    margin-right: 20px;
                                }

                                .tabs {
                                    overflow: hidden;
                                    border-bottom: 2px solid #005151;
                                    margin-bottom: 20px;
                                }

                                .tabs button {
                                    background-color: #fff;
                                    /* Tab background color */
                                    border: 2px solid #005151;
                                    /* Tab border color */
                                    outline: none;
                                    padding: 10px 20px;
                                    /* Reduced height */
                                    cursor: pointer;
                                    font-size: 17px;
                                    transition: background-color 0.3s, color 0.3s;
                                    color: #005151;
                                    /* Tab text color */
                                    font-family: 'Noto Sans', sans-serif;
                                }

                                .tabs button.active {
                                    background-color: #005151;
                                    /* Active tab background color */
                                    color: #fff;
                                    /* Active tab text color */
                                    border-bottom: 2px solid #fff;
                                }

                                .tab-content {
                                    display: none;
                                    padding: 10px;
                                    background-color: #f9f9f9;
                                    border: 1px solid #ddd;
                                }

                                .tab-content.active {
                                    display: block;
                                }

                                .custom-label {
                                    font-family: 'Noto Sans', sans-serif;
                                    text-align: left;
                                    display: block;
                                    margin-bottom: 5px;
                                    color: #898989;
                                    /* Label text color */
                                    display: inline;
                                    padding: .2em .6em .3em;
                                    font-size: 85%;
                                    font-weight: 700;
                                    line-height: 1;
                                    white-space: nowrap;
                                    vertical-align: baseline;
                                    border-radius: .25em;
                                }

                                .custom-input-container {
                                    padding-left: 10px;
                                }

                                .custom-input,
                                .custom-input-checkbox {
                                    height: 40px;
                                    font-family: 'Noto Sans', sans-serif;
                                    width: 100%;
                                    box-sizing: border-box;
                                }

                                .required-field {
                                    color: red;
                                    margin-right: 4px;
                                }



                                table.ControlLayout {
                                    border-collapse: separate;
                                    /* Ensure spacing is applied correctly */
                                    border-spacing: 10px;
                                    /* Adjust the value for the desired gap between cells */
                                }

                                table.ControlLayout th,
                                table.ControlLayout td {
                                    padding: 10px;
                                    /* Add padding inside cells for spacing around content */
                                    vertical-align: top;
                                    /* Align the content to the top of the cell */
                                }

                                body {
                                    margin: 0;
                                    overflow-x: hidden;
                                }

                                #principalEmployerContent {
                                    padding: 20px;
                                    box-sizing: border-box;
                                    overflow-y: auto;
                                    height: calc(100vh - 20px);
                                }

                                th label {
                                    text-align: left;
                                    display: block;
                                    font-weight: normal;
                                }

                                .custom-label {
                                    font-family: Arial, sans-serif;
                                    text-align: left;
                                    display: block;
                                }

                                .required-field {
                                    color: red;
                                    margin-right: 4px;
                                }

                                .page-header {
                                    background-color: #005151;
                                    color: #fff;
                                    padding: 10px;
                                    text-align: center;
                                    font-size: 20px;
                                }

                                .header-buttons {
                                    float: right;
                                    margin-right: 10px;
                                }

                                .tabs {
                                    display: flex;
                                    margin-bottom: 10px;
                                }

                                .tabs button {
                                    padding: 5px 10px;
                                    /* Adjust padding to decrease size */
                                    font-size: 12px;
                                    /* Decrease font size */
                                    margin-right: 5px;
                                    /* Space between tabs */
                                    border: 1px solid #ddd;
                                    /* Optional: add a border for visibility */
                                    border-radius: 3px;
                                    /* Optional: rounded corners */
                                }

                                .tabs button.active {
                                    background-color: #005151;
                                    color: white;
                                    border-bottom: 2px solid #005151;
                                }

                                .tab-content {
                                    display: none;
                                    padding: 20px;
                                    background-color: white;
                                    border: 1px solid #ccc;
                                }

                                .tab-content.active {
                                    display: block;
                                }

                                .custom-select {
                                    display: inline-block;
                                    width: 100%;
                                    /* Reduced the width to 50%, adjust as needed */
                                    height: 25px;
                                    padding: 5px;
                                    font-size: 12px;
                                    font-family: Arial, sans-serif;
                                    line-height: 1.5;
                                    color: #495057;
                                    background-color: #fff;
                                    background-clip: padding-box;
                                    border: 1px solid #ced4da;
                                    border-radius: 0.25rem;
                                    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
                                }

                                .custom-select:focus {
                                    border-color: #80bdff;
                                    outline: 0;
                                    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
                                }

                                .image-container {
                                    text-align: center;
                                    padding: 10px;
                                }

                                .image-container img {
                                    margin: 10px;
                                    cursor: pointer;
                                }


                                .tabs-container {
                                    display: flex;
                                    justify-content: space-between;
                                    /* Distribute space between tabs and buttons */
                                    align-items: center;
                                    /* Align items vertically */
                                }

                                .tabs {
                                    display: flex;
                                    flex-wrap: nowrap;
                                    /* Prevent wrapping of tabs */
                                }

                                .loader {
                                    display: none;
                                    text-align: center;
                                    margin-top: 15px
                                }

                                .tabs button {
                                    margin-right: 10px;
                                    /* Space between tabs */
                                }

                                .action-buttons {
                                    display: flex;
                                    /* Align buttons horizontally */
                                    align-items: center;
                                    /* Center buttons vertically */
                                }

                                .action-buttons button {
                                    margin-left: 10px;
                                    /* Space between buttons */
                                }

                                video {
                                    width: 200px;
                                    border: 1px solid #ccc;
                                    margin-top: 10px;
                                }

                                .loader {
                                    display: none;
                                    text-align: center;
                                    margin-top: 15px
                                }

                                .spinner {
                                    border: 4px solid #f3f3f3;
                                    border-top: 4px solid #007bff;
                                    border-radius: 50%;
                                    width: 30px;
                                    height: 30px;
                                    animation: spin 1s linear infinite;
                                    margin: auto
                                }

                                @keyframes spin {
                                    100% {
                                        transform: rotate(360deg)
                                    }
                                }

                                .custom-mb {
                                    margin-bottom: 2.5rem;
                                    /* Example custom margin */
                                }

                                .custom-radio {
                                    margin-right: 5px;
                                    /* Adjust the spacing between the radio button and the label */
                                    vertical-align: middle;
                                    /* Align the radio button with the label text */
                                }

                                .custom-label-inline {
                                    display: inline-block;
                                    vertical-align: middle;
                                    /* Align the label text with the radio button */
                                    margin-left: 3px;
                                    /* Adjust this value to control the space between the radio button and the label */

                                }

                                td {
                                    padding: 5px;
                                    /* Add padding to cells for spacing */
                                }

                                input[type="radio"] {
                                    margin-right: 5px;
                                    /* Space between radio button and label */
                                    vertical-align: middle;
                                    /* Align radio button with label text */
                                }

                                label {
                                    vertical-align: middle;
                                    /* Align label text with radio button */
                                    display: inline-block;
                                    /* Ensure label appears on the same line as radio button */
                                    color: #495057;
                                    /* Set the text color to a dark shade */
                                    font-family: Arial, sans-serif;
                                }

                                /* #preview {
            width: 200px;
            height: 200px;
            border: 1px solid #ddd;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        } */
                                /* #preview {
                                    width: 200px;
                                    height: 200px;
                                    border: 1px solid #ccc;
                                    display: flex;
                                    align-items: center;
                                    justify-content: center;
                                }

                                #preview img {
                                    max-width: 100%;
                                    max-height: 100%;
                                } */

                                .error-label {
                                    color: #d32f2f;
                                    display: none;
                                    font-size: 12px;
                                    margin-top: 4px;
                                    font-weight: 500;
                                }

                                .file-upload {
                                    position: relative;
                                    display: block;
                                    width: 100%;
                                }

                                .file-upload input[type="file"] {
                                    padding: 8px 10px;
                                    border: 1px solid #999;
                                    border-radius: 4px;
                                    width: 100%;
                                    box-sizing: border-box;
                                    background-color: #fff;
                                    font-size: 13px;
                                    cursor: pointer;
                                }

                                .auto-generated-info {
                                    background-color: #f5f5f5;
                                    border: 1px solid #ddd;
                                    border-radius: 4px;
                                    padding: 12px;
                                }

                                .auto-generated-info .custom-textarea {
                                    background-color: #fff;
                                    border: 1px solid #ddd;
                                    margin-bottom: 8px;
                                }

                                .btn-primary {
                                    background: #007bff;
                                    color: white
                                }

                                .btn-success {
                                    background: #28a745;
                                    color: white
                                }

                                .btn-danger {
                                    background: #dc3545;
                                    color: white
                                }
                            </style>


                        </head>

                        <body>

                            <div id="principalEmployerContent">
                                <div class="tabs-container">
                                    <div class="tabs">
                                        <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic
                                            Information</button>

                                    </div>
                                    <div class="action-buttons">

                                        <button id="saveButton" type="submit"
                                            class="btn btn-default process-footer-button-cancel ng-binding"
                                            onclick="saveRequester(); return false;">
                                            Save
                                        </button>

                                        <button type="button"
                                            class="btn btn-default process-footer-button-cancel ng-binding"
                                            onclick="cancelForm()">
                                            Cancel
                                        </button>

                                    </div>
                                </div>
                                <div id="tab1" class="tab-content active">
                                    <form id="registrationForm">

                                        <table>
                                            <tr>
                                                <th><span class="required-field">*</span> Username</th>
                                                <td><input type="text" id="username" required></td>
                                            </tr>

                                            <tr>
                                                <th><span class="required-field">*</span> Face Photo</th>
                                                <td>

                                                    <div id="preview">No Image</div>

                                                    <div style="margin-top:10px;display:flex;gap:10px;flex-wrap:wrap;">

                                                        <input type="file" id="mobileCameraInput" accept="image/*"
                                                            capture="environment" style="display:none"
                                                            onchange="previewImage(event)">
                                                        <button type="button" class="btn-primary"
                                                            onclick="openCamera()">Use Camera</button>
                                                    </div>

                                                    <video id="webcam" autoplay playsinline
                                                        style="display:none"></video>

                                                    <div id="cameraButtons" style="display:none;margin-top:8px;">
                                                        <button type="button" class="btn-success"
                                                            onclick="captureImage()">Capture</button>
                                                        <button type="button" class="btn-danger"
                                                            onclick="closeCamera()">Cancel</button>
                                                    </div>

                                                    <canvas id="canvas" style="display:none"></canvas>

                                                </td>
                                            </tr>

                                            <tr>
                                                <th></th>
                                                <td>
                                                    <button type="submit" class="btn-success">Register Face</button>
                                                </td>
                                            </tr>
                                        </table>

                                    </form>



                                </div>

                            </div>
                            <div class="loader" id="loader">
                                <div class="spinner"></div>
                                <p>Registering...</p>
                            </div>
                            <script>
                                let cameraStream = null;

                                /* IMAGE PREVIEW */
                                function previewImage(event) {
                                    const file = event.target.files[0];
                                    if (!file) return;
                                    const reader = new FileReader();
                                    reader.onload = e => {
                                        document.getElementById("preview").innerHTML =
                                            `<img src="${e.target.result}">`;
                                    };
                                    reader.readAsDataURL(file);
                                }

                                /* MOBILE CHECK */
                                function isMobile() {
                                    return /Android|iPhone|iPad/i.test(navigator.userAgent);
                                }

                                /* OPEN CAMERA */
                                function openCamera() {
                                    if (isMobile()) {
                                        document.getElementById("mobileCameraInput").click();
                                        return;
                                    }
                                    navigator.mediaDevices.getUserMedia({ video: true })
                                        .then(stream => {
                                            cameraStream = stream;
                                            const video = document.getElementById("webcam");
                                            video.srcObject = stream;
                                            video.style.display = "block";
                                            document.getElementById("cameraButtons").style.display = "block";
                                        })
                                        .catch(() => alert("Camera access denied"));
                                }

                                /* CAPTURE IMAGE */
                                function captureImage() {
                                    const video = document.getElementById("webcam");
                                    const canvas = document.getElementById("canvas");
                                    const ctx = canvas.getContext("2d");

                                    canvas.width = video.videoWidth;
                                    canvas.height = video.videoHeight;
                                    ctx.drawImage(video, 0, 0);

                                    canvas.toBlob(blob => {
                                        const file = new File([blob], "face.jpg", { type: "image/jpeg" });
                                        const dt = new DataTransfer();
                                        dt.items.add(file);
                                        // document.getElementById("imageFile").files=dt.files;
                                        previewImage({ target: { files: dt.files } });
                                        closeCamera();
                                    });
                                }

                                /* CLOSE CAMERA */
                                function closeCamera() {
                                    if (cameraStream) {
                                        cameraStream.getTracks().forEach(t => t.stop());
                                        cameraStream = null;
                                    }
                                    document.getElementById("webcam").style.display = "none";
                                    document.getElementById("cameraButtons").style.display = "none";
                                }
                                function saveRequester() {
                                    document.getElementById("registrationForm").submit();
                                }

                                function cancelForm() {
                                    document.getElementById("registrationForm").reset();
                                    document.getElementById("preview").innerHTML = "";
                                }

                                /* FORM SUBMIT */
                                document.getElementById("registrationForm").addEventListener("submit", async function (e) {
                                    e.preventDefault();

                                    const username = document.getElementById("username").value;
                                    const file = document.getElementById("imageFile").files[0];

                                    if (!username || !file) {
                                        alert("Username and Photo required");
                                        return;
                                    }

                                    document.getElementById("loader").style.display = "block";

                                    const formData = new FormData();
                                    formData.append("registerFace", JSON.stringify({ userId: username }));
                                    formData.append("imageFile", file);

                                    try {
                                        const res = await fetch("http://localhost:8080/CWFM/faced/register", { method: "POST", body: formData });
                                        const text = await res.text();
                                        alert(text);
                                        document.getElementById("registrationForm").reset();
                                        document.getElementById("preview").innerHTML = "No Image";
                                    } catch (err) {
                                        alert("Error: " + err.message);
                                    }

                                    document.getElementById("loader").style.display = "none";
                                });
                            </script>

                        </body>

                        </html>