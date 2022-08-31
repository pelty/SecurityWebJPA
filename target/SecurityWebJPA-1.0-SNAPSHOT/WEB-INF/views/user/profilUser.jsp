<%-- 
    Document   : profilUser
    Created on : 7 août 2022, 12:32:52
    Author     : dev-pro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://bootswatch.com/5/journal/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="/model/_navbar.jsp" %>
        <div class="">
            <h1>Bienvenue ${ SESSION_USER_KEY.username } !</h1>

            <c:if test = "${ !empty errMsg }">
                <p style="color: red;"> <c:out value="${errMsg}" /></p>
            </c:if>
            
        </div>
            
            
    </body>
</html>