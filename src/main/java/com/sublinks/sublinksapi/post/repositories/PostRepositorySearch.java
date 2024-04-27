package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.shared.RemovedState;
import java.util.List;

public interface PostRepositorySearch {

  List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);

  List<Post> allPostsByCommunityAndPersonAndRemoved(Community community, Person person,
      List<RemovedState> removedStates);

  List<Post> allPostsByPersonAndRemoved(Person person, List<RemovedState> removedStates);
}
