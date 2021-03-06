<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
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

<div class="backcolor"></div>
<div class="body-block shadow-large page_width">
    <section class="section1">
        <div>
            <a class="hbutton" href="${pageContext.request.contextPath}">
                <fmt:message key="main.main"/></a>
        </div>
        <div>
            <a id="authorizedLogin">${sessionScope.principal.login}</a>
        </div>
    </section>

    <header class="section2">
        <a><h2><fmt:message key="error.page"/></h2></a>
    </header>

    <section class="section4">
        <main>
            <div class="frame">
                <h2 style="color: red; text-align: center">
                    ${pageContext.exception.toString().substring(pageContext.exception.toString().indexOf(':') + 2)}
                </h2>
<%--                <br>--%>
<%--                <table>--%>
<%--                    <tr>--%>
<%--                        <td><b>Error:</b></td>--%>
<%--                        <td>${pageContext.exception}</td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td><b>URI:</b></td>--%>
<%--                        <td>${pageContext.errorData.requestURI}</td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td><b>Status code:</b></td>--%>
<%--                        <td>${pageContext.errorData.statusCode}</td>--%>
<%--                    </tr>--%>

                    <%--    <tr valign = "top">--%>
                    <%--        <td><b>Stack trace:</b></td>--%>
                    <%--        <td>--%>
                    <%--            <c:forEach var = "trace" items = "${pageContext.exception.stackTrace}">--%>
                    <%--                <p>${trace}</p>--%>
                    <%--            </c:forEach>--%>
                    <%--        </td>--%>
                    <%--    </tr>--%>
<%--                </table>--%>
            </div>
        </main>
    </section>
</div>
</body>

</html>