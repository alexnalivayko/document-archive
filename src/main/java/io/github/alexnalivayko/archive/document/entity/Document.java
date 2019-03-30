package io.github.alexnalivayko.archive.document.entity;

import io.github.alexnalivayko.archive.document.utils.PathConverter;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = Document.TABLE_NAME)
public class Document extends AbstractEntity implements Serializable {

	public static final String TABLE_NAME = "documents";

	@Column(name = "name")
	private String name;

	@Column(name = "document_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private DocumentType documentType;

	@Column(name = "original_format_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private OriginalFormatType originalFormatType;

	@Column(name = "date_upload", updatable = false)
	private Date dateUpload;

	@Column(name = "directory")
	@Convert(converter = PathConverter.class)
	private Path directory;

	@Column(name = "size", nullable = false)
	private Long size;

	public Document() {
	}

	public Document(String name,
	                DocumentType documentType,
	                OriginalFormatType originalFormatType,
	                Path directory,
	                Long size) {
		this.name = name;
		this.documentType = documentType;
		this.originalFormatType = originalFormatType;
		this.dateUpload = new Date();
		this.directory = directory;
		this.size = size;
	}
}