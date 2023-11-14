package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.services.LinkPersonPostService;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostAggregate;
import com.sublinks.sublinksapi.post.events.PostCreatedPublisher;
import com.sublinks.sublinksapi.post.events.PostDeletedPublisher;
import com.sublinks.sublinksapi.post.events.PostUpdatedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.utils.KeyGeneratorUtil;
import com.sublinks.sublinksapi.utils.KeyStore;
import com.sublinks.sublinksapi.utils.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostCreatedPublisher postCreatedPublisher;
    private final KeyGeneratorUtil keyGeneratorUtil;
    private final LinkPersonPostService linkPersonPostService;
    private final PostDeletedPublisher postDeletedPublisher;
    private final PostLikeService postLikeService;
    private final PostUpdatedPublisher postUpdatedPublisher;
    private final UrlUtil urlUtil;

    public String getPostMd5Hash(final Post post) {

        if (post.getLinkUrl() == null || post.getLinkUrl().isEmpty()) {
            return null;
        }

        try {
            final byte[] bytesOfLink = urlUtil.normalizeUrl(post.getLinkUrl()).getBytes(StandardCharsets.UTF_8);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] bytesOfMD5Link = md.digest(bytesOfLink);
            return new BigInteger(1, bytesOfMD5Link).toString(16);
        } catch (Exception ignored) {
            return null;
        }
    }

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

    @Transactional
    public void updatePost(final Post post) {

        postRepository.save(post);
        postUpdatedPublisher.publish(post);
    }

    @Transactional
    public void createPost(final Post post, final Person creator) {

        final KeyStore keys = keyGeneratorUtil.generate();
        post.setPublicKey(keys.publicKey());
        post.setPrivateKey(keys.privateKey());

        post.setLocal(true);
        final PostAggregate postAggregate = PostAggregate.builder()
                .post(post)
                .community(post.getCommunity())
                .build();
        post.setPostAggregate(postAggregate);
        post.setActivityPubId("");
        postRepository.save(post); // @todo fix second save making post look edited right away
        post.setActivityPubId("%s/post/%d".formatted(post.getInstance().getDomain(), post.getId()));
        postRepository.save(post);

        linkPersonPostService.createLink(creator, post, LinkPersonPostType.creator);
        postLikeService.updateOrCreatePostLikeLike(post, creator);

        postCreatedPublisher.publish(post);
    }

    public void softDeletePost(final Post post) {

        postRepository.save(post);
        postDeletedPublisher.publish(post);
    }
}
