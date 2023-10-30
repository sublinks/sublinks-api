package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void updateOrCreatePostLikeLike(final Post post, final Person person) {

        updateOrCreatePostLike(post, person, 1);
    }

    @Transactional
    public void updateOrCreatePostLikeDislike(final Post post, final Person person) {

        updateOrCreatePostLike(post, person, -1);
    }

    @Transactional
    public void updateOrCreatePostLikeNeutral(final Post post, final Person person) {

        updateOrCreatePostLike(post, person, 0);
    }

    public Optional<PostLike> getPostLike(final Post post, final Person person) {

        return postLikeRepository.getPostLikesByPostAndPerson(post, person);
    }

    private void updateOrCreatePostLike(final Post post, final Person person, final int score) {

        final Optional<PostLike> postLike = getPostLike(post, person);
        if (postLike.isEmpty()) {
            createPostLike(post, person, score);
        } else {
            updatePostLike(postLike.get(), score);
        }
    }

    private void createPostLike(final Post post, final Person person, final int score) {

        final PostLike postLike = PostLike.builder()
                .post(post)
                .person(person)
                .isUpVote(score == 0)
                .isDownVote(score == -1)
                .score(score)
                .build();
        postLikeRepository.save(postLike);
        // @todo publish like created
    }

    private void updatePostLike(final PostLike postLike, final int score) {

        postLike.setUpVote(score == 1);
        postLike.setDownVote(score == -1);
        postLike.setScore(score);
        postLikeRepository.save(postLike);
        // @todo publish like updated
    }
}
