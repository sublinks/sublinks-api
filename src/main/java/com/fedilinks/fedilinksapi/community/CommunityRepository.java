package com.fedilinks.fedilinksapi.community;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community findCommunityByIdOrTitleSlug(Long id, String titleSlug);
    Community findCommunityByTitleSlug(String titleSlug);
}
