<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 23.12.2018
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%@include file="/jsp/doctor/doctorHeader.jsp" %>
<main role="main">
    <c:if test="${sessionScope.requestSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestSuccess" key="doctor.recipe.request.success.accept"/>
            <br>
            <h3>${requestSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.requestError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestError" key="doctor.recipe.request.error.decline"/>
            <br>
            <h3>${requestSuccess}</h3>
        </div>
    </c:if>


    <div class="container d-flex w-100 h-100 p-3 mt-5 p-5 mx-auto flex-column">
        <fmt:message key="admin.users.role.doctor" var="doctorRole"/>
        <h2><ctg:hello role="${doctorRole}"/></h2>

        <fmt:message var="label" key="doctor.recipe.request.label"/>
        <h1 class="cover-heading">${label}</h1>
        <fmt:message var="info" key="doctor.recipe.request.info"/>
        <p class="lead">${info}</p>
        <p class="lead">
        <form class="form-inline mt-2 mt-md-0 p-2" method="post"
              action="${pageContext.request.contextPath}/mainController">
            <input type="hidden" name="command" value="show_recipe_request">
            <fmt:message var="recipeRequest" key="doctor.recipe.request.label"/>
            <button class="btn btn-success my-2 my-sm-0" type="submit">${recipeRequest}</button>
        </form>
        </p>
    </div>
</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/jquery-3.3.1.min.js"></script>
</body>
</html>
