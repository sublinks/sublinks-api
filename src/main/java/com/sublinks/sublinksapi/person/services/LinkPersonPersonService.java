package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.common.interfaces.ILinkingService;
import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import com.sublinks.sublinksapi.person.events.LinkPersonPersonCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonPersonDeletedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonPersonUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonPersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkPersonPersonService implements
    ILinkingService<LinkPersonPerson, Person, Person, LinkPersonPersonType> {


  private final LinkPersonPersonRepository linkPersonPersonRepository;
  private final LinkPersonPersonCreatedPublisher linkPersonPersonCreatedPublisher;
  private final LinkPersonPersonUpdatedPublisher linkPersonPersonUpdatedPublisher;
  private final LinkPersonPersonDeletedPublisher linkPersonPersonDeletedPublisher;

  @Override
  public Optional<LinkPersonPerson> getLink(Person fromPerson, Person toPerson,
      LinkPersonPersonType linkType) {

    if (toPerson != null && toPerson.isAdmin() && linkType.equals(LinkPersonPersonType.blocked)) {
      return Optional.empty();
    }
    return linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
        fromPerson, toPerson, linkType);
  }

  @Override
  public List<LinkPersonPerson> getLinks(Person person) {

    return linkPersonPersonRepository.getLinkPersonPeopleByFromPerson(person);
  }

  @Override
  public List<LinkPersonPerson> getLinks(Person person, LinkPersonPersonType linkPersonPersonType) {

    return linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndLinkType(person,
        linkPersonPersonType);
  }

  @Override
  public List<LinkPersonPerson> getLinksByEntity(Person person) {

    return linkPersonPersonRepository.getLinkPersonPeopleByToPerson(person);
  }

  @Override
  public boolean hasLink(Person fromPerson, Person toPerson, LinkPersonPersonType linkType) {

    return getLink(fromPerson, toPerson, linkType).isPresent();
  }

  @Override
  public boolean hasAnyLink(Person person, Person person2,
      List<LinkPersonPersonType> linkPersonPersonTypes) {

    for (LinkPersonPersonType linkType : linkPersonPersonTypes) {
      if (hasLink(person, person2, linkType)) {
        return true;
      }
    }
    return false;
  }

  public LinkPersonPerson createPersonLink(final Person fromPerson, final Person toPerson,
      final LinkPersonPersonType type) {

    final LinkPersonPerson link = LinkPersonPerson.builder()
        .fromPerson(fromPerson)
        .toPerson(toPerson)
        .linkType(type)
        .build();

    if (fromPerson.getLinkPersonPerson() == null) {
      fromPerson.setLinkPersonPerson(Set.of());
    }

    if (toPerson.getLinkPersonPerson() == null) {
      toPerson.setLinkPersonPerson(Set.of());
    }

    this.createLink(link);
    return link;
  }

  @Override
  public void createLink(LinkPersonPerson linkPersonPerson) {

    final Person fromPerson = linkPersonPerson.getFromPerson();
    final Person toPerson = linkPersonPerson.getToPerson();
    fromPerson.getLinkPersonPerson()
        .add(linkPersonPerson);
    toPerson.getLinkPersonPerson()
        .add(linkPersonPerson);
    linkPersonPersonCreatedPublisher.publish(linkPersonPersonRepository.save(linkPersonPerson));
  }

  @Override
  public void createLinks(List<LinkPersonPerson> linkPersonPeople) {

    linkPersonPeople.forEach(linkPersonPerson -> {
      final Person fromPerson = linkPersonPerson.getFromPerson();
      final Person toPerson = linkPersonPerson.getToPerson();
      fromPerson.getLinkPersonPerson()
          .add(linkPersonPerson);
      toPerson.getLinkPersonPerson()
          .add(linkPersonPerson);
      linkPersonPersonCreatedPublisher.publish(linkPersonPersonRepository.save(linkPersonPerson));
    });
  }

  @Override
  public void updateLink(LinkPersonPerson linkPersonPerson) {

    final Person fromPerson = linkPersonPerson.getFromPerson();
    final Person toPerson = linkPersonPerson.getToPerson();

    fromPerson.getLinkPersonPerson()
        .removeIf(link -> link.getId()
            .equals(linkPersonPerson.getId()));
    fromPerson.getLinkPersonPerson()
        .add(linkPersonPerson);

    toPerson.getLinkPersonPerson()
        .removeIf(link -> link.getId()
            .equals(linkPersonPerson.getId()));
    toPerson.getLinkPersonPerson()
        .add(linkPersonPerson);

    linkPersonPersonUpdatedPublisher.publish(linkPersonPersonRepository.save(linkPersonPerson));

  }

  @Override
  public void updateLinks(List<LinkPersonPerson> linkPersonPeople) {

    linkPersonPeople.forEach(linkPersonPerson -> {
      final Person fromPerson = linkPersonPerson.getFromPerson();
      final Person toPerson = linkPersonPerson.getToPerson();

      fromPerson.getLinkPersonPerson()
          .removeIf(link -> link.getId()
              .equals(linkPersonPerson.getId()));
      fromPerson.getLinkPersonPerson()
          .add(linkPersonPerson);

      toPerson.getLinkPersonPerson()
          .removeIf(link -> link.getId()
              .equals(linkPersonPerson.getId()));
      toPerson.getLinkPersonPerson()
          .add(linkPersonPerson);

      linkPersonPersonUpdatedPublisher.publish(linkPersonPersonRepository.save(linkPersonPerson));
    });
  }

  @Override
  public void deleteLink(LinkPersonPerson linkPersonPerson) {

    final Person fromPerson = linkPersonPerson.getFromPerson();
    final Person toPerson = linkPersonPerson.getToPerson();
    fromPerson.getLinkPersonPerson()
        .remove(linkPersonPerson);
    toPerson.getLinkPersonPerson()
        .remove(linkPersonPerson);
    linkPersonPersonRepository.delete(linkPersonPerson);
    linkPersonPersonDeletedPublisher.publish(linkPersonPerson);
  }

  @Override
  public void deleteLinks(List<LinkPersonPerson> linkPersonPeople) {

    linkPersonPeople.forEach(linkPersonPerson -> {
      final Person fromPerson = linkPersonPerson.getFromPerson();
      final Person toPerson = linkPersonPerson.getToPerson();
      fromPerson.getLinkPersonPerson()
          .remove(linkPersonPerson);
      toPerson.getLinkPersonPerson()
          .remove(linkPersonPerson);
      linkPersonPersonRepository.delete(linkPersonPerson);
      linkPersonPersonDeletedPublisher.publish(linkPersonPerson);
    });

  }

  public List<LinkPersonPerson> getLinkPersonPersonByFromPersonAndLinkType(Person fromPerson,
      LinkPersonPersonType linkType) {

    return linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndLinkType(fromPerson,
        linkType);
  }
}
