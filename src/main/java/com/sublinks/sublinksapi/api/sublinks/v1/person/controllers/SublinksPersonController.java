package com.sublinks.sublinksapi.api.sublinks.v1.person.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.CreatePerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.IndexPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.LoginResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.UpdatePerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.services.SublinksPersonService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/person")
@Tag(name = "Person", description = "Person API")
@AllArgsConstructor
public class SublinksPersonController extends AbstractSublinksApiController {

  private final PersonRepository personRepository;
  private final SublinksPersonService sublinksPersonService;
  private final ConversionService conversionService;

  @Operation(summary = "Get a list of persons")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<PersonResponse> index(@RequestParam(required = false) final IndexPerson indexPerson,
      final SublinksJwtPerson principal)
  {

    final Optional<Person> person = getOptionalPerson(principal);

    return sublinksPersonService.index(indexPerson == null ? IndexPerson.builder()
        .build() : indexPerson);
  }

  @Operation(summary = "Get a specific person")
  @GetMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonResponse show(@PathVariable String key, final SublinksJwtPerson principal) {

    final Optional<Person> person = getOptionalPerson(principal);

    return sublinksPersonService.show(key);
  }

  @Operation(summary = "Register a new person")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public LoginResponse create(final HttpServletRequest request,
      @RequestBody @Valid final CreatePerson createPerson)
  {

    return sublinksPersonService.registerPerson(createPerson, request.getRemoteAddr(),
        request.getHeader("User-Agent"));
  }

  @Operation(summary = "Log into a user")
  @PostMapping("/login")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public LoginResponse login(final HttpServletRequest request,
      @RequestBody @Valid final LoginPerson loginPerson)
  {

    return sublinksPersonService.login(loginPerson, request.getRemoteAddr(),
        request.getHeader("User-Agent"));
  }

  @Operation(summary = "Update an person")
  @PostMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonResponse update(@PathVariable String key,
      @RequestBody @Valid final UpdatePerson updatePersonForm, final SublinksJwtPerson principal)
  {

    final Person person = getPersonOrThrowUnauthorized(principal);

    return sublinksPersonService.updatePerson(person, updatePersonForm);
  }
}
