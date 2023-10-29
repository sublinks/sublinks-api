package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.LinkPersonPost;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.event.PostCreatedPublisher;
import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostAggregatesRepository postAggregatesRepository;
    private final PostCreatedPublisher postCreatedPublisher;
    private final KeyService keyService;

    public PostService(
            final PostRepository postRepository,
            final PostAggregatesRepository postAggregatesRepository,
            final PostCreatedPublisher postCreatedPublisher,
            final KeyService keyService
    ) {
        this.postRepository = postRepository;
        this.postAggregatesRepository = postAggregatesRepository;
        this.postCreatedPublisher = postCreatedPublisher;
        this.keyService = keyService;
    }

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

    public void createPost(final Post post) {

        final KeyStore keys = keyService.generate();
        post.setPublicKey(keys.publicKey());
        post.setPrivateKey(keys.privateKey());
        post.setLocal(true);
        postRepository.save(post);

        final PostAggregates postAggregates = PostAggregates.builder()
                .post(post)
                .community(post.getCommunity())
                .build();
        postAggregatesRepository.save(postAggregates);
        post.setPostAggregates(postAggregates);
        postCreatedPublisher.publish(post);
    }
}
