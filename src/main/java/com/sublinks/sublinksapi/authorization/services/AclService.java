package com.sublinks.sublinksapi.authorization.services;

import com.sublinks.sublinksapi.authorization.AclEntityInterface;
import com.sublinks.sublinksapi.authorization.entities.Acl;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionInterface;
import com.sublinks.sublinksapi.authorization.repositories.AclRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The AclService class provides methods for handling Access Control List (ACL) policies.
 */
@Service
@RequiredArgsConstructor
public class AclService {

  private final AclRepository aclRepository;
  private final RolePermissionService rolePermissionService;
  private final RoleService roleService;

  /**
   * Determines if a person is allowed to perform an action according to the ACL rules.
   *
   * @param person The person to check. Null is allowed and it will default to a new Person object.
   * @return An EntityPolicy object representing the result of the authorization check.
   */
  public EntityPolicy canPerson(final Person person) {

    if (person == null) {
      return new EntityPolicy(
          ActionType.check,
          aclRepository,
          rolePermissionService,
          roleService
      );
    }
    return new EntityPolicy(
        person,
        ActionType.check,
        aclRepository,
        rolePermissionService,
        roleService
    );
  }

  /**
   * Determines if a person is allowed to perform an action according to the ACL rules.
   *
   * @param person The person to check. Null is allowed and it will default to a new Person object.
   * @return An EntityPolicy object representing the result of the authorization check.
   */
  public EntityPolicy allowPerson(final Person person) {

    if (person == null) {
      return new EntityPolicy(
          ActionType.allow,
          aclRepository,
          rolePermissionService,
          roleService
      );
    }
    return new EntityPolicy(
        person,
        ActionType.allow,
        aclRepository,
        rolePermissionService,
        roleService
    );
  }

  /**
   * Revoke the authorization for a person to perform actions according to ACL rules.
   *
   * @param person The person to revoke the authorization for. Pass null to revoke authorization for
   *               a new Person object.
   * @return An EntityPolicy object representing the result of the authorization revocation.
   */
  public EntityPolicy revokePerson(final Person person) {

    if (person == null) {
      return new EntityPolicy(
          ActionType.revoke,
          aclRepository,
          rolePermissionService,
          roleService
      );
    }
    return new EntityPolicy(
        person,
        ActionType.revoke,
        aclRepository,
        rolePermissionService,
        roleService
    );
  }

  /**
   * The ActionType enum represents the different types of actions that can be performed in the ACL
   * system.
   */
  public enum ActionType {
    check,
    allow,
    revoke,
  }

  /**
   * The EntityPolicy class represents the policy for entity authorization based on ACL rules. It
   * provides methods to configure the policy and perform authorization checks.
   */
  public static class EntityPolicy {

    private final Person person;
    private final ActionType actionType;
    private final AclRepository aclRepository;
    private final RolePermissionService rolePermissionService;
    private final RoleService roleService;
    private final List<String> authorizedActions = new ArrayList<>();
    private boolean isPermitted = true;
    private AuthorizedEntityType entityType;
    private Long entityId;

    /**
     * The EntityPolicy class represents a policy for accessing and manipulating entities in an
     * Access Control List (ACL) system.
     *
     * @param actionType    the type of action to be performed
     * @param aclRepository the repository for accessing and manipulating ACL entities
     */
    public EntityPolicy(
        final ActionType actionType,
        final AclRepository aclRepository,
        final RolePermissionService rolePermissionService, RoleService roleService
    ) {

      this.roleService = roleService;

      this.person = Person.builder().build();
      this.actionType = actionType;
      this.aclRepository = aclRepository;
      this.rolePermissionService = rolePermissionService;
    }

    /**
     * EntityPolicy represents a policy for accessing and manipulating entities in an Access Control
     * List (ACL) system.
     *
     * @param person        the person associated with the policy
     * @param actionType    the type of action to be performed
     * @param aclRepository the repository for accessing and manipulating ACL entities
     */
    public EntityPolicy(
        final Person person,
        final ActionType actionType,
        final AclRepository aclRepository,
        RolePermissionService rolePermissionService, RoleService roleService
    ) {

      this.person = person;
      this.actionType = actionType;
      this.aclRepository = aclRepository;
      this.rolePermissionService = rolePermissionService;
      this.roleService = roleService;
    }

    /**
     * Adds an authorized action to the EntityPolicy.
     *
     * @param permission the authorized action to be added
     * @return the EntityPolicy object
     */
    public EntityPolicy performTheAction(final RolePermissionInterface permission) {

      this.authorizedActions.add(permission.toString());
      return this;
    }

    /**
     * Executes the specified action on the provided entity and sets the entity type and ID for the
     * policy.
     *
     * @param entity the entity on which the action will be performed
     * @return the modified EntityPolicy object
     */
    public EntityPolicy onEntity(final AclEntityInterface entity) {

      this.entityType = entity.entityType();
      this.entityId = entity.getId();
      execute();
      return this;
    }

    /**
     * Checks if the current policy is permitted.
     *
     * @return true if the policy is permitted, false otherwise
     */
    public boolean isPermitted() {

      return isPermitted;
    }

    /**
     * Throws an exception if the specified condition is not satisfied.
     *
     * @param exceptionSupplier supplier that provides the exception to be thrown
     * @param <X>               the type of exception to be thrown
     * @throws X the thrown exception if the condition is not satisfied
     */
    public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier)
        throws X {

      if (!isPermitted) {
        throw exceptionSupplier.get();
      }
    }

    /**
     * Executes the specified action based on the provided parameters.
     */
    private void execute() {

      if (entityType == null && entityId == null) {
        switch (actionType) {
          case allow -> throw new RuntimeException("Permissions cannot be granted to roles.");
          case revoke -> throw new RuntimeException("Permissions cannot be revoked from roles.");
          default -> checkRolePermission();
        }
        return;
      }
      switch (actionType) {
        case allow -> createAclRules();
        case revoke -> revokeAclRules();
        default -> checkAclRules();
      }
    }

    /**
     * Checks the role permissions for the current policy. If a person is associated with the
     * policy, it checks if the person is permitted to perform the specified role permission. If no
     * person is associated with the policy, it checks if the default guest role is permitted to
     * perform the specified role permission.
     */
    private void checkRolePermission() {

      if (this.person != null) {
        this.isPermitted = rolePermissionService.isPermitted(
            this.person,
            (RolePermissionInterface) this.authorizedActions
        );
      } else {
        this.isPermitted = rolePermissionService.isPermitted(
            roleService.getDefaultGuestRole(() -> new RuntimeException("No guest role defined.")),
            (RolePermissionInterface) this.authorizedActions
        );
      }
    }

    /**
     * Checks the ACL rules for the authorized actions.
     */
    private void checkAclRules() {

      for (String authorizedAction : authorizedActions) {
        Acl acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(
            person.getId(), entityType, entityId, authorizedAction
        );
        if (acl != null && !acl.isPermitted()) {
          isPermitted = false;
        }
        if (acl == null) {
          isPermitted = false;
        }
      }
    }

    /**
     * Revoke ACL rules for the authorized actions.
     */
    private void revokeAclRules() {

      for (String authorizedAction : authorizedActions) {
        Acl acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(
            person.getId(), entityType, entityId, authorizedAction, true
        );
        if (acl == null) {
          aclRepository.saveAndFlush(Acl.builder()
              .personId(person.getId())
              .entityType(entityType)
              .entityId(entityId)
              .authorizedAction(authorizedAction)
              .permitted(false)
              .build());
        } else {
          acl.setPermitted(false);
          aclRepository.saveAndFlush(acl);
        }
      }
    }

    /**
     * Creates ACL rules for the authorized actions.
     */
    private void createAclRules() {

      for (String authorizedAction : authorizedActions) {
        Acl acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(
            person.getId(), entityType, entityId, authorizedAction, false
        );
        if (acl == null) {
          aclRepository.saveAndFlush(Acl.builder()
              .personId(person.getId())
              .entityType(entityType)
              .entityId(entityId)
              .authorizedAction(authorizedAction)
              .permitted(true)
              .build());
        } else {
          acl.setPermitted(true);
          aclRepository.saveAndFlush(acl);
        }
      }
    }
  }
}
