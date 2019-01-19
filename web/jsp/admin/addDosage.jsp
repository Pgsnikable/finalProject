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
<main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4">
    <div class="px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <form method="post" action="${pageContext.request.contextPath}/mainController">
            <input type="hidden" name="command" value="add_dosage">

            <div class="col-md-6 mb-3">
                <fmt:message var="selectDrug" key="admin.add.dosage.select.drug"/>
                <label for="selectDrug">${selectDrug}</label>
                <select id="selectDrug" class="custom-select w-auto" name="drugId" required>
                    <c:forEach var="drug" items="${requestScope.drugs}" varStatus="status">
                        <fmt:message var="drugNameSelect" key="admin.add.dosage.select.drug"/>
                        <option hidden value="">${drugNameSelect}</option>
                        <option ${status.count eq 1 ? "selected" : ""} value="${drug.drugId}">
                                ${drug.name}
                        </option>
                    </c:forEach>
                </select><br>
            </div>

            <div class="col-md-6 mb-3">
                <fmt:message var="inputTitle" key="input.login.title"/>
                <fmt:message var="addDosageButton" key="admin.add.dosage.header"/>
                <fmt:message var="dosageLabel" key="admin.add.dosage.label"/>
                <label style="font-size: large" for="dosageName">${dosageLabel}</label>
                <fmt:message var="dosagePlaceholder" key="admin.add.dosage.placeholder"/>
                <input type="text" class="form-control" id="dosageName" name="dosageName"
                       placeholder="${dosagePlaceholder}" required
                       pattern="^[A-Z][a-z\s\D\d]{1,63}$|^[А-Я][а-яёъ\s\D\d]{1,63}$"
                       title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/></br>
                <button class="btn " type="submit">
                    <span data-feather="plus-circle"></span>
                    ${addDosageButton} <span class="sr-only">(current)</span>
                </button>
            </div>

        </form>
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
</body>
</html>
