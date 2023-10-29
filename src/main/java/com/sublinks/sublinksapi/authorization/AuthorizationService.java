package com.sublinks.sublinksapi.authorization;

import com.sublinks.sublinksapi.authorization.enums.AuthorizeAction;
import com.sublinks.sublinksapi.authorization.enums.AuthorizedEntityType;
import com.sublinks.sublinksapi.person.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class AuthorizationService {
    private final AclRepository aclRepository;

    public AuthorizationService(final AclRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    public EntityPolicy canPerson(final Person person) {

        if (person == null) {
            return new EntityPolicy(ActionType.check, aclRepository);
        }
        return new EntityPolicy(person, ActionType.check, aclRepository);
    }

    public EntityPolicy allowPerson(final Person person) {

        if (person == null) {
            return new EntityPolicy(ActionType.allow, aclRepository);
        }
        return new EntityPolicy(person, ActionType.allow, aclRepository);
    }

    public EntityPolicy revokePerson(final Person person) {

        if (person == null) {
            return new EntityPolicy(ActionType.revoke, aclRepository);
        }
        return new EntityPolicy(person, ActionType.revoke, aclRepository);
    }

    public enum ActionType {
        check,
        allow,
        revoke,
    }

    public enum ResponseType {
        allow,
        decline
    }

    public static class EntityPolicy {
        private final Person person;
        private final ActionType actionType;
        private final AclRepository aclRepository;
        private final List<AuthorizeAction> authorizedActions = new ArrayList<>();
        private ResponseType defaultResponse = ResponseType.allow;

        private boolean isPermitted = true;

        private AuthorizedEntityType entityType;
        private Long entityId;

        public EntityPolicy(final ActionType actionType, final AclRepository aclRepository) {
            this.person = Person.builder().build();
            this.actionType = actionType;
            this.aclRepository = aclRepository;
        }

        public EntityPolicy(final Person person, final ActionType actionType, final AclRepository aclRepository) {
            this.person = person;
            this.actionType = actionType;
            this.aclRepository = aclRepository;
        }

        public EntityPolicy performTheAction(final AuthorizeAction authorizedAction) {

            this.authorizedActions.add(authorizedAction);
            return this;
        }

        public EntityPolicy defaultResponse(final ResponseType responseType) {

            this.defaultResponse = responseType;
            return this;
        }

        public EntityPolicy defaultingToAllow() {

            return defaultResponse(ResponseType.allow);
        }

        public EntityPolicy defaultingToDecline() {

            return defaultResponse(ResponseType.decline);
        }

        public EntityPolicy onEntity(AuthorizedEntityType entityType) {

            this.entityType = entityType;
            execute();
            return this;
        }

        public EntityPolicy onEntity(final AuthorizationEntity entity) {

            this.entityType = entity.entityType();
            this.entityId = entity.getId();
            execute();
            return this;
        }

        public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {

            if (!isPermitted) {
                throw exceptionSupplier.get();
            }
        }

        private void execute() {

            switch (actionType) {
                case allow -> createAclRules();
                case revoke -> revokeAclRules();
                default -> checkAclRules();
            }
            ;
        }

        private void checkAclRules() {

            for (AuthorizeAction authorizedAction : authorizedActions) {
                Acl acl;
                if (entityId != null) {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedAction(
                            person.getId(), entityType, entityId, authorizedAction
                    );
                } else {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndAuthorizedAction(
                            person.getId(), entityType, authorizedAction
                    );
                }
                if (acl != null && !acl.isPermitted()) {
                    isPermitted = false;
                }
                if (acl == null && defaultResponse == ResponseType.decline) {
                    isPermitted = false;
                }
            }
        }

        private void revokeAclRules() {

            for (AuthorizeAction authorizedAction : authorizedActions) {
                Acl acl;
                if (entityId != null) {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(
                            person.getId(), entityType, entityId, authorizedAction, true
                    );
                } else {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndAuthorizedActionAndPermitted(
                            person.getId(), entityType, authorizedAction, true
                    );
                }
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

        private void createAclRules() {

            for (AuthorizeAction authorizedAction : authorizedActions) {
                Acl acl;
                if (entityId != null) {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityIdAndAuthorizedActionAndPermitted(
                            person.getId(), entityType, entityId, authorizedAction, false
                    );
                } else {
                    acl = aclRepository.findAclByPersonIdAndEntityTypeAndAuthorizedActionAndPermitted(
                            person.getId(), entityType, authorizedAction, false
                    );
                }
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
