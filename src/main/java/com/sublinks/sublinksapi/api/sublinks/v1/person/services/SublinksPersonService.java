package com.sublinks.sublinksapi.api.sublinks.v1.person.services;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.RegistrationMode;
import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtUtil;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortOrder;
import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.moderation.IndexBannedPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.enums.RegistrationState;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.CreatePerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonIdentity;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.UpdatePerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.moderation.BanPerson;
import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.services.RoleService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;

@AllArgsConstructor
@Service
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
  private final RoleService roleService;

  /**
   * Retrieves the name and domain identifiers of a person from the given key.
   *
   * @param key The key containing the person's information. If the key contains "@", it is split
   *            into name and domain using "@" as the separator. Otherwise, the name is set as the
   *            key and the domain is obtained from the local instance context.
   * @return The PersonIdentity object containing the name and domain identifiers of the person.
   */
  public PersonIdentity getPersonIdentifiersFromKey(String key) {

    String name, domain;
    if (key.contains("@")) {
      name = key.substring(0, key.indexOf('@'));
      domain = key.substring(key.indexOf('@') + 1);
    } else {
      name = key;
      domain = localInstanceContext.instance()
          .getDomain();
    }

    return PersonIdentity.builder()
        .name(name)
        .domain(domain)
        .build();
  }

  /**
   * Registers a person with the given details.
   *
   * @param createPersonForm The form containing the details of the person to be registered.
   * @param ip               The IP address of the client making the registration request.
   * @param userAgent        The user agent string of the client making the registration request.
   * @return The registration response containing the token, registration status, and error (if
   * any).
   * @throws ResponseStatusException if the email is required but not provided, or if the email send
   *                                 failed.
   */
  public LoginResponse registerPerson(final CreatePerson createPersonForm, final String ip,
      final String userAgent)
  {

    final InstanceConfig instanceConfig = localInstanceContext.instance()
        .getInstanceConfig();

    if (instanceConfig != null && instanceConfig.isRequireEmailVerification()) {
      if (createPersonForm.email()
          .isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email_required");
      }
    }

    final Person.PersonBuilder personBuilder = Person.builder()
        .name(createPersonForm.name())
        .displayName(createPersonForm.displayName())
        .avatarImageUrl(createPersonForm.avatarImageUrl())
        .bannerImageUrl(createPersonForm.bannerImageUrl())
        .biography(createPersonForm.bio())
        .matrixUserId(createPersonForm.matrixUserId());

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
      params.put("verificationUrl", localInstanceContext.instance()
          .getDomain() + "/verify_email/" + personEmailVerification.getToken());
      try {
        final String template_name = EmailTemplatesEnum.VERIFY_EMAIL.toString();
        emailService.saveToQueue(Email.builder()
            .personRecipients(List.of(person))
            .subject(emailService.getSubjects()
                .get(template_name)
                .getAsString())
            .htmlContent(emailService.formatTextEmailTemplate(template_name,
                new Context(Locale.getDefault(), params)))
            .textContent(emailService.formatEmailTemplate(template_name,
                new Context(Locale.getDefault(), params)))
            .build());
        status = RegistrationState.VERIFICATION_EMAIL_SENT;
      } catch (Exception e) {
        personRepository.delete(person);
        return LoginResponse.builder()
            .status(RegistrationState.NOT_CREATED)
            .error("email_send_failed")
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
              .answer(createPersonForm.answer())
              .build());
      if (!instanceConfig.isRequireEmailVerification()) {
        status = RegistrationState.APPLICATION_CREATED;
      }
    }

    if (token != null) {
      userDataService.checkAndAddIpRelation(person, ip, token, userAgent);
    }

    return LoginResponse.builder()
        .token(token)
        .status(status)
        .build();
  }

  /**
   * Logs in a person with the given credentials.
   *
   * @param loginPersonForm The login details of the person.
   * @param ip              The IP address of the client making the login request.
   * @param userAgent       The user agent string of the client making the login request.
   * @return The login response containing the token, registration status, and error (if any).
   * @throws ResponseStatusException if the person is not found, or if the person is deleted, or if
   *                                 the person's email is not verified, or if the person's
   *                                 registration application is not approved, or if the password is
   *                                 incorrect.
   */
  public LoginResponse login(final LoginPerson loginPersonForm, final String ip,
      final String userAgent)
  {

    final Optional<Person> foundPerson = personRepository.findOneByNameIgnoreCase(
        loginPersonForm.username());

    if (foundPerson.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found");
    }

    final Person person = foundPerson.get();

    if (person.isDeleted()) {
      return LoginResponse.builder()
          .status(RegistrationState.UNCHANGED)
          .error("person_deleted")
          .build();
    }

    if (!person.isEmailVerified()) {
      return LoginResponse.builder()
          .status(RegistrationState.UNCHANGED)
          .error("email_not_verified")
          .build();
    }

    Optional<PersonRegistrationApplication> application = personRegistrationApplicationRepository.findOneByPerson(
        person);

    if (application.isPresent() && application.get()
        .getApplicationStatus() != PersonRegistrationApplicationStatus.approved) {
      return LoginResponse.builder()
          .status(RegistrationState.UNCHANGED)
          .error("application_not_approved")
          .build();
    }

    if (!personService.isValidPersonPassword(person, loginPersonForm.password())) {
      return LoginResponse.builder()
          .status(RegistrationState.UNCHANGED)
          .error("password_incorrect")
          .build();
    }

    final String token = sublinksJwtUtil.generateToken(person);

    userDataService.checkAndAddIpRelation(person, ip, token, userAgent);

    return LoginResponse.builder()
        .token(token)
        .status(RegistrationState.UNCHANGED)
        .build();
  }

  /**
   * Updates a person's information.
   *
   * @param person           The person object to be updated.
   * @param updatePersonForm The form containing updated person information.
   * @return The updated person response.
   */
  public PersonResponse updatePerson(Person person, UpdatePerson updatePersonForm) {

    if (Optional.ofNullable(updatePersonForm.languagesKeys())
        .isPresent()) {
      person.setLanguages(languageRepository.findAllByCodeIsIn(updatePersonForm.languagesKeys()));
    }

    Optional.ofNullable(updatePersonForm.displayName())
        .ifPresent(person::setDisplayName);
    Optional.ofNullable(updatePersonForm.email())
        .ifPresent(person::setEmail);
    Optional.ofNullable(updatePersonForm.avatarImageUrl())
        .ifPresent(person::setAvatarImageUrl);
    Optional.ofNullable(updatePersonForm.bannerImageUrl())
        .ifPresent(person::setBannerImageUrl);
    Optional.ofNullable(updatePersonForm.bio())
        .ifPresent(person::setBiography);
    Optional.ofNullable(updatePersonForm.matrixUserId())
        .ifPresent(person::setMatrixUserId);

    if (Optional.ofNullable(updatePersonForm.password())
        .isPresent()) {
      Optional.ofNullable(updatePersonForm.oldPassword())
          .ifPresent(oldPassword -> {
            if (!personService.isValidPersonPassword(person, oldPassword)) {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password_incorrect");
            }
          });
      if (!updatePersonForm.password()
          .equals(updatePersonForm.passwordConfirmation())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "passwords_do_not_match");
      }
      personService.updatePassword(person, updatePersonForm.password());
    }

    personRepository.save(person);

    return conversionService.convert(person, PersonResponse.class);
  }

  /**
   * Retrieves a list of banned persons based on the search criteria.
   *
   * @param indexBannedPersonForm The form containing the search criteria.
   * @return A list of Person objects representing the banned persons.
   * @throws ResponseStatusException if the banned role is not found.
   */
  public List<Person> indexBannedPersons(final IndexBannedPerson indexBannedPersonForm) {

    Optional<Role> bannedRole = roleService.getBannedRole();

    if (bannedRole.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "banned_role_not_found");
    }

    return personRepository.findAllByNameAndBiographyAndRole(indexBannedPersonForm.search(),
        bannedRole.get()
            .getId(), PageRequest.of(indexBannedPersonForm.limit(), indexBannedPersonForm.page(),
            indexBannedPersonForm.sortOrder() == SortOrder.Asc ? Sort.by("name")
                .ascending() : Sort.by("name")
                .descending()));
  }

  /**
   * Bans a person based on the provided ban form and person details.
   *
   * @param banPersonForm The form containing the ban details.
   * @param person        The person to be banned.
   * @return The PersonResponse object representing the updated person information.
   */
  public PersonResponse banPerson(final BanPerson banPersonForm, final Person person) {

    PersonIdentity ids = getPersonIdentifiersFromKey(banPersonForm.key());

    Person bannedPerson = personRepository.findOneByNameAndInstance_Domain(ids.name(), ids.domain())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    bannedPerson.setRole(banPersonForm.ban() ? roleService.getBannedRole(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "banned_role_not_found"))
        : roleService.getDefaultRegisteredRole(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "default_registered_role_not_found")));
    personService.updatePerson(person);
    // @todo: modlog

    return conversionService.convert(person, PersonResponse.class);
  }

  public
}
