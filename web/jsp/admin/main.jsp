<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 23.12.2018
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <title>Main</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/admin.css" rel="stylesheet">
</head>
<body>
<%@include file="/jsp/admin/adminHeader.jsp" %>
<main role="main" class="col-md-10 ml-sm-auto col-lg-10">
    <c:if test="${sessionScope.orderSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="orderSuccess" key="admin.order.change"/>
            <br>
            <h3>${orderSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.orderFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="orderFailed" key="admin.order.change.failed"/>
            <br>
            <h3>${orderFailed}</h3>
        </div>
    </c:if>

    <c:if test="${sessionScope.orderDeleteMessageSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="orderDeleteSuccessMessage" key="admin.order.delete.success"/>
            <br>
            <h3>${orderDeleteSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.orderDeleteMessageError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="orderDeleteErrorMessage" key="admin.order.delete.failed"/>
            <br>
            <h3>${orderDeleteErrorMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addCategorySuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="categoryAddSuccess" key="admin.add.category.success"/>
            <br>
            <h3>${categoryAddSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addCategoryFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="categoryAddFailed" key="admin.add.category.failed"/>
            <br>
            <h3>${categoryAddFailed}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addDrugSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="drugAddSuccess" key="admin.add.drug.success"/>
            <br>
            <h3>${drugAddSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addDrugFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="drugAddFailed" key="admin.add.drug.failed"/>
            <br>
            <h3>${drugAddFailed}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addDosageSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="dosageAddSuccess" key="admin.add.dosage.success"/>
            <br>
            <h3>${dosageAddSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addDosageFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="dosageAddFailed" key="admin.add.dosage.failed"/>
            <br>
            <h3>${dosageAddFailed}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.userDeleteSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="userDeleteSuccessMessage" key="admin.delete.user.success"/>
            <br>
            <h3>${userDeleteSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.userDeleteFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="userDeleteFailedMessage" key="admin.delete.user.failed"/>
            <br>
            <h3>${userDeleteFailedMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.userSavedSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="userSavedMessage" key="admin.add.user.success"/>
            <br>
            <h3>${userSavedMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.drugDeleteFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="drugDeleteFailedMessage" key="admin.delete.drug.failed"/>
            <br>
            <h3>${drugDeleteFailedMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.drugDeleteSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="drugDeleteSuccessMessage" key="admin.delete.drug.success"/>
            <br>
            <h3>${drugDeleteSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.categoryDeleteSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="categoryDeleteSuccessMessage" key="admin.add.category.delete.success"/>
            <br>
            <h3>${categoryDeleteSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.categoryDeleteFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="categoryDeleteFailedMessage" key="admin.add.category.delete.failed"/>
            <br>
            <h3>${categoryDeleteFailedMessage}</h3>
        </div>
    </c:if>

    <fmt:message var="adminRole" key="admin.users.role.admin"/>
    <h2><ctg:hello role="${adminRole}"/></h2>
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
<c:remove var="orderSuccess" scope="session"/>
<c:remove var="orderFailed" scope="session"/>
<c:remove var="orderDeleteMessageSuccess" scope="session"/>
<c:remove var="orderDeleteMessageError" scope="session"/>
<c:remove var="addCategorySuccess" scope="session"/>
<c:remove var="addCategoryFailed" scope="session"/>
<c:remove var="addDrugSuccess" scope="session"/>
<c:remove var="addDrugFailed" scope="session"/>
<c:remove var="addDosageSuccess" scope="session"/>
<c:remove var="addDosageFailed" scope="session"/>
<c:remove var="userDeleteSuccess" scope="session"/>
<c:remove var="userDeleteFailed" scope="session"/>
<c:remove var="userSavedSuccess" scope="session"/>
<c:remove var="drugDeleteFailed" scope="session"/>
<c:remove var="drugDeleteSuccess" scope="session"/>
<c:remove var="categoryDeleteSuccess" scope="session"/>
<c:remove var="categoryDeleteFailed" scope="session"/>

</body>
</html>

