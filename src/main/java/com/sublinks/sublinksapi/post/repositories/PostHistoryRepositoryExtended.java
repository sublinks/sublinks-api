package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.dto.Person;

public interface PostHistoryRepositoryExtended {

  int deleteAllByCreator(Person Creator);
}
