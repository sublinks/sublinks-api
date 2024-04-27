package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentSearchRepository extends JpaRepository<Comment, Long> {

  @Query(value = "SELECT c.* FROM comments c LEFT JOIN posts p on p.id = c.post_id LEFT JOIN people pp ON pp.id = c.person_id LEFT JOIN communities cm ON cm.id = c.community_id WHERE c.search_vector @@ to_tsquery(:keyword);", countQuery = "SELECT COUNT(c.id) FROM comments c WHERE c.search_vector @@ to_tsquery(:keyword);", nativeQuery = true)
  Page<Comment> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
