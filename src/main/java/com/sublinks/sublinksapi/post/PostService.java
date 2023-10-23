package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import com.sublinks.sublinksapi.person.Person;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public Person getPostCreator(Post post) {
        if (post.getLinkPersonPost().isEmpty()) {
            return null;
        }
        LinkPersonPost linkPersonPost = post.getLinkPersonPost().iterator().next();
        if (linkPersonPost == null) {
            return null;
        }
        return linkPersonPost.getPerson();
    }
}
