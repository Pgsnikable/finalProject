<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <link href="/resources/css/carousel.css" rel="stylesheet">
    <link href="/resources/css/feather.css" rel="stylesheet">
    <script src="/resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<%@include file="/jsp/user/userHeader.jsp" %>
<main role="main">
    <c:if test="${sessionScope.orderSuccessMessage eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="successMessage" key="main.success.message.order"/>
            <br>
            <h3>${successMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.orderDeleteMessageSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="deleteSuccessMessage" key="order.delete.success"/>
            <br>
            <h3>${deleteSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.orderDeleteMessageError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="deleteErrorMessage" key="order.delete.message"/>
            <br>
            <h3>${deleteErrorMessage}</h3>
        </div>
    </c:if>
    <c:if test="${requestScope.orderChangeMessageSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="changeOrderSuccess" key="order.change.success"/>
            <br>
            <h3>${changeOrderSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${requestScope.orderChangeMessageError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="changeOrderError" key="order.change.error"/>
            <br>
            <h3>${changeOrderError}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.requestSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestSuccess" key="recipe.request.success"/>
            <br>
            <h3>${requestSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.requestError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestError" key="recipe.request.error"/>
            <br>
            <h3>${requestSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.requestDeleteSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestDeleteSuccess" key="recipe.request.delete.success"/>
            <br>
            <h3>${requestDeleteSuccess}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.requestDeleteError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="requestDeleteError" key="recipe.request.delete.error"/>
            <br>
            <h3>${requestDeleteError}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addBalanceSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="addBalanceSuccessMessage" key="cabinet.balance.success"/>
            <br>
            <h3>${addBalanceSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.addBalanceError eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="addBalanceErrorMessage" key="cabinet.balance.error"/>
            <br>
            <h3>${addBalanceErrorMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.updatedSuccess eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="updatedSuccessMessage" key="cabinet.update.message.success"/>
            <br>
            <h3>${updatedSuccessMessage}</h3>
        </div>
    </c:if>
    <c:if test="${sessionScope.updatedFailed eq 'true'}">
        <div class="container mt-5 pt-5 text-center">
            <fmt:message var="updatedFailedMessage" key="cabinet.update.message.failed"/>
            <br>
            <h3>${updatedFailedMessage}</h3>
        </div>
    </c:if>

    <h2><ctg:hello role="${sessionScope.user.login}"/></h2>

    <div id="myCarousel" class="carousel slide mt-5 pt-5" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="container">
                    <div class="carousel-caption text-left">
                        <fmt:message var="carouselFirstLabel" key="main.carousel.first.label"/>
                        <h1>${carouselFirstLabel}</h1>
                        <fmt:message var="carouselFirstText" key="main.carousel.first.text"/>
                        <p>${carouselFirstText}</p>
                        <fmt:message var="carouselFirstButton" key="main.carousel.first.button"/>
                        <p><a class="btn btn-lg btn-primary"
                              href="${pageContext.request.contextPath}/jsp/user/cabinet.jsp" role="button">${carouselFirstButton}</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="carousel-caption">
                        <fmt:message var="carouselSecondLabel" key="main.carousel.second.label"/>
                        <h1>${carouselSecondLabel}</h1>
                        <fmt:message var="carouselSecondText" key="main.carousel.second.text"/>
                        <p>${carouselSecondText}</p>
                        <fmt:message var="carouselSecondButton" key="main.carousel.second.button"/>
                        <p><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/jsp/user/cart.jsp"
                              role="button">${carouselSecondButton}</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="carousel-caption text-right">
                        <fmt:message var="carouselThirdLabel" key="main.carousel.third.label"/>
                        <h1>${carouselThirdLabel}</h1>
                        <fmt:message var="carouselThirdText" key="main.carousel.third.text"/>
                        <p>${carouselThirdText}</p>
                        <fmt:message var="carouselThirdButton" key="main.carousel.third.button"/>
                        <p><a class="btn btn-lg btn-primary"
                              href="${pageContext.request.contextPath}/jsp/user/updateData.jsp" role="button">${carouselThirdButton}</a></p>
                    </div>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/jquery-3.3.1.min.js"></script>
<c:remove var="orderSuccessMessage" scope="session"/>

<c:remove var="orderDeleteMessageSuccess" scope="session"/>
<c:remove var="orderDeleteMessageError" scope="session"/>
<c:remove var="deleteErrorMessage" scope="request"/>
<c:remove var="requestSuccess" scope="session"/>
<c:remove var="requestError" scope="session"/>
<c:remove var="requestDeleteSuccess" scope="session"/>
<c:remove var="requestDeleteError" scope="session"/>
<c:remove var="addBalanceSuccess" scope="session"/>
<c:remove var="addBalanceError" scope="session"/>

</body>
</html>
