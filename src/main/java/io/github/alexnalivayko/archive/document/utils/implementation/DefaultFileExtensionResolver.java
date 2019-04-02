package io.github.alexnalivayko.archive.document.utils.implementation;

import io.github.alexnalivayko.archive.document.utils.FileExtensionResolver;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Utility class for working with the file extension
 */
public class DefaultFileExtensionResolver implements FileExtensionResolver {

    private static final Pattern ANY_LETTER_REGEX = Pattern.compile(".*[a-zA-Z]+.*");

    /**
     * Method for deep searching file extension
     * (for example, "new-file.doc.log" -> "log")
     *
     * @param fileName - file name
     * @param fullExtension - deep search query
     * @return file extension
     */
    private String evaluateFileExtension(String fileName, boolean fullExtension) {
        if (StringUtils.isNotEmpty(fileName) && fileName.contains(".")) {
            fileName = fileName.toLowerCase();

            String extension = FilenameUtils.getExtension(fileName);
            while (!containsLetter(extension)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                String fileExtension = FilenameUtils.getExtension(fileName);
                if (StringUtils.isNotBlank(fileExtension)) {
                    extension = fileExtension + (fullExtension ? ("." + extension) : "");
                } else {
                    return "";
                }
            }

            return extension;
        }

        return "";
    }

    private boolean containsLetter(String line) {
        return ANY_LETTER_REGEX.matcher(line).matches();
    }

    /**
     * Returns file extension
     *
     * @param fileName - file name
     * @return file extension (no dot)
     */
    @Override
    public String getFileExtension(String fileName) {
        return null;
    }

    /**
     * Returns file extension
     *
     * @param fileName - file name
     * @return file extension (with dot)
     */
    @Override
    public String getFileExtensionWithDot(String fileName) {
        return null;
    }
}