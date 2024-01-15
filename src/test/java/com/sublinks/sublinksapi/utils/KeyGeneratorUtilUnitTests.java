package com.sublinks.sublinksapi.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyGeneratorUtilUnitTests {

  KeyGeneratorUtil keyGeneratorUtil = new KeyGeneratorUtil();

  // Pattern.DOTALL is used here so that the '.' will match line terminators found in the key
  Pattern publicKeyPattern = Pattern.compile("^-----BEGIN PUBLIC KEY-----\\r\\n(.+)\\r\\n-----END PUBLIC KEY-----\\r\\n$", Pattern.DOTALL);
  Pattern privateKeyPattern = Pattern.compile("^-----BEGIN PRIVATE KEY-----\\r\\n(.+)\\r\\n-----END PRIVATE KEY-----\\r\\n$", Pattern.DOTALL);

  @Test
  void givenSuccessfulKeyGeneration_whenGenerate_thenReturnKeyPairWrappedCorrectly() {

    KeyStore keyStore = keyGeneratorUtil.generate();

    assertNotNull(keyStore.publicKey());
    assertTrue(publicKeyPattern.matcher(keyStore.publicKey()).matches());

    assertNotNull(keyStore.privateKey());
    assertTrue(privateKeyPattern.matcher(keyStore.privateKey()).matches());
  }
}
