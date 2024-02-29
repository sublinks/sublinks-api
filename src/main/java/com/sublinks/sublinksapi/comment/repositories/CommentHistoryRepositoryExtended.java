package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentHistory;
import com.sublinks.sublinksapi.person.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentHistoryRepositoryExtended {

  int deleteAllByCreator(Person Creator);
}
