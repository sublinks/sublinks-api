package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.common.interfaces.ILinkingService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityDeletedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkPersonCommunityService implements
    ILinkingService<LinkPersonCommunity, Community, Person, LinkPersonCommunityType> {

  private final LinkPersonCommunityRepository linkPersonCommunityRepository;
  private final LinkPersonCommunityCreatedPublisher linkPersonCommunityCreatedPublisher;
  private final LinkPersonCommunityDeletedPublisher linkPersonCommunityDeletedPublisher;

  @Transactional
  public void createLinkPersonCommunityLink(Community community, Person person,
      LinkPersonCommunityType type) {

    createLinkPersonCommunityLink(community, person, type, null);
  }

  @Transactional
  public void createLinkPersonCommunityLink(Community community, Person person,
      LinkPersonCommunityType type, Date expireAt) {

    final LinkPersonCommunity newLink = LinkPersonCommunity.builder()
        .community(community)
        .person(person)
        .linkType(type)
        .expireAt(expireAt)
        .build();
    person.getLinkPersonCommunity()
        .add(newLink);
    community.getLinkPersonCommunity()
        .add(newLink);
    linkPersonCommunityRepository.save(newLink);
    linkPersonCommunityCreatedPublisher.publish(newLink);
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
      List<LinkPersonCommunityType> types) {

    Collection<LinkPersonCommunity> linkPersonCommunities = linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndLinkTypeIsIn(
        community, types);

    return linkPersonCommunities.stream()
        .map(LinkPersonCommunity::getPerson)
        .toList();
  }

  @Override
  public boolean hasLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    return false;
  }

  @Override
  public boolean hasAnyLink(Community community, Person person,
      List<LinkPersonCommunityType> linkPersonCommunityTypes) {

    return false;
  }

  @Override
  public void createLink(LinkPersonCommunity linkPersonCommunity) {

  }

  @Override
  public void createLinks(List<LinkPersonCommunity> linkPersonCommunities) {

  }

  @Override
  public void updateLink(LinkPersonCommunity linkPersonCommunity) {

  }

  @Override
  public void updateLinks(List<LinkPersonCommunity> linkPersonCommunities) {

  }

  @Override
  public void deleteLink(LinkPersonCommunity linkPersonCommunity) {

  }

  @Override
  public void deleteLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

  }


  @Override
  public void deleteLinks(List<LinkPersonCommunity> linkPersonCommunities) {

  }

  @Override
  public Optional<LinkPersonCommunity> getLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    return Optional.empty();
  }

  @Override
  public List<LinkPersonCommunity> getLinks(Person person) {

    return List.of();
  }

  @Override
  public List<LinkPersonCommunity> getLinks(Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    return List.of();
  }

  @Override
  public List<LinkPersonCommunity> getLinksByEntity(Community community) {

    return List.of();
  }
}
