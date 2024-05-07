package com.sublinks.sublinksapi.api.sublinks.v1.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.IndexCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.UpdateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.services.SublinksCommunityService;
import com.sublinks.sublinksapi.community.models.CommunitySearchCriteria;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "Community", description = "Community API")
public class SublinksCommunityController extends AbstractSublinksApiController {

  private final CommunityRepository communityRepository;
  private final SublinksCommunityService sublinksCommunityService;
  private final ConversionService conversionService;
  private final LocalInstanceContext localInstanceContext;

  @Operation(summary = "Get a list of communities")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<CommunityResponse> index(
      @RequestParam(required = false) final Optional<IndexCommunity> indexCommunityParam,
      final SublinksJwtPerson sublinksJwtPerson)
  {

    final Optional<Person> person = getOptionalPerson(sublinksJwtPerson);

    final IndexCommunity indexCommunityForm = indexCommunityParam.orElse(IndexCommunity.builder()
        .build());

    com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType sortType = indexCommunityForm.sortType();

    if (sortType == null) {
      if (person.isPresent() && person.get()
          .getDefaultSortType() != null) {
        sortType = conversionService.convert(person.get()
                .getDefaultSortType(),
            com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType.class);
      } else {
        sortType = com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType.New;
      }
    }

    SublinksListingType sublinksListingType = indexCommunityForm.sublinksListingType();

    if (sublinksListingType == null) {
      if (person.isPresent() && person.get()
          .getDefaultListingType() != null) {
        sublinksListingType = conversionService.convert(person.get()
            .getDefaultListingType(), SublinksListingType.class);
      } else if (localInstanceContext.instance()
          .getInstanceConfig()
          .getDefaultPostListingType() != null) {
        sublinksListingType = conversionService.convert(localInstanceContext.instance()
            .getInstanceConfig()
            .getDefaultPostListingType(), SublinksListingType.class);
      } else {
        sublinksListingType = SublinksListingType.Local;
      }
    }

    boolean showNsfw = indexCommunityForm.showNsfw() != null && indexCommunityForm.showNsfw();

    return communityRepository.allCommunitiesBySearchCriteria(CommunitySearchCriteria.builder()
            .page(indexCommunityForm.page())
            .perPage(indexCommunityForm.limit())
            .sortType(conversionService.convert(sortType, SortType.class))
            .listingType(conversionService.convert(sublinksListingType, ListingType.class))
            .showNsfw(showNsfw)
            .person(person.orElse(null))
            .build())
        .stream()
        .map(community -> conversionService.convert(community, CommunityResponse.class))
        .toList();

  }

  @Operation(summary = "Get a specific community")
  @GetMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommunityResponse show(@PathVariable final String key) {

    try {
      return conversionService.convert(communityRepository.findCommunityByTitleSlug(key),
          CommunityResponse.class);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Community not found");
    }
  }

  @Operation(summary = "Create a new community")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommunityResponse create(@RequestBody @Valid final CreateCommunity createCommunity,
      final SublinksJwtPerson sublinksJwtPerson)
  {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    return sublinksCommunityService.createCommunity(createCommunity, person);
  }

  @Operation(summary = "Update an community")
  @PostMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommunityResponse update(@PathVariable String key,
      @RequestBody @Valid UpdateCommunity updateCommunityForm, final SublinksJwtPerson principal)
  {

    final Person person = getPersonOrThrowUnauthorized(principal);

    return sublinksCommunityService.updateCommunity(key, updateCommunityForm, person);
  }

  @Operation(summary = "Purge an community")
  @DeleteMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public void delete(@PathVariable String key) {
    // @TODO: implement
  }
}
