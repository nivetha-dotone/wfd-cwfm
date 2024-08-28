<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
     <script src="resources/js/cms/users.js"></script>
      <script src="resources/js/commonjs.js"></script>
      <style>
      </style>
</head>
<body>
<div class="page-header">
        Users List
        <div class="header-buttons">
         <button type="submit" onclick="redirectToUserAdd()">Add</button>
        <button type="submit" onclick="redirectToUserEdit('${user.id}')">Edit</button> 
        <button type="submit" onclick="redirectToUserView('${user.id}')">View</button>
         <button type="button" onclick="UserExportToCSV()">Export</button> 
</div>


         
        </div>
     <%--    </div>
    <form id="searchForm">
    <input type="text" id="searchInput" name="searchQuery" placeholder="Search...">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
</form>  --%>   


     <form id="updateForm" method="POST" >
                         <div class="table-container">
    <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
        <thead>
        
<tr>
                    <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAllUsers()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">Login ID<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(2)">Username<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(3)">Contact Email<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(4)">Contact Number<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
<th class="header-text"  onclick="sortTable(5)">User Role<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Last Password Changed<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedUsers" value="${user.id}"></td>
                    <td style="border: 1px solid black;">${user.loginId}</td>
                    <td style="border: 1px solid black;">${user.username}</td>
                    <td style="border: 1px solid black;">${user.contactEmail}</td>
                    <td style="border: 1px solid black;">${user.contactNumber}</td>
                    <td style="border: 1px solid black;">${user.userRole}</td>
                    <td style="border: 1px solid black;">${user.lastPasswordChanged}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
     </div>
                        </form>
</body>
</html>
