package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public Person getPostCreator(final Post post) {

        if (post.getLinkPersonPost().isEmpty()) {
            return null;
        }
        for (LinkPersonPost linkPersonPost : post.getLinkPersonPost()) {
            if (linkPersonPost.getLinkType() == LinkPersonPostType.creator) {
                return linkPersonPost.getPerson();
            }
        }
        return null;
    }
}
