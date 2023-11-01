package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkPersonCommunityRepository extends JpaRepository<LinkPersonCommunity, Long> {
    Optional<LinkPersonCommunity> getLinkPersonCommunityByCommunityAndPersonAndLinkType(Community community, Person person, LinkPersonCommunityType type);

    Optional<LinkPersonCommunity> getLinkPersonCommunitiesByPersonAndLinkType(Person person, LinkPersonCommunityType type);
}
