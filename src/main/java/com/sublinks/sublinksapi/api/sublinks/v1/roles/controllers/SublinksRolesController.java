package com.sublinks.sublinksapi.api.sublinks.v1.roles.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
@Tag(name = "Roles", description = "Roles API")
public class SublinksRolesController extends AbstractSublinksApiController {
 @Operation(summary = "Get a list of roles")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void index() {
    // TODO: implement
  }

  @Operation(summary = "Get a specific role")
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void show(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Create a new post")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void create() {
    // TODO: implement
  }

  @Operation(summary = "Update an post")
  @PostMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void update(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Delete an post")
  @DeleteMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void delete(@PathVariable String id) {
    // TODO: implement
  }
}
