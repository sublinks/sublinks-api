package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.person.entities.LinkPersonInstance;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The LinkPersonInstanceRepository interface provides methods for managing link person instances.
 */
public interface LinkPersonInstanceRepository extends JpaRepository<LinkPersonInstance, Long> {

  Optional<LinkPersonInstance> findLinkPersonInstanceByInstanceAndPerson(
      Instance instance, Person person);
}