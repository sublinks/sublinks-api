package com.sublinks.sublinksapi.federation;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.events.CommunityCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.listeners.CommunityActorCreateListener;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.queue.services.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommunityActorCreateUnitTests {
  @Mock
  private Producer federationProducer;

  private CommunityActorCreateListener listener;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    listener = new CommunityActorCreateListener();
    listener.federationExchange = "testExchange";
    listener.federationProducer = federationProducer;
  }

  @Test
  void testOnApplicationEvent() {
    Community community = new Community();
    community.setActivityPubId("testId");
    community.setDescription("testDescription");
    community.setTitle("testTitle");
    community.setPrivateKey("testPrivateKey");
    community.setPublicKey("testPublicKey");

    CommunityCreatedEvent event = new CommunityCreatedEvent(this, community);

    ArgumentCaptor<Actor> actorCaptor = ArgumentCaptor.forClass(Actor.class);

    listener.onApplicationEvent(event);

    verify(federationProducer, times(1)).sendMessage(eq("testExchange"), eq(RoutingKey.ACTOR_CREATE.getValue()), actorCaptor.capture());

    Actor capturedActor = actorCaptor.getValue();
    assertAll(
        () -> assertEquals("testId", capturedActor.actor_id()),
        () -> assertEquals(ActorType.COMMUNITY.getValue(), capturedActor.actor_type()),
        () -> assertEquals("testDescription", capturedActor.bio()),
        () -> assertEquals("testTitle", capturedActor.display_name()),
        () -> assertEquals("testPrivateKey", capturedActor.private_key()),
        () -> assertEquals("testPublicKey", capturedActor.public_key())
    );
  }
}
