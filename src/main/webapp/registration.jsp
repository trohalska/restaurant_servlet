<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
    <meta charset="utf-8">
    <meta name="author" content="Tetiana Rohalska">
    <meta name="description" content="restaurant">
    <meta name="keywords" content="restaurant, final project, autumn 2020">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>restaurant</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/signup_request.js"></script>
</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">

    <section class="section1">
        <a class="abutton" href="${pageContext.request.contextPath}/app/login">
            <fmt:message key="sign.in"/></a>
        <a class="abutton" href="${pageContext.request.contextPath}">
            <fmt:message key="main.main"/></a>

        <label for="locales"></label>
        <select class="abutton" id="locales">
            <option value=""><fmt:message key="lang.change"/></option>
            <option value="en"><fmt:message key="lang.en"/></option>
            <option value="ua"><fmt:message key="lang.ua"/></option>
        </select>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#locales").change(function () {
                    let selectedOption = $('#locales').val();
                    if (selectedOption !== ''){
                        window.location.replace('?locale=' + selectedOption);
                    }
                });
            });
        </script>
    </section>

    <header class="section2">
        <a href="${pageContext.request.contextPath}">
            <h2><fmt:message key="main.header"/></h2>
        </a>
    </header>

    <section class="section4" ng-app="get_form" ng-controller="GetController">
        <main>
            <div class="frame">

                <div id="errorMsg">${requestScope.errorMsg}</div>

                <form id="formSignUp" class="form-signup">
                    <label for="login"><fmt:message key="sign.username"/></label>
                    <input id="login" class="form-styling" type="text" required autofocus/>

                    <label for="email"><fmt:message key="sign.email"/></label>
                    <input id="email" class="form-styling" type="email" required/>

                    <label for="password"><fmt:message key="sign.password"/></label>
                    <div>
                        <input id="password" class="form-styling" type="password" name="password" autocomplete="on" required/>
                        <span toggle="#password" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                    </div>

                    <div>
                        <label for="confirmpassword"><fmt:message key="sign.confirmPassword"/></label>
                        <input id="confirmpassword" class="form-styling" type="password" name="confirmpassword" required
                               autocomplete="on" />
                        <span toggle="#confirmpassword" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                    </div>

                    <label>
                        <input class="btn-signup" ng-click="signup('${pageContext.request.contextPath}/app/registration')" value="<fmt:message key="sign.up"/>"/>
                    </label>
                </form>
            </div>
        </main>
    </section>

</div>

</body>

<script src="${pageContext.request.contextPath}/js/general.js"></script>

</html>

