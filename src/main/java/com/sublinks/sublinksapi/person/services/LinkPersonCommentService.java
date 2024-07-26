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
import jakarta.persistence.EntityManager;
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

  private final EntityManager em;

  @Override
  public void refresh(LinkPersonComment linkPersonComment) {

    final Person person = linkPersonComment.getPerson();
    final Comment comment = linkPersonComment.getComment();

    em.refresh(person);
    em.refresh(comment);
  }

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
    this.refresh(link);
    this.linkPersonCommentCreatedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void createLinks(List<LinkPersonComment> links) {

    this.linkPersonCommentRepository.saveAllAndFlush(links)
        .forEach((link) -> {
          this.refresh(link);
          this.linkPersonCommentCreatedPublisher.publish(link);
        });
  }


  @Transactional
  @Override
  public void updateLink(LinkPersonComment link) {

    final Person person = link.getPerson();

    if (person.getLinkPersonComment() == null) {
      person.setLinkPersonComment(new LinkedHashSet<>());
    }

    person.getLinkPersonComment()
        .add(link);

    this.linkPersonCommentRepository.saveAndFlush(link);
    this.refresh(link);
    this.linkPersonCommentUpdatedPublisher.publish(link);
  }

  @Transactional
  @Override
  public void updateLinks(List<LinkPersonComment> links) {

    this.linkPersonCommentRepository.saveAllAndFlush(links)
        .forEach((link) -> {

          this.refresh(link);
          linkPersonCommentUpdatedPublisher.publish(link);
        });
  }

  @Transactional
  @Override
  public void deleteLink(LinkPersonComment link) {

    this.linkPersonCommentRepository.delete(link);
    this.refresh(link);
    this.linkPersonCommentDeletedPublisher.publish(link);
  }

  @Override
  public void deleteLink(Comment comment, Person person,
      LinkPersonCommentType linkPersonCommentType) {

    final Optional<LinkPersonComment> linkPersonCommentOptional = this.linkPersonCommentRepository.deleteLinkPersonCommentByCommentAndPersonAndLinkType(
        comment, person, linkPersonCommentType);

    if (linkPersonCommentOptional.isPresent()) {

      this.refresh(linkPersonCommentOptional.get());
      this.linkPersonCommentDeletedPublisher.publish(linkPersonCommentOptional.get());
    }
  }

  @Transactional
  @Override
  public void deleteLinks(List<LinkPersonComment> links) {

    this.linkPersonCommentRepository.deleteAll(links);
    links.forEach((link) -> {
      this.refresh(link);
      this.linkPersonCommentDeletedPublisher.publish(link);
    });
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
  public List<LinkPersonComment> getLinksByEntity(Comment comment, Person person) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByCommentAndPerson(comment, person);
  }

  @Override
  public List<LinkPersonComment> getLinksByEntity(Comment comment,
      List<LinkPersonCommentType> linkPersonCommentTypes) {

    return this.linkPersonCommentRepository.getLinkPersonCommentByCommentAndLinkTypeIn(comment,
        linkPersonCommentTypes);
  }

  @Override
  public List<LinkPersonComment> getLinksByEntity(Comment comment) {

    return this.linkPersonCommentRepository.getLinkPersonCommentsByComment(comment);
  }
}
