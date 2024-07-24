package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.common.interfaces.ILinkingService;
import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.events.LinkPersonPostCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonPostDeletedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonPostUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.LinkPersonPostRepository;
import com.sublinks.sublinksapi.post.entities.Post;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkPersonPostService implements
    ILinkingService<LinkPersonPost, Post, Person, LinkPersonPostType> {

  private final LinkPersonPostRepository linkPersonPostRepository;
  private final LinkPersonPostCreatedPublisher linkPersonPostCreatedPublisher;
  private final LinkPersonPostDeletedPublisher linkPersonPostDeletedPublisher;
  private final LinkPersonPostUpdatedPublisher linkPersonPostUpdatedPublisher;

  @Override
  public boolean hasLink(Post post, Person person, LinkPersonPostType linkPersonPostType) {

    return linkPersonPostRepository.getLinkPersonPostByPostAndPersonAndLinkType(post, person,
            linkPersonPostType)
        .isPresent();
  }

  @Override
  public boolean hasAnyLink(Post post, Person person,
      List<LinkPersonPostType> linkPersonPostTypes) {

    return linkPersonPostRepository.getLinkPersonPostByPostAndPersonAndLinkTypeIn(post, person,
            linkPersonPostTypes)
        .isPresent();
  }

  @Transactional
  public LinkPersonPost createPostLink(final Post post, final Person person,
      final LinkPersonPostType type) {

    final LinkPersonPost link = LinkPersonPost.builder()
        .post(post)
        .person(person)
        .linkType(type)
        .build();

    if (post.getLinkPersonPost() == null) {
      post.setLinkPersonPost(new LinkedHashSet<>());
    }

    if (person.getLinkPersonPost() == null) {
      person.setLinkPersonPost(new LinkedHashSet<>());
    }

    this.createLink(link);
    return link;
  }

  @Transactional
  @Override
  public void createLink(LinkPersonPost linkPersonPost) {

    final Person person = linkPersonPost.getPerson();

    if (person.getLinkPersonPost() == null) {
      person.setLinkPersonPost(new LinkedHashSet<>());
    }

    person.getLinkPersonPost()
        .add(linkPersonPost);

    linkPersonPostCreatedPublisher.publish(linkPersonPostRepository.save(linkPersonPost));
  }

  @Transactional
  @Override
  public void createLinks(List<LinkPersonPost> linkPersonPosts) {

    linkPersonPostRepository.saveAll(linkPersonPosts)
        .forEach((linkPersonPost) -> {

          final Person person = linkPersonPost.getPerson();

          if (person.getLinkPersonPost() == null) {
            person.setLinkPersonPost(new LinkedHashSet<>());
          }

          person.getLinkPersonPost()
              .add(linkPersonPost);

          linkPersonPostCreatedPublisher.publish(linkPersonPost);
        });
  }

  @Transactional
  @Override
  public void updateLink(LinkPersonPost linkPersonPost) {

    final Person person = linkPersonPost.getPerson();

    if (person.getLinkPersonPost() == null) {
      person.setLinkPersonPost(new LinkedHashSet<>());
    }

    person.getLinkPersonPost()
        .add(linkPersonPost);

    linkPersonPostUpdatedPublisher.publish(linkPersonPostRepository.save(linkPersonPost));
  }

  @Transactional
  @Override
  public void updateLinks(List<LinkPersonPost> linkPersonPosts) {

    linkPersonPostRepository.saveAll(linkPersonPosts)
        .forEach((linkPersonPost) -> {
          final Person person = linkPersonPost.getPerson();

          if (person.getLinkPersonPost() == null) {
            person.setLinkPersonPost(new LinkedHashSet<>());
          }

          person.getLinkPersonPost()
              .add(linkPersonPost);

          linkPersonPostUpdatedPublisher.publish(linkPersonPost);
        });
  }

  @Transactional
  @Override
  public void deleteLink(LinkPersonPost linkPersonPost) {

    final Person person = linkPersonPost.getPerson();

    if (person.getLinkPersonPost() == null) {
      person.setLinkPersonPost(new LinkedHashSet<>());
    }

    person.getLinkPersonPost()
        .remove(linkPersonPost);

    linkPersonPostRepository.delete(linkPersonPost);
    linkPersonPostDeletedPublisher.publish(linkPersonPost);
  }

  @Transactional
  @Override
  public void deleteLinks(List<LinkPersonPost> linkPersonPosts) {

    linkPersonPostRepository.deleteAll(linkPersonPosts);
    linkPersonPosts.forEach((linkPersonPost) -> {
      final Person person = linkPersonPost.getPerson();

      if (person.getLinkPersonPost() == null) {
        person.setLinkPersonPost(new LinkedHashSet<>());
      }

      person.getLinkPersonPost()
          .remove(linkPersonPost);

      linkPersonPostDeletedPublisher.publish(linkPersonPost);
    });
  }

  @Override
  public Optional<LinkPersonPost> getLink(Post post, Person person,
      LinkPersonPostType linkPersonPostType) {

    return linkPersonPostRepository.getLinkPersonPostByPostAndPersonAndLinkType(post, person,
        linkPersonPostType);
  }

  @Override
  public List<LinkPersonPost> getLinks(Person person) {

    return linkPersonPostRepository.getLinkPersonPostByPerson(person);
  }

  @Override
  public List<LinkPersonPost> getLinks(Person person, LinkPersonPostType linkPersonPostType) {

    return linkPersonPostRepository.getLinkPersonPostByPersonAndLinkType(person,
        linkPersonPostType);
  }

  @Override
  public List<LinkPersonPost> getLinksByEntity(Post post) {

    return linkPersonPostRepository.getLinkPersonPostByPost(post);
  }
}