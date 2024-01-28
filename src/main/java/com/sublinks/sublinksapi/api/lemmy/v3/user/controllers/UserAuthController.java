package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtUtil;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.RegistrationMode;
import com.sublinks.sublinksapi.api.lemmy.v3.errorhandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.CaptchaResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.DeleteAccountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GenerateTotpSecretResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetCaptchaResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Login;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PasswordResetResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Register;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.SuccessResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.UpdateTotp;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.UpdateTotpResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.VerifyEmailResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.CaptchaService;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.instance.dto.InstanceConfig;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.dto.Captcha;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonRegistrationApplication;
import com.sublinks.sublinksapi.person.enums.PersonRegistrationApplicationStatus;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonRegistrationApplicationService;
import com.sublinks.sublinksapi.person.services.PersonService;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterBlockedException;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterReportException;
import com.sublinks.sublinksapi.slurfilter.services.SlurFilterService;
import com.sublinks.sublinksapi.utils.TotpUtil;
import com.sublinks.sublinksapi.utils.models.LemmyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
@Tag(name = "User")
public class UserAuthController extends AbstractLemmyApiController {

  private final JwtUtil jwtUtil;
  private final PersonService personService;
  private final PersonRepository personRepository;
  private final LocalInstanceContext localInstanceContext;
  private final PersonRegistrationApplicationService personRegistrationApplicationService;
  private final SlurFilterService slurFilterService;
  private final CaptchaService captchaService;
  private final RoleAuthorizingService roleAuthorizingService;
  private final ConversionService conversionService;

  @Operation(summary = "Register a new user.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class, description =
          "JWT will be empty if registration "
              + "requires email verification or application approval."))}),
      @ApiResponse(responseCode = "400", description = "Passwords do not match.", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("register")
  LoginResponse create(@Valid @RequestBody final Register registerForm) {

    InstanceConfig instanceConfig = localInstanceContext.instance().getInstanceConfig();

    if (instanceConfig != null) {
      if (instanceConfig.getRegistrationMode() == RegistrationMode.Closed) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "registration_closed");
      }
      if (instanceConfig.isCaptchaEnabled()) {
        if (!captchaService.validateCaptcha(registerForm.captcha_answer(), true)) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_captcha");
        }
      }
    }

    if (personRepository.findOneByName(registerForm.username()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username_taken");
    }
    try {
      String filteredUsername = slurFilterService.censorText(registerForm.username());
      if (!Objects.equals(filteredUsername, registerForm.username())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "person_blocked_by_slur_filter");
      }
    } catch (SlurFilterBlockedException | SlurFilterReportException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "person_blocked_by_slur_filter");
    }

    final Person person = personService.getDefaultNewUser(registerForm.username());
    if (!Objects.equals(registerForm.password(), registerForm.password_verify())) {
      throw new RuntimeException("Passwords do not match");
      // @todo throw lemmy error code
    }
    personService.createPerson(person);
    String token = jwtUtil.generateToken(person);

    if (instanceConfig != null
        && instanceConfig.getRegistrationMode() == RegistrationMode.RequireApplication) {
      personRegistrationApplicationService.createPersonRegistrationApplication(
          PersonRegistrationApplication.builder()
              .applicationStatus(PersonRegistrationApplicationStatus.pending).person(person)
              .question(instanceConfig.getRegistrationQuestion()).answer(registerForm.answer())
              .build());
      token = "";
    }

    return LoginResponse.builder().jwt(token).registration_created(true).verify_email_sent(false)
        .build();
  }

  @Operation(summary = "Fetch a Captcha.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetCaptchaResponse.class))})})
  @GetMapping("get_captcha")
  GetCaptchaResponse captcha() {

    Captcha captcha = captchaService.getCaptcha();
    return GetCaptchaResponse.builder()
        .ok(conversionService.convert(captcha, CaptchaResponse.class)).build();
  }

  @Operation(summary = "Log into lemmy.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetCaptchaResponse.class))}),
      @ApiResponse(responseCode = "400", description = "A valid user is not found or password is incorrect.", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))})})
  @PostMapping("login")
  LoginResponse login(@Valid @RequestBody final Login loginForm) throws LemmyException {

    final Person person = personRepository.findOneByName(loginForm.username_or_email())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    // @todo verify password

    String totpSecret = person.getTotpVerifiedSecret();
    if (totpSecret != null) {
      if (loginForm.totp_2fa_token() == null) {
        throw new LemmyException("missing_totp_token", HttpStatus.BAD_REQUEST);
      }
      if (!TotpUtil.verify(totpSecret, loginForm.totp_2fa_token())) {
        throw new LemmyException("wrong_totp_token", HttpStatus.BAD_REQUEST);
      }
    }

    final String token = jwtUtil.generateToken(person);
    return LoginResponse.builder().jwt(token)
        .registration_created(false) // @todo return true if application created
        .verify_email_sent(false) // @todo return true if welcome email sent for verification
        .build();
  }

  @Operation(summary = "Delete your account.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DeleteAccountResponse.class))})})
  @PostMapping("delete_account")
  DeleteAccountResponse delete(final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermission(person, RolePermission.DELETE_USER);

    // @todo: delete account

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Reset your password.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PasswordResetResponse.class))})})
  @PostMapping("password_reset")
  PasswordResetResponse passwordReset(final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    // @todo: implement reset password and check for 2fa

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Change your password from an email / token based reset.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))})})
  @PostMapping("password_change")
  LoginResponse passwordChange() {

    // @todo: implement reset password
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Verify your email.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VerifyEmailResponse.class))})})
  @PostMapping("verify_email")
  VerifyEmailResponse verifyEmail() {

    // @todo: implement verify Email

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Change your user password.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))})})
  @PutMapping("change_password")
  LoginResponse changePassword(final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    roleAuthorizingService.hasAdminOrPermission(person, RolePermission.RESET_PASSWORD);

    // @todo: implement change password

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Generate a TOTP / two-factor secret.\r\r Afterwards you need to call `/user/totp/update` with a valid token to enable it.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenerateTotpSecretResponse.class))})})
  @PostMapping("totp/generate")
  GenerateTotpSecretResponse totpGenerate(final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    try {
      String secret = TotpUtil.createSecretString(160);
      GenerateTotpSecretResponse generateTotpSecretResponse = GenerateTotpSecretResponse.builder()
          .totp_secret_url(
              TotpUtil.createUri(localInstanceContext.instance().getDomain(), person.getName(),
                  secret).toString()

          ).build();

      person.setTotpSecret(secret);
      personService.updatePerson(person);

      return generateTotpSecretResponse;

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "totp_secret_generation_failed");
    }
  }

  @Operation(summary =
      "Enable / Disable TOTP / two-factor authentication.\r\r To enable, you need to first call "
          + "`/user/totp/generate` and then pass a valid token to this.\r\r "
          + "Disabling is only possible if 2FA was previously enabled. Again it is necessary to pass a valid token.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateTotpResponse.class))})})
  @PostMapping("totp/update")
  UpdateTotpResponse totpUpdate(@Valid @RequestBody final UpdateTotp updateTotpForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    UpdateTotpResponse.UpdateTotpResponseBuilder updateTotpResponseBuilder = UpdateTotpResponse
        .builder();

    if (updateTotpForm.enabled()) {
      if (person.getTotpSecret() == null) {
        updateTotpResponseBuilder.enabled(false);
        return updateTotpResponseBuilder.build();
      }
      if (!TotpUtil.verify(person.getTotpSecret(), updateTotpForm.totp_token())) {
        updateTotpResponseBuilder.enabled(false);
        return updateTotpResponseBuilder.build();
      }
      person.setTotpVerifiedSecret(person.getTotpSecret());
      person.setTotpSecret(null);
    } else {
      if (TotpUtil.verify(person.getTotpVerifiedSecret(), updateTotpForm.totp_token())) {
        updateTotpResponseBuilder.enabled(false);
        return updateTotpResponseBuilder.build();
      }
      person.setTotpVerifiedSecret(null);
    }
    personService.updatePerson(person);

    return updateTotpResponseBuilder.enabled(true).build();
  }

  @Operation(summary =
      "Validates your Token, throws an error if it is invalid.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateTotpResponse.class))})})
  @PostMapping("validate_auth")
  SuccessResponse validate_auth(final JwtPerson principal) {

    Optional<Person> person = getOptionalPerson(principal);

    return SuccessResponse.builder().success(person.isPresent())
        .error(person.isPresent() ? null : "not_logged_in").build();
  }
}
