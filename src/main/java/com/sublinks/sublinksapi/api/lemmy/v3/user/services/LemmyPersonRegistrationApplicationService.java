package com.sublinks.sublinksapi.api.lemmy.v3.user.services;

import com.sublinks.sublinksapi.api.lemmy.v3.admin.models.RegistrationApplicationView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPersonRegistrationApplicationService {

  private final ConversionService conversionService;

  public RegistrationApplicationView getPersonRegistrationApplicationView(
      PersonRegistrationApplication personRegistrationApplication) {

    return RegistrationApplicationView.builder().registration_application(
            conversionService.convert(personRegistrationApplication,
                com.sublinks.sublinksapi.api.lemmy.v3.admin.models.RegistrationApplication.class))
        .creator(conversionService.convert(personRegistrationApplication.getPerson(),
            com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person.class)).creator_local_user(
            (LocalUser) conversionService.convert(personRegistrationApplication.getPerson(),
                TypeDescriptor.valueOf(Person.class), TypeDescriptor.valueOf(LocalUser.class)))
        .build();
  }
}
