package com.sublinks.sublinksapi.api.lemmy.v3.post.controllers;

import com.sublinks.sublinksapi.api.lemmy.v3.common.controllers.AbstractLemmyApiController;
import com.sublinks.sublinksapi.api.lemmy.v3.errorHandler.ApiError;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.ListPostReportsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostReportResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Transactional
@RequestMapping(path = "/api/v3/post")
@Tag(name = "Post")
public class PostModActionsController extends AbstractLemmyApiController {

  @Operation(summary = "A moderator remove for a post.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("remove")
  PostResponse remove() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "A moderator lock for a post.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("lock")
  PostResponse lock() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "A moderator feature for a post (Sticky).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PostResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Post Not Found",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class))})
  })
  @PostMapping("feature")
  PostResponse feature() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "Resolve a post report. Only a mod can do this.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PostReportResponse.class))})
  })
  @PostMapping("report/resolve")
  PostReportResponse reportResolve() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Operation(summary = "List post reports.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ListPostReportsResponse.class))})
  })
  @PostMapping("report/list")
  ListPostReportsResponse reportList() {

    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }
}
