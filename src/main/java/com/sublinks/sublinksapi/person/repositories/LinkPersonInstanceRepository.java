package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.person.dto.LinkPersonInstance;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonInstanceRepository extends JpaRepository<LinkPersonInstance, Long> {

  Collection<LinkPersonInstance> getLinkPersonInstancesByInstanceAndLinkTypeIsIn(Instance instance,
      Collection<LinkPersonInstanceType> linkTypes);

  Collection<LinkPersonInstance> getLinkPersonInstancesByInstanceAndLinkTypeIsInAndPerson(
      Instance instance, Collection<LinkPersonInstanceType> linkTypes, Person person);
}
