package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.person.Person;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final AclRepository aclRepository;

    public AuthorizationService(AclRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    EntityPolicy canPerson(Person person) {
        return new EntityPolicy(person, ActionType.check, aclRepository);
    }

    EntityPolicy allowPerson(Person person) {
        return new EntityPolicy(person, ActionType.allow, aclRepository);
    }

    public enum ActionType {
        check,
        allow
    }

    public static class EntityPolicy {
        private final Person person;
        private final ActionType actionType;
        private final AclRepository aclRepository;
        private boolean isCreate = false;
        private boolean isRead = false;
        private boolean isUpdate = false;
        private boolean isDelete = false;

        public EntityPolicy(Person person, ActionType actionType, AclRepository aclRepository) {
            this.person = person;
            this.actionType = actionType;
            this.aclRepository = aclRepository;
        }

        private void check(Acl acl) throws Exception {
            if (isCreate && !acl.isCanCreate()) {
                throw new Exception("You cannot create this entity");
            }
            if (isRead && !acl.isCanRead()) {
                throw new Exception("You cannot read this entity");
            }
            if (isUpdate && !acl.isCanUpdate()) {
                throw new Exception("You cannot update this entity");
            }
            if (isDelete && !acl.isCanDelete()) {
                throw new Exception("You cannot delete this entity");
            }
        }

        private void allow(Acl acl) {
            acl.setCanCreate(isCreate);
            acl.setCanRead(isRead);
            acl.setCanUpdate(isUpdate);
            acl.setCanDelete(isDelete);
            aclRepository.saveAndFlush(acl);
        }

        EntityPolicy create() {
            this.isCreate = true;
            return this;
        }

        EntityPolicy read() {
            this.isRead = true;
            return this;
        }

        EntityPolicy update() {
            this.isUpdate = true;
            return this;
        }

        EntityPolicy delete() {
            this.isDelete = true;
            return this;
        }

        boolean entity(AuthorizationEntity entity) {
            try {
                Acl acl = aclRepository.findAclByPersonIdAndEntityTypeAndEntityId(person.getId(), entity.entityType(), entity.getId());
                switch (this.actionType) {
                    case allow:
                        allow(acl);
                        break;
                    case check:
                        check(acl);
                        break;
                    default:
                        throw new RuntimeException("Invalid action type for authorization service.");
                }
                return true;
            } catch (RuntimeException exception) {
                throw exception;
            } catch (Exception exception) {
                return false;
            }
        }
    }
}
