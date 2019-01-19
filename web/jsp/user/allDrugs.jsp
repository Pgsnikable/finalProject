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
        <c:if test="${not empty allDrugsMap}">
            <c:forEach var="drugCategory" items="${allDrugsMap}" varStatus="categoryStatus">
                <hr>
                <h5 class="font-italic">${drugCategory.key.name}</h5>
                <div class="px-3 py-3 pt-md-3 pb-md-3 mx-auto text-center">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">№</th>
                                <fmt:message var="name" key="drug.name"/>
                                <th scope="col">${name}</th>
                                <fmt:message var="composition" key="drug.composition"/>
                                <th scope="col">${composition}</th>
                                <fmt:message var="indications" key="drug.indications"/>
                                <th scope="col">${indications}</th>
                                <fmt:message var="application" key="drug.applications"/>
                                <th scope="col">${application}</th>
                                <fmt:message var="contra" key="drug.contraindications"/>
                                <th scope="col">${contra}</th>
                                <fmt:message var="recipe" key="drug.recipe"/>
                                <th scope="col">${recipe}</th>
                                <fmt:message var="storage" key="drug.storage.quantity"/>
                                <th scope="col">${storage}</th>
                                <fmt:message var="price" key="drug.price"/>
                                <th scope="col">${price}</th>
                                <fmt:message var="dosage" key="drug.dosage"/>
                                <th scope="col">${dosage}</th>
                                <fmt:message var="forOrder" key="cart.quantity"/>
                                <th scope="col">${forOrder}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="drug" items="${drugCategory.value}" varStatus="status">
                                <tr>
                                    <td>${categoryStatus.count}</td>
                                    <td>${drug.key.name}</td>
                                    <td>${drug.key.composition}</td>
                                    <td>${drug.key.indications}</td>
                                    <td>${drug.key.modeOfApplication}</td>
                                    <td>${drug.key.contraindications}</td>
                                    <td>
                                        <fmt:message key="drug.recipe.label.yes" var="yes"/>
                                        <fmt:message key="drug.recipe.label.no" var="no"/>

                                        <c:choose>
                                            <c:when test="${drug.key.recipeNedeed eq 'true' }">
                                                ${yes}
                                            </c:when>
                                            <c:otherwise>
                                                ${no}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${drug.key.storageQuantity}</td>
                                    <td>${drug.key.price}</td>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/mainController">
                                        <input type="hidden" name="command" value="add_to_cart">
                                        <td>
                                            <select class="custom-select w-auto" name="drugDosageId">

                                                <fmt:message var="dosageSelect" key="drug.dosage.select"/>
                                                <option hidden value="">${dosageSelect}</option>
                                                <c:forEach var="dosage" items="${drug.value}" varStatus="status">
                                                    <option ${status.count eq 1 ? "selected" : ""}
                                                            value="${dosage.dosageId}">${dosage.dosage}</option>
                                                </c:forEach>

                                            </select>
                                        </td>

                                        <td>
                                            <input class="w-auto" type="number" min="0"
                                                   max="${drug.key.storageQuantity}" name="selectedQuantity" value="0">
                                        </td>
                                        <td>
                                            <fmt:message var="cartButton" key="cart.add.button"/>
                                            <button type="submit" class="btn btn-outline-primary btn w-auto"
                                                    style="word-break: break-all">
                                                <span data-feather="shopping-cart"></span>
                                            ${cartButton}
                                                <span class="sr-only">(current)</span>
                                            </button>
                                        </td>
                                    </form>
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>


</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>

</body>
</html>
