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
            <c:when test="${not empty requestScope.allUsersMap}">
                <div class="row">
                    <fmt:message var="allUsers" key="admin.users.header"/>
                    <h3 class="h3 text-center">${allUsers}</h3>
                </div>
                <div class="table-responsive p-1">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">№</th>
                            <fmt:message var="login" key="admin.users.login"/>
                            <th scope="col">${login}</th>
                            <fmt:message var="firstName" key="admin.users.firstname"/>
                            <th scope="col">${firstName}</th>
                            <fmt:message var="lastName" key="admin.users.lastname"/>
                            <th scope="col">${lastName}</th>
                            <th scope="col">Email</th>
                            <fmt:message var="birthday" key="admin.users.birthday"/>
                            <th scope="col">${birthday}</th>
                            <fmt:message var="userRole" key="admin.users.role"/>
                            <th scope="col">${userRole}</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="user" items="${requestScope.allUsersMap}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${user.key.login}</td>
                                <c:choose>
                                    <c:when test="${user.key.role eq 'USER'}">
                                        <td>${user.value.firstName}</td>
                                        <td>${user.value.lastName}</td>
                                        <td>${user.value.email}</td>
                                        <fmt:parseDate value="${user.value.dateOfBirth}" var="dateOfBirth"
                                                       type="date"
                                                       dateStyle="short" pattern="yyyy-MM-dd"/>
                                        <fmt:formatDate value="${dateOfBirth}" var="formatDateTime"
                                                        pattern="dd.MM.yyyy"/>
                                        <td>${formatDateTime}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>-</td>
                                        <td>-</td>
                                        <td>-</td>
                                        <td>-</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <c:if test="${user.key.role eq 'USER' }">
                                        <fmt:message key="admin.users.role.user" var="userRole"/>
                                        <c:out value="${userRole}"/>
                                    </c:if>
                                    <c:if test="${user.key.role eq 'DOCTOR'}">
                                        <fmt:message key="admin.users.role.doctor" var="doctorRole"/>
                                        <c:out value="${doctorRole}"/>
                                    </c:if>
                                    <c:if test="${user.key.role eq 'ADMIN'}">
                                        <fmt:message var="adminRole" key="admin.users.role.admin"/>
                                        <c:out value="${adminRole}"/>
                                    </c:if>

                                </td>
                                <td>
                                    <form class="mt-2 mt-md-0 p-2" method="post"
                                          action="${pageContext.request.contextPath}/mainController">
                                        <input type="hidden" name="command" value="delete_user">
                                        <input type="hidden" name="userId"
                                               value="${user.key.userId}">
                                        <fmt:message var="deleteUser" key="admin.users.delete"/>
                                        <button class="btn btn-sm btn-danger my-2 my-sm-0"
                                                type="submit">
                                            <span data-feather="user-x"></span>
                                                ${deleteUser}
                                            <span class="sr-only">(current)</span>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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
