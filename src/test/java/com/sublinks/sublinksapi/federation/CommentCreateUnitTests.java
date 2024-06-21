package com.sublinks.sublinksapi.federation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.listeners.CommentCreateListener;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.queue.services.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CommentCreateUnitTests {

  @MockBean
  private Producer producer;
  private CommentCreateListener listener;

  @BeforeEach
  void setUp() {

    listener = new CommentCreateListener(producer);
    listener.setFederationExchange("testExchange");
  }

  @Test
  void testOnApplicationEvent() {

    Comment comment = new Comment();
    Person person = new Person();
    Post post = new Post();

    person.setActivityPubId("testPersonActivityPubId");
    post.setActivityPubId("testPostActivityPubId");
    comment.setActivityPubId("testId");
    comment.setCommentBody("Test Body");
    comment.setPerson(person);
    comment.setPost(post);

    CommentCreatedEvent event = new CommentCreatedEvent(this, comment);

    ArgumentCaptor<com.sublinks.sublinksapi.federation.models.Comment> commentCaptor = ArgumentCaptor.forClass(
        com.sublinks.sublinksapi.federation.models.Comment.class);

    listener.onApplicationEvent(event);

    verify(listener.getFederationProducer(), times(1)).sendMessage(eq("testExchange"),
        eq(RoutingKey.COMMENT_CREATE.getValue()), commentCaptor.capture());

    com.sublinks.sublinksapi.federation.models.Comment capturedComment = commentCaptor.getValue();
    assertAll(
        () -> assertEquals("testId", capturedComment.id()),
        () -> assertEquals("Test Body", capturedComment.content())
    );
  }
}
