package io.github.alexnalivayko.archive.document.model;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.service.DocumentService;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("payment")
public class PaymentDocumentGenerator implements DocumentGenerator {

	private final DocumentService documentService;

	@Override
	public List<Document> findAll() {
		return documentService.getAllDocumentsByType(DocumentType.PAYMENT);
	}

	public PaymentDocumentGenerator(DocumentService documentService) {
		this.documentService = documentService;
	}
}