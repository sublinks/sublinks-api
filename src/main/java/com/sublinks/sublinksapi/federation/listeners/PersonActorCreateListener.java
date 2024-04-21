package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.federation.enums.ActorType;
import com.sublinks.sublinksapi.federation.enums.RoutingKey;
import com.sublinks.sublinksapi.federation.models.Actor;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import com.sublinks.sublinksapi.queue.services.Producer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class PersonActorCreateListener implements ApplicationListener<PersonCreatedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(PersonActorCreateListener.class);

  @Value("${sublinks.federation.exchange}")
  private String federationExchange;
  final private Producer federationProducer;

  @Override
  public void onApplicationEvent(@NonNull PersonCreatedEvent event) {

    if (getFederationProducer() == null) {
      logger.error("federation producer is not instantiated properly");
      return;
    }

    Person person = event.getPerson();

    final Actor actorMessage = Actor.builder()
        .actor_id(person.getActorId())
        .actor_type(ActorType.USER.getValue())
        .bio(person.getBiography())
        .display_name(person.getName())
        .username(person.getUsername())
        .matrix_user_id(person.getMatrixUserId())
        .private_key(person.getPrivateKey())
        .public_key(person.getPublicKey())
        .build();

    getFederationProducer().sendMessage(getFederationExchange(), RoutingKey.ACTOR_CREATE.getValue(),
        actorMessage);
    logger.info(String.format("person actor created %s", person.getActorId()));
  }
}