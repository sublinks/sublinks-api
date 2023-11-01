package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.services.LinkPersonPostService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostAggregates;
import com.sublinks.sublinksapi.post.events.PostCreatedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostAggregatesRepository;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.utils.KeyService;
import com.sublinks.sublinksapi.utils.KeyStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostAggregatesRepository postAggregatesRepository;
    private final PostCreatedPublisher postCreatedPublisher;
    private final KeyService keyService;
    private final LinkPersonPostService linkPersonPostService;

    public Person getPostCreator(final Post post) {

        if (post.getLinkPersonPost() == null || post.getLinkPersonPost().isEmpty()) {
            return null;
        }
        for (LinkPersonPost linkPersonPost : post.getLinkPersonPost()) {
            if (linkPersonPost.getLinkType() == LinkPersonPostType.creator) {
                return linkPersonPost.getPerson();
            }
        }
        return null;
    }

    public void createPost(final Post post, final Community community, final Person creator) {

        post.setCommunity(community);

        final KeyStore keys = keyService.generate();
        post.setPublicKey(keys.publicKey());
        post.setPrivateKey(keys.privateKey());

        post.setLocal(true);

        postRepository.save(post);

        linkPersonPostService.createLink(creator, post, LinkPersonPostType.creator);

        final PostAggregates postAggregates = PostAggregates.builder()
                .post(post)
                .community(post.getCommunity())
                .build();
        postAggregatesRepository.save(postAggregates);
        post.setPostAggregates(postAggregates);
        postCreatedPublisher.publish(post);
    }
}
