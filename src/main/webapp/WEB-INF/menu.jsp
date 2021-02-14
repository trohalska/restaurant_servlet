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
            <c:when test="${sessionScope.role=='ROLE_GUEST'}">
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
            <fmt:message key="main.main"/></a>

        <c:if test="${sessionScope.role!='ROLE_GUEST'}">

            <a class="abutton" href="${pageContext.request.contextPath}/app/customer/basket">
                <fmt:message key="main.basket"/></a>

            <a class="abutton" href="${pageContext.request.contextPath}/app/customer/orders">
                <fmt:message key="main.orders"/></a>
        </c:if>

        <c:if test="${sessionScope.role=='ROLE_MANAGER'}">
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
        <a><h2><fmt:message key="main.header"/></h2></a>
    </header>

    <section class="section4"
             ng-app="get_form" ng-controller="GetController" data-ng-init="getItems()">
        <div class="page_head">
            <div>
                <h2><fmt:message key="main.menu"/></h2>
            </div>

<%--            <div ng-model="categories">--%>
<%--                <label for="categories_filter"></label>--%>
<%--                <select id="categories_filter" class="form-styling">--%>
<%--                    <option th:text="#{menu.filter.name}"></option>--%>
<%--                    <option value="0" th:text="#{menu.filter.all}"></option>--%>
<%--                    <option ng-repeat="c in categories" value="{{c.id}}">{{c.category}}</option>--%>
<%--                </select>--%>
<%--                <script type="text/javascript">--%>
<%--                    $(document).ready(function() {--%>
<%--                        $("#categories_filter").change(function () {--%>
<%--                            category = $('#categories_filter').val();--%>
<%--                            page = 1;--%>
<%--                            replace();--%>
<%--                        });--%>
<%--                    });--%>
<%--                </script>--%>
<%--            </div>--%>
        </div>

<%--        <div id="dishes_block">--%>
<%--            <div>--%>
<%--                <table id="table" ng-model="dishes">--%>
<%--                    <tr>--%>
<%--                        <th columns="0" onclick="sorting('id')">№</th>--%>
<%--                        <th columns="1" onclick="sorting('name', this)" th:text="#{menu.dish}"></th>--%>
<%--                        <th columns="2" onclick="sorting('price')" th:text="#{menu.price}"></th>--%>
<%--                        <th columns="3" onclick="sorting('categories')" th:text="#{menu.category}"></th>--%>
<%--                    </tr>--%>
<%--                    <tbody id="dishes_table">--%>
<%--                    <tr ng-repeat="i in dishes" class="rows" checked=false>--%>
<%--                        <td>{{i.id}}</td>--%>
<%--                        <td>{{i.name}}</td>--%>
<%--                        <td>{{i.price}}</td>--%>
<%--                        <td>{{i.category}}</td>--%>
<%--                    </tr>--%>
<%--                    </tbody>--%>
<%--                </table>--%>
<%--            </div>--%>
<%--        </div>--%>

        <div id="dishes_block">
            <div>
                <table id="table">
                    <tr>
                        <th columns="0">№</th>
                        <th columns="1"><fmt:message key="menu.dish"/></th>
                        <th columns="2"><fmt:message key="menu.price"/></th>
                        <th columns="3"><fmt:message key="menu.category"/></th>
                        <c:if test="${sessionScope.role!='ROLE_GUEST'}">
                            <th columns="4"><fmt:message key="basket.action"/></th>
                        </c:if>
                        <c:if test="${sessionScope.role=='ROLE_MANAGER'}">
                            <th columns="5"><fmt:message key="basket.action"/></th>
                        </c:if>

                    </tr>
                    <tbody id="dishes_table">
                    <c:forEach var="dish" items="${requestScope.dishes}">
                        <tr class="rows">
                            <td><c:out value="${dish.id}"/></td>
                            <td><c:out value="${dish.name}"/></td>
                            <td><c:out value="${dish.price}"/></td>
                            <td><c:out value="${dish.categories.category}"/></td>

                            <c:if test="${sessionScope.role!='ROLE_GUEST'}">
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/app/customer/basket/add">
                                        <input name="id" class="hidden" type="number"
                                               value="<c:out value="${dish.id}"/>"/>
                                        <input class="abutton" type="submit" value="<fmt:message key="button.add"/>">
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${sessionScope.role=='ROLE_MANAGER'}">
                                <td>
                                    <form method="put" action="${pageContext.request.contextPath}/app/manager/menu/update">
                                        <input name="id" class="hidden" type="number"
                                               value="<c:out value="${dish.id}"/>"/>
                                        <input class="abutton" type="submit" value="<fmt:message key="button.update"/>">
                                    </form>

                                    <form method="delete" action="${pageContext.request.contextPath}/app/manager/menu/delete">
                                        <input name="id" class="hidden" type="number"
                                               value="<c:out value="${dish.id}"/>"/>
                                        <input class="abutton" type="submit" value="<fmt:message key="button.delete"/>">
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>



<%--        <div class="pages" ng-model="pageable">--%>
<%--            <div th:text="#{page.current} + ' {{pageable.page}}'"></div>--%>

<%--            <div class="pagination">--%>
<%--                <a ng-repeat="x in [].constructor(pageable.totalPages) track by $index"--%>
<%--                   class="abutton"--%>
<%--                   href="/?page={{$index+1}}&sort={{pageable.sortField}}&direct={{pageable.sortDirection}}&category={{pageable.categoryId}}">{{ $index+1 }}</a>--%>
<%--            </div>--%>

<%--            <div th:text="#{page.total} + ' {{pageable.totalPages}}'"></div>--%>
<%--        </div>--%>

<%--        <div class="page_head" sec:authorize="isAuthenticated()">--%>
<%--            <div style="margin: 20px 0;">--%>
<%--                <label for="addDish" th:text="#{main.addDishId}"></label>--%>
<%--                <input id="addDish" ng-model="dishId"/>--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                <a class="button" ng-click="postdata(dishId)" th:text="#{button.add}"></a>--%>
<%--            </div>--%>
<%--        </div>--%>

    </section>

</div>
</body>


</html>
