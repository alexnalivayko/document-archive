package io.github.alexnalivayko.archive.document.utils;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeUtils {

	// abc.zip
	// abc.pdf,..
	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		// application/pdf
		// application/xml
		// image/gif, ...
		String mineType = servletContext.getMimeType(fileName) + ";charset=UTF-8";
		try {
			return MediaType.parseMediaType(mineType);
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

}
