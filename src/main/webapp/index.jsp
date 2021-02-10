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

    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>--%>

<%--    <script src="${pageContext.request.contextPath}/js/general.js"></script>--%>
<%--    <script src="${pageContext.request.contextPath}/js/main_request.js"></script>--%>

</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <a id="authorizedLogin">${sessionScope.userName}</a>
        <c:choose>
            <c:when test="${sessionScope.userName==null}">
                <a class="abutton" href="${pageContext.request.contextPath}/app/login">
                    <fmt:message key="sign.in"/></a>
                <a class="abutton" href="${pageContext.request.contextPath}/app/registration">
                    <fmt:message key="sign.up"/></a>
            </c:when>
            <c:otherwise>
                <a class="abutton" href="${pageContext.request.contextPath}/app/logout">
                    <fmt:message key="sign.out"/></a>
            </c:otherwise>
        </c:choose>

        <a class="abutton" href="${pageContext.request.contextPath}">
            <fmt:message key="main.menu"/></a>

        <a class="abutton" href="${pageContext.request.contextPath}/customer/basket">
            <fmt:message key="main.basket"/></a>

        <a class="abutton" href="${pageContext.request.contextPath}/customer/orders">
            <fmt:message key="main.orders"/></a>

        <c:if test="${sessionScope.role=='ROLE_MANAGER'}">
            <a class="abutton" href="${pageContext.request.contextPath}/manager/orders">
                <fmt:message key="main.manager"/></a>
        </c:if>

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
        <a><h2><fmt:message key="main.header"/></h2></a>
    </header>

<%--    <br>--%>
<%--    <a class="button" href="${pageContext.request.contextPath}/app/exception">Exception</a>--%>
    <br>
    <br>
    <a class="button" href="${pageContext.request.contextPath}/app/manager/getAll">Watch all users</a>
    <br>
    <br>
    <a class="button" href="${pageContext.request.contextPath}/app/menu">MENU</a>

</div>
</body>


</html>
