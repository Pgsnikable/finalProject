<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 09.01.2019
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <link href="/resources/css/feather.css" rel="stylesheet">
</head>
<body class="bg-light">
<%@include file="/jsp/user/userHeader.jsp" %>
<main role="main">

    <div class="mt-5 px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <div class="row">
            <fmt:message var="cabinetLabel" key="cabinet.label"/>
            <h3 class="h3">${cabinetLabel}</h3>
        </div>
        <div class="row">
            <div class="col-4">
                <div class="list-group" id="cabinetTab" role="tablist">
                    <fmt:message var="loginLabel" key="login.label"/>
                    <fmt:message var="firstName" key="register.firstname"/>
                    <fmt:message var="lastName" key="register.lastname"/>
                    <fmt:message var="birthday" key="register.birthday"/>
                    <fmt:message var="balance" key="cabinet.balance"/>
                    <fmt:message var="cardNumber" key="register.card.label"/>
                    <a class="list-group-item list-group-item-action active" id="list-login-list" data-toggle="list"
                       href="#list-login" role="tab" aria-controls="login">${loginLabel}</a>
                    <a class="list-group-item list-group-item-action" id="list-firstName-list" data-toggle="list"
                       href="#list-firstName" role="tab" aria-controls="firstName">${firstName}</a>
                    <a class="list-group-item list-group-item-action" id="list-lastName-list" data-toggle="list"
                       href="#list-lastName" role="tab" aria-controls="lastName">${lastName}</a>
                    <a class="list-group-item list-group-item-action" id="list-birthday-list" data-toggle="list"
                       href="#list-birthday" role="tab" aria-controls="birthday">${birthday}</a>
                    <a class="list-group-item list-group-item-action" id="list-email-list" data-toggle="list"
                       href="#list-email" role="tab" aria-controls="email">Email</a>
                    <a class="list-group-item list-group-item-action" id="list-balance-list" data-toggle="list"
                       href="#list-balance" role="tab" aria-controls="balance">${balance}</a>
                    <a class="list-group-item list-group-item-action" id="list-cardNumber-list" data-toggle="list"
                       href="#list-cardNumber" role="tab" aria-controls="cardNumber">${cardNumber}</a>

                </div>
            </div>
            <div class="col-8">
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="list-login" role="tabpanel"
                         aria-labelledby="list-login-list"><h4 class="font-italic">${sessionScope.user.login}</h4></div>
                    <div class="tab-pane fade" id="list-firstName" role="tabpanel"
                         aria-labelledby="list-firstName-list"><h4
                            class="font-italic">${sessionScope.userData.firstName}</h4></div>
                    <div class="tab-pane fade" id="list-lastName" role="tabpanel"
                         aria-labelledby="list-lastName-list"><h4
                            class="font-italic">${sessionScope.userData.lastName}</h4></div>
                    <div class="tab-pane fade" id="list-birthday" role="tabpanel"
                         aria-labelledby="list-birthday-list"><h4
                            class="font-italic">${sessionScope.userData.dateOfBirth}</h4></div>
                    <div class="tab-pane fade" id="list-email" role="tabpanel"
                         aria-labelledby="list-email-list"><h4 class="font-italic">${sessionScope.userData.email}</h4>
                    </div>
                    <div class="tab-pane fade" id="list-balance" role="tabpanel"
                         aria-labelledby="list-balance-list"><h4
                            class="font-italic">${sessionScope.userPaymentData.balance} BYN</h4></div>
                    <div class="tab-pane fade" id="list-cardNumber" role="tabpanel"
                         aria-labelledby="list-cardNumber-list"><h4
                            class="font-italic">${sessionScope.userPaymentData.cardNumber}</h4></div>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="container text-center">
                <form class="mt-2 mt-md-0 p-2" method="post"
                      action="${pageContext.request.contextPath}/jsp/user/updateData.jsp">
                    <fmt:message var="updateData" key="cabinet.update.data"/>
                    <button class="btn btn-lg btn-outline-success my-2 my-sm-0" type="submit">${updateData}</button>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="container text-center">
                <form class="mt-2 mt-md-0 p-2" method="post"
                      action="${pageContext.request.contextPath}/mainController">
                    <input type="hidden" name="command" value="add_balance">
                    <fmt:message var="addBalance" key="cabinet.balance.button"/>
                    <button class="btn btn-lg btn-info my-2 my-sm-0" type="submit">${addBalance}</button>
                </form>

            </div>
        </div>
    </div>
    <%@include file="/jsp/footer.jsp" %>
</main>
<script src="/resources/js/bootstrap.js"></script>
</body>
</html>
