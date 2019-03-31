package io.github.alexnalivayko.archive.document.service.impl;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.repository.DocumentRepository;
import io.github.alexnalivayko.archive.document.service.DocumentService;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import io.github.alexnalivayko.archive.document.utils.PathConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;


	@Override
	public Document create() {
		return new Document();
	}

	@Override
	public Document createWithParameters(String name,
	                                     DocumentType documentType,
	                                     OriginalFormatType originalFormatType,
	                                     Path directory,
	                                     Long size) {
		return new Document(name, documentType, originalFormatType, directory, size);
	}

	@Override
	public void deleteById(Long id) {
		documentRepository.deleteById(id);
	}

	@Override
	public Document getById(Long id) {
		return documentRepository.getById(id);
	}

	@Override
	public Document getByName(String name) {
		return documentRepository.findByNameLike(name);
	}

	@Override
	public List getAll() {
		return Collections.singletonList(documentRepository.findAll());
	}

	@Override
	public void setDocumentDirectoryByType(DocumentType documentType, Document document) {
		switch (documentType) {
			case INVOICE:
				document.setDirectory(PathConverter.getDocPathByType("Invoices"));
				break;
			case PACKING_LIST:
				document.setDirectory(PathConverter.getDocPathByType("Packing lists"));
				break;
			case BILL_FOR_PAYMENT:
				document.setDirectory(PathConverter.getDocPathByType("Bills for payment"));
				break;
			case CONTRACT:
				document.setDirectory(PathConverter.getDocPathByType("Contracts"));
				break;
			case ACCEPTANCE_ACT:
				document.setDirectory(PathConverter.getDocPathByType("Acceptance acts"));
				break;
			case FOUNDING_DOCUMENT:
				document.setDirectory(PathConverter.getDocPathByType("Founding documents"));
				break;
			case PROTOCOL:
				document.setDirectory(PathConverter.getDocPathByType("Protocols"));
				break;
			case DECREE:
				document.setDirectory(PathConverter.getDocPathByType("Decrees"));
				break;
			case OTHER:
				document.setDirectory(PathConverter.getDocPathByType("Others"));
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public void uploadDocument(MultipartFile uploadFile, String customFileName, Document document) throws Exception {

		documentRepository.save(document);
	}


}