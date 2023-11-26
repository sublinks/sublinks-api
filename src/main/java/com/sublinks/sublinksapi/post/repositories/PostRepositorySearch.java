package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import java.util.List;

public interface PostRepositorySearch {

  List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);

  List<Post> allPostsByCommunityAndPerson(Community community, Person person);

}
