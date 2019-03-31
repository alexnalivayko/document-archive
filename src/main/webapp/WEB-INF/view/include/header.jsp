<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
	<tr>
		<td>
			<a href="<c:url value="/dashboard/index"/>"><spring:message code="header.dashboard"/></a>
			<a href="<c:url value="/dashboard/upload"/>"><spring:message code="header.upload-document"/></a>
		</td>
	</tr>
</table>
<hr>