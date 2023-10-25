package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.Person;

import java.util.List;

public interface PostRepositorySearch {
    List<Post> filterByFetchRequest(Person person, String communityName);
}
