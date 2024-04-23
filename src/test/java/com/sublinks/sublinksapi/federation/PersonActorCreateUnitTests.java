package com.sublinks.sublinksapi.federation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.listeners.PersonActorCreateListener;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import com.sublinks.sublinksapi.queue.services.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PersonActorCreateUnitTests {

  @MockBean
  private Producer producer;
  private PersonActorCreateListener listener;

  @BeforeEach
  void setUp() {

    listener = new PersonActorCreateListener(producer);
    listener.setFederationExchange("testExchange");
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

    verify(listener.getFederationProducer(), times(1)).sendMessage(eq("testExchange"),
        eq(RoutingKey.ACTOR_CREATE.getValue()), actorCaptor.capture());

    Actor capturedActor = actorCaptor.getValue();
    assertAll(
        () -> assertEquals("testId", capturedActor.id()),
        () -> assertEquals(ActorType.USER.getValue(), capturedActor.actor_type()),
        () -> assertEquals("testBio", capturedActor.bio()),
        () -> assertEquals("testDisplayName", capturedActor.name()),
        () -> assertEquals("testName", capturedActor.username()),
        () -> assertEquals("testPrivateKey", capturedActor.private_key()),
        () -> assertEquals("testPublicKey", capturedActor.public_key())
    );

  }

  @Test
  void testOnApplicationEventWithBlankDisplayName() {

    Person person = new Person();
    person.setActivityPubId("testId");
    person.setActorId("testId");
    person.setBiography("testBio");
    person.setName("testName");
    person.setDisplayName("");
    person.setPrivateKey("testPrivateKey");
    person.setPublicKey("testPublicKey");

    PersonCreatedEvent event = new PersonCreatedEvent(this, person);

    ArgumentCaptor<Actor> actorCaptor = ArgumentCaptor.forClass(Actor.class);

    listener.onApplicationEvent(event);

    verify(listener.getFederationProducer(), times(1)).sendMessage(eq("testExchange"),
        eq(RoutingKey.ACTOR_CREATE.getValue()), actorCaptor.capture());

    Actor capturedActor = actorCaptor.getValue();
    assertAll(
        () -> assertEquals("testId", capturedActor.id()),
        () -> assertEquals(ActorType.USER.getValue(), capturedActor.actor_type()),
        () -> assertEquals("testBio", capturedActor.bio()),
        () -> assertEquals("testName", capturedActor.name()),
        () -> assertEquals("testName", capturedActor.username()),
        () -> assertEquals("testPrivateKey", capturedActor.private_key()),
        () -> assertEquals("testPublicKey", capturedActor.public_key())
    );

  }
}