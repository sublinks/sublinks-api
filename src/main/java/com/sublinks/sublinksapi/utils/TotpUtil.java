package com.sublinks.sublinksapi.utils;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTPGenerator;
import com.sublinks.sublinksapi.person.entities.Person;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Duration;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TotpUtil {

  public static TOTPGenerator getTotpGenerator(byte[] secret) {

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

  public static TOTPGenerator getTotpGenerator(String secretOrUri) {

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

  public static byte[] createSecret(int bits) {

    if (bits < 0) {
      // Default to 160 bits
      bits = 160;
    }

    return SecretGenerator.generate(bits);
  }

  public static String createSecretString(int bits) {

    return new String(createSecret(bits));
  }

  public static URI createUri(String issuer, String account, String secret)
      throws URISyntaxException {

    return getTotpGenerator(secret).getURI(issuer, account);
  }

  public static String createUriString(Person person) {

    if (person == null) {
      return null;
    }
    if (person.getTotpVerifiedSecret() == null) {
      return null;
    }

    try {
      return TotpUtil.createUri(person.getInstance().getInstanceConfig().getInstance().getDomain(),
          person.getName(), person.getTotpVerifiedSecret()).toString();
    } catch (Exception e) {
      return null;
    }
  }

  public static boolean verify(@NonNull String secret, @NonNull String code) {

    Boolean isValid = getTotpGenerator(secret).verify(code);

    String sec = generate_otp_code(secret);

    String sec2 = generate_otp_code(secret);

    return isValid;
  }

  public static String generate_otp_code(String secret) {

    return getTotpGenerator(secret).now();
  }
}
