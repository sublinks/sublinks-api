package com.sublinks.sublinksapi.api.sublinks.v1.person.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.moderation.BanPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.person.services.SublinksPersonService;
import com.sublinks.sublinksapi.person.entities.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/person/{key}/aggregation")
@Tag(name = "Person Aggegation", description = "Person Aggregation API")
@AllArgsConstructor
public class SublinksPersonAggregationController extends AbstractSublinksApiController {

  private final SublinksPersonService sublinksPersonService;
  private final ConversionService conversionService;

  @Operation(summary = "Get a person's aggregation")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonResponse show(@RequestBody @Valid BanPerson banPersonForm,
      final SublinksJwtPerson jwtPerson)
  {

    final Person person = getPersonOrThrowUnauthorized(jwtPerson);

    return sublinksPersonService.banPerson(banPersonForm, person);
  }
}
