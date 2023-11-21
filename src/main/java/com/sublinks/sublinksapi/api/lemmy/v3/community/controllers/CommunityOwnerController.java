package com.sublinks.sublinksapi.api.lemmy.v3.community.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtPerson;
import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.EditCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.community.services.LemmyCommunityService;
import com.sublinks.sublinksapi.api.lemmy.v3.errorHandler.ApiError;
import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.authorization.services.AuthorizationService;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.community.services.CommunityService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
import com.sublinks.sublinksapi.utils.SlugUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v3/community")
@Tag(name = "Community")
public class CommunityOwnerController extends AbstractLemmyApiController {

  private final LocalInstanceContext localInstanceContext;
  private final LinkPersonCommunityRepository linkPersonCommunityRepository;
  private final CommunityService communityService;
  private final AuthorizationService authorizationService;
  private final LemmyCommunityService lemmyCommunityService;
  private final SlugUtil slugUtil;
  private final CommunityRepository communityRepository;

  @Operation(summary = "Create a new community.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CommunityResponse.class))})
  })
  @PostMapping
  @Transactional
  public CommunityResponse create(@Valid @RequestBody final CreateCommunity createCommunityForm,
      JwtPerson principal) {

    Person person = getPersonOrThrowUnauthorized(principal);
    authorizationService
        .canPerson(person)
        .performTheAction(AuthorizeAction.create)
        .onEntity(AuthorizedEntityType.community)
        .defaultingToAllow() // @todo use site setting to allow community creation
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    final List<Language> languages = new ArrayList<>();
    for (String languageCode : createCommunityForm.discussion_languages()) {
      final Optional<Language> language = localInstanceContext.languageRepository()
          .findById(Long.valueOf(languageCode));
      language.ifPresent(languages::add);
    }

    Community community = Community.builder()
        .instance(localInstanceContext.instance())
        .title(createCommunityForm.title())
        .titleSlug(slugUtil.stringToSlug(createCommunityForm.name()))
        .description(createCommunityForm.description())
        .isPostingRestrictedToMods(createCommunityForm.posting_restricted_to_mods() != null
            && createCommunityForm.posting_restricted_to_mods())
        .isNsfw(createCommunityForm.nsfw() != null && createCommunityForm.nsfw())
        .iconImageUrl(createCommunityForm.icon())
        .bannerImageUrl(createCommunityForm.banner())
        .languages(languages)
        .build();

    final Set<LinkPersonCommunity> linkPersonCommunities = new LinkedHashSet<>();
    linkPersonCommunities.add(LinkPersonCommunity.builder()
        .community(community)
        .person(person)
        .linkType(LinkPersonCommunityType.owner)
        .build());
    linkPersonCommunities.add(LinkPersonCommunity.builder()
        .community(community)
        .person(person)
        .linkType(LinkPersonCommunityType.follower)
        .build());

    communityService.createCommunity(community);
    linkPersonCommunityRepository.saveAllAndFlush(linkPersonCommunities);

    return lemmyCommunityService.createCommunityResponse(community, person);
  }

  @Operation(summary = "Edit a community.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CommunityResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Community Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PutMapping
  CommunityResponse update(@Valid final @RequestBody EditCommunity editCommunityForm,
      final JwtPerson principal) {

    Person person = getPersonOrThrowUnauthorized(principal);
    Community community = communityRepository.findById(editCommunityForm.community_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    authorizationService
        .canPerson(person)
        .performTheAction(AuthorizeAction.update)
        .onEntity(community)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

    community.setTitle(editCommunityForm.title());
    community.setDescription(editCommunityForm.description());
    community.setIconImageUrl(editCommunityForm.icon());
    community.setBannerImageUrl(editCommunityForm.banner());
    community.setNsfw(editCommunityForm.nsfw());
    community.setPostingRestrictedToMods(editCommunityForm.posting_restricted_to_mods());

    final List<Language> languages = new ArrayList<>();
    for (String languageCode : editCommunityForm.discussion_languages()) {
      final Optional<Language> language = localInstanceContext.languageRepository()
          .findById(Long.valueOf(languageCode));
      language.ifPresent(languages::add);
    }

    community.setLanguages(languages);

    communityService.updateCommunity(community);

    return lemmyCommunityService.createCommunityResponse(community, person);
  }
}
