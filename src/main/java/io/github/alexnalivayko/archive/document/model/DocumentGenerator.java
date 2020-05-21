package io.github.alexnalivayko.archive.document.model;

import io.github.alexnalivayko.archive.document.entity.Document;

import java.util.List;

public interface DocumentGenerator {
	List<Document> findAll();
}