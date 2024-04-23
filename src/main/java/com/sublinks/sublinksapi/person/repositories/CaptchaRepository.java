package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.Captcha;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaRepository extends JpaRepository<Captcha, Long> {

  Optional<Captcha> findFirstByLockedFalse();

  List<Captcha> findAllByLockedTrueAndUpdatedAtBefore(Date updatedAt);

  Optional<Captcha> findByWordIs(String word);

  void deleteAllByLockedFalse();

  long countAllByLockedFalse();
}
