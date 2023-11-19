package com.sublinks.sublinksapi.api.lemmy.v3.sitemap.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "sitemap", description = "the sitemap API")
public class SitemapController extends AbstractLemmyApiController {
    @Operation(summary = "Gets the sitemap", tags = { "sitemap" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/xml") })
    })
    @GetMapping("api/v3/sitemap.xml")
    String index() {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
