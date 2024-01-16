package com.sublinks.sublinksapi.authorization.services;

import com.sublinks.sublinksapi.authorization.dto.Role;
import com.sublinks.sublinksapi.authorization.enums.RolePermission;
import com.sublinks.sublinksapi.authorization.repositories.RoleRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleAuthorizingService {

  private final RoleRepository roleRepository;

  public static boolean isBanned(final Role role) {

    return role.getRolePermissions().stream()
        .anyMatch(x -> x.getPermission().equals(RolePermission.BANNED));
  }

  public static boolean isBanned(final Person person) {

    if (person == null || person.getRole() == null) {
      return false;
    }

    return isBanned(person.getRole());
  }

  public static boolean isAdmin(final Person person) {

    if (person == null || person.getRole() == null) {
      return false;
    }

    return isAdmin(person.getRole());
  }

  public static boolean isAdmin(@NonNull final Role role) {

    return role.getRolePermissions().stream()
        .anyMatch(x -> x.getPermission().equals(RolePermission.ADMIN));
  }

  public static <X extends Throwable> void isAdminElseThrow(Person person,
      Supplier<? extends X> exceptionSupplier) throws X {

    if (!isAdmin(person)) {
      throw exceptionSupplier.get();
    }
  }

  public Set<Person> getAdmins() {

    // i didnt used hashset here because it is not safe to use as it could duplicate.
    return roleRepository.findAllByRolePermissionContains(RolePermission.ADMIN).stream()
        .map(role -> role.getPersons().stream().toList()).reduce(new ArrayList<>(), (arr, ele) -> {
          ele.forEach(x -> {
            if (!arr.contains(x)) {
              arr.add(x);
            }
          });
          return arr;
        }).stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  public Set<Person> getUsers() {

    // i didnt used hashset here because it is not safe to use as it could duplicate.
    return roleRepository.findAllByRolePermissionContains(RolePermission.DEFAULT).stream()
        .map(role -> role.getPersons().stream().toList()).reduce(new ArrayList<>(), (arr, ele) -> {
          ele.forEach(x -> {
            if (!arr.contains(x)) {
              arr.add(x);
            }
          });
          return arr;
        }).stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  public Set<Person> getBannedUsers() {

    // i didnt used hashset here because it is not safe to use as it could duplicate.
    return roleRepository.findAllByRolePermissionContains(RolePermission.BANNED).stream()
        .map(role -> role.getPersons().stream().toList()).reduce(new ArrayList<>(), (arr, ele) -> {
          ele.forEach(x -> {
            if (!arr.contains(x)) {
              arr.add(x);
            }
          });
          return arr;
        }).stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  public Role getAdminRole() {

    return roleRepository.findAllByRolePermissionContains(RolePermission.ADMIN).stream().findFirst()
        .orElseThrow(() -> new RuntimeException("Admin role not found"));
  }

  public Role getUserRole() {

    return roleRepository.findAllByRolePermissionContains(RolePermission.REGISTERED).stream()
        .findFirst().orElse(getDefaultRole());
  }

  public Role getDefaultRole() {

    return roleRepository.findAllByRolePermissionContains(RolePermission.DEFAULT).stream()
        .findFirst().orElseThrow(() -> new RuntimeException("User role not found"));
  }

  public Role getBannedRole() {

    return roleRepository.findAllByRolePermissionContains(RolePermission.BANNED).stream()
        .findFirst().orElseThrow(() -> new RuntimeException("Banned role not found"));
  }

  public boolean hasAdminOrPermission(final Person person, final RolePermission rolePermission) {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    return isAdmin(role) || hasPermission(role, rolePermission);
  }

  public boolean hasAdminOrPermission(@NonNull final Role role,
      final RolePermission rolePermission) {

    return isAdmin(role) || hasPermission(role, rolePermission);
  }

  public boolean hasAdminOrAnyPermission(@NonNull final Role role,
      final Set<RolePermission> rolePermissions) {

    return rolePermissions.stream().anyMatch(x -> hasPermission(role, x));
  }

  public boolean hasAdminOrAnyPermission(final Person person,
      final Set<RolePermission> rolePermissions) {

    final Role role = person == null ? getDefaultRole() : person.getRole();
    return hasAdminOrAnyPermission(role, rolePermissions);
  }

  public <X extends Throwable> void hasAdminOrAnyPermissionOrThrow(@NonNull final Role role,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasAdminOrAnyPermission(role, rolePermissions)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasAdminOrAnyPermissionOrThrow(final Person person,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasAdminOrAnyPermissionOrThrow(role, rolePermissions, exceptionSupplier);
  }

  public <X extends Throwable> void hasAdminOrPermissionOrThrow(@NonNull final Role role,
      final RolePermission rolePermission, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasAdminOrPermission(role, rolePermission)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasAdminOrPermissionOrThrow(final Person person,
      final RolePermission rolePermission, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasAdminOrPermissionOrThrow(role, rolePermission, exceptionSupplier);
  }

  public boolean hasAdminOrAllPermissions(@NonNull final Role role,
      final Set<RolePermission> rolePermissions) {

    return isAdmin(role) || hasAllPermissions(role, rolePermissions);
  }

  public boolean hasAdminOrAllPermissions(final Person person,
      final Set<RolePermission> rolePermissions) {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    return hasAdminOrAllPermissions(role, rolePermissions);
  }

  public <X extends Throwable> void hasAdminOrAllPermissionsOrThrow(@NonNull final Role role,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasAdminOrAllPermissions(role, rolePermissions)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasAdminOrAllPermissionsOrThrow(final Person person,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasAdminOrAllPermissionsOrThrow(role, rolePermissions, exceptionSupplier);
  }

  public boolean hasPermission(final Role role, final RolePermission rolePermission) {

    if (isAdmin(role)) {
      return true;
    }

    return role.getRolePermissions().stream()
        .anyMatch(x -> x.getPermission().equals(rolePermission));
  }

  public boolean hasPermission(final Person person, final RolePermission rolePermission) {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    return hasPermission(role, rolePermission);
  }

  public <X extends Throwable> void hasPermissionOrThrow(@NonNull Role role,
      final RolePermission rolePermission, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasPermission(role, rolePermission)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasPermissionOrThrow(final Person person,
      final RolePermission rolePermission, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasPermissionOrThrow(role, rolePermission, exceptionSupplier);
  }

  public boolean hasAllPermissions(@NonNull final Role role,
      final Set<RolePermission> rolePermissions) {

    return rolePermissions.stream().allMatch(x -> hasPermission(role, x));
  }

  public boolean hasAllPermissions(final Person person,
      final Set<RolePermission> rolePermissions) {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    return hasAllPermissions(role, rolePermissions);
  }

  public <X extends Throwable> void hasAllPermissionsOrThrow(@NonNull final Role role,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasAllPermissions(role, rolePermissions)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasAllPermissionsOrThrow(final Person person,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasAllPermissionsOrThrow(role, rolePermissions, exceptionSupplier);
  }

  public boolean hasAnyPermission(@NonNull final Role role,
      final Set<RolePermission> rolePermissions) {

    return rolePermissions.stream().anyMatch(x -> hasPermission(role, x));
  }

  public boolean hasAnyPermission(final Person person, final Set<RolePermission> rolePermissions) {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    return hasAnyPermission(role, rolePermissions);
  }

  public <X extends Throwable> void hasAnyPermissionOrThrow(@NonNull final Role role,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    if (!hasAnyPermission(role, rolePermissions)) {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> void hasAnyPermissionOrThrow(final Person person,
      final Set<RolePermission> rolePermissions, Supplier<? extends X> exceptionSupplier) throws X {

    final Role role = person == null ? getDefaultRole() : person.getRole();

    hasAnyPermissionOrThrow(role, rolePermissions, exceptionSupplier);
  }
}
