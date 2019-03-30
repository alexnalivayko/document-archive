package io.github.alexnalivayko.archive.document.config;

import io.github.alexnalivayko.archive.document.service.impl.DocumentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentArchiveBeanConfiguration {

	@Bean
	public DocumentServiceImpl documentService() {
		return new DocumentServiceImpl();
	}
}