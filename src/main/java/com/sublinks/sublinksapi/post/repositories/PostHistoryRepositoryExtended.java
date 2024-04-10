package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.entities.Person;

public interface PostHistoryRepositoryExtended {

  int deleteAllByCreator(Person Creator);
}
