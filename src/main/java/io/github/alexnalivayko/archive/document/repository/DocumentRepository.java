package io.github.alexnalivayko.archive.document.repository;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

	Document findByNameLike(String name);

	Document getById(Long id);

	List<Document> findAllByDocumentType(DocumentType documentType);

	List<Document> findAllByDocumentTypeNot(DocumentType documentType);
}