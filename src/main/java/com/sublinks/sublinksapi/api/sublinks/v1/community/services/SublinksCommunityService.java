package com.sublinks.sublinksapi.api.sublinks.v1.community.services;

import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.UpdateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.ActorIdUtils;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommunityTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.community.services.CommunityService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksCommunityService {

  private final CommunityRepository communityRepository;
  private final ConversionService conversionService;
  private final CommunityService communityService;
  private final LocalInstanceContext localInstanceContext;
  private final RolePermissionService rolePermissionService;
  private final LinkPersonCommunityService linkPersonCommunityService;
  private final PersonRepository personRepository;

  /**
   * Creates a new community based on the provided community data and person.
   *
   * @param createCommunity The data for the new community.
   * @param person          The person creating the community.
   * @return The response containing the newly created community.
   * @throws ResponseStatusException If the community slug already exists or if the person is not
   *                                 authorized to create a community.
   */
  public CommunityResponse createCommunity(CreateCommunity createCommunity, Person person) {

    final Optional<Community> oldCommunity = communityRepository.findCommunityByTitleSlug(
        createCommunity.titleSlug());
    if (oldCommunity.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "community_slug_already_exist");
    }

    if (rolePermissionService.isPermitted(person, RolePermissionCommunityTypes.CREATE_COMMUNITY)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_create_community");
    }

    Community community = Community.builder()
        .title(createCommunity.title())
        .titleSlug(createCommunity.titleSlug())
        .bannerImageUrl(
            createCommunity.bannerImageUrl() != null ? createCommunity.bannerImageUrl() : null)
        .iconImageUrl(
            createCommunity.iconImageUrl() != null ? createCommunity.iconImageUrl() : null)
        .isNsfw(createCommunity.isNsfw() != null ? createCommunity.isNsfw() : false)
        .isPostingRestrictedToMods(createCommunity.isPostingRestrictedToMods())
        .description(createCommunity.description())
        .instance(localInstanceContext.instance())
        .build();
    communityService.createCommunity(community);

    return conversionService.convert(community, CommunityResponse.class);
  }

  /**
   * Updates a community based on the provided key, update form, and person.
   *
   * @param key                 The key of the community to update.
   * @param updateCommunityForm The update form containing the new community data.
   * @param person              The person performing the update.
   * @return The updated community response.
   * @throws ResponseStatusException If the community is not found, or the person is not authorized
   *                                 to perform the update.
   */
  public CommunityResponse updateCommunity(String key, UpdateCommunity updateCommunityForm,
      Person person)
  {

    String domain = ActorIdUtils.getActorDomain(key);
    if (domain != null && domain.equals(localInstanceContext.instance()
        .getDomain())) {
      key = ActorIdUtils.getActorId(key);
    }
    Optional<Community> foundCommunity = communityRepository.findCommunityByTitleSlug(key);

    if (foundCommunity.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found");
    }

    Community community = foundCommunity.get();
    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(person, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_update_community");
    }

    if (updateCommunityForm.description() != null) {
      community.setDescription(updateCommunityForm.description());
    }
    if (updateCommunityForm.iconImageUrl() != null) {
      community.setIconImageUrl(updateCommunityForm.iconImageUrl());
    }
    if (updateCommunityForm.bannerImageUrl() != null) {
      community.setBannerImageUrl(updateCommunityForm.bannerImageUrl());
    }
    if (updateCommunityForm.isNsfw() != null) {
      community.setNsfw(updateCommunityForm.isNsfw());
    }
    if (updateCommunityForm.isPostingRestrictedToMods() != null) {
      community.setPostingRestrictedToMods(updateCommunityForm.isPostingRestrictedToMods());
    }

    communityService.updateCommunity(community);

    return conversionService.convert(community, CommunityResponse.class);
  }

  /**
   * Retrieves the moderators of a community.
   *
   * @param key The key of the community.
   * @return The list of moderators in the community.
   * @throws ResponseStatusException If the community is not found.
   */
  public List<LinkPersonCommunity> getCommunityModerators(String key) {

    final Community community = communityRepository.findCommunityByTitleSlug(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));

    return linkPersonCommunityService.getLinkPersonCommunitiesByCommunityAndPersonAndLinkTypeIsIn(
            community, List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))
        .stream()
        .toList();
  }

  /**
   * Ban a person from a community.
   *
   * @param key       the community key
   * @param personKey the person key
   * @param person    the person making the request
   * @return the banned person
   * @throws ResponseStatusException if the community or person is not found, or if the person is
   *                                 not authorized to ban (details in the exception message)
   */
  public Person banPerson(String key, String personKey, Person person) {

    final Community community = communityRepository.findCommunityByTitleSlug(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));

    final Person personToBan = personRepository.findOneByNameIgnoreCase(personKey)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    if (linkPersonCommunityService.hasAnyLinkOrAdmin(personToBan, community,
        List.of(LinkPersonCommunityType.banned))) {
      return personToBan;
    }

    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(person, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_ban_person");
    }

    linkPersonCommunityService.removeAnyLink(personToBan, community,
        List.of(LinkPersonCommunityType.owner, LinkPersonCommunityType.moderator,
            LinkPersonCommunityType.follower, LinkPersonCommunityType.pending_follow));

    linkPersonCommunityService.addLink(personToBan, community, LinkPersonCommunityType.banned);

    return personToBan;
  }

  /**
   * Unban a person from a community.
   *
   * @param key       the community key
   * @param personKey the person key
   * @param person    the person making the request
   * @return the unbanned person
   * @throws ResponseStatusException if the community or person is not found, or if the person is
   *                                 not authorized to unban (details in the exception message)
   */
  public Person unbanPerson(String key, String personKey, Person person) {

    final Community community = communityRepository.findCommunityByTitleSlug(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found"));

    final Person personToUnban = personRepository.findOneByNameIgnoreCase(personKey)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "person_not_found"));

    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(personToUnban, community,
        List.of(LinkPersonCommunityType.banned))) {
      return personToUnban;
    }

    if (!linkPersonCommunityService.hasAnyLinkOrAdmin(person, community,
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_unban_person");
    }

    linkPersonCommunityService.removeLink(personToUnban, community, LinkPersonCommunityType.banned);

    return personToUnban;
  }
}
