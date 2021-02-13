<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</head>
<body>

<%--<h1>Вход в систему</h1><br/>--%>
<%--<form method="get" action="${pageContext.request.contextPath}/app/login">--%>

<%--    <input type="text" name="name"><br/>--%>
<%--    <input type="password" name="pass"><br/><br/>--%>
<%--    <input class="button" type="submit" value="Войти">--%>

<%--</form>--%>
<%--<br/>--%>
<%--<a href="${pageContext.request.contextPath}/app/logout">На головну</a>--%>

    <div class="backcolor"></div>
    <div class="body-block shadow-large page_width">

        <section class="section1">
            <a class="abutton" href="${pageContext.request.contextPath}/app/registration">
                <fmt:message key="sign.up"/></a>
            <a class="abutton" href="${pageContext.request.contextPath}">
                <fmt:message key="main.main"/></a>

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

        <section class="section4">
            <main>
                <div class="frame">
                    <div id="errorMsg">${requestScope.errorMsg}</div>

                    <form class="form-signin" method="post" action="${pageContext.request.contextPath}/app/login">
                        <label for="username"><fmt:message key="sign.username"/></label>
                        <input name="username" id="username" class="form-styling" type="text" required autofocus/>
                        <label for="password"><fmt:message key="sign.password"/></label>
                        <div>
                            <input name="password" id="password" class="form-styling" type="password" autocomplete="on" required/>
                            <span toggle="#password" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                        </div>
                        <input class="btn-signin" type="submit" value="<fmt:message key="sign.in"/>">
                    </form>
                </div>
            </main>
        </section>
    </div>

</body>

<script src="${pageContext.request.contextPath}/js/general.js"></script>

<%--<script>--%>
<%--    let init = () => {--%>
<%--        let urlParams = new URLSearchParams(window.location.search);--%>
<%--        if (urlParams.get('error') !== null) {--%>
<%--            alert('incorrect login or password!');--%>
<%--        }--%>
<%--    }--%>
<%--    window.onload = init;--%>
<%--</script>--%>

</html>