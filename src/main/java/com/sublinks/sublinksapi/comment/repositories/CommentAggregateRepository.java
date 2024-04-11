package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAggregateRepository extends JpaRepository<CommentAggregate, Long> {

}
