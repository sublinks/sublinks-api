package com.sublinks.sublinksapi.authorization.services;

import com.sublinks.sublinksapi.authorization.entities.Role;
import com.sublinks.sublinksapi.authorization.entities.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommentTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommunityTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionInstanceTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionInterface;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionMediaTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionModLogTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPersonTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPostTypes;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPrivateMessageTypes;
import com.sublinks.sublinksapi.authorization.enums.RoleTypes;
import com.sublinks.sublinksapi.authorization.repositories.RolePermissionsRepository;
import com.sublinks.sublinksapi.authorization.repositories.RoleRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for generating the initial roles for the application.
 */
@Service
@RequiredArgsConstructor
public class InitialRoleSetupService {

  private final RoleRepository roleRepository;
  private final RolePermissionsRepository rolePermissionsRepository;

  /**
   * Generates the initial roles for the application.
   */
  public void generateInitialRoles() {

    if (roleRepository.findAll()
        .isEmpty()) {
      createAdminRole();
      createBannedRole();
      createGuestRole();
      createRegisteredRole();
    }
  }

  /**
   * Saves the role permissions for a given role.
   *
   * @param role            the role for which the permissions are being saved
   * @param rolePermissions the set of role permissions to be saved
   */
  private void savePermissions(Role role, Set<RolePermissionInterface> rolePermissions) {

    role.setRolePermissions(rolePermissions.stream()
        .map(rolePermission -> rolePermissionsRepository.save(RolePermissions.builder()
            .role(role)
            .permission(rolePermission.toString())
            .build()))
        .collect(Collectors.toSet()));
  }

  /**
   * Applies common permissions to a set of role permissions.
   *
   * @param rolePermissions the set of role permissions to which common permissions will be added
   */
  private void applyCommonPermissions(Set<RolePermissionInterface> rolePermissions) {

    rolePermissions.add(RolePermissionPrivateMessageTypes.READ_PRIVATE_MESSAGES);
    rolePermissions.add(RolePermissionPostTypes.READ_POST);
    rolePermissions.add(RolePermissionPostTypes.READ_POSTS);
    rolePermissions.add(RolePermissionCommentTypes.READ_COMMENT);
    rolePermissions.add(RolePermissionCommentTypes.READ_COMMENTS);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITY);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITIES);
    rolePermissions.add(RolePermissionPersonTypes.READ_USER);
    rolePermissions.add(RolePermissionPersonTypes.READ_USERS);
    rolePermissions.add(RolePermissionModLogTypes.READ_MODLOG);
    rolePermissions.add(RolePermissionPersonTypes.READ_PERSON_AGGREGATION);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITY_AGGREGATION);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITY_MODERATORS);
    rolePermissions.add(RolePermissionPersonTypes.USER_LOGIN);
    rolePermissions.add(RolePermissionInstanceTypes.INSTANCE_SEARCH);
    rolePermissions.add(RolePermissionInstanceTypes.INSTANCE_READ_ANNOUNCEMENT);
    rolePermissions.add(RolePermissionInstanceTypes.INSTANCE_READ_ANNOUNCEMENTS);
  }

  /**
   * Creates the admin role with the specified permissions.
   */
  private void createAdminRole() {

    Set<RolePermissionInterface> rolePermissions = new HashSet<>();
    Role adminRole = roleRepository.save(Role.builder()
        .description("Admin role for admins")
        .name(RoleTypes.ADMIN.toString())
        .isActive(true)
        .build());

    savePermissions(adminRole, rolePermissions);
  }

  /**
   * Creates the guest role with all associated permissions.
   */
  private void createGuestRole() {

    Set<RolePermissionInterface> rolePermissions = new HashSet<>();
    applyCommonPermissions(rolePermissions);

    Role defaultUserRole = roleRepository.save(Role.builder()
        .description("Default role for all users")
        .name(RoleTypes.GUEST.toString())
        .isActive(true)
        .build());

    savePermissions(defaultUserRole, rolePermissions);
  }

  /**
   * Creates the banned role with all associated permissions.
   */
  private void createBannedRole() {

    Set<RolePermissionInterface> rolePermissions = new HashSet<>();
    applyCommonPermissions(rolePermissions);

    Role bannedRole = roleRepository.save(Role.builder()
        .description("Banned role for banned users")
        .name(RoleTypes.BANNED.toString())
        .isActive(true)
        .build());

    savePermissions(bannedRole, rolePermissions);
  }

  /**
   * Creates the registered role with all associated permissions.
   */
  private void createRegisteredRole() {

    Set<RolePermissionInterface> rolePermissions = new HashSet<>();
    applyCommonPermissions(rolePermissions);

    rolePermissions.add(RolePermissionMediaTypes.CREATE_MEDIA);

    rolePermissions.add(RolePermissionPostTypes.POST_UPVOTE);
    rolePermissions.add(RolePermissionPostTypes.POST_DOWNVOTE);
    rolePermissions.add(RolePermissionPostTypes.POST_NEUTRALVOTE);
    rolePermissions.add(RolePermissionCommentTypes.COMMENT_UPVOTE);
    rolePermissions.add(RolePermissionCommentTypes.COMMENT_DOWNVOTE);
    rolePermissions.add(RolePermissionCommentTypes.COMMENT_NEUTRALVOTE);

    rolePermissions.add(RolePermissionPrivateMessageTypes.CREATE_PRIVATE_MESSAGE);
    rolePermissions.add(RolePermissionPrivateMessageTypes.UPDATE_PRIVATE_MESSAGE);
    rolePermissions.add(RolePermissionPrivateMessageTypes.DELETE_PRIVATE_MESSAGE);

    rolePermissions.add(RolePermissionCommunityTypes.CREATE_COMMUNITY);
    rolePermissions.add(RolePermissionCommunityTypes.UPDATE_COMMUNITY);
    rolePermissions.add(RolePermissionCommunityTypes.DELETE_COMMUNITY);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITY_AGGREGATION);
    rolePermissions.add(RolePermissionCommunityTypes.READ_COMMUNITY_MODERATORS);

    rolePermissions.add(RolePermissionPostTypes.CREATE_POST);
    rolePermissions.add(RolePermissionPostTypes.UPDATE_POST);
    rolePermissions.add(RolePermissionPostTypes.DELETE_POST);

    rolePermissions.add(RolePermissionCommentTypes.CREATE_COMMENT);
    rolePermissions.add(RolePermissionCommentTypes.UPDATE_COMMENT);
    rolePermissions.add(RolePermissionCommentTypes.DELETE_COMMENT);

    rolePermissions.add(RolePermissionPersonTypes.UPDATE_USER_SETTINGS);
    rolePermissions.add(RolePermissionPersonTypes.RESET_PASSWORD);
    rolePermissions.add(RolePermissionPersonTypes.USER_EXPORT);
    rolePermissions.add(RolePermissionPersonTypes.MARK_MENTION_AS_READ);
    rolePermissions.add(RolePermissionPersonTypes.MARK_REPLIES_AS_READ);
    rolePermissions.add(RolePermissionPersonTypes.READ_MENTION_USER);
    rolePermissions.add(RolePermissionPersonTypes.READ_REPLIES);

    rolePermissions.add(RolePermissionPostTypes.MODERATOR_REMOVE_POST);
    rolePermissions.add(RolePermissionCommentTypes.MODERATOR_REMOVE_COMMENT);
    rolePermissions.add(RolePermissionCommunityTypes.MODERATOR_REMOVE_COMMUNITY);
    rolePermissions.add(RolePermissionCommunityTypes.MODERATOR_BAN_USER);

    rolePermissions.add(RolePermissionCommentTypes.MODERATOR_SPEAK);
    rolePermissions.add(RolePermissionCommentTypes.MODERATOR_SHOW_DELETED_COMMENT);
    rolePermissions.add(RolePermissionPostTypes.MODERATOR_SHOW_DELETED_POST);
    rolePermissions.add(RolePermissionCommunityTypes.MODERATOR_ADD_MODERATOR);
    rolePermissions.add(RolePermissionCommunityTypes.MODERATOR_REMOVE_MODERATOR);
    rolePermissions.add(RolePermissionPostTypes.MODERATOR_PIN_POST);
    rolePermissions.add(RolePermissionCommunityTypes.MODERATOR_TRANSFER_COMMUNITY);

    rolePermissions.add(RolePermissionCommunityTypes.COMMUNITY_FOLLOW);
    rolePermissions.add(RolePermissionCommunityTypes.COMMUNITY_BLOCK);

    rolePermissions.add(RolePermissionPersonTypes.USER_BLOCK);
    rolePermissions.add(RolePermissionPersonTypes.DELETE_USER);

    rolePermissions.add(RolePermissionCommentTypes.REPORT_COMMENT);
    rolePermissions.add(RolePermissionPostTypes.REPORT_POST);
    rolePermissions.add(RolePermissionPersonTypes.REPORT_USER);
    rolePermissions.add(RolePermissionCommunityTypes.REPORT_COMMUNITY);
    rolePermissions.add(RolePermissionPrivateMessageTypes.REPORT_PRIVATE_MESSAGE);

    rolePermissions.add(RolePermissionCommunityTypes.REPORT_COMMUNITY_RESOLVE);
    rolePermissions.add(RolePermissionCommunityTypes.REPORT_COMMUNITY_READ);

    Role registeredUserRole = roleRepository.save(Role.builder()
        .description("Default Role for all registered users")
        .name(RoleTypes.REGISTERED.toString())
        .isActive(true)
        .build());

    savePermissions(registeredUserRole, rolePermissions);
  }
}
