package com.sublinks.sublinksapi.api.sublinks.v1.community.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityAggregatesResponse;
import com.sublinks.sublinksapi.community.entities.CommunityAggregate;
import com.sublinks.sublinksapi.community.repositories.CommunityAggregateRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/community/{key}")
@Tag(name = "Community", description = "Community API")
public class SublinksCommunityAggregationController extends AbstractSublinksApiController {

  private final CommunityAggregateRepository communityAggregateRepository;
  private final ConversionService conversionService;

  @Operation(summary = "Get a community aggregate")
  @GetMapping("/aggregate")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommunityAggregatesResponse show(@PathVariable final String key)
  {

    final CommunityAggregate communityAggregate = communityAggregateRepository.findByCommunityKey(
        key);
    if (communityAggregate == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found");
    }

    return conversionService.convert(communityAggregate, CommunityAggregatesResponse.class);
  }
}
