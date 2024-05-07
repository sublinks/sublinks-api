package com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.controllers;

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
@RequestMapping("api/v1/privatemessage")
@Tag(name = "Privatemessage", description = "Privatemessage API")
public class SublinksPrivatemessageController extends AbstractSublinksApiController {
 @Operation(summary = "Get a list of privatemessages")
  @GetMapping
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void index() {
    // TODO: implement
  }

  @Operation(summary = "Get a specific privatemessage")
  @GetMapping("/{id}")
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void show(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Create a new privatemessage")
  @PostMapping
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void create() {
    // TODO: implement
  }

  @Operation(summary = "Update an privatemessage")
  @PostMapping("/{id}")
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void update(@PathVariable String id) {
    // TODO: implement
  }

  @Operation(summary = "Delete an privatemessage")
  @DeleteMapping("/{id}")
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void delete(@PathVariable String id) {
    // TODO: implement
  }
}
