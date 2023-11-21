package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositorySearch {
    List<Comment> allCommentsBySearchCriteria(CommentSearchCriteria commentSearchCriteria);

    @Query(value = "SELECT * FROM comments WHERE MATCH(comment_body) "
            + "AGAINST (:keyword)", nativeQuery = true)
    Page<Comment> searchCommentsByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
