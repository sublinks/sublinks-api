package com.sublinks.sublinksapi.utils;

import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

@Service
public class KeyService {
    public KeyStore generate() {

        final KeyPair pair = generateKeyPair();
        if (pair == null) {
            return new KeyStore(null, null);
        }
        final String base64EncodedPublic = base64Encode(pair.getPublic().getEncoded());
        final String base64EncodedPrivate = base64Encode(pair.getPrivate().getEncoded());
        return new KeyStore(
                wrapPublic(base64EncodedPublic),
                wrapPrivate(base64EncodedPrivate)
        );
    }

    private KeyPair generateKeyPair() {

        try {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (Exception exception) {
            return null;
        }
    }

    private String base64Encode(final byte[] src) {

        return Base64.getMimeEncoder().encodeToString(src);
    }

    private String wrapPrivate(final String privateKey) {

        return "-----BEGIN PRIVATE KEY-----\r\n" + privateKey + "\r\n-----END PRIVATE KEY-----\r\n";
    }

    private String wrapPublic(final String publicKey) {

        return "-----BEGIN PUBLIC KEY-----\r\n" + publicKey + "\r\n-----END PUBLIC KEY-----\r\n";
    }
}
