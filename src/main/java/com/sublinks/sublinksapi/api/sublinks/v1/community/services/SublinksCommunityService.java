package com.sublinks.sublinksapi.api.sublinks.v1.community.services;

import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CreateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.community.models.UpdateCommunity;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.ActorIdUtils;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.community.services.CommunityService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.person.entities.Person;
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

  public CommunityResponse createCommunity(CreateCommunity createCommunity, Person person) {

    final Community oldCommunity = communityRepository.findCommunityByTitleSlug(
        createCommunity.titleSlug());
    if (oldCommunity != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "community_slug_already_exist");
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

    Community community;
    try {
      String domain = ActorIdUtils.getActorDomain(key);
      if (domain != null && domain.equals(localInstanceContext.instance().getDomain())) {
        key = ActorIdUtils.getActorId(key);
      }
      community = communityRepository.findCommunityByTitleSlug(key);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "community_not_found");
    }

    if (updateCommunityForm.deleted().orElse(null) != null
        && updateCommunityForm.deleted().get() != community.isDeleted()) {
      community.setDeleted(updateCommunityForm.deleted().orElse(community.isDeleted()));
      //@todo: do modlog
    }
    if (updateCommunityForm.removed().orElse(null) != null
        && updateCommunityForm.removed().get() != community.isRemoved()) {
      community.setRemoved(updateCommunityForm.removed().orElse(community.isRemoved()));
      //@todo: do modlog
    }

    community.setTitle(updateCommunityForm.title());
    community.setDescription(updateCommunityForm.description());
    community.setBannerImageUrl(updateCommunityForm.bannerImageUrl().orElse(null));
    community.setIconImageUrl(updateCommunityForm.iconImageUrl().orElse(null));
    community.setNsfw(updateCommunityForm.isNsfw());
    community.setPostingRestrictedToMods(updateCommunityForm.isPostingRestrictedToMods());
    communityRepository.save(community);

    return conversionService.convert(community, CommunityResponse.class);

  }
}
