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
</head>
<body>
<%@include file="/jsp/user/userHeader.jsp" %>
<main role="main">

    <div class="mt-5 px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <c:choose>
            <c:when test="${not empty drugsMap}">
                <div class="row">
                    <fmt:message var="requestLabel" key="recipe.request.label"/>
                    <h3 class="h3 text-center">${requestLabel}</h3>
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
                        <c:forEach var="request" items="${drugsMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${request.key.name}</td>
                                <td>
                                    <select class="custom-select w-auto" form="drugs" name="drugDosageId">

                                        <fmt:message var="dosageSelect" key="drug.dosage.select"/>
                                        <option hidden value="">${dosageSelect}</option>
                                        <c:forEach var="dosage" items="${request.value}" varStatus="status">
                                            <option ${status.count eq 1 ? "selected" : ""}
                                                    value="${dosage.dosageId}">${dosage.dosage}</option>
                                        </c:forEach>

                                    </select>
                                </td>
                                <form id="drugs" method="post" action="${pageContext.request.contextPath}/mainController">
                                    <input type="hidden" name="command" value="send_request">
                                    <td>
                                        <input class="form-control" type="date" name="expirationDate"
                                               required title="${inputTitle}" value="2019-02-01" min="2019-02-01" max="2019-03-01"/>
                                    </td>
                                    <td>
                                        <fmt:message var="sendRequest" key="recipe.request.send.button"/>
                                        <button type="submit" class="btn btn-outline-primary btn w-auto"
                                                style="word-break: break-all">${sendRequest}</button>
                                    </td>
                                </form>
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
                <fmt:message var="drugsForRequest" key="recipe.request.empty"/>
                <h3 class="font-italic">${drugsForRequest}</h3>
            </c:otherwise>
        </c:choose>
    </div>

</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>

</body>
</html>
