package io.github.alexnalivayko.archive.document.service;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collection;

public interface DocumentService extends AbstractService {

	@Override
	Document create();

	Document createWithParameters(String name,
	                              DocumentType documentType,
	                              OriginalFormatType originalFormatType,
	                              Path directory,
	                              Long size);

	@Override
	void deleteById(Long id);

	@Override
	Document getById(Long id);

	@Override
	Document getByName(String name);

	@Override
	Collection getAll();

	void setDocumentDirectoryByType(DocumentType documentType, Document document);

	void uploadDocument(MultipartFile uploadFile, String customFileName, Document document) throws Exception;
}