package io.github.alexnalivayko.archive.document.utils;

public interface FileExtensionResolver {
    String getFileExtension(String fileName);

    String getFileExtensionWithDot(String fileName);
}
