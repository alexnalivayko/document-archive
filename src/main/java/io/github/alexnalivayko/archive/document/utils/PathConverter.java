package io.github.alexnalivayko.archive.document.utils;

import javax.persistence.AttributeConverter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathConverter implements AttributeConverter<Path, String> {

	@Override
	public String convertToDatabaseColumn(Path path) {
		return path.toString();
	}

	@Override
	public Path convertToEntityAttribute(String path) {
		return Paths.get(path);
	}

	public static Path getDocPathByType(String dir) {
		return Paths.get(System.getProperty("user.home")
				+ File.separator
				+ "Documents"
				+ File.separator
				+ "Document Archive"
				+ File.separator
				+ dir);
	}
}