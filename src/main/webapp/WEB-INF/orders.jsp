<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "parse" uri="custom.tld" %>
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
    <script src="${pageContext.request.contextPath}/js/general.js"></script>
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

            <c:if test="${sessionScope.principal.role=='ROLE_MANAGER'}">
                <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/orders_manager">
                    <fmt:message key="main.manage.orders"/></a>
                <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/dishes_manager">
                    <fmt:message key="main.manage.dishes"/></a>
            </c:if>
        </div>
        <div>
            <a id="authorizedLogin">${sessionScope.principal.login}</a>
            <a class="abutton" href="${pageContext.request.contextPath}/app/logout">
                <fmt:message key="sign.out"/></a>
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
        </div>
    </section>

    <header class="section2">
        <a><h2><fmt:message key="main.orders"/></h2></a>
    </header>

    <section class="section4">

        <div id="errorMsg">${requestScope.errorMsg}</div>

        <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">

            <div id="dishes_block">
                <div>
                    <table id="table">
                        <tr>
                            <th columns="0" onclick="tableSort(this, 'table')">№</th>
                            <th columns="1" onclick="tableSort(this, 'table')"><fmt:message key="orders.time"/></th>
                            <th columns="2" onclick="tableSort(this, 'table')"><fmt:message key="orders.status"/></th>
                            <th columns="3" onclick="tableSort(this, 'table')"><fmt:message key="orders.totalPrice"/></th>
                            <th columns="4"><fmt:message key="basket.action"/></th>
                        </tr>
                        <tbody id="dishes_table">
                        <c:forEach var="order" items="${requestScope.orders}">
                            <tr class="rows">
                                <td><c:out value="${order.id}"/></td>
                                <td><parse:parseLocalDateTime value="${order.time}"/></td>
                                <td><c:out value="${order.status}"/></td>
                                <td><c:out value="${order.totalPrice}"/></td>
                                <td>
                                    <c:if test="${order.status=='NEW'}">
                                        <form method="GET"
                                              action="${pageContext.request.contextPath}/app/customer/orders/payment">
                                            <label>
                                                <input name="id" class="hidden" type="text" value="${order.id}"/>
                                            </label>
                                            <input class="abutton" type="submit" value="<fmt:message key="orders.pay"/>">
                                        </form>
                                    </c:if>
                                </td>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:if>
    </section>

</div>
</body>


</html>
