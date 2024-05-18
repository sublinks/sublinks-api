package com.sublinks.sublinksapi.language.repositories;

import com.sublinks.sublinksapi.language.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

  Language findLanguageByCode(String code);

}
