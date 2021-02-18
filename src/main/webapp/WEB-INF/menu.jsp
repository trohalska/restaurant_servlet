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
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css"/>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
</head>

<body>
<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <div style="display:flex; align-items: center">
            <a class="hbutton" href="${pageContext.request.contextPath}">
                <fmt:message key="main.main"/></a>

            <c:if test="${sessionScope.principal.role!='ROLE_GUEST'}">
                <a class="hbutton" href="${pageContext.request.contextPath}/app/customer/basket">
                    <fmt:message key="main.basket"/></a>
                <a class="hbutton" href="${pageContext.request.contextPath}/app/customer/orders">
                    <fmt:message key="main.orders"/></a>
            </c:if>

            <c:if test="${sessionScope.principal.role=='ROLE_MANAGER'}">
                <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/orders_manager">
                    <fmt:message key="main.manage.orders"/></a>
                <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/dishes_manager">
                    <fmt:message key="main.manage.dishes"/></a>
            </c:if>
        </div>
        <div>
            <a id="authorizedLogin">${sessionScope.principal.login}</a>
            <c:choose>
                <c:when test="${sessionScope.principal.role=='ROLE_GUEST'}">
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
                            window.location.replace(
                                "${pageContext.request.contextPath}/app/menu" +
                                "?page=${requestScope.pageNo}&sort=${requestScope.sort}" +
                                "&direct=${requestScope.direct}&category=${requestScope.category}" +
                                '&locale=' + selectedOption);
                        }
                    });
                });
            </script>
        </div>
    </section>

    <header class="section2">
        <a><h2><fmt:message key="main.header"/></h2></a>
    </header>

    <section class="section4">

        <div id="errorMsg">${requestScope.errorMsg}</div>

        <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">
            <div class="page_head">
                <div>
                    <h2><fmt:message key="main.menu"/></h2>
                </div>
                <div>
                    <label for="categories_filter"></label>
                    <select id="categories_filter" class="form-styling">
                        <option><fmt:message key="menu.filter.name"/></option>
                        <option value="0"><fmt:message key="menu.filter.all"/></option>
                        <c:forEach var="c" items="${requestScope.categories}">
                            <option value="${c.id}">${c.category}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $(document).ready(function() {
                            $("#categories_filter").change(function () {
                                location.replace("${pageContext.request.contextPath}/app/menu?page=1&sort=${requestScope.sort}&direct=${requestScope.direct}&category="
                                    + $('#categories_filter').val());
                            });
                        });
                    </script>
                </div>
            </div>

            <div id="dishes_block">
                <div>
                    <table id="table">
                        <tr>
                            <th columns="0">
                                <a href="${pageContext.request.contextPath}/app/menu?page=${requestScope.pageNo}&sort=id&direct=${requestScope.directTable}&category=${requestScope.category}">
                                    №</a></th>
                            <th columns="1">
                                <a href="${pageContext.request.contextPath}/app/menu?page=${requestScope.pageNo}&sort=${requestScope.name}&direct=${requestScope.directTable}&category=${requestScope.category}">
                                    <fmt:message key="menu.dish"/></a></th>
                            <th columns="2">
                                <a href="${pageContext.request.contextPath}/app/menu?page=${requestScope.pageNo}&sort=price&direct=${requestScope.directTable}&category=${requestScope.category}">
                                    <fmt:message key="menu.price"/></a></th>
                            <th columns="3">
                                <a href="${pageContext.request.contextPath}/app/menu?page=${requestScope.pageNo}&sort=category_id&direct=${requestScope.directTable}&category=${requestScope.category}">
                                    <fmt:message key="menu.category"/></a></th>
                            <c:if test="${sessionScope.principal.role!='ROLE_GUEST'}">
                                <th columns="4"><fmt:message key="basket.action"/></th>
                            </c:if>
                        </tr>
                        <tbody id="dishes_table">
                        <c:forEach var="dish" items="${requestScope.dishes}">
                            <tr class="rows">
                                <td><c:out value="${dish.id}"/></td>
                                <td><c:out value="${dish.name}"/></td>
                                <td><c:out value="${dish.price}"/></td>
                                <td><c:out value="${dish.categories.category}"/></td>

                                <c:if test="${sessionScope.principal.role!='ROLE_GUEST'}">
                                    <td>
                                        <form method="POST"
                                              action="${pageContext.request.contextPath}/app/customer/basket/add">
                                            <label>
                                                <input name="id" class="hidden" type="number"
                                                       value="<c:out value="${dish.id}"/>"/>
                                            </label>
                                            <input class="abutton" type="submit" value="<fmt:message key="button.add"/>">
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="pages">
                <div><fmt:message key="page.current"/> ${requestScope.pageNo}</div>

                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${requestScope.totalPages}" step="1">
                        <a class="abutton"
                           href="${pageContext.request.contextPath}/app/menu?page=${i}&sort=${requestScope.sort}&direct=${requestScope.direct}&category=${requestScope.category}">
                            ${i}
                        </a>
                    </c:forEach>
                </div>

                <div><fmt:message key="page.total"/> ${requestScope.totalPages}</div>
            </div>

        </c:if>
    </section>
</div>
</body>
</html>
