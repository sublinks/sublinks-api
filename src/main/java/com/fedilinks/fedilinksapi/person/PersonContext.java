package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.comment.Comment;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonContext {
    private Person person;
    private Collection<Post> posts;
    private Collection<Comment> comments;
    private PersonAggregates personAggregates;
    private Collection<Integer> discussLanguages;
    private Collection<Community> moderates;
    private Collection<Community> follows;
    private Collection<Community> communityBlocks;
    private Collection<Person> personBlocks;
}
