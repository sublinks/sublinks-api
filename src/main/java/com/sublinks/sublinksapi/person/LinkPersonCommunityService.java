package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommunityDeletedPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkPersonCommunityService {
    private final LinkPersonCommunityRepository linkPersonCommunityRepository;
    private final LinkPersonCommunityCreatedPublisher linkPersonCommunityCreatedPublisher;
    private final LinkPersonCommunityDeletedPublisher linkPersonCommunityDeletedPublisher;

    @Transactional
    public void addLink(Person person, Community community, LinkPersonCommunityType type) {

        final LinkPersonCommunity newLink = LinkPersonCommunity.builder()
                .community(community)
                .person(person)
                .linkType(type)
                .build();
        person.getLinkPersonCommunity().add(newLink);
        community.getLinkPersonCommunity().add(newLink);
        linkPersonCommunityRepository.save(newLink);
        linkPersonCommunityCreatedPublisher.publish(newLink);
    }

    @Transactional
    public void removeLink(Person person, Community community, LinkPersonCommunityType type) {

        final Optional<LinkPersonCommunity> linkPersonCommunity =
                linkPersonCommunityRepository.getLinkPersonCommunityByCommunityAndPersonAndLinkType(
                        community,
                        person,
                        type
                );
        if (linkPersonCommunity.isEmpty()) {
            return;
        }
        person.getLinkPersonCommunity().removeIf(l -> Objects.equals(l.getId(), linkPersonCommunity.get().getId()));
        community.getLinkPersonCommunity().removeIf(l -> Objects.equals(l.getId(), linkPersonCommunity.get().getId()));
        linkPersonCommunityRepository.delete(linkPersonCommunity.get());
        linkPersonCommunityDeletedPublisher.publish(linkPersonCommunity.get());
    }
}
