<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<table>
	<tr>
		<td class="left-block">
			<a href="<c:url value="/dashboard/index"/>"><spring:message code="header.dashboard"/></a>
			<a href="<c:url value="/dashboard/upload"/>"><spring:message code="header.upload-document"/></a>
			<a href="<c:url value="/dashboard/view/all/all"/>"><spring:message code="header.view"/></a>
			<a href="<c:url value="/dashboard/view/pattern/all"/>"><spring:message code="header.view.pattern"/></a>
		</td>
		<td class="right-block">
			<sec:authentication var="principal" property="principal" />
			<spring:message code="header.welcome"/><c:out value="${principal.username}" />! | <a href="/logout"><spring:message code="header.logout"/></a>
		</td>
	</tr>
</table>
<hr>