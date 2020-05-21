package io.github.alexnalivayko.archive.document.controller;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.model.DocumentGenerator;
import io.github.alexnalivayko.archive.document.service.implementation.DocumentServiceImpl;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import io.github.alexnalivayko.archive.document.utils.MediaTypeUtils;
import io.github.alexnalivayko.archive.document.utils.implementation.DefaultCryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private final DocumentServiceImpl documentService; // В планах отказаться от исп. данного сервиса тут

	private final ServletContext servletContext;
	private final DefaultCryptoUtils defaultCryptoUtils;
	private final Map<String, DocumentGenerator> allDocuments;

	@GetMapping({"/", "/index"})
	public String dashboardPage(Map model) {
		return "dashboard/index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "dashboard/login";
	}

	@GetMapping("/upload")
	public String uploadDocument(Map model) {
		fillUploadPage(model);

		return "dashboard/upload";
	}

	// TODO
	@GetMapping("/patterns")
	public String downloadDocumentPatterns(Map model) {
		model.put("patterns", allDocuments.get("pattern").findAll());

		return "dashboard/patterns";
	}

	@PostMapping("/upload")
	public String sendFileToServer(@RequestParam("fileName") String customFileName,
								   @RequestParam MultipartFile[] uploadFiles,
								   @RequestParam DocumentType documentType,
								   @RequestParam OriginalFormatType originalFormatType,
								   @RequestParam(defaultValue = "false") boolean neededPackageToZip,
								   Map model) {
		if (neededPackageToZip) {
			try {
				File uploadZip = documentService.packageDocumentsToZip(uploadFiles, customFileName);

				FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(uploadZip.toPath()),
						false, uploadZip.getName(), (int) uploadZip.length(), uploadZip.getParentFile());
				IOUtils.copy(new FileInputStream(uploadZip), fileItem.getOutputStream());
				MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

				Document document = documentService.createFromFile(multipartFile, originalFormatType, documentType);
				documentService.setDocumentDirectoryByType(documentType, document);
				documentService.uploadDocument(multipartFile, customFileName, document);

				model.put("success", document.getName());
			} catch (Exception e) {
				log.error("Something wrong = {}", e.getMessage());
				model.put("error", customFileName);
			}
		} else {
			for (MultipartFile file : uploadFiles) {
				Document document = documentService.createFromFile(file, originalFormatType, documentType);

				try {
					documentService.setDocumentDirectoryByType(documentType, document);
					documentService.uploadDocument(file, customFileName, document);

					model.put("success", document.getName());
				} catch (Exception e) {
					log.error("Something wrong = {}", e.getMessage());
					model.put("error", customFileName);
				}
			}
		}

		fillUploadPage(model);

		return "dashboard/upload";
	}

	@GetMapping("/view/{scope}/all")
	public String viewAllDocuments(@PathVariable String scope, Map model) {
		DocumentGenerator documentGenerator = allDocuments.get(scope);

		if (documentGenerator == null) {
			throw new UnsupportedOperationException(scope + " is not supported yet");
		}

		fillViewPage(model, documentGenerator.findAll());

		return "dashboard/view";
	}

	@GetMapping("/delete/{id}")
	public String deleteDocument(@PathVariable Long id, Map model) {
		Document findDoc = documentService.getById(id);

		String filename = findDoc.getName();
		Path pathToDeleteFile = findDoc.getDirectory();

		try {
			FileUtils.forceDelete(new File(pathToDeleteFile + File.separator + filename));
			documentService.deleteById(id);
			model.put("success", filename);
		} catch (IOException e) {
			model.put("error", e.getMessage());
		}

		fillViewPage(model, documentService.getAll());

		return "dashboard/view";
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable("filename") String fileName) throws Exception {
		Document tempDoc = documentService.getByName(fileName);

		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		Path path = Paths.get(tempDoc.getDirectory() + File.separator + tempDoc.getName());

		byte[] decryptDocByte = defaultCryptoUtils.decrypt(Files.readAllBytes(path));
		ByteArrayResource resource = new ByteArrayResource(decryptDocByte);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(mediaType)
				.contentLength(decryptDocByte.length)
				.body(resource);
	}

	private void fillUploadPage(Map model) {
		model.put("documentTypes", DocumentType.values());
		model.put("originalFormats", OriginalFormatType.values());
	}

	private void fillViewPage(Map model, List<Document> documents) {
		model.put("documents", documents);
		model.put("countDocuments", documents.size());
	}

	@PostConstruct
	private void fillDocumentPatterns() {
		documentService.findAndCreateDocumentPatterns();
	}

	@Autowired
	public DashboardController(DocumentServiceImpl documentService,
							   ServletContext servletContext,
							   DefaultCryptoUtils defaultCryptoUtils,
							   Map<String, DocumentGenerator> allDocuments) {
		this.documentService = documentService;
		this.servletContext = servletContext;
		this.defaultCryptoUtils = defaultCryptoUtils;
		this.allDocuments = allDocuments;
	}
}