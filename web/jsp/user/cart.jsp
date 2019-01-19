<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 08.01.2019
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/feather.css" rel="stylesheet">
</head>
<body class="bg-light">
<%@include file="/jsp/user/userHeader.jsp" %>
<main role="main">

    <div class="mt-5 px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">

        <c:choose>
            <c:when test="${not empty sessionScope.cart.drugDosage}">
                <div class="row">
                    <fmt:message var="cartLabel" key="cart.label"/>
                    <h3 class="h3">${cartLabel}</h3>
                </div>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <fmt:message var="name" key="drug.name"/>
                            <th scope="col">${name}</th>
                            <fmt:message var="dosage" key="drug.dosage"/>
                            <th scope="col">${dosage}</th>
                            <fmt:message var="request" key="drug.recipe"/>
                            <th scope="col">${request}</th>
                            <fmt:message var="selectedQuantity" key="cart.selceted.quantity"/>
                            <th scope="col">${selectedQuantity}</th>
                            <fmt:message var="cartPrice" key="cart.price"/>
                            <th scope="col">${cartPrice}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${cart.drugName}</td>
                            <td>${cart.drugDosage.dosage}</td>
                            <td>
                                <c:set var="isResipe" value="${cart.recipe}"/>
                                <c:choose>
                                    <c:when test="${ isResipe eq 'true' }">
                                        <fmt:message key="drug.recipe.label.yes" var="yes"/>
                                        <c:out value="${yes}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="drug.recipe.label.no" var="no"/>
                                        <c:out value="${no}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${cart.quantity}</td>
                            <td>${cart.totalPrice}</td>
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
                        <form class="mt-2 mt-md-0 p-2" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="submitOrder" key="cart.order.button"/>
                            <input type="hidden" name="command" value="do_order">
                            <button class="btn btn-lg btn-outline-success my-2 my-sm-0"
                                    type="submit">
                                <span data-feather="shopping-bag"></span>
                                    ${submitOrder}
                                <span class="sr-only">(current)</span>
                            </button>
                        </form>

                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <fmt:message var="emptyCart" key="cart.empty.label"/>
                <h3 class="font-italic">${emptyCart}</h3>
            </c:otherwise>
        </c:choose>
    </div>

</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<!-- Icons -->
<script src="/resources/js/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>
