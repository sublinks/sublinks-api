package com.sublinks.sublinksapi.community.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community findCommunityByIdOrTitleSlug(Long id, String titleSlug);
    Community findCommunityByTitleSlug(String titleSlug);
}
