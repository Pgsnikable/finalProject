<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 31.12.2018
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
</head>
<%@include file="/jsp/locale.jsp" %>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-8 text-center">
            <fmt:message var="requestFrom" key="error.request.from"/>
            ${requestFrom}
            ${pageContext.errorData.requestURI}<br>

            <fmt:message var="statusCode" key="error.status"/>
            ${statusCode}
            ${pageContext.errorData.statusCode}<br>

            <fmt:message var="throwableText" key="error.throwable"/>
            ${throwableText}
            ${pageContext.errorData.throwable}<br>
        </div>
    </div>
</div>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>
