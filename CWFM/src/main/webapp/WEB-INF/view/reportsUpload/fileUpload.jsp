<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Data Import/Export</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
     <script src="resources/js/cms/dataimportexport.js"></script>
    <style>
        /* Styling for arrows and text button */
        .arrow-container, .button-container {
            text-align: left;
            margin: auto;
            margin-left: 20px; 
        }
        .arrow-container {
            text-align: left;
            margin: auto;
            margin-left: 85px;
             font-weight: bold;
        }

        .arrow, .button {
         font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
            font-size: 17px;
            cursor: pointer;
            text-decoration: none;
            color: black;
            display: inline-block;
            margin-right: -17px;
            font-weight:500;;
            padding: 4px 5px;
            border: none;
            background: none;
            color: darkcyan; 
            font-size: 23px; /* Adjust icon size */
            margin-bottom: -14px;
    /*  font-weight: bold; */
        }

        .arrow:hover, .button:hover {
            color: darkgray;
        }

        /* Right Sidebar */
        .sidebar {
            height: 100%;
            width: 0;
            position: fixed;
            top: 0;
            right: 0;
            background-color: #fff;
            transition: width 0.5s ease-in-out;
            color: white;
            overflow: hidden;
            border-radius:40px;
        }

       .sidebar-content {
    /*  background:  #f8f8ff; */ /* White background */
    background:  #fff; 
    width: 280px; /* Adjusted width */
    height: 85%; /* Height adjusts based on content */
    position: absolute;
    right: 30px; /* Added gap from the right */
    top: 75px; /* Added gap from the top */
    padding: 15px; /* Adjusted padding */
    box-shadow: -2px 0 5px rgba(0, 0, 0, 0.3); /* Softer shadow */
    border-radius: 8px; /* Smooth edges */
}

        /* Close Button */
        .close-btn {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 30px;
            background: none;
            border: none;
            color: #007bff;
            cursor: pointer;
        }

        /* Import Button */
        /* .import-button {
            padding: 10px;
            background: darkcyan;
            border: none;
            color: white;
            cursor: pointer;
            margin-top: 20px;
            
        }

        .import-button:hover {
            background: #45a049;
        } */
         .hidden {
            display: none;
        }
        .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.5);
    }

    .modal-content {
        background-color: #f8f8ff;
        margin: 10% auto;
        padding: 20px;
        border-radius: 8px;
        width: 50%;
        overflow-y:auto;
        height:285px;
    }

    .close-modal {
        color: #007bff;
        float: right;
        font-size: 28px;
        cursor: pointer;
    }

    .close-modal:hover {
        color: #007bff;
        }
        .modal-content{
        color:black;
        }
        table {
    border-collapse: collapse;
    width: 100%;
}
td {
    height: 30px; /* Ensuring empty cells have height */
}
 .action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
         margin-left: 10px; /* Space between buttons */
          padding: 10px;
    }
     .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
     .scrollable-table-container {
    overflow-x: auto;
    width: 100%;
    border: 1px solid #ddd;
    margin-top: 10px;
}

.scrollable-table-container table {
    min-width: 1000px;
    border-collapse: collapse;
}

.scrollable-table-container th,
.scrollable-table-container td {
    border: 1px solid #000;
    padding: 8px;
    text-align: left;
    white-space: nowrap;
}
 #viewtable{
 overflow-x:auto;
 }
 #templateinfo{
  overflow-y:auto;
 }
 #tableHeaderRow{
background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
 }
 #tableBody{
  padding: 10px;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
 text-align: left;
 font-family: 'Noto Sans', sans-serif;
    color: #898989;/* Label text color */
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
 }
 #tablerow {
	background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
 }
 #errorContainer ul {
    list-style: disc;
    padding-left: 20px;
    color: red;
    font-weight: 500;
    font-family: Arial, sans-serif;
  }
}
 
    </style>
 <%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
     String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
        String roleName = user != null ? user.getRoleName() : "";
        String roleId = user!=null?user.getRoleId():"";
        String contextPath =  request.getContextPath() ;
		%>
   

</head>
<body>

<!-- Arrows -->
<div class="arrow-container">
    <a href="#" class="arrow openSidebar" onclick="fileUploadTemplateSideBar()" style="font-weight: bold;">&#8593;</a> 
    <a href="#" class="arrow openSidebar" onclick="fileUploadTemplateSideBar()" style="font-weight: bold;">&#8595;</a> 
    <!-- <span class="arrow openSidebar" onclick="fileUploadTemplateSideBar">&#8593;</span> Up Arrow
    <span class="arrow openSidebar" onclick="fileUploadTemplateSideBar">&#8595;</span> Down Arrow -->
</div>

<!-- Import/Export Data Button -->
<div class="button-container">
    <!-- <button class="button openSidebar" onclick="fileUploadTemplateSideBar()">Import/Export Data</button> -->
    <a href="#" class="button openSidebar" onclick="fileUploadTemplateSideBar()">Import Data</a> 
    <div><br>
    
    <!-- <table >
    <thead >
				<tr>
					<th id="tablerow" ><input type="checkbox"></th>
					Add more table headers for each column
					<th id="tablerow" onclick="sortTable(1)">Template Name<span></span></th>
					<th id="tablerow" onclick="sortTable(2)">Status<span></span></th>
					<th id="tablerow" onclick="sortTable(3)">Created By<span></span></th>
					<th id="tablerow" onclick="sortTable(4)">Created Date<span></span></th>
					
				</tr>
			</thead>
			<tbody>
			<br>
			<td Style="font-family:'Noto Sans', sans-serif;font-size: 90%;color:grey;font-weight: 700;">No Records Found</td>
			</tbody>
    </table> --><br>
     <p style="color:grey;"><b>Note:</b></p>
     <p style="color:grey;">1.Should not upload different format as[for (.xlsx) should not upload(.xlx)]</p>
     <p style="color:grey;">2.only upload file as[for(.csv)]</p>
     <p style="color:grey;">3.Do not change first row heading content and order as provided in Templat Information</p>
     <p style="color:grey;">4.All columns should be fill</p>
    </div>
</div>
<div id="errorContainer" style="color:red; margin-top: 20px;"></div>
<div id="errorDisplay"></div>
<!-- Right Sidebar -->
<div id="sidebar" class="sidebar">
    <div class="sidebar-content">
        <button id="closeSidebar" class="close-btn">&times;</button>
        <h2 style="color:black;font-size: 0.875rem; ">Import Data</h2>
        <hr style="color:gray;">
        <!-- Import & Export Buttons -->
    <!-- <button id="importBtn"class="btn btn-default process-footer-button-cancel ng-binding" style="background-color: #007bff; color: white; border-radius: 5px;">Import</button> -->
    <!-- <button id="exportBtn"class="btn btn-default process-footer-button-cancel ng-binding" style="border: 1px solid #007bff; color: #007bff; border-radius: 5px;">Export</button> -->

    <br><br>
        <h4 style="color:grey;">Select a template to import data</h2>
        <select id="templateType" name="templateType" onchange="fetchTemplateOptions()" style="width: 100%; height: 45px;color:gray;" >
                <option value=""style="color:gray;">Template</option>
                <% if (user != null && !"Contractor".equals(roleName)) { %>
                <option value="generalMaster"style="color:gray;">Data-General Master</option>
              <!--  <option value="minimumWage"style="color:gray;">Data-Minimum Wage</option> -->
                <option value="principalEmployer"style="color:gray;">Data-Principal Employer</option>
                <option value="contractor"style="color:gray;">Data-Contractor</option>
                 <option value="workorder"style="color:gray;">Data-Workorder</option> 
                 <option value="workmenbulkupload"style="color:gray;">Data-Workmen Bulk Upload</option>
                   <% } %>
                  <% if (user != null && "Contractor".equals(roleName)) { %>
                 <option value="workmenbulkuploaddraft"style="color:gray;">Data-Workmen Bulk Upload Draft</option>
                 <% } %>
              </select>
              <a href="#" onclick="fetchTemplateInfo()"id="templateinfo">Template Information</a><br><br>
              <a href="#" onclick="viewTemplateInfo()" id="viewtemplate">View Template</a><br><br>
               <a href="#" onclick="downloadTemplateInfo()">Download Template</a>
         <!-- Message for Templates (Initially Hidden) -->
    <div id="templateOptions" class="hidden">
        <p id="templateMessage"></p>
        <a href="#" onclick="fetchTemplateInfo()">Template Information</a>
    </div>
    <div id="templateModal" class="modal" style="display:none;">
    <div class="modal-content">
        <span onclick="closeModal()" class="close-modal">&times;</span>
        <h2 id="modalTitle"></h2>
        <p id="modalDescription"></p>
        <div id="modalTable"></div>
    </div>
</div>
       <!-- <button class="import-button" onclick="alert('Import functionality coming soon!')">Import Data</button> -->
        <br> 
        <br>
        <br><br> <br><br><br><br><br><br><br>
        <!-- <button style="border: 1px solid #007bff; color: #007bff; border-radius: 5px;align: center;">Done</button> -->
    </div>
    <!-- View Template Container (Initially Hidden) -->
<!-- View Template Table (Initially Hidden) -->
<!-- View Template Table (Initially Hidden) -->

  
</div>
<!-- View Template Container (Table) -->
<div id="viewTemplateContainer" style="display: none; margin-top: 20px;">
   
    <div>
    
   
      <!-- <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveFileSidebar()"style="float:right;">Save</button> -->
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="openFileSidebar()"style="float:right;">Open File</button>
       <!-- <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="cancelButton()"style="float:right;">cancel</button>  -->
        <h2 style="color: darkcyan;font-size: 1rem;">Template Data</h2> 
        </div>
    <div id="viewtable">
    <table id="viewtable" border="1" style="width: 100%; border-collapse: collapse;">
        <thead>
            <tr id="tableHeaderRow">
                <!-- Headers will be set dynamically -->
            </tr>
        </thead>
        <tbody id="tableBody">
            <!-- JavaScript will insert empty rows here -->
        </tbody>
    </table>
 
    <!-- Open File Button -->
    
</div>
<div id="uploadMessage" style="display: none; font-weight: bold; margin-top: 10px;"></div>
<div id="fileUploadSidebar" class="sidebar" style="width: 0;">
    <div class="sidebar-content" id="fileUploadContainer">
        <button class="close-btn" onclick="closeFileSidebar()">&times;</button>
        <h2 style="color:black;font-size: 0.875rem;">Upload File</h2>
        <hr style="color:gray;">
        <h5 style="color:gray;">Max file size is 10240kb. Supported files types are text/csv, application/vnd.ms-excel.</h5>
        
        <!-- File Input -->
        <input type="file" id="fileInput" accept=".csv" style="margin-top: 10px;color:black;">
     
        <!-- Submit File Button -->
        <button onclick="uploadTemplateFile()" style="background-color: #007bff; color: white; border-radius: 5px; margin-top: 10px;">
            Upload
        </button>
       
    </div>
    <div id="uploadMessage" style="display: none; font-weight: bold; margin-top: 10px;"></div>
    
</div>
<div id="uploadMessage" style="display: none; font-weight: bold; margin-top: 10px;"></div>
</body>
</html>
