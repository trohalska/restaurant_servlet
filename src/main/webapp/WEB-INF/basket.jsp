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
    <script src="${pageContext.request.contextPath}/js/general.js"></script>

</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <a id="authorizedLogin">${sessionScope.principal.login}</a>

        <a class="abutton" href="${pageContext.request.contextPath}/app/logout">
            <fmt:message key="sign.out"/></a>
        <a class="abutton" href="${pageContext.request.contextPath}">
            <fmt:message key="main.main"/></a>
<%--        <a class="abutton" href="${pageContext.request.contextPath}/app/menu">--%>
<%--            <fmt:message key="main.menu"/></a>--%>
        <a class="abutton" href="${pageContext.request.contextPath}/app/customer/basket">
            <fmt:message key="main.basket"/></a>
        <a class="abutton" href="${pageContext.request.contextPath}/app/customer/orders">
            <fmt:message key="main.orders"/></a>

        <c:if test="${sessionScope.principal.role=='ROLE_MANAGER'}">
            <a class="abutton" href="${pageContext.request.contextPath}/app/manager/orders_manager">
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
        <a><h2><fmt:message key="main.basket"/></h2></a>
    </header>

    <section class="section4">

        <div id="errorMsg">${requestScope.errorMsg}</div>

        <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">

            <div id="baskets_block">
                <div>
                    <table id="table">
                        <tr>
                            <th columns="0" onclick="tableSort(this, 'table')">№</th>
                            <th columns="1" onclick="tableSort(this, 'table')"><fmt:message key="menu.dish"/></th>
                            <th columns="2" onclick="tableSort(this, 'table')"><fmt:message key="menu.price"/></th>
                            <th columns="3" onclick="tableSort(this, 'table')"><fmt:message key="menu.category"/></th>
                            <th columns="4"><fmt:message key="basket.action"/></th>

                        </tr>
                        <tbody id="baskets_table">
                        <c:forEach var="dish" items="${requestScope.dishes}">
                            <tr class="rows">
                                <td><c:out value="${dish.id}"/></td>
                                <td><c:out value="${dish.name}"/></td>
                                <td><c:out value="${dish.price}"/></td>
                                <td><c:out value="${dish.categories.category}"/></td>
                                <td>
                                    <form method="delete" action="${pageContext.request.contextPath}/app/customer/basket/delete">
                                        <input name="id" class="hidden" type="text"
                                               value="${dish.id}"/>
                                        <input class="abutton" type="submit" value="<fmt:message key="button.delete"/>">
                                    </form>
                                </td>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <div class="page_head">
                <p style="align-items: end;">
                    <span><fmt:message key="orders.totalPrice"/> = </span>
                    <span style="font-weight: 700">${requestScope.totalPrice}</span>
                </p>
            </div>

            <div class="page_head">
                <div style="margin: 20px 0;">
<%--                    <a class="button" href="${pageContext.request.contextPath}/app/customer/orders/create">--%>
<%--                        <fmt:message key="basket.create"/></a>--%>
                    <form method="post" action="${pageContext.request.contextPath}/app/customer/orders/create">
                        <input class="button" type="submit" value="<fmt:message key="basket.create"/>">
                    </form>

                </div>
                <div>
                    <form method="delete" action="${pageContext.request.contextPath}/app/customer/basket/delete_all">
                        <input class="button" type="submit" value="<fmt:message key="basket.deleteAll"/>">
                    </form>
                </div>
            </div>
        </c:if>


    </section>

</div>
</body>


</html>
