package io.github.alexnalivayko.archive.document.controller;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.service.implementation.DocumentServiceImpl;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class DashboardController {

	private final DocumentServiceImpl documentService;

	private final ServletContext servletContext;

	@GetMapping({"/dashboard/", "/dashboard/index"})
	public String dashboardPage() {
		return "index";
	}

	@GetMapping(path = {"/", "/login"})
	public String loginPage() {
		return "login";
	}

	@GetMapping(path = "/dashboard/upload")
	public String uploadDocumentPage() {
		return "upload";
	}

	@PostMapping("/dashboard/upload")
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
				Document document = documentService.createWithParameters(file.getOriginalFilename(),
						documentType, originalFormatType, null, file.getSize());

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

		return "upload";
	}

	private void fillUploadPage(Map model) {
		model.put("documentTypes", DocumentType.values());
		model.put("originalFormatTypes", OriginalFormatType.values());
	}

	private void fillViewPage(Map model, List<Document> documents) {
		model.put("documents", documents);
	}

	@Autowired
	public DashboardController(DocumentServiceImpl documentService,
							   ServletContext servletContext) {
		this.documentService = documentService;
		this.servletContext = servletContext;
	}
}