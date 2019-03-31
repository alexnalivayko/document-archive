package io.github.alexnalivayko.archive.document.controller;

import io.github.alexnalivayko.archive.document.entity.Document;
import io.github.alexnalivayko.archive.document.service.impl.DocumentServiceImpl;
import io.github.alexnalivayko.archive.document.type.DocumentType;
import io.github.alexnalivayko.archive.document.type.OriginalFormatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Controller
public class DashboardController {

	private final DocumentServiceImpl documentService;

	@GetMapping(path = "/dashboard/index")
	public String indexPage() {
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

		return "upload";
	}

	@Autowired
	public DashboardController(DocumentServiceImpl documentService) {
		this.documentService = documentService;
	}
}