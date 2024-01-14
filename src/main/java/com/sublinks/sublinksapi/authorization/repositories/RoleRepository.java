package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.dto.Role;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleSearchRepository {

  Optional<Collection<Role>> findAllByName(String name);

  Collection<Role> findByIdIn(Collection<Long> roleIds);

  Collection<Role> findAllByNameIn(Collection<String> roleNames);

  Collection<Role> findAllByNameContaining(String roleName);

  Collection<Role> findAllByNameContainingIgnoreCase(String roleName);

}
