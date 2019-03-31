<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><spring:message code="upload.page-name"/></title>
        <link type="text/css" rel="stylesheet" href="/static/css/style.css"/>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1><spring:message code="upload.title"/></h1>
        <form role="form" method="POST" enctype="multipart/form-data" action="<c:url value="/dashboard/upload"/>">
            <label for="inputName"><spring:message code="upload.form.filename"/></label>
            <input type="text" name="fileName" class="form-control" id="inputName">
            <label><spring:message code="upload.form.document-type"/></label>
            <select name="documentType" class="form-control">
                <option value="INVOICE">
                    <spring:message code="upload.form.select.document-type.invoice"/>
                </option>
                <option value="PACKING_LIST">
                    <spring:message code="upload.form.select.document-type.packing-list"/>
                </option>
                <option value="BILL_FOR_PAYMENT">
                    <spring:message code="upload.form.select.document-type.bill-for-payment"/>
                </option>
                <option value="CONTRACT">
                    <spring:message code="upload.form.select.document-type.contract"/>
                </option>
                <option value="ACCEPTANCE_ACT">
                    <spring:message code="upload.form.select.document-type.acceptance-act"/>
                </option>
                <option value="FOUNDING_DOCUMENT">
                    <spring:message code="upload.form.select.document-type.founding-document"/>
                </option>
                <option value="PROTOCOL">
                    <spring:message code="upload.form.select.document-type.protocol"/>
                </option>
                <option value="DECREE">
                    <spring:message code="upload.form.select.document-type.decree"/>
                </option>
                <option value="OTHER">
                    <spring:message code="upload.form.select.document-type.other"/>
                </option>
            </select>
            <label><spring:message code="upload.form.original-format"/></label>
            <select name="originalFormatType" class="form-control">
                <option value="ELECTRONIC">
                    <spring:message code="upload.form.select.original-format.electronic"/>
                </option>
                <option value="PAPER">
                    <spring:message code="upload.form.select.original-format.paper"/>
                </option>
            </select>
            <label for="inputFile"><spring:message code="upload.form.file"/></label>
            <input type="file" name="uploadFiles" id="inputFile" multiple>
            <label>
                <input name="neededPackageToZip" type="checkbox"><spring:message code="upload.form.checkbox.zip"/>
            </label>
            <button type="submit" class="btn btn-primary"><spring:message code="upload.form.submit-button"/></button>
        </form>
    </body>
    <div class="footer">
        <jsp:include page="include/footer.jsp"/>
    </div>
</html>
