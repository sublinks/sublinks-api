package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.LinkPersonPerson;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPersonType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonPersonRepository extends JpaRepository<LinkPersonPerson, Long> {

  Optional<LinkPersonPerson> getLinkPersonPersonByFromPersonAndToPersonAndLinkType(
      Person fromPerson, Person toPerson, LinkPersonPersonType linkType);

  List<LinkPersonPerson> getLinkPersonPersonByFromPersonAndLinkType(Person fromPerson,
      LinkPersonPersonType linkType);

  List<LinkPersonPerson> getLinkPersonPeopleByFromPerson(Person fromPerson);

  List<LinkPersonPerson> getLinkPersonPeopleByToPerson(Person toPerson);
}
