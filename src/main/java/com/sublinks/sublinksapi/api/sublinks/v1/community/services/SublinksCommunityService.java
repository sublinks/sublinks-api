package com.sublinks.sublinksapi.api.sublinks.v1.community.services;

import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.UpdateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.ActorIdUtils;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.community.services.CommunityService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
public class SublinksCommunityService {

  private final CommunityRepository communityRepository;
  private final ConversionService conversionService;
  private final CommunityService communityService;
  private final LocalInstanceContext localInstanceContext;
  private final RoleAuthorizingService roleAuthorizingService;
  private final InstanceRepository instanceRepository;
  private final LinkPersonCommunityService linkPersonCommunityService;

  public CommunityResponse createCommunity(CreateCommunity createCommunity, Person person) {

    final Optional<Community> oldCommunity = communityRepository.findCommunityByTitleSlug(
        createCommunity.titleSlug());
    if (oldCommunity.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "community_slug_already_exist");
    }

    if (roleAuthorizingService.hasAdminOrPermission(person, RolePermission.CREATE_COMMUNITY)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_create_community");
    }

    Community community = Community.builder()
        .title(createCommunity.title())
        .titleSlug(createCommunity.titleSlug())
        .bannerImageUrl(createCommunity.bannerImageUrl().orElse(null))
        .iconImageUrl(createCommunity.iconImageUrl().orElse(null))
        .isNsfw(createCommunity.isNsfw())
        .isPostingRestrictedToMods(createCommunity.isPostingRestrictedToMods())
        .description(createCommunity.description())
        .instance(localInstanceContext.instance())
        .build();
    communityService.createCommunity(community);

    return conversionService.convert(community, CommunityResponse.class);
  }

  public CommunityResponse updateCommunity(String key, UpdateCommunity updateCommunityForm,
      Person person) {

    String domain = ActorIdUtils.getActorDomain(key);
    if (domain != null && domain.equals(localInstanceContext.instance().getDomain())) {
      key = ActorIdUtils.getActorId(key);
    }
    Optional<Community> foundCommunity = communityRepository.findCommunityByTitleSlug(key);

    if (foundCommunity.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found");
    }

    Community community = foundCommunity.get();
    if (!roleAuthorizingService.isModeratorOrAdmin(person, community)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_authorized_to_update_community");
    }

    final Boolean isDeleted = updateCommunityForm.deleted().orElse(null);

    if (isDeleted != null && isDeleted != community.isDeleted()) {

      community.setDeleted(updateCommunityForm.deleted().orElse(community.isDeleted()));
      //@todo: do modlog
    }

    final Boolean isRemoved = updateCommunityForm.removed().orElse(null);

    if (isRemoved != null && isRemoved != community.isRemoved()) {

      if (!roleAuthorizingService.isModeratorOrAdmin(person, community)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_authorized_to_remove_community");
      }
      community.setRemoved(updateCommunityForm.removed().orElse(community.isRemoved()));
      //@todo: do modlog
    }

    updateCommunityForm.title().ifPresent(community::setTitle);
    updateCommunityForm.description().ifPresent(community::setDescription);
    updateCommunityForm.isNsfw().ifPresent(community::setNsfw);
    updateCommunityForm.iconImageUrl().ifPresent(community::setIconImageUrl);
    updateCommunityForm.bannerImageUrl().ifPresent(community::setBannerImageUrl);
    updateCommunityForm.isPostingRestrictedToMods().ifPresent(
        community::setPostingRestrictedToMods);
    communityRepository.save(community);

    return conversionService.convert(community, CommunityResponse.class);

  }
}
