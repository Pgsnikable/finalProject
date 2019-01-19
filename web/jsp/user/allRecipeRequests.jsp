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
            <c:when test="${not empty allRequestMap}">
                <div class="row">
                    <fmt:message var="recipeRequestLabel" key="recipe.request.page.label"/>
                    <h3 class="h3 text-center">${recipeRequestLabel}</h3>
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
                            <fmt:message var="expirationDate" key="recipe.page.expiration"/>
                            <th scope="col">${expirationDate}</th>
                            <fmt:message var="orderStatus" key="order.status"/>
                            <th scope="col">${orderStatus}</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="request" items="${allRequestMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${request.value.key.name}</td>
                                <td>${request.value.value.dosage}</td>
                                <fmt:parseDate value="${request.key.expirationDate}" var="expirationDate" type="date"
                                               dateStyle="short" pattern="yyyy-MM-dd"/>
                                <fmt:formatDate value="${expirationDate}" var="formatDate" pattern="dd.MM.yyyy"/>
                                <td>${formatDate}</td>
                                <td>
                                    <c:if test="${request.key.status eq 'ACCEPTED' }">
                                        <fmt:message key="order.status.accepted" var="yes"/>
                                        <c:out value="${yes}"/>
                                    </c:if>
                                    <c:if test="${request.key.status eq 'DECLINED'}">
                                        <fmt:message key="order.status.declined" var="no"/>
                                        <c:out value="${no}"/>
                                    </c:if>
                                    <c:if test="${request.key.status eq 'WAITING'}">
                                        <fmt:message var="wait" key="order.status.waiting"/>
                                        <c:out value="${wait}"/>
                                    </c:if>
                                </td>
                                <c:if test="${request.key.status eq 'WAITING'}">
                                    <td>
                                        <form class="mt-2 mt-md-0 p-2" method="post"
                                              action="${pageContext.request.contextPath}/mainController">
                                            <fmt:message var="declineRecipeRequest" key="recipe.request.delete.button"/>
                                            <input type="hidden" name="command" value="delete_request">
                                            <input type="hidden" name="requestId"
                                                   value="${request.key.recipeRequestId}">
                                            <input type="hidden" name="requestStatus" value="${request.key.status}">
                                            <button class="btn btn-danger my-2 my-sm-0"
                                                    type="submit">
                                                <span data-feather="trash-2"></span>
                                                    ${declineRecipeRequest}
                                                <span class="sr-only">(current)</span>
                                            </button>
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <fmt:message var="emptyRequest" key="recipe.request.without"/>
                <h3 class="font-italic">${emptyRequest}</h3>
            </c:otherwise>
        </c:choose>
    </div>

</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>
