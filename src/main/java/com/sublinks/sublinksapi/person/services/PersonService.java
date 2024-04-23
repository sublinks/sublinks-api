package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.entities.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.repositories.RolePermissionsRepository;
import com.sublinks.sublinksapi.authorization.repositories.RoleRepository;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.comment.repositories.CommentHistoryRepository;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.person.entities.LinkPersonInstance;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonAggregate;
import com.sublinks.sublinksapi.person.enums.PostListingMode;
import com.sublinks.sublinksapi.person.events.PersonCreatedPublisher;
import com.sublinks.sublinksapi.person.events.PersonDeletedPublisher;
import com.sublinks.sublinksapi.person.events.PersonUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.PersonAggregateRepository;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.post.repositories.PostHistoryRepository;
import com.sublinks.sublinksapi.post.services.PostService;
import com.sublinks.sublinksapi.privatemessages.services.PrivateMessageService;
import com.sublinks.sublinksapi.utils.BaseUrlUtil;
import com.sublinks.sublinksapi.utils.KeyGeneratorUtil;
import com.sublinks.sublinksapi.utils.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class PersonService {

  private final KeyGeneratorUtil keyGeneratorUtil;
  private final BaseUrlUtil baseUrlUtil;
  private final PersonRepository personRepository;
  private final PersonAggregateRepository personAggregateRepository;
  private final LocalInstanceContext localInstanceContext;
  private final PersonCreatedPublisher personCreatedPublisher;
  private final PersonUpdatedPublisher personUpdatedPublisher;
  private final PostService postService;
  private final PostHistoryRepository postHistoryRepository;
  private final CommentService commentService;
  private final CommentHistoryRepository commentHistoryRepository;
  private final PrivateMessageService privateMessageService;


  private final RoleRepository roleRepository;
  private final RolePermissionsRepository rolePermissionsRepository;
  private final RoleAuthorizingService roleAuthorizingService;
  private final PersonDeletedPublisher personDeletedPublisher;

  public Set<Role> generateInitialRoles() {

    Role adminRole = roleRepository.save(
        Role.builder().description("Admin role for admins").name("Admin").isActive(true).build());

    adminRole.setRolePermissions(Collections.singleton(rolePermissionsRepository.save(
        com.sublinks.sublinksapi.authorization.entities.RolePermissions.builder()
            .role(adminRole)
            .permission(RolePermission.ADMIN)
            .build())));

    Set<RolePermission> rolePermissions = new HashSet<>();
    rolePermissions.add(RolePermission.BANNED);
    rolePermissions.add(RolePermission.READ_PRIVATE_MESSAGES);
    rolePermissions.add(RolePermission.READ_POST);
    rolePermissions.add(RolePermission.READ_POSTS);
    rolePermissions.add(RolePermission.READ_COMMENT);
    rolePermissions.add(RolePermission.READ_COMMUNITY);
    rolePermissions.add(RolePermission.READ_COMMUNITIES);
    rolePermissions.add(RolePermission.READ_USER);
    rolePermissions.add(RolePermission.READ_MODLOG);
    Role bannedRole = roleRepository.save(Role.builder()
        .description("Banned role for banned users")
        .name("Banned")
        .isActive(true)
        .build());

    bannedRole.setRolePermissions(rolePermissions.stream()
        .map(rolePermission -> rolePermissionsRepository.save(
            RolePermissions.builder().role(bannedRole).permission(rolePermission).build()))
        .collect(Collectors.toSet()));

    rolePermissions.remove(RolePermission.BANNED);
    rolePermissions.add(RolePermission.DEFAULT);

    Role defaultUserRole = roleRepository.save(Role.builder()
        .description("Default role for all users")
        .name("User")
        .isActive(true)
        .build());

    defaultUserRole.setRolePermissions(rolePermissions.stream()
        .map(rolePermission -> rolePermissionsRepository.save(
            RolePermissions.builder().role(defaultUserRole).permission(rolePermission).build()))
        .collect(Collectors.toSet()));

    rolePermissions.remove(RolePermission.DEFAULT);
    rolePermissions.add(RolePermission.REGISTERED);

    rolePermissions.add(RolePermission.POST_UPVOTE);
    rolePermissions.add(RolePermission.POST_DOWNVOTE);
    rolePermissions.add(RolePermission.POST_NEUTRALVOTE);
    rolePermissions.add(RolePermission.COMMENT_UPVOTE);
    rolePermissions.add(RolePermission.COMMENT_DOWNVOTE);
    rolePermissions.add(RolePermission.COMMENT_NEUTRALVOTE);

    rolePermissions.add(RolePermission.CREATE_PRIVATE_MESSAGE);
    rolePermissions.add(RolePermission.UPDATE_PRIVATE_MESSAGE);
    rolePermissions.add(RolePermission.DELETE_PRIVATE_MESSAGE);

    rolePermissions.add(RolePermission.CREATE_COMMUNITY);
    rolePermissions.add(RolePermission.UPDATE_COMMUNITY);
    rolePermissions.add(RolePermission.DELETE_COMMUNITY);

    rolePermissions.add(RolePermission.CREATE_POST);
    rolePermissions.add(RolePermission.UPDATE_POST);
    rolePermissions.add(RolePermission.DELETE_POST);

    rolePermissions.add(RolePermission.CREATE_COMMENT);
    rolePermissions.add(RolePermission.UPDATE_COMMENT);
    rolePermissions.add(RolePermission.DELETE_COMMENT);

    rolePermissions.add(RolePermission.UPDATE_USER_SETTINGS);
    rolePermissions.add(RolePermission.RESET_PASSWORD);

    rolePermissions.add(RolePermission.MODERATOR_REMOVE_POST);
    rolePermissions.add(RolePermission.MODERATOR_REMOVE_COMMENT);
    rolePermissions.add(RolePermission.MODERATOR_REMOVE_COMMUNITY);
    rolePermissions.add(RolePermission.MODERATOR_BAN_USER);
    rolePermissions.add(RolePermission.MODERATOR_SPEAK);
    rolePermissions.add(RolePermission.MODERATOR_SHOW_DELETED_COMMENT);
    rolePermissions.add(RolePermission.MODERATOR_SHOW_DELETED_POST);
    rolePermissions.add(RolePermission.MODERATOR_ADD_MODERATOR);
    rolePermissions.add(RolePermission.MODERATOR_REMOVE_MODERATOR);
    rolePermissions.add(RolePermission.MODERATOR_PIN_POST);
    rolePermissions.add(RolePermission.MODERATOR_TRANSFER_COMMUNITY);

    rolePermissions.add(RolePermission.COMMUNITY_FOLLOW);
    rolePermissions.add(RolePermission.COMMUNITY_BLOCK);

    rolePermissions.add(RolePermission.USER_BLOCK);
    rolePermissions.add(RolePermission.DELETE_USER);

    rolePermissions.add(RolePermission.REPORT_COMMENT);
    rolePermissions.add(RolePermission.REPORT_POST);
    rolePermissions.add(RolePermission.REPORT_USER);
    rolePermissions.add(RolePermission.REPORT_COMMUNITY);
    rolePermissions.add(RolePermission.REPORT_PRIVATE_MESSAGE);

    rolePermissions.add(RolePermission.REPORT_COMMUNITY_RESOLVE);
    rolePermissions.add(RolePermission.REPORT_COMMUNITY_READ);

    Role registeredUserRole = roleRepository.save(Role.builder()
        .description("Default Role for all registered users")
        .name("Registered")
        .isActive(true)
        .build());

    registeredUserRole.setRolePermissions(rolePermissions.stream()
        .map(rolePermission -> rolePermissionsRepository.save(
            RolePermissions.builder().role(registeredUserRole).permission(rolePermission).build()))
        .collect(Collectors.toSet()));

    return new HashSet<>(roleRepository.findAll());
  }


  @Transactional
  public Optional<Language> getPersonDefaultPostLanguage(final Person person,
      final Community community) {

    for (Language language : person.getLanguages()) {
      if (community.getLanguages().contains(language)) {
        return Optional.of(language);
      }
    }
    return Optional.empty();
  }

  public boolean isPasswordEqual(final Person person, final String password) {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return passwordEncoder.matches(password, person.getPassword());
  }

  @Transactional
  public void createPerson(final Person person) {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    person.setPassword(passwordEncoder.encode(person.getPassword()));

    final KeyStore keys = keyGeneratorUtil.generate();
    person.setPublicKey(keys.publicKey());
    person.setPrivateKey(keys.privateKey());
    person.setLocal(true);
    person.setEmail(person.getEmail());
    // @todo: add email verification and send verification email on registration
    person.setEmailVerified(localInstanceContext.instance().getInstanceConfig() == null
        || !localInstanceContext.instance().getInstanceConfig().isRequireEmailVerification());

    boolean isInitialAdmin = localInstanceContext.instance().getDomain().isEmpty();
    if (isInitialAdmin) {
      Set<Role> roles = generateInitialRoles();
      person.setRole(roles.stream()
          .filter(x -> x.getRolePermissions()
              .stream()
              .anyMatch(y -> y.getPermission().equals(RolePermission.ADMIN)))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Admin role not found!")));
    } else {
      person.setRole(roleAuthorizingService.getUserRole());
    }

    final String userActorId = baseUrlUtil.getBaseUrl() + "/u/" + person.getName();
    person.setActorId(userActorId);

    person.setLinkPersonInstance(LinkPersonInstance.builder()
        .instance(localInstanceContext.instance())
        .person(person)
        .build());

    final List<Language> languages = new ArrayList<>(
        localInstanceContext.instance().getLanguages());
    person.setLanguages(languages);
    personRepository.save(person);

    final PersonAggregate personAggregate = PersonAggregate.builder().person(person).build();
    personAggregateRepository.save(personAggregate);
    person.setPersonAggregate(personAggregate);

    personCreatedPublisher.publish(person);
  }

  @Transactional
  public void updatePerson(final Person person) {

    personRepository.save(person);
    personUpdatedPublisher.publish(person);
  }

  public String encodePassword(final String password) {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return passwordEncoder.encode(password);
  }

  public Person getDefaultNewUser(final String name) {

    return Person.builder()
        .name(name)
        //.instance(localInstanceContext.instance())
        .password("")
        .displayName("")
        .activityPubId("")
        .avatarImageUrl("")
        .bannerImageUrl("")
        .biography("")
        .isShowAvatars(true)
        .isShowScores(true)
        .isShowAvatars(true)
        .isShowReadPosts(true)
        .isShowNsfw(true)
        .postListingType(PostListingMode.List)
        .build();
  }

  @Transactional
  public void deleteUserAccount(final Person person, final boolean deleteContent) {

    if (deleteContent) {
      // Log results ? @todo
      postService.deleteAllPostsByPerson(person);
      postHistoryRepository.deleteAllByCreator(person);
      commentService.deleteAllCommentsByPerson(person);
      commentHistoryRepository.deleteAllByCreator(person);
      privateMessageService.deleteAllPrivateMessagesByPerson(person);
    }

    person.setBiography("*Permanently Deleted*");
    person.setDisplayName("Deleted User-%s".formatted(person.getId()));

    person.setDeleted(true);
    person.setBannerImageUrl("");
    person.setAvatarImageUrl("");

    personDeletedPublisher.publish(personRepository.save(person));
  }
}
