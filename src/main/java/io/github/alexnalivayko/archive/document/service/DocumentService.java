package io.github.alexnalivayko.archive.document.service;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface DocumentService extends AbstractService<Document> {

	@Override
	Document create();

	Document createWithParameters(String name,
								  DocumentType documentType,
								  OriginalFormatType originalFormatType,
								  Path directory,
								  Long size);

	Document createFromFile(MultipartFile uploadFile,
							OriginalFormatType originalFormatType,
							DocumentType documentType);

	void uploadDocument(MultipartFile file,
						String customFileName,
						Document document) throws Exception;

	@Override
	void deleteById(Long id);

	@Override
	Document getById(Long id);

	@Override
	Document getByName(String name);

	@Override
	Collection<Document> getAll();

	List<Document> getAllDocumentsByType(DocumentType type);

	File packageDocumentsToZip(MultipartFile[] uploadFiles, String archiveName) throws Exception;

	void setDocumentDirectoryByType(DocumentType documentType, Document document);

	File convertMultipartFileToFile(MultipartFile file) throws IOException;
}