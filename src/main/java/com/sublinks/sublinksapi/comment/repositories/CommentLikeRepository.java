package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentLike;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>,
    CommentLikeRepositorySearch {

  Optional<CommentLike> getCommentLikeByPersonAndComment(Person person, Comment comment);
}
