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
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>

</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <div style="display:flex; align-items: center">
            <a class="hbutton" href="${pageContext.request.contextPath}">
                <fmt:message key="main.main"/></a>
            <a class="hbutton" href="${pageContext.request.contextPath}/app/customer/basket">
                <fmt:message key="main.basket"/></a>
            <a class="hbutton" href="${pageContext.request.contextPath}/app/customer/orders">
                <fmt:message key="main.orders"/></a>
            <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/orders_manager">
                <fmt:message key="main.manage.orders"/></a>
            <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/dishes_manager">
                <fmt:message key="main.manage.dishes"/></a>
        </div>
        <div>
            <a id="authorizedLogin">${sessionScope.principal.login}</a>
            <a class="abutton" href="${pageContext.request.contextPath}/app/logout">
                <fmt:message key="sign.out"/></a>
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
                            window.location.replace('?id=${requestScope.dish.id}&locale=' + selectedOption);
                        }
                    });
                });
            </script>
        </div>
    </section>

    <header class="section2">
        <a><h2><fmt:message key="dish.update"/></h2></a>
    </header>

    <section class="section4">
        <main>
            <div class="frame">

                <div id="errorMsg">${requestScope.errorMsg}</div>

                <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">
                    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/app/manager/dish/update/do">
                        <label for="id"></label>
                        <input name="id" id="id" class="hidden" type="text" value="${requestScope.dish.id}"/>

                        <label for="name_en"><fmt:message key="dish.name_en"/></label>
                        <input name="name_en" id="name_en" class="form-styling" type="text"
                               value="${requestScope.dish.nameEn}" required autofocus/>

                        <label for="name_ua"><fmt:message key="dish.name_ua"/></label>
                        <input name="name_ua" id="name_ua" class="form-styling" type="text"
                               value="${requestScope.dish.nameUa}" required/>

                        <label for="price"><fmt:message key="menu.price"/></label>
                        <input name="price" id="price" class="form-styling" type="number" required
                               min="0.01" max="1000.00" step="0.01" value="${requestScope.dish.price}"/>

                        <input class="btn-signin" type="submit" value="<fmt:message key="button.update"/>">
                    </form>
                </c:if>

            </div>
        </main>

    </section>

</div>
</body>


</html>