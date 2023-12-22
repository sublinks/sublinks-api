package com.sublinks.sublinksapi.api.lemmy.v3.user.repositories;

import com.sublinks.sublinksapi.api.lemmy.v3.user.dto.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CaptchaRepository extends JpaRepository<Captcha, Long> {

  @Query(value = "select * from captcha where locked is false limit 1", nativeQuery = true)
  Captcha findFirst();

  @Query(value = "select count(*) from captcha where locked is false", nativeQuery = true)
  long count();
}
