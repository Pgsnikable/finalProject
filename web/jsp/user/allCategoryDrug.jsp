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
        <c:if test="${not empty categoryList}">
            <div class="table-responsive">
                <table class="table">
                    <caption>Result</caption>
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <fmt:message var="name" key="category.name"/>
                        <th scope="col">${name}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="category" items="${categoryList}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${category.name}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/mainController">
                                    <input type="hidden" name="command" value="find_drug_by_category">
                                    <input type="hidden" name="categoryId" value="${category.categoryId}">
                                    <input type="hidden" name="categoryName" value="${category.name}">
                                    <fmt:message var="categoryButton" key="category.button.find.drug"/>
                                    <button type="submit" class="btn btn-sm btn-outline-primary">
                                        <span data-feather="list"></span>
                                            ${categoryButton}
                                        <span class="sr-only">(current)</span>
                                    </button>
                                </form>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <div class="mt-5 px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <c:if test="${not empty categoryDrugList}">
            <div class="table-responsive">
                <table class="table">
                    <caption>${param.categoryName}</caption>
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
                    <c:forEach var="drug" items="${categoryDrugList}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${drug.key.name}</td>
                            <td>${drug.key.composition}</td>
                            <td>${drug.key.indications}</td>
                            <td>${drug.key.modeOfApplication}</td>
                            <td>${drug.key.contraindications}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${drug.key.recipeNedeed eq 'true'}">
                                        <fmt:message key="drug.recipe.label.yes" var="yes"/>
                                        ${yes}
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="drug.recipe.label.no" var="no"/>
                                        ${no}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${drug.key.storageQuantity}</td>
                            <td>${drug.key.price}</td>
                            <form method="post" action="${pageContext.request.contextPath}/mainController">
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
                                    <input class="w-auto" type="number" min="0" max="${drug.key.storageQuantity}"
                                           name="selectedQuantity" value="0">
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
        </c:if>
    </div>


</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<c:remove var="categoryList" scope="request"/>


</body>
</html>
