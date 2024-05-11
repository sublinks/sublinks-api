package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityDeletedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkPersonCommunityService {

  private final LinkPersonCommunityRepository linkPersonCommunityRepository;
  private final LinkPersonCommunityCreatedPublisher linkPersonCommunityCreatedPublisher;
  private final LinkPersonCommunityDeletedPublisher linkPersonCommunityDeletedPublisher;

  public boolean hasLinkOrAdmin(Person person, Community community, LinkPersonCommunityType type) {

    return RolePermissionService.isAdmin(person) || hasLink(person, community, type);
  }

  public boolean hasAnyLinkOrAdmin(Person person, Community community,
      List<LinkPersonCommunityType> types)
  {

    return RolePermissionService.isAdmin(person) || hasAnyLink(person, community, types);
  }

  public boolean hasLink(Person person, Community community, LinkPersonCommunityType type) {

    final Optional<LinkPersonCommunity> linkPersonCommunity = linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkType(
        community, person, type);
    return linkPersonCommunity.isPresent();
  }

  public boolean hasAnyLink(Person person, Community community, List<LinkPersonCommunityType> types)
  {

    final List<LinkPersonCommunity> linkPersonCommunity = linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkTypeIsIn(
        community, person, types);
    return linkPersonCommunity.isEmpty();
  }

  @Transactional
  public void addLink(Person person, Community community, LinkPersonCommunityType type) {

    final LinkPersonCommunity newLink = LinkPersonCommunity.builder()
        .community(community)
        .person(person)
        .linkType(type)
        .build();
    person.getLinkPersonCommunity()
        .add(newLink);
    community.getLinkPersonCommunity()
        .add(newLink);
    linkPersonCommunityRepository.save(newLink);
    linkPersonCommunityCreatedPublisher.publish(newLink);
  }

  @Transactional
  public void removeLink(Person person, Community community, LinkPersonCommunityType type) {

    final Optional<LinkPersonCommunity> linkPersonCommunity = linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkType(
        community, person, type);
    if (linkPersonCommunity.isEmpty()) {
      return;
    }
    person.getLinkPersonCommunity()
        .removeIf(l -> Objects.equals(l.getId(), linkPersonCommunity.get()
            .getId()));
    community.getLinkPersonCommunity()
        .removeIf(l -> Objects.equals(l.getId(), linkPersonCommunity.get()
            .getId()));
    linkPersonCommunityRepository.delete(linkPersonCommunity.get());
    linkPersonCommunityDeletedPublisher.publish(linkPersonCommunity.get());
  }

  public Collection<Community> getPersonLinkByType(Person person, LinkPersonCommunityType type) {

    Collection<LinkPersonCommunity> linkPersonCommunities = linkPersonCommunityRepository.getLinkPersonCommunitiesByPersonAndLinkType(
        person, type);

    Collection<Community> communities = new ArrayList<>();
    for (LinkPersonCommunity linkPersonCommunity : linkPersonCommunities) {
      communities.add(linkPersonCommunity.getCommunity());
    }
    return communities;
  }

  public Collection<Person> getPersonsFromCommunityAndListTypes(Community community,
      List<LinkPersonCommunityType> types)
  {

    Collection<LinkPersonCommunity> linkPersonCommunities = linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndLinkTypeIsIn(
        community, types);

    return linkPersonCommunities.stream()
        .map(LinkPersonCommunity::getPerson)
        .toList();
  }

  public Collection<LinkPersonCommunity> getLinkPersonCommunitiesByCommunityAndPersonAndLinkTypeIsIn(
      Community community, List<LinkPersonCommunityType> types)
  {

    return linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndLinkTypeIsIn(
        community, types);
  }
}
