<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><spring:message code="login.page-name"/></title>
        <link type="text/css" rel="stylesheet" href="/static/css/style.css" />
    </head>
    <body>
        <div class="login-form">
            <h1><spring:message code="login.title"/></h1>
            <form action="<c:url value="/dashboard/login"/>" method="POST">
                <div>
                    <input class="login-input" type="text" id="username" name="username" placeholder="<spring:message code="login.username.label"/>">
                </div>
                <div>
                    <input class="login-input" type="password" id="pwd" name="password" placeholder="<spring:message code="login.password.label"/>">
                </div>

                <button type="submit"><spring:message code="login.submit.button"/></button>
            </form>
            <div class="footer">
                <jsp:include page="include/footer.jsp" />
            </div>
        </div>
    </body>
</html>