package com.sublinks.sublinksapi.api.sublinks.v1.search.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/search")
@Tag(name = "Search", description = "Search API")
public class SublinksSearchController extends AbstractSublinksApiController {
 @Operation(summary = "Get a list of privatemessages")
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
  })
  public void index() {
    // TODO: implement
  }
}
