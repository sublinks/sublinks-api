package com.fedilinks.fedilinksapi.authorization;

import com.fedilinks.fedilinksapi.person.Person;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    EntityPolicy canPerson(Person person) {
        return new EntityPolicy(person);
    }

    public static class EntityPolicy {
        private final Person person;
        private boolean isCheckCreate = false;
        private boolean isCheckRead = false;
        private boolean isCheckUpdate = false;
        private boolean isCheckDelete = false;

        public EntityPolicy(Person person) {
            this.person = person;
        }

        EntityPolicy create() {
            this.isCheckCreate = true;
            return this;
        }
        EntityPolicy read() {
            this.isCheckRead = true;
            return this;
        }

        EntityPolicy update() {
            this.isCheckUpdate = true;
            return this;
        }

        EntityPolicy delete() {
            this.isCheckDelete = true;
            return this;
        }

        boolean entity(AuthorizationEntity entity) {
            // @todo build acl query
            // where entity type is entity.getEntityType() and entity_id is entity.getId()
            if(isCheckCreate) {
                // where create is true
            }
            if (isCheckRead) {
                // where read is true
            }
            if (isCheckUpdate) {
                // where update is true
            }
            if (isCheckDelete) {
                // where delete is true
            }
            // return if record found
            return true;
        }
    }
}
