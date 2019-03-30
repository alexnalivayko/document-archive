package io.github.alexnalivayko.archive.document.service.impl;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.repository.DocumentRepository;
import io.github.alexnalivayko.archive.document.service.DocumentService;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}