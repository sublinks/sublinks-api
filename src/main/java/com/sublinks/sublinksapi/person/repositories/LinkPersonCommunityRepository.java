package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonCommunityRepository extends JpaRepository<LinkPersonCommunity, Long> {

  Optional<LinkPersonCommunity> getLinkPersonCommunityByCommunityAndPersonAndLinkType(
      Community community, Person person, LinkPersonCommunityType type);

  List<LinkPersonCommunity> getLinkPersonCommunityByCommunityAndPersonAndLinkTypeIsIn(
      Community community, Person person, List<LinkPersonCommunityType> types);

  List<LinkPersonCommunity> getLinkPersonCommunityByPersonAndLinkType(Person person,
      LinkPersonCommunityType type);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByPersonAndLinkType(Person person,
      LinkPersonCommunityType type);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByCommunityAndLinkTypeIn(Community community,
      List<LinkPersonCommunityType> types);


  List<LinkPersonCommunity> getLinkPersonCommunitiesByCommunityAndLinkType(Community community,
      LinkPersonCommunityType type);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByPerson(Person person);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByExpireAtBefore(Date expireAt);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByCommunityAndPerson(Community community,
      Person person);

  List<LinkPersonCommunity> getLinkPersonCommunitiesByCommunity(Community community);
}
