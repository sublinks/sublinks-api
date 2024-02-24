package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import com.sublinks.sublinksapi.shared.RemovedState;
import java.util.List;

public interface PostRepositorySearch {

  List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);

  List<Post> allPostsByCommunityAndPersonAndRemoved(Community community, Person person, List<RemovedState> removedStates);

  List<Post> allPostsByPersonAndRemoved(Person person, List<RemovedState> removedStates);
}
