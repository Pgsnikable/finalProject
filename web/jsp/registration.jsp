<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 04.01.2019
  Time: 23:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registration</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-light fixed-top">
    <div class="collapse navbar-collapse ml-auto">
        <%@include file="locale.jsp"%>
    </div>
</nav>
    <div class="container mt-5 text-center">
            <div class="text-center pt-5">
                <form class="form-signin pt-5 text-center" method="post" action="${pageContext.request.contextPath}/mainController">
                        <fmt:message var="inputTitle" key="input.login.title"/>
                        <input type="hidden" name="command" value="registration"/>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <fmt:message var="firstName" key="register.firstname"/>
                                <label for="firstName">${firstName}</label>
                                <fmt:message var="firstNamePlaceholder" key="register.firstname.placeholder"/>
                                <input type="text" class="form-control" id="firstName" name="firstName"
                                       placeholder="${firstNamePlaceholder}" required pattern="^[A-Z][a-z]{1,16}$|^[А-Я][а-яё]{1,16}$"
                                       title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')">
                            </div>
                            <div class="col-md-6 mb-3">
                                <fmt:message var="lastName" key="register.lastname"/>
                                <label for="lastName">${lastName}</label>
                                <fmt:message var="lastNamePlaceholder" key="register.lastname.placeholder"/>
                                <input type="text" class="form-control" id="lastName" name="lastName"
                                       placeholder="${lastNamePlaceholder}" required pattern="^[A-Z][a-z]{1,16}$|^[А-Я][а-яё]{1,16}$"
                                       title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')">
                                <div class="invalid-feedback">
                                    Valid last name is required.
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <fmt:message key="login.label" var="login"/>
                                <label class="font-weight-normal" for="login">${login}</label><br/>

                                <fmt:message var="loginPlaceholder" key="login.placeholder"/>
                                <input class="form-control" id="login" type="text" name="login" placeholder="${loginPlaceholder}"
                                       required pattern="^[\w-]{4,31}$" title="${inputTitle}"
                                       oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/><br/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <fmt:message var="password" key="password.label"/>
                                <label class="font-weight-normal" for="password">${password}</label>

                                <fmt:message var="passwordPlaceholder" key="password.placeholder"/>
                                <input class="form-control" id="password" type="password" name="password" placeholder="${passwordPlaceholder}"
                                       required pattern="^[A-Za-z]\w{3,31}$" title="${inputTitle}"
                                       oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="font-weight-normal" for="email">Email*</label>
                                <div class="input-group">
                                    <fmt:message var="emailPlaceholder" key="register.email.placeholder"/>
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">@</span>
                                    </div>
                                    <input class="form-control" id="email" type="email" name="email" placeholder="${emailPlaceholder}"
                                           required pattern="^([\w-\.]{2,15})@((?:[\w-]{0,4}\.){0,1}\w[\w-]{0,3})\.([a-z]{2,6})$"
                                           title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/>
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <fmt:message var="birthday" key="register.birthday"/>
                                <label class="font-weight-normal" for="birthday">${birthday}</label><br/>

                                <fmt:message var="birthdayPlaceholder" key="register.birthday.placeholder"/>
                                <input class="form-control" id="birthday" type="date" name="birthday" placeholder="${birthdayPlaceholder}"
                                       required title="${inputTitle}" value="2019-01-01" min="1900 -01-01" max="2019-01-01"
                                       oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/><br/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <fmt:message var="cardLabel" key="register.card.label"/>
                                <label for="cc-number">${cardLabel}</label>
                                <fmt:message var="cardPlaceholder" key="register.card.placeholder"/>
                                <input type="text" name="cardNumber" class="form-control" id="cc-number" placeholder="${cardPlaceholder}"
                                       required pattern="^\d{16}$" title="${inputTitle}"
                                       oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')">
                            </div>
                        </div>

                        <fmt:message var="updateButton" key="register.button"/>
                        <input class="btn btn-lg btn-primary" type="submit" name="reg" value="${updateButton}"/>

                        <c:if test="${sessionScope.registrationMessage eq 'true'}">
                            <fmt:message var="errorMessage" key="registration.error.message"/>
                            <br>${errorMessage}
                        </c:if>
                    </form>
            </div>
        <div class="fixed-bottom text-center">
            <%@include file="footer.jsp"%>
        </div>
    </div>
    <script src="/resources/js/bootstrap.js"></script>
    <script>
        function loc() {
            var lng = document.getElementById("language").value;
            if (lng === 'ru_RU'){
                return 'Неверный формат';
            } else {
                return 'Incorrect format';
            }
        }
    </script>
</body>
</html>