package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.person.models.PersonMentionSearchCriteria;
import java.util.List;

public interface PersonMentionRepositorySearch {

  List<PersonMention> allPersonMentionBySearchCriteria(
      PersonMentionSearchCriteria personMentionSearchCriteria);
}
