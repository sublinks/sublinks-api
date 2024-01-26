package com.sublinks.sublinksapi.utils;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTPGenerator;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class TotpUtil {

  public TOTPGenerator getTotpGenerator(byte[] secret) {

    if (secret == null) {
      return null;
    }

    return new TOTPGenerator.Builder(secret)
        .withHOTPGenerator(builder -> {
          builder.withAlgorithm(HMACAlgorithm.SHA512);
          builder.withPasswordLength(6);
        })
        .withPeriod(Duration.ofSeconds(30))
        .withClock(Clock.systemUTC())
        .build();
  }

  public TOTPGenerator getTotpGenerator(String secretOrUri) {

    if (secretOrUri == null) {
      return null;
    }

    if (secretOrUri.startsWith("otpauth://")) {
      try {
        return TOTPGenerator.fromURI(URI.create(secretOrUri));
      } catch (Exception e) {
        return null;
      }
    }

    return getTotpGenerator(secretOrUri.getBytes());
  }

  public byte[] createSecret(int bits) {

    if (bits < 0) {
      // Default to 160 bits
      bits = 160;
    }

    return SecretGenerator.generate(bits);
  }

  public String createSecretString(int bits) {

    return new String(createSecret(bits));
  }

  public URI createUri(String issuer, String account, String secret) throws URISyntaxException {

    return getTotpGenerator(secret).getURI(issuer, account);
  }

  public boolean verify(String secret, String code) {

    return getTotpGenerator(secret).verify(code);
  }

  public String generateCode(String secret) {

    return getTotpGenerator(secret).now();
  }
}
