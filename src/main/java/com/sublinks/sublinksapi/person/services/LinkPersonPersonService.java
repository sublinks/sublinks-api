package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import com.sublinks.sublinksapi.person.events.LinkPersonPersonDeletedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonPersonRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkPersonPersonService {


  private final LinkPersonPersonRepository linkPersonPersonRepository;
  private final LinkPersonPersonDeletedPublisher linkPersonPersonDeletedPublisher;

  @Transactional
  public void createLink(final Person fromPerson, final Person toPerson,
      final LinkPersonPersonType type) {

    Optional<LinkPersonPerson> linkPersonPerson = linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
        fromPerson, toPerson, type);

    if (linkPersonPerson.isPresent()) {
      linkPersonPerson.get()
          .setLinkType(type);

      fromPerson.getLinkPersonPerson()
          .removeIf(link -> link.equals(linkPersonPerson.get()));
      fromPerson.getLinkPersonPerson()
          .add(linkPersonPerson.get());
      toPerson.getLinkPersonPerson()
          .removeIf(link -> link.equals(linkPersonPerson.get()));
      toPerson.getLinkPersonPerson()
          .add(linkPersonPerson.get());

      linkPersonPersonRepository.save(linkPersonPerson.get());
      return;
    }

    final LinkPersonPerson newLink = LinkPersonPerson.builder()
        .fromPerson(fromPerson)
        .toPerson(toPerson)
        .linkType(type)
        .build();

    fromPerson.getLinkPersonPerson()
        .add(newLink);
    toPerson.getLinkPersonPerson()
        .add(newLink);
    linkPersonPersonRepository.save(newLink);

  }

  @Transactional
  public void removeLink(final Person fromPerson, final Person toPerson,
      LinkPersonPersonType type) {

    Optional<LinkPersonPerson> linkPersonPerson = linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
        fromPerson, toPerson, type);

    if (linkPersonPerson.isEmpty()) {
      return;
    }

    LinkPersonPerson link = linkPersonPerson.get();
    fromPerson.getLinkPersonPerson()
        .remove(link);
    toPerson.getLinkPersonPerson()
        .remove(link);
    linkPersonPersonRepository.delete(link);
    linkPersonPersonDeletedPublisher.publish(link);
  }

  public Optional<LinkPersonPerson> getLink(Person fromPerson, Person toPerson,
      LinkPersonPersonType linkType) {

    if (toPerson != null && toPerson.isAdmin() && linkType.equals(LinkPersonPersonType.blocked)) {
      return Optional.empty();
    }
    return linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
        fromPerson, toPerson, linkType);
  }

  public boolean hasLink(Person fromPerson, Person toPerson, LinkPersonPersonType linkType) {

    return getLink(fromPerson, toPerson, linkType).isPresent();
  }

  public boolean hasAllLinks(Person fromPerson, Person toPerson,
      List<LinkPersonPersonType> linkTypes) {

    for (LinkPersonPersonType linkType : linkTypes) {
      if (!hasLink(fromPerson, toPerson, linkType)) {
        return false;
      }
    }
    return true;
  }

  public List<LinkPersonPerson> getLinkPersonPersonByFromPersonAndLinkType(Person fromPerson,
      LinkPersonPersonType linkType) {

    return linkPersonPersonRepository.getLinkPersonPersonByFromPersonAndLinkType(fromPerson,
        linkType);
  }
}
