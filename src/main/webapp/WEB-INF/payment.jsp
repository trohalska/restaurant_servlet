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

</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <a id="authorizedLogin">${sessionScope.principal.login}</a>
        <a class="abutton" href="${pageContext.request.contextPath}">
            <fmt:message key="main.main"/></a>
    </section>

    <header class="section2">
        <a><h2><fmt:message key="payment.header"/></h2></a>
    </header>

    <section class="section4">

        <div id="errorMsg">${requestScope.errorMsg}</div>

        <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">
            <div class="page_head">
                <p style="display: inline-block">
                    <span><fmt:message key="payment.str1"/></span>
                    <span id="orderNo">${requestScope.order.id}</span>
                    <span><fmt:message key="payment.str2"/></span>
                    <span id="totalPrice">${requestScope.order.totalPrice}</span>
                    <span><fmt:message key="payment.str3"/></span>
                </p>
                <div>
                    <form method="POST" action="${pageContext.request.contextPath}/app/customer/orders/payment/pay">
                        <label>
                            <input name="id" class="hidden" type="text" value="${requestScope.order.id}"/>
                        </label>
                        <input class="abutton" type="submit" value="<fmt:message key="payment.button"/>">
                    </form>
                </div>
            </div>
        </c:if>
    </section>

</div>
</body>


</html>
