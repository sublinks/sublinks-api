package com.sublinks.sublinksapi.person.models;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonAggregate;
import com.sublinks.sublinksapi.post.entities.Post;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonContext {

  private Person person;
  private Collection<Post> posts;
  private Collection<Comment> comments;
  private PersonAggregate personAggregate;
  private Collection<Integer> discussLanguages;
  private Collection<Community> moderates;
  private Collection<Community> follows;
  private Collection<Community> communityBlocks;
  private Collection<Person> personBlocks;
}
