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
            <c:when test="${not empty requestScope.orderMap}">
                <div class="row">
                    <fmt:message var="orderLabel" key="admin.orders.all"/>
                    <h3 class="h3 text-center">${orderLabel}</h3>
                </div>
                <div class="table-responsive p-1">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">№</th>
                            <fmt:message var="firstName" key="register.firstname"/>
                            <th scope="col">${firstName}</th>
                            <fmt:message var="lastName" key="register.lastname"/>
                            <th scope="col">${lastName}</th>
                            <fmt:message var="drugName" key="drug.name"/>
                            <th scope="col">${drugName}</th>
                            <fmt:message var="dosage" key="drug.dosage"/>
                            <th scope="col">${dosage}</th>
                            <fmt:message var="orderTime" key="order.time"/>
                            <th scope="col">${orderTime}</th>
                            <fmt:message var="orderQuantity" key="order.quantity"/>
                            <th scope="col">${orderQuantity}</th>
                            <fmt:message var="orderCost" key="order.cost"/>
                            <th scope="col">${orderCost}</th>
                            <fmt:message var="orderStatus" key="order.status"/>
                            <th scope="col">${orderStatus}</th>
                            <fmt:message var="changeStatus" key="doctor.recipe.request.change"/>
                            <th scope="col">${changeStatus}</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="order" items="${requestScope.orderMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${order.key.key.firstName}</td>
                                <td>${order.key.key.lastName}</td>
                                <td>${order.value.key.name}</td>
                                <td>${order.value.value.dosage}</td>
                                <fmt:parseDate value="${order.key.value.orderTime}" var="orderTime"
                                               type="date"
                                               dateStyle="short" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                                <fmt:formatDate value="${orderTime}" var="formatDateTime"
                                                pattern="HH:mm:ss dd.MM.yyyy"/>
                                <td>${formatDateTime}</td>
                                <td>${order.key.value.quantity}</td>
                                <td>${order.key.value.orderCost}</td>
                                <td>
                                    <c:if test="${order.key.value.status eq 'ACCEPTED' }">
                                        <fmt:message key="order.status.accepted" var="yes"/>
                                        <c:out value="${yes}"/>
                                    </c:if>
                                    <c:if test="${order.key.value.status eq 'DECLINED'}">
                                        <fmt:message key="order.status.declined" var="no"/>
                                        <c:out value="${no}"/>
                                    </c:if>
                                    <c:if test="${order.key.value.status eq 'WAITING'}">
                                        <fmt:message var="wait" key="order.status.waiting"/>
                                        <c:out value="${wait}"/>
                                    </c:if>
                                </td>
                                <c:if test="${order.key.value.status eq 'WAITING'}">
                                    <form class="mt-2 mt-md-0 p-2" method="post"
                                          action="${pageContext.request.contextPath}/mainController">
                                        <input type="hidden" name="command" value="change_order_status">
                                        <input type="hidden" name="orderId"
                                               value="${order.key.value.orderId}">

                                        <td>
                                            <select class="custom-select w-auto" name="changedStatus">
                                                <fmt:message var="selectStatus"
                                                             key="doctor.recipe.request.select.status"/>
                                                <option hidden value="">${selectStatus}</option>
                                                <c:forEach var="newStatus" items="${requestScope.allStatus}"
                                                           varStatus="status">
                                                    <option ${status.count eq 1 ? "selected" : ""} value="${newStatus}">
                                                        <c:if test="${newStatus eq 'ACCEPTED' }">
                                                            <fmt:message key="order.status.accepted" var="yes"/>
                                                            <c:out value="${yes}"/>
                                                        </c:if>
                                                        <c:if test="${newStatus eq 'DECLINED'}">
                                                            <fmt:message key="order.status.declined" var="no"/>
                                                            <c:out value="${no}"/>
                                                        </c:if>
                                                        <c:if test="${newStatus eq 'WAITING'}">
                                                            <fmt:message var="wait" key="order.status.waiting"/>
                                                            <c:out value="${wait}"/>
                                                        </c:if>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <button class="btn btn-info" type="submit">
                                                <span data-feather="file-text"></span>
                                                    ${changeStatus} <span class="sr-only">(current)</span>
                                            </button>
                                        </td>
                                    </form>
                                </c:if>

                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/mainController">
                                        <input type="hidden" name="command" value="decline_order">
                                        <input type="hidden" name="declinedOrderId" value="${order.key.value.orderId}">
                                        <fmt:message var="deleteOrder" key="admin.orders.delete"/>
                                        <button class="btn btn-danger" type="submit">
                                            <span data-feather="trash-2"></span>
                                                ${deleteOrder} <span class="sr-only">(current)</span>
                                        </button>

                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <fmt:message var="emptyOrders" key="admin.all.orders.empty"/>
                <h3 class="font-italic">${emptyOrders}</h3>
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
