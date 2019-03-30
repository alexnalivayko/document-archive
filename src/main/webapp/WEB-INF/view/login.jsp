<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><spring:message code="login.page-name"/></title>
        <link type="text/css" rel="stylesheet" href="/static/css/style.css" />
    </head>
    <body>
        <div>
            <h1 class="custom-h1"><spring:message code="login.title"/></h1>
            <form action="<c:url value="/login"/>" method="POST">
                <div><label for="username"><spring:message code="login.username.label"/></label>
                    <input type="text" id="username" name="username">
                </div>
                <div>
                    <label for="pwd"><spring:message code="login.password.label"/></label>
                    <input type="password" id="pwd" name="password">
                </div>

                <button type="submit"><spring:message code="login.submit.button"/></button>
            </form>
        </div>
    </body>
</html>