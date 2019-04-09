package io.github.alexnalivayko.archive.document.utils;

public interface CryptoUtils {

    byte[] encrypt(byte[] originalBytes) throws Exception;

    byte[] decrypt(byte[] originalBytes) throws Exception;
}