package com.sublinks.sublinksapi.api.sublinks.v1.search.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/search")
@Tag(name = "Search", description = "Search API")
public class SearchController extends AbstractSublinksApiController {
 @Operation(summary = "Get a list of privatemessages")
  @GetMapping
  @ApiResponses(value = {
      // TODO: add responses
  })
  public void index() {
    // TODO: implement
  }
}
