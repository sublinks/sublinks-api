package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonCommunityRepository extends JpaRepository<LinkPersonCommunity, Long> {

  Optional<LinkPersonCommunity> getLinkPersonCommunityByCommunityAndPersonAndLinkType(
      Community community, Person person, LinkPersonCommunityType type);

  List<LinkPersonCommunity> getLinkPersonCommunityByCommunityAndPersonAndLinkTypeIsIn(
      Community community, Person person, List<LinkPersonCommunityType> types);

  Optional<LinkPersonCommunity> getLinkPersonCommunityByPersonAndLinkType(Person person,
      LinkPersonCommunityType type);

  Collection<LinkPersonCommunity> getLinkPersonCommunitiesByPersonAndLinkType(Person person,
      LinkPersonCommunityType type);

  Collection<LinkPersonCommunity> getLinkPersonCommunitiesByCommunityAndLinkTypeIsIn(
      Community community, List<LinkPersonCommunityType> types);
}
