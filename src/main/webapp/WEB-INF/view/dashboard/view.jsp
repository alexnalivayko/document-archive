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
		<c:if test="${success != null}">
			<div style="color: green">
				<h4><spring:message code="view.delete.success.info"/></h4>
				<p><spring:message code="view.delete.filename"/> ${success}</p>
			</div>
		</c:if>
		<c:if test="${error != null}">
			<div style="color: red">
				<h4><spring:message code="view.delete.error.info"/></h4>
				<p><spring:message code="view.delete.filename"/> ${error}</p>
			</div>
		</c:if>
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
				<c:choose>
					<c:when test="${empty documents}">
					<tr>
						<td colspan="7"><spring:message code="view.table.empty"/></td>
					</tr>
					</c:when>
					<c:otherwise>
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
										<c:when test="${document.originalFormatType == 'SCANNER'}">
											<spring:message code="upload.form.select.original-format.scanner"/>
										</c:when>
										<c:when test="${document.originalFormatType == 'PHOTO'}">
											<spring:message code="upload.form.select.original-format.photo"/>
										</c:when>
										<c:when test="${document.originalFormatType == 'PAPER'}">
											<spring:message code="upload.form.select.original-format.paper"/>
										</c:when>
										<c:when test="${document.originalFormatType == 'MAIL'}">
											<spring:message code="upload.form.select.original-format.mail"/>
										</c:when>
										<c:when test="${document.originalFormatType == 'OTHER'}">
											<spring:message code="upload.form.select.original-format.other"/>
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
											<a href="/dashboard/delete/${document.id}">
												<span><spring:message code="view.button.delete"/></span>
											</a>
										</tr>
									</table>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		</div>
	</body>
	<div class="footer">
		<table>
			<tr>
				<td class="right-block"><label><spring:message code="view.table.count"/>${countDocuments}</label></td>
			</tr>
		</table>
		<jsp:include page="include/footer.jsp" />
	</div>
</html>