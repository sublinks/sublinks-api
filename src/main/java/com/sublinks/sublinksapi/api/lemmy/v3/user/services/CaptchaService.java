package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.person.dto.Captcha;
import com.sublinks.sublinksapi.person.repositories.CaptchaRepository;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class CaptchaService {

  private final CaptchaRepository captchaRepository;

  private static String encodeToString(BufferedImage image) {

    String imageString;

    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(image, "png", bos);
      byte[] imageBytes = bos.toByteArray();

      imageString = Base64.getEncoder().encodeToString(imageBytes);

      bos.close();
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Image could not be written");
    }

    return imageString;
  }

  @Transactional
  public Captcha getCaptcha() {

    Captcha captcha = captchaRepository.findFirstByLockedFalse().orElse(createCaptcha());

    captcha.setLocked(true);
    captchaRepository.save(captcha);

    return captcha;
  }

  public boolean validateCaptcha(String word, boolean remove) {

    Optional<Captcha> captcha = captchaRepository.findByWordIs(word);
    if (remove && captcha.isPresent()) {
      captchaRepository.delete(captcha.get());
    }
    return captcha.isPresent();
  }

  public Captcha createCaptcha() {

    cn.apiclub.captcha.Captcha captcha = new cn.apiclub.captcha.Captcha.Builder(200, 50)
        .addText()
        .addBackground()
        .addNoise()
        .gimp()
        .addBorder()
        .build();

    Captcha captchaEntity = new Captcha();
    captchaEntity.setUuid(UUID.randomUUID().toString());
    captchaEntity.setWord(captcha.getAnswer());
    captchaEntity.setPng(encodeToString(captcha.getImage()));

    return captchaRepository.save(captchaEntity);
  }
}
