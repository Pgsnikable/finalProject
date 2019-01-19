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

        <div class="row">
            <fmt:message var="changeOrderForm" key="order.change.form"/>
            <h3 class="h3">${changeOrderForm}</h3>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <fmt:message var="name" key="drug.name"/>
                    <th scope="col">${name}</th>
                    <fmt:message var="dosage" key="drug.dosage"/>
                    <th scope="col">${dosage}</th>
                    <fmt:message var="selectedQuantity" key="drug.storage.quantity"/>
                    <th scope="col">${selectedQuantity}</th>
                    <fmt:message var="cartQuantity" key="cart.quantity"/>
                    <th scope="col">${cartQuantity}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${requestScope.drug.name}</td>
                    <td>
                        <select class="custom-select w-auto" form="change_drug" name="drugDosageId">

                            <fmt:message var="dosageSelect" key="drug.dosage.select"/>
                            <option hidden value="">${dosageSelect}</option>
                            <c:forEach var="drugDosage" items="${requestScope.dosages}" varStatus="status">
                                <option ${status.count eq 1 ? "selected" : ""}
                                        value="${drugDosage.dosageId}">${drugDosage.dosage}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>${requestScope.drug.storageQuantity}</td>
                    <td>
                        <input form="change_drug" class="w-auto" type="number" min="0"
                               max="${requestScope.drug.storageQuantity}"
                               name="selectedQuantity" value="0">
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="row text-center">
            <c:if test="${requestScope.orderErrorMessage eq 'true'}">
                <fmt:message var="errorMessage" key="cart.errorMessage"/>
                <br>${errorMessage}
            </c:if>
        </div>
        <div class="row">
            <div class="container text-center">
                <form id="change_drug" class="mt-2 mt-md-0 p-2" method="post"
                      action="${pageContext.request.contextPath}/mainController">
                    <fmt:message var="changeOrderButton" key="order.button.change"/>
                    <input type="hidden" name="command" value="change_order">
                    <input type="hidden" name="oldOrderId" value="${requestScope.oldOrderId}">
                    <input type="hidden" name="drugId" value="${requestScope.drug.drugId}">
                    <button class="btn btn-lg btn-primary my-2 my-sm-0"
                            type="submit">
                        <span data-feather="edit"></span>
                        ${changeOrderButton}
                        <span class="sr-only">(current)</span>
                    </button>
                </form>

            </div>
        </div>
    </div>


</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>

</body>
</html>
