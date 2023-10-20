package com.sublinksapp.sublinksappapi.person;

import com.sublinksapp.sublinksappapi.comment.Comment;
import com.sublinksapp.sublinksappapi.community.Community;
import com.sublinksapp.sublinksappapi.post.Post;
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
