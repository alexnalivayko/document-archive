<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title><spring:message code="admin.page-name"/></title>
		<link type="text/css" rel="stylesheet" href="/static/css/style.css" />
	</head>
	<body>
		<jsp:include page="include/header.jsp" />
		<h1><spring:message code="admin.title"/></h1>
	</body>
	<div class="footer">
		<jsp:include page="include/footer.jsp" />
	</div>
</html>