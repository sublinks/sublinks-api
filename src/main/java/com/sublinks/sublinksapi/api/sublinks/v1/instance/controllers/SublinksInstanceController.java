package com.sublinks.sublinksapi.api.sublinks.v1.instance.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/instance")
@Tag(name = "Instance", description = "Instance API")
public class SublinksInstanceController extends AbstractSublinksApiController {
 @Operation(summary = "Get a list of instances")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void index() {
    // TODO: implement
  }

  @Operation(summary = "Get a specific instance")
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void show(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Create a new instance")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void create() {
    // TODO: implement
  }

  @Operation(summary = "Update an instance")
  @PostMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void update(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Delete an instance")
  @DeleteMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void delete(@PathVariable String id) {
    // TODO: implement
  }
}
