package com.sublinks.sublinksapi.api.sublinks.v1.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityAggregatesResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityView;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.IndexCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.UpdateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.services.SublinksCommunityService;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import com.sublinks.sublinksapi.community.models.CommunitySearchCriteria;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
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
  private final LinkPersonCommunityService linkPersonCommunityService;

  @Operation(summary = "Get a list of communities")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<CommunityView> index(
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

    final CommunitySearchCriteria.CommunitySearchCriteriaBuilder criteria = CommunitySearchCriteria.builder()
        .perPage(indexCommunityForm.limit())
        .page(indexCommunityForm.page())
        .sortType(conversionService.convert(sortType, SortType.class))
        .listingType(conversionService.convert(sublinksListingType, ListingType.class))
        .showNsfw(showNsfw)
        .person(person.orElse(null));

    if (indexCommunityForm.limit() == 0) {
      criteria.perPage(20);
    }
    if (indexCommunityForm.page() == 0) {
      criteria.page(1);
    }

    final CommunitySearchCriteria communitySearchCriteria = criteria.build();

    List<CommunityView> communities = new ArrayList<>();

    communityRepository.allCommunitiesBySearchCriteria(communitySearchCriteria)
        .forEach(community -> communities.add(CommunityView.builder()
            .community(conversionService.convert(community, CommunityResponse.class))
            .communityAggregates(conversionService.convert(community.getCommunityAggregate(),
                CommunityAggregatesResponse.class))
            .moderators(linkPersonCommunityService.getPersonsFromCommunityAndListTypes(community,
                    List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))
                .stream()
                .map(pers -> conversionService.convert(pers, PersonResponse.class))
                .toList())
            .build()));

    return communities;
  }

  @Operation(summary = "Get a specific community")
  @GetMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommunityView show(@PathVariable final String key) {

    try {
      return communityRepository.findCommunityByTitleSlug(key)
          .map(comm -> CommunityView.builder()
              .community(conversionService.convert(comm, CommunityResponse.class))
              .communityAggregates(conversionService.convert(comm.getCommunityAggregate(),
                  CommunityAggregatesResponse.class))
              .moderators(linkPersonCommunityService.getPersonsFromCommunityAndListTypes(comm,
                      List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))
                  .stream()
                  .map(pers -> conversionService.convert(pers, PersonResponse.class))
                  .toList())
              .build())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "community_error");
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
