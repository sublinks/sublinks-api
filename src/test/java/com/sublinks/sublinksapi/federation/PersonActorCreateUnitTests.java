package com.sublinks.sublinksapi.federation;

import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.listeners.PersonActorCreateListener;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.queue.services.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PersonActorCreateUnitTests {

  @Mock
  private Producer federationProducer;

  private PersonActorCreateListener listener;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
    listener = new PersonActorCreateListener(federationProducer);
    listener.federationExchange = "testExchange";
  }

  @Test
  void testOnApplicationEvent() {

    Person person = new Person();
    person.setActivityPubId("testId");
    person.setActorId("testId");
    person.setDisplayName("testDisplayName");
    person.setBiography("testBio");
    person.setName("testName");
    person.setPrivateKey("testPrivateKey");
    person.setPublicKey("testPublicKey");

    PersonCreatedEvent event = new PersonCreatedEvent(this, person);

    ArgumentCaptor<Actor> actorCaptor = ArgumentCaptor.forClass(Actor.class);

    listener.onApplicationEvent(event);

    verify(federationProducer, times(1)).sendMessage(eq("testExchange"),
        eq(RoutingKey.ACTOR_CREATE.getValue()), actorCaptor.capture());

    Actor capturedActor = actorCaptor.getValue();
    assertAll(
        () -> assertEquals("testId", capturedActor.actor_id()),
        () -> assertEquals(ActorType.USER.getValue(), capturedActor.actor_type()),
        () -> assertEquals("testBio", capturedActor.bio()),
        () -> assertEquals("testName", capturedActor.display_name()),
        () -> assertEquals("testName", capturedActor.username()),
        () -> assertEquals("testPrivateKey", capturedActor.private_key()),
        () -> assertEquals("testPublicKey", capturedActor.public_key())
    );

  }
}