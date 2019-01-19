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
            <c:when test="${not empty allOrderMap}">
                <div class="row">
                    <fmt:message var="orderLabel" key="order.label"/>
                    <h3 class="h3 text-center">${orderLabel}</h3>
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
                            <fmt:message var="orderTime" key="order.time"/>
                            <th scope="col">${orderTime}</th>
                            <fmt:message var="orderQuantity" key="cart.selceted.quantity"/>
                            <th scope="col">${orderQuantity}</th>
                            <fmt:message var="orderCost" key="order.cost"/>
                            <th scope="col">${orderCost}</th>
                            <fmt:message var="orderStatus" key="order.status"/>
                            <th scope="col">${orderStatus}</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="request" items="${allOrderMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${request.value.key.name}</td>
                                <td>${request.value.value.dosage}</td>
                                <fmt:parseDate value="${request.key.orderTime}" var="issueTime" type="both"
                                               pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                                <fmt:formatDate value="${issueTime}" var="formatTime" pattern="HH:mm:ss dd.MM.yyyy"/>
                                <td>${formatTime}</td>
                                <td>${request.key.quantity}</td>
                                <td>${request.key.orderCost}</td>
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
                                            <fmt:message var="declineOrder" key="order.button.decline"/>
                                            <input type="hidden" name="command" value="decline_order">
                                            <input type="hidden" name="declinedOrderId" value="${request.key.orderId}">
                                                <%--<input type="hidden" name="drugId" value="${request.value.key.drugId}">--%>
                                                <%--<input type="hidden" name="orderStatus" value="${request.key.status}">--%>
                                                <%--<input type="hidden" name="orderCost" value="${request.key.orderCost}">--%>
                                            <input type="hidden" name="drugQuantity" value="${request.key.quantity}">
                                            <button class="btn btn-sm btn-danger my-2 my-sm-0"
                                                    type="submit">
                                                <span data-feather="trash-2"></span>
                                                    ${declineOrder}
                                                <span class="sr-only">(current)</span>
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <form class="mt-2 mt-md-0 p-2" method="post"
                                              action="${pageContext.request.contextPath}/mainController">
                                            <fmt:message var="changeOrder" key="order.button.change"/>
                                            <input type="hidden" name="command" value="go_to_change_order">
                                            <input type="hidden" name="orderId" value="${request.key.orderId}">
                                            <input type="hidden" name="drugId" value="${request.value.key.drugId}">
                                            <button class="btn btn-sm btn-warning my-2 my-sm-0"
                                                    type="submit">
                                                <span data-feather="edit"></span>
                                                    ${changeOrder}
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
                <div class="row text-center">
                    <c:if test="${requestScope.orderErrorMessage eq 'true'}">
                        <fmt:message var="errorMessage" key="cart.errorMessage"/>
                        <br>${errorMessage}
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <fmt:message var="emptyOrders" key="order.empty.label"/>
                <h3 class="font-italic">${emptyOrders}</h3>
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
