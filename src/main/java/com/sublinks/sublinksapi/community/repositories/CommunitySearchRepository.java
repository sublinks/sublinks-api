package com.sublinks.sublinksapi.community.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunitySearchRepository{
    @Query(value = "SELECT * FROM communities WHERE MATCH(title, title_slug, description) "
            + "AGAINST (:keyword)", nativeQuery = true)
    Page<Community> searchCommunitiesByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
