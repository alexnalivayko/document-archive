package io.github.alexnalivayko.archive.document.service.implementation;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.repository.DocumentRepository;
import io.github.alexnalivayko.archive.document.service.DocumentService;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import io.github.alexnalivayko.archive.document.utils.PathConverter;
import io.github.alexnalivayko.archive.document.utils.implementation.DefaultCryptoUtils;
import io.github.alexnalivayko.archive.document.utils.implementation.DefaultFileExtensionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DefaultCryptoUtils defaultCryptoUtils;

	@Autowired
	private DefaultFileExtensionResolver extensionResolver;

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
		return Document.builder()
				.name(name)
				.documentType(documentType)
				.originalFormatType(originalFormatType)
				.directory(directory)
				.size(size)
				.build();
	}

	@Override
	public Document createFromFile(MultipartFile uploadFile,
	                               OriginalFormatType originalFormatType,
	                               DocumentType documentType) {
		return Document.builder()
				.documentType(documentType)
				.originalFormatType(originalFormatType)
				.size(uploadFile.getSize())
				.build();
	}

	@Override
	public void uploadDocument(MultipartFile uploadFile,
	                           String customFileName,
	                           Document document) throws Exception {
		byte[] docBytes = uploadFile.getBytes();

		if (customFileName == null || customFileName.equals("")) {
			document.setName(uploadFile.getOriginalFilename());
		} else {
			document.setName(customFileName + extensionResolver.getFileExtensionWithDot(uploadFile.getOriginalFilename()));
		}

		File dir = new File(document.getDirectory().toString());

		if (!dir.exists()) {
			dir.mkdirs();
		}

		File finalFile = new File(dir.getAbsolutePath() + File.separator + document.getName());
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(finalFile));
		stream.write(defaultCryptoUtils.encrypt(docBytes));
		documentRepository.save(document);
		stream.flush();
		stream.close();
	}

	@Override
	public void deleteById(Long id) {
		Document document = documentRepository.getById(id);

		documentRepository.delete(document);
		log.info("Document [id={}, name={}] has been deleted.", document.getId(), document.getName());
	}

	@Override
	public File packageDocumentsToZip(MultipartFile[] uploadFiles, String customFileName) throws Exception {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));

		if (customFileName.equals("")) {
			customFileName = "ZIP_" +
					uploadFiles.length +
					"_" +
					"DATE_" +
					new SimpleDateFormat("dd_MM_YYYY_HH_mm").format(new Date());
		}

		if (!customFileName.endsWith(".zip")) {
			customFileName = customFileName.concat(".zip");
		}

		File zipFile = new File(tempDir, customFileName);

		FileOutputStream fos = new FileOutputStream(zipFile);

		try (ZipOutputStream zos = new ZipOutputStream(fos)) {
			for (MultipartFile file : uploadFiles) {
				zos.putNextEntry(new ZipEntry(Objects.requireNonNull(file.getOriginalFilename())));

				try (FileInputStream fis = new FileInputStream(convertMultipartFileToFile(file))) {
					byte[] buff = new byte[1024];
					int length;
					while (-1 != (length = fis.read(buff))) {
						zos.write(buff, 0, length);
					}
				}
				zos.closeEntry();
			}
		}

		return zipFile;
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
	public List<Document> getAll() {
		return documentRepository.findAllByDocumentTypeNot(DocumentType.PATTERN);
	}

	// TODO Method is called 2 times
	@PostConstruct
	private void fillDocumentPatterns() {
		File pathToDocumentPattern = new File(String.valueOf(PathConverter.getDocPathByType("Patterns")));

		if (pathToDocumentPattern.isDirectory()) {
			for (File pattern : Objects.requireNonNull(pathToDocumentPattern.listFiles())) {
				Document tempDoc = documentRepository.findByNameLike(pattern.getName());

				if (tempDoc == null) {
					tempDoc = Document.builder()
							.name(pattern.getName())
							.documentType(DocumentType.PATTERN)
							.originalFormatType(OriginalFormatType.ELECTRONIC)
							.directory(PathConverter.getDocPathByType("Patterns"))
							.size(pattern.length())
							.build();
					documentRepository.save(tempDoc);
				} else {
					log.info("This document {} will be added before!", tempDoc.getName());
				}
			}
		}
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
	public List<Document> getAllDocumentsByType(DocumentType type) {
		return documentRepository.findAllByDocumentType(type);
	}

	@Override
	public File convertMultipartFileToFile(MultipartFile convertFile) throws IOException {
		if (convertFile.getOriginalFilename() != null) {
			File tempFile = new File(convertFile.getOriginalFilename());
			//tempFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(convertFile.getBytes());
			fos.close();

			return tempFile;
		} else throw new IOException();
	}

}