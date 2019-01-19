<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="/resources/css/feather.css" rel="stylesheet">
</head>
<body>
<%@include file="/jsp/user/userHeader.jsp" %>
<main role="main">

    <div class="mt-5 px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <c:choose>
            <c:when test="${not empty allRecipeMap}">
                <div class="row">
                    <fmt:message var="recipeLabel" key="recipe.page.label"/>
                    <h3 class="h3 text-center">${recipeLabel}</h3>
                </div>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">№</th>
                            <fmt:message var="name" key="drug.name"/>
                            <th scope="col">${name}</th>
                            <fmt:message var="dosage" key="drug.dosage"/>
                            <th scope="col">${dosage}</th>
                            <fmt:message var="expirationTime" key="recipe.page.expiration"/>
                            <th scope="col">${expirationTime}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="request" items="${allRecipeMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${request.value.key.name}</td>
                                <td>${request.value.value.dosage}</td>
                                <fmt:parseDate value="${request.key.expirationDate}" var="expirationDate" type="date"
                                               dateStyle="short" pattern="yyyy-MM-dd"/>
                                <fmt:formatDate value="${expirationDate}" var="formatDate" pattern="dd.MM.yyyy"/>
                                <td>${formatDate}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="row text-center">
                    <c:if test="${requestScope.orderErrorMessage eq 'true'}">
                        <fmt:message var="errorMessage" key="cart.errorMessage"/>
                        <br>${errorMessage}
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <fmt:message var="recipesEmpty" key="recipe.page.empty.message"/>
                <h3 class="font-italic">${recipesEmpty}</h3>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<c:remove var="orderDeleteMessageSuccess" scope="request"/>
<c:remove var="deleteErrorMessage" scope="request"/>
</body>
</html>
