package io.github.alexnalivayko.archive.document.utils.implementation;

import io.github.alexnalivayko.archive.document.utils.CryptoUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Slf4j
@Getter
public class DefaultCryptoUtils implements CryptoUtils {

    private SecretKey myDesKey;
    private Cipher desCipher;

    @PostConstruct
    private void generateKeys() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            myDesKey = keyGenerator.generateKey();
            desCipher = Cipher.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encrypt(byte[] originalBytes) throws Exception {
        getDesCipher().init(Cipher.ENCRYPT_MODE, getMyDesKey());

        return getDesCipher().doFinal(originalBytes);
    }

    @Override
    public byte[] decrypt(byte[] originalBytes) throws Exception {
        getDesCipher().init(Cipher.DECRYPT_MODE, getMyDesKey());

        return getDesCipher().doFinal(originalBytes);
    }
}