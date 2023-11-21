package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunitySearchRepository extends JpaRepository<Community, Long> {

   // @Query(value = "SELECT p.*, ppc.* FROM posts p LEFT JOIN post_post_cross_post ppc ON ppc.post_id = p.id WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.post_body) LIKE LOWER(CONCAT('%', :keyword,'%'))",
   //        nativeQuery = true)
   @Query(value = "SELECT c.*, cl.* FROM communities c LEFT JOIN community_languages cl on cl.language_id = communitiy_id WHERE MATCH(c.title, c.titleSlug, c.description) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           nativeQuery = true)
    Page<Community> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
