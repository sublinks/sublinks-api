package com.sublinks.sublinksapi.authorization.repositories;

import com.sublinks.sublinksapi.authorization.dto.Role;
import com.sublinks.sublinksapi.authorization.dto.RolePermissions;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.person.dto.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class RoleSearchRepositoryImpl implements RoleSearchRepository {

  private final EntityManager em;

  @Override
  public boolean existsByPersonIdAndRolePermissionWithin(Long id,
      Set<RolePermission> rolePermissions) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Role> cq = cb.createQuery(Role.class);

    final Root<Role> roleTable = cq.from(Role.class);

    final List<Predicate> predicates = new ArrayList<>();

    final Join<Role, Person> rolePersonJoin = roleTable.join("persons");

    predicates.add(cb.equal(rolePersonJoin.get("id"), id));

    final Join<Role, RolePermissions> rolePermissionJoin = roleTable.join("rolePermissions");

    List<Predicate> rolePermissionPredicates = new ArrayList<>();
    rolePermissions.forEach(rolePermission -> {

      rolePermissionPredicates.add(cb.equal(rolePermissionJoin.get("permission"), rolePermission));
    });

    predicates.add(cb.or(rolePermissionPredicates.toArray(new Predicate[0])));

    cq.where(predicates.toArray(new Predicate[0]));

    return !em.createQuery(cq).getResultList().isEmpty();
  }

  @Override
  public boolean existsByPersonIdAndRolePermissionContains(Long id, RolePermission rolePermission) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Role> cq = cb.createQuery(Role.class);

    final Root<Role> roleTable = cq.from(Role.class);

    final List<Predicate> predicates = new ArrayList<>();

    final Join<Role, Person> rolePersonJoin = roleTable.join("persons");

    predicates.add(cb.equal(rolePersonJoin.get("id"), id));

    final Join<Role, RolePermissions> rolePermissionJoin = roleTable.join("rolePermissions");

    List<Predicate> rolePermissionPredicates = new ArrayList<>();

    rolePermissionPredicates.add(cb.equal(rolePermissionJoin.get("permission"), rolePermission));

    predicates.add(cb.or(rolePermissionPredicates.toArray(new Predicate[0])));

    cq.where(predicates.toArray(new Predicate[0]));

    return !em.createQuery(cq).getResultList().isEmpty();
  }

  @Override
  public Collection<Role> findAllByRolePermissionContains(RolePermission rolePermission) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Role> cq = cb.createQuery(Role.class);

    final Root<Role> roleTable = cq.from(Role.class);

    final List<Predicate> predicates = new ArrayList<>();

    final Join<Role, RolePermissions> rolePermissionJoin = roleTable.join("rolePermissions");

    predicates.add(cb.equal(rolePermissionJoin.get("permission"), rolePermission));

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

  @Override
  public Collection<Role> findAllByRolePermissionWithin(Set<RolePermission> rolePermissions) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Role> cq = cb.createQuery(Role.class);

    final Root<Role> roleTable = cq.from(Role.class);

    final List<Predicate> predicates = new ArrayList<>();

    final Join<Role, RolePermissions> rolePermissionJoin = roleTable.join("rolePermissions");

    List<Predicate> rolePermissionPredicates = new ArrayList<>();
    rolePermissions.forEach(rolePermission -> {

      rolePermissionPredicates.add(cb.equal(rolePermissionJoin.get("permission"), rolePermission));
    });

    predicates.add(cb.or(rolePermissionPredicates.toArray(new Predicate[0])));

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

  @Override
  public Collection<Role> findAllByRolePermissionIsIn(Set<RolePermission> rolePermissions) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Role> cq = cb.createQuery(Role.class);

    final Root<Role> roleTable = cq.from(Role.class);

    final Join<Role, RolePermissions> rolePermissionJoin = roleTable.join("rolePermissions");

    List<Predicate> rolePermissionPredicates = new ArrayList<>();
    rolePermissions.forEach(rolePermission -> {

      rolePermissionPredicates.add(cb.equal(rolePermissionJoin.get("permission"), rolePermission));
    });

    final List<Predicate> predicates = new ArrayList<>(rolePermissionPredicates);

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }
}
