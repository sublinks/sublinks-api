package com.sublinks.sublinksapi.api.lemmy.v3.modlog.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModLog;
import com.sublinks.sublinksapi.api.lemmy.v3.modlog.models.GetModlogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v3/modlog")
@Tag(name = "Miscellaneous")
public class ModlogController extends AbstractLemmyApiController {
    @Operation(summary = "Get the modlog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GetModlogResponse.class))})
    })
    @GetMapping
    GetModlogResponse index(@Valid final GetModLog getModLogForm, JwtPerson principal) {

        Optional<Person> person = getOptionalPerson(principal);

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
