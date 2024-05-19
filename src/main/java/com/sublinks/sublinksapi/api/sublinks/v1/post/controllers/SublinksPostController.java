package com.sublinks.sublinksapi.api.sublinks.v1.post.controllers;

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
@RequestMapping("api/v1/post")
@Tag(name = "Post", description = "Post API")
public class SublinksPostController extends AbstractSublinksApiController {

  @Operation(summary = "Get a list of posts")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void index() {
    // TODO: implement
    // @todo: implement cursor based pagination
  }

  @Operation(summary = "Get a specific post")
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
