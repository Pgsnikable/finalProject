<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 01.01.2019
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resources.localization.translation" scope="session"/>

<form method="post" class="navbar-nav mr-auto mt-2" action="">
    <input type="hidden" name="command" value="change_language">
    <div class="form-group">
        <select class="nav-item custom-select" id="language" name="language" onchange="submit()">
            <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
            <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
        </select>
    </div>
</form>
