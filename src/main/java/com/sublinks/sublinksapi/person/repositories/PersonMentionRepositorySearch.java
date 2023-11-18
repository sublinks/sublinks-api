package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.person.models.PersonMentionSearchCriteria;

import java.util.List;

public interface PersonMentionRepositorySearch {
    List<PersonMention> allPersonMentionBySearchCriteria(PersonMentionSearchCriteria personMentionSearchCriteria);
}
