<%-- 
    Document   : _navbar
    Created on : 7 août 2022, 12:28:01
    Author     : dev-pro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">S.W.J</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav me-auto">
                <c:if test="${ empty sessionScope.SESSION_USER_KEY}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/formregisteruser">s'inscrire</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login"><i class="bi bi-box-arrow-in-right"> Se connecter</i></a>
                    </li>
                </c:if>
                
                    
                <c:if test="${ !empty sessionScope.SESSION_USER_KEY }">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profiluser"><i class="bi bi-person-circle"> Profil </i></a>
                    </li>
                    
                    <c:if test="${ SESSION_USER_KEY.role == admin }">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/home"><i class="bi bi-person-lines-fill"> liste</i></a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"> Deconnexion </i></a>
                    </li>
                </c:if>
                    
<!--                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/upload"><i class="bi bi-box-arrow-in-right">Image</i></a>
                    </li>-->
                    
                
            </ul>
            
        </div>
    </div>
</nav>
    
       
