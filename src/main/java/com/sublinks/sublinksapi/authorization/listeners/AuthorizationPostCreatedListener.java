package com.sublinks.sublinksapi.authorization.listeners;

import com.sublinks.sublinksapi.authorization.enums.RolePermissionPostTypes;
import com.sublinks.sublinksapi.authorization.services.AclService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.events.PostCreatedEvent;
import com.sublinks.sublinksapi.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of the ApplicationListener interface and listens for the
 * PostCreatedEvent. Upon receiving the event, it retrieves the creator of the post using the
 * PostService.
 */
@Component
@RequiredArgsConstructor
public class AuthorizationPostCreatedListener implements ApplicationListener<PostCreatedEvent> {

  private final AclService aclService;
  private final PostService postService;

  @Override
  public void onApplicationEvent(PostCreatedEvent event) {

    Person person = postService.getPostCreator(event.getPost());
    aclService.allowPerson(person)
        .performTheAction(RolePermissionPostTypes.DELETE_POST)
        .performTheAction(RolePermissionPostTypes.UPDATE_POST)
        .onEntity(event.getPost());
  }
}
