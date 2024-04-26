package com.sublinks.sublinksapi.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorUtil {

  /**
   * Generates a new RSA key pair and wraps the keys in a KeyStore object. This method creates a new
   * RSA key pair with a specified key size of 2048 bits. The public and private keys are then
   * base64 encoded and wrapped in a standard format.
   *
   * @return A <a href="#{@link}">{@link KeyStore}</a> object containing the base64 encoded and
   * wrapped public and private keys. If the key generation fails, returns a KeyStore object with
   * null values for both keys.
   */
  public KeyStore generate() {

    final KeyPair pair = generateKeyPair();
    if (pair == null) {
      return new KeyStore(null, null);
    }
    final String base64EncodedPublic = base64Encode(pair.getPublic().getEncoded());
    final String base64EncodedPrivate = base64Encode(pair.getPrivate().getEncoded());
    return new KeyStore(
        wrapPublic(base64EncodedPublic),
        wrapPrivate(base64EncodedPrivate));
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
