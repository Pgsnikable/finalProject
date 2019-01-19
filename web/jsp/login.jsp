<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 24.12.2018
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>login</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/signin.css" rel="stylesheet">
</head>
<body>

<div class="container-fluid">
    <nav class="navbar navbar-expand-md navbar-light fixed-top">
        <div class="collapse navbar-collapse ml-auto">
            <%@include file="/jsp/locale.jsp" %>
        </div>
    </nav>

    <div class="row">

        <form class="form-signin text-center" method="post" action="${pageContext.request.contextPath}/mainController">
            <input type="hidden" name="command" value="login"/>
            <fmt:message var="inputTitle" key="input.login.title"/>

            <fmt:message key="login.label" var="login"/>
            <label class="h3 font-weight-normal" for="login">${login}</label><br/>

            <fmt:message var="loginPlaceholder" key="login.placeholder"/>
            <input class="form-control" id="login" type="text" name="login"
                   placeholder="${loginPlaceholder}" required pattern="^[\w-]{4,31}$"
                   title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/><br/>

            <fmt:message var="password" key="password.label"/>
            <label class="h3 font-weight-normal" for="password">${password}</label><br/>

            <fmt:message var="passwordPlaceholder" key="password.placeholder"/>
            <input class="form-control" id="password" type="password" name="password"
                   placeholder="${passwordPlaceholder}" required pattern="^[A-Za-z]\w{3,31}$"
                   title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/><br/>

            <fmt:message key="login.button" var="loginButton"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                <span data-feather="log-in"></span>
                ${loginButton}
                <span class="sr-only">(current)</span>
            </button>
            <br/>

            <c:if test="${sessionScope.message eq 'true'}">
                <fmt:message var="errorMessage" key="login.error.message"/>
                ${errorMessage}
            </c:if>
        </form>
    </div>
    <div class="row text-center">
        <div class="col-12">
            <fmt:message var="updateButton" key="register.button"/>
            <a href="${pageContext.request.contextPath}/jsp/registration.jsp" class="btn btn-sm btn-success"
               role="button">${updateButton}</a>
        </div>

    </div>

</div>
<div class="fixed-bottom text-center">
    <%@include file="footer.jsp" %>
</div>

<script src="/resources/js/bootstrap.js"></script>
<script>
    function loc() {
        var lng = document.getElementById("language").value;
        if (lng === 'ru_RU') {
            return 'Неверный формат';
        } else {
            return 'Incorrect format';
        }
    }
</script>
<script src="/resources/js/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>
