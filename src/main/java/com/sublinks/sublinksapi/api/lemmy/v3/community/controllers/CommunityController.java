package com.sublinks.sublinksapi.api.lemmy.v3.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.BlockCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.BlockCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.FollowCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.GetCommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.ListCommunities;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.ListCommunitiesResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.errorHandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Site;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/community")
@Tag(name = "Community")
public class CommunityController extends AbstractLemmyApiController {

  private final LocalInstanceContext localInstanceContext;
  private final CommunityRepository communityRepository;
  private final LemmyCommunityService lemmyCommunityService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final ConversionService conversionService;

  @Operation(summary = "Get / fetch a community.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GetCommunityResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Community Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @GetMapping
  public GetCommunityResponse show(@Valid final GetCommunity getCommunityForm,
      final JwtPerson principal) {

    final Community community = Optional.ofNullable(
        communityRepository.findCommunityByIdOrTitleSlug(
            getCommunityForm.id(), getCommunityForm.name()
        )).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    final Optional<Person> person = getOptionalPerson(principal);
    CommunityView communityView;
    if (person.isPresent()) {
      communityView = lemmyCommunityService.communityViewFromCommunity(community, person.get());
    } else {
      communityView = lemmyCommunityService.communityViewFromCommunity(community);
    }
    final List<CommunityModeratorView> moderatorViews = lemmyCommunityService.communityModeratorViewList(
        community);
    return GetCommunityResponse.builder()
        .community_view(communityView)
        .site(conversionService.convert(localInstanceContext, Site.class))
        .moderators(moderatorViews)
        .discussion_languages(lemmyCommunityService.communityLanguageCodes(community))
        .build();
  }

  @Operation(summary = "List communities, with various filters.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ListCommunitiesResponse.class))})
  })
  @GetMapping("list")
  @Transactional
  public ListCommunitiesResponse list(@Valid final ListCommunities listCommunitiesForm,
      final JwtPerson principal) {

    final Collection<CommunityView> communityViews = new LinkedHashSet<>();
    final Optional<Person> person = getOptionalPerson(principal);

    final Collection<Community> communities = communityRepository.findAll(); // @todo apply filters
    for (Community community : communities) {
      CommunityView communityView;
      if (person.isPresent()) {
        communityView = lemmyCommunityService.communityViewFromCommunity(community, person.get());
      } else {
        communityView = lemmyCommunityService.communityViewFromCommunity(community);
      }
      communityViews.add(communityView);
    }

    return ListCommunitiesResponse.builder()
        .communities(communityViews)
        .build();
  }

  @Operation(summary = "Follow / subscribe to a community.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CommunityResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Community Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("follow")
  CommunityResponse follow(@Valid @RequestBody final FollowCommunity followCommunityForm,
      final JwtPerson principal) {

    final Person person = getPersonOrThrowUnauthorized(principal);

    final Optional<Community> community = communityRepository.findById(
        (long) followCommunityForm.community_id());

    if (community.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (followCommunityForm.follow()) {
      if (linkPersonCommunityService.hasLink(person, community.get(),
          LinkPersonCommunityType.blocked)) {
        linkPersonCommunityService.removeLink(person, community.get(),
            LinkPersonCommunityType.blocked);
      }
      linkPersonCommunityService.addLink(person, community.get(), LinkPersonCommunityType.follower);
    } else {
      linkPersonCommunityService.removeLink(person, community.get(),
          LinkPersonCommunityType.follower);
    }

    return lemmyCommunityService.createCommunityResponse(
        community.get(),
        person
    );
  }

  @Operation(summary = "Block a community.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = BlockCommunityResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Community Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("block")
  BlockCommunityResponse block(@Valid @RequestBody final BlockCommunity blockCommunityForm,
      final JwtPerson principal) {

    Person person = getPersonOrThrowUnauthorized(principal);
    Community community = communityRepository.findById(blockCommunityForm.community_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    if (blockCommunityForm.block()) {
      if (linkPersonCommunityService.hasLink(person, community, LinkPersonCommunityType.follower)) {
        linkPersonCommunityService.removeLink(person, community, LinkPersonCommunityType.follower);
      }
      linkPersonCommunityService.addLink(person, community, LinkPersonCommunityType.blocked);
    } else {
      linkPersonCommunityService.removeLink(person, community, LinkPersonCommunityType.blocked);
    }

    return BlockCommunityResponse.builder()
        .community_view(lemmyCommunityService.communityViewFromCommunity(community, person))
        .blocked(blockCommunityForm.block())
        .build();
  }
}
