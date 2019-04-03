<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title><spring:message code="view.documents.page-name"/></title>
		<link type="text/css" rel="stylesheet" href="/static/css/style.css" />
	</head>
	<body>
		<jsp:include page="include/header.jsp" />
		<h1><spring:message code="view.documents.title"/></h1>
		<div>
			<table class="view-tg">
			<thead style="background-color: #dddddd">
				<tr>
					<th><spring:message code="view.table.document-name.tr"/></th>
					<th><spring:message code="view.table.directory.tr"/></th>
					<th><spring:message code="view.table.date.tr"/></th>
					<th><spring:message code="view.table.size.tr"/></th>
					<th><spring:message code="view.table.type.tr"/></th>
					<th><spring:message code="view.table.format.tr"/></th>
					<th><spring:message code="view.table.option.tr"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="document" items="${documents}" varStatus="loop">
				<tr>
					<td>${document.name}</td>
					<td>${document.directory}</td>
					<td>${document.dateUpload}</td>
					<td>${document.size}</td>
					<td>
						<c:choose>
							<c:when test="${document.documentType == 'INVOICE'}">
								<spring:message code="upload.form.select.document-type.invoice"/>
							</c:when>
							<c:when test="${document.documentType == 'PACKING_LIST'}">
								<spring:message code="upload.form.select.document-type.packing-list"/>
							</c:when>
							<c:when test="${document.documentType == 'BILL_FOR_PAYMENT'}">
								<spring:message code="upload.form.select.document-type.bill-for-payment"/>
							</c:when>
							<c:when test="${document.documentType == 'CONTRACT'}">
								<spring:message code="upload.form.select.document-type.contract"/>
							</c:when>
							<c:when test="${document.documentType == 'ACCEPTANCE_ACT'}">
								<spring:message code="upload.form.select.document-type.acceptance-act"/>
							</c:when>
							<c:when test="${document.documentType == 'PAYMENT'}">
								<spring:message code="upload.form.select.document-type.payment"/>
							</c:when>
							<c:when test="${document.documentType == 'FOUNDING_DOCUMENT'}">
								<spring:message code="upload.form.select.document-type.founding-document"/>
							</c:when>
							<c:when test="${document.documentType == 'PROTOCOL'}">
								<spring:message code="upload.form.select.document-type.protocol"/>
							</c:when>
							<c:when test="${document.documentType == 'DECREE'}">
								<spring:message code="upload.form.select.document-type.decree"/>
							</c:when>
							<c:when test="${document.documentType == 'OTHER'}">
								<spring:message code="upload.form.select.document-type.other"/>
							</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${document.originalFormatType == 'ELECTRONIC'}">
								<spring:message code="upload.form.select.original-format.electronic"/>
							</c:when>
							<c:when test="${document.originalFormatType == 'PAPER'}">
								<spring:message code="upload.form.select.original-format.paper"/>
							</c:when>
						</c:choose>
					</td>
					<td>
						<table>
							<tr>
								<a href="/dashboard/download/${document.name}">
									<span><spring:message code="view.button.download"/></span>
								</a>
							</tr>
							<tr>
								<a href="#" class="modalInput" data-toggle="modal" data-target="#modal-delete-doc"
								   rev="/dashboard/delete/${document.id}">
									<span><spring:message code="view.button.delete"/></span>
								</a>
							</tr>
						</table>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</body>
	<div class="footer">
		<jsp:include page="include/footer.jsp" />
	</div>
</html>