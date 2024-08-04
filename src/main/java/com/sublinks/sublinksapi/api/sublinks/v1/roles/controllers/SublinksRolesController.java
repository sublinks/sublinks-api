package com.sublinks.sublinksapi.api.sublinks.v1.roles.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.IndexRole;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.PersonRoleResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.services.SublinksRoleService;
import com.sublinks.sublinksapi.authorization.services.AclService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/roles")
@Tag(name = "Roles", description = "Roles API")
public class SublinksRolesController extends AbstractSublinksApiController {

  private final SublinksRoleService sublinksRoleService;

  @Operation(summary = "Get a list of roles")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public List<PersonRoleResponse> index(final Optional<IndexRole> indexRoleForm, final SublinksJwtPerson sublinksJwtPerson) {

  }

  @Operation(summary = "Get a specific role")
  @GetMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonRoleResponse show(@PathVariable String key) {
    // TODO: implement
  }

  @Operation(summary = "Create a new Role")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonRoleResponse create() {
    // TODO: implement
  }

  @Operation(summary = "Update an Role")
  @PostMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PersonRoleResponse update(@PathVariable String key) {
    // TODO: implement
  }

  @Operation(summary = "Delete an Role")
  @DeleteMapping("/{key}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public void delete(@PathVariable String key) {
    // TODO: implement
  }
}
