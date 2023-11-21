package com.sublinks.sublinksapi.api.lemmy.v3.sitemap.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "Miscellaneous")
public class SitemapController extends AbstractLemmyApiController {

  @Operation(summary = "Gets the sitemap.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(mediaType = MediaType.APPLICATION_XML_VALUE)})
  })
  @GetMapping("api/v3/sitemap.xml")
  String index() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
