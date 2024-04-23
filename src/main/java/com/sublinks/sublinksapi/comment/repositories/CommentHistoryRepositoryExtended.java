package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.person.entities.Person;

public interface CommentHistoryRepositoryExtended {

  int deleteAllByCreator(Person Creator);
}
