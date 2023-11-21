package com.sublinks.sublinksapi.api.lemmy.v3.federatedinstance.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.federatedinstance.models.GetFederatedInstancesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v3/federated_instances")
@Tag(name = "Miscellaneous")
public class FederatedInstancesController extends AbstractLemmyApiController {

  @Operation(summary = "Fetch federated instances.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GetFederatedInstancesResponse.class))})
  })
  @GetMapping
  GetFederatedInstancesResponse index() {

    return GetFederatedInstancesResponse
        .builder()
        .build();
  }
}
