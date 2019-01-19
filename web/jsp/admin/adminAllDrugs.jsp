<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="/resources/css/admin.css" rel="stylesheet">
</head>
<body>
<%@include file="/jsp/admin/adminHeader.jsp" %>
<main role="main" class="col-md-10 ml-sm-auto col-lg-10">
    <div class="px-2 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <c:choose>
            <c:when test="${not empty requestScope.allDrugsMap}">
                <c:forEach var="drugCategory" items="${requestScope.allDrugsMap}" varStatus="categoryStatus">
                    <div class="row">
                        <div class="container col-6 text-right">
                            <h5 class="font-italic">${drugCategory.key.name}</h5>
                        </div>
                        <div class="container col-6 text-left">
                            <form method="post" action="${pageContext.request.contextPath}/mainController">
                                <input type="hidden" name="command" value="delete_category">
                                <input type="hidden" name="categoryId" value="${drugCategory.key.categoryId}">

                                    <fmt:message var="deleteCategoryButton" key="admin.add.category.delete"/>
                                <button type="submit" class="btn btn-sm btn-danger btn w-auto"
                                        style="word-break: break-all">
                                    <span data-feather="trash-2"></span>
                                        ${deleteCategoryButton}
                                    <span class="sr-only">(current)</span>
                                </button>
                        </div>
                        </form>
                    </div>
                    <div class="table-responsive p-1">
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
                                        <input type="hidden" name="command" value="delete_drug">
                                        <input type="hidden" name="drugId" value="${drug.key.drugId}">
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
                                            <fmt:message var="deleteDrugButton" key="admin.delete.drug.header"/>
                                            <button type="submit" class="btn btn-danger btn w-auto"
                                                    style="word-break: break-all">
                                                <span data-feather="trash-2"></span>
                                                    ${deleteDrugButton}
                                                <span class="sr-only">(current)</span>
                                            </button>
                                        </td>
                                    </form>
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <fmt:message var="emptyUsers" key="admin.users.empty"/>
                <h3 class="font-italic">${emptyUsers}</h3>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
<!-- Icons -->
<script src="/resources/js/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>
