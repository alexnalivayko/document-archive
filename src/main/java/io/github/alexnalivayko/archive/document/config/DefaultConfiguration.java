package io.github.alexnalivayko.archive.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
public class DefaultConfiguration {

	/**
	 *  <bean id="localeResolver" class="org.springframework.web.servlet.i18n.CookieLocaleResolver">
	 */
	@Bean(name = "localeResolver")
	public CookieLocaleResolver getLocaleResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("en"));
		cookieLocaleResolver.setCookieMaxAge(100000);

		return cookieLocaleResolver;
	}

	/**
	 * <bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
	 */
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:/locales/messages");
		resource.setCacheSeconds(1);
		resource.setDefaultEncoding("UTF-8");

		return resource;
	}
}