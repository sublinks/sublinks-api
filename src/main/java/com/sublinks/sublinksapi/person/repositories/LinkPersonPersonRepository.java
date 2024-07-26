package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonPersonRepository extends JpaRepository<LinkPersonPerson, Long> {

  Optional<LinkPersonPerson> getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
      Person fromPerson, Person toPerson, LinkPersonPersonType type);

  List<LinkPersonPerson> getLinkPersonPersonByFromPersonAndLinkType(Person fromPerson,
      LinkPersonPersonType type);

  List<LinkPersonPerson> getLinkPersonPeopleByFromPerson(Person fromPerson);

  List<LinkPersonPerson> getLinkPersonPeopleByToPerson(Person toPerson);

  Optional<LinkPersonPerson> deleteLinkPersonPersonByFromPersonAndToPersonAndLinkType(
      Person fromPerson, Person toPerson, LinkPersonPersonType linkType);

  List<LinkPersonPerson> getLinkPersonPeopleByFromPersonAndToPerson(Person fromPerson,
      Person toPerson);

  List<LinkPersonPerson> getLinkPersonPeopleByFromPersonAndLinkTypeIn(Person fromPerson,
      List<LinkPersonPersonType> types);

  Optional<LinkPersonPerson> getLinkPersonPeopleByFromPersonAndToPersonAndLinkTypeIn(
      Person fromPerson, Person toPerson, List<LinkPersonPersonType> linkPersonPersonTypes);
}
