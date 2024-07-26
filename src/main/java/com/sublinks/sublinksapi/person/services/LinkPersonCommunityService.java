package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.common.interfaces.ILinkingService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityDeletedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonCommunityRepository;
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
  private final LinkPersonCommunityUpdatedPublisher linkPersonCommunityUpdatedPublisher;
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

    this.createLink(newLink);
  }

  @Override
  public boolean hasLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkType(
            community, person, linkPersonCommunityType)
        .isPresent();
  }

  @Override
  public boolean hasAnyLink(Community community, Person person,
      List<LinkPersonCommunityType> linkPersonCommunityTypes) {

    return !this.linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndLinkTypeIn(
            community, linkPersonCommunityTypes)
        .isEmpty();
  }

  @Transactional
  @Override
  public void createLink(LinkPersonCommunity link) {

    this.linkPersonCommunityRepository.saveAndFlush(link);

    this.linkPersonCommunityCreatedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void createLinks(List<LinkPersonCommunity> linkPersonCommunities) {

    this.linkPersonCommunityRepository.saveAllAndFlush(linkPersonCommunities)
        .forEach(this.linkPersonCommunityCreatedPublisher::publish);
  }

  @Transactional
  @Override
  public void updateLink(LinkPersonCommunity link) {

    this.linkPersonCommunityRepository.saveAndFlush(link);

    this.linkPersonCommunityUpdatedPublisher.publish(link);

  }

  @Transactional
  @Override
  public void updateLinks(List<LinkPersonCommunity> linkPersonCommunities) {

    this.linkPersonCommunityRepository.saveAllAndFlush(linkPersonCommunities)
        .forEach(this.linkPersonCommunityUpdatedPublisher::publish);
  }

  @Transactional
  @Override
  public void deleteLink(LinkPersonCommunity link) {

    this.linkPersonCommunityRepository.delete(link);
    this.linkPersonCommunityDeletedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void deleteLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    final Optional<LinkPersonCommunity> linkOptional = this.getLink(community, person,
        linkPersonCommunityType);

    linkOptional.ifPresent((link) -> {
      this.linkPersonCommunityRepository.delete(link);
      this.linkPersonCommunityDeletedPublisher.publish(link);
    });
  }


  @Transactional
  @Override
  public void deleteLinks(List<LinkPersonCommunity> linkPersonCommunities) {

    this.linkPersonCommunityRepository.deleteAll(linkPersonCommunities);

    linkPersonCommunities.forEach(this.linkPersonCommunityDeletedPublisher::publish);

  }

  @Override
  public Optional<LinkPersonCommunity> getLink(Community community, Person person,
      LinkPersonCommunityType linkPersonCommunityType) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkType(
        community, person, linkPersonCommunityType);
  }

  @Override
  public List<LinkPersonCommunity> getLinks(Person person) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunitiesByPerson(person);
  }

  @Override
  public List<LinkPersonCommunity> getLinks(Person person, List<LinkPersonCommunityType> types) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunityByPersonAndLinkTypeIn(person,
        types);
  }

  @Override
  public List<LinkPersonCommunity> getLinks(Person person, LinkPersonCommunityType type) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunityByPersonAndLinkType(person,
        type);
  }

  @Override
  public List<LinkPersonCommunity> getLinksByEntity(Community community, Person person) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndPerson(
        community, person);
  }

  @Override
  public List<LinkPersonCommunity> getLinksByEntity(Community community,
      List<LinkPersonCommunityType> linkPersonCommunityType) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunityAndLinkTypeIn(
        community, linkPersonCommunityType);
  }

  @Override
  public List<LinkPersonCommunity> getLinksByEntity(Community community) {

    return this.linkPersonCommunityRepository.getLinkPersonCommunitiesByCommunity(community);
  }
}
