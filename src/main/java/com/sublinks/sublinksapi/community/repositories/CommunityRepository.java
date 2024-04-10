package com.sublinks.sublinksapi.community.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long>,
    CommunitySearchRepository {

  Community findCommunityByIdOrTitleSlug(Long id, String titleSlug);

  Community findCommunityByIsLocalTrueAndTitleSlug(String titleSlug);

  Community findCommunityByTitleSlug(String titleSlug);
}
