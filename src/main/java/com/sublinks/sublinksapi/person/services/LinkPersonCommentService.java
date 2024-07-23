package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import com.sublinks.sublinksapi.comment.enums.LinkPersonCommentType;
import com.sublinks.sublinksapi.comment.repositories.LinkPersonCommentRepository;
import com.sublinks.sublinksapi.common.interfaces.ILinkingService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.events.LinkPersonCommentCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommentDeletedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonCommentUpdatedPublisher;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkPersonCommentService implements
    ILinkingService<LinkPersonComment, Comment, Person, LinkPersonCommentType> {

  private final LinkPersonCommentRepository linkPersonCommentRepository;
  private final LinkPersonCommentCreatedPublisher linkPersonCommentCreatedPublisher;
  private final LinkPersonCommentUpdatedPublisher linkPersonCommentUpdatedPublisher;
  private final LinkPersonCommentDeletedPublisher linkPersonCommentDeletedPublisher;

  @Override
  public boolean hasLink(Comment comment, Person person,
      LinkPersonCommentType linkPersonCommentType) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByCommentAndPersonAndLinkType(
            comment, person, linkPersonCommentType)
        .isPresent();
  }

  @Override
  public boolean hasAnyLink(Comment comment, Person person,
      List<LinkPersonCommentType> linkPersonCommentTypes) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByCommentAndPersonAndLinkTypeIn(
            comment, person, linkPersonCommentTypes)
        .isPresent();
  }

  @Transactional
  public LinkPersonComment createCommentLink(final Comment comment, final Person person,
      final LinkPersonCommentType type) {

    final LinkPersonComment link = LinkPersonComment.builder()
        .comment(comment)
        .person(person)
        .linkType(type)
        .build();

    if (comment.getLinkPersonComment() == null) {
      comment.setLinkPersonComment(new LinkedHashSet<>());
    }

    this.createLink(link);
    return link;
  }

  @Transactional
  @Override
  public void createLink(LinkPersonComment link) {

    this.linkPersonCommentRepository.save(link);
    linkPersonCommentCreatedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void createLinks(List<LinkPersonComment> links) {

    this.linkPersonCommentRepository.saveAll(links)
        .forEach(linkPersonCommentCreatedPublisher::publish);
  }


  @Transactional
  @Override
  public void updateLink(LinkPersonComment link) {

    this.linkPersonCommentRepository.save(link);
    linkPersonCommentUpdatedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void updateLinks(List<LinkPersonComment> links) {

    linkPersonCommentRepository.saveAll(links)
        .forEach(linkPersonCommentUpdatedPublisher::publish);
  }

  @Transactional
  @Override
  public void deleteLink(LinkPersonComment link) {

    linkPersonCommentRepository.delete(link);
    linkPersonCommentDeletedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void deleteLinks(List<LinkPersonComment> links) {

    linkPersonCommentRepository.deleteAll(links);
    links.forEach(linkPersonCommentDeletedPublisher::publish);
  }

  @Override
  public Optional<LinkPersonComment> getLink(Comment comment, Person person,
      LinkPersonCommentType linkPersonCommentType) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByCommentAndPersonAndLinkType(
        comment, person, linkPersonCommentType);
  }

  @Override
  public List<LinkPersonComment> getLinks(Person person) {

    return this.linkPersonCommentRepository.getLinkPersonCommentsByPerson(person);
  }

  @Override
  public List<LinkPersonComment> getLinks(Person person,
      LinkPersonCommentType linkPersonCommentType) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByPersonAndLinkType(person,
        linkPersonCommentType);
  }

  @Override
  public List<LinkPersonComment> getLinksByEntity(Comment comment) {

    return this.linkPersonCommentRepository.getLinkPersonCommentsByComment(comment);
  }
}
