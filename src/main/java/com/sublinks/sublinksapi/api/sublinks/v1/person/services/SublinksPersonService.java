package com.sublinks.sublinksapi.api.sublinks.v1.person.services;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.RegistrationMode;
import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtUtil;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.CreatePerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.RegistrationState;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.UpdatePerson;
import com.sublinks.sublinksapi.email.entities.Email;
import com.sublinks.sublinksapi.email.enums.EmailTemplatesEnum;
import com.sublinks.sublinksapi.email.services.EmailService;
import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonEmailVerification;
import com.sublinks.sublinksapi.person.entities.PersonRegistrationApplication;
import com.sublinks.sublinksapi.person.enums.PersonRegistrationApplicationStatus;
import com.sublinks.sublinksapi.person.repositories.PersonRegistrationApplicationRepository;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonEmailVerificationService;
import com.sublinks.sublinksapi.person.services.PersonRegistrationApplicationService;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.person.services.UserDataService;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;

@AllArgsConstructor
public class SublinksPersonService {

  private final PersonService personService;
  private final SublinksJwtUtil sublinksJwtUtil;
  private final PersonRepository personRepository;
  private final LocalInstanceContext localInstanceContext;
  private final EmailService emailService;
  private final PersonEmailVerificationService personEmailVerificationService;
  private final PersonRegistrationApplicationService personRegistrationApplicationService;
  private final PersonRegistrationApplicationRepository personRegistrationApplicationRepository;
  private final UserDataService userDataService;
  private final ConversionService conversionService;
  private final LanguageRepository languageRepository;

  public LoginResponse registerPerson(final CreatePerson createPersonForm, final String ip,
      final String userAgent) {

    final InstanceConfig instanceConfig = localInstanceContext.instance().getInstanceConfig();

    if (instanceConfig != null && instanceConfig.isRequireEmailVerification()) {
      if (createPersonForm.email().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email_required");
      }
    }

    final Person.PersonBuilder personBuilder = Person.builder()
        .name(createPersonForm.name())
        .displayName(createPersonForm.displayName())
        .avatarImageUrl(createPersonForm.avatarImageUrl().orElse(null))
        .bannerImageUrl(createPersonForm.bannerImageUrl().orElse(null))
        .biography(createPersonForm.bio().orElse(null))
        .matrixUserId(createPersonForm.matrixUserId().orElse(null));

    final Person person = personBuilder.build();

    personService.createPerson(person);

    String token = sublinksJwtUtil.generateToken(person);
    RegistrationState status = RegistrationState.CREATED;

    if (instanceConfig != null && instanceConfig.isRequireEmailVerification()) {
      token = null;

      PersonEmailVerification personEmailVerification = personEmailVerificationService.create(
          person, ip, userAgent);

      Map<String, Object> params = emailService.getDefaultEmailParameters();

      params.put("person", person);
      params.put("verificationUrl", localInstanceContext.instance().getDomain() + "/verify_email/"
          + personEmailVerification.getToken());
      try {
        final String template_name = EmailTemplatesEnum.VERIFY_EMAIL.toString();
        emailService.saveToQueue(Email.builder()
            .personRecipients(List.of(person))
            .subject(emailService.getSubjects().get(template_name).getAsString())
            .htmlContent(emailService.formatTextEmailTemplate(template_name,
                new Context(Locale.getDefault(), params)))
            .textContent(emailService.formatEmailTemplate(template_name,
                new Context(Locale.getDefault(), params)))
            .build());
        status = RegistrationState.VERIFICATION_EMAIL_SENT;
      } catch (Exception e) {
        personRepository.delete(person);
        return LoginResponse.builder()
            .token(Optional.empty())
            .status(RegistrationState.NOT_CREATED)
            .error(Optional.of("email_send_failed"))
            .build();
      }
    }
    if (instanceConfig != null
        && instanceConfig.getRegistrationMode() == RegistrationMode.RequireApplication) {
      token = null;

      personRegistrationApplicationService.createPersonRegistrationApplication(
          PersonRegistrationApplication.builder()
              .applicationStatus(instanceConfig.isRequireEmailVerification()
                  ? PersonRegistrationApplicationStatus.inactive
                  : PersonRegistrationApplicationStatus.pending)
              .person(person)
              .question(instanceConfig.getRegistrationQuestion())
              .answer(createPersonForm.answer().orElse(null))
              .build());
      if (!instanceConfig.isRequireEmailVerification()) {
        status = RegistrationState.APPLICATION_CREATED;
      }
    }

    if (token != null) {
      userDataService.checkAndAddIpRelation(person, ip, token, userAgent);
    }

    return LoginResponse.builder().token(Optional.ofNullable(token)).status(status).build();
  }

  public LoginResponse login(final LoginPerson loginPersonForm, final String ip,
      final String userAgent) {

    final Optional<Person> foundPerson = personRepository.findOneByName(loginPersonForm.username());

    if (foundPerson.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found");
    }

    final Person person = foundPerson.get();

    if (person.isDeleted()) {
      return LoginResponse.builder()
          .token(Optional.empty())
          .status(RegistrationState.UNCHANGED)
          .error(Optional.of("person_deleted"))
          .build();
    }

    if (!person.isEmailVerified()) {
      return LoginResponse.builder()
          .token(Optional.empty())
          .status(RegistrationState.UNCHANGED)
          .error(Optional.of("email_not_verified"))
          .build();
    }

    Optional<PersonRegistrationApplication> application = personRegistrationApplicationRepository.findOneByPerson(
        person);

    if (application.isPresent() && application.get().getApplicationStatus()
        != PersonRegistrationApplicationStatus.approved) {
      return LoginResponse.builder()
          .token(Optional.empty())
          .status(RegistrationState.UNCHANGED)
          .error(Optional.of("application_not_approved"))
          .build();
    }

    if (!personService.isPasswordEqual(person, loginPersonForm.password())) {
      return LoginResponse.builder()
          .token(Optional.empty())
          .status(RegistrationState.UNCHANGED)
          .error(Optional.of("password_incorrect"))
          .build();
    }

    final String token = sublinksJwtUtil.generateToken(person);

    userDataService.checkAndAddIpRelation(person, ip, token, userAgent);

    return LoginResponse.builder()
        .token(Optional.of(token))
        .status(RegistrationState.UNCHANGED)
        .build();
  }

  public PersonResponse updatePerson(Person person, UpdatePerson updatePersonForm) {

    if (updatePersonForm.languagesKeys().isPresent()) {
      person.setLanguages(
          languageRepository.findAllByCodeIsIn(updatePersonForm.languagesKeys().get()));
    }

    updatePersonForm.displayName().ifPresent(person::setDisplayName);
    updatePersonForm.email().ifPresent(person::setEmail);
    updatePersonForm.avatarImageUrl().ifPresent(person::setAvatarImageUrl);
    updatePersonForm.bannerImageUrl().ifPresent(person::setBannerImageUrl);
    updatePersonForm.bio().ifPresent(person::setBiography);
    updatePersonForm.matrixUserId().ifPresent(person::setMatrixUserId);

    if (updatePersonForm.oldPassword().isPresent() && updatePersonForm.password().isPresent()
        && updatePersonForm.passwordConfirmation().isPresent()) {
      if (!personService.isPasswordEqual(person, updatePersonForm.oldPassword().get())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password_incorrect");
      }
      if (!updatePersonForm.password().get().equals(
          updatePersonForm.passwordConfirmation().get())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password_mismatch");
      }
      personService.updatePassword(person, updatePersonForm.password().get());
    }

    personRepository.save(person);

    return conversionService.convert(person, PersonResponse.class);
  }
}
