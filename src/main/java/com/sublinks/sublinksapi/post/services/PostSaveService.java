package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostSave;
import com.sublinks.sublinksapi.post.events.PostSaveCreatedPublisher;
import com.sublinks.sublinksapi.post.events.PostSaveDeletedPublisher;
import com.sublinks.sublinksapi.post.repositories.PostSaveRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSaveService {

  private final PostSaveRepository postSaveRepository;
  private final PostSaveCreatedPublisher postSaveCreatedPublisher;
  private final PostSaveDeletedPublisher postSaveDeletedPublisher;

  public boolean isPostSaved(final Post post, final Person person) {

    final Optional<PostSave> currentPostSave = postSaveRepository.getPostSaveByPostAndPerson(post,
        person);
    return currentPostSave.isPresent();
  }

  public void createPostSave(final Post post, final Person person) {

    if (isPostSaved(post, person)) {
      return;
    }

    final PostSave postSave = PostSave.builder()
        .post(post)
        .person(person)
        .build();
    postSaveRepository.save(postSave);
    postSaveCreatedPublisher.publish(postSave);
  }

  public void deletePostSave(final Post post, final Person person) {

    final Optional<PostSave> postSave = postSaveRepository.getPostSaveByPostAndPerson(post, person);
    postSave.ifPresent(postSaveRepository::delete);
    postSave.ifPresent(postSaveDeletedPublisher::publish);
  }
}
