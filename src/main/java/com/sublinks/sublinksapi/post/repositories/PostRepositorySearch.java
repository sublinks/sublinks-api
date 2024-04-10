package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import java.util.List;

public interface PostRepositorySearch {

  List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);

  List<Post> allPostsByCommunityAndPerson(Community community, Person person);

}
