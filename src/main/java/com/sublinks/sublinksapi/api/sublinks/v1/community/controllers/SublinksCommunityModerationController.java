package com.sublinks.sublinksapi.api.sublinks.v1.community.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.Moderation.CommunityModeratorResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.services.SublinksCommunityService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
@RequestMapping("/api/v1/community/{key}/moderation")
@Tag(name = "Community", description = "Community Moderation API")
public class SublinksCommunityModerationController extends AbstractSublinksApiController {

  private final LinkPersonCommunityService linkPersonCommunityService;
  private final SublinksCommunityService sublinksCommunityService;
  private final CommunityRepository communityRepository;
  private final PersonRepository personRepository;
  private final ConversionService conversionService;

  @Operation(summary = "Get moderators of the community")
  @GetMapping("/moderators")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<CommunityModeratorResponse> show(@PathVariable final String key) {

    return sublinksCommunityService.getCommunityModerators(key)
        .stream()
        .map(communityModerator -> conversionService.convert(communityModerator,
            CommunityModeratorResponse.class))
        .toList();
  }

  @Operation(summary = "Add a moderator to the community")
  @GetMapping("/moderator/add/{personKey}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<CommunityModeratorResponse> add(@PathVariable final String key,
      @PathVariable final String personKey, final SublinksJwtPerson sublinksJwtPerson)
  {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    final Community community = communityRepository.findCommunityByTitleSlug(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));

    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(person, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_add_moderator");
    }

    final Person newModerator = personRepository.findOneByName(personKey)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    if (linkPersonCommunityService.hasAnyLinkOrAdmin(newModerator, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "person_already_moderator");
    }

    linkPersonCommunityService.addLink(newModerator, community, LinkPersonCommunityType.moderator);

    return sublinksCommunityService.getCommunityModerators(key)
        .stream()
        .map(communityModerator -> conversionService.convert(communityModerator,
            CommunityModeratorResponse.class))
        .toList();
  }

  @Operation(summary = "Remove a moderator from the community")
  @GetMapping("/moderator/remove/{personKey}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<CommunityModeratorResponse> remove(@PathVariable final String key,
      @PathVariable final String personKey)
  {

    final Community community = communityRepository.findCommunityByTitleSlug(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));

    final Person person = personRepository.findOneByName(personKey)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(person, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_remove_moderator");
    }

    linkPersonCommunityService.removeLink(person, community, LinkPersonCommunityType.moderator);

    return sublinksCommunityService.getCommunityModerators(key)
        .stream()
        .map(communityModerator -> conversionService.convert(communityModerator,
            CommunityModeratorResponse.class))
        .toList();
  }

  @Operation(summary = "Ban a user from a community")
  @GetMapping("/ban/{personKey}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})

}
