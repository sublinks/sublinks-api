package com.sublinks.sublinksapi.api.lemmy.v3.user.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtUtil;
import com.sublinks.sublinksapi.api.lemmy.v3.authentication.models.LoginResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.DeleteAccountResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GenerateTotpSecretResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetCaptchaResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Login;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PasswordResetResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Register;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.UpdateTotpResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.VerifyEmailResponse;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/user")
@Tag(name = "user", description = "the user API")
public class UserAuthController {
    private final JwtUtil jwtUtil;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @PostMapping("register")
    LoginResponse create(@Valid @RequestBody final Register registerForm) {

        final Person person = personService.getDefaultNewUser(registerForm.username());
        if (!Objects.equals(registerForm.password(), registerForm.password_verify())) {
            throw new RuntimeException("Passwords do not match");
            // @todo throw lemmy error code
        }
        personService.createPerson(person);
        final String token = jwtUtil.generateToken(person);
        return LoginResponse.builder()
                .jwt(token)
                .registration_created(false)
                .verify_email_sent(false)
                .build();
    }

    @GetMapping("get_captcha")
    GetCaptchaResponse captcha() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("login")
    LoginResponse login(@Valid @RequestBody final Login loginForm) {

        final Person person = personRepository.findOneByName(loginForm.username_or_email());
        // @todo verify password
        final String token = jwtUtil.generateToken(person);
        return LoginResponse.builder()
                .jwt(token)
                .registration_created(false) // @todo return true if application created
                .verify_email_sent(false) // @todo return true if welcome email sent for verification
                .build();
    }

    @PostMapping("delete_account")
    DeleteAccountResponse delete() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("password_reset")
    PasswordResetResponse passwordReset() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("password_change")
    LoginResponse passwordChange() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("verify_email")
    VerifyEmailResponse verifyEmail() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("change_password")
    LoginResponse changePassword() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("totp/generate")
    GenerateTotpSecretResponse totpGenerate() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("totp/update")
    UpdateTotpResponse totpUpdate() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
