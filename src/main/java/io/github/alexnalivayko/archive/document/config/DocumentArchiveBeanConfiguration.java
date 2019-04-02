package io.github.alexnalivayko.archive.document.config;

import io.github.alexnalivayko.archive.document.service.implementation.DocumentServiceImpl;
import io.github.alexnalivayko.archive.document.utils.implementation.DefaultFileExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentArchiveBeanConfiguration {

	@Bean
	public DocumentServiceImpl documentService() {
		return new DocumentServiceImpl();
	}

	@Bean
	public DefaultFileExtensionResolver fileExtensionResolver() {
		return new DefaultFileExtensionResolver();
	}
}