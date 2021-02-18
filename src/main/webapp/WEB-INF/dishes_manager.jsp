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
            <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/orders_manager">
                <fmt:message key="main.manage.orders"/></a>
            <a class="mbutton" href="${pageContext.request.contextPath}/app/manager/dishes_manager">
                <fmt:message key="main.manage.dishes"/></a>
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
        <a><h2><fmt:message key="main.manage.dishes"/></h2></a>
    </header>

    <section class="section4">

        <div id="errorMsg">${requestScope.errorMsg}</div>

        <c:if test="${requestScope.errorMsg==null || requestScope.errorMsg==''}">
            <div class="page_head">
                <div>
                    <h2><fmt:message key="main.menu"/></h2>
                </div>
                <div>
                    <a class="button"
                       href="${pageContext.request.contextPath}/app/manager/dish/create">
                        <fmt:message key="button.create"/></a>
                </div>
            </div>

            <div id="dishes_block">
                <div>
                    <table id="table">
                        <tr>
                            <th columns="0" onclick="tableSort(this, 'table')">№</th>
                            <th columns="1" onclick="tableSort(this, 'table')"><fmt:message key="menu.dish"/></th>
                            <th columns="2" onclick="tableSort(this, 'table')"><fmt:message key="menu.price"/></th>
                            <th columns="3" onclick="tableSort(this, 'table')"><fmt:message key="menu.category"/></th>
                            <th columns="4"><fmt:message key="basket.action"/></th>
                        </tr>
                        <tbody id="dishes_table">
                        <c:forEach var="dish" items="${requestScope.dishes}">
                            <tr class="rows">
                                <td><c:out value="${dish.id}"/></td>
                                <td><c:out value="${dish.name}"/></td>
                                <td><c:out value="${dish.price}"/></td>
                                <td><c:out value="${dish.categories.category}"/></td>
                                <td>
                                    <a class="abutton"
                                       href="${pageContext.request.contextPath}/app/manager/dish/update?id=${dish.id}">
                                        <fmt:message key="button.update"/></a>
                                    <a class="abutton" href="#"
                                       onclick="deleteDish('${dish.name}', '${dish.id}')">
                                        <fmt:message key="button.delete"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </section>

</div>
</body>


<script>
    let deleteDish = (name, id) => {
        if (confirm("<fmt:message key="dish.delete.alert"/> " + name)) {
            location.replace("${pageContext.request.contextPath}/app/manager/dish/delete?id=" + id);
        }
    }
</script>

</html>
