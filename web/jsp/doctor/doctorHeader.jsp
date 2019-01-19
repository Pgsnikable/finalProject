<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 07.01.2019
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="cart" scope="session" class="by.training.project.entity.Cart"/>--%>
<%--<jsp:setProperty name="cart" property="userId" value="${sessionScope.user.userId}"/>--%>
<header>
    <nav class="navbar navbar-dark navbar-expand-md navbar-light fixed-top bg-light shadow rounded">
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <%@include file="/jsp/locale.jsp" %>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <form class="form-inline mt-2 mt-md-0 p-2" method="post"
                          action="${pageContext.request.contextPath}/jsp/user/main.jsp">
                        <fmt:message var="home" key="main.home"/>
                        <button class="btn btn-success my-2 my-sm-0" type="submit">${home}</button>
                    </form>
                </li>
            </ul>
            <form class="form-inline mt-2 mt-md-0" method="post"
                  action="${pageContext.request.contextPath}/mainController">
                <fmt:message var="logout" key="logout.button"/>
                <input type="hidden" name="command" value="logout">
                <button class="btn btn-warning my-2 my-sm-0" type="submit">${logout}</button>
            </form>
        </div>
    </nav>
</header>

